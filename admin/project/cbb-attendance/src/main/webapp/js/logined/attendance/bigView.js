$(function(){
	/**
	 * 得到当前时间,利用定时器
	 */
	getNowTime()
	window.setInterval("getNowTime()",1000)
	getData();
	window.setInterval("getData()",10000);
	referPage()
	$(".col").mouseover();
	$(".col").mouseout();
})
/**
 * 获得大屏报表（签到、外勤、迟到、请假）列表
 */
function getData(){
	$.ajax({
		url:basePath+"attWap/attendanceWap_getBigAttendanceReport.action?_clientType=wap",
		type:"post",
		data:{"userId":1},
		dataType:"text",
		success:function(result){
			if(result.indexOf("100||")==0){
				result = result.substring(5);
				result = eval('(' + result + ')');
				var goOuthtml="";
				var goLeavehtml="";
				var goSignhtml="";
				var goLatehtml="";
				
				/**
				 * 外勤
				 */
				for(var i=0;i<result.goOutList.length;i++){
					var date=new Date(result.goOutList[i].createTime)
					goOuthtml+="<li><span class='width30'>"+result.goOutList[i].userName+"</span>";
					goOuthtml+="<span class='width30'>"+FormatTime(date)+"</span>";
					goOuthtml+="<span class='width40'>"+(result.goOutList[i].memo?result.goOutList[i].memo:'无')+"</span></li>";
				}
				/**
				 * 请假
				 */
				for(var i=0;i<result.leaveList.length;i++){
					var startdate=new Date(result.leaveList[i].startLeaveTime)
					var enddate=new Date(result.leaveList[i].endLeaveTime)
					goLeavehtml+="<li><span class='width30'>"+result.leaveList[i].userName+"</span>";
					goLeavehtml+="<span class='width35'>"+FormatTime_MMDDHHMM(startdate)+"</span>";
					goLeavehtml+="<span class='width35'>"+FormatTime_MMDDHHMM(enddate)+"</span></li>";
				}
				/**
				 * 已签到
				 */
				for(var i=0;i<result.signList.length;i++){
					//format("yyyy-MM-dd hh:mm:ss")
					var date=new Date(result.signList[i].createTime)
					goSignhtml+="<li><span class='width50'>"+result.signList[i].userName+"</span>";
					goSignhtml+="<span class='width50'>"+FormatTime(date)+"</span></li>";
				}
				/**
				 * 迟到
				 */
				for(var i=0;i<result.lateList.length;i++){
					var date=new Date(result.lateList[i].createTime)
					goLatehtml+="<li><span class='width40'>"+result.lateList[i].userName+"</span>";
					goLatehtml+="<span class='width60'>"+FormatTime(date)+"</span></li>";
				}
			}
			if(goLeavehtml==""){
				$("#goLeaveList").html("<p style='font-size:14px;color:#fff;text-align:center;height:30px;line-height:30px;' >无人员请假</p>")
			}else{
				$("#goLeaveList").html(goLeavehtml)
			}
			
			if(goOuthtml==""){
				$("#goOutList").html("<p style='font-size:14px;color:#fff;text-align:center;height:30px;line-height:30px;' >无人员外出</p>")
			}else{
				$("#goOutList").html(goOuthtml)
			}
			
			if(goSignhtml==""){
				$("#goSignList").html("<p style='font-size:14px;color:#fff;text-align:center;height:30px;line-height:30px;' >无人员签到</p>")
			}else{
				$("#goSignList").html(goSignhtml)
			}
			
			if(goLatehtml==""){
				$("#goLateList").html("<p style='font-size:14px;color:#fff;text-align:center;height:30px;line-height:30px;' >无人员迟到</p>")
			}else{
				$("#goLateList").html(goLatehtml)
			}
		}
	})
}


/**
 * 得到当前时间
 */
function getNowTime(){
	var NowTime="";
	var date=new Date();
	var year=date.getFullYear();
	var month=date.getMonth()+1;
	var day=date.getDate();
	var hour=date.getHours();
	var minute=date.getMinutes();
	var second=date.getSeconds();
	var xingqi=date.getDay();
	switch(xingqi){
		case 1:
			xingqi="星期一";
			break;
		case 2:
			xingqi="星期二";
			break;
		case 3:
			xingqi="星期三";
			break;
		case 4:
			xingqi="星期四";
			break;
		case 5:
			xingqi="星期五";
			break;
		case 6:
			xingqi="星期六";
			break;
		case 7:
			xingqi="星期日";
			break;
	}
	minute<10 ? minute="0"+minute:minute
	second<10 ? second="0"+second:second
	
	NowTime=""+year+"年"+month+"月"+day+"日"+" "+xingqi+" "+hour+":"+minute+":"+second;
	$("#timeView").html(NowTime);
	
}

/**
 * 得到当前时间,格式化时间字符串
 */
function FormatTime(date){
//	var NowTime="";
//	var date=new Date();
	var year=date.getFullYear();
	var month=date.getMonth()+1;
	var day=date.getDate();
	
	var hour=date.getHours();
	var minute=date.getMinutes();
	var second=date.getSeconds();
	
	hour<10 ? hour="0"+hour:hour
	minute<10 ? minute="0"+minute:minute
	second<10 ? second="0"+second:second
			
	//NowTime=""+year+"年"+month+"月"+day+"日 "+hour+":"+minute+":"+second;
NowTime=hour+":"+minute+":"+second;
	return NowTime;
	
}



/**
 * 得到当前时间,格式化时间字符串
 */
function FormatTime_MMDDHHMM(date){
	var NowTime="";

	var month=date.getMonth()+1;
	var day=date.getDate();
	
	var hour=date.getHours();
	var minute=date.getMinutes();
	var second=date.getSeconds();
	month = (month <10 ? ("0"+month):month);
day = (day <10 ? ("0"+day):day);
	hour =  (hour<10 ? ("0"+hour):hour);
	minute = (minute<10 ? ("0"+minute):minute);
	second = (second<10 ? ("0"+second):second)
			
	
NowTime=month+"-"+day+" "+hour+":"+minute;
	return NowTime;
	
}

/**
 * 当按f11的时候刷新页面,因为全屏的时候下面会出现空白，需要刷新一下
 */

function referPage(){
	$(document).keydown(function(event){
		　　　　if(event.keyCode == 122){
		　　　　　　 window.location.reload();
		　　　　}
	});
}

