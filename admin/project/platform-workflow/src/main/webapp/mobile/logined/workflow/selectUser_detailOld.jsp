<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
 <div class="swiper-container ">
            <div class="swiper-wrapper">
                <div class="swiper-slide">
                </div>
            </div>
        </div>
<script type="text/javascript">
function initPlugin(){
	var num = $(".swiper-slide .flow-head").length;
	var tie = (num+1) * 105;
	$(".swiper-slide").css("width",tie);
	new Swiper('.swiper-container',{
	    scrollContainer: true
	});
}

function processUserInfos(userInfos,totalState,curApprover){
	$(".swiper-slide").empty();
	for(var i = 0 ; i<userInfos.length; i++){
		if(i!=userInfos.length-1){
			var atime = userInfos[i].time;
			var photoUrl = userInfos[i].photoUrl;
			var userName = userInfos[i].approver;
			var advice = userInfos[i].advice;
			var approveResult = userInfos[i].approveResult;
			var approverId = userInfos[i].approverId;
			createHead(atime, photoUrl, userName, advice, approveResult, approverId,i,totalState);
			creeateTo();
		}else{
			var ss2 = createLast(totalState);
			$(".swiper-slide").append(ss2);
		}
	}
	initPlugin();
	//如果流程结束，不允许再次撤销
	if(totalState == "approving"){
		$("#dischargeBut").show();
	}else{
		$("#dischargeBut").hide();
	}
}

function createHead(atime,photoUrl,userName,advice,approveResult,userId,index,totalState){
	var strs = "<div class='flow-head'>";
	var lastName = userName;
	if(userName!=null&&userName.length>2){
		lastName=userName.substring(userName.length-2,userName.length);
	}
	col = Math.floor(Math.random()*7+1); //1-10
	if(photoUrl.indexOf("data")==0){
		photoUrl = "";
	}
	strs+="<div class='round ico-"+col+"'>"+lastName+"<img src='"+photoUrl+"'/></div>";
	if(atime){
		var tstrs = atime.split(" ");
		var ymd = tstrs[0].split("-");
		strs+="<span class='flow-time'>"+ymd[1]+"月"+ymd[2]+"日<span>"+tstrs[1]+"</span></span>";
	}
	var result = "";
	aa = "gray";
	
	if(totalState == 'approving'){
		result = "待审批";
	}
	
	if(approveResult == 0 ){
		aa = "green";
		result = "同意";
	}else if(approveResult == 1 ){
		aa = "red";
		result = "拒绝";
	}else if(approveResult == 2){
		aa = "red";
		result = "撤销";
	}else if(approveResult == 3){
		aa = "orange";
		result = "审批中";
	}else if(approveResult == 4){
		aa = "green";
		result = "转交";
	}else if(approveResult == -1){
		result = "提交申请";
		aa = "green";
	}
	
	strs+="<span class='flow-name "+aa+"'>"+userName+"</span>";
	strs+="<span class='flow-handle "+aa+"'>"+result+"</span>";
	var flag = "message_"+userId;
	if(advice){
		strs+="<img class='show' id='"+flag+"' src='"+basePath+"mobile/images/message.png'>";
		strs+="<div class='message message2'>";
		strs+="<span class='bot'></span><span class='top'></span>";
		strs+="<p style='font-size: 12px;'>"+advice+"</p>";
		strs+="</div>";
		strs+="</div>";
	}
	$(".swiper-slide").append(strs);
	$("#"+flag).click(function(){
		$("#"+flag).next(".message2").fadeToggle("1000");
	});
}

/*
 * <div class="flow-head flow-head-last">
 <div class="round over"></div>
 </div>
 */
function createLast(totalState){
	var result = "";
	sta = "";
	result+="<div class='flow-head'>";
	if(totalState == 'approving'){
		sta = "";
	}else{
		sta = "over-on";
	}
	result+="<div class='over-off "+sta+"'></div>";
	result+="</div>";
	return result;
}

function creeateTo(){
	var result = "<div class='flow-head flow-to'></div>";
	$(".swiper-slide").append(result);
}

</script>        