package cn.com.qytx.platform.serviceAdapter.core.util;

import cn.com.qytx.platform.serviceAdapter.core.Constant;
import cn.com.qytx.platform.serviceAdapter.core.base.RetInfo;

/**
 * @ClassName:     CommonUtil.java
 * @Description:   工具类
 * 
 * @author         wangxj
 * @version        V1.0  
 * @Date           2015-6-1 下午02:29:18 
 */
public class CommonUtil {
	/** 
	 * @Title:        initErrorRetInfo 
	 * @Description:  初始化公共返回类信息---失败场景
	 * @param:        @return    
	 * @return:       RetInfo    
	 * @throws 
	 * @author        wangxj
	 * @Date          2015-6-1 下午03:52:15 
	 */
	public static RetInfo initErrorRetInfo(){
		RetInfo retInfo = new RetInfo();
		retInfo.setRetResult(Constant.RETINFO_FAILURE_RESULT);
		retInfo.setRetMsg(Constant.RETINFO_FAILURE_RETMSG);
		return retInfo;
	}
	
	/** 
	 * @Title:        initSuccessRetInfo 
	 * @Description:  初始化公共返回类信息---成功场景
	 * @param:        @return    
	 * @return:       RetInfo    
	 * @throws 
	 * @author        wangxj
	 * @Date          2015-6-1 下午03:52:47 
	 */
	public static RetInfo initSuccessRetInfo(){
		RetInfo retInfo = new RetInfo();
		retInfo.setRetResult(Constant.RETINFO_SUCCESS_RESULT);
		retInfo.setRetMsg(Constant.RETINFO_SUCCESS_RETMSG);
		return retInfo;
	}
}
