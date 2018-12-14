package cn.com.qytx.cbb.publicDom.vo;

import java.sql.Timestamp;

/**
 * 功能：公文查询数据模型
 * 版本: 1.0
 * 开发人员：贾永强
 * 创建日期: 2013-4-16 上午10:22:00 
 * 修改日期：2013-4-16 上午10:22:00 
 * 修改列表：
 */
public class PublicDomView {

	private String		title;
	private String		wenhao;
	private String		fromGroup;
	private String		receiverGroup;
	private String		gongwenTypeName;
	private String		miji;
	private String		huanji;
	private String		gatherSource;
	private String		instanceId;
	private	String		state;
	private String		creater;
	private Timestamp	createTime;
	private Timestamp	endTime;
	private String		taskId;
	private String 		phone;
	//add by jiayq，上一步任务结束时间
	private Timestamp 	lastTimeStamp;
	
	public Timestamp getLastTimeStamp() {
		return lastTimeStamp;
	}
	public void setLastTimeStamp(Timestamp lastTimeStamp) {
		this.lastTimeStamp = lastTimeStamp;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getWenhao() {
		return wenhao;
	}
	public void setWenhao(String wenhao) {
		this.wenhao = wenhao;
	}
	public String getFromGroup() {
		return fromGroup;
	}
	public void setFromGroup(String fromGroup) {
		this.fromGroup = fromGroup;
	}
	public String getReceiverGroup() {
		return receiverGroup;
	}
	public void setReceiverGroup(String receiverGroup) {
		this.receiverGroup = receiverGroup;
	}
	public String getGongwenTypeName() {
		return gongwenTypeName;
	}
	public void setGongwenTypeName(String gongwenTypeName) {
		this.gongwenTypeName = gongwenTypeName;
	}
	public String getMiji() {
		return miji;
	}
	public void setMiji(String miji) {
		this.miji = miji;
	}
	public String getHuanji() {
		return huanji;
	}
	public void setHuanji(String huanji) {
		this.huanji = huanji;
	}
	public String getGatherSource() {
		return gatherSource;
	}
	public void setGatherSource(String gatherSource) {
		this.gatherSource = gatherSource;
	}
	public String getInstanceId() {
		return instanceId;
	}
	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}
	public String getState() {
		return state;
	}
	
	public String getParsedState(){
		if(this.state == null){
			this.state = "已归档";
		}else if(this.state.equals("领导批阅")){
			this.state  = "批阅中";
		}else if(this.state.equals("收文分发")){
			this.state = "分发中";
		}else if(this.state.equals("收文阅读")){
			this.state = "传阅中";
		}else if(this.state.equals("发文核稿")){
			this.state = "核稿中";
		}else if(this.state.equals("套红盖章")){
			this.state = "盖章中";
		}else if(this.state.equals("发文")){
			this.state = "分发中";
		}else if(this.state.equals("归档")){
			this.state = "待归档";
		}
		return this.state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCreater() {
		return creater;
	}
	public void setCreater(String creater) {
		this.creater = creater;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	public Timestamp getEndTime() {
		return endTime;
	}
	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}
}
