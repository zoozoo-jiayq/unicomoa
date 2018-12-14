<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="../../common/taglibs.jsp"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>按模块设置管理权限</title>
    <jsp:include page="../../common/head.jsp"/>
    <script type="text/javascript" src="${ctx}/js/common/hashmap.js"></script>
    <script type="text/javascript" src="${ctx}/js/common/treeNode.js"></script>
    <script type="text/javascript" src="${ctx}/js/user/selectuser.js"></script>
    <script type="text/javascript" src="${ctx}/js/logined/user/common_select_group_role.js"></script>
    <script type="text/javascript" src="${ctx}/js/logined/user/module_management_range.js"></script>
</head>
<body>
<div class="mainpage">
    <div class="leftMenu" style="top:5px">
        <table cellpadding="0" cellspacing="0" class="BlockTop">
            <tr>
                <td class="left"></td>
                <td class="center">模块名</td>
                <td class="right"></td>
            </tr>
        </table>
        <div class="blockBox">
            <ul class="menuList">
                <s:iterator value="#moduleNameList" var="moduleName">
                    <li><a href="javascript:void(0)">${moduleName}</a></li>
                </s:iterator>
            </ul>
        </div>
    </div>
<div class="input" style="width: auto">
    <div class="pageTitle">
        <em class="iconAdd">
            ${userName}&nbsp;-&nbsp;<span id="targetModuleName"></span>
        </em>
    </div>
    <table width="100%" border="0" cellpadding="0" cellspacing="1"
           class="inputTable">
        <tr>
            <th style="width: 80px">人员范围：</th>
            <td>
                <input id="userIds" value="" type="hidden"/>
                <textarea id="userNames" style="width: 400px" rows="4" readonly="readonly" class="formTextarea"></textarea>
                <a class="icon_add" href="javascript:void(0);" onclick="selectUser('userNames','userIds')">添加</a>
                <a class="icon_clear" href="javascript:void(0);" onclick="cleanSelector('userNames','userIds')">清空</a>
            </td>
        </tr>
        <tr>
            <th>角色范围：</th>
            <td>
                <input id="roleIds" value="" type="hidden"/>
                <textarea id="roleNames" style="width: 400px"  rows="4" readonly="readonly" class="formTextarea"></textarea>
                <a class="icon_add" href="javascript:void(0);" onclick="selectRole('roleNames','roleIds')">添加</a>
                <a class="icon_clear" href="javascript:void(0);" onclick="cleanSelector('roleNames','roleIds')">清空</a>
            </td>
        </tr>
        <tr>
            <th>部门范围：</th>
            <td>
                <input id="groupIds" value="" type="hidden"/>
                <textarea id="groupNames" style="width: 400px"  rows="4" readonly="readonly" class="formTextarea"></textarea>
                <a class="icon_add" href="javascript:void(0);" onclick="selectGroup('groupNames','groupIds')">添加</a>
                <a class="icon_clear" href="javascript:void(0);" onclick="cleanSelector('groupNames','groupIds')">清空</a>
            </td>
        </tr>
    </table>
    <div class="buttonArea">
        <span class="mainButton"><input value="保 存" type="button" onclick="saveModulePriv()"/></span>
        &nbsp;&nbsp;
        <span class="mainButton"><input value="关 闭" type="button" onclick="closeDialog()"/></span>
    </div>
 </div>
</div>
<input type="hidden" id="userId" value="${userId}"/>
</body>
</html>