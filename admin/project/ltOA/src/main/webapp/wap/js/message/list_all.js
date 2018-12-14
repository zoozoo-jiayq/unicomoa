var iDisplayStart = 0;
$(document).ready(function() {
	findAllUserMessage();
	
    $("#loadMoreMessage").click(function () {
    	findAllUserMessage();
    });
});

// 查询登录用户所有微讯消息
var inBoxLoadAll = 0;
function findAllUserMessage() {
	var userHtml = "";
	 if (inBoxLoadAll == 1) {
	        return;
	    }
	$.ajax({
	    url : basePath + "message/setup_findAllUserMessage.action"+wapParam+"&random="+Math.random(),
	    type : "post",
	    dataType : "json",
	    data: {
            "iDisplayStart": iDisplayStart
        },
	    success : function(data) {
            if (data == undefined) {
                loadMore.more("loadMoreMessage");
                return;
            }

            var aaData = data["aaData"];
            for (var i = 0; i < aaData.length; i++) {
                var dataMap = aaData[i];
                
                userHtml += '<li onclick=openUserMessageList("'+dataMap["fromUserId"]+'","'+dataMap["fromUserName"]+'")><h2><time class="fr">'
                     + dataMap["sendTime"] + '</time>'+dataMap["fromUserName"];
                
                if (dataMap["unReadNum"] != null && "0" != dataMap["unReadNum"]){
                	userHtml += '<em class="msgItemNum">'+dataMap["unReadNum"]+'</em></h2><p class="f14">'+dataMap["contentInfo"]+'</p></li>';
                }else{
                	userHtml += '</h2><p class="f14">'+dataMap["contentInfo"]+'</p></li>';
                }
            }
            $("#messageList").append(userHtml);
            
            // 设置每次起始位置
            iDisplayStart = parseInt(data["iDisplayStart"]);
            if (data["loadAll"]) {
                $("#loadMoreMessage a").text("已完成加载");
                inBoxLoadAll = 1;
            } else {
                $("#loadMoreMessage a").text("点击加载更多...");
            }
	    }
	});
}

// 页面跳转到与某人的聊天记录页面
function openUserMessageList(userId, fromUserName){
	 var userName = encodeURI(fromUserName);
	window.location.href = basePath + "wap/logined/message/list_user.jsp"+wapParam+"&userId=" + userId + "&userName=" + userName;
	
}