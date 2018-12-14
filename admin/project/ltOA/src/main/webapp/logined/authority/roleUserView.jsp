<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>人员详情</title>
<style type="text/css"> 
 .peo{ line-height:25px; padding:5px 8px; font-size:14px; font-family:"微软雅黑"}
 .peo span{ padding-right:5px; float:left;}
</style>
</head>
<body>
   <div class="peo">
       <c:if test="${empty userNameHtml}" >
           ---
       </c:if>
         <c:if test="${!empty userNameHtml}" >
           ${userNameHtml  }
       </c:if>
   </div>
</body>
</html>
