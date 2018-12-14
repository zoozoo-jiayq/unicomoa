<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
    <!DOCTYPE html>
    <html lang="en" manifest="<%=request.getContextPath() %>/mobile/workflow.manifest">
    <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>报销申请</title>
    <jsp:include page="workflowHead.jsp" />
    <link rel="stylesheet" type="text/css" href="${ctx}mobile/css/style.css"/>
    </head>
<body>
<div class="container-fluid">
    <div class="form-horizontal">
        <div class="input-group bt10">
            <span class="input-group-addon bdnone">报销金额</span>
            <input type="tel" class="form-control bdnone rtext" placeholder="请输入金额" id="rbSement">
        </div>
        <div class="input-group bt10">
            <span class="input-group-addon bdnone">报销类别</span>
            <input type="text" class="form-control bdnone rtext" placeholder="如：采购经费、活动经费" id="rbSementType">
        </div>
        <div class="block-item bt10">
            <textarea rows="5" placeholder="请输入报销原因" id="reason"></textarea>
            <img class="camera" src="${ctx}mobile/images/camera.png" style="display:none">
        </div>
        <jsp:include page="photo.jsp"></jsp:include>
        <jsp:include page="selectUser.jsp"></jsp:include>
        <div class="buttonArea">
            <button class="btn btn-primary" id="sbn" type="button">提交</button>
        </div>
    </div>
</div>
<script type="text/javascript" src="${ctx }mobile/js/logined/workflow/baoxiao.js"></script>
</body>
</html>