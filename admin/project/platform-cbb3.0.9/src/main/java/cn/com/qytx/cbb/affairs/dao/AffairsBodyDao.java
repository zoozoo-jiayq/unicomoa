package cn.com.qytx.cbb.affairs.dao;

import java.io.Serializable;

import org.springframework.stereotype.Component;

import cn.com.qytx.cbb.affairs.domain.AffairsBody;
import cn.com.qytx.platform.base.dao.BaseDao;

@Component
public class AffairsBodyDao extends BaseDao<AffairsBody,Integer> implements Serializable{
	
	public AffairsBody findByInfo(String smsType, String contextInfo) {
		String hql = "smsType =?1 and contentInfo like ?2";
		return findOne(hql,smsType,contextInfo+"%");
	}
}
