package cn.com.qytx.cbb.notify.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import cn.com.qytx.platform.base.domain.CompanyId;
import cn.com.qytx.platform.base.domain.DeleteState;

@Entity
@Table(name="tb_platform_parameter")
public class PlatformParameter implements Serializable{
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id; //主键
	@Column(name="module_name",length=200)
	private String moduleName; //模块名称
	
	@Column(name="par_items",length=200)
	private String parItems;//实体类的路径
	
	@Column(name="par_value_coll")
	private String parValueColl;//实体类字段值
	
	@DeleteState
	@Column(name="is_delete")
	private Integer isDelete; //删除状态
	
	@Column(name="par_describe",length=200)
	private String parDescribe;//描述
	
	@Column(name="instenceid")
	private Integer instenceid;//栏目主键
	
	@CompanyId
	@Column(name="company_id",updatable=false)
	private Integer companyId;
	
	@Column(name="code")
	private String code;//栏目code
	
	
	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}
	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	@Column(name="module_name",length=200)
	public String getModuleName() {
		return moduleName;
	}
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	@Column(name="par_items",length=200)
	public String getParItems() {
		return parItems;
	}
	public void setParItems(String parItems) {
		this.parItems = parItems;
	}
	@Column(name="par_value_coll")
	public String getParValueColl() {
		return parValueColl;
	}
	public void setParValueColl(String parValueColl) {
		this.parValueColl = parValueColl;
	}
	@DeleteState
	@Column(name="is_delete")
	public Integer getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}
	@Column(name="par_describe",length=200)
	public String getParDescribe() {
		return parDescribe;
	}
	public void setParDescribe(String parDescribe) {
		this.parDescribe = parDescribe;
	}
	@Column(name="instenceid")
	public Integer getInstenceid() {
		return instenceid;
	}
	public void setInstenceid(Integer instenceid) {
		this.instenceid = instenceid;
	}
	public Integer getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}
	
}

