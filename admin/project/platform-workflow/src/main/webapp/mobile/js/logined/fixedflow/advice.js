mui.plusReady(function(){
	//判断当前是否有网络
	if(!getNetConnection()){
		$(".mui-content").hide();
		$(".noNet").show();
		return false;
	}
	var self = plus.webview.currentWebview();
	var instanceId=self.instanceId;
	var editorId=self.editorId;
	var taskId=self.taskId;
	var type=self.type;//0 审批 1查看
	var defaultValue=self.defaultValue;//默认值
	if(defaultValue){
		$("#option").val(defaultValue);
	}
	if(type==0){
		$("div.advice").show();
		$("nav").show();
	}else if(type==1){
		$("div.advice").hide();
		$("nav").hide();
	}
	//加载审批历史列表
	mui.ajax(window.windowCommon.basePath+'dom/mobile/option!getAdviceList.action?time='+(new Date()).getTime(),{
			data:{
				"instanceId":instanceId,
				"editorId":editorId,
				"_clientType":"wap"
			},
			dataType:'text',//服务器返回json格式数据
			type:'post',//HTTP请求类型
			timeout:10000,//超时时间设置为10秒；
			crossDomain:true,
			success:function(msg){
				//服务器返回响应，根据响应结果，分析是否登录成功；
				if(msg.indexOf("100||")==0){
					var resultJson = msg.substring(5);
					var result =jQuery.parseJSON(resultJson);
					
					if(result.length>0){
						showAdviceWithHTML(result,taskId,type);
					}else{
						if(type==1){
							$(".nodata").show();
						}
					}
					
	            }
			},
			error:function(xhr,type,errorThrown){
			//异常处理；
			//console.log(errorThrown);
		}
	});
	
	//提交审批意见
	var submitBtn = document.getElementById("submitBtn");
	submitBtn.addEventListener("tap",function(){
		var option = $.trim($("#option").val());
		submitAdvice(instanceId,editorId,taskId,option);
		
	})
});
//显示审批记录
function showAdviceWithHTML(result,taskIdCur,type){
	if(result!=null){
		var html="";
		for(var i=0;i<result.length;i++){
			var userName=result[i].userName;
			var createTime=result[i].createTime;
			var content=result[i].content;
			var taskId=result[i].taskId;
			var editorId=result[i].editorId;
			var headPhoto=result[i].headPhoto;
			var userId=result[i].userId;
			if(taskId==taskIdCur&&userId==window.windowCommon.approveLoginId&&type==0){
				$("#option").val(content);
				continue;
			}
			var time="";
			if(createTime){
				var ymd=createTime.split(" ")[0];
				var hms=createTime.split(" ")[1];
				time=ymd.split("-")[1]+"月"+ymd.split("-")[2]+"日 "+hms.split(":")[0]+":"+hms.split(":")[1];
			}
			html+="<li class=\"mui-table-view-cell mui-media mui-conmment\">";
			html+="<a href='javascript:void(0);'>";
			if(headPhoto){
				html+='<img class="mui-media-object mui-pull-left" src="'+window.windowCommon.photoUrl+headPhoto+'" width="30" height="30">';
			}else{
				html+='<img class="mui-media-object mui-pull-left" src="../../images/female.png" width="30" height="30">';
			}
			
			html+="<div class=\"mui-media-body\">"+userName;
			html+="<p class=\"mui-time\">"+time+"</p>";
			html+="<p>"+content+"</p>";
			html+="</div>";
			html+="</a>";
			html+="</li>";
		}
		$("div ul").append(html);
	}

}
//提交审批意见
function submitAdvice(instanceId,editorId,taskId,option){
	//判断textarea是否为空
		if(option.length==0){
			mui.toast("请输入审批意见");
			return;
		}
		if(option.length>255){
			mui.toast("审批意见不超过255字");
			return;
		}
		mui.ajax(window.windowCommon.basePath+'dom/mobile/option!updateAdvice.action?time='+(new Date()).getTime(),{
			data:{
				"content":option,
				"instanceId":instanceId,
				"editorId":editorId,
				"taskId":taskId,
				"userId":window.windowCommon.approveLoginId,
				"_clientType":"wap"
			},
			dataType:'text',//服务器返回json格式数据
			type:'post',//HTTP请求类型
			timeout:10000,//超时时间设置为10秒；
			crossDomain:true,
			success:function(msg){
				//服务器返回响应，根据响应结果，分析是否登录成功；
				if(msg.indexOf("100||")==0){
					mui.toast("提交成功");
					//关闭当前窗口
					plus.webview.close("advice");
	            }else{
	            	$(obj).attr("disabled",false);
	            	mui.toast("提交失败");
	            }
			},
			error:function(xhr,type,errorThrown){
				//异常处理；
				$(obj).attr("disabled",false);
				mui.toast("请求数据异常");
			}
	});
}
