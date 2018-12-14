package cn.com.qytx.cbb.publicDom.tag;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.jbpm.api.ProcessEngine;
import org.jbpm.api.model.Activity;
import org.jbpm.api.model.Transition;
import org.jbpm.api.task.Task;
import org.jbpm.pvm.internal.model.ProcessDefinitionImpl;
import org.jbpm.pvm.internal.model.TransitionImpl;

import cn.com.qytx.cbb.customJpdl.dao.NodeFormAttributeDao;
import cn.com.qytx.cbb.customJpdl.domain.NodeFormAttribute;
import cn.com.qytx.cbb.publicDom.service.PublicDocumentConstant;
import cn.com.qytx.cbb.publicDom.service.PublicDomService;
import cn.com.qytx.platform.utils.spring.SpringUtil;

/**
 * 功能：控制显示下一步可选操作
 * 版本: 1.0
 * 开发人员：贾永强
 * 创建日期: 下午6:26:30 
 * 修改日期：下午6:26:30 
 * 修改列表：
 */
public class ShowNextOperationTagSupport extends TagSupport {
	
	private String taskId;
	private Integer menu;
	private ProcessEngine engine = (ProcessEngine) SpringUtil.getBean("processEngine");
	private PublicDomService publicDomService = (PublicDomService) SpringUtil.getBean("publicDomService");
	private NodeFormAttributeDao nodeFormAttributeDao = (NodeFormAttributeDao) SpringUtil.getBean("nodeFormAttributeDao");

	@Override
    public int doStartTag() throws JspException {
	    // TODO Auto-generated method stub
		 JspWriter out = this.pageContext.getOut();
		 if(taskId==null || taskId.equals("")){
			 try {
	            out.println("");
            } catch (IOException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
            }
			 return SKIP_BODY;
		 }
		List<String> operations = publicDomService.getEnableOperations(taskId, menu);
		
		
		//获取公文流程各个节点的NODEID
		Task task = engine.getTaskService().getTask(taskId);
		String instanceId = task.getExecutionId();
		String documentTypeFlag = task.getExecutionId().substring(0, instanceId.indexOf("."));
		String documentType = (String) engine.getExecutionService().getVariable(task.getExecutionId(), "gongwenType_id");
		List<NodeFormAttribute> nodelist = nodeFormAttributeDao.findByProcessAttributeId(Integer.parseInt(documentType)*-1);
		
		String result = "";
		for(Iterator<String> it = operations.iterator(); it.hasNext();){
			String optionName = it.next();
			int nodeId = 0 ;
			ProcessDefinitionImpl define = (ProcessDefinitionImpl) engine.getRepositoryService().createProcessDefinitionQuery().processDefinitionKey(documentTypeFlag).uniqueResult();
			Transition tran =  define.findActivity(task.getName()).findOutgoingTransition(optionName);
			if(tran!=null){
				Activity activity =  tran.getDestination();
				activity = getNextTaskActivity(activity);
//				if(activity.getActivityBehaviour().)
				if(activity!=null){
					String nodeName = activity.getName();
					for(int i=0; i<nodelist.size(); i++){
						if(nodelist.get(i).getNodeName().equals(nodeName)){
							nodeId = nodelist.get(i).getId();
							break;
						}
					}
				}
			}
			result+=createButton(optionName,nodeId);
			if(it.hasNext()){
				//result+="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
			}
		}
		try {
	        out.print(result);
        } catch (IOException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
        }
	    return SKIP_BODY;
    }
	
	private Activity getNextTaskActivity(Activity activity){
		if(activity.getType().equals("task")){
			return activity;
		}else{
			List<TransitionImpl> list = (List<TransitionImpl>) activity.getOutgoingTransitions();
			if(list!=null && list.size()>0){
				Activity nextactivity = list.get(0).getDestination(); 
				return getNextTaskActivity(nextactivity);
			}else{
				return null;
			}
		}
		
	}
	
	private String createButton(String buttonName,int nodeId){
	    String name = buttonName;
		name = name.trim();
		String showName = PublicDocumentConstant.OPERATION_MAP.get(name);
		String result = "<input type='button' nodeId='"+nodeId+"' nextAction='"+name+"' name='"+name+"' value='"+showName+"' class='formButton'/>";
		return result;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public Integer getMenu() {
		return menu;
	}

	public void setMenu(Integer menu) {
		this.menu = menu;
	}
	
}
