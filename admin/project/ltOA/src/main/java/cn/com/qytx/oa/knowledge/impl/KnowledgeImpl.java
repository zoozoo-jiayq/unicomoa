package cn.com.qytx.oa.knowledge.impl;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.qytx.monitor.client.log.MonitorLogger;
import cn.com.qytx.monitor.client.logImpl.Log4jImpl;
import cn.com.qytx.oa.knowledge.dao.KnowledgeCollectDao;
import cn.com.qytx.oa.knowledge.dao.KnowledgeDao;
import cn.com.qytx.oa.knowledge.dao.KnowledgeTypeDao;
import cn.com.qytx.oa.knowledge.domain.Knowledge;
import cn.com.qytx.oa.knowledge.domain.KnowledgeCollect;
import cn.com.qytx.oa.knowledge.domain.KnowledgeType;
import cn.com.qytx.oa.knowledge.service.IKnowledge;
import cn.com.qytx.oa.util.StringUtil;
import cn.com.qytx.oa.util.TimeUtil;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.PageRequest;
import cn.com.qytx.platform.base.query.Pageable;
import cn.com.qytx.platform.base.service.impl.BaseServiceImpl;
import cn.com.qytx.platform.org.domain.UserInfo;

/**
 * 功能: 知识库service实现类
 * 版本: 1.0
 * 开发人员: 李华伟
 * 创建日期: 2013-11-7
 * 修改日期: 2013-11-7
 * 修改列表:
 */
@Service
@Transactional
public class KnowledgeImpl extends BaseServiceImpl<Knowledge> implements IKnowledge, Serializable
{
    protected static final Logger logger = Logger.getLogger(KnowledgeImpl.class.getName());
	
    /**
     * 序列号
     */
    private static final long serialVersionUID = 4691577512764236972L;
    /**
     * 知识库dao
     */
    @Autowired
    private KnowledgeDao knowledgeDao;
    /**
     * 知识库收藏dao
     */
    @Resource
    private KnowledgeCollectDao knowledgeCollectDao;
    /**
     * 知识库类型dao
     */
    @Autowired
    @Transient
    private KnowledgeTypeDao knowledgeTypeDao;

    /**
     * 功能：分页查询知识库列表页面
     * @param page page
     * @param knowledge knowledge查询条件
     * @return 分页数据
     */
    public Page<Knowledge> findPageByVo(Pageable page, UserInfo userInfo, Knowledge knowledge)
    {
        Page<Knowledge> result = knowledgeDao.findPageByVo(page, userInfo, knowledge);
        List<Knowledge> knowledgeList = result.getContent();
        if (null != knowledgeList && !knowledgeList.isEmpty())
        {
            for (Knowledge tempKnowledge : knowledgeList)
            {
                // 初始化分类路径
                String pathName = knowledgeTypeDao.getKnowledgeTypeNameById(tempKnowledge.getKnowledgeType().getVid());
                tempKnowledge.setPathName(pathName);
            }
        }

        return result;
    }

    /**
     * 功能：知识库创建索引
     * @param id 知识库id
     * @param isForkGroup 权限
     * @param creatUserId 创建人id
     * @param typeId 知识库类型
     * @param startTime开始时间
     * @param endTime 结束时间
     * @return
     */
    @Deprecated
    public String initKnowledgeIndex(Integer id, Integer isForkGroup, Integer creatUserId, Integer typeId,
            String startTime, String endTime)
    {
        Timestamp start = null;
        Timestamp end = null;
        String timeFormat = "yyyy-MM-dd HH:mm:ss";
        if (startTime != null && !"".equals(startTime))
        {
            start = TimeUtil.timeStr2stamp(startTime, timeFormat);
        }
        if (endTime != null && !"".equals(endTime))
        {
            end = TimeUtil.timeStr2stamp(endTime, timeFormat);
        }

        if (id != null && id > 0)
        {
            Knowledge kl = this.findById(id);
//            LuceneUtil.delete(kl);
//            LuceneUtil.createIndex(kl);
        }
        else
        {
            byIdIsNull(isForkGroup, creatUserId, typeId, start, end);
        }

        return "生成索引成功";
    }

