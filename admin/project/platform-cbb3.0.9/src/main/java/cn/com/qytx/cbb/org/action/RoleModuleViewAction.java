package cn.com.qytx.cbb.org.action;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import cn.com.qytx.platform.base.action.BaseActionSupport;
import cn.com.qytx.platform.org.domain.ModuleInfo;
import cn.com.qytx.platform.org.domain.RoleInfo;
import cn.com.qytx.platform.org.service.IModule;
import cn.com.qytx.platform.org.service.IRole;

/**
 * @Description: [模版详细ACTION]
 * @Author: [REN]
 * @CreateDate: [2012-10-12 上午09:31:32]
 * @UpdateUser: [REN]
 * @UpdateDate: [2012-10-12 上午09:31:32]
 * @UpdateRemark: [说明本次修改内容]
 * @Version: [v1.0]
 */
public class RoleModuleViewAction extends BaseActionSupport {
	/**
     * 描述含义
     */
    private static final long serialVersionUID = -8273478635377412976L;
    /** 模版信息 */
	@Resource(name = "moduleService")
	IModule moduleService;
	/**角色信息*/
	@Resource(name = "roleService")
	IRole roleService;
	
	private Integer roleId;

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	/**
	 * 
	 * @Title: loadRoleModuleView
	 * @Description: TODO(得到角色模版详细)
	 * @param
	 * @return String 返回类型
	 */
	@SuppressWarnings("unchecked")
	public String loadRoleModuleView() {
		if (roleId != null) {
			Map<Integer, ModuleInfo> moduleMap = moduleService.findModuleMap(null, roleId);
			Map<Integer, ModuleInfo> firstModuleMap = moduleService.findModuleLevelMap(moduleMap, 1);
			Map<Integer, ModuleInfo> secondModuleMap = moduleService.findModuleLevelMap(moduleMap, 2);
			Map<Integer, ModuleInfo> thirdModuleMap = moduleService.findModuleLevelMap(moduleMap, 3);
			Map<Integer, ModuleInfo> fourthModuleMap = moduleService.findModuleLevelMap(moduleMap, 4);

			
			List<ModuleInfo> firstModuleList=new ArrayList<ModuleInfo>();
			List<ModuleInfo> secondModuleList=new ArrayList<ModuleInfo>();
			List<ModuleInfo> thirdModuleList=new ArrayList<ModuleInfo>();
			List<ModuleInfo> fourthModuleList=new ArrayList<ModuleInfo>();
			
			for (Entry<Integer, ModuleInfo> entry : firstModuleMap.entrySet()) {
				firstModuleList.add(entry.getValue());
			}
			for (Entry<Integer, ModuleInfo> entry : secondModuleMap.entrySet()) {
				secondModuleList.add(entry.getValue());
			}
			for (Entry<Integer, ModuleInfo> entry : thirdModuleMap.entrySet()) {
				thirdModuleList.add(entry.getValue());
			}
			for (Entry<Integer, ModuleInfo> entry : fourthModuleMap.entrySet()) {
				fourthModuleList.add(entry.getValue());
			}
			
			Collections.sort(firstModuleList, new ModuleInfoComparator());
			Collections.sort(secondModuleList, new ModuleInfoComparator());
			Collections.sort(thirdModuleList, new ModuleInfoComparator());
			Collections.sort(fourthModuleList, new ModuleInfoComparator());
			
			this.getRequest().setAttribute("firstModuleList",firstModuleList);
			this.getRequest().setAttribute("secondModuleList",secondModuleList);
			this.getRequest().setAttribute("thirdModuleList",thirdModuleList);
			this.getRequest().setAttribute("fourthModuleList",fourthModuleList);
			
			RoleInfo roleInfo=roleService.findOne(roleId);
			this.getRequest().setAttribute("roleInfo",roleInfo);
			
		}
		return SUCCESS;
	}
	
}

class ModuleInfoComparator implements Comparator<ModuleInfo>, Serializable{
	public int compare(ModuleInfo o1, ModuleInfo o2) {
		return o1.getOrderIndex() - o2.getOrderIndex();
	}
}
