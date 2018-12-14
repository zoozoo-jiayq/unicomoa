package cn.com.qytx.cbb.dict.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import cn.com.qytx.cbb.dict.domain.Dict;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.Pageable;
import cn.com.qytx.platform.base.service.BaseService;

/**
 * 
 * @Description:  [通用的信息维表SERVICE]   
 * @Author:       [REN]   
 * @CreateDate:   [2012-10-8 下午08:17:37]   
 * @UpdateUser:   [REN]   
 * @UpdateDate:   [2012-10-8 下午08:17:37]   
 * @UpdateRemark: [说明本次修改内容]  
 * @Version:      [v1.0]
 */

public interface IDict extends BaseService<Dict>,Serializable {
	
	/**
	* @Title: 根据 sysTag 和 InfoType 获取所有信息
	* @Description:
	* @param @param infoType
	* @param @param sysTag //来源系统1：仓储2：物流 3：电商
	* @param @return   
	* @return   返回类型
	* @throws
	*/	
	public List<Dict> findList(String infoType, Integer sysTag);

	
	/**
	* @Title: 根据 sysTag 和 InfoType 和value 获取对应的类别信息
	* @Description:
	* @param @param infoType
	* @param @param sysTag //来源系统1：仓储2：物流 3：电商
	* @param @return   
	* @return   返回类型
	* @throws
	*/	
	public List<Dict> findList(String infoType, Integer sysTag,Integer value);
	
	/**
	 * @Title: findByPage 
	 * @Description: TODO(分页列表) 
	 * @param  createrId 创建人员ID
	 * @param  name 任务类型
	 * @return Page    返回类型
	 */
	public Page<Dict> findByPage(Pageable pageable,Integer createrId, String infoType);
	
	/**
	 * @Title: findByPage 
	 * @Description: 根据任务infoType获取对应的任务名称
	 * @param  infoType 任务类型
	 * @return List<String> 返回类型
	 */
	public List<Dict> findListByInfoType(String infoType);	
    
	/**
	* @Title: loadByTypeTagValue
	* @Description:(根据类型值得到通用信息)
	* @param  infoType
	* @param  sysTag
	* @param  value
	* @return InfoType   返回类型
	*/
	public Dict loadByTypeTagValue(String infoType, Integer sysTag,Integer value);
	
	/**
	* @Title: getInfoTypeByName
	* @param  infoType
	* @param  sysTag
	* @param  name
	* @return InfoType   返回类型
	*/
	public Dict getInfoTypeByName(String infoType, Integer sysTag,String name);
	
	/**
	* @Title: 
	* @Description:
	* @param @param infoType
	* @param @param sysTag
	* @param @return   
	* @return   返回类型
	* @throws
	*/
	public List<Dict> findListByName(String name);
	/**
	 * 
	 * @param sysTag  类别：0--系统
	 * @return
	 */
	public List<Dict> findSysList(Integer sysTag);
	
	/**
	* @Title: getInfoTypeByName
	* @param  infoType
	* @param  sysTag
	* @param  name
	* @return InfoType   返回类型
	*/
	public Dict getInfoTypeByNameAndNotId(String infoType, Integer sysTag,String name,Integer id);
	/**
	 * 功能：根据父类id删除子类型
	 * @return
	 */
	public  int  deleteByInfoType(String infotype);
	
	/**
	 * 更改标识
	 * @param infoType
	 * @return
	 */
	public int  upateDictByInfoType(String oldInfoType,String newInfoType);
	
	/**
	 * 功能：查询所有字典类型
	 * 
	 * @return Map<infoType,Map<value,name>>
	 */
	public Map<String,Map<String,String>> findAllDict();
	/**
	 * 功能：
	* <p>标题: findMap 根据 sysTag 和 InfoType 获取所有信息</p>
	* <p>描述:根据 sysTag 和 InfoType 获取所有信息 </p>
	*  @param infoType
	*  @param sysTag
	*  @return map <>
	 */
	public Map<String,String> findMap(String infoType,Integer sysTag);
	/**
	 * 功能：获取所有数据包括已经删除的
	 * @return
	 */
	public List<Dict> findAllList(String infoType, Integer sysTag);
	
	public List<Dict> findAllListByValue(String infoType, Integer sysTag);

}