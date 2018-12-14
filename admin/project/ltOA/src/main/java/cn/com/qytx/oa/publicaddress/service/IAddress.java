package cn.com.qytx.oa.publicaddress.service;

import java.io.Serializable;
import java.util.List;

import cn.com.qytx.oa.publicaddress.domain.Address;
import cn.com.qytx.oa.publicaddress.vo.AddressVo;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.Pageable;
import cn.com.qytx.platform.base.service.BaseService;
import cn.com.qytx.platform.org.domain.UserInfo;

/**
 * 功能:通讯簿联系人service
 * 版本: 1.0
 * 开发人员: 李华伟
 * 创建日期: 2013-3-22
 * 修改日期: 2013-3-22
 * 修改列表:
 */
public interface IAddress extends BaseService<Address>, Serializable
{
    /**
     * 根据群组Id删除组下成员
     * @param groupId 群组Id
     */
    public void deleteByGroupId(Integer groupId);

    /**
     * 根据vo查询联系人信息
     * @param vo AddressVo
     * @return List<Address>
     */
    public List<Address> findAllAddress(AddressVo vo);

    /**
     * 根据联系人Id获取联系人信息
     * @param addressId 联系人Id
     * @return Address
     */
    public Address findById(Integer addressId);

    /**
     * 更新联系人信息
     * @param address address
     * @return 结果信息
     */
    public String updateAddress(Address address);

    /**
     * 获取共享组联系人列表
     * @param groupId 组ID
     * @param userId 用户ID
     * @return List<Address>
     */
    public List<Address> findShareAddress(Integer groupId, Integer userId);

    /**
     * 批量删除联系人信息
     * @param addressIds addressIds
     */
    public void deleteAddresses(Integer[] addressIds);

    /**
     * 功能：根据群组Id,用户Id,和类型获取所有联系人信息
     * @param groupId 以,分割的字符串
     * @param adminUser 用户
     * @param type 类型 1表示个人,2表示共享 3表示公共
     * @return List<Address>
     */
    public List<Address> findAddressByType(String groupIds, UserInfo adminUser, Integer type);

    /**
     * 功能：根据联系人姓名查询联系人
     * @param addressName 联系人姓名
     * @param userId 当前登录Id
     * @return List<Address>
     */
    public List<Address> findAddressByName(String addressName, UserInfo adminUser, String privHql);

    /**
     * 功能：分页获取公共通讯簿组下联系人信息
     * @param page 分页数据
     * @param vo 查询条件
     * @return 查询结果
     */
    public Page<Address> findPublicAddressByPage(Pageable page, AddressVo vo);

    /**
     * 功能：批量保存联系人信息
     * @param addressList 联系人基本信息
     * @param groupName 群组名称
     * @param groupId 群组Id
     * @param userInfo 添加人信息
     */
    public void saveAddressList(List<Address> addressList, String groupName, Integer groupId,
            UserInfo userInfo);

    /**
     * 功能：根据姓名过滤个人通讯簿数据
     * @param addressName 过滤字段
     * @param adminUser 当前登录人
     * @return 结果List
     */
    public List<Address> findOwnAddressByName(String addressName, UserInfo adminUser);

    /**
     * 功能：根据姓名和电话查询联系人信息
     * @param name 姓名
     * @param phone 手机号
     * @return 联系人
     */
    public Address getAddressByParams(String name, String phone);
    /**
     * 保存联系人基本信息
     * @param address address
     */
    public void saveAddress(Address address);
    
    /**
     * 功能：根据vo查询外部通讯录的人员信息
     * @param vo
     * @return
     */
    public List<Address> getPublicAddress(AddressVo vo);
}
