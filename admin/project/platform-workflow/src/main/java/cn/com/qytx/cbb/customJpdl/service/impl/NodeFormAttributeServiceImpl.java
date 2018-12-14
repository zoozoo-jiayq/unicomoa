package cn.com.qytx.cbb.customJpdl.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.qytx.cbb.customForm.dao.FormPropertiesDao;
import cn.com.qytx.cbb.customForm.domain.FormProperties;
import cn.com.qytx.cbb.customJpdl.dao.NodeFormAttributeDao;
import cn.com.qytx.cbb.customJpdl.domain.NodeFormAttribute;
import cn.com.qytx.cbb.customJpdl.service.NodeFormAttributeService;
import cn.com.qytx.platform.base.application.TransportUser;
import cn.com.qytx.platform.base.service.impl.BaseServiceImpl;
import cn.com.qytx.platform.org.dao.GroupDao;
import cn.com.qytx.platform.org.dao.RoleDao;
import cn.com.qytx.platform.org.dao.UserDao;
import cn.com.qytx.platform.org.domain.GroupInfo;
import cn.com.qytx.platform.org.domain.RoleInfo;
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.org.service.IGroup;
import cn.com.qytx.platform.org.service.IUser;
import cn.com.qytx.platform.utils.StringUtil;

/**
 * 功能：流程节点操作实现类
 * 版本: 1.0
 * 开发人员：贾永强
 * 创建日期: 2013-3-25 下午5:37:01 
 * 修改日期：2013-3-25 下午5:37:01 
 * 修改列表：
 */
@Service("nodeFormAttributeService")
@Transactional
public class NodeFormAttributeServiceImpl extends BaseServiceImpl<NodeFormAttribute>implements NodeFormAttributeService{
	@Resource
	private NodeFormAttributeDao nodeFormAttributeDao;
	@Resource
	private UserDao<UserInfo> userDao;
	@Resource
	private GroupDao<GroupInfo> groupDao;
	@Resource
	private RoleDao<RoleInfo> roleDao;
	@Resource
	private FormPropertiesDao formPropertiesDao;
    @Resource(name = "groupService")
    private IGroup groupService;
    @Resource(name = "userService")
    private IUser userService;
	


	/**
	 * 功能：更新流程节点
	 * @param
	 * @return
	 * @throws   
	 */
	@Override
	public void updateNodeFormAttribute(NodeFormAttribute node) {
		if(node!=null){
			node.setCompanyId(TransportUser.get().getCompanyId());
			nodeFormAttributeDao.saveOrUpdate(node);
		}
	}

	/**
	 * 功能：查找刘晨根节点
	 * @param
	 * @return
	 * @throws   
	 */
	@Override
	public NodeFormAttribute findById(Integer nodeId) {
		return nodeFormAttributeDao.findOne(nodeId);
	}

	/**
	 * 功能：根据流程ID和任务名称获取节点属性
	 * @param
	 * @return
	 * @throws   
	 */
	@Override
	public NodeFormAttribute findByProcessIdAndTaskName(int processAttributeId,
			String taskName) {
		
		return nodeFormAttributeDao.findByProcessIdAndName(processAttributeId, taskName);
				
	}

	/**
	 * 功能：根据用户ID集合查找用户信息
	 * @param
	 * @return
	 * @throws   
	 */
	@Override
	public List<UserInfo> findByUserIds(String ids) {
		return userDao.findAll("userId in ("+ids+")");
	}

	/**
	 * 功能：根据部门ID集合查询部门列表
	 * @param
	 * @return
	 * @throws   
	 */
	@Override
	public List<GroupInfo> findByGroupIds(String ids) {
		return groupDao.findAll("groupId in ("+ids+")");
	}

	/**
	 * 功能：根据角色ID集合查询角色列表
	 * @param
	 * @return
	 * @throws   
	 */
	@Override
	public List<RoleInfo> findByRoleIds(String ids) {
		if(ids.endsWith(",")){
			ids = ids.substring(0, ids.length()-1);
		}
		return roleDao.findAll("roleId in ("+ids+") and isDelete = 0");
	}

	/**
	 * 功能：根绝ID集合查找对象集合
	 * @param
	 * @return
	 * @throws   
	 */
	@Override
	public List<FormProperties> findFormPropertiesByIds(String ids) {
		if(StringUtil.isEmpty(ids)){
			return new ArrayList<FormProperties>();
		}else{
			if(ids.endsWith(",")){
				ids = ids.substring(0, ids.length()-1);
			}
			return formPropertiesDao.findAll("propertyId in ("+ids+")");
		}
	}

	/**
	 * 功能：获取流程的第一个节点 也就是 状态为start
	 * @param processId2
	 * @return
	 */
	@Override
	public NodeFormAttribute findFirstTask(int processId2) {
		return nodeFormAttributeDao.findFirstTask(processId2);
	}

	/**
	 * 功能：根据nodeId获取node的候选人
	 * @param
	 * @return
	 * @throws   
	 */
	@Override
    public List<UserInfo> getUsersByNodeId(int nodeId) {
	    // TODO Auto-generated method stub
		NodeFormAttribute nfa = nodeFormAttributeDao.findOne(nodeId);
		String users = nfa.getCandidate();
		String groups = nfa.getDepts();
		String roles = nfa.getRoles();
		return userService.findUsersByIds(users, groups, roles);
    }

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
	@Override
	public void updateNodeFormAttribute(int nodeId, String candidate,
			String groups, String roles, String writerables, String secrets,int editDoc) {
		// TODO Auto-generated method stub
		nodeFormAttributeDao.updateNodeFormAttribute(nodeId, candidate, groups, roles, writerables, secrets,editDoc);
	}

}
