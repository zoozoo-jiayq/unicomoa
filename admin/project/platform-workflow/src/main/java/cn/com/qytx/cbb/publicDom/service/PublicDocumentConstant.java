package cn.com.qytx.cbb.publicDom.service;

import java.util.HashMap;
import java.util.Map;

import cn.com.qytx.cbb.publicDom.service.PublicDomService.MenuType;

/**
 * 功能：公文管理使用的变量
 * 版本: 1.0
 * 开发人员：贾永强
 * 创建日期: 2013-4-15 下午5:47:59 
 * 修改日期：2013-4-15 下午5:47:59 
 * 修改列表：
 */
public class PublicDocumentConstant {
	//收文流程定义文件
	public final static String GATHER_FILE = "cn/com/qytx/cbb/publicDom/service/gather-file.jpdl.xml";
	//发文流程定义文件
	public final static String DISPATCHER_FILE = "cn/com/qytx/cbb/publicDom/service/dispatch-file.jpdl.xml";

	//发文流程名称
	public final static String DISPATCHER_PROCESS_NAME = "dispatch";
	//收文流程名称
	public final static String GATHER_PROCESS_NAME = "gather";
	
	//收文登记员
	public final static String GATHER_REGISTER = "gather_register";
	
	//任务发起人
	public final static String CREATER = "creater";
	
	//公文处理人
	public final static String ASSIGNER = "assigner";
	
	//文号
	public final static String WENHAO = "wenhao";
	
	//公文类型
	public final static String GONGWENTYPE = "gongwenType";
	public final static String GONGWENTYPE_ID = "gongwenType_id";
	
	//附件
	public final static String ATTACH = "attach";
	
	//标题
	public final static String TITLE = "title";
	
	//打印份数
	public final static String PRINTUNUM = "printNum";
	
	//发文部门
	public final static String SENDER_DEPT = "sendDept";
	
	//收文部门
	public final static String GATHER_DEPT = "reciver";
	
	public final static String ZIPER = "ziper";
	public final static String ZIP_TIME = "zipTime";
	
	//一级分类名称
	public final static String FIRST_LEVEL_NAME = "firstLevel";
	
	//公文流程状态映射--未处理
	public static Map<String, String> stateMap = new HashMap<String, String>(){
		{
			this.put(MenuType.DIS_CREATER.getNodeName(), "拟稿中");
			this.put(MenuType.DIS_APPROVE.getNodeName(), "核稿中");
			this.put(MenuType.DIS_REDER.getNodeName(), "盖章中");
			this.put(MenuType.DIS_SENDLIST.getNodeName(), "分发中");
			this.put(MenuType.ZIP.getNodeName(), "待归档");
			this.put(MenuType.GA_REGISTER.getNodeName(), "待登记");
			this.put(MenuType.GA_APPROVE.getNodeName(), "批阅中");
			this.put(MenuType.GA_DISPATCH.getNodeName(), "分发中");
			this.put(MenuType.GA_READ.getNodeName(), "传阅中");
		}
	};
	
	//收文来源:新增或者发文
	public static final String GATHER_SOURCE = "source";
	public enum GATHER_SOURCE{
		SOURCE_CREATE("create"),SOURCE_DISPATCHER("dispatch");
		private GATHER_SOURCE(String source){
			this.source = source;
		}
		private String source;
		public String getSource(){
			return source;
		}
	}
	//收文来源，发文实例ID
	public static final String GATHER_SOURCE_DISPATCH_EXECUTIONID = "source_executionid";
	
	//密级:“绝密”、“机密”、“秘密”
	public static final String SECRET_LEVEL = "secretLevel";
	
	//缓急
	public static final String HUANJI = "huanji";

		//查询类型
	public enum SearchType{
		//我申请的
		MY_START,
		//我参与的
		MY_PART,
		//我处理中的
		MY_PROCESSING,
		//已办结的
		MY_COMPLETE
	}
	
	//审批记录
	public static final String APPROVE_HIST_RECORD = "approveHistRecord";
	
	/**
	 * 操作映射  ：后台操作码----》界面显示名称
	 */
	public static final Map<String,String> OPERATION_MAP = new HashMap<String, String>(){{
	    this.put("转领导批阅", "登记转批阅");
	    this.put("转收文分发", "登记转分发");
	    this.put("转阅读", "分发");
	    this.put("已阅", "已阅");
	    this.put("转核稿", "转核稿");
	    this.put("转盖章", "完成核稿转盖章");
	    this.put("转发文分发", "转办公室发文");
	    this.put("转分发", "发文给办公室");
	    this.put("保存", "保存");
	    this.put("归档", "归档");
	}};
	
	/*
	 * 下一步操作操作---下一个节点名称
	 */
	public static final Map<String,String> OPERATION_NODE_MAP = new HashMap<String, String>(){{
		this.put("转领导批阅", "领导批阅");
	    this.put("转收文分发", "收文分发");
	    this.put("转阅读", "收文阅读");
	    this.put("已阅", "归档");
	    this.put("转核稿", "发文核稿");
	    this.put("转盖章", "套红盖章");
	    this.put("转发文分发", "发文分发");
	}};
	
}
