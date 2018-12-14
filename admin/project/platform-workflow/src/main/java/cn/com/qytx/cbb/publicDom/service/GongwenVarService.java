package cn.com.qytx.cbb.publicDom.service;

import java.util.Map;

import cn.com.qytx.cbb.publicDom.domain.GongwenVar;
import cn.com.qytx.platform.base.service.BaseService;


/**
 * 功能：
 * 版本: 1.0
 * 开发人员：贾永强
 * 创建日期: 下午4:21:46 
 * 修改日期：下午4:21:46 
 * 修改列表：
 */
public interface GongwenVarService  extends BaseService<GongwenVar>{
	
	/**
	 * 功能：根据流程实例ID查询公文变量
	 * @param
	 * @return
	 * @throws   
	 */
	public GongwenVar findByInstanceId(String instanceId);
	
	/**
	 * 功能：获取任务发起人信息
	 * 作者：jiayongqiang
	 * 参数：
	 * 输出：
	 */
	public Map<String,String> findCreaterInfoByInstanceId(String instanceId);
}
