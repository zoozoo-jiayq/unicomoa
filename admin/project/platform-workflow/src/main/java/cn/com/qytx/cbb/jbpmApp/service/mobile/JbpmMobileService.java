package cn.com.qytx.cbb.jbpmApp.service.mobile;

import java.io.Serializable;
import java.util.List;

import cn.com.qytx.cbb.jbpmApp.action.mobile.jsonModel.DataElementImplDetail;
import cn.com.qytx.cbb.jbpmApp.action.mobile.jsonModel.DataElementImplMyApproved;
import cn.com.qytx.cbb.jbpmApp.action.mobile.jsonModel.DataElementImplMyStart;
import cn.com.qytx.cbb.jbpmApp.action.mobile.jsonModel.DataElementImplNextTaskInfo;
import cn.com.qytx.cbb.jbpmApp.action.mobile.jsonModel.DataElementImplNotApproved;
import cn.com.qytx.cbb.jbpmApp.action.mobile.jsonModel.DataElementImplWaitApprove;
import cn.com.qytx.cbb.jbpmApp.service.mobile.htmlElement.HtmlElement;
import cn.com.qytx.platform.base.query.Pageable;
import cn.com.qytx.platform.org.domain.UserInfo;

/**
 * 功能：工作流手机接口
 * 版本: 1.0
 * 开发人员：贾永强
 * 创建日期: 下午2:02:02 
 * 修改日期：下午2:02:02 
 * 修改列表：
 */
public interface JbpmMobileService extends Serializable {
	
	//查询我发起的
	public List<DataElementImplMyStart> findByStart(String loginName,Pageable page);
	
	//待我审批(统计)
	public DataElementImplNotApproved  findMyNotApproved(String loginName);
	
	//待审批列表/已挂起，type：0 待审批;1已挂起
	public List<DataElementImplWaitApprove> findMyWaitApprove(int type,String loginName,Pageable page);
	
	//我处理的
	public List<DataElementImplMyApproved> findMyApproved(String loginName,Pageable page);
	
	//查看详情
	public DataElementImplDetail findByInstanceId(String instanceId); 
	
	//发起流程的时候，获取表单属性
	public List<HtmlElement> getStartFormProperties(int processId);
	
	//删除
	public void deleteInstance(String instance);
	
	//查看原始表单,返回formId
	public int viewForm(String instanceId);
	
	//获取表单表单元素
	public List<HtmlElement> getHtmlElementsForFlow(String instanceId);

	//获取公文表单元素
	public List<HtmlElement> getHtmlElementsForDoc(String instanceId,String userId);
	
	//获取下一步任务信息
	//无分支
	@Deprecated
	public DataElementImplNextTaskInfo getNextTaskInfo(String instanceId,String userId);
	
	//有分支,获取下一步任务信息
	public List<DataElementImplNextTaskInfo> getNextTaskInfos(String instanceId,String loginName);
	
	/**
	 * 功能：发起流程的时候获取下一步的任务信息
	 * @param processId:流程定义ID
	 * @return
	 * @throws   
	 */
	public List<DataElementImplNextTaskInfo> getStartNextTaskInfos(int processId);
	

	/**
	 * 获取待审批数
	 * @param string
	 * @return
	 */
	public int getApproveCount(String string);
	
	
	/**
	 * 功能：发起流程
	 * @param 
	 * ui:当前登录用户
	 * processId:流程定义Id
	 * formData:表单数据
	 * attachData:附件数据
	 * advice:审批意见
	 * nextAction:下一步流向
	 * nextUser:下一步任务处理人
	 * @return
	 * @throws   
	 */
	public String startProcess(UserInfo ui,int processId,String formData,String attachData,String advice,String nextAction,String nextUser);
	
}
