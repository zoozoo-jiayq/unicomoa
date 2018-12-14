package cn.com.qytx.cbb.publicDom.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.qytx.cbb.publicDom.dao.FormAuthorityDao;
import cn.com.qytx.cbb.publicDom.domain.FormAuthority;
import cn.com.qytx.cbb.publicDom.service.IFormAuthority;
import cn.com.qytx.platform.base.service.impl.BaseServiceImpl;
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.org.service.IUser;

@Service("formAuthorityService")
@Transactional
public class FormAuthorityServiceImpl extends BaseServiceImpl<FormAuthority> implements IFormAuthority {

	@Resource
	private FormAuthorityDao formAuthorityDao;
	
	@Resource
	private IUser userService;
	

	@Override
	public FormAuthority findByFormPropertyId(int formPropertyId) {
		// TODO Auto-generated method stub
		return formAuthorityDao.findByFormPropertyId(formPropertyId);
	}

	@Override
	public void saveOrUpdateFormAuthority(FormAuthority fa) {
		// TODO Auto-generated method stub
		formAuthorityDao.saveOrUpdate(fa);
	}

	@Override
	@Deprecated
	public List<String> findHasAuthorityProperties(String formId, String userId) {
		// TODO Auto-generated method stub
		List<String> result = new ArrayList<String>();
		List<FormAuthority> list = formAuthorityDao.findByFormId(Integer.parseInt(formId));
		for(int i=0; i<list.size(); i++){
			boolean flag = false;
			FormAuthority temp = list.get(i);
			String candidate = temp.getCandidate();
			String groups = temp.getGroups();
			String roles = temp.getRoles();
			List<UserInfo> temp1 = userService.findUsersByIds( candidate);
			if(temp1!=null){
				for(int j=0; j<temp1.size(); j++){
					if(temp1.get(j).getUserId().toString().equals(userId)){
						result.add(temp.getPropertyName());
						flag = true;
						break;
					}
				}
			}
			if(flag){
				continue;
			}
			List<UserInfo> temp2 = userService.findUsersByGroupId(groups);
			if(temp2!=null){
				for(int j=0; j<temp2.size(); j++){
					if(temp2.get(j).getUserId().toString().equals(userId)){
						result.add(temp.getPropertyName());
						flag = true;
						break;
					}
				}
			}
			if(flag){
				continue;
			}
			List<UserInfo> temp3 = userService.findUsersByRoleId(roles);
			if(temp3!=null){
				for(int j=0; j<temp3.size(); j++){
					if(temp3.get(j).getUserId().toString().equals(userId)){
						result.add(temp.getPropertyName());
						flag = true;
						break;
					}
				}
			}
			if(flag){
				continue;
			}
		}
		return result;
	}

	@Override
	public List<FormAuthority> findByFormId(int formId) {
		// TODO Auto-generated method stub
		return formAuthorityDao.findByFormId(formId);
	}

}
