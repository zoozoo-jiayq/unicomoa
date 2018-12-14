<%--
功能: 报表管理-权限设置
版本:1.0
开发人员: CQL
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="../../common/taglibs.jsp"/>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>报表权限设置</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <jsp:include page="../../common/head.jsp"/>
    <script type="text/javascript">
        var basePath = "${ctx}";
    </script>
<script type="text/javascript" src="${ctx}js/user/selectuser.js"></script>
<script type="text/javascript" src="${ctx}js/common/hashmap.js"></script>
<script type="text/javascript" src="${ctx}js/user/user.js"></script>
    <script type="text/javascript" src="${ctx}/js/logined/report/reportSet.js"></script>
</head>
<body>
  <div class="input_new">
	<table class="listTitle" cellspacing="0" cellpadding="0" border="0">
	    	<tbody><tr>
	        	<td class="left"></td><td class="center"><label>报表授权</label></td><td class="right"></td>
	        </tr>
	</tbody>
	</table>
	
	<table width="100%" border="0" cellpadding="0" cellspacing="0"  class="inputTable">
		<tr>
				<th style="width:150px;"><label>报表类型 </label></th>
				<td> ${reportTypeName } </td>
		         <th style="width:150px;"><label>报表分类</label></th>
				<td>  ${reportCategoryName }</td>
		   </tr>
		<tr>
				<th style="width:150px;"><label>报表名称</label></th>
				<td colspan="3"> ${reportInfo.reportName }</td>
		</tr>
		 <tr>
        	<th>说明</th><td colspan="3">
        	<textarea id="memo" name="memo"   onkeyup="this.value = this.value.substring(0,500)"  cols="106" rows="4" class="formTextarea"><s:property value='#request.addressGroup.maintainGroupNames'/></textarea> 
        	 </td>
        </tr>
	    <tr>
        	<th>权限设置</th><td colspan="3">
        	<input id="userIds" name="userIds"
						value=" ${reportInfo.roles }" type="hidden" />
        	<textarea id="userNames" name="userNames" readonly="readonly" cols="106" rows="4" class="formTextarea"><s:property value='#request.addressGroup.maintainUserNames'/></textarea> 
        	<a class="icon_add" href="javascript:void(0);" onclick="selectPerson()">添加</a><a
						class="icon_clear"  href="javascript:void(0);" onclick="clearPerson()">清空</a></td>
        </tr>
	</table>
	<div class="buttonArea">
       <span class="mainButton"><input id="saveBtn" hideFocus="" value="确 定" type="formButton_green"/></span>
       &nbsp;&nbsp;
       <span class="mainButton"><input  id="cancleBtn" hideFocus="" value="取 消" type="button" /></span>
	</div>
</div>
      
</body>
</html>