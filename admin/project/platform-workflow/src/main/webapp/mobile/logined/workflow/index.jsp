<%@page import="cn.com.qytx.platform.org.service.IUser"%>
<%@page import="cn.com.qytx.platform.org.domain.UserInfo" %>
<%@page import="cn.com.qytx.platform.base.application.SpringContextHolder"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="en" manifest="<%=request.getContextPath() %>/mobile/workflow.manifest">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>审批</title>
    <jsp:include page="workflowHead.jsp" />
    <link rel="stylesheet" type="text/css" href="${ctx}mobile/css/style.css"/>
    <script type="text/javascript">
		var basePath = '${ctx}';
	</script>
</head>
<body style="background:white;">
<div class="container-fluid">
   <div class="homePage">
       <div class="banner">
           <a class="applyBtn" href="${ctx}mobile/logined/workflow/myStartList.jsp?_clientType=wap">
               <h2>我的申请</h2>
           </a>
           <a class="approvalBtn" href="${ctx}mobile/logined/workflow/myApproveList.jsp?_clientType=wap">
               <div class="" id="approveRound"></div>
               <h2>我的审批</h2>
           </a>
       </div>
       <table class="table table-bordered">
           <tbody>
             <tr>
               <td>
                  <a href="${ctx }/mobile/logined/workflow/qingjia.jsp?_clientType=wap">
                     <div class="flowBox">
                        <span class="iconImg"></span>
                        <h2>请假</h2>
                     </div>
                  </a>
               </td>
               <td>
                  <a href="${ctx }/mobile/logined/workflow/baoxiao.jsp?_clientType=wap">
                     <div class="flowBox">
                        <span class="iconImg ico-1"></span>
                        <h2>报销</h2>
                     </div>
                  </a>
               </td>
               <td>
                   <a href="${ctx }/mobile/logined/workflow/chuchai.jsp?_clientType=wap">
                     <div class="flowBox">
                        <span class="iconImg ico-2"></span>
                        <h2>出差</h2>
                     </div>
                  </a>
               </td>
             </tr>
              <tr>
               <td>
                  <a href="${ctx }/mobile/logined/workflow/waichu.jsp?_clientType=wap">
                     <div class="flowBox">
                        <span class="iconImg ico-3"></span>
                        <h2>外出</h2>
                     </div>
                  </a>
               </td>
               <td>
                  <a href="${ctx }/mobile/logined/workflow/lingyong.jsp?_clientType=wap">
                     <div class="flowBox">
                        <span class="iconImg ico-4"></span>
                        <h2>物品领用</h2>
                     </div>
                  </a>
               </td>
               <td>
                   <a href="${ctx }/mobile/logined/workflow/tongyong.jsp?_clientType=wap">
                     <div class="flowBox">
                        <span class="iconImg ico-5"></span>
                        <h2>通用</h2>
                     </div>
                  </a>
               </td>
             </tr>
           </tbody>
       </table>
   </div>
</div>
<script type="text/javascript" src="${ctx}mobile/js/logined/workflow/index.js"></script>
<script>
	document.body.addEventListener('touchstart', function () { });
</script>
</body>
</html>