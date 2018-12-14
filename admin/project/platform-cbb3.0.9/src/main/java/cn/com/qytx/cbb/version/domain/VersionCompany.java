package cn.com.qytx.cbb.version.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="Version_company")
public class VersionCompany {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="vid")
	private Integer vid;
	@Column(name="Version_id")
	private Integer versionId;//版本id
	@Column(name="Company_id")
	private Integer companyId;//公司id
	@Column(name="User_id")
	private Integer userId;//人员id

	public Integer getVid() {
		return vid;
	}

	public void setVid(Integer vid) {
		this.vid = vid;
	}

	public Integer getVersionId() {
		return versionId;
	}

	public void setVersionId(Integer versionId) {
		this.versionId = versionId;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	
}
