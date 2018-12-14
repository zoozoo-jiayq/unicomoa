package cn.com.qytx.oa.knowledge.impl;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.qytx.cbb.org.util.TreeNode;
import cn.com.qytx.monitor.client.log.MonitorLogger;
import cn.com.qytx.monitor.client.logImpl.Log4jImpl;
import cn.com.qytx.oa.knowledge.dao.KnowledgeDao;
import cn.com.qytx.oa.knowledge.dao.KnowledgeTypeDao;
import cn.com.qytx.oa.knowledge.domain.Knowledge;
import cn.com.qytx.oa.knowledge.domain.KnowledgeType;
import cn.com.qytx.oa.knowledge.service.IKnowledgeType;
import cn.com.qytx.platform.base.service.impl.BaseServiceImpl;
import cn.com.qytx.platform.org.domain.UserInfo;

/**
 * 功能: 知识库分类service实现类
 * 版本: 1.0
 * 开发人员: 李华伟
 * 创建日期: 2013-11-6
 * 修改日期: 2013-11-6
 * 修改列表:
 */
@Service
@Transactional
public class KnowledgeTypeImpl extends BaseServiceImpl<KnowledgeType> implements IKnowledgeType, Serializable
{
    protected static final Logger logger = Logger.getLogger(KnowledgeTypeImpl.class.getName());
	
    /**
     * 序列号
     */
    private static final long serialVersionUID = 4427492193615740927L;

    /**
     * 知识库分类dao
     */
    @Autowired
    private KnowledgeTypeDao knowledgeTypeDao;

    /**
     * 知识库dao
     */
    @Autowired
    private KnowledgeDao knowledgeDao;

    /**
     * 功能：根据Id删除知识库分类
     * @param id分类Id
     * @return int 0表示成功 1表示包含子分类不能删除 2表示包含知识库
     */
    @Override
    public int deleteById(Integer id,Integer moduleId)
    {
        // 判断是否包含子分类,或者知识库中被引用 TODO
        List<KnowledgeType> typeList = knowledgeTypeDao.findByParentId(id,moduleId);
        if (null != typeList && !typeList.isEmpty())
        {
            // 包含子类不能删除
            return 1;
        }

        // 判断是否包含知识库
        Knowledge knowledge = knowledgeDao.findKnowledgeByTypeId(id);
        if (null != knowledge)
        {
            // 此分类下包含知识库信息不能删除
            return 2;
        }

        // 更新状态为已删除
        knowledgeTypeDao.deleteById(id);
        UserInfo  user=(UserInfo) ServletActionContext.getRequest().getSession().getAttribute("adminUser");
        logger.info(user.getUserName()+"删除知识库类型 id="+id);
        return 0;
    }

    /**
     * 功能：查询所有知识库类型
     * @param containAll 是否包含顶级
     * @param user 坐席
     * @param isPrivate 显示个人 显示公共
     * @param isOnly 是否只显示本组织的
     * @return
     */
    @Override
    public List<TreeNode> findAllKnowledgeType(boolean containAll, UserInfo user, Integer isPrivate, Integer isOnly,Integer columnId)
    {
        List<TreeNode> treeList = new ArrayList<TreeNode>();
        TreeNode treeNode = new TreeNode();
        if (containAll)
        {
            treeNode.setId("kid_0");// 部门ID前加gid表示类型为部门
            treeNode.setName("知识库");
            treeNode.setPId("kid_-1");
            treeList.add(treeNode);
        }
        List<KnowledgeType> typeList = knowledgeTypeDao.findAllKnowledgeType(user, isPrivate, isOnly,columnId);
        if (null != typeList && !typeList.isEmpty())
        {
            for (KnowledgeType type : typeList)
            {
                treeNode = new TreeNode();
                treeNode.setId("kid_" + type.getVid());// 部门ID前加gid表示类型为部门
                treeNode.setName(type.getName());
                treeNode.setPId("kid_" + type.getParentId());
                treeList.add(treeNode);
            }
        }
        return treeList;
    }

