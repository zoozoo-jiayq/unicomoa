<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>微讯记录</title>
<jsp:include page="../../common/flatHead.jsp" />
<link href="${ctx}/flat/css/reset.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/flat/css/main.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/flat/css/InstantMessage.css" rel="stylesheet" type="text/css" />
<link href="${ctx}flat/plugins/datatable/skins/datatable_page.css" rel="stylesheet" type="text/css" />
<link href="${ctx}flat/plugins/datatable/skins/datatable_default.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}flat/plugins/datatable/jquery.dataTables.min.js"></script>
<link href="${ctx}/flat/plugins/form/skins/form_default.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/flat/plugins/Accormenus/skins/Accormenus_default.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}/flat/js/base.js"></script>
<script type="text/javascript" src="${ctx}js/logined/message/list_user_message.js"></script>

</head>
<body>
	<div class="InMessageBox">
   	<div class="InMessagebg">
   	<input id="userId" type="hidden" value="${paramValues.userId[0]}" />
   	 <input id="userName" type="hidden" value="${paramValues.userName[0]}" />
   	 <h2 class="InMessageTit"><em></em>与<i id="titleText"></i>的消息记录</h2>
	 	<ul id="contents">
        	
        	
        </ul>      
        
	 </div>
	  <!-- 分页信息 -->
	  	<div id="pageFoot">
	  </div> 
	<div class="clear"></div>
  </div>
</body>
</html>