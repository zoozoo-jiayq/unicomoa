/**
 * 日历js
 */
var _nowDate = new Date().getTime();
getNowDate();
setInterval(function(){getNowDate();},30000);
//获得当前服务器时间
function getNowDate(){
		$.ajax({
			url:	basePath+"/date/getNowDate.action?_clientType=wap&t="+Math.random(),
			type:	"get",
			dataType:"text",
			success:function(data){
				_nowDate=Number(data);
			}
		});
}


function Year_Month() {
	var now = new Date(_nowDate);
	var yy = now.getFullYear();
	var mm = now.getMonth() + 1;

	var cl = '';
	if (now.getDay() == 0)
		cl = '';
	if (now.getDay() == 6)
		cl = '';
	return (cl + yy + '年' + mm + '月');
}
function Date_of_Today() {
	var now = new Date(_nowDate);
	var cl = '';
	if (now.getDay() == 0)
		cl = '';
	if (now.getDay() == 6)
		cl = '';
	return (cl + now.getDate() + '');
}
function Day_of_Today() {
	var day = new Array();
	day[0] = "星期日";
	day[1] = "星期一";
	day[2] = "星期二";
	day[3] = "星期三";
	day[4] = "星期四";
	day[5] = "星期五";
	day[6] = "星期六";
	var now = new Date(_nowDate);
	var cl = '';
	if (now.getDay() == 0)
		cl = '';
	if (now.getDay() == 6)
		cl = '';
	return (cl + day[now.getDay()] + '');
}
function CurentTime() {
	var now = new Date(_nowDate);
	var hh = now.getHours();
	var mm = now.getMinutes();
	var ss = now.getTime() % 60000;
	ss = (ss - (ss % 1000)) / 1000;
	var clock = hh + ':';
	if (mm < 10)
		clock += '0';
	clock += mm + ':';
	if (ss < 10)
		clock += '0';
	clock += ss;
	return (clock);
}

function CurentWel() {
	var wel;
	var now = new Date(_nowDate);
	var hour = now.getHours();
	if (hour < 1)
		wel = '子时';
	else if (hour < 3)
		wel = '丑时';
	else if (hour < 5)
		wel = '寅时';
	else if (hour < 7)
		wel = '卯时';
	else if (hour < 9)
		wel = '辰时';
	else if (hour < 11)
		wel = '巳时';
	else if (hour < 13)
		wel = '午时';
	else if (hour < 15)
		wel = '未时';
	else if (hour < 17)
		wel = '申时';
	else if (hour < 19)
		wel = '酉时';
	else if (hour < 21)
		wel = '戌时';
	else if (hour < 23)
		wel = '亥时';
	else {
		wel = '子时';
	}
	return (wel);
}


function refreshCalendarClockFix() {
	_nowDate=Number(_nowDate)+Number(1000);
	var time = CurentTime();
	var year_month = Year_Month();
	var date = Date_of_Today();
	var week = Day_of_Today();
	var str = year_month + date + "日  " + week;
	$("#clockFix").html(str+"&nbsp;"+time);
}
setInterval('refreshCalendarClockFix()', 1000);