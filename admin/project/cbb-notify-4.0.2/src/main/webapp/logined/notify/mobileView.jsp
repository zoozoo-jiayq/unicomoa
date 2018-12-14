<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<% String id = request.getParameter("id");
String path = request.getContextPath();
String basePath = request.getScheme() + "://"  + request.getServerName() + ":" + request.getServerPort()  + path+"/" ;
request.setAttribute("ctx", basePath);
%>
<!DOCTYPE html PUBLIC "-//WAPFORUM//DTD XHTML Mobile 1.0//EN" "http://www.wapforum.org/DTD/xhtml-mobile10.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<META name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=0">
<META name="apple-mobile-web-app-capable" content="yes">
<META name="apple-mobile-web-app-status-bar-style" content="black">
<META name="format-detection" content="telephone=no">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="${ctx}js/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="${ctx}js/logined/notify/mobileType.js"></script>
<script type="text/javascript" src="${ctx}js/logined/notify/mobileView.js"></script>
<title>查看</title>
<script type="text/javascript">
	var basePath ="${ctx}";
	var downPath = basePath + "filemanager/prevViewByPath.action?_clientType=wap&filePath=";
</script>
<style>
* {
	margin: 0px;
	padding: 0px;
	font-style: normal;
}
a {
	color: rgb(96, 127, 166);
	text-decoration: none;
}
html {
	line-height: 1.6;
	-ms-text-size-adjust: 100%;
	-webkit-text-size-adjust: 100%;
}
body {
	line-height: inherit;
	font-family: "Helvetica Neue", Helvetica, Arial, sans-serif;
	background: #fff;
	-webkit-touch-callout: none;
	word-break:keep-all;word-wrap:break-word;
}
.articleDetail {
	padding: 10px 15px 15px;
}
.articleDetail h1 {
	margin-bottom: 12px;
	line-height: 1.4;
	font-weight: 400;
	font-size: 24px;
	font-weight: normal;
	word-break:keep-all;word-wrap:break-word;
}
.articleDetail h2 {
	margin-bottom: 28px;
	line-height: 20px;
	font-size: 15px;
	overflow: hidden;
	color: #8c8c8c;
	font-weight: normal;
	word-break:keep-all;word-wrap:break-word;
}
.articleDetail h2 span {
	color: #607fa6;
	margin-left: 8px;
}
.articleDetail p {
	font-size: 18px;
	min-height: 1em;
	white-space: pre-wrap;
	clear: both;
	line-height: 1.6;
	display: block;
	-webkit-margin-before: 0px;
	-webkit-margin-after: 1em;
	-webkit-margin-start: 0px;
	-webkit-margin-end: 0px;
}
.articleDetail div {
	display: block;
}
.content {
	overflow: hidden;
	color: #3e3e3e;
	font-size: 18px;
	line-height: 1.6;
}
.content img {
	max-width: 100%;
	margin-bottom: 6px;
}
.articleDetail h2 .num{ 
	background:url(../../images/eye.png) no-repeat  left center;
	background-size:14px;
	padding-left:20px; 
	margin-left: 15px;
	color:#bbbbbb;
}
</style>
</head>
<body>
<div class="articleDetail">
<input type="hidden" name="id" id="id" value="<%=id%>"/>
<h1 id="title"></h1>
<h2 id="info"></h2> 
<div id="content" class="content"></div>
</div>
</body>
</html>
