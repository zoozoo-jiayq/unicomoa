package cn.com.qytx.oa.publicaddress.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.qytx.cbb.affairs.domain.AffairsBody;
import cn.com.qytx.cbb.affairs.service.IAffairsBody;
import cn.com.qytx.oa.dataPriv.service.IDataPriv;
import cn.com.qytx.oa.publicaddress.dao.AddressDao;
import cn.com.qytx.oa.publicaddress.dao.AddressGroupDao;
import cn.com.qytx.oa.publicaddress.dao.AddressOfGroupDao;
import cn.com.qytx.oa.publicaddress.domain.Address;
import cn.com.qytx.oa.publicaddress.domain.AddressConst;
import cn.com.qytx.oa.publicaddress.domain.AddressGroup;
import cn.com.qytx.oa.publicaddress.domain.AddressOfGroup;
import cn.com.qytx.oa.publicaddress.service.IAddress;
import cn.com.qytx.oa.publicaddress.vo.AddressVo;
import cn.com.qytx.oa.util.StringUtil;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.Pageable;
import cn.com.qytx.platform.base.service.impl.BaseServiceImpl;
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.org.service.IGroupUser;
import cn.com.qytx.platform.org.service.IRole;
import cn.com.qytx.platform.utils.CbbConst;

/**
 * 功能:通讯薄联系人Impl
 * 版本: 1.0
 * 修改列表:
 */
@Service
@Transactional
public class AddressImpl extends BaseServiceImpl<Address> implements IAddress
{
    /**
     * 通讯薄联系人Dao
     */
	@Autowired
    AddressDao addressDao;

    /**
     * 通讯薄联系人和组关系Dao
     */
	@Autowired
    AddressOfGroupDao addressOfGroupDao;

    /**
     * 通讯薄联系人组Dao
     */
	@Autowired
    AddressGroupDao addressGroupDao;

    /**
     * 事务提醒主体Impl
     */
	@Autowired
    IAffairsBody affairsBodyImpl;

    /**
     * 权限设置impl
     */
	@Autowired
    IDataPriv dataPrivImpl;

    /**
     * 群组人员
     */
	@Autowired
    IGroupUser groupUserService;

    /**
     * 角色
     */
	@Autowired
    IRole roleService;

    /**
     * 保存联系人基本信息
     * @param address address
     */
    public void saveAddress(Address address)
    {
        // 根据联系人组获取组名信息
        if (AddressConst.DEFAULT_GROUP == address.getAddressGroupId() || AddressConst.DEFAULT_PUBLIC_GROUP == address.getAddressGroupId())
        {
            address.setGroupName(AddressConst.DEFAULT_GROUP_NAME);
        }
        else
        {
            if (StringUtil.isEmpty(address.getGroupName()))
            {
                AddressGroup ag = addressGroupDao.findById(address.getAddressGroupId());
                address.setGroupName(ag.getName());
            }
        }

        // 保存联系人基本信息
        addressDao.saveOrUpdate(address);

        // 保存中间表
        AddressOfGroup aog = new AddressOfGroup();
        aog.setGroupId(address.getAddressGroupId());
        aog.setAddressId(address.getId());
        aog.setCompanyId(address.getCompanyId());
        addressOfGroupDao.saveOrUpdate(aog);
    }

