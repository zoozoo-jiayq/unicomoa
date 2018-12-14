<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <jsp:include page="../../common/taglibs.jsp"/>
    <title>绩效考核</title>
	<script type="text/javascript" src="${ctx}logined/appraisal/plugins/dialog/artDialog4.1.6/artDialog.js?skin=blue"></script> 
    <script type="text/javascript" src="${ctx}logined/appraisal/plugins/dialog/artDialog4.1.6/iframeTools.js"></script>
	<link href="${ctx}/logined/appraisal/plugins/datatable/skins/datatable_default.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="${ctx}/logined/appraisal/js/admin/adminHome.js?version=${version}"></script>
    
  </head>
<body style="overflow:hidden;">
<jsp:include page="../header.jsp"/>
	<div class="dc_index_content">
		<jsp:include page="../notify.jsp"/>
		<div class="dc_index_right">
			<div class="mb15">
				<span class="btn_inline btn_white" onclick="goCreate()">创建考核</span>
				<span class="btn_inline btn_white ml15" onclick="goTemplate()">模板设置</span>
			</div>
			<table class="report_table" cellspacing="0" style="border-bottom:none;">
				<thead class="report_one">
					<tr>
						<th class="wd300">考核名称</th>
						<th class="wd150">考核进度</th>
						<th class="wd100">操作</th>
					</tr>
				</thead>
			</table>
			<div class="index_table_box" style="border-top:none;">	
				<table id="myTable" class="report_table" cellspacing="0" style="border:none;">
					<!-- <thead class="report_one">
						<tr>
							<th class="wd300">考核名称</th>
							<th class="wd150">考核进度</th>
							<th class="wd100">操作</th>
						</tr>
					</thead> -->
					<tbody id="firstTr">
					</tbody>
				</table>
			</div>			

			<div class="liucheng_box">
				<p class="liucheng_title">操作流程</p>
				<ul class="liucheng_detail">
					<li>
						<em class="right_arrow"></em>
						<div class="detail_icons">
							<em class="gly_01"></em>
							<span>创建考核</span>
						</div>
					</li>
					<li>
						<em class="right_arrow"></em>
						<div class="detail_icons">
							<em class="gly_02"></em>
							<span>选择考核</span>
						</div>
					</li>
					<li>
						<em class="right_arrow"></em>
						<div class="detail_icons">
							<em class="gly_03"></em>
							<span>管控考核</span>
						</div>
					</li>
					<li>
						<em class="right_arrow"></em>
						<div class="detail_icons">
							<em class="gly_04"></em>
							<span>提交领导审批</span>
						</div>
					</li>
					<li>
						<div class="detail_icons">
							<em class="gly_05"></em>
							<span>查看考核成绩</span>
						</div>
					</li>
				</ul>
			</div>
		</div>
	</div>
</body>
</html>
