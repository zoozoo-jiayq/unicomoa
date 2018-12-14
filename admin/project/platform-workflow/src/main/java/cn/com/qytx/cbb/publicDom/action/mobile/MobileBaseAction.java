package cn.com.qytx.cbb.publicDom.action.mobile;

import javax.annotation.Resource;

import cn.com.qytx.platform.base.action.BaseActionSupport;
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.org.service.IUser;

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
	
	@Resource(name="userService")
	private IUser userService;
	
	String userId;
	
    /**
     * @throws Exception 
     * 功能：向手机端返回操作成功的信息
     * @param
     * @return
     * @throws   
     */
    public void ajaxSuccess(String datas) throws Exception{
        
    	String data = (datas==null?"":datas);
    	String result = SUCCESS_PREFIX+data;
    	ajax(result);
    }
    
    /**
     * 功能：向手机端返回操作异常的信息
     * @param
     * @return
     * @throws   
     */
    public void ajaxException(String datas) throws Exception{
    	String data = (datas==null?"":datas);
    	ajax(EXCEPTION_PREFIX+data);
    }
    

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public UserInfo getCurrentUser(){
    	return userService.findOne(Integer.parseInt(userId));
    }
	
}
