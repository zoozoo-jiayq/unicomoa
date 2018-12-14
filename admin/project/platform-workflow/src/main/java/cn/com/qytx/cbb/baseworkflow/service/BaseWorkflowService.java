package cn.com.qytx.cbb.baseworkflow.service;

import cn.com.qytx.cbb.baseworkflow.domain.BaseWorkflow;
import cn.com.qytx.cbb.baseworkflow.model.ViewModule;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.Pageable;
import cn.com.qytx.platform.base.service.BaseService;

/**
 * 功能  固定流程接口
 * <br/>版本  1.0
 * <br/>开发人员  zyf
 * <br/>创建日期  2016年1月6日
 * <br/>修改日期  2016年1月6日
 * <br/>修改列表
 */
public interface BaseWorkflowService extends BaseService<BaseWorkflow> {
	
	/**
	 * 获取最大的流程实例Id
	 * @return
	 */
	public String getLastBaseworkflowInstanceId();
	
	/**
	 * 发起流程
	 * @param userId 发起人
	 * @param formData 表单数据
	 * @param userIds 审批人列表，多个人之间用逗号隔开
	 * @param code 类型code
	 */
	public String start(int userId,String formData,String userIds,String code);
	
	/**
	 * 审批
	 * @param userId  审批人
	 * @param instanceId 流程实例ID
	 * @param formData 审批数据
	 * @param approveResult 审批结果
	 */
	public void approve(int userId,String instanceId,String advice,String approveResult);
	
	/**
	 * 查看流程的详细信息
	 * @param instanceId
	 * @return
	 */
	public ViewModule findDetailInfoByInstanceId(String instanceId);
	
	/**
	 * 删除流程
	 * @param instanceId
	 */
	public void deleteByInstanceId(String instanceId);
	
	/**
	 * 转交
	 * @param userId 用户ID，当前转交人ID
	 * @param turner 转交人，转交对象
	 * @param instanceId 流程实例ID
	 * @param advice  转交意见
	 */
	public void turn(Integer userId,String turner,String instanceId,String advice);
	
	/**
	 * 分页查询我申请的
	 * @param page
	 * @param userId
	 * @return
	 */
	public Page<BMyStarted> findByStartedList(Pageable page,Integer userId,String moduleCode);
	
	/**
	 * 查询待我审批的
	 * @param page
	 * @param userId
	 * @return
	 */
	public Page<BMyWaitApprove> findByWaitApproveList(Pageable page,Integer userId,String moduleCode);
	
	/**
	 * 查询我已经审批过的
	 */
	public Page<BMyProcessed> findMyProcessedList(Pageable page,Integer userId,String moduleCode);
	
	/**
	 * 查询待我审批的数量
	 * @param userId
	 * @param moduleCode
	 */
	public int myWaitCount(Integer userId, String moduleCode);
}
