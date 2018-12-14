<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ include file="../../common/taglibs.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@ include file="../../common/head.jsp"%>
<jsp:include page="../../common/flatHeadNoChrome.jsp"/>
</head>
<body>
<div class="list">
	<div class="pageTitle"><em class="iconEdit">签批意见</em></div>
	<table width="100%" border="0" cellpadding="0" cellspacing="0"  class="inputTable" >
        <tr>
        <th><span class="requireField"> *</span><label>签批意见：</label></th>
	            <td class="td_warning" >
	                 <input id="contentInfoInput" type="hidden"/>
				     <script id="contentInfo" type="text/plain" style="width:100%"></script>
	            </td>
	      </tr>
	</table>
</div>
<script>
	ue = UE.getEditor('contentInfo', {
		initialFrameWidth : "100%"
	});
</script>
</body>
</html>
