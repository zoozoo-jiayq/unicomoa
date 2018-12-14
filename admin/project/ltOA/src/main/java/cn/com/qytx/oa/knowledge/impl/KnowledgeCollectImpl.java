package cn.com.qytx.oa.knowledge.impl;

import java.io.Serializable;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.qytx.monitor.client.log.MonitorLogger;
import cn.com.qytx.monitor.client.logImpl.Log4jImpl;
import cn.com.qytx.oa.knowledge.dao.KnowledgeCollectDao;
import cn.com.qytx.oa.knowledge.domain.KnowledgeCollect;
import cn.com.qytx.oa.knowledge.service.IKnowledgeCollect;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.Pageable;
import cn.com.qytx.platform.base.service.impl.BaseServiceImpl;
import cn.com.qytx.ydzj.action.PublicPhoneBookAction;

/**
 * 功能: 知识库收藏的实现类
 * 版本: 1.0
 * 开发人员: 马恺
 * 创建日期: 2014-11-25
 * 修改日期: 2014-11-25
 * 修改列表:
 */
@Service
@Transactional
public class KnowledgeCollectImpl extends BaseServiceImpl<KnowledgeCollect> implements IKnowledgeCollect, Serializable
{
    protected static final Logger logger = Logger.getLogger(KnowledgeTypeImpl.class.getName());
	
    private static final long serialVersionUID = -6694362188745973109L;
    @Resource
    private KnowledgeCollectDao knowledgeCollectDao;

    /**
     * 功能：知识库收藏分页查询
     * @param page
     * @param kc 查询条件
     * @return
     */
    @Override
    public Page<KnowledgeCollect> findByPage(Pageable page, KnowledgeCollect kc)
    {

        return knowledgeCollectDao.findByPage(page, kc);
    }

    /**
     * 功能：知识库取消收藏
     * @param id
     */
    @Override
    @Deprecated
    public void changeStateById(Integer id)
    {

        knowledgeCollectDao.changeStateById(id);
        KnowledgeCollect kc = knowledgeCollectDao.findOne(id);
        logger.info("取消知识库收藏"+kc);
//        LuceneUtil.deleteCollect(kc.getKnowledge(), kc.getSeat().getUserId());
    }

    /**
     * 功能：知识库添加收藏
     * @param kc
     */
    @Override
    @Deprecated
    public void saveOrUpdateKC(KnowledgeCollect kc)
    {
        knowledgeCollectDao.saveOrUpdateKC(kc);
        logger.info("添加知识库收藏"+kc);
//        LuceneUtil.addCollectIndex(kc.getKnowledge(), kc.getSeat().getUserId());

    }

    /**
     * 功能：根据知识id 坐席id查询知识库对象
     * @param knowledgeId 知识库id
     * @param seatId 坐席id
     * @return
     */
    @Override
    public KnowledgeCollect findOne(Integer knowledgeId, Integer seatId)
    {

        return knowledgeCollectDao.findOne(knowledgeId, seatId);
    }

}
