<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="en" manifest="<%=request.getContextPath() %>/mobile/workflow.manifest">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>请假申请</title>
    <jsp:include page="workflowHead.jsp" />
    <link rel="stylesheet" type="text/css" href="${ctx}mobile/css/style.css"/>
</head>
<body>
<div class="fzBox" style="display:none">
<div class="mask" style="display:block" onclick="cancle()"></div>
<div class="popbox" style="display:block">
   <ul id="selectUl" class="qingType">
            <li><a class="block-item" href="javascript:void(0);" onclick="selectType(this);">事假</a></li>
            <li><a class="block-item" href="javascript:void(0);" onclick="selectType(this);">病假</a></li>
            <li><a class="block-item" href="javascript:void(0);" onclick="selectType(this);">年假</a></li>
            <li><a class="block-item" href="javascript:void(0);" onclick="selectType(this);">调休</a></li>
            <li><a class="block-item" href="javascript:void(0);" onclick="selectType(this);">婚假</a></li>
            <li><a class="block-item" href="javascript:void(0);" onclick="selectType(this);">产假</a></li>
            <li><a class="block-item" href="javascript:void(0);" onclick="selectType(this);">陪产假</a></li>
            <li><a class="block-item" href="javascript:void(0);" onclick="selectType(this);">路途假</a></li>
            <li><a class="block-item" href="javascript:void(0);" onclick="selectType(this);">其他</a></li>
 	 </ul>
</div>
</div>
    <div class="container-fluid">
        <div class="form-horizontal" id="qingjia">
<!--             <div class="input-group bt10">
                <span class="input-group-addon bdnone">请假类型</span>
                <div class="pull-right select-panel">
                    <select class="bdnone" id="leaveType">
                        <option value="事假">事假</option>
                        <option value="病假">病假</option>
                        <option value="年假">年假</option>
                        <option value="调假">调假</option>
                        <option value="婚假">婚假</option>
                        <option value="产假">产假</option>
                        <option value="陪产假">陪产假</option>
                        <option value="路途假">路途假</option>
                        <option value="其他">其他</option>
                    </select>
                </div>
            </div> -->
           <div class="input-group bt10">
                <span class="input-group-addon bdnone">请假类型</span>
                <span class="form-control bdnone rtext"  style="color:rgb(153,153,153)" id="leaveType">事假</span>
            </div>
            <div class="input-group bt10">
                <span class="input-group-addon bdnone">开始时间</span>
                <input type="text" class="form-control bdnone rtext arrow" id="beginTime" readonly="readonly" placeholder="请选择">
            </div>
            <div class="input-group">
                <span class="input-group-addon bdnone">结束时间</span>
                <input type="text" class="form-control bdnone rtext arrow" id="endTime" readonly="readonly" placeholder="请选择">
            </div>
            <div class="input-group">
                <span class="input-group-addon bdnone">请假天数</span>
                <span class="form-control bdnone rtext" style="color:rgb(153,153,153)" id="days">请输入请假天数</span>
            </div>
            <div class="block-item bt10">
                <textarea rows="5" placeholder="请输入请假原因" id="reason"></textarea>
                <img class="camera" src="${ctx}mobile/images/camera.png" style="display:none">
            </div>
            <jsp:include page="photo.jsp"></jsp:include>
            <jsp:include page="selectUser.jsp"></jsp:include>
            <div class="buttonArea">
                <button class="btn btn-primary" type="button" id="sbn">提交</button>
            </div>
    </div>
   
</div>
<script type="text/javascript" src="${ctx}mobile/js/logined/workflow/qingjia.js"></script>
</body>
</html>