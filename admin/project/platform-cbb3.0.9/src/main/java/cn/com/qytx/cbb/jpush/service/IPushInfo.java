package cn.com.qytx.cbb.jpush.service;

import java.util.List;

import cn.com.qytx.cbb.jpush.domain.PushInfo;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.Pageable;
import cn.com.qytx.platform.base.service.BaseService;

/**
 * 
 * <br/>功能: 推送接口
 * <br/>版本: 1.0
 * <br/>开发人员: REN
 * <br/>创建日期: 2013-11-21
 * <br/>修改日期: 2013-11-21
 * <br/>修改列表:
 */
public interface IPushInfo extends BaseService<PushInfo>{
	/**
	 * 
	 * 功能：添加发布
	 * @param pushInfo
	 */
//	public int addPushInfo(final PushInfo pushInfo);
	/**
	 * 
	 * 功能：编辑发布
	 * @param pushInfo
	 */
//	public int editPushInfo(final PushInfo pushInfo);
	/**
	 * 
	 * 功能：根据条件得到分页信息
	 * @param page
	 * @param userId
	 * @param subject
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public Page<PushInfo> findPage(Pageable pageable,Integer userId,String subject,String startTime,String endTime);
	/**
	 * 功能：获取给登陆人发送的所有活动推荐
	 * @param userId 客户经理id
	 * @param companyId 公司id
	 * @return
	 */
	public List<PushInfo> findPushsByUserId(Integer userId);
    /**
     * 功能：根据用户id获取推送信息
     * @param userId
     * @return
     */
    public List<PushInfo> findPushsByUserId(Integer userId,Integer pushType);
	/**
	 * 通过活动id获得活动信息
	 * @param pushId
	 * @return
	 */
//	public PushInfo findById(Integer pushId);
	/**
	 * 通过活动id删除活动信息
	 * @param pushId
	 * @return
	 */
	public int deletePushs(String ids);
    /**
     * 功能：得到下一次推送
     * @param pushId
     * @return
     */
    public PushInfo loadNextPush(Integer pushId);
}
