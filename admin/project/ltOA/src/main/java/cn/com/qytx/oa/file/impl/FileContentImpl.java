package cn.com.qytx.oa.file.impl;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.qytx.oa.file.dao.FileContentDao;
import cn.com.qytx.oa.file.domain.FileContent;
import cn.com.qytx.oa.file.service.IFileContent;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.PageRequest;
import cn.com.qytx.platform.base.service.impl.BaseServiceImpl;

/**
 * 
 * @Description: [文件IMPL]
 * @Author: [xcj]
 * @CreateDate: [2013-3-12 下午04:51:50]
 * @UpdateUser: [xcj]
 * @UpdateDate: [2012-3-12 下午04:51:50]
 * @UpdateRemark: [说明本次修改内容]
 * @Version: [v1.0]
 */
@Service
@Transactional
public class FileContentImpl extends BaseServiceImpl<FileContent> implements IFileContent,Serializable {
	/** 短信发送主表dao */
	@Autowired
	FileContentDao fileContentDao;



	/**
	 * 
	 * @Title: getFileConentListById
	 * @Description: TODO(得到文件记录)
	 * @param sortId
	 * @return void 返回类型
	 */

	public Page<FileContent> getFileConentListById(PageRequest page, int sortId,
			String subject, String createUserId,
			Timestamp starttime, Timestamp endTime,Integer userId,int type) {
		// TODO Auto-generated method stub
		return fileContentDao.getFileConentListById(page, sortId, subject,
				createUserId,starttime,endTime,userId,type);
	}

	/**
	 * 根据contentId得到文件记录
	 */
	public FileContent getFileConentById(int contentId) {

		return fileContentDao.findOne(contentId);
	}

	/**
	 * 
	 * 功能：得到附件文件
	 * 
	 * @param sortId
	 * @return
	 */
	public List<FileContent> getAttachmentFile(int sortId) {

		return fileContentDao.getAttachmentFile(sortId);
	}

	/**
	 * 
	 * 功能：查询具有访问权限的所有的文件
	 * 
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
			String dataPrivHql) {
		return fileContentDao.getFileConentListAll(page, sortId, subject,
				contentNo, createUserId, content, contentTwo, contentThree,
				attachmentDesc, attachmentName, attachmentContent, starttime,
				endTime, dataPrivHql);

	}
  /**
   * 
   * 功能：
   * @param page
   * @param createUserId
   * @return
   */
	public Page<FileContent> getFilenNoCheckConentListAll(PageRequest page,
			String createUserId) {
		
		return fileContentDao.getFilenNoCheckConentListAll(page, createUserId);
	}

	/**
	 * 
	 * 功能：签阅文件
	 */
public void updateFileCheck(int isCheck,int contentId) {
	
	fileContentDao.updateFileCheck(isCheck,contentId);
}
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

	public void updateFile(String subject, String content, String attachment,
			String ContentNo, String keyword, String attachmentDesc,
			int contentId) {
		fileContentDao.updateFile(subject, content, attachment, ContentNo, keyword, attachmentDesc, contentId);
		
	}

	public void deleteFilesBySortId(int sortId){
		fileContentDao.deleteFilesBySortId(sortId);
	}
}