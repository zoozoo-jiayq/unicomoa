/**
 * 
 */
package cn.com.qytx.oa.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.UUID;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;

import cn.com.qytx.monitor.client.log.MonitorLogger;
import cn.com.qytx.monitor.client.logImpl.Log4jImpl;
import cn.com.qytx.platform.base.action.BaseActionSupport;
import cn.com.qytx.platform.utils.datetime.DateTimeUtil;

/**
 * 功能:导入的baseaction
 * 版本: 1.0
 * 开发人员: 彭小东
 * 创建日期: 2015-2-6
 * 修改日期: 2015-2-6
 * 修改列表:
 */
public class ImportBaseAction extends BaseActionSupport
{
    /**
     * 描述含义
     */
    private static final long serialVersionUID = 1L;
    /**
     * log4j日志对象
     */
    private final static MonitorLogger logger =new Log4jImpl(ImportBaseAction.class);
    /**
     * 上传文件
     */
    protected File fileToUpload;
    /**
     * 上传文件名称
     */
    protected String uploadFileName;
    /**
     * 上传的文件
     */
    protected String file;
    /**
     * 成功个数
     */
    protected int successNum = 0;
    /**
     * 跳过条数
     */
    protected int skipNum = 0;
    /**
     * 错误文件名称以及路径
     */
    protected String errorFileNamePath = "";
    /**
     * 保存 错误的wwb流
     */
    protected transient WritableWorkbook errorWWB;
    /**
     * 保存错误记录的sheet流
     */
    protected transient WritableSheet errorws;
    /**
     * 当前错误记录的行位置
     */
    protected int errorRowsNum = 1;//
    /**
     * 保存错误记录的文件名 导出的文件名
     */
    protected String errorFileName;
    /**
     * 导入失败的记录数
     */
    protected int errorImportNum = 0;

    /**
     * 导入数据的输入流
     */
    protected InputStream stream;
    /**
     * 导入数据的表格
     */
    protected Workbook wb;
    /**
     * 导入数据的sheet
     */
    protected Sheet sheet;
    /**
     * 导入的总行数
     */
    protected int OldRows;
    /**
     * 导入的总列数
     */
    protected int OldCols;

    /**
     * 功能：关闭流
     */
    protected void colseAllStream()
    {
        if (wb != null)
        {
            wb.close();
        }
        if (stream != null)
        {
            try
            {
                stream.close();
            }
            catch (IOException e)
            {
            }
        }
        if (errorWWB != null)
        {
            try
            {
                errorWWB.close();

            }
            catch (Exception e)
            {
            	logger.error("colseAllStream error:"+e);
            }
        }

    }

    /**
     * 功能：初始化保存的错误记录的文件
     * @return
     */
    protected boolean initErrorFile(String fileName)
    {
        if (errorFileName == null || "".equals(errorFileName))
        {
            @SuppressWarnings("deprecation")
            String path = ServletActionContext.getRequest().getRealPath("/upload");
            errorFileName = fileName + "_" + DateTimeUtil.dateToString(new Date(), "yyyyMMddHHmmss") ;
            errorFileNamePath = path + File.separator + "errorImportExcel" + File.separator + errorFileName + ".xls";
            errorFileName = File.separator + "errorImportExcel" + File.separator + errorFileName + ".xls";
            File filePath = new File(path + File.separator + "errorImportExcel");
            if (!filePath.isDirectory())
            {
                try
                {
                    filePath.mkdirs();
                }
                catch (Exception e)
                {
                	logger.error("colseAllStream error:"+e);
                    return false;
                }
            }
            File file = new File(errorFileNamePath);
            if (!file.isFile())
            {
                try
                {
                    file.createNewFile();
                }
                catch (Exception e)
                {
                	logger.error("colseAllStream error:"+e);
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * 功能：导出的数据结果
     * @return
     */
    protected String getImportResult()
    {
        String result;
        if (OldRows > 1)
        {

            String path = this.getRequest().getContextPath();
            String basePath = getRequest().getScheme() + "://" + getRequest().getServerName() + ":"
                    + getRequest().getServerPort() + path + "/";

            if (errorImportNum > 0)
            {
            	if(skipNum>0){
            		result = "导入完毕！导入成功" + successNum + "条记录，跳过"+skipNum+"条记录，失败" + errorImportNum
            				+ "条记录<a href='" + basePath + "/upload/" + errorFileName
            				+ "' class='download_wrong' >下载失败记录</a>";
            	}else{
            		result = "导入完毕！导入成功" + successNum + "条记录，失败" + errorImportNum
            				+ "条记录<a href='" + basePath + "/upload/" + errorFileName
            				+ "' class='download_wrong' >下载失败记录</a>";
            	}
            }
            else
            {
            	if(skipNum>0){
            		result = "导入完毕！导入成功" + successNum + "条记录，跳过"+skipNum+"条记录，失败" + errorImportNum
                            + "条记录。";
            	}else{
            		result = "导入完毕！导入成功" + successNum + "条记录，失败" + errorImportNum
                            + "条记录。";
            	}
            }
        }
        else
        {
        	if(skipNum>0){
        		result = "导入完毕！导入成功" + successNum + "条记录，跳过"+skipNum+"条记录，失败" + errorImportNum
                        + "条记录。";
        	}else{
        		result = "导入完毕！导入成功" + successNum + "条记录，失败" + errorImportNum
                        + "条记录。";
        	}
        }
        return result;
    }

    /**
     * 上传文件
     */
    protected boolean uploadFile()
    {
        @SuppressWarnings("deprecation")
        String path = ServletActionContext.getRequest().getRealPath("/upload");
        File checkPath = new File(path);
        if (!checkPath.exists())
        {
            // 目录不存在，则创建目录
            try
            {
                checkPath.mkdirs();
            }
            catch (Exception e)
            {
            	logger.error("colseAllStream error:"+e);
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
                    try
                    {
                        savefile.getParentFile().mkdirs();
                    }
                    catch (Exception e)
                    {
                    	logger.error("colseAllStream error:"+e);
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
     * 功能：初始化sheet
     * @return
     */
    protected String initSheet()
    {
        try
        {
            stream = new FileInputStream(file);
            wb = Workbook.getWorkbook(stream);
        }
        catch (BiffException e)
        {
            return "读取导入数据的execle失败！";
        }
        catch (IOException e)
        {
            return "读取导入的文件错误！";
        }
        sheet = wb.getSheet(0);
        OldCols = sheet.getColumns();
        OldRows = sheet.getRows();
        if (wb == null)
        {
            return "要导入的文件不正确！";
        }
        if (OldRows == 1)
        {
            return "请填写要导入的内容！";
        }
        if (sheet == null)
        {
            return "要导入的文件不正确！";
        }
        return null;

    }

    /* get set */
    public File getFileToUpload()
    {
        return fileToUpload;
    }

    public void setFileToUpload(File fileToUpload)
    {
        this.fileToUpload = fileToUpload;
    }

    public String getUploadFileName()
    {
        return uploadFileName;
    }

    public void setUploadFileName(String uploadFileName)
    {
        this.uploadFileName = uploadFileName;
    }

    public String getFile()
    {
        return file;
    }

    public void setFile(String file)
    {
        this.file = file;
    }

    public int getSuccessNum()
    {
        return successNum;
    }

    public void setSuccessNum(int successNum)
    {
        this.successNum = successNum;
    }

	public int getSkipNum() {
		return skipNum;
	}

	public void setSkipNum(int skipNum) {
		this.skipNum = skipNum;
	}

}
