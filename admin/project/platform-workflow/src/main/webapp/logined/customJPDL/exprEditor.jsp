<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<jsp:include page="customJPDLHead.jsp"/>
	<script type="text/javascript" src="${ctx}/js/logined/customJPDL/exprMap.js"></script>
	<script type="text/javascript" src="${ctx}/js/logined/customJPDL/exprEditor.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>编辑表达式</title>
<style>
.inputTable th{ width:50px}
</style>
</head>
<body>
<div class="list">
	<c:forEach items="${routers}" var="router">
		<div class="pageTitle"><em class="iconList">${router.name}</em></div>
		
				<table border="0" cellpadding="0" cellspacing="1" class="inputTable">
						<tr>
								<td>字段：
								<select id="formProperty_${router.id }">
							<c:forEach items="${formProperties}" var="fp">
								<option value="${fp.propertyName}">${fp.propertyNameCh }</option>
							</c:forEach>
						</select>
								条件：
								<select id="realtive_${router.id }"><option value="e">等于</option>
											 <option value="ne">不等于</option>
											 <option value="g">大于</option>
											 <option value="ge">大于等于</option>
											 <option value="l">小于</option>
											 <option value="le">小于等于</option>
											 <!--
											 <option value="in">包含</option>
											 <option value="nin">不包含</option>
											-->
						</select>
								值：
								<input type="text" id="exprValue_${router.id }"  class="formText" />
								连接符：
								<select id="connect_${router.id}">
							<option value="and">and</option>
							<option value="or">or</option>
						</select>
									<input name="" type="button" class="formButton" value="添 加" onClick="javascript:generateExpr('${router.id}');" /></td>
						</tr>
				</table>
		<p class="l30 red">温馨提醒：符合以下条件才能转入到${router.name}</p>
		<table cellpadding="0" cellspacing="0"  class="pretty dataTable">
				<thead>
						<tr>
								<th width="70%">条件描述</th>
								<th>操作</th>
						</tr>
				</thead>
				<tbody id="table_${router.id }">
				<tr id="plainTr_${router.id }"><td colspan="2" >暂无数据!</td></tr>		
				</tbody>
		</table>
	</c:forEach>
</div>
</body>
</html>