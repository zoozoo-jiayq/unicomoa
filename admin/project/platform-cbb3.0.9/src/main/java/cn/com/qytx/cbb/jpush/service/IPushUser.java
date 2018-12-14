package cn.com.qytx.cbb.jpush.service;

import cn.com.qytx.cbb.jpush.domain.PushUser;
import cn.com.qytx.platform.base.service.BaseService;

/**
 * 
 * <br/>功能: 推送人员接口
 * <br/>版本: 1.0
 * <br/>开发人员: REN
 * <br/>创建日期: 2013-11-21
 * <br/>修改日期: 2013-11-21
 * <br/>修改列表:
 */
public interface IPushUser extends BaseService<PushUser>{
	/**
	 * 
	 * 功能：添加发布人
	 * @param pushId
	 * @param userIds
	 * @return
	 */
	public int addPushUser(Integer pushId,String userIds);
	/**
	 * 
	 * 功能：得到推荐人员
	 * @param pushId
	 * @return
	 */
	public String findUserNamesByPushId(Integer pushId);
	/**
	 * 
	 * 功能：得到推荐人员id
	 * @param pushId
	 * @return
	 */
	public String findUserIdsByPushId(Integer pushId);
	
	/**
	 * 功能：根据推送id和uerid获取pusher
	 * @return
	 */
	public PushUser findByPushIdAndUserId(Integer pushId,Integer userId);
    
}
