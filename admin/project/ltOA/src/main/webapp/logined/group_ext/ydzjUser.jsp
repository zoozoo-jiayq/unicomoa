<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>人员列表</title>
<style type="text/css">
 .inputTable th{width:150px;}
</style>
<jsp:include page="../../common/flatHead.jsp" />
 <link href="${ctx }flat/css/reset.css" rel="stylesheet" type="text/css" />
<link href="${ctx }flat/css/main.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="${ctx}flat/plugins/tree/skins/tree_default.css" type="text/css"/>
<link href="${ctx }flat/plugins/datatable/skins/datatable_default.css" rel="stylesheet" type="text/css" />
<link href="${ctx }flat/plugins/Accormenus/skins/Accormenus_default.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx }flat/js/placeholder.js"></script>
<script type="text/javascript" src="${ctx }flat/js/base.js"></script>
 <link rel="stylesheet" href="${ctx}flat/plugins/tree/ztree/zTreeStyle/zTreeStyle.css" type="text/css"/>
 <script type="text/javascript" src="${ctx}plugins/tree/ztree/jquery.ztree.all-3.5.min.js"></script>
<script type="text/javascript" src="${ctx}js/common/treeNode.js"></script>
<script type="text/javascript" src="${ctx}js/logined/group_ext/ydzjUser.js"></script>
<style>
	.treered span {color:red;}
	.treegreen span {color:green;}
</style>
</head>
<body>
<input id="type" type="hidden" value="${paramValues.type[0]}" />
<div class="mainpage">
  <div class="leftMenu">
    <div class="service-menu">
      <h1>群组管理</h1>
      <div class="workes"><a onclick="addGroup()" href="javascript:void(0)"><em class="em1"></em>新增</a><a onclick="updateGroup()" href="javascript:void(0)"><em class="em2"></em>修改</a><a id="deleteGroupDiv" style="cursor:pointer;"><em class="em3"></em>删除</a></div>
      <div class="zTreeDemoBackground">
		<ul id="groupUserTree" class="ztree" style="margin-top:0px;width:auto">
		</ul>
      </div>
    </div>
  </div>
  <iframe class="iframeresize" id="page" name="page"  border="0" frameBorder="0" scrolling="auto" style="width: 100%; height:100%" ></iframe>
</div>
    
</body>
</html>