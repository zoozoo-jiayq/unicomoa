package cn.com.qytx.cbb.org.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import cn.com.qytx.platform.base.action.BaseActionSupport;
import cn.com.qytx.platform.org.domain.GroupInfo;
import cn.com.qytx.platform.org.domain.RoleInfo;
import cn.com.qytx.platform.org.domain.RoleUser;
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.org.service.IGroup;
import cn.com.qytx.platform.org.service.IGroupUser;
import cn.com.qytx.platform.org.service.IRole;
import cn.com.qytx.platform.org.service.IRoleUser;
import cn.com.qytx.platform.org.service.IUser;
import cn.com.qytx.platform.utils.encrypt.MD5;
import cn.com.qytx.platform.utils.pinyin.FormatPinyinTo9Data;
import cn.com.qytx.platform.utils.pinyin.Pinyin4jUtil;

/**
 * 
 * <br/>功能: 人员导入
 * <br/>版本: 1.0
 * <br/>开发人员: 任
 * <br/>创建日期: 2013-4-11
 * <br/>修改日期: 2013-4-11
 * <br/>修改列表:
 */
public class ImportUserAction extends BaseActionSupport {
    protected static final Logger LOGGER = Logger.getLogger(ImportUserAction.class.getName());
    private static final long serialVersionUID = 1L;
    private File fileToUpload;
    private String uploadFileName;//设置上传文件的文件名
    private String uploadContentType;//上传文件的类型
    private String radioType;//导入类型
    private String file;//上传的文件
    private List<UserInfo> errorUserList=new ArrayList<UserInfo>();
    private String errorFile;
    private int allNum = 0 ;
    private int errorNum = 0 ;
    /**用户信息*/
    @Resource(name = "userService")
    IUser userService;
    /**
     * 部门,群组管理接口
     */
    @Resource(name = "groupService")
    private IGroup groupService;
    
    /**部门人员信息*/
    @Resource(name = "groupUserService")
    IGroupUser groupUserService;
    
    @Resource(name = "roleUserService")
    private IRoleUser roleUserService;

    @Autowired
    private IRole roleService;
    
    /**
     * 检查文件是否符合格式action
     *
     * @return
     * @throws Exception
     */
    public String check() throws Exception {

        try {
            if (uploadFile()) {
            	//原有的用户
            	List<UserInfo> oldUserList = userService.companyId(getLoginUser().getCompanyId()).unDeleted().findAll();
            	 Map<String,GroupInfo> groupMap = ExcelUtil.getGroupList(getLoginUser().getCompanyId(), file);
                String result = checkExcel(file,groupMap,oldUserList);
                ajax(result);
            }

        } catch (Exception e) {
            LOGGER.error(e);
            ajax("对不起！导入文件时出错！");
        }
        return null;
    }


    /**
     * 导入人员action
     */
    public String importUser() throws Exception {


        try {
            if (uploadFile()) {
                String result = startImportUser(file);
                if (allNum > 0) {
                    String path = this.getRequest().getContextPath();
                    String basePath = getRequest().getScheme() + "://" + getRequest().getServerName() + ":" + getRequest().getServerPort() + path + "/";

                    result = "共" + allNum + "行数据，导入成功" + (allNum-errorNum) + "行,失败" + errorNum + "行";
                    if(errorFile!=null && !errorFile.equals(""))
                    {
                        result = "共" + allNum + "行数据，导入成功" + (allNum-errorNum) + "行,失败" + errorNum + "行,&nbsp;&nbsp;<a href='"+basePath+"/tempfiles/"+errorFile+"'>点击下载失败文件"+"</a>";
                    }
                }
                ajax(result);
            }

        } catch (Exception e) {
            LOGGER.error(e);
            ajax("对不起！导入文件时出错！");
        }
        return null;
    }

    /**
     * 上传文件
     */
    private boolean uploadFile() {
        String path = ServletActionContext.getRequest().getRealPath("/upload");
        File checkPath = new File(path);
        if (!checkPath.exists()) {
            //目录不存在，则创建目录
            boolean result = checkPath.mkdirs();
            if (!result){
                return false;
            }
        }
        try {
            if (fileToUpload != null) {
                String fileName = UUID.randomUUID().toString();
                String ext = ".xls";
                if (uploadFileName != null) {
                    ext = uploadFileName.substring(uploadFileName.lastIndexOf("."));
                }
                if (!ext.equals(".xls")) {
                    return false;
                }
                String saveFileName = fileName + ext;
                File savefile = new File(new File(path), saveFileName);
                if (!savefile.getParentFile().exists()){
                    boolean result = savefile.getParentFile().mkdirs();
                    if (!result){
                        return false;
                    }
                }
                    
                FileUtils.copyFile(fileToUpload, savefile);//拷贝文件
                file = path + "/" + saveFileName;

            }

        } catch (Exception e) {

            return false;
        }
        return true;
    }

