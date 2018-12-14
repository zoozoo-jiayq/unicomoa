<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<% String id = request.getParameter("id");
String path = request.getContextPath();
String basePath = request.getScheme() + "://"  + request.getServerName() + ":" + request.getServerPort()  + path+"/" ;
request.setAttribute("ctx", basePath);
%>
<!DOCTYPE html PUBLIC "-//WAPFORUM//DTD XHTML Mobile 1.0//EN" "http://www.wapforum.org/DTD/xhtml-mobile10.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="${ctx}js/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="${ctx}js/logined/notify/mobileView.js"></script>
<title>查看公告-手机版</title>
<script type="text/javascript">
	var basePath ="${ctx}";
</script>
<style>
  *{ margin:0; padding:0;}
  h1{ font-size:31px; color:#333333; padding:5px 8px; line-height:25px;font-weight:bold;}
  h2{ font-size:22px; color:#888888; padding:0 8px;}
  h2 span{ color:#607fa6;}
  p{ font-size:28px; color:#333333; padding:5px 8px; line-height:25px;}
</style>
</head>
<body>
<input type="hidden" name="id" id="id" value="<%=id%>"/>
<h1 id="title"></h1>
<h2 id="info"></h2>
<p id="content"></p>
</body>
</html>
