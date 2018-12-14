<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<% String roleId = request.getParameter("roleId");
	request.setAttribute("roleId",roleId);
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<jsp:include page="../../common/taglibs.jsp"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>权限维护</title>
<jsp:include page="../../common/flatHead.jsp"/>
<link href="${ctx}flat/css/reset.css" rel="stylesheet" type="text/css" />
<link href="${ctx}flat/css/authority.css" rel="stylesheet" type="text/css" />
<link href="${ctx}flat/css/reset.css" rel="stylesheet" type="text/css" />
<link href="${ctx}flat/css/authority.css" rel="stylesheet" type="text/css" />
<link href="${ctx}flat/css/handle.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}js/common/jquery-1.6.min.js"></script>
<script type="text/javascript" src="${ctx}flat/plugins/dialog/artDialog.js?skin=default"></script>
<script type="text/javascript" src="${ctx}flat/plugins/dialog/iframeTools.js"></script>
<style type="text/css">
	html,body{
		height:100%;
		overflow:hidden;
	}
	.tabContent{
		overflow:auto;
	}
</style>
<script type="text/javascript">
	var basePath="${ctx}";
	$(document).ready(function(){
		$(".tab ul li").click(function(){
			var index = $(this).index();
			if(!$(this).hasClass("current")){
				$(this).addClass("current").siblings().removeClass("current");
				$("body").find(".tabContent").hide();
				var currContent = $("body").find(".tabContent").eq(index);
				if(currContent.html()==null || currContent.html().trim()==""){
					var roleId = $("#roleId").val();
					currContent.html("<iframe width=\"100%\" height=\"100%\" frameborder=\"0\" scrolling=\"auto\" src=\""+basePath+"/module/mobileList.action?roleId="+roleId+"\"></iframe>");
				}
				currContent.show();
			}
		});
		var height = $("body").height();
		$(".tabContent").css("height",(height-58)+"px");
	});
</script>
</head>
<body>
 <input type="hidden" id="roleId" name="roleId" value="${roleId}"/>
 <div class="header_hd">
    <div class="operate">
           <div class="tab">
                <ul>
                  <li class="current"><a href="javascript:void(0);">电脑应用授权</a></li>
                  <li><a href="javascript:void(0);">手机应用授权</a></li>
                </ul>
          </div>
    </div>
 </div>
 <div class="tabContent"  style="overflow:hidden;">
 	<iframe width="100%" height="100%" frameborder="0" scrolling="auto" src="${ctx}authority/loadRoleModuleView.action?roleId=${roleId}">
 	</iframe>
 </div> 
 <div class="tabContent" style="display:none">
 	
 </div>
</body>
</html>