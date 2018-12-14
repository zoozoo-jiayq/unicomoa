package cn.com.qytx.cbb.systemSet.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.com.qytx.cbb.systemSet.domain.SysConfig;
import cn.com.qytx.platform.base.dao.BaseDao;

/**
 * 功能：系统设置对象
 * 版本: 1.0
 * 开发人员：贾永强
 * 创建日期: 下午5:03:46 
 * 修改日期：下午5:03:46 
 * 修改列表：
 */
@Repository
public class SysConfigDao extends BaseDao<SysConfig, Integer> {

	/**
	 * 功能：读取配置文件
	 * @param
	 * @return
	 * @throws   
	 */
	public Map<String,String> getSysConfig(){
		Map map = new HashMap<String, String>();
		List<SysConfig> sc = super.findAll();
		if(sc!=null){
			for(int i=0; i<sc.size(); i++){
				SysConfig sys = sc.get(i);
				String key = sys.getConfigKey();
				String value = sys.getConfigValue();
				map.put(key, value);
			}
		}
		return map;
	}
	
	
	/**
	 * 功能：保存系统设置
	 * @param
	 * @return
	 * @throws   
	 */
	public void saveSysConfig(Map<String,String> map){
	    updateOneConfig(map, SysConfig.SYS_APPROVE_WIDGET);
	    updateOneConfig(map, SysConfig.SYS_READER_WIDGET);
	    updateOneConfig(map, SysConfig.SYS_APPROVE_COMMENT);
	    updateOneConfig(map, SysConfig.NOTICE_UPDATE_PASSWORD);
	    updateOneConfig(map, SysConfig.DOM_GATHER_REGISTER);
	    updateOneConfig(map, SysConfig.DOM_GATHER_APPROVE);
	    updateOneConfig(map, SysConfig.FORCE_READ);
	    updateOneConfig(map, SysConfig.DOM_GATHER_ZIP);
	    updateOneConfig(map, SysConfig.DOM_DISPATCH_NIGAO);
	    updateOneConfig(map, SysConfig.DOM_DISPATCH_HEGAO);
	    updateOneConfig(map, SysConfig.DOM_DISPATCH_RED);
	    updateOneConfig(map, SysConfig.DOM_DISPATCH_ZIP);
	    updateOneConfig(map, SysConfig.BUMENZHUANLAN);
	    updateOneConfig(map, SysConfig.BUMENZHUANLAN_NAME);
	}
	
	private void updateOneConfig(Map<String,String> map,String key){
	    String sql = "configKey = ?";
        if(map.get(key)!=null){
            String hql = "update SysConfig set configValue = ? where configKey = ?";
            List<SysConfig> list = super.findAll(sql, key);
            if(list!=null && list.size()>0){
                super.executeQuery(hql,map.get(key), key);
            }else{
                SysConfig config = new SysConfig();
                config.setConfigKey(key);
                config.setConfigValue(map.get(key));
                super.saveOrUpdate(config);
            }
        }
	}


	public SysConfig getSysConfig(String key) {
		String hql = "configKey = ?1 ";
		return findOne(hql,key);
	}
}
