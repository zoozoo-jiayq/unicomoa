<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <jsp:include page="../../common/taglibs.jsp"/>
    <title>设定分级标准</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript" src="${ctx}/logined/appraisal/js/admin/add_graderNormal.js?version=${version}"></script>	
	<script type="text/javascript" src="${ctx}logined/appraisal/plugins/dialog/artDialog4.1.6/artDialog.js?skin=blue"></script> 
    <script type="text/javascript" src="${ctx}logined/appraisal/plugins/dialog/artDialog4.1.6/iframeTools.js"></script>
  </head>
  
<body>
<jsp:include page="../header.jsp"/>
<input type="hidden" id="vid" value="${param.vid}">
<input type="hidden" id="ck_vid" value="${param.ck_vid}">
<input type="hidden" id="type" value="${param.type}">
<input type="hidden" id="hid" value="${param.hid}">
	<div class="creat_container">
		<div class="crumbs">
			<ul>
				<li>
					<a href="#">1、填写考核信息</a>
					<a href="#" class="has_done">1、填写考核信息</a>
				</li>
				<li>
					<a href="#">2、考核表设置</a>
					<a href="#" class="has_done">2、考核表设置</a>
				</li>
				<li>
					<a href="#">3、设定考核关系</a>
					<a href="#" class="has_done">3、设定考核关系</a>
				</li>
				<li>
					<a href="#">4、设定分级标准</a>
					<a href="#" class="has_done">4、设定分级标准</a>
				</li>
			</ul>
		</div>
		<ul class="creat_list four_type">
			<li>
				<span class="creat_list_title">考核分级：</span>
				<select class="creat_list_select" id="khfj" onchange="gradeChange()">
				</select>
			</li>
			<li>
				<span class="creat_list_title">分级描述：</span>
				<p class="mt3" id="fjms">无</p>
			</li>
		</ul>
		<table class="score_table">
			<thead>
				<tr>
					<th>级别</th>
					<th>级名</th>
					<th>分值区间</th>
				</tr>
			</thead>
			<tbody id="firstTr">
			</tbody>
		</table>
		<div class="btn_container2">
			<span class="btn_inline btn_white" onclick="goBack()">上一步</span>
			<span class="btn_inline btn_gray ml15" onclick="toClear()">重置</span>
			<span class="btn_inline btn_white ml15" onclick="save()">提交</span>
		</div>
	</div>
</body>
</html>
