<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<% String id = request.getParameter("id");
	request.setAttribute("id",id);
 %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>OA办公系统已审批公告通知</title>
<jsp:include page="../../common/flatHead.jsp" />
<link href="${ctx}/flat/css/reset.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/flat/plugins/datatable/skins/datatable_page.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/flat/plugins/datatable/skins/datatable_default.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}js/logined/notify/viewApproveList.js"></script>
</head>
<body>
<div class="list">
	<div class="searchArea">
	<input type="hidden" id="columnId" name="columnId" value="${id}"/>
			<table cellspacing="0" cellpadding="0">
			<tbody>
			   <tr>
				<td class="right">
				  <label>类型：</label>
				  <select id="notifyType" name="notifyType">
				  		<option value="">全部类型</option>
                    </select>
                  <label>创建时间：</label>
				<input id="beginDate" name="beginDate" type="text" class="formText Wdate" onClick="WdatePicker({maxDate: '#F{$dp.$D(\'endDate\')}'})" style="width:110px;"/>
				-
				<input id="endDate" name="endDate" type="text" class="formText Wdate" onClick="WdatePicker({minDate: '#F{$dp.$D(\'beginDate\')}'})" style="width:110px;"/>  
           		 
				<label>关键字：</label>
				<span style="position:relative;">
				<input type="text" id="subject" name="subject" placeholder="标题" class="formText searchkey" maxlength="50"/>
				</span>
				
				<input hideFocus="" value="查 询" class="searchButton" type="button"/>                                
			</td></tr></table>
	</div>
	<table cellpadding="0" cellspacing="0"  class="pretty dataTable" id="myTable">
		<thead>
		 <tr>
	        <th class="longTxt" style="width:100%">标题</th>
	        <th id="notifyType" style="width:100px;">类型</th>
	        <th id="username" style="width:90px;">创建人</th>
	        <th id="createDate" style="width:120px;">创建时间</th>
	        <th id="browse" class="data_r" style="width:90px;">浏览人数</th>
	        <th style="width:70px;">审核结果</th>
	        <th style="width:70px;" class="right_bdr0">操作</th> 

	      </tr>
		</thead>
	</table>
</div>
<script>funPlaceholder(document.getElementById("subject"));</script>
</body>
</html>
