package cn.com.qytx.cbb.secret.action;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import cn.com.qytx.cbb.secret.domain.SecretProperty;
import cn.com.qytx.cbb.secret.domain.SecretSettings;
import cn.com.qytx.cbb.secret.sevice.ISecretProperty;
import cn.com.qytx.cbb.secret.sevice.ISecretSettings;
import cn.com.qytx.platform.base.action.BaseActionSupport;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.Sort;
import cn.com.qytx.platform.base.query.Sort.Direction;

/**
 * 功能: 保密设置
 * 版本: 1.0
 * 开发人员: zyf
 * 创建日期: 2014年12月23日
 * 修改日期: 2014年12月23日
 * 修改列表:
 */
public class SecretSettingsAction extends BaseActionSupport {
	
	@Autowired
	ISecretSettings secretSettingsService;
	
	@Autowired
	ISecretProperty secretPropertyService;
	
	/**
	 * 主键 
	 */
	private Integer id;
	
	/**
	 * 选中的设置id
	 */
	private String ids;
	
	private SecretSettings secret ;
	
	
	/**
	 * 功能：获得保密设置列表（分页）
	 */
	public void secretSettingsList(){
		Sort sort = new Sort(new Sort.Order(Direction.DESC,"createTime"));
		Page<SecretSettings> pageInfo = secretSettingsService.getSecretSettingsByPage(this.getPageable(sort), this.getLoginUser().getCompanyId());
		List<Map<String,Object>> listMap = new ArrayList<Map<String,Object>>();
		if(pageInfo!=null){
			for(SecretSettings secret:pageInfo.getContent()){
				Map<String,Object> map = new HashMap<String, Object>();
				map.put("id", secret.getId());
				map.put("attributeName", secret.getAttributeName());
				map.put("applyUserNames", secretSettingsService.getUserNamesByIds(secret.getApplyUserIds()));
				map.put("invisibleUserNames", secretSettingsService.getUserNamesByIds(secret.getInvisibleUserIds()));
				listMap.add(map);
			}
		}
		ajaxPage(pageInfo, listMap);
	}
	
	/**
	 * 功能：跳转到保密设置修改页面
	 * @return
	 */
	public String toEdit(){
		secret = secretSettingsService.findOne(id);
		secret.setApplyUserNames(secretSettingsService.getUserNamesByIds(secret.getApplyUserIds()));
		secret.setInvisibleUserNames(secretSettingsService.getUserNamesByIds(secret.getInvisibleUserIds()));		
		List<SecretProperty> list = secretPropertyService.allSecretProperty(this.getLoginUser().getCompanyId());
		this.getRequest().setAttribute("propertyList", list);
		
		String attrbutes=secret.getAttribute();		
		if(StringUtils.isNotBlank(attrbutes) ){
		  List<Map<String,String>> listMap = new ArrayList<Map<String,String>>();
		  String[] attrsArr=attrbutes.split(",");
		  String attrs="";
		  for(String attr:attrsArr){
			  attrs+=attr+",";
		  }
		  this.getRequest().setAttribute("attrArr", attrs);		  
		
		}
		return SUCCESS;
	}
	
	/**
	 * 功能：删除所选中的设置
	 */
	public void delete(){
		try {
			secretSettingsService.deleteSettingsByIds(ids,this.getLoginUser().getCompanyId());
			ajax(1);
		} catch (Exception e) {
			ajax(0);
		}
	}
	
	/**
	 * 功能：新增保密设置
	 */
	public void save(){
		try {
			if(secret!=null){
				secret.setCompanyId(this.getLoginUser().getCompanyId());
				secret.setCreateTime(new Timestamp(System.currentTimeMillis()));
				secret.setCreateUserId(this.getLoginUser().getUserId());
				secret.setIsDelete(0);
				secretSettingsService.saveUpdate(secret,this.getLoginUser().getCompanyId());
				ajax(1);
			}else{
				ajax(0);
			}
		} catch (Exception e) {
			ajax(0);
		}
	}

	/**
	 * 功能：修改保密设置
	 */
	public void update(){
		try {
			if(secret!=null){
				secret.setCompanyId(this.getLoginUser().getCompanyId());
				secretSettingsService.saveUpdate(secret,this.getLoginUser().getCompanyId());
				ajax(1);
			}else{
				ajax(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
			ajax(0);
		}
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public SecretSettings getSecret() {
		return secret;
	}

	public void setSecret(SecretSettings secret) {
		this.secret = secret;
	}
	
}
