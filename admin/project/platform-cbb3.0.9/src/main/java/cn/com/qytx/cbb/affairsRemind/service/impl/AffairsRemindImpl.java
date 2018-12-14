package cn.com.qytx.cbb.affairsRemind.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.qytx.cbb.affairsRemind.dao.AffairsRemindDao;
import cn.com.qytx.cbb.affairsRemind.domain.AffairsRemind;
import cn.com.qytx.cbb.affairsRemind.service.IAffairsRemind;
import cn.com.qytx.platform.base.service.impl.BaseServiceImpl;

/**
 * 功能: 设置重复提醒
 * 版本: 1.0
 * 开发人员: zyf
 * 创建日期: 2014年12月2日
 * 修改日期: 2014年12月2日
 * 修改列表:
 */
@Service
@Transactional
public class AffairsRemindImpl extends BaseServiceImpl<AffairsRemind> implements IAffairsRemind {

	@Autowired
	AffairsRemindDao affairsRemindDao;
	
	/**
	 * 功能：获得提醒重复设置
	 * @return
	 */
	public AffairsRemind getRemind(){
		return affairsRemindDao.getRemind();
	}
}
