package cn.com.qytx.oa.dataPriv.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import cn.com.qytx.oa.dataPriv.domain.DataPriv;
import cn.com.qytx.platform.base.dao.BaseDao;

/**
 * 功能:数据权限列表dao
 * 版本: 1.0
 * 开发人员: 李华伟
 * 创建日期: 2013-3-22
 * 修改日期: 2013-3-22
 * 修改列表:
 */
@Repository("dataDao")
public class DataPrivDao extends BaseDao<DataPriv, Integer> implements Serializable
{

    /**
	 * 描述含义
	 */
	private static final long serialVersionUID = 1L;

	/**
     * 功能：批量删除文件夹权限
     * @param refId 关联Id
     * @param moduleName 模块名称
     * @param roleIds 角色Id集合
     * @param groupIds 群组Id集合
     * @param userIds 人员Id集合
     * @param roleNames 角色名称集合
     * @param groupNames 群组名称集合
     * @param userNames 人员名称集合
     */
    public void deleteFileSort(String refId, String moduleName, String roleIds, String groupIds,
            String userIds, String roleNames, String groupNames, String userNames)
    {
        StringBuilder hql = new StringBuilder();
        hql.append("delete  DataPriv dataPriv where 1=1");
        StringBuilder paramHql = new StringBuilder();

        List<Object> params = new ArrayList<Object>();

        if (null != refId && !"".equals(refId))
        {
            paramHql.append("and dataPriv.refId= ?");
            params.add(Integer.parseInt(refId));
        }

        if (null != moduleName && !"".equals(moduleName))
        {
            paramHql.append("and dataPriv.moduleName = ?");
            params.add(moduleName);
        }

        /*
         * if (null != roleIds && !"".equals(roleIds))
         * {
         * paramHql.append("and dataPriv.roleIds= ? ");
         * params.add(roleIds);
         * }
         * if (null != groupIds && !"".equals(groupIds))
         * {
         * paramHql.append("and dataPriv.groupIds = ?  ");
         * params.add(groupIds);
         * }
         * if (null != userIds && !"".equals(userIds))
         * {
         * paramHql.append("and dataPriv.userIds= ?  ");
         * params.add(userIds);
         * }
         */

        if (null != roleNames && !"".equals(roleNames))
        {
            paramHql.append("and dataPriv.roleNames = ?");
            params.add(roleNames);
        }

        if (null != groupNames && !"".equals(groupNames))
        {
            paramHql.append("and dataPriv.groupNames = ?");
            params.add(groupNames);
        }
        if (null != userNames && !"".equals(userNames))
        {
            paramHql.append("and dataPriv.userNames = ?");
            params.add(userNames);
        }
        if (paramHql.length() > 0)
        {

            hql.append(paramHql);

        }
        super.bulkDelete(hql.toString(), params.toArray());
    }
    /**
     * 
     * 功能：返回一个列表
     * @param fileSortId
     * @return
     */
	public List<DataPriv> getChildFileSortById(int fileSortId) {
		 
		List<DataPriv> list=super.findAll(" refId=?",fileSortId);
		return list;
	}
	
