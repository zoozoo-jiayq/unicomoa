/**
 * 
 */
package cn.com.qytx.oa.file.dao;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import cn.com.qytx.oa.file.domain.FileContent;
import cn.com.qytx.platform.base.application.TransportUser;
import cn.com.qytx.platform.base.dao.BaseDao;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.PageRequest;

/**
 * 
 * 功能:文件的dao类
 * 版本: 1.0
 * 开发人员: 徐长江
 * 创建日期: 2013-3-21
 * 修改日期: 2013-3-21
 * 修改列表:
 */
@Component
public class FileContentDao extends BaseDao<FileContent, Integer> implements Serializable{

	
  /**
	 * 描述含义
	 */
	private static final long serialVersionUID = 1L;

/**
   * 
   * 功能：查找文件
   * @param page
   * @param sortId
   * @param subject
   * @param createUserId
   * @param starttime
   * @param endTime
   * @return Page<FileContent>
   */
	public Page<FileContent> getFileConentListById(PageRequest page,int sortId,String subject,String createUserId,Timestamp starttime,Timestamp endTime,Integer userId,int type)
	
	{
		  StringBuilder hql = new StringBuilder();
          hql.append("select fileContent from FileContent fileContent where fileContent.isDelete = 0  and 1=1 and companyId="+TransportUser.get().getCompanyId());
          StringBuilder paramHql = new StringBuilder();
   
           List<Object> params = new ArrayList<Object>();      
           
           /**文件列表*/
          if(sortId!=0) {
               paramHql.append(" and fileContent.fileSort.sortId = ?");
               params.add(sortId);
          }
           /**开始时间*/
           if (null != starttime)
           {
               paramHql.append(" and fileContent.createTime >= ?");
               params.add(starttime);
           }
           
           /**
            * 文件类型
            */
           if(type == 1){
        	   paramHql.append(" and fileContent.type = 1 ");
           }else if(type == 2){
        	   paramHql.append(" and fileContent.type = 2 and (fileContent.userIds like '%,"+userId+",%' or fileContent.userIds like '"+userId+",%') ");
           }
           
           /**开始时间*/
           if (null != endTime )
           {
               paramHql.append(" and fileContent.createTime <= ?");
               params.add(endTime);
           }
           
           /**标题*/
           if (null != subject && !"".equals(subject))
           {
        	   subject = subject.replaceAll("%", "/%");
        	   subject = subject.replaceAll("_", "/_");
               paramHql.append(" and fileContent.subject like ?   escape '/'  ");
               params.add("%" + subject + "%");
           }

         
           /**接受电话*/
           if (null !=createUserId && !"".equals(createUserId))
           {
               paramHql.append(" and fileContent.createUserId=? ");
               params.add(Integer.parseInt(createUserId));
           }
           
           if (paramHql.length() > 0)
           {
              
               hql.append(paramHql);
               return super.findByPage(page, hql.toString()+" order by fileContent.createTime desc", params.toArray());
           }  
           return super.findByPage(page, hql.toString()+" order by fileContent.createTime desc");
	}
	
	
	
	
	/**
	 * 
	 * 功能：查询具有访问权限的所有的文件
	 * @param page
	 * @param sortId
	 * @param subject
	 * @param contentNo
	 * @param createUserId
	 * @param content
	 * @param contentTwo
	 * @param contentThree
	 * @param attachmentDesc
	 * @param attachmentName
	 * @param attachmentContent
	 * @param starttime
	 * @param endTime
	 * @param dataPrivHql
	 * @return
	 */
		public Page<FileContent> getFileConentListAll(PageRequest page, int sortId,
				String subject, String contentNo, String createUserId,
				String content, String contentTwo, String contentThree,
				String attachmentDesc, String attachmentName,
				String attachmentContent, Timestamp starttime, Timestamp endTime,
				String dataPrivHql){
			
			
			  StringBuilder hql = new StringBuilder();
	          hql.append("select fileContent from FileContent fileContent where 1=1 and companyId="+TransportUser.get().getCompanyId());
	          StringBuilder paramHql = new StringBuilder();
	   
	           List<Object> params = new ArrayList<Object>();      
	           /**开始时间*/
	           if (null != starttime)
	           {
	               paramHql.append("and fileContent.createTime >= ?");
	               params.add(starttime);
	           }
	           
	           /**开始时间*/
	           if (null != endTime)
	           {
	               paramHql.append("and fileContent.createTime <= ?");
	               params.add(endTime);
	           }
	           
	           /**标题*/
	           if (null != subject && !"".equals(subject))
	           {
	               paramHql.append("and fileContent.subject like ? ");
	               params.add("%" + subject + "%");
	           }

	         
	           /**排序号*/
	           if (null != contentNo && !"".equals(contentNo))
	           {
	               paramHql.append("and fileContent.contentNo= ?");
	               params.add(contentNo);
	           }
	           /**接受电话*/
	           if (null !=createUserId && !"".equals(createUserId))
	           {
	               paramHql.append("and fileContent.createUserId=? ");
	               params.add(Integer.parseInt(createUserId));
	           }
	           
	           /**内容关键字*/
	           if (null !=content && !"".equals(content))
	           {
	               paramHql.append("and fileContent.keyWord like ?");
	               params.add("%" + content + "%");
	           }
	           /**内容关键字2*/
	           if (null !=contentTwo && !"".equals(contentTwo))
	           {
	               paramHql.append("and fileContent.keyWord like ?");
	               params.add("%" + contentTwo + "%");
	           }
	           /**内容关键字3*/
	           if (null !=contentThree && !"".equals(contentThree))
	           {
	               paramHql.append("and fileContent.keyWord like ?");
	               params.add("%" + contentThree + "%");
	           }
	           /**附件描述*/
	           if (null !=attachmentDesc && !"".equals(attachmentDesc))
	           {
	               paramHql.append("and fileContent.attachementDesc like ?");
	               params.add("%" + attachmentDesc + "%");
	           }
	           /**附件描述*/
	           if (null !=attachmentName && !"".equals(attachmentName))
	           {
	               paramHql.append("and fileContent.attachmentName like ?");
	               params.add("%" + attachmentName + "%");
	           }
	           /**附件描述*/
	           if (null !=attachmentContent && !"".equals(attachmentContent))
	           {
	               paramHql.append("and fileContent.attachmentName like ?");
	               params.add("%" + attachmentContent + "%");
	           }
	           if (paramHql.length() > 0)
	           {
	              
	               hql.append(paramHql);
	               return super.findByPage(page, hql.toString()+"and"+ dataPrivHql+" order by fileContent.createTime desc", params.toArray());
	           }  
	           return super.findByPage(page, hql.toString()+" and "+dataPrivHql+" order by fileContent.createTime desc");
		}

