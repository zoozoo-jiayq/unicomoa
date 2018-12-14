<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%
session.removeAttribute("adminUser");
session.invalidate();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>超时退出</title> 
    <link rel="shortcut icon" href="<%=basePath%>images/favicon.ico" type="image/x-icon"/>

	<meta http-equiv="refresh" content="5;url=<%=basePath %>login.jsp">

  </head>
  
  <body>
   	由于您长时间未操作系统已自动退出,5秒后系统自动跳转到登录界面……
  
  </body>
</html>
