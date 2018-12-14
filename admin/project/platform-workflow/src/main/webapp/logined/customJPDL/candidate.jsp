<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>编辑经办权限</title>
<jsp:include page="../../common/flatHeadNoChrome.jsp"/>
<link href="${ctx }flat/css/reset.css" rel="stylesheet" type="text/css" />
<link href="${ctx }flat/plugins/form/skins/form_default.css" rel="stylesheet" type="text/css" />
<link href="${ctx }flat/css/handle.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="${ctx}js/user/selectuser.js"></script>
    <script type="text/javascript" src="${ctx}js/common/hashmap.js"></script>
    <script type="text/javascript" src="${ctx}js/common/treeNode.js"></script>
    <script type="text/javascript" src="${ctx}/js/logined/customJPDL/candidate.js"></script>
    <style>.inputTable th{width:85px;}</style>
</head>
<body>
<form action="${ctx}/workflow/nodeManager!update.action" method="post">
    <input type="hidden" id="nodeId" name="node.id" value="${nodeId}"/>
    <input type="hidden" id="userIds" name="node.candidate" value="${node.candidate}"/>
    <input type="hidden" id="groupIds" name="node.depts" value="${node.depts}"/>
    <input type="hidden" id="roleIds" name="node.roles" value="${node.roles}"/>
    <input type="hidden" name="node.processAttribute.id" value="${node.processAttribute.id}"/>
    <input type="hidden" name="node.nodeName" value="${node.nodeName}" />
    <input type="hidden" name="node.writeableProperties" value="${node.writeableProperties}"/>
    <input type="hidden" name="node.secretProperties" value="${node.secretProperties}"/>
    <input type="hidden" name="node.nodeType" value="${node.nodeType}"/>
    <input type="hidden" name="node.descri" value="${node.descri}"/>
    <input type="hidden" name="node.nodeOrder" value="${node.nodeOrder}"/>
    <input type="hidden" name="processAttributeId" value="${node.processAttribute.id}"/>
    <input type="hidden" name="type" value="${type}"/>

<div class="formPage">
  <div class="formbg">
        <div class="big_title">步骤 :${node.nodeName } <i class="red ml10 f12">设置经办权限(经办权限为“部门”、“角色”、“人员”三者的合集)</i></div>
        <div class="content_form">
             <table cellpadding="0" cellspacing="0"  class="inputTable">
		              <tr>
				            <th ><label>授权范围：<br />（人员）</label></th>
				            <td><textarea name="textarea" cols="40" rows="3"    readonly="readonly" class="readOnlyTextarea" id="users"><c:forEach items="${userInfos}" var="u">${u.userName},</c:forEach></textarea><span class="addMember"><a class="icon_add" href="#" id="addUser">添加</a><a  class="icon_clear" href="#" id="removeUser" >清空</a></span></td>
				        </tr>
				        <tr>
				            <th><label>授权范围：<br />（部门）</label></th>
				            <td><textarea name="textarea" cols="40" rows="3"    readonly="readonly"  class="readOnlyTextarea" id="groups"><c:forEach items="${groupInfos}" var="g">${g.groupName},</c:forEach></textarea><span class="addMember"><a class="icon_add" href="#" id="addGroup">添加</a><a  class="icon_clear" href="#" id="removeGroup" >清空</a></span></td>
				        </tr>
				        <tr>
				            <th><label>授权范围：<br />（角色）</label></th>
				            <td><textarea name="textarea" cols="40" rows="3"     readonly="readonly"  class="readOnlyTextarea" id="roles"><c:forEach items="${roleInfos}" var="r">${r.roleName},</c:forEach></textarea><span class="addMember"><a class="icon_add" href="#" id="addRole">添加</a><a  class="icon_clear" href="#" id="removeRole" >清空</a></span></td>
				        </tr>
             </table>
        </div>
     </div>
      <div class="buttonArea"> 
			<input   type="submit"  class="formButton_green" value="确定" hidefocus=""/>
			<input type="button"  onclick="window.history.back(); return false;"  class="formButton_grey"  value="返回" hidefocus=""/>
        </div>
      </div>          
</form>
</body>
</html>