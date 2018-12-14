<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<jsp:include page="../../common/taglibs.jsp"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>个人设置--主页</title>
    <jsp:include page="../../common/head.jsp"/>
    <script type="text/javascript" src="${ctx}js/logined/systemset/index.js"></script>
</head>
<body>
<div class="mainpage">
<!--左侧begin-->
		<div class="leftMenu">
				<table cellpadding="0" cellspacing="0" class="BlockTop">
						<tr>
								<td class="left"></td>
								<td class="center"><h2><em></em>个人信息</h2></td>
								<td class="right"></td>
						</tr>
				</table>
				<div class="blockBox">
						<ul class="menuList">
								<li id="recordSetli" class="current"><a href="javascript:void(0);"  onclick="toRecordSet();">个人资料</a></li>
								<li id="pwdSetli"><a href="javascript:void(0);" onclick="toPwdSet();">修改密码</a></li>
						</ul>
				</div>
		</div>
<!--左侧end-->
	<div class="input" style="position:relative;width:auto">
		   <iframe class="iframeresize"  src=""  id="mainFrame"  style="overflow-x:hidden;heigth:100%;" name="main" frameborder="no" scrolling="auto" hidefocus></iframe>
	</div>
</div>
</body>
</html>