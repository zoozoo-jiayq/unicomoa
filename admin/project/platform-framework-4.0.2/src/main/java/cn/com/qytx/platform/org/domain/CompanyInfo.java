package cn.com.qytx.platform.org.domain;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.Transient;

import cn.com.qytx.platform.org.domain.base.BaseCompanyInfo;
/**
 * 功能：单位信息表
 * 版本: 1.0
 * 开发人员：贾永强
 * 创建日期: 下午2:17:26 
 * 修改日期：下午2:17:26 
 * 修改列表：
 */
@Entity
@Table(name="tb_company_info")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorValue(value="CompanyInfo")
public class CompanyInfo extends BaseCompanyInfo {
    
	private static final long serialVersionUID = 1L;
    //系统名称
    @Column(name="sys_name",length=50)
    private String sysName;
    @Transient
    private Integer imType;
    
	public Integer getImType() {
		return imType;
	}

	public void setImType(Integer imType) {
		this.imType = imType;
	}

	public String getSysName() {
		return sysName;
	}

	public void setSysName(String sysName) {
		this.sysName = sysName;
	}

	
}