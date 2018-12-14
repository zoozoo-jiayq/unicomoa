package cn.com.qytx.oa.record.action;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import cn.com.qytx.oa.record.service.IUserRecord;
import cn.com.qytx.platform.base.action.BaseActionSupport;
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.utils.tree.TreeNode;

public class UserRecordTreeAction extends BaseActionSupport{
	private static final long serialVersionUID = -4552973298215172579L;
	
	 @Autowired
     private IUserRecord userRecordService;
	
	private Integer treeType;//1 在职树 2 离职树
	
	public String userRecordTree(){
		String contextPath = getRequest().getContextPath();
        List<TreeNode> treeNodes = new ArrayList<TreeNode>();
		UserInfo userInfo = this.getLoginUser();
        treeNodes = userRecordService.userRecordTree(userInfo, null, treeType, contextPath);
        if(treeNodes!=null&&!treeNodes.isEmpty()){
        	for(int i=0; i<treeNodes.size(); i++){
            	treeNodes.get(i).setOpen(true);
            }
        }
        Gson json = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        String jsons = json.toJson(treeNodes);
        ajax(jsons);
		return null;
	}

	public Integer getTreeType() {
		return treeType;
	}

	public void setTreeType(Integer treeType) {
		this.treeType = treeType;
	}

	
}
