<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>下载页面</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

  </head>
  
  <body>
    <form action="download?companyId=1&moduleCode=22" method="get">
    	下载
    	<table>
	    	<tr>
		    	<td style="text-align: right">文件路径：</td>
		    	<td>
			    	<input type="text" name="filePath"/>
		    	</td>
	    	</tr>
	    	<tr>
		    	<td style="text-align: right">文件名：</td>
		    	<td>
			    	<input type="text" name="fileName"/>
		    	</td>
	    	</tr>
    	</table>
    	<input type="submit" value="下载">
    </form>
  </body>
</html>
