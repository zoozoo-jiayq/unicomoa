var bTime = "";
var eTime = "";

//初始化时间
(function(){
	var curDate = new Date();
	var date1=addHours(new Date(),1);
	var date2=addHours(new Date(),2);
	var y = date1.getFullYear();
	var m = date1.getMonth()+1;
	var d = date1.getDate();
	var h = date1.getHours();
	var sx = "上午";
	if(h>=12){
		sx = "下午";
	}
	$("#beginTime").find("span").html(m+"月"+d+"日");
	$("#beginTime").find("time").html(sx+h+":00");
	bTime = y+"-"+m+"-"+d+" "+h+":00:00";
	
	y = date2.getFullYear();
	m = date2.getMonth()+1;
	d = date2.getDate();
    h = date2.getHours();
	if(h>=12){
		sx = "下午";
	}else{
		sx="上午";
	}
	$("#endTime").find("span").html(m+"月"+d+"日");
	$("#endTime").find("time").html(sx+h+":00");
	eTime = y+"-"+m+"-"+d+" "+h+":00:00";
	
	var hoursDiff=HoursDiff(eTime, bTime);
	$("#outTime").html(hoursDiff);
})();

$("#beginTime").click(function(){
	h5Adapter.getTime(bTime, "yyyy-MM-dd HH:mm:ss", function(da){
		if(da.isSuccess){
			//判断选择时间是否小于当前时间
			if(compareTime(da.selectTime)){
				return;
			};
			if(compare(da.selectTime,eTime)){
				bTime = da.selectTime;
				var m = bTime.split(" ")[0].split("-")[1];
				var d = bTime.split(" ")[0].split("-")[2];
				$("#beginTime").find("span").html(Number(m)+"月"+Number(d)+"日");
				var h = bTime.split(" ")[1].split(":")[0];
				sx = "上午";
				if(h>=12){
					sx="下午";
				}
				$("#beginTime").find("time").html(sx+h+":00");
				if($("#beginTime").find("span").html()!=null&&$("#beginTime").find("span").html()!=""&&
						$("#endTime").find("span").html()!=null&&$("#endTime").find("span").html()!=""){
					var hoursDiff=HoursDiff(eTime, bTime);
					$("#outTime").html(hoursDiff);
				}
			}
		}
	});
});
//设置结束时间控件
$("#endTime").click(function(){
	h5Adapter.getTime(eTime, "yyyy-MM-dd HH:mm:ss", function(da){
		if(da.isSuccess){
			//判断选择时间是否小于当前时间
			if(compareTime(da.selectTime)){
				return;
			};
			if(compare(bTime,da.selectTime)){
				eTime = da.selectTime;
				var m = eTime.split(" ")[0].split("-")[1];
				var d = eTime.split(" ")[0].split("-")[2];
				$("#endTime").find("span").html(Number(m)+"月"+Number(d)+"日");
				var h = eTime.split(" ")[1].split(":")[0];
				sx = "上午";
				if(h>=12){
					sx="下午";
				}
				$("#endTime").find("time").html(sx+h+":00");
				if($("#beginTime").find("span").html()!=null&&$("#beginTime").find("span").html()!=""&&
						$("#endTime").find("span").html()!=null&&$("#endTime").find("span").html()!=""){
					var hoursDiff=HoursDiff(eTime, bTime);
					$("#outTime").html(hoursDiff);
				}
			}
		}
	});
});

function compare(beginTime,endTime){
	var d1 = Date.parse(beginTime.replace(/-/g,"/"));
	var d2 = Date.parse(endTime.replace(/-/g,"/"));
	if(d2<=d1){
		h5Adapter.tips("开始时间不能大于结束时间!",500);
		return false;
	}
	return true;
}
function compareTime(selectTime){
	var nowDate=new Date();
	nowDate.setSeconds(0, 0);
	var selDate=new Date(selectTime.replace("-","/"));
	if(selDate.valueOf()<nowDate.valueOf()){
		h5Adapter.tips("选择时间不能小于当前时间",500);
		return true;
	}else{
		return false;
	}
}
function getFormData(){
	//开始时间
	var beginYMD = $("#beginTime").find("span").html();
	var beginHMS = $("#beginTime").find("time").html();
	//结束时间
	var endYMD = $("#endTime").find("span").html();
	var endHMS = $("#endTime").find("time").html();
	//外出时间
	var outTime = $("#outTime").html();
	//外出原因
	var reason = $("#reason").val();

	var data = new Object();
	data.beginYMD = beginYMD;
	data.beginHMS = beginHMS;
	data.endYMD = endYMD;
	data.endHMS = endHMS;
	data.outTime = outTime;
	data.reason = reason;
	data.imgs = photos;
	return JSON.stringify(data);
}

