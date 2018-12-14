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
	src="${ctx}/js/logined/address/add_group.js"></script>
<script type="text/javascript" src="${ctx}js/common/validate_form.js"></script>
<script type="text/javascript" src="${ctx}js/common/showError.js"></script>
</head>

<body>

	<div class="input" style="position:relative;width:auto">
		<div class="pageTitle">
			<em class="iconAdd">新建分组</em>
		</div>
		<form id="form1">
			<table width="100%" border="0" cellpadding="0" cellspacing="0"
				class="inputTable">
				<tr>
					<th>排序号</th>
					<td><input id="orderNo" type="text" class="formText" size="30"
						maxlength="9"
						onkeyup="this.value=this.value.replace(/[^0-9]/g,'')" /></td>
						<th>分组名称</th>
					<td><input id="name" type="text" class="formText" size="30"
						valid="required" errmsg="addressbook.msg_groupName_not_null"  maxlength="30"/>
						<span class="requireField">*</span>
					</td>
				</tr>
			</table>
		</form>
		<div class="buttonArea"><input id="add" value="提 交" class="formButton_green"
				type="button" class="formButton"/> &nbsp;&nbsp; <input
				id="back" value="返 回" type="button" class="formButton_grey"/>
		</div>
	</div>
</body>
</html>