    /**
     * 保存或者更新联系人基本信息
     * @param address address
     */
    public Address saveOrUpdate(Address address)
    {
        // 保存联系人基本信息
    	Address result = addressDao.saveOrUpdate(address);

        // 是否向共享人员发送事务提醒
        if (null != address.getAffairsSign() && CbbConst.AFFAIRS_YES == address.getAffairsSign())
        {
            AffairsBody affairsBody = new AffairsBody();
            affairsBody.setCompanyId(address.getCompanyId());
            Timestamp createTime = new Timestamp(new Date().getTime());
            affairsBody.setCreateTime(createTime);
            affairsBody.setCreateUserInfo(address.getCreateUserInfo());
            affairsBody.setIsDelete(CbbConst.NOT_DELETE);
            affairsBody.setFromUserInfo(address.getCreateUserInfo());

            // 设置提醒人员 以,分割
            affairsBody.setToIds(address.getShareToUserIds());

            // 模块名
            affairsBody.setSmsType(CbbConst.AFFAIRS_TYPE_ADDRESS);

            // 提醒内容
            StringBuffer sb = new StringBuffer();
            sb.append(address.getCreateUserInfo().getUserName());
            sb.append("共享了个人通讯簿中");
            sb.append(address.getGroupName());
            sb.append("中的");
            sb.append(address.getName());
            sb.append("的通讯录");
            affairsBody.setContentInfo(sb.toString());
            affairsBody.setSendTime(createTime);

            // 设置提醒对应URL
            try
            {
                String url = "logined/address/address_main.jsp?type=share&groupId="
                        + address.getAddressGroupId() + "&groupName=";
                String groupName = URLEncoder.encode(address.getGroupName(), "UTF-8");
                affairsBody.setRemindUrl(url + groupName);
            }
            catch (UnsupportedEncodingException e)
            {
                e.printStackTrace();
            }
            affairsBodyImpl.saveOrUpdate(affairsBody);
        }
        return result;
    }

    /**
     * 根据群组Id删除组下成员
     * @param groupId 群组Id
     */
    public void deleteByGroupId(Integer groupId)
    {
        addressDao.deleteByGroupId(groupId);
    }

    /**
     * 根据vo查询联系人信息
     * @param vo AddressVo
     * @return List<Address>
     */
    public List<Address> findAllAddress(AddressVo vo)
    {
        return addressDao.findAllAddress(vo);
    }


    /**
     * 更新联系人信息
     * @param address address
     * @return 结果信息
     */
    public String updateAddress(Address address)
    {
        // 查询联系人信息
        Address addressBean = addressDao.findById(address.getId());
        if (null == addressBean)
        {
            return "notexist";
        }

        // 比较联系人群组是否变更
        if (address.getAddressGroupId().intValue() != addressBean.getAddressGroupId())
        {
            // 删除原对应关系并添加新对应关系
            AddressOfGroup aog = addressOfGroupDao.findByGidAndAid(addressBean.getAddressGroupId(),
                    address.getId());
            if (null == aog)
            {
                aog = new AddressOfGroup();
                aog.setGroupId(address.getAddressGroupId());
                aog.setAddressId(address.getId());
                aog.setCompanyId(address.getCompanyId());
            }
            else
            {
            	aog.setCompanyId(address.getCompanyId());
                aog.setGroupId(address.getAddressGroupId());
            }
            addressOfGroupDao.saveOrUpdate(aog);

            // 获取联系人组信息,更新组名信息
            if (AddressConst.DEFAULT_GROUP != address.getAddressGroupId())
            {
                AddressGroup ag = addressGroupDao.findById(address.getAddressGroupId());
                addressBean.setGroupName(null != ag ? ag.getName()
                        : AddressConst.DEFAULT_GROUP_NAME);
            }
            else
            {
                addressBean.setGroupName(AddressConst.DEFAULT_GROUP_NAME);
            }
        }

        // 更新基本信息并保存
        copyAddress(address, addressBean);
        addressDao.saveOrUpdate(addressBean);
        return "success";
    }

