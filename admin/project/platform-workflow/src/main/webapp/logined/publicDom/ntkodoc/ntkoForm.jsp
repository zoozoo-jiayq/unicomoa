<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../../common/taglibs.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html  xmlns="http://www.w3.org/1999/xhtml">
<head>
<!--设置缓存-->
<meta http-equiv="cache-control" content="no-cache,must-revalidate"/>
<meta http-equiv="pragram" content="no-cache"/>
<meta http-equiv="expires" content="0"/>
<title>常用工作流程</title>
<jsp:include page="../../../common/head.jsp" />
<script type="text/javascript" src="${ctx}js/common/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="${ctx}js/logined/jbpmApp/map.js"></script> <!-- JS map 数据结构工具类 -->
<script type="text/javascript" src="${ctx}js/logined/customForm/formEvent.js"></script> <!-- 表单事件 -->
<script type="text/javascript" src="${ctx}js/logined/jbpmApp/formcheck.js"></script><!-- 流程表单 -->
<script type="text/javascript" src="${ctx}js/user/selectuser.js"></script>
<script type="text/javascript" src="${ctx}js/common/hashmap.js"></script>
<script type="text/javascript" src="${ctx}js/user/user.js"></script>
<script type="text/javascript" src="${ctx}js/logined/publicDom/dispatch/formCommon.js"></script> 
 <script type="text/javascript" src="${ctx}js/logined/publicDom/formAuthority.js"></script>
<script type="text/javascript" src="${ctx}js/logined/publicDom/ntkodoc/docForm.js"></script>
<script type="text/javascript" src="${ctx}js/logined/ntko/NtkoAddSecSign.js"></script>
<script type="text/javascript" src="${ctx}js/logined/publicDom/ntkodoc/addSign.js"></script>
<script type="text/javascript">
           var attatchmap = new Map();
            window.parent.frameResize1();
</script>
<style>.input {margin-left:10px;}</style>
</head>
<body onload="initOcx();" style="height:1000px"  >
	<input type="hidden" id="taskIds" value="${taskIds}" />
  <!-- 产生控件 -->
  <script src="${ctx}js/logined/ntko/NtkoGenObj.js"></script>
   <div style="align:left" class="input" id="form">  
                     ${formSource}
	</div>
	<div class="mt20"  style="padding-left:20px">
	<form id="myform" method="post"  action="${ctx}ntko/webSign_saveFile.action"  enctype="multipart/form-data">
	  <input type="hidden" id="documentExtId" name="documentExtId" value="${documentExtId}"/>
	  <c:if test="${webSignList!=null}">
	       <label>选择印章：</label>
				<select id="webSign"  name="categoryId">
				<option    value="" >请选择印章</option>
					<c:forEach items="${webSignList}" var="webSign">
							<option value="${webSign.url}">${webSign.name}</option>
					</c:forEach>
				</select>
		<input class="formButton" onclick="addYZ();" type="button" value="盖章"/>
	  </c:if>
	    </form>
	</div>
 </body>
		
		</html>