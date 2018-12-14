package cn.com.qytx.oa.knowledge.action;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import cn.com.qytx.monitor.client.log.MonitorLogger;
import cn.com.qytx.monitor.client.logImpl.Log4jImpl;
import cn.com.qytx.oa.knowledge.domain.Knowledge;
import cn.com.qytx.oa.knowledge.domain.KnowledgeCollect;
import cn.com.qytx.oa.knowledge.impl.KnowledgeImpl;
import cn.com.qytx.oa.knowledge.service.IKnowledge;
import cn.com.qytx.oa.knowledge.service.IKnowledgeCollect;
import cn.com.qytx.platform.base.action.BaseActionSupport;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.Sort;
import cn.com.qytx.platform.base.query.Sort.Direction;
import cn.com.qytx.platform.org.domain.UserInfo;

/**
 * 功能: 知识库收藏的action
 * 版本: 1.0
 * 开发人员: 马恺
 * 创建日期: 2014-11-25
 * 修改日期: 2014-11-25
 * 修改列表:
 */
public class KnowledgeCollectAction extends BaseActionSupport
{
    protected static final Logger logger = Logger.getLogger(KnowledgeActionType.class.getName());
	
    private static final long serialVersionUID = -4083090234134194017L;

    /**
     * 知识库收藏逻辑接口
     */
    @Autowired
    private transient IKnowledgeCollect knowledgeCollectImpl;
    /**
     * 知识库逻辑接口
     */
    @Autowired
    private transient IKnowledge knowledgeService;

    /**
     * 知识库
     */
    private Knowledge knowledge;

    /**
     * 收藏知识库
     */
    private KnowledgeCollect knowledgeCollect;

    /**
     * 收藏知识
     */
    public void collectKnowledge()
    {
        try
        {
            UserInfo seat = getLoginUser();
            Knowledge tempK = knowledgeService.findOne(knowledge.getVid());
            if (seat != null)
            {
                KnowledgeCollect old = knowledgeCollectImpl.findOne(knowledge.getVid(), seat.getUserId());
                if (old == null)
                {
                    if (tempK != null)
                    {
                        KnowledgeCollect temp = new KnowledgeCollect();
                        temp.setSeat(seat);
                        temp.setKnowledge(tempK);
                        temp.setState(0);
                        temp.setLastUpdateTime(new Timestamp(System.currentTimeMillis()));
                        temp.setCompanyId(seat.getCompanyId());
                        knowledgeCollectImpl.saveOrUpdateKC(temp);
                        ajax(temp.getId());// 收藏成功
                    }
                    else
                    {
                        ajax("error1");// 失败
                    }
                }
                else
                {
                    ajax("alreadyCollect");// 已收藏
                }
            }
        }
        catch (Exception e)
        {
            ajax("error2");// 失败
            logger.error("收藏知识库错误", e);
        }

    }

    /**
     * 分页查询收藏的知识
     */
    public void findCollectByPage()
    {
        try
        {
            UserInfo seat = getLoginUser();
            knowledgeCollect.setSeat(seat);
            Page<KnowledgeCollect> pageInfo = knowledgeCollectImpl.findByPage(getPageable(new Sort(Direction.DESC,
                    "lastUpdateTime")), knowledgeCollect);
            List<KnowledgeCollect> list = pageInfo.getContent();
            List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
            if (list != null)
            {
                int i = pageInfo.getNumber() * pageInfo.getSize() + 1;
                for (KnowledgeCollect kc : list)
                {
                    mapList.add(kc.thisToMap(i));
                    i++;
                }
            }
            Map<String, Object> jsonMap = new HashMap<String, Object>();
            jsonMap.put("aaData", mapList);

            this.ajaxPage(pageInfo, mapList);
        }
        catch (Exception e)
        {
        	logger.error("分页查询知识库错误", e);
          //  LOGGER.error("方法"+GetMethodUtil.getExectionMethod(), e);
        }
    }

    /**
     * 删除收藏
     */
    public void deleteCollect()
    {
        Integer kcId = Integer.parseInt(this.getRequest().getParameter("kcId"));
        try
        {
            knowledgeCollectImpl.changeStateById(kcId);
            ajax(0);
        }
        catch (Exception e)
        {
            ajax(1);
            logger.error("删除收藏知识库错误", e);
            //LOGGER.error("方法"+GetMethodUtil.getExectionMethod(), e);
        }

    }

    public Knowledge getKnowledge()
    {
        return knowledge;
    }

    public void setKnowledge(Knowledge knowledge)
    {
        this.knowledge = knowledge;
    }

    public KnowledgeCollect getKnowledgeCollect()
    {
        return knowledgeCollect;
    }

    public void setKnowledgeCollect(KnowledgeCollect knowledgeCollect)
    {
        this.knowledgeCollect = knowledgeCollect;
    }

}
