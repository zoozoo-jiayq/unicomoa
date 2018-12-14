<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>查看日程</title>
<jsp:include page="../../common/flatHead.jsp"/>
<link href="${ctx}/flat/css/reset.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/flat/plugins/form/skins/form_default.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}/js/logined/dict/addDict.js?version=${version}"></script>
<script type="text/javascript" src="${ctx}logined/appraisal/plugins/dialog/artDialog4.1.6/artDialog.js?skin=blue"></script> 
<script type="text/javascript" src="${ctx}logined/appraisal/plugins/dialog/artDialog4.1.6/iframeTools.js"></script>
<style type="text/css">
.inputTable th{width:85px;}
</style>
</head>
<body class="bg_white">
<input type="hidden" id="infoType"  value="${param.infoType}"/>
<div class="elasticFrame formPage">
  <table width="100%" cellspacing="0" cellpadding="0" border="0" class="inputTable">
    <tbody>
      <tr>
        <th><label>名称：</label></th>
        <td><input type="text" class="formText" id="typeName" value="${param.typeName1}" maxlength="8" /></td>
      </tr>
       <tr>
        <th><label>排序号：</label></th>
        <td><input type="text" id="infoOrder"  onkeyup="this.value=this.value.replace(/[^0-9]/g,'')" class="formText"  value="${param.typeOrder}"  maxlength="3"   /></td>
      </tr>
    </tbody>
  </table>
</div>
</body>
</html>