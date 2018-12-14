package cn.com.qytx.oa.publicaddress.dao;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.com.qytx.oa.publicaddress.domain.Address;
import cn.com.qytx.oa.publicaddress.domain.AddressConst;
import cn.com.qytx.oa.publicaddress.vo.AddressVo;
import cn.com.qytx.platform.base.dao.BaseDao;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.Pageable;
import cn.com.qytx.platform.base.query.Sort;
import cn.com.qytx.platform.base.query.Sort.Direction;
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.utils.CbbConst;

/**
 * 功能:通讯簿联系人Dao
 * 版本: 1.0
 * 修改列表:
 */
@Component
public class AddressDao extends BaseDao<Address, Integer> implements Serializable
{
    /**
	 * 描述含义
	 */
	private static final long serialVersionUID = 1L;

	/**
     * 根据群组Id删除组下成员
     * @param groupId 群组Id
     */
    public void deleteByGroupId(Integer groupId)
    {
        super.bulkUpdate(
                "update Address set isDelete = ?  where isDelete = ?  and addressGroupId = ?",
                CbbConst.DELETED, CbbConst.NOT_DELETE, groupId);
    }

    /**
     * 根据vo查询联系人信息
     * @param vo AddressVo
     * @return List<Address>
     */
    @SuppressWarnings("unchecked")
    public List<Address> findAllAddress(AddressVo vo)
    {
        StringBuilder hql = new StringBuilder();
        hql.append(" x.isDelete = " + CbbConst.NOT_DELETE);
        if (null != vo)
        {
        	
        	
        	
            List<Object> params = new ArrayList<Object>();
            hql.append(" and (1=1");
           // 姓名
            if (!StringUtils.isEmpty(vo.getName()))
            {
                hql.append(" or x.name like ?");
                params.add("%" + vo.getName() + "%");
            }
            // 单位名称
            if (!StringUtils.isEmpty(vo.getCompanyName()))
            {
            	if(!StringUtils.isEmpty(vo.getName())){
            		hql.append(" or ");
            	}
                hql.append(" x.companyName like ?");
                params.add("%" + vo.getCompanyName() + "%");
            }
            // 手机
            if (!StringUtils.isEmpty(vo.getFamilyMobileNo()))
            {
                hql.append(" or x.familyMobileNo like ?");
                params.add("%" + vo.getFamilyMobileNo() + "%");
               
            }
            hql.append(" )");
            // 主键Id
            Integer id = vo.getId();
            if (null != id)
            {
                hql.append(" and x.id = ?");
                params.add(id);
            }

            // 组Id
            Integer groupId = vo.getAddressGroupId();
            if (!StringUtils.isEmpty(vo.getType()) && "share".equals(vo.getType()))
            {
                // 共享时,查询共享组Id下的所有联系人
                hql.append(" and x.id in (select aog.addressId from AddressOfGroup aog where aog.groupId = ?)");
                params.add(groupId);
            }
            else
            {
                if (null != groupId && CbbConst.SEARCH_ALL != groupId)
                {
                    // 查询指定组信息
                    if (groupId == AddressConst.DEFAULT_GROUP)
                    {
                        // 默认组时,需要查询个人默认组和公共默认组的人员
                        hql.append(" and ((x.id in (select aog.addressId from AddressOfGroup aog where aog.groupId = ?) and x.createUserInfo.userId = ? ) or ");
                        hql.append("  x.id in (select aog.addressId from AddressOfGroup aog where aog.groupId = ?)) ");
                        params.add(groupId);
                        params.add(vo.getCreateUserInfo().getUserId());
                        params.add(AddressConst.DEFAULT_PUBLIC_GROUP);
                    }
                    else
                    {
                        hql.append(" and x.id in (select aog.addressId from AddressOfGroup aog where aog.groupId = ?)");
                        params.add(groupId);
                    }
                }
                else
                {
                    if (null != vo.getPublicSign() && 1 == vo.getPublicSign())
                    {
                        // 查询所有公共组信息
                        hql.append(" and x.id in (select aog.addressId from AddressOfGroup aog where (aog.groupId in (select ag.id ");
                        hql.append("from AddressGroup ag where ag.isDelete = 0 and ag.groupType = ? and "
                                + vo.getDataPrivHql() + ")) or aog.groupId = ?)");
                        params.add(AddressConst.GROUPTYPE_PUBLIC);
                        params.add(AddressConst.DEFAULT_PUBLIC_GROUP);
                    }
                    else
                    {
                        // 查询个人所有通讯簿
                        hql.append(" and (x.id in (select aog.addressId from AddressOfGroup aog where (aog.groupId in (select ag1.id ");
                        hql.append("from AddressGroup ag1 where ag1.isDelete = 0 and ag1.createUserInfo.userId = ?  and ag1.groupType = ?))) or ");
                        params.add(vo.getCreateUserInfo().getUserId());
                        params.add(AddressConst.GROUPTYPE_PERSONAL);

                        // 有开放权限的公共通讯簿组
                        hql.append("x.id in ( select aog1.addressId from AddressOfGroup aog1 where aog1.groupId in (select ag.id ");
                        hql.append("from AddressGroup ag where ag.isDelete = 0  and ag.groupType = ? and "
                                + vo.getDataPrivHql() + "))");
                        params.add(vo.getCreateUserInfo().getCompanyId());
                        params.add(AddressConst.GROUPTYPE_PUBLIC);

                        // 公共默认组成员
                        hql.append(" or (x.id in ( select aog2.addressId from AddressOfGroup aog2 where aog2.groupId = ? ))");
                        params.add(AddressConst.DEFAULT_PUBLIC_GROUP);

                        // 个人默认组
                        hql.append(" or (x.id in (select aog3.addressId from AddressOfGroup aog3 where aog3.groupId = ?) and x.createUserInfo.userId = ? ))");
                        params.add(AddressConst.DEFAULT_GROUP);
                        params.add(vo.getCreateUserInfo().getUserId());
                    }
                }
            }
            
            
            // 性别
            Integer sex = vo.getSex();
            if (null != sex && CbbConst.SEARCH_ALL != sex)
            {
                hql.append(" and x.sex = ?");
                params.add(sex);
            }

            // 生日查询开始时间
            if (null != vo.getStartTimestamp())
            {
                hql.append(" and x.birthday >= ?");
                params.add(vo.getStartTimestamp());
            }

            // 生日查询结束时间
            if (null != vo.getEndTimestamp())
            {
                hql.append(" and x.birthday <= ?");
                params.add(vo.getEndTimestamp());
            }

            // 昵称
            if (!StringUtils.isEmpty(vo.getNickname()))
            {
                hql.append(" and x.nickname like ?");
                params.add("%" + vo.getNickname() + "%");
            }

         

            // 工作电话
            if (!StringUtils.isEmpty(vo.getOfficePhone()))
            {
                hql.append(" and x.officePhone like ?");
                params.add("%" + vo.getOfficePhone() + "%");
            }

            // 单位地址
            if (!StringUtils.isEmpty(vo.getCompanyAddress()))
            {
                hql.append(" and x.companyAddress like ?");
                params.add("%" + vo.getCompanyAddress() + "%");
            }

            // 家庭电话
            if (!StringUtils.isEmpty(vo.getFamilyPhoneNo()))
            {
                hql.append(" and x.familyPhoneNo like ?");
                params.add("%" + vo.getFamilyPhoneNo() + "%");
            }

            // 家庭住址
            if (!StringUtils.isEmpty(vo.getFamilyAddress()))
            {
                hql.append(" and x.familyAddress like ?");
                params.add("%" + vo.getFamilyAddress() + "%");
            }

         

            // 备注
            if (!StringUtils.isEmpty(vo.getRemark()))
            {
                hql.append(" and x.remark like ?");
                params.add("%" + vo.getRemark() + "%");
            }

            // 获取共享组时,需要添加共享信息
            if (!StringUtils.isEmpty(vo.getType()) && "share".equals(vo.getType()))
            {
                hql.append(" and x.shareToUserIds like ?");
                params.add("%," + vo.getCreateUserInfo().getUserId() + ",%");

                // 同时判断是否在共享区间内
                Timestamp now = new Timestamp(System.currentTimeMillis());
                hql.append(" and (x.startShareTime is null or x.startShareTime <= ?)");
                params.add(now);

                // 结束时间
                hql.append(" and (x.endShareTime is null or x.endShareTime >= ?)");
                params.add(now);
            }
            // else
            // {
            // hql.append(" and x.createUserInfo.userId = ?");
            // params.add(vo.getCreateUserInfo().getUserId());
            // }

            // 排序方式
            /*if (!StringUtils.isEmpty(vo.getSortFiled()) && !StringUtils.isEmpty(vo.getSortType()))
            {
                hql.append(" order by x." + vo.getSortFiled() + " " + vo.getSortType());
            }*/

            if (!params.isEmpty())
            {
            	// 排序方式
                if (!StringUtils.isEmpty(vo.getSortFiled()) && !StringUtils.isEmpty(vo.getSortType()))
                {
                    return super.findAll(hql.toString(),new Sort(vo.getSortFiled()), params.toArray());
                }else {
                	return super.findAll(hql.toString(), params.toArray());
				}
            }
        }
        return super.findAll(hql.toString());
    }

