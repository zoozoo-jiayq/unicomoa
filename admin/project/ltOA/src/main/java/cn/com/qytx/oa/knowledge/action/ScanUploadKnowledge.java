package cn.com.qytx.oa.knowledge.action;

import java.io.File;
import java.sql.Timestamp;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import cn.com.qytx.monitor.client.log.MonitorLogger;
import cn.com.qytx.monitor.client.logImpl.Log4jImpl;
import cn.com.qytx.oa.knowledge.domain.KnowledgeType;
import cn.com.qytx.oa.knowledge.service.IKnowledge;
import cn.com.qytx.oa.knowledge.service.IKnowledgeType;
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
public class ScanUploadKnowledge extends BaseActionSupport
{
    /**
     * log4j日志对象
     */
    protected static final Logger logger = Logger.getLogger(ScanUploadKnowledge.class.getName());
	
    private static final long serialVersionUID = 7904041551298696632L;
    /**
     * 上传文件路径名称
     */
    private String fileNames;
    /**
     * 操作用户
     */
    private UserInfo tempUser;

    /**
     * 知识库逻辑接口
     */
    @Resource
    private transient IKnowledge knowledgeService;
    /**
     * 知识库类型逻辑接口
     */
    @Resource
    private transient IKnowledgeType knowledgeTypeService;

    /**
     * 功能：保存知识库并创建知识库索引
     * @return
     */
    // knowledge/initIndexByDirect.action?fileNames=E:\lucene\index
    public String initIndex()
    {

        UserInfo userInfo = getLoginUser();
        KnowledgeType knowTop = knowledgeTypeService.getNativeKnowledgeType(userInfo
                .getIsForkGroup());
        // 扫描知识库的位置
        if (fileNames == null)
        {
            fileNames = PropertiesUtil.get("lunceneInitFile", "");
        }
        if (fileNames != null && !"".equals(fileNames))
        {
            File file = new File(fileNames);
            File[] files = file.listFiles();
            if (files.length > 0)
            {
                for (int i = 0; i < files.length; i++)
                {
                    File tempFile = files[i];
                    if (tempFile.isDirectory())
                    {
                        // 获取顶级类型
                        KnowledgeType ktTemp = knowledgeTypeService.findByName(tempFile.getName(),
                                0);
                        if (ktTemp == null)
                        {
                            int isonly = Integer.valueOf(PropertiesUtil.get("isOnly", "0"));
                            if (isonly == 1)
                            {
                                ktTemp = new KnowledgeType();
                                ktTemp.setName(tempFile.getName());
                                ktTemp.setParentId(2);// 顶级部门父id为2
                                ktTemp.setOrderIndex(999);
                                ktTemp.setCreateUserInfo(userInfo);
                                ktTemp.setIsForkGroup(userInfo.getIsForkGroup());
                                ktTemp.setIsPrivate(0);
                                ktTemp.setCreateTime(new Timestamp(System.currentTimeMillis()));
                                ktTemp.setIsDelete(0);
                                ktTemp.setCompanyId(userInfo.getCompanyId());
                                knowledgeTypeService.saveOrUpdateKnowledgeType(ktTemp);
                                tempUser = ktTemp.getCreateUserInfo();
                                openDirectory(tempFile, ktTemp.getVid());
                            }

                        }
                        else
                        {
                            tempUser = ktTemp.getCreateUserInfo();
                            openDirectory(tempFile, ktTemp.getVid());
                        }

                    }
                    else
                    {
                        // 如果解压的文件直接在加跟目录下 则添加到用户权限的根目录下
                        parseFileToKnowledge(tempFile.getPath(), knowTop.getVid(), true);
                    }
                }
            }
        }
        ajax("扫描完成");
        return null;
    }

    /**
     * 功能：打开文件夹
     * @param fileDirectory:解压后的文件夹
     */
    public void openDirectory(File unFile, Integer parentId)
    {
        File[] files = unFile.listFiles();
        if (files.length > 0)
        {
            for (int i = 0; i < files.length; i++)
            {
                File tempFile = files[i];
                if (tempFile.isDirectory())
                {
                    if (tempFile.getName().lastIndexOf(".files") >= 0
                            && tempFile.getName().lastIndexOf(".files") == (tempFile.getName()
                                    .length() - 6))
                    {// 如果是htm文件的附件，则跳过
                        continue;
                    }
                    KnowledgeType ktTemp = knowledgeTypeService.findOrCreateType(tempUser,
                            tempFile.getName(), parentId);
                    // createKt(tempFile.getName(), parentId);
                    openDirectory(tempFile, ktTemp.getVid());
                }
                else
                {
                    // 如果解压的文件直接在加跟目录下 则添加到用户权限的根目录下
                    parseFileToKnowledge(tempFile.getPath(), parentId, true);
                }
            }
        }
    }

    /**
     * 解析解压过后的htm,html与txt文件，并存入知识库
     */
    private void parseFileToKnowledge(String knowledgefile, Integer knowledgeGroup,
            boolean isZipUpload)
    {
        File file = new File(knowledgefile);
        try
        {
            if (file.exists() && file.isFile())
            {
                String title = "";
                if (isZipUpload)
                {
                    title = knowledgefile.substring(knowledgefile.lastIndexOf(File.separator) + 1,
                            knowledgefile.lastIndexOf('.'));
                }
                else
                {
                    String tempFile = (String) this.getSession().getAttribute(knowledgefile);
                    title = tempFile.substring(tempFile.lastIndexOf('\\') + 1,
                            tempFile.lastIndexOf('.'));
                }
                StringBuilder content = ZipDecompression.getKnowledgeContent(knowledgefile, file);
                if (!"errorType".equals(content.toString()) && !"".equals(content.toString()))
                {
                    knowledgeService.saveKnowledge(title, content.toString(), knowledgeGroup,
                            getLoginUser());
                    if (file.isFile() && file.exists())
                    {
                        boolean flag = file.delete();
                        if (!flag)
                        {

                            logger.info(file.getPath() + "删除失败！", null);
                        }
                    }
                }

            }
        }
        catch (Exception e)
        {
            logger.error("扫描解析异常", e);
            // LOGGER.error("方法"+GetMethodUtil.getExectionMethod(), e);
        }
    }

    public String getFileNames()
    {
        return fileNames;
    }

    public void setFileNames(String fileNames)
    {
        this.fileNames = fileNames;
    }

    public UserInfo getTempUser()
    {
        return tempUser;
    }

    public void setTempUser(UserInfo tempUser)
    {
        this.tempUser = tempUser;
    }

}
