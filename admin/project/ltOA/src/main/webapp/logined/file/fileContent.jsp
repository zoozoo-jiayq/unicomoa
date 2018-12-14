<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<jsp:include page="../../common/flatHead.jsp"/>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>资料管理</title>
<link href="${ctx}flat/css/reset.css" rel="stylesheet" type="text/css" />
<link href="${ctx}flat/css/main.css" rel="stylesheet" type="text/css" />
<link href="${ctx}flat/plugins/form/skins/form_default.css" rel="stylesheet" type="text/css" />
<link href="${ctx}flat/plugins/datatable/skins/datatable_default.css" rel="stylesheet" type="text/css" />
<link href="${ctx}flat/plugins/Accormenus/skins/Accormenus_default.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="${ctx}flat/plugins/tree/skins/tree_default.css" type="text/css"/>
 <link rel="stylesheet" type="text/css" href="${ctx}plugins/upload/uploadify.css"/>
<script type="text/javascript" src="${ctx}flat/js/base.js"></script>
 <script type="text/javascript" language="javascript" src="${ctx}flat/plugins/upload/jquery.uploadify.min.js" ></script>
<script type="text/javascript" src="${ctx}flat/js/placeholder.js"></script>
<script type="text/javascript" src="${ctx}plugins/tree/ztree/jquery.ztree.all-3.5.min.js"></script>
<script type="text/javascript" src="${ctx}js/common/ztree-wrapper.js"></script>
<script type="text/javascript" src="${ctx}plugins/datatable/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="${ctx}plugins/datatable/selecedForDatatablePagination.js"></script>
<style type="text/css">
.inputTable th{width:100px;}
</style>
 <script type="text/javascript" src="${ctx}js/logined/file/delFile.js"></script>
 <script type="text/javascript" src="${ctx}js/logined/file/checkFile.js"></script>
 <script type="text/javascript" src="${ctx}js/logined/file/downFile.js"></script>
 <script type="text/javascript" src="${ctx}js/logined/file/setfileContent.js?v=1.0"></script>
 <script type="text/javascript" src="${ctx}js/logined/file/upload.js"></script>
</head>
<body>
<input type="hidden" id="fileSortType" value="<%=request.getParameter("fileSort") %>" />
<input type="hidden" name="fileSortId" id="fileSortId" value="0"/>
<input type="hidden" name="path" id="path" value=""/>
<input type="hidden" name="parentId" id="parentId" value=""/>
<input type="hidden" name="sortNo" id="sortNo" value=""/>
<input type="hidden" name="sortName" id="sortName" value=""/>
<input  type="hidden"  id="fileOpType"  value="add"   />
<div class="mainpage"> 
	<!--左侧begin-->
	<div class="leftMenu">
	 <div class="service-menu">
		 <h1  id="wenjiangui">文件柜</h1>
	      <div class="workes"><a href="javascript:void()" id="addChildFileSort"><em class="em1"></em>新增</a><a href="javascript:void()" id="updateChildFileSortById"><em class="em2"></em>修改</a><a href="javascript:void()" id="deleteChildFileSortById"><em class="em3"></em>删除</a></div>
	      <div class="zTreeDemoBackground">
	        <ul id="fileContentTree" class="ztree">
	        </ul>
	      </div>
	</div>
	</div>
<iframe  class="iframeresize"  id="page" frameborder="0" scrolling="auto" border="0" id="page" name="page" onload="javascript:dyniframesize('page');" ></iframe>
</div>
</body>
<script type="text/javascript">
		var type="${param.type}";
	
		if(menuStyle != "default"){
			var mainpage = $("div.mainpage");
			mainpage.removeClass("mainpage").addClass("mainpage_r");
		}
</script>
</html>

 