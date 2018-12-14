<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<jsp:include page="../../common/taglibs.jsp" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>考勤记录</title>
<link href="${ctx}flat/css/reset.css" rel="stylesheet" type="text/css" />
<link href="${ctx}flat/css/attendance.css" rel="stylesheet" type="text/css" />
<link href="${ctx}flat/plugins/form/skins/form_default.css" rel="stylesheet" type="text/css" />
<link href="${ctx}flat/plugins/datatable/skins/datatable_default.css" rel="stylesheet" type="text/css" />
<jsp:include page="../../common/flatHead.jsp" />
<style>
.dataTables_info {width:200px;}
</style>
<script type="text/javascript" language="javascript" src="${ctx}js/logined/attendance/attendanceRecord.js"></script>
</head>
<body>
<input id="beginT" type="hidden"  value="<%=request.getParameter("beginT")!=null?request.getParameter("beginT"):"" %>"  />
 <input id="endT" type="hidden"  value="<%=request.getParameter("endT")!=null?request.getParameter("endT"):"" %>"  />
 <input id="userId" type="hidden"  value="<%=request.getParameter("userId")!=null?request.getParameter("userId"):"-1" %>"  />
  
  <div class="formbg">
	  <!-- 打卡记录 -->
	  <div id="contents">
	  </div>
      <!-- 分页信息 -->
	  <div id="pageFoot">
	  </div>  
  </div>
 
 
</body>
</html>
