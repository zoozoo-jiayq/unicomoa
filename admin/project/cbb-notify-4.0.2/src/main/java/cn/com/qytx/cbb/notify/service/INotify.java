package cn.com.qytx.cbb.notify.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.com.qytx.cbb.notify.domain.Notify;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.Pageable;
import cn.com.qytx.platform.base.service.BaseService;
import cn.com.qytx.platform.org.domain.UserInfo;

public interface INotify extends BaseService<Notify>{
	
	
	/**
	 * 当前人公告列表
	 * @param pageable 分页对象
	 * @param userId   用户id
	 * @param notifyType 类型
	 * @param searchWord 查询关键字
	 * @param columnId  模块id
	 * @return
	 */
	public Page<Notify> viewList(Pageable pageable,Integer userId, int notifyType, String searchWord,Integer columnId,Integer isShowOut);
	/**
	 * 当前人公告列表
	 * @param pageable 分页对象
	 * @param userId 用户id
	 * @param notifyType 类型
	 * @param searchWord 查询关键字
	 * @param columnId 模块id
	 * @return
	 */
	public Page<Notify> approveList(Pageable pageable,Integer userId, Integer notifyType, String subject,Date beginDate, Date endDate,Integer columnId,Integer status);
	/**
	 * 查看当前登录人未读的公告个数
	 * @param userId
	 * @param columnId 模块id
	 * @return
	 */
	public Integer countOfNotReadNotify(Integer userId,Integer columnId);
	/**
	 * 通知公告管理页面
	 * @param pageable 分页对象
	 * @param notifyType 类型
	 * @param subject 主题
	 * @param beginDate 开始时间
	 * @param endDate 结束时间
	 * @param userInfo 用户
	 * @param columnId 模块id
	 * @return
	 */
	public Page<Notify> list(Pageable pageable, Integer notifyType, String subject, Date beginDate, Date endDate,UserInfo userInfo,Integer columnId,Integer status);
	
	/**
	 * 保存
	 * @param notify
	 */
	public void save(Notify notify);
	/**
	 * 删除多条记录
	 * @param ids
	 */
	public void delByIds(String ids);
	/**
	 * 设置置顶
	 * @param ids
	 * @param isTop
	 */
	public void updateTop(String ids, Integer isTop);
	/**
	 * 更新实体类
	 * @param notify
	 */
	public void update(Notify notify);
	/**
	 * 撤销
	 * @param id
	 */
	public void draw(Integer id);
	/**
	 * 终止该公告
	 * @param id
	 */
	public void stop(Integer id);
	/**
	 * 生效公告
	 * @param id 公告id
	 * @param startDateStr 开始时 间
	 * @param endDateStr 结束时间
	 */
	public void effect(Integer id, String startDateStr, String endDateStr);
	
	/**
	 * 
	 * 功能：部门专栏列表
	 * @param pageable 分页对象
	 * @param userId   用户id
	 * @param notifyType 类型
	 * @param searchWord 查询关键字
	 * @param columnId 模块id
	 * @param groupId
	 * @return
	 */
	@Deprecated
	public Page<Map<String,Object>> clmViewList(Pageable pageable,Integer userId, int notifyType, String searchWord,Integer columnId,String groupId);
	/**
	 * 功能：审批
	 * @param notify 公告
	 * @param userInfo 用户
	 */
	public void approveNotify(Notify notify,UserInfo userInfo);
	
	/**
	 * 功能：发送提醒
	 * @param notify
	 * @return
	 */
	public String sendReminder(Notify notify);
	
	
	/**
	 * 功能：查询最新发布的5条数据
	 * @param typeId
	 * @return
	 */
	public List<Notify> getTopFiveNotifys(Integer typeId);
	
	/**
	 * 公告阅读情况详细信息
	 * @param notifyId
	 * @return
	 */
	public Map<String,Object> viewNotify(Integer notifyId);
}