    private void copyAddress(Address source, Address target)
    {
        // 联系人所属群组
        target.setAddressGroupId(source.getAddressGroupId());

        // 排序号
        target.setOrderNo(source.getOrderNo());

        // 联系人姓名
        target.setName(source.getName());

        // 联系人首字母
        target.setFirstLetter(source.getFirstLetter());

        // 性别
        target.setSex(source.getSex());

        // 生日
        target.setBirthday(target.getBirthday());

        // 昵称
        target.setNickname(source.getNickname());

        // 职务信息
        target.setPostInfo(source.getPostInfo());

        // 配偶姓名
        target.setWifeName(source.getWifeName());

        // 子女
        target.setChildren(source.getChildren());

        // 照片url
        target.setPhoto(source.getPhoto());

        // 公司名称
        target.setCompanyName(source.getCompanyName());

        // 公司地址
        target.setCompanyAddress(source.getCompanyAddress());

        // 单位邮编
        target.setCompanyPostCode(source.getCompanyPostCode());

        // 工作电话
        target.setOfficePhone(source.getOfficePhone());

        // 工作传真
        target.setOfficeFax(source.getOfficeFax());

        // 家庭住址
        target.setFamilyAddress(source.getFamilyAddress());

        // 家庭邮编
        target.setFamilyPostCode(source.getFamilyPostCode());

        // 家庭电话
        target.setFamilyPhoneNo(source.getFamilyPhoneNo());

        // 家庭手机
        target.setFamilyMobileNo(source.getFamilyMobileNo());

        // 电子邮件
        target.setFamilyEmail(source.getFamilyEmail());

        // 备注
        target.setRemark(source.getRemark());
    }

    /**
     * 获取共享组联系人列表
     * @param groupId 组ID
     * @param userId 用户ID
     * @return List<Address>
     */
    public List<Address> findShareAddress(Integer groupId, Integer userId)
    {
        return addressDao.findShareAddress(groupId, userId);
    }

    /**
     * 批量删除联系人信息
     * @param addressIds addressIds
     */
    public void deleteAddresses(Integer[] addressIds)
    {
        if (null != addressIds)
        {
            for (Integer addressId : addressIds)
            {
                this.delete(addressId,true);
            }
        }
    }

    /**
     * 功能：根据群组Id,用户Id,和类型获取所有联系人信息
     * @param groupId 以,分割的字符串
     * @param userId 用户Id
     * @param type 类型 1表示个人,2表示共享 3表示公共
     * @return List<Address>
     */
    public List<Address> findAddressByType(String groupIds, UserInfo adminUser, Integer type)
    {
        // 个人通讯簿组
        if (type == 1){
            return addressDao.findSelfAllByGroupIds(groupIds, adminUser);
        }else if (type == 2){
            return addressDao.findShareAllByGroupIds(groupIds, adminUser);
        }else if (type == 3){
            return addressDao.findPublicAllByGroupIds(groupIds, adminUser);
        }
        return null;
    }

    /**
     * 功能：根据联系人姓名查询联系人
     * @param addressName 联系人姓名
     * @param userId 当前登录Id
     * @return List<Address>
     */
    public List<Address> findAddressByName(String addressName, UserInfo adminUser, String privHql)
    {
        return addressDao.findAddressByName(addressName, adminUser, privHql) ;
    }

    /**
     * 功能：分页获取公共通讯簿组下联系人信息
     * @param page 分页数据
     * @param vo 查询条件
     * @return 查询结果
     */
    public Page<Address> findPublicAddressByPage(Pageable page, AddressVo vo)
    {
        return addressDao.findPublicAddressByPage(page, vo);
    }
    
