
mui.plusReady(function(){
	//页面滚动
	mui('.mui-scroll-wrapper').scroll();
	
	var self = plus.webview.currentWebview();
	var instanceId=self.instanceId;
	var type=self.type;
	if(type==2){//撤销
		$("#title").html("撤销");
		$("#state").hide();
		$("#operTitle").html("撤销原因");
		$("#submitBtn").attr("disabled",false);
		$("#submitBtn").show();
	}else if(type==1){//拒绝
		$("#title").html("审批");
		$("#state").html("审批意见：拒绝");
		$("#operTitle").html("批复内容");
		$("#submitBtn").attr("disabled",false);
		$("#submitBtn").show();
	}else if(type==0){//同意
		$("#title").html("审批");
		$("#state").html("审批意见：同意");
		$("#operTitle").html("批复内容");
		loadUserInfo(instanceId);
	}
	document.querySelector("a[name='userSelect']").addEventListener("tap",function(){
		$(this).focus();
		showUserList();
	});
	document.getElementById("submitBtn").addEventListener("release",function(){
		submitBtn(this);
	})
	//关闭弹出层
	var iconClose=document.getElementById("iconClose");
	iconClose.addEventListener("tap",function(){
		setTimeout(function(){
			mui("#userList").popover("toggle"); 
		},300); 
		unbindSelectUser();
	});
});
//显示审批人列表
function showUserList() {
	var nextUsers = $("#nextUsers").val();
	$("#userList li a").removeClass("on");
	$("#userList li a").each(function() {
		var userId = $(this).attr("userId");
		if (nextUsers==userId) {
			$(this).addClass("on");
		}
	});
	setTimeout(function() {
		mui("#userList").popover("toggle");
	}, 300);
	bindSelectUser();
}
//删除选择人员
function deleteSelectUser(){
	$("a[name='headUser']").remove();
	$("#nextUsers").val("");
	$("#userSelect").show();
}
//加载审批人员信息
function loadUserInfo(instanceId){
	mui.ajax(window.windowCommon.basePath+'workflow/option!getNextTaskInfo.action?time='+(new Date()).getTime(),{
			data: {
				"instanceId":instanceId,
				userId:window.windowCommon.approveLoginId,
				"_clientType":"wap"
			},
			dataType:'text',//服务器返回json格式数据
			type:'post',//HTTP请求类型
			timeout:10000,//超时时间设置为10秒；
			crossDomain:true,
			success:function(msg){
				//服务器返回响应，根据响应结果，分析是否登录成功；
				if(msg.indexOf("100||")==0){
	            	var result = msg.substring(5);
	            	if(result=="null"){
	            		$("#selectDiv").hide();
	            	}else{
	            		$("#selectDiv").show();
	            	}
	                showUserWithHTML(result);
	                $("#submitBtn").attr("disabled",false);
	                 $("#submitBtn").show();
	            }
			},
			error:function(xhr,type,errorThrown){
				//异常处理；
				//console.log(errorThrown);
			}
		});
}

