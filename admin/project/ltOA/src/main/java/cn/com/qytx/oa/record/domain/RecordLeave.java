/**
 * 
 */
package cn.com.qytx.oa.record.domain;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import cn.com.qytx.cbb.file.domain.Attachment;
import cn.com.qytx.platform.base.domain.BaseDomain;
import cn.com.qytx.platform.base.domain.DeleteState;
import cn.com.qytx.platform.org.domain.UserInfo;

/**
 * 功能:离职
 * 版本: 1.0
 * 开发人员: 王刚刚
 * 创建日期: 2016年12月22日
 * 修改日期: 2016年12月22日
 * 修改列表: 
 */
@Entity
@Table(name="tb_ohr_leave")
public class RecordLeave extends BaseDomain  {

	/**
	 * 描述含义
	 */
	private static final long serialVersionUID = 616637475066187886L;

	/**
	 * 主键ID
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id", unique=true,nullable=false)
	private Integer id;
	
	/**
     * 用户 对象
     */
    @JoinColumn(name="user_id",nullable=false)
   	@ManyToOne(cascade=CascadeType.REFRESH)
	private UserInfo userInfo;
    /**
     * 担任职务
     */
    @Column(name="position")
    private String position;
    /**
     * 离职类型
     */
    @Column(name="leave_type")
    private Integer leaveType;
	@Transient
	private String leaveTypeString;
    /**
     * 申请日期
     */
    @Column(name="apply_date")
    private Timestamp applyDate;
    /**
     * 拟离职日期
     */
    @Column(name="intended_to_leave_date")
    private Timestamp intendedToLeaveDate;
    /**
     * 实际离职日期
     */
    @Column(name="actual_leave_date")
    private Timestamp actualLeaveDate;
    /**
     * 工资截止日期
     */
    @Column(name="wage_cutoff_date")
    private Timestamp wageCutoffDate;
    /**
     * 离职部门ID
     */
    @Column(name="leave_department_name")
    private String leaveDepartmentName;
    /**
     * 离职当月薪资
     */
    @Column(name="leave_the_month_wage")
    private  Float  leaveTheMonthWage;
    /**
     * 去向
     */
    @Column(name="whereabouts")
    private String whereabouts;
    /**
     * 离职手续办理
     */
    @Column(name="resignation_procedure")
    private String resignationProcedure;
    /**
     * 备注
     */
    @Column(name="remark")
    private String remark;
    /**
     * 离职原因
     */
    @Column(name="leave_reason")
    private String leaveReason;
    /**
     * 附件
     */
    @Column(name="attment")
    private String attment;
    
    
    /**
   	 * 创建时间
   	 */
       @Column(name="create_time")
   	private Timestamp createTime;
   	
   	/**
   	 * 修改时间
   	 */
      @Column(name="update_time")
   	  private Timestamp updateTime;
       
       /**
        * 创建人
        */
       @Column(name="create_user_id")
       private Integer createUserId;
       /**
        * 修改人
        */
       @Column(name="update_user_id")
       private Integer updateUserId;
       
       @DeleteState
       @Column(name="is_delete")
       private Integer isDelete;

	   	@Transient
	   	private List<Attachment> attachmentList = new ArrayList<Attachment>();

		/**
		 * @return the id
		 */
		public Integer getId() {
			return id;
		}

		/**
		 * @param id the id to set
		 */
		public void setId(Integer id) {
			this.id = id;
		}

		/**
		 * @return the userInfo
		 */
		public UserInfo getUserInfo() {
			return userInfo;
		}

