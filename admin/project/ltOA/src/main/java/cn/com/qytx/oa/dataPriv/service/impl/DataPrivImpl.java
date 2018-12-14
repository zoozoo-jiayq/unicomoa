package cn.com.qytx.oa.dataPriv.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.qytx.oa.dataPriv.dao.DataPrivDao;
import cn.com.qytx.oa.dataPriv.domain.DataPriv;
import cn.com.qytx.oa.dataPriv.service.IDataPriv;
import cn.com.qytx.platform.base.service.impl.BaseServiceImpl;

/**
 * 功能:数据权限Impl 版本: 1.0 开发人员: 李华伟 创建日期: 2013-3-22 修改日期: 2013-3-22 修改列表:
 */
@Service("dataPrivService")
@Transactional
public class DataPrivImpl extends BaseServiceImpl<DataPriv> implements
		IDataPriv {
	/**
	 * 通讯薄联系人Dao
	 */
	@Resource(name = "dataDao")
	DataPrivDao dataPrivDao;

	/**
	 * 获取查询数据权限额外的hql语句
	 * 
	 * @param vidStr
	 *            映射主键字符串
	 * @param moudleName
	 *            模块名称 不能为空
	 * @param subMoudle
	 *            子模块名称 可为空
	 * @param userId
	 *            用户ID
	 * @param groupId
	 *            用户所属群组Id
	 * @param roleId
	 *            用户所属角色
	 * @return String
	 */
	public String getDataPrivHql(String vidStr, String moudleName,
			String subMoudle, Integer userId, Integer groupId, Integer roleId) {
		return dataPrivDao.getDataPrivHql(vidStr, moudleName, subMoudle, userId, groupId, roleId);
	}

	/**
	 * 获取查询数据权限额外的hql语句
	 * 
	 * @param vidStr
	 *            映射主键字符串
	 * @param moudleName
	 *            模块名称 不能为空
	 * @param subMoudle
	 *            子模块名称 可为空
	 * @param userId
	 *            用户ID
	 * @param groupId
	 *            用户所属群组Id
	 * @param roleIdList
	 *            用户所属角色集合
	 * @return String
	 */
	public String getDataPrivHql(String vidStr, String moudleName,
			String subMoudle, Integer userId, Integer groupId,
			List<Integer> roleIdList) {
		return dataPrivDao.getDataPrivHql(vidStr, moudleName, subMoudle, userId, groupId, roleIdList);
	}

	/**
	 * 获取查询数据权限额外的hql语句
	 * 
	 * @param vidStr
	 *            映射主键字符串
	 * @param moudleName
	 *            模块名称 不能为空
	 * @param subMoudle
	 *            子模块名称 可为空
	 * @param userId
	 *            用户ID
	 * @param groupId
	 *            用户所属群组Id
	 * @param roleIdList
	 *            用户所属角色集合
	 * @return String
	 */
	public List getFileSortNedPriv(String vidStr, String moudleName,
			String subMoudle, Integer userId, Integer groupId,
			List<Integer> roleIdList) {
		return dataPrivDao.getFileSortNedPriv(vidStr, moudleName, subMoudle, userId, groupId, roleIdList);

	}

	/**
	 * 返回权限实体类
	 * 
	 * @return DataPriv
	 */

	public DataPriv getDataPrivByRefId(Integer refId, String moduleName) {
		return (DataPriv) dataPrivDao.getDataPrivByRefId(refId, moduleName);
	}

	/**
	 * 修改权限实体类
	 * 
	 * @return void
	 */

	public int updateDataPriv(Integer refId, String moduleName, String roleIds,
			String roleNames, String userIds, String userNames,
			String groupIds, String GroupName) {
		// TODO Auto-generated method stub
		return dataPrivDao.updateDataPriv(refId, moduleName, roleIds, roleNames, userIds, userNames, groupIds, GroupName);
	}

	/**
	 * 删除权限
	 * 
	 * @return void
	 */
	public void deleteFileSort(String refId, String moduleName, String roleIds,
			String groupIds, String userIds, String roleNames,
			String groupNames, String userNames) {

		dataPrivDao.deleteFileSort(refId, moduleName, roleIds, groupIds,
				userIds, roleNames, groupNames, userNames);
	}

	/**
	 * 得到权限列表
	 */
	public List<DataPriv> getFileSortById(String finalFileSortIds,
			String finalFileSortIdPrivs) {

		return dataPrivDao.getFileSortById(finalFileSortIds, finalFileSortIdPrivs);
	}

	/**
	 * 根据人员得到权限列表
	 */

	public List<DataPriv> getFileSortByPerson(String userIds) {

		return dataPrivDao.getFileSortByPerson(userIds);
	}

	/**
	 * 
	 * 功能：根据id得到一个权限列表
	 * 
	 * @param fileSortId
	 * @return
	 */
	public List<DataPriv> getChildFileSortById(int fileSortId) {
		// TODO Auto-generated method stub
		return dataPrivDao.getChildFileSortById(fileSortId);
	}

	/**
	 * 
	 * 功能：
	 * 
	 * @param dataprivid
	 * @param roleIds
	 * @param roleNames
	 * @param userIds
	 * @param userNames
	 * @param groupIds
	 * @param GroupName
	 * @return
	 */
	public int updateDataPrivById(Integer dataprivid, String roleIds,
			String roleNames, String userIds, String userNames,
			String groupIds, String GroupName) {

		return dataPrivDao.updateDataPrivById(dataprivid, roleIds, roleNames,
				userIds, userNames, groupIds, GroupName);
	}
	

    /**
     * 根据ID获取对象
     * 功能：
     * @return
     */
	public DataPriv getDataPrivById(Integer id) {
		return dataPrivDao.getDataPrivById(id);
	}
}
