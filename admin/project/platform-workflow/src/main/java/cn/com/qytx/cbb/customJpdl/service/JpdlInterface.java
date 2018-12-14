package cn.com.qytx.cbb.customJpdl.service;


/**
 * 功能：JPDL定义的接口
 * 版本: 1.0
 * 开发人员：贾永强
 * 创建日期: 2013-3-22 上午11:37:10 
 * 修改日期：2013-3-22 上午11:37:10 
 * 修改列表：
 */
public interface JpdlInterface {

	//开始任务节点
	public final static String NODE_TYPE_START 		= "start";
	//判断节点
	public final static String NODE_TYPE_DECISON 	= "decision";
	//分支节点
	public final static String NODE_TYPE_FORK		= "fork";
	//回合节点
	public final static String NODE_TYPE_JOIN		= "join";
	//会签节点
	public final static String NODE_TYPE_MULTISIGN	= "mutilSign";
	//任务节点
	public final static String NODE_TYPE_TASK		= "task";
	//结束节点
	public final static String NODE_TYPE_END		= "end";
	//归档
	public final static String NODE_TYPE_ZIP		= "zip";
	
	/*
	 * JPDL变量命名
	 */
	//任务发起人变量
	public static final String VAR_CREATER="creater";
	//候选人变量
	public static final String VAR_CANDIDATE_USERS="candidate_persons";
	//回退信息
	public static final String ROLL_BACK_INFO = "rollbackinfo";
	
	//发起流程和结束流程节点名称
	public static final String START_NODE_NAME = "发起流程";
	public static final String END_NODE_NAME = "结束";
	
	
	public static final String APPROVE_RESULT_AGREE = "同意";
	public static final String APPROVE_RESULT_ROLLBACK = "不同意";
	public static final String APPROVE_RESULT_SUSPEND = "挂起";
	public static final String APPROVE_RESULT_REPEAL = "撤销";
	
	//流程变量附件和审批意见的定义
	public static final String VAR_ATTACH = "attach";
	public static final String VAR_ADVICE = "advice";
	
	//流程状态：审批中，结束，驳回，撤销
	public static final String PROCESS_STATE_APPROVE = "approve";//审批中
	public static final String PROCESS_STATE_END = "end";//结束
	public static final String PROCESS_STATE_ROLLBACK = "endnoagree";//驳回
	public static final String PROCESS_STATE_REPEAL = "repeal";//撤销
	
	

}
