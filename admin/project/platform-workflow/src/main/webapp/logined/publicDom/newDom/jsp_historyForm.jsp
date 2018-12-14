<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%
 String pageType= request.getParameter("pageType")!=null? request.getParameter("pageType"):"";
 %>
<head>
	<jsp:include page="publicDomHead.jsp"></jsp:include>

<title>历史发文单</title>
</head>
<body >
<div id="showInfo"></div>

<input type="hidden" id="instanceId" value='<%=request.getParameter("historyDispatchInstanceId") %>' />
<input type="hidden" id="documentExtId" value='<%=request.getParameter("historyDispatchDocumentExtId")%>'/>
<input type="hidden" id="currentUserName"  value="<s:property value="#session.adminUser.userName"/>" />
<input type="hidden" id="currentUserId"  value="<s:property value="#session.adminUser.userId"/>" />
<input type="hidden" id="history" value="history"/>
<input type="hidden" id="printFlag" value="${print}"/>
 
       <div class="tabContent">
        <div class="mainpage mainpage_45">
          <!-- 闭合的菜单  -->
           
            <div class="openMenu">
               <h2>隐藏常用操作</h2>
               <ul>
                   <li><a href="#" onclick="doSaveForm();"  class="disabled"  >保存表单</a></li>
                   <li><a href="#" onclick="addYZFromEkey();" class="disabled" >插入签章</a></li>
               </ul>
               <h3>更多 ∨</h3>
               <ul class="more_cz">
                  <li><a href="#" class="disabled">页面设置</a></li>
                  <li><a href="#" class="disabled">保留痕迹</a></li>
                  <li><a href="#" class="disabled">不留痕迹</a></li>
                  <li><a href="#" class="disabled">显示痕迹</a></li>
                  <li><a href="#" class="disabled">隐藏痕迹</a></li>
                  <li><a href="#" class="disabled">印章验证</a></li>
                  <li><a href="#" class="disabled">获取文号</a></li>
                  <li><a href="#" class="disabled">操作历史</a></li>
                  <li><a href="#" class="disabled">套红模板</a></li>
                  <li><a href="#" id="printlink" onclick="doPrint();">打印</a></li>
               </ul>
            </div>
             <div class="closeMenu">
               <p><img src="${ctx }/flat/images/colse_hd.png">显示常用操作<img src="${ctx }/flat/images/down_hd.png"></p>
            </div>
            <div class="rightForm">
            <form id="customForm" ></form></div>
        </div>
    </div>

</body>
<script type="text/javascript" src="${ctx}/js/logined/publicDom/dispatch/formCommon.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	 $(".closeMenu").click(function(){
	     $(".mainpage").removeClass("mainpage_45");
	 });
	 $(".openMenu h2").click(function(){
	     $(".mainpage").addClass("mainpage_45");
	 });
	  $(".openMenu h3").click(function(){
		if($(".more_cz").is(":visible")){
	      $(this).next(".more_cz").slideToggle();
		}else{
		  $(this).next(".more_cz").slideToggle();
	    }
		
	 });
});
(function (argument) {
	// body...
	//获取表单模板数据
	var instanceId = $("#instanceId").val();
	$.get(basePath+"/dom/public!ajaxFormTemplate.action?instanceId="+encodeURI(instanceId)+"&r="+Math.random(),function(data) {
		if(data == 'delete'){
			$("#customForm").html("<font color='red'>该公文类型已删除或者该公文类型没有对应的表单!</font>");
		}else{
			$("#customForm").html(data);
			readonlyForm("customForm");

			//获取表单属性值
			$.get(basePath+"/dom/public!ajaxFormPropertyValue.action?instanceId="+encodeURI(instanceId)+"&r="+Math.random(),function(data) {
				// body...
				//设置表单值
				setFormData("customForm",data);
				initWidget(function(){
					
				});
			})
		}
	});
})();

</script>
<jsp:include page="jsp_ntko_sign.jsp"></jsp:include>
<jsp:include page="jsp_print.jsp"></jsp:include>
</html>