		/**
		 * @param userInfo the userInfo to set
		 */
		public void setUserInfo(UserInfo userInfo) {
			this.userInfo = userInfo;
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
		 * @return the leaveType
		 */
		public Integer getLeaveType() {
			return leaveType;
		}

		/**
		 * @param leaveType the leaveType to set
		 */
		public void setLeaveType(Integer leaveType) {
			this.leaveType = leaveType;
		}

		/**
		 * @return the applyDate
		 */
		public Timestamp getApplyDate() {
			return applyDate;
		}

		/**
		 * @param applyDate the applyDate to set
		 */
		public void setApplyDate(Timestamp applyDate) {
			this.applyDate = applyDate;
		}

		/**
		 * @return the intendedToLeaveDate
		 */
		public Timestamp getIntendedToLeaveDate() {
			return intendedToLeaveDate;
		}

		/**
		 * @param intendedToLeaveDate the intendedToLeaveDate to set
		 */
		public void setIntendedToLeaveDate(Timestamp intendedToLeaveDate) {
			this.intendedToLeaveDate = intendedToLeaveDate;
		}

		/**
		 * @return the actualLeaveDate
		 */
		public Timestamp getActualLeaveDate() {
			return actualLeaveDate;
		}

		/**
		 * @param actualLeaveDate the actualLeaveDate to set
		 */
		public void setActualLeaveDate(Timestamp actualLeaveDate) {
			this.actualLeaveDate = actualLeaveDate;
		}

		/**
		 * @return the wageCutoffDate
		 */
		public Timestamp getWageCutoffDate() {
			return wageCutoffDate;
		}

		/**
		 * @param wageCutoffDate the wageCutoffDate to set
		 */
		public void setWageCutoffDate(Timestamp wageCutoffDate) {
			this.wageCutoffDate = wageCutoffDate;
		}

	

		public String getLeaveDepartmentName() {
			return leaveDepartmentName;
		}

		public void setLeaveDepartmentName(String leaveDepartmentName) {
			this.leaveDepartmentName = leaveDepartmentName;
		}

	

		public Float getLeaveTheMonthWage() {
			return leaveTheMonthWage;
		}

		public void setLeaveTheMonthWage(Float leaveTheMonthWage) {
			this.leaveTheMonthWage = leaveTheMonthWage;
		}

		/**
		 * @return the whereabouts
		 */
		public String getWhereabouts() {
			return whereabouts;
		}

		/**
		 * @param whereabouts the whereabouts to set
		 */
		public void setWhereabouts(String whereabouts) {
			this.whereabouts = whereabouts;
		}

		/**
		 * @return the resignationProcedure
		 */
		public String getResignationProcedure() {
			return resignationProcedure;
		}

		/**
		 * @param resignationProcedure the resignationProcedure to set
		 */
		public void setResignationProcedure(String resignationProcedure) {
			this.resignationProcedure = resignationProcedure;
		}

		/**
		 * @return the remark
		 */
		public String getRemark() {
			return remark;
		}

		/**
		 * @param remark the remark to set
		 */
		public void setRemark(String remark) {
			this.remark = remark;
		}

		/**
		 * @return the attment
		 */
		public String getAttment() {
			return attment;
		}

		/**
		 * @param attment the attment to set
		 */
		public void setAttment(String attment) {
			this.attment = attment;
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
		 * @return the updateTime
		 */
		public Timestamp getUpdateTime() {
			return updateTime;
		}

		/**
		 * @param updateTime the updateTime to set
		 */
		public void setUpdateTime(Timestamp updateTime) {
			this.updateTime = updateTime;
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
		 * @return the updateUserId
		 */
		public Integer getUpdateUserId() {
			return updateUserId;
		}

		/**
		 * @param updateUserId the updateUserId to set
		 */
		public void setUpdateUserId(Integer updateUserId) {
			this.updateUserId = updateUserId;
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

		/**
		 * @return the attachmentList
		 */
		public List<Attachment> getAttachmentList() {
			return attachmentList;
		}

		/**
		 * @param attachmentList the attachmentList to set
		 */
		public void setAttachmentList(List<Attachment> attachmentList) {
			this.attachmentList = attachmentList;
		}

		public String getLeaveReason() {
			return leaveReason;
		}

		public void setLeaveReason(String leaveReason) {
			this.leaveReason = leaveReason;
		}

	public String getLeaveTypeString() {
		return leaveTypeString;
	}

	public void setLeaveTypeString(String leaveTypeString) {
		this.leaveTypeString = leaveTypeString;
	}
}
