package cn.com.qytx.cbb.module.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.com.qytx.cbb.module.dao.ModuleInfoMobileDao;
import cn.com.qytx.cbb.module.domain.ModuleInfoMobile;
import cn.com.qytx.cbb.module.service.IModuleInfoMobile;
import cn.com.qytx.platform.base.service.impl.BaseServiceImpl;
import cn.com.qytx.platform.org.dao.RoleDao;
import cn.com.qytx.platform.org.domain.RoleInfo;

@Service("moduleInfoMobileService")
@Transactional
public class ModuleInfoMobileImpl extends BaseServiceImpl<ModuleInfoMobile> implements IModuleInfoMobile {
	
	@Resource(name="moduleInfoMobileDao")
	private ModuleInfoMobileDao moduleInfoMobileDao;
	@Resource(name="roleDao")
    private RoleDao<RoleInfo> roleDao;
	
	@Transactional(readOnly=true)
	public List<ModuleInfoMobile> findPager(String keyword){
		return moduleInfoMobileDao.findPager(keyword);
	}
	@Transactional(propagation=Propagation.REQUIRED)
	public void updateStatue(Integer id,Integer statue){
		moduleInfoMobileDao.updateStatue(id,statue);
	}
	@Transactional(readOnly=true)
	public boolean valiName(Integer id, String name){
		return moduleInfoMobileDao.valiName(id,name);
	}
	@Transactional(readOnly=true)
	public Integer valiOrder(Integer id,String order){
		Integer count = moduleInfoMobileDao.valiOrder(id, order);
		if(count>0){
			return 1;
		}else{
			if(order.length() > 2){
				order = order.substring(0,order.length()-2);
				if(moduleInfoMobileDao.getModuleByOrder(order)){
					return 2;
				}else{
					return 3;
				}
			}else{
				return 2;
			}
		}
	}
	@Transactional(readOnly=true)
	public boolean valiCode(Integer id,String code){
		return moduleInfoMobileDao.valiCode(id,code);
	}
	@Transactional(readOnly=true)
	public List<ModuleInfoMobile> getRootTreeList(){
		return moduleInfoMobileDao.getRootList();
	}
	@Transactional(propagation=Propagation.REQUIRED)
	public ModuleInfoMobile saveOrUpdate(ModuleInfoMobile moduleInfoMobile) {
		if(moduleInfoMobile.getOrderList().length()>2){
			String order = moduleInfoMobile.getOrderList().substring(0,moduleInfoMobile.getOrderList().length()-2);
			ModuleInfoMobile parent = moduleInfoMobileDao.getByOrder(order);
			moduleInfoMobile.setParent(parent);
		}
		return super.saveOrUpdate(moduleInfoMobile);
	}
	@Transactional(readOnly=true)
	public List<ModuleInfoMobile> mobileIndex(Integer id,Integer userId){
		List<RoleInfo> roleList =roleDao.findRolesByUserId(userId, 1);
		String roleIdArr="";
        if(roleList!=null)
        {
            roleIdArr= getRoleIds(roleList);
        }
		return moduleInfoMobileDao.mobileIndex(id,roleIdArr);
	}
	  /**
     * 获取角色id列表
     * @param roleList
     * @return
     */
    private String getRoleIds(final List<RoleInfo> roleList)
    {
        StringBuffer roleIdArr= new StringBuffer();
        String result = "";
        for(RoleInfo role:roleList)
        {
            roleIdArr.append(role.getRoleId()+",");
        }
        if(roleIdArr.toString().endsWith(","))
        {
            result=roleIdArr.substring(0,roleIdArr.length()-1);
        }
        return result;
    }
    @Transactional(readOnly=true)
    public ModuleInfoMobile getByCode(String code){
    	return moduleInfoMobileDao.getByCode(code);
    }
    @Transactional(propagation=Propagation.REQUIRED)
    public void update(ModuleInfoMobile oldModuleInfoMobile){
    	moduleInfoMobileDao.saveOrUpdate(oldModuleInfoMobile);
    }
    public void save(ModuleInfoMobile moduleInfoMobile){
    	moduleInfoMobileDao.saveOrUpdate(moduleInfoMobile);
    }
}
