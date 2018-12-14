<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	request.setAttribute("ctx", basePath);
	request.setAttribute("version", "3.1");
%>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0;"
	name="viewport" />
<meta content="yes" name="apple-mobile-web-app-capable" />
<meta content="black" name="apple-mobile-web-app-status-bar-style" />
<meta content="telephone=no" name="format-detection" />
<link href="${ctx}mobile/plugins/bootstrap/css/bootstrap.min.css"
	rel="stylesheet">
<link href="${ctx}mobile/plugins/Font-Awesome/css/font-awesome.min.css"
	rel="stylesheet">
<link href="${ctx}mobile/plugins/Flat-UI/css/flat-ui.min.css" rel="stylesheet">
<link rel="stylesheet" type="text/css"
	href="${ctx}mobile/css/question.css" />
<script type="text/javascript" src="${ctx}mobile/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="${ctx }mobile/js/html5Adapter.js"></script>
<script type="text/javascript" src="${ctx }mobile/js/base.js"></script>
<script type="text/javascript" src="${ctx }mobile/js/numConvert.js"></script>
<script type="text/javascript"
	src="${ctx}mobile/plugins/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript"
	src="${ctx}mobile/plugins/Flat-UI/js/flat-ui.min.js"></script>
<script type="text/javascript"
	src="${ctx}mobile/plugins/Flat-UI/js/application.js"></script>
<script type="text/javascript" src="${ctx }mobile/js/iscroll.js"></script>
<script type="text/javascript">
	var basePath = "${ctx}";
</script>
