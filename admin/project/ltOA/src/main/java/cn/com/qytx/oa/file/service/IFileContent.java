package cn.com.qytx.oa.file.service;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import cn.com.qytx.oa.file.domain.FileContent;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.PageRequest;
import cn.com.qytx.platform.base.service.BaseService;
/**
 * 
 * 功能:文件的接口
 * 版本: 1.0
 * 开发人员: 徐长江
 * 创建日期: 2013-3-21
 * 修改日期: 2013-3-21
 * 修改列表:
 */
public interface IFileContent extends BaseService<FileContent>,Serializable{
	
	
	/**
	 * 
	 * 功能：根据条件查找文件
	 * @param page
	 * @param sortId
	 * @param subject
	 * @param createUserId
	 * @param starttime
	 * @param endTime
	 * @return Page<FileContent>
	 */
	
 public Page<FileContent> getFileConentListById(PageRequest page,int sortId,String subject,String createUserId,Timestamp starttime,Timestamp endTime,Integer userId,int type);
 
/**
 * 
 * 功能：根据文件的id得到文件
 * @param contentId
 * @return FileContent
 */
	
  public FileContent getFileConentById(int contentId);
  /**
   * 
   * 功能：根据文件夹得到文件
   * @param sortId
   * @return
   */
	public List<FileContent> getAttachmentFile(int sortId);
	/**
	 * 
	 * 功能：
	 * @param page
	 * @param createUserId
	 * @return
	 */
	public Page<FileContent> getFilenNoCheckConentListAll(PageRequest page, String createUserId);
	/**
	 * 
	 * 功能：签阅文件
	 * @param isCheck
	 * @param contentId
	 */
	public void updateFileCheck(int isCheck,int contentId);
    /**
     * 
     * 功能：修改文件
     * @param subject
     * @param content
     * @param attachment
     * @param ContentNo
     * @param keyword
     * @param attachmentDesc
     * @param contentId
     */
	public void updateFile(String subject,String content,String attachment,String ContentNo,String keyword,String attachmentDesc,int contentId);
	
	
	public void deleteFilesBySortId(int sortId);
}