<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http：//www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>管理权限设置-新增</title>
<link href="${ctx}css/author_main.css" rel="stylesheet" type="text/css" />
<jsp:include page="../../common/flatHead.jsp" />
<script type="text/javascript">
	var basePath = "${ctx}";
</script>
<link href="${ctx}flat/css/reset.css" rel="stylesheet" type="text/css" />
<link href="${ctx}flat/css/d_index.css" rel="stylesheet" type="text/css" />
<link href="${ctx}flat/plugins/form/skins/form_default.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" language="javascript" src="${ctx}js/logined/authority/userGroupPowerAdd.js"></script>
<script type="text/javascript" src="${ctx}js/user/selectuser.js"></script>
<style type="text/css">
.inputTable th {
	width: 72px;
}
</style>
</head>
<body>
<div class="nav">
	<div class="navLeft">单位管理<span>></span>管理范围设置<span>></span>新增授权</div>
</div>
<input type="hidden" id="userIds" name=userIds />
<input type="hidden" id="groupIds" name="groupIds"/>
<div class="formPage">
  <div class="formbg">
    <div class="big_title">新增授权</div>
    <div class="content_form">
      <table width="100%" cellspacing="0" cellpadding="0" border="0" class="inputTable">
        <tbody>
          <tr>
            <th><label>授权人员：</label></th>
            <td><textarea class="readOnlyTextarea" id="users"></textarea>
              <span class="addMember"><a  href="javascript:void(0)" class="icon_add" id="addUser">添加</a><a href="#" class="icon_clear" id="removeUser">清空</a></span></td>
          </tr>
          <tr>
            <th><label>授权范围：</label></th>
            <td><textarea class="readOnlyTextarea" id="groups"></textarea>
              <span class="addMember"><a  href="javascript:void(0)" class="icon_add" id="addGroup">添加</a><a href="#" class="icon_clear" id="removeGroup">清空</a></span></td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
  <div class="buttonArea">
    <input type="button" class="formButton_green" value="确定" hidefocus="" id="sure"/>
    <input type="button" value="取消" class="formButton_grey"  hidefocus="" id="cancle"/>
  </div>
</div>
</body>
</html>
