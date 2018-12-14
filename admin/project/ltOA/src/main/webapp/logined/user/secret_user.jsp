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
<jsp:include page="../../common/head.jsp" />
<script type="text/javascript">
	var basePath = "${ctx}";
</script>
<script type="text/javascript" src="${ctx}js/common/treeNode.js"></script>
<script type="text/javascript" src="${ctx}js/logined/user/secret_userTree.js"></script>
<script type="text/javascript" src="${ctx}js/logined/user/importuser.js"></script>
<script type="text/javascript" src="${ctx}js/common/ajaxfileupload.js"></script>
<script type="text/javascript" src="${ctx}js/common/validate_form.js"></script>
<script type="text/javascript" src="${ctx}js/common/showError.js"></script>
<script type="text/javascript">
    //openSelectTreeUser(zTreeOnCheckResult);
    function zTreeOnCheckResult(data)
    {
    	//类型
    	var type=data.getType();
    	var from = "&type=" + $("#type").val();
    	var source = "&source=tree";
    	if(type=="group"){
        	//组id
        	var groupId=data.Id;
        	if(groupId){
        		$("#page").attr("src",basePath+"logined/user/secret_userList.jsp?groupId="+groupId+from);
        	}
    	}else{
        	//人员id
        	var userId=data.Id;
        	if(userId){
        		$("#page").attr("src",basePath+"user/getUserById.action?userId="+userId+from+source);
        	}
    	}
    }
    $(document).ready(function() {
    	var type = "?type=" + $("#type").val();
    	//查询用户
    	$("#userSelect").click(function(){
    		
    		$("#page").attr("src",basePath+"logined/user/secret_userList.jsp"+type);
    		
    		refreshTree("gid_0");
    	});
    	//点击导入
    	$("#importUser").click(function(){
    		uploadUser();
    		return false;
    	});
    	
    	//$("#page").attr("src",basePath+"logined/user/userList.jsp"+type);
    	(function(){
    	    $("#page").attr("src",basePath+"logined/user/secret_userList.jsp"+type);
            
            refreshTree("gid_0");
    	})();
    });
    
    function refreshTree(id){
    	openSelectTreeUser(zTreeOnCheckResult, null, id);
    }
</script>
</head>
<body>
<input id="type" type="hidden" value="${paramValues.type[0]}" />
<div class="mainpage">
<!--左侧begin-->
		<div class="leftMenu">
				<table cellpadding="0" cellspacing="0" class="BlockTop">
						<tr>
								<td class="left"></td>
								<td class="center">组织结构</td>
								<td class="right"></td>
						</tr>
				</table>
				<c:if test="${paramValues.type[0]!='view'}">
				<div class="blockBox">
						<ul id="groupUserTree" class="ztree" style="margin-top:0px;width:auto">
						</ul>
                        <h2 id="userSelect" style="display:none">用户查询</h2>
						
				</div>
				<table cellpadding="0" cellspacing="0" class="BlockBot" style="display:none">
						<tr>
								<td class="left"></td>
								<td class="center"><h2 id="importUser">用户导入</h2></td>
								<td class="right"></td>
						</tr>
						</table>
				
				</c:if>
				<c:if test="${paramValues.type[0]=='view'}">
				<div class="blockBox">
						<ul id="groupUserTree" class="ztree" style="margin-top:0px;width:auto">
						</ul>
				</div>
				<table cellpadding="0" cellspacing="0" class="BlockBot"  style="display:none">
						<tr>
								<td class="left"></td>
								<td class="center"> <h2 id="userSelect">用户查询</h2></td>
								<td class="right"></td>
						</tr>
				</table></c:if>
		</div>
<!--左侧end-->
    <iframe class="iframeresize" id="page" name="page"  border="0" frameBorder="0" scrolling="auto" style="width: 100%; height:100%" ></iframe>
</div>
</body>
</html>