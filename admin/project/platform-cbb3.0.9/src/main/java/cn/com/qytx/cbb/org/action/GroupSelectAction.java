package cn.com.qytx.cbb.org.action;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import cn.com.qytx.platform.base.action.BaseActionSupport;
import cn.com.qytx.platform.org.domain.CompanyInfo;
import cn.com.qytx.platform.org.domain.GroupInfo;
import cn.com.qytx.platform.org.service.ICompany;
import cn.com.qytx.platform.org.service.IGroup;
import cn.com.qytx.platform.utils.tree.TreeNode;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * 功能:
 * 版本: 1.0
 * 开发人员: zyf
 * 创建日期: 2014年8月5日
 * 修改日期: 2014年8月5日
 * 修改列表:
 */
public class GroupSelectAction extends BaseActionSupport{
	
    /**
     * 部门/群组管理接口
     */
    @Resource(name = "groupService")
    private IGroup groupService;
    
    @Autowired
    private ICompany companyService;
    
    private String flag;
    
    /**
     * 功能：获取部门/群组树
     * @return
     */
	public String selectGroup(){

    	String contextPath = getRequest().getContextPath()+"/";
        List<TreeNode> treeNodes = new ArrayList<TreeNode>();
            // 根据部门选择
            GroupInfo forkGroup = null;
            
            CompanyInfo companyInfo = companyService.findOne(getLoginUser().getCompanyId());
	        List<GroupInfo> groupList = new ArrayList<GroupInfo>();
	        if(companyInfo!=null)
	        {
	            if(forkGroup==null){
	            	TreeNode treeHead = new TreeNode();
	            	treeHead.setId("gid_0");//部门ID前加gid表示类型为部门
	            	treeHead.setName(StringUtils.isBlank(companyInfo.getShortName())?companyInfo.getCompanyName():companyInfo.getShortName());
	            	treeHead.setPId("gid_-1");
	            	treeHead.setIcon(contextPath + "/images/company.png");
	            	treeHead.setOpen(true);
	            	treeNodes.add(treeHead);
	            	groupList = groupService.getGroupList(companyInfo.getCompanyId(), GroupInfo.DEPT);
	            }else{
	            	//add by jiayq
	            	TreeNode treeHead = new TreeNode();
	            	treeHead.setId("gid_"+forkGroup.getGroupId());
	            	treeHead.setName(forkGroup.getGroupName());
	            	treeHead.setPId("gid_-1");
	            	//treeHead.setIcon(path + "images/group.jpg");
	            	treeHead.setOpen(true);
	            	treeNodes.add(treeHead);
	            	groupList = groupService.getSubGroupList(forkGroup.getGroupId());
	            }
	        }
	        if (groupList != null)
	        {
	            // 遍历部门
	            for (GroupInfo group : groupList)
	            {
	            	if(flag!=null && flag.equals("column")){
	            		if(group.getParentId()!=0){
	            			continue;
	            		}
	            	}
	            	String groupName = StringUtils.substring(group.getGroupName(), 0, 8);
	                TreeNode treeNode = new TreeNode();
	                treeNode.setId("gid_" + group.getGroupId().toString());// 部门ID前加gid表示类型为部门
	                treeNode.setName(groupName);
	                treeNode.setPId("gid_" + group.getParentId().toString());
	                treeNode.setObj(group.getOrderIndex());
	               //treeNode.setIcon(path + "images/group.jpg");
	                treeNodes.add(treeNode);
	            }
	        }
	        
            
        Gson json = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        String jsons = json.toJson(treeNodes);
        ajax(jsons);
        return null;
    
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	
}
