<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>权限设置</title>
<jsp:include page="../../common/flatHead.jsp" />
<link href="${ctx}flat/css/reset.css" rel="stylesheet" type="text/css" />
<link href="${ctx}flat/plugins/form/skins/form_default.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}js/logined/secret/secretPropertySet.js"></script>
<style type="text/css">
  label.radio{ margin-top:7px; float:left;}
  label.radio input{ margin:0;}
</style>
</head>
<body>
	<div class="formPage">
	  <div class="formbg">
	    <div class="big_title">保密字段权限设置</div>
	    <div class="content_form">
	             <table width="100%" border="0" cellpadding="0" cellspacing="0"  class="inputTable" id="myTable">
	                <s:iterator value="#request.propertyList" id="list" status="status">
	                	<s:if test="#status.index%3 == 0">
	                		<tr>
	                	</s:if>
	                		 <th><label><s:property value="value" />：</label></th>
	                  		 <td><label class="radio"><input type="checkbox" name="property" title="<s:property value="value" />" value="<s:property value="key" />"/></label></td>
	                	<s:if test="#status.index%3 == 2 || #status.isLast">
	                		</tr>
	                	</s:if>
	                </s:iterator>   
	             </table>
	    </div>
	  </div>
	  <div class="buttonArea">
	        <input type="button" onclick="javascript:sub();" class="formButton_green" value="保存"/>
	        <span class="blue">点击 “保存”，保存需要控制显示的保密字段</span>
	  </div>
	</div>
</body>
</html>