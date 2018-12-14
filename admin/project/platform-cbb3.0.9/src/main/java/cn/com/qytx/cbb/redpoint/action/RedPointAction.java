package cn.com.qytx.cbb.redpoint.action;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import cn.com.qytx.cbb.redpoint.utils.HttpPostUtil;
import cn.com.qytx.platform.base.action.BaseActionSupport;

import com.google.gson.Gson;

/**
 * 功能: 模块待处理数量统计类 
 * 版本: 1.0
 * 开发人员: zyf
 * 创建日期: 2015年3月17日
 * 修改日期: 2015年3月17日
 * 修改列表:
 */
public class RedPointAction extends BaseActionSupport{

	/**
	 * 描述含义
	 */
	private static final long serialVersionUID = 8422588011858860752L;
	
	/**
	 * 用户id 
	 */
	private Integer userId;
	
	/**
	 * 单位id
	 */
	private Integer companyId;
	
	/**
	 * 公告模块id
	 */
	private Integer columnId;
	
	/**
	 * 模块待处理数量统计
	 */
	public void getRedPointCount(){
		
		if(userId == null || companyId == null){
			ajax("101||参数不正确");
			return;
		}
		
		Properties prop = new Properties(); 
		InputStream is = getClass().getResourceAsStream("/redPointRequestUrl.properties");
		Map<String, Object> result = new HashMap<String, Object>();
		try{
			prop.load(is);
			for(Enumeration  e = prop.propertyNames();e.hasMoreElements();){
				String key = e.nextElement().toString();
				try {
					Map<String,Object> param = new HashMap<String, Object>();
					param.put("userId", userId);
					param.put("companyId", companyId);
					param.put("columnId", columnId);
					String count = HttpPostUtil.doPost(prop.getProperty(key), param);
					result.put(key, count);
				} catch (Exception e1) {
					e1.printStackTrace();
					LOGGER.error(e1.getMessage());
				}
			}
			
		}catch(IOException e){
			e.printStackTrace();
			LOGGER.error(e.getMessage());
		}
		
		Gson json = new Gson();
		ajax("100||"+json.toJson(result));
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Integer getColumnId() {
		return columnId;
	}

	public void setColumnId(Integer columnId) {
		this.columnId = columnId;
	}
	
}
