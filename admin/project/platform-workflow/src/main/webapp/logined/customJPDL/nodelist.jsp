<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<table>
		<tr>
			<td>节点名称</td>
			<td>节点描述</td>
			<td>任务候选人</td>
			<td>可写字段</td>
			<td>保密字段</td>
		</tr>
		<c:forEach items="${nodeList}" var="node" >
		<tr>
			<td>${node.nodeName }</td>
			<td>${node.descri }</td>
			<td><a href="<%=request.getContextPath() %>/workflow/nodeManager!candidate.action?nodeId=${node.id}">经办权限</a></td>
			<td><a href="<%=request.getContextPath() %>/workflow/nodeManager!writeAble.action?nodeId=${node.id}">可写字段</a></td>
			<td><a href="<%=request.getContextPath() %>/workflow/nodeManager!secretAble.action?nodeId=${node.id}">保密字段</a></td>
		</tr>
		</c:forEach>
	</table>
</body>
</html>