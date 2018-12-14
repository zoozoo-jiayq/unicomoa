package cn.com.qytx.oa.dataPriv.service;

import java.io.Serializable;
import java.util.List;

import cn.com.qytx.oa.dataPriv.domain.DataPriv;
import cn.com.qytx.platform.base.service.BaseService;

/**
 * 功能:数据权限service
 * 版本: 1.0
 * 开发人员: 李华伟
 * 创建日期: 2013-3-22
 * 修改日期: 2013-3-22
 * 修改列表:
 */
public interface IDataPriv extends BaseService<DataPriv>,Serializable 
{
    /**
     * 获取查询数据权限额外的hql语句
     * @param vidStr 映射主键字符串
     * @param moudleName 模块名称 不能为空
     * @param subMoudle 子模块名称 可为空
     * @param userId 用户ID
     * @param groupId 用户所属群组Id
     * @param roleId 用户所属角色
     * @return String
     */
    public String getDataPrivHql(String vidStr, String moudleName, String subMoudle,
            Integer userId, Integer groupId, Integer roleId);

    /**
     * 获取查询数据权限额外的hql语句
     * @param vidStr 映射主键字符串
     * @param moudleName 模块名称 不能为空
     * @param subMoudle 子模块名称 可为空
     * @param userId 用户ID
     * @param groupId 用户所属群组Id
     * @param roleIdList 用户所属角色集合
     * @return String
     */
    public String getDataPrivHql(String vidStr, String moudleName, String subMoudle,
            Integer userId, Integer groupId, List<Integer> roleIdList);

    /**
     * 功能：根据refId得到权限记录
     * @param refId 管理的Id
     * @param moduleName 模块名
     * @return DataPriv
     */
    public DataPriv getDataPrivByRefId(Integer refId, String moduleName);

    /**
     * 功能：更新权限记录
     * @param refId refId
     * @param moduleName 模块名
     * @param roleIds 角色Id集合
     * @param roleNames 角色名集合
     * @param userIds 人员Id集合
     * @param userNames 人员名字集合
     * @param groupIds 群组Id集合
     * @param GroupName 群组名集合
     * @return int
     */
    public int updateDataPriv(Integer refId, String moduleName, String roleIds, String roleNames,
            String userIds, String userNames, String groupIds, String GroupName);
    /**
     * 功能：更新权限记录
     * @param dataprivid
     * @param roleIds 角色Id集合
     * @param roleNames 角色名集合
     * @param userIds 人员Id集合
     * @param userNames 人员名字集合
     * @param groupIds 群组Id集合
     * @param GroupName 群组名集合
     * @return int
     */
    public int updateDataPrivById(Integer dataprivid, String roleIds, String roleNames,
            String userIds, String userNames, String groupIds, String GroupName);
    
    /**
     * 功能：删除权限记录
     * @param refId refId
     * @param moduleName 模块名
     * @param roleIds 角色Id集合
     * @param groupIds 群组Id集合
     * @param userIds 人员Id集合
     * @param roleNames 角色名集合
     * @param groupNames 群组名集合
     * @param userNames 人员名字集合
     */
    public void deleteFileSort(String refId, String moduleName, String roleIds, String groupIds,
            String userIds, String roleNames, String groupNames, String userNames);

    /**
     * 功能：得到权限记录
     * @param finalFileSortIds finalFileSortIds
     * @param finalFileSortIdPrivs finalFileSortIdPrivs
     * @return List<DataPriv>
     */
    public List<DataPriv> getFileSortById(String finalFileSortIds, String finalFileSortIdPrivs);

    /**
     * 功能：根据人员得到权限列表
     * @param userIds 人员Id
     * @return List<DataPriv>
     */
    public List<DataPriv> getFileSortByPerson(String userIds);

    /**
     * 
     * 功能：得到用户的新建,编辑，删除权限
     * @param vidStr vidStr
     * @param moudleName 模块名
     * @param subMoudle 子模块名
     * @param userId userId
     * @param groupId groupId
     * @param roleIdList roleIdList
     * @return List 
     */
    public List getFileSortNedPriv(String vidStr, String moudleName, String subMoudle,
            Integer userId, Integer groupId, List<Integer> roleIdList);
    /**
     * 功能：根据人员得到权限列表
     * @param userIds 人员Id
     * @return List<DataPriv>
     */
    public List<DataPriv> getChildFileSortById(int fileSortId);
    
    /**
     * 根据ID获取对象
     * 功能：
     * @return
     */
    public DataPriv getDataPrivById(Integer id);


}
