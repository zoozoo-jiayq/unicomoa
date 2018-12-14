<%@ page language="java" import="java.util.*"  contentType="text/html; charset=UTF-8" pageEncoding="utf-8"  %>
 <%@ page import="cn.com.qytx.platform.org.domain.UserInfo"%>
 <!-- 换肤 -->
 <% 
        UserInfo userInfoSkin = (UserInfo) session.getAttribute("adminUser");
	  	 Integer tempSkin = userInfoSkin.getSkinLogo();
	  	 Integer skin = 1;
	  	 if(tempSkin != null){
	  		 skin = Integer.parseInt((tempSkin+"").split("")[1]);
	  	 }
	%>
 <!-- 换肤加class -->
<% 
	if( skin == 1){ %>
 	<%}  else if(skin == 2){ %>
 	<script>
 		$("body").addClass("green_skin");
 	</script>
 	<%}  else if(skin == 3){ %>
 	<script>
 		$("body").addClass("blue_skin");
 	</script>
 <%} else if(skin == 4){ %>
 	<script>
 		$("body").addClass("brand_skin");
 	</script>
 <%} %>