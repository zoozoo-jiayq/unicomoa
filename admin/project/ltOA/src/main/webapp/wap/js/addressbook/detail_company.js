$(document).ready(function() {
    $(".selectHidden").each(function () {
        if ($(this).val() != "") {
            var theId = $(this).attr("id").toString();
            var selectId = theId.substring(0, theId.lastIndexOf("_"));
            $("#" + selectId).val($(this).val());
        }
    });
    
    
    var stationText=$("#station").find("option:selected").text();  
    var stationVal=$("#station").find("option:selected").val();  
    if ("" != stationVal){
    	$("#stationLi").html("岗位："+stationText);
    }else{
    	$("#stationLi").html("岗位：");
    }
    
    $("#back").click(function() {
    	if ($("#requestSrc").val() == "android"){
    		window.activity.retMainPage();
    	}else{
    		window.location.href = document.referrer;
    	}
    });
});

//页面跳转到与某人的聊天记录页面
function openUserMessageList(userId, fromUserName){
	 var userName = encodeURI(fromUserName);
	window.location.href = basePath + "wap/logined/message/list_user.jsp"+wapParam+"&userId=" + userId + "&userName=" + userName+"&random="+Math.random();;
	
}