<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<jsp:include page="../../common/taglibs.jsp" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>人员列表</title>
<jsp:include page="../../common/flatHead.jsp" />
<link href="${ctx }flat/css/reset.css" rel="stylesheet" type="text/css" />
<link href="${ctx }flat/plugins/form/skins/form_default.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx }flat/plugins/dialog/artDialog.js?skin=blue"></script>
<script type="text/javascript" src="${ctx }flat/plugins/dialog/iframeTools.js"></script>
<script type="text/javascript" src="${ctx }flat/js/base.js"></script>
<script type="text/javascript" src="${ctx}js/logined/group_ext/groupAdd.js"></script>
<script type="text/javascript" src="${ctx}js/common/treeNode.js"></script>
<script type="text/javascript" src="${ctx}js/common/hashmap.js"></script>
<script type="text/javascript" src="${ctx}js/common/treeNode.js"></script>
<script type="text/javascript" src="${ctx}js/logined/group_ext/selectGroup.js"></script>
<style type="text/css">
.inputTable th{width:75px;}
</style>
</head>
<body class="bg_white">
<input type="hidden" id="groupType" value="${param.groupType}"/>
<div class="elasticFrame formPage">
<form action="#" id="groupForm">
  <table width="100%" cellspacing="0" cellpadding="0" border="0" class="inputTable">
    <tbody>
      <tr>
        <th><label>群组名称：</label></th>
        <td><input name="" type="text" class="formText" style="width:350px;" maxlength="30" id="groupName" 
						     onkeyup="this.value=this.value.replace(/[\\]/g,'')"
						      valid="required" errmsg="groupExt.group_name_not_null"/></td>
      </tr>
      <tr>
      	<th><label>排序字段：</label></th>
        <td><input name="input2" type="text" style="width:150px;" class="formText" maxlength="3" id="groupOrder" onkeyup="this.value=this.value.replace(/[^0-9]/g,'')" valid="required|isNumber" errmsg="groupExt.group_order_not_null|groupExt.group_order_format_error"/>
        <font class="gray_c ml10">正序排列，值越小排序越靠前</font></td>
      </tr>
        </tbody>
  </table>
<input value="保 存" type="submit" style="display:none" class="formButton_green" id="groupAdd"/>
</form>
</div>
</body>
</html>