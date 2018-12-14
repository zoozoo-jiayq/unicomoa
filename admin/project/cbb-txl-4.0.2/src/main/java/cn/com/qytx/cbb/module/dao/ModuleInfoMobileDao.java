package cn.com.qytx.cbb.module.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import cn.com.qytx.cbb.module.domain.ModuleInfoMobile;
import cn.com.qytx.platform.base.application.TransportUser;
import cn.com.qytx.platform.base.dao.BaseDao;
import cn.com.qytx.platform.base.query.Sort;
import cn.com.qytx.platform.base.query.Sort.Direction;
/**
 * 手机模块
 * @author liyanlei
 *
 */
@Repository("moduleInfoMobileDao")
public class ModuleInfoMobileDao extends BaseDao<ModuleInfoMobile,Integer>{
	
	/**
	 * 查询分页显示
	 * @param keyword
	 * @return
	 */
	public List<ModuleInfoMobile> findPager( String keyword) {
  		String hql = " 1=1 ";
		if(keyword!=null && !"".equals(keyword)){
			hql = " and (code like '%"+keyword+"%' or name like '%"+keyword+"%')";
		}
		return super.findAll(hql);
	}
	/**
	 * 启用状态
	 * @param id
	 * @param statue
	 */
	public void updateStatue(Integer id, Integer statue) {
		String hql = "update ModuleInfoMobile set statue = ?1 where id = ?2";
		executeQuery(hql,statue,id);
	}
	
	/**
	 * 模块名称验证
	 * @param id
	 * @param name
	 * @return
	 */
	public boolean valiName(Integer id, String name) {
		String hql ="";
		if(id == null){
			hql = "name = ?1";
			return findOne(hql,name)!=null;
		}else{
			hql = "name = ?1 and id != ?2 ";
			return findOne(hql,name,id) != null;
		}
		
	}
	/**
	 * 通过排序号得到模块信息
	 * @param orderList
	 * @return
	 */
	public ModuleInfoMobile getByOrder(String orderList){
		String hql = "orderList = ?1";
		return super.findOne(hql,orderList);
	}
	
	/**
	 * 编码验证
	 * @param id
	 * @param code
	 * @return
	 */
	public boolean valiCode(Integer id,String code) {
		if(id == null){
			String hql = "code = ?1";
			return findOne(hql,code)!=null;
		}else{
			String hql = "code = ?1 and id != ?2";
			return findOne(hql,code,id)!=null;
		}
		
	}
	/**
	 * 验证排序号
	 * @param id
	 * @param order
	 * @return
	 */
	public Integer valiOrder(Integer id,String order){
		String hql = "select count(*) as counting from  ModuleInfoMobile where ";
		if(id == null){
			hql += "orderList = ?1 ";
			return Integer.parseInt(entityManager.createQuery(hql).setParameter(1,order).getSingleResult().toString());
		}else{
			hql += "orderList = ?1 and id != ?2 ";
			return Integer.parseInt(entityManager.createQuery(hql).setParameter(1,order).setParameter(2,id).getSingleResult().toString());
		}
		
	}
	/**
	 * 通过排序号得到模块信息
	 * @param order
	 * @return
	 */
	public boolean getModuleByOrder(String order){
		String hql = "orderList = ?1 ";
		return findOne(hql,order)!=null;
	}
	/**
	 * 得到一级列表
	 * @return
	 */
	public List<ModuleInfoMobile> getRootList(){
		String hql = "from ModuleInfoMobile where statue = 1 and parent is null";
		return entityManager.createQuery(hql).getResultList();
	}
	
	/**
	 * 登录主页
	 * @param id
	 * @param roleIdArr
	 * @return
	 */
	public List<ModuleInfoMobile> mobileIndex(Integer id, String roleIdArr) {
	        if (StringUtils.isEmpty(roleIdArr)){
	            return  new ArrayList<ModuleInfoMobile>();
	        }else{
	        	 String hql = "from ModuleInfoMobile where 1=1";
	 	        if(roleIdArr!=null && !roleIdArr.equals("")){
	 	            hql+=" and statue = 1 and id in (select moduleId from RoleModuleMobile where roleId in ("+roleIdArr+"))";
	 	           if(id!=null && !"".equals(id)){
		 	        	hql += " and parent.id = "+id;
		 	        }
	 	        }
	 	        hql+= " order by orderList asc";
	 	        //Sort s = new Sort(new Sort.Order(Direction.ASC,"orderList"));
	 	        return this.find(hql);
	        }
	}
	/**
	 * 通过得到编码得到模块信息
	 * @param code
	 * @return
	 */
	public ModuleInfoMobile getByCode(String code) {
		String hql = "code = ?1";
		return findOne(hql,code);
	}

}
