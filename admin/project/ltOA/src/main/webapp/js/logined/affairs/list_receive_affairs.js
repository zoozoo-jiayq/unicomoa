$(document).ready(function() {
	// 清除table页的cookie数据
	$.removeTableCookie('SpryMedia_DataTables_myTable_list_receive_affairs.jsp');
	queryReceiveAffairs();

	// 头部全选复选框
	$("#myTable").delegate("#total", "click", function(event) {
		checkTotal();
		event.stopPropagation();
	});
	
	// 子项复选框按钮
	$("#myTable").delegate(":checkbox[name='chk']", "click", function(event) {
		checkChange();
		event.stopPropagation();
	});

	// 单击删除
	$("#deleteBtn").click(function() {
		deleteBatchAffairs();
		return false;
	});

	// 更新事务提醒为已读
	$("#updateReadedBtn").click(function() {
		updateReadedAffairs();
		return false;
	});

	// 删除所有已读提醒
	$("#deleteAllReaded").click(function() {
		deleteAllReaded();
		return false;
	});

	// 全部删除
	$("#deleteAllBtn").click(function() {
		deleteAllAffairs();
		return false;
	});

	// 全部标记为已读
	$("#updateAllReaded").click(function() {
		updateAllReaded();
		return false;
	});
});

function queryReceiveAffairs() {
	_checkedIds="";
	qytx.app.grid({
		id	:	"myTable",
		url	:	basePath + "affairs/setup_getAffairsPage.action?remindFlag=1",
		iDisplayLength	:	tableDisplayLength,
		valuesFn	:	[ {
						        "aTargets" : [ 0 ],// 覆盖第一列
						        "fnRender" : function(oObj) {
							        return '<input name="chk" value="' + oObj.aData.id + '" type="checkbox" />';
						        }
						    },{
						        "aTargets" : [ 2 ],// 覆盖第三列
						        "fnRender" : function(oObj) {
						        	var smsType = oObj.aData.smsType;
						        	var remindUrl = oObj.aData.remindUrl;
							        remindUrl = window.encodeURI(remindUrl);
							        var html='';
							        if(oObj.aData.status_1=="2"){
							        	//html='<a style="text-decoration:none" ><span style="color:gray">' + oObj.aData.contentInfo+ '</span></a>';
										html='<a href="javascript:void(0);" onclick=getDetail("' + remindUrl + '","'+smsType+'","'+oObj.aData.id+'") style="color:gray">' + oObj.aData.contentInfo+ '</a>';
							        }else if(oObj.aData.status_1=="1"){
							        	html='<a href="javascript:void(0);" onclick=getDetail("' + remindUrl + '","'+smsType+'","'+oObj.aData.id+'")>' + oObj.aData.contentInfo+ '</a>';
							        }
							        
						        	if ("通讯薄" == smsType){
						        		return '<em class="icon_addrBook"></em>'+html;
						        	}else if ("工作日志" == smsType){
						        		return '<em class="icon_logBook"></em>'+html;
						        	}else if ("电子邮件" == smsType){
						        		return '<em class="icon_mailBox"></em>'+html;
						        	}else if ("通知公告" == smsType){
						        		return '<em class="icon_notice"></em>'+html;
						        	}else if ("日程安排" == smsType){
						        		return '<em class="icon_schedule"></em>'+html;
						        	}else if ("日程安排-周期性事务" == smsType){
						        		return '<em class="icon_schedule"></em>'+html;
						        	}else if ("文件柜" == smsType){
						        		return '<em class="icon_fileCabinet"></em>'+html;
						        	}else if (smsType.indexOf("工作流") == 0){
						        		return '<em class="icon_workflow"></em>'+html;
						        	}else if (smsType.indexOf("工作流") == 0){
						        		return '<em class="icon_workflow"></em>'+html;
						        	}else if (smsType.indexOf("工作流") == 0){
						        		return '<em class="icon_workflow"></em>'+html;
						        	}else if (smsType.indexOf("公文管理") == 0){
						        		return '<em class="icon_officialDoc"></em>'+html;
						        	}else if (smsType.indexOf("公文管理") == 0){
						        		return '<em class="icon_officialDoc"></em>'+html;
						        	}else if (smsType.indexOf("公文管理") == 0){
						        		return '<em class="icon_officialDoc"></em>'+html;
						        	}else if (smsType.indexOf("公文管理") == 0){
						        		return '<em class="icon_officialDoc"></em>'+html;
						        	}else if (smsType.indexOf("公文管理") == 0){
						        		return '<em class="icon_officialDoc"></em>'+html;
						        	}	
							        return  '<em class="icon_common"></em>'+html;
						        }
						    }, {
						        "aTargets" : [ 4 ],// 覆盖第五列
						        "fnRender" : function(oObj) {
							        // 查看详情
							        var remindUrl = oObj.aData.remindUrl;
							        remindUrl = window.encodeURI(remindUrl);
							        var smsType = oObj.aData.smsType;
							        if(oObj.aData.status_1=="1"){
							        	 return '<a href="javascript:void(0);" onclick=getDetail("' + remindUrl + '","'+smsType+'","'+oObj.aData.id+'")>查看</a>';
							        }else if(oObj.aData.status_1=="2"){
							        	//return '<a   style="text-decoration:none"  ><span style="color:gray">查看</span></a>';
										return '<a href="javascript:void(0);" onclick=getDetail("' + remindUrl + '","'+smsType+'","'+oObj.aData.id+'") style="color:gray">查看</a>';
							        }
						        }
						    } ]
	});
}

