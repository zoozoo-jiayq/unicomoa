package cn.com.qytx.oa.publicaddress.action;

import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import cn.com.qytx.oa.dataPriv.domain.DataPriv;
import cn.com.qytx.oa.dataPriv.service.IDataPriv;
import cn.com.qytx.oa.publicaddress.domain.AddressConst;
import cn.com.qytx.oa.publicaddress.domain.AddressGroup;
import cn.com.qytx.oa.publicaddress.service.IAddressGroup;
import cn.com.qytx.oa.util.StringUtil;
import cn.com.qytx.platform.base.action.BaseActionSupport;
import cn.com.qytx.platform.org.domain.GroupInfo;
import cn.com.qytx.platform.org.domain.RoleInfo;
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.org.service.IGroup;
import cn.com.qytx.platform.org.service.IGroupUser;
import cn.com.qytx.platform.org.service.IRole;
import cn.com.qytx.platform.org.service.IUser;
import cn.com.qytx.platform.utils.CbbConst;

import com.google.gson.Gson;

/**
 * 功能:通讯薄联系人组action
 * 版本: 1.0
 * 开发人员: 李华伟
 * 创建日期: 2013-3-22
 * 修改日期: 2013-3-22
 * 修改列表:
 */
public class AddressGroupAction extends BaseActionSupport
{

    /**
     * 序列号
     */
    private static final long serialVersionUID = 3424341211365641475L;

    /**
     * 日志类
     */
    private static final Logger LOGGER = Logger.getLogger(AddressGroupAction.class);

    /**
     * 通讯薄联系人组service
     */
    @Autowired
    IAddressGroup addressGroupImpl;

    /**
     * 权限设置impl
     */
    @Autowired
    IDataPriv dataPrivImpl;

    /**
     * 组用户信息
     */
    @Autowired
    IGroupUser groupUserService;

    /**
     * 角色
     */
    @Autowired
    IRole roleService;

    @Autowired
    IGroup groupService;
    
    @Autowired
    IUser userService;
    /**
     * 联系人组信息
     */
    private AddressGroup addressGroup;

    /**
     * 联系人群组Id
     */
    private Integer id;

    /**
     * 删除联系人组类型 1表示删除 2表示清空
     */
    private Integer deleteType;

    /**
     * 是否跳转到维护权限页面 0表示非 1表示跳转
     */
    private Integer maintain;

    /**
     * 通讯簿类型1个人2公共
     */
    private Integer groupType;

    /**
     * 是否需要修改或者新建
     */
    private Integer updateSign;

    /**
     * 获取联系人组信息
     */
    public void getAddressGroupList()
    {
        PrintWriter out = null;
        try
        {
            out = this.getResponse().getWriter();
            // 首先获取登录人基本信息
            UserInfo user = (UserInfo) this.getSession().getAttribute("adminUser");
            List<AddressGroup> list = addressGroupImpl.findAllByUser(user);
            // 把群组填充到map里面
            List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
            // // 添加默认组
            // Map<String, Object> mapDefault = new HashMap<String, Object>();
            // mapDefault.put("id", AddressConst.DEFAULT_GROUP);
            // mapDefault.put("name", AddressConst.DEFAULT_GROUP_NAME);
            // mapList.add(mapDefault);
            if (list != null)
            {
                for (AddressGroup tempAddressGroup : list)
                {
                    Map<String, Object> map = new HashMap<String, Object>();

                    // 组ID
                    Integer id = tempAddressGroup.getId();
                    map.put("id", id);

                    // 组名
                    String name = tempAddressGroup.getName();
                    map.put("name", name);

                    // 包含人数
                    Integer containAddress = tempAddressGroup.getContainAddress();
                    map.put("containAddress", containAddress);
                    mapList.add(map);
                }
            }
            Map<String, Object> jsonMap = new HashMap<String, Object>();
            jsonMap.put("aaData", mapList);
            Gson json = new Gson();
            String jsons = json.toJson(jsonMap);
            out.print(jsons);
        }
        catch (Exception e)
        {
            LOGGER.error("getAddressGroupList error. ", e);
        }
        finally
        {
            if (null != out)
            {
                out.close();
            }
        }
    }

