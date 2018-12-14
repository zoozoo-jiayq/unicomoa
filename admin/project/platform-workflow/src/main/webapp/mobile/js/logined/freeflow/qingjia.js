var bTime = "";
var eTime = "";
function processTime(id,selectTime){
	var r = new StringDate(selectTime).format();
	$("#"+id).html(r);
	if($("#beginTime").html()!=null&&$("#beginTime").html()!=""&&$("#endTime").html()!=null&&$("#endTime").html()!=""){
		$("#days").html(new StringDate(eTime).diff(new StringDate(bTime)));
	}
}

function compareTime(selectTime){
	var nowDate=new Date();
	var timeAry=selectTime.split(" ");
	var selDate=new Date(timeAry[0].replace("-","/"));
	if(timeAry[1]=="上午"){
		selDate.setHours(0, 0, 0, 0);
	}
	if(timeAry[1]=="下午"){
		selDate.setHours(11, 59, 59, 999);
	}
	if(selDate.valueOf()<nowDate.valueOf()){
		mui.toast("选择时间不能小于当前时间!");
		return true;
	}else{
		return false;
	}
}
//打开请假类型页面
function selectType(){
	mui.openWindow({
		url:"leaveType.html",
		id:"leaveType"
	});
}
function getSelectType(type){
	$("#leaveType").html(type);
}
//获取表单数据
function getFormData(){
	//请假类型
	var leaveType = $("#leaveType").html();
	//开始时间
	var beginTime = $("#beginTime").html();
	//结束时间
	var endTime = $("#endTime").html();
	//请假天数
	var days = $("#days").html();
	//请假原因
	var reason = $("#reason").val();

	var data = new Object();
	data.leaveType = leaveType;
	data.beginTime = beginTime;
	data.endTime = endTime;
	data.days = days;
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
	var userId = window.windowCommon.approveLoginId;
	//发送数据
	jQuery.ajax({
		   type: "POST",
		   url: window.windowCommon.basePath+"baseworkflow/start.c?_clientType=wap",
		   data: {"formData":datas,"userIds":userIds,"userId":userId,"code":"qingjia"}, 
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
	//开始时间
	var beginTime = $("#beginTime").html();
	//结束时间
	var endTime = $("#endTime").html();
	if(beginTime == ""||beginTime == null){
		mui.toast("请选择开始时间");
		return false;
	}
	if(endTime == ""||endTime == null){
		mui.toast("请选择结束时间");
		return false;
	}
	if(reason == ""||reason == null){
		mui.toast("请输入请假原因");
		return false;
	}
	if(reason.length>=500){
		mui.toast("请假原因应小于500个字");
		return false;
	}
	if(!userIds){
		mui.toast("请选择人员");
		return false;
	}
	
	return true;
}

mui.plusReady(function(){
	//获取默认开始时间，结束时间
	var date=addDate(new Date(),1);
	var year=date.getFullYear();
	var month=date.getMonth()+1;
	var day=date.getDate();
	bTime=year+"-"+month+"-"+day+" 上午";
	eTime=year+"-"+month+"-"+day+" 下午";
	$("#beginTime").html(new StringDate(bTime).format());
	$("#endTime").html(new StringDate(eTime).format());
	if($("#beginTime").html()!=null&&$("#beginTime").html()!=""&&$("#endTime").html()!=null&&$("#endTime").html()!=""){
		$("#days").html(new StringDate(eTime).diff(new StringDate(bTime)));
	}
	//绑定选择请假类型事件
	document.getElementById("leaveType").addEventListener("tap",function(){
		selectType();
	});
	//设置选择时间控件
	$("#beginTime").click(function(){
		var defaultAry=[bTime,"yyyy-MM-dd a"];
		plus.qytxplugin.showDateTimeHalf(defaultAry,function(da){
				//判断选择时间是否小于当前时间
//				if(compareTime(da.date)){
//					return;
//				};
				if(bTime!=""&&eTime!=""){
					var cr = new StringDate(eTime).compare(new StringDate(da.date));
					if(cr<0){
						mui.toast("开始时间不能大于结束时间!");
						return;
					}
				}
				bTime = da.date;
				processTime("beginTime",bTime);
		});
	});
	//设置结束时间控件
	$("#endTime").click(function(){
		var defaultAry=[eTime,"yyyy-MM-dd a"];
		plus.qytxplugin.showDateTimeHalf(defaultAry,function(da){
			//判断选择时间是否小于当前时间
//				if(compareTime(da.date)){
//					return;
//				};
				if(bTime!=""&&eTime!=""){
					var cr = new StringDate(da.date).compare(new StringDate(bTime));
					if(cr<0){
						mui.toast("开始时间不能大于结束时间!");
						return;
					}
				}
				eTime = da.date;
				processTime("endTime",eTime);
		});
	});
});