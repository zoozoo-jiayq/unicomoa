var qy_lang = {};
qy_lang.global = {
		page_up : "上一页",
		page_down : "下一页",
		songti : "宋体",
		delete_1 : "删除",
		select : "选择",
		total : "合计",
		yes : "是",
		no : "否",
		reply : "回复",
		error : "错误：",
		close : "关闭",
		regist : "注册",
		first_page : "首页",
		before_page : "上页",
		next_page : "下页",
		last_page : "尾页{0}111{1}",
		refresh_1 : "刷新",
		right : "正确",
		print : "打印",
		print_preview : "打印预览",
		clear : "清空",
		details : "详情"
};

//即时消息
qy_lang.message = {
		msg_user_not_null : "接收人不能为空，请选择接收人！",
		message_user_not_null : "收信人不能为空，请选择收信人！",
		msg_contentInfo_not_null : "内容不能为空，请输入内容！",
		msg_content_too_long: "内容长度过长！",
		msg_confirm_info : "确认要删除选中项吗？",
		msg_del_success : "删除即时消息成功！",
		msg_del_failure : "删除即时消息失败！",
		msg_del_at_least_one : "要删除即时消息,请至少选择其中一项！",
		msg_send_success : "即时消息已发送！",
		msg_send_failure : "发送失败！",
		msg_delete_one_confirm_info : "确认要删除该条信息吗？",
		msg_confirm_info_all : "确认要删除对应的即时消息吗？"
};

//事务提醒
qy_lang.affairs = {
		msg_confirm_info : "确认要删除选中项吗？",
		msg_del_success : "删除提醒成功！",
		msg_del_failure : "删除提醒失败！",
		msg_del_at_least_one : "要删除提醒,请至少选择其中一项！",
		msg_read_at_least_one : "要标记提醒为已读,请至少选择其中一项！",
		msg_read_confirm_info : "确认要将选中项标记为已读吗？",
		msg_readed_error : "标记为已读失败！",
		msg_del_read_confirm_info : "确认要删除所有已读的提醒吗？",
		msg_del_unread_confirm_info : "确认要删除所有未阅的提醒吗？",
		msg_del_receive_confirm_info : "确认要删除所有已阅读的提醒吗？",
		msg_del_readed_confirm_info : "确认要删除所有已提醒收信人的提醒吗？",
		msg_del_deleted_confirm_info : "确认要删除所有收信人已删除的提醒吗？",
		msg_retransmission_error : "重发失败！",
		msg_del_all_confirm_info : "确认要删除所有已发送的提醒吗？",
		msg_del_vo_success : "提醒已删除！",
		msg_configuration_success : "设置成功！",
		msg_configuration_error : "设置失败！"
};

//通讯薄
qy_lang.addressbook = {
		msg_success : "操作成功！",
		msg_confirm_info : "确认要删除选中项吗？",
		msg_name_not_null : "姓名不能为空，请输入姓名！",
		msg_phone_limit : "工作电话格式不正确，请输入7到12位数字！",
		msg_post_limit : "单位邮编格式不正确，请输入6位数字！",
		msg_fax_limit : "工作传真格式不正确，请输入7到12位数字！",
		msg_familyPostCode_limit : "家庭邮编格式不正确，请输入6位数字！",
		msg_familyphone_limit : "家庭电话格式不正确，请输入7到12位数字！",
		msg_familyismobile_error : "手机格式不正确，请输入有效的手机号码！",
		msg_email_error : "邮箱格式不正确，请输入有效的电子信箱！",
		msg_add_error : "新建联系人失败！",
		msg_add_exist : "联系人已存在！",
		msg_groupName_not_null : "组名不能为空，请输入组名！",
		msg_groupName_exist : "该组名已存在！",
		msg_add_group_error : "添加分组失败！",
		msg_update_group_error : "更新分组失败！",
		msg_update_address_error : "服务器异常！",
		msg_del_group_confirm : "确认要删除该分组吗？<br>注意:该分组下的联系人将全部转入到默认分组中！",
		msg_del_group_confirm1 : "确认要删除该分组吗？",
		msg_del_group_error : "删除分组失败！",
		msg_clear_address_confirm : "您确定要删除“{0}”分组的联系人吗？",
		msg_clear_group_error : "清空分组失败！",
		msg_del_address_confirm : "确认要删除吗？删除后不可恢复！",
		msg_del_address_error : "删除联系人失败！",
		msg_del_address_at_least_one : "要删除联系人,请至少选择其中一项！",
		msg_del_multiple_address_confirm : "确认要删除选中项吗？",
		msg_share_address_success : "保存完成！",
		msg_time_error : "截止时间不能早于起始时间！",
		msg_must_uploadfile : "请先选择导入的文件,再上传！",
		msg_phone_not_null : "手机不能为空，请输入手机！"
};

