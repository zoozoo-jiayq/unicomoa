<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>设置维护权限</title>
<%@ include file="../../common/taglibs.jsp"%>
<jsp:include page="../../common/head.jsp" />
<script type="text/javascript">
	var basePath = "${ctx}";
</script>
<script type="text/javascript"
	src="${ctx}js/logined/publicaddress/maintain_group.js"></script>
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
<div class="pageTitle"><em class="iconAdd">设置维护权限</em></div>
	<table width="100%" border="0" cellpadding="0" cellspacing="1"  class="inputTable">
    	<tr>
            <th>分组名称</th><td><input name="" type="text" class="formText" 
             readonly="readonly"
             disabled="disabled"
             value="<s:property value="#request.addressGroup.name" />" 
             size="30" /></td></tr>
            <tr>
        	<th>维护部门</th><td>
        	<input id="maintainGroupIds" name="maintainGroupIds"
						value="<s:property value='#request.addressGroup.maintainGroupIds'/>" type="hidden" />
        	<textarea id="maintainGroupNames" name="maintainGroupNames" readonly="readonly" cols="106" rows="4" class="formTextarea"><s:property value='#request.addressGroup.maintainGroupNames'/></textarea> 
        	<a class="icon_add" href="javascript:void(0);" onclick="selectGroup()">添加</a><a
						class="icon_clear" href="javascript:void(0);" onclick="clearGroup()">清空</a></td>
        </tr>
    	<tr>
        	<th>维护人员</th><td>
        	<input id="maintainUserIds" name="maintainUserIds"
						value="<s:property value='#request.addressGroup.maintainUserIds'/>" type="hidden" />
        	<textarea id="maintainUserNames" name="maintainUserNames" readonly="readonly" cols="106" rows="4" class="formTextarea"><s:property value='#request.addressGroup.maintainUserNames'/></textarea> 
        	<a class="icon_add" href="javascript:void(0);" onclick="selectPerson()">添加</a><a
						class="icon_clear"  href="javascript:void(0);" onclick="clearPerson()">清空</a></td>
        </tr>
    </table>     
 <div class="buttonArea">
    <span class="mainButton"><input id="update" hideFocus="" value="确 定" type="button"></span>
    &nbsp;&nbsp;
    <span class="mainButton"><input  id="closeBtn" hideFocus="" value="关 闭" type="button" ></span>
    </div>
</div>
</body>
</html>