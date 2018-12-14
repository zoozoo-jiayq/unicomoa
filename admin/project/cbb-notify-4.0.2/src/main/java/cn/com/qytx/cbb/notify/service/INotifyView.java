package cn.com.qytx.cbb.notify.service;

import java.util.List;
import java.util.Map;

import cn.com.qytx.cbb.notify.domain.Notify;
import cn.com.qytx.cbb.notify.domain.NotifyView;
import cn.com.qytx.platform.base.service.BaseService;
import cn.com.qytx.platform.org.domain.UserInfo;

public interface INotifyView extends BaseService<NotifyView>{
	
	/**
	 * 查询当前用的是否查看当前公告 true 已查看， false 未查看
	 * @param createUserId
	 * @param notifyId
	 * @param readType
	 * @return
	 */
	public boolean getNotifyView(Integer createUserId,Integer notifyId);
	
	/**
	 * 通过通知公告Id，得到查看的总人数
	 * @param id
	 * @return
	 */
	public Integer countNotifyPeoples(Integer id);
	
	/**
	 * 增加浏览次数，更新通知公告浏览次数
	 * @param notifyView
	 * @param notify
	 */
	public void saveNV(NotifyView notifyView, Notify notify);
	
	/**
	 * 手机端发布范围
	 * @param notifyId
	 * @return
	 */
	public List<String[]> mobileView(Integer notifyId); 
	
	/**
	 * 手机端已读人数
	 * @param notifyId
	 * @return
	 */
	public List<NotifyView> mobileRead(Integer notifyId);
	/**
	 * 未读人数
	 * @param notifyId
	 * @return
	 */
	public List<UserInfo> mobileUnread(Integer notifyId);
	
	/**
	 * 未读人数、已读人数列表
	 * @param notifyId
	 * @return
	 */
	public List<Map<String,Object>> viewList(Integer notifyId);
	
	/**
	 * 获取公告的查看人数
	 * @param companyId
	 * @return
	 */
	public Map<Integer,Integer> getViewUserNumByNotigyId(String notifyIds,Integer userId);
	
}