/**
 * 短信发送模块的验证
 * 
 * @type {{}}
 */
qy_lang.sms = {
		sms_user_not_null : "请添加收信人！",
		sms_content_not_null : "短信内容不能为空！",
		sms_date_valide : "日期格式不正确！",
		sms_phone_valide : "电话格式不正确！"

};

/**
 * 短信发送模块的验证
 */
qy_lang.file = {
		file_not_null : "文件夹名称不能为空！",
		fileContent_not_null : "文件名称不能为空！",
		fileContent_content_not_null : "文件内容不能为空！"

};

/**
 * 邮件模块
 * 
 * @type {{}}
 */
qy_lang.email = {
		receive_user_not_null : "收信人不能为空，请选择收信人！",
		you_will_send_no_subject_email : "邮件主题为空，是否发送邮件？",
		email_content_too_long: "邮件内容过长！",
		already_saved_to_draft : "已为您保存到草稿箱！",
		already_auto_saved_to_draft : "已自动为您保存到草稿箱！",
		send_success : "内部邮件发送成功！",
		no_selected_email : "要{0}邮件，请至少选择其中的一项！",
		confirm_destroy_selected_email : "确认要永久删除选中项吗？删除后不可恢复！",
		confirm_destroy_this_email : "确认要永久删除吗？",
		confirm_export_selected_email : "确定要导出选中的邮件吗？",
		confirm_delete_selected_email : "确认要删除选中项吗？",
		confirm_delete_this_email : "确认要删除吗？",
		alert_delete_not_read_email : "注意：如果删除内部收件人未读取邮件，内部收件人将不会接收该邮件！",
		confirm_delete_all_receiver_deleted : "确定要删除所有收件人永久删除邮件吗？",
		confirm_delete_all_receiver_read : "确定要删除所有收件人已读取邮件吗？",
		confirm_delete_all_receiver_unread : "确定要删除所有收件人未读邮件吗？删除后未读取的收件人将不会接收该邮件！",
		alert_no_read_email_to_delete : "没有已读取邮件可删除！",
		alert_no_unread_email : "本页没有未读取邮件！",
		confirm_delete_this_page_read : "确认要删除所有已读吗？",
		delete_email_success : "删除{0}封{1}成功！",
		confirm_flag_read_selected : "确认要将选中项标记为已读吗？",
		alert_only_can_forward_one_email : "只能转发一封邮件！",
		confirm_flag_read_all : "确认要将当前邮件箱全部标记为已读吗？",
		flag_read_all_success : "已经标记{0}封邮件为已读！",

		confirm_recovery_selected_email : "确定要恢复选中的邮件吗？",
		confirm_recovery_this_email : "确定要恢复邮件吗？",

		confirm_send_selected_email : "确定要发送选中的邮件吗？",
		confirm_move_email_to : "确定要将选中的邮件移动到 {0} 吗？",
		confirm_clean_wastebasket : "确定要清空垃圾箱中的所有邮件吗？",
		already_clean_wastebasket : "已经清空垃圾箱！",
		email_not_exist : "邮件不存在！",

		already_is_first : "已经是第一封了！",
		already_is_last : "已经是最后封了！",

		email_box_add_success : "新增文件夹成功！",
		email_box_update_success : "修改文件夹成功！",
		email_box_per_page_max_size_must_over_zero : "每页显示邮件数不能小于0！",
		email_box_confirm_delete : "删除该文件夹将删除该文件夹下所有邮件，确认要删除吗？",
		email_box_delete_success : "删除文件夹成功。",
		email_box_order_no_must_be_number : "序号必须为数字！",
		email_box_order_no_must_over_zero : "序号不能小于0！",
		email_box_name_not_null : "名称不能为空！",
		email_box_page_max_must_be_number : "每页显示邮件数必须为数字！"
};

/**
 * 人事档案模块
 * 
 * @type {{}}
 */
