$(document).ready(function () {
    $(".meil_wrap").delegate("li", "click", function () {
        $(this).parent().parent().parent().parent().find(".current").removeClass("current");
        $(this).addClass("current");
    });
//    var redirectURL=$("#redirectURL").val();
//    if($.trim(redirectURL)!=""){
//        $("iframe[name='emailContent']").attr("src",basePath+redirectURL);
//    }
    //初始化时清空‘全选’选中状态
    $("#myBoxTag").click(function(){
 	   if($("#wj_ul").is(":hidden")){
 		  $("#myBoxTag").removeClass("on");
 		   $("#wj_ul").show();
 	   }else{
 		   $("#myBoxTag").addClass("on");
 		   $("#wj_ul").hide();
 	   }
    });
});
/**
 * ajax更新所有的邮件箱中的邮件数量
 */
function updateBoxEmailCount() {
    qytx.app.ajax({
        url: basePath + "logined/email/getBoxEmailCount.action"+"?_r=" + getOaRandom(),
        type: "get",
        dataType: 'text',
        success: function (data) {
            if (data != '' && null != data) {
                // 获取返回结果后更新对应box的数量信息
                data = JSON.parse(data);
                for (var i in data) {	
                    if(i=="notRead"){
                    	if(data[i+""]>0){
                    		$("#box_inBox").html("&nbsp;("+data[i + ""]+")");
                    	}else{
                    		$("#box_inBox").html("");
                    	}
                    }else{
                    	if(i!="inBox"&&i!="outBox"&&i!="draftBox"&&i!="wastebasketBox"&&i!=""){
                    		if(data[i+""]>0){
                    			$("#box_my_"+i).html("&nbsp;("+data[i + ""]+")");
                    		}else{
                    			$("#box_my_"+i).html("");
                    		}
                    	}
                    }
                }
            }
        }
    });
}
/**
 * 更新自定义邮件箱列表
 */
function updateDiyBoxList() {
	qytx.app.ajax({
        url: basePath + "logined/email/loadDiyEmailBoxList.action"+"?_r=" + getOaRandom(),
        type: "get",
        dataType: 'text',
        success: function (data) {
            if (data != '' && null != data) {
                // 获取返回结果后更新对应box的数量信息
                data = JSON.parse(data);
                var html = "";
                for (var i = 0; i < data.length; i++) {
                    var id = data[i]["id"];
                    var boxName = data[i]["boxName"];
                    var emailCount = data[i]["emailCount"];
                    html += "<li id='emailBox_" + id + "'>";
                    html += "<a href='" + basePath + "logined/email/emailToListPage.action?emailBoxId=" + id + "' target='emailContent' >";
                    html += ""+boxName+"";
                    html += "<span id='box_my_" + id + "'>";
                    if(emailCount&&emailCount>0){
                    	html += "("+emailCount+")";
                    }
                    html += "</span>";
                    html += "</a>";
                    html += "</li>";
                }
                $("#wj_ul").html(html);
                if(html.length>0){
                	$("#myBoxTag").show();
                }else{
                	$("#myBoxTag").hide();
                }
//                $("#otherBoxDiv").append("<li  ><a href='"+basePath+"logined/email/emailBoxList.jsp' target='emailContent'>邮箱设置</a></li>");
//                $("#otherBoxDiv").show();
                
            }
        }
    });
}

/**
 * 删除一个邮件箱
 * @param boxId 邮件箱ID
 */
function deleteEmailBox(boxId) {
    $("#emailBox_" + boxId).remove();
    // 如果所有邮件箱都删除时,隐藏"其他邮箱"
    if ($("#wj_ul").find("li").length == 0){
    	$("#myBoxTag").hide();
    }
}
 