    /**
     * 根据联系人Id获取联系人信息
     * @param addressId 联系人Id
     * @return Address
     */
    @Transactional(readOnly=true)
    public Address findById(Integer addressId)
    {
        return (Address) super.findOne(" id = ? and isDelete = "
                + CbbConst.NOT_DELETE, addressId);
    }

    /**
     * 获取共享组联系人列表
     * @param groupId 组ID
     * @param userId 用户ID
     * @return List<Address>
     */
    @SuppressWarnings("unchecked")
    public List<Address> findShareAddress(Integer groupId, Integer userId)
    {
        StringBuffer hql = new StringBuffer();
        hql.append(" id in (select aog.addressId from AddressOfGroup aog where aog.groupId = ? ) and isDelete = ? and shareToUserIds like ? ");

        // 添加共享时间限制
        Timestamp now = new Timestamp(new Date().getTime());
        hql.append(" and (startShareTime is null or startShareTime <= ?)");
        hql.append(" and (endShareTime is null or endShareTime >= ?)");
        return super.findAll(hql.toString(), groupId, CbbConst.NOT_DELETE, "%" + userId + "%", now,
                now);
    }

    /**
     * 根据Id删除联系人
     * @param groupId 群组Id
     */
    public void deleteById(Integer addressId)
    {
        super.bulkUpdate("update Address set isDelete = ?  where isDelete = ?  and id = ?",
                CbbConst.DELETED, CbbConst.NOT_DELETE, addressId);
    }

