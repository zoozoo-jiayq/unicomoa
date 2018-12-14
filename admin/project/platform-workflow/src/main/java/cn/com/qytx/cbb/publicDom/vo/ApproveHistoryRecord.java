package cn.com.qytx.cbb.publicDom.vo;

/**
 * 功能：审批记录
 * 版本: 1.0
 * 开发人员：贾永强
 * 创建日期: 下午1:05:02 
 * 修改日期：下午1:05:02 
 * 修改列表：
 */
public class ApproveHistoryRecord {

	private String approveTime;
	private String option;
	private String userName;
	private String role;
	private String group;
	private String phone;
	
	private String content;//审批内容
	private String sign;//签名
	private Integer userId;
	private String photo;
	
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getApproveTime() {
		return approveTime;
	}
	public void setApproveTime(String approveTime) {
		this.approveTime = approveTime;
	}
	public String getOption() {
		return option;
	}
	public void setOption(String option) {
		this.option = option;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	

}
