package cn.com.qytx.cbb.secret.sevice;

import java.util.List;

import cn.com.qytx.cbb.secret.domain.SecretSettings;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.Pageable;
import cn.com.qytx.platform.base.service.BaseService;

/**
 * 功能: 保密设置
 * 版本: 1.0
 * 开发人员: zyf
 * 创建日期: 2014年12月23日
 * 修改日期: 2014年12月23日
 * 修改列表:
 */
public interface ISecretSettings extends BaseService<SecretSettings> {

	/**
	 * 功能：获得单位的所有保密设置
	 * @param pageable 分页信息
	 * @param companyId
	 * @return
	 */
	public Page<SecretSettings> getSecretSettingsByPage(Pageable pageable,int companyId);
	
	/**
	 * 功能：根据用户id获得用户姓名
	 * @param userIds
	 * @return
	 */
	public String getUserNamesByIds(String userIds);
	
	/**
	 * 功能：根据设置id删除设置
	 * @param ids
	 */
	public void deleteSettingsByIds(String ids,Integer companyId);
	
	/**
	 * 功能：新增修改
	 * @param secret
	 */
	public void saveUpdate(SecretSettings secret,int companyId);
	
	/**
	 * 功能：根据用户id和公司id获得该用户在公司中不可看到的保密信息
	 * @param companyId
	 * @param userId
	 * @return
	 */
	public List<SecretSettings> getSettingsByUserAndCompany(int companyId,int userId);

	/**
	 * 获得所有单位的保密设置
	 * @return
	 */
	public List<SecretSettings> findAllCompanySettings();
}
