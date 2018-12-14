package cn.com.qytx.cbb.attendance.domain;
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
 * 功能: 考勤IP段设置 实体类
 * 版本: 1.0
 * 开发人员: 陈秋利
 * 创建日期: 2014-7-8
 */
@Entity
@Table(name="tb_attendance_ipset")
public class AttendanceIpSet extends BaseDomain implements java.io.Serializable {
	 private static final long serialVersionUID = 1L;
	 @Id
	 @GeneratedValue(strategy = GenerationType.AUTO)
	 @Column(name="ipset_id", unique=true,nullable=false)
	 private Integer ipSetId; //考勤IP设置ID 
	 
	 @Column(name="begin_ip")
     private String beginIp; //起始Ip
	 
	 @Column(name="end_ip")
     private String endIp; //结束Ip
	 
	 @Column(name="create_user_id")
	 private Integer createUserId; //创建人
	   
	 @Column(name="create_time")
	 private Timestamp createTime;//创建时间
	 
	 @Column(name="last_update_time")
	 private Timestamp lastUpdateTime; //最后一次修改时间
	    
	 @Column(name="last_update_user_id")
	 private Integer lastUpdateUserId; //最后一次修改人
	
     @DeleteState
     @Column(name="is_delete")
     private Integer isDelete; // 是否删除

	/**
	 * @return the ipSetId
	 */
	public Integer getIpSetId() {
		return ipSetId;
	}

	/**
	 * @param ipSetId the ipSetId to set
	 */
	public void setIpSetId(Integer ipSetId) {
		this.ipSetId = ipSetId;
	}

	/**
	 * @return the beginIp
	 */
	public String getBeginIp() {
		return beginIp;
	}

	/**
	 * @param beginIp the beginIp to set
	 */
	public void setBeginIp(String beginIp) {
		this.beginIp = beginIp;
	}

	/**
	 * @return the endIp
	 */
	public String getEndIp() {
		return endIp;
	}

	/**
	 * @param endIp the endIp to set
	 */
	public void setEndIp(String endIp) {
		this.endIp = endIp;
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
	 * @return the lastUpdateTime
	 */
	public Timestamp getLastUpdateTime() {
		return lastUpdateTime;
	}

	/**
	 * @param lastUpdateTime the lastUpdateTime to set
	 */
	public void setLastUpdateTime(Timestamp lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	/**
	 * @return the lastUpdateUserId
	 */
	public Integer getLastUpdateUserId() {
		return lastUpdateUserId;
	}

	/**
	 * @param lastUpdateUserId the lastUpdateUserId to set
	 */
	public void setLastUpdateUserId(Integer lastUpdateUserId) {
		this.lastUpdateUserId = lastUpdateUserId;
	}

	/**
	 * @return the isDelete
	 */
	public Integer getIsDelete() {
		return isDelete;
	}

	/**
	 * @param isDelete the isDelete to set
	 */
	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}
     
    
	
}
