package cn.com.qytx.cbb.publicDom.service.impl;

import java.util.HashMap;
import java.util.Map;

/**
 * 功能：公文各个菜单的URL映射
 * 版本: 1.0
 * 开发人员：贾永强
 * 创建日期: 上午9:44:59 
 * 修改日期：上午9:44:59 
 * 修改列表：
 */
public  class PublicDomMenuUrlMap {

	public final static Map<String,String> URLMAP = new HashMap<String,String>(){{
		
		//发文
		put("发文拟稿", "/dom/dispatch!toAdd.action");
		put("拟稿记录", "/dom/dispatch!dispatchList.action?menu=5");
		put("发文核稿", "/dom/dispatch!dispatchList.action?menu=6");
		put("套红盖章", "/dom/dispatch!dispatchList.action?menu=7");
		put("发文分发", "/dom/dispatch!dispatchList.action?menu=8");
		
		//收文
		put("收文登记","/dom/gather!toAdd.action");
		put("收文记录","/dom/gather!gatherList.action?menu=1");
		put("领导批阅","/dom/gather!gatherList.action?menu=2");
		put("收文分发","/dom/gather!gatherList.action?menu=3");
		put("收文阅读","/dom/gather!gatherList.action?menu=4");
	}};
}
