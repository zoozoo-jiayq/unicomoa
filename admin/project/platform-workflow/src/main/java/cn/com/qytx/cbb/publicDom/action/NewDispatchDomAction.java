package cn.com.qytx.cbb.publicDom.action;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import cn.com.qytx.cbb.publicDom.service.PublicDocumentConstant;
import cn.com.qytx.cbb.systemSet.domain.SysConfig;
import cn.com.qytx.cbb.systemSet.service.SysConfigService;
import cn.com.qytx.platform.org.domain.CompanyInfo;
import cn.com.qytx.platform.org.domain.GroupInfo;
import cn.com.qytx.platform.org.service.IGroup;

/**
 * 功能：发文模块控制器，处理发文的页面的一些特殊处理
 * 版本: 1.0
 * 开发人员：贾永强
 * 创建日期: 上午11:00:46 
 * 修改日期：上午11:00:46 
 * 修改列表：
 */
public class NewDispatchDomAction extends MyBaseAction {
	/*------------spring注入属性begin---------------*/
    @Resource(name = "groupService")
    private IGroup groupService;
    @Resource
    private SysConfigService sysConfigService;
	
	/*------------spring注入属性end  ---------------*/

	/*---------------私有属性begin------------------*/

	/**
	 * 功能：跳转到新增页面
	 * @param
	 * @return
	 * @throws   
	 */
	public String toAdd(){
		return "add";
	}
	
	/**
	 * 功能：进入发文列表
	 * @param
	 * @return
	 * @throws   
	 */
	public String dispatchList(){
		if(searchType == null || searchType.equals("")){
			if(menu == 5){
				return "writeDoc";
			}else if(menu == 6){
				return "approve";
			}else if(menu == 7){
				return "red";
			}else if(menu == 8){
				return "send";
			}
		}else if(searchType.equals("completed")){
			if(menu == 5){
				return "writeDoc_complete";
			}else if(menu == 6){
				return "approve_complete";
			}else if(menu == 7){
				return "red_complete";
			}else if(menu == 8){
				return "send_complete";
			}
		}
		if(menu == 10){
			return "dispatch_zip";
		}
		return null;
	}
	
	public String getDefaultName(){
		GroupInfo group = groupService.getForkGroup(getLoginUser().getCompanyId(),getLoginUser().getUserId());
		if(group!=null){
			return group.getGroupName();
		}else{
			CompanyInfo ci = (CompanyInfo) getSession().getAttribute("companyInfo");
			return ci.getCompanyName();
		}
	}
	
	/**
     * 功能：获取发文拟稿的操作列表
     * 作者：jiayongqiang
     * 参数：
     * 输出：
     */
    public List<String> getStartOperations(){
        List<String> list = new ArrayList<String>();
        String operations = sysConfigService.getSysConfig().get(SysConfig.DOM_DISPATCH_NIGAO);
        String[] strs = operations.split(",");
        for(int i=0; i<strs.length; i++){
            String temp = strs[i];
            String r = PublicDocumentConstant.OPERATION_MAP.get(temp);
            list.add(r);
        }
        return list;
    }

}
