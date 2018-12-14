package cn.com.qytx.oa.publicaddress.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.biff.EmptyCell;
import jxl.read.biff.BiffException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import cn.com.qytx.oa.publicaddress.domain.Address;
import cn.com.qytx.oa.publicaddress.domain.AddressConst;
import cn.com.qytx.oa.publicaddress.service.IAddress;
import cn.com.qytx.oa.util.StringUtil;
import cn.com.qytx.platform.base.action.BaseActionSupport;
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.utils.CbbConst;
import cn.com.qytx.platform.utils.datetime.DateTimeUtil;
import cn.com.qytx.platform.utils.pinyin.Pinyin4jUtil;

/**
 * 功能:
 * 版本: 1.0
 * 修改列表:
 */
public class AddressImportAction extends BaseActionSupport
{

    /**
     * 描述含义
     */
    private static final long serialVersionUID = 4788176104230359914L;
    
    /**
     * 日志类
     */
    private static final Logger logger = Logger.getLogger(AddressImportAction.class);
    
    /**
     * 通讯薄联系人service
     */
    @Autowired
    IAddress addressImpl;
    
    private String uploadFileName;// 设置上传文件的文件名
    /**
     * 上传文件
     */
    private File fileToUpload;

    /**
     * //上传的文件名
     */
    private String file;
    
    /**
     * 群组Id
     */
    private Integer groupId;

    /**
     * 群组名称
     */
    private String groupName;

    private int allNum = 0;//总个数
    private int successNum = 0;//成功个数
    private int skipNum = 0;//跳过个数
    private int overrideNum = 0;//覆盖个数
    private String errorFile;
    public void importCheckAddress() throws IOException
    {
        try
        {
            if (uploadFile())
            {
                String result = checkExcel(file);
               ajax(result);
            }

        }
        catch (Exception e)
        {
//            logger.error(e);
//            writer = new PrintWriter(this.getResponse().getWriter());
//            writer.print("对不起！导入文件时出错！");
//            writer.close();
            ajax("对不起！导入文件时出错！");
        }
    }

    /**
     * 上传文件
     */
    @SuppressWarnings("deprecation")
    private boolean uploadFile()
    {
        String path = ServletActionContext.getRequest().getRealPath("/upload");
        File checkPath = new File(path);
        if (!checkPath.exists())
        {
            // 目录不存在，则创建目录
            boolean result = checkPath.mkdirs();
            if (!result)
            {
                return false;
            }
        }
        try
        {
            if (fileToUpload != null)
            {
                String fileName = UUID.randomUUID().toString();
                String ext = ".xls";
                if (uploadFileName != null)
                {
                    ext = uploadFileName.substring(uploadFileName.lastIndexOf("."));
                }
                if (!ext.equals(".xls"))
                {
                    return false;
                }
                String saveFileName = fileName + ext;
                File savefile = new File(new File(path), saveFileName);
                if (!savefile.getParentFile().exists())
                {
                    boolean result = savefile.getParentFile().mkdirs();
                    if (!result)
                    {
                        
                    }
                }
                FileUtils.copyFile(fileToUpload, savefile);// 拷贝文件
                file = path + "/" + saveFileName;

            }

        }
        catch (Exception e)
        {

            return false;
        }
        return true;
    }

