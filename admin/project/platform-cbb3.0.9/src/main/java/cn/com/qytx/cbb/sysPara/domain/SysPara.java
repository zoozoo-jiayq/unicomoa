package cn.com.qytx.cbb.sysPara.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import cn.com.qytx.platform.base.domain.BaseDomain;

/**
 * 系统参数实体类
 * @author lhw
 *
 */
@Entity
@Table(name="tb_om_sys_para")
public class SysPara extends BaseDomain{
	/**
	 * 序列号
	 */
	@Transient
	private static final long serialVersionUID = -3217498317923538203L;
	
	/**
	 * 主键ID
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	
	/**
	 * 参数名
	 */
	@Column(name="para_name")
	private String paraName;
	
	/**
	 * 参数值
	 */
	@Column(name="para_value")
	private String paraValue;

	public String getParaName() {
		return paraName;
	}

	public void setParaName(String paraName) {
		this.paraName = paraName;
	}

	public String getParaValue() {
		return paraValue;
	}

	public void setParaValue(String paraValue) {
		this.paraValue = paraValue;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
}