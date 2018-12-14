package cn.com.qytx.oa.knowledge.dao;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Repository;

import cn.com.qytx.oa.knowledge.domain.KnowledgeType;
import cn.com.qytx.platform.base.dao.BaseDao;
import cn.com.qytx.platform.base.query.Sort;
import cn.com.qytx.platform.base.query.Sort.Direction;
import cn.com.qytx.platform.base.query.Sort.Order;
import cn.com.qytx.platform.org.domain.UserInfo;

/**
 * 功能:知识库类型dao
 * 版本: 1.0
 * 开发人员: 李华伟
 * 创建日期: 2013-11-6
 * 修改日期: 2013-11-6
 * 修改列表:
 */
@Repository
public class KnowledgeTypeDao extends BaseDao<KnowledgeType, Integer> implements Serializable
{
    /**
     * 序列号
     */
    private static final long serialVersionUID = 2007928535797746747L;

    /**
     * 功能：根据姓名, 父Id查询知识库分类
     * @param name 名称
     * @param parentId 父类Id
     * @return KnowledgeType
     */
    public KnowledgeType findByName(String name, Integer parentId)
    {
        String hql = "name = ? and parentId = ? and isDelete = 0";
        return (KnowledgeType) super.findOne(hql, name, parentId);
    }

    /**
     * 功能：根据姓名, 父Id,isParivate查询个人知识库分类
     * @param name 名称
     * @param parentId 父类Id
     * @return KnowledgeType
     */
    public KnowledgeType findByNameForPrivate(String name, Integer parentId, Integer parivateId)
    {
        String hql = "name = ? and parentId = ? and isDelete = 0 and isPrivate= ?";
        return (KnowledgeType) super.findOne(hql, name, parentId, parivateId);
    }

    /**
     * 功能：获取全部知识库类别
     * @param user 操作人
     * @param isPrivate 显示个人 显示公共
     * @param isOnly 是否只显示本组织的
     * @return List<KnowledgeType>
     */

    public List<KnowledgeType> findAllKnowledgeType(UserInfo user, Integer isPrivate, Integer isOnly,Integer columnId)
    {
        StringBuilder sb = new StringBuilder("isDelete = 0 ");
        List<Object> params = new LinkedList<Object>();
//        if (user != null && user.getIsForkGroup() != 2)
//        {
//            if (isOnly != null && isOnly == 1)
//            {
//                sb.append(" and  isForkGroup=? ");
//            }
//            else
//            {
//                sb.append(" and ( isForkGroup=2 or isForkGroup=?)");
//            }
//            params.add(user.getIsForkGroup());
//        }
        sb.append("and isPrivate=?");
        params.add(isPrivate);
        //功能模块id
        if (columnId!=null) {
        	sb.append(" and columnId=? ");
            params.add(columnId);
		}

        // 排序
        Order order = new Order(Direction.ASC, "orderIndex");
        Sort s = new Sort(order);
        return super.findAll(sb.toString(), s, params.toArray() );
    }

    /**
     * 功能：根据姓名, 父Id查询知识库分类
     * @param name 名称
     * @param parentId 父类Id
     * @return KnowledgeType
     */
    public List<KnowledgeType> findByParentId(Integer parentId,Integer moduleId)
    {
        // 排序
        Order order = new Order(Direction.ASC, "orderIndex");
        Sort s = new Sort(order);
        String hql = "parentId = ? and isDelete = 0 and columnId=?";
        return super.findAll(hql, s, parentId,moduleId);
    }

    /**
     * 功能：更新状态为删除
     * @param id
     * @return
     */
    public void deleteById(Integer id)
    {
        String hql = "update KnowledgeType set isDelete = 1 where vid = ?";
        super.executeQuery(hql, id);
        // super.bulkUpdate(hql, id);
    }

    /**
     * 功能：获取知识库完整路径
     * @param knowledgeTypeId 知识库类型
     * @return String
     */
    public String getKnowledgeTypeNameById(int knowledgeTypeId)
    {
        String hql = "isDelete=0 and vid = ?";
        KnowledgeType knowledgeType = (KnowledgeType) super.findOne(hql, knowledgeTypeId);
        String res = "";

        if (null != knowledgeType)
        {
            // 获取此部门的级联上级部门
            String hqlAll = "isDelete=0 and vid in (" + knowledgeType.getPath() + ")";
            List<KnowledgeType> knowledgeTypeList = super.findAll(hqlAll);

            // 组装级联结果信息
            String path = knowledgeType.getPath();
            String[] allPath = path.split(",");
            StringBuffer pathSb = new StringBuffer();
            for (String groupId : allPath)
            {
                KnowledgeType temp = getKnowledgeTypeFromList(knowledgeTypeList,
                        Integer.parseInt(groupId));
                if (null != temp)
                {
                    pathSb.append(temp.getName());
                    pathSb.append(" > ");
                }
            }
            if (pathSb.length() > 0)
            {
                return pathSb.substring(0, pathSb.length() - 3);
            }
        }
        return res;
    }

    private KnowledgeType getKnowledgeTypeFromList(List<KnowledgeType> knowledgeTypeList,
            Integer groupId)
    {
        for (KnowledgeType knowledgeType : knowledgeTypeList)
        {
            if (knowledgeType.getVid().intValue() == groupId.intValue())
            {
                return knowledgeType;
            }
        }
        return null;
    }

    /**
     * 根据id查找知识库类
     * @param id
     * @return
     */
    public KnowledgeType findById(Integer id)
    {

        return super.findOne(id);
    }

    /**
     * 返回当前登陆用户的顶级知识库类别
     * @return KnowledgeType
     */
    public KnowledgeType getNativeKnowledgeType(Integer isForkGroup)
    {
        String hql = " isDelete=0 and parentId=0 and isPrivate=0 and isForkGroup=" + isForkGroup;

        return (KnowledgeType) super.findOne(hql);
    }

    /**
     * 功能：
     * @return
     */
    public List<Object> findallIsFork()
    {
        // TODO Auto-generated method stub
        String sql = "SELECT  distinct(is_fork_group)  FROM tb_Knowledge_type";
        @SuppressWarnings("unchecked")
        List<Object> list = super.entityManager.createNativeQuery(sql).getResultList();
        return list;
    }
}
