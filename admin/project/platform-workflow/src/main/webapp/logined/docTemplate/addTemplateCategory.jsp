<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>新增表单分类</title>
<jsp:include page="../../common/flatHead.jsp"></jsp:include>
<link href="${ctx}/flat/css/reset.css" rel="stylesheet" type="text/css" />
<link href="${ctx}flat/plugins/form/skins/form_default.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" language="javascript" src="${ctx}js/logined/docTemplate/addDocCategory.js" ></script>
<style type="text/css">
.inputTable th{width:60px;}
</style>
</head>
<body class="bg_white">
<div class="elasticFrame formPage" >
		<table width="100%" cellspacing="0" cellpadding="0" border="0" class="inputTable">
			<tbody>
				<tr>
						<th><label>排序号：</label></th>
						<td><input onkeyup="testNum(this);return false;" type="text" class="formText" maxlength="2" size="5" id="formCategoryIndex" name="formCategoryIndex"/>
								</td>
				</tr>
				<tr>
						<th><label>名称：</label></th>
						<td><input name="input" type="text" class="formText" maxlength="20" size="50" id="formCategoryName" name="formCategoryName"/></td>
				</tr>		
			</tbody>
		</table>
		<div class="buttonArea" style="display:none">
				<input hideFocus="" value="保 存" type="button" id="addCategory" name="addCategory" class="formButton_green" style="display: none"/>
				&nbsp;&nbsp;
				<input hideFocus=""  value="返 回" type="button" id="goback" name="goback" class="formButton_grey" style="display: none"/>
		</div>
</div>
</body>
</html>
