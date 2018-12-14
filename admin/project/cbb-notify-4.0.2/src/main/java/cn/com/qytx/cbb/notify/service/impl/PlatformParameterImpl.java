package cn.com.qytx.cbb.notify.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.qytx.cbb.notify.dao.PlatformParameterDao;
import cn.com.qytx.cbb.notify.domain.PlatformParameter;
import cn.com.qytx.cbb.notify.service.IPlatformParameter;
import cn.com.qytx.platform.base.service.impl.BaseServiceImpl;
import cn.com.qytx.platform.security.SystemContextHolder;

@Transactional
@Service("platformParameterService")
public class PlatformParameterImpl extends BaseServiceImpl<PlatformParameter> implements IPlatformParameter{
	
	@Resource(name="platformParameterDao")
	private PlatformParameterDao parmsDao;
	
	@Transactional(readOnly=true)
	public Object findEntityByInstenceId(int instentceid) {
		Object obj = parmsDao.findEntityByInstenceId(instentceid);
		if(obj == null){
			PlatformParameter param = new PlatformParameter();
			param.setCompanyId(SystemContextHolder.getSessionContext().getUser().getCompanyId());
			param.setIsDelete(0);
			param.setInstenceid(instentceid);
			param.setModuleName("通知公告");
			param.setParDescribe("通知公告");
			param.setParItems("cn.com.qytx.cbb.notify.vo.TbColumnSetting");
			param.setParValueColl("{\"isComment\":0,\"isAuditing\":\"0\",\"isSeeAttach\":0,\"isEdit\":1,\"showImage\":1,\"publishUserIds\":\"\",\"publishUserNames\":\"\",\"isSmipleText\":0,\"isDataFilter\":0}");
			parmsDao.saveOrUpdate(param);
			return parmsDao.findEntityByInstenceId(instentceid);
		}else{
			return obj;
		}
	}

	@Transactional(readOnly=true)
	public PlatformParameter findByInstenceId(int instentceid) {
		PlatformParameter param = parmsDao.findByInstenceId(instentceid);
		if(param==null){
			param = new PlatformParameter();
			param.setCompanyId(SystemContextHolder.getSessionContext().getUser().getCompanyId());
			param.setIsDelete(0);
			param.setInstenceid(instentceid);
			param.setModuleName("通知公告");
			param.setParDescribe("通知公告");
			param.setParItems("cn.com.qytx.cbb.notify.vo.TbColumnSetting");
			param.setParValueColl("{\"isComment\":0,\"isAuditing\":\"0\",\"isSeeAttach\":0,\"isEdit\":1,\"showImage\":1,\"publishUserIds\":\"\",\"publishUserNames\":\"\",\"isSmipleText\":0,\"isDataFilter\":0}");
			parmsDao.saveOrUpdate(param);
		}
		return param;
	}

}
