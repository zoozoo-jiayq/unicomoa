package cn.com.qytx.oa.record.domain;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import cn.com.qytx.cbb.file.domain.Attachment;
import cn.com.qytx.platform.base.domain.BaseDomain;
import cn.com.qytx.platform.base.domain.DeleteState;
import cn.com.qytx.platform.org.domain.UserInfo;

/**
 * 功能：用户档案-奖惩
 * 版本：1.0
 * 开发人员: pb
 * 创建日期：2016年12月19日
 * 修改日期：2016年12月19日	
 */
@Entity
@Table(name="tb_ohr_penalties")
public class RecordPenalties extends BaseDomain implements java.io.Serializable {
	public Timestamp getPenaltiesDate() {
		return penaltiesDate;
	}

	public void setPenaltiesDate(Timestamp penaltiesDate) {
		this.penaltiesDate = penaltiesDate;
	}

	public String getWagesMonth() {
		return wagesMonth;
	}

	public void setWagesMonth(String wagesMonth) {
		this.wagesMonth = wagesMonth;
	}

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public Integer getNature() {
		return nature;
	}

	public void setNature(Integer nature) {
		this.nature = nature;
	}

	public Float getPenalties_money() {
		return penalties_money;
	}

	public void setPenalties_money(Float penalties_money) {
		this.penalties_money = penalties_money;
	}

	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getAttment() {
		return attment;
	}

	public void setAttment(String attment) {
		this.attment = attment;
	}

	public String getReminded() {
		return reminded;
	}

	public void setReminded(String reminded) {
		this.reminded = reminded;
	}

	public String getExplain() {
		return explain;
	}

	public void setExplain(String explain) {
		this.explain = explain;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(Integer createUserId) {
		this.createUserId = createUserId;
	}

	public Integer getUpdateUserId() {
		return updateUserId;
	}

	public void setUpdateUserId(Integer updateUserId) {
		this.updateUserId = updateUserId;
	}

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

	private static final long serialVersionUID = 6003502541882035434L;

	/**
	 * 主键ID
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id", unique=true,nullable=false)
	private Integer id;
	
	/**
	 * 奖惩日期
	 */
	@Column(name="penalties_date")
	private Timestamp penaltiesDate; 
	
	/**
	 * 工资月份
	 */
	@Column(name="wages_month")
	private String wagesMonth; 
	
	/**
	 * 奖惩项目
	 */
	@Column(name="project")
	private String project;
	
	/**
	 * 奖惩属性 0 奖励  1 惩罚
	 */
	private Integer nature;
	
	/**
	 * 奖惩属性
	 */
	@Column(name="penalties_money")
	private Float penalties_money;
	
	/**
     * 用户 对象
     */
    @JoinColumn(name="user_id",nullable=false)
   	@ManyToOne(cascade=CascadeType.REFRESH)
	private UserInfo userInfo;
    
    /**
	 * 备注
	 */
    private String remark;
    
    /**
     * 附件
     */
    private String attment;
    
    /**
     * 提醒
     */
    private String reminded;
    
    /**
	 * 说明
	 */
    private String explain;
    
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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public List<Attachment> getAttachmentList() {
		return attachmentList;
	}

	public void setAttachmentList(List<Attachment> attachmentList) {
		this.attachmentList = attachmentList;
	}
}
