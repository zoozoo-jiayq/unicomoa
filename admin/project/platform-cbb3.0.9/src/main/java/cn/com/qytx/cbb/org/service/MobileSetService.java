package cn.com.qytx.cbb.org.service;


/**
 * <br/>功能: 手机端通讯录群组人员管理接口 
 * <br/>版本: 1.0
 * <br/>开发人员: zyf
 * <br/>创建日期: 2015年5月18日
 * <br/>修改日期: 2015年5月18日
 * <br/>修改列表:
 */
public interface MobileSetService {

	/**
	 * 功能：新增群组人员
	 * @param companyId 单位ID
	 * @param userId 操作人ID
	 * @param userName 操作人姓名
	 * @param groupId 群组ID
	 * @param userIds 人员ID列表，多个人员ID之间,隔开
	 * @return
	 */
	public String addSetUsers (Integer companyId,Integer userId,String userName,Integer groupId,String userIds)throws Exception;
	
	/**
	 * 功能：删除群组人员
	 * @param companyId 单位ID
	 * @param userId 操作人ID
	 * @param userName 操作人姓名
	 * @param groupId 群组ID
	 * @param userIds 人员ID列表，多个人员ID之间,隔开
	 * @return
	 */
	public String deleteSetUsers (Integer companyId,Integer userId,String userName,Integer groupId ,String userIds)throws Exception;
}
