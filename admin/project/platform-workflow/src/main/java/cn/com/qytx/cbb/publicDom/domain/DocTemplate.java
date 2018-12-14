package cn.com.qytx.cbb.publicDom.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import cn.com.qytx.platform.base.domain.BaseDomain;

/**
 * 功能: 公文模版
 * 版本: 1.0
 * 开发人员: 陈秋利
 * 创建日期: 2013-5-9
 * 修改日期: 2013-5-9
 * 修改列表:
 */
@Entity
@Table(name="tb_doc_template")
public class DocTemplate extends BaseDomain implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	@Id
    @Column(name = "doctemplate_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Integer docTemplateId; //模板ID
	
	@Column(name="doctemplate_name",length=50)
	private String docTemplateName; //文件模板ID
	
	@Column(name="filename",length=100)
	private String fileName;
	
	@Column(name="docurl",length=100)
	private String docUrl; //文件存放路径
	
	@Column(name="categoryid")
	private Integer categoryId;//
	
	@Column(name="userids",length=2000)
	private String userIds;
	
	@Column(name="isdelete")
	private Integer isDelete;//
	/**
	 * 创建时间
	 */
	@Column(name="create_time")
	private Date createTime;
	public Integer getDocTemplateId() {
		return docTemplateId;
	}
	public void setDocTemplateId(Integer docTemplateId) {
		this.docTemplateId = docTemplateId;
	}
	public String getDocTemplateName() {
		return docTemplateName;
	}
	public void setDocTemplateName(String docTemplateName) {
		this.docTemplateName = docTemplateName;
	}
	public String getDocUrl() {
		return docUrl;
	}
	public void setDocUrl(String docUrl) {
		this.docUrl = docUrl;
	}
	public Integer getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public Integer getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}
	public String getUserIds() {
		return userIds;
	}
	public void setUserIds(String userIds) {
		this.userIds = userIds;
	}
	@Column(name="user_names")
	private String userNames;
	public String getUserNames() {
		return userNames;
	}
	public void setUserNames(String userNames) {
		this.userNames = userNames;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}