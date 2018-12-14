package cn.com.qytx.cbb.version.domain;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="Version_info")
public class VersionInfo {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="vid")
	private Integer vid;
	@Column(name="Version_name",length=500)
	private String versionName;//版本
	@Column(name="Version_code")
	private Integer versionCode;//版本code
	@Column(name="Version_src",length=500)
	private String versionSrc;//版本地址
	@Column(name="Creat_time")
	private Timestamp creatTime;//创建时间
	@Column(name="Creat_userId")
	private Integer creatUserId;//创建人员
	@Column(name="Is_formal")
	private Integer isFormal;//是否是正式版本（0:是，1：否）
	@Column(name="Is_strong")
	private Integer isStrong;//是否为强制升级（0:否，1：是）
	@Column(name="Is_delete")
	private Integer isDelete;//是否删除（0：否，1：是）
	@Column(name="Version_content",length=2000)
	private String versionContent;//版本升级内容
	@Column(name="type")
	private Integer type;//版本类别 0表示安卓 1表示IOS
	public Integer getVid() {
		return vid;
	}

	public void setVid(Integer vid) {
		this.vid = vid;
	}

	public String getVersionName() {
		return versionName;
	}

	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}

	public Integer getVersionCode() {
		return versionCode;
	}

	public void setVersionCode(Integer versionCode) {
		this.versionCode = versionCode;
	}

	public String getVersionSrc() {
		return versionSrc;
	}

	public void setVersionSrc(String versionSrc) {
		this.versionSrc = versionSrc;
	}

	public Timestamp getCreatTime() {
		return creatTime;
	}

	public void setCreatTime(Timestamp creatTime) {
		this.creatTime = creatTime;
	}

	public Integer getCreatUserId() {
		return creatUserId;
	}

	public void setCreatUserId(Integer creatUserId) {
		this.creatUserId = creatUserId;
	}

	public Integer getIsFormal() {
		return isFormal;
	}

	public void setIsFormal(Integer isFormal) {
		this.isFormal = isFormal;
	}

	public Integer getIsStrong() {
		return isStrong;
	}

	public void setIsStrong(Integer isStrong) {
		this.isStrong = isStrong;
	}

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

	public String getVersionContent() {
		return versionContent;
	}

	public void setVersionContent(String versionContent) {
		this.versionContent = versionContent;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
	
}
