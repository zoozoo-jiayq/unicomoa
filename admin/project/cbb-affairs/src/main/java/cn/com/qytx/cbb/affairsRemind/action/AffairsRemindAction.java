package cn.com.qytx.cbb.affairsRemind.action;

import org.springframework.beans.factory.annotation.Autowired;

import cn.com.qytx.cbb.affairsRemind.domain.AffairsRemind;
import cn.com.qytx.cbb.affairsRemind.service.IAffairsRemind;
import cn.com.qytx.platform.base.action.BaseActionSupport;

/**
 * 功能: 设置重复提醒
 * 版本: 1.0
 * 开发人员: zyf
 * 创建日期: 2014年12月2日
 * 修改日期: 2014年12月2日
 * 修改列表:
 */
public class AffairsRemindAction extends BaseActionSupport {

	@Autowired
	IAffairsRemind affairsRemindService;
	
	private AffairsRemind affairsRemind;
	
	/**
	 * 功能：加载重复提醒设置
	 * @return
	 */
	public void load(){
		affairsRemind = affairsRemindService.getRemind();
		if(affairsRemind!=null){
			ajax(affairsRemind);
		}else{
			affairsRemind = new AffairsRemind();
			ajax(affairsRemind);
		}
	}

	public void save(){
		try{
			AffairsRemind affairsRemindOld = affairsRemindService.getRemind();
			if(affairsRemindOld!=null){
				affairsRemind.setId(affairsRemindOld.getId());
			}
			affairsRemind.setCompanyId(getLoginUser().getCompanyId());
			affairsRemindService.saveOrUpdate(affairsRemind);
			ajax(1);
		}catch(Exception e){
			ajax(0);
		}
	}
	
	
	public AffairsRemind getAffairsRemind() {
		return affairsRemind;
	}

	public void setAffairsRemind(AffairsRemind affairsRemind) {
		this.affairsRemind = affairsRemind;
	}
	
	
}
