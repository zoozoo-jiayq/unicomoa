package cn.com.qytx.platform.org.domain;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import cn.com.qytx.platform.org.domain.base.BaseGroupInfo;


/**
 * 功能：部门信息表
 * 版本: 1.0
 * 开发人员：贾永强
 * 创建日期: 下午3:36:05 
 * 修改日期：下午3:36:05 
 * 修改列表：
 */
@Entity
@Table(name="tb_group_info")
public class GroupInfo  extends BaseGroupInfo{
	
	public static final Integer DEPT = 1;
	
	
	@Column(name="group_state")
	private Integer groupState;
	
	@Column(name="branch",length=50)
	private String branch;
	
	//部门主管
	@Column(name="director_id")
	private Integer directorId;
	
	@Column(name="assistant_id")
	private Integer assistantId;
	
	@Column(name="top_director_id")
	private Integer topDirectorId;
	
	@Column(name="top_change_id")
	private Integer topChangeId;
	
	@Column(name="grade")
	private Integer grade;
	
	@Transient
	private String directorName;
	@Transient
	private String assistantName;
	@Transient
	private String topDirectorName;
	@Transient
	private String topChangeName;
	@Transient
	private String	formatFunctions;
	
    @Column(name="is_fork_group")
	private Integer isForkGroup;//该部门所属的分支机构，如果为0则属于总公司

    //公开级别 在移动通讯助理中使用
    @Column(name="public_level")
    private Integer publicLevel;

	public Integer getGroupState() {
        return groupState;
    }

    public void setGroupState(Integer groupState) {
        this.groupState = groupState;
    }

	public Integer getIsForkGroup() {
		return isForkGroup;
	}

	public void setIsForkGroup(Integer isForkGroup) {
		this.isForkGroup = isForkGroup;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public Integer getDirectorId() {
		return directorId;
	}

	public void setDirectorId(Integer directorId) {
		this.directorId = directorId;
	}

	public Integer getAssistantId() {
		return assistantId;
	}

	public void setAssistantId(Integer assistantId) {
		this.assistantId = assistantId;
	}

	public Integer getTopDirectorId() {
		return topDirectorId;
	}

	public void setTopDirectorId(Integer topDirectorId) {
		this.topDirectorId = topDirectorId;
	}

	public Integer getTopChangeId() {
		return topChangeId;
	}

	public void setTopChangeId(Integer topChangeId) {
		this.topChangeId = topChangeId;
	}

	public Integer getGrade() {
		return grade;
	}

	public void setGrade(Integer grade) {
		this.grade = grade;
	}

	public String getDirectorName() {
		return directorName;
	}

	public void setDirectorName(String directorName) {
		this.directorName = directorName;
	}

	public String getAssistantName() {
		return assistantName;
	}

	public void setAssistantName(String assistantName) {
		this.assistantName = assistantName;
	}

	public String getTopDirectorName() {
		return topDirectorName;
	}

	public void setTopDirectorName(String topDirectorName) {
		this.topDirectorName = topDirectorName;
	}

	public String getTopChangeName() {
		return topChangeName;
	}

	public void setTopChangeName(String topChangeName) {
		this.topChangeName = topChangeName;
	}

	public String getFormatFunctions() {
		return formatFunctions;
	}

	public void setFormatFunctions(String formatFunctions) {
		this.formatFunctions = formatFunctions;
	}

    public Integer getPublicLevel() {
        return publicLevel;
    }

    public void setPublicLevel(Integer publicLevel) {
        this.publicLevel = publicLevel;
    }
    
    
}