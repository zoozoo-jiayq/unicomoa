package cn.com.qytx.cbb.consts;

import java.util.Map;
import java.util.TreeMap;

public class CbbConst {
	/**
     * 未删除标记:0
     */
    public static final int NOT_DELETE = 0;

    /**
     * 删除标记：1
     */
    public static final int DELETED = 1;

    /**
     * 查询所有值,设置为-1
     */
    public static final int SEARCH_ALL = -1;

    /**
     * 时间格式化串
     */
    public static final String TIME_FORMAT_STR = "yyyy-MM-dd HH:mm:ss";
    /**
     * 时间格式化串
     */
    public static final String TIME_FORMAT_SUBSTR = "yyyy-MM-dd HH:mm";
    
    /**
     * 日期格式化串
     */
    public static final String DATE_FORMAT_STR = "yyyy-MM-dd";
    
    /**
     * 时间格式化串
     */
    public static final String MINUTE_FORMAT_STR = "HH:mm";

    /**
     * 时间格式化串
     */
    public static final String MONTH_FORMAT_STR = "MM-dd HH:mm";
    
    /**
     * 年格式化串
     */
    public static final String YEAR_FORMAT_STR = "yyyy";
    
    /**
     * 人员Id分隔符
     */
    public static final String USERID_SEGMENTATION = ",";

    /**
     * 发送事务提醒
     */
    public static final int AFFAIRS_YES = 1;

    /**
     * 不发送事务提醒
     */
    public static final int AFFAIRS_NO = 0;

    /**
     * 事务提醒类型:通讯薄
     */
    public static final String AFFAIRS_TYPE_ADDRESS = "通讯薄";

    /**
     * 事务提醒类型:电子邮件
     */
    public static final String AFFAIRS_TYPE_EMAIL = "内部邮件";

    /**
     * 事务提醒类型:日志
     */
    public static final String AFFAIRS_TYPE_DAILY = "工作日志";
    /**
     * 事务提醒类型:日志
     */
    public static final String AFFAIRS_TYPE_PROGRAMME = "日程管理";

    /**
     * 事务提醒类型:日志
     */
    public static final String AFFAIRS_TYPE_TASK = "我的任务";
    
    /**
     * 模块名称:公告
     */
    public static final String NOTIFY_MODULE = "notify";

    /**
     * 事务提醒类型:公告通知
     */
    public static final String NOTIFY_REMIND_TYPE = "公告通知";

    /**
     * 事务提醒类型:日程安排
     */
    public static final String CALENDAR_REMIND_TYPE = "日程安排";

    /**
     * 事务提醒类型:日程安排-周期性事务
     */
    public static final String AFFAIR_REMIND_TYPE = "日程安排-周期性事务";

    /**
     * 模块名称:新建文件
     */
//    public static final String NEW_FILE = "新建文件";

    /**
     * 模块名称:公共文件柜
     */
    public static final String AFFAIRS_TYPE_FILE = "文件柜";
    /**
     * 事务提醒类型:工作流：提醒下一步经办人
     */
    public static final String AFFAIRS_TYPE_WORKFLOW_NEXT = "工作流：提醒下一步经办人";
    /**
     * 事务提醒类型:工作流：提醒下一步经办人
     */
    public static final String AFFAIRS_TYPE_WORKFLOW_CREATER = "工作流：提醒流程发起人";
    /**
     * 事务提醒类型:工作流：提醒下一步经办人
     */
    public static final String AFFAIRS_TYPE_WORKFLOW_ALL = "工作流：提醒流程所有人员";

    /**
     * 权限范围,全体角色
     */
    public static final String ALL_ROLE_NAME = "全体角色";
    /**
     * 发送聊天室提醒
     */
    public static final String AFFAIRS_TYPE_CHATMESSAGE = "聊天提醒";
    
    /*
     * 发文核稿
     */
    public static final String PUBLICDOM_DIS_APPROVE 		= "公文管理：发文核稿";
    
    public static final String PUBLICDOM_DIS_RED            = "公文管理：套红盖章";
    public static final String PUBLICDOM_DIS_SEND			= "公文管理：发文分发";
    
    /*
     * 收文登记
     */
    public static final String PUBLICDOM_GA_REGISTER 		= "公文管理：收文登记";
    
    /*
     * 领导批阅
     */
    public static final String PUBLICDOM_GA_LEADER_APPROVE 	= "公文管理：领导批阅";
    
    /*
     * 收文分发
     */
    public static final String PUBLICDOM_GA_DISPATCHER 		= "公文管理：收文分发";
    
    /*
     * 收文阅读
     */
    public static final String PUBLICDOM_GA_READ 			= "公文管理：收文阅读";
    
    public static final Map<Integer, String> AFFAIRSMAP = new TreeMap<Integer,String>();

    static{
        AFFAIRSMAP.put(2, AFFAIRS_TYPE_EMAIL); // 电子邮件
        AFFAIRSMAP.put(3, AFFAIRS_TYPE_DAILY); // 工作日志
        AFFAIRSMAP.put(4, NOTIFY_REMIND_TYPE); // 公告


        AFFAIRSMAP.put(8, AFFAIRS_TYPE_WORKFLOW_NEXT); // 工作流：提醒下一步经办人
        AFFAIRSMAP.put(9, AFFAIRS_TYPE_WORKFLOW_CREATER);// 工作流：提醒流程发起人
        AFFAIRSMAP.put(10, AFFAIRS_TYPE_WORKFLOW_ALL);// 工作流：提醒流程所有人员       
     
        AFFAIRSMAP.put(12, PUBLICDOM_DIS_APPROVE);// 公文管理：收文登记
        AFFAIRSMAP.put(13, PUBLICDOM_GA_REGISTER);// 公文管理：收文登记  
        AFFAIRSMAP.put(14, PUBLICDOM_GA_LEADER_APPROVE);// 公文管理：领导批阅
        AFFAIRSMAP.put(15, PUBLICDOM_GA_DISPATCHER);// 公文管理：收文分发
        AFFAIRSMAP.put(16, PUBLICDOM_GA_READ);// 公文管理：收文阅读
    }
    
    /**
     * 用户状态 未设置用户登录
     */
    public static final int USERSTATE_UNLOGIN = 2;
    
    /**
     * 模块名称:车辆管理
     */
    public static final String CAR_APPROVE = "车辆审批";
    
    /**
     * 模块名称：任务管理
     */
    public static final String TASK_BEIGN_ALERT = "任务管理-开始提醒";
    public static final String TASK_DELAY_ALERT = "任务管理-延误提醒";
    public static final String TASK_END_ALERT = "任务管理-结束提醒";
}
