package cn.com.qytx.cbb.jbpmApp.service.impl;

public class WorkFlowConstants {
	/**
	 * 工作流变量的KEY常量，工作流封装内部使用
	 * @author anxing
	 *
	 */
	public static class VAR_CONSTANTS
	{
		public static final String CREATER="creater";
		public static final String CANDIDATE_USERS="candidate_persons";
		public static final String TASKTRACE="taskTrace";
		public static final String SIGN_COMMENTS_MAP="sign_comments_map";  //会签意见
		
	}
	/**
	 * 流程ACTION常量
	 * @author anxing
	 *
	 */
	public static class ACTION_CANSTANTS
	{
		public static final String BEGIN_ACTION="发起流程";
		public static final String SYS_ROLLBACK_TRAN_TAG="SYS_ROLLBACK_TAG";
	}
}
