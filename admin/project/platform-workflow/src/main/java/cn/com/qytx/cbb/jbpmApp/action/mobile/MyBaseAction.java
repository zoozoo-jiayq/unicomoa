package cn.com.qytx.cbb.jbpmApp.action.mobile;

import javax.annotation.Resource;

import org.jbpm.api.ExecutionService;
import org.jbpm.api.HistoryService;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.TaskService;

import cn.com.qytx.cbb.jbpmApp.action.mobile.jsonModel.IJson;
import cn.com.qytx.platform.base.action.BaseActionSupport;

/**
 * 功能：
 * 版本: 1.0
 * 开发人员：贾永强
 * 创建日期: 2013-4-16 下午5:52:55 
 * 修改日期：2013-4-16 下午5:52:55 
 * 修改列表：
 */
public class MyBaseAction extends BaseActionSupport {
	
	/**
	 * 描述含义
	 */
	private static final long serialVersionUID = 1L;
	protected final static Integer NORMAL = 100;//正常
	protected final static Integer SERVER_EXCEPTION = 101;//服务异常
	protected final static Integer PARAMETER_EXCEPTION = 102;//参数异常
	protected final static Integer DATA_IS_NULL = 103;//数据为空
	
	public enum RESPONSE_CODE{
		RESPONSE_NORMAL_CODE(NORMAL),RESPONSE_SERVER_ERROR(SERVER_EXCEPTION),
		RESPONSE_PARAMETER_ERROR(PARAMETER_EXCEPTION),RESPONSE_DATA_NULL(DATA_IS_NULL);
		private Integer resCode;

		private RESPONSE_CODE(Integer resCode) {
			this.resCode = resCode;
		}

		public Integer getResCode() {
			return resCode;
		}
	}
	
	public enum DESCRIPTION{
		MY_START("我发起的"),NOT_PROCESSED("待我审批"),WAIT_APPROVE("待审批"),SUSPEND("已挂起"),COMPLETED("经我处理"),
		DETAIL("详情"),OPTION("操作");
		private String description;

		private DESCRIPTION(String description) {
			this.description = description;
		}

		public String getDescription() {
			return description;
		}
		
		
	}
	
	
	@Resource(name="processEngine")
transient 	ProcessEngine processEngine;
	
	protected int offset = 0;
	    
    protected int iDisplayLength = 10 ;
    
    
    protected String userId ;
    
    public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	/*private transient  Page<Object> page = new Page<Object>();
    
	public int getIDisplayLength() {
		return iDisplayLength;
	}

	public void setIDisplayLength(int iDisplayLength) {
		this.iDisplayLength = iDisplayLength;
	}

	public Page<Object> getPage() {
		this.page.setiDisplayStart(offset);
		this.page.setPageSize(this.iDisplayLength);
		int pageNum = (int) (Math.ceil(offset*1D / iDisplayLength)) + 1;
		this.page.setPageNo(pageNum);
		return page;
	}

	public void setPage(Page<Object> page) {
		this.page = page;
	}*/
	
	public TaskService getTaskService(){
		return processEngine.getTaskService();
	}
	
	public HistoryService getHistoryService(){
		return processEngine.getHistoryService();
	}
	
	public ExecutionService getExecutionService(){
		return processEngine.getExecutionService();
	}
	
	public void ajax(IJson json) throws Exception{
//		ServletActionContext.getResponse().getWriter().print(json.getMobileClientResponse());
		ajax(json.getMobileClientResponse());
	}
	
	/*public UserInfo getLoginUser(String userId){
		IUser userService = (IUser) SpringUtil.getBean("userService");
		return userService.getUserById(Integer.parseInt(userId));
	}
	*/
/*	public String getLoginName(String userId){
		UserInfo ui = getLoginUser(userId);
		return ui.getLoginName();
	}*/
}
