package cn.com.qytx.cbb.attendance.domain;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import cn.com.qytx.platform.base.domain.BaseDomain;

/**
 * 功能: 考勤记录 日期
 * 版本: 1.0
 * 开发人员: 陈秋利
 * 创建日期: 2014-7-8
 */
@Entity
@Table(name="tb_attendance_days")
public class AttendanceDays extends BaseDomain{

	@Id
	 @GeneratedValue(strategy = GenerationType.AUTO)
	 @Column(name="att_day_id", unique=true,nullable=false)
	 private Integer attDayId; //考勤日期ID 
	 
	 @Column(name="day")
	 private Timestamp day;//时间
	 
	 @Column(name="week")
	 private String week; //星期

	/**
	 * @return the attDayId
	 */
	public Integer getAttDayId() {
		return attDayId;
	}

	/**
	 * @param attDayId the attDayId to set
	 */
	public void setAttDayId(Integer attDayId) {
		this.attDayId = attDayId;
	}

	/**
	 * @return the day
	 */
	public Timestamp getDay() {
		return day;
	}

	/**
	 * @param day the day to set
	 */
	public void setDay(Timestamp day) {
		this.day = day;
	}

	/**
	 * @return the week
	 */
	public String getWeek() {
		return week;
	}

	/**
	 * @param week the week to set
	 */
	public void setWeek(String week) {
		this.week = week;
	}
	   
	 
	 
	
	 
	  
}
