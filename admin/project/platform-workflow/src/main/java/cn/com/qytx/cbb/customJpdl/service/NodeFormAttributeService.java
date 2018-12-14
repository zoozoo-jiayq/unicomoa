package cn.com.qytx.cbb.customJpdl.service;

import java.util.List;

import cn.com.qytx.cbb.customForm.domain.FormProperties;
import cn.com.qytx.cbb.customJpdl.domain.NodeFormAttribute;
import cn.com.qytx.platform.base.service.BaseService;
import cn.com.qytx.platform.org.domain.GroupInfo;
import cn.com.qytx.platform.org.domain.RoleInfo;
import cn.com.qytx.platform.org.domain.UserInfo;

/**
 * 功能：流程节点服务接口
 * 版本: 1.0
 * 开发人员：贾永强
 * 创建日期: 2013-3-25 下午5:34:55 
 * 修改日期：2013-3-25 下午5:34:55 
 * 修改列表：
 */
public interface NodeFormAttributeService extends BaseService<NodeFormAttribute> {

	/**
	 * 功能：更新节点属性
	 * @param
	 * @return
	 * @throws   
	 */
	public void updateNodeFormAttribute(NodeFormAttribute node);
	
	/**
	 * 功能：根据节点ID查找节点
	 * @param
	 * @return
	 * @throws   
	 */
	public NodeFormAttribute findById(Integer nodeId);
	
	/**
	 * 功能：根据流程ID和任务名称查询节点属性
	 * @param
	 * @return
	 * @throws   
	 */
	public NodeFormAttribute findByProcessIdAndTaskName(int processAttributeId,String taskName);

	
	/**
	 * 功能：根据用户的ID集合查询用户列表
	 * @param
	 * @return
	 * @throws   
	 */
	public List<UserInfo> findByUserIds(String ids);
	
	/**
	 * 功能：根据部门ID集合查询部门列表
	 * @param
	 * @return
	 * @throws   
	 */
	public List<GroupInfo> findByGroupIds(String ids);
	
	/**
	 * 功能：根据角色ID集合查询角色列表
	 * @param
	 * @return
	 * @throws   
	 */
	public List<RoleInfo> findByRoleIds(String ids);
	
	/**
	 * 功能：根绝ID集合查找对象
	 * @param
	 * @return
	 * @throws   
	 */
	public List<FormProperties> findFormPropertiesByIds(String ids);


	/**
	 * 功能：获取流程的第一个节点 也就是 状态为start
	 * @param processId2,第一个task节点固定为"发起流程"
	 * @return
	 */
	@Deprecated
	public NodeFormAttribute findFirstTask(int processId2);
	
	/**
	 * 功能：根据nodeId获取node的候选人
	 * @param
	 * @return
	 * @throws   
	 */
	public List<UserInfo> getUsersByNodeId(int nodeId);
	
	/**
	 * 功能：更新流程节点
	 * @param nodeId
	 * @param candidate
	 * @param groups
	 * @param roles
	 * @param writerables
	 * @param secrets
	 * @param editDoc
	 */
	public void updateNodeFormAttribute(int nodeId,String candidate,String groups,String roles,String writerables,String secrets,int editDoc);
	
}
