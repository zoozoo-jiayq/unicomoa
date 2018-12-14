package cn.com.qytx.cbb.publicDom.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import cn.com.qytx.platform.base.domain.BaseDomain;


/**
 * 功能:
 * 版本: 1.0
 * 开发人员: 陈秋利
 * 创建日期: 2013-4-15
 * 修改日期: 2013-4-15
 * 修改列表: 收发文扩展表
 */
@Entity
@Table(name="tb_document_ext")
public class DocumentExt  extends BaseDomain{
	private static final long serialVersionUID = 1L;

	@Id
    @Column(name = "documentextid")
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Integer documentExtId;
	
	@Column(name="processinstance_id",length=100)
	private String processInstanceId; //流程实例ID
	
	@Column(name="signdata")
	private byte[] signData; //签字数据
	
	@Column(name="attachid")
	private Integer attachId; //正文附件ID

	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}


	public byte[] getSignData() {
		return signData;
	}

	public void setSignData(byte[] signData) {
		this.signData = signData;
	}

	public Integer getDocumentExtId() {
		return documentExtId;
	}

	public void setDocumentExtId(Integer documentExtId) {
		this.documentExtId = documentExtId;
	}

	public Integer getAttachId() {
		return attachId;
	}

	public void setAttachId(Integer attachId) {
		this.attachId = attachId;
	}
	
	
	
	
	
}
