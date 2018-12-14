package cn.com.qytx.cbb.module.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import cn.com.qytx.cbb.module.domain.ModuleInfoMobile;
import cn.com.qytx.cbb.module.service.IModuleInfoMobile;
import cn.com.qytx.platform.base.action.BaseActionSupport;
/**
 * 导入抽奖人员action
 * @author liyanlei
 *
 */
public class UploadAction extends BaseActionSupport{
		
	protected static final Logger LOGGER = Logger.getLogger(UploadAction.class.getName());
    private static final long serialVersionUID = 1L;
    private File fileToUpload;
    private String uploadFileName;//设置上传文件的文件名
    private String uploadContentType;//上传文件的类型
    private String radioType;//导入类型
    private String file;//上传的文件
    private int allNum = 0;//总个数
    private int successNum = 0;//成功个数
    private int skipNum = 0;//跳过个数
    private int errorNum = 0;//跳过个数
    private int overrideNum = 0;//覆盖个数
    private List<ModuleInfoMobile> errorModuleList=new ArrayList<ModuleInfoMobile>();
    private String errorFile;
    
    @Resource(name="moduleInfoMobileService")
    private IModuleInfoMobile moduleInfoMobileService;
    
    /**
     * 检查文件是否符合格式action
     *
     * @return
     * @throws Exception
     */
    public String check() throws Exception {

        try {
            if (uploadFile()) {
                String result = checkExcel(file);
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
    public String importModule() throws Exception {


        try {
            if (uploadFile()) {
                String result = startImportModule(file);
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
                ajax("导入成功！");
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
     * @param excel
     * @return 空字符串表示验证成功 其他失败
     * @throws Exception
     */
    private String checkExcel(String excel) {
        Workbook wb = null;
        String result = "";
        try {
            InputStream is = new FileInputStream(excel);
            wb = Workbook.getWorkbook(is);
            if(wb==null)
            {
                result="要导入的文件不正确！";
                return result;
            }
            Sheet sheet = wb.getSheet("手机模块");    //获取工作Excel
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
                            //模块名称
                            if (val == null || val.equals("")) {
                                result = "第" + (j + 1) + "行第" + (k + 1) + "列模块名称不能为空！";
                                flag = false;//退出循环
                                break;
                            } else {
                                if (val.length() > 50) {
                                    result = "第" + (j + 1) + "行第" + (k + 1) + "列模块名称长度不能大于50！";
                                    flag = false;//退出循环
                                    break;
                                }
                            }
                        } else if (k == 1) {
                            //判断模块代码
                            if (val == null || val.equals("")) {
                                result = "第" + (j + 1) + "行第" + (k + 1) + "列模块代码不能为空！";
                                flag = false;//退出循环
                                break;
                            } else {
                                if (val.length() > 150) {
                                    result = "第" + (j + 1) + "行第" + (k + 1) + "列模块代码长度不能大于150！";
                                    flag = false;//退出循环
                                    break;
                                }
                            }
                        } else if (k == 2) {
                            //判断模块链接
                            if (val != null && !"".equals(val)){
                            }else{
                            	result ="第" + (j + 1) + "行第" + (k + 1) + "列性别不能为空！";
                            	flag = false;
                            	break;
                            }
                        }else if (k == 3) {
                            //判断模块排序
                            if (val == null || val.equals("")) {
                                result = "第" + (j + 1) + "行第" + (k + 1) + "列模块排序不能为空！";
                                flag = false;//退出循环
                                break;
                            }
                        }
                    }
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
	private String startImportModule(String excel) throws Exception {
        String check = checkExcel(excel);
        if (!check.equals("")) {
            return check;
        } else {
            List<ModuleInfoMobile> moduleList=getModuleList(excel);
            if(moduleList!=null && moduleList.size()>0)
            {
                for(ModuleInfoMobile moduleInfoMobile:moduleList)
                {
	                  //开始导入手机模块
	            	  try{
		                   // 判断是否重复
		            	   ModuleInfoMobile  oldModuleInfoMobile = moduleInfoMobileService.getByCode(moduleInfoMobile.getCode());
		                   if (null != oldModuleInfoMobile){
		                	   oldModuleInfoMobile.setName(moduleInfoMobile.getName());
		                	   oldModuleInfoMobile.setOrderList(moduleInfoMobile.getOrderList());
		                	   oldModuleInfoMobile.setUrl(moduleInfoMobile.getUrl());
		                	   moduleInfoMobileService.update(oldModuleInfoMobile);
		                	   successNum++;
		                   }else{
		                	   moduleInfoMobileService.save(moduleInfoMobile);
		                   }
	            	  }catch (Exception e) {
	            		  errorModuleList.add(moduleInfoMobile);
	            	  }
                }
            }
        }
        return "";
    }
    /**
     * 由文件生成用户列表
     * @param excel
     * @return
     */
    private List<ModuleInfoMobile> getModuleList(String excel)
    {
        List<ModuleInfoMobile> list =new ArrayList<ModuleInfoMobile>();
        Workbook wb = null;
        try
        {
            InputStream is = new FileInputStream(excel);
            wb = Workbook.getWorkbook(is);
            Sheet sheet = wb.getSheet("手机模块");    //获取工作Excel
            boolean flag = true;
            //第一行不判断
            for (int j = 1; j < sheet.getRows(); j++) {
                Cell[] cells = sheet.getRow(j);    //读取一行
                if (cells != null && cells.length > 0) {
                	ModuleInfoMobile moduleInfoMobile = new ModuleInfoMobile();
                	moduleInfoMobile.setName(cells[0].getContents());
                	moduleInfoMobile.setCode(cells[1].getContents());
                	moduleInfoMobile.setUrl(cells[2]==null?"":cells[2].getContents());
                	moduleInfoMobile.setOrderList(cells[3].getContents());
                	list.add(moduleInfoMobile);
                }
                if (!flag) {
                    break;
                }
            }
        }
        catch (Exception ex){
            ex.printStackTrace();
            LOGGER.error(ex);
        }
        finally {
            if(wb!=null){
                wb.close();
            }
        }
        return list;
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
