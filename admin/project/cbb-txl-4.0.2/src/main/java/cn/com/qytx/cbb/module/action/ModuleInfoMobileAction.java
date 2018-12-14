package cn.com.qytx.cbb.module.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import cn.com.qytx.cbb.file.config.FilePathConfig;
import cn.com.qytx.cbb.file.domain.Attachment;
import cn.com.qytx.cbb.file.service.IAttachment;
import cn.com.qytx.cbb.module.domain.ModuleInfoMobile;
import cn.com.qytx.cbb.module.service.IModuleInfoMobile;
import cn.com.qytx.platform.base.action.BaseActionSupport;
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.utils.JsonUtils;

/**
 * 手机模块action
 * @author liyanlei
 *
 */
public class ModuleInfoMobileAction extends BaseActionSupport{
	
	@Resource(name="moduleInfoMobileService")
	private IModuleInfoMobile moduleInfoMobileService;
	@Resource(name="filePathConfig")
	private FilePathConfig filePathConfig;
	@Resource(name="attachmentService")
	private IAttachment attachmentService;
	
	private String keyword;
	private Integer statue;
	private Integer id;
	private String name;
	private String order;
	private ModuleInfoMobile mobile;
	private String code;
	private List<ModuleInfoMobile> list;
	private Integer roleId;
	private Integer userId;
	
	/**
	 * 分页查询
	 * @return
	 */
	public String list(){
		List<ModuleInfoMobile> list = moduleInfoMobileService.findPager(keyword);
		List<Map<String,Object>> returnList = new ArrayList<Map<String,Object>>();
		for(ModuleInfoMobile moduleInfoMobile:list){
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("no",moduleInfoMobile.getId());
			map.put("name",moduleInfoMobile.getName());
			map.put("orderList",moduleInfoMobile.getOrderList());
			map.put("url",moduleInfoMobile.getUrl()==null?"":moduleInfoMobile.getUrl());
			map.put("code",moduleInfoMobile.getCode());
			map.put("level",moduleInfoMobile.getOrderList().split("-").length);
			map.put("icon",filePathConfig.getFileViewPath()+"upload//"+moduleInfoMobile.getIcon());
			map.put("statue",moduleInfoMobile.getStatue());
			returnList.add(map);
		}
		  Map<String, Object> jsonMap = new HashMap<String, Object>();
          jsonMap.put("iTotalRecords",list.size());
          jsonMap.put("iTotalDisplayRecords",list.size());
          jsonMap.put("aaData",returnList);
          ajax(JsonUtils.generJsonString(jsonMap));
		return null;
	}
	/**
	 * 更新状态
	 * @return
	 */
	public String updateStatue(){
		Map<String,Object> returnMap = new HashMap<String, Object>();
		moduleInfoMobileService.updateStatue(id,statue);
		returnMap.put("success",true);
		ajax(JsonUtils.generJsonString(returnMap));
		return null;
	}
	/**
	 * 验证名称
	 * @return
	 */
	public String valiName(){
		ajax(moduleInfoMobileService.valiName(id,name));
		return null;
	}
	/**
	 * 验证排序
	 * @return
	 */
	public String valiOrder(){
		ajax(moduleInfoMobileService.valiOrder(id,order));
		return null;
	}
	/**
	 * 上传图标
	 * @return
	 */
	public String filePath(){
	  Attachment attachment = attachmentService.findOne(id);
	  ajax(filePathConfig.getFileViewPath()+"upload//"+attachment.getAttachFile());
	  return null;
	}
	/**
	 * 手机端列表
	 * @return
	 */
	public String mobileList(){
		list = moduleInfoMobileService.getRootTreeList();
		return "mobileList";
	}
	/**
	 * 删除
	 * @return
	 */
	public String del(){
		moduleInfoMobileService.delete(id,true);
		return null;
	}
	/**
	 * 新增
	 * @return
	 */
	public String save(){
		UserInfo userInfo = this.getLoginUser();
		if(userInfo!=null&&mobile!=null){
			mobile.setCompanyId(userInfo.getCompanyId());
		}
		moduleInfoMobileService.saveOrUpdate(mobile);
		return null;
	}
	/**
	 * 验证code
	 * @return
	 */
	public String valiCode(){
		ajax(moduleInfoMobileService.valiCode(id,code));
		return null;
	}
	/**
	 * 更新
	 * @return
	 */
	public String viewUpdate(){
		mobile = moduleInfoMobileService.findOne(id);
		return "viewUpdate";
	}
	/**
	 * 登录成功
	 * @return
	 */
	public String mobileIndex(){
		list = moduleInfoMobileService.mobileIndex(id,userId);
		List<Map<String,Object>> returnList = new ArrayList<Map<String,Object>>();
		for(ModuleInfoMobile moduleInfoMobile:list){
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("id",moduleInfoMobile.getId());
			map.put("name",moduleInfoMobile.getName());
			map.put("code",moduleInfoMobile.getCode());
			map.put("url",moduleInfoMobile.getUrl());
			map.put("icon",filePathConfig.getFileViewPath()+"upload//"+moduleInfoMobile.getIcon());
			map.put("orderList",moduleInfoMobile.getOrderList());
			returnList.add(map);
		}
		ajax("100||"+JsonUtils.generJsonString(returnList));
		return null;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public Integer getStatue() {
		return statue;
	}
	public void setStatue(Integer statue) {
		this.statue = statue;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	public ModuleInfoMobile getMobile() {
		return mobile;
	}
	public void setMobile(ModuleInfoMobile mobile) {
		this.mobile = mobile;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	} 
	public String getViewPath(){
		return filePathConfig.getFileViewPath();
	}
	public List<ModuleInfoMobile> getList() {
		return list;
	}
	public void setList(List<ModuleInfoMobile> list) {
		this.list = list;
	}
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
}
