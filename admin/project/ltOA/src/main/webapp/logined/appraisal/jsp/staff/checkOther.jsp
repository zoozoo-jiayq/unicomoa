<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <jsp:include page="../../common/taglibs.jsp"/>
    <title>考核他人</title>
	<script type="text/javascript" src="${ctx}/logined/appraisal/plugins/datatable/jquery.dataTables.min.js"></script>
	<script type="text/javascript" src="${ctx}/logined/appraisal/plugins/qytx-cbb-v1.0.js"></script>
	<script type="text/javascript" src="${ctx}logined/appraisal/plugins/dialog/artDialog4.1.6/artDialog.js?skin=blue"></script>
	<script type="text/javascript" src="${ctx}logined/appraisal/plugins/dialog/artDialog4.1.6/iframeTools.js"></script>
	<script type="text/javascript" src="${ctx}/logined/appraisal/js/staff/checkOther.js?version=${version}"></script>
  </head>
  
<body>
<input type="hidden" id="tid" value="${param.tid}">
		<!-- 右侧内容模块 -->
		<div class="iframe_right_content">
			<div class="navigation_bar">
				<p>当前位置：
					<a href="#">首页</a>
					<span> > 考核他人</span>
				</p>
			</div>
			<div class="kh_score_box">
				<div class="pr">
					<p class="kh_score_title" id="khmc">绩效考核</p>
				</div>
				<div class="kh_score_detail">
					<ul class="kh_detail_list">
						<li>
							<span class="kh_detail_list_title">状态：</span>
							<select class="kh_select" id="state" onchange="getList(this.value,$('#userName').val())">
								<option value="">请选择</option>
								<option value="1">已评分</option>
								<option value="2">未评分</option>
							</select>
						</li>
						<li class="mr10">
							<input type="text" class="creat_list_input" placeholder="请输入姓名" id="userName">
						</li>
						<li>
							<span class="addmuban_btn pdtb4" onclick="find()">搜索</span>
						</li>
					</ul>
					<p class="tishi_daiping">我已评分：<span class="color_red" id="dpfrs"></span></p>
 					<table class="score_table mt15">
						<thead>
							<tr>
								<th class="wd100">姓名</th>
								<th class="wd165">部门</th>
								<th class="wd165">职务</th>
								<th class="wd100">自述自评</th>
								<th class="wd80">评分</th>
							</tr>
						</thead>
						<tbody id="firstTr">
						</tbody>
					</table>
					
<!-- 					<table id="myTable" cellpadding="0" cellspacing="0" class="score_table mt15">
			            <thead>
			            <tr>
							<th id="userName" class="wd100">姓名</th>
							<th id="groupName" class="wd165">部门</th>
							<th id="job" class="wd165">职务</th>
							<th id="pl" class="wd100">自述自评</th>
							<th id="fz" class="wd80">评分</th>
			            </tr>
			            </thead>   
			        </table> -->
				</div>
			</div>
		</div>
<!-- 	<script src="../js/jquery-1.8.3.min.js"></script>
	<script src="../js/jxkh.js"></script> -->
</body>
</html>
