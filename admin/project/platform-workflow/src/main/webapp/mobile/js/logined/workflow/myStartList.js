$(document).ready(function(){
	var userId = h5Adapter.getItemValue("currentUserId");
	//当前页数
	var currentPage=0;
	//是否结尾
	var isEnd=0;
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
			url : basePath+"/baseworkflow/myStarted.c?_clientType=wap&r="+Math.random(),
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
				if(currentPage==0){
					h5Adapter.setItem("myStartList_"+userId, jsonData);
					scrollDataList.empty();
				}
				if(data.length==0&&currentPage==0){
					$(".nodata").show();
				}else{
					//加载页面
					appendContent(jsonData);
				}
				
				//下一页
				if(data.length==10){
					currentPage++;
				}else{
					isEnd=1;
					$(".scroll_loading").hide();
					$(".scroll_loading").remove();	
				}
			}
			
		});	
	}
	//从localStorage中读取数据
	h5Adapter.getItem("myStartList_"+userId,appendContent);
	//默认加载第一页
	//pullUpAction();
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
});
//加载页面
function appendContent(jsonData){
	
	if(!jsonData){
		return;
	}
	try{
		var data= JSON.parse(jsonData);
	}catch(e){
		return;
	}
	var scrollDataList=$("#scroll_dataList");
	var size = data.length;
	if(size != 0){
		for ( var i = 0; i < size; i++) {
			var code = data[i].code;
			var title = data[i].title+"申请";
			var state = data[i].state;
			var applyTime = data[i].applyTime;
			var instanceId = data[i].instanceId;
			if(state!=null){
				var stateName=state.split("(")[0];
				var userName=state.substring(state.indexOf("("),state.length-1);
				if(userName!=null&&userName.length>7){
					userName=userName.substring(0,7)+"...";
					state=stateName+userName+")";
				}
			}
			var viewUrl="";
			var imgClass="";
			var imgTxt="";
			if(code=="qingjia"){//请假
				viewUrl=basePath+"mobile/logined/workflow/qingjia_detail.jsp?_clientType=wap&instanceId="+instanceId;
				imgClass="ico-1";
			 }else if(code=="baoxiao"){//报销
				viewUrl=basePath+"mobile/logined/workflow/baoxiao_detail.jsp?_clientType=wap&instanceId="+instanceId;
				imgClass="ico-2";
			 }else if(code=="chuchai"){//出差
				viewUrl=basePath+"mobile/logined/workflow/chuchai_detail.jsp?_clientType=wap&instanceId="+instanceId;
				imgClass="ico-3";
			 }else if(code=="waichu"){//外出
				viewUrl=basePath+"mobile/logined/workflow/waichu_detail.jsp?_clientType=wap&instanceId="+instanceId;
				imgClass="ico-4";
			 }else if(code=="lingyong"){//物品领用
				viewUrl=basePath+"mobile/logined/workflow/lingyong_detail.jsp?_clientType=wap&instanceId="+instanceId;
				imgClass="ico-5";
			 }else if(code=="tongyong"){//通用
				viewUrl=basePath+"mobile/logined/workflow/tongyong_detail.jsp?_clientType=wap&instanceId="+instanceId;
				imgClass="ico-6";
			 }
			 var html = "";
			 html+='<div class="block-item applyList">';
			 html+='<a href="'+viewUrl+'">';
			 html+='<div class="media">';
			 html+='<div class="media-left">';
			 html+='<div class="media-object iconImg '+imgClass+'">'+imgTxt+'</div>';
			 html+='</div>';
			 html+='<div class="media-body">';
			 html+='<h4 class="media-heading">'+title+'</h4>';
			 html+=state+'<span>'+applyTime+'</span>';
	         html+='</div></div></a></div>';
			//加载一页
			scrollDataList.append(html);
		}
	}
}

//退出成功后调用的方法
function onSuccess(method,data) {

}
//退出失败后调用的方法
function onError(method,data){

}
/**
 * webview加载完毕初始化导航条按钮
 */
function loadSuccess(isNetOk){
	h5Adapter.onHeaderButton(null,{"type":"back","onClickMethod":"goBack"});
}

//退出调查问卷模块
function goBack(){
	window.location.href=basePath+"mobile/logined/workflow/index.jsp?_clientType=wap";
	//h5Adapter.backLast();
}