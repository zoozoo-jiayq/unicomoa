package cn.com.qytx.cbb.org.action;

import javax.annotation.Resource;

import cn.com.qytx.platform.base.action.BaseActionSupport;
import cn.com.qytx.platform.org.domain.CompanyInfo;
import cn.com.qytx.platform.org.service.ICompany;

/**
 * 企业管理action
 * 版本: 1.0
 * 开发人员：黄普友
 * 创建日期: 13-3-28
 * 修改日期：upadte by 贾永强
 * 修改列表：
 */
public class CompanyAction extends BaseActionSupport{

	@Resource
	private ICompany companyService;
    
	private CompanyInfo company;
	
    public CompanyInfo getCompany() {
		return company;
	}

	public void setCompany(CompanyInfo company) {
		this.company = company;
	}

	/**
     * 功能：查看公司信息
     * @param
     * @return
     * @throws   
     */
    public String viewCompanyInfo(){
    	return SUCCESS;
    }
    
    /**
     * @throws Exception 
     * 功能：修改公司信息
     * @param
     * @return
     * @throws   
     */
    public String updateCompanyInfo() throws Exception{
    	Integer companyId = company.getCompanyId();
    	if(companyId != null && companyId.intValue() > 0){
    		CompanyInfo oldCompanyInfo = companyService.findOne(companyId);
    		oldCompanyInfo.setCompanyName(company.getCompanyName());
    		oldCompanyInfo.setLogUrl(company.getLogUrl());
    		oldCompanyInfo.setShortName(company.getShortName());
    		oldCompanyInfo.setSysName(company.getSysName());
    		oldCompanyInfo.setEmail(company.getEmail());
    		oldCompanyInfo.setLinkMan(company.getLinkMan());
    		oldCompanyInfo.setTel(company.getTel());
    		oldCompanyInfo.setAddress(company.getAddress());
    		oldCompanyInfo.setPhilosophy(company.getPhilosophy());
    		oldCompanyInfo.setIntroduction(company.getIntroduction());
    		companyService.saveOrUpdate(oldCompanyInfo);
    	}
    	/*LogoConfig.getInstance().setLogoUrl(company.getLogUrl());
    	LogoConfig.getInstance().setSysName(company.getSysName());*/
    	CompanyInfo sessionCompany=(CompanyInfo)this.getSession().getAttribute("companyInfo");
    	if(sessionCompany != null){
    		sessionCompany.setSysName(company.getSysName());
    		sessionCompany.setLogUrl(company.getLogUrl());
    	}
    	ajax("success");
    	return null;
    }
    
    public CompanyInfo getCompanyInfo(){
    	int companyId = getLoginUser().getCompanyId();
    	return companyService.findOne(companyId);
    }
}
