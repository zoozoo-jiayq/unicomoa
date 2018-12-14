package cn.com.qytx.cbb.secret.dao;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.com.qytx.cbb.secret.domain.SecretSettings;
import cn.com.qytx.platform.base.dao.BaseDao;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.Pageable;
import cn.com.qytx.platform.org.dao.UserDao;
import cn.com.qytx.platform.org.domain.UserInfo;

/**
 * 功能: 保密设置
 * 版本: 1.0
 * 开发人员: zyf
 * 创建日期: 2014年12月23日
 * 修改日期: 2014年12月23日
 * 修改列表:
 */
@Component
public class SecretSettingsDao extends BaseDao<SecretSettings, Serializable> {

	@Autowired
	UserDao<UserInfo> userDao;
	
	/**
	 * 功能：获得单位的所有保密设置
	 * @param pageable 分页信息
	 * @param companyId
	 * @return
	 */
	public Page<SecretSettings> getSecretSettingsByPage(Pageable pageable,int companyId){
		return findAll(" isDelete = 0 and companyId = ?", pageable, companyId);
	}
	
	/**
	 * 功能：根据用户id获得用户姓名
	 * @param userIds
	 * @return
	 */
	public String getUserNamesByIds(String userIds){
		StringBuffer sb = new StringBuffer("");
		if(StringUtils.isNotEmpty(userIds)){
			if(userIds.startsWith(",")){
				userIds = userIds.substring(1);
			}
			if(userIds.endsWith(",")){
				userIds = userIds.substring(0,userIds.length()-1);
			}
			List<UserInfo> list = userDao.findAll(" isDelete = 0 and userId in ("+userIds+") ");
			if(list!=null && list.size()>0){
				for(int i=0;i<list.size();i++){
					UserInfo userInfo = list.get(i);
					sb.append(userInfo.getUserName());
					if(i < (list.size()-1)){
						sb.append(",");
					}
				}
			}
		}
		return sb.toString();
	}
	
	/**
	 * 功能：根据设置id删除设置
	 * @param ids
	 */
	public void deleteSettingsByIds(String ids,Integer companyId){
		if(StringUtils.isNotEmpty(ids)){
			if(ids.startsWith(",")){
				ids = ids.substring(1);
			}
			if(ids.endsWith(",")){
				ids = ids.substring(0,ids.length()-1);
			}
			this.entityManager.createQuery("update SecretSettings set isDelete = 1 where id in ("+ids+") ").executeUpdate();
			this.entityManager.createQuery(" update UserInfo set lastUpdateTime = ? where companyId="+companyId+" and isDelete =0 ").setParameter(1, new Timestamp(System.currentTimeMillis())).executeUpdate();
		}
	}
	
	/**
	 * 功能：新增修改
	 * @param secret
	 */
	public void saveUpdate(SecretSettings secret,int companyId){
		this.entityManager.createQuery(" update UserInfo set lastUpdateTime = ? where companyId="+companyId+" and isDelete =0 ").setParameter(1, new Timestamp(System.currentTimeMillis())).executeUpdate();
		this.entityManager.createQuery(" update UserInfo set lastUpdateTime = ? where companyId="+companyId+" and isDelete =0 ").setParameter(1, new Date()).executeUpdate();
		this.saveOrUpdate(secret);
	}
	
	/**
	 * 功能：根据用户id和公司id获得该用户在公司中不可看到的保密信息
	 * @param companyId
	 * @param userId
	 * @return
	 */
	public List<SecretSettings> getSettingsByUserAndCompany(int companyId,int userId){
		return this.findAll(" companyId=? and invisibleUserIds like '%,"+userId+",%' ",companyId);
	}
	
	/**
	 * 获得所有单位的保密设置
	 * @return
	 */
	public List<SecretSettings> findAllCompanySettings(){
		return super.find("select s from SecretSettings s where s.isDelete = 0");
	}

}
