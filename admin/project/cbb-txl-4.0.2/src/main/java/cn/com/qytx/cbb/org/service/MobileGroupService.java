package cn.com.qytx.cbb.org.service;




/**
 * 功能: 手机端通讯录部门接口 
 * 版本: 1.0
 * 开发人员: zyf
 * 创建日期: 2015年5月18日
 * 修改日期: 2015年5月18日
 * 修改列表:
 */
public interface MobileGroupService {

	/**
	 * 功能：新增部门/群组
	 * @param companyId 单位ID
	 * @param userId 操作人员ID
	 * @param userName 操作人员名称
	 * @param parentId 上级ID
	 * @param groupName 部门/群组名称
	 * @param groupType 部门类型 1 企业通讯录 2 公共通讯录 3 私人通讯录 4 公共群组 5 个人群组
	 * @return
	 */
	public Integer addGroup (Integer companyId,Integer userId,String userName,Integer parentId ,String groupName,Integer groupType) throws Exception;
	
	/**
	 * 功能：更新部门/群组
	 * @param companyId 单位ID
	 * @param userId 操作人员ID
	 * @param userName 操作人员名称
	 * @param groupId 要更新的部门/群组ID
	 * @param parentId 上级ID
	 * @param groupName部门/群组名称
	 */
	public void updateGroup (Integer companyId,Integer userId,String userName,Integer groupId, Integer parentId ,String groupName)throws Exception;
	
	/**
	 * 功能：删除部门/群组
	 * @param companyId 单位ID
	 * @param userId 操作人员ID
	 * @param userName 操作人员名称
	 * @param groupId 要删除的部门/群组ID
	 * @return
	 */
	public Integer deleteGroup (Integer companyId,Integer userId,String userName,Integer groupId)throws Exception;
	
	/**
	 * 功能：部门排序
	 * @param companyId 单位ID
	 * @param userId 操作人员ID
	 * @param userName 操作人员名称
	 * @param sortList 排序列表 GroupOrder实体类的 json格式
	 */
	public void groupOrder (Integer companyId,Integer userId,String userName,String sortList)throws Exception;
}
