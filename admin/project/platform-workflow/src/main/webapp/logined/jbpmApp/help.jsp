<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<script type="text/javascript">
		 var basePath="<%=basePath%>";
	     function down(){
	        var url =basePath+"js/logined/ntko/OfficeControlSetup.rar";
	        window.location.href=url;
	     }
	</script>
  </head>
  
  <body>
    <a   href="<%=basePath%>logined/jbpmApp/downNtkoFile.jsp"  >下载插件</a>
  </body>
</html>
