package cn.com.qytx.oa.publicaddress.impl;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.qytx.oa.dataPriv.domain.DataPriv;
import cn.com.qytx.oa.dataPriv.service.IDataPriv;
import cn.com.qytx.oa.publicaddress.dao.AddressDao;
import cn.com.qytx.oa.publicaddress.dao.AddressGroupDao;
import cn.com.qytx.oa.publicaddress.dao.AddressOfGroupDao;
import cn.com.qytx.oa.publicaddress.domain.AddressConst;
import cn.com.qytx.oa.publicaddress.domain.AddressGroup;
import cn.com.qytx.oa.publicaddress.service.IAddressGroup;
import cn.com.qytx.platform.base.service.impl.BaseServiceImpl;
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.utils.CbbConst;

/**
 * 功能:通讯簿联系人组IMPL
 * 版本: 1.0
 * 开发人员: 李华伟
 * 创建日期: 2013-3-22
 * 修改日期: 2013-3-22
 * 修改列表:
 */
@Service
@Transactional
public class AddressGroupImpl extends BaseServiceImpl<AddressGroup> implements IAddressGroup
{

    /**
     * 通讯薄联系人组Dao
     */
	@Autowired
    AddressGroupDao addressGroupDao;

    /**
     * 通讯薄联系人组中间表Dao
     */
	@Autowired
    AddressOfGroupDao addressOfGroupDao;

    /**
     * 通讯薄联系人Dao
     */
	@Autowired
    AddressDao addressDao;

    /**
     * 权限设置impl
     */
	@Autowired
    IDataPriv dataPrivImpl;

    /**
     * 根据用户ID获取此用户的联系人组信息
     * @param user 用户
     * @return List<AddressGroup>
     */
    public List<AddressGroup> findAllByUser(UserInfo user)
    {
        return addressGroupDao.findByUser(user);
    }

    /**
     * 根据联系人组组名查询联系人组
     * @param user 用户
     * @param groupName 组名
     * @param groupType 组类型
     * @return List<AddressGroup>
     */
    public List<AddressGroup> findAllByName(UserInfo user, String groupName, Integer groupType)
    {
        return addressGroupDao.findAllByName(user, groupName, groupType);
    }



    /**
     * 保存或更新联系人分组信息
     * @param addressGroup 联系人分组实体类
     */
    public AddressGroup saveOrUpdate(AddressGroup addressGroup)
    {
    	AddressGroup result = addressGroupDao.saveOrUpdate(addressGroup);

        // 如果为公共通讯簿,需要同时更新权限表
        if (AddressConst.GROUPTYPE_PUBLIC == addressGroup.getGroupType())
        {
            // 设置或者更新权限数据
            DataPriv dataPrivVisit = dataPrivImpl.getDataPrivByRefId(addressGroup.getId(),
                    AddressConst.PRIVDATA_MODULE_ADDRESS);
            if (dataPrivVisit != null)
            {
                dataPrivVisit.setLastUpdateTime(new Timestamp(Calendar.getInstance()
                        .getTimeInMillis()));
            }
            else
            {
                dataPrivVisit = new DataPriv();
                dataPrivVisit.setRefId(addressGroup.getId());
                dataPrivVisit.setModuleName(AddressConst.PRIVDATA_MODULE_ADDRESS);
                dataPrivVisit.setCreateTime(addressGroup.getCreateTime());
                dataPrivVisit.setCreateUserId(addressGroup.getCreateUserInfo().getUserId());
                dataPrivVisit.setIsDelete(CbbConst.NOT_DELETE);
                dataPrivVisit.setLastUpdateTime(new Timestamp(Calendar.getInstance()
                        .getTimeInMillis()));
                dataPrivVisit.setLastUpdateUserId(addressGroup.getCreateUserInfo().getUserId());
                dataPrivVisit.setCompanyId(addressGroup.getCreateUserInfo().getCompanyId());

            }
            dataPrivVisit.setUserIds(addressGroup.getUserIds());
            dataPrivVisit.setUserNames(addressGroup.getUserNames());
            dataPrivVisit.setGroupIds(addressGroup.getGroupIds());
            dataPrivVisit.setGroupNames(addressGroup.getGroupNames());
            dataPrivVisit.setRoleIds(addressGroup.getRoleIds());
            dataPrivVisit.setRoleNames(addressGroup.getRoleNames());
            dataPrivImpl.saveOrUpdate(dataPrivVisit);
        }
        
        // 同时更新人员表中对应的名称
        addressDao.updateGroupName(addressGroup.getId(), addressGroup.getName());
        return result;
    }


    /**
     * 删除或者清空联系人群组表
     * @param vid 组ID
     * @param deleteType 删除类型1表示删除2表示清空
     * @param compyId 公司ID
     */
    public void deleteAddressGroup(Integer vid, Integer deleteType)
    {
        // 获取通讯簿组
        
        // 如果为删除,则同时删除该组
        if (deleteType.intValue() == AddressConst.DELETE_TYPE)
        {
            addressGroupDao.deleteById(vid);

            // 将该组的人员变更为默认组
//            if (null != addressGroup.getGroupType() && AddressConst.GROUPTYPE_PUBLIC == addressGroup.getGroupType())
//            {
//                addressOfGroupDao.updateGroupId(vid, AddressConst.DEFAULT_PUBLIC_GROUP);
//            }
//            else
//            {
//                addressOfGroupDao.updateGroupId(vid, AddressConst.DEFAULT_GROUP);
//            }
            
        }
        else
        {
            // 清空该组的人员信息
            addressDao.deleteByGroupId(vid);
        }
    }

    /**
     * 根据用户获取此用户的共享组信息
     * @param user 用户
     * @return List<AddressGroup>
     */
    public List<AddressGroup> findShareByUser(UserInfo user)
    {
        return addressGroupDao.findShareByUser(user);
    }

    /**
     * 根据用户获取此用户的公共通讯簿信息
     * @param user 用户
     * @return List<AddressGroup>
     */
    public List<AddressGroup> findPublicByUser(UserInfo user, String dataPrivHql)
    {
        return addressGroupDao.findPublicByUser(user, dataPrivHql);
    }
    
    /**
     * 功能：更新通讯薄组信息
     * @param addressGroup addressGroup
     */
    public void update(AddressGroup addressGroup)
    {
        addressGroupDao.saveOrUpdate(addressGroup);
    }

	public AddressGroup findById(Integer vid) {
		return addressGroupDao.findById(vid);
	}
}
