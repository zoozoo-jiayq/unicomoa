<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>权限维护</title>
<jsp:include page="../../common/flatHead.jsp"/>
<link href="${ctx}flat/css/reset.css" rel="stylesheet" type="text/css" />
<link href="${ctx}flat/css/authority.css" rel="stylesheet" type="text/css" />
<style type="text/css">
	html,body{
		height:100%;
		width:100%:
	}
	body{
		overflow:auto;
	}
</style>
<script type="text/javascript">
var basePath="${ctx}";
function goback(){
	window.parent.location.href=basePath +"logined/authority/roleList.jsp";
}
</script>
<script type="text/javascript" language="javascript" src="${ctx}js/logined/authority/mobileList.js"></script>
</head>
<body style="overflow:auto;overflow-x:auto;">
<form action="" id="myForm" name="myForm">
<input type="hidden" id="roleId" name="roleId" value="${roleId}"/>
<div class="anthority">
		<div class="title_an">
			<span class="name">功能权限维护</span>
			<input id="addRoleModuleMobile" name="addRoleModuleMobile" type="button" value="保  存" class="formButton_green" />
			<input id="cancelRoleModule" name="cancelRoleModule" type="button" class="formButton_grey" onclick="goback()" value="返  回" />
		</div>
		<table border="0" cellpadding="0" cellspacing="5" class="access" style="width:100%" id="moduleTable">
			<tbody>
			<tr>
				<s:iterator value="list">
					<td valign="top">
						<h2>
							<input id="module${id}" class="inptchkbx" type="checkbox" value="${id}" name="moduleIds"/>
							 <c:out value="${name}"/>
						</h2>
						<ul>
							<s:iterator value="children">
							<li>
							<input id="module${id}" type="checkbox" class="inptchkbx" name="moduleIds" value="${id}"/>
							${name}</li>
							</s:iterator>
						</ul>
					</td>
				</s:iterator>
			</tr>
			</tbody>
		</table>
	</div>
	</form>
</body>
</html>