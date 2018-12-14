package cn.com.qytx.cbb.org.action.mobile;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.com.qytx.cbb.file.config.FilePathConfig;
import cn.com.qytx.cbb.org.service.GenerateDataService;
import cn.com.qytx.platform.base.action.BaseControllerSupport;
import cn.com.qytx.platform.org.domain.CompanyInfo;
import cn.com.qytx.platform.org.service.ICompany;
import cn.com.qytx.platform.org.service.IUser;

/**
 * <br/>功能 通讯录初始化接口  
 * <br/>版本  1.0
 * <br/>开发人员  zyf
 * <br/>创建日期  2016年1月6日
 * <br/>修改日期  2016年1月6日
 * <br/>修改列表
 */
@Controller
@RequestMapping("/org/mobile")
public class OrgInitAction extends BaseControllerSupport {

	/**
	 * 描述含义
	 */
	private static final long serialVersionUID = 1139516893855844031L;

	protected static final Logger logger = Logger.getLogger(OrgInitAction.class
			.getName());

	public final static String BigData_PREX = "/bigData/";
	public final static String BigData_FileName = "userInfo.zip";
	public final static Long ONE_DAY = 24 * 60 * 60 * 1000l;

	// 单位业务接口
	@Resource(name = "generateDataService")
	GenerateDataService generateDataService;
	// 用户业务接口
	@Resource(name = "userService")
	IUser userService;
	// 数据库文件
	@Resource(name = "filePathConfig")
	private FilePathConfig filePathConfig;

	@Resource(name = "companyService")
	private ICompany companyService;

	/**
	 * /org/mobile/getTotalOrg.action 功能:获得大数据
	 * 
	 * @return
	 */
	@RequestMapping("/init.c")
	public void getTotalOrg(
			@RequestParam(value = "companyId", required = true) Integer companyId,
			@RequestParam(value = "userId", required = true) Integer userId,
			HttpServletResponse response)
			throws Exception {

		int count = filePathConfig.getBigDataStand();
		String url = filePathConfig.getFileViewPath().trim() + BigData_PREX;
		String dbFilePath = filePathConfig.getFileUploadPath().trim()
				+ BigData_PREX;
		String dbFileLocalPath = filePathConfig.getFileLocalPath().trim()
				+ BigData_PREX;
		Map<String, Object> result = generateDataService.initContact(companyId,
				userId, count, url, dbFilePath, dbFileLocalPath);
		SUCCESS(result,response);
	}

	/**
	 * /org/mobile/getUpdateOrg.action
	 * 
	 * @return
	 */
	@RequestMapping("/getBasicInfo.c")
	public void getBasicInfo(
			@RequestParam(value = "companyId", required = true) Integer companyId,
			@RequestParam(value = "userId", required = true) Integer userId,
			@RequestParam(value = "lastUpdateTime", required = false) String lastUpdateTime,
			@RequestParam(value = "infoType", required = false) Integer infoType,
			@RequestParam(value = "isOrg", required = false) Integer isOrg,
			HttpServletResponse response)
			throws Exception {
		logger.info("接收到请求,method=getBasicInfo,companyId=" + companyId
				+ ",userId=" + userId + ",lastUpdateTime=" + lastUpdateTime
				+ ",infoType=" + infoType + ",isOrg=" + isOrg);
//		Map<String, Object> result = generateDataService.getBasicInfo(companyId, userId,
//				lastUpdateTime);
		Map<String, Object> result = generateDataService.getBasicInfoFromCache(companyId,userId,lastUpdateTime);
		SUCCESS(result,response);
	}

	/**
	 * /org/mobile/generateTargetCompanyData.action 功能:主动调用生成指定单位的大数据文件
	 * 
	 * @return
	 */
	@RequestMapping("/generateTargetCompanyData.c")
	public void generateTargetCompanyTotalOrg(
			@RequestParam(value = "companyId", required = false) Integer companyId)
			throws Exception {
		String localpath = filePathConfig.getFileLocalPath();
		String uploadpath = filePathConfig.getFileUploadPath();
		if (companyId == null) {
			List<CompanyInfo> companylist = companyService.findAll();
			for (int i = 0; i < companylist.size(); i++) {
				int cId = companylist.get(i).getCompanyId();
				generateDataService.generateFileWithTargetCompanyInfo(cId,
						uploadpath + BigData_PREX, localpath + BigData_PREX,
						true);
			}
		} else {
			generateDataService.generateFileWithTargetCompanyInfo(companyId,
					uploadpath + BigData_PREX, localpath + BigData_PREX, true);
		}
	}

}