qy_lang.record = {

		no_select_item_to_delete : "要删除人事档案,请至少选择其中的一项。",
		confirm_to_delete_select : "确认要删除吗？删除后不可恢复。",

		login_name_not_null : "请选择用户",
		job_not_null : "请输入职务",
		login_name_already_exist : "该用户已建档!",
		user_name_not_null : "姓名不能为空，请输入姓名。",
		mobile_no_not_null : "手机号码不能为空，请输入手机号码",
		not_allow_empty : "不允许为空",
		must_be_number : "请输入数字",
		must_over_zero : "必须大于0",
		must_be_right_phone_no : "电话格式不正确，请输入7到12位数字",
		must_be_right_mobile_no : "手机号码格式不正确",
		must_be_right_email : "邮箱格式不正确，请输入有效的电子信箱",
		must_be_right_msn : "MSN格式不正确，请输入有效的MSN账户",
		must_be_right_qq : "QQ号码只能是数字",
		must_be_chinese : "请输入中文",
		must_be_english : "请输入英文",
		must_be_english_name : "请输入正确的英文名",
		must_be_integer : "请输入数字",
		must_be_idCard : "请输入正确的身份证号",
		introduction_out_of_max_length : "单位简介长度不能超过200字！"
};

/**
 * 日志模块的验证
 */
qy_lang.daily = {
		daily_title_not_null : "日志标题不能为空,请输入日志标题。",
		daily_content_not_null : "日志内容不能为空,请输入日志内容。",
		daily_createTime_not_null : "创建时间不能为空,请选择创建时间。",
		daily_review_content_not_null : "点评内容不能为空,请输入点评内容。",
		daily_review_review_content_not_null : "回复内容不能为空,请输入回复内容。",
		daily_content_count_max : "日志内容最大字数为5000，请修改。",
		daily_review_content_count_max : "点评内容最大字数为500，请修改。",
		daily_review_review_content_count_max : "回复内容最大字数为500，请修改。",
	    daily_shareNames_not_null:"分享对象不能为空！"
};

/**
 * 任务模块的验证
 */
qy_lang.task = {
		task_taskNo_not_null : "排序号不能为空！",
		task_subject_not_null : "任务标题不能为空，请输入任务标题。",
		task_content_not_null : "任务详情不能为空！",
		task_createTime_not_null : "创建时间不能为空！",
		task_beginDate_not_null : "起止日期不能为空！",
		task_endDate_not_null : "起止日期不能为空！",
		task_rate_not_null : "完成率不能为空！",
		task_finishTime_not_null : "完成时间不能为空，请选择完成时间。",
		task_totalTime_not_null : "总工作量不能为空！",
		task_useTime_not_null : "实际工作量不能为空！",
		task_remindTime_not_null : "提醒时间不能为空！",
		task_startDate_endDate : "结束时间不能早于起始时间!",
		task_user_not_null:"请选择要转交的对象！"	,
		task_report_not_long:"转交说明字数必须在1000字以内！",
		task_content_not_long:"完成目标字数必须在1000字以内！"
};

/**
 * 任务模块的验证
 */
qy_lang.countdown = {
		countdown_content_not_null : "名称不能为空，请输入名称。",
		countdown_endTime_not_null : "截止日期不能为空，请选择截止日期！"
};

/**
 * 日志模块的验证
 */
qy_lang.sysset = {
		sysset_oldPass_not_null : "原密码不能为空，请输入原密码。",
		sysset_newPass_not_null : "新密码不能为空，请输入新密码。",
		sysset_newPass1_not_null : "确认新密码不能为空，请输入确认新密码。",
		sysset_oldPass_is_wrong : "原密码错误，请重新输入。",
		sysset_newPass1_is_wrong : "确认新密码与新密码不匹配，请重新输入。",
		sysset_birthDay_is_null : "生日不能为空，请选择生日。",
		sysset_newPass_limit : "新密码必须为6-20位的有效组合。",
		sysset_newPass1_limit : "确认新密码与新密码不匹配，请重新输入。",
		sysset_mobile_not_null : "手机号码不能为空，请输入手机号码。",
		sysset_be_right_email : "MSN格式错误，请输入正确的MSN。",
		sysset_userName_not_null : "姓名不能为空！"
};

/**
 * 自定义流程的验证
 */
qy_lang.customJpdl = {
		process_name_not_null : "流程名称不能为空！",
		process_order_must_int : "流程排序号必须是整数!",
		process_name_expr_not_null : "文号生成器不能为空!",
		process_name_expr_begin_not_null : "编号计数器不能为空！",
		process_name_expr_begin_int : "编号计数器必须是整数!",
		process_name_expr_length_not_null : "编号位数不能为空!",
		process_name_expr_length : "编号位数必须是整数!",
		form_not_null : "表单不能为空！",
		process_type_not_null : "请选择流程分类！"
};
/**
 * 群组验证
 */
