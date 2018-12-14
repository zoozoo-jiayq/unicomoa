$(document).ready(function() {
			getAllLogNums();
			getLogList();
		});

/**
 * 查询日志获取列表
 */
function getLogList() {
	qytx.app.grid({
		id	:	"Table",
		url	:	basePath + "log/log_getLogList.action",
		iDisplayLength:	10,
		bInfo:false,
		selectParam:	{
							"logType":0,
							"userIds":"",
							"startTime":"",
							"endTime":"",
							"ip":"",
							"max":$("#max").val(),
							"remark":""
						},
		valuesFn:	[{
						"aTargets" : [3],
						"fnRender" : function(oObj) {
							var logType = oObj.aData.logType;
							var logTypeName = "";
							if (logType == 0) {
								logTypeName = "全部日志";
							} else if (logType == 1) {
								logTypeName = "登录日志";
							} else if (logType == 2) {
								logTypeName = "密码错误";
							} else if (logType == 3) {
								logTypeName = "用户名错误";
							} else if (logType == 4) {
								logTypeName = "清除密码";
							} else if (logType == 5) {
								logTypeName = "非法IP登录";
							} else if (logType == 6) {
								logTypeName = "退出系统";
							} else if (logType == 7) {
								logTypeName = "用户添加";
							} else if (logType == 8) {
								logTypeName = "用户修改";
							} else if (logType == 9) {
								logTypeName = "用户删除";
							} else if (logType == 10) {
								logTypeName = "用户离职";
							} else if (logType == 11) {
								logTypeName = "用户修改登录密码";
							} else if (logType == 12) {
								logTypeName = "部门添加";
							} else if (logType == 13) {
								logTypeName = "部门修改";
							} else if (logType == 14) {
								logTypeName = "部门删除";
							} else if (logType == 15) {
								logTypeName = "公告通知管理";
							} else if (logType == 16) {
								logTypeName = "公共文件柜";
							} else if (logType == 17) {
								logTypeName = "邮件删除";
							} else if (logType == 18) {
								logTypeName = "按模块设置管理范围";
							}
							return logTypeName;
						}
					}]
	});
}
/**
 * 统计结果
 * 
 * @return
 */
function getAllLogNums() {
	var urlStr = basePath + "log/log_getAllLogNums.action";
	qytx.app.ajax({
				url : urlStr,
				type : "post",
				dataType : 'json',
				success : function(data) {
					$("#allDay").html(data.allDay);
					$("#allNum").html(data.allNum);
					$("#thisYearNum").html(data.thisYearNum);
					$("#thisMonthNum").html(data.thisMonthNum);
					$("#todayNum").html(data.todayNum);
					$("#averageNum").html(data.averageNum);
				}
			});
}