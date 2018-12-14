<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../../../common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>表单模板预览</title>
		 <jsp:include page="../../../common/flatHeadNoChrome.jsp"/>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<script type="text/javascript" src="${ctx}js/user/selectuser.js"></script>
    	<script type="text/javascript" src="${ctx}js/common/hashmap.js"></script>
    	<script type="text/javascript" src="${ctx}js/user/user.js"></script>
		<script type="text/javascript" src="${ctx}js/logined/customForm/formEvent.js"></script>
	</head>
	<style>
	.tableCenter table{margin:10px auto;}
	</style>
	
	<body >
	<div class="tableCenter" id="FORM_HTML">
	<script type="text/javascript">
	if(art.dialog.data("FORM_HTML")){
		$("#FORM_HTML").html(art.dialog.data("FORM_HTML"));
	}else
	</script>
	<s:property escape="false" value="#request.formContent" />
	 <div class="center" style="display:none">	<input type="button" value="关 闭" class="formButton" onClick="closeDialog();return false;" id="gb"/></div>
	</div>
	</body>
</html>