    /**
     * 添加联系人分组信息
     */
    public void addAddressGroup()
    {
        PrintWriter out = null;
        try
        {
            // 初始化输出流
            out = this.getResponse().getWriter();

            // 基本参数校验
            if (!addAddressGroupCheck(addressGroup))
            {
                out.print("param error");
                return;
            }

            // 新增
            if (addressGroup.getId() == null)
            {
                UserInfo user = (UserInfo) this.getSession().getAttribute("adminUser");

                // 逻辑校验 组名不能重复
                List<AddressGroup> list = addressGroupImpl.findAllByName(user,
                        addressGroup.getName(), addressGroup.getGroupType());
                if (null != list && !list.isEmpty())
                {
                    LOGGER.error("addAddressGroup error. " + addressGroup.getName()
                            + "already exists.");
                    out.print("nameExist");
                    return;
                }

                // 保存或者更新实体类信息
                addressGroup.setIsDelete(CbbConst.NOT_DELETE);
                addressGroup.setCreateUserInfo(user);
                addressGroup.setCreateTime(new Timestamp(new Date().getTime()));
                addressGroup.setLastUpdateTime(new Timestamp(new Date().getTime()));
                addressGroup.setLastUpdateUserId(user.getUserId());
                addressGroup.setCompanyId(user.getCompanyId());
                addressGroupImpl.saveOrUpdate(addressGroup);
                out.print("success");
            }
            else
            {
                // 修改
                // 基本参数校验
                if (!updateAddressGroupCheck(addressGroup))
                {
                    out.print("param error");
                    return;
                }
                UserInfo user = (UserInfo) this.getSession().getAttribute("adminUser");

                // 逻辑校验 组名不能重复
                List<AddressGroup> list = addressGroupImpl.findAllByName(user,
                        addressGroup.getName(), addressGroup.getGroupType());
                if (null != list && !list.isEmpty()
                        && list.get(0).getId().intValue() != addressGroup.getId())
                {
                    LOGGER.error("updateAddressGroup error. " + addressGroup.getName()
                            + "already exists.");
                    out.print("nameExist");
                    return;
                }

                // 获取数据库记录信息
                AddressGroup ag = addressGroupImpl.findById(addressGroup.getId());
                if (null == ag)
                {
                    LOGGER.info("updateAddressGroup is null. id = " + id);
                    out.print("deleted");
                }
                else
                {
                    // 更新实体类信息
                    ag.setLastUpdateTime(new Timestamp(new Date().getTime()));
                    ag.setLastUpdateUserId(user.getUserId());
                    ag.setOrderNo(addressGroup.getOrderNo());
                    ag.setName(addressGroup.getName());
                    ag.setMaintainUserIds(addressGroup.getMaintainUserIds());
                    ag.setMaintainUserNames(addressGroup.getMaintainUserNames());
                    // 更新权限信息
                    if (addressGroup.getGroupType() == AddressConst.GROUPTYPE_PUBLIC)
                    {
                        ag.setUserIds(addressGroup.getUserIds());
                        ag.setUserNames(addressGroup.getUserNames());
                        ag.setGroupIds(addressGroup.getGroupIds());
                        ag.setGroupNames(addressGroup.getGroupNames());
                        ag.setRoleIds(addressGroup.getRoleIds());
                        ag.setRoleNames(addressGroup.getRoleNames());
                    }
                    addressGroupImpl.saveOrUpdate(ag);
                    out.print("success");
                }
            }

        }
        catch (Exception e)
        {
            LOGGER.error("addAddressGroup error. ", e);
        }
        finally
        {
            if (null != out)
            {
                out.close();
            }
        }
    }

    /**
     * 添加分组信息参数校验
     * @return
     */
    private boolean addAddressGroupCheck(AddressGroup addressGroup)
    {
        boolean result = true;
        // 入参校验
        if (null == addressGroup)
        {
            LOGGER.error("addAddressGroup error. addressGroup is null");
            result = false;
        }
        // 组名不能为空
        else if (StringUtils.isEmpty(addressGroup.getName()))
        {
            LOGGER.error("addAddressGroup error. addressGroup name is null");
            result = false;
        }
        return result;
    }