    /**
     * 功能：用于initKnowledgeIndex方法中id等于null的情况
     * @param isForkGroup 权限
     * @param creatUserId 创建人id
     * @param typeId 知识库类型
     * @param startTime开始时间
     * @param endTime 结束时间
     */
    @Deprecated
    public void byIdIsNull(Integer isForkGroup, Integer creatUserId, Integer typeId, Timestamp start, Timestamp end)
    {

        int countPage = 1;// 总页数
        int currentPage = 0;// 当前页
        if (isForkGroup == null || isForkGroup == 0)
        {// 生成全部索引
            List<Object> listFor = knowledgeTypeDao.findallIsFork();

            for (Object obj : listFor)
            {// 创建所有公共的知识库索引
                countPage = 1;// 总页数
                currentPage = 0;// 当前页
                Pageable page = null;
                Page<Knowledge> result = null;
                while (currentPage < countPage)
                {
                    page = new PageRequest(currentPage, 100);
                    result = knowledgeDao.findKnowledge(page, Integer.valueOf(obj.toString()), creatUserId, typeId, 0,
                            start, end);
                    countPage = result.getTotalPages();
                    if (result.getTotalElements() > 0)
                    {
//                        LuceneUtil.createIndexByFork(result.getContent());// 把查询的结果生成索引
                    }

                    currentPage = currentPage + 1;
                }
            }
        }
        else
        {// 生成指定市的索引
            countPage = 1;// 总页数
            currentPage = 0;// 当前页
            Pageable page = null;
            Page<Knowledge> result = null;
            while (currentPage < countPage)
            {
                page = new PageRequest(currentPage, 100);
                result = knowledgeDao.findKnowledge(page, isForkGroup, creatUserId, typeId, 0, start, end);
                countPage = result.getTotalPages();
                if (result.getTotalElements() > 0)
                {
//                    LuceneUtil.createIndexByFork(result.getContent());// 把查询的结果生成索引
                }
                currentPage = currentPage + 1;
            }
        }
        // 生成私人索引
        countPage = 1;// 总页数
        currentPage = 0;// 当前页
        Pageable page = null;
        Page<Knowledge> result = null;
        while (currentPage < countPage)
        {
            page = new PageRequest(currentPage, 100);
            result = knowledgeDao.findKnowledge(page, null, creatUserId, typeId, -1, start, end);
            countPage = result.getTotalPages();
            if (result.getTotalElements() > 0)
            {
//                LuceneUtil.createIndex(result.getContent());// 把查询的结果生成索引
            }

            currentPage = currentPage + 1;
        }

    }

    /**
     * 功能：更新个人收藏知识库索引
     * @param creatUserId 创建人id
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return
     */
    @Deprecated
    public String initColleKnowledgeIndex(Integer creatUserId, String startTime, String endTime)
    {
        Timestamp start = null;
        Timestamp end = null;
        if (startTime != null && !"".equals(startTime))
        {
            start = TimeUtil.timeStr2stamp(startTime, "yyyy-MM-dd HH:mm:ss");
        }
        if (endTime != null && !"".equals(endTime))
        {
            end = TimeUtil.timeStr2stamp(endTime, "yyyy-MM-dd HH:mm:ss");
        }
        int countPage = 1;// 总页数
        int currentPage = 0;// 当前页
        Pageable page = null;
        Page<KnowledgeCollect> result = null;
        // LuceneUtil.deleteAllIndex(2, 0, creatUserId);
        while (currentPage < countPage)
        {
            page = new PageRequest(currentPage, 100);
            result = knowledgeCollectDao.findKnowledgeColl(page, creatUserId, start, end);
            countPage = result.getTotalPages();
            List<KnowledgeCollect> kcs = result.getContent();
            for (KnowledgeCollect kc : kcs)
            {
//                LuceneUtil.addCollectIndex(kc.getKnowledge(), kc.getSeat().getUserId());// 把查询的结果生成索引
            }
            currentPage = currentPage + 1;
        }
        return "生成索引成功";
    }

    /**
     * 功能：删除知识库索引
     * @param id 知识库id
     * @param type 0 所有的 1私人的 2 收藏的 3 公共的
     * @param isFork forkid
     * @param cuId 用户id
     * @return
     */
    @Deprecated
    public String deleteKnowledgeIndex(Integer id, Integer type, Integer isFork, Integer cuId)
    {
        if (id != null && id > 0)
        {
//            LuceneUtil.delete(this.findById(id));
        }
        else
        {
//            LuceneUtil.deleteAllIndex(type, isFork, cuId);
        }
        return "删除成功";

    }

    /**
     * 功能：根据Id删除知识库信息
     * @param id 主键
     */
    @Override
    @Deprecated
    public void deleteById(Integer id)
    {
        knowledgeDao.deleteById(id);
        UserInfo  user=(UserInfo) ServletActionContext.getRequest().getSession().getAttribute("adminUser");
        logger.info(user.getUserName()+"删除知识库 id="+id);
//        LuceneUtil.delete(this.findById(id));
    }

