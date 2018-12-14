$(document).ready(function() {
    // 点击发送
    $("#sendBtn").click(function() {
        sendMessage();
        return false;
    });
    
    // 删除人员
    $(".div_txt .memberList").live("click", function () {
        $(this).remove();
    });

});

/**
 * 发送微讯消息
 */
function sendMessage() {
	var userIds = getUserIdNames("userListEm").userId;
	var contentInfo = $.trim($("#contentInfo").val());
	
	if ("" == userIds){
		 new WapDialog({content: "必须选择人员"}).show();
		return;
	}
	
	if ("" == contentInfo){
		new WapDialog({content: "信息内容不能为空"}).show();
		return;
	}
	
	var paramData = {
	    'messageInfo.toUids' : userIds,
	    'messageInfo.contentInfo' : contentInfo,
	    'messageInfo.msgType' : 2
	};
	$.ajax({
	    url : basePath + "message/setup_sendMessage.action"+wapParam+"&random="+Math.random(),
	    type : "post",
	    dataType : "text",
	    data : paramData,
	    success : function(data) {
	    	 if ("success" == data) {
	    		 new WapDialog({
		             title: "提示",
		             content: "消息发送成功",
		             btn1Name: "确定",
		         }).show();
	    		 
	    		 // 清空消息内容和人员
	    		 $("#userIds").val("");
	    		 $("#contentInfo").val("");
	    		 
	    	 }else{
	    		 new WapDialog({
		             title: "提示",
		             content: "消息发送失败",
		             btn1Name: "确定"
		         }).show();
	    	 }
	    	 
	    }
	});
}


function addReceiver(toUserDivId) {
    openUserSelect($("#" + toUserDivId),addReceiverCallBack,toUserDivId);
}


function addReceiverCallBack(userMap, toUserDivId) {
    if (userMap != undefined) {
        userMap.forEach(function(value,key){
            if(value.Type=="user"){
                addAnReceiver2UserDiv(value.Id, value.Name, toUserDivId);
            }
        });
    }
}

var deleteUserIcon = basePath + "/wap/images/icon_cls.png";
function addAnReceiver2UserDiv(userId, userName, toUserDivId) {
    var toUserDiv = $("#" + toUserDivId);
    if (toUserDiv.find("img[userId='" + userId + "']").length == 0) {//没有则加上
        var html = "<em class='memberList'>";
        html += "<img src='" + deleteUserIcon + "' class='cls' userId='" + userId + "' userName='" + userName + "' />";
        html += userName;
        html += "</em>";
        toUserDiv.append(html);
    }
}

function getUserIdNames(toUserDivId) {
    var userName = "";
    var userId = "";
    $("#" + toUserDivId + " .memberList img").each(function () {
        userName += "," + $(this).attr("userName");
        userId += "," + $(this).attr("userId");
    });
    if (userName.length > 1) {
        userName = userName.substr(1);
    }
    if (userId.length > 1) {
        userId = userId.substr(1);
    }
    return {userName: userName, userId: userId};
}