		/**
		 * 
		 * 功能：查询具有访问权限的所有的文件
		 * @param page
		 * @param sortId
		 * @param subject
		 * @param contentNo
		 * @param createUserId
		 * @param content
		 * @param contentTwo
		 * @param contentThree
		 * @param attachmentDesc
		 * @param attachmentName
		 * @param attachmentContent
		 * @param starttime
		 * @param endTime
		 * @param dataPrivHql
		 * @return
		 */
		public Page<FileContent> getFilenNoCheckConentListAll(PageRequest page, String createUserId){

	    String hql="select fileContent from FileContent fileContent where fileContent.isDelete=0 and fileContent.createUserId="+Integer.parseInt(createUserId)+" and companyId="+TransportUser.get().getCompanyId();
	    return super.findByPage(page, hql.toString()+" order by fileContent.createTime asc"); 
			
		}

         /**
          * 
          * 功能：根据文件夹得到文件
          * @param sortId
          * @return
          */
		public List<FileContent> getAttachmentFile(int sortId)
		
		{
			  StringBuilder hql = new StringBuilder();
	          hql.append("select fileContent from FileContent fileContent where isDelete = 0 and 1=1 and companyId="+TransportUser.get().getCompanyId());
	          StringBuilder paramHql = new StringBuilder();
	   
	           List<Object> params = new ArrayList<Object>();      
	           
	           /**文件夹id*/
	           if (sortId!=0)
	           {
	               paramHql.append(" and fileContent.fileSort.sortId = ?");
	               params.add(sortId);
	           }
	         
	           if (paramHql.length() > 0)
	           {
	              
	               hql.append(paramHql);
	               return super.find(hql.toString()+"order by fileContent.contentNo asc", params.toArray());
	             
	           }  
	           return super.find(hql.toString()+"order by fileContent.contentNo asc");
		}
	
	
	    /**
	     * 
	     * 功能：修改文件
	     * @param isCheck
	     * @param contentId
	     */
		public void updateFileCheck(int isCheck,int contentId)
		{
			 super.bulkUpdate("update FileContent fileContent set fileContent.isDelete=? where fileContent.contentId=?", isCheck,contentId);
		}
	
	   /**
	    * 
	    * 功能：
	    * @param subject
	    * @param content
	    * @param attachment
	    * @param ContentNo
	    * @param keyword
	    * @param attachmentDesc
	    * @param contentId
	    */
		public void updateFile(String subject, String content, String attachment,
				String ContentNo, String keyword, String attachmentDesc,
				int contentId) {
			 super.bulkUpdate("update FileContent fileContent set fileContent.subject=?,fileContent.content=?,fileContent.attachment=?,fileContent.contentNo=?,fileContent.keyWord=?,fileContent.attachmentDesc=? where fileContent.contentId=?",subject,content,attachment,ContentNo,keyword,attachmentDesc,contentId);
			
		}
		
		public void deleteFilesBySortId(int sortId){
			super.bulkUpdate("update FileContent fileContent set fileContent.isDelete =1 where fileContent.fileSort.sortId = ?",sortId);
		}
}
