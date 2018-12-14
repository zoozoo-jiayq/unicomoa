<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<jsp:include page="../../common/taglibs.jsp" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>群组管理</title>
<jsp:include page="../../common/head.jsp" />
<script type="text/javascript">
	var basePath = "${ctx}";
</script>
<script type="text/javascript" src="${ctx}js/common/treeNode.js"></script>
<script type="text/javascript" src="${ctx}js/logined/group_ext/userTree.js"></script>
<script type="text/javascript" src="${ctx}js/logined/group_ext/group.js"></script>

<script type="text/javascript">
    
</script>
</head>
<body>
	<div class="mainpage">
		<!--左侧begin-->
		<div class="leftMenu">

			<table cellpadding="0" cellspacing="0" class="BlockTop">
				<tr>
					<td class="left"></td>
					<td class="center">
						群组管理
					</td>
					<td class="right"></td>
				</tr>
			</table>
			<div class="blockBox">
			<div class="file_top_list"  id="file_top_list">
                   <ul>
                      <li style="width:49%"><a href="#"><img src="${ctx}images/public/add.png"/><span id="addDiv">新增</span></a></li>
                      <li style="display:none"><a href="#"><img src="${ctx}images/public/setAccess.png"/><span id="updateChildFileSortById">编辑</span></a></li>
                      <li style="background:none; width:49%"><a href="#"><img src="${ctx}images/public/close.png"/><span id="deleteGroupDiv">删除</span></a></li>
                   </ul>
               </div>
				<ul id="groupUserTree" class="ztree" style="margin-top:0px;width:auto;overflow:auto">
                </ul>
			</div>
			      
		</div>
		<!--左侧end-->
    <iframe  class="iframeresize" id="page" name="page"  border="0" frameBorder="0" scrolling="auto" style="width: 100%; height:100%" ></iframe>
	</div>
</body>
</html>