    /**
     * 功能：保存知识库信息
     * @param knowledge 知识库
     * @return 0表示成功 1表示标题重复
     */
    @Override
    @Deprecated
    public int saveKnowledge(Knowledge knowledge)
    {
        // 判断标题是否重复
        Knowledge oldKnowledge = knowledgeDao.findKnowledgeByParam(knowledge.getTitle(), knowledge.getKnowledgeType()
                .getVid());
        if (null != oldKnowledge)
        {
            return 1;
        }
        knowledge.setIsDelete(0);
        knowledge.setHasRecordAudio(0);
        knowledge.setContentVoice(StringUtil.delHTMLTag(knowledge.getContentInfo()));
        knowledgeDao.saveOrUpdate(knowledge);
        logger.info("成功添加一条知识"+knowledge);
//        LuceneUtil.createIndex(knowledge);
        return 0;
    }

    /**
     * 功能：保存更新知识库信息
     * @param knowledge 知识库
     * @return 0表示成功 1表示标题重复
     */
    @Override
    @Deprecated
    public int saveOrUpdateKnowledge(Knowledge knowledge)
    {
        // 新增
        if (null == knowledge.getVid())
        {
            return saveKnowledge(knowledge);
        }
        else
        {
            knowledge.setHasRecordAudio(0);
            knowledge.setContentVoice(StringUtil.delHTMLTag(knowledge.getContentInfo()));
          
            knowledgeDao.saveOrUpdate(knowledge);
            logger.info("成功修改一条知识"+knowledge);
//            LuceneUtil.updateIndex(knowledge);
        }

        // 修改 审核通过,只修改状态
        // Knowledge oldKnowledge = knowledgeDao.findOne(knowledge.getVid());
        // oldKnowledge.setAuditUserInfo(knowledge.getAuditUserInfo());
        // oldKnowledge.setAuditTime(knowledge.getAuditTime());
        // oldKnowledge.setAuditSign(knowledge.getAuditSign());

        return 0;
    }

    /**
     * 功能：根据Id获取知识库信息
     * @param knowledgeId knowledgeId
     * @return Knowledge
     */
    @Override
    public Knowledge findById(Integer knowledgeId)
    {
        return knowledgeDao.findOne(knowledgeId);
    }

    /**
     * 功能：后台首页 知识库 5条
     * @return List<Notice>
     */
    public List<Object> findLaster5Notice()
    {
        return knowledgeDao.findLaster5Notice();
    }

    /**
     * 功能：通过索引查询知识库
     * @param page
     * @param option 坐席
     * @param queryStr 查询条件
     * @param kType 0公共知识库 1 个人知识库 2 我的收藏
     * @return
     */
    @Deprecated
    public Page<Knowledge> findKonwledgeByLucene(Pageable page, UserInfo option, String queryStr, int kType)
    {

        if (option == null)
        {
            return null;
        }
        if (queryStr == null)
        {
            queryStr = "";
        }

        List<String> fileName = new LinkedList<String>();
        if (kType == 0)
        {// 查询公共知识库

            if (option.getIsForkGroup() != 2)
            {
                fileName.add("knowledge" + option.getIsForkGroup());
            }
            fileName.add("knowledge2");
        }
        else if (kType == 1)
        {// 查询个人知识库
            fileName.add("myKnowledge//file" + option.getUserId());
        }
        else if (kType == 2)
        {// 查询我的收藏
            fileName.add("collectKnowledge//file" + option.getUserId());
        }
        else
        {// 查询所有的知识库
            fileName.add("myKnowledge//file" + option.getUserId());
            if (option.getIsForkGroup() != 2)
            {
                fileName.add("knowledge" + option.getIsForkGroup());
            }
            fileName.add("knowledge2");
        }

//        Page<Knowledge> kList = LuceneUtil.searchBook(fileName, queryStr, page);
        return null;

    }

