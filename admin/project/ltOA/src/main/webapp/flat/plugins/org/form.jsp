<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>选择人员</title>
<jsp:include page="../../../common/flatHead.jsp" />
<link href="${ctx}flat/css/reset.css" rel="stylesheet" type="text/css" />
	<link href="${ctx }flat/plugins/form/skins/form_default.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx }flat/js/valid.js"></script>
</head>
<body  class="bg_white" >
	<form id="form1" style="padding-top:100px;">
		<table style="width:600px;" align="center">
			<th style="width:200px;">
				key
			</th>
			<th style="width:400px;">
				value
			</th>
			<tr>
				<td>
					用户名
				</td>
				<td>
					<input type="hidden" id="username" name="username" valid="required|username" errmsg="user.user_loginName_not_null|user.user_loginName_format_error" maxlength="255"/>
				</td>
			</tr>
			<tr>
				<td>
					办公电话
				</td>
				<td>
					<input type="hidden" id="telphone" name="telphone" valid="telphone" errmsg="telphone" maxlength="255"/>
				</td>
			</tr>
			<tr>
				<td>
					placeholder
				</td>
				<td>
					<input type="text" id="placeholder" name="placeholder" placeholder="placeholder"  maxlength="255"/>
				</td>
			</tr>
		</table>
		<div align="center" style="padding-top:50px;">
			<input type="button" id="add" value="提交" />
		</div>
	</form>
<script type="text/javascript">
$("#add").click(function(){
	qytx.app.SimpleForm({
		id	:"form1",
		url:basePath + "/user/ajaxUserById.action?userId=1",
		validFn:[{
					url:basePath + "/user/ajaxUserById.action?userId=1",
					callback:function(data){
						qytx.app.valid.showObjError($("#placeholder"), "user.user_loginName_not_null");
						return false;
					}
					}],
		success:function(data){
			alert(data);
		}
	});
});
</script>
</body>
</html>