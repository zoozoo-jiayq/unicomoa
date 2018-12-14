<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>基础设置</title>
<%@ include file="../../common/taglibs.jsp"%>
<jsp:include page="../../common/head.jsp" />
<script type="text/javascript"
	src="${ctx}js/logined/address/no_address.js"></script>

</head>
<body>
<div class="notice" style="margin-top:80px">
	<h2 class="titbg"></h2>
	<div class="txtContent">
	<em class="nodata"></em><p>未找到相应记录!</p>
	<div class="clear"></div>
	</div>
</div>
</body>
</html>