    /**
     * 查询群组详细信息
     * @return String对应页面信息
     */
    public String getDetailAddressGroup()
    {
        // 入参校验
        if (null == id)
        {
            LOGGER.error("getDetailAddressGroup error. id is null");
        }
        AddressGroup ag = addressGroupImpl.findById(id);
        if (null == ag)
        {
            LOGGER.info("AddressGroup is null. id = " + id);
            return "error";
        }
        
        String maintainUserIds = ag.getMaintainUserIds();
        if(StringUtils.isNotEmpty(maintainUserIds)){
        	List<UserInfo> listMaintainUser = userService.findUsersByIds(maintainUserIds);
        	maintainUserIds = "";
        	String maintainUserNames = "";
        	if(listMaintainUser!=null && listMaintainUser.size()>0){
        		for(UserInfo mUser : listMaintainUser){
        			maintainUserIds+=mUser.getUserId()+",";
        			maintainUserNames+=mUser.getUserName()+",";
        		}
        	}
        	ag.setMaintainUserIds(maintainUserIds);
        	ag.setMaintainUserNames(maintainUserNames);
        }
        
        this.getRequest().setAttribute("addressGroup", ag);
        if (ag.getGroupType() == AddressConst.GROUPTYPE_PERSONAL)
        {
            // 跳转到个人通讯簿修改组页面
            return "update";
        }
        else
        {
            // 跳转到维护权限页面
            if (null != maintain && 1 == maintain)
            {
                return "maintain";
            }

            // 跳转到公共通讯簿修改组页面
            DataPriv dp = dataPrivImpl.getDataPrivByRefId(ag.getId(),
                    AddressConst.PRIVDATA_MODULE_ADDRESS);
            if(dp!=null){
            	String userIds = dp.getUserIds();
            	String userNames = "";
            	if(StringUtils.isNotEmpty(userIds)){
            		List<UserInfo> listUser = userService.findUsersByIds(userIds);
            		userIds = "";
            		if(listUser!=null && listUser.size()>0){
            			for(UserInfo user:listUser){
            				userIds += user.getUserId()+",";
            				userNames += user.getUserName()+",";
            			}
            		}
            		dp.setUserIds(userIds);
            		dp.setUserNames(userNames);
            	}
            }
            
            this.getRequest().setAttribute("dataPriv", dp);
            return "update_public";
        }
    }

    /**
     * 更新联系人组信息
     */
    public void updateAddressGroup()
    {
        PrintWriter out = null;
        try
        {
            // 初始化输出流
            out = this.getResponse().getWriter();

            // 基本参数校验
            if (!updateAddressGroupCheck(addressGroup))
            {
                out.print("param error");
                return;
            }
            UserInfo user = (UserInfo) this.getSession().getAttribute("adminUser");

            // 逻辑校验 组名不能重复
            List<AddressGroup> list = addressGroupImpl.findAllByName(user, addressGroup.getName(),
                    addressGroup.getGroupType());
            if (null != list && !list.isEmpty()
                    && list.get(0).getId().intValue() != addressGroup.getId())
            {
                LOGGER.error("updateAddressGroup error. " + addressGroup.getName()
                        + "already exists.");
                out.print("nameExist");
                return;
            }

            // 获取数据库记录信息
            AddressGroup ag = addressGroupImpl.findById(addressGroup.getId());
            if (null == ag)
            {
                LOGGER.info("updateAddressGroup is null. id = " + id);
                out.print("deleted");
            }
            else
            {
                // 更新实体类信息
                ag.setLastUpdateTime(new Timestamp(new Date().getTime()));
                ag.setLastUpdateUserId(user.getUserId());
                ag.setOrderNo(addressGroup.getOrderNo());
                ag.setName(addressGroup.getName());

                // 更新权限信息
                if (addressGroup.getGroupType() == AddressConst.GROUPTYPE_PUBLIC)
                {
                    ag.setUserIds(addressGroup.getUserIds());
                    ag.setUserNames(addressGroup.getUserNames());
                    ag.setGroupIds(addressGroup.getGroupIds());
                    ag.setGroupNames(addressGroup.getGroupNames());
                    ag.setRoleIds(addressGroup.getRoleIds());
                    ag.setRoleNames(addressGroup.getRoleNames());
                }
                addressGroupImpl.saveOrUpdate(ag);
                out.print("success");
            }
        }
        catch (Exception e)
        {
            LOGGER.error("addAddressGroup error. ", e);
        }
        finally
        {
            if (null != out)
            {
                out.close();
            }
        }
    }

    /**
     * 更新分组信息参数校验
     * @return
     */
    private boolean updateAddressGroupCheck(AddressGroup tempAddressGroup)
    {
        boolean result = true;
        // 入参校验
        if (null == tempAddressGroup)
        {
            LOGGER.error("updateAddressGroupCheck error. addressGroup is null");
            result = false;
        }
        // 主键不能为空
        else if (null == tempAddressGroup.getId())
        {
            LOGGER.error("updateAddressGroupCheck error. addressGroup id is null");
            result = false;
        }
        // 组名不能为空
        else if (StringUtils.isEmpty(tempAddressGroup.getName()))
        {
            LOGGER.error("updateAddressGroupCheck error. addressGroup name is null");
            result = false;
        }
        return result;
    }

