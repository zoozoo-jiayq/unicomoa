package cn.com.qytx.cbb.module.service.impl;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.com.qytx.cbb.module.dao.RoleModuleMobileDao;
import cn.com.qytx.cbb.module.domain.RoleModuleMobile;
import cn.com.qytx.cbb.module.service.IRoleModuleMobile;
import cn.com.qytx.platform.base.application.TransportUser;
import cn.com.qytx.platform.base.service.impl.BaseServiceImpl;

@Transactional
@Service("roleModuleMobileImpl")
public class RoleModuleMobileImpl extends BaseServiceImpl<RoleModuleMobile> implements IRoleModuleMobile{
		
	@Resource(name="roleModuleMobileDao")
	private RoleModuleMobileDao roleModuleMobileDao;
	
	@Transactional(readOnly=true)
	public List<RoleModuleMobile> getModuleList(Integer roleId){
		return roleModuleMobileDao.getModuleList(roleId);
	}
	@Transactional(propagation=Propagation.REQUIRED)
	public void updateModuleList(Integer[] moduleIds, Integer roleId){
		roleModuleMobileDao.delByRoleId(roleId);
		if(moduleIds!=null &&moduleIds.length > 0){
			for(int i = 0 ; i < moduleIds.length ; i++){
				RoleModuleMobile roleModuleMobile = new RoleModuleMobile();
				roleModuleMobile.setModuleId(moduleIds[i]);
				roleModuleMobile.setRoleId(roleId);
				roleModuleMobile.setCompanyId(TransportUser.get().getCompanyId());
				roleModuleMobileDao.saveOrUpdate(roleModuleMobile);
			}
		}
		
	}
}
