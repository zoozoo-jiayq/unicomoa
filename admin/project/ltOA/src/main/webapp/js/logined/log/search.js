$(document).ready(function(e) {
			// 查询按钮绑定事件
			$("#search").bind("click", function() {
				var startTime = $("#startTime").val();
				var endTime = $("#endTime").val();
				if(startTime && endTime){
					if(startTime>endTime){
						qytx.app.dialog.alert("请正确输入开始日期和结束日期！");
						return;
					}else{
						getLogList();
					}
				}
				getLogList();
			});
			$("#exportLog").bind("click", function() {
				exportLog();
			});
			// 查询按钮绑定事件
			$("#searchNew").bind("click", function() {
						var max = $("#max").val();
						if (max <= 0 || max > 300) {
							$("#max").val(300);
							qytx.app.dialog.alert("请输入0--300内的整数！");
							return;
						}
						getLogList();
					//	$("#searchDiv").css("display", "none");
					//	$("#tableDiv").css("display", "block");
					});
			// 头部全选复选框
			$("#Table").delegate("#total", "click", function(event) {
						checkTotal();
						event.stopPropagation();
					});
			// 删除
			$(".delete").live("click", function() {
						var cks = $("input[name='ck']:checked");

						if (cks.length == 0) {
							qytx.app.dialog.alert("请选择待删除的信息! ");
							return;
						}
						qytx.app.dialog.confirm('确认要删除吗?', function() {
									deleteObject();
								}, function() {

								});
					});

			// 列表页返回
			$("#returnback").bind("click", function() {
						returnback();
					});
			
			getLogList();
			
		});

// 处理事件的函数
function FSubmit(e) {
	if (e == 13) {
		// 获取search内容
		jQuery("#search").trigger("click");
		e.returnValue = false;
		// 返回false，在没有使用document.loginForm.submit()时可用于取消提交
	}
}

/**
 * 确定按钮
 * 
 * @return
 */
function searchMethod() {
	var searchType = $("input[name='searchType']:checked").val();
	if (searchType == 1) {// 查询
		getLogList();
		$("#searchDiv").css("display", "none");
		$("#searchDiv").css("display", "none");
		$("#tableDiv").css("display", "block");
	} else if (searchType == 2) {// 导出
		exportLog();
	} else if (searchType == 3) {// 删除
		deleteListByParam();
	}
}
/**
 * 头部全选记录
 */
function checkTotal() {
	var isTotalCheck = $("input:checkbox[id='total']").prop("checked");
	var checkNum = 0;
	if (isTotalCheck) {
		$("input:checkbox[name='ck']").prop("checked", function(i, val) {
					checkNum = checkNum + 1;
					return true;
				});
	} else {
		$("input:checkbox[name='ck']").prop("checked", false);
	}
}

/**
 * 选择记录
 */
function check() {
	var checkTotalNum = $("input:checkbox[name='ck']");
	var isAllChecked = true;
	checkTotalNum.each(function(i, val) {
				if ($(checkTotalNum[i]).prop("checked")) {
				} else {
					isAllChecked = false;
				}
			});
	if (!isAllChecked) {
		$("input:checkbox[id='total']").prop("checked", false);
	} else {
		$("input:checkbox[id='total']").prop("checked", true);
	}
}

/**
 * 批量删除
 * 
 * @return
 */
function deleteObject() {
	var cks = $("input[name='ck']:checked");

	if (cks.length == 0) {
		qytx.app.dialog.alert("请选择待删除的信息! ");
		return;
	}
	var deleteIds = "";
	// 修改选中行
	for (var i = 0; i < cks.length; i++) {
		deleteIds += $(cks[i]).val() + ",";
	}
	if (deleteIds != "") {
		deleteIds = deleteIds.substring(0, deleteIds.length - 1);
	}

	qytx.app.ajax({
				url : basePath + "log/log_deleteListByIds.action?deleteIds="
						+ deleteIds,
				type : "post",
				dataType : "html",
				success : function(data) {
					if (data == 0) {
						//art.dialog.alert("删除成功！", function() {
									getLogList();
						//		});

					} else {
						art.dialog.alert("删除失败！");
					}
				}
			});
}