    /**
     * 功能：以详情的样式，分页查询知识库
     * @param page
     * @param knowledge
     * @return
     */
    @Override
    public Page<Knowledge> findByPageDetail(Pageable page, Knowledge knowledge)
    {
        Page<Knowledge> pageInfo = knowledgeDao.findByPageDetail(page, knowledge);
        List<Knowledge> kList = pageInfo.getContent();
        if (kList != null)
        {
            for (Knowledge k : kList)
            {
                // 标题
                String title = StringUtils.defaultString(k.getTitle(), "");
                if (StringUtils.isNotBlank(title) && title.length() > 30)
                {
                    title = title.substring(0, 30) + "...";
                }
                // 内容
                String contentInfo = StringUtils.defaultString(k.getContentInfo(), "");
                if (StringUtils.isNotBlank(contentInfo) && contentInfo.length() > 500)
                {
                    contentInfo = contentInfo.substring(0, 500) + "...";
                }
                // 录入人
                String userName = StringUtils.defaultString(k.getCreateUserInfo().getUserName(), "");
                // 录入时间
                Timestamp createTime = k.getCreateTime();
                String createTimeStr = "";
                if (createTime != null)
                {
                    createTimeStr = TimeUtil.timestamp2Str(createTime, "yyyy-MM-dd HH:mm");
                }
                String htmlStr = "";
                htmlStr += "<div class='dailyDetailBox'>";
                // 更具关键字标红字体
                String search = knowledge.getSearch();
                if (StringUtils.isNotBlank(search))
                {
                    String[] keyArr = search.split(" ");
                    if (keyArr.length > 1)
                    {
                        for (int i = 0; i < keyArr.length; i++)
                        {
                            title = title.replace(keyArr[i], "< em >" + keyArr[i] + "</em >");
                            contentInfo = contentInfo.replace(keyArr[i], "<em>" + keyArr[i] + "</em>");
                        }
                    }
                    else
                    {
                        title = title.replace(keyArr[0], "<em>" + keyArr[0] + "</em>");
                        contentInfo = contentInfo.replace(keyArr[0], "<em>" + keyArr[0] + "</em>");
                    }
                    htmlStr += "<div class='big_title'><a href='javascript:toDetail(" + k.getVid() + ");' >" + title
                            + "</a></div>";
                    htmlStr += "<div class='dailyDetail'>";
                    htmlStr += "<p>" + contentInfo + "</p>";
                    htmlStr += "<p class='dailyTime'><span>录入人：" + userName + "</span><span class='time'>录入时间："
                            + createTimeStr + "</span></p>";
                    htmlStr += "</div>";
                }
                else
                {
                    htmlStr += "<div class='big_title'><a href='javascript:toDetail(" + k.getVid() + ");' >" + title
                            + "</a></div>";
                    htmlStr += "<div class='dailyDetail'>";
                    htmlStr += "<p>" + contentInfo + "</p>";
                    htmlStr += "<p class='dailyTime'><span>录入人：" + userName + "</span><span class='time'>录入时间："
                            + createTimeStr + "</span></p>";
                    htmlStr += "</div>";
                }
                htmlStr += "</div>";
                k.setKdDetail(htmlStr);
            }
        }
        return knowledgeDao.findByPageDetail(page, knowledge);
    }

    /**
     * 功能：保存知识库
     * @param title 标题
     * @param contentStr 内容
     * @param knowledgeGroup 组
     * @param userInfo 创建人
     * @return 1 失败 0 成功
     */
    public int saveKnowledge(String title, String contentStr, Integer knowledgeGroup, UserInfo userInfo)
    {
        int isSuccess = 1;

        // 限制上传文件的名称长度不超过100个字符
        if (title.length() <= 100)
        {
            Knowledge knowledge = new Knowledge();
            knowledge.setTitle(title);
            knowledge.setContentInfo(contentStr);
            knowledge.setKeyword(title);
            KnowledgeType ktype = knowledgeTypeDao.findOne(knowledgeGroup);
            knowledge.setKnowledgeType(ktype);
            knowledge.setCreateTime(new Timestamp(System.currentTimeMillis()));
            knowledge.setAuditTime(new Timestamp(System.currentTimeMillis()));
            knowledge.setCreateUserInfo(userInfo);
            knowledge.setAuditUserInfo(userInfo);
            if (ktype != null)
            {
                knowledge.setIsForkGroup(ktype.getIsForkGroup());
            }
            else
            {
                knowledge.setIsForkGroup(userInfo.getIsForkGroup());
            }
            knowledge.setIsPrivate(0);
            knowledge.setAuditSign(2);
            knowledge.setIsDelete(0);
            knowledge.setCompanyId(userInfo.getCompanyId());
            isSuccess = saveKnowledge(knowledge);
            
            /*
             * if(isdelete&&file.isFile()&&file.exists()){
             * boolean flag=file.delete();
             * if(!flag){
             * logger.error(file.getPath()+"删除失败！");
             * }
             * }
             */
        }
        return isSuccess;
    }
    
    /**
     * 功能：分页查询知识库列表页面
     * @param page page
     * @return 分页数据
     */
    public Page<Knowledge> findPage(Pageable page, UserInfo userInfo,Integer typeId,Integer moduleId){
    	return knowledgeDao.findPage(page, userInfo, typeId, moduleId);
    }
}
