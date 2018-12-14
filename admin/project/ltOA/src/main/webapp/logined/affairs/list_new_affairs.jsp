<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>事务提醒</title>
<jsp:include page="../../common/flatHead.jsp" />
<link href="${ctx}/flat/css/reset.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/flat/css/Reminder.css" rel="stylesheet" type="text/css" />
<link href="${ctx}flat/plugins/datatable/skins/datatable_page.css" rel="stylesheet" type="text/css" />
<link href="${ctx}flat/plugins/datatable/skins/datatable_default.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}flat/plugins/datatable/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="${ctx}plugins/datatable/selecedForDatatablePagination.js"></script>
<script type="text/javascript" src="${ctx}js/logined/affairs/list_new_affairs.js"></script>
</head>
<body>
<div class="list">
		<div class="searchArea">
				<table cellspacing="0" cellpadding="0">
						<tbody>
								<tr>
                                		<td class="right">&nbsp;</td>
										<td style="width:92px;"><div class="fButton orangeBtn" id="deleteBtn"> <span class="delete" >删除</span> </div></td>
								</tr>
						</tbody>
				</table>
		</div>
		<table class="pretty dataTable"  cellspacing="0" cellpadding="0" id="myTable">
				<thead>
						<tr >
								<th class="chk"><input  type="checkbox" id="total"/></th>
								<th style="width: 140px;" id="sendTime">发送时间</th>
								<th style="width: 100%;" id="contentInfo" class="tdCenter longTxt iconText">内容</th>
								<th style="width: 100px;" id="fromUserName">发送人</th>
								<th class="right_bdr0" style="width: 70px;">操作</th>
						</tr>
				</thead>
				<tbody>
						
				</tbody>
		</table>
</div>
</body>
</html>
