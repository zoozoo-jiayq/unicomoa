/**
 * 
 */
package cn.com.qytx.cbb.jbpmApp.domain;

import cn.com.qytx.platform.base.domain.BaseDomain;

/**
 * 功能: 考勤详情Vo
 * 版本: 1.0
 * 开发人员: 王刚
 * 创建日期: 2017年4月13日
 * 修改日期: 2017年4月13日
 * 修改列表: 
 */
public class AttenceVo extends BaseDomain {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String userName;
	
	private String groupName;
	
	private Integer groupId;
	/**
	 * 是否到岗（0 到岗 1未到岗）
	 */
	private Integer state;
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public Integer getGroupId() {
		return groupId;
	}
	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}

	
}
