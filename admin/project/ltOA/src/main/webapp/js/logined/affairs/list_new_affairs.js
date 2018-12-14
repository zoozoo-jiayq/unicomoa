$(document).ready(function() {	
	// 清除table页的cookie数据
	$.removeTableCookie('SpryMedia_DataTables_myTable_list_new_affairs.jsp');
	
	queryReceiveAffairs();
	//头部全选复选框
	$("#myTable").delegate("#total", "click", function(event){    
	   	checkTotal();
		event.stopPropagation();
    });
	
	// 子项复选框按钮
	$("#myTable").delegate(":checkbox[name='chk']", "click", function(event) {
		checkChange();
		event.stopPropagation();
	});
	
    //单击删除
    $("#deleteBtn").click(function(){
    	deleteBatchAffairs();
        return false;
    });
    
    //单击全部删除
    $("#deleteAllBtn").click(function(){
    	deleteAllAffairs();
        return false;
    });
});

function queryReceiveAffairs(){
	_checkedIds="";
	qytx.app.grid({
		id	:	"myTable",
		url	:	basePath+"affairs/setup_getAffairsPage.action?remindFlag=0",
		iDisplayLength:	tableDisplayLength,
		valuesFn	:	[{
					            "aTargets": [0],//覆盖第一列
					            "fnRender": function ( oObj ) {
					                return '<input name="chk" value="' + oObj.aData.id + '" type="checkbox" />';
					            }
					      },{
						        "aTargets" : [ 2 ],// 覆盖第三列
						        "fnRender" : function(oObj) {
						        	var smsType = oObj.aData.smsType;
						        	var remindUrl = oObj.aData.remindUrl;
							        remindUrl = window.encodeURI(remindUrl);
						        	if ("通讯薄" == smsType){
						        		return '<em class="icon_addrBook"></em>'+ '<a href="javascript:void(0);" onclick=getDetail("'+remindUrl+'","'+oObj.aData.id+'","'+smsType+'")>' +
						        		  oObj.aData.contentInfo + '</a>';
						        	}else if ("工作日志" == smsType){
						        		return '<em class="icon_logBook"></em>'+ '<a href="javascript:void(0);" onclick=getDetail("'+remindUrl+'","'+oObj.aData.id+'","'+smsType+'")>' +
						        		  oObj.aData.contentInfo + '</a>';
						        	}else if ("电子邮件" == smsType){
						        		return '<em class="icon_mailBox"></em>'+ '<a href="javascript:void(0);" onclick=getDetail("'+remindUrl+'","'+oObj.aData.id+'","'+smsType+'")>' +
						        		  oObj.aData.contentInfo + '</a>';
						        	}else if ("通知公告" == smsType){
						        		return '<em class="icon_notice"></em>'+ '<a href="javascript:void(0);" onclick=getDetail("'+remindUrl+'","'+oObj.aData.id+'","'+smsType+'")>' +
						        		  oObj.aData.contentInfo + '</a>';
						        	}else if ("日程安排" == smsType){
						        		return '<em class="icon_schedule"></em>'+ '<a href="javascript:void(0);" onclick=getDetail("'+remindUrl+'","'+oObj.aData.id+'","'+smsType+'")>' +
						        		  oObj.aData.contentInfo + '</a>';
						        	}else if ("日程安排-周期性事务" == smsType){
						        		return '<em class="icon_schedule"></em>'+ '<a href="javascript:void(0);" onclick=getDetail("'+remindUrl+'","'+oObj.aData.id+'","'+smsType+'")>' +
						        		  oObj.aData.contentInfo + '</a>';
						        	}else if ("文件柜" == smsType){
						        		return '<em class="icon_fileCabinet"></em>'+ '<a href="javascript:void(0);" onclick=getDetail("'+remindUrl+'","'+oObj.aData.id+'","'+smsType+'")>' +
						        		  oObj.aData.contentInfo + '</a>';
						        	}
						        	else if ("工作流：提醒下一步经办人" == smsType){
						        		return '<em class="icon_workflow"></em>'+ '<a href="javascript:void(0);" onclick=getDetail("'+remindUrl+'","'+oObj.aData.id+'","'+smsType+'")>' +
						        		  oObj.aData.contentInfo + '</a>';
						        	}else if ("工作流：提醒流程发起人" == smsType){
						        		return '<em class="icon_workflow"></em>'+ '<a href="javascript:void(0);" onclick=getDetail("'+remindUrl+'","'+oObj.aData.id+'","'+smsType+'")>' +
						        		  oObj.aData.contentInfo + '</a>';
						        	}else if ("工作流：提醒流程所有人员" == smsType){
						        		return '<em class="icon_workflow"></em>'+ '<a href="javascript:void(0);" onclick=getDetail("'+remindUrl+'","'+oObj.aData.id+'","'+smsType+'")>' +
						        		  oObj.aData.contentInfo + '</a>';
						        	}else if ("公文管理：发文核稿" == smsType){
						        		return '<em class="icon_officialDoc"></em>'+ '<a href="javascript:void(0);" onclick=getDetail("'+remindUrl+'","'+oObj.aData.id+'","'+smsType+'")>' +
						        		  oObj.aData.contentInfo + '</a>';
						        	}else if ("公文管理：收文登记" == smsType){
						        		return '<em class="icon_officialDoc"></em>'+ '<a href="javascript:void(0);" onclick=getDetail("'+remindUrl+'","'+oObj.aData.id+'","'+smsType+'")>' +
						        		  oObj.aData.contentInfo + '</a>';
						        	}else if ("公文管理：领导批阅" == smsType){
						        		return '<em class="icon_officialDoc"></em>'+ '<a href="javascript:void(0);" onclick=getDetail("'+remindUrl+'","'+oObj.aData.id+'","'+smsType+'")>' +
						        		  oObj.aData.contentInfo + '</a>';
						        	}else if ("公文管理：收文分发" == smsType){
						        		return '<em class="icon_officialDoc"></em>'+ '<a href="javascript:void(0);" onclick=getDetail("'+remindUrl+'","'+oObj.aData.id+'","'+smsType+'")>' +
						        		  oObj.aData.contentInfo + '</a>';
						        	}else if ("公文管理：收文阅读" == smsType){
						        		return '<em class="icon_officialDoc"></em>'+ '<a href="javascript:void(0);" onclick=getDetail("'+remindUrl+'","'+oObj.aData.id+'","'+smsType+'")>' +
						        		  oObj.aData.contentInfo + '</a>';
						        	}
						        	else if ("工作流" == smsType){
						        		return '<em class="icon_workflow"></em>'+ '<a href="javascript:void(0);" onclick=getDetail("'+remindUrl+'","'+oObj.aData.id+'","'+smsType+'")>' +
						        		  oObj.aData.contentInfo + '</a>';
						        	}else if ("公文管理" == smsType){
						        		return '<em class="icon_officialDoc"></em>'+ '<a href="javascript:void(0);" onclick=getDetail("'+remindUrl+'","'+oObj.aData.id+'","'+smsType+'")>' +
						        		  oObj.aData.contentInfo + '</a>';
						        	}		
							        return  '<em class="icon_common"></em>'+ '<a href="javascript:void(0);" onclick=getDetail("'+remindUrl+'","'+oObj.aData.id+'","'+smsType+'")>' +
					       		  oObj.aData.contentInfo + '</a>';
						        }
						    },{
					            "aTargets": [4],//覆盖第五列
					            "fnRender": function ( oObj ) {
					           	 // 查看详情 
					           	 var remindUrl=oObj.aData.remindUrl;
					           		var smsType = oObj.aData.smsType;
					           	 remindUrl = window.encodeURI(remindUrl);
					           	 return '<a href="javascript:void(0);" onclick=getDetail("'+remindUrl+'","'+oObj.aData.id+'","'+smsType+'")>查看</a>';
					            }
					      }]
	});
	
}

