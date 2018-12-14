package cn.com.qytx.platform.org.dao;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Repository;

import cn.com.qytx.platform.base.dao.BaseDao;
import cn.com.qytx.platform.org.domain.CompanyInfo;

/**
 * 功能：公司操作
 * 作者： jiayongqiang
 * 创建时间：2014年6月23日
 */
@Repository("companyDao")
public class CompanyDao <T extends CompanyInfo>  extends BaseDao<CompanyInfo,Integer> implements Serializable{

	public List<CompanyInfo> findByIds(String companyIds){
		String hql = "from CompanyInfo where companyId in ( "+companyIds+" )";
		return super.find(hql);
	}
  
}