    /**
     * 判断文件是否符合要求
     *
     * @param excel 导入的文件名称
     * @param groupInfoMap  导入文件中的部门名称和部门对象的映射
     * @param oldusers 单位中已经存在的用户
     * @return 空字符串表示验证成功 其他失败
     * @throws Exception
     */
    private String checkExcel(String excel,Map<String,GroupInfo> groupInfoMap,List<UserInfo> oldusers) {
        Workbook wb = null;
        String result = "";
        try {
            Integer companyId=0;
            Object obj=getSession().getAttribute("adminUser");
            if(obj!=null){
            	UserInfo loginUser=(UserInfo)obj;
            	companyId=loginUser.getCompanyId();
            }
            InputStream is = new FileInputStream(excel);
            wb = Workbook.getWorkbook(is);
            if(wb==null)
            {
                result="要导入的文件不正确！";
                return result;
            }
            Sheet sheet = wb.getSheet("通讯录");    //获取工作Excel
            if(sheet==null)
            {
                result="要导入的文件不正确！";
                return result;
            }
            boolean flag = true;
            int totalRow=sheet.getRows();
            if(totalRow==1)
            {
                result="请填写要导入的内容！";
                return result;
            }
            
            //用户判断电话号码是否重复
            Map<String,Boolean> phoneMap = new HashMap<String, Boolean>();
            
            //得到列的长度
            Cell[] cellsZero = sheet.getRow(0);    //读标题行
            Integer headLength=cellsZero.length;
            //第一行不判断
            for (int j = 1; j < sheet.getRows(); j++) {
                Cell[] cells = sheet.getRow(j);    //读取一行
                // 如果这行全部为空,则不处理
                boolean isEmpty = true;
                if(cells!=null){
                for (Cell cell : cells)
                {
                    String val = cell.getContents();// 内容
                    if (null != val && !"".equals(val.trim()))
                    {
                        isEmpty = false;
                        break;
                    }
                }
                }
                if (isEmpty)
                {
                    continue;
                } 
                
                if(cells!=null&&cells.length<headLength){
                    result = "第" + (j + 1) + "行数据不完整！";
                    break;
                }
                if (cells != null && cells.length > 0) {
                    for (int k = 0; k < cells.length; k++) {

                        String val = cells[k].getContents();//内容
                        if (val != null) {
                            val = val.trim();
                        }
                      if (k == 0) {
                            //判断姓名
                            if (val == null || val.equals("")) {
                                result = "第" + (j + 1) + "行第" + (k + 1) + "列姓名不能为空！";
                                flag = false;//退出循环
                                break;
                            } else {
                                if (val.length() > 20) {
                                    result = "第" + (j + 1) + "行第" + (k + 1) + "列姓名长度不能大于20！";
                                    flag = false;//退出循环
                                    break;
                                }
                            }
                        } else if (k == 1) {
                            //判断部门
                            if (val == null || val.equals("")) {
                                result = "第" + (j + 1) + "行第" + (k + 1) + "列部门不能为空！";
                                flag = false;//退出循环
                                break;
                            } else {
                                if (val.length() > 30) {
                                    result = "第" + (j + 1) + "行第" + (k + 1) + "列部门长度不能大于30！";
                                    flag = false;//退出循环
                                    break;
                                }else{
                                	//判断部门是否存在
                                	GroupInfo  groupInfoVO=groupInfoMap.get(val);
                                	if(groupInfoVO==null){
                                        result = "第" + (j + 1) + "行第" + (k + 1) + "列部门("+val+")不存在！";
                                        flag = false;//退出循环
                                        break;
                                	}
                                }
                            }
                        } else if (k == 2) {
                            //判断性别
                            if (val != null && !"".equals(val)){
                                if(!("男".equals(val) || "女".equals(val))){
                                    result = "第" + (j + 1) + "行第" + (k + 1) + "列性别填写错误！";
                                    flag = false;//退出循环
                                    break;
                                }
                            }else{
                            	result ="第" + (j + 1) + "行第" + (k + 1) + "列性别不能为空！";
                            	flag = false;
                            	break;
                            }
                        }else if (k == 3) {
                            //判断移动电话1
                            if (val == null || val.equals("")) {
                                result = "第" + (j + 1) + "行第" + (k + 1) + "列联系电话不能为空！";

                                flag = false;//退出循环
                                break;
                            } else {
                                if (!CheckUtil.isNumeric(val)) {
                                    result = "第" + (j + 1) + "行第" + (k + 1) + "列联系电话格式不正确，应该为11位手机号码！";
                                    flag = false;//退出循环
                                    break;
                                } else if (val.length() != 11) {
                                    result = "第" + (j + 1) + "行第" + (k + 1) + "列联系电话格式不正确，应该为11位手机号码！";
                                    flag = false;//退出循环
                                    break;
                                }
                            }
                            
                            Boolean phoneFlag = phoneMap.get(val);
                            if(phoneFlag == null || phoneFlag == false){
                            	phoneMap.put(val, true);
                            }else{
                            	result = "第" + (j + 1) + "行第" + (k + 1) + "列联系电话在EXCEL中有重复!";
                                flag = false;//退出循环
                                break;
                            }
                            
                            //判断电话号码是否重复，如果电话重复而姓名不重复则非法
                            if(oldusers!=null){
                            	for(Iterator<UserInfo> it = oldusers.iterator(); it.hasNext();){
                            		UserInfo ui = it.next();
                            		if(ui.getPhone()!=null && ui.getPhone().equals(val)){
                            			if(ui.getUserName()!=null && !(cells[0].getContents().equals(ui.getUserName()))){
                            				result = "第" + (j + 1) + "行第" + (k + 1) + "列联系电话重复！";
                            				flag = false;
                            				break;
                            			}
                            		}
                            	}
                            }
                            
                        }
                    }//column
                }
                if (!flag) {
                    break;
                }
            }//one sheet

        } catch (Exception ex) {
            result = ex.getMessage().toString();
        } finally {
            if (wb != null) {
                wb.close();
            }
        }
        return result;
    }

