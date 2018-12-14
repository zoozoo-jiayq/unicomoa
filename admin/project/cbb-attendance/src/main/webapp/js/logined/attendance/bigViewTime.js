$(function(){
	/**
	 * 得到当前时间,利用定时器
	 */
	getNowTime();
	window.setInterval("getNowTime()",1000);
	getData();
	window.setInterval("getData()",1000);
	
	referPage();
})
/**
 * 获得大屏报表（签到）列表
 */
function getData(){
	$.ajax({
		url:basePath+"attWap/attendanceWap_getBigAttendanceReportOther.action?_clientType=wap",
		type:"post",
		data:{"userId":1},
		dataType:"text",
		success:function(result){
			if(result.indexOf("100||")==0){
				result = result.substring(5);
				result = eval('(' + result + ')');
				var goSignhtml="";
				/**
				 * 已签到
				 */
				for(var i=0;i<result.signList.length&&i<24;i++){
					var time=new Date(result.signList[i].createTime);
					var userName=result.signList[i].userName;
					var photoPath=result.signList[i].photoPath;
					if(i%3==0){
						goSignhtml+="<div class='row'>";
					}
					goSignhtml+="<div class='col'><div class='info'><span class='img-box'><img src='"+serverPath+"/files/upload/"+photoPath+"' alt=''></span>";
					goSignhtml+="<span class='name'>"+userName+"</span>";
					goSignhtml+="<span class='time'>"+FormatTime(time).substr(11)+"</span></div></div>";
					if(i%3==2){
						goSignhtml+="</div>";
					}
				}
			}
			
			if(goSignhtml==""){
				$(".people-box").html("<p style='color:#fff;text-align:center' >暂无数据</p>")
			}else{
				$(".people-box").html(goSignhtml)
			}
			var count=result.signList.length;
			$(".amount").html("共计"+count+"人")
			
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
	hour<10 ? hour="0"+hour:hour
	minute<10 ? minute="0"+minute:minute
	second<10 ? second="0"+second:second
	
	NowTime=""+year+"年"+month+"月"+day+"日"+" "+xingqi+" ";
	$(".today-time").html(NowTime);
	
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
			
	NowTime=""+year+"年"+month+"月"+day+"日 "+hour+":"+minute+":"+second;
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

