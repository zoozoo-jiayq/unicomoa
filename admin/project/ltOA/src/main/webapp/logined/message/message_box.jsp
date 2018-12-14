<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>微讯盒子</title>
<%@ include file="../../common/taglibs.jsp" %>
<jsp:include page="../../common/head.jsp"/>
<script type="text/javascript">
    var basePath = "${ctx}";
</script>
<script type="text/javascript" src="${ctx}plugins/accordion/accordion.js"></script>

<link rel="stylesheet" type="text/css"
	href="${ctx}plugins/accordion/accordion.css">
<style type="text/css">
#container {
	width: 100%;
	height: 100%;
}

#leftMenu {
	width: 18%;
	height: 100%;
	border: 1px solid #CDCDCD;
	border-right: none;
	float: left;
}

#spacerBar {
	width: 5px;
	height: 100%;
	background-color: #e5e5e5;
	border: 1px solid #CDCDCD;
	float: left;
}

#rightContent {
	width: 80%;
	height: 100%;
	border: 1px solid #CDCDCD;
	border-left: none;
	float: left;
}
</style>

</head>
<body>
	<div id="container">
		<!-- 左边菜单 -->
		<div id="leftMenu">
			<div id="emailMenu">
				<div id="wrapper" style="width:100%">
					<ul class="accordion">
						<li><a href="${ctx}logined/message/list_new_message.jsp" target="message_main">未确认微讯</a></li>
						<li><a href="${ctx}logined/message/send_message.jsp" target="message_main">发送微讯</a></li>
						<li><a href="${ctx}logined/message/list_user_message.jsp?userId=3&userName=huang" target="message_main">对话记录管理</a></li>
						<li><a href="${ctx}logined/message/list_all_message.jsp" target="message_main">微讯查询</a></li>
					</ul>
				</div>
			</div>
		</div>
		<!-- 间隔条 -->
		<div id="spacerBar"></div>
		<!-- 右边详情 -->
		<div id="rightContent">
			<iframe  class="iframeresize"  frameborder="0" height="100%" width="100%" scrolling="auto"
				style="height: 100%;width: 100%" name="message_main" ></iframe>
		</div>
	</div>
</body>
</html>
