package cn.com.qytx.cbb.affairsRemind.dao;

import java.io.Serializable;

import org.springframework.stereotype.Component;

import cn.com.qytx.cbb.affairsRemind.domain.AffairsRemind;
import cn.com.qytx.platform.base.dao.BaseDao;

/**
 * 功能: 设置重复提醒
 * 版本: 1.0
 * 开发人员: zyf
 * 创建日期: 2014年12月2日
 * 修改日期: 2014年12月2日
 * 修改列表:
 */
@Component
public class AffairsRemindDao extends BaseDao<AffairsRemind, Serializable> {
	
	/**
	 * 功能：获得提醒重复设置
	 * @return
	 */
	public AffairsRemind getRemind(){
		return this.findOne(" 1=? ",1);
	}
}