    /**
     * 判断文件是否符合要求
     * @param excel
     * @return 空字符串表示验证成功 其他失败
     * @throws Exception
     */
    private String checkExcel(String excel)
    {
        Workbook wb = null;
        String result = "";
        try
        {
            Integer companyId = 0;
            Object obj = getSession().getAttribute("adminUser");
            if (obj != null)
            {
                UserInfo loginUser = (UserInfo) obj;
                companyId = loginUser.getCompanyId();
            }
            InputStream is = new FileInputStream(excel);
            wb = Workbook.getWorkbook(is);
            if (wb == null)
            {
                result = "要导入的文件不正确！";
                return result;
            }
            Sheet sheet = wb.getSheet(0);    // 获取工作Excel
            if (sheet == null)
            {
                result = "要导入的文件不正确！";
                return result;
            }
            boolean flag = true;
            int totalRow = sheet.getRows();
            if (totalRow == 1)
            {
                result = "请填写要导入的内容！";
                return result;
            }
            // 得到列的长度
//            Cell[] cellsZero = sheet.getRow(0);    // 读标题行
//            Integer headLength = cellsZero.length - 1;
            // 第一行不判断
            for (int j = 1; j < sheet.getRows(); j++)
            {
                Cell[] cells = sheet.getRow(j);    // 读取一行
                // 如果这行全部为空,则不处理
                boolean isEmpty = true;
                if(cells!=null){
                for (Cell cell : cells)
                {
                    String val = cell.getContents();// 内容
                    if (StringUtils.isNotBlank(val))
                    {
                        isEmpty = false;
                        break;
                    }
                }}
                if (isEmpty)
                {
                    continue;
                }

//                if (cells != null && cells.length < 13)
//                {
//                    result = "第" + (j + 1) + "行数据不完整！";
//                    break;
//                }
                if (cells != null && cells.length > 0)
                {
                    for (int k = 0; k < cells.length; k++)
                    {

                        String val = cells[k].getContents();// 内容
                        if (val != null)
                        {
                            val = val.trim();
                        }
                        if (k == 0)
                        {
                            // 姓名： 25个字符以内 非空
                            if (StringUtil.isEmpty(val))
                            {
                                result = "第" + (j + 1) + "行第" + (k + 1) + "列姓名不能为空！";
                                flag = false;// 退出循环
                                break;
                            }
                            else
                            {
                                if (val.length() > 25)
                                {
                                    result = "第" + (j + 1) + "行第" + (k + 1) + "列姓名长度不能大于25！";
                                    flag = false;// 退出循环
                                    break;
                                }
                            }
                        }
                        else if (k == 1)
                        {
                            // 性別： 可為空,必須為男或者女
                            if (!StringUtil.isEmpty(val))
                            {
                                if (!(AddressConst.MEN.equals(val) || AddressConst.WOMEN.equals(val)))
                                {
                                    result = "第" + (j + 1) + "行第" + (k + 1) + "列性別只能输入男/女！";
                                    flag = false;// 退出循环
                                    break;
                                }
                            }
                        }
                        else if (k == 2)
                        {
                            // 生日：yyyy-MM-dd 可为空
                            if (!StringUtil.isEmpty(val))
                            {
                                if (!DateTimeUtil.isValidDate(val, CbbConst.DATE_FORMAT_STR))
                                {
                                	result = "第" + (j + 1) + "行第" + (k + 1) + "列生日格式不正确！";
                                    flag = false;// 退出循环
                                    break;
                                }
                            }
                        }
                        else if (k == 3)
                        {
                            // 昵称：25字符以内 可为空
                            if (!StringUtil.isEmpty(val))
                            {
                                if (val.length() > 25)
                                {
                                    result = "第" + (j + 1) + "行第" + (k + 1) + "列昵称长度不能大于25！";
                                    flag = false;// 退出循环
                                    break;
                                }
                            }
                        }
                        else if (k == 4)
                        {
                            // 职务：25字符以内 可为空
                            if (!StringUtil.isEmpty(val))
                            {
                                if (val.length() > 25)
                                {
                                    result = "第" + (j + 1) + "行第" + (k + 1) + "列职务长度不能大于25！";
                                    flag = false;// 退出循环
                                    break;
                                }
                            }
                        }
                        else if (k == 5)
                        {
                            // 配偶：25字符以内 可为空
                            if (!StringUtil.isEmpty(val))
                            {
                                if (val.length() > 25)
                                {
                                    result = "第" + (j + 1) + "行第" + (k + 1) + "列配偶长度不能大于25！";
                                    flag = false;// 退出循环
                                    break;
                                }
                            }
                        }
                        else if (k == 6)
                        {
                            // 子女：25字符以内 可为空
                            if (!StringUtil.isEmpty(val))
                            {
                                if (val.length() > 25)
                                {
                                    result = "第" + (j + 1) + "行第" + (k + 1) + "列子女长度不能大于25！";
                                    flag = false;// 退出循环
                                    break;
                                }
                            }
                        }
                        else if (k == 7)
                        {
                            // 单位名称： 200字符以内 可为空
                            if (!StringUtil.isEmpty(val))
                            {
                                if (val.length() > 200)
                                {
                                    result = "第" + (j + 1) + "行第" + (k + 1) + "列单位名称长度不能大于200！";
                                    flag = false;// 退出循环
                                    break;
                                }
                            }
                        }
                        else if (k == 8)
                        {
                            // 单位地址： 200字符以内 可为空
                            if (!StringUtil.isEmpty(val))
                            {
                                if (val.length() > 200)
                                {
                                    result = "第" + (j + 1) + "行第" + (k + 1) + "列单位地址长度不能大于200！";
                                    flag = false;// 退出循环
                                    break;
                                }
                            }
                        }
                        else if (k == 9)
                        {
                            // 单位邮编： 6为数字 可为空
                            if (!StringUtil.isEmpty(val))
                            {
                                if (!isZipCode(val))
                                {
                                    result = "第" + (j + 1) + "行第" + (k + 1) + "列单位邮编格式不正确！";
                                    flag = false;// 退出循环
                                    break;
                                }
                            }
                        }
                        else if (k == 10)
                        {
                            // 工作电话： 7-12位有效数字 可为空
                            if (!StringUtil.isEmpty(val))
                            {
                                if (!isPhone(val))
                                {
                                    result = "第" + (j + 1) + "行第" + (k + 1) + "列工作电话格式不正确！";
                                    flag = false;// 退出循环
                                    break;
                                }
                            }
                        }
                        else if (k == 11)
                        {
                            // 工作传真：7-12位有效数字 可为空
                            if (!StringUtil.isEmpty(val))
                            {
                                if (!isPhone(val))
                                {
                                    result = "第" + (j + 1) + "行第" + (k + 1) + "列工作传真格式不正确！";
                                    flag = false;// 退出循环
                                    break;
                                }
                            }
                        }
//                        else if (k == 12)
//                        {
//                            // 家庭住址： 200字符以内 可为空
//                            if (!StringUtil.isEmpty(val))
//                            {
//                                if (val.length() > 200)
//                                {
//                                    result = "第" + (j + 1) + "行第" + (k + 1) + "列家庭住址长度不能大于200！";
//                                    flag = false;// 退出循环
//                                    break;
//                                }
//
//                            }
//                        }
//                        else if (k == 13)
//                        {
//                            // 家庭邮编： 6为数字 可为空
//                            if (!StringUtil.isEmpty(val))
//                            {
//                                if (!isZipCode(val))
//                                {
//                                    result = "第" + (j + 1) + "行第" + (k + 1) + "列家庭邮编格式不正确！";
//                                    flag = false;// 退出循环
//                                    break;
//                                }
//                            }
//                        }
//                        else if (k == 14)
//                        {
//                            // 家庭电话： 7-12位有效数字 可为空
//                            if (!StringUtil.isEmpty(val))
//                            {
//                                if (!isPhone(val))
//                                {
//                                    result = "第" + (j + 1) + "行第" + (k + 1) + "列家庭电话格式不正确！";
//                                    flag = false;// 退出循环
//                                    break;
//                                }
//
//                            }
//                        }
                        else if (k == 12)
                        {
                            // 手机：手机号码 不可为空
                            if (!StringUtil.isEmpty(val))
                            {
                                if (!isMobilePhone(val))
                                {
                                    result = "第" + (j + 1) + "行第" + (k + 1) + "列手机格式不正确！";
                                    flag = false;// 退出循环
                                    break;
                                }
                            }else{
                                result = "第" + (j + 1) + "行第" + (k + 1) + "列手机不能为空！";
                                flag = false;// 退出循环
                                break;
                            }
                        }
//                        else if (k == 16)
//                        {
//                            // 电子邮件： 电子邮件格式 可为空
//                            if (!StringUtil.isEmpty(val))
//                            {
//                                if (!isEmail(val))
//                                {
//                                    result = "第" + (j + 1) + "行第" + (k + 1) + "列电子邮件格式不正确！";
//                                    flag = false;// 退出循环
//                                    break;
//                                }
//                            }
//                        }
                        else if (k == 13)
                        {
                            // 备注： 500字符以内 可为空
                            if (!StringUtil.isEmpty(val))
                            {
                                if (val.length() > 500)
                                {
                                    result = "第" + (j + 1) + "行第" + (k + 1) + "列备注格式不正确！";
                                    flag = false;// 退出循环
                                    break;
                                }
                            }
                        }
                    }// column
                }
                if (!flag)
                {
                    break;
                }
            }// one sheet

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            result = ex.getMessage().toString();
        }
        finally
        {
            if (wb != null)
            {
                wb.close();
            }
        }
        return result;
    }

