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
<script type="text/javascript" language="javascript" src="${ctx}js/logined/customForm/editFormCategory.js" ></script>
<style type="text/css">
.inputTable th{width:68px;}
</style>
</head>
<body class="bg_white">
<!-- 隐藏域 -->
<input name="formCategoryId" id="formCategoryId" type="hidden" value="<s:property value="#request.fc.categoryId" />"/>
<input name="oldFormCategoryName" id="oldFormCategoryName" type="hidden" value="<s:property value="#request.fc.categoryName" />"/>
<div class="elasticFrame formPage">
<form action="#" id="formCategoryform">
            <table width="100%" cellspacing="0" cellpadding="0" border="0" class="inputTable">
               <tbody>
                      <tr>
                            <th><label>排序号：</label></th>
                            <td><input onkeyup="testNum(this);return false;" valid="required" errmsg="formcategory.cate_order_not_null" maxlength="2" name="formCategoryIndex" id="formCategoryIndex" type="text" class="formText" value="<s:property value="#request.fc.orderIndex" />"/></td>
                      </tr>
                      <tr>
                            <th><label>名称：</label></th>
                            <td><input name="formCategoryName" id="formCategoryName" valid="required" errmsg="formcategory.cate_name_not_null" maxlength="20" class="formText" size="30" value="<s:property value="#request.fc.categoryName" />"/></td>
                      </tr>
              </tbody>
           </table>
           </form>
 <input name="saveCategory" style="display:none" id="editCategory" value="确 定" type="button" class="formButton_green"   onclick="saveCategory()"/>
</div>
</body>
</html>
