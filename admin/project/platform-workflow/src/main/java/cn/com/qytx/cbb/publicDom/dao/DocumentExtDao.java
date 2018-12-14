package cn.com.qytx.cbb.publicDom.dao;

import java.io.Serializable;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import cn.com.qytx.cbb.publicDom.domain.DocumentExt;
import cn.com.qytx.platform.base.dao.BaseDao;

/**
 * 功能: 公文内容扩张字段，包括签章
 * 版本: 1.0
 * 开发人员: 陈秋利
 * 创建日期: 2013-4-17
 * 修改日期: 2013-4-17
 * 修改列表:
 */
@Repository("documentExtDao")
public class DocumentExtDao extends BaseDao<DocumentExt, Integer> implements Serializable{

	/**
	 * 描述含义
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 功能：查询某个公务类别下的公文数
	 * @param doctypeId
	 * @return
	 */
	public int getDocTypeCount(Integer docTypeId) {
		String sql = "SElect COUNT(DBID_) AS C FROM JBPM4_VARIABLE where KEY_ = 'gongwenType_id' and STRING_VALUE_ = "+docTypeId;
		Session session = (Session) entityManager.getDelegate();
		Object result = session.createSQLQuery(sql).uniqueResult();
		if (result!=null) {
			String rs = result.toString();
			return Integer.parseInt(rs);
		}else {
			return 0;
		}
	}

	public DocumentExt findByProcessInstanceId(String processInstanceId) {
		String hql = "from DocumentExt where processInstanceId=? ";
		return (DocumentExt)this.findUnique(hql, processInstanceId);
	}

 
	
}
