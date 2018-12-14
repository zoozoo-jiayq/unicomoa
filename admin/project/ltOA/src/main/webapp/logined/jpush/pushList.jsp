<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<jsp:include page="../../common/taglibs.jsp" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>消息列表</title>
<jsp:include page="../../common/head.jsp" />
<link rel="shortcut icon" href="${ctx }images/favicon.ico"
	type="image/x-icon" />
<script type="text/javascript" src="${ctx}/js/logined/jpush/pushList.js?v=${jsversion}"></script>
<script type="text/javascript">
var basePath = '${ctx }';
</script>
</head>
<body>
	<div class="list">
		<div class="listbtn">
			<table cellspacing="0" cellpadding="0">
				<tbody>
					<tr>
						<td style="width: 20%">
							<div class="tDiv2">
								<div class="fbutton">
									<div>
										<span class="add" id="add">新增</span>
									</div>
								</div>
								<div class="fbutton">
									<div>
										<span class="delete" id="delete">删除</span>
									</div>
								</div>
							</div></td>
						<td class="right" style="width: 80%"><label>起止时间：</label><input
							id="startDate" type="text" class="formText Wdate"
							onfocus="WdatePicker({maxDate: '#F{$dp.$D(\'endDate\')}',skin:'default',dateFmt:'yyyy-MM-dd'})" />&nbsp;到&nbsp;<input
							id="endDate" type="text" class="formText Wdate"
							onfocus="WdatePicker({minDate: '#F{$dp.$D(\'startDate\')}',skin:'default',dateFmt:'yyyy-MM-dd'})" />
							&nbsp; <label>主题：</label><input type="text" class="formText"
							style="width:150px;" id="searchName" /> &nbsp; <input
							type="button" id="search" class="formButton" value="查询" /></td>
					</tr>
				</tbody>
			</table>
		</div>


		<div class="prettyList">
			<table cellpadding="0" cellspacing="0" class="pretty dataTable"
				id="myTable">
				<thead>
					<tr>
						<th class="chk"><input type='checkbox' id='total' />
						</th>
						<th class="num">序号</th>
						<th style="width:40%;">主题</th>
						<th style="width:60%;">内容</th>
						<th style="width:80px;">发布人</th>
						<th style="width:180px;">发布时间</th>
						<th style="width:70px;" class="right_bdr0">操作</th>
					</tr>
				</thead>
				<tbody>

				</tbody>
			</table>
			<div class="clear"></div>
		</div>
	</div>
</body>
</html>
