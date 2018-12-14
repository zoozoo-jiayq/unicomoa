<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>部门管理</title>
<jsp:include page="../../common/flatHead.jsp" />
<script type="text/javascript">
	var basePath = "${ctx}";
</script>
<link href="${ctx }flat/css/reset.css" rel="stylesheet" type="text/css" />
<link href="${ctx }flat/css/main.css" rel="stylesheet" type="text/css" />
<link href="${ctx }flat/plugins/datatable/skins/datatable_default.css" rel="stylesheet" type="text/css" />
<link href="${ctx }flat/plugins/Accormenus/skins/Accormenus_default.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="${ctx }flat/plugins/tree/skins/tree_default.css" type="text/css"/>
<script type="text/javascript" src="${ctx }flat/js/placeholder.js"></script>
<script type="text/javascript" src="${ctx }flat/js/base.js"></script>
<script type="text/javascript" src="${ctx }flat/plugins/tree/skins/jquery.ztree.all-3.2.min.js"></script>
<script type="text/javascript" src="${ctx }flat/plugins/dialog/artDialog.js?skin=default"></script>
<script type="text/javascript" src="${ctx }flat/plugins/dialog/iframeTools.js"></script>
<script type="text/javascript" src="${ctx}js/common/treeNode.js"></script>
<script type="text/javascript" src="${ctx}js/logined/group/userTree.js"></script>
<script type="text/javascript" src="${ctx}js/logined/group/group.js"></script>

<script type="text/javascript">
    
</script>
<style type="text/css">
.service-menu .workes a{width:89px;text-align:center;}
</style>
</head>
<body>
<div class="mainpage">
  <div class="leftMenu">
    <div class="service-menu">
      <h1>部门管理</h1>
   	  <div class="workes"><a href="javascript:void(0)" target="page" id="addDiv"><em class="em1"></em>新增</a><a href="javascript:void(0)" id="deleteGroupDiv"><em class="em3"></em>删除</a></div>
      <div class="zTreeDemoBackground">
      	<ul id="groupUserTree" class="ztree" style="margin-top:0px;width:auto;overflow:auto"></ul>
      </div>
	 <!--  <table cellpadding="0" cellspacing="0" class="BlockBot" >
		<tr>
			<td class="left"></td>
			<td class="center"><h2 class="op" id="groupList">部门列表</h2></td>
			<td class="right"></td>
		</tr>
	  </table> -->
    </div>
  </div>
  <iframe frameborder="0" scrolling="auto" border="0" id="page" name="page" class="iframeresize"></iframe>
</div>
</body>
<script>
funPlaceholder(document.getElementById("searchWord"));
</script>
</html>