    /**
     * 功能：是否为数字
     * @param str
     * @return
     */
    public static boolean isNum(String str)
    {
        return str.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
    }

    /**
     * 功能：是否为邮编
     * @param str
     * @return
     */
    public static boolean isZipCode(String str)
    {
        return str.matches("^[0-9]{6}$");
    }

    /**
     * 功能：是否为电话号码
     * @param str
     * @return
     */
    public static boolean isPhone(String str)
    {
        return str.matches("^[0-9]{7,12}$");
    }

    /**
     * 功能：是否为手机号码
     * @param str
     * @return
     */
    public static boolean isMobilePhone(String str)
    {
        return str.matches("^(13[0-9]|15[0-9]|18[0-9])[0-9]{8}$");
    }

    /**
     * 功能：是否为邮箱
     * @param str
     * @return
     */
    public static boolean isEmail(String str)
    {
        return str.matches("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$");
    }

    public File getFileToUpload()
    {
        return fileToUpload;
    }

    public void setFileToUpload(File fileToUpload)
    {
        this.fileToUpload = fileToUpload;
    }

    public String getFile()
    {
        return file;
    }

    public void setFile(String file)
    {
        this.file = file;
    }

    public String getUploadFileName()
    {
        return uploadFileName;
    }

    public void setUploadFileName(String uploadFileName)
    {
        this.uploadFileName = uploadFileName;
    }
    
    
    
