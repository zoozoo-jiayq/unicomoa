<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>上传页面</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
    <form action="upload?companyId=1&moduleCode=22&userId=1" method="post" enctype ="multipart/form-data">
    	批量上传
    	<table>
	    	<tr>
		    	<td style="text-align: right">选择文件：</td>
		    	<td>
			    	<input type="file" name="file"/>
		    	</td>
	    	</tr>
	    	<tr>
		    	<td style="text-align: right">选择文件：</td>
		    	<td>
			    	<input type="file" name="file"/>
		    	</td>
	    	</tr>
	    	<tr>
		    	<td style="text-align: right">选择文件：</td>
		    	<td>
			    	<input type="file" name="file"/>
		    	</td>
	    	</tr>
    	</table>
    	<input type="submit" value="保存">
    </form>
  </body>
</html>
