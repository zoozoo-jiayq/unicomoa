<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<jsp:include page="../../common/taglibs.jsp" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>人员查看</title>
<link href="${ctx}flat/css/reset.css" rel="stylesheet" type="text/css" />
<link href="${ctx}flat/plugins/form/skins/form_default.css" rel="stylesheet" type="text/css" />
<style type="text/css">
.inputTable th{width:80px;}
</style>
<jsp:include page="../../common/taglibs.jsp" />
<script type="text/javascript">
	var basePath = "${ctx}";
	function closeWin(){
		art.dialog.close();
	}
</script>
</head>
<body class="bg_white">
<input id="type" type="hidden" value='${type}'/>
<div class="elasticFrame formPage">
	 <h2 class="small_title" style="padding-top:0">登录信息</h2>
	<table width="100%" cellspacing="0" cellpadding="0" border="0" class="inputTable">
		<tbody>
		<tr>
			<th><label>用户名：</label></th>
			<td>${user.loginName}</td>
		</tr>
        <tr>
			<th><label>选择角色：</label></th>
			<td>${roleNames}</td>
		</tr>
        <tr>
			<th><label>登录状态：</label></th>
			<td><s:if test="(#request.user.userState!=null && #request.user.userState==0)||(#request.user.userState==null)">
						启用 </s:if> 
				<s:if test="(#request.user.userState!=null&&#request.user.userState==1)">不启用</s:if></td>
		</tr>
		</tbody>
	</table>
    <h2 class="small_title">用户信息</h2>
	<table width="100%" border="0" cellpadding="0" cellspacing="0"  class="inputTable">
		<tr>
			<th><label>选择用户：</label></th>
			<td>${user.userName}</td>
		</tr>
        <tr>
			<th><label>性别：</label></th>
			<td><s:if test="(#request.user.sex!=null&&#request.user.sex==0)">女</s:if>
					      <s:if test="(#request.user.sex!=null&&#request.user.sex==1)">男</s:if></td>
		</tr>
        <tr>
			<th><label>单位/部门：</label></th>
			<td>${groupName}</td>
		</tr>
        <tr>
			<th><label>职务：</label></th>
			<td>${user.job}</td>
		</tr>
        <tr>
			<th><label>手机：</label></th>
			<td>${user.phone}</td>
		</tr>
        <tr>
			<th><label>电子邮件：</label></th>
			<td>${user.email}</td>
		</tr>
	</table>
</div>
</body>
</html>