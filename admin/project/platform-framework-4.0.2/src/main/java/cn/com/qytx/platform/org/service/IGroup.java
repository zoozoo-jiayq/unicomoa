package cn.com.qytx.platform.org.service;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.com.qytx.platform.base.service.BaseService;
import cn.com.qytx.platform.org.domain.GroupInfo;

/**
 * 部门/群组操作接口
 * <br/>User: 黄普友
 * <br/>Date: 13-2-20
 * <br/>Time: 下午3:18
 */
public interface IGroup  extends BaseService<GroupInfo>,Serializable  {


    /**
     * 获取自己所在的部门/群组
     * @param companyId 单位ID
     * @param userId 人员ID
     * @param groupType 部门类型
     * @return 部门列表
     */
    public GroupInfo getGroupByUserId(int companyId,int userId) ;
    
    /**
     * 返回部门下面子部门的数量
     * @param companyId 单位ID
     * @param groupId 部门ID
     * @return 指定部门的子部门数量
     */
    public int checkExistsChildGroup(int companyId,int groupId);
    /**
     * 获取部门/群组列表
     * @param companyId  企业ID
     * @param groupType  部门类型: 1.公共部门 2.公共群组 3.外部部门 4.个人群组
     * @return
     */
    public List<GroupInfo> getGroupList(int companyId,int groupType);


    /**
     * 获取部门列表
     * @param companyId
     * @param userId
     * @param lastUpdateTime 上次更新时间，为null 表示所有数据
     * @param needUserGroup 是否需要群组信息
     * @return
     */
    public List<GroupInfo> getGroupList(Integer companyId,Integer userId,Date lastUpdateTime,boolean needUserGroup );

    
	/**
	 * @Title: findGroupTree 
	 * @Description: TODO( 获取全部群组信息) 
	 * @param companyId
	 * @param groupType 组类型
	 * @return List<Group>    返回类型
	 */
	public List<GroupInfo> findGroupTree(Integer companyId,Integer groupType);
	
	/**
	 * 功能：根据用户ID获取该用户所属分支机构
	 * @param userId:用户ID
	 * @return GroupInfo:如果用户的父部门存在分支机构，则返回该分支机构，如果不存在分支机构则返回空
	 * @throws   
	 */
	public GroupInfo getForkGroup(int companyId,int userId);
	
	/**
	 * 功能：根据用户查询当前用户所在部门的结构
	 * @param companyId 单位ID
	 * @param userId 当前用户ID：
	 * @return 用户所在的部门结构树
	 */
	public  String getGroupNamePathByUserId(int companyId, int userId);
	public String getGroupNamePath(int companyId,String groupIdPath);
	
	
	/**
	 * 功能：根据父部门ID获取所有的子部门
	 * @param parentGroupId 父部门ID
	 * @return 返回子部门列表
	 */
	public List<GroupInfo> getSubGroupList(int parentGroupId);
	
	/**
	 * 功能：根据部门名称得到部门(级联部门以"\"分割)
	 * @param companyId,单位ID
	 * @param groupPathName 部门路径
	 * @return 部门对象
	 */
	public GroupInfo loadGroupByPathName(Integer companyId,String groupPathName);
	
	/**
	 * 功能：指定的部门ID下面是否有相同的群组名称
	 * @param parentId:父部门ID
	 * @param groupName:部门名称
	 * @param groupType:部门类型
	 * @param companyId:单位ID
	 * @return true有相同的部门,false没有相同的部门
	 */
	public boolean isHasSameGroupName(int parentId,String groupName,int groupType,int companyId);
	
	/**
	 * 判断指定部门下面是否有子部门
	 * @param groupId 部门ID
	 * @return true有子部门,false没有子部门
	 */
	public boolean isHasChild(int groupId);
	
	/**
	 * 判断指定部门下面是否有人员
	 * @param groupId 部门ID
	 * @return true:有人，false没人
	 */
	public boolean isHasUsers(int companyId,int groupId);
	
	/**
	 * 
	 * 功能：获取全部群组信息,分级别
	 * @param companyId 公司id
	 * @param groupType 组类型
	 * @return 部门树列表
	 */
	public List<GroupInfo> findGradeGroupTree(Integer companyId,Integer groupType);



    /**
     * 部门排序
     * @param companyId 单位ID
     * @param groupId   部门ID
     * @param startIndex 开始索引
     * @param endIndex   结束索引
     */
    public void sortOrder(Integer companyId,Integer groupId,Integer startIndex,Integer endIndex);
    /**
     * 获取指定部门的子部门的最大排序号
     * @param companyId 单位ID
     * @param parentId 父部门ID
     * @param groupType 部门类型
     * @return 最大排序号
     */
    public Integer getMaxOrderIndex(Integer companyId,Integer parentId,int groupType);
    
    
    /**
     * 根据部门分机号码和公司id获取部门/群组
     * @param companyId 单位ID
     * @param groupCode 部门代码
     * @return 部门列表
     */
	public List<GroupInfo> getGroupListByGroupCode(int companyId,String groupCode);
    
