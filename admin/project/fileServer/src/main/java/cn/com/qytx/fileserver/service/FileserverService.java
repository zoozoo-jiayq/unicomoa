package cn.com.qytx.fileserver.service;

import java.util.Map;

/**
 * 功能: 文件服务器接口 
 * 版本: 1.0
 * 开发人员: zyf
 * 创建日期: 2015年4月29日
 * 修改日期: 2015年4月29日
 * 修改列表:
 */
public interface FileserverService {
	/**
	 * 保存附件对象 
	 */
	public Integer saveAttachment(String filePath,String fileName,long fileSize,int companyId,int userId);
	
	/**
	 * 功能：根据主键id获得文件信息
	 * @param attachmentId
	 * @return
	 */
	public Map<String, Object> getAttachment(int attachmentId);
}
