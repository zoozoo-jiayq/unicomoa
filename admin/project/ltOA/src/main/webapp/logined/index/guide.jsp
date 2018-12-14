<%@ page language="java" import="java.util.*"  contentType="text/html; charset=UTF-8" pageEncoding="utf-8"  %>
 <%@ page import="cn.com.qytx.platform.org.domain.UserInfo"%>
<%
   UserInfo userInfoGuide = (UserInfo) session.getAttribute("adminUser");
	if(userInfoGuide != null && userInfoGuide.getLastLoginTime()==null){
%>

 <div class="guide">
     <span class="shade"></span>
     <span class="guide_box0"><em class="goin carry"></em> <em class="gb"></em></span>
     <span class="guide_box1" style="display:none;"><em class="close"></em> <em class="carry"></em></span>
     <span class="guide_box2" style="display:none;"><em class="close"></em> <em class="carry"></em></span>
     <span class="guide_box3" style="display:none;"><em class="close"></em> <em class="carry"></em></span>
     <span class="guide_box4" style="display:none;"><em class="close"></em><em class="carry"></em></span>
     <span class="guide_box5" style="display:none;"><em class="close"></em><em class="carry"></em></span>
     <span class="guide_box6" style="display:none;"><em class="close"></em><em class="carry"></em></span>
     <span class="guide_box7" style="display:none;"><em class="close"></em><em class="carry"></em></span>
     <span class="guide_box8" style="display:none;"><em class="close"></em><em class="carry"></em></span>
     <span class="guide_box9" style="display:none;"><em class="close"></em><em class="carry"></em></span>
     <span class="guide_box10" style="display:none;"><em class="close"></em><em class="carry ful"></em></span>
 </div>
   <script type="text/javascript">
	    $(document).ready(function(){
		  $(".carry").click(function(){
		     $(this).parent().fadeOut("slow").next().fadeIn("slow");
		  });
		  $(".ful , .close , .gb").click(function(){
		     $(".guide").fadeOut("slow");
		  });
		});
  </script>
   <%} %>