    /**
     * 导入人员action
     */
    public String importAddress() throws Exception {


        try {
            if (uploadFile()) {
                String result = startImportUser(file);
                if (allNum > 0) {
                    int failNum = allNum - successNum - skipNum - overrideNum;//失败条数
                    if (failNum < 0) {
                        failNum = 0;
                    }
                    String path = this.getRequest().getContextPath();
                    String basePath = getRequest().getScheme() + "://" + getRequest().getServerName() + ":" + getRequest().getServerPort() + path + "/";

                    result = "共" + allNum + "行数据，导入成功" + (successNum) + "行,失败" + failNum + "行";
                    if(errorFile!=null && !errorFile.equals(""))
                    {
                        result = "共" + allNum + "行数据，导入成功" + (successNum) + "行,失败" + failNum + "行,&nbsp;&nbsp;<a href='"+basePath+"/tempfiles/"+errorFile+"'>点击下载失败文件"+"</a>";

                    }
                }
//                writer.print(result);
//                writer.close();
                ajax(result);
            }

        } catch (Exception e) {
            ajax("对不起！导入文件时出错！");
        }
        return null;
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
        String check = checkExcel(excel);
        if (!check.equals("")) {
            return check;
        } else {
            List<Address> list=getUserList(excel);
            if(list!=null && list.size()>0)
            {
                allNum=list.size();//总个数
                successNum=list.size();//成功数量
            }
            
            addressImpl.saveAddressList(list, groupName, groupId, adminUser);
        }
        
        
        return "";
    }
    
