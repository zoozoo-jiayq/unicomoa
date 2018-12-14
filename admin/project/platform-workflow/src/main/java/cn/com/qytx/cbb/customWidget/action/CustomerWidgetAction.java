package cn.com.qytx.cbb.customWidget.action;

import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.jadira.usertype.spi.utils.lang.StringUtils;

import cn.com.qytx.cbb.customWidget.vo.JbpmAdvice;
import cn.com.qytx.cbb.jbpmApp.domain.WorkflowVar;
import cn.com.qytx.cbb.jbpmApp.service.IWorkflowVar;
import cn.com.qytx.cbb.publicDom.dao.GongwenVarDao;
import cn.com.qytx.cbb.publicDom.domain.GongwenVar;
import cn.com.qytx.cbb.publicDom.service.GongwenVarService;
import cn.com.qytx.cbb.publicDom.service.PublicDomService;
import cn.com.qytx.cbb.systemSet.domain.SysConfig;
import cn.com.qytx.cbb.systemSet.service.SysConfigService;
import cn.com.qytx.platform.base.action.BaseActionSupport;
import cn.com.qytx.platform.org.domain.GroupInfo;
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.org.service.IGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * 功能：自定义控件的后台实现
 * 作者： jiayongqiang
 * 创建时间：2014年7月8日
 */
public class CustomerWidgetAction extends BaseActionSupport {