qy_lang.group = {
		group_phone_not_null : "电话不能为空！",
		group_phone_format_error : "  电话号码格式不正确！",
		group_order_not_null : "排序号不能为空！",
		group_order_format_error : "排序号必须为数字！",
		group_name_not_null : "部门名称不能为空！",
		parent_group_not_self_group : "上级部门不能为本部门"
};

/**
 * 群组ext验证
 */
qy_lang.groupExt = {
		group_name_not_null : "群组名称不能为空！",
		group_order_format_error : "排序字段必须为数字！",
		group_order_not_null : "排序字段不能为空！"
};
/**
 * 公告模块验证
 */
qy_lang.notify = {
		notify_begDate_not_null:"开始时间不能为空",
		notify_subject_not_null : "请输入标题",
		notify_content_not_null : "请输入内容",
		notify_content_length:"内容长度不能大于1000",
		notify_userName_not_null : "请选择发布范围",
		notify_notifyType_not_null:"请选择类型",
		notify_topDays_scope : "置顶天数范围为0-365",
		notify_reason_not_null : "请输入审批意见",
		notify_reason_length : "批复内容字数应为1-100",
		notify_clear_confirm_info : '确定要清空查阅情况吗？',
		notify_auditer : '已提交审批',
		notify_fabu : '发布成功',
		notify_baocun : '保存成功',
		notify_less_than_begDate : '终止日期应该大于生效日期'
};
/**
 * 人员
 */
qy_lang.user = {
		user_loginName_not_null : "用户名不能为空",
		user_loginName_format_error : "用户名只能输入数字,字母或者下划线",
		user_userName_not_null : "姓名不能为空",
		user_role_not_null : "主角色不能为空",
		user_group_not_null : "单位/部门不能为空",
		user_phone_not_null : "手机号码不能为空",
		user_phone_format_error : "手机号码格式不正确",
		user_email_format_error : "电子邮件格式不正确",
		user_officeTel_format_error : "办公电话格式不正确",
		password_not_null : "密码不能为空",
		password_limit : "密码不能少于6位",
		confirm_password_not_null : "确认密码不能为空",
		confirm_password_limit : "确认密码不能少于6位",
		password_must_be_consistent : "密码和确认密码必须一致",
		user_not_null : "人员不能为空",
		user_is_exist : "该人员已配置登录名,请重新选择",
		loginName_is_exist : "用户名已存在",
		select_role_not_null:"角色不能为空"
};

/**
 * 日程模块验证
 */
qy_lang.calendar = {
		calendar_content_not_null : "日程安排内容不能为空，请输入日程安排内容。",
		calendar_content_too_long: "日程安排内容不能超过1000个字！",
		calendar_days_scope : "天的范围为0-365",
		calendar_hours_scope : "小时范围为0-24",
		calendar_minutes_scope : "分钟范围为0-59",
		confirm_add_info : "确定保存吗？",
		confirm_modify_info : "确定修改吗？",
		calendar_begTime_not_null : "开始时间不能为空！",
		calendar_endTime_not_null : "结束时间不能为空！",
		calendar_days_scope : "天的范围为0-365",
		calendar_hours_scope : "小时范围为0-24",
		calendar_minutes_scope : "分钟范围为0-59",
		calendar_less_than_begTime : '结束时间不能早于开始时间！',
		calendar_less_than_begDate : '结束日期不能早于开始日期！',
		remaind_less_than_begTime : '提醒时间不能大于开始时间！',
		confirm_delete_info : "确认要删除吗？删除后不可恢复！"
};

/**
 * 人员
 */
qy_lang.role = {
		role_roleName_not_null : "角色名称不能为空！",
		role_roleCode_not_null : "角色代码不能为空！",
		role_roleCode_format_error : "角色代码必须为字母！"
};

/**
 * 公文模版
 */
qy_lang.documentType = {
		doctypeName_not_null : "公文类型名称不能为空！",
		expr_not_null : "文号表达式不能为空！",
		beginNum_not_null : "编号计数器不能为空！",
		beginNum_int : "编号计数器必须为数值类型！",
		numLength_not_null : "编号位数不能为空！",
		numLength_int : "编号位数必须为数值类型！",
		docCateName_not_null : "请选择分类！",
		DispatchTypeName_not_null : "请选择公文类型！",
		secretLevel_not_null : "请选择密级！",
		huanji_not_null : "请选择缓急！",
		formCategory_not_null : "请选择公文登记单模版！",
		printTemplateCode_not_null : "维护代码不能为空！",
		docTemplateId_not_null: "请选择文件模版！",
		first_level_not_null:"请选择一级分类！",
		second_level_not_null:"请选择二级分类！"
};

