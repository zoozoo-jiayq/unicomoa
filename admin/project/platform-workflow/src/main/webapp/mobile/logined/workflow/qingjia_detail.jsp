<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="en" manifest="<%=request.getContextPath() %>/mobile/workflow.manifest">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>请假详情</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <jsp:include page="workflowHead.jsp" />
    <link rel="stylesheet" type="text/css" href="${ctx }mobile/css/style.css"/>
    <script type="text/javascript" src="${ctx}mobile/js/logined/workflow/qingjia.js"></script>
</head>
<body onload="loadData();">
<input type="hidden" id="instanceId" value="<%=request.getParameter("instanceId") %>" />
<input type="hidden" id="operation" value="<%=request.getParameter("operation") %>" />
<div class="container-fluid of">
    <form class="form-horizontal">
        <div class="showBox">
            <div class="faceBox">
            	<div class="imgBox" id="imgDiv"><img src="" id="photo"/><span id="imgName"></span></div>
                <h2 class="name" id="userName"></h2>
            </div>
            <dl>
               <dt id="leaveType"></dt>
               <dd class="time" id="beginAndEndTime"></dd>
               <dd id="reason"></dd>
        	   <jsp:include page="photo_detail.jsp"></jsp:include>
            </dl>
        </div>
       	<jsp:include page="selectUser_detail.jsp"></jsp:include>
       	<jsp:include page="operation.jsp"></jsp:include>
    </form>
</div>

</body>
</html>