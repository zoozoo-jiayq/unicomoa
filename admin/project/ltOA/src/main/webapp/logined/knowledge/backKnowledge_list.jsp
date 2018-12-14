<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ page language="java" import="java.util.*" pageEncoding="gb2312"%> 
<%@ page import="java.net.*" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>知识库</title>
<script type="text/javascript" >
var basePath='${ctx}';
</script>
<%@ include file="../../common/taglibs.jsp"%>
<jsp:include page="../../common/flatHead.jsp" />

<link href="${ctx}flat/css/reset.css" rel="stylesheet" type="text/css" />
<link href="${ctx}flat/css/main.css" rel="stylesheet" type="text/css" />
<link href="${ctx}flat/plugins/datatable/skins/datatable_default.css" rel="stylesheet" type="text/css" />
<link href="${ctx}flat/plugins/Accormenus/skins/Accormenus_default.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="${ctx}flat/plugins/tree/skins/tree_default.css" type="text/css"/>
<script type="text/javascript" src="${ctx}js/common/placeholder.js?version=${version}"></script>
<script type="text/javascript" src="${ctx}js/common/treeNode.js?version=${version}"></script>
<script type="text/javascript" src="${ctx}js/logined/knowledge/konwledgeTypeTree.js?version=${version}"></script>
<script type="text/javascript" src="${ctx}js/logined/knowledge/group.js?version=${version}"></script>
<!-- 打开上传html文件窗口 -->
<script type="text/javascript" src="${ctx}js/logined/knowledge/openUpload.js?version=${version}"></script>
<script type="text/javascript" src="${ctx}js/logined/knowledge/backKnowledge_list.js?version=${version}"></script>
<script type="text/javascript">
    openSelectTreeUser(zTreeOnCheckResult);
    function zTreeOnCheckResult(data)
    {
    	//类型
    	var name = data.name;
    	$("#typeName").val(name);
    	var typeId = data.id.substr(4);
    	$("#typeId").val(typeId);
    	$("#searchkey").val("");
    	$.removeTableCookie('SpryMedia_DataTables_knowlegeTable_backKnowledge_list.jsp');
    	getTable();
    	
    }
    
    function refreshTree(id){
    	openSelectTreeUser(zTreeOnCheckResult, null, id);
    }
</script>
</head>

<body>
<input type="hidden" id="isAdminInput" value="<%=(String)session.getAttribute("isAdmin") %>"/>
<input type="hidden" id="columnId" value="${param.columnId }"/>
<!-- 是否显示增删改 -->
<input type="hidden" id="numType" value="${param.type}"/>
<div class="mainpage">
  <div class="leftMenu">
    <div class="service-menu">
      <h1>分类列表</h1>
      <div class="workes" id="buttonType">
      <a href="javascript:void(0)" id="addType"><em class="em1"></em>新增</a><a href="javascript:void(0)" id="updateType" ><em class="em2"></em>修改</a><a href="javascript:void(0)" id="deleteType"><em class="em3"></em>删除</a>
      </div>
      <div class="zTreeDemoBackground blockBox" >
            <input id="parentId" type="hidden" name="" value="0"/>
            <input id="typeName" type="hidden" name="" value="0"/>
            <input id="typeId" type="hidden" name="" value="0"/>
            <input id="containAll" type="hidden" value="1" name="" />
            <input id="isBeforeOrAfter" type="hidden" value="0"/>
            <input id="auditSign" type="hidden" value="2"/> 
      		<input id="treeNodeId"  type="hidden"/>
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
           
            <td class="right">
              <label>关键字：</label>
              <span style="position:relative;">
            <input type="text" id="searchkey" class="formText searchkey" placeholder="标题/关键字"/>
            </span>
              <input type="button" class="searchButton" id="searchBtn" value="查询"/></td>
              <td style="width:92px;"><div id="add" class="fButton blueBtn">
                <div> <span class="add" >新增</span> </div>
              </div>
              </td>
              <td style="width:92px;display:none;">
              	<div id="file_upload" class="fButton blueBtn" onclick="onclickOpload();">
              		<div> <span class="upload" >上传</span> </div>
              	</div>
              </td>
          </tr>
        </tbody>
      </table>
    </div>
    <table id="knowlegeTable" width="100%" border="0" cellpadding="0" cellspacing="0" class="pretty">
          <thead>
          <tr>
          <th style="width:50px">序号</th>
          <th style="width:70%">标题</th>
          <th style="width:30%">关键字</th>
		  <th style="width:100px">录入人</th>
          <th style="width:110px">创建时间</th>
		  <th style="width:140px" class="right_bdr0">操作</th>
        </tr>
      </thead>
 
    </table>
    <div class="clear"></div>
  </div>
  
</div>

<script>
setTimeout(function(){
	funPlaceholder(document.getElementById("searchkey"));
}, 200);
</script>
</body>
</html>