/**
 * 头部全选记录
 */
function checkTotal() {
	var isTotalCheck=$("input:checkbox[id='total']").prop("checked");
	var checkNum=0;
	if(isTotalCheck){
		$("input:checkbox[name='chk']").prop("checked", function( i, val ) {
			checkNum=checkNum+1;
            return true;
        });
	}else{
		$("input:checkbox[name='chk']").prop("checked", false);
	}
}

/**
 * 批量删除事务信息信息
 */
function deleteBatchAffairs() {
    //获取选择删除的事务提醒Id
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
    
	//删除任务脚本
	qytx.app.dialog.confirm(sprintf("affairs.msg_confirm_info"), function () {
       qytx.app.ajax({
			url : basePath+"affairs/setup_deleteAffairs.action?type=delete",
			type : "post",
			dataType :'text',
			data: ids,
			success : function(data) {
				if(data == "success") {    
					// 删除成功不提示信息
					queryReceiveAffairs();
				} else {
					qytx.app.dialog.alert(sprintf("affairs.msg_del_failure"));
				}
			}
		});
	}, function () {
	    return;
	});
}

/**
 * 点击详情操作
 * @param urlStr 访问URL
 */
function getDetail(urlStr, affairsId,smsType){
	// 同时更新状态为已读
	qytx.app.ajax({
	    url : basePath + "affairs/setup_updateReadedAffairs.action?type=update&affairsId="+affairsId,
	    type : "post",
	    dataType : 'text',
	    success : function(data) {
		    if (data == "success") {
			    queryReceiveAffairs();
		    } else {
//			    art.dialog.alert(sprintf("affairs.msg_readed_error"));
		    }
	    }
	});
	
	window.top.addTab(Math.random(), basePath+urlStr, smsType);
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

/**
 * 全部删除
 */
function deleteAllAffairs() {
	// 删除所有已接受和已发送的提醒
	qytx.app.dialog.confirm(sprintf("affairs.msg_del_unread_confirm_info"), function() {
		qytx.app.ajax({
		    url : basePath + "affairs/setup_deleteAllUnReaded.action",
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