/**
 * 套红模版
 */
qy_lang.docTemplate = {
		name_not_null : "模板名称不能为空！",
		category_not_null : "请选择模板类型！",
		roleSet_not_null : "请设置授权范围！",
		file_not_null : "请选择模板文件！"
};

/**
 * 添加印章
 */
qy_lang.webSign= {
		name_not_null : "印章名称不能为空！",
		password_not_null : "印章密码不能为空！",
		password_limit : "印章密码不能少于6位！",
		signType_not_null : "请选择印章类型！",
		picWidth_not_null : "图片宽度不能为空！",
		picWidth_isNumber: "图片宽度必须为数值类型！",
		picWidth_range: "图片宽度范围（1-500)",
		picHeight_not_null : "图片高度不能为空！",
		picHeight_isNumber: "图片高度必须为数值类型！",
		picHeight_range: "图片高度范围（1-500)",
		signSize_not_null : "请选择印章大小！",
		roleSet_not_null : "请设置授权范围！",
		groupSel_not_null	 : "请选择单位/部门！"
};

/*
 * 新建公文
 */
qy_lang.gather_file = {
		title_not_null : "请输入公文标题!",
		sourceDept_not_null : "请输入来文单位!",
		wenhao_not_null : "公文文号不能为空!"
};

/**
 * 个人桌面模块
 */
qy_lang.desktop = {

		reorder_screen_success : "屏幕顺序已设置成功！",
		add_screen_success : "屏幕添加成功！",
		confirm_delete_desktop_screen : "该屏幕下的快捷方式也将被删除，确定要删除吗？",
		delete_screen_success : "屏幕删除成功！",

		add_module_to_current_screen_success : "应用已添加到当前屏幕！",
		please_add_an_screen_before_the_action : "请先到'分屏设置'中添加一个屏幕！",
		current_screen_module_count_over_32 : "当前桌面应用将超过32个，继续添加可能会引起新增应用无法显示和使用！确认要继续吗？"
};

/*
 * 新建社区
 */
qy_lang.community = {
		name_is_exist : "社区名称已经存在！",
		name_not_null: "请输入法社区名称",
		orderNo_not_null:"请输入排序号"
};

/*
 * 新建社区风采
 */
qy_lang.communityStyle = {
		subject_not_null : "请输入标题",
		usernames_not_null : "请指定发布范围",
		partake_not_null : "请指定参与人数",
		compere_not_null : "请填写主持人",
		community_not_null : "请指定所属社区",
		createTime_not_null : "请选择发布时间",
		describe_not_null : "请输入发布内容",
		describe_count_max : "最大只能输入5000字符"
};
/**
 * 车辆信息维护
 */
qy_lang.carmaintain = {
		licensePlateNumber_not_null:"请输入车牌号",
		maintainType_not_null:"请选择维护类型",
		maintainContent_not_null:"请输入维护类型说明",
		maintainCause_not_null:"请输入维护原因",
		maintainTime_not_null:"请选择维护时间",
		operationUser_not_null:"请选择经办人",
		maintenance_not_null:"请输入维修厂",
		maintainPrice_not_null:"请输入维护费",
		remark_not_null:"请输入备注",
		maintainPrice_not_char:"维护费只能输入整数或小数",
		maintainPrice_not_max:"维护费只能输入6位整数和2位小数"
};

/**
 * 车辆加油信息
 */
qy_lang.carvehicle = {
		licensePlateNumber_not_null:"请选择车牌号",
		refuelTime_not_null:"请选择加油时间",
		refuelType_not_null:"请选择加油类型",
		amount_not_null:"加油数量不能为空",
		onePrice_not_null:"单价不能为空",
		price_not_null:"费用不能为空",
		operationUser_not_null:"请选择经办人",
		onePrice_not_max:"维护费只能输入6位整数和2位小数",
		price_not_max:"维护费只能输入6位整数和2位小数"
};
/**
 * 车辆信息管理
 */
