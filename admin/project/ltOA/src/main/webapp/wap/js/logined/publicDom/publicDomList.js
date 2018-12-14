jQuery(document).ready(function($){
	
	//初始化
	(function(){
		if(!$("#menu").val()){
			$("#myDispatcher").addClass("current");
			$("#myReadList").removeClass("current");
			$("#myApprove").removeClass("current");
			$.get(basePath+"mobilePublicDom/controler!toPublicDomlist.action",function(data){
				myDispatcher(data);
			});
		}else if($("#menu").val() == 'myReadList' ){
			$("#myDispatcher").removeClass("current");
			$("#myReadList").addClass("current");
			$("#myApprove").removeClass("current");
			$.get(basePath+"mobilePublicDom/controler!toMyReadList.action",function(data){
				$(".mailList").html("");
				myReadList(data);
			});
		}else if($("#menu").val() == 'myApprove' ){
			$("#myDispatcher").removeClass("current");
			$("#myReadList").removeClass("current");
			$("#myApprove").addClass("current");
			$.get(basePath+"mobilePublicDom/controler!toMyApproveList.action",function(data){
				$(".mailList").html("");
				myApprove(data);
			});
		}
	})();
	
	
	//我的发文
	$("#myDispatcher").click(function(){
		//window.location.href=basePath+"mobilePublicDom/controler!toPublicDomlist.action";
		$(this).addClass("current");
		$("#myReadList").removeClass("current");
		$("#myApprove").removeClass("current");
		$("#curPage").val(1);
		$("#menu").val("myDispatcher");
		$.get(basePath+"mobilePublicDom/controler!toPublicDomlist.action",function(data){
			$(".mailList").html("");
			myDispatcher(data);
		});
	});
	
	function myDispatcher(data){
		var content = $(".mailList");
		data = eval('('+data+')');
		for(var i=0; i<data.length; i++){
			var tempData = data[i];
			var tempHTML = '<h2><a href="#">[发]'+tempData.title;
			var tempHTML1 = '<i class="gray">[';
			if(tempData.state == '待发送'){
				tempHTML1 = '<i class="green">[';
			}
			var tempHTML2 = tempData.state+']</i></a></h2><h3>'+$("#userName").val()+'<time>'+tempData.startTime+'</time></h3>';
			
			var jli = $("<li>");
			jli.data("instanceId",tempData.instanceId);
			jli.append(tempHTML+tempHTML1+tempHTML2);
			content.append(jli);
			jli.click(function(){
				var instanceId = $(this).data("instanceId");
				window.location.href=basePath+'/mobilePublicDom/controler!toProcessDetail.action?processInstanceId='+instanceId+'&menuType=myDispatcher&taskId='+tempData.taskId;
			});
//			$(tempHTML+tempHTML1+tempHTML2,{
//				click:function(){
//					alert(1);
//					window.location.href=basePath+'/mobilePublicDom/controler!toProcessDetail.action?processInstanceId='+tempData.instanceId+'&menuType=myDispatcher&taskId='+tempData.taskId;
//				}}).appendTo(content);
//			content.append(tempHTML+tempHTML1+tempHTML2);
//			$("li[name='"+tempData.instanceId+"']").live("click",function(){
//				alert(tempData.instanceId);
//				window.location.href=basePath+'/mobilePublicDom/controler!toProcessDetail.action?processInstanceId='+tempData.instanceId+'&menuType=myDispatcher&taskId='+tempData.taskId;
//			});
			}
	}
	
	//------------------------------------------
	
	//我的阅读
	$("#myReadList").click(function(){
		//window.location.href=basePath+"mobilePublicDom/controler!toMyReadList.action";
		$(this).addClass("current");
		$("#myDispatcher").removeClass("current");
		$("#myApprove").removeClass("current");
		$("#menu").val("myReadList");
		$.get(basePath+"mobilePublicDom/controler!toMyReadList.action",function(data){
			$(".mailList").html("");
			myReadList(data);
		});
	});
	
	function myReadList(data){
		var content = $(".mailList");
		data = eval('('+data+')');
		for(var i=0; i<data.length; i++){
			var tempData = data[i];
			var jli = $("<li>");
			jli.data("instanceId",tempData.instanceId);
			jli.data("taskId",tempData.taskId);
			var tempHTML = '<h2><a href="#">[发]'+tempData.title+'</a></h2><h3>'+$("#userName").val()+'<time>'+tempData.startTime+'</time></h3>';
			if(tempData.state == '未阅读'){
				tempHTML = '<h2><a href="#">[发]'+tempData.title+'<i class="red">[待阅读]</i></a></h2><h3>'+$("#userName").val()+'<time>'+tempData.startTime+'</time></h3>';
			}
			jli.append(tempHTML);
			content.append(jli);
			$(jli).click(function(){
				var instanceId = $(this).data("instanceId");
				var taskId = $(this).data("taskId");
				window.location.href=basePath+'/mobilePublicDom/controler!toProcessDetail.action?processInstanceId='+instanceId+'&menuType=myReadList&taskId='+taskId;
			});
		}
	}
	
	//-------------------------------------------
	
	//公文审批
	$("#myApprove").click(function(){
		//window.location.href=basePath+"mobilePublicDom/controler!toMyApproveList.action";
		$(this).addClass("current");
		$("#myDispatcher").removeClass("current");
		$("#myReadList").removeClass("current");
		$("#curPage").val(1);
		$("#menu").val("myApprove");
		$.get(basePath+"mobilePublicDom/controler!toMyApproveList.action",function(data){
			$(".mailList").html("");
			myApprove(data);
		});
	});
	
	function myApprove(data){
		var content = $(".mailList");
		data = eval('('+data+')');
		for(var i=0; i<data.length; i++){
			var tempData = data[i];
			var jli = $("<li>");
			jli.data("instanceId",tempData.instanceId);
			jli.data("taskId",tempData.taskId);
			var tempHTML = '<h2><a href="#">';
			var tempHTML1 =	'<i class="green">[发]</i>';
			if(tempData.flag == '收'){
				tempHTML1 =	'<i class="blue">[收]</i>';
			}
			var tempHTML2 = tempData.title+'<i class="orange">['+tempData.state+']</i></a></h2><h3>'+$("#userName").val()+'<time>'+tempData.startTime+'</time></h3>';
			jli.append(tempHTML+tempHTML1+tempHTML2);
			content.append(jli);
			$(jli).click(function(){
				var instanceId = $(this).data("instanceId");
				var taskId = $(this).data("taskId");
				window.location.href=basePath+'/mobilePublicDom/controler!toProcessDetail.action?processInstanceId='+instanceId+'&menuType=myApprove&taskId='+taskId;
			});
		}
	}
	
	$("#loadMoreMessage").click(function(){
		var curPage = $("#curPage").val()*1;
		var nextPage = curPage*1+1;
		var menu = $("#menu").val();
		if(menu == "myDispatcher"){
			$.get(basePath+"mobilePublicDom/controler!toPublicDomlist.action?page.pageNo="+nextPage,function(data){
				myDispatcher(data);
			});
		}else if(menu == 'myReadList'){
			$.get(basePath+"mobilePublicDom/controler!toMyReadList.action?page.pageNo="+nextPage,function(data){
				myReadList(data);
			});
		}else if(menu == 'myApprove'){
			$.get(basePath+"mobilePublicDom/controler!toMyApproveList.action?page.pageNo="+nextPage,function(data){
				myApprove(data);
			});
		}
	});
	
});