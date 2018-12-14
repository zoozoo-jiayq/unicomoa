<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>设计流程</title>
<jsp:include page="customJPDLHead.jsp"/>
</head>
<body>
<div class="mainpage" style=" padding-left:200px">
<!--左侧begin-->

<!--左侧end-->
<iframe id="page" name="page" src="${ctx}/workflow/manager!editProcess.action?processAttributeId=${processAttributeId}" border="0" frameBorder="0" scrolling="auto" style="width: 100%; height:100%" ></iframe>

</div>
</body>
</html>