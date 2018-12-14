function showDataWithHTML(result){
	result =jQuery.parseJSON(result);
	if(result&&result.length>0){
		$("#freeflow").empty();
	} 
	$("#freeflow").show();
	if(result!=null){
		var html="";
		for(var i=0;i<result.length;i++){
			var id=result[i].id;
			var processName=result[i].processName;
			var j=i%6+1;
			html+="<li class=\"mui-table-view-cell mui-media mui-col-xs-4\">";
			html+="<a href='javascript:void(0);' processId='"+id+"' class='apply'>";
			html+="<span class='icon ico-"+j+"'>"+processName.substring(0,1)+"</span>";
			html+="<div class=\"mui-media-body\">"+processName+"</div>";
			html+="</a>";
			html+="</li>";
		} 
		
		$("#freeflow").append(html);
		$.each($("a.apply"), function() { 
			//打开申请页面
			this.addEventListener("tap",function(){
				mui.openWindow({
					url: 'apply.html', 
					id:'apply',
					extras:{
						processId:this.getAttribute("processId")
					},
					show:{
				      autoShow:false
				    },
					waiting:{
						autoShow:true,//自动显示等待框，默认为true
      					title:''//等待对话框上显示的提示内容
					}
				});
			});
		});
	}
	
}
//我的待审批数量
function mywaitCount(){
	$.ajax({
		type: "POST",
        url: window.windowCommon.basePath+"baseworkflow/myWaitCount.c?r="+Math.random(),
        data: {
        	"userId":window.windowCommon.approveLoginId,
        	"_clientType":"wap"
        },
        success: function(msg){
            if(msg.indexOf("100||")==0){
            	var result = msg.substring(5);
                if(result>0){
                	$("#approveRound").addClass("approve-round");
                }else{
                	$("#approveRound").removeClass("approve-round");
                }
            }
        }
		
	});
}
//我的申请列表
function myStartList(){
	mui.openWindow({
		url:"myStartList.html",
		id:"myStartList",
		waiting:{
	      autoShow:false//自动显示等待框，默认为true
	    }
	});
}

//我的审批列表
function myApproveList(){
	mui.openWindow({
		url:"myApproveListLeft.html",
		id:"myApproveListLeft",
		waiting:{
	      autoShow:false//自动显示等待框，默认为true
	    }
	});
}
//请假申请
function qingjia(){
	mui.openWindow({
		url:"../freeflow/qingjia.html",
		id:"qingjia"
	});
}
//报销申请
function baoxiao(){
	mui.openWindow({
		url:"../freeflow/baoxiao.html",
		id:"baoxiao"
	});
}
//出差申请
function chuchai(){
	mui.openWindow({
		url:"../freeflow/chuchai.html",
		id:"chuchai"
	});
}
//外出申请
function waichu(){
	mui.openWindow({
		url:"../freeflow/waichu.html",
		id:"waichu"
	});
}
//物品领用申请
function lingyong(){
	mui.openWindow({
		url:"../freeflow/lingyong.html",
		id:"lingyong"
	});
}
//通用申请
function tongyong(){
	mui.openWindow({
		url:"../freeflow/tongyong.html",
		id:"tongyong"
	});
}
$(document).ready(function(){
	var winWidth = $(window).width();
	var bannerHeight = 0.6*winWidth;
	$('.head-bg').height(bannerHeight+"px");
	mui('.mui-scroll-wrapper').scroll();
});
mui.plusReady(function(){
	//判断当前是否有网络
	if(!getNetConnection()){
		$(".mui-content").hide();
		$(".noNet").show();
		return false;
	}
	mywaitCount();
	  // IOS系统 需要处理太阳花等待问题
    if(plus.os.name=='iOS'){
        //plus.qytxplugin.log("plusReady start");
        plus.navigator.closeSplashscreen();
        plus.qytxplugin.appReady();
    }
	document.getElementById("myStartList").addEventListener("release",function(){
		myStartList();
	});
	document.getElementById("myApproveList").addEventListener("release",function(){
		myApproveList();
	});
	document.getElementById("qingjia").addEventListener("tap",function(){
		qingjia();
	});
	document.getElementById("baoxiao").addEventListener("tap",function(){
		baoxiao();
	});
	document.getElementById("chuchai").addEventListener("tap",function(){
		chuchai();
	});
	document.getElementById("waichu").addEventListener("tap",function(){
		waichu();
	});
	document.getElementById("lingyong").addEventListener("tap",function(){
		lingyong();
	});
	document.getElementById("tongyong").addEventListener("tap",function(){
		tongyong();
	});
	setTimeout(function(){
		mui.ajax(window.windowCommon.basePath+'workflow/option!getProcessAttributeList.action?time='+(new Date()).getTime(),{
			data: {"userId":window.windowCommon.approveLoginId,"_clientType":"wap"},
			dataType:'text',//服务器返回json格式数据
			type:'post',//HTTP请求类型
			timeout:10000,//超时时间设置为10秒；
			crossDomain:true,
			success:function(msg){
				//服务器返回响应，根据响应结果，分析是否登录成功;
				if(msg.indexOf("100||")==0){
	            	var result = msg.substring(5);
	                showDataWithHTML(result);
	            }
			},
			error:function(xhr,type,errorThrown){
				mui.toast("网络异常，请稍后重试");
				//异常处理；
//				console.log(errorThrown);
			}
		});
	},100);

	
});