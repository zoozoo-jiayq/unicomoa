<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>学习</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="viewport" content="initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
    <!--css-->
    <%
    String basePath = "http://192.168.10.28:8080/qytx-oa-mysql/" ;
    request.setAttribute("ctx", basePath);
    String userId=request.getParameter("userId"); 
    request.setAttribute("userId", userId);
    String loginUserNow=request.getParameter("loginUserNow"); 
    request.setAttribute("loginUserNow", loginUserNow);
%>   
<script>
  var basePath="${ctx}";
  var userId="${userId}"
  var loginUserNow="${loginUserNow}"
</script>
	<link href="${ctx}css/wapRecord/wapRecord.css" rel="stylesheet">
	<script src="../../js/jquery-1.8.3.min.js"></script>
	<script type="text/javascript" src="${ctx}js/logined/wapRecord/studyList.js"></script>
</head>
<input type="hidden" id="userId" value="${param.userId}">
<body class="bg-gray">
    <div class="header-bar">
			<i class="icons icon-18x18 icon-return" onclick="javascript:window.history.back()"></i>
			<span class="title">学习</span>
	</div>
		<!--end .header-bar-->
		<div class="container-wrapper">	
			<div class="reward-list top5" id="list">
			</div>
			<!--end .reward-list-->
		</div>
		<!--end .container-wrapper-->
		<p class="loading-more" id="moreData">点击加载更多</p>
		<p class="loading-more" id="nohave">暂无数据</p>
</body>
</html>