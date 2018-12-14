package cn.com.qytx.cbb.publicDom.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import cn.com.qytx.platform.base.domain.BaseDomain;

/**
 * 功能：记录公文正文修改记录
 * 版本: 1.0
 * 开发人员：贾永强
 * 创建日期: 上午9:25:47 
 * 修改日期：上午9:25:47 
 * 修改列表：
 */
@Entity
@Table(name="tb_history_doc")
public class HistoryDoc extends BaseDomain{
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	//修改人
	@Column(name="update_user",length=50)
	private String updateUser;
	//修改时间
	@Column(name="update_time")
	private Date updateTime;
	//历史文档地址
	@Column(name="history_doc_path",length=100)
	private String hostoryDocPath;
	
	@Column(name="instance_id",length=100)
	private String instanceId;
	
	@Transient
	private String formTime;

	public String getUpdateUser() {
		return updateUser;
	}
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
	public String getHostoryDocPath() {
		return hostoryDocPath;
	}
	public void setHostoryDocPath(String hostoryDocPath) {
		this.hostoryDocPath = hostoryDocPath;
	}
	public String getInstanceId() {
		return instanceId;
	}
	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getFormTime() {
		return formTime;
	}
	public void setFormTime(String formTime) {
		this.formTime = formTime;
	}
	
}
