function processUserInfos(userInfos,totalState,curApprover,operation){
	$(".showList").empty();
	for(var i = 0 ; i<userInfos.length; i++){
		var atime = userInfos[i].time;
		var photoUrl = userInfos[i].photoUrl;
		var userName = userInfos[i].approver;
		var advice = userInfos[i].advice;
		var approveResult = userInfos[i].approveResult;
		var approverId = userInfos[i].approverId;
		createHead(atime, photoUrl, userName, advice, approveResult, approverId,i,totalState,userInfos.length,operation);
	}
	//initPlugin();
	//如果流程结束，不允许再次撤销
	if(totalState == "approving" && operation =="view"){
		$("#viewOper").show();
	}else{
		$("#viewOper").hide();
	}
}

function createHead(atime,photoUrl,userName,advice,approveResult,userId,index,totalState,userNum,operation){
	
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
	if(operation=="view" && approveResult == 3){
		var self = plus.webview.currentWebview();
		var processerUser=JSON.parse(self.processerUser);
		var title=self.title;
		if(processerUser.length==1){
			userId=processerUser[0].userId;
		}else if(processerUser.length>1){
			for(var j=0 ;j<processerUser.length;j++){
				if(userName==processerUser[j].userName){
					userId=processerUser[j].userId;
				}
			}
		}
		var content=window.windowCommon.approveLoginName+"催您审批他的"+title;
		strs+='<a href="javascript:void(0);" onclick="sendDidi(\''+userId+'\',\''+content+'\')" ><em class="water"></em></a>';
	}
	$(".showList").append(strs);
	
}
/**
 * 发送嘀嘀提醒
 * @param {Object} userId
 * @param {Object} content
 */
function sendDidi(userId,content){
	var didi={
		"userIds":userId,//接收人userIds
		"userIdsEditable":true,//接收人是否可以编辑  true可以 false不可以
		"remindType":1,//提醒方式 1语音4录音8短信0应用内
		"remindTypeEditable":true,//提醒方式是否能编辑 true可以 false不可以
		"contentType":3,//内容类型 1录音 2短语 3文字
		"contentTypeEditable":true,//内容类型是否能编辑 true可以 false不可以
		"content":content,//嘀嘀内容
		"contentEidtable":true,//内容是否能编辑 true可以 false不可以
		"systemName":"lgz",//系统名字
		"moduleName":"approve",//模块名字
		"fromWhere":"approveView"//审批模块详情
	};
	var didiJson=JSON.stringify(didi);
	//添加蒙版
	var flag = false;
	var mask = mui.createMask(function(){
	    return flag;
	});
	//显示遮罩
	mask.show();
	window.plus.qytxplugin.sendDidi(didiJson,function(){
		//关闭遮罩
		setTimeout(function(){
			//关闭遮罩
			flag = true;
			mask.close();
		},1000);
	});
	//防止手机端出错,3秒自动消失
	setTimeout(function(){
		//关闭遮罩
		flag = true;
		mask.close();
	},3000);
	
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