package cn.com.qytx.oa.publicaddress.service;

import java.io.Serializable;
import java.util.List;

import cn.com.qytx.oa.publicaddress.domain.AddressGroup;
import cn.com.qytx.platform.base.service.BaseService;
import cn.com.qytx.platform.org.domain.UserInfo;

/**
 * 功能:通讯簿联系人组接口
 * 版本: 1.0
 * 开发人员: 李华伟
 * 创建日期: 2013-3-22
 * 修改日期: 2013-3-22
 * 修改列表:
 */
public interface IAddressGroup extends BaseService<AddressGroup>, Serializable
{

    /**
     * 根据用户获取此用户的联系人组信息
     * @param user 用户
     * @return List<AddressGroup>
     */
    public List<AddressGroup> findAllByUser(UserInfo user);

    /**
     * 根据联系人组组名查询联系人组
     * @param user 用户
     * @param groupName 组名
     * @param groupType 组类型
     * @return List<AddressGroup>
     */
    public List<AddressGroup> findAllByName(UserInfo user, String groupName, Integer groupType);

    /**
     * 根据主键查询联系人组信息
     * @param vid 主键
     * @return AddressGroup
     */
    public AddressGroup findById(Integer vid);

    /**
     * 删除或者清空联系人群组表
     * @param vid 组ID
     * @param deleteType 删除类型1表示删除2表示清空
     * @param compyId 公司ID
     */
    public void deleteAddressGroup(Integer vid, Integer deleteType);

    /**
     * 根据用户获取此用户的共享组信息
     * @param user 用户
     * @return List<AddressGroup>
     */
    public List<AddressGroup> findShareByUser(UserInfo user);

    /**
     * 根据用户获取此用户的公共通讯簿信息
     * @param user 用户
     * @return List<AddressGroup>
     */
    public List<AddressGroup> findPublicByUser(UserInfo user, String dataPrivHql);

    /**
     * 功能：更新通讯薄组信息
     * @param addressGroup addressGroup
     */
    public void update(AddressGroup addressGroup);
}
