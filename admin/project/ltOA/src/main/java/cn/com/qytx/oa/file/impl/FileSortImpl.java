package cn.com.qytx.oa.file.impl;


import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.qytx.oa.file.dao.FileSortDao;
import cn.com.qytx.oa.file.domain.FileSort;
import cn.com.qytx.oa.file.service.IFileSort;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.PageRequest;
import cn.com.qytx.platform.base.service.impl.BaseServiceImpl;


/**
 * 
 * @Description:  [文件类别IMPL]   
 * @Author:       [xcj]   
 * @CreateDate:   [2013-3-12 下午03:51:50]   
 * @UpdateUser:   [xcj]   
 * @UpdateDate:   [2012-3-12 下午03:51:50]   
 * @UpdateRemark: [说明本次修改内容]  
 * @Version:      [v1.0]
 */
@Service
@Transactional
public class FileSortImpl extends BaseServiceImpl<FileSort> implements IFileSort,Serializable {
	/**短信发送主表dao */
	@Autowired
    FileSortDao  fileSortDao;

	/**
	 * 
	 * @Title: save
	 * @Description: TODO(保存文件夹类型) 
	 * @param fileSort
	 * @return void    返回类型
	 */
	public void save(FileSort fileSort) {
		fileSortDao.saveOrUpdate(fileSort);
		
	}

	/**
	 * 
	 * @Title: findAllFileSort
	 * @Description: TODO(返回文件夹类型) 
	 * @param parentId
	 * @return List    返回类型
	 */
	public List<FileSort>findAllFileSort(String parentId) {
		
		return fileSortDao.findAllFileSort(parentId);
	}
	/**
	 * 
	 * @Title: findAllFileSorts
	 * @Description: TODO(返回文件夹类型) 
	 * @param page
	 * @return List    返回类型
	 */
	
	public Page<FileSort> findAllFileSorts(PageRequest page) {
		
		return fileSortDao.findAllFileSorts(page);
	}
	/**
	 * 
	 * @Title: findByFileSortId
	 * @Description: TODO(查找文件夹类型) 
	 * @param fileSortId
	 * @return FileSort    返回类型
	 */
	public FileSort findByFileSortId(int fileSortId) {
		
		return fileSortDao.findByFileSortId(fileSortId);
	}
	/**
	 * 
	 * @Title: findAllByFileSortId
	 * @Description: TODO(查找文件夹类型) 
	 * @param fileSortId
	 * @return List<FileSort>   返回类型
	 */
	public List<FileSort> findAllByFileSortId(int fileSortId) {
		// TODO Auto-generated method stub
		return fileSortDao.findAllByFileSortId(fileSortId);
	}
	/**
	 * 
	 * @Title: findByFileSortName
	 * @Description: TODO(查找文件夹类型) 
	 * @param findByFileSortName
	 * @param userId
	 * @param path
	 * @return FileSort   返回类型
	 */
	public FileSort findByFileSortName(String findByFileSortName,int userId,Integer path ,String sortType) {
		// TODO Auto-generated method stub
		return fileSortDao.findByFileSortName(findByFileSortName,userId,path,sortType);
	}
	/**
	 * 
	 * @Title: findByFileSortName
	 * @Description: TODO(查找文件夹类型) 
	 * @param sortNo,sortName,sortId,lastUpdateUserId,timeStamp
	 * @return FileSort   返回类型
	 */
	public void updateFileSort(String sortNo, String sortName, Timestamp timeStamp,int lastUpdateUserId,int sortId) {
		fileSortDao.updateFileSort(sortNo, sortName,timeStamp, lastUpdateUserId ,sortId);
		
	}
	/**
	 * 
	 * @Title: findFileSortByLikeId
	 * @Description: TODO(查找文件夹类型) 
	 * @param sortId
	 * @return List<FileSort>   返回类型
	 */
	public List<FileSort> findFileSortByLikeId(int sortId) {
		// TODO Auto-generated method stub
		return fileSortDao.findFileSortByLikeId(sortId);
	}
	/**
	 * 
	 * @Title: findAllFileContent
	 * @Description: TODO(查找文件) 
	 * @param dataPrivHql
	 * @return List<FileSort>   返回类型
	 */
	public List<FileSort> findAllFileContent(String dataPrivHql) {
		
		return fileSortDao.findAllFileContent(dataPrivHql);
	}
	/**
	 * 
	 * 功能：
	 * @param createUser
	 * @return
	 */
	public List<FileSort> findAllFileContentNotDataPriv(int createUser,int fileSortType,int type) {
		// TODO Auto-generated method stub
		return fileSortDao.findAllFileContentNotDataPriv(createUser,fileSortType,type);
	}

	/**
	 * 
	 * 功能：查找文件夹判断是否重复
	 * @param sortName
	 * @param parentId
	 * @param id
	 * @return
	 */
	public List<FileSort> findFileSortByParentIdNotMine(String sortName,Integer parentId,Integer id){
		return fileSortDao.findFileSortByParentIdNotMine(sortName,parentId,id);
	}
    public List<FileSort> findSortByParendId(int parentId,int sortType,Integer type) {
	    // TODO Auto-generated method stub
	    return fileSortDao.findSortByParentId(parentId,sortType,type);
    }
	
	}

