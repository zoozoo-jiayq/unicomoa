package cn.com.qytx.cbb.systemSet.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import cn.com.qytx.platform.base.domain.BaseDomain;

/**
 * 功能：系统配置
 * 版本: 1.0
 * 开发人员：贾永强
 * 创建日期: 下午4:44:06 
 * 修改日期：下午4:44:06 
 * 修改列表：
 */
@Entity
@Table(name="tb_sys_config")
public class SysConfig extends BaseDomain{
    //系统默认密码是123456
    public final static String DEFAULT_PASSWORD = "123456";

    /**************KEY值的定义 开始***********************/
	//审批控件
	public final static String SYS_APPROVE_WIDGET = "approve_widget";
	//阅读控件
	public final static String SYS_READER_WIDGET = "reader_widget";
	
	//审批意见
	public final static String SYS_APPROVE_COMMENT = "approve_comment";
	
	//删除公文
	public final static String SYS_DELETE_OFFICIAL  = "delete_official";
	
	//是否提醒修改默认密码：yes提醒；no不提醒
	public final static String NOTICE_UPDATE_PASSWORD = "notice_update_password";
	
	//收文登记的操作
	public final static String DOM_GATHER_REGISTER = "dom_gather_register";
	//领导批阅的操作
	public final static String DOM_GATHER_APPROVE = "dom_gather_approve";
	//收文分发
	public final static String DOM_GATHER_FENFA = "dom_gather_fenfa";
	//收文阅读
	public final static String DOM_GATHER_READ = "dom_gather_read";
	//是否强制签约
	public final static String FORCE_READ = "force_read";
	//收文归档设置
	public final static String DOM_GATHER_ZIP = "dom_gather_zip";
	
	//发文拟稿
	public final static String DOM_DISPATCH_NIGAO = "dom_dis_nigao";
	//发文核稿
	public final static String DOM_DISPATCH_HEGAO = "dom_dis_hegao";
	//套红盖章
	public final static String DOM_DISPATCH_RED = "dom_dis_red";
	//发文分发
	public final static String DOM_DISPATCH_FENFA = "dom_dis_fenfa";
	//发文归档设置
	public final static String DOM_DISPATCH_ZIP = "dom_dis_zip";
	//部门专栏所属部门KEY
	public final static String BUMENZHUANLAN = "bumenzhuanlan";
	public final static String BUMENZHUANLAN_NAME = "bumenzhuanlan_name";
	
	/*******************************KEY值的定义 结束************************************/
	
	//审批控件：key:approve_widget-----------value:1:不填写审批意见的时候只显示审批人和日期，默认为1；2：不填写审批意见的时候则删除该审批意见
	//阅读控件：key:reader_widget------------ value:阅读控件按照系统设置的人员顺序排序，默认为1；2：按照阅读顺序排序。
	//审批意见：key:approve_comment------------ value:1显示  2 隐藏
	//审批意见：key:delete_official------------ value:1 允许删除 2 禁止删除

	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column(name="config_key",length=50)
	private String configKey ;
	
	@Column(name="config_value")
	private String configValue ;
	
	@Column(name="checkpagemodule")
	private String checkPageMoudle;
	

	public String getCheckPageMoudle() {
		return checkPageMoudle;
	}


	public void setCheckPageMoudle(String checkPageMoudle) {
        this.checkPageMoudle = checkPageMoudle;
    }


    public String getConfigKey() {
		return configKey;
	}

	public void setConfigKey(String configKey) {
		this.configKey = configKey;
	}

	public String getConfigValue() {
		return configValue;
	}

	public void setConfigValue(String configValue) {
		this.configValue = configValue;
	}
	
}
