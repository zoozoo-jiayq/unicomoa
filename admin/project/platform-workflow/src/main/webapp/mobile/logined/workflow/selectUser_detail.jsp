<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>   
<div class="showList">

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
	$(".showList").empty();
	for(var i = 0 ; i<userInfos.length; i++){
		var atime = userInfos[i].time;
		var photoUrl = userInfos[i].photoUrl;
		var userName = userInfos[i].approver;
		var advice = userInfos[i].advice;
		var approveResult = userInfos[i].approveResult;
		var approverId = userInfos[i].approverId;
		createHead(atime, photoUrl, userName, advice, approveResult, approverId,i,totalState,userInfos.length);
	}
	//initPlugin();
	//如果流程结束，不允许再次撤销
	if(totalState == "approving"){
		$("#dischargeBut").show();
	}else{
		$("#dischargeBut").hide();
	}
}

function createHead(atime,photoUrl,userName,advice,approveResult,userId,index,totalState,userNum){
	
	var result = "";
	var stateClass="";
	var stepClass="";
	var redClass="";
	if(approveResult == 0 ){
		result = "同意";
		stateClass="";
	}else if(approveResult == 1 ){
		result = "拒绝";
		redClass="redBtn";
		stateClass="state2";
	}else if(approveResult == 2){
		result = "已撤销";
		redClass="grayBtn";
		stateClass="state6";
	}else if(approveResult == 3){
		result = "审批中";
		redClass="orangeBtn";
		stepClass="stepBoxIng";
		stateClass="state1";
	}else if(approveResult == 4){
		result = "已转交";
		redClass="yellowBtn";
		stateClass="state5";
	}else if(approveResult == -1){
		result = "提交申请";
		stateClass="";
	}else if(approveResult == 5){
		result = "待审批";
		stateClass="state3";
	}
	if(totalState == "over"&&result=="同意"&&((index+1)==userNum)){
		result = "完结";
		redClass="greenBtn";
		stateClass="";
	}
	var strs = "<div class='stepBox "+stepClass+"'>";
	strs+="<span class='state "+stateClass+"'></span>";
	strs+="<div class='txt'>";
	strs+="<em class='jt'></em>";
	strs+="<h2>";
	if(atime){
		var tstrs = atime.split(" ");
		var ymd = tstrs[0].split("-");
		strs+="<span class='time'>"+ymd[1]+"-"+ymd[2]+" "+tstrs[1]+"</span>";
	}
	strs+="<span>"+userName+"</span>";
	if(redClass!=""){
		strs+="<span class='Btn "+redClass+"'>"+result+"</span>";
	}
	strs+="</h2>";
	if(result == "提交申请"||result == "待审批"){
		strs+="<p>"+result+"</p>";
	}
	if(advice&&result!="同意"){
		strs+="<p>"+advice+"</p>";
	}
	if(advice&&result=="同意"){
		strs+="<p>已同意&nbsp;("+advice+")</p>";
	}else if(!advice&&result=="同意"){
		strs+="<p>已同意</p>";
	}
	$(".showList").append(strs);
	
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