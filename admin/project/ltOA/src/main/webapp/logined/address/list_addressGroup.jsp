<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>管理分组</title>
<%@ include file="../../common/taglibs.jsp"%>
<jsp:include page="../../common/head.jsp" />
<script type="text/javascript">
	var basePath = "${ctx}";
</script>
<script type="text/javascript"
	src="${ctx}js/logined/address/list_addressGroup.js"></script>
<style type="text/css" title="currentStyle">
	@import "${ctx}plugins/datatable/table_style.css";
	@import "${ctx}plugins/datatable/datatable_page.css";
</style>
</head>

<body>
	<div class="list">
		<div class="pageTitle"><em class="iconList">管理分组</em></div>
		<div class="listbtn">
				<div class="tDiv2">
						<div class="fbutton">
								<div><span id="addGroup" class="add">新建分组</span></div>
						</div>
						<div class="btnseparator"></div>
						
				</div>
				<div style="clear:both"></div>
		</div>
		<table id="myTable" cellpadding="0" cellspacing="0"  class="pretty dataTable">
				<thead>
						<tr>
								<th style="width:50%;">分组名称</th>
								<th style="width:50%;">操作</th>
						</tr>
				</thead>
		</table>
</div>
</body>
</html>