    /**
     * 开始导入人员
     *
     * @param excel
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
	private String startImportUser(String excel) throws Exception {
        UserInfo adminUser = (UserInfo) this.getSession().getAttribute("adminUser");
        int companyId = adminUser.getCompanyId();
        int maxOrder = userService.getMaxOrderIndex(adminUser.getCompanyId());
        //从excel中提取部门信息
        Map<String,GroupInfo> groupMap = ExcelUtil.getGroupList(companyId, excel);
        //原有的用户
    	List<UserInfo> oldUserList = userService.companyId(companyId).unDeleted().findAll();
        String check = checkExcel(excel,groupMap,oldUserList);
        if (!check.equals("")) {
            return check;
        } else {
        	
        	//从excel中提取用户信息
            List<UserInfo> userList = ExcelUtil.getUserList(excel);
            List<UserInfo> lastUsers = new ArrayList<UserInfo>();
            RoleInfo role = roleService.findByRoleCode("common_role");
            if(userList!=null && userList.size()>0){
                allNum=userList.size();//总个数
                errorNum = 0 ;
                for(UserInfo user:userList){
	                  //开始导入人员
	            	  try{
	            	   //填充未设置字段           
	                   //根据部门名称得到部门代码
	                   Integer groupId=0;
	                   GroupInfo  groupInfoVO = groupMap.get(user.getGroupName()); 
	                   if(groupInfoVO!=null){
	                	   groupId=groupInfoVO.getGroupId();
	                	   user.setGroupId(groupId);
	                	   user.setIsForkGroup(groupInfoVO.getIsForkGroup());
	                   }else{
	                       errorNum++;
	                       break;
	                   }
	                   
	                   // 判断是否重复
	                   UserInfo  oldUserInfo = getOldUser(oldUserList, user.getUserName(), user.getPhone());
	                   if (null != oldUserInfo)
	                   {
	                	   int oldGroupId = oldUserInfo.getGroupId();
	                	   oldUserInfo.setGroupId(groupInfoVO.getGroupId());
	                	   // 性别
                           oldUserInfo.setSex( user.getSex()!=null? user.getSex():0);
                           
                           user.setLastUpdateTime(new Date());
//                           userService.saveOrUpdate(oldUserInfo);
                           lastUsers.add(oldUserInfo);
                           if(oldUserInfo.getGroupId()!=oldGroupId && !oldUserInfo.getGroupId().equals(oldGroupId)){
	                       		groupService.updateGroupTime(oldUserInfo.getGroupId());
	                       		groupService.updateGroupTime(oldGroupId);
                       	   }
	                       continue;
	                   }
	                   
	                   //登录密码默认123456
	                   MD5 md5=new MD5();
	                   String pass=md5.encrypt("123456");
	                   user.setIsDelete(0);
	                   user.setLoginPass(pass);
	                   user.setCompanyId(adminUser.getCompanyId());
	                   user.setPartitionCompanyId(adminUser.getCompanyId()%10);
	           
	                   SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	                 //  user.setRegisterTime(new Date());
	                   user.setCreateTime(new Date());
	                   user.setLastUpdateTime(new Date());
	                   String py=Pinyin4jUtil.getPinYinHeadChar(user.getUserName());
	                   user.setPy(py);
		       	        //获得全拼拼音 带空格
		       	        String fullPy = Pinyin4jUtil.getPinYinWithBlank(user.getUserName());
		       	        user.setFullPy(fullPy);
		       	        //获得用户姓名全拼对应九宫格按键
		       	        String formattedNumber = FormatPinyinTo9Data.getFormattedNumber(fullPy);
		       	        user.setFormattedNumber(formattedNumber);
		       	        //user.setOrderIndex(9999);
	                   user.setSkinLogo(1);
	                   user.setIsDefault(1);
	                   user.setTaoDa(0);
	                   user.setOfficeWidget(0);
	                   user.setSinWidget(0);
	                   //设置用户为登录用户  add by jiayq
	                   user.setUserState(UserInfo.USERSTATE_LOGIN);
	                   user.setLoginName(user.getPhone());
	                   user.setRegisterTime(new Date());
	                   
	                   user.setOrderIndex(++maxOrder);
//	                   userService.saveOrUpdate(user);
	                   lastUsers.add(user);
	            	  }catch (Exception e) {
	            		  errorUserList.add(user);
	            		  e.printStackTrace();
	            	  }finally{
	            		  
	            	  }
	            	
                }
               userService.saveOrUpdate(lastUsers);
               List<RoleUser> roleUserList = new ArrayList<RoleUser>();
               if(role!=null){
            	   if(lastUsers!=null&&lastUsers.size()>0){
            		   for (UserInfo userInfo : lastUsers) {
            			   RoleUser roleUser = new RoleUser();
            			   roleUser.setRoleId(role.getRoleId());
            			   roleUser.setUserId(userInfo.getUserId());
            			   roleUser.setCompanyId(companyId);
            			   roleUser.setType(1);
            			   roleUserList.add(roleUser);
            		   }
            		   roleUserService.saveOrUpdate(roleUserList);
               }
               }
            }
        }
        return "";
    }
    
    private UserInfo getOldUser(List<UserInfo> oldusers,String userName,String phone){
    	if(oldusers!=null && oldusers.size()>0){
    		for(int i=0; i<oldusers.size(); i++){
    			UserInfo ui = oldusers.get(i);
    			if(ui.getUserName()!=null && ui.getPhone()!=null){
	    			if(ui.getUserName().equals(userName) && ui.getPhone().equals(phone)){
	    				return ui;
	    			}
    			}
    		}
    	}
    	return null;
    }
   
    
    
    /**
     * 替换掉str里面的特殊字符
     * @param str
     * @return
     */
    private static String stringFilter(String str){
        String regEx="[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Pattern p   =   Pattern.compile(regEx);
        Matcher m   =   p.matcher(str);
        return m.replaceAll("").trim();
    }
    public File getFileToUpload() {
        return fileToUpload;
    }

    public void setFileToUpload(File fileToUpload) {
        this.fileToUpload = fileToUpload;
    }

    public String getUploadFileName() {
        return uploadFileName;
    }

    public void setUploadFileName(String uploadFileName) {
        this.uploadFileName = uploadFileName;
    }

    public String getUploadContentType() {
        return uploadContentType;
    }

    public void setUploadContentType(String uploadContentType) {
        this.uploadContentType = uploadContentType;
    }

    public String getRadioType() {
        return radioType;
    }

    public void setRadioType(String radioType) {
        this.radioType = radioType;
    }

}
