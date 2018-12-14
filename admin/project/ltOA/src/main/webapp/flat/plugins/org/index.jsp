<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>选择人员</title>
<jsp:include page="../../../common/flatHead.jsp" />
<link href="${ctx}flat/css/reset.css" rel="stylesheet" type="text/css" />
<link href="${ctx}flat/plugins/selectMember/skins/selectMember_default.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" href="${ctx}flat/plugins/peopleTree/skins/tree_default.css" type="text/css" />
<script type="text/javascript" src="${ctx}flat/js/placeholder.js"></script>

<script type="text/javascript" src="${ctx}flat/js/smallTabSelectTree.js"></script>
<script type="text/javascript" src="${ctx}flat/plugins/Accormenus/skins/jquery.Accormenus.js"></script>
</head>
<body  class="bg_white" >
<input type="button" value="点击" onclick="tanchu();"/>

<script type="text/javascript">
	function tanchu(){
		qytx.app.tree.alertUserTree({
			async:	true,
			callback:	function(data){
				alert(JSON.stringify(data));
			}
		});
	}
</script>
</body>
</html>