    /**
     * 由文件生成用户列表
     * @param excel
     * @return
     * @throws  
     */
    private List<Address> getUserList(String excel)
    {
        List<Address> list =new ArrayList<Address>();
        Workbook wb = null;
        try
        {
            InputStream is = new FileInputStream(excel);
            wb = Workbook.getWorkbook(is);
            Sheet sheet = wb.getSheet(0);    //获取工作Excel
            //第一行不判断
            for (int j = 1; j < sheet.getRows(); j++) {

                Cell[] cells = sheet.getRow(j);    //读取一行
                if (cells != null && cells.length > 0) {
                    Address tempAddress=new Address();
                    for (int k = 0; k < cells.length; k++) {
                        String val = cells[k].getContents();//内容
                        if (val != null) {
                            val = val.trim();
                        }else{
                            continue;
                        }
                        if (k == 0) {
                            tempAddress.setName(val);

                            // 根据联系人名字获取首字母
                            String firstLetter = Pinyin4jUtil.getPinYinHeadChar(val.substring(0, 1));
                            tempAddress.setFirstLetter(firstLetter);
                        }else if (k == 1) {
                            if (AddressConst.MEN.equals(val))
                            {
                                tempAddress.setSex(AddressConst.MEN_INT);
                            }
                            else if (AddressConst.WOMEN.equals(val))
                            {
                                tempAddress.setSex(AddressConst.WOMEN_INT);
                            }
                        }else if (k == 2) {
                        	if(cells[k]!=null && !cells[k].getClass().equals(EmptyCell.class)){
//                        		 DateCell dc = (DateCell) cells[k];
                        		 String birthday = cells[k].getContents();
                        		 if(!StringUtil.isEmpty(birthday)){
                        			 SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                        			 Date date = format.parse(birthday);
                        			 tempAddress.setBirthday(new Timestamp(date.getTime()));
                        		 }
                        	}
                        }else if (k == 3) {
                            tempAddress.setNickname(val);
                        }else if (k == 4) {
                            tempAddress.setPostInfo(val);
                        }else if (k == 5) {
                            tempAddress.setWifeName(val);
                        }else if (k == 6) {
                            tempAddress.setChildren(val);
                        }else if (k == 7) {
                            tempAddress.setCompanyName(val);
                        }else if (k == 8) {
                            tempAddress.setCompanyAddress(val);
                        }else if (k == 9) {
                            tempAddress.setCompanyPostCode(val);
                        }else if (k == 10) {
                            tempAddress.setOfficePhone(val);
                        }else if (k == 11) {
                            tempAddress.setOfficeFax(val);
//                        }else if (k == 12) {
//                            tempAddress.setFamilyAddress(val);
//                        }else if (k == 13) {
//                            tempAddress.setFamilyPostCode(val);
//                        }else if (k == 14) {
//                            tempAddress.setFamilyPhoneNo(val);
                        }else if (k == 12) {
                            tempAddress.setFamilyMobileNo(val);
//                        }else if (k == 16) {
//                            tempAddress.setFamilyEmail(val);
                        }else if (k == 13) {
                            tempAddress.setRemark(val);
                        }
                    }
                    list.add(tempAddress);
                }
            }//one sheet

        }
        catch (IOException ex)
        {
            ex.printStackTrace();
            logger.error(ex);
        }
        catch (BiffException ex)
        {
            ex.printStackTrace();
            logger.error(ex);
        }catch(Exception e){
        	e.printStackTrace();
        }
        finally {
            if(wb!=null)
            {
                wb.close();
            }
        }
        return list;
    }

    public Integer getGroupId()
    {
        return groupId;
    }

    public void setGroupId(Integer groupId)
    {
        this.groupId = groupId;
    }

    public String getGroupName()
    {
        return groupName;
    }

    public void setGroupName(String groupName)
    {
        this.groupName = groupName;
    }

    public String getErrorFile()
    {
        return errorFile;
    }

    public void setErrorFile(String errorFile)
    {
        this.errorFile = errorFile;
    }
}
