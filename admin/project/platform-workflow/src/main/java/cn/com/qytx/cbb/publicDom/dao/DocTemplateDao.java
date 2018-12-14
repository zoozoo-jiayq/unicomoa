package cn.com.qytx.cbb.publicDom.dao;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Repository;

import cn.com.qytx.cbb.publicDom.domain.DocTemplate;
import cn.com.qytx.platform.base.application.TransportUser;
import cn.com.qytx.platform.base.dao.BaseDao;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.Pageable;

/**
 * 功能: 正文模板
 * 版本: 1.0
 * 开发人员: 陈秋利
 * 创建日期: 2013-4-17
 * 修改日期: 2013-4-17
 * 修改列表:
 */
@Repository("docTemplateDao")
public class DocTemplateDao extends BaseDao<DocTemplate, Integer> implements Serializable{

	/**
	 * 描述含义
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 查询公司下的所有模板
	 * @param companyId
	 * @return
	 */
	public List<DocTemplate> findAlByCompanyIdl(Integer companyId) {
		//String hql="from DocTemplate where  companyId=? and isDelete = 0";
		return this.findAll("companyId=? and isDelete = 0", companyId);
	}

	public Page<DocTemplate> findPageList(Integer docTemplateType, String docTemplateName,
			 int companyId,Pageable page) {
			 String hql = "select d from DocTemplate d where isDelete=0 and companyId="+companyId ;
			 if(docTemplateName!=null&&!"".equals(docTemplateName)){
				 hql+=" and docTemplateName like'%"+docTemplateName+"%'  ";
			 }
			 if(docTemplateType!=null&&docTemplateType>0){
				 hql+=" and categoryId="+docTemplateType+" ";
			 }

			 hql+=" order by docTemplateId desc";    
		     return this.findByPage(page, hql);
	}

	public Boolean deleteMore(String docTmplates) {
	    String ids = docTmplates;
		if(ids!=null&&ids.endsWith(",")){
			ids=ids.substring(0,ids.length()-1);
			String hql="update DocTemplate set isDelete=1 where docTemplateId in( "+ids+")";
		    this.createQuery(hql);
		}
		return true;
	}

	public DocTemplate findByName(String name) {
		 String hql="select d from DocTemplate d where  docTemplateName=? and isDelete=0 and companyId="+TransportUser.get().getCompanyId();
		 return (DocTemplate)this.findUnique(hql, name);
	}

	public int getCount(Integer categoryId) {
		// String hql=" where isDelete=0  and categoryId="+categoryId;
		 //this.findTotalCount(this.getEntityClass(),hql);
         int num= count("isDelete=0  and categoryId=?",categoryId);
         return num;
	}

	public List<DocTemplate> findByCategory(int category){
		//String hql = "from DocTemplate where   isDelete=0 and categoryId = ?";
		return this.findAll("isDelete=0 and categoryId = ?", category);
	}

	public List<DocTemplate> findByCategory(int categoryId,
			Integer companyId) {
		//String hql = "from DocTemplate where   isDelete=0 and categoryId = ? and companyId=? ";
		return this.findAll("isDelete=0 and categoryId = ? and companyId=?", categoryId,companyId);
	}
}
