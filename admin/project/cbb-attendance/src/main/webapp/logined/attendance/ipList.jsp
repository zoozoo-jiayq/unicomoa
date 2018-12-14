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
  <title>考勤设置</title>
 <jsp:include page="../../common/flatHead.jsp"></jsp:include>
<link href="${ctx}flat/css/reset.css" rel="stylesheet" type="text/css" />
<link href="${ctx}flat/plugins/datatable/skins/datatable_page.css" rel="stylesheet" type="text/css" />
<link href="${ctx}flat/plugins/datatable/skins/datatable_default.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}flat/plugins/datatable/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="${ctx}flat/js/placeholder.js"></script>
<script type="text/javascript" src="${ctx}js/logined/attendance/ipList.js"></script>
<script type="text/javascript">
</script>
</head>
<body>
<div class="list">
  <div class="searchArea">
    <table cellspacing="0" cellpadding="0">
      <tbody>
        <tr>
          <td class="right"  >&nbsp;</td>
           <td style="width: 120px;">
          	<div class="fButton greenBtn"> <span class="add">新增IP段</span> </div>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
  <table cellpadding="0" cellspacing="0"  class="pretty" id="ipTable">
    <thead>
      <tr>
        <th class="num">序号</th>
        <th style="width:50%;">起始IP地址</th>
        <th style="width:50%;">截止IP地址</th>
        <th style="width:100px;">操作人</th>
        <th style="width:120px;">操作时间</th>
        <th style="width:80px;" class="right_bdr0">操作</th>
      </tr>
    </thead>
    <tbody>
		<c:choose>
			<c:when test="${ipStr==''}">
				没有检索出数据
			</c:when>
			<c:otherwise>
				${ipStr}
			</c:otherwise>
		</c:choose>      
    </tbody>
  </table>
</div>
</body>
</html>

