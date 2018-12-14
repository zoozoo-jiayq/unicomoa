<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<input type="hidden" id="currentSignUser" value="${adminUser.userName }" />
<input type="hidden" id="cabpath" value="<%=request.getContextPath()%>"/>
<c:if test="${adminUser.sinWidget==1}"><script src="${ctx}js/logined/ntko/NtkoGenObj.js"></script></c:if>
<script type="text/javascript" src="${ctx}js/logined/ntko/NtkoAddSecSign.js"></script>
<script type="text/javascript" src="${ctx}js/logined/publicDom/ntkodoc/addSign.js"></script>
<form id="yzform" style="display:none" method="post"  action="${ctx}ntko/webSign_saveFile.action?documentExtId=${documentExtId}"  enctype="multipart/form-data">
</form>
<script type="text/javascript">
		<c:if test="${adminUser.sinWidget==1}">initOcx();</c:if>
</script>
