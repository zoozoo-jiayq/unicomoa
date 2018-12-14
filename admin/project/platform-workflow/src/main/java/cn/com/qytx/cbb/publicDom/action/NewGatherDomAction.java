package cn.com.qytx.cbb.publicDom.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import cn.com.qytx.cbb.customForm.domain.FormCategory;
import cn.com.qytx.cbb.publicDom.domain.DocumentType;
import cn.com.qytx.cbb.publicDom.domain.GongwenVar;
import cn.com.qytx.cbb.publicDom.service.GongwenVarService;
import cn.com.qytx.cbb.publicDom.service.PublicDocumentConstant;
import cn.com.qytx.cbb.publicDom.service.PublicDomService;
import cn.com.qytx.cbb.publicDom.vo.PublicDomVo;
import cn.com.qytx.cbb.publicDom.vo.ReadStateCount;
import cn.com.qytx.cbb.publicDom.vo.ReadStateView;
import cn.com.qytx.cbb.systemSet.domain.SysConfig;
import cn.com.qytx.cbb.systemSet.service.SysConfigService;
import cn.com.qytx.platform.base.application.TransportUser;
import cn.com.qytx.platform.org.domain.UserInfo;

/**
 * 功能：收文管理控制器，处理收文模块一些特有的页面。
 * 版本: 1.0
 * 开发人员：贾永强
 * 创建日期: 上午10:58:34 
 * 修改日期：上午10:58:34 
 * 修改列表：
 */
public class NewGatherDomAction extends MyBaseAction {
	/*------------spring注入属性begin---------------*/
	@Resource(name="publicDomService")
	private PublicDomService publicDomService;
	@Resource(name="gongwenVarService")
	private GongwenVarService gongwenVarService;
	@Resource
	private SysConfigService sysConfigService; 

	PublicDomVo publicDomVo;

	public PublicDomVo getPublicDomVo() {
        return publicDomVo;
    }

    public void setPublicDomVo(PublicDomVo publicDomVo) {
        this.publicDomVo = publicDomVo;
    }

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
	 * 功能：进入收文列表
	 * @param
	 * @return
	 * @throws   
	 */
	public String gatherList(){
		if(searchType==null || searchType.equals("")){
			if(menu == 1){
				return "register";
			}else if(menu == 2){
				return "approve";
			}else if(menu == 3){
				return "dispatcher";
			}else if(menu == 4){
				return "read";
			}
		}else if(searchType.equals("completed")){
			if(menu == 1){
				return "register_complete";
			}else if(menu == 2){
				return "approve_complete";
			}else if(menu == 3){
				return "dispatcher_complete";
			}else if(menu == 4){
				return "read_complete";
			}
		}
		if(menu == 9){//收文归档
			return "gather_zip";
		}
		return null;
	}
	
	
	/**
	 * 功能：获取传阅状态
	 * @param
	 * @return
	 * @throws   
	 */
	public String toGetReadState(){
		return "read_state";
	}
	
	/**
	 * 功能：获取传阅状态列表
	 * @param
	 * @return
	 * @throws   
	 */
	public List<ReadStateView> getReadStateViews(){
		return publicDomService.getReadStateViewList(instanceId);
	}
	
	public ReadStateCount getReadStateCount(){
		ReadStateCount rsc = new ReadStateCount();
		List<ReadStateView> list = getReadStateViews();
		rsc.setTotal(list.size());
		int reading = 0;
		int readcomplete = 0;
		for(int i=0; i<list.size(); i++){
			if(list.get(i).getState().contains("未阅读")){
				reading++;
			}else if(list.get(i).getState().contains("已阅读")){
				readcomplete++;
			}
		}
		rsc.setReading(reading);
		rsc.setReadComplete(readcomplete);
		return rsc;
	}
	
	/**
	 * @throws Exception 
	 * 功能：获取文号
	 * @param
	 * @return
	 * @throws   
	 */
	public String getWenhao() throws Exception{
		DocumentType documentType = documentTypeService.findOne((documentTypeId));
		int categoryid = documentType.getCategoryId();
		FormCategory fc = formCategory.findById(categoryid);
		String categoryName = "";
		if(fc!=null){
			categoryName = fc.getCategoryName();			
		}
		UserInfo user = getLoginUser();
		String wenhao = documentType.getWenhao(user.getLoginName(),user.getGroupName(),categoryName);
		String canupdate = documentType.getCanUpdate();
		ajax("{'wenhao':'"+wenhao+"','canUpdate':'"+canupdate+"'}");
		return null;
	}
	
