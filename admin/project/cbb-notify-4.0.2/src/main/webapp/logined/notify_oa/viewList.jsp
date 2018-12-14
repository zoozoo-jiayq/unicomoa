<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<% String id = request.getParameter("id");
	request.setAttribute("id",id);
 %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>公告查看</title>
<jsp:include page="../../common/flatHead.jsp" />
<link href="${ctx}/flat/css/reset.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/flat/plugins/datatable/skins/datatable_page.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/flat/plugins/datatable/skins/datatable_default.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}js/logined/notify/viewList.js"></script>
</head>
<body>
	<div class="list">
	<input type="hidden" id="columnId" name="columnId" value="${id}"/>
	<div class="searchArea">
			<table cellspacing="0" cellpadding="0">
			<tbody>
			   <tr>
			    <td style="width: 20%"></td>
				<td class="right" style="width: 80%">
				  <label>类型：</label>
				  <select name="notifyType">
				  	<option value="0">全部类型</option>
                  </select>
				<label>关键字：</label>
				<span style="position:relative;">
				<input type="text" id="subject" name="subject" placeholder="标题" class="formText searchkey" maxlength="50" />
				</span>
				<input hideFocus="" value="查询" class="searchButton" type="button" id="searchButton" />                               
				</td>
			</tr>
			</tbody>
		</table>
	</div>
	<table cellpadding="0" cellspacing="0"  class="pretty dataTable" id="myTable">
		<thead>
		<tr>
       	 	<th id="subject" class="longTxt">标题</th>
        	<th id="typename" style="width:100px;">类型</th>
        	<th id="username" style="width:90px;">发布人</th>
       		<th id="approveDate" style="width:150px;">发布时间</th>
        	<th id="totalCount" style="width:70px;" class="data_r">浏览次数</th>
     	 </tr>
		</thead>
	</table>
</div>
<script>funPlaceholder(document.getElementById("subject"));</script>
</body>
</html>
