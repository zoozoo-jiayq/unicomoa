<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>大屏展示时间</title>
<jsp:include page="../../common/flatHead.jsp"/>
<script type="text/javascript" src="${ctx}js/jquery-1.8.3.min.js"></script>
<link href="${ctx}css/bigView.css" rel="stylesheet" type="text/css" />
<link href="${ctx}css/fliptimer.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}js/logined/attendance/jquery.fliptimer.js"></script>
<script type="text/javascript" src="${ctx}js/logined/attendance/bigViewTime.js"></script>
<script>

	$(function(){
		$('.dowebok').flipTimer({
			seconds: true
		});
	});
</script>

</head>
  <body>
	<div class="screen-container">
			<div class="dowebok-box">
				<span class="today-time"></span>
				<div class="dowebok">
					<div class="hours"></div>
					<div class="minutes"></div>
					<div class="seconds"></div>
				</div>
				<!--end .dowebok-->
			</div>
			<!--end .dowebok-box-->
			<div class="people-box">
				<%-- <div class="row">
					<div class="col">
						<div class="info">
							<span class="img-box">
								<img src="${serverPath }files/upload/photo" alt="img">
							</span>
							<span class="name">张夏雨</span>
							<span class="time">08:10:12</span>
						</div>
					</div>
					<!--end .col-->
				</div> --%>
			<!--end .people-box-->
			</div>
			<div class="amount"></div>
	</div>
		<!--end .error-container-->
		<script>
			$(".screen-container").height($(window).height());
		</script>
  </body>
</html>