/**
 * 头部全选记录
 */
function checkTotal() {
	var isTotalCheck = $("input:checkbox[id='total']").prop("checked");
	var checkNum = 0;
	if (isTotalCheck) {
		$("input:checkbox[name='chk']").prop("checked", function(i, val) {
			checkNum = checkNum + 1;
			return true;
		});
	} else {
		$("input:checkbox[name='chk']").prop("checked", false);
	}
}

/**
 * 批量删除事务信息信息
 */
function deleteBatchAffairs() {
	// 获取选择删除的事务提醒Id
	var chkbox = document.getElementsByName("chk");
	var ids = "";
	var selLen = 0;
	var arr;
	if(_checkedIds!=null&&_checkedIds!=""){
		var checkedIds=_checkedIds.substring(0, _checkedIds.length-1);
		var arr=checkedIds.split(",");
		for (var i = 0; i < arr.length; i++) {
			ids += '&affairsId='+arr[i];
			selLen++;
		}
	}
	if (selLen == 0) {
		qytx.app.dialog.alert(sprintf("affairs.msg_del_at_least_one"));
		return;
	}

	// 删除事务提醒
	qytx.app.dialog.confirm(sprintf("affairs.msg_confirm_info"), function() {
		qytx.app.ajax({
		    url : basePath + "affairs/setup_deleteAffairs.action?type=delete",
		    type : "post",
		    dataType : 'text',
		    data : ids,
		    success : function(data) {
			    if (data == "success") {
				    //queryReceiveAffairs();
//			    	window.location.reload();
			    	_checkedIds="";
			    	queryReceiveAffairs();
			    	return false;
			    } else {
			    	qytx.app.dialog.alert(sprintf("affairs.msg_del_failure"));
			    }
		    }
		});
	}, function() {
		return;
	});
}

/**
 * 批量设置事务信息信息为已读
 */
function updateReadedAffairs() {
	// 获取选择设置的事务提醒Id
	var chkbox = document.getElementsByName("chk");
	var ids = "";
	var selLen = 0;
	for ( var i = 0; i < chkbox.length; i++) {
		if (chkbox[i].checked) {
			ids += '&affairsId=' + chkbox[i].value;
			selLen++;
		}
	}
	if (selLen == 0) {
		qytx.app.dialog.alert(sprintf("affairs.msg_read_at_least_one"));
		return;
	}

	// 设置为已读
	qytx.app.dialog.confirm(sprintf("affairs.msg_read_confirm_info"), function() {
		qytx.app.ajax({
		    url : basePath + "affairs/setup_updateReadedAffairs.action?type=update",
		    type : "post",
		    dataType : 'text',
		    data : ids,
		    success : function(data) {
			    if (data == "success") {
				    queryReceiveAffairs();
			    } else {
			    	qytx.app.dialog.alert(sprintf("affairs.msg_readed_error"));
			    }
		    }
		});
	}, function() {
		return;
	});
}

/**
 * 删除所有已读提醒
 */
function deleteAllReaded() {
	// 删除所有已读提醒
	qytx.app.dialog.confirm(sprintf("affairs.msg_del_read_confirm_info"), function() {
		qytx.app.ajax({
		    url : basePath + "affairs/setup_deleteAllReaded.action",
		    type : "post",
		    dataType : "text",
		    success : function(data) {
			    if ("success" == data) {
				    queryReceiveAffairs();
			    } else {
			    	qytx.app.dialog.alert(sprintf("affairs.msg_del_failure"));
			    }
		    }
		});
	}, function() {
		return;
	});
}

/**
 * 全部删除
 */
function deleteAllAffairs() {
	// 删除所有提醒
	qytx.app.dialog.confirm(sprintf("affairs.msg_del_receive_confirm_info"), function() {
		qytx.app.ajax({
		    url : basePath + "affairs/setup_deleteAllReaded.action",
		    type : "post",
		    dataType : "text",
		    success : function(data) {
			    if ("success" == data) {
				    queryReceiveAffairs();
			    } else {
			    	qytx.app.dialog.alert(sprintf("affairs.msg_del_failure"));
			    }
		    }
		});
	}, function() {
		return;
	});
}

/**
 * 全部标记为已读
 */
function updateAllReaded() {
	// 删除所有提醒
	// art.dialog.confirm("确认要设置所有事务提醒信息为已读吗?", function () {
	qytx.app.ajax({
	    url : basePath + "affairs/setup_updateAllReaded.action",
	    type : "post",
	    dataType : "text",
	    success : function(data) {
		    if ("success" == data) {
			    queryReceiveAffairs();
		    } else {
		    	qytx.app.dialog.alert(sprintf("affairs.msg_readed_error"));
		    }
	    }
	});
	// }, function () {
	// return;
	// });
}

function getDetail(urlStr,smsType,id) {
	window.top.addTab(Math.random(), basePath+urlStr, smsType);
	var url=basePath+"affairs/setup_updateReadedAffairs.action?affairsId="+id;
	qytx.app.ajax({
		url : url,
		type : "post",
		dataType :'text',
		success : function(data) {
			if(data == "success") {
				window.location.reload();
			}
		}
	});
}

/**
 * 子项复选框变更
 */
function checkChange(){
	if ($('#myTable :checkbox[name="chk"][checked="checked"]').length ==
		$('#myTable :checkbox[name="chk"]').length){
		$("input:checkbox[id='total']").prop("checked", true);
	}else{
		$("input:checkbox[id='total']").prop("checked", false);
	}
}