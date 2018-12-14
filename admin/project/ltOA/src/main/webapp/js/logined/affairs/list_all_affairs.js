$(document).ready(function() {
	$("#tableArea").hide();
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
	
	// 单击确认
	$("#confirm").click(function() {
		var type = $('input[name="operationType"]:checked').val();
		if ('1' == type) {
			$("#searchArea").hide();
			$("#tableArea").show();
			queryAllAffairs();
		} else if ('2' == type) {
			// 导出
			exportAffairByVo();
		} else if ('3' == type) {
			// 删除
			deleteAffairByVo();
		}
		return false;
	});

	// 单击返回
	$("#backBtn").click(function() {
		$("#searchArea").show();
		$("#tableArea").hide();
		//重置iframe高度
        window.parent.frameResize();
		return false;
	});

	// 单击删除
	$("#deleteBtn").click(function() {
		deletAffairs();
		return false;
	});

	// 单击删除全部
	$("#deleteAllBtn").click(function() {
		deleteAllAffairs();
		return false;
	});
});

function queryAllAffairs() {
	_checkedIds="";
	$('#myTable').dataTable({
	    "bDestroy" : true,
	    "bProcessing" : true,
	    'bServerSide' : true,
	    'fnServerParams' : function(aoData) {
		    aoData.push({
		        "name" : "affairsVo.userType",
		        "value" : $("#userType").val()
		    }, // 人员类型
		    {
		        "name" : "affairsVo.userIds",
		        "value" : $.trim($("#userIds").val())
		    }, // 接收人
		    {
		        "name" : "affairsVo.smsType",
		        "value" : $("#smsType").val()
		    }, // 提醒类型
		    {
		        "name" : "affairsVo.startTime",
		        "value" : $.trim($("#startTime").val())
		    }, // 查询开始时间
		    {
		        "name" : "affairsVo.endTime",
		        "value" : $.trim($("#endTime").val())
		    }, // 查询结束时间
		    {
		        "name" : "affairsVo.contentInfo",
		        "value" : $.trim($("#contentInfo").val())
		    }, // 内容
		    {
		        "name" : "affairsVo.sortFiled",
		        "value" : $.trim($("#sortFiled").val())
		    }, // 排序字段
		    {
		        "name" : "affairsVo.sortType",
		        "value" : $.trim($("#sortType").val())
		    } // 排序方式
		    );
	    },
	    "sAjaxSource" : basePath + "affairs/setup_getAllAffairs.action",// 查询所有提醒信息
	    "sServerMethod" : "POST",
	    "sPaginationType" : "full_numbers",
	    "bPaginate" : false, // 翻页功能
	    "bLengthChange" : false, // 改变每页显示数据数量
	    "bFilter" : false, // 过滤功能
	    "bSort" : false, // 排序功能
	    "bInfo" : false,// 页脚信息
	    "bAutoWidth" : false,// 自动宽度
	    "iDisplayLength" : 1000000, // 每页显示多少行
	    "aoColumns" : [ {
	        "sTitle" : "<input type='checkbox' id='total'/>",
	        "mDataProp" : null
	    }, {
	        "sTitle" : $("#userType option:selected").text(),
	        "mDataProp" : "fromUserName"
	    }, {
	        "sTitle" : '内容',
	        "mDataProp" : "contentInfo",
	        "sClass" : "longTxt iconText"
	    }, {
	        "sTitle" : '发送时间',
	        "mDataProp" : "sendTime"
	    }, {
	        "sTitle" : '提醒',
	        "mDataProp" : "remindFlag"
	    }, {
	        "sTitle" : '操作',
	        "mDataProp" : null
	    } ],
	    "oLanguage" : {
		    "sUrl" : basePath + "plugins/datatable/cn.txt" // 中文包
	    },
	    "fnDrawCallback" : function(oSettings) {
	    	$('#myTable tbody  tr td[class="longTxt iconText"]').each(function() {
				this.setAttribute('title', $(this).text());
			});
		    window.parent.frameResize();
		    
		    _getChecked();
	    },
	    "fnInitComplete": function () {
            //重置iframe高度
            window.parent.frameResize();
        },
	    "aoColumnDefs" : [ {
	        "aTargets" : [ 0 ],// 覆盖第一列
	        "fnRender" : function(oObj) {
		        return '<input name="chk" value="' + oObj.aData.id + '" type="checkbox" />';
	        }
	    },{
	        "aTargets" : [ 2 ],// 覆盖第三列
	        "fnRender" : function(oObj) {
	        	var smsType = oObj.aData.smsType;
	        	if ("通讯薄" == smsType){
	        		return '<em class="icon_addrBook"></em>'+oObj.aData.contentInfo;
	        	}else if ("工作日志" == smsType){
	        		return '<em class="icon_logBook"></em>'+oObj.aData.contentInfo;
	        	}else if ("电子邮件" == smsType){
	        		return '<em class="icon_mailBox"></em>'+oObj.aData.contentInfo;
	        	}else if ("公告通知" == smsType){
	        		return '<em class="icon_notice"></em>'+oObj.aData.contentInfo;
	        	}else if ("日程安排" == smsType){
	        		return '<em class="icon_schedule"></em>'+oObj.aData.contentInfo;
	        	}else if ("日程安排-周期性事务" == smsType){
	        		return '<em class="icon_schedule"></em>'+oObj.aData.contentInfo;
	        	}else if ("文件柜" == smsType){
	        		return '<em class="icon_fileCabinet"></em>'+oObj.aData.contentInfo;
	        	}else if ("工作流：提醒下一步经办人" == smsType){
	        		return '<em class="icon_workflow"></em>'+oObj.aData.contentInfo;
	        	}else if ("工作流：提醒流程发起人" == smsType){
	        		return '<em class="icon_workflow"></em>'+oObj.aData.contentInfo;
	        	}else if ("工作流：提醒流程所有人员" == smsType){
	        		return '<em class="icon_workflow"></em>'+oObj.aData.contentInfo;
	        	}else if ("公文管理：发文核稿" == smsType){
	        		return '<em class="icon_officialDoc"></em>'+oObj.aData.contentInfo;
	        	}else if ("公文管理：收文登记" == smsType){
	        		return '<em class="icon_officialDoc"></em>'+oObj.aData.contentInfo;
	        	}else if ("公文管理：领导批阅" == smsType){
	        		return '<em class="icon_officialDoc"></em>'+oObj.aData.contentInfo;
	        	}else if ("公文管理：收文分发" == smsType){
	        		return '<em class="icon_officialDoc"></em>'+oObj.aData.contentInfo;
	        	}else if ("公文管理：收文阅读" == smsType){
	        		return '<em class="icon_officialDoc"></em>'+oObj.aData.contentInfo;
	        	}	        	
		        return  '<em class="icon_common"></em>'+oObj.aData.contentInfo;
	        }
	    }, {
	        "aTargets" : [ 5 ],// 覆盖第6列
	        "fnRender" : function(oObj) {
		        // 查看详情
		        var remindUrl = oObj.aData.remindUrl;
		        return '<a href="javascript:void(0);" onclick=getDetail("' + remindUrl + '")>详情</a>';
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
function deletAffairs() {
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
		art.dialog.alert(sprintf("affairs.msg_del_at_least_one"));
		return;
	}

	// 删除任务脚本
	art.dialog.confirm(sprintf("affairs.msg_confirm_info"), function() {
		$.ajax({
		    url : basePath + "affairs/setup_deleteSendAffairs.action?type=delete",
		    type : "post",
		    dataType : 'text',
		    data : ids,
		    success : function(data) {
			    if (data == "success") {
//				    art.dialog.alert('删除成功！');
				    queryAllAffairs();
			    } else {
			    	art.dialog.alert(sprintf("affairs.msg_del_failure"));
			    }
		    }
		});
	}, function() {
		return;
	});
}

/**
 * 删除所有事务提醒信息
 */
function deleteAllAffairs() {
	// 获取选择删除的事务提醒Id
	var chkbox = document.getElementsByName("chk");
	var ids = "";
	
	for ( var i = 0; i < chkbox.length; i++) {
		$(chkbox).attr("checked", true);
		ids += '&affairsId=' + chkbox[i].value;
	}
	$("#total").attr("checked", true);
	
	// 删除任务脚本
	art.dialog.confirm(sprintf("affairs.msg_confirm_info"), function() {
		$.ajax({
		    url : basePath + "affairs/setup_deleteSendAffairs.action?type=delete",
		    type : "post",
		    dataType : 'text',
		    data : ids,
		    success : function(data) {
			    if (data == "success") {
//				    art.dialog.alert('删除成功！');
				    queryAllAffairs();
			    } else {
			    	art.dialog.alert(sprintf("affairs.msg_del_failure"));
			    }
		    }
		});
	}, function() {
		return;
	});
}

/**
 * 点击详情操作
 * @param urlStr 访问URL
 */
function getDetail(urlStr){
	window.open(basePath+urlStr);
}

/**
 * 清空人员
 */
function clearPerson() {
	$("#userIds").val(null);
	$("#userNames").val(null);
}

/**
 * 选择部门人员控件，选择人员
 */
function selectPerson() {
	openSelectUser(3, selectCallBackForm, null, $("#userIds").val());// 选择人员
}

/**
 * 部门人员选择的回调函数
 */
function selectCallBackForm(data, param) {
	var userIds = "";
	var userNames = "";
	data.forEach(function(value, key) {
		userIds += value.Id + ",";
		userNames += value.Name + ",";
	});

	$("#userIds").val(userIds);
	$("#userNames").val(userNames);
}

/**
 * 导出事务提醒信息
 */
function exportAffairByVo() {
	var url = basePath + "affairs/setup_exportAffairsByVo.action?affairsVo.userType=" + $("#userType").val()
	        + "&affairsVo.userIds=" + $.trim($("#userIds").val()) + "&affairsVo.smsType=" + $("#smsType").val()
	        + "&affairsVo.startTime=" + $.trim($("#startTime").val()) + "&affairsVo.endTime="
	        + $.trim($("#endTime").val()) + "&affairsVo.contentInfo=" + $.trim($("#contentInfo").val())
	        + "&affairsVo.sortFiled=" + $.trim($("#sortFiled").val()) + "&affairsVo.sortType="
	        + $.trim($("#sortType").val());
	window.open(url);
}

/**
 * 根据vo删除事务提醒
 */
function deleteAffairByVo() {
	var paramData = {
	    "affairsVo.userType" : $("#userType").val(), // 人员类型
	    "affairsVo.userIds" : $.trim($("#userIds").val()), // 接收人
	    "affairsVo.smsType" : $("#smsType").val(), // 提醒类型
	    "affairsVo.startTime" : $.trim($("#startTime").val()), // 查询开始时间
	    "affairsVo.endTime" : $.trim($("#endTime").val()), // 查询结束时间
	    "affairsVo.contentInfo" : $.trim($("#contentInfo").val())
	// 内容
	};

	// 设置为已读
//	art.dialog.confirm('您确定要删除所选提醒吗？', function() {
		$.ajax({
		    url : basePath + "affairs/setup_deleteAffairsByVo.action",
		    type : "post",
		    dataType : 'text',
		    data : paramData,
		    success : function(data) {
			    if (data == "success") {
				    art.dialog.alert(sprintf("affairs.msg_del_vo_success"));
			    } else {
			    	art.dialog.alert(sprintf("affairs.msg_del_failure"));
			    }
		    }
		});
//	}, function() {
//		return;
//	});
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
