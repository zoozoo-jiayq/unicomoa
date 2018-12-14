package cn.com.qytx.cbb.org.job;

import java.util.List;

import javax.annotation.Resource;

import cn.com.qytx.cbb.file.config.FilePathConfig;
import cn.com.qytx.cbb.org.action.mobile.OrgInitAction;
import cn.com.qytx.cbb.org.service.GenerateDataService;
import cn.com.qytx.platform.org.domain.CompanyInfo;
import cn.com.qytx.platform.org.service.ICompany;

public class GenerateCompanyBaseInfo  {

	@Resource(name="generateDataService")
	private GenerateDataService generateDataService;
	
	@Resource(name="companyService")
	private ICompany companyService;
	
	@Resource
	private FilePathConfig filePathConfig;
	
	public void execute() {
		System.out.println("自动生成大数据文件启动...");
		// TODO Auto-generated method stub
		List<CompanyInfo> companylist = companyService.findAll();
		String localpath = filePathConfig.getFileLocalPath();
		String uploadpath = filePathConfig.getFileUploadPath();
		for(int i=0; i<companylist.size(); i++){
			int companyId = companylist.get(i).getCompanyId();
			generateDataService.generateFileWithTargetCompanyInfo(companyId, uploadpath+OrgInitAction.BigData_PREX, localpath+OrgInitAction.BigData_PREX,false);
		}
		System.out.println("自动生成大数据文件结束...");
	}
	
	public void hh(){
		execute();
	}

}