qy_lang.messagecar = {
		carnumber_car:"请输入正确格式的车牌号",
		carnumber_not_null:"请输入车牌号",
		carmodel_not_null:"请输入厂牌型号",
		enginenumber_not_null:"请输入发动机号",
		carprice_not_null:"请输入车辆价格",
		carprice1_not_null:"只能输入整数和2位小数",
		cartype_not_null:"请选择车辆类型",
		type_not_null:"请选择当前状态",
		buy_time_not_null:"请输入购买日期",
		driver_not_null:"请输入司机姓名",
		userNames_not_null:"请选择申请权限"
};

/**
 * 新建申请管理
 */
qy_lang.applycaradd = {
		carnumber_not_null:"请输入车牌号",
		carnumber1_not_null:"请选择车牌号",
		operation_not_null:"请选择用车人",
		start_time_not_null:"请输入开始时间",
		end_time_not_null:"请输入结束时间",
		cause_not_null:"请输入用车事由",
		destination_not_null:"请输入目的地",
		mileage_not_null:"请输入里程数",
		mileage1_not_null:"请输入合法的里程数",
		appcontent_not_null:"请输入批复内容",
		appopinion_not_null:"请选择审批意见",
		appopinion1_not_null:"请选择审批人" ,
		destination_error : "请输入合法的里程数!" 
};

qy_lang.teskadd = {
		tixing_not_null:"请选择提醒设置！",
		tName_not_null:"请输入任务名称！",
		undertake_user_not_null:"请选择承办人！",
		task_content_not_null:"请填写完成目标！",
		start_not_null:"请选择开始时间！",
		end_not_null:"请选择结束时间！",
		task_llength_not_null:"任务时长不能为空",
		reporting_not_null :"任务汇报不能为空 ",
		percentage_max:"进度取值在0~100之间！",
		percentage_not_null: "完成进度不能为空",
		taskdelivery_not_null :"任务转交不能为空",
		percent_not_min:"输入进度必须大于当前进度" ,
		imp_not_null : "请选择重要度！"
};


qy_lang.meetadd = {
		meetingTop_not_null:"请输入会议标题",
		starttime_not_null:"请选择开始时间",
		endtime_not_null:"请选择结束时间",
		meet_content_not_null:"请输入会议内容",
		address_not_null:"请选择会议地点",
		User_not_null:"请选择出席人员",
		forward_not_null:"请设置短信提醒提前分钟",
		alertCount_not_null:"请设置短信提醒次数"
};

qy_lang.NewsAdd = {
		NewsAdd_not_null:"请输入新闻标题",
		NewsTop_only_One:"新闻标题已存在",
		publishScope_not_null:"请选择发布范围",
		type_not_null:"请选择新闻类型",
		publishTime_not_null:"请输入发布时间",
		text_not_null:"请输入新闻内容",
		User_not_null:"请选择出席人员"
};



/**
 * 新建会议室
 */
qy_lang.meetroomadd = {
		roomname_not_null : "会议室名称不能为空",
		roomcount_not_null : "会议室人数不能为空",
		roomaddress_not_null : "会议室位置不能为空",
		roommiao_not_null : "会议室描述不能为空",
		roomshe_not_null : "会议室设备状况不能为空",
		roomcount_num : "会议室人数只能为整数字",
		Count_null : "批复内容不能为空！"
};

/**
 * 新建会议室
 */
qy_lang.Newstype = {
		typeName_not_null : "请输入类型名称",
		typeNum_not_null : "请输入排序号",
		typeNum_type:"排序号只能为数字",
		typeName_only_One:"类型名称已存在"
};

/*
 * 公司信息 
 */
qy_lang.company = {
		company_companyName_not_null:"单位名称不能为空!",
		company_shortName_not_null:"单位简称不能为空!",
		company_sysName_not_null:"系统名称不能为空!"
};

/*
 * 抽奖信息
 */
qy_lang.lottery = {
		itemName_not_null:"奖项名称不能为空!",
		userName_not_null:"奖项内容部能为空!",
		amount_not_null:"奖项数量不能为空!",
		amount_format_error:"奖项数量必须是数字!",
		orderIndex_not_null:"抽奖顺序不能为空!",
		orderIndex_format_error:"抽奖顺序必须是数字!",
		title_not_null:"标题不能为空!",
		question_not_null:"所属试卷不能为空!",
		insertTime_not_null:"抽奖日期不能为空!"
};


/**
 * 调查问卷
 */
