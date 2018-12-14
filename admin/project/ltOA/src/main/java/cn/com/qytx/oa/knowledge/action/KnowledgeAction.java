package cn.com.qytx.oa.knowledge.action;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import cn.com.qytx.cbb.file.domain.Attachment;
import cn.com.qytx.cbb.file.service.IAttachment;
import cn.com.qytx.monitor.client.log.MonitorLogger;
import cn.com.qytx.monitor.client.logImpl.Log4jImpl;
import cn.com.qytx.oa.knowledge.domain.Knowledge;
import cn.com.qytx.oa.knowledge.domain.KnowledgeCollect;
import cn.com.qytx.oa.knowledge.domain.KnowledgeType;
import cn.com.qytx.oa.knowledge.impl.KnowledgeImpl;
import cn.com.qytx.oa.knowledge.service.IKnowledge;
import cn.com.qytx.oa.knowledge.service.IKnowledgeCollect;
import cn.com.qytx.oa.knowledge.service.IKnowledgeType;
import cn.com.qytx.oa.util.TimeUtil;
import cn.com.qytx.platform.base.action.BaseActionSupport;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.PageImpl;
import cn.com.qytx.platform.base.query.Pageable;
import cn.com.qytx.platform.base.query.Sort;
import cn.com.qytx.platform.base.query.Sort.Direction;
import cn.com.qytx.platform.org.domain.UserInfo;

/**
 * 功能:知识库管理
 * 版本: 1.0
 * 开发人员: 彭小东
 * 创建日期: 2015-2-9
 * 修改日期: 2015-2-9
 * 修改列表:
 */
public class KnowledgeAction extends BaseActionSupport
{
    protected static final Logger logger = Logger.getLogger(KnowledgeAction.class.getName());
	
    /**
     * 序列号
     */
    private static final long serialVersionUID = -5088974549574219746L;
    /**
     * 标示
     */
    public String managerKnowStr = "managerKnowledge";
    /**
     * 知识库逻辑接口
     */
    @Autowired
    private transient IKnowledge knowledgeService;
    /**
     * 知识库类型逻辑接口
     */
    @Autowired
    private transient IKnowledgeType knowledgeTService;
    /**
     * 我的知识库收藏逻辑接口
     */
    @Autowired
    private transient IKnowledgeCollect knowledgeCImpl;
    
    @Autowired
    IAttachment attachmentService;
    
    /**
     * 知识库id
     */
    private Integer id;
    /**
     * 用户权限
     */
    private Integer isFork;
    /**
     * 创建人id
     */
    private Integer cuId;
    /**
     * 知识库类型id
     */
    private Integer typeId;
    /**
     * 查询的开始时间
     */
    private String startTime;
    /**
     * 查询的结束时间
     */
    private String endTime;
    /**
     * 知识库
     */
    private Knowledge knowledge;
    /**
     * 首页获取知识库列表参数
     */
    private Integer myParam;
    /**
     * 0:跳转到查看页面，1：跳转到编辑页面
     */
    private Integer isToView;

    /**
     * 0:后台 1：前台
     */
    private Integer isBeforeOrAfter;

    // private Integer beforeOrAfter;
    /**
     * isPrivate 0是公工的 1是个人的
     */
    private Integer isPrivate;
    /**
     * 0 所有的 1私人的 2 收藏的 3 公共的
     */
    private transient Integer delType;

    /**
     * 页面的发布时间时间格式显示
     * @return
     */
    private String time;

    /**
     * 查询条件
     */
    private String search;

    /**
     * 是否从坐席端首页跳转来的。（1是的，0或null不是）
     */
    private Integer fromseatwelcom;

    public Integer getFromseatwelcom()
    {
        return fromseatwelcom;
    }

    public void setFromseatwelcom(Integer fromseatwelcom)
    {
        this.fromseatwelcom = fromseatwelcom;
    }

    /**
     * /knowledge/initIndex.action?isFork=22&cuId=2&typeId=11&startTime=2014-12-10 00:00:01&endTime=2014-12-10 00:00:01
     * 功能：初始化索引
     * @return
     */
    public String initIndex()
    {
        
        final String result = knowledgeService.initKnowledgeIndex(id, isFork, cuId, typeId, startTime, endTime);
        knowledgeService.initColleKnowledgeIndex(cuId, startTime, endTime);
        logger.info("操作人"+getLoginUser().getUserName()+"初始化索引");
        ajax(result);
        return null;
    }

