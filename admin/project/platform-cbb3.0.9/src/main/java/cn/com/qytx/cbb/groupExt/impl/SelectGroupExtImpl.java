package cn.com.qytx.cbb.groupExt.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.qytx.cbb.groupExt.service.ISelectGroupExt;
import cn.com.qytx.platform.base.query.Sort;
import cn.com.qytx.platform.base.query.Sort.Direction;
import cn.com.qytx.platform.base.query.Sort.Order;
import cn.com.qytx.platform.base.service.impl.BaseServiceImpl;
import cn.com.qytx.platform.org.dao.CompanyDao;
import cn.com.qytx.platform.org.dao.GroupDao;
import cn.com.qytx.platform.org.domain.CompanyInfo;
import cn.com.qytx.platform.org.domain.GroupInfo;
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.utils.tree.TreeNode;

@Service
@Transactional
public class SelectGroupExtImpl extends BaseServiceImpl<GroupInfo> implements ISelectGroupExt {
	@Autowired
	CompanyDao<CompanyInfo> companyDao;
	
	@Autowired
	GroupDao<GroupInfo> groupDao;
	
	public List<TreeNode> selectUserByGroup(UserInfo adminUser,String path) {
		List<TreeNode> treeNodes = new ArrayList<TreeNode>();
      	CompanyInfo companyInfo = companyDao.findOne(adminUser.getCompanyId());
        List<GroupInfo> publicGroupList = new ArrayList<GroupInfo>();
        List<GroupInfo> myGroupList = new ArrayList<GroupInfo>();
        if(companyInfo!=null)
        {
            	TreeNode treeHead = new TreeNode();
            	treeHead.setId("gid_0");//部门ID前加gid表示类型为部门
            	treeHead.setName(StringUtils.isBlank(companyInfo.getShortName())?companyInfo.getCompanyName():companyInfo.getShortName());
              	treeHead.setTitle(StringUtils.isBlank(companyInfo.getShortName())?companyInfo.getCompanyName():companyInfo.getShortName());
            	treeHead.setPId("gid_-1");
            	treeHead.setIcon(path + "/images/company.png");
            	treeHead.setOpen(true);
            	treeNodes.add(treeHead);
            	Order o1 = new Order(Direction.ASC,"orderIndex");
                Order o2 = new Order(Direction.ASC,"groupId");
            	Sort sort = new Sort(o1,o2);
            	publicGroupList = groupDao.findAll(" groupId in (select a.groupId from GroupInfo a ,GroupUser b where a.companyId = ? and a.groupType = ? and a.isDelete = ? and a.groupId = b.groupId and b.userId = ?)",sort,companyInfo.getCompanyId(),4,0,adminUser.getUserId());//公共群组
            	myGroupList = groupDao.findAll(" companyId=? and groupType =? and isDelete=? and  userId=? ",sort,companyInfo.getCompanyId(),5,0,adminUser.getUserId());//个人群组
        }
       
        if (publicGroupList != null)
        {
            // 遍历公共群组
            for (GroupInfo group : publicGroupList)
            {
               // String groupName = StringUtils.substring(group.getGroupName(), 0, 8);
                TreeNode treeNode = new TreeNode();
                treeNode.setId("gid_" + group.getGroupId().toString());// 部门ID前加gid表示类型为部门
                treeNode.setName(group.getGroupName());
                treeNode.setTitle(group.getGroupName());
                treeNode.setPId("gid_" + group.getParentId().toString());
                treeNode.setIcon(path + "/images/group.png");
            	treeNode.setObj("4");
                treeNodes.add(treeNode);
            }
        }
        if (myGroupList != null)
        {
        	// 遍历个人群组
        	for (GroupInfo group : myGroupList)
        	{
        		// String groupName = StringUtils.substring(group.getGroupName(), 0, 8);
        		TreeNode treeNode = new TreeNode();
        		treeNode.setId("gid_" + group.getGroupId().toString());// 部门ID前加gid表示类型为部门
        		treeNode.setName(group.getGroupName());
        		treeNode.setTitle(group.getGroupName());
        		treeNode.setPId("gid_" + group.getParentId().toString());
        		treeNode.setIcon(path + "/images/group.png");
        		treeNode.setObj("5");
        		treeNodes.add(treeNode);
        	}
        }
        return treeNodes;
	}
}
