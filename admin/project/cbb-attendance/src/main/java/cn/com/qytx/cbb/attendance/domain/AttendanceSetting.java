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

@Table(name="tb_attendance_setting")
@Entity
public class AttendanceSetting extends BaseDomain implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id",unique=true,nullable=false)
	private Integer id;
	
	 @Column(name="create_user_id")
	 private Integer createUserId; //创建人
	   
	 @Column(name="create_time")
	 private Timestamp createTime;//创建时间
	 
	 @Column(name="last_update_time")
	 private Timestamp lastUpdateTime;//更新时间
	 
	 @Column(name="is_delete")
	 @DeleteState
	 private Integer isDelete;//是否删除
	 
	 @Column(name="range")
	 private Integer range;//允许打卡距离
	 
	 @Column(name="duty_type")
	 private Integer dutyType;//1一日两次，2一日4次
	 
	 @Column(name="am_on_duty")
	 private Timestamp amOnDuty;//上午上班时间
	 
	 @Column(name="am_off_duty")
	 private Timestamp amOffDuty;//上午下班班时间
	 
	 @Column(name="pm_on_duty")
	 private Timestamp pmOnDuty;//下午上班时间
	 
	 @Column(name="pm_off_duty")
	 private Timestamp pmOffDuty;//下午下班时间

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(Integer createUserId) {
		this.createUserId = createUserId;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Timestamp getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Timestamp lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

	public Integer getRange() {
		return range;
	}

	public void setRange(Integer range) {
		this.range = range;
	}

	public Integer getDutyType() {
		return dutyType;
	}

	public void setDutyType(Integer dutyType) {
		this.dutyType = dutyType;
	}

	public Timestamp getAmOnDuty() {
		return amOnDuty;
	}

	public void setAmOnDuty(Timestamp amOnDuty) {
		this.amOnDuty = amOnDuty;
	}

	public Timestamp getAmOffDuty() {
		return amOffDuty;
	}

	public void setAmOffDuty(Timestamp amOffDuty) {
		this.amOffDuty = amOffDuty;
	}

	public Timestamp getPmOnDuty() {
		return pmOnDuty;
	}

	public void setPmOnDuty(Timestamp pmOnDuty) {
		this.pmOnDuty = pmOnDuty;
	}

	public Timestamp getPmOffDuty() {
		return pmOffDuty;
	}

	public void setPmOffDuty(Timestamp pmOffDuty) {
		this.pmOffDuty = pmOffDuty;
	}
	
}