    /**
     * 功能：根据子部门获取所有的父部门集合，返回结果包含子部门
     * @param all 所有的部门集合
     * @param childs 子部门集合
     * @return 子部门及其父部门的集合
     */
    public Set<GroupInfo> getAllparentGroups(List<GroupInfo> all,List<GroupInfo> childs);
    
    /**
     * 获取部门/群组列表 (变更过的，sql server或框架不支持union拼写)
     * @param companyId 单位ID
     * @param userId 操作用户ID
     * @param needUserGroup 是否需要群组信息
     * @return 部门列表
     */
    public List<GroupInfo> getGroupListChanged(Integer companyId,Integer userId,Date lastUpdateTime,boolean needUserGroup);
    public List<GroupInfo> getGroupListChanged(Integer companyId,Integer userId,Date lastUpdateTime);
    
    /**
     * 功能：根据公司 部门ids获取部门
     * @param companyId 单位ID
     * @param groupIds 部门ID集合，多个ID之间用","隔开
     * @return 返回部门列表
     */
    public List<GroupInfo> getGroupListByIds(Integer companyId,String groupIds);
    /**
     * 功能：上移 下移操作 0上移 1 下移
     * @param groupInfo 部门信息
     * @param value 操作标识
     * @return
     */
    public int upOrDownOrder(GroupInfo groupInfo,Integer value);
    
    
    /**
     * 增加或者修改部门信息
     * @param groupInfo 部门对象
     */
    public void addOrUpdateGroup(GroupInfo groupInfo);
    
    /**
     * 根据部门ID删除部门，软删除
     * @param id  部门ID
     */
    public void deleteGroup(int id);
    
	/**
	 * 功能：得到公司部门下面的人员数量(包括子部门),建立部门ID,人员数的映射
	 * @param companyId
	 * @return Map,key值是部门ID，value是部门的人员数(包括子部门)
	 */
	public Map<Integer,Integer> getCompanyGroupCountMap(Integer companyId);
	
	/**
	 * 更新部门的最后一次更新时间
	 * @param groupId 部门ID
	 */
	public void updateGroupTime(int groupId);
	
	/**
	 * 
	 * 功能：修改部门最后一次更新时间 根据人员ids
	 * @param companyId 单位ID
	 * @param ids 人员ID集合
	 */
	public void updateGroupsByUserIds(int companyId, String ids);
	
	public List<GroupInfo> getGroupsByUserIds(int companyId,String userIds);
	
	/**根据部门级别查找部门列表
	 * @param grade
	 * @return
	 */
	public List<GroupInfo> findGroupListByGrade(int grade);
	
	/**
	 * 获取所有的分支机构
	 * @return
	 */
	public List<GroupInfo> findForkGroupList();
	
	/**
	 * 获取最新部门的最后修改时间
	 */
	public Timestamp getLastUpdateTime(int companyId);
	
    /**添加部门
     * @param groupInfo
     */
    public void addGroup(GroupInfo groupInfo);
    
    public void updateGroup(GroupInfo groupInfo);
    
    /**
     * 获得今天所有的部门修改
     * @param companyId
     * @return
     */
    public List<GroupInfo> getTodayGroupListChanged(Integer companyId);
    
    /**
     * 获取部门列表
     * @param companyId 单位ID
     * @param lastUpdateTime 上次更新时间
     * @param count 数量
     * @return
     */
    public List<GroupInfo> getGroupList(Integer companyId,Integer userId,String lastUpdateTime,Integer count);
    
    /**根据部门父ID和部门名称关键字查询部门列表，如果父ID为空或者0，则查询所有部门
     * @param parentId
     * @param groupkey
     * @return
     */
    public List<Map<String,Object>> findGroupListByParentIdAndGroupkey(Integer companyId,Integer parentId,String groupName,String gids);
    
    /**
     * 根据父部门ID获取一级子部门列表
     * @param companyId
     * @param parentId
     * @return
     */
    public List<GroupInfo> getGroupListByParentId(Integer companyId,Integer parentId);
    
    /**
	 * 在指定ID下面是否有相同的部门名称
	 * 功能：parentId
	 * @param parentId
	 * @param groupName
	 * @return
	 */
	public boolean isHasSameGroupNameForSet(Integer parentId,String groupName,int groupType,int companyId,int userId);
}
