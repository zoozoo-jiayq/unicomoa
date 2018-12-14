<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<jsp:include page="../common/taglibs.jsp"/>
	<title>绩效考核</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script src="${ctx}/logined/appraisal/common/jxkh.js"></script>
	<script type="text/javascript" src="${ctx}/logined/appraisal/plugins/jquery.uploadify.min.js"></script>
	<script type="text/javascript" src="${ctx}/logined/appraisal/plugins/attachHelp.js"></script>
	<script type="text/javascript" src="${ctx}/logined/appraisal/plugins/attachment.js"></script>
	<script type="text/javascript" src="${ctx}/logined/appraisal/common/commonUpload.js"></script>

	<script type="text/javascript" src="${ctx}/logined/appraisal/common/weather.js"></script>
	<script type="text/javascript">    
		function startTime(){    
			var today=new Date()    
			var week=new Array("星期日","星期一","星期二","星期三","星期四","星期五","星期六");    
			var year=today.getFullYear()    
			var month=today.getMonth()+1    
			var date=today.getDate()    
			var day=today.getDay()    
			var h=today.getHours()    
			var m=today.getMinutes()    
			var s=today.getSeconds()    
			// add a zero in front of numbers<10    
			h=checkTime(h)    
			m=checkTime(m)    
			s=checkTime(s)    
			//document.getElementById('time').innerHTML=" "+year+"年"+month+"月"+date+"日  "+week[day]+"  "+h+":"+m+":"+s+" "    
			document.getElementById('yearweek').innerHTML=" "+year+"年"+month+"月"+date+"日  "+week[day]
			document.getElementById('hms').innerHTML= "  "+h+":"+m+":"+s+" "
			t=setTimeout('startTime()',500)    
		}    
	    
		function checkTime(i){    
			if (i<10){
				i="0" + i
			}    
			return i    
		}
	
		function time(){  
		    today=new Date();  
		    function initArray(){  
		        this.length=initArray.arguments.length  
		        for(var i=0;i<this.length;i++)  
		            this[i+1]=initArray.arguments[i];  
		    }  
		    var d=new initArray(" 星期日"," 星期一"," 星期二"," 星期三"," 星期四"," 星期五"," 星期六");  
		    var year=today.getFullYear();  
		    var month=today.getMonth()+1;  
		    var day=today.getDate();  
		    var str = year+"年"+month+"月"+day+"日"+d[today.getDay()+1];  
		    return str;  
		}
	</script>  
</head>
	<body onload="startTime()">
		<div class="header">
			<div class="top_logo"></div>
			<p class="top_title">新密市人民检察院绩效考核系统</p>
			
			<div class="fr top_info_box">		
				 <div>
				 	<p class="overf">
						<span class="fl top_info"> 欢迎你，</span>
					 	<span class="fl mr10 top_info" >${session.adminUser.userName }</span>
						<span class="current_time fl" id="hms"></span>
				 	</p>
					<!-- <span id="w_city"></span>&nbsp;<span class="mr15" id="w_weather"></span> -->
				</div>
				<p>
					<!-- <span class="mr10" id="w_temperature"></span> -->
					<span id="yearweek"></span>
				</p> 
				
				<!-- <p class="mt5">
					<span class="top_btn set_icon"></span>
					<span class="top_btn exit_icon"></span>
				</p> -->
			</div>
		</div>
	</body>
</html>