qy_lang.naire ={
	title_not_null :"请输入问卷名称",
	sm_not_null :"问卷说明不能为空",
	stime_not_null :"开始时间不能为空",
	etimee_not_null :"结束时间不能为空",
	fb_not_null	:"发布范围不能为空",
	des_max_length:"问卷说明不能超过200字！"
};
qy_lang.attendance={
		beginIp_not_null:"截止IP有值,起始IP不能为空!",
		endIp_not_null:"起始IP有值,截止IP不能为空!",
		beginIp_format_error:"起始IP输入格式有误,请重新输入",
		endIp_format_error:"截止IP输入格式有误,请重新输入",
		beginIp_not_null1:"起始IP不能为空!",
		endIp_not_null1:"截止IP不能为空",
		range_error:"起始IP与截止IP不在同一IP段!",
		rang_error_dayu:"起始IP与截止IP范围不正确!"
			
}
/**
	*新品汇
	*/
qy_lang.good={
	good_category_name:"新品汇类型不能为空!",
	good_category_orderList:""
}
/**
 * 手机端模块
 */
qy_lang.module = {
		module_name_not_null:"菜单名称不能为空！",
		module_code_not_null:"菜单代码不能为空！",
		module_name_is_exit:"菜单名称已经存在！",
		module_code_is_exit:"菜单代码已经存在！",
		module_order_is_exit:"菜单排序号已经存在！",
		module_parent_is_exit:"菜单父节点不存在！",
		module_order_not_null:"排序号不能为空！"
		
	};
/*
* 新公文
*/
qy_lang.domflow = {
	wenhao_not_null:"文号不能为空!",
	title_not_null:"标题不能为空!",
	fromDept_not_null:"来文单位不能为空!"
}

//报表
qy_lang.reportSet = {
		msg_set_report_at_least_one : "要批量授权,请至少选择其中一项！"
};
/*
* 报表基础数据
*/
qy_lang.report = {
	projectName_not_null:"项目名称不能为空!",
	note_count_max:"备注最大字数为1000，请修改。",
	sectionName_not_null:"标段名称不能为空!",
	sectionNumber_not_null:"标段编号不能为空",
	sectionNumber_isNum:"标段编号必须为数字"
};

/*
 * 自定义表单
 */
qy_lang.customForm = {
		form_name_not_null:"表单名称不能为空!",
		category_name_not_null:"表单分类不能为空!"
};
/*
 * 表单和流程分类
 */
qy_lang.formcategory = {
		cate_order_not_null:"排序号不能为空!",
		cate_name_not_null:"名称不能为空!",
		cate_name_repeat:"名称不能重复!"
}

/*
 * 电话号码 7-12位数字
 */
qy_lang.telphone = {
		telphone_is_not_num:"电话只能是数字!",
		telphone_length_out_of_range:"电话格式不正确，其输入7到12位数字!"
}

qy_lang.knowledge = {
		subject_not_null : "请输入标题！",
		keyword_not_null : "请输入关键字！",
		content_length_limit : "内容长度不能超过2000个字！",
		content_not_null : "请输入内容！",
		knowledgeType_not_null : "请选择分类名称！",
		TypeName_not_null:"请输入分类名称！"
};

qy_lang.courseUnit = {
		courseUnit_not_null:"视频名称不能为空!"
};


/**
 * 课程基本信息
 */
qy_lang.baseInfo = {
		courseName_not_null : "请输入课程名称!",
		courseName_is_repeat : "课程名称已存在!",
		speaker_not_null : "请输入主讲人!",
		chapters_not_null : "请输入章节数!",
		chapters_maxlength:"章节数必须大于0!",
		unitExercises_not_null:"请输入每章节最大抽题数!",
		courseBrief_not_null:"请输入课程简介!",
		courseBrief_maxLength:"课程简介不能超过500个字!"
};

/**
 * 
 */
qy_lang.question={
		    questionTitle_not_null:"请输入题干!",
			questionItem_not_null:"请输入选项内容!",
			grade_not_null:"请输入考题分值!",
			grade_is_valid:"分值必须为正整数!",
			questionParse_max_length:"试题解析不能超过500个字!"
}
/**
 * 试卷设置
 */
qy_lang.examSet = {
		testName_not_null : "请输入试卷标题！",
		intro_not_null : "请输入试卷说明！",
		questionNums_is_null : "请输入考试题数！",
		questionNums_min_length : "设置的考试题数必须大于0!",
		questionNums_is_out : "设置的考试题数超过最大考试题数，请重新设置！",
		testTime_is_null : "请输入考试时长！",
		beginTime_is_null : "请输入考试开始时间！",
		endTime_is_null : "请输入考试结束时间！",
		publishUser_not_null:"请选择发布范围！"
};

/**
 * 课程基本信息
 */
