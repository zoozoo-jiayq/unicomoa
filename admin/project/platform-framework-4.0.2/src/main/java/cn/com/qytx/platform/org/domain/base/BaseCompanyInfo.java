package cn.com.qytx.platform.org.domain.base;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import cn.com.qytx.platform.base.domain.CompanyId;

/**
 * 功能：基本单位信息表
 * 版本: 1.0
 * 开发人员：贾永强
 * 创建日期: 下午2:17:26 
 * 修改日期：下午2:17:26 
 * 修改列表：
 */
@MappedSuperclass
public class BaseCompanyInfo implements Serializable {
	
	//认证企业
	public static Integer COMPANY_STATE_VIP = 1;
	
	@CompanyId
    @Id
    @Column(name = "company_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Integer companyId;
    
    //单位名称
    @Column(name="company_name",length=150)
	private String companyName;
    
    //单位编码
    @Column(name="company_code",length=50)
	private String companyCode;
    
    //联系电话
    @Column(name="Tel",length=50)
    private String tel ;

    //地址
    @Column(name="Address",length=150)
    private String address;
    
    //邮箱
    @Column(name="Email",length=50)
    private String email;
    
    //说明
    @Column(name="Introduction",length=200)
    private String introduction;
    
    //logo地址
    @Column(name="log_Url",length=100)
    private String logUrl;
    
    //单位简称
    @Column(name="short_name",length=50)
    private String shortName;
    
    //联系人
    @Column(name="link_man",length=150)
    private String linkMan;
    
    @Column(name="philosophy")
    private String philosophy;
    
    //1，已认证；else 未认证
    @Column(name="company_state")
    private Integer companyState;
    
    @Column(name="expiration_time")
    private Date expirationTime;
    
    @Column(name="insert_time")
    private Timestamp insertTime;
    
    @Transient
	private String extension;
    
	public String getPhilosophy() {
		return philosophy;
	}

	public void setPhilosophy(String philosophy) {
		this.philosophy = philosophy;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public String getLogUrl() {
		return logUrl;
	}

	public void setLogUrl(String logUrl) {
		this.logUrl = logUrl;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getLinkMan() {
		return linkMan;
	}

	public void setLinkMan(String linkMan) {
		this.linkMan = linkMan;
	}

	public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

	public String getCompanyName() {
		return this.companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyCode() {
		return this.companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Integer getCompanyState() {
		return companyState;
	}

	public void setCompanyState(Integer companyState) {
		this.companyState = companyState;
	}

	public Date getExpirationTime() {
		return expirationTime;
	}

	public void setExpirationTime(Date expirationTime) {
		this.expirationTime = expirationTime;
	}

	public Timestamp getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(Timestamp insertTime) {
		this.insertTime = insertTime;
	}


}