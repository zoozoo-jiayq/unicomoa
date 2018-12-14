<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <jsp:include page="../../common/taglibs.jsp"/>
    <title>绩效考核</title>

	<link href="${ctx}/logined/appraisal/plugins/datatable/skins/datatable_default.css" rel="stylesheet" type="text/css" />
	  <script type="text/javascript" src="${ctx}logined/appraisal/plugins/dialog/artDialog4.1.6/artDialog.js?skin=blue"></script>
	  <script type="text/javascript" src="${ctx}logined/appraisal/plugins/dialog/artDialog4.1.6/iframeTools.js"></script>
	  <script type="text/javascript" src="${ctx}/logined/appraisal/js/staff/staffHome.js?version=${version}"></script>
  </head>
<body>
<jsp:include page="../header.jsp"/>
	<div class="dc_index_content">
		<jsp:include page="../notify.jsp"/>
		<div class="dc_index_right">	
			<div class="index_table_box">		
		        <table id="myTable" cellspacing="0" class="report_table">
		            <thead class="report_one">
		            <tr>
		                  <th class="wd300">考核名称</th>
		                  <th class="wd150">考核进度</th>
		                  <th class="wd150">我的自评</th>
		                  <th class="wd150">我已评分</th>
		                  <th class="wd100">操作</th>
		            </tr>
		            </thead>
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
							<em class="czliucheng_01"></em>
							<p>选择考核</p>
						</div>
					</li>
					<li>
						<em class="right_arrow"></em>
						<div class="detail_icons">
							<em class="czliucheng_02"></em>
							<p>自述自评</p>
							<p>(如果需要)</p>
						</div>
					</li>
					<li>
						<em class="right_arrow"></em>
						<div class="detail_icons">
							<em class="czliucheng_03"></em>
							<p>考核他人</p>
							<p>(如果需要)</p>
						</div>
					</li>
					<li>
						<em class="right_arrow"></em>
						<div class="detail_icons">
							<em class="czliucheng_04"></em>
							<p>审批结果</p>
							<p>(领导)</p>
						</div>
					</li>
					<li>
						<div class="detail_icons">
							<em class="czliucheng_05"></em>
							<p>查看我的考核成绩</p>
						</div>
					</li>
				</ul>
			</div>
		</div>
	</div>
</body>
</html>
