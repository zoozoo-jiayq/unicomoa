<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>正文历史记录</title>
<jsp:include page="publicDomHead.jsp" />
<script type="text/javascript" src="${ctx}/js/logined/publicDom/docHistory.js"></script>
</head>
<body>
	<input type="hidden" id="taskId" value='<%=request.getParameter("taskId")%>'/>
<div class="list" style="width:500px; margin:0 auto;">
<div class="pageTitle"><em class="iconList">历史版本</em><i class="ml10">共<span id="num">0</span>条</i></div>
				<table cellpadding="0" cellspacing="0"  class="pretty dataTable">
						<thead>
								<tr>
										<th style="width:100px">保存时间</th>
										<th style="width:100px">人员</th>
										<th style="width:80px;">操作</th>
								</tr>
						</thead>
						<tbody id="show">
								<!-- <tr class="even">
										<td>2013-03-18 17ï¼26</td>
										<td>ç²æ°¸ä¹</td>
										<td><a href="" title="">ä¸è½½</a><a href="" title="">é¢è§</a></td>
								</tr>
								<tr class="odd">
										<td>2013-03-18 17ï¼26</td>
										<td>é©¬ä¿å¼º</td>
										<td><a href="" title="">ä¸è½½</a><a href="" title="">é¢è§</a></td>
								</tr> -->
						</tbody>
				</table>
</div>
</body>
</html>