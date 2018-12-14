package cn.com.qytx.cbb.version.action;

import java.sql.SQLException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.com.qytx.cbb.version.domain.VersionInfo;
import cn.com.qytx.cbb.version.service.IVersion;
import cn.com.qytx.platform.base.action.BaseControllerSupport;

import com.google.gson.Gson;

@Controller
@RequestMapping("/version/")
public class UpdateVersionAction extends BaseControllerSupport {

	private static final long serialVersionUID = 1L;
	
	protected static final Logger logger = Logger.getLogger(UpdateVersionAction.class.getName());
	/**
	 * 版本信息管理
	 */
	@Resource(name="versionService")
	IVersion versionService;
	
	private String companyId;//公司ID
	
	private String userId;//人员ID
	
	private String versionCode;//当前版本号
	
	private Integer type;//版本类别 0表示安卓 1表示IOS
	
	@RequestMapping(value="/control.c")
	public void getNewVersion(@RequestParam(value="companyId",required=true)String companyId,
							@RequestParam(value="versionCode",required=true)String versionCode,
							@RequestParam(value="type",required=false,defaultValue="0")Integer type,
							@RequestParam(value="userId",required=true) String userId,
							HttpServletResponse response) throws SQLException{
		String ret="";
		VersionInfo ver=versionService.getVersionInfo(companyId, userId, versionCode,type);
		if(ver!=null){
			Gson gson=new Gson();
			String verJson=gson.toJson(ver);
			SUCCESS(verJson,response);
		}else{
			SERVER_ERROR("无更新版本",response);
		}
		
	}

	
}
