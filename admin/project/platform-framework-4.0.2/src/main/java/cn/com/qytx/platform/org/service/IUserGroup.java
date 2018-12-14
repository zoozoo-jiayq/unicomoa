package cn.com.qytx.platform.org.service;

import java.util.List;
import java.util.Map;

import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.Pageable;
import cn.com.qytx.platform.base.service.BaseService;
import cn.com.qytx.platform.org.domain.UserGroup;

/**
 * 功能:管理范围接口
 * 开发人员: zhangjingjing
 * 创建日期: 2014年9月12日
 */
public interface IUserGroup extends BaseService<UserGroup>{
	
	/**
	 * 功能：根据公司获取管理数据
	 * @param page
	 * @param companyId
	 * @return
	 */
	public Page<Map<String,Object>> findByCompany(Pageable page,Integer companyId,String searchkey);
	
	/**
	 * 功能：根据人员公司获取数据
	 * @param userId
	 * @param companyId
	 * @return
	 */
	public UserGroup findByUserCompany(Integer userId,Integer companyId);
	
	/**
	 * 功能：判断用户是否存在管理权限
	 * @param userId
	 * @param companyId
	 * @param groupId
	 * @return
	 */
	public boolean checkIsExistPower(Integer userId,Integer companyId,Integer groupId);

	/**
     * 获得所有公司的所有管理范围设置
     */
    public List<UserGroup> findAllCompanyUserGroup();
}
