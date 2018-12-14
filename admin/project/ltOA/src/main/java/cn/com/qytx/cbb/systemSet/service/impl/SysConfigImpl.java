package cn.com.qytx.cbb.systemSet.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.qytx.cbb.systemSet.dao.SysConfigDao;
import cn.com.qytx.cbb.systemSet.domain.SysConfig;
import cn.com.qytx.cbb.systemSet.service.SysConfigService;


/**
 * 功能：系统配置实现类
 * 版本: 1.0
 * 开发人员：贾永强
 * 创建日期: 下午5:31:31 
 * 修改日期：下午5:31:31 
 * 修改列表：
 */
@Service("sysConfigService")
@Transactional
public class SysConfigImpl implements SysConfigService {

	@Resource
	private SysConfigDao sysConfigDao;
	
	/* 添加默认设置
	 * @see cn.com.qytx.cbb.systemSet.service.SysConfigService#getSysConfig()
	 */
	public Map<String, String> getSysConfig() {
		// TODO Auto-generated method stub
	    
	    Map<String,String> map = sysConfigDao.getSysConfig();
        if(map.get(SysConfig.SYS_APPROVE_WIDGET) == null){
            map.put(SysConfig.SYS_APPROVE_WIDGET, "1");
        }
        if(map.get(SysConfig.SYS_READER_WIDGET) == null){
            map.put(SysConfig.SYS_READER_WIDGET, "1");
        }
        if(map.get(SysConfig.SYS_APPROVE_COMMENT)==null){
            map.put(SysConfig.SYS_APPROVE_COMMENT, "1");
        }
        if(map.get(SysConfig.SYS_DELETE_OFFICIAL)==null){
            map.put(SysConfig.SYS_DELETE_OFFICIAL, "1");
        }
        if(map.get(SysConfig.NOTICE_UPDATE_PASSWORD)==null){
            map.put(SysConfig.NOTICE_UPDATE_PASSWORD, "no");
        }
        
        //收文登记默认操作：转领导批阅，转收文分发
        if(map.get(SysConfig.DOM_GATHER_REGISTER) == null){
            map.put(SysConfig.DOM_GATHER_REGISTER, "转领导批阅,转收文分发");
        }
        //领导批阅默认操作:转领导批阅，转收文分发
        if(map.get(SysConfig.DOM_GATHER_APPROVE) == null){
            map.put(SysConfig.DOM_GATHER_APPROVE, "转领导批阅,转收文分发");
        }
        //是否强制签阅读,默认强制签阅，默认值1，非强制签约2
        if(map.get(SysConfig.FORCE_READ) == null){
            map.put(SysConfig.FORCE_READ, "1");
        }
        //归档设置,默认为1手动归档；2自动归档
        if(map.get(SysConfig.DOM_GATHER_ZIP) == null){
            map.put(SysConfig.DOM_GATHER_ZIP, "1");
        }
        
        //发文拟稿默认操作：转核稿
        if(map.get(SysConfig.DOM_DISPATCH_NIGAO) == null){
            map.put(SysConfig.DOM_DISPATCH_NIGAO, "转核稿");
        }
        //发文核稿默认操作：转核稿，转盖章,转发文分发
        if(map.get(SysConfig.DOM_DISPATCH_HEGAO) == null){
            map.put(SysConfig.DOM_DISPATCH_HEGAO, "转核稿,转盖章,转发文分发");
        }
        //套红盖章默认操作：转核稿，转发文分发
        if(map.get(SysConfig.DOM_DISPATCH_RED) == null){
            map.put(SysConfig.DOM_DISPATCH_RED, "转核稿,转发文分发");
        }
        //归档,默认值是1，手动归档；2自动归档
        if(map.get(SysConfig.DOM_DISPATCH_ZIP) == null){
            map.put(SysConfig.DOM_DISPATCH_ZIP, "1");
        }
        
		return map;
	}

	public void saveOrUpdateSysConfig(Map<String, String> map) {
		// TODO Auto-generated method stub
		sysConfigDao.saveSysConfig(map);
	}

	public List<SysConfig> getSysConfigs() {
		// TODO Auto-generated method stub
		return sysConfigDao.findAll();
	}

}
