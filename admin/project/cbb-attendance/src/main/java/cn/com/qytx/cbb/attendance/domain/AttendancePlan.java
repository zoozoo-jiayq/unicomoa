package cn.com.qytx.cbb.attendance.domain;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import cn.com.qytx.platform.base.domain.BaseDomain;
import cn.com.qytx.platform.base.domain.DeleteState;

/**
 * 功能 考勤方案，考勤组设置 
 * <br/>版本  1.0
 * <br/>开发人员  zyf
 * <br/>创建日期  2016年7月19日
 * <br/>修改日期  2016年7月19日
 * <br/>修改列表
 */
@Table(name="tb_attendance_plan")
@Entity
public class AttendancePlan extends BaseDomain implements Serializable {

	 /**
	 * 描述含义
	 */
	private static final long serialVersionUID = 1L;
	
	 @Id
	 @GeneratedValue(strategy = GenerationType.AUTO)
	 @Column(name="id", unique=true,nullable=false)
	 private Integer id; //考勤
	 
	 @DeleteState
	 @Column(name="is_delete")
	 private Integer isDelete;
	 
	 //创建时间
	 @Column(name="create_time")
	 private Timestamp createTime;
	 
	 //创建人id
	 @Column(name="create_user_id")
	 private Integer createUserId;
	 
	 //考勤方案名称
	 @Column(name="subject")
	 private String subject;
	 
	 //考勤组成员
	 @Column(name="user_ids")
	 private String userIds;
	 
	 //通用上班时间
	 @Column(name="common_on")
	 private Timestamp commonOn;
	 
	 //通用上午下班时间
	 @Column(name="common_am_off")
	 private Timestamp commonAmOff;
	 //通用下午上班时间
	 @Column(name="common_pm_on")
	 private Timestamp commonPmOn;
	 
	 //通用下班时间
	 @Column(name="common_off")
	 private Timestamp commonOff;
	 
	 //周一是否需要打卡
	 @Column(name="mon_rest")
	 private Integer monRest;
	 
	 //周一上班时间
	 @Column(name="mon_on")
	 private Timestamp monOn;
	 
	 //周一下班时间
	 @Column(name="mon_off")
	 private Timestamp monOff;
	 
	 //周二是否需要打卡
	 @Column(name="tues_rest")
	 private Integer tuesRest;
	 
	 //周二上班时间
	 @Column(name="tues_on")
	 private Timestamp tuesOn;
	 
	 //周二下班时间
	 @Column(name="tues_off")
	 private Timestamp tuesOff;
	 
	 //周三是否需要打卡
	 @Column(name="wed_rest")
	 private Integer wedRest;
	 
	 //周三上班时间
	 @Column(name="wed_on")
	 private Timestamp wedOn;
	 
	 //周三下班时间
	 @Column(name="wed_off")
	 private Timestamp wedOff;
	 
	 //周四是否需要打卡
	 @Column(name="thur_rest")
	 private Integer thurRest;
	 
	 //周四上班时间
	 @Column(name="thur_on")
	 private Timestamp thurOn;
	 
	 //周四下班时间
	 @Column(name="thur_off")
	 private Timestamp thurOff;
	 
	 //周五是否需要打卡
	 @Column(name="fri_rest")
	 private Integer friRest;
	 
	 //周五上班时间
	 @Column(name="fri_on")
	 private Timestamp friOn;
	 
	 //周五下班时间
	 @Column(name="fri_off")
	 private Timestamp friOff;
	 
	 //周六是否需要打卡
	 @Column(name="sat_rest")
	 private Integer satRest;
	 
	 //周六上班时间
	 @Column(name="sat_on")
	 private Timestamp satOn;
	 
	 //周六下班时间
	 @Column(name="sat_off")
	 private Timestamp satOff;
	 
	 //周日是否需要打卡
	 @Column(name="sun_rest")
	 private Integer sunRest;
	 
	 //周日上班时间
	 @Column(name="sun_on")
	 private Timestamp sunOn;
	 
	 //周日下班时间
	 @Column(name="sun_off")
	 private Timestamp sunOff;
	 
	 //地理位置
	 @Column(name="location")
	 private String location;
	 
	 //经度
	 @Column(name="longitude")
	 private String longitude;
	 
	 //纬度
	 @Column(name="latitude")
	 private String latitude;
	 
