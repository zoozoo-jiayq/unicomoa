/**
 * 
 */
package cn.com.qytx.oa.knowledge.action;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import cn.com.qytx.monitor.client.log.MonitorLogger;
import cn.com.qytx.monitor.client.logImpl.Log4jImpl;
import cn.com.qytx.oa.knowledge.impl.KnowledgeImpl;
import cn.com.qytx.oa.knowledge.service.IKnowledge;

/**
 * 功能:定时任务的后台接口 用于删除和创建所有的索引
 * 版本: 1.0
 * 开发人员: 彭小东
 * 创建日期: 2014-12-31
 * 修改日期: 2014-12-31
 * 修改列表:
 */
public class KnowledgeIndex
{
    /**
     * 序列号
     */
    private static final long serialVersionUID = 2811917379318955019L;
    protected static final Logger logger = Logger.getLogger(KnowledgeIndex.class.getName());
	

    @Resource(name = "knowledgeImpl")
    private IKnowledge knowledgeService;

    /**
     * 功能：创建和删除索引
     */
    public void initKnowledge()
    {
        int result = delIndex();
        if (result == 1)
        {
            initIndex();
        }
    }

    // 初始化索引
    private int initIndex()
    {
        String result = knowledgeService.initKnowledgeIndex(null, null, null, null, null, null);
        logger.info(result);
        knowledgeService.initColleKnowledgeIndex(null, null, null);
        logger.info("创建收藏索引成功");

        return 1;
    }

    // /knowledge/delIndex.action?isFork=22
    // type 0 所有的 1私人的 2 收藏的 3 公共的
    private int delIndex()
    {

        String result = knowledgeService.deleteKnowledgeIndex(null, 0, null, null);
        logger.info(result);
        return 1;
    }

}
