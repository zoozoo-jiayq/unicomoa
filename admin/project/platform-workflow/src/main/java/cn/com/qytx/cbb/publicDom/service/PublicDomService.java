package cn.com.qytx.cbb.publicDom.service;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.qytx.cbb.customForm.domain.FormAttribute;
import cn.com.qytx.cbb.customWidget.vo.JbpmAdvice;
import cn.com.qytx.cbb.publicDom.domain.DocumentType;
import cn.com.qytx.cbb.publicDom.service.PublicDocumentConstant.GATHER_SOURCE;
import cn.com.qytx.cbb.publicDom.vo.PublicDomView;
import cn.com.qytx.cbb.publicDom.vo.PublicDomVo;
import cn.com.qytx.cbb.publicDom.vo.ReadStateView;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.Pageable;
import cn.com.qytx.platform.org.domain.GroupInfo;
import cn.com.qytx.platform.org.domain.UserInfo;

/**
 * 功能：公文的工作流接口
 * 版本: 1.0
 * 开发人员：贾永强
 * 创建日期: 2013-4-16 上午10:12:50 
 * 修改日期：2013-4-16 上午10:12:50 
 * 修改列表：
 */
public interface PublicDomService extends Serializable{
	
	public static final Map<String,Integer> MENU_NUMBER = new HashMap<String, Integer>(){{
		this.put("收文登记", 1);
		this.put("领导批阅", 2);
		this.put("收文分发", 3);
		this.put("收文阅读", 4);
		this.put("发文拟稿", 5);
		this.put("发文核稿", 6);
		this.put("套红盖章", 7);
		this.put("发文分发", 8);
	}};

	//公文类型,发文，收文
	public enum DomType{
		//发文
		DISPATCHER(PublicDocumentConstant.DISPATCHER_PROCESS_NAME),
		//收文
		GATHER(PublicDocumentConstant.GATHER_PROCESS_NAME);
		
		private String processName;
		
		//枚举构造函数
		private DomType(String processName){
			this.processName = processName;
		}
		public String getProcessName(){
			return processName;
		}
	};
	
	public enum MenuType{
		DIS_CREATER("发文拟稿"),
		DIS_APPROVE("发文核稿"),
		DIS_REDER("套红盖章"),
		DIS_SENDLIST("发文分发"),
		DIS_SEND("发送"),
		GA_REGISTER("收文登记"),
		GA_APPROVE("领导批阅"),
		GA_DISPATCH("收文分发"),
		GA_READ("收文阅读"),
		ZIP("归档");
		private String nodeName;
		
		private MenuType(String nodeName){
			this.nodeName = nodeName;
		}
		public String getNodeName(){
			return nodeName;
		}
	}
	
	/**
	 * 功能：新建公文
	 * @param，
	 * @return
	 * @throws   
	 */
	public String createInstance(PublicDomVo publicDom,UserInfo user,GATHER_SOURCE source);
	
	
	/**
	 * 功能：查询待处理的任务，优化过，如果查询归档，则只查询所属分支机构
	 * @param
	 * @return
	 * @throws   
	 */
	public Page<PublicDomView> searchWaittingProcessTask(String processName,List<String> nodeNames,String title,String userId,GroupInfo forkGroup,Pageable page);
	
	/**
	 * 功能：查询经我办理的任务，优化过，如果查询归档，则只查询所属分支机构
	 * @param
	 * @return
	 * @throws   
	 */
	public Page<PublicDomView> searchMyCompletedProcessTask(String processName,List<String> nodeNames,String title,String userId,GroupInfo forkGroup,Pageable page);
	
	/**
	 * 功能：完成任务，转交下一步,在方法内判断是否会签
	 * @param taskId:当前任务ID，nextTaskAssigner：下一步任务处理人,如果有多个处理人用,隔开
	 * 			;outcome:下一步任务
	 * 			outcome:带有to
	 * @return
	 * @throws   
	 */
	public void completeTask(UserInfo userInfo,String taskId,String nextTaskAssigner,String outcome);
	
	/**
	 * 功能：获取发文流程定义ID
	 * @param
	 * @return
	 * @throws   
	 */
	public String getDispatcherDomProcessDefineId();
	
