<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>单位通讯录</title>
<jsp:include page="../../common/flatHead.jsp" />
<link href="${ctx}/css2/reset.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/css2/d_index.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="${ctx}flat/plugins/tree/skins/tree_default.css" type="text/css"/>
<link href="${ctx}flat/plugins/datatable/skins/datatable_default.css" rel="stylesheet" type="text/css" />
<link href="${ctx}flat/plugins/Accormenus/skins/Accormenus_default.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}plugins/tree/ztree/jquery.ztree.all-3.5.min.js"></script>
<script type="text/javascript" src="${ctx}flat/plugins/datatable/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="${ctx}js/placeholder.js"></script>
<script type="text/javascript" src="${ctx}flat/js/base.js"></script>
<script type="text/javascript" src="${ctx}js/common/treeNode.js"></script>
<script type="text/javascript" src="${ctx}js/logined/group/userTree.js"></script>
<script type="text/javascript" src="${ctx}js/logined/authority/userList.js"></script>

</head>
<body>
<input name="groupId" type="hidden" id="groupId"/>
<div class="nav">
	<div class="navLeft">单位管理<span>></span>单位通讯录管理</div>
    <div class="navRight">
    	<label>关键字：</label>
            <span style="position:relative;">
            <input type="text" id="searchkey"  class="formText searchkey"   name="" placeholder="姓名/手机号码/V网短号" />
            </span>
            <input type="button" value="查询" class="searchButton" id="sure"/>
			 <a href="javascript:void();" class="import2" onclick="disp_alert4()">导入</a>
    <a href="javascript:void();" class="export2">导出</a>
    <a href="javascript:void();" class="batch_dzh2" onclick="disp_alert3()">批量发送客户端地址</a>
    <a href="#" class="synchro2">同步到客户端</a>
    </div>
</div>
<div class="mainpage">
  <div class="leftMenu">
    <div class="service-menu">
      <h1>部门管理</h1>
      <div class="workes" id="option"><a href="javascript:void()" onclick="disp_alert1()"><em class="em1"></em>新增</a><a href="javascript:void()" onclick="disp_alert2()"><em class="em2"></em>修改</a><a href=""><em class="em3"></em>删除</a></div>
      <div class="zTreeDemoBackground">
       <ul id="groupUserTree" class="ztree">
       </ul>
      </div>
    </div>
  </div>
  <div class="list">
  <div class="searchArea">
    <table cellspacing="0" cellpadding="0">
      <tbody>
        <tr>
          <td class="right">&nbsp;</td>
          <td style="width:318px;"><div class="fButton blueBtn">
                <div> <span class="add" onclick="window.location.href='d01单位管理-单位通讯录管理-新增.html';">新增</span> </div>
              </div>
              <div class="fButton orangeBtn"> <span class="delete" onclick="javascript:art.dialog.confirm('确定要删除吗？删除后不可恢复。', function(){}, function(){art.dialog.tips('你取消了操作');});">删除</span> </div>
              <div class="fButton blueBtn" onclick="disp_alert7();">
                <div> <span class="move">移动到部门</span> </div>
              </div>
              </td>
        </tr>
      </tbody>
    </table>
  </div>
  <div class="listTable">
  <table width="100%" class="pretty" cellspacing="0" cellpadding="0" id="userTable">
   
  </table>
  </div>
</div>
</div>
</body>
<script>
funPlaceholder(document.getElementById("searchkey"));
</script>
</html>
