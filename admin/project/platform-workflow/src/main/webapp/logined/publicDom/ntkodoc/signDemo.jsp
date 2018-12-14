<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../../common/taglibs.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html  xmlns="http://www.w3.org/1999/xhtml">
<head>
<!--设置缓存-->
<meta http-equiv="cache-control" content="no-cache,must-revalidate"/>
<meta http-equiv="pragram" content="no-cache"/>
<meta http-equiv="expires" content="0"/>
<!-- <title>常用工作流程</title> -->
<jsp:include page="../../../common/head.jsp" />

<script type="text/javascript" src="${ctx}js/common/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="${ctx}js/logined/ntko/NtkoAddSecSign.js"></script>
<script type="text/javascript" src="${ctx}js/logined/publicDom/ntkodoc/addSign.js"></script>
<script src="${ctx}js/logined/ntko/NtkoGenObj.js"></script>
</head>
<body>
  <!-- 产生控件 -->
  <!-- <input type="hidden" id="documentExtId" name="documentExtId" value="440"/> -->
  <input type="hidden" id="documentExtId" name="documentExtId" value="<%=request.getParameter("documentExtId")%>"/>
</body>
<script type="text/javascript">
initOcx();
</script>
</html>