package cn.com.qytx.platform.base.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

/**
 * Domain基类，属性extension可用来扩展表字段
 */
@MappedSuperclass
public class BaseDomain implements Serializable {
	private static final long serialVersionUID = 1L;
	@Transient
	private String extension;
	@CompanyId
	@Column(name="company_id")
	private Integer companyId;
	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}
	
}
