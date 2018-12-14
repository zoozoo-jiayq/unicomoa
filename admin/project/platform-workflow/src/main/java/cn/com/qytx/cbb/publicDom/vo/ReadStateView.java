package cn.com.qytx.cbb.publicDom.vo;


/**
 * 功能：
 * 版本: 1.0
 * 开发人员：贾永强
 * 创建日期: 2013-4-24 下午3:02:05 
 * 修改日期：2013-4-24 下午3:02:05 
 * 修改列表：
 */
public class ReadStateView {

	private String userName;
	private String state;
	private String groupName;
	private String role;
	private String readTime;
	
	public String getReadTime() {
		return readTime;
	}
	public void setReadTime(String readTime) {
		this.readTime = readTime;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
}