    /**
     * type 0 所有的 1私人的 2 收藏的 3 公共的
     * 功能：/knowledge/delIndex.action?isFork=22
     * @return
     */
    public String delIndex()
    {
        if (delType == null)
        {
            delType = 0;
        }
        final String result = knowledgeService.deleteKnowledgeIndex(id, delType, isFork, cuId);
        logger.info("操作人"+getLoginUser().getUserName()+"清空索引");
        ajax(result);
        return null;
    }

    /**
     * 关键字查询知识库页面
     */
    public String findByPageDetail()
    {
        try
        {
            // 设置权限
            final UserInfo userInfo = this.getLoginUser();

            Page<Knowledge> pageInfo = null;
            if (knowledge != null && knowledge.getKnowledgeType() != null
                    && knowledge.getKnowledgeType().getVid() != null)
            {
                KnowledgeType knowledgeType = knowledgeTService.findById(knowledge.getKnowledgeType().getVid());
                if (knowledgeType != null)
                {
                    knowledge.setKnowledgeType(knowledgeType);
                }
            }
            Sort sort = new Sort(new Sort.Order(Direction.DESC, "createTime"));
            Pageable pageAble = getPageable(sort);
            if (isPrivate == null)
            {
                isPrivate = 0;
            }
            pageInfo = knowledgeService.findKonwledgeByLucene(pageAble, userInfo, knowledge.getSearch(), isPrivate);
            if (pageInfo != null)
            {
                List<Knowledge> list = pageInfo.getContent();
                List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
                if (list != null)
                {
                    for (Knowledge o : list)
                    {
                        String kdDeatil = o.getKdDetail();
                        Map<String, Object> map = new HashMap<String, Object>();
                        map.put("vid", o.getVid());
                        map.put("kdDeatil", kdDeatil);
                        mapList.add(map);
                        map.put("managerKnowledge", "");
                    }
                    Map<String, Object> jsonMap = new HashMap<String, Object>();
                    jsonMap.put("aaData", mapList);

                    this.ajaxPage(pageInfo, mapList);
                }
            }
            else
            {
                this.ajaxPage(new PageImpl<Knowledge>(new ArrayList<Knowledge>(), pageAble, 0), null);
            }

        }
        catch (Exception e)
        {
        	logger.error("查询错误", e);
          
        }

        return null;
    }

    /**
     * 按条件获取列表
     * @return
     */
    public String getTable()
    {
        try
        {
            Page<Knowledge> pageInfo = null;
//            System.out.println(Integer.valueOf("ddd"));
            if (knowledge != null && knowledge.getKnowledgeType() != null
                    && knowledge.getKnowledgeType().getVid() != null)
            {
                KnowledgeType kt = knowledgeTService.findById(knowledge.getKnowledgeType().getVid());
                if (kt != null)
                {
                    knowledge.setKnowledgeType(kt);
                }
            }
            Sort sort = new Sort(new Sort.Order(Direction.DESC, "createTime"));
            UserInfo userInfo = this.getLoginUser();
            if (knowledge.getIsPrivate() != null && knowledge.getIsPrivate() > 0)
            {
                knowledge.setIsPrivate(userInfo.getUserId());
            }
            else
            {
                knowledge.setIsPrivate(0);
            }
            knowledge.setIsForkGroup(userInfo.getIsForkGroup());

            pageInfo = knowledgeService.findPageByVo(getPageable(sort), userInfo, knowledge);

            List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
            List<Knowledge> list = pageInfo.getContent();
            if (list != null)
            {
                int index = pageInfo.getNumber() * pageInfo.getSize() + 1;
                for (Knowledge o : list)
                {
                    mapList.add(o.thisToMap(index));
                    index++;
                }

            }
           
            Map<String, Object> jsonMap = new HashMap<String, Object>();
            jsonMap.put("aaData", mapList);

            this.ajaxPage(pageInfo, mapList);
        }
        catch (Exception e)
        {
        	logger.error("获取列表错误", e);
        }
        return null;
    }

