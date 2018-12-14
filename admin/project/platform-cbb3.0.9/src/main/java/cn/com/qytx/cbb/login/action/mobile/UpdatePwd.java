package cn.com.qytx.cbb.login.action.mobile;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import cn.com.qytx.cbb.login.vo.WapUser;
import cn.com.qytx.platform.base.action.BaseActionSupport;
import cn.com.qytx.platform.org.domain.ModuleInfo;
import cn.com.qytx.platform.org.domain.RoleInfo;
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.org.service.IModule;
import cn.com.qytx.platform.org.service.IRole;
import cn.com.qytx.platform.org.service.IUser;
import cn.com.qytx.platform.utils.encrypt.MD5;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class UpdatePwd extends BaseActionSupport {
	@Autowired
	private IUser userService;
    @Autowired
    private IModule moduleService;
	@Autowired
	private IRole roleService;
	private String method;
	private String _clientType;
	private int userId = -1;
	private String password;
	public void updatePwd(){
		PrintWriter writer;
		try{
			writer = new PrintWriter(this.getResponse().getWriter());
			String result = "102||参数不正确";
			if(method==null||!"updatePwd".equals(method)){
				result = "103||参数:method 或者 json 为空";
			}else if(_clientType==null||!"wap".equals(_clientType)||userId==-1||StringUtils.isEmpty(password)){
				result = "102||参数不正确";
			}else{
				UserInfo user = userService.findOne(userId);
				if(user==null){
					result = "101||用户不存在";
				}else{
					user.setLoginPass(new MD5().encrypt(password));
					userService.saveOrUpdate(user);
					user= userService.findOne(user.getUserId());
					Gson json = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		        	  WapUser wapUser = new WapUser();
		        	  wapUser.setUserId(user.getUserId());
		        	  wapUser.setUserName(user.getUserName());
		        	  wapUser.setLoginName(user.getLoginName());
		        	  wapUser.setLoginPass(user.getLoginPass());
		        	  wapUser.setSex(user.getSex());
		        	  wapUser.setWorkNo(user.getWorkNo());
		        	  wapUser.setJob(user.getJob());
		        	  if(user.getBirthDay()!=null)
		        		  wapUser.setBirthDay(new SimpleDateFormat("yyyy-MM-dd").format(user.getBirthDay()));
		        	  wapUser.setEmail(user.getEmail());
		        	  wapUser.setPy(user.getPy());
		        	  wapUser.setGroupId(user.getGroupId());//部门id
		        	  wapUser.setCompanyId(user.getCompanyId());
		        	  wapUser.setPhone(user.getPhone());
		        	  wapUser.setPhone2(user.getPhone2());
		        	  if(user.getSignName()==null){
		        		  wapUser.setSignName("");
		        	  }else{
		        		  wapUser.setSignName(user.getSignName());
		        	  }
		        	  String roleIdArr="";
		        	  List<RoleInfo> roleList =roleService.getRoleByUser(wapUser.getUserId()); //根据人员Id获取角色列表
		              if(roleList!=null)
		              {
		                  roleIdArr= getRoleIds(roleList);
		              }
		              List<ModuleInfo> moduleList = new ArrayList<ModuleInfo>();
		              if(user.getIsDefault()==0){
		            	  moduleList = moduleService.getAllModule();
		              }else{
		            	  moduleList =moduleService.getModuleByRole(roleIdArr);//获取模块列表
		              }
		              wapUser.setModuleList(moduleList);
			          String jsons = json.toJson(wapUser);
					result = "100||"+jsons;
				}
			}
			
			
			writer.print(result);
	        writer.flush();
	        writer.close();
		}catch(Exception e){

		}
	}
	
	 /**
     * 获取角色id列表
     * @param roleList
     * @return
     */
    private String getRoleIds(final List<RoleInfo> roleList)
    {
        StringBuffer roleIdArr=new StringBuffer();
        String arr="";
        for(RoleInfo role:roleList)
        {
            roleIdArr.append(role.getRoleId()+",");
        }
        if(roleIdArr!=null&&roleIdArr.toString().endsWith(","))
        {
       	 arr=roleIdArr.toString().substring(0,roleIdArr.toString().length()-1);
        }
        return arr;
    }
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String get_clientType() {
		return _clientType;
	}
	public void set_clientType(String _clientType) {
		this._clientType = _clientType;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}