    /**
     * 删除或者清空联系人群组
     */
    public void deleteAddressGroup()
    {
        PrintWriter out = null;
        try
        {
            // 初始化输出流
            out = this.getResponse().getWriter();

            // 基本参数校验
            if (null == id || null == deleteType)
            {
                LOGGER.error("deleteAddressGroup error. param is null");
                return;
            }
            addressGroupImpl.deleteAddressGroup(id, deleteType);
            out.print("success");
        }
        catch (Exception e)
        {
            LOGGER.error("addAddressGroup error. ", e);
        }
        finally
        {
            if (null != out)
            {
                out.close();
            }
        }
    }

    /**
     * 获取共享联系人组信息
     */
    public void getShareGroupList()
    {
        PrintWriter out = null;
        try
        {
            out = this.getResponse().getWriter();
            // 首先获取登录人基本信息
            UserInfo user = (UserInfo) this.getSession().getAttribute("adminUser");
            List<AddressGroup> list = addressGroupImpl.findShareByUser(user);
            // 把订单信息填充到map里面
            List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
            // 添加默认组
            Map<String, Object> mapDefault = new HashMap<String, Object>();
            mapDefault.put("id", AddressConst.DEFAULT_GROUP);
            mapDefault.put("name", AddressConst.DEFAULT_GROUP_NAME);
            mapList.add(mapDefault);
            if (list != null)
            {
                for (AddressGroup tempAddressGroup : list)
                {
                    Map<String, Object> map = new HashMap<String, Object>();

                    // 组ID
                    Integer id = tempAddressGroup.getId();
                    map.put("id", id);

                    // 组名
                    String name = tempAddressGroup.getName();
                    map.put("name", name);
                    mapList.add(map);
                }
            }
            Map<String, Object> jsonMap = new HashMap<String, Object>();
            jsonMap.put("aaData", mapList);
            Gson json = new Gson();
            String jsons = json.toJson(jsonMap);
            out.print(jsons);
        }
        catch (Exception e)
        {
            LOGGER.error("getAddressGroupList error. ", e);
        }
        finally
        {
            if (null != out)
            {
                out.close();
            }
        }
    }

