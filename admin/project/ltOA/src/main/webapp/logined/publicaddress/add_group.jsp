<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>新建群组</title>
<jsp:include page="../../common/flatHead.jsp" />
<link href="${ctx }flat/css/reset.css" rel="stylesheet" type="text/css" />
<link href="${ctx }flat/plugins/form/skins/form_default.css" rel="stylesheet" type="text/css" />
<link href="${ctx }flat/plugins/annex/skins/annex_default.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}js/common/jquery.lock.js"></script>
<script type="text/javascript" src="${ctx }flat/plugins/dialog/artDialog.js?skin=default"></script>
<script type="text/javascript" src="${ctx }flat/plugins/dialog/iframeTools.js"></script>
<script type="text/javascript"
	src="${ctx}js/logined/publicaddress/add_group.js"></script>
<script type="text/javascript" src="${ctx}flat/plugins/org/org.js"></script>
<script type="text/javascript" src="${ctx}js/common/hashmap.js"></script>
<script type="text/javascript" src="${ctx}js/user/user.js"></script>
</head>

<body>
<input type="hidden" id="groupId"
		value="<s:property value='#request.addressGroup.id'/>" />
<div class="formPage">
  <div class="formbg">
    <div class="big_title"><s:if test="id!=null">修改分组</s:if><s:else>新增分组</s:else></div>
    <div class="content_form">
    <form id="form1">
      <table width="100%" border="0" cellpadding="0" cellspacing="0"  class="inputTable">
        <tr>
          <th><label>排序号：</label></th>
          <td><input id="orderNo" name="" type="text" class="formText"
						 maxlength="9"
						value="<s:property value="#request.addressGroup.orderNo" />"
						onkeyup="this.value=this.value.replace(/[^0-9]/g,'')" /></td>
          <th><em class="requireField">*</em><label>分组名称：</label></th>
          <td><input id="name" name="" type="text" class="formText"
						valid="required" errmsg="addressbook.msg_groupName_not_null"
						value="<s:property value="#request.addressGroup.name" />"
						maxlength="30" /> </td>
        </tr>
				<tr style="display: none;">
					<th>范围（部门）</th>
					<td colspan="3">
					<input id="groupIds" name="groupIds"
						value="<s:property value='#request.userInfoId'/>" type="hidden" />
					<textarea id="groupNames"  name="textarea" readonly="readonly"
							class="readOnlyTextarea"></textarea> <a class="icon_add" href="javascript:void(0);" onclick="selectGroup()">添加</a><a
						class="icon_clear" href="javascript:void(0);" onclick="clearGroup()">清空</a>
					</td>
				</tr>
				<tr style="display: none;">
					<th>范围（角色）</th>
					<td colspan="3">
					<input id="roleIds" name="roleIds"
						value="<s:property value='#request.userInfoId'/>" type="hidden" />
						<textarea id="roleNames" name="textarea" readonly="readonly"
							class="readOnlyTextarea"></textarea> <a class="icon_add" href="javascript:void(0);" onclick="selectRole()">添加</a><a
						class="icon_clear" href="javascript:void(0);" onclick="clearRole()">清空</a>
					</td>
				</tr>
				<tr>
          <th><label>查看权人员：</label></th>
          <td colspan="3">
<input id="userIds" name="userIds"
						type="hidden" value="<s:property value='#request.dataPriv.userIds'/>" />
					<textarea id="userNames"   readonly="readonly"
							class="readOnlyTextarea"><s:property value='#request.dataPriv.userNames'/></textarea> 
          	<span class="addMember"><a class="icon_add" href="javascript:void(0);" onclick="selectPerson()">添加</a><a class="icon_clear" href="javascript:void(0);" onclick="clearPerson()">清空</a></span>
          </td>
        </tr>
        <tr>
          <th><label>修改权人员：</label></th>
          <td colspan="3">
	<input id="maintainUserIds" name="maintainUserIds" type="hidden" value="<s:property value='#request.addressGroup.maintainUserIds'/>" />
					<textarea id="maintainUserNames"   readonly="readonly"
							class="readOnlyTextarea"><s:property value='#request.addressGroup.maintainUserNames'/></textarea> 
          	<span class="addMember"><a class="icon_add"href="javascript:void(0);" onclick="selectUpdatePerson()">添加</a><a class="icon_clear" href="javascript:void(0);" onclick="clearUpdatePerson()">清空</a></span>
          </td>
        </tr>
      </table>
      </form>
    </div>
  </div>
  <div class="buttonArea">
    <input type="button" class="formButton_green" value="提交"  id="add"/>
    <input type="button" class="formButton_grey" value="返回" id="back"  onclick="javascript:window.history.go(-1);" />
  </div>
</div>
</body>
</html>