package cn.com.qytx.oa.publicaddress.dao;

import java.io.Serializable;

import org.springframework.stereotype.Component;

import cn.com.qytx.oa.publicaddress.domain.AddressConst;
import cn.com.qytx.oa.publicaddress.domain.AddressOfGroup;
import cn.com.qytx.platform.base.dao.BaseDao;

/**
 * 功能:通讯薄联系人和组中间表Dao
 * 版本: 1.0
 * 开发人员: 李华伟
 * 创建日期: 2013-3-22
 * 修改日期: 2013-3-22
 * 修改列表:
 */
@Component
public class AddressOfGroupDao extends BaseDao<AddressOfGroup, Integer> implements Serializable
{
    /**
	 * 描述含义
	 */
	private static final long serialVersionUID = 1L;


	/**
     * 查询关联表数据
     * @param groupId
     * @param addressId
     * @return
     */
    public AddressOfGroup findByGidAndAid(Integer groupId, Integer addressId)
    {
        return (AddressOfGroup) this.findOne(
                " groupId = ? and addressId = ?", groupId, addressId);
    }
    
    /**
     * 
     * 功能：更新群组Id信息
     * @param oldGroupId
     * @param newGroupId
     */
    public void updateGroupId(Integer oldGroupId, Integer newGroupId)
    {
        // 更新关联表信息
        String hql = "update AddressOfGroup set groupId = ? where groupId = ?";
        super.bulkUpdate(hql, newGroupId, oldGroupId);
        
        // 更新人员表群组信息
        String hqlAddress = "update Address set addressGroupId = ?, groupName = ? where addressGroupId = ?";
        super.bulkUpdate(hqlAddress, newGroupId, AddressConst.DEFAULT_GROUP_NAME, oldGroupId);
    }
    
    
    /**
     * 
     * 功能：删除对应联系人的中间表
     * @param addressId
     */
    public void deleteByAid(Integer addressId)
    {
        super.bulkDelete("delete from AddressOfGroup where addressId = ? ",  addressId);
    }
}
