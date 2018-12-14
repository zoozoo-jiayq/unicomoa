<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>联系人查看</title>
<%@ include file="../../common/taglibs.jsp"%>
<jsp:include page="../../common/head.jsp" />
<script type="text/javascript">
	var basePath = "${ctx}";
</script>
<title>系统提醒</title>
<script type="text/javascript"
	src="${ctx}js/logined/affairs/affairs_main.js"></script>
<link href="${ctx}plugins/datatable/table_style.css" rel="stylesheet"
	type="text/css" />
</head>
<body>
<div class="mainpage">
<!--左侧begin-->
		<div class="leftMenu">
				<table cellpadding="0" cellspacing="0" class="BlockTop">
						<tr>
								<td class="left"></td>
								<td class="center">事务提醒</td>
								<td class="right"></td>
						</tr>
				</table>
				<div class="blockBox">
                		<ul class="menuList">
								<li onclick='openIframe("${ctx}logined/affairs/list_new_affairs.jsp")' id="newAffairsLi" class="current"><a>未阅</a></li>
								<li onclick='openIframe("${ctx}logined/affairs/list_receive_affairs.jsp")' id="receiveAffairsLi" ><a>已阅</a></li>
							<!-- 	<li onclick='openIframe("${ctx}logined/affairs/list_send_affairs.jsp")' id="sendAffairsLi"><a>已发送</a></li> -->
						</ul>
				</div>
			<table cellpadding="0" cellspacing="0" class="BlockBot" style="display: none;">
				<tr>
					<td class="left"></td>
					<td class="center"><h3 onclick='openIframe("${ctx}logined/affairs/list_all_affairs.jsp")' id="allAffairsLi">
							<em></em>查询
						</h3>
					</td>
					<td class="right"></td>
				</tr>
			</table>
		</div>
<!--左侧end-->
<iframe class="iframeresize" id="affairs_main" src="${ctx}logined/affairs/list_new_affairs.jsp" class="xx_iframe" style="overflow-x:hidden;" name="affairs_main" frameborder="no" scrolling="auto" hidefocus ></iframe>
</div>
</body>
</html>
