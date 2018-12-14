var _iDisplayStart=0; //显示开始位置
var _iDisplayLength = 15; // 每页显示条数 
$(document).ready(function() {	
	// 清除table页的cookie数据
	$.removeTableCookie('SpryMedia_DataTables_myTable_list_user_message.jsp');
	
    var urlinfo = window.location.href;//獲取url
    var userName = urlinfo.split("?")[1].split("=")[2];//拆分url得到”=”後面的參數
    $("#userName").val(decodeURI(userName));
    $("#titleText").empty();
    $("#titleText").append(decodeURI(userName));
    queryUserMessage();
	
    //单击发送微讯
    $("#sendMessageBtn").click(function(){
        window.location.href=basePath+"message/replyMessage.action?userId="+$("#userId").val();
        return false;
    });
    
});
//跳转页
function getPage(page){
	if(page){
		_iDisplayStart = _iDisplayLength*page-1;
		//-获取考勤记录
		queryUserMessage();
	}
}
function queryUserMessage(){
	var userId = $("#userId").val();
	var paramData = {
			"userId":userId,
			"iDisplayStart":_iDisplayStart,//显示开始位置
			"iDisplayLength":_iDisplayLength // 每页显示条数 
	};
	var url=basePath + "message/setup_getMessageWithUId.action";
	qytx.app.ajax({
		url : url,
		type : "post",
		data : paramData,
		dataType : "json",
		success : function(data) {	
			 var contents= data.contents;
			 var pageHtml= data.pageHtml;
			 $("#pageFoot").html(pageHtml);
			 $("#contents").html(contents);
			 window.parent.frameResize();
	    }
	});
	
}

/**
 * 删除微讯
 */
function deleteMessage(id) {	
	//删除微讯
	qytx.app.dialog.confirm(sprintf("message.msg_delete_one_confirm_info"), function () {
		qytx.app.ajax({
			url : basePath+"message/setup_deleteMessageById.action?messageId="+id,
			type : "post",
			dataType :'text',
			success : function(data) {
				if(data == "success") {
					queryUserMessage();
				} else {
					qytx.app.dialog.alert(sprintf("message.msg_del_failure"));
				}
			}
		});
	}, function () {
	    return;
	});
}

