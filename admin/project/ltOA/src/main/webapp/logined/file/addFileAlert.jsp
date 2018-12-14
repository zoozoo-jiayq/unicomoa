<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>资料管理-新增弹框</title>
<jsp:include page="../../common/flatHead.jsp"/>
<link href="${ctx}flat/css/reset.css" rel="stylesheet" type="text/css" />
<link href="${ctx}flat/plugins/form/skins/form_default.css" rel="stylesheet" type="text/css" />
<style type="text/css">
.inputTable th{width:100px;}
</style>
</head>
<body class="bg_white">
<div class="elasticFrame formPage">
	<form id="form1">
            <table width="100%" cellspacing="0" cellpadding="0" border="0" class="inputTable">
               <tbody>
                      <tr>
                            <th><em class="requireField">*</em><label>文件夹名称：</label></th>
                            <td><input id="sortName" maxlength="15" type="text" class="formText" valid="required" errmsg="file.file_not_null"/></td>
                      </tr>
                      <tr>
                            <th><label>排序号：</label></th>
                            <td><input type="text"  onkeyup="this.value=this.value.replace(/[^0-9]/g,'')"  maxlength="10" id="sortNo" class="formText"/></td>
                      </tr>
              </tbody>
           </table>
     </form>
</div>
</body>
</html>