    /**
     * 功能：保存知识库分类
     * @param k KnowledgeType
     * @return int 0成功 1重名
     */
    @Override
    public int saveKnowledgeType(KnowledgeType k)
    {
        KnowledgeType knowledgeType;
        // 获取父部门的path
        KnowledgeType parentKnowledgeType = knowledgeTypeDao.findOne(k.getParentId());

        // 获取父id 如果父Id为0 则为顶级部门
        if (k.getIsPrivate() == 0)
        {
//            if (k.getIsForkGroup().intValue() != parentKnowledgeType.getIsForkGroup().intValue())
//            {
//                return 2;
//            }
            knowledgeType = knowledgeTypeDao.findByName(k.getName(), k.getParentId());
        }
        else
        {

            knowledgeType = knowledgeTypeDao.findByNameForPrivate(k.getName(), k.getParentId(), k.getIsPrivate());
        }

        if (null != knowledgeType)
        {
            return 1;
        }
        k.setIsDelete(0);

        // 保存知识库
        knowledgeTypeDao.saveOrUpdate(k);

        if (null != parentKnowledgeType)
        {
            k.setIsForkGroup(parentKnowledgeType.getIsForkGroup());
            k.setPath(parentKnowledgeType.getPath() + "," + k.getVid());
        }
        else
        {
            k.setPath(k.getVid().toString());
        }
        
        knowledgeTypeDao.saveOrUpdate(k);
        logger.info("添加知识库类型"+k);
        return 0;
    }

    /**
     * 功能：保存更新知识库分类
     * @param k KnowledgeType
     * @return int 0成功 1重名
     */
    @Override
    public int saveOrUpdateKnowledgeType(KnowledgeType k)
    {
        // 判断是否为新增
        if (null == k.getVid())
        {
            return saveKnowledgeType(k);
        }
        else
        {
            // 获取父id 如果父Id为0 则为顶级部门
            KnowledgeType oldKnowledgeType = knowledgeTypeDao.findOne(k.getVid());
            KnowledgeType knowledgeType;
            if (k.getIsPrivate() == 0)
            {
                knowledgeType = knowledgeTypeDao.findByName(k.getName(), oldKnowledgeType.getParentId());

            }
            else
            {
                knowledgeType = knowledgeTypeDao.findByNameForPrivate(k.getName(), oldKnowledgeType.getParentId(),
                        k.getIsPrivate());
            }

            if (null != knowledgeType && k.getVid().intValue() != knowledgeType.getVid())
            {
                return 1;
            }

            // 修改知识库分类
            oldKnowledgeType = knowledgeTypeDao.findOne(k.getVid());
            oldKnowledgeType.setName(k.getName());
            oldKnowledgeType.setOrderIndex(k.getOrderIndex());
            knowledgeTypeDao.saveOrUpdate(oldKnowledgeType);
            logger.info("修改知识库类型"+k);
        }
        return 0;
    }

    /**
     * 功能：根据Id查询知识库分类
     * @param id分类Id
     * @return KnowledgeType
     */
    @Override
    public KnowledgeType findById(Integer id)
    {
        return knowledgeTypeDao.findOne(id);
    }

    /**
     * 返回当前登陆用户的顶级知识库类别
     * @return KnowledgeType
     */
    @Override
    public KnowledgeType getNativeKnowledgeType(Integer isForkGroup)
    {

        return knowledgeTypeDao.getNativeKnowledgeType(isForkGroup);
    }

    /**
     * 功能：查询知识库类型
     * @param name 知识名称
     * @param parentId 知识库父id
     * @return
     */
    @Override
    public KnowledgeType findByName(String name, Integer parentId)
    {

        return knowledgeTypeDao.findByName(name, parentId);
    }

    /**
     * 功能：查询私人知识库类型
     * @param name 知识库名称
     * @param parentId 父id
     * @param parivateId 私有id（坐席id）
     * @return
     */
    @Override
    public KnowledgeType findByNameForPrivate(String name, Integer parentId, Integer parivateId)
    {
        return knowledgeTypeDao.findByNameForPrivate(name, parentId, parivateId);
    }

    /**
     * 功能：查找或者创建知识库类型
     * @param tempUser 操作人
     * @param name 知识库类型名称
     * @param parentId 父id
     * @return
     */
    public KnowledgeType findOrCreateType(UserInfo tempUser, String name, Integer parentId)
    {
        KnowledgeType kt = findByName(name, parentId);
        if (kt == null)
        {
            KnowledgeType temp = new KnowledgeType();
            temp.setCreateTime(new Timestamp(System.currentTimeMillis()));
            temp.setCreateUserInfo(tempUser);
            temp.setIsDelete(0);
            temp.setName(name);
            temp.setParentId(parentId);
            temp.setOrderIndex(999);
            temp.setIsForkGroup(tempUser.getIsForkGroup());
            temp.setIsPrivate(0);
            temp.setCompanyId(tempUser.getCompanyId());
            saveOrUpdateKnowledgeType(temp);
            return temp;
        }
        else
        {
            return kt;
        }
    }

    public List<KnowledgeType> findByParentId(Integer parentId,Integer moduleId){
    	return knowledgeTypeDao.findByParentId(parentId, moduleId);
    }
 }
