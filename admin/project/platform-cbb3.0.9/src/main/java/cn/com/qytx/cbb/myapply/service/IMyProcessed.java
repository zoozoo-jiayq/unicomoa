package cn.com.qytx.cbb.myapply.service;

import java.util.List;

import cn.com.qytx.cbb.myapply.domain.MyProcessed;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.Pageable;
import cn.com.qytx.platform.base.service.BaseService;

public interface IMyProcessed extends BaseService<MyProcessed>{

	
	/**
	 * 功能：根据用户id查询已办理的记录
	 * @return
	 */
	public Page<MyProcessed> findListByUserId(Pageable pageable,Integer userId,String moduleCode);
	
	/**
	 * 单个记录删除
	 * @param instanceId
	 * @param moduleCode
	 */
	public void del(String instanceIds, String moduleCode);
	
	public List<MyProcessed> findByInstanceId(String instanceId);
	
}
