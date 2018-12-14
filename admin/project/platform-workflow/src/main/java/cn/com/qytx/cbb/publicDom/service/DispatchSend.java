package cn.com.qytx.cbb.publicDom.service;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jbpm.api.ExecutionService;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.ProcessInstance;
import org.jbpm.api.task.Task;

import cn.com.qytx.cbb.publicDom.dao.GongwenVarDao;
import cn.com.qytx.cbb.publicDom.domain.DocumentExt;
import cn.com.qytx.cbb.publicDom.domain.GongwenVar;
import cn.com.qytx.cbb.publicDom.service.PublicDocumentConstant.GATHER_SOURCE;
import cn.com.qytx.cbb.publicDom.util.AffairUtils;
import cn.com.qytx.platform.base.application.TransportUser;
import cn.com.qytx.platform.org.domain.GroupInfo;
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.org.service.IGroup;
import cn.com.qytx.platform.org.service.IUser;
import cn.com.qytx.platform.utils.spring.SpringUtil;
/**
 * 功能：发文发送
 * 版本: 1.0
 * 开发人员：贾永强
 * 创建日期: 2013-4-15 下午4:03:38 
 * 修改日期：2013-4-15 下午4:03:38 
 * 修改列表：
 */
public class DispatchSend implements Serializable {

	public DispatchSend(Task task,String gathers,String instanceid,UserInfo info){
		this.gathers = gathers;
		this.sourceExecutionid = instanceid;
		this.sender = info;
		this.task = task;
	}
	
	private String gathers = "";
	
	private String sourceExecutionid;
	
	private UserInfo sender;
	
	private Task task;

	public String getSourceExecutionid() {
		return sourceExecutionid;
	}

	public void setSourceExecutionid(String source_executionid) {
		this.sourceExecutionid = source_executionid;
	}

	public String getGathers() {
		return gathers;
	}

	public void setGathers(String gathers) {
		this.gathers = gathers;
	}

	/**
	 * 功能：
	 * @param
	 * @return
	 * @throws   
	 */
	public void execute(UserInfo fromUser) {
		GongwenVarDao gongwenVarDao = (GongwenVarDao) SpringUtil.getBean("gongwenVarDao");
		ProcessEngine engine = (ProcessEngine) SpringUtil.getBean("processEngine");
		PublicDomService publicDomService = (PublicDomService) SpringUtil.getBean("publicDomService");
		GatherRegisterService gatherRegisterService = (GatherRegisterService) SpringUtil.getBean("gatherRegisterService");
		IDocumentExtService documentExtService  = (IDocumentExtService) SpringUtil.getBean("documentExtService");
		IUser userService = (IUser) SpringUtil.getBean("userService");
	    IGroup groupService = (IGroup) SpringUtil.getBean("groupService");
		ExecutionService executionService = engine.getExecutionService();
		Set<String> varSet = new HashSet<String>();
		varSet.add(PublicDocumentConstant.TITLE);
		varSet.add(PublicDocumentConstant.WENHAO);
		
		//将附件信息拷贝到新的流程
		varSet.add(PublicDocumentConstant.ATTACH);
		
		varSet.add(PublicDocumentConstant.SECRET_LEVEL);
		varSet.add(PublicDocumentConstant.HUANJI);
		varSet.add(PublicDocumentConstant.SENDER_DEPT);
		Map<String, Object> varMap1 = engine.getExecutionService().getVariables(sourceExecutionid, varSet);
		DocumentExt documentExt = documentExtService.findByProcessInstanceId(sourceExecutionid);
		String[] modulelist = gathers.split(",");
		if(modulelist!=null){
			for(int i=0;i<modulelist.length;i++){
				if(modulelist[i]==null || modulelist[i].equals("")){
					continue;
				}
				String groupId = modulelist[i];
				List<Integer> userIds = gatherRegisterService.findUserIdByRoleAndGroup(groupId);
				//发文流程中记录下已发送的数量，add by jiayq
				if(userIds!=null && userIds.size()>0){
					engine.getExecutionService().setVariable(sourceExecutionid, "receiver_count", userIds.size());
			
				
				for(int j=0;j<userIds.size();j++){
					Map<String,Object> map = new HashMap<String, Object>();
					map.putAll(varMap1);
					map.put(PublicDocumentConstant.CREATER, userIds.get(j)+"");
					map.put(PublicDocumentConstant.GATHER_SOURCE, GATHER_SOURCE.SOURCE_DISPATCHER.getSource());
					map.put(PublicDocumentConstant.GATHER_SOURCE_DISPATCH_EXECUTIONID, sourceExecutionid);
					String gatherGroupName = gatherRegisterService.findGroupNameById(modulelist[i]);
					map.put(PublicDocumentConstant.GATHER_DEPT, gatherGroupName);
					
					//add by jiayq,发文分发产生的收文不保存公文类型
					map.put(PublicDocumentConstant.GONGWENTYPE, null);
					map.put(PublicDocumentConstant.GONGWENTYPE_ID, null);
					ProcessInstance processInstance = executionService.startProcessInstanceByKey(PublicDocumentConstant.GATHER_PROCESS_NAME, map);
					AffairUtils.insertAffair(fromUser, userIds.get(j)+"", task.getName(),varMap1.get(PublicDocumentConstant.TITLE).toString(),processInstance.getId(),"收文登记");
					
					GongwenVar var = new GongwenVar();
					var.setInstanceId(processInstance.getId());
					var.setCompanyId(TransportUser.get().getCompanyId());
					//add by jiayq,发文分发产生的收文不保存公文类型
					var.setGongwenTypeName(null);
					var.setHuanji(varMap1.get(PublicDocumentConstant.HUANJI).toString());
					var.setMiji(varMap1.get(PublicDocumentConstant.SECRET_LEVEL).toString());
					var.setTitle(varMap1.get(PublicDocumentConstant.TITLE).toString());
					var.setGatherSource(GATHER_SOURCE.SOURCE_DISPATCHER.getSource());
					var.setFromGroup(varMap1.get(PublicDocumentConstant.SENDER_DEPT).toString());
					var.setReceiverGroup(gatherGroupName);
					if(varMap1.get(PublicDocumentConstant.WENHAO)!=null){
						var.setWenhao(varMap1.get(PublicDocumentConstant.WENHAO).toString());
					}else{
						var.setWenhao("");
					}
					UserInfo userInfo = userService.findOne( userIds.get(j));
					var.setCreater(userInfo.getUserName());
					var.setCreateTime(new Timestamp(Calendar.getInstance().getTimeInMillis()));
					GroupInfo forkGroup = groupService.getForkGroup(userInfo.getCompanyId(),userIds.get(j));
					int forkGroupId = 0 ;
					if(forkGroup!=null){
						forkGroupId =  forkGroup.getGroupId();
					}
					var.setForkGroupId(forkGroupId);
					var.setCompanyId(userInfo.getCompanyId());
					gongwenVarDao.saveOrUpdate(var);
					
					//拷贝电子印章数据，DOC文档数据到新流程
					DocumentExt temp = new DocumentExt();
					temp.setProcessInstanceId(processInstance.getId());
					temp.setAttachId(documentExt.getAttachId());
					temp.setCompanyId(userInfo.getCompanyId());
					documentExtService.saveOrUpdate(temp);
				}
			}
			}
		}
	}
}
