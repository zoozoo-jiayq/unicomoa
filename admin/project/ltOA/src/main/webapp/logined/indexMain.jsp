<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
	response.sendRedirect("");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>多模块</title>
    <jsp:include page="../common/flatHead.jsp" />
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link href="${ctx}flat/css/reset.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript">
    	function toLogin(sysName){
    		window.location.href = basePath+"login/unitLogin.action?loginSysName="+sysName;
    	}
    </script>
</head>
<body>
   
   <input type="button" onClick="toLogin('OA')"  class="formButton_grey" value="办公平台" />
   <input type="button" onClick="toLogin('HR')"  class="formButton_grey" value="个人事务" />
</div>
</body>
</html>