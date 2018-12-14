<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<jsp:include page="../../common/taglibs.jsp"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>邮件不存在</title>
    <jsp:include page="../../common/head.jsp"/>
    <script type="text/javascript" src="${ctx}/js/logined/email/email_alert_error.js"></script>
    <script type="text/javascript" src="${ctx}/js/logined/email/email_common.js"></script>
</head>
<body style="height:400px;overflow: hidden;">
<div class="notice" style="margin-top:80px">
    <h2 class="titbg"></h2>
    <div class="txtContent">
        <em class="nodata"></em><p id="errorMsgBox">&nbsp;</p>
        <div class="clear"></div>
    </div>
</div>
<input type="hidden" id="errorMsg" value="${errorMsg}" />
</body>
</html>
