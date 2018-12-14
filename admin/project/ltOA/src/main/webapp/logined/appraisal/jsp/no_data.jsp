<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<jsp:include page="../common/taglibs.jsp"/>

    <title>绩效考核</title>
    <script type="text/javascript" src="${ctx}logined/appraisal/plugins/dialog/artDialog4.1.6/artDialog.js?skin=blue"></script>
    <script type="text/javascript" src="${ctx}logined/appraisal/plugins/dialog/artDialog4.1.6/iframeTools.js"></script>
    <script type="text/javascript">
	    $(document).ready(function () {
	    	var titleArr = new Array();//标题
	    	titleArr.push(" > 自述自评");
	    	titleArr.push(" > 考核他人");
	    	titleArr.push(" > 结果审批");
	    	titleArr.push(" > 查看成绩");
	    	titleArr.push(" > 考核成绩");
	    	
	    	
	    	var msgArr = new Array();//提示
	    	msgArr.push("本次考核不支持自述自评，或者您不在考核范围内，无需自评。");
	    	msgArr.push("本次考核暂无需要您评分的人员。");
	    	msgArr.push("您无权查看该次考核成绩。");
	    	msgArr.push("考核尚未结束，暂不能查看成绩。");
	    	msgArr.push("管理员设置评分结束并提交审核后，才可以查看考核成绩。");
	    	
	    	var type= $("#type").val();
	    	$("#title").html(titleArr[type]);
	    	$("#msg").html(msgArr[type]);
	    });
    </script>
</head>
<body>
	<input id="type" type="hidden" value="${param.type }"/>
    <!-- 右侧内容模块 -->
    <div class="iframe_right_content">
        <div class="navigation_bar">
            <p>当前位置：
                <a href="#">首页</a>
                <span id="title"> > 自述自评</span>
            </p>
        </div>
        <div class="not_end">
            <div class="not_end_icon"></div>
            <p id="msg">本次考核不支持自述自评，或者您不在考核范围内，无需自评。</p>
        </div>
    </div>

</body>
</html>
