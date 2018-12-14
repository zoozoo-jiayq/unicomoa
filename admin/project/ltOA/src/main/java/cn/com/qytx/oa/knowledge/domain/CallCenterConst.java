package cn.com.qytx.oa.knowledge.domain;

public class CallCenterConst {
	/**
	 * 时间格式化串 不带秒
	 */
	public static final String TIME_FORMAT_STR_NOT_MIN = "yyyy-MM-dd HH:mm";

	/**
	 * 工单编号年月
	 */
	public static final String TIME_FORMAT_STR_Y_M = "yyyyMM";

	/**
	 * 诉求编号年
	 */
	public static final String TIME_FORMAT_STR_Y = "yyyy";

	/**
	 * 格式化日期
	 */
	public static final String DATE_FORMAT_STR = "yyyy-MM-dd";

	/**
	 * 时间格式化串 不带秒
	 */
	public static final String HOUR_MIN = "HH:mm";

	/**
	 * 时间格式化串 小时
	 */
	public static final String HOUR = "HH";

	/**
	 * 格式化日期包含毫秒数
	 */
	public static final String TIME_ALL_FORMAT_STR = "yyyy-MM-dd HH:mm:ss.SSS";

	/**
	 * 时间格式化串 标准
	 */
	public static final String TIME_FORMAT_STANDARD_STR = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 工单列表空白显示
	 */
	public static final String WORKORDER_TABLE_BLANK = "-";

	/**
	 * 全部选择
	 */
	public static final int SELECT_ALL = -1;

	/**
	 * 工单类型 1咨询 2投诉 3维修
	 */
	public static final int TYPE_ADVISORY = 1;
	public static final int TYPE_COMPLAINTS = 2;
	public static final int TYPE_REPAIR = 3;

	/**
	 * 工单状态 -1 表示全部工单 1 已受理 2 待回访 3 待办结 4 已办结
	 */
	public static final int STATE_ACCEPTED = 1;
	public static final int STATE_TRANSFER = 2;
	public static final int STATE_REVISIT = 3;
	public static final int STATE_FINISH = 4;

	/**
	 * 工单处理状态 动作 1 受理 2 转回访 3 转处理 4 办结
	 */
	public static final int DEAL_STATE_ACCEPTED = 1;
	public static final int DEAL_STATE_REVISIT = 2;
	public static final int DEAL_STATE_HANDLE = 3;
	public static final int DEAL_STATE_FINISH = 4;

	/**
	 * 查询列表状态 1 来电弹屏 2 坐席工单列表(咨询,投诉,维修) 3 后台工单列表
	 */
	public static final int LIST_HISTORY = 1;
	public static final int LIST_SEAT = 2;
	public static final int LIST_SYSTEM = 3;

	/**
	 * 公告最多置顶条数
	 */
	public static final int MAX_TOP_NOTICE = 6;

	/**
	 * 管理员角色代码
	 */
	public static final String ADMIN_ROLECODE = "administrator";

	/**
	 * 坐席角色代码
	 */
	public static final String SEAT_ROLECODE = "siter";

	/**
	 * 座席班长角色代码
	 */
	public static final String SEATLEADER_ROLECODE = "seatLeader";

	/**
	 * 市区部门编码
	 */
	public static final String URBAN_DISTRICT_CODE = "urbanDistrict";

	/**
	 * 最长时限
	 */
	public static final int MAX_LIMIT_TIME = 24;
	/**
	 * 导出6w条限制
	 */
	public static final int MAX_EXPORTNUM = 60000;
	/**
	 * 公司的编码 1 鹤壁人民医院 2 3 4 5 6
	 */

	public static final int COMPANY_MARK_SYSTEM = 1;
}
