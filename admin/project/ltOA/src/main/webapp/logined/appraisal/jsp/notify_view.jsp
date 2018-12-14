<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <jsp:include page="../common/taglibs.jsp"/>   
    <title>通知公告详情</title>


	  <link rel="stylesheet" type="text/css" href="${ctx}/logined/appraisal/plugins/uploadify.css"/>
	  <script type="text/javascript" src="${ctx}/logined/appraisal/plugins/jquery.uploadify.min.js"></script>
	  <script type="text/javascript" src="${ctx}/logined/appraisal/plugins/attachHelp.js"></script>
	  <script type="text/javascript" src="${ctx}/logined/appraisal/plugins/attachment.js"></script>
	  <script type="text/javascript" src="${ctx}/logined/appraisal/common/commonUpload.js"></script>
	  <script type="text/javascript"
			  src="${ctx}logined/appraisal/plugins/dialog/artDialog4.1.6/artDialog.js?skin=blue"></script>
	  <script type="text/javascript"
			  src="${ctx}logined/appraisal/plugins/dialog/artDialog4.1.6/iframeTools.js"></script>

	  <script type="text/javascript" src="${ctx}/logined/appraisal/js/notify_view.js?version=${version}"></script>
  </head>
  
<body>
<input type="hidden" id="id" value="${param.id}">
<!-- 外面的div是模拟的alert弹框，开发不需要复制外层div -->
	<div class="alert_container" style="width:770px;">
		<div>
			<p class="gonggao_title" id="title">通知</p>
			<div class="fabu_info">
				<span class="fl">发布时间：<span id="time">无</span></span>
				<span class="fr ml40">浏览次数：<span id="num">0</span></span>
				<span class="fr">发布人：<span id="name">无</span></span>
			</div>
			<p class="gonggao_detail" id="content">无</p>
            <span id="fj">附件：</span>
            <div  id="file"></div>
            				
		</div>
	</div>
</body>
</html>
