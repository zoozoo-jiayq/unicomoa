<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%String id = request.getParameter("id");
  String columnId = request.getParameter("columnId");
  request.setAttribute("columnId",columnId);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>查阅情况</title>
<jsp:include page="../../common/flatHead.jsp" />
<script type="text/javascript" src="${ctx}plugins/datatable/jquery.dataTables.min.js"></script>
<script language="javascript" type="text/javascript" src="${ctx}plugins/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx}js/logined/affairs/affairs_check.js"></script>
<link href="${ctx}/flat/css/reset.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/flat/plugins/infoDetail/skins/infoDetail_default.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/flat/plugins/annex/skins/annex_default.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/flat/plugins/datatable/skins/datatable_page.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/flat/plugins/datatable/skins/datatable_default.css" rel="stylesheet" type="text/css" />
<link href="${ctx}css/main.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}js/logined/notify/viewNotify.js"></script>
</head>
<body>
<input type="hidden" id="id" value="<%=id %>"/>
<input type="hidden"  id="columnId" name="columnId" value="${columnId}" />
<input type="hidden" id="notReaderId"/>
<input type="hidden"  id="category" name="category" value="${param.category}" />
<div class="detailsPage">
	<div class="detailbg">
	<h2 id="subject"></h2>    
	<h3>
		<span id="createTimeStr">2014年03月26日 16:15</span>
		<span id="createUserName">发布人：程伟</span>
		<span id="viewCount">浏览次数：0</span>
	</h3>
	<div class="expFile mt10">
    	<h4 class="annex_title" id="record">阅读记录 : <font></font></h4>
    <table cellpadding="0" cellspacing="0"  class="pretty mt10" id="myTable">
		<thead>
			<tr>
				<th>部门/成员单位</th>
				<th>已读人员</th>
				<th class="right_bdr0">未读人员</th>
			</tr>
		</thead>
		<tbody>
		</tbody>
	</table>
	</div>
	</div>
	<div class="buttonArea">
        <input type="button" onclick="goBack();" class="formButton_grey" value="返回" />
    </div>
</div>
</body>
</html>
