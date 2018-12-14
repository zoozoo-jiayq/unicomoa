package cn.com.qytx.cbb.affairSetting.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;

import cn.com.qytx.cbb.affairSetting.domain.AffairSetting;
import cn.com.qytx.cbb.affairSetting.service.IAffairSetting;
import cn.com.qytx.cbb.affairs.service.IAffairsBody;
import cn.com.qytx.platform.base.action.BaseActionSupport;

public class AffairSettingAction extends BaseActionSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Resource
	IAffairSetting affairSettingService;
	@Resource
	IAffairsBody affairsBodyService;
	/**
	 * 设置信息集合 "12,1,0,1_13,1,1,0_"
	 */
	private String settings;
	public void getAffairSettingList(){
		try
		{
			List<AffairSetting> settingList=affairSettingService.findList();
			List<Map<String, Object>> list=new ArrayList<Map<String, Object>>();
			int i=1;
			for(AffairSetting affairSetting :settingList)
			{
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("no", i);
				map.put("id", affairSetting.getId());
				map.put("moduleName", affairSetting.getModuleName());
				map.put("affairPriv", affairSetting.getAffairPriv());
				map.put("smsPriv", affairSetting.getSmsPriv());
				map.put("pushPriv", affairSetting.getPushPriv());
				list.add(map);
				i++;
			}
			Map<String, Object> jsonMap = new HashMap<String, Object>();
	        jsonMap.put("aaData", list);
	        ajax(jsonMap);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
	/**
	 * 更新设置信息
	 */
	public void update(){
		try
		{
			List<AffairSetting> affairSettings=new ArrayList<AffairSetting>();
			if(StringUtils.isNoneBlank(settings))
			{
				settings=settings.substring(0, settings.length()-1);
				String[] arry=settings.split("_");
				for(String setting:arry){
					String[] subArry=setting.split(",");
					if(subArry.length>=0)
					{
						AffairSetting affairSetting=affairSettingService.findById(Integer.parseInt(subArry[0]));
						affairSetting.setAffairPriv(Integer.parseInt(subArry[1]));
						affairSetting.setSmsPriv(Integer.parseInt(subArry[2]));
						affairSetting.setPushPriv(Integer.parseInt(subArry[3]));
						affairSettings.add(affairSetting);
					}
				}
				affairSettingService.updateAll(affairSettings);
				ajax("0");//保存成功
			}
			else
			{
				ajax("1");//保存失败
			}
		}
		catch(Exception e)
		{
			ajax("1");//保存失败
			e.printStackTrace();
		}
	}
	public String getSettings() {
		return settings;
	}
	public void setSettings(String settings) {
		this.settings = settings;
	}
	
	
	
	
	
}
