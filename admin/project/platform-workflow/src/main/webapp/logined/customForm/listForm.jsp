<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>增加表单</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<jsp:include page="../../common/flatHeadNoChrome.jsp"/>
<link href="${ctx }flat/css/reset.css" rel="stylesheet" type="text/css" />
<link href="${ctx }flat/css/main.css" rel="stylesheet" type="text/css" />
<link href="${ctx }flat/plugins/datatable/skins/datatable_default.css" rel="stylesheet" type="text/css" />
<link href="${ctx }flat/plugins/Accormenus/skins/Accormenus_default.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" language="javascript" src="${ctx}js/logined/customForm/list.js" ></script>
</head>
<body>
<jsp:include page="shareFormCategory.jsp" />
<input name="formType" id="formType" type="hidden" value="<%=request.getParameter("categoryId")%>"/>
<input name="searchName" id="searchName" type="hidden" value="<%=request.getParameter("searchName")%>"/>



<div class="list">
		<table cellpadding="0" cellspacing="0"  id="myTable"  class="pretty dataTable">
				<thead>
						<tr>
								<th class="num" id="no">序号</th>
								<th style="width:100%;" class="longTxt" id="name">表单名称</th>
								<th style="width:260px;" class="right_bdr0 data_l">操作</th>
						</tr>
				</thead>
				<tbody>
				</tbody>
		</table>
</div>
 
<script type="text/javascript">
	if('<%=request.getParameter("categoryName")%>' != 'null'){
		$("#search").hide();
		$("#manage").show();
	}else{
		$("#search").show();
		$("#manage").hide();
	}
</script>
</body>
</html>