	 //有效范围
	 @Column(name="range")
	 private Integer range;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Integer getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(Integer createUserId) {
		this.createUserId = createUserId;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getUserIds() {
		return userIds;
	}

	public void setUserIds(String userIds) {
		this.userIds = userIds;
	}

	public Timestamp getCommonOn() {
		return commonOn;
	}

	public void setCommonOn(Timestamp commonOn) {
		this.commonOn = commonOn;
	}

	public Timestamp getCommonOff() {
		return commonOff;
	}

	public void setCommonOff(Timestamp commonOff) {
		this.commonOff = commonOff;
	}

	public Timestamp getMonOn() {
		return monOn;
	}

	public void setMonOn(Timestamp monOn) {
		this.monOn = monOn;
	}

	public Timestamp getMonOff() {
		return monOff;
	}

	public void setMonOff(Timestamp monOff) {
		this.monOff = monOff;
	}

	public Timestamp getTuesOn() {
		return tuesOn;
	}

	public void setTuesOn(Timestamp tuesOn) {
		this.tuesOn = tuesOn;
	}

	public Timestamp getTuesOff() {
		return tuesOff;
	}

	public void setTuesOff(Timestamp tuesOff) {
		this.tuesOff = tuesOff;
	}

	public Timestamp getWedOn() {
		return wedOn;
	}

	public void setWedOn(Timestamp wedOn) {
		this.wedOn = wedOn;
	}

	public Timestamp getWedOff() {
		return wedOff;
	}

	public void setWedOff(Timestamp wedOff) {
		this.wedOff = wedOff;
	}

	public Timestamp getThurOn() {
		return thurOn;
	}

	public void setThurOn(Timestamp thurOn) {
		this.thurOn = thurOn;
	}

	public Timestamp getThurOff() {
		return thurOff;
	}

	public void setThurOff(Timestamp thurOff) {
		this.thurOff = thurOff;
	}

	public Timestamp getFriOn() {
		return friOn;
	}

	public void setFriOn(Timestamp friOn) {
		this.friOn = friOn;
	}

	public Timestamp getFriOff() {
		return friOff;
	}

	public void setFriOff(Timestamp friOff) {
		this.friOff = friOff;
	}

	public Timestamp getSatOn() {
		return satOn;
	}

	public void setSatOn(Timestamp satOn) {
		this.satOn = satOn;
	}

	public Timestamp getSatOff() {
		return satOff;
	}

	public void setSatOff(Timestamp satOff) {
		this.satOff = satOff;
	}

	public Timestamp getSunOn() {
		return sunOn;
	}

	public void setSunOn(Timestamp sunOn) {
		this.sunOn = sunOn;
	}

	public Timestamp getSunOff() {
		return sunOff;
	}

	public void setSunOff(Timestamp sunOff) {
		this.sunOff = sunOff;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public Integer getRange() {
		return range;
	}

	public void setRange(Integer range) {
		this.range = range;
	}

	public Integer getMonRest() {
		return monRest;
	}

	public void setMonRest(Integer monRest) {
		this.monRest = monRest;
	}

	public Integer getTuesRest() {
		return tuesRest;
	}

	public void setTuesRest(Integer tuesRest) {
		this.tuesRest = tuesRest;
	}

	public Integer getWedRest() {
		return wedRest;
	}

	public void setWedRest(Integer wedRest) {
		this.wedRest = wedRest;
	}

	public Integer getThurRest() {
		return thurRest;
	}

	public void setThurRest(Integer thurRest) {
		this.thurRest = thurRest;
	}

	public Integer getFriRest() {
		return friRest;
	}

	public void setFriRest(Integer friRest) {
		this.friRest = friRest;
	}

	public Integer getSatRest() {
		return satRest;
	}

	public void setSatRest(Integer satRest) {
		this.satRest = satRest;
	}

	public Integer getSunRest() {
		return sunRest;
	}

	public void setSunRest(Integer sunRest) {
		this.sunRest = sunRest;
	}

	public Timestamp getCommonAmOff() {
		return commonAmOff;
	}

	public void setCommonAmOff(Timestamp commonAmOff) {
		this.commonAmOff = commonAmOff;
	}

	public Timestamp getCommonPmOn() {
		return commonPmOn;
	}

	public void setCommonPmOn(Timestamp commonPmOn) {
		this.commonPmOn = commonPmOn;
	}

	 
}
