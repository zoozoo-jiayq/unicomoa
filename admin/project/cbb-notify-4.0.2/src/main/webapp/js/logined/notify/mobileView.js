function connectWebViewJavascriptBridge(callback) {
    if (window.WebViewJavascriptBridge) {
        callback(WebViewJavascriptBridge);
    } else {
        document.addEventListener('WebViewJavascriptBridgeReady', function() {
         	callback(WebViewJavascriptBridge);
        }, false);
    }
}
$(document).ready(function(){
	var id = $("#id").val();
	getNotifyInfo(id);
	//点击进入已读未读列表
	try { 
		if(browser.versions.ios){
			//IOS
		    connectWebViewJavascriptBridge(function(bridge) {
		        bridge.init(function(message, responseCallback) {
		           if (responseCallback) {
		               responseCallback("");
		           };
		        });
				$("#info").click(function(){   
					bridge.callHandler('qytx.getUserRecord', null, function(response){});
					return false;
				});
		    });
		}else{
			//其他
			$("#info").click(function(){   
				window.getUserRecord.callOnJs2("click"); 
				return false;
			});
		}
	} catch (e) { 
	} 
});

/**
 * 加载公告信息
 * @param notifyId
 */
function getNotifyInfo(notifyId) {
	dataParam = {
		'id' : notifyId,
		'_clientType':'wap'
	};
	$.ajax({
		type : 'post',
		url : basePath + "wapNotify/mobileViewById.action",
		data : dataParam,
		dataType : 'json',
		async : false,
		success : function(data) {
			var readCount=data.readCount;
			var count=readCount+data.unreadCount;
			$("#title").text(data.subject);
			$("#info").html(data.differenceTime+"&nbsp;&nbsp;<span>"+data.createUser.userName+"</span><em class='num'>"+data.readCount+"/"+count+"</em>");
			$("#content").html(data.content);
			$("#content").find("a").removeAttr("href");
		}
	});
}