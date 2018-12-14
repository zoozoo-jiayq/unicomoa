package cn.com.qytx.cbb.org.service;


/**
 * <br/>功能: 手机端通讯录人员接口 
 * <br/>版本: 1.0
 * <br/>开发人员: zyf
 * <br/>创建日期: 2015年5月18日
 * <br/>修改日期: 2015年5月18日
 * <br/>修改列表:
 */
public interface MobileUserService {

	/**
	 * 功能：新增用户
	 * @param companyId 单位ID
	 * @param userId 操作人ID
	 * @param userName 操作人姓名
	 * @param userJson 人员Json UserInfo实体类的 json格式
	 * @return
	 */
	public Integer addUsersInfo (Integer companyId,Integer userId,String userName,String userJson) throws Exception;
	
	/**
	 * 功能：更新用户
	 * @param companyId 单位ID
	 * @param userId 操作人ID
	 * @param userName 操作人姓名
	 * @param userJson 人员Json UserInfo实体类的 json格式
	 * @return
	 */
	public Integer updateUsersInfo (Integer companyId,Integer userId,String userName,String userJson) throws Exception;
	
	/**
	 * 功能：删除用户
	 * @param companyId 单位ID
	 * @param userId 操作人ID
	 * @param userName 操作人姓名
	 * @param userIds 人员ID列表，多个人员ID之间,隔开
	 */
	public void deleteUsersInfo (Integer companyId,Integer userId,String userName,String userIds) throws Exception;
	
	/**
	 * 功能：人员排序
	 * @param companyId 单位ID
	 * @param userId 操作人ID
	 * @param userName 操作人姓名  
	 * @param sortList 排序列表 UserOrder实体类的 json格式
	 */
	public void userOrder (Integer companyId,Integer userId,String userName,String sortList) throws Exception;
}
