package cn.com.qytx.cbb.secret.action;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import cn.com.qytx.cbb.secret.domain.SecretProperty;
import cn.com.qytx.cbb.secret.sevice.ISecretProperty;
import cn.com.qytx.platform.base.action.BaseActionSupport;

import com.google.gson.Gson;

/**
 * 功能: 用户信息保密设置
 * 版本: 1.0
 * 开发人员: zyf
 * 创建日期: 2014年12月23日
 * 修改日期: 2014年12月23日
 * 修改列表:
 */
public class SecretAction extends BaseActionSupport {

	/**
	 * 描述含义
	 */
	private static final long serialVersionUID = -4294413870748964054L;
	
	@Autowired
	ISecretProperty secretPropertyService;
	
	private String propertyListJson;

	/**
	 * 功能：到单位用户信息保密字段设置页面
	 * @return
	 */
	public String toMain(){
		Properties prop = new Properties();
		try {
			prop.load(getClass().getResourceAsStream("/secretProperty.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.getRequest().setAttribute("propertyList", prop);
		return SUCCESS;
	}
	
	/**
	 * 功能：获得单位设置的用户信息保密字段
	 */
	public void getPropertyList(){
		List<SecretProperty> list = secretPropertyService.allSecretProperty(this.getLoginUser().getCompanyId());
		if(list!=null){
			ajax(list);
		}else{
			ajax("[]");
		}
	}
	
	
	/**
	 * 功能：保存单位保密信息字段设置
	 */
	public void saveProperty(){
		try{
			secretPropertyService.deleteAll(this.getLoginUser().getCompanyId());
			if(StringUtils.isNotEmpty(propertyListJson)){
				Gson json  = new Gson();
				List<Map<String,Object>> list = json.fromJson(propertyListJson, List.class);
				for(Map<String,Object> map:list){
					SecretProperty secretProperty = new SecretProperty();
					secretProperty.setAttribute(map.get("attribute").toString());
					secretProperty.setAttributeName(map.get("attributeName").toString());
					secretProperty.setCompanyId(this.getLoginUser().getCompanyId());
					secretProperty.setCreateUserId(this.getLoginUser().getUserId());
					secretProperty.setCreatTime(new Timestamp(System.currentTimeMillis()));
					secretPropertyService.saveOrUpdate(secretProperty);
				}
			}
			ajax(1);
		}catch(Exception e){
			ajax(0);
		}
	}

	public String getPropertyListJson() {
		return propertyListJson;
	}

	public void setPropertyListJson(String propertyListJson) {
		this.propertyListJson = propertyListJson;
	}

}
