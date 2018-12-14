<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../../common/taglibs.jsp" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>人员列表</title>
<style type="text/css">
 .inputTable th{width:150px;}
</style>
<jsp:include page="../../common/flatHead.jsp" />
<script type="text/javascript">
	var basePath = "${ctx}";
</script>
<link href="${ctx}flat/css/reset.css" rel="stylesheet" type="text/css" />
<link href="${ctx}flat/css/main.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="${ctx}flat/plugins/tree/skins/tree_default.css" type="text/css"/>
<link href="${ctx}flat/plugins/Accormenus/skins/Accormenus_default.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}js/logined/user/importuserFather.js"></script>
<script type="text/javascript" src="${ctx}js/common/ajaxfileupload.js"></script>
<script type="text/javascript" src="${ctx}flat/plugins/dialog/iframeTools.js"></script>
<script type="text/javascript" src="${ctx}flat/js/base.js"></script>
<script type="text/javascript">
    function zTreeOnCheckResult(nodes){
    	if(nodes && nodes.length>0){
    		var node = nodes[0];
    		var from = "&type=" + $("#type").val();
        	var source = "&source=tree";
        	if(node.id.indexOf("gid_") == 0){
        		//组id
            	var groupId=node.id.substr(4);
            	if(groupId){
            		$("#page").attr("src",basePath+"logined/user/userList.jsp?groupId="+groupId+from);
            	}
        	}else{
        		//人员id
            	var userId=node.id.substr(4);
            	if(userId){
            		$("#page").attr("src",basePath+"user/getUserById.action?userId="+userId+from+source);
            	}
        	}
        	
    	}
    }
    $(document).ready(function() {
    	var type = "?type=" + $("#type").val();
    	//查询用户
    	$("#userSelect").click(function(){
    		
    		$("#page").attr("src",basePath+"logined/user/userSelect.jsp"+type);
    		
    		refreshTree("gid_0");
    	});
    	//点击导入
    	$("#importUser").click(function(){
    		uploadUser();
    		return false;
    	});
    	
    	//$("#page").attr("src",basePath+"logined/user/userList.jsp"+type);
    	(function(){
    	    $("#page").attr("src",basePath+"logined/user/userList.jsp"+type);
            
            refreshTree("gid_0");
    	})();
    });
    
    function refreshTree(id){
    	qytx.app.tree.user({
    		id	:	"groupUserTree",
    		click:	zTreeOnCheckResult,
    		loadComplete:function(){
    			var zTreeObject = $.fn.zTree.getZTreeObj("groupUserTree");
    			var node = zTreeObject.getNodeByParam("id", id, null);
    			zTreeObject.selectNode(node);
    			var arr = [];
    			arr.push(node);
    			zTreeOnCheckResult(arr);
    		}
    	});
    }
</script>
</head>
<body>
<input id="type" type="hidden" value="${paramValues.type[0]}" />
<div class="mainpage">
<!--左侧begin--><%--

				
			<c:if test="${paramValues.type[0]=='view'}">
				--%><div class="leftMenu">
					<div class="service-menu">
						<h1>组织结构</h1>
						<div class="zTreeDemoBackground">
							<ul id="groupUserTree" class="ztree">
							</ul>
						</div>
					</div>
				</div><%--

			</c:if>
		
--%><!--左侧end-->
    <iframe frameborder="0" scrolling="auto" border="0" id="page" name="page" class="iframeresize" ></iframe>
</div>
</body>
<script type="text/javascript">
		if(menuStyle == "left"){
			var mainpage = $("div.mainpage");
			mainpage.removeClass("mainpage").addClass("mainpage_r");
		}
</script>
</html>