qy_lang.capital = {
		capitalName_not_null : "资产名称不能为空!",
		capitalGroupId_not_null : "资产组不能为空!",
		capitalPrice_format_error : "资产单价必须是数字!",
		capitalNum_not_null : "资产数量不能为空!",
		capitalNum_format_error : "资产数量必须是大于0的数字!"
};

/**
 * 
 */
qy_lang.transfer = {
		transfer_type_not_null : "请选择调动类型!",
		transfer_date_not_null : "调动日期不能为空!",
		effective_date_not_null : "调动生效日期不能为空!",
		beforeGroup_not_null : "调动前部门不能为空!",
		postGroup_not_null : "调动后部门不能为空!",
		postRole_not_null : "调动后角色不能为空!"
};
/**
 * 人事档案职称
 */
qy_lang.recordTitle = {
		title_not_null : "职称名称不能为空!",
		access_type_not_null : "请选择获取方式!"
};
/**
 * 
 */
qy_lang.work = {
		work_position_not_null : "担任的职务不能为空!",
		work_department_not_null : "所在部门不能为空!",
		work_startDate_not_null : "开始时间不能为空!",
		work_endDate_not_null : "结束时间不能为空!",
		work_workUnit_not_null : "工作单位不能为空!",
		work_leavingReasons_not_null : "离职原因不能为空!",
	    work_comparison_time: "结束日期不能小于开始日期!"
};


/**
 * 
 */
qy_lang.relation = {
		relation_memberName_not_null : "成员姓名不能为空!",
		relation_relation_not_null : "与本人的关系不能为空!",
		relation_personalPhone_not_null : "个人电话不能为空!",
		relation_homePhone_not_null : "家庭电话不能为空!",
		relation_homeAddress_not_null : "家庭地址不能为空!",
		relation_phone_not : "电话格式不正确!",
};

/**
 *@param str
 *            奖惩的信息
 * @returns 格式化后信息
 */
qy_lang.penalties = {
    penalties_type_not_null : "请选择奖惩类型!",
    penalties_date_not_null : "奖惩日期不能为空!",
    penalties_penalties_money_not_null : "奖惩金额过大!",
    penalties_project_not_null : "奖惩项目不能为空!",
    penalties_penalties_money_not_number : "请输入数字!"
};

/**
 *@param str
 *            学习信息
 * @returns 格式化后信息
 */
qy_lang.recordLearn = {
	major_not_null : "所学专业不能为空!",
	education_not_null : "所获学历不能为空!",
	school_not_null : "所在院校不能为空!",
	reterence_not_null : "证明人不能为空!",
	date_not_null : "结束日期不能小于开始日期!",
};

/**
 *@param str
 *            培训信息
 * @returns 格式化后信息
 */
qy_lang.recordTraining = {
		trainPlanName_not_null : "培训计划不能为空!",
		trainMechanism_not_null : "培训机构不能为空!",
		trainDate_not_null : "培训日期不能为空!",
		trainMoney_not_isNumber:"请输入数字!"
};

/**
 *@param str
 *            离职信息
 * @returns 格式化后信息
 */
qy_lang.recordLeave = {
	leaveType_not_null : "离职类型不能为空!",
	intended_not_null : "拟离职日期不能为空!",
	actual_not_null : "实际离职日期不能为空!",
	wage_not_null : "工资截止日期不能为空!",
	date_not_error : "申请日期不能大于离职日期!",
	leaveWage_not_number : "薪资请输入数字!",
	leaveWage_not__big : "薪资输入过大!",
};

/**
 * 获取格式化后的提示信息
 * 
 * @param str
 *            调用的信息
 * @returns 格式化后信息
 */
function sprintf(str) {
	var array = str.split("&&");
	var msgArray;
	var paramArray;
	var result = qy_lang;
	if (array.length == 1) {
		// 没有需要替换的参数
		msgArray = array[0].split(".");

		for (var i = 0; i < msgArray.length; i++) {
			result = result[msgArray[i]];
		}
	} else {
		msgArray = array[0].split(".");
		paramArray = array[1].split("##");

		for (var i = 0; i < msgArray.length; i++) {
			result = result[msgArray[i]];
		}
		result = result.format(paramArray);
	}
	return result;
}

String.prototype.format = function() {
	if (arguments.length == 0)
		return this;
	var paramArray = arguments[0];
	for (var s = this, i = 0; i < paramArray.length; i++) {
		s = s.replace(new RegExp("\\{" + i + "\\}", "g"), paramArray[i]);
	}
	return s;
};
