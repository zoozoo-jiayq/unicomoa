<%--
功能: 报表管理
版本:1.0
开发人员: CQL
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="../../common/taglibs.jsp"/>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String  reportType  =  request.getParameter("reportType")!=null?request.getParameter("reportType"):""; 
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>报表查看</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <jsp:include page="../../common/head.jsp"/>
    <script type="text/javascript">
		   //登录框上下居中
			$(document).ready(function($) {
				resizeLayout();//内容上下居中*/
			});
			function resizeLayout() {
				// 主操作区域高度
			      var wHeight = (window.document.documentElement.clientHeight || window.document.body.clientHeight || window.innerHeight);
			      var cHeight = wHeight;
			$(".notice").css("padding-top",cHeight/2);
			};
			$(window).resize(function() {
					resizeLayout();
			});
</script>
    
</head>
<body>
     <div class="notice">
	<div class="txtContent">
	<% if("1".equals(reportType)) {%>
	    <p>请选择要填报的报表！</p>
	 <% } %>
	   <% if("2".equals(reportType)) {%>
	    <p>请选择要审批的报表！</p>
	 <% } %>
	 <% if("3".equals(reportType)) {%>
	    <p>请选择要批阅的报表！</p>
	 <% } %>        
	</div>
	</div>

</body>
</html>