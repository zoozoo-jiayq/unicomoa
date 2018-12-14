<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>导入通讯簿联系人</title>
<%@ include file="../../common/taglibs.jsp"%>
<jsp:include page="../../common/head.jsp" />
<script type="text/javascript">
	var basePath = "${ctx}";
</script>
<link rel="stylesheet" type="text/css"
	href="${ctx}plugins/upload/uploadify.css" />
<script type="text/javascript"
	src="${ctx}plugins/upload/jquery.uploadify.min.js"></script>
<script type="text/javascript"
	src="${ctx}js/logined/address/upload_import.js"></script>
<script type="text/javascript"
	src="${ctx}js/logined/address/import_address.js"></script>
</head>

<body>
	<input id="groupId" type="hidden" value="${paramValues.groupId[0]}" />
	<input id="groupName" type="hidden" value="${paramValues.groupName[0]}" />
	<input id="moduleName" type="hidden" value="addressbook" />
	<input id="excelPath" type="hidden" />
	<input id="errorFileName" type="hidden" />
	<div class="inputFile" style="width:auto;margin:10px;">
		<p class="pl20 right"><a href="${ctx}upload/downProjectFile.action?filePath=logined/address&fileName=通讯簿联系人.xls&filedisplayName=通讯簿联系人.xls">下载模板</a></p>
		<table width="100%" border="0" cellpadding="0" cellspacing="0"	class="inputTable">
		<tr id="importTr"><th style="width:160px;">请指定用于导入的Excel文件</th><td style="width:220px;"><input id="file_upload" name="fileupload" type="file"
					multiple="true" />
					<div id="queue">
					</div></td></tr>
		</table>	
			<p class="warning" style="line-height:30px">
							<span id="result" style="display: none;">
							    <em class="ml20">本次成功导入<i id="successNum">0</i>条信息</em>
								<em id="errorResult">111</em>
							</span>
						</p>
		<div class="buttonArea"><input
				hideFocus="" id="improtBtn" value="导 入" type="button" disabled="disabled" class="formButton" />&nbsp;&nbsp;<input hideFocus=""
				value="关 闭" type="submit"
				onclick="closeWindow()" class="formButton" />
		</div>
	</div>
</body>
</html>