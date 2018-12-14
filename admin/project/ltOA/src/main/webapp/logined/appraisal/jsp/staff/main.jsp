<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>新密检察院绩效考核系统</title>
    <jsp:include page="../../common/taglibs.jsp"/>
	<script type="text/javascript" src="${ctx}logined/appraisal/plugins/dialog/artDialog4.1.6/artDialog.js?skin=blue"></script>
	<script type="text/javascript" src="${ctx}logined/appraisal/plugins/dialog/artDialog4.1.6/iframeTools.js"></script>
    <script type="text/javascript" src="${ctx}/logined/appraisal/js/staff/main.js?version=${version}"></script>
</head>
<body>
	<jsp:include page="../header.jsp"/>
	<input type="hidden" id="tid" value="${param.tid}"/>
	<ul class="nav">
	    <li onclick="javascript:window.location.href='${ctx}performance/welcome.action'">
	        <a href="javascript:void(0);" class="index">返回首页
	        </a>
	    </li>
	    <li class="active" onclick="clickMenu(0)">
	        <a href="javascript:void(0);" class="kh_info">考核信息
	            <em class="red_circle"></em>
	        </a>
	    </li>
	    <li onclick="clickMenu(1)">
	        <a href="javascript:void(0);" class="ziping">自述自评
	            <em id="zpzsPoint" class="red_circle"></em>
	        </a>
	    </li>
	    <li onclick="clickMenu(2)">
	        <a id="khtr" href="javascript:void(0);" class="others">考核他人
	            <em id="khtrPoint" class="red_circle"></em>
	        </a>
	    </li>
	    <li onclick="clickMenu(3)">
	        <a href="javascript:void(0);" class="score">考核成绩
	            <em id="jgshPoint" class="red_circle"></em>
	        </a>
	    </li>
	    <li onclick="clickMenu(4)">
	        <a href="javascript:void(0);" class="my_score">我的考核成绩
	            <em id="wdkhcjPoint" class="red_circle"></em>
	        </a>
	    </li>
	</ul>
	<div class="right_content" style="overflow:hidden;">	
		<iframe id="MyIframe" src="appraisalInfo.jsp?tid=${param.tid}" frameborder="0" width="100%" height="100%"></iframe>
	</div>
</body>
</html>
