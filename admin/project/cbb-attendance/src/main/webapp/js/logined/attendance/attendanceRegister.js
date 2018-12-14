/**
 * 页面加载
 */
$(document).ready(function() {
	//获取日历
	getServiceTime();
	//动态的获取时间
	setInterval(getNowTime,1000);
	getNowTime();
 
	//点击打卡
	$("#click").click(function(){
		punchCard();}
	);

});

/**
 * 打卡
 */
function punchCard(){
	//打卡完先置为disabled
	qytx.app.dialog.confirm("确认要打卡吗？",function(){
		$("#click").attr("disabled","disabled");
		qytx.app.ajax({
			url :basePath + "attendance/punchCard.action" ,
			type : "post",
			success : function(data) {
			if(data=="1"){
				qytx.app.dialog.tips("打卡成功");
			}else if(data=="0"){
				qytx.app.dialog.tips("不在打卡范围内!");
			}else{
				qytx.app.dialog.tips("打卡失败!");
			}
			//读取记录
			loadRecords();
			}
		});
	});
	
}

/**
 * 获取服务器时间
 * @return
 */
function getServiceTime(){
	qytx.app.ajax({
		url :basePath + "attendance/getServiceTime.action" ,
		type : "post",
		dataType : "json",
		success : function(data) {
			if(data){
				var data=data.split(">");	
				var dateStr = data[0].split("-");
				$("#day").val(data[0]);
//				$("#date1").html(dateStr[0]+"<span>年</span>"+dateStr[1]+"<span>月</span>"+dateStr[2]+"<span>日</span>");
				$("#date1").html(dateStr[0]+"<span>年</span>"+dateStr[1]+"<span>月</span>");
				$("#date2").html(data[1]);
				$("#date3").html(data[2]);
				var date4="农历"+data[3];
				$("#date4").html(date4);
				//读取记录
				loadRecords();
			}
	}
	});
}
/**
 * 读取记录
 */
function loadRecords(){
	
	var timeStr = $("#day").val();
	var paramData = {
			"day":timeStr
	};
	var url=basePath + "attendance/punchReport.action";
	qytx.app.ajax({
		url : url,
		type : "post",
		data : paramData,
		dataType : "json",
		success : function(data) {	
			var t=data.split("+");
			$("#tBody").html(t[0]);
			$("#lastTime").val(t[1]);

	}
	});
}

/**
 * 动态获取时间
 */
function getNowTime(){
	var lastTime = $("#lastTime").val();
	var limitTime=10;
	var paramData = {
			"lastTime":lastTime,
			"limitTime":limitTime
	};
	qytx.app.ajax({
		url :basePath + "attendance/getServiceTime.action" ,
		type : "post",
		data : paramData,
		dataType : "json",
		success : function(data) {
		var data=data.split(">");	
		var nowTime=data[4];
		var nowTime_s=data[5];
		$("#nowTime").html(nowTime+"&nbsp;")
		$("#nowTime_s").html(nowTime_s);		
		if(data[6]=='0'){
			$("#click").attr("disabled","disabled");
			$("#click").attr("class","btn_grey");
		}else{
			$("#click").removeAttr("disabled");
			$("#click").attr("class","btn_blue");
		}
		//当到00:00:00点时,刷新页面
		var H_M=nowTime.split(":");
		var h=H_M[0];
		var m=H_M[1];
		var s=nowTime_s;
		if(h=='00' && m=='00' && s=='00'){
			window.location.reload();
		}
	}
	});
}
