<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>新建表单</title>
<jsp:include page="../../common/flatHeadNoChrome.jsp" />
<link href="${ctx }flat/css/reset.css" rel="stylesheet" type="text/css" />
<link href="${ctx }flat/plugins/form/skins/form_default.css" rel="stylesheet" type="text/css" />
<link href="${ctx }flat/plugins/annex/skins/annex_default.css" rel="stylesheet" type="text/css" />
<style type="text/css">
.inputTable th { width:75px;}
</style>

<script type="text/javascript" language="javascript" src="${ctx}js/logined/customForm/add.js" ></script>
</head>
<body>
<jsp:include page="shareFormCategory.jsp" />
<div class="formPage">
		<div class="formbg">
				<div class="big_title">新增表单</div>
				<form id="form1" action="">
				<div class="content_form">
						<table width="100%" border="0" cellpadding="0" cellspacing="0"  class="inputTable">
								<tr>
										<th><span class="requireField"></span><label>表单名称：
												</label></th>
										<td><input   maxlength="30" name="formName" id="formName" valid="required" errmsg="customForm.form_name_not_null"  type="text" class="formText" size="20"  /></td>
								</tr>
								<tr>
										<th><label>表单分类：</label></th>
										<td><select name="formType" id="formType"  class="formText iconTree" style="width:250px;">
												</select></td>
								</tr>
						</table>
				</div>
				</form>
		</div>
		<div class="buttonArea">
				<input type="button" id="addForm"  class="formButton_green" value="保存"  />
				<span class="blue">点击“保存”，提交表单信息</span>
		</div>
</div>
</body>
</html>
