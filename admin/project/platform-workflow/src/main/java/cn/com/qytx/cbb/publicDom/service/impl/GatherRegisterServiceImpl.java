package cn.com.qytx.cbb.publicDom.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.qytx.cbb.publicDom.service.GatherRegisterService;
import cn.com.qytx.cbb.publicDom.service.PublicDocumentConstant;
import cn.com.qytx.platform.org.domain.GroupInfo;
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.org.service.IGroup;
import cn.com.qytx.platform.org.service.IUser;

/**
 * 功能：
 * 版本: 1.0
 * 开发人员：贾永强
 * 创建日期: 2013-4-22 下午2:18:10 
 * 修改日期：2013-4-22 下午2:18:10 
 * 修改列表：
 */
@Service("gatherRegisterService")
@Transactional
public class GatherRegisterServiceImpl  implements GatherRegisterService  {

	@Resource
	private IGroup groupService;
	@Resource
	private IUser userService;
	
	/**
	 * 功能：查询指定部门的收文登记员ID集合
	 * @param
	 * @return
	 * @throws   
	 */
	@Override
	public List<Integer> findUserIdByRoleAndGroup(String groupId) {
		List<UserInfo> ulist = userService.findUsersByRoleCodeAndGroup(PublicDocumentConstant.GATHER_REGISTER, Integer.parseInt(groupId));
		List<Integer> list = new ArrayList<Integer>();
		for(int i=0; i<ulist.size(); i++){
			list.add(ulist.get(i).getUserId());
		}
		return list; 
	}

	/**
	 * 功能：
	 * @param
	 * @return
	 * @throws   
	 */
	@Override
	public String findGroupNameById(String groupId) {
		GroupInfo g = groupService.findOne(Integer.parseInt(groupId));
		if(g!=null){
			return g.getGroupName();
		}else{
			return "";
		}
	}

	/**
	 * 功能：
	 * @param
	 * @return
	 * @throws   
	 */
	@Override
	public String findGroupNameByUserId(String userId) {
		UserInfo u = userService.findOne(Integer.parseInt(userId));
//		List<GroupInfo> g = groupService.getGroupByUserId(u.getCompanyId(), GroupInfo.DEPT, u.getUserId());
		GroupInfo g = groupService.findOne(u.getGroupId());
		if(g!=null ){
			return g.getGroupName();
		}
		return null;
	}

}