    /**
     * 功能：分页获取公共通讯簿组下联系人信息
     * @param page 分页数据
     * @param vo 查询条件
     * @return 查询结果
     */
    public Page<Address> findPublicAddressByPage(Pageable page, AddressVo vo)
    {
        StringBuilder hql = new StringBuilder();
        hql.append(" x.isDelete = " + CbbConst.NOT_DELETE);
        if (null != vo)
        {
            List<Object> params = new ArrayList<Object>();

            // 组Id
            Integer groupId = vo.getAddressGroupId();
            if (null != groupId)
            {
                hql.append(" and x.id in (select aog.addressId from AddressOfGroup aog where aog.groupId = ?)");
                params.add(groupId);
            }
            //搜索关键字
            String searchWord = vo.getName();
            if(searchWord!=null&&!"".equals(searchWord)){
            	hql.append(" and ( x.name like '%"+searchWord+"%' or x.familyMobileNo like '%"+searchWord+"%' ) ");
            }
            
            int sex = vo.getSex();
            if(vo.getSex()!=null && sex!=-1){
            	hql.append(" and x.sex = "+sex+" ");
            }
            
            if (!params.isEmpty())
            {
                return super.findAll( hql.toString(),page, params.toArray());
            }
        }
        return super.findAll(hql.toString(),page);
    }

