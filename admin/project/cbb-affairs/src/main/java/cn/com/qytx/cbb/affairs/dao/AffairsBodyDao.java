package cn.com.qytx.cbb.affairs.dao;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import cn.com.qytx.cbb.affairs.domain.AffairsBody;
import cn.com.qytx.platform.base.dao.BaseDao;

@Component
public class AffairsBodyDao extends BaseDao<AffairsBody,Integer> implements Serializable{
	
	private static final long serialVersionUID = 1L;

	public AffairsBody findByInfo(String smsType, String contextInfo) {
		String hql = "smsType =?1 and contentInfo like ?2";
		return findOne(hql,smsType,contextInfo+"%");
	}
	public List<AffairsBody> findAffairsBodyByRecordId(String moduleName, String recordId){
		String hql = "recordId=? and smsType=? and sendTime>?";
		return super.findAll(hql, recordId,moduleName,new Timestamp(System.currentTimeMillis()));
	}
	
	public void deleteByRecordIds(String moduleName,String recordIds){
		String sql="delete from tb_om_affairs_body where record_id in ("+recordIds+") and sms_type='"+moduleName+"'";
		this.entityManager.createNativeQuery(sql).executeUpdate();
	}
}
