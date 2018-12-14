<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<!-- 打印控件 和印章控件-->
<jsp:include page="jsp_print.jsp"></jsp:include>
<jsp:include page="jsp_ntko_sign.jsp"></jsp:include>
<div id="showInfo"></div>
<!--查看已办接的任务-->
<% String history = (String)(request.getAttribute("history")==null?"":request.getAttribute("history")); %>

<input type="hidden" id="currentUserName"  value="${adminUser.userName }" />
<input type="hidden" id="currentUserId"  value="${adminUser.userId }" />
 <input type="hidden" id="signFlag" value="${adminUser.sinWidget}" />
<input type="hidden" id="printFlag" value="${adminUser.taoDa}"/>

        <div class="mainpage mainpage_45">
          <!-- 闭合的菜单  -->
           
            <div class="openMenu">
               <h2>隐藏常用操作</h2>
               <ul>
                   <li><a href="#" onclick="doSaveForm();"  <%if("history".equals(history)) {%>class="disabled" <%}%> >保存表单</a></li>
                   <li><a href="#" onclick="addYZFromEkey();" <%if("history".equals(history)) {%>class="disabled" <%}%>>插入签章</a></li>
               </ul>
               <h3>更多 ∨</h3>
               <ul class="more_cz">
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
 <script type="text/javascript">
 $(document).ready(function($) {
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
</script>       
<script type="text/javascript" src="${ctx}/js/logined/publicDom/dispatch/formCommon.js"></script>

