package cn.com.qytx.cbb.publicDom.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.qytx.cbb.publicDom.dao.GongwenVarDao;
import cn.com.qytx.cbb.publicDom.domain.GongwenVar;
import cn.com.qytx.cbb.publicDom.service.GongwenVarService;
import cn.com.qytx.platform.base.service.impl.BaseServiceImpl;

/**
 * 功能：
 * 版本: 1.0
 * 开发人员：贾永强
 * 创建日期: 下午4:21:46 
 * 修改日期：下午4:21:46 
 * 修改列表：
 */
@Service("gongwenVarService")
@Transactional
public class GongwenVarServiceImpl extends BaseServiceImpl<GongwenVar> implements GongwenVarService {

	@Resource(name="gongwenVarDao")
	private GongwenVarDao gongwenVarDao;
	

	@Override
	public GongwenVar findByInstanceId(String instanceId) {
		// TODO Auto-generated method stub
		return gongwenVarDao.findByInstanceId(instanceId);
	}


	@Override
	public Map<String, String> findCreaterInfoByInstanceId(String instanceId) {
		// TODO Auto-generated method stub
		Map<String,String> map = new HashMap<String, String>();
		GongwenVar var = gongwenVarDao.findByInstanceId(instanceId);
		Date d = var.getCreateTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String date = sdf.format(d);
		map.put("dataTime", date);
		String creater = var.getCreater();
		map.put("userName", creater);
		String groupName = var.getFromGroup();
		map.put("groupName", groupName);
		return map;
	}

}
