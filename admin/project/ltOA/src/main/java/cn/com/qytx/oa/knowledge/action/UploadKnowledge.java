package cn.com.qytx.oa.knowledge.action;

import java.io.File;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import cn.com.qytx.oa.knowledge.domain.KnowledgeType;
import cn.com.qytx.oa.knowledge.service.IKnowledge;
import cn.com.qytx.oa.knowledge.service.IKnowledgeType;
import cn.com.qytx.oa.util.GetMethodUtil;
import cn.com.qytx.oa.util.PropertiesUtil;
import cn.com.qytx.oa.util.ZipDecompression;
import cn.com.qytx.platform.base.action.BaseActionSupport;
import cn.com.qytx.platform.org.domain.UserInfo;

/**
 * 功能:上传知识库.html以及.zip格式
 * 版本: 1.0
 * 开发人员: 张东领
 * 创建日期: 2014-12-11
 * 修改日期: 2014-12-11
 * 修改列表:
 */
public class UploadKnowledge extends BaseActionSupport
{
    /**
     * 描述含义
     */
    private static final long serialVersionUID = 2185359441425975880L;
    /**
     * log4j日志对象
     */
    protected static final Logger logger = Logger.getLogger(KnowledgeAction.class.getName());
	
    /**
     * 上传文件
     */
    private File fileupload;
    /**
     * 上传来文件的文件名
     */
    private String uploadFileName;//
    /**
     * 上传的文件保存的路径
     */
    private String file;
    /**
     * 选中知识库类型ID
     */
    private Integer typeId;
    /**
     * 上传文件路径名称
     */
    private String fileNames;
    /**
     * 导入成功数
     */
    private Integer saveSuccess = 0;
    /**
     * 导入格式失败数
     */
    private Integer saveFailWithType = 0;
    /***
     * 已存在导入失败数
     */
    private Integer saveFailWithExist = 0;
    /**
     * 文件名称长度超过100导入失败数
     */
    private Integer saveFailLength = 0;
    @SuppressWarnings("unused")
    private static final int BUFFER = 2048;
    /**
     * 知识库逻辑接口
     */
    @Resource
    private transient IKnowledge knowledgeService;
    /**
     * 知识库类型逻辑接口
     */
    @Resource
    private transient IKnowledgeType knowledgeTService;

