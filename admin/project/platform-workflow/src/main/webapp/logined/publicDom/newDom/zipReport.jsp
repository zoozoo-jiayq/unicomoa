<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="publicDomHead.jsp"></jsp:include>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>公文归档统计</title>
<style>
.list{margin:0 auto;width:60%}
</style>
</head>
<body>
<div class="list">
	<div class="contentTitle"><em class="contentTitle_con">公文归档统计</em></div>
     <table cellspacing="0" cellpadding="0" class="pretty dataTable">
		<thead>
			<tr>
				<th style="width:200px;">${zipReport.forkGroupName }</th>
				<th style="width:50%;">收文</th>
				<th style="width:50%;">发文</th>
			</tr>
		</thead>
		<tbody>
			<tr class="odd">
				<td>待归档</td>
				<td>${zipReport.gatherWaitZip }</td>
				<td>${zipReport.disWaitZip }</td>
			</tr>
			<tr class="odd">
                <td>已归档</td>
				<td>${zipReport.gatherCompZip }</td>
				<td>${zipReport.disCompZip }</td>
			</tr>
            <tr class="odd">
                <td>合计</td>
				<td>${zipReport.gatherCount }</td>
				<td>${zipReport.disCount }</td>
			</tr>
           <tr class="odd">
                <td>文档总计</td>
				<td colspan="2">${zipReport.count }</td>
			</tr>
		</tbody>
	</table>
	
</div>
</body>
</html>