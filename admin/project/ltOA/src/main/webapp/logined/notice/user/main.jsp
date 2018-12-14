<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.*"%>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="cn.com.qytx.platform.security.*" %>
<%@ page import="cn.com.qytx.platform.org.domain.UserInfo" %>
<%
//模拟登陆
UserInfo loginUser=new UserInfo();
loginUser.setUserId(1);
loginUser.setUserName("李四");
loginUser.setCompanyId(1);
SessionVariable  sessionVariable=new SessionVariable(loginUser);
session.setAttribute("sessionVariable",sessionVariable);
session.setAttribute("modelCategory",1);
%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
			SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");//设置日期格式
			String nowTime = df.format(new Date());
			SimpleDateFormat df2 = new SimpleDateFormat("yyyy年MM月dd日 EEEE");//设置日期格式
			String week = df2.format(new Date());
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>通知公告</title>
<link rel="shortcut icon" href="<%=basePath%>images/favicon.ico" type="image/x-icon" />
<link href="<%=basePath%>css/baseStyle.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath%>css/indexMain.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath%>css/index.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=basePath%>js/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="<%=basePath%>js/main.js"></script>
<script type="text/javascript" src="<%=basePath%>js/plugins/dialog/artDialog.js?skin=blue"></script>
<script type="text/javascript" src="<%=basePath%>js/plugins/dialog/iframeTools.js"></script>
<script type="text/javascript" >
 var basePath='<%=basePath%>';
	$(document).ready(function () {
       //单击退出
       $("#loginOut").click(function(){
           self.close();
		});
		//getWeather();
	});
</script>
<script type="text/javascript" src="<%=basePath%>js/weather.js"></script>
<script type="text/javascript" src="<%=basePath%>js/calendar.js"></script>
<style type="text/css">
html {
	overflow: hidden;
}
</style>
</head>
<body class="frame_class">
	<input type="hidden" id="needCompleted"
		value="${companySetting.needCompleted}" />
	<input type="hidden" id="isCompleted"
		value="${companySetting.completed}" />
	<!--头部 begin-->
	<div class="head">
		<div class="main_head">
			<div class="head_r_menu" style="width: 400px">
				<p class="top_ul">
					<span id="week"><%=week %> </span>
					<span id="clock" ><font><%=nowTime %></font></span>
				</p>
			    <ul class="down_ul">
			       <li>
		              <a  href="javascript:void(0);"  class="icon_head_out" title="退出" id=loginOut>退出</a>
			       </li>
			    </ul>
			</div>
			<div class="fl">
				<a id="logo" target="_self"><img
					src="<%=basePath%>images/index/logo.png" /> </a>
			</div>
			<div class="u_info">
				欢迎您 <font>（${sessionVariable.user.userName}  )</font>
			</div>
		</div>
	</div>
	<div>
		<iframe src="<%=basePath%>logined/notice/user/mainIndex.jsp"  id="iframe_main" class="meetingIframe"
			name="iframe_main" frameborder="0" scrolling="auto"></iframe>
	</div>
	<!--底部信息begin-->
	<div class="bottom">
		<span>中国移动通信集团河南有限公司</span>
	</div>
	<!--底部信息end-->
</body>
</html>