    /**
     * 获取公共联系人组信息
     */
    @SuppressWarnings("unchecked")
    public void getPublicAddressGroupList()
    {
        PrintWriter out = null;
        try
        {
            out = this.getResponse().getWriter();

            // 首先获取登录人基本信息
            UserInfo user = (UserInfo) this.getSession().getAttribute("adminUser");
            List<AddressGroup> list;
            GroupInfo groupInfoBean = null;
            if (null != maintain && 1 == maintain)
            {
                int uId = user.getUserId();

                // 获取所属组
                groupInfoBean = groupService.findOne(user.getGroupId());

                // 获取所属角色
                List<Integer> listRole = new ArrayList<Integer>();
                List<RoleInfo> roleIdList = roleService.getRoleByUser(uId);
                if (null != roleIdList)
                {
                    for (RoleInfo roleInfo : roleIdList)
                    {
                        listRole.add(roleInfo.getRoleId());
                    }
                }

                String dataPrivHql = dataPrivImpl.getDataPrivHql("id",
                        AddressConst.PRIVDATA_MODULE_ADDRESS, "", uId, groupInfoBean == null ? 0
                                : groupInfoBean.getGroupId(), listRole);

                list = addressGroupImpl.findPublicByUser(user, dataPrivHql);

                // 如果为修改或者新建公共组权限 还需要判断是否有维护权限
                if (null != updateSign && updateSign == 1 && null != list && !list.isEmpty())
                {
                    List<AddressGroup> updateGroup = new ArrayList<AddressGroup>();
                    for (AddressGroup tempAddressGroup : list)
                    {
                        // 维护人员
                        String maintainUserIds = tempAddressGroup.getMaintainUserIds();
                        if (!StringUtils.isEmpty(maintainUserIds)
                                && (maintainUserIds.indexOf("," + user.getUserId() + ",") >= 0||maintainUserIds.indexOf(user.getUserId() + ",") == 0))
                        {
                            updateGroup.add(tempAddressGroup);
                            continue;
                        }

                        // 维护部门
                        String maintainGroupIds = tempAddressGroup.getMaintainGroupIds();
                        if (null != groupInfoBean
                                && null != groupInfoBean.getGroupId()
                                && !StringUtils.isEmpty(maintainGroupIds)
                                && maintainGroupIds.indexOf("," + groupInfoBean.getGroupId() + ",") >= 0)
                        {
                            updateGroup.add(tempAddressGroup);
                        }
                    }
                    list = updateGroup;
                }
            }
            else
            {
                list = addressGroupImpl.findPublicByUser(user, null);
            }

            // 把群组填充到map里面
            List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();

            // 添加默认组
            Map<String, Object> mapDefault = new HashMap<String, Object>();
            mapDefault.put("id", AddressConst.DEFAULT_PUBLIC_GROUP);
            mapDefault.put("name", AddressConst.DEFAULT_GROUP_NAME);
            mapDefault.put("userNames", AddressConst.ALL_USER_NAME);
            mapDefault.put("groupNames", AddressConst.ALL_GROUP_NAME);
            mapDefault.put("roleNames", AddressConst.ALL_ROLE_NAME);
            mapDefault.put("maintainUserNames", AddressConst.ALL_USER_NAME);
            
            List<UserInfo> allUser = userService.findAllUsers(getLoginUser().getCompanyId(), "");
            Map<String, UserInfo> userMap = new HashMap<String, UserInfo>();
            if(allUser!=null && allUser.size()>0){
            	for(UserInfo u:allUser){
            		userMap.put(""+u.getUserId(), u);
            	}
            }
            
            
            // mapList.add(mapDefault);
            if (list != null)
            {
                for (AddressGroup tempAddressGroup : list)
                {
                    Map<String, Object> map = new HashMap<String, Object>();

                    // 组ID
                    Integer id = tempAddressGroup.getId();
                    map.put("id", id);

                    // 组名
                    String name = tempAddressGroup.getName();
                    map.put("name", name);

                    // 是否有维护权限
                    map.put("isMaintainPriv", isMaintainPriv(tempAddressGroup, groupInfoBean, user));

                    DataPriv dp = dataPrivImpl.getDataPrivByRefId(tempAddressGroup.getId(),
                            AddressConst.PRIVDATA_MODULE_ADDRESS);
                    if (null != dp)
                    {
                        // 开放部门
                        map.put("userNames",getUserNamesByIds(userMap,dp.getUserIds()));
                        // 开放角色
                        map.put("groupNames",
                                StringUtils.isEmpty(dp.getGroupNames()) ? "" : dp.getGroupNames());
                        // 开放人员
                        map.put("roleNames",
                                StringUtils.isEmpty(dp.getRoleNames()) ? "" : dp.getRoleNames());
                    }
                    else
                    {
                        // 开放部门
                        map.put("userNames", "");
                        // 开放角色
                        map.put("groupNames", "");
                        // 开放人员
                        map.put("roleNames", "");
                    }

                    // 维护人员
                	map.put("maintainUserNames", getUserNamesByIds(userMap, tempAddressGroup.getMaintainUserIds()));
                    mapList.add(map);
                }
            }
            Map<String, Object> jsonMap = new HashMap<String, Object>();
            jsonMap.put("aaData", mapList);
            Gson json = new Gson();
            String jsons = json.toJson(jsonMap);
            out.print(jsons);
        }
        catch (Exception e)
        {
            LOGGER.error("getAddressGroupList error. ", e);
        }
    }

