$(document).ready(function() {	
	// 清除table页的cookie数据
	$.removeTableCookie('SpryMedia_DataTables_myTable_list_send_affairs.jsp');
	_checkedIds="";
	querySendAffairs();
	
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
    
    //删除已提醒收信人提醒
    $("#deleteAllReaded").click(function(){
    	deleteAllSendReaded();
        return false;
    });
    
    //全部删除
    $("#deleteAllBtn").click(function(){
    	deleteAllAffairs();
        return false;
    });

    //删除收信人已删除提醒
    $("#deleteToUserDeleted").click(function(){
    	deleteToUserDeleted();
        return false;
    });
    
});

function querySendAffairs(){
	_checkedIds="";
	$('#myTable').dataTable({
        "bDestroy":true,
        "bProcessing": true,
        'bServerSide': true,
        'fnServerParams': function ( aoData ) {
         },
         "sAjaxSource": basePath+"affairs/setup_getSendAffairsPage.action",//获取发送的事务提醒列表
        "sServerMethod": "POST",
        "sPaginationType": "full_numbers",
        "bPaginate": true, //翻页功能
        "bStateSave": true,//状态保存，使用了翻页或者改变了每页显示数据数据
        "bLengthChange": false, //改变每页显示数据数量
        "bFilter": false, //过滤功能
        "bSort": false, //排序功能
        "bInfo": true,//页脚信息
        "bAutoWidth": false,//自动宽度
        "iDisplayLength":20, //每页显示多少行
        "aoColumns": [               
            {"sTitle":"<input type='checkbox' id='total'/>", "mDataProp": null },
            {"sTitle":'收信人', "mDataProp": "toUserName" },
            {"sTitle":'内容', "mDataProp": "contentInfo" ,"sClass": "longTxt iconText"},
            {"sTitle":'发送时间', "mDataProp": "sendTime"  },
            {"sTitle":'状态', "mDataProp": null },
            {"sTitle":'操作', "mDataProp": null }
        ],  
        "oLanguage": {
            "sUrl": basePath+"plugins/datatable/cn.txt" //中文包
        },
        "fnDrawCallback": function (oSettings) {
        	$('#myTable tbody  tr td[class="longTxt iconText"]').each(function() {
				this.setAttribute('title', $(this).text());
			});
            //重置iframe高度
            window.parent.frameResize();
            _getChecked();
        },
        "fnInitComplete": function () {
            //重置iframe高度
            window.parent.frameResize();
        },
		"aoColumnDefs":[{
	             "aTargets": [0],//覆盖第一列
	             "fnRender": function ( oObj ) {
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
		    },{
	             "aTargets": [4],//覆盖第五列
	             "fnRender": function ( oObj ) {
	            	 // 查看详情 
	            	 var remindFlag=oObj.aData.remindFlag;
	            	 if ('0' == remindFlag){
	            		 return '<div class="starsPop0"><span title="未确认提醒" class="new"></span></div>';
	            	 }else if("1" == remindFlag){
	            		 return '<div class="starsPop0"><span title="对方未读的提醒" class="noread"></span></div>';
	            	 }else if("2" == remindFlag){
	            		 return '<div class="starsPop0"><span title="对方已读提醒" class="readed"></span></div>';
	            	 }else if ("3" == remindFlag){
	            		 return '<div class="starsPop0"><span title="收信人已删除" class="delMail"></span></div>';
	            	 }
	            	 return remindFlag;
	             }
	       },{
	             "aTargets": [5],//覆盖第六列
	             "fnRender": function ( oObj ) {
	            	 var id=oObj.aData.id;
	            	 
	            	 // 只有已阅读的才能重发
	            	 var remindFlag=oObj.aData.remindFlag;
	            	 if (2 == remindFlag || 3 == remindFlag){
	            		 return '<a href="javascript:void(0);" onclick=retransmission("'+id+'")>重发</a>';
	            	 }
	            	 else{
	            		 return "";
	            	 }
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
    	art.dialog.alert(sprintf("affairs.msg_del_at_least_one"));
    	return;
    }
	 
	//删除事务提醒
	art.dialog.confirm(sprintf("affairs.msg_confirm_info"), function () {
       $.ajax({
			url : basePath+"affairs/setup_deleteSendAffairs.action?type=delete",
			type : "post",
			dataType :'text',
			data: ids,
			success : function(data) {
				if(data == "success") {
					querySendAffairs();
				} else {
					art.dialog.alert(sprintf("affairs.msg_del_failure"));
				}
			}
		});
	}, function () {
	    return;
	});
}

/**
 * 删除已提醒收信人提醒
 */
function deleteAllSendReaded(){
	//删除已提醒收信人提醒
	art.dialog.confirm(sprintf("affairs.msg_del_readed_confirm_info"), function () {
		$.ajax({
		      url:basePath+"affairs/setup_deleteAllSendReaded.action",
		      type:"post",
		      dataType: "text",
		      success: function(data){
			      if("success" == data){
			    	  querySendAffairs();
			      }else{
			    	  art.dialog.alert(sprintf("affairs.msg_del_failure"));
			      }
		      }
		 });
	}, function () {
	    return;
	});
}

/**
 * 全部删除
 */
function deleteAllAffairs(){
	//删除所有提醒
	art.dialog.confirm(sprintf("affairs.msg_del_all_confirm_info"), function () {
		$.ajax({
		      url:basePath+"affairs/setup_deleteAllSendAffairs.action",
		      type:"post",
		      dataType: "text",
		      success: function(data){
			      if("success" == data){
			    	  querySendAffairs();
			      }else{
			    	  sprintf("affairs.msg_del_failure")
			      }
		      }
		 });
	}, function () {
	    return;
	});
}

/**
 * 删除收信人已删除提醒
 */
function deleteToUserDeleted(){
	//删除收信人已删除提醒
	art.dialog.confirm(sprintf("affairs.msg_del_deleted_confirm_info"), function () {
		$.ajax({
		      url:basePath+"affairs/setup_deleteToUserDeleted.action",
		      type:"post",
		      dataType: "text",
		      success: function(data){
			      if("success" == data){
			    	  querySendAffairs();
			      }else{
			    	  art.dialog.alert(sprintf("affairs.msg_del_failure"));
			      }
		      }
		 });
	}, function () {
	    return;
	});
}

/**
 * 重发消息
 * @param id
 */
function retransmission(id){
	$.ajax({
	      url:basePath+"affairs/setup_retransmissionAffairs.action?retransmissionId="+id,
	      type:"post",
	      dataType: "text",
	      success: function(data){
		      if("success" == data){
		    	  querySendAffairs();
		      }else{
		    	  art.dialog.alert(sprintf("affairs.msg_retransmission_error"));
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