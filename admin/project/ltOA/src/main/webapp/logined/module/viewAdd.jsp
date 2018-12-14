<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>公告管理页面</title>
<jsp:include page="../../common/flatHead.jsp" />
<link href="${ctx}/flat/css/reset.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/flat/plugins/form/skins/form_default.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/flat/plugins/annex/skins/annex_default.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}plugins/datatable/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="${ctx}js/logined/module/viewAdd.js"></script>
<script type="text/javascript" language="javascript" src="${ctx}plugins/upload/jquery.uploadify.min.js" ></script>
</head>
<body>
<form action="" id="form1" name="form1">
<input type="hidden" name="icon" id="icon"/>
<div class="formPage">
  <div class="formbg">
    <div class="big_title">新增菜单</div>
    <div class="content_form">
      <table width="100%" border="0" cellpadding="0" cellspacing="0"  class="inputTable">
        <tr>
          <th><label>编号：</label></th>
          <td>XXXXXXXX&nbsp;&nbsp;<em class="tip">注：编号由系统自动生成</em></td>
          <th rowspan="3">图标：</th>
          <td rowspan="3">
                <dl class="myphoto">
                      <dt><em class="close"></em><img src="../../images/icon_mrsl.png" width="107" height="107" id="iconImage"/></dt>
                      <dd><h3><input id="file_upload" name="fileupload" type="file" multiple="true"/></h3></dd>
                      <dd><p>支持 .jpg .png格式图片，200K以内</p></dd>
                </dl>
          </td>
        </tr>
        <tr>
        	<th><em class="requireField">*</em><label>菜单名称：</label></th>
            <td><input id="name" name="name" class="formText" type="text" maxlength="20" valid="required" errmsg="module.module_name_not_null"/></td>
        </tr>
        <tr>
        	<th><em class="requireField">*</em><label>菜单代码：</label></th>
            <td><input id="code" name="code" class="formText" type="text" maxlength="20" valid="required" errmsg="module.module_code_not_null"/></td>
        </tr>
         <tr>
        	<th><em class="requireField">*</em><label>排序号：</label></th>
            <td colspan="3"><input id="orderList" name="orderList" class="formText" type="text" maxlength="50" valid="required" errmsg="module.module_order_not_null"/></td>
        </tr>
        <tr>
        	<th><label>页面路径：</label></th>
            <td colspan="3"><input id="url" name="url" class="formText" type="text" maxlength="250"/></td>
        </tr>
        <tr>
        	<th><label>状态：</label></th>
            <td colspan="3">
            	<label class="radio"><input type="radio" name="statue" value="1" checked="checked"/>启用</label>
            	<label class="radio"><input type="radio" name="statue" value="2"/>停用</label>
            </td>
        </tr>
      </table>
    </div>
   
  </div>
  <div class="buttonArea">
    <input type="button" class="formButton_green" value="保存" onclick="subModule();" />
    <input type="button" class="formButton_grey" value="取消" onclick="goback();"/>
  </div>
</div>
</form>
</body>
</html>
