<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<link type="text/css"
	href="<%=request.getContextPath() %>/logined/customJPDL/myflow/lib/jquery-ui-1.8.4.custom/css/smoothness/jquery-ui-1.8.4.custom.css"
	rel="stylesheet" />

<style type="text/css">
body {
  margin : 0;
  pading: 0;
  text-align: left;
  font-family: Arial, sans-serif, Helvetica, Tahoma;
  font-size: 12px;
  line-height: 1.5;
  color: black;
  background-image: url(<%=request.getContextPath() %>/logined/customJPDL/myflow/img/bg.png);
}

.node {
	width : 70px;
	text-align : center;
	vertical-align:middle;
	border: 1px solid #fff;
}

.mover{
	border: 1px solid #ddd;
	background-color: #ddd;
}
.selected{
	background-color: #ddd;
}
.state{}

#myflow_props table{
	
}
#myflow_props th {
	letter-spacing: 2px;
	text-align: left;
	padding: 6px;
	background: #ddd;
} 
#myflow_props td {
	background: #fff;
	padding: 6px;
} 

#pointer {
	background-repeat:no-repeat;
	background-position:center;
	
}
#path {
	background-repeat:no-repeat;
	background-position:center;
	
}
#task{
	background-repeat:no-repeat;
	background-position:center;
	
}
#state{
	background-repeat:no-repeat;
	background-position:center;
	
}
</style>
</head>
<body >
<input type="hidden" id="jsonData" value="${jsonData }"/>
<input type="hidden" id="proName" value="<%=request.getContextPath()%>"/>
<input type="hidden" id="actives" value="${actives}"/>
<div id="myflow">
</div>
</body>
<script type="text/javascript" src="<%=request.getContextPath() %>/logined/customJPDL/myflow/lib/raphael-min.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/logined/customJPDL/myflow/lib/jquery-1.10.2.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/logined/customJPDL/myflow/lib/jquery-ui.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/logined/customJPDL/myflow/myflow.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/logined/customJPDL/myflow/myflow.jpdl4.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/logined/customJPDL/myflow/myflow.editors.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/logined/customJPDL/view.js">
</script>
</html>