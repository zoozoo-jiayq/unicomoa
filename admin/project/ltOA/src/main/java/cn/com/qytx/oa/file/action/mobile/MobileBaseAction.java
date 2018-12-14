package cn.com.qytx.oa.file.action.mobile;

import cn.com.qytx.platform.base.action.BaseActionSupport;

/**
 * 功能：公文手机接口基础类
 * 版本: 1.0
 * 开发人员：贾永强
 * 创建日期: 2013-4-16 下午5:52:55 
 * 修改日期：2013-4-16 下午5:52:55 
 * 修改列表：
 */
public class MobileBaseAction extends BaseActionSupport {
	
	//成功的前缀
	public static final String SUCCESS_PREFIX = "100||";
	//异常的前缀
	public static final String EXCEPTION_PREFIX = "101||";
	
	/**
	 * 描述含义
	 */
	private static final long serialVersionUID = 1L;
	
    
    /**
     * @throws Exception 
     * 功能：向手机端返回操作成功的信息
     * @param
     * @return
     * @throws   
     */
    public void ajaxSuccess(String data) throws Exception{
    	data = (data==null?"":data);
    	String result = SUCCESS_PREFIX+data;
    	ajax(result);
    }
    
    /**
     * 功能：向手机端返回操作异常的信息
     * @param
     * @return
     * @throws   
     */
    public void ajaxException(String data) throws Exception{
    	data = (data==null?"":data);
    	ajax(EXCEPTION_PREFIX+data);
    }
    


	
}
