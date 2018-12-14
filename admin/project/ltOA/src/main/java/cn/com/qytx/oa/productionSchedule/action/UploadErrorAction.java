package cn.com.qytx.oa.productionSchedule.action;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import cn.com.qytx.cbb.file.config.FilePathConfig;
import cn.com.qytx.platform.base.action.BaseActionSupport;

/**
 * 功能:上传错误接口 
 * 版本: 1.0
 * 开发人员: zyf
 * 创建日期: 2016年3月4日
 * 修改日期: 2016年3月4日
 * 修改列表:
 */
public class UploadErrorAction extends BaseActionSupport {

    @Resource(name="filePathConfig")
    private FilePathConfig filePathConfig;
	
	private String date;
	
	/**
	 * 功能：上报错误日期
	 */
	public void uploadError(){
		try {
			File f = new File(filePathConfig.getFileUploadPath()+"/date.json");
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
			Gson json = new Gson();
			Map<String,Object> m = new HashMap<String, Object>();
			if(f.exists()){
				//判断是否已经有错误日志
				String str = "";
			    BufferedReader br = new BufferedReader(new FileReader(filePathConfig.getFileUploadPath()+"/date.json"));
	            String lineTxt = br.readLine();
	            while (lineTxt != null)
	            {
	            	str+=lineTxt;
	                lineTxt = br.readLine();
	            }
	            br.close();
	            
	            if(StringUtils.isNotEmpty(str)){
	            	m = json.fromJson(str,  new TypeToken<Map<String,Object>>(){}.getType());
	            }
			}
			if(m.get("date")!=null){
				Date dt1 = format1.parse(date);
				Date dt2 = format1.parse(m.get("date").toString());
				if(dt1.getTime() > dt2.getTime()){
					m.put("date", date);
				}
			}else{
				m.put("date", date);
			}
			List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
			if(m.get("log") != null){
				list = (List<Map<String, Object>>) m.get("log");
			}
			
			//封装错误日期信息
			Map<String, Object> m1 = new HashMap<String, Object>();
			m1.put("userId", getLoginUser().getUserId());
			m1.put("date", date);
			m1.put("insertTime", format.format(new Date()));
			list.add(m1);
			m.put("log", list);
			String data = json.toJson(m);
			
			InputStream is = new ByteArrayInputStream(data.getBytes("UTF-8"));   
			FileOutputStream fos = new FileOutputStream(f);
			byte[] b = new byte[1024];
			int l = is.read(b);
			while(l != -1){
				fos.write(b,0,l);
				l = is.read(b);
			}
			is.close();
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
			ajax(0);
			return;
		}
		ajax(1);
	}
	
	/**
	 * 功能：获取安全生产天数
	 */
	public void getSafeDays(){
		try {
			File f = new File(filePathConfig.getFileUploadPath()+"/date.json");
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat format1 = new SimpleDateFormat("yyyy年MM月dd日");
			String curDate = format1.format(new Date());
			Gson json = new Gson();
			String day = "2016-03-01";
			if(f.exists()){
				//判断是否已经有错误日志
				String str = "";
			    BufferedReader br = new BufferedReader(new FileReader(filePathConfig.getFileUploadPath()+"/date.json"));
	            String lineTxt = br.readLine();
	            while (lineTxt != null)
	            {
	            	str+=lineTxt;
	                lineTxt = br.readLine();
	            }
	            br.close();
	            
	            if(StringUtils.isNotEmpty(str)){
	            	Map<String, Object> map = json.fromJson(str,  new TypeToken<Map<String,Object>>(){}.getType());
	            	if(map!=null && map.get("date")!=null){
	            		day = map.get("date").toString();
	            	}
	            }
			}
			
			long nowTime = System.currentTimeMillis();
			long dayTime = format.parse(day).getTime();
			int diff = ((Long) ((nowTime - dayTime)/(24*60*60*1000))).intValue();
			Map<String,Object> jsonMap = new HashMap<String, Object>();
			jsonMap.put("diff", diff);
			jsonMap.put("curDate", curDate);
			Gson gson = new Gson();
			String jsonDate = gson.toJson(jsonMap);
			ajax(jsonDate);
		} catch (Exception e) {
			e.printStackTrace();
			ajax(0);
			return;
		}
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
}