/**
 * 查询日志获取列表
 */
function getLogList() {
	qytx.app.grid({
		id	:	"Table",
		url	:	basePath + "log/log_getLogList.action",
		bInfo:	false,
		selectParam:	{
							"logType":$("#logType").val(),
							"userIds":$.trim($("#userIds").val()),
							"startTime":$.trim($("#startTime").val()),
							"endTime":$.trim($("#endTime").val()),
							"ip":$.trim($("#ip").val()),
							"max":$("#max").val(),
							"userName":$("#loginUserName").val(),
							"remark":$.trim($("#remark").val())
						},
		valuesFn:	[ {
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
							} 
			//				else if (logType == 4) {
			//					logTypeName = "清除密码";
			//				} else if (logType == 5) {
			//					logTypeName = "非法IP登录";
			//				}
							else if (logType == 6) {
								logTypeName = "退出系统";
							} else if (logType == 7) {
								logTypeName = "用户添加";
							} else if (logType == 8) {
								logTypeName = "用户修改";
							} else if (logType == 9) {
								logTypeName = "用户删除";
							} 
			//				else if (logType == 10) {
			//					logTypeName = "用户离职";
			//				} 
							else if (logType == 11) {
								logTypeName = "登录密码修改";
							} else if (logType == 12) {
								logTypeName = "部门添加";
							} else if (logType == 13) {
								logTypeName = "部门修改";
							} else if (logType == 14) {
								logTypeName = "部门删除";
							} else if (logType == 15) {
								logTypeName = "公告删除";
							} else if (logType == 16) {
								logTypeName = "邮件发送";
							} else if (logType == 17) {
								logTypeName = "邮件删除";
							} 
			//				else if (logType == 18) {
			//					logTypeName = "按模块设置管理范围";
			//				}
							return logTypeName;
						}
					}]
	});
}

/**
 * 导出
 * 
 * @return
 */
function exportLog() {
	// 导出文件
	var logType = $("#logType").val();
	var userIds = $.trim($("#userIds").val());
	var startTime = $.trim($("#startTime").val());
	var endTime = $.trim($("#endTime").val());
	var ip = $.trim($("#ip").val());
	var remark = $.trim($("#remark").val());
	var userName = $("#loginUserName").val();
	var max = 10000000;

	document.location = basePath + "log/log_exportLog.action?logType="
			+ logType + "&userIds=" + userIds + "&startTime=" + startTime
			+ "&endTime=" + endTime + "&ip=" + ip + "&remark=" + remark
			+ "&max=" + max + "&userName=" + userName;
}

/**
 * 查询删除
 * 
 * @return
 */
function deleteListByParam() {
	var logType = $("#logType").val();
	var userIds = $.trim($("#userIds").val());
	var startTime = $.trim($("#startTime").val());
	var endTime = $.trim($("#endTime").val());
	var ip = $.trim($("#ip").val());
	var remark = $.trim($("#remark").val());
	var max = $("#max").val();
	var paramData = {
		'logType' : logType,
		'userIds' : userIds,
		'startTime' : startTime,
		'endTime' : endTime,
		'ip' : ip,
		'remark' : remark,
		'max' : max
	};

	var urlStr = basePath + "log/log_deleteListByParam.action";
	qytx.app.ajax({
				url : urlStr,
				type : "post",
				dataType : 'json',
				data : paramData,
				success : function(data) {
					//art.dialog.alert("删除成功！", function() {
								getLogList();
					//		});
				}
			});
}
/**
 * 返回搜索页面
 * 
 * @return
 */
function returnback() {
	$("#max").val(300);
	$("#searchDiv").css("display", "block");
	$("#tableDiv").css("display", "none");
}

/**
 * 清空共享人员
 * 
 * @return
 */
function clearUser() {
	$("#userIds").val("");
	$("#userNames").val("");
}
document.onkeydown = function(event){
	if(event.keyCode == 13){
		getLogList();
	}
}