    /**
     * 功能：根据用户ids获得所有未删除用户的姓名
     * @return
     */
    private String getUserNamesByIds(Map<String, UserInfo> map,String userIds){
    	String userNames = "";
    	if(StringUtils.isNotEmpty(userIds)){
    		String[] idArr = userIds.split(",");
    		for(String id:idArr){
    			UserInfo user = map.get(id);
    			if(user!=null){
    				userNames += user.getUserName()+",";
    			}
    		}
    		if(userNames.endsWith(",")){
    			userNames = userNames.substring(0,userNames.length()-1);
    		}
    	}
    	
    	return userNames;
    }
    
    
    /**
     * 功能：判断公共通讯簿联系人,当前用户是否有维护权限
     * @param tempAddressGroup 组信息
     * @param groupInfoBean 当前用户所属组
     * @param user 当前用户
     * @return 是否有权限
     */
    private boolean isMaintainPriv(AddressGroup tempAddressGroup, GroupInfo groupInfoBean,
            UserInfo user)
    {
        if (null != tempAddressGroup)
        {
            // 用户是否拥有权限
            String maintainUserIds = tempAddressGroup.getMaintainUserIds();
            if (!StringUtil.isEmpty(maintainUserIds) && null != user && null != user.getUserId())
            {
                if (maintainUserIds.indexOf("," + user.getUserId() + ",") >= 0)
                {
                    return true;
                }
            }

            // 用户所在的组是否拥有权限
            String maintainGroupIds = tempAddressGroup.getMaintainGroupIds();
            if (!StringUtil.isEmpty(maintainGroupIds) && null != groupInfoBean
                    && null != groupInfoBean.getGroupId())
            {
                if (maintainGroupIds.indexOf("," + groupInfoBean.getGroupId() + ",") >= 0)
                {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 维护权限
     */
    public void maintainAddressGroup()
    {
        try
        {
            UserInfo user = (UserInfo) this.getSession().getAttribute("adminUser");

            // 获取数据库记录信息
            AddressGroup ag = addressGroupImpl.findById(addressGroup.getId());
            if (null == ag)
            {
                LOGGER.info("updateAddressGroup is null. id = " + id);
                ajax("deleted");
            }
            else
            {
                // 更新实体类信息
                ag.setLastUpdateTime(new Timestamp(new Date().getTime()));
                ag.setLastUpdateUserId(user.getUserId());

                // 更新维护权限信息
                if (null != ag.getGroupType() && ag.getGroupType() == AddressConst.GROUPTYPE_PUBLIC)
                {
                    ag.setMaintainGroupIds(addressGroup.getMaintainGroupIds());
                    ag.setMaintainGroupNames(addressGroup.getMaintainGroupNames());
                    ag.setMaintainUserIds(addressGroup.getMaintainUserIds());
                    ag.setMaintainUserNames(addressGroup.getMaintainUserNames());
                }
                addressGroupImpl.update(ag);
                ajax("success");
            }
        }
        catch (Exception e)
        {
            LOGGER.error("maintainAddressGroup error. ", e);
        }
    }

    /**
     * 获取当前用户维护权限
     */
    public void getMaintainPriv()
    {
        try
        {
            UserInfo user = (UserInfo) this.getSession().getAttribute("adminUser");

            // 获取所属组
            GroupInfo groupInfoBean = groupService.findOne(user.getGroupId());

            // 获取数据库记录信息
            AddressGroup ag = addressGroupImpl.findById(addressGroup.getId());
            if (null == ag)
            {
                LOGGER.info("updateAddressGroup is null. id = " + id);
                ajax("deleted");
            }
            else
            {
                // 维护人员
                String maintainUserIds = ag.getMaintainUserIds();
                if (!StringUtils.isEmpty(maintainUserIds)
                        && (maintainUserIds.indexOf("," + user.getUserId() + ",") >= 0||maintainUserIds.indexOf( user.getUserId() + ",") == 0))
                {
                    ajax("success");
                    return;
                }

                // 维护部门
                String maintainGroupIds = ag.getMaintainGroupIds();
                if (null != groupInfoBean && null != groupInfoBean.getGroupId()
                        && !StringUtil.isEmpty(maintainGroupIds)
                        && maintainGroupIds.indexOf("," + groupInfoBean.getGroupId() + ",") >= 0)
                {
                    ajax("success");
                    return;
                }
                ajax("false");
            }
        }
        catch (Exception e)
        {
            LOGGER.error("maintainAddressGroup error. ", e);
        }
    }

    public AddressGroup getAddressGroup()
    {
        return addressGroup;
    }

    public void setAddressGroup(AddressGroup addressGroup)
    {
        this.addressGroup = addressGroup;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public Integer getDeleteType()
    {
        return deleteType;
    }

    public void setDeleteType(Integer deleteType)
    {
        this.deleteType = deleteType;
    }

    public Integer getMaintain()
    {
        return maintain;
    }

    public void setMaintain(Integer maintain)
    {
        this.maintain = maintain;
    }

    public Integer getGroupType()
    {
        return groupType;
    }

    public void setGroupType(Integer groupType)
    {
        this.groupType = groupType;
    }

    public Integer getUpdateSign()
    {
        return updateSign;
    }

    public void setUpdateSign(Integer updateSign)
    {
        this.updateSign = updateSign;
    }
}
