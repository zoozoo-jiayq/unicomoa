package cn.com.qytx.cbb.customForm.domain;
// default package

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import cn.com.qytx.platform.base.domain.BaseDomain;

import com.google.gson.annotations.Expose;

/**
 * TdFormCategory entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="tb_cbb_form_category")
public class FormCategory extends BaseDomain{

	//表单分类
	public static final Integer CATEGORY_TYPE_FORM = 1;
	//流程分类
	public static final Integer CATEGORY_TYPE_PROCESS = 2;
	//公文套红模板分类
	public static final Integer DOC_RED = 3;
	
	//公文表单分类
	public static final Integer DOC_FORM = 11;
	//公文流程分类
	public static final Integer DOC_FLOW = 12;
	
	//报表分类
	public static final Integer REPORT_TYPE = 21;
	
	//公文流程类型：1收文；2发文
	public static final Map<Integer,String> DOM_TYPE_MAP = new HashMap<Integer, String>(){{
		this.put(1, "收文");
		this.put(2, "发文");
	}};
	
	/**
	 * 描述含义
	 */
	private static final long serialVersionUID = 1L;
	// Fields

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="category_id")
	@Expose
	private Integer categoryId;//分类id
	@Expose
	@Column(name="category_name",length=100,nullable=false)
	private String categoryName;//分类名称
	/*
	 * 分类的类型  1，工作流表单分类 2，工作流程分类，3公文套红模板分类
	 * update by jiayq,新增两种类型：11新公文表单分类；12新公文流程分类
	 */
	@Column(name="type")
	private Integer type;
	@Column(name="orderindex")
	private Integer orderIndex;//排序号
	/**
	 * 创建时间
	 */
	@Column(name="create_time")
	private Timestamp createTime;

	/**
	 * 最后修改时间
	 */
	@Column(name="last_update_time")
	private Timestamp lastUpdateTime;
	
	//如果是公文流程分类，则需要区分是收文还是发文；1收文；2发文，默认是1
	@Column(name="domtype")
	private Integer domType;
	
	public Integer getDomType() {
		return domType;
	}
	public void setDomType(Integer domType) {
		this.domType = domType;
	}
	/**
	 * 得到创建时间
	 * @return 创建时间
	 */
	public Timestamp getCreateTime() {
		return createTime;
	}
	/**
	 * 设置创建时间
	 * @param createTime
	 */
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	/**
	 * 得到最后修改时间
	 * @return 最后修改时间
	 */
	public Timestamp getLastUpdateTime() {
		return lastUpdateTime;
	}
	/**
	 * 设置最后修改时间
	 * @param lastUpdateTime
	 */
	public void setLastUpdateTime(Timestamp lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	// Property accessors
	/**
	 * 得到分类的id
	 * @return
	 */
	public Integer getCategoryId() {
		return this.categoryId;
	}
	/**
	 * 设置分类的id
	 * @param categoryId
	 */
	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}
	/**
	 * 得到分类名称
	 * @return
	 */
	public String getCategoryName() {
		return this.categoryName;
	}
	/**
	 * 设置分类名称
	 * @param categoryName
	 */
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	
	/**
	 * 得到分类的类别      1，表单分类 2，流程分类
	 * @return
	 */

	public Integer getType() {
		return this.type;
	}
	/**
	 * 设置分类的类别    1，表单分类 2，流程分类
	 * @param type
	 */
	public void setType(Integer type) {
		this.type = type;
	}
	/**
	 * 得到排序号
	 * @return orderIndex
	 */
	public Integer getOrderIndex() {
		return orderIndex;
	}
	/**
	 * 设置排序号
	 * @param orderIndex
	 */
	public void setOrderIndex(Integer orderIndex) {
		this.orderIndex = orderIndex;
	}

	public String getDomTypeZH(){
		if(this.domType!=null && this.domType>0){
			return DOM_TYPE_MAP.get(this.domType);
		}
		return "";
	}
}