//日期工具，date格式：yyyy-MM-dd HH:mm:ss
function StringDate(date){
	this.stringDate = date;
}
//当前pattern支持的格式如下：xx月xx日 上午
StringDate.prototype.format = function(){
	var str = this.stringDate.split(" ");
	var str1 = str[0]; //年月日
	var str2 = str[1]; //上午或下午
	
	var ymd = str1.split("-");
	var r1 = Number(ymd[1])+"月"+Number(ymd[2])+"日";
	
	var r2 = str2;
	return r1+" "+r2;
};

//比较日期 日期格式 yyyy-MM-dd HH:mm:ss 或 yyyy-MM-dd 上午/下午
StringDate.prototype.compare = function(other) {
	if (!other.stringDate) {
		return 1;
	}
	if (!this.stringDate) {
		return 1;
	}
	var dateAry1 = this.stringDate.split(" ");
	var dateAry2 = other.stringDate.split(" ");
	var d1 = Date.parse(dateAry1[0].replace(/-/g, "/"));
	var d2 = Date.parse(dateAry2[0].replace(/-/g, "/"));
	var ret = 1;
	if (d2 > d1) {
		ret = -1;
	} else if (d2 == d1) {
		if (dateAry1[1] == "下午" || dateAry1[1] == "上午") {
			if (dateAry2[1] == "下午" && dateAry1[1] == "上午") {
				ret = -1;
			}
		} else {
			var d11 = Date.parse(endTime.replace(/-/g, "/"));
			var d22 = Date.parse(startTime.replace(/-/g, "/"));
			if (d22 > d11) {
				ret = -1;
			}
		}

	} else {
		ret = 1;
	}
	return ret;
};

//间隔天数，精确到0.5天
StringDate.prototype.diff = function(other){
	if(!other.stringDate){
		return "";
	}
	if(!this.stringDate){
		return "";
	}
	var r1 = this.stringDate.split(" ")[0];
	var r2 = other.stringDate.split(" ")[0];
	var diff = DateDiff(r1,r2);
	
	var r11 = this.stringDate.split(" ")[1];
	var r22 = other.stringDate.split(" ")[1];
	if(r11=="上午"){
		r11=0;
	}else{
		r11=1;
	}
	if(r22=="上午"){
		r22=0;
	}else{
		r22=1;
	}
	if(r11-r22>0){
		diff = diff+1;
	}
	if(r11-r22<0){
		diff= diff+0;
	}
	if(r11-r22==0){
		diff=diff+0.5;
	}
	return diff;
};


//计算两个日期的间隔天数  
function DateDiff(sDate1, sDate2){ //sDate1和sDate2是2002-12-18格式   
	var aDate, oDate1, oDate2, iDays;   
	aDate = sDate1.split("-");
	oDate1 = new Date(aDate[0] + '/' + aDate[1] + '/' + aDate[2]); //转换为12-18-2002格式   
	aDate = sDate2.split("-");  
	oDate2 = new Date(aDate[1] + '/' + aDate[2] + '/' + aDate[0]);   
	iDays = parseInt(Math.abs(oDate1 - oDate2) / 1000 / 60 / 60 /24); //把相差的毫秒数转换为天数   
	return iDays;   
}
//计算两个日期的间隔小时数  
function HoursDiff(sDate1, sDate2){ //sDate1和sDate2是2002-12-18 13:00:00格式   
	var aDate, oDate1, oDate2, iHours;
	aDate = sDate1.split(" ")[0].split("-");   
	oDate1 = Date.parse(aDate[0] + '/' + aDate[1] + '/' + aDate[2]+' '+sDate1.split(" ")[1]); //转换为12-18-2002 13:00:00格式   
	aDate = sDate2.split(" ")[0].split("-");   
	oDate2 = Date.parse(aDate[0] + '/' + aDate[1] + '/' + aDate[2]+' '+sDate2.split(" ")[1]);   
	iHours = parseInt(Math.abs(oDate1 - oDate2) / 1000 / 60 / 60); //把相差的毫秒数转换为小时数   
	return iHours;   
} 
//加减日期天数
function addDate(sDate,days){
	var longTime=sDate.valueOf();
	longTime=longTime+Number(days)*24*60*60*1000;
	return new Date(longTime);
}
//加减日期小时数
function addHours(sDate,hours){
	var longTime=sDate.valueOf();
	longTime=longTime+Number(hours)*60*60*1000;
	return new Date(longTime);
}
