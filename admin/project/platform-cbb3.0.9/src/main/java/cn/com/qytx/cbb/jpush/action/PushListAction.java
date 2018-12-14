package cn.com.qytx.cbb.jpush.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import cn.com.qytx.cbb.jpush.domain.PushInfo;
import cn.com.qytx.cbb.jpush.domain.PushUser;
import cn.com.qytx.cbb.jpush.service.IPushInfo;
import cn.com.qytx.cbb.jpush.service.IPushUser;
import cn.com.qytx.platform.base.action.BaseActionSupport;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.org.service.IUser;
import cn.com.qytx.platform.utils.datetime.DateTimeUtil;

import com.google.gson.Gson;
/**
 * 功能:推送记录 列表查询
 * 版本: 1.0
 * 开发人员: zhangjingjing
 * 创建日期: 2014-6-24
 * 修改日期: 2014-6-24
 * 修改列表:
 */
public class PushListAction extends BaseActionSupport {
	private static final long serialVersionUID = 1L;
	@Resource(name = "pushInfoService")
	private IPushInfo pushInfoService;
	@Resource(name = "userService")
	private IUser userService;
	@Resource
	private IPushUser pushUserServiece;

	private String subject;  // 主题
	private String startTime;// 开始时间
	private String endTime;  // 结束时间
	private PushInfo push;
	private String ids;      // 删除的pushid字符串，以逗号分隔
	/**
	 * 功能：查询活动推荐列表
	 * @return
	 */
	public String findPushList() {
		try {
			Object userObject = getSession().getAttribute("adminUser");
			if (userObject != null) {
				UserInfo user = (UserInfo) userObject;
				// pageInfo = new Page<PushInfo>();
				//int pageNum = iDisplayStart / iDisplayLength + 1;
				// pageInfo.setPageNo(pageNum);
				Page<PushInfo> page = pushInfoService.findPage(
						getPageable(), user.getUserId(), subject, startTime, endTime);
				List<PushInfo> pushList = page.getContent();
				List<Map> list = new ArrayList<Map>();
			//	Map<Integer, String> companySerMap = userService.getAllUserName();
				Map<String, Object> map = null;
				int i = getPageable().getPageNumber() * this.getIDisplayLength() + 1;
				for (PushInfo push : pushList) {
					
					map = new HashMap<String, Object>();
					map.put("name", push.getUserName());
					map.put("num", i++);// 序号
					map.put("id", push.getPushId());// id
					map.put("subject", push.getSubject());// 主题
					map.put("content", push.getShowContent() == null ? "": push.getShowContent());// 内容
					String pushTime=DateTimeUtil.timestampToString(push.getPushTime(), "yyyy-MM-dd HH:mm:ss");
					map.put("pushTime", pushTime);// 发布时间
					list.add(map);
				}
				ajaxPage(page,list);
			}
		} catch (Exception ex) {
		    LOGGER.error(ex.getMessage());
			return ERROR;
		}
		return null;
	}

	/**
	 * 删除推送
	 */
	public String deletePush() {
		try {
			// 得到登陆用户
			Object userObject = this.getSession().getAttribute("adminUser");
			if (userObject != null) {
				int res = pushInfoService.deletePushs(ids);
				ajax(res);
			} else {
				ajax("0");
			}
		} catch (Exception e) {
		    LOGGER.error(e.getMessage());
			try {
				ajax("-1");
			} catch (Exception e1) {
			    LOGGER.error(e1.getMessage());
			}
			return null;
		}
		return null;
	}

	/**
	 * 
	 * 功能：查询我的活动推荐列表（手机端使用）
	 * 
	 * @return
	 */
	public String findMyPush() {
		try {
			int userId;
			//int companyId;
			HttpServletRequest request = this.getRequest();
			if (request.getParameter("userId") == null) {
				ajax("103||参数异常");
				return null;
			}
			userId = Integer.parseInt(request.getParameter("userId"));
			List<PushInfo> pushList = new ArrayList<PushInfo>();
			pushList= pushInfoService.findPushsByUserId(userId);
			if (pushList != null && pushList.size() > 0) {
				SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
				for (int i = 0; i < pushList.size(); i++) {
					PushInfo push = pushList.get(i);
					push.setPushTimeStr(sdf.format(push.getPushTime()));
					push.setIsRead(pushUserServiece.findByPushIdAndUserId(push.getPushId(), userId).getIsRead());
				}
				Gson gson = new Gson();
				String jsons = gson.toJson(pushList);
				ajax("100||" + jsons);
			} else {
				ajax("101||暂无数据");
			}
		} catch (Exception ex) {
		    LOGGER.error(ex.getMessage());
			try {
				ajax("102||操作异常");
			} catch (Exception e) {
			    LOGGER.error(e.getMessage());
			}
			return null;
		}
		return null;
	}

	/**
	 * 得到活动推荐详细信息
	 */
	public String findPushInfo() {
		try {
			int pushId;
			int userId;
			int pushCount = 0;
			int pushSuccessCount = 0;
			HttpServletRequest request = this.getRequest();
			if (request.getParameter("id") == null) {
				ajax("103||参数异常");
				return null;
			}
			// logger.info("得到推送活动详细，手机端请求参数：id="+request.getParameter("id"));
			pushId = Integer.parseInt(request.getParameter("id"));
			if (request.getParameter("userId")!=null) {
				userId = Integer.parseInt(request.getParameter("userId"));
				PushUser pushUser=pushUserServiece.findByPushIdAndUserId(pushId, userId);
				if (null!=pushUser) {
					pushUser.setIsRead(1);
					pushUserServiece.saveOrUpdate(pushUser);
				}
			}
			push = pushInfoService.findOne(pushId);
			
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
			push.setPushTimeStr(sdf.format(push.getPushTime()));
			// 推荐数量 推荐成功数量
			//if (request.getParameter("userId") != null&& request.getParameter("companyId") != null) {
				//Integer userId = Integer.parseInt(request.getParameter("userId"));
				// pushCount = recommendService.getRecommendCount(userId,
				// pushId);
				// pushSuccessCount =
				// recommendService.getRecommendSuccess(userId, pushId);
		//	}
			push.setPushCount(pushCount);
			push.setPushSuccessCount(pushSuccessCount);
		} catch (Exception ex) {
		    LOGGER.error(ex.getMessage());
			try {
				ajax("102||操作异常");
			} catch (Exception e) {
			    LOGGER.error(e.getMessage());
			}
			return null;
		}
		return SUCCESS;
	}
	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public PushInfo getPush() {
		return push;
	}

	public void setPush(PushInfo push) {
		this.push = push;
	}

	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}


}
