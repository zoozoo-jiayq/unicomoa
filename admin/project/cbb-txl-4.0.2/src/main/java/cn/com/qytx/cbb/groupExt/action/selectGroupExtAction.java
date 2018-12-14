package cn.com.qytx.cbb.groupExt.action;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;

import cn.com.qytx.cbb.groupExt.service.ISelectGroupExt;
import cn.com.qytx.platform.base.action.BaseActionSupport;
import cn.com.qytx.platform.org.service.IGroup;
import cn.com.qytx.platform.utils.tree.TreeNode;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * 功能: 获得群组树的内容
 * 版本: 1.0
 * 开发人员: zyf
 * 创建日期: 2014年11月24日
 * 修改日期: 2014年11月24日
 * 修改列表:
 */
public class selectGroupExtAction extends BaseActionSupport{
	
    /**
     * 部门/群组管理接口
     */
    @Resource(name = "groupService")
    private IGroup groupService;
    
    /**
     * 群组树
     */
    @Autowired
    ISelectGroupExt selectGroupExtService;
    
    /**
     * 功能：获得群组的list
     */
    public void selectGroup(){
		List<TreeNode> treeNodes = selectGroupExtService.selectUserByGroup(this.getLoginUser(), getRequest().getContextPath());
	 	Gson json = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		String jsons = json.toJson(treeNodes);
		ajax(jsons);
	}
    
}
