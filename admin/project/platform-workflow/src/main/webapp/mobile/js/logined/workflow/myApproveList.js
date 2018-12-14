$(document).ready(function(){
	var userId = h5Adapter.getItemValue("currentUserId");
	//当前页数
	var currentPage=0;
	//是否结尾
	var isEnd=0;
	//当前页数
	var currentPage_2=0;
	//是否结尾
	var isEnd_2=0;
	/** 
	 * 上拉刷新 （自定义实现此方法） 
	 */
	function pullUpAction() {
		if(isEnd==1){
			return;
		}
		var scrollDataList=$("#scroll_dataList");
		return jQuery.ajax({
			type : "post",
			url : basePath+"/baseworkflow/myWaitProcess.c?_clientType=wap&r="+Math.random(),
			timeout:10000,
			data : {
				"iDisplayStart":currentPage*10,
				"iDisplayLength":10,
				"userId":userId,
				"moduleCode":"baseworkflow"
			},
			dataType : "text"
		}).then(function(listData){
			var dataAry=listData.split("||");
			if(dataAry[0]=="100"){
				var jsonMap=eval('(' + dataAry[1] + ')');
				//数据列表
				var data=jsonMap.aaData;
				//列表总条数
				var total=jsonMap.iTotalRecords;
				if(total>0){
					$("#tab1 a").html("待我审批("+total+")");
				}else{
					$("#tab1 a").html("待我审批");
				}
				var jsonData=JSON.stringify(data);
				if(currentPage==0){
					h5Adapter.setItem("myWaitApproveList_"+userId, jsonData);
					scrollDataList.empty();
				}
				if(data.length==0&&currentPage==0){
					$(".nodate").show();
				}else{
					//加载页面
					appendContent(jsonData);
				}
				//下一页
				if(data.length==10){
					currentPage++;
				}else{
					isEnd=1;
					$("#scroll_up").remove();
				}
			}
			
		});	
	}
	//从localStorage中读取数据
	h5Adapter.getItem("myWaitApproveList_"+userId,appendContent);
	//执行滑动
	upScroll({
		initPage:1,
		scrollAreaId:"#scroll_wrapper",
		pullUpAreaId:"#scroll_up",
		loadArea:function(){
			return "<div class='scroll_loading'><em></em>内容加载中....</div>";
		},
		againArea:function(){
			return "<div class='scroll_loadfail'><span>网络异常，<a href='#'>稍后重试</a></span></div>";
		},
		serverData:function(){
			return pullUpAction();
		}
	});
	
	/** 
	 * 上拉刷新 （自定义实现此方法） 
	 */
	function pullUpAction_2() {
		if(isEnd_2==1){
			return;
		}
		var scrollDataList=$("#scroll_dataList_2");
		return jQuery.ajax({
			type : "post",
			url : basePath+"/baseworkflow/myProcessed.c?_clientType=wap&r="+Math.random(),
			timeout:10000,
			data : {
				"iDisplayStart":currentPage*10,
				"iDisplayLength":10,
				"userId":userId,
				"moduleCode":"baseworkflow"
			},
			dataType : "text"
		}).then(function(listData){
			var dataAry=listData.split("||");
			if(dataAry[0]=="100"){
				var jsonMap=eval('(' + dataAry[1] + ')');
				//数据列表
				var data=jsonMap.aaData;
				var jsonData=JSON.stringify(data);
				if(currentPage_2==0){
					h5Adapter.setItem("myApprovedList_"+userId, jsonData);
					scrollDataList.empty();
				}
				if(data.length==0&&currentPage==0){
					$(".nodate_2").show();
				}else{
					//加载页面
					appendContent_2(jsonData);
				}
				//下一页
				if(data.length==10){
					currentPage_2++;
				}else{
					isEnd_2=1;
					$("#scroll_up_2").remove();
				}
			}
			
		});	
	}
	//从localStorage中读取数据
	h5Adapter.getItem("myApprovedList_"+userId,appendContent_2);
	//默认加载第一页
	//pullUpAction_2();
	//执行滑动
	upScroll({
		initPage:1,
		scrollAreaId:"#scroll_wrapper_2",
		pullUpAreaId:"#scroll_up_2",
		loadArea:function(){
			return "<div class='scroll_loading'><em></em>内容加载中....</div>";
		},
		againArea:function(){
			return "<div class='scroll_loadfail'><span>网络异常，<a href='#'>稍后重试</a></span></div>";
		},
		serverData:function(){
			return pullUpAction_2();
		}
	});
	
});

