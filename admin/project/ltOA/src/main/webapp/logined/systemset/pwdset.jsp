<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>密码设置</title>
<jsp:include page="../../common/flatHead.jsp" />
<link href="${ctx}flat/css/reset.css" rel="stylesheet" type="text/css" />
<link href="${ctx}flat/plugins/form/skins/form_default.css" rel="stylesheet" type="text/css" />
<link href="${ctx}flat/plugins/annex/skins/annex_default.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}js/logined/systemset/pwdset.js"></script>
<script type="text/javascript" src="${ctx}flat/js/placeholder.js"></script>
<style>
.inputTable th{ width:100px;}
.pla label{ line-height:19px;}
</style>
</head>
<body>
<form id="form1">
<input type="hidden" name="userId" id="userId" value="<s:property value='user.userId'/>"/>
<div class="formPage">
  <div class="formbg">
    <div class="big_title">密码修改</div>
    <div class="content_form">
      <table width="100%" border="0" cellpadding="0" cellspacing="0"  class="inputTable">
        <tr>
          <th><label>用户名：</label></th>
          <td><s:property value="user.loginName"/></td>
        </tr>
        <tr>
        	<th><label>原密码：</label></th>
            <td><span style="position:relation;" class="pla"><input name="input15" id="oldPass"  placeholder="请输入原密码"  valid="required" errmsg="sysset.sysset_oldPass_not_null" onkeyup="value=value.replace(/[^\w\.\*\@\#\%\^\&\(\)\?\/]/ig,'')"  onFocus="" onBlur="oldPassBlur();" type="password" class="formText" size="18" maxLength="20"/></span></td>
       </tr>
        <tr>
        	<th><label>新密码：</label></th>
            <td><span style="position:relation;" class="pla"><input name="input15" id="newPass"  placeholder="请输入新密码" onkeyup="value=value.replace(/[^\w\.\*\@\#\%\^\&\(\)\?\/]/ig,'')" valid="required|limit" errmsg="sysset.sysset_newPass_not_null|sysset.sysset_newPass_limit" min="6"  onFocus="newPassFocus();" type="password" class="formText" size="18" maxLength="20"/><em class="gray_9"></em></span></td>
        </tr>
        <tr>
        	<th><label>确认新密码：</label></th>
            <td><span style="position:relation;" class="pla"><input name="input15" id="newPass1" placeholder="请再次输入新密码"   valid="required|limit" onkeyup="value=value.replace(/[^\w\.\*\@\#\%\^\&\(\)\?\/]/ig,'')" errmsg="sysset.sysset_newPass1_not_null|sysset.sysset_newPass1_limit"  min="6" type="password" class="formText" size="18" maxLength="20" /><em class="gray_9"></em></span></td>
        </tr>
        <tr>
        	<th><label>上次修改时间：</label></th>
            <td><s:property value="time"/></td>
        </tr>
      </table>
    </div>
  </div>
  <div class="buttonArea">
    <input type="button" class="formButton_green" id="submitButton" value="保存" /><span class="blue"></span>
  </div>
</div>
</form>
<script>funPlaceholder(document.getElementById("oldPass"));</script>
<script>funPlaceholder(document.getElementById("newPass"));</script>
<script>funPlaceholder(document.getElementById("newPass1"));</script>

</body>
</html>
