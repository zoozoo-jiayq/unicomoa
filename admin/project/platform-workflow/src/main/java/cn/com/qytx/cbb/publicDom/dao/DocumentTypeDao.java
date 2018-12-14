/**
 * 
 */
package cn.com.qytx.cbb.publicDom.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import cn.com.qytx.cbb.publicDom.domain.DocumentType;
import cn.com.qytx.platform.base.dao.BaseDao;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.Pageable;
import cn.com.qytx.platform.org.domain.UserInfo;

/**
 * 
 * 功能: 公文类型Dao
 * 版本: 1.0
 * 开发人员: 陈秋利
 * 创建日期: 2013-4-16
 * 修改日期: 2013-3-16
 * 修改列表:
 */
@Repository("documentTypeDao")
public class DocumentTypeDao extends BaseDao<DocumentType, Integer> implements Serializable{
	
	/**
	 * 描述含义
	 */
	private static final long serialVersionUID = 1L;

	/**根据公文分类，公文名称，查找公文类型 列表
	 * 查询分页信息
	 * @param pageInfo 
	 */
	public Page<DocumentType> findPageList(String doctypeName, int categoryId, Pageable pageInfo) {
		 String hql = " categoryId in (select id from Dict where isDelete = 0) " ;

		 if(doctypeName!=null&&!"".equals(doctypeName)){
			 hql+=" and (doctypeName like'%"+doctypeName+"%' ) ";
		 }
		 if(categoryId>0){
			 hql+=" and categoryId="+categoryId+" ";
		 }
		 return this.findAll(hql, pageInfo);
	}

	
	public void updateBeginNum(int docTypeId){
		DocumentType dt = this.getDocumentTypeByDocName("驻马店市投资公司文件审阅笺");
		String hql = "update DocumentType set beginNum = beginNum+1 where doctypeId = "+docTypeId;
		this.bulkUpdate(hql);
		//super.bulkUpdate(hql);
		
	}

	public List<DocumentType> getDocumentTypeListByUser(UserInfo loginUser,int groupId) {
		List<DocumentType> result = new ArrayList<DocumentType>();
		int companyId=-1;
		if(loginUser!=null){
			companyId=loginUser.getCompanyId();
		}
		 String hql = "from DocumentType where companyId=? " ;
		 List<DocumentType> list = this.findAll(hql, companyId);
		 for (DocumentType documentType : list) {
			 String str = documentType.getDocBm();
			 if(str!=null && !str.equals("")){
				 String[] temp = str.split(",");
				 for (int i = 0; i < temp.length; i++) {
					if(Integer.parseInt(temp[i])==groupId){
						result.add(documentType);
						break;
					}
				}
			 }
		 }
		 return result;
	}
  
//	public List<InfoType> findBySysTag(String infoType){
//		String hql = "from InfoType where infoType = ? and sysTag != -1 and isDelete = 0";
//		return super.getSession().createQuery(hql).setString(0, infoType).list();
//	}
	
	/**
	 * 功能：根据一级公文分类ID查找二级公文分类列表，
	 * 作者：jiayongqiang
	 * 参数：infoTypeId:一级公文分类，gongwenType:收/发文
	 * 输出：
	 */
	public List<DocumentType> getDocumentTypesbyInfoTypeId(int infoTypeId,Integer gongwenType){
		String hql = "";
		if(gongwenType == null){
			gongwenType = 1;
		}
		if( gongwenType ==2){
			hql = " categoryId = ? and gongwenType = ?";
		}else{
			hql = " categoryId = ? and (gongwenType is null or gongwenType = ?)";
		}
		return super.findAll(hql, infoTypeId,gongwenType);
	}
	
	/**
	 * 功能：根据公文类型名称查找公文类型
	 * 作者：jiayongqiang
	 * 参数：
	 * 输出：
	 */
	public DocumentType getDocumentTypeByDocName(String docName){
		String hql = "doctypeName = ?";
		return super.findOne(hql, docName);
	}
	
	public List<DocumentType> findByFormId(int formId){
	    String hql = "formId = ?";
	    return super.findAll(hql, formId);
	}
	
	/**获取公文的收/发标识
	 * @param docTypeId
	 * @return
	 */
	public int getDocumentTypeFlag(int docTypeId){
		String hql = "select gongwenType from DocumentType where doctypeId = ?";
		Integer flag = super.findUnique(hql, docTypeId);
		if(flag == null){
			flag = 1;
		}
		return flag;
	}
	
	/**根据公文类型删除节点
	 * @param docTypeId
	 */
	public void deleteNodesByDocumentType(int docTypeId){
		String hql = "delete from NodeFormAttribute where processAttribute.id = ?";
		super.bulkDelete(hql, docTypeId*-1);
	}
}
