package cn.com.qytx.cbb.datafilter.action;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import cn.com.qytx.cbb.datafilter.service.IDataFilter;
import cn.com.qytx.platform.base.action.BaseActionSupport;
import cn.com.qytx.platform.base.application.TransportUser;
import cn.com.qytx.platform.base.application.TransportUserFilter;
import cn.com.qytx.platform.base.dao.BaseDao;
import cn.com.qytx.platform.org.domain.GroupInfo;
import cn.com.qytx.platform.org.domain.RoleInfo;
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.org.service.IGroup;
import cn.com.qytx.platform.org.service.IRole;
import cn.com.qytx.platform.org.service.IUser;
import cn.com.qytx.platform.security.SystemContextHolder;
import cn.com.qytx.platform.security.domain.DataFilter;
import cn.com.qytx.platform.utils.spring.SpringUtil;

import com.google.gson.Gson;

/**
 * 数据权限配置
 * @author jiayongqiang
 *
 */
public class DataFilterAction extends BaseActionSupport {
	
	@Resource
	private IDataFilter dataFilterService;
	@Resource
	private IUser userSerivce;
	@Resource
	private IGroup groupService;
	@Resource
	private IRole roleService;
	
	private String moduleClassName;
	private String config;
	private Integer id;
	private Integer userId;
	
	public String getModuleClassName() {
		return moduleClassName;
	}

	public void setModuleClassName(String moduleClassName) {
		this.moduleClassName = moduleClassName;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	/**
	 * 进入数据权限主页面
	 * @return
	 */
	public String main(){
		return "main";
	}
	
	/**
	 * 进入模块配置界面
	 * @return
	 */
	public String manager(){
		return "configManager";
	}
	
	public String addDatafilter(){
		Gson gson = new Gson();
		Map map = gson.fromJson(config, Map.class);
		String relation = (String) map.get("relationType");
		String id = (String) map.get("id");
		String condition = (String) map.get("condition");
		String configName = (String) map.get("configName");
		DataFilter df = new DataFilter();
		df.setModelClassName(moduleClassName);
		df.setCompanyId(getLoginUser().getCompanyId());
		df.setConditionJpql(condition);
		df.setName(configName);
		df.setOperationType("READ");
		if(relation.equals("all")){
			df.setRelationId(relation);
		}else{
			df.setRelationId(relation+"_"+id);
		}
		dataFilterService.saveOrUpdate(df);
		ajax("{status:'success'}");
		return null;
	}
	
	/**
	 * 删除
	 * @return
	 */
	public String delete(){
		dataFilterService.delete(id, true);
		return "configManager";
	}
	
	public String test(){
		UserInfo u = userSerivce.findOne(userId);
		TransportUser.set(u);
		GroupInfo gi = groupService.findOne(u.getGroupId());
		List<RoleInfo> roles = roleService.getRoleByUser(userId);
		SystemContextHolder.setSessionContext(TransportUserFilter.getSessionVariable(u,gi,roles));
		Map<String,BaseDao> map = SpringUtil.getApplicationContext().getBeansOfType(BaseDao.class);
		Set<Map.Entry<String, BaseDao>> set = map.entrySet();
		for(Iterator<Map.Entry<String, BaseDao>> it = set.iterator(); it.hasNext();){
			BaseDao bd = it.next().getValue();
			String name = bd.getEntityClass().getName();
			if(name.endsWith(moduleClassName)){
				bd.findAll();
				ajax(bd.getHqlLog());
				break;
			}
		}
		return null;
	}
	
	public String getUserInfo(){
		UserInfo u = userSerivce.findOne(userId);
		GroupInfo g = groupService.findOne(u.getGroupId());
		List<RoleInfo> rolelist = roleService.getRoleByUser(userId);
		Map<String,String> map = new HashMap<String, String>();
		map.put("groupInfo", g.getGroupName());
		String roles = "";
		for(int i=0; i<rolelist.size(); i++){
			roles+=(rolelist.get(i).getRoleName()+",");
		}
		map.put("roles", roles);
		String forkGroup = "无";
		if(u.getIsForkGroup()!=null && u.getIsForkGroup()>0){
			GroupInfo fk = groupService.findOne(u.getIsForkGroup());
			forkGroup = fk.getGroupName();
		}
		map.put("forkGroup", forkGroup);
		Gson gson = new Gson();
		String str = gson.toJson(map);
		ajax(str);
		return null;
		
	}
	
	public List<DataFilter> getDataFilterList(){
		return dataFilterService.findByModule(moduleClassName);
	}

	public String getConfig() {
		return config;
	}

	public void setConfig(String config) {
		this.config = config;
	}
	
}
