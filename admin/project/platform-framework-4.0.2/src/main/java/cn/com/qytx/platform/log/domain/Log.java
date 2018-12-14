package cn.com.qytx.platform.log.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import cn.com.qytx.platform.base.domain.BaseDomain;


/**
 *文件名:log对象
 *CopyRright(C):北京全亚通信技术有限公司
 *创建人:贾永强
 *创建日期:2013-2-21上午9:17:17
 *修改人:
 *修改日期:
 *功能描述:
 *版本号:
 */
@Entity
@Table(name = "log")
public class Log extends BaseDomain{

    @Id
    @Column(name = "vid")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

	public enum Action{
		//c,新增；r,读；U：更新;D:删除
		C,R,U,D
	}
	
	//插入时间,默认是当前时间
	@Column(name="insert_time")
	private Date insertTime = new Date();
	@Column(name="user_id")
	private Integer userId;
	@Column(name="user_name")
	private String userName;
	//系统代码
	@Column(name="sys_name")
	private String sysName;
	//模块代码
	@Column(name="module_name")
	private String moduleName;
	//操作类型，
    @Column(length = 64)
	private String action;
	//日志内容
    @Column(name="log_content")
	private String logContent;
	//引用ID
	@Column(name="ref_id")
	private Integer refId = 0;
	//客户IP
	@Column(name="ip")
	private String ip = "";
	//ip所在地
	@Column(name="ip_address")
	private String ipAddress;
	//日志类型  例：LogType.java 详解
	@Column(name="log_type")
	private Integer logType;
	//备注
	@Column(name="remark")
	private String remark;
	//是否删除
	@Column(name="is_delete")
	private Integer isDelete;
	//类别
	private Integer type;//类别：  0、系统日志之手动添加  1、拦截器配置

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getInsertTime() {
		return insertTime;
	}
	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getSysName() {
		return sysName;
	}
	public void setSysName(String sysName) {
		this.sysName = sysName;
	}
	public String getModuleName() {
		return moduleName;
	}
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	public String getLogContent() {
		return logContent;
	}
	public void setLogContent(String logContent) {
		this.logContent = logContent;
	}
	public Integer getRefId() {
		return refId;
	}
	public void setRefId(Integer refId) {
		this.refId = refId;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public Integer getLogType() {
		return logType;
	}
	public void setLogType(Integer logType) {
		this.logType = logType;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	
}
