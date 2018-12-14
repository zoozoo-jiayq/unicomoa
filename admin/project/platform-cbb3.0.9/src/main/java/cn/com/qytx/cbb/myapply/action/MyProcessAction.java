package cn.com.qytx.cbb.myapply.action;

import org.springframework.beans.factory.annotation.Autowired;

import cn.com.qytx.cbb.myapply.domain.MyProcessed;
import cn.com.qytx.cbb.myapply.service.IMyProcessed;
import cn.com.qytx.platform.base.action.BaseActionSupport;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.Sort;
import cn.com.qytx.platform.base.query.Sort.Direction;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class MyProcessAction extends BaseActionSupport{
	/**
	 * 描述含义
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	private IMyProcessed myProcessService;
	
	private int userId;
	
	private String moduleCode;
	/**
	 * 功能：手机端接口，查询我审批的
	 * @return
	 */
	public String myProcess(){
		Sort sort = new Sort(new Sort.Order(Direction.DESC,"endTime"));
		Page<MyProcessed> page = myProcessService.findListByUserId(getPageable(sort),userId,moduleCode);
		GsonBuilder gsonBuilder = new GsonBuilder();
		Gson gson = gsonBuilder.serializeNulls().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		ajax("100||"+gson.toJson(page.getContent()));
		return null;
	}


	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}


	public String getModuleCode() {
		return moduleCode;
	}


	public void setModuleCode(String moduleCode) {
		this.moduleCode = moduleCode;
	}
	
	
}
