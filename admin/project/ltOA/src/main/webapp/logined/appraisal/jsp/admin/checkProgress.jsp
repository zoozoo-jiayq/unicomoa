<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <jsp:include page="../../common/taglibs.jsp"/>   
    <title>考核进度查询</title>
	<link href="${ctx}/logined/appraisal/plugins/datatable/skins/datatable_default.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="${ctx}/logined/appraisal/plugins/datatable/jquery.dataTables.min.js"></script>
	<script type="text/javascript" src="${ctx}/logined/appraisal/plugins/qytx-cbb-v1.0.js"></script>
	<script type="text/javascript" src="${ctx}/logined/appraisal/js/admin/checkProgress.js?version=${version}"></script> 
	<script type="text/javascript" src="${ctx}logined/appraisal/plugins/dialog/artDialog4.1.6/artDialog.js?skin=blue"></script> 
    <script type="text/javascript" src="${ctx}logined/appraisal/plugins/dialog/artDialog4.1.6/iframeTools.js"></script>
  </head>
  
<body>

<input type="hidden" id="tid" value="${param.tid}">
		<!-- 右侧内容模块 -->
		<div class="iframe_right_content" >
			<div class="navigation_bar">
				<p>当前位置：
					<a href="#">首页</a>
					<span> > 考核进度</span>
				</p>
			</div>
			<div class="kh_score_box">
				<div class="pr">
					<p class="kh_score_title" id="khmc">绩效考核</p>
				</div>
				<div class="kh_score_detail">
					<div class="progress_tabs">
						<ul class="tabs_list">
							<li class="active">自述进度</li>
							<li >进度查询</li>
						</ul>
					</div>
					<div class="progress_item">
						<ul class="creat_list">
							<li class="pdl50">
								<span class="creat_list_title wd45">状态：</span>
								<select class="creat_list_select" id="state" onchange="getList(this.value)">
									<option value="-2">请选择</option>
									<option value="0">未自评述职</option>
									<option value="1">已自评述职</option>
									<!-- <option value="-1">无需自评述职</option> -->
								</select>
							</li>
						</ul>
						<table id="myTable" cellpadding="0" cellspacing="0" class="score_table mt15">
				            <thead>
				            <tr>
				                  <th id="khryName" class="wd100">姓名</th>
				                  <th id="khryGroup" class="wd200">部门</th>
				                  <th id="khryJob" class="wd200">职务</th>
				                  <th class="wd200">查看自述自评</th>
				            </tr>
				            </thead>   
			      		</table>
					</div>
					<div class="progress_item hide">
						<ul class="creat_list">
							<li class="pdl50">
								<span class="creat_list_title wd45">状态：</span>
								<select class="creat_list_select"  id="pfState" onchange="getList2(this.value)">
									<option value="-1">请选择</option>
									<option value="1">评分完成</option>
									<option value="2">评分未完成</option>
								</select>
							</li>
						</ul>
						<table id="myTable2" cellpadding="0" cellspacing="0" class="score_table mt15">
				            <thead>
				            <tr>
				                  <th id="khryName" class="wd100">姓名</th>
				                  <th id="khryGroup" class="wd165">部门</th>
				                  <th id="ydfrs" class="wd100">已打分人数</th>
				                  <th id="wdfrs" class="wd100">未打分人数</th>
				            </tr>
				            </thead>   
			      		</table>
<!-- 						<table class="score_table mt20">
							<thead>
								<tr>
									<th class="wd100">姓名</th>
									<th class="wd165">部门</th>
									<th class="wd100">已打分人数</th>
									<th class="wd100">未打分人数</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td>羊羊羊</td>
									<td>产品部</td>
									<td class="blue_color" onclick="ccc()">4</td>
									<td class="blue_color" onclick="aaa()">6</td>
								</tr>
							</tbody>
						</table> -->
					</div>
				</div>

			</div>
		</div>

		<!--侧滑会话详情-->
		<div class="modal">
			<div class="pop_yaw1" id="iframeDiv1">
				<div class="pr">
					<em class="pop_close" onclick="shut()"></em>
				</div>
				<p class="dafen_title">未打分人员</p>
				<div class="person_name_box">
					<div class="person_info" id="wdfrys">
						
					</div>
					<!-- <span class="btn_inline btn_blue name_box_btn">确定</span> -->
				</div>			
			</div>
			<div class="pop_yaw1" id="iframeDiv">
				<div class="pr">
					<em class="pop_close" onclick="shut()"></em>
				</div>
				<p class="dafen_title">已打分人员</p>
				<div class="person_name_box">
					<div class="person_info" id="ydfrys">
						
					</div>
					<!-- <span class="btn_inline btn_blue name_box_btn">确定</span> -->
				</div>			
			</div>
		</div>
		
	<!-- <script src="../js/jquery-1.8.3.min.js"></script>
	<script src="../js/jxkh.js"></script> -->
	<script>
	$(function() {		
		/*进度查询页面切换*/
		$(".tabs_list li").live("click",function(){
			$(this).addClass('active');
			$(this).siblings().removeClass('active')
			var index = $(this).index();
			$(".progress_item").eq(index).removeClass('hide');
			$(".progress_item").eq(index).siblings(".progress_item").addClass('hide');
		})	
	})
		/*侧滑实验*/
		// 查看未打分人员
		var wdf = function(khryId){
			getDfry(khryId,2);
			$(".modal").show();
			$("#iframeDiv1").show().css("z-index","10000");
			$("#iframeDiv1").animate({right:"0px"},300);			
		}
		// 查看已打分人员
		var ydf = function(khryId){
			getDfry(khryId,1);
			$(".modal").show();
			$("#iframeDiv").show().css("z-index","10000");
			$("#iframeDiv").animate({right:"0px"},300);
		}

		//侧滑关闭
		var shut = function(){
			$("#iframeDiv1").animate({right:"-525px"},300);	
			$("#iframeDiv").animate({right:"-525px"},300);	
			setTimeout(function(){
				$(".modal").hide();				
			},300) 
		}
		
		var nameBox=$(window).height()-90-50;
		$(".person_name_box").css("height",nameBox);
	</script>
</body>
</html>
