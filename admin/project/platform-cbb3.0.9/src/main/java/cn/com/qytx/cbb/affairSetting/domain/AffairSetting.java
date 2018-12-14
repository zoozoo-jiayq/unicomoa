package cn.com.qytx.cbb.affairSetting.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import cn.com.qytx.platform.base.domain.BaseDomain;
/**
 * 事务提醒设置类
 * @author TANG
 *
 */
@Entity
@Table(name="tb_om_affair_setting")
public class AffairSetting extends BaseDomain implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	 @Column(name="id", unique=true,nullable=false)
	 private Integer id; //Id 
	 
    @Column(name="module_name",length=255)
    private String moduleName; //模块名称
    
    @Column(name="affair_priv")
    private Integer affairPriv; //在线事务提醒  0不提醒   1提醒
    
    @Column(name="sms_priv")
    private Integer smsPriv; //发送短信提醒  0不发送   1发送
    
    @Column(name="push_priv")
    private Integer pushPriv; //推送消息提醒  0不推送   1推送

    @Column(name="module_code",length=255)
    private String moduleCode; //模块编码
    
    
    
	public String getModuleCode() {
		return moduleCode;
	}

	public void setModuleCode(String moduleCode) {
		this.moduleCode = moduleCode;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public Integer getAffairPriv() {
		return affairPriv;
	}

	public void setAffairPriv(Integer affairPriv) {
		this.affairPriv = affairPriv;
	}

	public Integer getSmsPriv() {
		return smsPriv;
	}

	public void setSmsPriv(Integer smsPriv) {
		this.smsPriv = smsPriv;
	}

	public Integer getPushPriv() {
		return pushPriv;
	}

	public void setPushPriv(Integer pushPriv) {
		this.pushPriv = pushPriv;
	}
    
    
}
