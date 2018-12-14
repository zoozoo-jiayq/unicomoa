<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <jsp:include page="../../common/taglibs.jsp"/>
    
    <title>查看自述自评</title>
	  <script type="text/javascript" src="${ctx}logined/appraisal/plugins/dialog/artDialog4.1.6/artDialog.js?skin=blue"></script>
	  <script type="text/javascript" src="${ctx}logined/appraisal/plugins/dialog/artDialog4.1.6/iframeTools.js"></script>
	<script type="text/javascript" src="${ctx}/logined/appraisal/js/staff/appraisalShow.js?version=${version}"></script>
	<script type="text/javascript" src="${ctx}/logined/appraisal/plugins/attachHelp.js"></script>
  </head>
<body>
<input type="hidden" id="tid" value="${param.tid}">
<input type="hidden" id="userId" value="${param.userId}">
		<!-- 右侧内容模块 -->
		<div class="iframe_right_content">
			<div class="navigation_bar">
				<p>当前位置：
					<a href="#">首页</a>
					<span> > 考核他人</span>
					<span> > 查看自述自评</span>
				</p>
				<a href="${ctx}/logined/appraisal/jsp/staff/checkOther.jsp?tid=${param.tid}" class="back_btn fr">>> 返回</a>
			</div>
			<div class="kh_score_box">
				<div class="pr">
					<p class="kh_score_title">2016年度公司员工绩效考核</p>
				</div>	
				<div class="kh_score_detail">
					<ul class="kh_detail_list">
						<li>
							<span class="kh_detail_list_title">姓名：</span>
							<span class="kh_detail_list_content" id="userName"></span>
						</li>
						<li>
							<span class="kh_detail_list_title">部门：</span>
							<span class="kh_detail_list_content" id="groupName"></span>
						</li>
						<li>
							<span class="kh_detail_list_title">职务：</span>
							<span class="kh_detail_list_content" id="job"></span>
						</li>
					</ul>
					<ul class="khinfo_list">
						<li>
							<span class="khinfo_list_title">自述自评：</span>
							<p class="khinfo_list_detail" id="wdsz"></p>
						</li>
						<li>
							<span class="khinfo_list_title">述职文件：</span>
							<p class="khinfo_list_detail" id="attachmentList">	无						
							</p>							
						</li>
						<li>
							<span class="khinfo_list_title">考核项：</span>
						</li>
					</ul>
					<div class="pdl30">
						<table class="score_table">
							<thead>
								<tr>
									<th class="wd115">指标分类</th>
									<th class="wd165">指标名称</th>
									<th class="wd400">指标描述</th>
									<th class="wd100">分值</th>
									<th class="wd80">打分</th>
								</tr>
							</thead>
							<tbody id="firstTr">

							</tbody>
						</table>
					</div>
					<p class="kh_total">总计：<span id="sum">0</span>分</p>
				</div>
			</div>
		</div>
<!-- 	<script src="../js/jquery-1.8.3.min.js"></script>
	<script src="../js/jxkh.js"></script> -->
</body>
</html>