	/**
	 * 功能：获取收文流程定义ID
	 * @param
	 * @return
	 * @throws   
	 */
	public String getGatherDomProcessDefineId();
	
	/**
	 * 功能：删除流程实例
	 * @param taskiDs:任务ID，多个实例用，隔开
	 * @return
	 * @throws   
	 */
	public void deleteInstance(String taskIds);
	
	/**
	 * 功能：根绝任务ID获取自定义表单源代码
	 * @param
	 * @return
	 * @throws   
	 */
	public FormAttribute getFormSource(String taskId);
	
	public FormAttribute getFormSourceByInstanceId(String instanceId);
	
	
	/**
	 * 功能：保存表单数据，附件信息
	 * @param
	 * @return
	 * @throws   
	 */
	public void saveFormDataAndCommants(String jsonData,String instanceId,Integer formId);
	
	/**
	 * 功能：根据任务ID获取实例ID
	 * @param
	 * @return
	 * @throws   
	 */
	public String getInstanceIdByTaskId(String taskId);
	
	
	/**
	 * 功能：获取子任务的阅读状态
	 * @param
	 * @return
	 * @throws   
	 */
	public List<ReadStateView> getReadStateViewList(String instanceId);
	public List<ReadStateView> getReadStateViewCount(String instanceId);
	
	
	/**
	 * 功能：查询公文的待办任务数量
	 * @param
	 * @return
	 * @throws   
	 */
	public int getPersonTaskCount(DomType domType,MenuType menuType,String userId);
	

	/**
	 * 功能：根据任务ID获取公文类型
	 * @param
	 * @return
	 * @throws   
	 */
	public DocumentType getDocumentTypeByTaskId(String taskId);
	
	
	/**
	 * 功能：判断标题是否重复，重复返回true,不重复返回false
	 * @param
	 * @return
	 * @throws   
	 */
	public boolean titleIsRepeat(String title);
	
	public int getPersonTaskCount(DomType dispatcher, MenuType disApprove,
			int userId);
	
	/**
	 * 功能：压缩归档
	 * @param
	 * @return
	 * @throws   
	 */
	public File zipForDownload(String instanceId) throws Exception;
	
	/**
	 * 功能：批量完成任务
	 * @param
	 * @return
	 * @throws   
	 */
	public void batchCompleteTask(UserInfo userInfo,String[] taskIds);
	
	
	/**
	 * 功能：批量打包下载
	 * @param
	 * @return
	 * @throws   
	 */
	public File batchZipForDowanload(String[] instanceIds) throws Exception;
	
	/**
	 * 功能：保存审批意见
	 * @param
	 * @return
	 * @throws   
	 */
	public String saveAdvice(String instanceId,JbpmAdvice advice);
	public String saveAdvice(String instanceId,List<JbpmAdvice> advicelist);
	
	/**
	 * 功能：获取意见控件
	 * @param
	 * @return
	 * @throws   
	 */
	public String getAdvice(String instanceId);
	
	
	/**
	 * 功能：保存审批人姓名，流程变量名称"readerName"
	 * @param
	 * @return
	 * @throws   
	 */
	public void saveReaderName(String instanceId,UserInfo ui);
	/**
	 * 功能：获取审批人姓名列表
	 * @param
	 * @return
	 * @throws   
	 */
	public String getReaderNamelist(String instanceId);
	
	
	/**
	 * 功能：查询当前任务的可用操作
	 * @param
	 * @return
	 * @throws   
	 */
	public List<String> getEnableOperations(String taskId,Integer menu);
	
	/**
	 * 功能：获取套红模板的路径
	 * @param taskId:任务Id,
	 *             domflow:domflow新公文；
	 *             domflow:其它,代表新公文
	 * @return
	 * @throws   
	 */
	public int getTaohongTemplate(String taskId,String domflow);
	
	/**
	 * 功能：获取office转为HTML内容后的HTML内容
	 * 作者：jiayongqiang
	 * 参数：
	 * 输出：
	 */
	public String getOfficeHtmlContent(String instanceId,String filePath,String imgPath);
	
	/**是否可以归档，
	 * 先判断是否强制归档，如果配置是否，返回true
	 * 如果当前节点就是归档节点，返回true
	 * @param instanceId
	 * @return
	 */
	public boolean isCanZip(String instanceId);
}