	/**
	 * 
	 * 功能：
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
		
		return super.bulkUpdate("update DataPriv set roleIds=?,roleNames=?,userIds=?,userNames=?,groupIds=?,groupNames=? where id=?",roleIds,roleNames, userIds,userNames,groupIds,GroupName,dataprivid);
	}
	
    /**
     * 根据ID获取对象
     * 功能：
     * @return
     */
	public DataPriv getDataPrivById(Integer id) {
		return this.findOne(id);
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
	 * @param roleId
	 *            用户所属角色
	 * @return String
	 */
	public String getDataPrivHql(String vidStr, String moudleName,
			String subMoudle, Integer userId, Integer groupId, Integer roleId) {
		if (StringUtils.isEmpty(vidStr) || StringUtils.isEmpty(moudleName)) {
			return null;
		}

		if (null == userId || null == groupId || null == roleId) {
			return null;
		}

		StringBuffer sbHql = new StringBuffer();
		sbHql.append(vidStr + " in (");
		sbHql.append("select dp.refId from DataPriv dp where (dp.userIds like '%,"
				+ userId + ",%'");
		sbHql.append(" or dp.groupIds like '%," + groupId + ",%'");
		sbHql.append(" or dp.roleIds like '%," + roleId + ",%')");
		sbHql.append(" and dp.moduleName = '" + moudleName + "'");
		if (!StringUtils.isEmpty(subMoudle)) {
			sbHql.append(" and dp.subModuleName = '" + subMoudle + "'");
		}
		sbHql.append(")");
		return sbHql.toString();
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
		if (StringUtils.isEmpty(vidStr) || StringUtils.isEmpty(moudleName)) {
			return null;
		}

		if (null == userId || null == groupId || null == roleIdList) {
			return null;
		}

		StringBuffer sbHql = new StringBuffer();
		sbHql.append(" " + vidStr + " in (");
		sbHql.append("select dp.refId from DataPriv dp where (dp.userIds like '%,"
				+ userId + ",%'");
		sbHql.append(" or dp.groupIds like '%," + groupId + ",%'");

		for (Integer roleId : roleIdList) {
			sbHql.append(" or dp.roleIds like '%," + roleId + ",%'");
		}
		sbHql.append(") and dp.moduleName = '" + moudleName + "'");
		if (!StringUtils.isEmpty(subMoudle)) {
			sbHql.append(" and dp.subModuleName = '" + subMoudle + "'");
		}
		sbHql.append(")");
		return sbHql.toString();
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
		if (StringUtils.isEmpty(vidStr) || StringUtils.isEmpty(moudleName)) {
			return null;
		}

		if (null == userId || null == groupId || null == roleIdList) {
			return null;
		}
		StringBuilder hql = new StringBuilder();
		hql.append("1=1");
		StringBuilder paramHql = new StringBuilder();

		List<Object> params = new ArrayList<Object>();
		/** refId */
		if (null != vidStr && !"".equals(vidStr)) {
			paramHql.append(" and x.refId= ?");
			params.add(Integer.parseInt(vidStr));
		}

		/** 模块名称 */
		if (null != moudleName && !"".equals(moudleName)) {
			paramHql.append(" and x.moduleName = ?");
			params.add(moudleName);
		}

		/** 用户名称 */
		if (null != userId) {
			paramHql.append(" and (x.userIds like ? ");
			params.add("%," + userId + ",%");
		}

		/** 用户机构 */
		if (null != groupId) {
			paramHql.append("or x.groupIds like ? ");
			params.add("%," + groupId + ",%");
		}
		/** 用户角色 */
		if (null != roleIdList && roleIdList.size() != 0) {
			for (Integer roleId : roleIdList) {
				paramHql.append(" or x.roleIds like '%," + roleId
						+ ",%'");
			}
			paramHql.append(")");
		}

		if (paramHql.length() > 0) {

			hql.append(paramHql);
			return this.findAll(hql.toString(), params.toArray());
		}

		return this.findAll(hql.toString());

	}
	
	
	/**
	 * 返回权限实体类
	 * 
	 * @return DataPriv
	 */

	public DataPriv getDataPrivByRefId(Integer refId, String moduleName) {
		return (DataPriv) this
				.findUnique(
						"from DataPriv dataPriv where dataPriv.refId=? and dataPriv.moduleName=?",
						refId, moduleName);
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
		return this.bulkUpdate(
						"update DataPriv dataPriv set dataPriv.roleIds=? and dataPriv.roleNames=? and dataPriv.userIds=? and dataPriv.userNames=? and dataPriv.groupIds=? and dataPriv.groupNames=? where dataPriv.refId=? and dataPriv.moduleName=?",
						roleIds, roleNames, userIds, userNames, roleIds,
						roleNames, refId, moduleName);
	}
	
	/**
	 * 得到权限列表
	 */
	public List<DataPriv> getFileSortById(String finalFileSortIds,
			String finalFileSortIdPrivs) {

		return this.findAll("from DataPriv dataPriv where dataPriv.refId in ("
						+ finalFileSortIds.substring(0,
								finalFileSortIds.length() - 1)
						+ ") and dataPriv.moduleName in ("
						+ finalFileSortIdPrivs + ")");
	}
	
	/**
	 * 根据人员得到权限列表
	 */

	public List<DataPriv> getFileSortByPerson(String userIds) {

		return this.findAll(
						"from DataPriv dataPriv where dataPriv.userIds like ?",
						userIds);
	}
}
