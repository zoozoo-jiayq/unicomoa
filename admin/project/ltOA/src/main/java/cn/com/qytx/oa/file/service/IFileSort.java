package cn.com.qytx.oa.file.service;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import cn.com.qytx.oa.file.domain.FileSort;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.PageRequest;
import cn.com.qytx.platform.base.service.BaseService;
/**
 * 
 * 功能:文件夹的接口
 * 版本: 1.0
 * 开发人员: 徐长江
 * 创建日期: 2013-3-21
 * 修改日期: 2013-3-21
 * 修改列表:
 */

public interface IFileSort extends BaseService<FileSort>,Serializable{
	/**
	 * 
	 * 功能：根据id得到文件夹列表
	 * @param parentId
	 * @return List<FileSort>
	 */
	public List<FileSort> findAllFileSort(String parentId);
 /**
  * 
  * 功能：根据权限得到文件夹列表
  * @param dataPrivHql
  * @return List<FileSort>
  */
	public List<FileSort> findAllFileContent(String dataPrivHql);
	
	/**
	 * 
	 * 功能：得到文件夹列表
	 * @param page
	 * @return Page<FileSort>
	 */
	public Page<FileSort> findAllFileSorts(PageRequest page);

   /**
    * 
    * 功能：保存文件夹
    * @param fileSort
    */
	public void save(FileSort fileSort);

   /**
    * 
    * 功能：根据id得到文件夹
    * @param fileSortId
    * @return
    */
	public FileSort findByFileSortId(int fileSortId);
  /**
   * 
   * 功能：根据id得到文件夹的列表
   * @param fileSortId
   * @return List<FileSort>
   */
	public List<FileSort> findAllByFileSortId(int fileSortId);
 /**
  * 
  * 功能：根据文件夹的名称，得到文件夹
  * @param findByFileSortName
  * @param userId
  * @param partentId
  * @return FileSort
  */
	
	public FileSort findByFileSortName(String findByFileSortName,int userId,Integer partentId ,String sortType);
	
/**
 * 
 * 功能:根据条件修改文件夹
 * @param sortNo
 * @param sortName
 * @param timeStamp
 * @param lastUpdateUserId
 * @param sortId
 */
	public void updateFileSort(String sortNo,String sortName,Timestamp timeStamp,int lastUpdateUserId,int sortId);
   /**
    * 
    * 功能：根据id得到子文件夹的列表
    * @param sortId
    * @return
    */
	public List<FileSort> findFileSortByLikeId(int sortId);
	/**
	 * 
	 * 功能：fileSortType:0知识库管理；1新蔡教育
	 * @param createUser
	 * @return
	 */
	public List<FileSort> findAllFileContentNotDataPriv(int createUser,int fileSortType,int type);
	
	/**
	 * 
	 * 功能：修改文件判断是否重复
	 * @param sortName
	 * @param parentId
	 * @param id
	 * @return
	 */
	public List<FileSort> findFileSortByParentIdNotMine(String sortName,Integer parentId,Integer id);
	
	/**
	 * 功能：返回1级文件夹
	 * @param
	 * @return
	 * @throws   
	 */
	public List<FileSort> findSortByParendId(int parentId,int sortType,Integer type);
}