//显示审批人员信息
function showUserWithHTML(result){
	result =jQuery.parseJSON(result);
	if(result!=null){
		var html="";
		var candidate=result[0].candidate;
		var nextTaskName=result[0].nextTaskName;
		var onlySelectOne=result[0].onlySelectOne;
		$("#nextAction").val("TO "+nextTaskName);
		if(onlySelectOne){
			$("#oper").hide();
		}else{
			$("#oper").show();
		}
		for(var i=0;i<candidate.length;i++){
			var userId=candidate[i].id;
			var name=candidate[i].name;
			var photo=candidate[i].photo;
			var photoUrl=window.windowCommon.photoUrl;
			var showName=name;
			if(name!=null&&name.length>2){
				showName=name.substring(name.length-2,name.length);
			}
			var col=letterCode(showName);
			html+="<li class=\"mui-table-view-cell mui-media mui-approve\">";
			html+="<a href='javascript:void(0);' onlySelectOne="+onlySelectOne+" userId="+userId+" name="+name+" photo="+photo+">";
			if(photo==null||photo==""||photo==undefined){
				 html+='<div class="textround mui-pull-left head-bg-'+col+'">'+showName+'</div>';
			}else{
				 html+="<img class=\"mui-pull-left imground\" src='"+photoUrl+photo+"'>";
			}
	        html+="<div class=\"mui-media-body\">";
	        html+="<span class=\"user-name\">"+name+"</span>";
	        html+="<i class=\"checkoff mui-pull-right\"></i></div></a>";
	        html+="</li>";
		}
		$("#userList ul").append(html);
		//如果只有一个审批人，则直接显示
		if(candidate.length==1){
			var userId=candidate[0].id;
			var name=candidate[0].name;
			var photo=candidate[0].photo;
			showOnlyOne(userId,name,photo);
		}
	}

}
//绑定选择人员事件
function bindSelectUser(){
	$("#userList ul").delegate("li a","tap",function(){
		var onlySelectOne=$(this).attr("onlySelectOne");
		selectUserStyle(this,onlySelectOne);
		return false;
	});
}
//取消绑定选择人员事件
function unbindSelectUser(){
	$("#userList ul").undelegate("li a","tap");
}
//如果只有一个审批人，则直接显示
function showOnlyOne(userId, name, photo) {
	var photoUrl = window.windowCommon.photoUrl;
	var showName = name;
	if (showName.length > 2) {
		showName = showName.substring(showName.length - 2, showName.length);
	}
	var html = "";
	html += '<a href="javascript:void(0);" name="headUser">';
	html += '<div class="head headImg">';
	if (photo == null || photo == "" || photo == undefined || photo == 'undefined') {
		html += '<div class="round ico-1">' + showName + '</div>';
	} else {
		html += '<div class="round"><img src="' + photoUrl + photo + '"></div>';
	}
	html += '<p class="center">' + name + '</p>';
	html += '</div></a>';
	$("#addHead").before(html);
	$("#nextUsers").val(userId);
	document.querySelector("a[name='headUser']").addEventListener("tap",function(){
		$(this).focus();
		showUserList();
	});
	document.querySelector("a[name='headUser']").addEventListener("longtap",function(){
		deleteSelectUser();
	});
	$("#userSelect").hide();
}
//选择人员确定
function sure(){
	$("#userSelect").hide();
	$("#approveDiv").show();
	$("#addHead").siblings().remove();
	var approveUserIds="";
	$("#userList a.on").each(function(){
		var userId=$(this).attr("userId");
		var name=$(this).attr("name");
		var photo=$(this).attr("photo");
		var photoUrl=window.windowCommon.photoUrl;
		var html="";
		var showName=name;
		if(showName.length>2){
			showName=showName.substring(showName.length-2,showName.length);
		}
		html+='<a href="javascript:void(0);" name="headUser">';
		html+='<div class="head headImg">';
		if(photo==null||photo==""||photo==undefined||photo=='undefined'){
			html+='<div class="round ico-1">'+showName+'</div>';
		}else{
			html+='<div class="round"><img src="'+photoUrl+photo+'"></div>';
		}
		html+='<p class="center">'+name+'</p>';
		html+='</div></a>';
		$("#addHead").before(html);
		approveUserIds+=userId+",";
	});
	if(approveUserIds!=""){
		approveUserIds=approveUserIds.substring(0,approveUserIds.length-1);
	}
	$("#nextUsers").val(approveUserIds);
	document.querySelector("a[name='headUser']").addEventListener("tap",function(){
		$(this).focus();
		showUserList();
	});
	document.querySelector("a[name='headUser']").addEventListener("longtap",function(){
		deleteSelectUser();
	});
	mui('#userList').popover('toggle');
	unbindSelectUser();
}
/*
 * 提交按钮
 */
function submitBtn(obj){
	$(obj).attr("disabled",true);
	var self = plus.webview.currentWebview();
	var instanceId=self.instanceId;
	var type=self.type;
	var formData=self.formData;
	var advice=$.trim($("#advice").val());
	if(advice.length>500){
		if(type==2){
			mui.toast("撤销原因字数不能超过500");
		}else{
			mui.toast("批复内容字数不能超过500");
		}
		$(obj).attr("disabled",false);
		return ;
	}
	//下一步操作
	var nextAction=$("#nextAction").val();
	//下一步审批人
	var nextUsers=$("#nextUsers").val();
	if($("#selectDiv").is(":visible")&&type==0&&(nextUsers==null||nextUsers==""||nextUsers==undefined)){
		mui.toast("请选择下一步审批人");
		$(obj).attr("disabled",false);
		return;
	}
	//显示提交等待
	showWaiting();
	mui.ajax(window.windowCommon.basePath+'workflow/option!approve.action?time='+(new Date()).getTime(),{
		data: {
			instanceId:instanceId,
			userId:window.windowCommon.approveLoginId,
			approveResult:type,
			advice:advice,
			nextAction:nextAction,
			assigner:nextUsers,
			formData:formData,
			"_clientType":"wap"
		},
		dataType:'text',//服务器返回json格式数据
		type:'post',//HTTP请求类型
		timeout:60000,//超时时间设置为60秒；
		crossDomain:true,
		success:function(msg){
			//关闭提交等待
			closeWaiting();
			//服务器返回响应，根据响应结果，分析是否登录成功；
			if(msg.indexOf("100||")==0){
				mui.toast("提交成功");
				//撤销
            	if(type==2){    
            		//跳转到我的申请页面
            		var myStartListPage=plus.webview.getWebviewById("myStartList");
            		if(myStartListPage){
            			myStartListPage.reload();
            		}else{
            			mui.openWindow({
							url:"myStartList.html",
							id:"myStartList",
							waiting:{
						      autoShow:false//自动显示等待框，默认为true
						    }
						});
            		}
					
            	}else if(type==1||type==0){//拒绝
            		plus.webview.getLaunchWebview().evalJS("mywaitCount()");
            		plus.webview.close("detail");
            		//跳转到我的申请页面
            		plus.webview.getWebviewById("myApproveListLeft").reload();
//					mui.openWindow({
//						url:"myApproveListLeft.html",
//						id:"myApproveListLeft",
//						extras:{
//							flag:"approved"
//						}
//					});
            	}
            }else{
            	$(obj).attr("disabled",false);
            	mui.toast("提交失败");
            }
		},
		error:function(xhr,type,errorThrown){
			//关闭提交等待
			closeWaiting();
			//异常处理；
			$(obj).attr("disabled",false);
			mui.toast("请求数据异常");
		}
	});
	
}
