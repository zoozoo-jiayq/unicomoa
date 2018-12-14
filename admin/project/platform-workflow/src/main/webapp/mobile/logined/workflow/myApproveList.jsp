<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
request.setAttribute("ctx", basePath);
%>
<!DOCTYPE html>
<html lang="zh-CN" manifest="<%=request.getContextPath() %>/mobile/workflow.manifest">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>我的审批</title>
    <script type="text/javascript">
		var basePath = '${ctx}';
	</script>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link type="text/css" rel="stylesheet" href="${ctx }mobile/plugins/bootstrap/css/bootstrap.min.css" />
    <link rel="stylesheet" type="text/css" href="${ctx }mobile/css/style.css"/>
    <link type="text/css" rel="stylesheet" href="${ctx }mobile/css/iscroll5.css" />
    <style type="text/css">
    	.scroll_wrapper{
    		top:45px !important;
    	}
    </style>
    <script type="text/javascript" src="${ctx }mobile/js/jquery-1.11.1.min.js"></script>
    <script type="text/javascript" src="${ctx }mobile/js/iscroll5.js"></script>
	<script type="text/javascript" src="${ctx }mobile/js/iscroll5-list.js"></script>
	<script type="text/javascript" src="${ctx}mobile/js/html5Adapter.js"></script>
	<script type="text/javascript" src="${ctx }mobile/js/logined/workflow/letterCode.js"></script>
	<script type="application/javascript" src="${ctx }mobile/js/logined/workflow/myApproveList.js"></script>
</head>
<body class="bgWhite">
<input type="hidden" id="flag" value="<%=request.getParameter("flag")%>"/>
<div class="container-fluid">
    <ul class="nav nav-tabs tab-ques">
          <li role="presentation" class="active"  id="tab1" onclick="showTab(this,'tab',2)">
          	<a href="javascript:void(0);">待我审批</a>
          </li>
          <li role="presentation" id="tab2" onclick="showTab(this,'tab',2)">
          	<a href="javascript:void(0);">我已审批</a>
          </li>
    </ul>
    <article class="tabContent" style="display:block;" id="tabCont1">
    	<div id="scroll_wrapper" class="scroll_wrapper">
			<div id="scroll_scroller" class="scroll_scroller">
				<div id="scroll_dataList" class="container-fluid">
				</div>
				<div id="scroll_up" class="scroll_up" ></div>
			</div>
		</div>
       
      <div class="media nodate" style="display:none">
            <div class="media-body">
               <div class="noDate">暂无数据</div>
            </div>
      </div>
    </article>
    <article class="tabContent" id="tabCont2" style="display:none">
    	<div id="scroll_wrapper_2" class="scroll_wrapper">
			<div id="scroll_scroller_2" class="scroll_scroller">
				<div id="scroll_dataList_2" class="container-fluid">
				</div>
				<div id="scroll_up_2" class="scroll_up"></div>
			</div>
		</div>
       <div class="media nodate_2" style="display:none">
            <div class="media-body">
               <div class="noDate">暂无数据</div>
            </div>
       </div>
    </article>
</div>  
    <script type="text/javascript" src="${ctx}mobile/plugins/bootstrap/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="${ctx}mobile/js/base.js"></script>

    <script type="text/javascript">
    (function(){
    	var flag = $("#flag").val();
    	if(flag == 'processed'){
    		$("#tab1").removeClass("active");
    		$("#tab2").addClass("active");
    		$("#tabCont1").hide();
    		$("#tabCont2").show();
    	}
    })();
	
    </script>
</body>
</html>
