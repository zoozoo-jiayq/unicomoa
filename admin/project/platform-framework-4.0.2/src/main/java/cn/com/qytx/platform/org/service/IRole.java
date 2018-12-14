package cn.com.qytx.platform.org.service;

import java.io.Serializable;
import java.util.List;

import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.Pageable;
import cn.com.qytx.platform.base.service.BaseService;
import cn.com.qytx.platform.org.domain.RoleInfo;

/**
 * 角色操作接口类
 * User: 黄普友
 * Date: 13-2-19
 * Time: 下午4:54
 */
public interface IRole   extends BaseService<RoleInfo>,Serializable  {

    /**
     * 获取角色列表
     * @param companyId 企业ID
     * @return
     */
    public List<RoleInfo> getRoleList();
    /**
     * 根据人员ID数组获取该人员角色列表
     * @param userId 人员ID
     * @return
     */
    public List<RoleInfo> getRoleByUser(int userId);
    
	/**
	 * @Title: isExistRoleCode 
	 * @Description: (是否存在角色代码) 
	 * @param roleCode 角色代码
	 * @return Boolean    返回类型
	 */
	public Boolean isExistRoleCode(String roleCode);
	
	   /**
     * @Title: isExistRoleCode 
     * @Description: (是否存在角色名称) 
     * @param roleCode 角色名称
     * @return Boolean    返回类型
     */
    public Boolean isExistRoleName(String roleName);
    
	/**
	 * @Title: findByPage 
	 * @Description: TODO(角色分页列表) 
	 * @param page
	 * @return Page    返回类型
	 */
	public Page<RoleInfo> findByPage(Pageable page);
	
	/**
	 * 
	 * 功能：通过人员id得到角色列表
	 * @param userId
	 * @param companyId
	 * @param type
	 * @return
	 */
	public List<RoleInfo> findRolesByUserId(Integer userId,Integer type);
	
	/** 根据roleCode查找角色
	 * @param roleCode
	 * @return
	 */
	public RoleInfo findByRoleCode(String roleCode);
	/**
	 * 根据companyId,roleCode查找角色
	 * @param companyId
	 * @param roleCode
	 * @return
	 */
	public RoleInfo findByRoleCode(Integer companyId, String roleCode);
	
}