	/**
	 * @throws Exception 
	 * 功能：判断收文是系统内收文还是系统外收文
	 * @param
	 * @return
	 * @throws   
	 */
	public String ajustIsOutOrIn() throws Exception{
		String source = (String) processEngine.getExecutionService().getVariable(instanceId, PublicDocumentConstant.GATHER_SOURCE);
		ajax(source);
		return null;
	}
	
	/**
	 * 功能：进入系统内登记页面
	 * @param
	 * @return
	 * @throws   
	 */
	public String toInnerSystemUpate(){
		if(instanceId!=null){
			Set<String> set = new HashSet<String>();
			set.add(PublicDocumentConstant.TITLE);
			set.add(PublicDocumentConstant.HUANJI);
			set.add(PublicDocumentConstant.SECRET_LEVEL);
			set.add(PublicDocumentConstant.WENHAO);
			set.add(PublicDocumentConstant.SENDER_DEPT);
			varMap = processEngine.getExecutionService().getVariables(instanceId, set);
		}
		return "innerSystemUpate";
	}
	
	/**
	 * 功能：更新系统内收文
	 * @param
	 * @return
	 * @throws   
	 */
	public String updateInnerSystem(){
		int domTypeId = getPublicDomVo().getDomTypeId();
		String domTypeName = getPublicDomVo().getDomTypeName();
		String wenhao = getPublicDomVo().getWenhao();
		Map<String,String> varMap = new HashMap<String, String>();
		varMap.put(PublicDocumentConstant.GONGWENTYPE_ID, domTypeId+"");
		varMap.put(PublicDocumentConstant.GONGWENTYPE, domTypeName);
		varMap.put(PublicDocumentConstant.WENHAO, wenhao);
		varMap.put(PublicDocumentConstant.SECRET_LEVEL, getPublicDomVo().getSecretLevel());
		varMap.put(PublicDocumentConstant.HUANJI, getPublicDomVo().getHuanji());
		varMap.put(PublicDocumentConstant.WENHAO, getPublicDomVo().getWenhao());
		varMap.put(PublicDocumentConstant.TITLE, getPublicDomVo().getTitle());
		varMap.put(PublicDocumentConstant.FIRST_LEVEL_NAME, getPublicDomVo().getFirstLevel());
		
		processEngine.getExecutionService().setVariables(instanceId, varMap);
		
		GongwenVar gongwenVar = gongwenVarService.findByInstanceId(instanceId);
		gongwenVar.setGongwenTypeName(domTypeName);
		gongwenVar.setWenhao(wenhao);
		gongwenVar.setMiji(getPublicDomVo().getSecretLevel());
		gongwenVar.setHuanji(getPublicDomVo().getHuanji());
		gongwenVar.setWenhao(getPublicDomVo().getWenhao());
		gongwenVar.setTitle(getPublicDomVo().getTitle());
		gongwenVar.setCompanyId(TransportUser.get().getCompanyId());
		gongwenVarService.saveOrUpdate(gongwenVar);
		
		String taskId = processEngine.getTaskService().createTaskQuery().processInstanceId(instanceId).uniqueResult().getId();
		ajax(taskId);
		return null;
	}
	
	public String getWenhaoForReadState(){
	    GongwenVar var = gongwenVarService.findByInstanceId(instanceId);
	    return var.getWenhao();
	}
	
	/**
	 * 功能：获取收文登记的操作列表
	 * 作者：jiayongqiang
	 * 参数：
	 * 输出：
	 */
	public List<String> getStartOperations(){
	    List<String> list = new ArrayList<String>();
	    String operations = sysConfigService.getSysConfig().get(SysConfig.DOM_GATHER_REGISTER);
	    String[] strs = operations.split(",");
	    for(int i=0; i<strs.length; i++){
	        String temp = strs[i];
	        String r = PublicDocumentConstant.OPERATION_MAP.get(temp);
	        list.add(r);
	    }
	    return list;
	}
	
}