	@Resource
	private PublicDomService publicDomService;
	@Resource
	private SysConfigService sysConfigService;
	@Resource
	private IGroup groupService;
	@Resource
	private GongwenVarService gongwenVarService;
	@Resource
	private IWorkflowVar workflowVarService;
	//控件的审批意见
	private String advice;
	//控件名称
	private String editorName;
	private String instanceId;
	private String taskId;
	
	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}

	public String getAdvice() {
		return advice;
	}

	public void setAdvice(String advice) {
		this.advice = advice;
	}

	public String getEditorName() {
		return editorName;
	}

	public void setEditorName(String editorName) {
		this.editorName = editorName;
	}

	/**
     * @throws Exception 
     * @throws UnsupportedEncodingException 
     * 功能：获取阅读控件中阅读人信息
     * @param
     * @return
     * @throws   
     */
    public String initReaderName() throws Exception{
    	if(instanceId!=null && !instanceId.equals("")){
    		String str = publicDomService.getReaderNamelist(instanceId);
    		ajax(str);
    	}
    	return null;
    }
    
    /**
     * 功能：保存阅读人
     * @param
     * @return
     * @throws   
     */
    public String saveReaderName() throws Exception{
    	if(instanceId!=null && !instanceId.equals("")){
    		publicDomService.saveReaderName(instanceId, getLoginUser());
    		ajax("success");
    	}
    	return null;
    }
    
    /**
     * 功能：获取指定实例的公文审批控件的审批意见
     * @param
     * @return
     * @throws   
     */
    public String getTdAdvice() throws Exception{
    	if(instanceId!=null && !instanceId.equals("")){
			String result = publicDomService.getAdvice(instanceId);
			if(result!=null && !result.equals("")){
				ajax(result);
			}
    	}
    	return null;
    }
    
    /**
     * @throws Exception 
     * @throws UnsupportedEncodingException 
     * 功能：保存审批控件的审批意见，保存的key值是tdAdvice
     * @param
     * @return
     * @throws   
     */
    public String saveAdvice() throws Exception{
    	UserInfo ui = getLoginUser();
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
    	String approveTime = sdf.format(new Date());
    	editorName = URLDecoder.decode(editorName,"UTF-8");
    	JbpmAdvice ah = new JbpmAdvice();
    	ah.setContent(advice);
    	ah.setUserName(ui.getUserName());
    	ah.setOrderIndex(ui.getOrderIndex());
    	ah.setEditorName(editorName);
    	ah.setSignType(ui.getSignType()==null?0:ui.getSignType());
    	ah.setAdviceTime(approveTime);
    	ah.setTaskId(taskId);
    	if(ah.getSignType() == 2){
    		ah.setSignUrl(ui.getSignUrl());
    	}
    	Map<String,String> config = sysConfigService.getSysConfig();
		String str = config.get(SysConfig.SYS_APPROVE_WIDGET);
    	//发起流程的时候，填写保存意见，但是这时候还没有生成流程实例，所以暂时保存在SESSION中
    	if(instanceId == null || instanceId.equals("")){
			List<JbpmAdvice> advicelist = (List<JbpmAdvice>) getSession().getAttribute("tdAdvice");
			if(advicelist == null){
				advicelist = new ArrayList<JbpmAdvice>();
				
				if(advice == null || advice.equals("")){
					//add by jiayq，如果approve_widget的值为1，则保存，如果值为2，则不保存,默认为1
					if(str == null|| str.equals("") || str.equals("1")){
						advicelist.add(ah);
					}
				}else{
					advicelist.add(ah);
				}
			}else{
				//如果已经存在审批意见，则修改，否则添加
				boolean b = false;
				for(Iterator it = advicelist.iterator();it.hasNext();){
					JbpmAdvice ja = (JbpmAdvice) it.next();
					if(ja.getEditorName().equals(ah.getEditorName())){
						b = true;
						if(str==null || str.equals("") || str.equals("1")){
							ja.setContent(ah.getContent());
							ja.setAdviceTime(ah.getAdviceTime());
							ja.setSignType(ah.getSignType());
							ja.setSignUrl(ah.getSignUrl());
						}else{
							if(ah.getContent()!=null && !ah.getContent().equals("")){
								ja.setContent(ah.getContent());
    							ja.setAdviceTime(ah.getAdviceTime());
    							ja.setSignType(ah.getSignType());
    							ja.setSignUrl(ah.getSignUrl());
							}else{
								it.remove();
							}
						}
						
					}
				}
				
				if(!b){
					advicelist.add(ah);
				}
			}
    			getSession().setAttribute("tdAdvice", advicelist);
    			Gson gson = new Gson();
    			String res = gson.toJson(advicelist);
    			ajax(res);
    	}else{//保存到数据库
    		ah.setInstanceId(instanceId);
    		ah.setTaskId(taskId);
    		String result = publicDomService.saveAdvice(instanceId, ah);
    		ajax(result);
    	}
    	return null;
    }
    
    /**
	 * @throws Exception 
	 * @throws UnsupportedEncodingException 
	 * 功能：获取上一次的处理意见
	 * @param
	 * @return
	 * @throws   
	 */
	public String getLastAdvice() throws Exception{
		List<JbpmAdvice> advicelist = null;
		if(instanceId==null || instanceId.equals("")){
			advicelist = (List<JbpmAdvice>) getSession().getAttribute("tdAdvice");
		}else{
			String result = publicDomService.getAdvice(instanceId);
			Gson gson = new Gson();
			advicelist = gson.fromJson(result, new TypeToken<List<JbpmAdvice>>(){}.getType());
		}
		if(advicelist==null){
			ajax("");
		}else{
			for(int i=0; i<advicelist.size(); i++){
				JbpmAdvice temp = advicelist.get(i);
				if(temp.getEditorName().equals(editorName)){
					if(taskId == null || taskId.equals("")){
						ajax(temp.getContent());
					}else if(taskId.equals(temp.getTaskId())){
						ajax(temp.getContent());
					}
				}
			}
			ajax("");
		}
		return null;
	}
	
	/**
	 * 功能：初始化任务发起人信息
	 * 作者：jiayongqiang
	 * 参数：
	 * 输出：
	 */
	public String initCreateInfo(){
		Map<String,String> result = new HashMap<String, String>();
		if(StringUtils.isEmpty(instanceId)){
			result = getCurrentUserInfo();
		}else{
			if(instanceId.startsWith("gather") || instanceId.startsWith("dispatch")){
				result = gongwenVarService.findCreaterInfoByInstanceId(instanceId);
			}else{
				result = workflowVarService.findCreaterInfoByInstanceId(instanceId);
			}
		}
		Gson gson = new Gson();
		String str = gson.toJson(result);
		ajax(str);
		return null;
	}
	
	/**
	 * 初始化文号
	 * @return
	 */
	public String initWenhaoWidget(){
		Map<String,String> map = new HashMap<String, String>();
		if(StringUtils.isEmpty(instanceId)){
		}else if(instanceId.startsWith("gather") || instanceId.startsWith("dispatch")){
			GongwenVar var = gongwenVarService.findByInstanceId(instanceId);
			map.put("wenhao", var.getWenhao());
		}else{
			WorkflowVar var = workflowVarService.findByInstanceId(instanceId);
			map.put("wenhao", var.getTitle());
		}
		ajax(map);
		return null;
	}
	
	/**
	 * 功能：获取当前登录用户的信息
	 * 作者：jiayongqiang
	 * 参数：
	 * 输出：
	 */
	private Map<String,String> getCurrentUserInfo(){
		Map<String,String> map = new HashMap<String, String>();
		UserInfo u = getLoginUser();
		String userName = u.getUserName();
		int groupId = u.getGroupId();
		String groupName = "";
		if(groupId>0){
			GroupInfo g = groupService.findOne(groupId);
			if(g!=null){
				groupName = g.getGroupName();
			}
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String date = sdf.format(new Date());
		map.put("userName", userName);
		map.put("groupName", groupName);
		map.put("dataTime", date);
		return map;
	}
	
	public String addAdvice(){
		return "advice";
	}
	
	
}
