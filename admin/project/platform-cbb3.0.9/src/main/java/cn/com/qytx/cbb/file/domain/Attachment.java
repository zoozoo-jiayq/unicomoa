package cn.com.qytx.cbb.file.domain;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import cn.com.qytx.platform.base.domain.BaseDomain;
import cn.com.qytx.platform.base.domain.DeleteState;
/**
 * 附件实体类
 * @版本：1.0  
 * @开发人员：陈秋利   
 * @创建日期：2013-3-21 ATTACH_FILE
 * @修改日期：
 */
@Entity
@Table(name="tb_om_attachment")
public class Attachment extends BaseDomain implements Serializable{
	private static final long serialVersionUID = 1L;

	/**
	 * 主键ID
	 */
	@Id
	@Column(name="vid")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column(name="attach_file",length=200,nullable=false)
	private String attachFile;
	
	@Column(name="create_user_id",nullable=true)
	private Integer createUserId;
	
	@Column(name="create_time",length=23)
	private Timestamp createTime=new Timestamp(System.currentTimeMillis());
	
	@DeleteState
    @Column(name = "is_delete")
    private Integer isDelete;
	
	@Column(name="attach_name",length=200,nullable=false)
	private String attachName;	
	
	@Column(name="attach_size",length=100)
	private Long attachSize;
	
	/**一下字段只是用于显示附件的图片*/
	@Transient
	private String attacthSuffix;// 附件类型


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getAttachFile() {
		return attachFile;
	}


	public void setAttachFile(String attachFile) {
		this.attachFile = attachFile;
	}


	public Integer getCreateUserId() {
		return createUserId;
	}


	public void setCreateUserId(Integer createUserId) {
		this.createUserId = createUserId;
	}


	public Timestamp getCreateTime() {
		return createTime;
	}


	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}


	public Integer getIsDelete() {
		return isDelete;
	}


	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}


	public String getAttachName() {
		return attachName;
	}


	public void setAttachName(String attachName) {
		this.attachName = attachName;
	}


	public String getAttacthSuffix() {
		return attacthSuffix;
	}


	public void setAttacthSuffix(String attacthSuffix) {
		this.attacthSuffix = attacthSuffix;
	}


	public Long getAttachSize() {
		return attachSize;
	}


	public void setAttachSize(Long attachSize) {
		this.attachSize = attachSize;
	}



	
}
