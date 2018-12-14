package cn.com.qytx.platform.org.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.Pageable;
import cn.com.qytx.platform.base.service.BaseService;
import cn.com.qytx.platform.org.dao.UserVo;
import cn.com.qytx.platform.org.domain.GroupInfo;
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.utils.tree.TreeNode;

/**
 * 用户操作接口类
 * <br/>User: 黄普友
 * <br/>Date: 13-2-16
 * <br/>Time: 下午2:48
 */
public interface IUser extends BaseService<UserInfo> {

    /**
     * 根据IDS删除指定的用户
     * @param userIds:待删除的用户列表，用","隔开
     * @param flag:true真删除/false假删除
     * @param companyId:单位ID
     */
    void deleteUserByIds(String userIds,boolean flag,int companyId);
    
    /**根据IDS删除指定的用户，默认为假删除
     * @param companyId 单位ID
     * @param ids 待删除的用户集合，用","隔开
     */
    void deleteUserByIds(Integer companyId,String ids);
    
    /**
     * 根据登录名获取用户
     * @param loginName 登录名
     * @return 用户对象
     * @throws Exception
     */
    UserInfo getUserByUserName(String loginName);


    /**
     * 根据电话号码获取用户
     * @param companyId 企业ID
     * @param phone 用户电话号码
     * @return 用户对象
     * @throws Exception
     */
    UserInfo getUserByPhone(String phone);


    /**
     * 根据条件进行分页查询--按搜索内容查询
     *  @param companyId 企业ID
     * @param page 分页信息
     * @param groupId 部门ID
     * @return 结果集的分页对象
     * @throws Exception
     */
    Page<UserInfo> findByPage(int companyId,final Pageable page, int groupId) throws Exception;
    
    /**
     * 更新上次登录时间
     * @param userId
     */
    void updateLastLoginTime(int userId);

    /**
     * 查找人员
     * @param companyId 企业ID
     * @param searchName  查找人员条件，姓名，电话，手机
     * @return
     */
    public List<UserInfo> findAllUsers(int companyId,String searchName);
	/**
	 *  得到未登录人员
	 * @param companyId
	 * @param searchName
	 * @return
	 */
	public List<UserInfo> findAllNoLoginUsers(int companyId, String searchName);
	/**
	 * @Title: findAllUsers 
	 * @Description: TODO(管理员列表) 
	 * @param userName 用户名
	 * @param phone 电话
	 * @param PY 拼音
	 * @return List<User>   返回类型
	 */
	public List<UserInfo> findAllUsers(int companyId,String userName,String phone,String PY);
    
    
    /**
     * 根据角色 id获取人员列表
     * @param ids 角色 id,多个id之间，隔开
     * @return
     */
    public List<UserInfo> findUsersByRoleId(String ids);
    

    /**
     * 根据部门ID集合查询用户列表
     * @param ids 部门ID集合，以","隔开
     * @return 用户列表
     */
    public List<UserInfo> findUsersByGroupId(String ids);

    /**
     * 根据人员ID，部门ID，角色ID获取人员列表
     * @param companyId 单位ID
     * @param userIds 人员Id，之间用，隔开
     * @param groupIds 部门Id，之间用，隔开
     * @param roleIds 角色Id，之间用，隔开
     * @return
     */
    public List<UserInfo> findUsersByIds(String userIds,String groupIds,String roleIds);
    
    /**
     * 根据人员ID获取人员列表
     * @param companyId 单位ID
     * @param userIds 人员Id，之间用，隔开
     * @return
     */
    public List<UserInfo> findUsersByIds(String userIds);
    /**
     * 根据vo查询联系人信息
     * @param vo UserVo
     * @return List<Address>
     */
    public Page<UserInfo> findAllUsersByPage(UserVo vo,Pageable page,String groupIds);
    
    
    /**
     * 功能：部门层级，部门排序，人员排序，人员拼音 排序
     * @param vo 查询参数
     * @param page 分页对象
     * @param groupIds 部门ID集合，以","隔开
     * @return
     */
    public Page<Map<String, Object>> findByPageAll(UserVo vo,Pageable page,String groupIds);

	/**
	 * 
	 * 功能：更新用户状态
	 * @param ids 用户ID集合，以逗号隔开
	 */
    public void updateStateByIds(String ids);
	/**
	 * 功能：更新用户密码
	 * @param ids 用户id，以","隔开
	 * @param password 新密码
	 */
    public void updatePasswordByIds(String ids,String password);
	/**
	 * 根据电话号码修改用户密码
	 * @param phone 电话号码
	 * @param password 新密码
	 */
	public void updatePasswordByPhone(String phone, String password);
    
    /**
     * 根据登录名查询用户信息
     * @param loginName 登录名
     * @return 用户对象
     */
    public UserInfo findByLoginName(String loginName);

    
    /**
     * 
     * 功能：根据姓名和电话查询人员
     * @param companyId 公司Id
     * @param name 姓名
     * @param phone 电话
     * @return 人员信息
     */
    UserInfo getUserByNamePhone(int companyId,String name, String phone);
    