//提交
$("#sbn").click(function(){
	$("#sbn").attr("disabled",true);
	var flag = check();
	if(!flag){
		$("#sbn").attr("disabled",false);
		return;
	}
	var datas = getFormData();
	var userId = h5Adapter.getItemValue("currentUserId");
	//发送数据
	jQuery.ajax({
		   type: "POST",
		   url: basePath+"/baseworkflow/start.c?_clientType=wap",
		   data: {"formData":datas,"userIds":userIds,"userId":userId,"code":"waichu"}, 
		   success: function(msg){
		     if(msg.indexOf("100||")==0){
		    	 window.location.href = basePath+"mobile/logined/workflow/myStartList.jsp?_clientType=wap";
		     }else{
		    	 $("#sbn").attr("disabled",false);
		     }
		   }
	});
});
//验证
function check(){
	var reason = $("#reason").val();
	var outTime = $("#outTime").html();
	if(reason == ""||reason == null){
		h5Adapter.tips('请输入外出原因');
		return false;
	}else if(reason.length>=500){
		h5Adapter.tips('外出原因字应小于500个字');
		return false;
	}else if(!userIds){
		h5Adapter.tips("请选择人员");
		return false;
	}else{
		return true;
	}
}
//从后台加载表单数据
function loadData(){
	h5Adapter.getItem($("#instanceId").val(), function(result){
		if(result){
			//showDataWithHTML(result);
		}
	});
	$.ajax({
		url:basePath+"/baseworkflow/view.c?instanceId="+$("#instanceId").val(),
		type:"POST",
		cache:false,
		success:function(msg){
			if(msg.indexOf("100||")==0){
				var result = msg.substring(5);
				showDataWithHTML(result);
				h5Adapter.setItem($("#instanceId").val(), result);
			}
		}
	});
}
function showDataWithHTML(result){
	result =jQuery.parseJSON(result);
	//发起人的姓名和头像
	var createrName = result.createrName;
	var createrPhoto = result.createrPhoto;
    var imgName=createrName;
	 if(createrName!=null&&createrName.length>2){
	 	imgName=createrName.substring(createrName.length-2,createrName.length);
	 }
	  var col=letterCode(imgName);
	 $("#imgName").html(imgName);
	 $("#imgDiv").addClass("head-bg-"+col);
	 if(createrPhoto!=null&&createrPhoto!=""&&createrPhoto!=undefined){
		 $("#photo").attr("src",createrPhoto);
		 $("#imgName").hide();
	 }else{
		 $("#photo").hide();
	 }
	$("#userName").html(createrName);
	//外出2小时
	var formData = jQuery.parseJSON(result.formData);
	var outTime = formData.outTime;
	$("#outTime").html("外出"+"<i>"+outTime+"</i>小时");
	
	//时间范围:6月8号&nbsp;上午&nbsp;-&nbsp;6月9号&nbsp;下午
	var beginTime = formData.beginYMD+""+formData.beginHMS;
	var endTime = formData.endYMD+""+formData.endHMS;
	
	$("#beginAndEndTime").html(beginTime+"&nbsp;-&nbsp;"+endTime);
	//原因
	var reason = formData.reason;
	$("#reason").html(reason);
	//图片
	processPhoto(formData.imgs);
	//审批历史
	processUserInfos(result.history,result.totalState,result.approverId);
}  
  
/**
 * webview加载完毕初始化导航条按钮
 */
function loadSuccess(isNetOk){
	h5Adapter.onHeaderButton(null,{"type":"back","onClickMethod":"goBack"});
}

//退出调查问卷模块
function goBack(){
	var operation=$("#operation").val();
	if(operation!=null&&operation!=""&&operation!=undefined&&operation=="view"){
		window.location.href = basePath+"mobile/logined/workflow/myApproveList.jsp?_clientType=wap&flag=processed";
	}else{
		h5Adapter.backLast();
	}
}