package cn.com.qytx.oa.deskTop.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;

import cn.com.qytx.cbb.myapply.service.IMyProcessed;
import cn.com.qytx.cbb.myapply.service.IMyWaitProcess;
import cn.com.qytx.cbb.notify.service.IPlatformParameter;
import cn.com.qytx.oa.deskTop.service.IDesktop;
import cn.com.qytx.platform.base.action.BaseActionSupport;
import cn.com.qytx.platform.org.domain.CompanyInfo;
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.org.service.ICompany;
import cn.com.qytx.platform.org.service.IUser;
import cn.com.qytx.platform.utils.JsonUtils;

/**
 * 卡片式首页
 * @author CQL
 */
public class DeskKaAction extends BaseActionSupport{
	private static final long serialVersionUID = 1L;
	@Autowired
	private IUser userService;
	@Autowired
	private IMyWaitProcess myWaitProcessService;
	@Autowired
	private IMyProcessed myProcessed;
	@Autowired
	private IDesktop deskTop;
	@Resource(name="platformParameterService")
	private IPlatformParameter platformParameterService;
	@Resource(name="companyService")
	ICompany companyService;
	/** 任务管理 */
//	@Autowired
//	ITaskService taskServiceImp;
	protected String userId ;
	private Integer remindFlag;//0已发送 1已接受 2已阅
	
	/**
	 * 获取待处理的个数
	 */
	public String approveCount(){
		UserInfo userInfo=this.getLoginUser();
		Map<String,Object> returnMap = new HashMap<String, Object>();
		  if(userInfo!=null){
			   returnMap.put("jbpmWaitSize",0);
			   returnMap.put("jbpmApproveSize",0);
		  }
		  ajax(JsonUtils.generJsonString(returnMap));
		return null;
	}

	
	/**
	 * 获取事务处理个数
	 */
	public String affairsCount(){
		UserInfo userInfo=this.getLoginUser();
		Map<String,Integer> affCountMap = null;
		  if(userInfo!=null){
			 Integer userId=userInfo.getUserId();	
			 if(remindFlag==null){
				 remindFlag=1;
			 }
			 affCountMap = deskTop.getAffairsCount(userId,remindFlag);
		  }
		  ajax(affCountMap);
		return null;
	}
	
	/**
	 * 获取公司理念
	 * @return
	 */
	public String getCompanyPhilosophy(){
		List<CompanyInfo> list = companyService.findAll();
		String philosophy =""; 
		Map<String,String> map = new HashMap();
		if(list!=null && list.size()>0){
			CompanyInfo ci = companyService.findAll().get(0);
		    philosophy = ci.getPhilosophy();
		    map.put("philosophy", philosophy);
		} 
		 ajax(map);
		return null;
	}
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}


	public Integer getRemindFlag() {
		return remindFlag;
	}


	public void setRemindFlag(Integer remindFlag) {
		this.remindFlag = remindFlag;
	}
	
	
}
