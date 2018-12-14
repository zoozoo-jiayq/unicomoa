package cn.com.qytx.cbb.publicDom.service;

import java.util.List;

/**
 * 功能：查询收文登记员
 * 版本: 1.0
 * 开发人员：贾永强
 * 创建日期: 2013-4-22 下午2:16:16 
 * 修改日期：2013-4-22 下午2:16:16 
 * 修改列表：
 */
public interface GatherRegisterService {

	
	/**
	 * 功能：根绝部门ID查询收文登记员的Id
	 * @param
	 * @return
	 * @throws   
	 */
	public List<Integer> findUserIdByRoleAndGroup(String groupId);
	
	/**
	 * 功能：根绝部门ID查找部门名称
	 * @param
	 * @return
	 * @throws   
	 */
	public String findGroupNameById(String groupId);
	
	
	/**
	 * 功能：根绝用户ID查询部门名称
	 * @param
	 * @return
	 * @throws   
	 */
	public String findGroupNameByUserId(String userId);
	
}

