<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%String notifyId = request.getParameter("notifyId"); %>
<%String returnType = request.getParameter("returnType"); 
  String columnId = request.getParameter("columnId");
  request.setAttribute("columnId",columnId);
  request.setAttribute("returnType",returnType);
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>公告详情</title>
<%@ include file="../../../common/flatHead.jsp"%>
<style type="text/css">
	#myTable tr td{height:118px;}
	#myTable thead tr{height:0;}
	#myTable tr th{height:0;border:none;}
</style>
<script type="text/javascript" src="${ctx}plugins/datatable/jquery.dataTables.min.js"></script>
<script language="javascript" type="text/javascript" src="${ctx}plugins/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx}js/logined/column/view.js"></script>
<link href="${ctx}/flat/css/reset.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/flat/plugins/infoDetail/skins/infoDetail_default.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/flat/plugins/annex/skins/annex_default.css" rel="stylesheet" type="text/css" />
</head>
<body>
<input type="hidden" id="notifyId" value="<%=notifyId %>"/>
<input type="hidden" id="returnType" value="<%=returnType %>"/>
<input type="hidden" id="columnId" name="columnId" value="${columnId}">
<input type="hidden" id="category" value="${param.category} "/>
<div class="detailsPage">
	<div class="detailbg">
	<h2 id="subject"></h2>
    <h3><span id="publishDeptOther"></span><span id="createUserName"></span><span id="createTimeStr"></span><span  id="viewCount"></span></h3>
    <div class="detailsContent">
	    <p id="content"><p></p></p>
    </div>
  
    <div class="expFile" id="fileListDiv">
    	<h4 class="annex_title">附件:</h4>
 		<div class="annex">
    	<ul id="attachmentList"></ul>
    	<div class="clear"></div>
    	</div>
    </div>
    <div class="reviewBox" style="display:none;">
    	<dl class="reviewCon">
    	  <dt><em></em><div class="clear"></div><p></p></dt>
    	  <dd>
          	<h4><a class="fr" id="countComment" onclick="showComment();"></a>评论</h4>
          	<p><textarea class="formTextarea" id="comment" maxlength="1000" ></textarea></p>
            <p class="reviewButtonArea"><input id="subComment" class="reviewButton fr" value="提交" type="button" /></p>
          </dd>
    	</dl>
    	<div class="reviewList" style="display:none;">
        	<h4>最新评论</h4>
        	<table cellpadding="0" cellspacing="0"  class="pretty dataTable" id="myTable">
				<thead>
				</thead>
			</table>
        </div>
        </div>
        <p class="clear"></p>
    </div>
    <div class="buttonArea"> 
    <input type="button" onclick="javaScript:window.history.back();" class="formButton_grey" value="返回" />
    <s:if test="#request.returnType==1">
   		 <input id="forword" type="button" style="display: none" class="formButton_green" value="转发" onclick="transmit()"/>
    </s:if>
    </div>
</div>
</body>
</html>
