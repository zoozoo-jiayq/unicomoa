package cn.com.qytx.oa.email.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import cn.com.qytx.oa.email.service.IEmailBox;
import cn.com.qytx.platform.base.action.BaseActionSupport;
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.org.service.IUser;

/**
 * 
 * 功能:初始化用户的邮件箱，解决数据库人员导入时邮箱未初始化问题
 * 版本: 1.0
 * 开发人员: zyf
 * 创建日期: 2014年8月8日
 * 修改日期: 2014年8月8日
 * 修改列表:
 */
public class DefaultEmailAction extends BaseActionSupport {
	/**
	 * 描述含义
	 */
	private static final long serialVersionUID = -1266184029306392425L;

    /**
     * 邮箱
     */
    @Autowired
    IEmailBox emailBoxService;
    
    @Autowired
    IUser userService;
    
    public void defaultEmail(){
    	List<UserInfo> list = userService.findAll(" isDelete = 0");
    	if(list!=null&&list.size()>0){
    		for(UserInfo userInfo:list){
    			//初始化用户邮箱
                emailBoxService.saveDefaultAfterCheckExist(userInfo.getUserId(),getLoginUser());
    		}
    	}
    	ajax(1);
    }

}
