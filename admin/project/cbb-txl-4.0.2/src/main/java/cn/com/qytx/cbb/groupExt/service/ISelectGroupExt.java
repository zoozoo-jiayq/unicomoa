package cn.com.qytx.cbb.groupExt.service;

import java.util.List;

import cn.com.qytx.platform.base.service.BaseService;
import cn.com.qytx.platform.org.domain.GroupInfo;
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.utils.tree.TreeNode;

public interface ISelectGroupExt extends BaseService<GroupInfo>{
	
	public List<TreeNode> selectUserByGroup(UserInfo adminUser,String path);
}
