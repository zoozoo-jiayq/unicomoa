package cn.com.qytx.cbb.org.service;

import java.util.List;

import cn.com.qytx.platform.base.service.BaseService;
import cn.com.qytx.platform.org.domain.GroupInfo;
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.utils.tree.TreeNode;

/**
 * <br/>功能 人员树搜索  
 * <br/>版本  1.0
 * <br/>开发人员  zyf
 * <br/>创建日期  2016年1月6日
 * <br/>修改日期  2016年1月6日
 * <br/>修改列表
 */
public interface ISelectUser extends BaseService<UserInfo> {
	/**
     * 功能：搜索一级部门结构
     * 作者：张永峰
     * 参数：
     * 输出：
     */
    public List<TreeNode> selectDefaultOrg(UserInfo adminUser,GroupInfo forkGroup,int showType,int key,String path,int groupType);
    
    /**
     * 功能：搜索一级群组
     * 作者：张永峰
     * 参数：
     * 输出：
     */
    public List<TreeNode> selectDefaultQunzu(int companyId, int qunzuType,String contextPath);
    
    /**
     * 功能：搜索一级角色
     * @return
     */
    public List<TreeNode> selectDefaultRole();
    
    /**
     * 功能：获得节点下面的信息
     * @param nodeId
     * @param companyId
     * @param contextPath
     * @param showType
     * @param type
     * @return
     */
    public List<TreeNode> getChildrenByNodeId(UserInfo userInfo,int nodeId,String contextPath,int showType,int type);
}
