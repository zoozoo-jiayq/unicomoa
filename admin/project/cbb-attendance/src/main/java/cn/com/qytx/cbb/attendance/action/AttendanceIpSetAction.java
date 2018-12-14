package cn.com.qytx.cbb.attendance.action;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import cn.com.qytx.cbb.attendance.domain.AttendanceIpSet;
import cn.com.qytx.cbb.attendance.service.IAttendanceIpSet;
import cn.com.qytx.platform.base.action.BaseActionSupport;
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.org.service.IUser;

import com.google.gson.Gson;

/**
 * 功能: 考勤-IP设置 action 版本: 1.0 开发人员: 陈秋利 创建日期: 2014-7-8
 */
public class AttendanceIpSetAction extends BaseActionSupport {
	/** 序列号 */
	private static final long serialVersionUID = 2314184560157105391L;
	/** 日志类 */
	private static final Logger logger = Logger
			.getLogger(AttendanceIpSetAction.class);

	/** 考勤-IP设置 service */
	@Resource(name = "attendanceIpSetService")
	IAttendanceIpSet attendanceIpSet;
	/** 引入用户接口 */
	@Resource(name = "userService")
	private IUser userService;
	/** 考勤ip段ID */
	private Integer ipSetId;
	/** 考勤Ip串 */
	private String ipStr;
	private String beginIp;
	private String endIp;
	/** ip实体 */
	private AttendanceIpSet aSet;

	/*
	 * 获取IP列表
	 */
	public String getIPList() {

		List<AttendanceIpSet> aisList = this.attendanceIpSet.findAll();
		StringBuffer sb = new StringBuffer();
		if (aisList != null && aisList.size() > 0) {
			for (int i = 0; i < aisList.size(); i++) {
				AttendanceIpSet ais = aisList.get(i);
				Integer userId = ais.getCreateUserId();
				String creater = "";
				List<UserInfo> uiList = this.userService.findUsersByIds(userId
						+ "");
				if (uiList != null && uiList.size() > 0) {
					UserInfo ui = uiList.get(0);
					creater = ui.getUserName();
				}
				Timestamp ts = ais.getLastUpdateTime();
				String createTime = ts.toString();

				if (i % 2 == 0) {
					sb.append("<tr class=\"odd\">");
				} else {
					sb.append("<tr class=\"even\">");
				}
				sb.append("<td>" + (i + 1) + "</td> ");
				sb.append("<td>" + ais.getBeginIp() + "</td>");
				sb.append("<td>" + ais.getEndIp() + "</td>");
				sb.append("<td>" + creater + "</td>");
				sb.append("<td>"
						+ createTime.substring(0, createTime.lastIndexOf(":"))
						+ "</td>");
				sb.append("<td class=\"tdCenter\"><a style=\"cursor:pointer\" onclick=\"updateIp("
						+ ais.getIpSetId()
						+ ");\">修改</a><a  style=\"cursor:pointer\" onclick=\"deleteIp("
						+ ais.getIpSetId() + ")\">删除</a></td>");
				sb.append("</tr>");
			}
		}else{
			sb.append("<tr class=\"odd\">");
			sb.append("<td valign=\"top\" colspan=\"6\" class=\"dataTables_empty\">没有检索到数据</td>");
			sb.append("</tr>");
		}
		this.getRequest().setAttribute("ipStr", sb.toString());
		return "success";
	}

	/**
	 * 添加Ip段
	 * 
	 * @return
	 */
	public String addIp() {
		UserInfo userInfo = this.getLoginUser();
		if (userInfo != null) {
			AttendanceIpSet ais = new AttendanceIpSet();
			ais.setBeginIp(this.beginIp);
			ais.setEndIp(this.endIp);
			ais.setCreateTime(new Timestamp(System.currentTimeMillis()));
			ais.setCreateUserId(userInfo.getUserId());
			ais.setIsDelete(0);
			ais.setCompanyId(userInfo.getCompanyId());
			ais.setLastUpdateTime(new Timestamp(System.currentTimeMillis()));
			ais.setLastUpdateUserId(userInfo.getUserId());
			this.attendanceIpSet.saveOrUpdate(ais);
			ajax("");
		} else {
			ajax("1");
		}
		return null;
	}

	/*
	 * 获取考勤设置信息
	 */
	public String preUpdateIp() {
		UserInfo userInfo = this.getLoginUser();
		if (userInfo != null) {
			if (this.ipSetId != null) {
				AttendanceIpSet ais = this.attendanceIpSet.findOne(ipSetId);
				Gson gson = new Gson();
				String gsonStr = gson.toJson(ais);
				ajax(gsonStr);
			} else {
				ajax("1");
			}
		} else {
			ajax("1");
		}
		return null;
	}

	/**
	 * 编辑Ip段
	 * 
	 * @return
	 */
	public String updateIp() {
		UserInfo userInfo = this.getLoginUser();
		if (userInfo != null) {
			if (this.ipSetId != null) {
				AttendanceIpSet tempAis = this.attendanceIpSet
						.findOne(this.ipSetId);
				tempAis.setBeginIp(this.beginIp);
				tempAis.setEndIp(this.endIp);
				tempAis.setLastUpdateTime(new Timestamp(System
						.currentTimeMillis()));
				tempAis.setLastUpdateUserId(userInfo.getUserId());
				this.attendanceIpSet.saveOrUpdate(tempAis);
				ajax("");
			} else {
				ajax("1");
			}
		} else {
			ajax("1");
		}
		return null;
	}

	/**
	 * 删除IP段
	 * @return
	 */
	public String deleteIp() {
		UserInfo userInfo = this.getLoginUser();
		if (userInfo != null) {
			if (this.ipSetId != null) {
				//this.attendanceIpSet.delete(ipSetId, true);
				this.attendanceIpSet.deleteAttendanceIpById(ipSetId);
				ajax("");
			} else {
				ajax("1");
			}
		} else {
			ajax("1");
		}
		return null;
	}

	public String getIpStr() {
		return ipStr;
	}

	public void setIpStr(String ipStr) {
		this.ipStr = ipStr;
	}

	public Integer getIpSetId() {
		return ipSetId;
	}

	public void setIpSetId(Integer ipSetId) {
		this.ipSetId = ipSetId;
	}

	public AttendanceIpSet getaSet() {
		return aSet;
	}

	public void setaSet(AttendanceIpSet aSet) {
		this.aSet = aSet;
	}

	public String getBeginIp() {
		return beginIp;
	}

	public void setBeginIp(String beginIp) {
		this.beginIp = beginIp;
	}

	public String getEndIp() {
		return endIp;
	}

	public void setEndIp(String endIp) {
		this.endIp = endIp;
	}

}
