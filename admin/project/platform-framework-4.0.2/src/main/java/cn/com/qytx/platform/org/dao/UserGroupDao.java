package cn.com.qytx.platform.org.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import cn.com.qytx.platform.base.dao.BaseDao;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.PageImpl;
import cn.com.qytx.platform.base.query.Pageable;
import cn.com.qytx.platform.org.domain.UserGroup;

/**
 * 功能:管理范围操作类
 */
@Repository("userGroupDao")
public class UserGroupDao <T extends UserGroup> extends BaseDao<UserGroup, Integer> implements Serializable{
	private static final long serialVersionUID = 1L;
	public Page<Map<String,Object>> findByCompany(Pageable page,Integer companyId,String searchkey){
	    StringBuilder hql = new StringBuilder();
	    hql.append("select new map(u.userId as userId,u.groupId as groupId,u.userName as userName,u.phone as phone,ug.groupPower as groupPower,ug.id as id) from UserGroup ug,UserInfo u where u.isDelete=0 and ug.user.userId=u.userId");
		hql.append(" and u.companyId=").append(companyId);
		hql.append(" and ug.companyId=").append(companyId);
	    if (StringUtils.isNoneBlank(searchkey)) {
			hql.append(" and (u.userName like '%")
			.append(searchkey)
		    .append("%' or u.phone like '%")
			.append(searchkey)
			.append("%')");
		}
		@SuppressWarnings("unchecked")
		List<Map<String,Object>> content=entityManager.createQuery(hql.toString()).setFirstResult(page.getOffset()).setMaxResults(page.getPageSize()).getResultList();
		return new PageImpl<Map<String,Object>>(content,page,entityManager.createQuery(hql.toString()).getResultList().size());
		}
	
	public UserGroup findByUserCompany(Integer userId,Integer companyId){
		return findOne("x.user.userId=?1 and companyId=?2", userId,companyId);
	}
	
    /**
     * 检查指定人员对某部门是否有管理权限
     * @param userId 用户ID
     * @param companyId 单位ID
     * @param groupId 部门ID
     * @return true有权限,false没权限
     */
    public boolean checkIsExistPower(Integer userId,Integer companyId,Integer groupId){
		return findOne("x.user.userId=?1 and companyId=?2 and groupPower like '%,"+groupId+",%'",userId,companyId)==null?false:true;
	}
    
    /**
     * 获得所有公司的所有管理范围设置
     */
    public List<UserGroup> findAllCompanyUserGroup(){
    	return super.find("select u from UserGroup u where u.user.userId in (select userId from UserInfo where isDelete=0)");
    }
}
