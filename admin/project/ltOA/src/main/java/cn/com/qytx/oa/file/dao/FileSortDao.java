/**
 * 
 */
package cn.com.qytx.oa.file.dao;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import cn.com.qytx.oa.file.domain.FileSort;
import cn.com.qytx.platform.base.application.TransportUser;
import cn.com.qytx.platform.base.dao.BaseDao;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.PageRequest;


/**
 * 
 * 功能:文件夹的dao类
 * 版本: 1.0
 * 开发人员: 徐长江
 * 创建日期: 2013-3-21
 * 修改日期: 2013-3-21
 * 修改列表:
 */
@Component
public class FileSortDao extends BaseDao<FileSort, Integer> implements Serializable{
   /**
	 * 描述含义
	 */
	private static final long serialVersionUID = 1L;

/**
    * 
    * 功能：根据id得到文件夹列表
    * @param parentId
    * @return List<FileSort>
    */
 
	public List<FileSort> findAllFileSort(String parentId)
	{        
		
		
	    StringBuilder hql = new StringBuilder();
        hql.append("select fileSort from FileSort fileSort where fileSort.isDelete = 0 and 1=1 and companyId="+TransportUser.get().getCompanyId());
      
 
	   return super.find(hql.toString());
	}
/**
 * 
 * 功能：根据权限得到文件夹的列表
 * @param dataPrivHql
 * @return  List<FileSort>
 */
 
	public List<FileSort> findAllFileContent(String dataPrivHql)
	{        
		
		
	    StringBuilder hql = new StringBuilder();
        hql.append("select fileSort from FileSort fileSort where fileSort.isDelete = 0 and 1=1 and "+dataPrivHql+" and companyId="+TransportUser.get().getCompanyId());
	   return super.find(hql.toString());
	}
   /**
    * 
    * 功能：根据用户得到文件列表
    * @param createUser
    * @return
    */
		public List<FileSort> findAllFileContentNotDataPriv(int createUser,int fileSortType,int type)
		{        
		    StringBuilder hql = new StringBuilder();
		    
		    hql.append("select f from FileSort f where 1=1 and f.isDelete=0  and f.sortType = "+fileSortType+" and type="+type+" and companyId="+TransportUser.get().getCompanyId()+" order by f.sortNo asc");
		   return super.find(hql.toString());
		}
  /**
   * 
   * 功能：获得文件夹列表
   * @param page
   * @return
   */
 
	public  Page<FileSort> findAllFileSorts(PageRequest page)
	{        	
	 String hql="select f from FileSort f where f.isDelete = 0 and parentId='"+0+"' and companyId="+TransportUser.get().getCompanyId()+" order by f.sortNo asc";
     return super.findByPage(page,hql);
    
	}
  /**
   * 
   * 功能：根据文件夹id得到文件夹
   * @param findByFileSortId
   * @return
   */
	public FileSort findByFileSortId(int findByFileSortId) {
		
		return super.findOne(findByFileSortId);
	}
   /**
    * 
    * 功能：根据条件查找文件类别
    * @param findByFileSortName
    * @param userId
    * @param parentId
    * @return
    */
	public FileSort findByFileSortName(String findByFileSortName,int userId,Integer parentId ,String sortType) {
		List<Object> list = new ArrayList<Object>();
		list.add(findByFileSortName);
		list.add(userId);
		list.add(parentId);
		String str = "select fileSort from FileSort fileSort where fileSort.sortName=? and fileSort.isDelete=0 and fileSort.createUserId=? and fileSort.parentId=? and companyId="+TransportUser.get().getCompanyId();
		if(sortType != null){
			list.add(sortType);
			str +=" and fileSort.sortType=? ";
		}
		return (FileSort) super.findUnique(str,list.toArray());
	}
 /**
  * 
  * 功能：根据id得到子文件夹
  * @param findByFileSortId
  * @return List<FileSort>
  */
	public List<FileSort> findAllByFileSortId(int findByFileSortId) {
		
		return super.find("select fileSort from FileSort fileSort where fileSort.isDelete = 0 and fileSort.parentId=? and companyId="+TransportUser.get().getCompanyId(), findByFileSortId);


}
/**
 * 
 * 功能：修改文件夹类
 * @param sortNo
 * @param sortName
 * @param timeStamp
 * @param lastUpdateUserId
 * @param sortId
 */
	public void updateFileSort(String sortNo,String sortName,Timestamp timeStamp,int lastUpdateUserId,int sortId)
	{
		List<Object> list = new ArrayList<Object>();
		list.add(sortNo);
		list.add(sortName);
		list.add(timeStamp);
		list.add(lastUpdateUserId);
		list.add(sortId);
     super.bulkUpdate("update FileSort set sortNo=? ,sortName=? ,lastUpdateTime=?,lastUpdateUserId=? where sortId=?" ,list.toArray());
	}
   /**
    * 
    * 功能：获取子文件夹列表
    * @param sortId
    * @return List<FileSort>
    */
	public  List<FileSort> findFileSortByLikeId(int sortId) {
		String param="%/"+sortId+"/%";
		String hql="selelct fileSort from FileSort fileSort where fileSort.isDelete = 0 and fileSort.path like ? and companyId="+TransportUser.get().getCompanyId();
		return  super.find(hql, param);
	}
	
	/**
	 * 
	 * 功能：修改文件判断是否重复
	 * @param sortName
	 * @param parentId
	 * @param id
	 * @return
	 */
	public List<FileSort> findFileSortByParentIdNotMine(String sortName,Integer parentId,Integer id){
		return super.find("select f from FileSort f where f.isDelete = 0 and f.sortName =? and f.parentId=? and f.sortId !=?", sortName,parentId,id);
	}

	public List<FileSort> findSortByParentId(int parentId,int sortType,Integer type){
		String hql = "select f from FileSort f where f.isDelete = 0 and f.parentId = "+parentId+" and f.sortType =  "+sortType +" and f.type="+type;
		return super.find(hql);
	}
	
}
