<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<jsp:include page="../../../common/taglibs.jsp"/>
<!DOCTYPE html>
<html lang="en" manifest="<%=request.getContextPath() %>/mobile/workflow.manifest">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>我的申请</title>
    <script type="text/javascript">
		var basePath = '${ctx}';
	</script>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
   <link type="text/css" rel="stylesheet" href="${ctx }mobile/plugins/bootstrap/css/bootstrap.min.css" />
    <link rel="stylesheet" type="text/css" href="${ctx }mobile/css/style.css"/>
    <link type="text/css" rel="stylesheet" href="${ctx }mobile/css/iscroll5.css" />
    <script type="text/javascript" src="${ctx }mobile/js/jquery-1.11.1.min.js"></script>
    <script type="text/javascript" src="${ctx }mobile/js/iscroll5.js"></script>
	<script type="text/javascript" src="${ctx }mobile/js/iscroll5-list.js"></script>
	<script type="text/javascript" src="${ctx}mobile/js/html5Adapter.js"></script>
 	<script type="application/javascript" src="${ctx }mobile/js/logined/workflow/myStartList.js"></script>
</head>
<body class="bgWhite">

<div class="container-fluid">
	<div id="scroll_wrapper" class="scroll_wrapper">
		<div id="scroll_scroller" class="scroll_scroller">
			<div id="scroll_dataList" class="container-fluid">
			</div>
				<div id="scroll_up" class="scroll_up" ></div>
		</div>
	</div>
	<div class="block-item nodata" style="display:none;border:none">
        <div class="noDate">暂无数据</div> 
    </div>
</div>
</body>
</html>