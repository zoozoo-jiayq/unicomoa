package cn.com.qytx.oa.carManage.action;

import java.io.BufferedInputStream;
import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import cn.com.qytx.oa.carManage.service.ICarInOut;
import cn.com.qytx.oa.carManage.util.CommonClass;
import cn.com.qytx.platform.base.action.BaseActionSupport;

/**
 * @author 吴胜光
 * @time 2017/10/17
 * @des 车辆进出接口（第三方平台调用）
 */
public class CarInOutAction extends BaseActionSupport{

	/**
	 * 序列化
	 */
	private static final long serialVersionUID = 6564750362719224772L;

	/**
	 * 日志
	 */
	protected static final Logger LOGGER = Logger.getLogger(CarInOutAction.class.getName());
	
	@Autowired
	private ICarInOut carInOutService;
	
	private JSONObject jsonData;

	/**
	 * 车辆进场/出场接受消息接口
	 * code 0  成功 1失败
	 */
	public void carInOut(){
		String command = "";
		try {
			HttpServletRequest request = getRequest();
			request.setCharacterEncoding("UTF-8");
			//BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
			BufferedInputStream in = new BufferedInputStream(request.getInputStream());
			byte[] b = new byte[1024];
	        StringBuilder sb_utf = new StringBuilder();
	        int i=0;
	        do {
	            i = in.read(b);
	            if (i > 0) {
	                sb_utf.append(new String(b, 0, i,"UTF-8"));
	            }
	        } while(i == 1024);
	        String paramVal = sb_utf.toString();
	        LOGGER.info("carInOut paramVal:"+paramVal);
	        JSONObject json = JSONObject.fromObject(paramVal);
	        command = json.getString("command");
//	        String charset = json.getString("charset");
//	        String device_id = json.getString("device_id");
//	        String message_id = json.getString("message_id");
//	        String sign = json.getString("sign");
//	        String sign_type = json.getString("sign_type");
	        JSONObject content = JSONObject.fromObject(json.get("biz_content"));
	        String car_no = (String)content.get("car_license_number");
	        car_no = URLDecoder.decode(car_no, "utf-8");
			if("REPORT_CAR_IN_LIST".equals(command)){
				//车辆进场
				carInOutService.carIn(car_no);
			}else if("REPORT_CAR_OUT_LIST".equals(command)){
				//车辆出厂
				carInOutService.carOut(car_no);
			}
			
			jsonData = CommonClass.setRequestHeadParam(command);
			JSONObject biz_content = new JSONObject();
			biz_content.element("code", 0);
			biz_content.element("msg", "ok");
			jsonData.element("biz_content", biz_content);
			LOGGER.info("carInOut result:"+jsonData.toString());
			ajax(jsonData.toString());
			/*HttpServletResponse response = ServletActionContext.getResponse();
            response.setHeader("ContentType", "text/json");
            response.setCharacterEncoding("utf-8");
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.getWriter().write(jsonData.toString());
            response.getWriter().flush(); */
		} catch (Exception e) {
			jsonData = CommonClass.setRequestHeadParam(command);
			JSONObject biz_content = new JSONObject();
			biz_content.element("code", 1);
			biz_content.element("msg", e);
			jsonData.element("biz_content", biz_content);
			LOGGER.info("carInOut result:"+jsonData.toString());
			ajax(jsonData.toString());
		}
	}

	public JSONObject getJsonData() {
		return jsonData;
	}

	public void setJsonData(JSONObject jsonData) {
		this.jsonData = jsonData;
	}

	
}
