package cn.com.qytx.cbb.dict.dao;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.com.qytx.cbb.dict.domain.Dict;
import cn.com.qytx.platform.base.dao.BaseDao;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.Pageable;
import cn.com.qytx.platform.base.query.Sort;
/**
 * 功能:dictDao
 * 版本: 1.0
 * 开发人员: zhangjingjing
 * 创建日期: 2014-6-27
 * 修改日期: 2014-6-27
 * 修改列表:
 */
@Repository("dictDao")
public class DictDao extends BaseDao<Dict,Integer> implements Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 * @Title: delete
	 * @Description: (删除通用信息) 
	 * @param id
	 * @return void    返回类型
	 */
	public void delete(Integer id) {
		String hql="update Dict set isDelete=1 where id=?";
		this.bulkUpdate(hql, id);
	}
    /**
     * 功能：根据类型删除数据
     */
    public int deleteByInfoType(String infotype){
    	String hql="update Dict set isDelete=1 where infoType=?";
    	return bulkUpdate(hql, infotype);
    }
    
    /**
     * 更改唯一标识
     * @param oldInfoType
     * @param newInfoType
     * @return
     */
    public int upateDictByInfoType(String oldInfoType,String newInfoType){
    	return bulkUpdate("update Dict set infoType=?1 where infoType=?2 and isDelete=0", newInfoType,oldInfoType);
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
	public List<Dict> findList(String infoType, Integer sysTag) {
		return findAll("infoType=?1 and sysTag=?2 and isDelete=0",new Sort(new Sort.Order(Sort.Direction.ASC,"infoOrder")),infoType,sysTag);
	}
	
	public List<Dict> findAllList(String infoType, Integer sysTag) {
		return findAll("infoType=?1 and sysTag=?2",new Sort(new Sort.Order(Sort.Direction.ASC,"infoOrder")),infoType,sysTag);
	}
	
	public List<Dict> findAllListByValue(String infoType, Integer sysTag) {
		return findAll("infoType=?1 and sysTag=?2",new Sort(new Sort.Order(Sort.Direction.ASC,"value")),infoType,sysTag);
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
	public List<Dict> findList(String infoType, Integer sysTag,Integer value) {
		return findAll("infoType=?1 and sysTag=?2 and value=?3",infoType,sysTag,value);
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
		return findOne("infoType=?1 and sysTag=?2 and value=?3",infoType,sysTag,value);
	}
	
	/**
	* @Title: getInfoTypeByName
	* @param  infoType
	* @param  sysTag
	* @param  name
	* @return InfoType   返回类型
	*/
	public Dict getInfoTypeByName(String infoType, Integer sysTag,String name) {
		return  findOne("infoType=?1 and sysTag=?2 and name=?3 and isDelete=0",infoType,sysTag,name);
	}
	
	/**
	* @Title: getInfoTypeByName
	* @param  infoType
	* @param  sysTag
	* @param  name
	* @return InfoType   返回类型
	*/
	public Dict getInfoTypeByNameAndNotId(String infoType, Integer sysTag,String name,Integer id) {
		return findOne("infoType=?1 and sysTag=?2 and name=?3 and id !=?4 and isDelete=0", infoType,sysTag,name,id);
	}
	/**
	 * @Title: findByPage 
	 * @Description: (分页列表) 
	 * @param  createrId 创建人员ID
	 * @param  infoType 任务类型
	 * @return Page    返回类型
	 */
	public Page<Dict> findByPage(Pageable pageable, Integer createrId, String infoType)
	{
		return findAll("isDelete=1 and recordUserId=?1 and infoType=?2",pageable, createrId, infoType);
	}
	
	/**
	 * @Title: findByPage 
	 * @Description: 根据任务infoType获取对应的任务名称
	 * @param  infoType 任务类型
	 * @return List<String> 返回类型
	 */
	public List<Dict> findListByInfoType(String infoType)
	{
		String hql=" isDelete=0 and infoType= ?";
		return super.findAll(hql, infoType);
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
	
	public List<Dict> findListByName(String name) {
		return this.findAll("isDelete=0 and name like'%?1%'",name);
	}
	
	/**
	 * @param sysTag  类别：0--系统
	 * @return
	 */
	public List<Dict> findSysList(Integer sysTag) {
		return this.findAll("isDelete=0 and sysTag=?1",new Sort(new Sort.Order(Sort.Direction.ASC,"infoOrder")),sysTag);
	}
	
	public Map<String,Map<String,String>> findAllDict(){
		Map<String,Map<String,String>> rs=null;
			List<Dict> pDicts= (List<Dict>)this.findAll(" isDelete=0  and sysTag=-1");
			List<Dict> dicts=  (List<Dict>)this.findAll(" isDelete=0  and sysTag!=-1");
			if(pDicts==null){
				return new HashMap<String,Map<String,String>>();
			}else{
				rs=new HashMap<String,Map<String,String>>();
				if (dicts!=null) {
					for(Dict dict:dicts){
						rs.put(dict.getInfoType(), new HashMap<String,String>());
					}
				}
			}
			if(dicts!=null){
				for(Dict dict:dicts){
					rs.get(dict.getInfoType()).put(String.valueOf(dict.getValue()), dict.getName());
				}
			}
			return rs;
		}

}