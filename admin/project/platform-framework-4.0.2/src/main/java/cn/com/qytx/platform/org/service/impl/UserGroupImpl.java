package cn.com.qytx.platform.org.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.Pageable;
import cn.com.qytx.platform.base.service.impl.BaseServiceImpl;
import cn.com.qytx.platform.org.dao.UserGroupDao;
import cn.com.qytx.platform.org.domain.UserGroup;
import cn.com.qytx.platform.org.service.IUserGroup;

@Transactional
@Service("userGroupService")
public class UserGroupImpl extends BaseServiceImpl<UserGroup> implements IUserGroup {
	@Resource(name="userGroupDao")
	private UserGroupDao<UserGroup> userGroupDao;
	
	public Page<Map<String,Object>> findByCompany(Pageable page,Integer companyId,String searchkey){
		return userGroupDao.findByCompany(page, companyId,searchkey);
	}
	
	public UserGroup findByUserCompany(Integer userId,Integer companyId){
		return userGroupDao.findByUserCompany(userId, companyId);
	}
	public boolean checkIsExistPower(Integer userId,Integer companyId,Integer groupId){
		return userGroupDao.checkIsExistPower(userId, companyId, groupId);
	}
	
	/**
     * 获得所有公司的所有管理范围设置
     */
    public List<UserGroup> findAllCompanyUserGroup(){
    	return userGroupDao.findAllCompanyUserGroup();
    }
}
