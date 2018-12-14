package cn.com.qytx.cbb.module.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

/**
 * 手机权限实体类
 * @author liyanlei
 *
 */
@Entity
@Table(name="tb_module_info_mobile")
public class ModuleInfoMobile  implements Serializable{
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	/**
	 * 模块名称
	 */
	@Column(name="name",length=50)
	private String name;
	
	/**
	 * 模块编码
	 */
	@Column(name="code",length=150)
	private String code;
	
	/**
	 * 链接地址
	 */
	@Column(name="url",length=250)
	private String url;
	
	/**
	 * 图标
	 */
	@Column(name="icon",length=500)
	private String icon;
	
	/**
	 * 排序
	 */
	@Column(name="order_list",length=50)
	private String orderList;
	
	/**
	 * 1启用、2停用
	 */
	@Column(name="statue")
	private Integer statue;
		
	@JoinColumn(name="parent_id")
	@ManyToOne(cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	private ModuleInfoMobile parent;
	
	@OrderBy("orderList asc")
	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY,mappedBy="parent")
	private List<ModuleInfoMobile> children = new ArrayList<ModuleInfoMobile>();
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getOrderList() {
		return orderList;
	}
	public void setOrderList(String orderList) {
		this.orderList = orderList;
	}
	public Integer getStatue() {
		return statue;
	}
	public void setStatue(Integer statue) {
		this.statue = statue;
	}
	@JoinColumn(name="parent_id")
	@ManyToOne(cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	public ModuleInfoMobile getParent() {
		return parent;
	}
	public void setParent(ModuleInfoMobile parent) {
		this.parent = parent;
	}
	@OrderBy("orderList asc")
	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY,mappedBy="parent")
	public List<ModuleInfoMobile> getChildren() {
		return children;
	}
	public void setChildren(List<ModuleInfoMobile> children) {
		this.children = children;
	}
	
}
