package cn.com.qytx.cbb.notify.service;

import cn.com.qytx.cbb.notify.domain.PlatformParameter;
import cn.com.qytx.platform.base.service.BaseService;

public interface IPlatformParameter extends BaseService<PlatformParameter>{
	
	/**
	 * 功能： 通过栏目id查找对象
	 * @param instentceid
	 * @return
	 */
	public PlatformParameter findByInstenceId(int instentceid);
	/**
	 * 功能： 通过参数对象复制属性
	 * @param instentceid
	 * @return
	 */
	public Object findEntityByInstenceId(int instentceid);
}
