package cn.com.qytx.fileserver.impl;

import java.util.Map;

import cn.com.qytx.fileserver.dao.BaseDao;
import cn.com.qytx.fileserver.service.FileserverService;

/**
 * 功能: 文件服务器实现 
 * 版本: 1.0
 * 开发人员: zyf
 * 创建日期: 2015年4月29日
 * 修改日期: 2015年4月29日
 * 修改列表:
 */
public class FileserverImpl implements FileserverService {
	
	private BaseDao baseDao;

	public BaseDao getBaseDao() {
		return baseDao;
	}

	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}
	
	/**
	 * 保存附件对象 
	 */
	final String INSERT_SQL="insert into tb_om_attachment (attach_file,attach_name,attach_size,company_id,create_user_id,is_delete) values (?,?,?,?,?,0)";
	public Integer saveAttachment(String filePath,String fileName,long fileSize,int companyId,int userId){
		baseDao.saveEntity(INSERT_SQL, filePath,fileName,fileSize,companyId,userId);
		Map<String,Object> map = baseDao.findMapBySql("select vid from tb_om_attachment where company_id = ? and attach_file=? ", companyId,filePath);
		if(map!=null && map.get("vid")!=null){
			return Integer.valueOf(map.get("vid").toString());
		}else{
			return null;
		}
	}
	
	/**
	 * 功能：根据主键id获得文件信息
	 * @param attachmentId
	 * @return
	 */
	public Map<String, Object> getAttachment(int attachmentId){
		return baseDao.findMapBySql("select attach_file,attach_name from tb_om_attachment where 1=? and vid = ? ", 1,attachmentId);
	}
}
