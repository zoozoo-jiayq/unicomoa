package cn.com.qytx.cbb.affairsRemind.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import cn.com.qytx.platform.base.domain.BaseDomain;

@Entity
@Table(name="tb_om_affairs_remind")
public class AffairsRemind extends BaseDomain {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="vid", unique=true,nullable=false)
	private Integer id; //Id 
	
	@Column(name="status",nullable=false)
	private Integer status=0;//是否启用重复提醒 0 不启用 1启用
	
	@Column(name="times",nullable=false)
	private Integer times=0;//提醒次数
							//设置为1次，在每天 08:45，发送待办事项提醒；
							//设置为2次，在每天 08:45、14:45，发送待办事项提醒；
							//设置为4次，在每天 08:45、11:30、14:45、17:30，发送待办事项提醒；

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getTimes() {
		return times;
	}

	public void setTimes(Integer times) {
		this.times = times;
	}

	
	
}
