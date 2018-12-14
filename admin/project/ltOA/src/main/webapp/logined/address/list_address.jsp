<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>外部通讯录</title>
<jsp:include page="../../common/flatHead.jsp" />
<link href="${ctx}flat/css/reset.css" rel="stylesheet" type="text/css" />
<link href="${ctx}flat/css/main.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="${ctx}flat/plugins/tree/skins/tree_default.css" type="text/css"/>
<link href="${ctx}flat/plugins/datatable/skins/datatable_default.css" rel="stylesheet" type="text/css" />
<link href="${ctx}flat/plugins/Accormenus/skins/Accormenus_default.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}flat/js/placeholder.js"></script>
<script type="text/javascript" src="${ctx}flat/plugins/dialog/iframeTools.js"></script>
<script type="text/javascript" src="${ctx}flat/plugins/tree/skins/jquery.ztree.all-3.2.min.js"></script>
<script type="text/javascript" src="${ctx}flat/plugins/dialog/artDialog.js?skin=default"></script>
<script type="text/javascript" src="${ctx}flat/plugins/dialog/iframeTools.js"></script>
<script type="text/javascript" src="${ctx}flat/plugins/datatable/jquery.dataTables.min.js"></script>
<script type="text/javascript"
	src="${ctx}js/logined/address/list_address.js"></script>
<script type="text/javascript" src="${ctx}flat/plugins/datatable/selecedForDatatablePagination.js"></script>
<script type="text/javascript">
	var basePath = "${ctx}";
</script>
</head>
<body>
<input id="isInit" type="hidden" />
	<input id="publicSign" type="hidden"
		value="${paramValues.publicSign[0]}" />
<div class="list">
  <div class="searchArea">
    <table cellspacing="0" cellpadding="0">
      <tbody>
        <tr>
          <td ></td>
          <td class="right"><label>分组：</label>
            <select name="addressGroupId"
                    id="addressGroupId">
                        <option value='-1'>全部</option>
                  </select>
            <label>关键字：</label>
            <span class="position:relative;">
            <input type="text" style="width:200px" class="formText  searchkey" placeholder="姓名/手机号码" id="ncp" name=""/>
            </span>
            <input id="search"  type="button" class="searchButton" value="查询"/></td>
            </td>
        </tr>
      </tbody>
    </table>
  </div>
  <table id="publicTable" cellpadding="0" cellspacing="0"  class="pretty dataTable">
    <thead>
      <tr>
        <th style="width:180px;" id="groupName" class="longTxt"> 组名 </th>
        <th style="width:90px;" id="name">姓名</th>
        <th style="width:100px;" id="sex">性别</th>
        <th class="longTxt" id="companyName">单位/部门</th>
        <th style="width:100px;" id="postInfo">职务</th>
        <th style="width:120px;" id="officePhone">工作电话</th>
        <th style="width:120px;" id="familyMobileNo">手机号码</th>
        <th style="width:120px;" id="familyEmail">电子邮件</th>
        <th style="width:120px;" class="right_bdr0">操作</th>
      </tr>
    </thead>    
  </table>
</div>
<script>funPlaceholder(document.getElementById("ncp"));</script>
</body>
</html>
