<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>管理分组</title>
<jsp:include page="../../common/flatHead.jsp" />
<link href="${ctx }flat/css/reset.css" rel="stylesheet" type="text/css" />
<link href="${ctx }flat/css/main.css" rel="stylesheet" type="text/css" />
<link href="${ctx }flat/css/systemManagement.css" rel="stylesheet" type="text/css" />
<link href="${ctx }flat/plugins/datatable/skins/datatable_default.css" rel="stylesheet" type="text/css" />
<link href="${ctx }flat/plugins/Accormenus/skins/Accormenus_default.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="${ctx }flat/plugins/tree/skins/tree_default.css" type="text/css"/>
<script type="text/javascript" src="${ctx }flat/plugins/dialog/artDialog.js?skin=default"></script>
<script type="text/javascript" src="${ctx }flat/plugins/dialog/iframeTools.js"></script>
<script type="text/javascript" src="${ctx}plugins/datatable/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="${ctx}js/logined/user/importuserFather.js"></script>
<script type="text/javascript" src="${ctx}js/common/ajaxfileupload.js"></script>
<script type="text/javascript" src="${ctx}js/logined/publicaddress/import_address.js"></script>
<script type="text/javascript"	src="${ctx}js/logined/publicaddress/list_setupgroup.js"></script>

</head>
<body>
	<div class="list">
    <div class="searchArea">
      <table cellspacing="0" cellpadding="0">
        <tbody>
          <tr>
          	<td class="right">&nbsp;</td>
            <td style="width:120px;"><div class="fButton greenBtn"> <span id="addGroup" class="add">新增分组</span> </div>
              </td>
            <td class="right">
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <table cellpadding="0" cellspacing="0" id="myTable"  class="pretty dataTable">
    <thead>
      <tr>
        <th style="width:120px" id="name" class="longTxt">分组名称</th>
        <th style="width:50%;" id="userNames" class="longTxt">查看权人员</th>
        <th style="width:50%;" id="maintainUserNames" class="longTxt">修改权人员</th>
        <th style="width:200px;" class="oper right_bdr0">操作</th>
      </tr>
    </thead>
    </table>
  </div>
</body>
</html>