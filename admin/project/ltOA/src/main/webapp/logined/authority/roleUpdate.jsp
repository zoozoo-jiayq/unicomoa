<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<jsp:include page="../../common/taglibs.jsp"/>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>修改</title>
<jsp:include page="../../common/flatHead.jsp"/>
<link href="${ctx}flat/css/reset.css" rel="stylesheet" type="text/css" />
<link href="${ctx}flat/plugins/form/skins/form_default.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
var basePath="${ctx}";
</script>
<script type="text/javascript" language="javascript" src="${ctx}js/logined/authority/roleUpdate.js"></script>
<style type="text/css">
.inputTable th{width:85px;}
</style>
</head>
<body class="bg_white">
<input type="hidden" name="roleId" id="roleId" value="<s:property value='#request.role.roleId' />"/>
	<div class="elasticFrame formPage">

		 <table style="display: none;" class="inputTable" cellspacing="0" cellpadding="0" border="0">
    	<tbody><tr>
        	<td class="left"></td><td class="center">修改角色：</td><td class="right"></td>
        </tr>
    </tbody></table>
		<form action="#" id="roleForm">
			<table width="100%" border="0" cellpadding="0" cellspacing="0"
				class="inputTable">
				<tr>
					<th><em class="requireField">*</em>角色名称：</th>
					<td><input name="roleName" id="roleName" type="text"
						class="formText" class="inpt" maxlength="20" size="30"
						valid="required" errmsg="role.role_roleName_not_null" value="<s:property value='#request.role.roleName' />"/> 
					</td>
				</tr>
				<tr style="display: none;">
					<th><em
						class="requireField">*</em>角色代码：</th>
					<td><input name="roleCode" id="roleCode" type="text"
						class="formText" class="inpt" maxlength="20"
						onkeyup="this.value=this.value.replace(/[^a-z]/g,'')" size="30"
						 errmsg="role.role_roleCode_not_null|role.role_roleCode_format_error" value="<s:property value='#request.role.roleCode' />"/><em class="gray_9">只能输入小写字母</em>
					</td>
				</tr>
				<tr>
					<th>角色说明：</th>
					<td><input name="roleMemo" id="roleMemo" type="text"
						class="formText" class="inpt" maxlength="100" size="30" value="<s:property value='#request.role.memo' />"/>
					</td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>