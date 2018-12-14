<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>查询提醒</title>
<%@ include file="../../common/taglibs.jsp"%>
<jsp:include page="../../common/head.jsp" />
<script type="text/javascript">
	var basePath = "${ctx}";
</script>
<script type="text/javascript"	src="${ctx}js/logined/affairs/list_all_affairs.js"></script>
<script type="text/javascript" src="${ctx}js/user/selectuser.js"></script>
<script type="text/javascript" src="${ctx}js/common/hashmap.js"></script>
<script type="text/javascript" src="${ctx}js/user/user.js"></script>
<link href="${ctx}css/home.css" rel="stylesheet" type="text/css" />
</head>
<body>
	<div class="input" style="width: auto;">
		<div id="searchArea">
			<div class="pageTitle">
				<em class="iconSearch">查询</em>
			</div>
			<table width="100%" border="0" cellpadding="0" cellspacing="0"
				class="inputTable">
				<tr>
					<th style="width:80px"><select id="userType" name="userType">
							<option value='1'>发送人</option>
							<option value='2'>收信人</option>
					</select></th>
					<td><input type="hidden" id="userIds" /> <textarea
							id="userNames" class="readOnlyText" style="width:80%" rows="2"
							readonly="readonly"></textarea><span class="addMember"><a class="icon_add"
						href="javascript:void(0);" onclick="selectPerson()">添加</a> <a
						class="icon_clear" href="javascript:void(0);"
						onclick="clearPerson()">清空</a></span></td>
				</tr>
				<tr>
					<th><label>类型：</label></th>
					<td><select id="smsType" name="smsType">
							<option value='-1'>所有类型</option>
							<option value='通讯薄'>通讯薄</option>
							<option value='电子邮件'>电子邮件</option>
							<option value='工作日志'>工作日志</option>
							<option value='公告通知'>公告通知</option>
							<option value='日程安排'>日程安排</option>
							<option value='日程安排-周期性事务'>日程安排-周期性事务</option>
							<option value='文件柜'>文件柜</option>
							<option value='工作流：提醒下一步经办人'>工作流：提醒下一步经办人</option>
							<option value='工作流：提醒流程发起人'>工作流：提醒流程发起人</option>
							<option value='工作流：提醒流程所有人员'>工作流：提醒流程所有人员</option>
							<option value='聊天提醒'>聊天提醒</option>
							<option value='公文管理：发文核稿'>公文管理：发文核稿</option>
							<option value='公文管理：收文登记'>公文管理：收文登记</option>
							<option value='公文管理：领导批阅'>公文管理：领导批阅</option>
							<option value='公文管理：收文分发'>公文管理：收文分发</option>
							<option value='公文管理：收文阅读'>公文管理：收文阅读</option>
					</select></td>
				</tr>
				<tr>
					<th><label>时间：</label></th>
					<td><input id="startTime" name="paramValue" type="text"
						class="Wdate formText"
						onfocus="WdatePicker({maxDate: '#F{$dp.$D(\'endTime\')}',skin:'default',dateFmt:'yyyy-MM-dd HH:mm:ss'})"
						size="30" />&nbsp;-&nbsp;<input id="endTime" name="paramValue" type="text"
						class="Wdate formText"
						onfocus="WdatePicker({minDate: '#F{$dp.$D(\'startTime\')}',skin:'default',dateFmt:'yyyy-MM-dd HH:mm:ss'})"
						size="30" /></td>
				</tr>
			
				<tr>
					<th><label>内容：</label></th>
					<td><textarea id="contentInfo" name="contentInfo"
					        onKeyUp="if(this.value.length > 3000) this.value=this.value.substr(0,3000)"
							class="formTextarea" style="width:80%" rows="4"></textarea></td>
				</tr>
				<tr>
					<th><label>排序字段：</label></th>
					<td><select id="sortFiled" name="userType">
							<option value='1'>类型</option>
							<option value='2'>内容</option>
							<option value='3'>发信人/收信人</option>
							<option value='4'>发送时间</option>
					</select> <select id="sortType" name="userType">
							<option value='DESC'>降序</option>
							<option value='ASC'>升序</option>
					</select></td>
				</tr>
				<tr>
					<th><label>操作：</label></th>
					<td><label class="radio"><input type="radio"
							name="operationType" checked="checked" value="1" />查询</label> <label
						class="radio"><input type="radio" name="operationType"
							value="2" />导出</label> <label class="radio"><input type="radio"
							name="operationType" value="3" />删除</label></td>
				</tr>
			</table>
			<div class="buttonArea"><input id="confirm" hideFocus=""
					value="确 定" type="submit" class="formButton" />
			</div>
		</div>
	</div>


	<div class="list" id="tableArea" style="display: none;">
		<div class="pageTitle">
			<em class="iconList">查询结果（最多显示200条记录）</em>
		</div>
		<div class="listbtn">
			<div class="tDiv2">
				<div class="fbutton">
					<div>
						<span id="deleteBtn" class="delete" title="删除对方未读的提醒后，对方将不会接收到">删除</span>
					</div>
				</div>
				<div class="btnseparator"></div>
				<div class="fbutton">
					<div>
						<span id="deleteAllBtn" class="delete">全部删除</span>
					</div>
				</div>

				
			</div>
			<div style="clear:both"></div>
		</div>
		<table id="myTable" cellpadding="0" cellspacing="0"
			class="pretty dataTable">
			<thead>
				<tr>
					<th class="chk"><input type="checkbox" name="" /></th>
					<th style="width: 100px;">发送人</th>
					<th>内容</th>
					<th style="width: 150px;">发送时间</th>
					<th style="width: 40px;">提醒</th>
					<th style="width: 80px;">操作</th>
				</tr>
			</thead>
		</table>
		<div class="buttonArea">
			<input id="backBtn" value="返 回" class="formButton" type="submit" />
		</div>
	</div>
</body>
</html>