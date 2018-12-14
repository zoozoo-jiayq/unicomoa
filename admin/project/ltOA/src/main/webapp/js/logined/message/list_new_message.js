$(document).ready(function() {	
	// 清除table页的cookie数据
	$.removeTableCookie('SpryMedia_DataTables_myTable_list_new_message.jsp');
	
	queryReceiveMessage();
	
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
    	deleteBatchMessage();
        return false;
    });
});

function queryReceiveMessage(){
	_checkedIds="";
	qytx.app.grid({
		id	:	"myTable",
		url	:	basePath+"message/setup_getMessagePage.action?remindFlag=0",
		iDisplayLength	:	10,
		valuesFn	:	[{
	            "aTargets": [0],//覆盖第一列
	            "fnRender": function ( oObj ) {
	                return '<input name="chk" value="' + oObj.aData.id + '" type="checkbox" />';
	            }
	      },{
	            "aTargets": [4],//覆盖第五列
	            "fnRender": function ( oObj ) {
	           	 // 需要回复的Id
	           	 var id=oObj.aData.id;
	           	 return '<a href= "'+basePath+'message/replyMessage.action?messageId='+id+'">回复</a>';
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
 * 批量删除微讯
 */
function deleteBatchMessage() {
    //获取选择删除的微讯Id
	var chkbox = document.getElementsByName("chk");
	var ids = _checkedIds;
    if (ids.length <= 1) {
    	qytx.app.dialog.alert(sprintf("message.msg_del_at_least_one"));
    	return;
    }
	 
	//删除所选微讯
	qytx.app.dialog.confirm(sprintf("message.msg_confirm_info"), function () {
		qytx.app.ajax({
			url : basePath+"message/setup_deleteMessageById.action?type=delete",
			type : "post",
			dataType :'text',
			data: ids,
			success : function(data) {
				if(data == "success") {    
					_checkedIds="";
//					art.dialog.alert(sprintf("message.msg_del_success"));
					queryReceiveMessage();
				} else {
					qytx.app.dialog.alert(sprintf("message.msg_del_failure"));
				}
			}
		});
	}, function () {
	    return;
	});
}

/**
 * 回复微讯
 * @param messageId 微讯Id
 */
function replyMessage(messageId){
    // 回复微讯
	window.location.href=basePath+"message/replyMessage.action?messageId="+messageId;
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