    /**
     * 后台添加或者修改
     */

    public String addOrUpdate()
    {
        UserInfo userInfo = this.getLoginUser();
        Integer vid = knowledge.getVid();
        int a = 0;
        if (vid == null)
        {
            // 添加
            knowledge.setCreateTime(new Timestamp(System.currentTimeMillis()));
            knowledge.setAuditTime(new Timestamp(System.currentTimeMillis()));
            KnowledgeType knowledgeType = knowledgeTService.findById(knowledge.getKnowledgeType().getVid());
            if (isPrivate != null && isPrivate > 0)
            {
                knowledge.setIsPrivate(userInfo.getUserId());
            }
            else
            {
                knowledge.setIsPrivate(0);
                if (knowledgeType == null)
                {
                    a = 3;
                }
            }
            knowledge.setCreateUserInfo(userInfo);
            knowledge.setAuditUserInfo(userInfo);
            if (knowledgeType != null)
            {
                knowledge.setIsForkGroup(knowledgeType.getIsForkGroup());
            }
            else
            {
                knowledge.setIsForkGroup(userInfo.getIsForkGroup());
            }
            knowledge.setAuditSign(2);
            knowledge.setIsDelete(0);
            knowledge.setCompanyId(userInfo.getCompanyId());
            if (a == 0)
            {
                a = knowledgeService.saveOrUpdateKnowledge(knowledge);
             
            }

            ajax(a);

        }
        else
        {// 修改
            Knowledge kl = knowledgeService.findById(vid);
            a = 0;
//            // 不是本组的不能修改
//            if (kl.getKnowledgeType().getIsForkGroup().intValue() != userInfo.getIsForkGroup().intValue())
//            {
//                a = 2;
//            }
            kl.setTitle(knowledge.getTitle());
            kl.setKeyword(knowledge.getKeyword());
            kl.setAttachmentIds(knowledge.getAttachmentIds());
            if (knowledge.getKnowledgeType() != null && knowledge.getKnowledgeType().getVid() != null)
            {
                KnowledgeType knowledgeType = knowledgeTService.findById(knowledge.getKnowledgeType().getVid());
                if (knowledgeType != null)
                {
                    kl.setKnowledgeType(knowledgeType);
                }
            }
            kl.setContentInfo(knowledge.getContentInfo());
            a = knowledgeService.saveOrUpdateKnowledge(kl);
            ajax(a);

        }
        return null;
    }

    /**
     * 前台添加或者修改
     */
    public String addOrUpdateBefore()
    {
        UserInfo userInfo = this.getLoginUser();
        Integer vid = knowledge.getVid();
        if (vid == null)
        {
            // 添加
            knowledge.setCreateTime(new Timestamp(System.currentTimeMillis()));
            knowledge.setCreateUserInfo(userInfo);
            knowledge.setAuditSign(1);
            knowledge.setIsDelete(0);
            knowledge.setIsForkGroup(userInfo.getIsForkGroup());
            if (isPrivate != null && isPrivate > 0)
            {
                knowledge.setIsPrivate(userInfo.getUserId());
            }
            else
            {
                knowledge.setIsPrivate(0);
            }
        }
        knowledge.setCompanyId(userInfo.getCompanyId());
        int a = knowledgeService.saveOrUpdateKnowledge(knowledge);
        ajax(a);
        return null;
    }