    /**
     * 
     * 功能：根据姓名和电话查询人员
     * @param companyId 公司Id
     * @param phone 电话
     * @return 人员信息
     */
    UserInfo getUserByNamePhone(int companyId, String phone);
    
    /**
     * 功能：分页查询用户列表
     * @param
     * @return
     * @throws   
     */
    public List<UserInfo> findByPage(Pageable page);
    
    /**
	 * 修改用户皮肤标志
	 * @param id 用户ID
	 * @param skinLogo 皮肤标识ID
	 */
	public void updateUserSkinLogo(int id, int skinLogo);
	
	/**
	 * 功能：查询所有的用户ID,用户姓名映射
	 * @param
	 * @return
	 * @throws   
	 */
	public Map<Integer,String> getAllUserName();
	
	/**
	 * 
	 * 功能：通过角色id得到人员列表
	 * @param roleId 角色ID
	 * @param companyId 单位id
	 * @param type 角色类型
	 * @return
	 */
	public List<UserInfo> findUsersByRoleId(Integer roleId,Integer companyId,Integer type);

    /**
     * 人员排序
     * @param companyId 单位ID
     * @param groupId   部门ID
     * @param userId    人员ID
     * @param startIndex 开始索引
     * @param endIndex   结束索引
     */
    public void sortOrder(Integer companyId,Integer groupId,Integer userId,Integer startIndex,Integer endIndex);
    /**
     * 获取最大排序号
     * @param companyId
     * @param groupId
     * @return 最大排序号
     */
    public Integer getMaxOrderIndex(Integer companyId,Integer groupId);
    public Integer getMaxOrderIndex(Integer companyId);
    
    /**根据角色代码和部门ID查询该部门下的人员ID集合
     * @param roleCode 角色代码
     * @param groupId 部门ID
     * @return 用户列表
     */
    public List<UserInfo> findUsersByRoleCodeAndGroup(String roleCode,int groupId);
    
    /**
     * 功能：根据二级局查找二级局下属的部门-人员树，如果二级局为空则查询所有的部门-人员树，根据forkGroup缓存
     * 作者：jiayongqiang
     * 参数：
     * 输出：
     */
    public List<TreeNode> selectUserByGroup(UserInfo userInfo,GroupInfo forkGroup,String moduleName,int showType,int key,String path,int groupType);
    
    /* (non-Javadoc)
	 * 根据二级局查找二级局下属的部门-人员树，如果二级局为空则查询所有的部门-人员树
	 * 有缓存的实现，
	 * @see cn.com.qytx.platform.org.service.IUser#selectUserByGroup(cn.com.qytx.platform.org.domain.UserInfo, cn.com.qytx.platform.org.domain.GroupInfo, java.lang.String, int)
	 */
	public List<TreeNode> selectUserByGroup(int companyId,GroupInfo forkGroup,String moduleName,int showType,int key,String path,int groupType,Integer optType,Integer state);
    
    /**
     * 功能：查找角色和人员，
     * 作者：jiayongqiang
     * 参数：
     * 输出：
     */
    public List<TreeNode> selectUserByRole(UserInfo userInfo,String moduleName,int showType,String path);
    
    /**
     * 功能：根据单位ID、群组类型，查询人员、群组，5 个人群组;6公共群组(原为4)
     * 作者：jiayongqiang
     * 参数：
     * 输出：
     */
    public List<TreeNode> selectUserByQunzu(int companyId,int qunzuType,String contextPath);
    
    /**
     * 功能：判断电话号码是否重复
     * 作者：jiayongqiang
     * 参数：
     * 输出：true:重复，false不重复
     */
    public boolean phoneIsRepeat(String phone);
    public boolean phoneIsRepeatWithCompany(String phone);
    public boolean phoneIsRepeat(String userName,String phone);
    
    /**
     * 功能：判断电话号码是否重复
     * 作者：jiayongqiang
     * 参数：
     * 输出：true:重复，false不重复
     */
    public List<UserInfo> findUsersByPhone(String phone);
    
    /**
     * 保存人员并排序
     * @param userInfo
     */
    @Deprecated
    public void addOrUpdateUserWithOrder(UserInfo userInfo);
    @Deprecated
    public void addOrUpdateUserWithOrder(UserInfo userInfo,String oldPhone);
    
    /**
     * 根据登录名查询用户
     * @param loginNames 登录名
     * @return 用户列表
     */
    public List<UserInfo> findUsersByLoginNames(String loginNames);
    
    /**
     * 根据登录名和密码查询用户
     * @param loginName 登录名
     * @param loginPass 密码
     * @return 用户列表
     */
	public  List<UserInfo> findByLoginNamePass(String loginName,String loginPass);
    
	/**
     * 根据登录名和密码查询用户，通讯助理专用
     * @param loginName 登录名
     * @param loginPass 密码
     * @return 用户列表
     */
	public  List<UserInfo> findByLoginNamePassTXZL(String loginName,String loginPass);
    