    /**
     * 功能：批量保存联系人信息
     * @param addressList 联系人基本信息
     * @param groupName 群组名称
     * @param groupId 群组Id
     * @param userInfo 添加人信息
     */
    public void saveAddressList(List<Address> addressList,String groupName, Integer groupId, UserInfo userInfo)
    {
        if (null != addressList && !addressList.isEmpty())
        {
            // 创建时间
            Timestamp now = new Timestamp(System.currentTimeMillis());
            
            for (Address tempAddress : addressList)
            {
                // 获取已存在的联系人
                Address existBean = getAddressByParams(tempAddress.getName(), tempAddress.getFamilyMobileNo());
                if (null != existBean){
                    // 性别                       手机      备注
                    Integer sex = tempAddress.getSex();
                    if (null != sex)
                    {
                        existBean.setSex(sex);
                    }
                    
                    // 生日
                    Timestamp birthday = tempAddress.getBirthday();
                    if (null != birthday)
                    {
                        existBean.setBirthday(birthday);
                    }
                    
                    // 昵称
                    String nickname = tempAddress.getNickname();
                    if (!StringUtil.isEmpty(nickname))
                    {
                        existBean.setNickname(nickname);
                    }
                    
                    // 职务
                    String postInfo = tempAddress.getPostInfo();
                    if (!StringUtil.isEmpty(postInfo))
                    {
                        existBean.setPostInfo(postInfo);
                    }
                    
                    // 配偶
                    String wifeName = tempAddress.getWifeName();
                    if (!StringUtil.isEmpty(wifeName))
                    {
                        existBean.setWifeName(wifeName);
                    }
                    
                    // 子女
                    String children = tempAddress.getChildren();
                    if (!StringUtil.isEmpty(children))
                    {
                        existBean.setChildren(children);
                    }
                    
                    // 单位名称
                    String companyName = tempAddress.getCompanyName();
                    if (!StringUtil.isEmpty(companyName))
                    {
                        existBean.setCompanyName(companyName);
                    }
                    
                    // 单位地址
                    String companyAddress = tempAddress.getCompanyAddress();
                    if (!StringUtil.isEmpty(companyAddress))
                    {
                        existBean.setCompanyAddress(companyAddress);
                    }
                    
                    // 单位邮编
                    String companyPostCode = tempAddress.getCompanyPostCode();
                    if (!StringUtil.isEmpty(companyPostCode))
                    {
                        existBean.setCompanyPostCode(companyPostCode);
                    }
                    
                    // 工作电话
                    String officePhone = tempAddress.getOfficePhone();
                    if (!StringUtil.isEmpty(officePhone))
                    {
                        existBean.setOfficePhone(officePhone);
                    }
                    
                    // 工作传真
                    String officeFax = tempAddress.getOfficeFax();
                    if (!StringUtil.isEmpty(officeFax))
                    {
                        existBean.setOfficeFax(officeFax);
                    }
                    
                    // 备注
                    String remark = tempAddress.getRemark();
                    if (!StringUtil.isEmpty(remark))
                    {
                        existBean.setRemark(remark);
                    }
                    
                    // 更新公用信息
                    existBean.setCreateTime(now);
                    existBean.setCreateUserInfo(userInfo);
                    existBean.setGroupName(groupName);
                    existBean.setAddressGroupId(groupId);       
                    existBean.setCompanyId(userInfo.getCompanyId());
                    addressDao.saveOrUpdate(existBean);
                    
                    // 同时更新中间表
                    AddressOfGroup aog = new AddressOfGroup();
                    addressOfGroupDao.deleteByAid(existBean.getId());
                    aog.setGroupId(existBean.getAddressGroupId());
                    aog.setAddressId(existBean.getId());
                    addressOfGroupDao.saveOrUpdate(aog);
                }else{
                    tempAddress.setCreateTime(now);
                    tempAddress.setCreateUserInfo(userInfo);
                    tempAddress.setGroupName(groupName);
                    tempAddress.setAddressGroupId(groupId);       
                    tempAddress.setCompanyId(userInfo.getCompanyId());
                    this.saveAddress(tempAddress);
                }
            }
        }
    }
    
    /**
     * 
     * 功能：根据姓名过滤个人通讯簿数据
     * @param addressName 过滤字段
     * @param adminUser 当前登录人
     * @return 结果List
     */
    public List<Address> findOwnAddressByName(String addressName, UserInfo adminUser)
    {
        return addressDao.findOwnAddressByName(addressName, adminUser);
    }
    
    /**
     * 功能：根据姓名和电话查询联系人信息
     * @param name 姓名
     * @param phone 手机号
     * @return 联系人
     */
    public Address getAddressByParams(String name, String phone){
        return addressDao.getAddressByParams(name, phone);
    }

	@Override
	public Address findById(Integer addressId) {
		return addressDao.findById(addressId);
	}
    /**
     * 功能：根据vo查询外部通讯录的人员信息
     * @param vo
     * @return
     */
    public List<Address> getPublicAddress(AddressVo vo){
    	return addressDao.getPublicAddress(vo);
    }
}
