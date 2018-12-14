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
		if( skin == 1){
	%>
  	 <link  href="" rel="stylesheet" type="text/css" />
  	 <script type="text/javascript" src="${ctx }flat/plugins/dialog/artDialog.js?skin=default"></script>
 	<%}  else if(skin == 2){ %>
  	<link href="${ctx}flat/skins/green/green.css" rel="stylesheet" type="text/css" />
  	<script type="text/javascript" src="${ctx }flat/plugins/dialog/artDialog.js?skin=green"></script>
 	<%}  else if(skin == 3){ %>
 	<link href="${ctx}flat/skins/blue/blue.css" rel="stylesheet" type="text/css" />
  	<script type="text/javascript" src="${ctx }flat/plugins/dialog/artDialog.js?skin=blue"></script>
 <%} else if(skin == 4){ %>
 	<link href="${ctx}flat/skins/brand/brand.css" rel="stylesheet" type="text/css" />
  	<script type="text/javascript" src="${ctx }flat/plugins/dialog/artDialog.js?skin=brand"></script>
 <%} %>
  <script type="text/javascript" src="${ctx }flat/plugins/dialog/iframeTools.js"></script>
  <script type="text/javascript" src="${ctx}js/common/artClose.js"></script>
