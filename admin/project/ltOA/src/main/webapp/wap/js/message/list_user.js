$(document).ready(function() {
//	var urlinfo = window.location.href;// 獲取url
//	var userName = urlinfo.split("?")[1].split("=")[2];// 拆分url得到”=”後面的參數
	
//	$("#userNameH1").html(decodeURI(userName));

	findUserMessage();

	// 点击发送
	$("#sendBtn").click(function() {
		sendMessage();
		return false;
	});
	
//	$("#back").click(function() {
//		window.location.href = basePath + "wap/logined/message/list_all.jsp" + wapParam;
//		return false;
//	});
});

var lastTime = '-1';

var iDisplayStart = 0;

// 查询登录用户所有微讯消息
function findUserMessage() {
	var messageHtml = "";
	$.ajax({
	    url : basePath + "message/setup_getWapMessageWithUId.action"+wapParam+"&userId=" + $("#userId").val(),
	    type : "post",
	    dataType : "json",
	    data : {
		    "iDisplayStart" : iDisplayStart
	    },
	    success : function(data) {
		    var aaData = data["aaData"];
		    for ( var i = 0; i < aaData.length; i++) {
			    var dataMap = aaData[i];

			    if (dataMap["receiveFlag"] == "1") {
				    messageHtml += '<li class="msgRecive">';
			    } else {
				    messageHtml += '<li class="msgSend">';
			    }
			    messageHtml += '<div class="msgbox"><em class="dot"></em>' + dataMap["contentInfo"] + '</div><p><time>'
			            + dataMap["sendTime"] + '</time></p></li>';

			    if (lastTime < dataMap["sendTime"]) {
				    lastTime = dataMap["sendTime"];
			    }
		    }
		    $("#thelist").prepend(messageHtml);
		    myScroll.refresh();
		    if (iDisplayStart == 0){
		    	// 滚动到底部,显示最后的信息
		    	myScroll.scrollToElement ($(".msgbox").last()[0], 0);  
		    }
		    iDisplayStart = parseInt(data["iDisplayStart"]);
		    
		    
	    }
	});
}

/**
 * 发送微讯消息
 */
function sendMessage() {
	var contentInfo = $.trim($("#contentInfo").val());
	if ("" == contentInfo) {
		new WapDialog({
			content : "信息内容不能为空"
		}).show();
		return;
	}

	var paramData = {
	    'messageInfo.toUids' : $("#userId").val(),
	    'messageInfo.contentInfo' : contentInfo,
	    'messageInfo.msgType' : 2,
	    'messageInfo.srcType' : 'box'
	};
	$.ajax({
	    url : basePath + "message/setup_sendMessage.action"+wapParam,
	    type : "post",
	    dataType : "text",
	    data : paramData,
	    success : function(data) {
		    if ("success" == data) {
			    // 发送成功后获取此阶段接收的消息
			    findUserMessageByTime();
			    
			    // 清空发送消息
			    $("#contentInfo").val("");
		    } else {
			    new WapDialog({
			        title : "提示",
			        content : "消息发送失败",
			        btn1Name : "确定",
			    }).show();
		    }
	    }
	});
}

// 查询登录用户新聊天信息
function findUserMessageByTime() {
	var messageHtml = "";

	$.ajax({
	    url : basePath + "message/setup_getWapMessageWithUIdByTime.action"+wapParam+"&userId=" + $("#userId").val() + "&beginTime="
	            + lastTime,
	    type : "post",
	    dataType : "json",
	    success : function(data) {
		    var aaData = data["aaData"];
		    for ( var i = 0; i < aaData.length; i++) {
			    var dataMap = aaData[i];

			    if (dataMap["receiveFlag"] == "1") {
				    messageHtml += '<li class="msgRecive">';
			    } else {
				    messageHtml += '<li class="msgSend">';
			    }
			    messageHtml += '<div class="msgbox"><em class="dot"></em>' + dataMap["contentInfo"] + '</div><p><time>'
			            + dataMap["sendTime"] + '</time></p></li>';

			    if (lastTime < dataMap["sendTime"]) {
				    lastTime = dataMap["sendTime"];
			    }

		    }
		    $("#thelist").append(messageHtml);
		    myScroll.refresh();
		    myScroll.scrollToElement ($(".msgbox").last()[0], 0);  
	    }
	});
}
