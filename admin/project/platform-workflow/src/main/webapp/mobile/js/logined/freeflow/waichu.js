var bTime = "";
var eTime = "";

function compare(beginTime,endTime){
	var d1 = Date.parse(beginTime.replace(/-/g,"/"));
	var d2 = Date.parse(endTime.replace(/-/g,"/"));
	if(d2<=d1){
		mui.toast("开始时间不能大于结束时间");
		return false;
	}
	return true;
}
function compareTime(selectTime){
	var nowDate=new Date();
	nowDate.setSeconds(0, 0);
	var selDate=new Date(selectTime.replace("-","/"));
	if(selDate.valueOf()<nowDate.valueOf()){
		mui.toast("选择时间不能小于当前时间");
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
//	data.imgs = photos;
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
	var userId=window.windowCommon.approveLoginId;
	//发送数据
	jQuery.ajax({
		   type: "POST",
		   url: window.windowCommon.basePath+"baseworkflow/start.c?_clientType=wap",
		   data: {"formData":datas,"userIds":userIds,"userId":userId,"code":"waichu"}, 
		   success: function(msg){
		    if(msg.indexOf("100||")==0){
		    	mui.toast("提交成功");
				//打开我的申请页面 
            	mui.openWindow({
					url: '../fixedflow/myStartList.html',
					id:'myStartList'
				});
		     }else{
		     	 mui.toast("提交失败");
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
		mui.toast("请输入外出原因");
		return false;
	}else if(reason.length>=500){
		mui.toast("外出原因字应小于500个字");
		return false;
	}else if(!userIds){
		mui.toast("请选择人员");
		return false;
	}else{
		return true;
	}
}

  
mui.plusReady(function(){
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
	
	$("#beginTime").click(function(){
		var defaultAry=[bTime,"yyyy-MM-dd HH:mm:ss"];
		plus.qytxplugin.selectDateTime(defaultAry,function(da){
				//判断选择时间是否小于当前时间
//				if(compareTime(da.date)){
//					return;
//				};
				if(compare(da.date,eTime)){
					bTime = da.date;
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
		});
		
	});
	//设置结束时间控件
	$("#endTime").click(function(){
		var defaultAry=[eTime,"yyyy-MM-dd HH:mm:ss"];
		plus.qytxplugin.selectDateTime(defaultAry,function(da){
				//判断选择时间是否小于当前时间
//				if(compareTime(da.date)){
//					return;
//				};
				if(compare(bTime,da.date)){
					eTime = da.date;
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
		});
		
	});
		
});