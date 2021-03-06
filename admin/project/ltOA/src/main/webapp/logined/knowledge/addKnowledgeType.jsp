<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<jsp:include page="../../common/taglibs.jsp" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>新增知识库类型</title>
<jsp:include page="../../common/flatHead.jsp"/>
<link href="${ctx}flat/css/reset.css" rel="stylesheet" type="text/css" />
<link href="${ctx}flat/plugins/form/skins/form_default.css" rel="stylesheet" type="text/css" />
<style type="text/css">
.inputTable th{width:82px;}
</style>

<!-- 验证start -->
<script type="text/javascript" src="${ctx}js/common/showError.js?version=${version}"></script>
<!-- 验证end -->
<script type="text/javascript" src="${ctx}js/logined/knowledge/addKnowledgeType.js?version=${version}"></script>

</head>

<body class="bg_white">
<input type="hidden" id="isPrivate" value="${param.isPrivate}"/>
<input id="columnId" type="hidden" value="${param.columnId }"/>
<form id="addTypeName">
<div class="elasticFrame formPage">
  <table width="100%" cellspacing="0" cellpadding="0" border="0" class="inputTable">
    <tbody>
      <tr>
        <th><em class="requireField">*</em><label>分类名称：</label></th>
        <td><input id="typeName" name="Input32"  type="text" class="formText" maxlength="10"  valid="required" errmsg="knowledge.TypeName_not_null" /></td>
      </tr>
      <tr>
        <th><label>排序号：</label></th>
        <td>
         <input id="parentIds" type="hidden"  value="${param.parentId}"/>
        <input name="Input32" id="orderType" type="text" value=""  class="formText"  maxlength="3"  onkeyup="value=value.replace(/[^\d]/g,'')"/>
        </td>
      </tr>
    </tbody>
  </table>
</div>
</form>
</body>


</html>