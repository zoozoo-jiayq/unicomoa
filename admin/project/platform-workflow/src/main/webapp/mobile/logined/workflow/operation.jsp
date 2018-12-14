<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<% 
	String operation = request.getParameter("operation");
	if(operation==null){
%>
<div class="buttonArea" id="dischargeBut" style="display:none">
            <button class="btn btn-primary" id="discharge" type="button"  onclick="javascript:window.location.href='${ctx}mobile/logined/workflow/discharge.jsp?_clientType=wap&instanceId=<%=request.getParameter("instanceId") %>'">撤销</button>
</div>
<%}else if(operation.equals("approve")){ %>
<div class="btn-group btn-group-justified navbar-fixed-bottom" role="group" aria-label="...">
           <div class="btn-group" role="group">
	            <button type="button" class="btn btn-default" id="noagree">
	                <img src="${ctx }mobile/images/btn-group1.png">
	            </button>
            </div>
            <div class="btn-group" role="group">
	            <button type="button" class="btn btn-default" id="agree">
	                <img src="${ctx }mobile/images/btn-group2.png">
	            </button>
            </div>
            <div class="btn-group" role="group">
	            <button type="button" class="btn btn-default" id="turn">
	                <img src="${ctx }mobile/images/btn-group3.png">
	            </button>
            </div>
 </div>
<%} else if(operation.equals("view")){}%>
<script type="text/javascript" src="${ctx }mobile/js/logined/workflow/operation.js"></script>