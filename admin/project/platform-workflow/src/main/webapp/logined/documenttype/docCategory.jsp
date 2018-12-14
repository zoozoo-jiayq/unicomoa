<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>分类设置</title>
<jsp:include page="../../common/head.jsp"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="${ctx}plugins/datatable/jquery.dataTables.min.js"></script>
<script type="text/javascript" language="javascript" src="${ctx}js/logined/customForm/formCategoryList.js" ></script>

</head>
<body>
<div class="list">
		<!-- <div class="pageTitle"><em class="iconList">管理表单分类</em></div> -->
		<div class="listbtn">
				<div class="tDiv2">
						<div class="fbutton">
								<div><span class="add" onclick="javascript:location.href='${ctx}logined/customForm/addFormCategory.jsp'">新增</span></div>
						</div>
				</div>
		</div>
		<table cellpadding="0" cellspacing="0"  class="pretty dataTable" id="myTable">
				<thead>
				</thead>
		</table>
</div>
</body>
</html>
