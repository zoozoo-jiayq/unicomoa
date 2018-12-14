package cn.com.qytx.cbb.attendance.domain;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.transaction.annotation.Transactional;

import cn.com.qytx.platform.base.domain.BaseDomain;
/**
 * 功能: 考勤记录 实体类
 * 版本: 1.0
 * 开发人员: 陈秋利
 * 创建日期: 2014-7-8
 */
@Entity
@Table(name="tb_attendance")
public class Attendance extends BaseDomain  implements java.io.Serializable {
	 private static final long serialVersionUID = 1L;
	 @Id
	 @GeneratedValue(strategy = GenerationType.AUTO)
	 @Column(name="att_id", unique=true,nullable=false)
	 private Integer attId; //考勤 
	 
	 @Column(name="create_user_id")
	 private Integer createUserId; //创建人
	   
	 @Column(name="create_time")
	 private Timestamp createTime;//创建时间
	 
	 @Column(name="position")
	 private String position; //位置或IP
	 
	 @Column(name="longitude")
	 private String longitude; //经度
	 
	 @Column(name="latitude")
	 private String latitude; //纬度
	 
	 @Column(name="att_Source")
	 private Integer attSource; //打卡来源 1 pc端 2 手机端
	 
	 @Column(name="att_type")
	 private Integer attType;//打卡类型  10上午上班签到   11下午上班签到   20上午下班签退 21下午下班签退
	 @Column(name="att_state")
	 private Integer attState;//打卡状态 1正常 2迟到 3早退 4外勤(新版不适用) 7加班
	 
	 @Column(name="out_of_range")
	 private Integer outOfRange;//0正常 1外勤
	 
	 @Column(name="memo")
	 private String memo;//备注

	 @Column(name="setting_time")
	 private Timestamp settingTime;
	 /**
	  * 大屏展示列表打卡人员名字
	  */
	 @Transient
	 private String userName;
	 /**
	  * 大屏展示列表头像路径
	  */
	 @Transient
	 private String photoPath;  
	 
	 @Transient
	 private Integer carType;
	 
	/**
	 * @return the attId
	 */
	public Integer getAttId() {
		return attId;
	}

	public String getPhotoPath() {
		return photoPath;
	}

	public void setPhotoPath(String photoPath) {
		this.photoPath = photoPath;
	}

	/**
	 * @param attId the attId to set
	 */
	public void setAttId(Integer attId) {
		this.attId = attId;
	}

	/**
	 * @return the createUserId
	 */
	public Integer getCreateUserId() {
		return createUserId;
	}

	/**
	 * @param createUserId the createUserId to set
	 */
	public void setCreateUserId(Integer createUserId) {
		this.createUserId = createUserId;
	}

	/**
	 * @return the createTime
	 */
	public Timestamp getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return the position
	 */
	public String getPosition() {
		return position;
	}

	/**
	 * @param position the position to set
	 */
	public void setPosition(String position) {
		this.position = position;
	}

	/**
	 * @return the longitude
	 */
	public String getLongitude() {
		return longitude;
	}

	/**
	 * @param longitude the longitude to set
	 */
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	/**
	 * @return the latitude
	 */
	public String getLatitude() {
		return latitude;
	}

	/**
	 * @param latitude the latitude to set
	 */
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	/**
	 * @return the attSource
	 */
	public Integer getAttSource() {
		return attSource;
	}

	/**
	 * @param attSource the attSource to set
	 */
	public void setAttSource(Integer attSource) {
		this.attSource = attSource;
	}

	public Integer getAttType() {
		return attType;
	}

	public void setAttType(Integer attType) {
		this.attType = attType;
	}

	public Integer getAttState() {
		return attState;
	}

	public void setAttState(Integer attState) {
		this.attState = attState;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Timestamp getSettingTime() {
		return settingTime;
	}

	public void setSettingTime(Timestamp settingTime) {
		this.settingTime = settingTime;
	}

	public Integer getOutOfRange() {
		return outOfRange;
	}

	public void setOutOfRange(Integer outOfRange) {
		this.outOfRange = outOfRange;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getCarType() {
		return carType;
	}

	public void setCarType(Integer carType) {
		this.carType = carType;
	}
	
}