    /**
     * 功能：根据公司id和人员登录名得到用户
     * @param companyId 单位ID
     * @param loginName 登录名
     * @return
     */
	public UserInfo loadUserByCompyIdName(Integer companyId,String loginName);
    
    /**
     * 
     * 功能：设置人员信息（是否可见，人工转接，漏话提醒）
     * <br/>@param type  1 设置人员是否可见 2 人工转接设置 3 漏话提醒设置 
     * <br/>@param companyId 单位ID
     * <br/>@param userIds 用户ID集合
     * <br/>@param state  是否可见(0显示1隐藏) 人工转接设置(1：不限制0：仅内部转接2：不可被转接) 漏话提醒设置(0：不需要，1：短信通知，2：语音留言)
     * <br/>@return  更新的记录数
     */
    public int updateUser(int type,int companyId,String userIds,int state);

    /**
     * 根据单位ID和人员ID获取单位所有人员信息
     * @param companyId 单位ID
     * @param userId 人员ID(可能需要根据该人员的权限)
     * @return
     */
    public List<UserInfo> findAll(Integer companyId,Integer userId);
    /**
     * 功能：过滤数据权限的部门树
     * @param adminUser
     * @return
     */
    public List<TreeNode> selecFilterGroup(UserInfo adminUser,GroupInfo forkGroup,String moduleName,int showType,int key,String path,int groupType);
    /**
     * 功能：过滤隐藏人员和管理范围
     * @param user 当前操作用户
     * @param searchName 查询条件
     * @return 用户列表
     */
    public List<UserInfo> findAllUsersAndFilter(UserInfo user,String searchName);
    
    /**
	 * 功能：获得用户的所有关联人
	 * @param userId 用户ID
	 * @param companyId 单位ID
	 * @return 用户列表
	 */
	public List<UserInfo> findVirtualUsers(int userId,int companyId);
	
	/**
	 * 功能：将手机端传递过来的用户中虚拟用户转换为真实用户
	 * @param userIds 包括虚拟用户的用户ID集合
	 * @param flag true 要前后',' false 不要前后','
	 * @return 过滤掉虚拟用户后的ID集合
	 */
	public String transVirtualToTrueUser(String userIds,boolean flag);
	
	/**
	 * 获取部门的人员数量
	 * @param companyId
	 * @return key:部门ID，value部门人员数
	 */
	public Map<Integer,Integer> getUserNumByGroup(int companyId);
	
	/**根据单位ID获取单位下的人员数
	 * @param companyid
	 * @param groupId
	 * @return
	 */
	public Integer getUserNumsByGroupId(int companyid,int groupId);
	
	/**
	 * 功能：根据部门name 姓名获得用户信息
	 * @param companyId
	 * @param groupId
	 * @param userName
	 * @return
	 */
	public List<UserInfo> getUserByGroupUserName(int companyId,String groupName,String userName);
	
	public Timestamp getLastUpdateNew(int companyId);
	
	public List<UserInfo> findUsersByLastUpdateTime(int companyId,String lastUpdateTime);
	
	public List<UserInfo> findUsersByPhone(int companyId,String phone);
	
	/**
	 * 根据电话和V网短号查询人员信息
	 * @param companyId
	 * @param phone
	 * @return
	 */
	public UserInfo getUserByCompanyIdPhone(int companyId, String phone);
	
	/**
	 * 根据电话号码集合获取人员集合
	 * @param phones
	 * @return
	 */
	public List<UserInfo> getUserInfoByPhones(String phones);
	
	/**
	 * 功能：根据主用户获得虚拟用户
	 * @param userIds
	 * @return
	 */
	public List<UserInfo> getVirtualUsers(String userIds);
	
	/**
     * 获取人员列表
     * @param companyId 单位ID
     * @param lastUpdateTime 上次更新时间
     * @param count 数量
     * @return
     */
    public List<UserInfo> getUserList(Integer companyId,Integer userId,String lastUpdateTime,Integer count);
    
    /**
     * 根据单位ID和登录名查询登陆用户
     * @param companyId
     * @param loginName
     * @return
     */
    public UserInfo getUserInfoByLoginName(Integer companyId,String loginName);
    /**
     * 获取公司的超级管理员
     * @param companyId
     * @return
     */
    public UserInfo findDefaultUser(Integer companyId);
    
    /**
     * 查找单位直属员工
     * @param companyId
     * @return
     */
    public List<UserInfo> findCompanyUsers(Integer companyId);
    
    /**
     * 根据姓名，电话、v网短号查询
     * @param companyId
     * @param searchkey
     * @return
     */
    public List<UserInfo> searchUserlist(Integer companyId,String searchkey);
    
    /**
     * 根据姓名，电话查询
     * @param companyId
     * @param searchkey
     * @return
     */
    public List<UserInfo> searchUserlistByNameAndPhone(Integer companyId,String searchkey);
    
    /**
     * 获取企业的所有子账号人员信息
     * @param companyId
     * @return
     */
	public List<UserInfo> findAccount(Integer companyId);
	
	/**更改用户排序
	 * @param userId
	 * @param orderIndex
	 */
	public void updateUserOrderIndex(int userId,int orderIndex);
}