    public List<Address> findSelfAllByGroupIds(String groupIds, UserInfo adminUser)
    {
        StringBuilder hql = new StringBuilder();
        hql.append(" x.isDelete = " + CbbConst.NOT_DELETE);
        hql.append(" and x.addressGroupId in (" + groupIds + ")");
        hql.append(" and x.createUserInfo.userId = " + adminUser.getUserId());
        return super.findAll(hql.toString(),new Sort(Direction.ASC, "x.orderNo"));
    }

    public List<Address> findShareAllByGroupIds(String groupIds, UserInfo adminUser)
    {
        StringBuffer hql = new StringBuffer();
        hql.append(" id in (select aog.addressId from AddressOfGroup aog where aog.groupId in ("
                + groupIds + ") ) and isDelete = ? and shareToUserIds like ? ");

        // 添加共享时间限制
        Timestamp now = new Timestamp(new Date().getTime());
        hql.append(" and (startShareTime is null or startShareTime <= ?)");
        hql.append(" and (endShareTime is null or endShareTime >= ?)");
        return super.findAll(hql.toString(),new Sort(Direction.ASC, "orderNo"), CbbConst.NOT_DELETE, "%" + adminUser.getUserId() + "%",
                now, now);
    }

    public List<Address> findPublicAllByGroupIds(String groupIds, UserInfo adminUser)
    {
        StringBuilder hql = new StringBuilder();
        hql.append(" x.isDelete = " + CbbConst.NOT_DELETE);
        hql.append(" and x.id in (select aog.addressId from AddressOfGroup aog where aog.groupId in ("
                + groupIds + "))");
        return super.findAll(hql.toString());

    }
    
    /**
     * 功能：根据联系人姓名查询联系人
     * @param addressName 联系人姓名
     * @param userId 当前登录Id
     * @return List<Address>
     */
    public List<Address> findAddressByName(String addressName, UserInfo adminUser, String privHql){
        StringBuilder hql = new StringBuilder();
        List<Object> params = new ArrayList<Object>();
        hql.append(" x.isDelete = " + CbbConst.NOT_DELETE);
        
        if (!StringUtils.isEmpty(addressName)){
            hql.append(" and x.name like ? ");
            params.add("%" + addressName + "%");
        }
        // 查询个人所有通讯簿
        hql.append(" and (x.id in (select aog.addressId from AddressOfGroup aog where (aog.groupId in (select ag1.id ");
        hql.append("from AddressGroup ag1 where ag1.isDelete = 0 and ag1.createUserInfo.userId = ?  and ag1.groupType = ?))) ");
        params.add(adminUser.getUserId());
        params.add(AddressConst.GROUPTYPE_PERSONAL);

        // 查询共享通讯簿
        hql.append(" or x.id in (select s.id from Address s where s.isDelete = ? and s.shareToUserIds like ? ");
        params.add(CbbConst.NOT_DELETE);
        params.add(","+adminUser.getUserId()+",");
        Timestamp now = new Timestamp(new Date().getTime());
        hql.append(" and (startShareTime is null or startShareTime <= ?)");
        hql.append(" and (endShareTime is null or endShareTime >= ?))");
        params.add(now);
        params.add(now);
        
        
        // 有开放权限的公共通讯簿组
        hql.append(" or x.id in ( select aog1.addressId from AddressOfGroup aog1 where aog1.groupId in (select ag.id ");
        hql.append("from AddressGroup ag where ag.isDelete = 0 and ag.groupType = ? and "
                + privHql + "))");
        params.add(adminUser.getCompanyId());
        params.add(AddressConst.GROUPTYPE_PUBLIC);

        // 公共默认组成员
        hql.append(" or (x.id in ( select aog2.addressId from AddressOfGroup aog2 where aog2.groupId = ? ))");
        params.add(AddressConst.DEFAULT_PUBLIC_GROUP);

        // 个人默认组
        hql.append(" or (x.id in (select aog3.addressId from AddressOfGroup aog3 where aog3.groupId = ?) and x.createUserInfo.userId = ? ))");
        params.add(AddressConst.DEFAULT_GROUP);
        params.add(adminUser.getUserId());
        
        return super.findAll(hql.toString(), params.toArray());
    }
    
