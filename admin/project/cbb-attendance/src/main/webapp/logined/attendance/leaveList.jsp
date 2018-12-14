<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../../common/taglibs.jsp" />
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
 <head>
  <title>请假详情</title>
 <jsp:include page="../../common/flatHead.jsp"></jsp:include>
<link href="${ctx}flat/css/reset.css" rel="stylesheet" type="text/css" />
<link href="${ctx}flat/plugins/datatable/skins/datatable_page.css" rel="stylesheet" type="text/css" />
<link href="${ctx}flat/plugins/datatable/skins/datatable_default.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}flat/plugins/datatable/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="${ctx}flat/js/placeholder.js"></script>
<script type="text/javascript" src="${ctx}js/logined/attendance/leaveList.js"></script>
<script type="text/javascript">
</script>
</head>
<body>
<input type="hidden" id="startTime" value="${param.startTime }"/>
<input type="hidden" id="endTime" value="${param.endTime }"/>
<input type="hidden" id="userId" value="${param.userId }"/>
<input type="hidden" id="type" value="${param.type }"/>
<div class="list">
  <table cellpadding="0" cellspacing="0"  class="pretty" >
    <thead>
      <tr>
        <th style="width:100px">审批单名称</th>
        <th style="width:50px;">时长</th>
         <th style="width:50px;">天数</th>
        <th style="width:153px;">起止时间</th>
      </tr>
    </thead>
    <tbody id="leaveBody">
    </tbody >
    
  </table>
</div>
</body>
</html>

