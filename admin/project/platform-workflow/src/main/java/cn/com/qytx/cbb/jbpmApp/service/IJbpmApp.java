package cn.com.qytx.cbb.jbpmApp.service;

import java.io.File;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.List;

import cn.com.qytx.cbb.customJpdl.domain.NodeFormAttribute;
import cn.com.qytx.cbb.customJpdl.domain.ProcessAttribute;
import cn.com.qytx.cbb.customWidget.vo.JbpmAdvice;
import cn.com.qytx.cbb.file.domain.Attachment;
import cn.com.qytx.platform.org.domain.UserInfo;
/**
 * 工作流应用接口
 * @版本：1.0  
 * @开发人员：陈秋利   
 * @创建日期：2013-3-21 
 * @修改日期：
 */
public interface IJbpmApp extends Serializable{

    /**
	 * 获取某流程的附件信息
	 * @param processInstanceId
	 * @return
	 */
	public List<Attachment>  getAttachList(String processInstanceId);
	/**
	 * 获取标题
	 * @param processInstanceId
	 * @return 
	 */
	String getTitle(String processInstanceId);
	/**
	 * 获取某流程的审批意见信息
	 * @param processInstanceId
	 * @return
	 */
	List<JbpmAdvice> getAdviceList(String processInstanceId);
	
	/**
	 * 功能：根据流程ID和变量名查找流程变量
	 * @param
	 * @return
	 * @throws   
	 */
	String getHistVarList(String processInstanceId,String searchKey);
	
	/**
	 * 功能：获取代办个数
	 * @param userId
	 * @return
	 */
	int countOfTask(int userId);
	
	/**
	 * 获取当前活动节点个数
	 * @param processInstanceId
	 * @return
	 */
	int getCurrentTaskCount(String processInstanceId);
	
	/**是否可以删除一个流程，判断条件：只有除了发起人外没其他人处理的时候才可以删除
	 * true:可以删除；false:不能删除
	 * @param instanceId
	 * @return
	 */
	boolean canDeleteInstance(String instanceId);
	
	/**
	 * add by jiayq
	 * 功能：完成任务，同时更新流程变量,手机端和PC端审批的时候都调用此页面
	 * @param taskId:当前任务Id, attachData:附件数据，formData:表单数据,advice:审批意见,,nextAction:下一步任务的走向,nextAssigners:下一步任务处理人
	 * @return
	 * @throws   
	 */
	public void completeTask(UserInfo ui,String taskId,String attachData,String formData,String advice,String nextAction,String nextAssigners) throws UnsupportedEncodingException;
	
	/**
	 * add by jiayq
	 * 功能：驳回任务，同时更新流程变量，手机端和PC端驳回的时候都调用此页面
	 * @param  instanceId:当前任务ID，attachData:附件数据,formData:表单数据，advice:审批意见
	 * result:默认是不同意
	 * @return
	 * @throws   
	 */
	public void rollbackTask(String instanceId,String attachData,String formData,UserInfo userInfo,String advice,String rollbackType);
	
	/**
	 * add by jiayq
	 * 功能：挂起任务。
	 * result：挂起
	 * @param
	 * @return
	 * @throws   
	 */
	public void suspendTask(String taskId,UserInfo ui);
	
	/**
	 * 功能：发起流程
	 * @param 
	 * ui:发起人
	 * title:标题
	 * formDataJSON:表单数据
	 * attachJSON:附件数据
	 * advice:审批意见
	 * processId:流程定义ID
	 * @return
	 * @throws   
	 */
	public String startProcess(UserInfo ui,String title,String formDataJSON,String attachJSON,String advice,int processId,String nextAction,String nextUsers);
	
	/**
	 * 功能：构造流程实例名称
	 * @param
	 * @return
	 * @throws   
	 */
	public String generateProcessInstanceName(UserInfo userInfo,ProcessAttribute pa);
	
	/**
	 * 获取用户待处理的任务数
	 * @param string
	 * @return
	 */
	int getApproveCount(String string);
	
	/**
	 * 功能：根据流程实例ID 查询审批控件的审批意见
	 * @param
	 * @return
	 * @throws   
	 */
	public List<JbpmAdvice> findTdAdvicebyInstanceId(String instanceId);
	
	/**
	 * 功能：根据流程实例ID查询阅者签名信息
	 * @param
	 * @return
	 * @throws   
	 */
	public String[] findReaderNamesByInstanceId(String instanceId);
	
	/**导出表单，含数据
	 * @param instanceId
	 * @return
	 */
	public File downloadFormWithData(int processId,String instanceId);
	
	/**
	 * @param instanceId
	 * @return
	 */
	public NodeFormAttribute findCurrentNodeByInstanceId(String instanceId); 
	
	/**发送提醒
	 * @param nextTaskMainUserId2：接收人
	 * @param userInfo 发送人
	 * @param instanceId 流程ID
	 * @throws UnsupportedEncodingException
	 */
	public void setAffairsSign(String nextTaskMainUserId2, UserInfo userInfo,String instanceId) throws UnsupportedEncodingException;
	
	public void updateFormData( String taskId,String formData);
}
