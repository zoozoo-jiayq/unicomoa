<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>新建群组</title>
<%@ include file="../../common/taglibs.jsp"%>
<jsp:include page="../../common/head.jsp" />
<script type="text/javascript">
	var basePath = "${ctx}";
</script>
<script type="text/javascript"
	src="${ctx}js/logined/publicaddress/update_group.js"></script>
<script type="text/javascript" src="${ctx}js/common/validate_form.js"></script>
<script type="text/javascript" src="${ctx}js/common/showError.js"></script>
<script type="text/javascript" src="${ctx}js/user/selectuser.js"></script>
<script type="text/javascript" src="${ctx}js/common/hashmap.js"></script>
<script type="text/javascript" src="${ctx}js/user/user.js"></script>
</head>

<body>
	<input type="hidden" id="groupId"
		value="<s:property value='#request.addressGroup.id'/>" />
	<div class="input">
		<div class="pageTitle">
			<em class="iconAdd">修改分组</em>
		</div>
		<form id="form1">
			<table width="100%" border="0" cellpadding="0" cellspacing="0"
				class="inputTable">
				<tr>
					<th>排序号</th>
					<td><input id="orderNo" name="" type="text" class="formText"
						style="width:80%"  maxlength="9"
						value="<s:property value="#request.addressGroup.orderNo" />"
						onkeyup="this.value=this.value.replace(/[^0-9]/g,'')" />
					</td>
					<th><span class="requireField">*</span>分组名称</th>
					<td><input id="name" name="" type="text" class="formText" style="width:80%" 
						valid="required" errmsg="addressbook.msg_groupName_not_null"
						value="<s:property value="#request.addressGroup.name" />"
						maxlength="30" /> 
					</td>
				</tr>
				<tr>
					<th>范围（部门）</th>
					<td colspan="3">
					<input id="groupIds" name="userIds"
						value="<s:property value='#request.dataPriv.groupIds'/>" type="hidden" />
					<textarea id="groupNames"  name="textarea" style="width:80%"  rows="4" readonly="readonly"
							class="readOnlyText"><s:property value='#request.dataPriv.groupNames'/></textarea><span class="addMember"><a class="icon_add" href="javascript:void(0);" onclick="selectGroup()">添加</a><a
						class="icon_clear" href="javascript:void(0);" onclick="clearGroup()">清空</a></span>
					</td>
				</tr>
				<tr>
					<th>范围（角色）</th>
					<td colspan="3">
					<input id="roleIds" name="userIds"
						value="<s:property value='#request.dataPriv.roleIds'/>" type="hidden" />
						<textarea id="roleNames" name="textarea" style="width:80%"  rows="4" readonly="readonly"
							class="readOnlyText"><s:property value='#request.dataPriv.roleNames'/></textarea> <span class="addMember"><a class="icon_add" href="javascript:void(0);" onclick="selectRole()">添加</a><a
						class="icon_clear" href="javascript:void(0);" onclick="clearRole()">清空</a></span>
					</td>
				</tr>
				<tr>
					<th>范围（人员）</th>
					<td colspan="3">
					<input id="userIds" name="userIds"
						value="<s:property value='#request.dataPriv.userIds'/>" type="hidden" />
					<textarea id="userNames" style="width:80%"  rows="4" readonly="readonly"
							class="readOnlyText"><s:property value='#request.dataPriv.userNames'/></textarea> <span class="addMember"><a class="icon_add" href="javascript:void(0);" onclick="selectPerson()">添加</a><a
						class="icon_clear"  href="javascript:void(0);" onclick="clearPerson()">清空</a></span>
					</td>
				</tr>
			</table>
		</form>
		<div class="buttonArea">
			<input id="update" hideFocus="" class="formButton_green" value="修 改" type="button">
			&nbsp;&nbsp; <span class="mainButton"><input id="back" hideFocus="" value="返 回" type="button" class="formButton_grey" onclick="javascript:window.location.href = document.referrer;">
			</span>
		</div>
	</div>
</body>
</html>