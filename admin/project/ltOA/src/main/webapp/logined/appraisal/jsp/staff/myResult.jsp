<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<jsp:include page="../../common/taglibs.jsp"/>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>我的结果</title>

	  <script type="text/javascript" src="${ctx}logined/appraisal/plugins/dialog/artDialog4.1.6/artDialog.js?skin=blue"></script>
	  <script type="text/javascript" src="${ctx}logined/appraisal/plugins/dialog/artDialog4.1.6/iframeTools.js"></script>
	<script type="text/javascript" src="${ctx}/logined/appraisal/js/staff/myResult.js?version=${version}"></script>
	<style>
		.aui_content{padding:0!important;}
	</style>

  </head>
<body>
<input type="hidden" id="tid" value="${param.tid}">
<input type="hidden" id="userId" value="${param.userId}">
<input type="hidden" id="typeId" value="${param.typeId}">
		<!-- 右侧内容模块 -->
		<div class="iframe_right_content">
			<div class="navigation_bar">
				<p>当前位置：
					<a href="#">首页</a>
					<span> > 考核结果</span>
				</p>
				<a id="back"  class="back_btn fr">>> 返回</a>
			</div>
			<div class="kh_score_box">
				<div class="pr">
					<p class="kh_score_title" id="khmc">绩效考核</p>
										
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
						<li class="percent100 clear_pd"></li>
						<li  id="gd_zz">
							<span class="kh_detail_list_title">最终得分：</span>
							<span class="kh_detail_list_content" id="zzfz"></span>分
						</li>
						<li id="gd_ys">
							<span class="kh_detail_list_title">原始得分：</span>
							<span class="kh_detail_list_content" id="ysfz"></span>分
							<a class="search_icon result_search" href="#" onclick="show()"></a>
						</li>
						<li id="ks_fz">
							<span class="kh_detail_list_title">得分：</span>
							<span class="kh_detail_list_content" id="fz">0</span>分
						</li>
						<li>
							<span class="kh_detail_list_title">级别：</span>
							<span class="kh_detail_list_content" id="jb">无</span>
						</li>
					</ul>
					<ul class="khinfo_list">
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
							<tbody  id="firstTr">
							</tbody>
						</table>
					</div>
					
					<p class="kh_total">总计：<span id="sum">0</span>分</p>
					<!-- <div class="kh_remark">
						<p class="kh_remark_title">我的考核评语</p>
						<ul class="khinfo_list" id="firstLi">
						</ul>
					</div> -->
					<ul class="khinfo_list">
						<li>
							<span class="khinfo_list_title">考核评语：</span>
							
						</li>
					</ul>
					<div class="pdl30">
						<ul class="pingjia_list" id="firstLi">
						</ul>
					</div>
					
				</div>
			</div>
		</div>
	<!-- <script src="../js/jquery-1.8.3.min.js"></script>
	<script src="../js/jxkh.js"></script> -->
</body>
</html>
