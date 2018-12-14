、<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>大屏展示</title>
<jsp:include page="../../common/flatHead.jsp"/>
<script type="text/javascript" src="${ctx}js/jquery-1.8.3.min.js"></script>
<link href="${ctx}css/bigView.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}js/logined/attendance/scroll.js"></script>
<script type="text/javascript" src="${ctx}js/logined/attendance/bigView.js"></script>
<script>
			$(function(){
				$("div.list_lh").myScroll({
					speed:100, //数值越大，速度越慢
					rowHeight:30 //li的高度
				});
			});
		</script>
</head>
  <body>
  	<div class="check-container">
			<div class="big-title">
				<p id="timeView"></p>
			</div>
			<!--end .big-title-->
			<div class="table-box">
				<div class="col width35">
					<p class="title">已签到</p>
					<div class="table-title-box">
						<span class="width50">姓名</span>
						<span class="width50">打卡时间</span>
					</div>
					<!--end .table-title-box-->
					<div class="list_lh">
					<!-- 已签到 -->
						<ul id="goSignList">
						</ul>
					</div>
					<!--end .list_1h-->
				</div>
				<!--end .col-->
				<div class="col width42">
					<p class="title">迟到</p>
					<div class="table-title-box">
						<span class="width40">姓名</span>
						<span class="width60">打卡时间</span>
					</div>
					<!--end .table-title-box-->
					<div class="list_lh height63">
						<ul id="goLateList">
							
						</ul>
					</div>
					<!--end .list_1h-->
				</div>
				<!--end .col-->
				<div class="col top5 width42">
					<p class="title">外勤</p>
					<div class="table-title-box">
						<span class="width30">姓名</span>
						<span class="width30">打卡时间</span>
						<span class="width40">备注</span>
					</div>
					<!--end .table-title-box-->
					<div class="list_lh height63">
						<ul id="goOutList">
							
							
						</ul>
					</div>
					<!--end .list_1h-->
				</div>
				<!--end .col-->
				<div class="col top5 width42">
					<p class="title">请假</p>
					<div class="table-title-box">
						<span class="width30">姓名</span>
						<span class="width35">开始时间</span>
						<span class="width35">结束时间</span>
					</div>
					<!--end .table-title-box-->
					<div class="list_lh height63">
						<ul id="goLeaveList">
							
						</ul>
					</div>
					<!--end .list_1h-->
				</div>
				<!--end .col-->
			</div>
			<!--end .table-box-->
		</div>
  </body>
  <script>
			 $(".check-container").height($(window).height());
			/*$(".check-container").css("min-height","$(window.height())"); */
	</script>
</html>