    /**
     * 根据id找到知识库
     * isToView 0 查看页面 1更新页面 2审核页面 3前台更新页面
     * @return
     */
    public String toViewOrUpdate()
    {
        //
        knowledge = knowledgeService.findById(knowledge.getVid());
        Integer parentId = knowledge.getKnowledgeType().getParentId();
        KnowledgeType knowledget = null;
        if (isToView == 0)
        {
            String parentNameStr = knowledge.getKnowledgeType().getName();
            while (parentId != null && parentId != 0)
            {
                knowledget = knowledgeTService.findById(parentId);
                if (knowledget != null)
                {
                    String parentName = knowledget.getName();
                    parentNameStr = parentName + ">" + parentNameStr;
                    parentId = knowledget.getParentId();
                }
                else
                {
                    parentId = 0;
                }
            }
            this.getRequest().setAttribute("parentNameStr", parentNameStr);// 父类型名称组合
        }

        UserInfo seat = getLoginUser();
        KnowledgeCollect kc = knowledgeCImpl.findOne(knowledge.getVid(), seat.getUserId());
        if (kc != null)
        {
            this.getRequest().setAttribute("kcId", kc.getId());
            this.getRequest().setAttribute("isCollect", true);// 已收藏
        }
        else
        {
            this.getRequest().setAttribute("isCollect", false);// 未收藏
        }
        time = TimeUtil.dateToStr(knowledge.getAuditTime(), "yyyy-MM-dd HH:mm:ss");
        
        List<Attachment> fileList = new ArrayList<Attachment>();
        if(StringUtils.isNotEmpty(knowledge.getAttachmentIds())){
        	fileList = attachmentService.getAttachmentsByIds(knowledge.getAttachmentIds());
        }
        this.getRequest().setAttribute("fileList", fileList);
        if (isToView == 0)
        {
            time = TimeUtil.dateToStr(knowledge.getAuditTime(), "yyyy-MM-dd HH:mm");
            return "toAdd";
        }
        if (isToView == 1)
        {
            return "toUpdate";
        }
        if (isToView == 2)
        {
            return "toShenhe";
        }
        if (isToView == 3)
        {
            return "toUpdateBefore";
        }
        return null;
    }

    /**
     * 删除知识库
     */
    public String delete()
    {
        UserInfo user = getLoginUser();
        if (user != null)
        {
            Knowledge ktype = knowledgeService.findById(knowledge.getVid());
            int a = 0;
            knowledgeService.deleteById(knowledge.getVid());
            ajax(a);
        }
        return null;
    }

    /**
     * @return the isPrivate
     */
    public Integer getIsPrivate()
    {
        return isPrivate;
    }

    /**
     * @param isPrivate the isPrivate to set
     */
    public void setIsPrivate(Integer isPrivate)
    {
        this.isPrivate = isPrivate;
    }

    /**
     * @return the id
     */
    public Integer getId()
    {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id)
    {
        this.id = id;
    }

    /**
     * @return the isFork
     */
    public Integer getIsFork()
    {
        return isFork;
    }

    /**
     * @param isFork the isFork to set
     */
    public void setIsFork(Integer isFork)
    {
        this.isFork = isFork;
    }

    /**
     * @return the cuId
     */
    public Integer getCuId()
    {
        return cuId;
    }

    /**
     * @param cuId the cuId to set
     */
    public void setCuId(Integer cuId)
    {
        this.cuId = cuId;
    }

    /**
     * @return the typeId
     */
    public Integer getTypeId()
    {
        return typeId;
    }

    /**
     * @param typeId the typeId to set
     */
    public void setTypeId(Integer typeId)
    {
        this.typeId = typeId;
    }

    /**
     * @return the startTime
     */
    public String getStartTime()
    {
        return startTime;
    }

    /**
     * @param startTime the startTime to set
     */
    public void setStartTime(String startTime)
    {
        this.startTime = startTime;
    }

    /**
     * @return the endTime
     */
    public String getEndTime()
    {
        return endTime;
    }

    /**
     * @param endTime the endTime to set
     */
    public void setEndTime(String endTime)
    {
        this.endTime = endTime;
    }

    public String getTime()
    {
        return time;
    }

    public void setTime(String time)
    {
        this.time = time;
    }

    public Integer getIsBeforeOrAfter()
    {
        return isBeforeOrAfter;
    }

    public void setIsBeforeOrAfter(Integer isBeforeOrAfter)
    {
        this.isBeforeOrAfter = isBeforeOrAfter;
    }

    public Integer getMyParam()
    {
        return myParam;
    }

    public void setMyParam(Integer myParam)
    {
        this.myParam = myParam;
    }

    public Integer getIsToView()
    {
        return isToView;
    }

    public void setIsToView(Integer isToView)
    {
        this.isToView = isToView;
    }

    public Knowledge getKnowledge()
    {
        return knowledge;
    }

    public void setKnowledge(Knowledge knowledge)
    {
        this.knowledge = knowledge;
    }

    public String getSearch()
    {
        return search;
    }

    public void setSearch(String search)
    {
        this.search = search;
    }

}
