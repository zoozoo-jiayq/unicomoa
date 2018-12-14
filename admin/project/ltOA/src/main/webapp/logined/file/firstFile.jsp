<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<jsp:include page="../../common/taglibs.jsp"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>知识管理--公共文件柜--新建文件</title>
<link href="${ctx}/css/qj-css.css" rel="stylesheet" type="text/css" />
<jsp:include page="../../common/head.jsp"/>
<script type="text/javascript">
  var basePath ="${ctx}";
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
<!-- <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>浏览文件</title>
<link href="../css/base.css" rel="stylesheet" type="text/css" />
<link href="../css/public.css" rel="stylesheet" type="text/css" />-->
</head> 
<body>
    <input id="size" type="hidden" value="${paramValues.size[0]}" />
	<div class="notice">
	<div class="txtContent">
                    <p>请选择文件夹浏览文件！</p>
	</div>
	</div>
</body>
<script type="text/javascript">
  var size = $("#size").val();
  if (size == '0'){
	  $("#message").html("文件柜为空,请首先配置文件柜！");
  }
</script>
</html>