    /**
     * 
     * 功能：根据姓名过滤个人通讯簿数据
     * @param addressName 过滤字段
     * @param adminUser 当前登录人
     * @return 结果List
     */
    public List<Address> findOwnAddressByName(String addressName, UserInfo adminUser){
        StringBuilder hql = new StringBuilder();
        List<Object> params = new ArrayList<Object>();
        hql.append(" x.isDelete = " + CbbConst.NOT_DELETE);
        
        if (!StringUtils.isEmpty(addressName)){
            hql.append(" and (x.name like ? or x.familyMobileNo like ? or x.firstLetter like ?)");
            params.add("%" + addressName + "%");
            params.add("%" + addressName + "%");
            params.add("%" + addressName + "%");
        }
        // 查询个人所有通讯簿
        hql.append(" and (x.id in (select aog.addressId from AddressOfGroup aog where (aog.groupId in (select ag1.id ");
        hql.append("from AddressGroup ag1 where ag1.isDelete = 0 and ag1.createUserInfo.userId = ?  and ag1.groupType = ?)))");
        params.add(adminUser.getUserId());
        params.add(AddressConst.GROUPTYPE_PERSONAL);

        // 个人默认组
        hql.append(" or (x.id in (select aog3.addressId from AddressOfGroup aog3 where aog3.groupId = ?) and x.createUserInfo.userId = ? )))");
        params.add(AddressConst.DEFAULT_GROUP);
        params.add(adminUser.getUserId());
        
        return super.findAll(hql.toString(), params.toArray());
    }
    
    public void updateGroupName(Integer groupId, String groupName){
        super.bulkUpdate(
                "update Address set groupName = ?  where addressGroupId = ?",
                groupName, groupId);
    }
    
    /**
     * 功能：根据姓名和电话查询联系人信息
     * @param name 姓名
     * @param phone 手机号
     * @return 联系人
     */
    public Address getAddressByParams(String name, String phone){
        String hql = " x.isDelete = ? and x.name = ? and x.familyMobileNo = ?";
        return (Address)super.findOne(hql, CbbConst.NOT_DELETE, name, phone);
    }
    
    /**
     * 功能：根据vo查询外部通讯录的人员信息
     * @param vo
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<Address> getPublicAddress(AddressVo vo){

        StringBuilder hql = new StringBuilder();
        hql.append(" x.isDelete = " + CbbConst.NOT_DELETE);
        if (null != vo)
        {
            List<Object> params = new ArrayList<Object>();

            // 组Id
            Integer groupId = vo.getAddressGroupId();
            if (null != groupId)
            {
                hql.append(" and x.id in (select aog.addressId from AddressOfGroup aog where aog.groupId = ?)");
                params.add(groupId);
            }
            //搜索关键字
            String searchWord = vo.getName();
            if(searchWord!=null&&!"".equals(searchWord)){
            	hql.append(" and ( x.name like '%"+searchWord+"%' or x.familyMobileNo like '%"+searchWord+"%' ) ");
            }
            
            int sex = vo.getSex();
            if(vo.getSex()!=null && sex!=-1){
            	hql.append(" and x.sex = "+sex+" ");
            }
            
            if (!params.isEmpty())
            {
                return super.findAll( hql.toString(), params.toArray());
            }
        }
        return super.findAll(hql.toString());
    
    }
}
