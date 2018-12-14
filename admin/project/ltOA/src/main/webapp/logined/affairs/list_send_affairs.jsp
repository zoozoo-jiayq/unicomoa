<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>已发送的提醒</title>
<%@ include file="../../common/taglibs.jsp"%>
<jsp:include page="../../common/head.jsp" />
<script type="text/javascript">
	var basePath = "${ctx}";
</script>
<script type="text/javascript"
	src="${ctx}js/logined/affairs/list_send_affairs.js"></script>
<link href="${ctx}css/home.css" rel="stylesheet" type="text/css" />
</head>

<body>
	<div class="list">
		<div class="pageTitle">
			<em class="iconList">已发送提醒</em>
		</div>
		<div class="listbtn">
			<div class="tDiv2">
				<div class="fbutton">
					<div>
						<span id="deleteBtn" class="delete">删除</span>
					</div>
				</div>
				<div class="btnseparator"></div>
			<!-- 	<div class="fbutton">
					<div>
						<span id="deleteAllReaded" class="delete">删除已提醒收信人提醒</span>
					</div>
				</div>
				<div class="fbutton">
					<div>
						<span id="deleteToUserDeleted" class="delete">删除收信人已删除提醒</span>
					</div>
				</div>
				 -->
				<div class="fbutton">
					<div>
						<span id="deleteAllBtn" class="delete">全部删除</span>
					</div>
				</div>
				
			</div>
			<div style="clear:both"></div>
		</div>
		<table id="myTable" class="pretty dataTable" cellpadding="0"
			cellspacing="0">
			<thead>
				<tr>
					<th class="chk"><input type="checkbox" name="" />
					</th>
					<th style="width: 100px;">发送人</th>
					<th>内容</th>
					<th>发送时间</th>
					<th style="width: 40px">类型</th>
					<th style="width: 150px;">操作</th>
				</tr>
			</thead>
		</table>
	</div>
</body>
</html>