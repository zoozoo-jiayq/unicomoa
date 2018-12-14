<%@ page contentType="text/html;charset=utf-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<link href="${ctx}images/favicon.ico" mce_href="favicon.ico" rel="bookmark" type="image/x-icon" /> 
<link href="${ctx}images/favicon.ico" mce_href="favicon.ico" rel="icon" type="image/x-icon" /> 
<link href="${ctx}images/favicon.ico" mce_href="favicon.ico" rel="shortcut icon" type="image/x-icon" /> 
<title>预览文件</title>
</head>
<body>
	<c:if test="${requestScope.previewFilePath==''}">
	不支持此文件预览
	</c:if>
	<c:if test="${requestScope.previewFilePath!=''}">
		<c:redirect url="${requestScope.previewFilePath}"></c:redirect>
	</c:if>
</body>
</html>