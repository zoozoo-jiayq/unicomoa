package cn.com.qytx.cbb.module.service;

import java.util.List;

import cn.com.qytx.cbb.module.domain.ModuleInfoMobile;
import cn.com.qytx.platform.base.service.BaseService;
/**
 * 手机模块接口定义
 * @author liyanlei
 *
 */
public interface IModuleInfoMobile extends BaseService<ModuleInfoMobile>{
		
	/**
	 * 分页查询
	 * @param pageable
	 * @param keyword
	 * @return
	 */
	public List<ModuleInfoMobile> findPager(String keyword);
	
	/**
	 * 更改状态
	 * @param id
	 * @param statue
	 */
	public void updateStatue(Integer id,Integer statue);
	
	/**
	 * 验证名称是否存在，true表示存在，false表示不存在
	 * @param id
	 * @param name
	 */
	public boolean valiName(Integer id, String name);
	/**
	 * 验证排序，1 表示已存在，2表示成功，3表示父节点不存在
	 * @param id
	 * @param order
	 */
	public Integer valiOrder(Integer id, String order);
	
	/**
	 * 验证编码
	 * @param code
	 * @return
	 */
	public boolean valiCode(Integer id,String code);
	
	/**
	 * 手机端模块
	 * @return
	 */
	public List<ModuleInfoMobile> getRootTreeList();
	
	/**
	 * 
	 * @param id
	 * @param userId
	 * @return
	 */
	public List<ModuleInfoMobile> mobileIndex(Integer id,Integer userId);
	
	/**
	 * 通过code得到对象
	 * @param code
	 * @return
	 */
	public ModuleInfoMobile getByCode(String code);
	
	/**
	 * 
	 * @param 更新
	 */
	public void update(ModuleInfoMobile oldModuleInfoMobile);
	
	/**
	 * 保存
	 * @param moduleInfoMobile
	 */
	public void save(ModuleInfoMobile moduleInfoMobile);
	
}
