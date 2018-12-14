<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	request.setAttribute("ctx", basePath);
	request.setAttribute("version", "3.1");
%>
 <link href="${ctx }mobile/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet">
<script src="${ctx }mobile/js/jquery-1.8.1.min.js"></script>
<script src="${ctx }mobile/plugins/swiper/js/idangerous.swiper-2.1.min.js"></script>
<script src="${ctx }mobile/plugins/swiper/js/idangerous.swiper.scrollbar-2.1.js"></script>
<script type="text/javascript" src="${ctx }mobile/js/html5Adapter.js"></script>
<script type="text/javascript" src="${ctx }js/common/json2.js"></script>
<script type="text/javascript" src="${ctx }mobile/js/logined/workflow/workflowBase.js"></script>
<script type="text/javascript" src="${ctx }mobile/js/logined/workflow/hammer.js"></script>
<script type="text/javascript" src="${ctx }mobile/js/base.js"></script>
<script type="text/javascript" src="${ctx }mobile/js/logined/workflow/letterCode.js"></script>
<script type="text/javascript">
	var basePath = "${ctx}";
</script>
