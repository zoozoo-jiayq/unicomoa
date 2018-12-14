package cn.com.qytx.cbb.myapply.service;

import java.util.List;

import cn.com.qytx.cbb.myapply.domain.MyProcessed;
import cn.com.qytx.platform.org.domain.UserInfo;

/**
 * 功能：我的申请对系统内其它模块提供的API
 * 作者： jiayongqiang
 * 创建时间：2014年8月21日
 */
public interface MyApplyService {
	
	/**
	 * 功能：目前只有三个模块使用该功能；workflow:工作流，news:新闻；notify：通知公告
	 * 作者： jiayongqiang
	 * 创建时间：2014年8月21日
	 */
	public enum ModuleCode{
		WORKFLOW("workflow"),NEWS("news"),NOTIFY("notify"),BASEWORKFLOW("baseworkflow");
		
		private String code;
		private ModuleCode(String str){
			this.code = str;
		}
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		
	}
	

	/**
	 * 功能：创建工作流、新闻、公告的时候，调用该接口。该API会在【我的申请】表中插入一条记录，记录creater的申请记录
	 * 作者：jiayongqiang
	 * 参数：	creater；创建人；
			categoryName:分类名称
			title:标题
			instanceId:业务Id,如果是新闻公告，则是新闻公告记录的ID
			moduleCode:枚举类型，标记各个模块
	 * 输出：
	 */
	public void createProcess(UserInfo creater,
								String categoryName,
								String title,
								String instanceId,
								ModuleCode moduleCode);
	
	/**
	 * 功能：当审批的时候调用该接口。该API会将【待审批】表中对应的记录删除，在【已审批】表中插入一条审批历史
	 * 作者：jiayongqiang
	 * 参数：
	 * 	moduleCode:模块标志
		instanceId:业务ID
		approver:审批人，可以为空
		advice:审批意见
		state:状态，该状态将会改变申请表的状态
		approveResult:审批结果
	 * 输出：
	 */
	public void approve(ModuleCode moduleCode,
						String instanceId,
						UserInfo approver,
						String advice,String state,String approveResult);
	
	/**
	 * 功能：当审批的时候调用该接口。该API会将【待审批】表中插入一条记录
	 * 作者：jiayongqiang
	 * 参数：
		categoryName:分类名称
		title:标题
		InstanceId:业务ID
		nextAssigner:下一步处理人，多个人用逗号隔开
		moduleCode:模块标志
	 * 输出：
	 */
	public void addApprover(String categoryName,
							String title,
							String instanceId,
							String nextAssigner,
							ModuleCode moduleCode);
	/**
	 * 功能：更新我的申请状态
	 * 作者：jiayongqiang
	 * 参数：
	 * 输出：
	 */
	public void updateState(ModuleCode moduleCode,String instanceId,String state);

	/**
	 * 删除业务记录
	 * @param instanceId
	 * @param moduleCode
	 */
	public void delMyStarted(String instanceIds,ModuleCode moduleCode);
	
	/**
	 * 功能：驳回/撤销的时候，删除指定instanceId的所有待审批记录，在审批历史表中添加一条记录
	 * 作者：jiayongqiang
	 * 参数：
	 * 输出：
	 */
	public void rollback(ModuleCode moduleCode,String instanceId,UserInfo approver,String advice,String state,String rollbackType);
	
	/**查询制定流程的审批历史
	 * @param instanceId
	 * @return
	 */
	public List<MyProcessed> findHistoryByInstanceId(String instanceId);
}
