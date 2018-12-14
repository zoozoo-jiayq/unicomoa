package cn.com.qytx.oa.knowledge.service;

import java.util.List;

import cn.com.qytx.oa.knowledge.domain.Knowledge;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.Pageable;
import cn.com.qytx.platform.base.service.BaseService;
import cn.com.qytx.platform.org.domain.UserInfo;


/**
 * 功能: 知识库service
 * 版本: 1.0
 * 开发人员: 李华伟
 * 创建日期: 2013-11-7
 * 修改日期: 2013-11-7
 * 修改列表:
 */
public interface IKnowledge extends BaseService<Knowledge>
{
    /**
     * 功能：分页查询知识库列表页面
     * @param page page
     * @param knowledge knowledge查询条件
     * @return 分页数据
     */
    Page<Knowledge> findPageByVo(Pageable page, UserInfo userInfo,Knowledge knowledge);

    /**
     * 功能：根据Id删除知识库信息
     * @param id 参数信息
     */
    void deleteById(Integer id);

    /**
     * 功能：保存知识库信息
     * @param knowledge 知识库
     * @return 0表示成功 1表示标题重复
     */
    int saveKnowledge(Knowledge knowledge);

    /**
     * 功能：保存更新知识库信息
     * @param knowledge 知识库
     * @return 0表示成功 1表示标题重复
     */
    int saveOrUpdateKnowledge(Knowledge knowledge);

    /**
     * 功能：根据Id获取知识库信息
     * @param knowledgeId knowledgeId
     * @return Knowledge
     */
    Knowledge findById(Integer knowledgeId);
    
    /**
     * 功能：后台首页 知识库 5条
     * @return List<Notice>
     */
    List<Object> findLaster5Notice();
    /**
     * 功能：以详情的样式，查询知识库
     * @param page
     * @param knowledge
     * @return
     */
    Page<Knowledge> findByPageDetail(Pageable page,Knowledge knowledge);
    /**
     * 
     * 功能：用lucene查询知识库
     * @param page
     * @param knowledge
     * @param kType  0 公共  1 个人
     * @return
     */
    Page<Knowledge> findKonwledgeByLucene(Pageable page,UserInfo option,String queryStr,int kType);
    /**
     * 
     * 功能：初始化知识库
     * @param isForkGroup  权限
     * @param creatUserId 创建者id
     * @param typeId  知识库类型id
     * @param startTime  查询的开始时间
     * @param endTime  查询的结束时间
     * @return
     */
    String initKnowledgeIndex(Integer id,Integer isForkGroup,Integer creatUserId,Integer typeId,String startTime,String endTime);

	/**
	 * 功能：根据权限和  创建者id删除索引
     * @param id  知识库id
      * @param type  0 所有的  1私人的  2 收藏的 3 公共的
     * @param isFork  forkid 
     * @param cuId   用户id
     * @return
     */
	String deleteKnowledgeIndex(Integer id,Integer type ,Integer isFork, Integer cuId);
	String initColleKnowledgeIndex(Integer creatUserId,String startTime,String endTime);

    /**
     * 
     * 功能：保存知识库
     * @param title 标题
     * @param contentStr 内容
     * @param knowledgeGroup  组
     * @param userInfo 创建人
     * @return  1 失败   0 成功
     */
   public  int saveKnowledge(String title,String contentStr,Integer knowledgeGroup,UserInfo userInfo);
    
   
   /**
    * 功能：分页查询知识库列表页面
    * @param page page
    * @return 分页数据
    */
   public Page<Knowledge> findPage(Pageable page, UserInfo userInfo,Integer typeId,Integer moduleId);
}
