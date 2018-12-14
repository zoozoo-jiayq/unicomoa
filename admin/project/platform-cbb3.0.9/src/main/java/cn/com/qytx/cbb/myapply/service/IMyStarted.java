package cn.com.qytx.cbb.myapply.service;

import cn.com.qytx.cbb.myapply.domain.MyStarted;
import cn.com.qytx.cbb.myapply.service.MyApplyService.ModuleCode;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.Pageable;
import cn.com.qytx.platform.base.service.BaseService;

public interface IMyStarted extends BaseService<MyStarted>{

	
	/**
	 * 功能：根据用户id查询申请列表
	 * @return
	 */
	public Page<MyStarted> findListByUserId(Pageable pageable,Integer userId,String moduleCode);
	
	/**
	 * 根据流程Id查询我发起的流程
	 * @param instanceId
	 * @return
	 */
	public MyStarted findByInstanceId(ModuleCode code,String instanceId);
	
	/**
	 * 根据流程Id查询我发起的流程
	 * @param instanceId
	 * @return
	 */
	public MyStarted findByInstanceId(String instanceId);
	
}
