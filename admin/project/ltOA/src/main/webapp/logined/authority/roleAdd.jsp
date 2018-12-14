<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<jsp:include page="../../common/taglibs.jsp" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>新增</title>
<jsp:include page="../../common/flatHead.jsp" />
<link href="${ctx}flat/css/reset.css" rel="stylesheet" type="text/css" />
<link href="${ctx}flat/plugins/form/skins/form_default.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
	var basePath = "${ctx}";
</script>
<script type="text/javascript" language="javascript"
	src="${ctx}js/logined/authority/roleAdd.js"></script>
<style type="text/css">
.inputTable th{width:85px;}
</style>
</head>
<body class="bg_white">
	<div class="elasticFrame formPage">
		<form action="#" id="roleForm">
			<table width="100%" border="0" cellpadding="0" cellspacing="0"
				class="inputTable">
				<tr>
					<th><em class="requireField">*</em>角色名称：</th>
					<td><input name="roleName" id="roleName" type="text" 
						class="formText" class="inpt"  style="width:80%"
						maxlength="20"
						valid="required" errmsg="role.role_roleName_not_null" />
					</td>
				</tr>
				<tr  style="display: none;">
					<th><em
						class="requireField">*</em>角色代码：</th>
					<td><input name="roleCode" id="roleCode" type="text"
					    maxlength="20" value="default"
						class="formText" class="inpt" 	onkeyup="this.value=this.value.replace(/[^a-z]/g,'')" style="width:50%"
						valid="required|isEnglish" errmsg="role.role_roleCode_not_null|role.role_roleCode_format_error" /><em class="gray_9">只能输入小写字母</em>
					</td>
				</tr>
				<tr>
					<th>角色说明：</th>
					<td><input name="roleMemo" id="roleMemo" type="text" maxlength="50"
						class="formText" class="inpt"  style="width:80%" />
					</td>
				</tr>
			</table>
		</form>
</div>
</body>
</html>