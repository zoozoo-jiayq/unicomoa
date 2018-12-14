<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>修改表单</title>
 <jsp:include page="../../common/flatHeadNoChrome.jsp"/>
<link href="${ctx }flat/css/reset.css" rel="stylesheet" type="text/css" />
<link href="${ctx }flat/plugins/form/skins/form_default.css" rel="stylesheet" type="text/css" />
<link href="${ctx }flat/plugins/annex/skins/annex_default.css" rel="stylesheet" type="text/css" />
<style type="text/css">
.inputTable th { width:75px;}
.txt_1 { font-size:14px; color:#555555; margin-right:5px;}
.txt_1 a { margin-right:7px;}
</style>
<script type="text/javascript">
	var formType='<s:property value="#request.fa.categoryId" />';
</script>
<script type="text/javascript" language="javascript" src="${ctx}js/logined/customForm/edit.js" ></script>
</head>
<body>
<jsp:include page="shareFormCategory.jsp" />
<!-- 隐藏域 -->
<input name="formId" id="formId" type="hidden" value="<s:property value="#request.fa.formId" />"/>
<input name="oldFormName" id="oldFormName" type="hidden" value="<s:property value="#request.fa.formName" />"/>

<div class="formPage">
		<div class="formbg">
				<div class="big_title">修改表单名称<span class="fr txt_1">相关操作：
						<a href="${ctx }logined/customForm/html_editor/editor.jsp?formId=${fa.formId}" class="view_url" target="_blank">表单设计</a>
		                <a href="javascript:void(0)" onclick="viewForm(${fa.formId});return false;" class="view_url">预览</a>
		                <a href="javascript:void(0)" onclick="importForm(${fa.formId});return false;"  class="view_url">导入</a> 
		                <a href="${ctx }workflowForm/reportForm.action?formId=${fa.formId}">导出</a>
		                <a href="javascript:void(0)" onclick="deleteForm('${fa.formId}');return false;">删除</a>
                </span></div>
				<div class="content_form">
				<form action="" id="form1">
						<table width="100%" border="0" cellpadding="0" cellspacing="0"  class="inputTable">
								<tr>
										<th><span class="requireField"></span><label>表单名称：
												</label></th>
										<td><input  name="formName" id="formName" valid="required" errmsg="customForm.form_name_not_null"  value="<s:property value="#request.fa.formName" />"  type="text" class="formText" size="107" maxlength="30" /></td>
								</tr>
								<tr>
										<th><label>表单分类：</label></th>
										<td><select  name="formType" id="formType"   style="width:250px;">
												</select></td>
								</tr>
						</table>
						</form>
				</div>
		</div>
		<div class="buttonArea">
				 <input type="button"  name="saveForm" id="saveForm"  class="formButton_green" value="确定" />
    <input type="button" class="formButton_grey" name="returnList" id="returnList"   value="返回" />
		</div>
</div>
 
</body>
</html>