//待审批加载页面
function appendContent(jsonData){
	if(!jsonData){
		return;
	}
	try{
		var data=JSON.parse(jsonData);
	}catch(e){
		return;
	}
	var scrollDataList=$("#scroll_dataList");
	var size = data.length;
	if(size != 0){
		for ( var i = 0; i < size; i++) {
			var photoUrl = data[i].photoUrl;
			var title = data[i].title;
			var state = data[i].state;
			var arriveTime = data[i].arriveTime;
			var instanceId = data[i].instanceId;
			var currentUserId = data[i].currentUserId;
			var createrName = data[i].createrName;
			var code="";
			if(instanceId!=null&&instanceId!=undefined){
				code=instanceId.substring(0,instanceId.indexOf("."));
			}
			var approveUrl="";
			if(code=="qingjia"){//请假
				 approveUrl=basePath+"mobile/logined/workflow/qingjia_detail.jsp?_clientType=wap&instanceId="+instanceId+"&operation=approve";
			 }else if(code=="baoxiao"){//报销
				 approveUrl=basePath+"mobile/logined/workflow/baoxiao_detail.jsp?_clientType=wap&instanceId="+instanceId+"&operation=approve";
			 }else if(code=="chuchai"){//出差
				 approveUrl=basePath+"mobile/logined/workflow/chuchai_detail.jsp?_clientType=wap&instanceId="+instanceId+"&operation=approve";
			 }else if(code=="waichu"){//外出
				 approveUrl=basePath+"mobile/logined/workflow/waichu_detail.jsp?_clientType=wap&instanceId="+instanceId+"&operation=approve";
			 }else if(code=="lingyong"){//物品领用
				 approveUrl=basePath+"mobile/logined/workflow/lingyong_detail.jsp?_clientType=wap&instanceId="+instanceId+"&operation=approve";
			 }else if(code=="tongyong"){//通用
				 approveUrl=basePath+"mobile/logined/workflow/tongyong_detail.jsp?_clientType=wap&instanceId="+instanceId+"&operation=approve";
			 }
			if(createrName!=null&&createrName.length>2){
				createrName=createrName.substring(createrName.length-2,createrName.length);
			}
			var col=letterCode(createrName);
			var html = "";
			 html+='<div class="media peoList">';
			 html+='<div class="media-body">';
			 html+='<a href="'+approveUrl+'">';
			 html+='<div class="show">';
			 html+='<div class="faceBox">';
			 if(photoUrl==null||photoUrl==undefined||photoUrl==""){
				 html+='<div class="imgBox head-bg-'+col+'">'+createrName+'</div>';
			 }else{
				 html+='<div class="imgBox "><img src="'+photoUrl+'"/></div>';
			 }
			 
			 html+='</div></div>';
			 html+='<h3 class="media-heading">'+title+'</h3>';
			 html+='<p><span class="time">'+arriveTime+'</span>'+state+'</p>';
			 html+='</a></div></div>';
			//加载一页
			scrollDataList.append(html);
		}
	}
}

//已审批加载页面
function appendContent_2(jsonData){
	if(!jsonData){
		return;
	}
	try{
		var data=JSON.parse(jsonData);
	}catch(e){
		return;
	}
	var scrollDataList=$("#scroll_dataList_2");
	var size = data.length;
	if(size != 0){
		for ( var i = 0; i < size; i++) {
			var photoUrl = data[i].photoUrl;
			var title = data[i].title;
			var state = data[i].state;
			var approveTime = data[i].approveTime;
			var instanceId = data[i].instanceId;
			var code="";
			if(instanceId!=null&&instanceId!=undefined){
				code=instanceId.substring(0,instanceId.indexOf("."));
			}
			var createrName = data[i].createrName;
			var approvedUrl="";
			if(code=="qingjia"){//请假
				approvedUrl=basePath+"mobile/logined/workflow/qingjia_detail.jsp?_clientType=wap&instanceId="+instanceId+"&operation=view";
			 }else if(code=="baoxiao"){//报销
				 approvedUrl=basePath+"mobile/logined/workflow/baoxiao_detail.jsp?_clientType=wap&instanceId="+instanceId+"&operation=view";
			 }else if(code=="chuchai"){//出差
				 approvedUrl=basePath+"mobile/logined/workflow/chuchai_detail.jsp?_clientType=wap&instanceId="+instanceId+"&operation=view";
			 }else if(code=="waichu"){//外出
				 approvedUrl=basePath+"mobile/logined/workflow/waichu_detail.jsp?_clientType=wap&instanceId="+instanceId+"&operation=view";
			 }else if(code=="lingyong"){//物品领用
				 approvedUrl=basePath+"mobile/logined/workflow/lingyong_detail.jsp?_clientType=wap&instanceId="+instanceId+"&operation=view";
			 }else if(code=="tongyong"){//通用
				 approvedUrl=basePath+"mobile/logined/workflow/tongyong_detail.jsp?_clientType=wap&instanceId="+instanceId+"&operation=view";
			 }
			if(createrName!=null&&createrName.length>2){
				createrName=createrName.substring(createrName.length-2,createrName.length);
			}
			var col=letterCode(createrName);
			var html = "";
			 html+='<div class="media peoList">';
			 html+='<div class="media-body">';
			 html+='<a href="'+approvedUrl+'">';
			 html+='<div class="show">';
			 html+='<div class="faceBox">';
			 if(photoUrl==null||photoUrl==undefined||photoUrl==""){
				 html+='<div class="imgBox head-bg-'+col+'">'+createrName+'</div>';
			 }else{
				 html+='<div class="imgBox "><img src="'+photoUrl+'"/></div>';
			 }
			 
			 html+='</div></div>';
			 html+='<h3 class="media-heading">'+title+'</h3>';
			 html+='<p><span class="time">'+approveTime+'</span>'+state+'</p>';
			 html+='</a></div></div>';
			//加载一页
			scrollDataList.append(html);
		}
	}
}
function loadSuccess(){
	h5Adapter.onHeaderButton(null,{"type":"back","onClickMethod":"back"});
}

function back(){
	window.location.href=basePath+"mobile/logined/workflow/index.jsp?_clientType=wap";
	//h5Adapter.backLast();
}


