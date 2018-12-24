package com.unicomoa.unicomoa.base;

import java.util.HashMap;
import java.util.Map;

public interface Constant {

	public final Map<String,Integer> WORK_PLAN_TYPE = new HashMap<String,Integer>(){
		private static final long serialVersionUID = 1L;
	{
		this.put("sale", 1);
		this.put("visit", 2);
		this.put("other", 3);
	}};
	
	public final int WORK_PLAN_STATE_GOING = 1;
	public final int WORK_PLAN_STATE_END = 2;
}
