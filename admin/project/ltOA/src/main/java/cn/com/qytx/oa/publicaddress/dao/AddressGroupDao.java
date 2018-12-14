package cn.com.qytx.oa.publicaddress.dao;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import cn.com.qytx.oa.publicaddress.domain.AddressConst;
import cn.com.qytx.oa.publicaddress.domain.AddressGroup;
import cn.com.qytx.platform.base.dao.BaseDao;
import cn.com.qytx.platform.base.query.Sort;
import cn.com.qytx.platform.base.query.Sort.Direction;
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.utils.CbbConst;

/**
 * 功能:联系人群组Dao
 * 版本: 1.0
 * 开发人员: 李华伟
 * 创建日期: 2013-3-22
 * 修改日期: 2013-3-22
 * 修改列表:
 */
@Component
public class AddressGroupDao extends BaseDao<AddressGroup, Integer> implements Serializable
{
    /**
	 * 描述含义
	 */
	private static final long serialVersionUID = 1L;

	/**
     * 根据用户ID获取此用户的联系人组信息
     * @param user UserInfo
     * @return List<AddressGroup>
     */
    @SuppressWarnings("unchecked")
    public List<AddressGroup> findByUser(UserInfo user)
    {
        List<AddressGroup> ownAddressGroupList = super
                .findAll(
                        " createUserInfo.userId = ? and isDelete = ?  and groupType = 1 ",new Sort(Direction.ASC, "orderNo"),
                        user.getUserId(), CbbConst.NOT_DELETE);

        if (null == ownAddressGroupList)
        {
            ownAddressGroupList = new ArrayList<AddressGroup>();
        }
        
        // 添加默认组
        AddressGroup ag = new AddressGroup();
        ag.setId(AddressConst.DEFAULT_GROUP);
        ag.setName(AddressConst.DEFAULT_GROUP_NAME);
        ownAddressGroupList.add(0, ag);

        // 查询每个组包含的人员数量
        String hql = "select address_Group_Id, count(*) from tb_oab_address where is_delete = 0 and create_user_id = "
                + user.getUserId() + " group by address_Group_Id";
        List list = entityManager.createNativeQuery(hql).getResultList();
        
        HashMap<Integer,Integer> numMap = getGroupUserNum(list);
        for (AddressGroup temp : ownAddressGroupList)
        {
            Integer containAddress = numMap.get(temp.getId());
            if (null == containAddress)
            {
                containAddress = 0;
            }
            temp.setContainAddress(containAddress);
        }

        return ownAddressGroupList;
    }

    public HashMap<Integer,Integer> getGroupUserNum(List list)
    {
        try
        {
            HashMap ht=new HashMap();
            Iterator it = list.iterator();
            while (it.hasNext()) {
                Object[] obj = (Object[]) it.next();
                int groupId=Integer.parseInt(obj[0].toString());
                int userNum=Integer.parseInt(obj[1].toString());
                ht.put(groupId,userNum);
            }
            return ht;
        }
        catch (Exception ex)
        {

        }
        return null;
    }

    /**
     * 根据联系人组组名查询联系人组
     * @param user 用户
     * @param groupName 组名
     * @param groupType 组类型
     * @return List<AddressGroup>
     */
    @SuppressWarnings("unchecked")
    public List<AddressGroup> findAllByName(UserInfo user, String groupName, Integer groupType)
    {
        // 区分公共通讯簿和个人通讯簿
        if (AddressConst.GROUPTYPE_PERSONAL == groupType)
        {
            return super
                    .findAll(
                            " createUserInfo.userId = ? and isDelete = ? and name = ? and groupType = ?",
                            user.getUserId(), CbbConst.NOT_DELETE, groupName,
                            groupType);
        }
        else
        {
            return super
                    .findAll(
                            " isDelete = ?  and name = ? and groupType = ?",
                            CbbConst.NOT_DELETE, groupName, groupType);
        }

    }

    /**
     * 根据主键查询联系人组信息
     * @param vid 主键
     * @return AddressGroup
     */
    public AddressGroup findById(Integer vid)
    {
        return (AddressGroup) super.findOne(" id = ? and isDelete = ?",
                vid, CbbConst.NOT_DELETE);

    }

    /**
     * 根据主键查询联系人组信息
     * @param vid 主键
     * @return AddressGroup
     */
    public void deleteById(Integer vid)
    {
        super.bulkUpdate("update AddressGroup set isDelete = ?  where isDelete = ?  and id = ?",
                CbbConst.DELETED, CbbConst.NOT_DELETE, vid);
        ;

    }

    /**
     * 根据用户获取此用户的共享组信息
     * @param user 用户
     * @return List<AddressGroup>
     */
    @SuppressWarnings("unchecked")
    public List<AddressGroup> findShareByUser(UserInfo user)
    {
        //
        StringBuffer hql = new StringBuffer();
        hql.append(" x.isDelete = ? ");
        hql.append("and x.id in (select aog.groupId from AddressOfGroup aog where aog.addressId in");
        hql.append("(select a.id from Address a where a.shareToUserIds like ? and a.isDelete = 0");
        Timestamp now = new Timestamp(System.currentTimeMillis());
        hql.append(" and (a.startShareTime <= ? or a.startShareTime is null) and (a.endShareTime >= ? or a.endShareTime is null)");
        hql.append(")) ");
        return super.findAll(hql.toString(),new Sort(Direction.ASC, "x.orderNo"), CbbConst.NOT_DELETE,
                "%," + user.getUserId() + ",%", now, now);
    }

    /**
     * 根据用户获取此用户的公共通讯簿信息
     * @param user 用户
     * @return List<AddressGroup>
     */
    public List<AddressGroup> findPublicByUser(UserInfo user, String dataPrivHql)
    {
        if (StringUtils.isEmpty(dataPrivHql))
        {
            return super
                    .findAll(
                            " isDelete = ? and  groupType = ? ",new Sort(Direction.ASC, "orderNo"),
                            CbbConst.NOT_DELETE, AddressConst.GROUPTYPE_PUBLIC);
        }
        else
        {
            return super.findAll(
                    " isDelete = ?  and groupType = ? and "
                            + dataPrivHql + " ",new Sort(Direction.ASC, "orderNo"), CbbConst.NOT_DELETE,
                            AddressConst.GROUPTYPE_PUBLIC);
        }

    }
}
