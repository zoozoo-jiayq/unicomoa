<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<% String id = request.getParameter("id");
	request.setAttribute("id",id);
 %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>
<% String noticeType= request.getParameter("noticeType"); 
  if("1".equals(noticeType)){ %>
   公告管理
<%}else if("2".equals(noticeType)){ %>
公告查看
<%} %>   

</title>
<jsp:include page="../../common/flatHead.jsp" />
<link href="${ctx}css/main.css" rel="stylesheet" type="text/css" />
<link href="${ctx}css/reset.css" rel="stylesheet" type="text/css" />
<link href="${ctx}css/main.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}plugins/datatable/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="${ctx}js/common/jquery.cookie.js"></script>
<script language="javascript" type="text/javascript" src="${ctx}plugins/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx}js/common/placeholder.js"></script>
<script type="text/javascript" src="${ctx}js/d_main.js"></script>


<script type="text/javascript" src="${ctx}js/common/calendar.js"></script>
<script type="text/javascript" src="${ctx}js/common/weather.js"></script>

<script>
 $(function(){
    getWeather();
    setInterval('refreshCalendarClock()', 1000);
 	$("#exit").click(function(){
		art.dialog.confirm("确定要退出吗？",function(){
			window.close();
		});
	});
	
	var noticeType= $("#noticeType").val();
	var id=$("#paramId").val();
	if(noticeType==1){
	$("#frame_main").attr("src",basePath+"logined/notify/list.jsp?id="+id);
	}else if(noticeType==2){
	$("#frame_main").attr("src",basePath+"/logined/notify/viewList.jsp?id="+id);
	}
 })
</script>
</head>
<body class="frame_class">
<input type="hidden" id="paramId" value="${param.id }"/>
<input type="hidden" id="noticeType" value="${param.noticeType}"/>
<!--头部 begin-->
	<div class="head">
		<table>
			<tr>
				<td style="width:350px;"><h1>移动通讯助理</h1>
				</td>
				<td valign="top">
					<div class="top_info">
						<span>欢迎您，<em class="name">${sessionScope.sessionUser.userName}！</em></span> 
						<span id="w_city"></span>&nbsp;&nbsp;<span id="w_weather"></span><span id="w_temperature"></span>
						<span id="date"><%=new java.text.SimpleDateFormat("yyyy年MM月dd日").format(new Date()) %> <%=new java.text.SimpleDateFormat("EEEE").format(new Date()) %></span>
						<span id="time" class="time"><%=Integer.parseInt( (new java.text.SimpleDateFormat("HH").format(new Date()) ) ) %>:<%=new java.text.SimpleDateFormat("mm:ss").format(new Date()) %></span>
					</div>
					<div class="down_operate">
						<a href="javascript:void(0)" class="icon_quit" id="exit">退出</a>
					</div></td>
			</tr>
		</table>
	</div>
	<iframe id="frame_main" src="" name="main_target" class="meetingIframe" frameborder="no" scrolling="auto" ></iframe>
      <!-- <div class="bottom"></div> -->
	
	<!--底部信息begin-->
	<div class="bottom"><span>中国移动通信集团河南有限公司</span></div>
	<!--底部信息end-->
</body>