<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>更新分组</title>
<%@ include file="../../common/taglibs.jsp"%>
<jsp:include page="../../common/head.jsp" />
<script type="text/javascript">
	var basePath = "${ctx}";
</script>
<script type="text/javascript"
	src="${ctx}/js/logined/address/update_group.js"></script>
<script type="text/javascript" src="${ctx}js/common/validate_form.js"></script>
<script type="text/javascript" src="${ctx}js/common/showError.js"></script>
</head>

<body>
	<input type="hidden" id="id"
		value="<s:property value='#request.addressGroup.id'/>" />
	<div class="input" style="position:relative;">
		<div class="pageTitle">
			<em class="iconEdit">修改分组</em>
		</div>
		<form id="form1">
			<table width="100%" border="0" cellpadding="0" cellspacing="0"
				class="inputTable">
				<tr>
					<th>排序号</th>
					<td><input id="orderNo" type="text" class="formText" size="30"
						maxlength="9"
						value="<s:property value='#request.addressGroup.orderNo'/>"
						onkeyup="this.value=this.value.replace(/[^0-9]/g,'')" />
					</td>
				</tr>
				<tr>
					<th><span class="requireField">*</span>分组名称</th>
					<td><input type="text" class="formText" size="30" id="name"
						value="<s:property value='#request.addressGroup.name' />"
						maxlength="30" valid="required" errmsg="addressbook.msg_groupName_not_null" /> </td>
				</tr>
			</table>
		</form>
		<div class="buttonArea"><input id="update" value="修 改" class="formButton_green"
				type="button" />&nbsp;&nbsp;<input
				id="back" value="返 回" type="button" class="formButton_grey"/>
		</div>
	</div>
</body>
</html>