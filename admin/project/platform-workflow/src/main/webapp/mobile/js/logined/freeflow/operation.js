mui.plusReady(function(){
		//页面滚动
		mui('.mui-scroll-wrapper').scroll();
		var userId=window.windowCommon.approveLoginId;
		var self=plus.webview.currentWebview();
		var instanceId = self.instanceId;
		var operation=self.operation;
		var type=self.type;
		var turn=self.turn;
		console.log(operation);
		if(operation=="approved"){
			$("#viewOper").hide();
			$("#approveOper").hide();
		}else if(operation=="approving"){
			$("#viewOper").hide();
			$("#approveOper").show();
		}else{
			$("#viewOper").hide();
			$("#approveOper").hide();
		}
		
		if(type==2){//撤销
			$("#title").html("撤销");
			$("#state").hide();
			$("#advice").attr("placeholder","请输入撤销原因");
		}else if(type==1){//拒绝
			$("#title").html("审批");
			$("#state").html("拒绝");
			$("#advice").attr("placeholder","请输入批复内容（非必填）");
		}else if(type==0){//同意
			$("#title").html("审批");
			$("#state").html("同意");
			$("#advice").attr("placeholder","请输入批复内容（非必填）");
		}else if(type==3){//转交
			var userName = eval('('+turn+')').userName;
			$("#title").html("审批");
			$("#state").html("转交给:"+userName);
			$("#advice").attr("placeholder","请输入批复内容（非必填）");
		}
		
		//撤销
		$("#dischargeBut").click(function(){
			plus.webview.close("free_operate");
			mui.openWindow({
				url:"operate.html",
				id:"free_operate",
				extras:{
					instanceId:instanceId,
					type:2
				}
			});
		});
		//不同意
		$("#noagree").click(function(){
			plus.webview.close("free_operate");
			mui.openWindow({
				url:"operate.html",
				id:"free_operate",
				extras:{
					instanceId:instanceId,
					type:1
				}
			});
		});
		//同意
		$("#agree").click(function(){
			plus.webview.close("free_operate");
			mui.openWindow({
				url:"operate.html",
				id:"free_operate",
				extras:{
					instanceId:instanceId,
					type:0
				}
			});
		});
		
		//转交
		$("#turn").click(function() {
			//单选人员
			plus.qytxplugin.selectUsers("", 1, function(data) {
				if (data) {
					checkTurner(instanceId,data);
				}
			});
					
//			var data = [{
//				"userId": "29219255",
//				"userName": "韩冰"
//			}];
//			checkTurner(instanceId, data);
		});
		//点击提交按钮
		$("#operSubmit").click(function(){
			$("#operSubmit").attr("disabled",true);
			var advice = $("#advice").val();
			var userId=window.windowCommon.approveLoginId;
			if(type==2){//撤销
				discharge(instanceId, userId, advice);
			}else if(type==1){//拒绝
				noAgree(instanceId, userId, advice);
			}else if(type==0){//同意
				agree(instanceId, userId, advice);
			}else if(type==3){//转交
				var turner=eval('('+turn+')');
				turnTo(instanceId, userId, advice,turner);
			}
		});
});
//转交人验证
function checkTurner(instanceId,data) {
	var suid = data[0].userId;
	$.ajax({
		type: "post",
		url: window.windowCommon.basePath + "baseworkflow/checkTurnerIsRepeat.c",
		data: {
			"instanceId": instanceId,
			"turnUserId": suid
		},
		success: function(msg) {
			if (msg.indexOf("100||") == 0) {
				msg = msg.substring(5);
				msg = eval('(' + msg + ')');
				if (msg.result == true) { //重复
					mui.toast("该审批人已在列表中");
				} else {
					var turn = JSON.stringify(data[0]);
					plus.webview.close("free_operate");
					mui.openWindow({
						url: "operate.html",
						id: "free_operate",
						extras: {
							instanceId: instanceId,
							turn: turn,
							type: 3
						}
					});
				}
			}
		}
	});
}
//撤销
function discharge(instanceId, userId, advice) {
	if (advice != null && advice != "" && advice.length > 300) {
		mui.toast("撤销原因字数不超过300");
		$("#operSubmit").attr("disabled", false);
		return false;
	}
	$.ajax({
		url: window.windowCommon.basePath + "baseworkflow/approve.c?_clientType=wap",
		type: "post",
		data: {
			"instanceId": instanceId,
			"approveResult": "2",
			"userId": userId,
			"advice": advice
		},
		success: function(msg) {
			if(msg.indexOf("100||")==0){
		    	mui.toast("提交成功");
				//打开我的申请页面 
				plus.webview.getWebviewById("myStartList").reload();
		     }else{
		     	 mui.toast("提交失败");
		    	 $("#operSubmit").attr("disabled",false);
		     }
		}
	});
}
//拒绝
function noAgree(instanceId, userId, advice) {
	if (advice != null && advice != "" && advice.length >= 500) {
		mui.toast("批复内容应小于500个字");
		$("#operSubmit").attr("disabled", false);
		return false;
	}
	$.ajax({
		type: "post",
		url: window.windowCommon.basePath + "baseworkflow/approve.c",
		data: {
			"instanceId": instanceId,
			"approveResult": "1",
			"userId": userId,
			"advice": advice
		},
		success: function(msg) {
			if(msg.indexOf("100||")==0){
				plus.webview.getLaunchWebview().evalJS("mywaitCount()");
		    	mui.toast("提交成功");
				//打开我的申请页面 
				plus.webview.close("myApproveListLeft");
				plus.webview.close("myApproveListRight");
				//跳转到我的申请页面
				mui.openWindow({
					url: "../fixedflow/myApproveListLeft.html",
					id: "myApproveListLeft",
					extras: {
						flag: "approved"
					}
				});
		     }else{
		     	 mui.toast("提交失败");
		    	 $("#operSubmit").attr("disabled",false);
		     }
		}
	});
}
//同意
function agree(instanceId, userId, advice) {
	if (advice != null && advice != "" && advice.length >= 500) {
		mui.toast("批复内容应小于500个字");
		$("#operSubmit").attr("disabled", false);
		return false;
	}
	$.ajax({
		type: "post",
		url: window.windowCommon.basePath + "baseworkflow/approve.c",
		data: {
			"instanceId": instanceId,
			"approveResult": "0",
			"userId": userId,
			"advice": advice
		},
		success: function(msg) {
			if(msg.indexOf("100||")==0){
				plus.webview.getLaunchWebview().evalJS("mywaitCount()");
		    	mui.toast("提交成功");
				//打开我的申请页面 
				plus.webview.close("myApproveListLeft");
				plus.webview.close("myApproveListRight");
				//跳转到我的申请页面
				mui.openWindow({
					url: "../fixedflow/myApproveListLeft.html",
					id: "myApproveListLeft",
					extras: {
						flag: "approved"
					}
				});
		     }else{
		     	 mui.toast("提交失败");
		    	 $("#operSubmit").attr("disabled",false);
		     }
		}
	});
}
//转交
function turnTo(instanceId, userId, advice,turner) {
	var userName = turner.userName;
	var photoUrl = turner.userPhoto;
	var ss = {
		userId: turner.userId,
		userName: userName,
		photoUrl: photoUrl
	};
	var sr = JSON.stringify(ss);
	if (advice != null && advice != "" && advice.length >= 500) {
		mui.toast("批复内容应小于500个字");
		$("#operSubmit").attr("disabled", false);
		return false;
	}
	$.ajax({
		type: "post",
		url: window.windowCommon.basePath + "baseworkflow/turn.c",
		data: {
			"instanceId": instanceId,
			"turner": sr,
			"userId": userId,
			"advice": advice
		},
		success: function(msg) {
			if(msg.indexOf("100||")==0){
				plus.webview.getLaunchWebview().evalJS("mywaitCount()");
		    	mui.toast("提交成功");
				//打开我的申请页面 
				plus.webview.close("myApproveListLeft");
				plus.webview.close("myApproveListRight");
				//跳转到我的申请页面
				mui.openWindow({
					url: "../fixedflow/myApproveListLeft.html",
					id: "myApproveListLeft",
					extras: {
						flag: "approved"
					}
				});
		     }else{
		     	 mui.toast("提交失败");
		    	 $("#operSubmit").attr("disabled",false);
		     }
		}
	});
}