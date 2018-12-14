package cn.com.qytx.cbb.myapply.service;

import java.util.List;

import cn.com.qytx.cbb.myapply.domain.MyWaitProcess;
import cn.com.qytx.cbb.myapply.service.MyApplyService.ModuleCode;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.Pageable;
import cn.com.qytx.platform.base.service.BaseService;

public interface IMyWaitProcess extends BaseService<MyWaitProcess>{

	
	/**
	 * 功能：根据用户id查询待办理列表
	 * @return
	 */
	public Page<MyWaitProcess> findListByUserId(Pageable pageable,Integer userId,String moduleCode);
	
	/**
	 * 功能：根据模块代码，任务ID，审批人查询待处理任务
	 * 作者：jiayongqiang
	 * 参数：
	 * 输出：
	 */
	public MyWaitProcess findByModuleCodeAndInstanceId(ModuleCode code,String instanceId,Integer approver);
	
	/**
	 * 删除
	 * @param instanceId
	 * @param moduleCode
	 */
	public void del(String instanceIds, String moduleCode);
	
	/**
	 * 功能： 获得所有待审批的数量
	 * @param userId
	 * @return
	 */
	public int myWaitCount(Integer userId);
	
	/**
	 * 功能：获得指定模块下面待处理数量
	 * @param userId
	 * @param moduleCode
	 * @return
	 */
	public int myWaitModuleCount(Integer userId, String moduleCode);
	
	/**
	 * 功能：获得指定模块下面指定流程的审批人
	 * @param instanceIds
	 * @param moduleCode
	 * @return
	 */
	public List<Integer> getProcesserByInstanceIds(String instanceIds,String moduleCode);
	
	/**
	 * 功能：保存我的待办列表
	 * @param list
	 * @return
	 */
	public List<MyWaitProcess> saveList(Iterable<MyWaitProcess> list);
	
	/**
	 * 根据流程ID查询待审批的流程
	 * @param instanceId
	 * @return
	 */
	public List<MyWaitProcess> findByInstanceId(String instanceId);
}