    /**
     * 功能：上传文件
     * @return
     */
    public void uploadKnowledge()
    {
        PrintWriter writer = null;
        String path = PropertiesUtil.get("knowledgeUpload", "");
        File checkPath = new File(path);
        if (!checkPath.exists())
        {
            try
            {
                // 目录不存在，则创建目录
                checkPath.mkdirs();
            }
            catch (Exception e)
            {
                logger.error("创建目录异常",e);
               // LOGGER.error("方法"+GetMethodUtil.getExectionMethod(), e);
            }
        }
        try
        {
            HttpServletResponse response = this.getResponse();
            response.setHeader("ContentType", "text/json");
            response.setCharacterEncoding("utf-8");
            response.setContentType("text/html;charset=utf-8");
            writer = this.getResponse().getWriter();
            if (fileupload != null)
            {
                String fileName = UUID.randomUUID().toString();
                String ext = "";
                if (uploadFileName != null)
                {
                    ext = uploadFileName.substring(uploadFileName.lastIndexOf('.'));
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
                        LOGGER.error("方法"+GetMethodUtil.getExectionMethod(), e);
                    }
                }
                FileUtils.copyFile(fileupload, savefile);// 拷贝文件
                file = path + "\\" + saveFileName;
                this.getSession().setAttribute(file, uploadFileName);
                writer.print(file);
                writer.flush();
            }

        }
        catch (Exception e)
        {
            logger.error("上传文件异常异常",e);
            //LOGGER.error("方法"+GetMethodUtil.getExectionMethod(), e);
        }
        finally
        {
            if (writer != null)
            {
                writer.close();
            }
        }
    }

    /**
     * 功能：解压缩文件
     * @param fileName:上传的压缩文件
     * @param unZipDir:解压到的路径
     */
    public void unZip(String fileName, String unZipDir, String encoding)
    {
        UserInfo userInfo = this.getLoginUser();
        try
        {
            ZipDecompression.unZip(fileName, unZipDir, "gb2312");
            if (StringUtils.isNotBlank(unZipDir))
            {
                if (typeId != null && typeId == 0)
                {
                    KnowledgeType kt = knowledgeTService.getNativeKnowledgeType(userInfo.getIsForkGroup());
                    if (kt != null)
                    {
                        typeId = kt.getVid();
                    }
                }
                File file = new File(unZipDir);
                openDirectory(file, typeId);
            }
        }
        catch (Exception e)
        {
            logger.error("解压文件异常",e);
//            LOGGER.error("方法"+GetMethodUtil.getExectionMethod(), e);
        }
    }

    /**
     * 功能：打开文件夹
     * @param fileDirectory:解压后的文件夹
     */
    public void openDirectory(File unFile, Integer parentId)
    {

        File[] files = unFile.listFiles();
        // KnowledgeType kt = createKt(unFile.getName(), parentId);
        if (files.length > 0)
        {
            for (int i = 0; i < files.length; i++)
            {
                File tempFile = files[i];
                if (tempFile.isDirectory())
                {
                    if (tempFile.getName().lastIndexOf(".files") >= 0
                            && tempFile.getName().lastIndexOf(".files") == (tempFile.getName().length() - 6))
                    {// 如果是htm文件的附件，则跳过
                        continue;
                    }
                    KnowledgeType ktTemp = knowledgeTService.findOrCreateType(getLoginUser(), tempFile.getName(),
                            parentId);
                    openDirectory(tempFile, ktTemp.getVid());
                }
                else
                {
                    parseFileToKnowledge(tempFile.getPath(), parentId, true);
                }
            }
        }
    }

    /**
     * 功能：文件保存到数据库中(导入)
     * @return
     */
    public String saveUploadKnowledge()
    {
        UserInfo userInfo = getLoginUser();
        try
        {
            if (typeId != null && typeId == 0)
            {
                ajax("notExist");// 知识库类型不存在
                return null;
            }
            KnowledgeType knowledgeType = knowledgeTService.findById(typeId);
            if (knowledgeType == null)
            {
                ajax("notExist");// 知识库类型不存在
                return null;
            }
            else
            {
                if (knowledgeType.getIsForkGroup().intValue() != userInfo.getIsForkGroup().intValue())
                {
                    ajax("noPerm");// 您的权限不够
                    return null;
                }
            }

            typeId = knowledgeType.getVid();

            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmSS");
            if (fileNames != null)
            {
                String[] fileNameArr = fileNames.split(",");
                String knowledgefile = "";
                if (fileNameArr.length == 1)
                {
                    knowledgefile = fileNameArr[0];
                    String ext = knowledgefile.substring(knowledgefile.lastIndexOf('.'));
                    // 如果上传的文件是压缩包，解压
                    if (ext.equals(".zip"))
                    {
                        String path = knowledgefile.substring(0, knowledgefile.lastIndexOf('\\'));
                        path = path + "\\" + sdf.format(new Date());
                        unZip(knowledgefile, path, "UTF-8");
                    }
                    else
                    {
                        // 保存到数据库中
                        parseFileToKnowledge(knowledgefile, typeId, false);
                    }
                }
                else if (fileNameArr.length > 1)
                {
                    for (int i = 0; i < fileNameArr.length; i++)
                    {
                        knowledgefile = fileNameArr[i];
                        // 保存到数据库中
                        parseFileToKnowledge(knowledgefile, typeId, false);
                    }
                }
            }
            ajax(saveSuccess + "," + saveFailWithType + "," + saveFailWithExist + "," + saveFailLength);
        }
        catch (Exception e)
        {
            ajax("error");
            logger.error("保存知识库异常",e);
//            LOGGER.error("方法"+GetMethodUtil.getExectionMethod(), e);
        }
        return null;
    }

    /**
     * 解析解压过后的htm,html与txt文件，并存入知识库
     */
    private void parseFileToKnowledge(String knowledgefile, Integer knowledgeGroup, boolean isZipUpload)
    {
        UserInfo userInfo = this.getLoginUser();
        File file = new File(knowledgefile);
        try
        {
            if (file.exists() && file.isFile())
            {
                String title = "";
                Integer lastPoint;
                if (isZipUpload)
                {
                    lastPoint = knowledgefile.lastIndexOf('.');
                    if (lastPoint != -1)
                    {
                        title = knowledgefile.substring(knowledgefile.lastIndexOf(File.separator) + 1, lastPoint);
                    }
                    else
                    {
                        saveFailWithType++;
                        return;
                    }
                }
                else
                {
                    String tempFile = (String) this.getSession().getAttribute(knowledgefile);
                    lastPoint = tempFile.lastIndexOf('.');
                    if (lastPoint != -1)
                    {
                        title = tempFile.substring(tempFile.lastIndexOf('\\') + 1, lastPoint);
                    }
                    else
                    {
                        saveFailWithType++;
                        return;
                    }
                }
                StringBuilder content = ZipDecompression.getKnowledgeContent(knowledgefile, file);
                if (!"errorType".equals(content.toString()))
                {
                    int res = knowledgeService.saveKnowledge(title, content.toString(), knowledgeGroup, userInfo);
                    if (res == 1)
                    {
                        saveFailWithExist++;
                    }
                    else
                    {
                        saveSuccess++;
                        // 保存成功后删除原文件
                        if (file.isFile() && file.exists())
                        {
                            boolean flag = file.delete();
                            if (!flag)
                            {
                                logger.error(file.getPath() + "删除失败！");
                            }
                        }
                    }
                }
                else//文件格式不正确
                {saveFailWithType++;
                    return;
                }
            }
        }
        catch (Exception e)
        {
            logger.error("扫描解析异常",e);
//            LOGGER.error("方法"+GetMethodUtil.getExectionMethod(), e);
        }
    }

    public File getFileupload()
    {
        return fileupload;
    }

    public void setFileupload(File fileupload)
    {
        this.fileupload = fileupload;
    }

    public String getFileuploadFileName()
    {
        return uploadFileName;
    }

    public void setFileuploadFileName(String uploadFile)
    {
        this.uploadFileName = uploadFile;
    }

    public String getFile()
    {
        return file;
    }

    public void setFile(String file)
    {
        this.file = file;
    }

    public Integer getTypeId()
    {
        return typeId;
    }

    public void setTypeId(Integer typeId)
    {
        this.typeId = typeId;
    }

    public String getFileNames()
    {
        return fileNames;
    }

    public void setFileNames(String fileNames)
    {
        this.fileNames = fileNames;
    }

}
