package cn.com.qytx.cbb.file.service;

import java.io.Serializable;
import java.util.List;

import cn.com.qytx.cbb.file.domain.Attachment;
import cn.com.qytx.platform.base.service.BaseService;

public interface IAttachment extends BaseService<Attachment>,Serializable {

	
	/**
	 * 根据附件ID获取附件的基本信息
	 * @param attachmentId
	 * @return 附件实体
	 */
	public Attachment getAttachment(int attachmentId);
	
	/**
	 * 根据多个id获得附件列表
	 * @param ids
	 * @return
	 */
	public List<Attachment> getAttachmentsByIds(String ids);
	/**
	 * 获取附件显示的 编辑页面的html 预览和 删除
	 * @param ids
	 * @return
	 */
	String getAttachmentEditHtml(String ids);
	/**
	 * 获取附件显示的 查看页面的html 预览和下载
	 * @param ids
	 * @return
	 */
	String getAttachmentViewHtml(String ids);
	
	/**
	 * 功能：检查文件名是否重复，
	 * 作者：jiayongqiang
	 * 参数：
	 * 输出：true:重复；false：不重复
	 */
	boolean checkExisting(String fileName);

	/**
	 * 获取附件显示的 查看页面的html  下载
	 * @param ids
	 * @return
	 */
	public String getAttachmentViewDownHtml(String appendix);

}
