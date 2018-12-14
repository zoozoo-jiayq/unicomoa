package cn.com.qytx.cbb.file.dao;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Repository;

import cn.com.qytx.cbb.file.domain.Attachment;
import cn.com.qytx.platform.base.dao.BaseDao;

@Repository("attachmentDao")
public class AttachmentDao extends BaseDao<Attachment,Integer> implements Serializable{

	public List<Attachment> getAttachmentsByIds(String data) {
		String ids = data;
		if(ids!=null&&!ids.equals("")){
			if(ids.substring(0, 1).equals(",")){
				ids = ids.substring(1, ids.length()-1);
			}	
		}
		String hql = "id in ("+ids+") and isDelete=0";
		return findAll(hql,null);
	}
	
	public List<Attachment> findByFileName(String fileName){
		String hql = "attachName = ?";
		return findAll(hql, fileName);
	}

}
