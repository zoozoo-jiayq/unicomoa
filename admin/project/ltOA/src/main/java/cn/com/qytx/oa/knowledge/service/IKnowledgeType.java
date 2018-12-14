package cn.com.qytx.oa.knowledge.service;

import java.util.List;

import cn.com.qytx.cbb.org.util.TreeNode;
import cn.com.qytx.oa.knowledge.domain.KnowledgeType;
import cn.com.qytx.platform.base.service.BaseService;
import cn.com.qytx.platform.org.domain.UserInfo;

/**
 * 功能: 知识库分类service
 * 版本: 1.0
 * 开发人员: 李华伟
 * 创建日期: 2013-11-6
 * 修改日期: 2013-11-6
 * 修改列表:
 */
public interface IKnowledgeType extends BaseService<KnowledgeType> 
{
    /**
     * 功能：根据Id删除知识库分类
     * @param id分类Id
     * @return int 0表示成功 1表示包含子分类不能删除 2表示包含知识库
     */
    int deleteById(Integer id,Integer moduleId);
    
    
    
    /**
     * 功能：查询知识库分类树
     * @return List<TreeNode>
     */
    List<TreeNode> findAllKnowledgeType(boolean containAll,  UserInfo user,Integer isPrivate,Integer isOnly,Integer columnId);
    
    /**
     * 功能：保存知识库分类
     * @param k KnowledgeType
     * @return int 0成功 1重名
     */
    int saveKnowledgeType(KnowledgeType k);
    
    /**
     * 功能：保存更新知识库分类
     * @param k KnowledgeType
     * @return int 0成功 1重名
     */
    int saveOrUpdateKnowledgeType(KnowledgeType k);

    /**
     * 功能：根据Id查询知识库分类
     * @param id分类Id
     * @return KnowledgeType
     */
    KnowledgeType findById(Integer id);
    
    /**
	 * 返回当前登陆用户的顶级知识库类别
	 * @return KnowledgeType
	 */
	KnowledgeType getNativeKnowledgeType(Integer isForkGroup);
	
	/**
     * 功能：根据姓名, 父Id查询知识库分类
     * @param name 名称
     * @param parentId 父类Id
     * @return KnowledgeType
     */
    KnowledgeType findByName(String name, Integer parentId);
    
    /**
     * 功能：根据姓名, 父Id,isParivate查询个人知识库分类
     * @param name 名称
     * @param parentId 父类Id
     * @return KnowledgeType
     */
    KnowledgeType findByNameForPrivate(String name, Integer parentId,Integer parivateId);



    /**
     * 功能：查找或者创建知识库类型
     * @param tempUser 操作人
     * @param name  知识库类型名称
     * @param parentId 父id
     * @return
     */
    KnowledgeType findOrCreateType(UserInfo tempUser, String name, Integer parentId);


    /**
     * 功能：根据父节点查询子节点
     * @param parentId
     * @param moduleId
     * @return
     */
    public List<KnowledgeType> findByParentId(Integer parentId,Integer moduleId);


}
