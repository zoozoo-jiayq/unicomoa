package cn.com.qytx.cbb.publicDom.mobile.vo;

/**
 * 功能：手机端公文列表展示对象
 * 版本: 1.0
 * 开发人员：贾永强
 * 创建日期: 下午4:09:32 
 * 修改日期：下午4:09:32 
 * 修改列表：
 */
public class MobilePublicDomView {
	private String instanceId;
	private String title;
	private String from;
	private String receiveTime;
	private String isSystem;
	private String gongwenTypeId;
	private String state;
	private String fromTag="登记";
	private long  readers = 0;//分发人数
	
	public long getReaders() {
		return readers;
	}
	public void setReaders(long readers) {
		this.readers = readers;
	}
	public String getFromTag() {
		return fromTag;
	}
	public void setFromTag(String fromTag) {
		this.fromTag = fromTag;
	}
	public String getInstanceId() {
		return instanceId;
	}
	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getReceiveTime() {
		return receiveTime;
	}
	public void setReceiveTime(String receiveTime) {
		this.receiveTime = receiveTime;
	}
	public String getIsSystem() {
		return isSystem;
	}
	public void setIsSystem(String isSystem) {
		this.isSystem = isSystem;
	}
	public String getGongwenTypeId() {
		return gongwenTypeId;
	}
	public void setGongwenTypeId(String gongwenTypeId) {
		this.gongwenTypeId = gongwenTypeId;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
}
