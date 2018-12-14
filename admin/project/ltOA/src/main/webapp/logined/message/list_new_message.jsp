<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>未确认的微讯</title>
		<jsp:include page="../../common/flatHead.jsp" />
		<script type="text/javascript">
	var basePath = "${ctx}";
</script>
		<script type="text/javascript"
			src="${ctx}js/logined/message/list_new_message.js"></script>
		
	</head>

	<body>
		<div class="list" style="margin-top:10px">
			<div class="pageTitle">
				<em class="iconList">未确认的消息 </em>
			</div>
			<div class="listbtn">
				<div class="tDiv2">
					<div class="fbutton">
						<div>
							<span id="deleteBtn" class="delete">删除</span>
						</div>
					</div>
					<div class="btnseparator"></div>
				</div>
				<div style="clear: both"></div>
			</div>
			<table id="myTable" cellpadding="0" cellspacing="0" class="pretty">
				<thead>
					<tr>
						<th class="chk">
							<input type="checkbox" name="" id="total" />
						</th>
						<th style="width: 100px;" id="fromUserName">
							发送人
						</th>
						<th style="width: 100%" id="contentInfo" class="longTxt">
							内容
						</th>
						<th style="width: 120px;" id="sendTime">
							发送时间
						</th>
						<th style="width: 150px;" class="right_bdr0">
							操作
						</th>
					</tr>
				</thead>
			</table>
		</div>
	</body>
</html>