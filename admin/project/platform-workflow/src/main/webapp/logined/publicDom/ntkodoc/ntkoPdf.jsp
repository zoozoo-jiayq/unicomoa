<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../../common/taglibs.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html  xmlns="http://www.w3.org/1999/xhtml">
<head>
<!--设置缓存-->
<meta http-equiv="cache-control" content="no-cache,must-revalidate"/>
<meta http-equiv="pragram" content="no-cache"/>
<meta http-equiv="expires" content="0"/>
<title>常用工作流程</title>
<link href="${ctx}css/base.css" rel="stylesheet" type="text/css" />
<link href="${ctx}css/public.css" rel="stylesheet" type="text/css" />
<link href="${ctx}plugins/datatable/table_style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}js/common/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="${ctx}js/logined/publicDom/ntkodoc/docNtko.js"></script>  
<style>
.leftHide{width:15px;display:none; cursor:pointer}
.leftShow{width:135px;}
.mainpage{ padding-left:150px;}
.mainpageHide{padding-left:30px}
</style>
<script>
		  var basePath ="${ctx}";
		   $(document).click(function(){
              $(window.parent.document).find(".menu").hide(); //
              $(window.parent.parent.document).find(".menu").hide();
              $(window.parent.parent.parent.document).find(".menu").hide();
          });
   var currentUser="${adminUser.userName}";
    window.parent.frameResize1();
</script>
 <script language="javascript">
 
</script>
 
</head>
<body > 
<!-- <input type=button name=print value="打印" onclick="pdf.print()"/>  -->
<div id="showdiv" style="Z-INDEX: 0; LEFT:10px; WIDTH: 10px; POSITION: absolute; TOP: -30px; HEIGHT: 10px">
<object classid="clsid:CA8A9780-280D-11CF-A24D-444553540000" width="1000" height="700" border="0"  top="-10"  name="pdf"> 
       <param name="toolbar" value="false"/>
      <param name="_Version" value="65539"/> 
      <param name="_ExtentX" value="20108"/> 
      <param name="_ExtentY" value="10866"/> 
      <param name="_StockProps" value="0"/> 
      <param name="SRC" value="${ctx}ntko/ajax_getOfficePdfContent.action?documentExtId=<%=request.getParameter("documentExtId")%>"/> 
</object> 
</div>



 </body>
		
		</html>