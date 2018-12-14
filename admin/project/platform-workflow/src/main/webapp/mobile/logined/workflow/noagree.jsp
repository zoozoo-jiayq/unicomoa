<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>审批</title>
    <jsp:include page="workflowHead.jsp" />
    <link rel="stylesheet" type="text/css" href="${ctx }mobile/css/style.css"/>
</head>
<body>
<input type="hidden" id="instanceId" value="<%=request.getParameter("instanceId") %>" />
<div class="container-fluid">
    <ul>
        <li class=  "input-group">
            <span class="input-group-addon bdnone ltext">拒绝</span>
        </li>
        <li class="input-group">
            <span class="input-group-addon bdnone">
                <textarea placeholder="请输入批复内容（非必填）" id="advice_noagree"></textarea>
            </span>
        </li>
   </ul>
   <div class="buttonArea">
        <button class="btn btn-primary" type="button" id="but_noagree">提交</button>
   </div>        
</div>
</body>
<script type="text/javascript" src="${ctx }mobile/js/logined/workflow/operation.js"></script>
</html>