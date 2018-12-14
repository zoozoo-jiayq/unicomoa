package cn.com.qytx.cbb.dict.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.qytx.cbb.dict.dao.DictDao;
import cn.com.qytx.cbb.dict.domain.Dict;
import cn.com.qytx.cbb.dict.service.IDict;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.Pageable;
import cn.com.qytx.platform.base.service.impl.BaseServiceImpl;


/**
 * 
 * @Description:  [通用的信息维表IMPL]   
 * @Author:       [REN]   
 * @CreateDate:   [2012-10-8 下午08:17:37]   
 * @UpdateUser:   [REN]   
 * @UpdateDate:   [2012-10-8 下午08:17:37]   
 * @UpdateRemark: [说明本次修改内容]  
 * @Version:      [v1.0]
 */

@Transactional
@Service("dictService")
public class DictImpl extends BaseServiceImpl<Dict> implements IDict {
	private static final long serialVersionUID = 1L;
	@Resource(name = "dictDao")
    DictDao dictDao;

	/**
	* @Title: 
	* @Description:
	* @param @param infoType
	* @param @param sysTag
	* @param @return   
	* @return   返回类型
	* @throws
	*/
	public List<Dict> findList(String infoType, Integer sysTag) {
		return dictDao.findList(infoType,  sysTag) ;
	}
	

	/**
	 * @Title: findByPage 
	 * @Description: TODO(分页列表) 
	 * @param  createrId 创建人员ID
	 * @param  infoType 任务类型
	 * @return Page    返回类型
	 */
	public Page<Dict> findByPage(Pageable Pageable, Integer createrId, String infoType)
	{
		return dictDao.findByPage(Pageable, createrId, infoType);
	}
	
	/**
	 * @Title: findByPage 
	 * @Description: 根据任务infoType获取对应的任务名称
	 * @param  infoType 任务类型
	 * @return List<String> 返回类型
	 */
	public List<Dict> findListByInfoType(String infoType)
	{
		List<Dict> list = dictDao.findListByInfoType(infoType);
		List<Dict> result = new ArrayList<Dict>();
		for(int i=0; i<list.size(); i++){
			if(list.get(i).getSysTag()!=-1){
				result.add(list.get(i));
			}
		}
		return result;
	}
	/**
	* @Title: 
	* @Description:
	* @param @param infoType
	* @param @param sysTag
	* @param @return   
	* @return   返回类型
	* @throws
	*/
	public List<Dict> findListByName(String name) {
		return dictDao.findListByName(name) ;
	}
	/**
	* @Title: loadByTypeTagValue
	* @Description:(根据类型值得到通用信息)
	* @param  infoType
	* @param  sysTag
	* @param  value
	* @return InfoType   返回类型
	*/
	public Dict loadByTypeTagValue(String infoType, Integer sysTag,Integer value) {
		return dictDao.loadByTypeTagValue(infoType, sysTag, value);
	}
	
	/**
	* @Title: getInfoTypeByName
	* @param  infoType
	* @param  sysTag
	* @param  name
	* @return InfoType   返回类型
	*/
	public Dict getInfoTypeByName(String infoType, Integer sysTag,String name) {
		return dictDao.getInfoTypeByName(infoType, sysTag, name);
	}

	/**
	* @Title: 
	* @Description:
	* @param @param infoType
	* @param @param sysTag
	* @param @param value
	* @param @return   
	* @return   返回类型
	* @throws
	*/
	public List<Dict> findList(String infoType, Integer sysTag,
			Integer value) {
		return dictDao.findList(infoType, sysTag, value);
	}

	

	/**
	 * 
	 * @param sysTag  类别：0--系统
	 * @return
	 */
	public List<Dict> findSysList(Integer sysTag) {
		return dictDao.findSysList(sysTag) ;
	}
	
	/**
	* @Title: getInfoTypeByName
	* @param  infoType
	* @param  sysTag
	* @param  name
	* @return InfoType   返回类型
	*/
	public Dict getInfoTypeByNameAndNotId(String infoType, Integer sysTag,String name,Integer id){
		return dictDao.getInfoTypeByNameAndNotId(infoType, sysTag, name,id);
	}


	public int deleteByInfoType(String infotype) {
		return dictDao.deleteByInfoType(infotype);
	}


	public int upateDictByInfoType(String oldInfoType,String newInfoType) {
		
		return dictDao.upateDictByInfoType(oldInfoType, newInfoType);
	}

	public Map<String,Map<String,String>> findAllDict(){
		return dictDao.findAllDict();
	}
	
	public Map<String,String> findMap(String infoType,Integer sysTag){
		List<Dict> dicts=dictDao.findList(infoType,  sysTag) ;
		Map<String,String> map=new HashMap<String,String>();
		if(dicts!=null){
			for(Dict dict:dicts){
				map.put(String.valueOf(dict.getValue()), dict.getName());
			}
		}
		return map;
	}
	public List<Dict> findAllList(String infoType, Integer sysTag){
		return dictDao.findAllList(infoType, sysTag);
	}
	
	public List<Dict> findAllListByValue(String infoType, Integer sysTag){
		return dictDao.findAllListByValue(infoType, sysTag);
	}
}