package cn.com.qytx.cbb.org.action.mobile;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import cn.com.qytx.cbb.org.util.TreeNode;
import cn.com.qytx.platform.base.action.BaseActionSupport;
import cn.com.qytx.platform.org.domain.GroupInfo;
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.org.service.IGroup;
import cn.com.qytx.platform.org.service.IUser;

import com.google.gson.Gson;

/**
 * 功能：手机端选择人员、部门接口
 * 版本: 1.0
 * 开发人员：贾永强
 * 创建日期: 下午4:24:16 
 * 修改日期：下午4:24:16 
 * 修改列表：
 */
public class MobileUserAction extends BaseActionSupport {

	//当前手机登陆用户ID
	private int userId;
	
	//是否选择全部，all选择全部的，不根据二级局选择；part根据二级局标志来选择,默认是后者
	private String flag = "part";
	
	private int sex = 1 ;
	
    public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	@Resource
    private IGroup groupInfoVOService;
    @Resource(name = "groupService")
    private IGroup groupService;
    @Resource(name = "userService")
    IUser userService;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	/**
	 * @throws Exception 
	 * 功能：根据部门选择人员
	 * @param
	 * @return
	 * @throws   
	 */
	public String selectUsersAndGroup() throws Exception{
		List<TreeNode> result = new ArrayList<TreeNode>();
		UserInfo u = userService.findOne(userId);
		List<GroupInfo> list = getGroups(u.getCompanyId(),userId, flag);
		String ids = "";
		for(Iterator it = (Iterator) list.iterator(); it.hasNext(); ){
			GroupInfo temp = (GroupInfo) it.next();
			ids+=temp.getGroupId();
			if(it.hasNext()){
				ids+=",";
			}
		}
		List<UserInfo> users = userService.findUsersByGroupId(ids);
		for(int i=0; i<list.size(); i++){
			GroupInfo gi = list.get(i);
			TreeNode tn = new TreeNode();
			tn.setId("gid_"+gi.getGroupId());
			tn.setName(gi.getGroupName());
			tn.setPId("gid_"+gi.getParentId());
			result.add(tn);
		}
		for(int i=0; i<users.size(); i++){
			UserInfo ui = users.get(i);
			TreeNode tn = new TreeNode();
			tn.setId("uid_"+ui.getUserId());
			tn.setName(ui.getUserName());
			tn.setPId("gid_"+ui.getGroupId().toString());
			tn.setTarget(ui.getPhone());
			result.add(tn);
		}
		Gson gson = new Gson();
		String data = gson.toJson(result);
		ajax("100||"+data);
		return null;
	}
	
	
	/**
	 * @throws Exception 
	 * 功能：根据部门选择人员
	 * @param
	 * @return
	 * @throws   
	 */
	public String selectUserList() {
		try{
			UserInfo u = userService.findOne(userId);
			Integer companyId = u.getCompanyId();
			List<Map<String,Object>> userListMap = userService.selectUserMap(companyId);
			Gson gson = new Gson();
			ajax("100||"+gson.toJson(userListMap));
		}catch(Exception e){
			ajax("102||系统繁忙,请稍后重试");
		}
		return null;
	}
	
	/**
	 * @throws Exception 
	 * 功能：只选择部门
	 * @param
	 * @return
	 * @throws   
	 */
	public String selectGroup() throws Exception{
		UserInfo u = userService.findOne(userId);
		List<GroupInfo> list = getGroups(u.getCompanyId(),userId, flag);
		List<TreeNode> nodes = new ArrayList<TreeNode>();
		for(int i=0; i<list.size(); i++){
			GroupInfo gi = list.get(i);
			TreeNode tn = new TreeNode();
			tn.setId(gi.getGroupId()+"");
			tn.setName(gi.getGroupName());
			tn.setPId(gi.getParentId()==null?"0":gi.getParentId().toString());
			nodes.add(tn);
		}
		Gson gson = new Gson();
		String strs = gson.toJson(nodes);
		ajax("100||"+strs);
		return null;
	}
	
	//获取部门
	private List<GroupInfo> getGroups(int companyId,int userId,String flag){
		String temFlag = flag;
		List<GroupInfo> list = new ArrayList<GroupInfo>();
		if(temFlag == null || temFlag.equals("")){
			temFlag = "part";
		}
		if(temFlag.equals("part")){
			GroupInfo forkGroup = groupService.getForkGroup(companyId, userId);
			if(forkGroup!=null){
				List<GroupInfo> temp = groupService.getSubGroupList(forkGroup.getGroupId());
				list.addAll(temp);
				list.add(forkGroup);
			}else{
				list = groupService.getGroupList(1,1);
			}
		}else{
			list = groupService.getGroupList(1, 1);
		}
		return list;
	}
	
	/**
	 * 功能：修改用户信息
	 * 作者：jiayongqiang
	 * 参数：
	 * 输出：
	 */
	public String updateUser(){
		UserInfo u = userService.findOne(userId);
		u.setSex(sex);
		userService.saveOrUpdate(u);
		ajax("100||success");
		return null;
	}
	
}
