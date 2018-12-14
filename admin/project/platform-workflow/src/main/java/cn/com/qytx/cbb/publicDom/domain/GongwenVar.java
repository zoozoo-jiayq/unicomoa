package cn.com.qytx.cbb.publicDom.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import cn.com.qytx.platform.base.domain.BaseDomain;

/**
 * 功能：
 * 版本: 1.0
 * 开发人员：贾永强
 * 创建日期: 下午4:21:46 
 * 修改日期：下午4:21:46 
 * 修改列表：
 */
@Entity
@Table(name="tb_gongwen_var")
public class GongwenVar extends BaseDomain{

	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer 	id;
	
	@Column(name="title",length=100)
	private String		title;
	
	@Column(name="wenhao",length=100)
	private String		wenhao;
	
	@Column(name="fromgroup",length=100)
	private String		fromGroup;
	
	@Column(name="receivergroup",length=100)
	private String		receiverGroup;
	
	@Column(name="gongwentypename",length=100)
	private String		gongwenTypeName;
	
	@Column(name="miji",length=50)
	private String		miji;
	
	@Column(name="huanji",length=50)
	private String		huanji;
	
	@Column(name="gathersource",length=50)
	private String		gatherSource;
	
	@Column(name="instanceid",length=50)
	private String		instanceId;
	
	@Column(name="state",length=50)
	private	String		state;
	
	@Column(name="creater",length=50)
	private String		creater;
	
	@Column(name="creater_id")
	private Integer createrId;
	
	@Column(name="createtime")
	private Date	createTime;
	@Column(name="endtime")
	private Date	endTime;
	
	@Column(name="forkgroup_id")
	private Integer forkGroupId;
	
	@Column(name="signimg",length=1000)
	private String signImg;
	
	public String getSignImg() {
		return signImg;
	}
	public void setSignImg(String signImg) {
		this.signImg = signImg;
	}
	public Integer getForkGroupId() {
		return forkGroupId;
	}
	public void setForkGroupId(Integer forkGroupId) {
		this.forkGroupId = forkGroupId;
	}
	public void setId(Integer id) {
		this.id = id;
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
	public void setState(String state) {
		this.state = state;
	}
	public String getCreater() {
		return creater;
	}
	public void setCreater(String creater) {
		this.creater = creater;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public Integer getId() {
		return id;
	}
	public Integer getCreaterId() {
		return createrId;
	}
	public void setCreaterId(Integer createrId) {
		this.createrId = createrId;
	}
	
}
