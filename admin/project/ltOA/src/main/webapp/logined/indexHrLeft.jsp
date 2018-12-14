﻿<%@ page language="java" import="java.util.*"  contentType="text/html; charset=UTF-8" pageEncoding="utf-8"  %>
<%@ page import="cn.com.qytx.platform.utils.datetime.Lunar"%>
<%@ page import="cn.com.qytx.cbb.login.action.LogoConfig"%>
<%@ page import="cn.com.qytx.cbb.weather.action.Weather"%>
<%@ page import=" cn.com.qytx.platform.org.domain.ModuleInfo" %>
<%@ page import="  org.apache.commons.lang3.StringUtils" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<jsp:include page="../common/taglibs.jsp" />
<%@ page import="cn.com.qytx.platform.org.domain.UserInfo"%>
<%@page import="cn.com.qytx.platform.org.domain.CompanyInfo"%>
<%
response.setHeader("Pragma","No-cache");  
response.setHeader("Cache-Control","no-cache");  
	UserInfo userInfo = (UserInfo) session.getAttribute("adminUser");
	Integer tempSkinLogo = userInfo.getSkinLogo();
	if(tempSkinLogo != null){
		String str[] = (tempSkinLogo+"").split("");
		String tempStr = str[1]+"2";
		userInfo.setSkinLogo(Integer.parseInt(tempStr));//第二位如果是1的话 代表默认 菜单 如果是2的 话代表菜单 固定
	}
	
	CompanyInfo company=(CompanyInfo)session.getAttribute("companyInfo");
	String loginSysName=(String)session.getAttribute("loginSysName");
	String sysName = "";
	if(StringUtils.isNoneBlank(loginSysName) && "HR".equals(loginSysName)){
		sysName="新密市人民检察院个人事务系统";
	}else if(StringUtils.isNoneBlank(loginSysName) && "OA".equals(loginSysName)){
		sysName="新密市人民检察院办公平台";
	}else{
		if(company != null){
			sysName = company.getSysName();
			if(sysName == null){
				sysName = company.getCompanyName();
			}
		}
	}
%>

<%
     session.setMaxInactiveInterval(18000000); //单位是秒
        String moduleList = "";
			if (session.getAttribute("menu") != null) {
				moduleList = session.getAttribute("menu").toString();
			}
			//获取农历日期
			Calendar today = Calendar.getInstance();
			Date date = new Date();
			today.setTime(date);
			Lunar lunar = new Lunar(today);
			String basePath2 = request.getScheme() + "://"
		            + request.getServerName() + ":" + request.getServerPort() ;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="chrome=1" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="${ctx}images/favicon.ico" mce_href="favicon.ico" rel="bookmark" type="image/x-icon" /> 
<link href="${ctx}images/favicon.ico" mce_href="favicon.ico" rel="icon" type="image/x-icon" /> 
<link href="${ctx}images/favicon.ico" mce_href="favicon.ico" rel="shortcut icon" type="image/x-icon" /> 
<title>新密市人民检察院个人事务系统</title>
<!-- 换肤  -->
<jsp:include page="index/changeSkin.jsp" /> 
<script type="text/javascript">
    var lunar = "<%=lunar%>";
    var basePath = "${ctx}";
    var basePath2 = "<%=basePath2%>";
    var toke="${session.token}";
</script>
<script type="text/javascript" src="${ctx}js/common/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="${ctx}js/common/stringutil.js"></script>
<script type="text/javascript" src="${ctx }/flat/js/async.js"></script>

<script type="text/javascript" src="${ctx}js/common/json2.js"></script>
<script type="text/javascript" src="${ctx}js/common/stringbuilder.js"></script>
<script type="text/javascript" src="${ctx}/js/common/calendar.js"></script>
<script type="text/javascript" src="${ctx}/js/switchskin.js"></script>
<script type="text/javascript" src="${ctx}/js/logined/userChat.js"></script>
 <link href="${ctx }flat/css/reset.css" rel="stylesheet" type="text/css" />
<link href="${ctx }flat/css/main.css" rel="stylesheet" type="text/css" />
<link href="${ctx }flat/css/InstantMessage.css" rel="stylesheet" type="text/css" />
<script src="${ctx }flat/js/mainLeft.js" type="text/javascript"></script>
<script src="${ctx }flat/plugins/tab/tabLeft.js" type="text/javascript"></script>

<script type="text/javascript" src="${ctx}plugins/tree/ztree/jquery.ztree.all-3.5.min.js"></script> 
<link rel="stylesheet" href="${ctx}flat/plugins/tree/skins/tree_default.css" type="text/css"/>
<script type="text/javascript" src="${ctx}/flat/js/jquery.SuperSlide.2.1.1.js"></script>
<script type="text/javascript" src="${ctx}flat/js/qytx-cbb-v1.0.js"></script>
<script type="text/javascript">
   function showHide(){
      $("#work_pop").hide(); 
   }
   //把菜单更改到数据库 
   		$(function(){
   			var url = "${ctx}/skinAndMenu/skinAndMenu.action?k="+Math.random();
   	   		$.get(url,null,function(data){});
   		});
   		
</script>
<script type="text/javascript" src="${ctx}js/common/refreshJsp.js"></script>
<script type="text/javascript" src="${ctx}flat/js/index.js"></script>
<style type="text/css">
  .iframeFather  {
     top:0px!important;
     left:180px!important;
     bottom:0px!important;
  }
  .s_menu{
    display:block;
  }
  #taskbar_center{
  	padding-left:10px;
  }
</style>
</head>
<body>
<input id="moduleType"  type="hidden" value="left" />
	<!--头部 begin-->
	<div class="indexHeader">
		<div class="headbg">
			<div class="indexContent">
				<h1>
					<%if(company!=null &&company.getLogUrl()!=null&&!company.getLogUrl().equals("")){ %><img id="companyLogo"   src="${ctx}filemanager/prevViewByPath.action?filePath=<%=company.getLogUrl() %>" alt="" /><%}%><font id="sysName"><%=sysName%></font> 
				</h1>
                <div class="head_nav">
                    <ul>
                       <li>
                         <p class="user">欢迎您，<font>${session.adminUser.userName }</font></p>
                       </li>
                       <li>
                          <p><span id="w_city"></span></p><%-- &nbsp;&nbsp;<span id="w_weather"></span></p>
                          <p><span id="w_temperature"></span></p> --%>
                       </li>
                       <li class="last">
                          <div class="sysTime" id="clock">
	                          <p><font class="time"><%=Integer.parseInt( (new java.text.SimpleDateFormat("HH").format(new Date()) ) ) %>:<%=new java.text.SimpleDateFormat("mm:ss").format(new Date()) %></font></p>
	                          <p><font ><%=new java.text.SimpleDateFormat("yyyy年MM月dd日").format(new Date()) %><%=new java.text.SimpleDateFormat("EEEE").format(new Date())%></font></p></div>
                       </li>
                    </ul>
				  </div>
			   </div>

			<div class="index_nav">
				<div class="index_menu"  style="display:none;" ><em></em>菜单</div>
				<div id="taskbar_center">
					<span class="btnLeft" id="tabs_left_scroll"><a
						href="javascript:void(0)"></a>
					</span>
					<div id="tabs_container">
						<ul id="div_tab">
						</ul>
					</div>
					<span class="btnRight" id="tabs_right_scroll"><a
						href="javascript:void(0)"></a>
					</span>
				</div>

				<div id="index_nav_right" class="menu_right">
				   <%-- <span><em title="首页"   class="index"></em></span> --%>	
					<%-- <span><em title="结构" class="stru"></em></span> --%>
                    <span><em title="换肤" class="skin"  ></em></span>
				<% 
					boolean flag = false;
					List<ModuleInfo> modulelist = (List<ModuleInfo>)request.getSession().getAttribute("moduleList"); 
					for(int i=0; i<modulelist.size(); i++){
						if(modulelist.get(i).getModuleName().equals("个人信息")){
							flag = true;
							break;
						}
					}
				 
					if(flag){
				%>
				<input type="hidden"  value="1"  id="setFlag" />
				      <span><em title="设置"  onclick=" $('.menu').hide();gotoUrl(119, '个人设置', '${ctx}sysset/toRecordSet.action', '')" class="fit"></em></span>
				      <%} else{%>
				      <input type="hidden"  value="0"  id="setFlag" />
				      <%} %>
                      <span><em title="注销"  onclick="exit();" class="quit"></em></span>
                      <span><em title="收起" class="clsHead"></em></span>
				</div>
			</div>
              <!--页面二级菜单-->
			<div class="s_tab"  id="s_tab">
              <div class="lastMenu" style="display: block;">
              </div>
            </div>
		</div>
	</div>
	<!--头部 end-->
	
	<!--系统菜单 begin-->
   <div id="mainContainer" class="leftframe_menu">
	    <div    id="menu" class="menu_l" >
	    </div>
	  <!--右侧内容 begin-->
	  <div  id="mainFrameContainer">
	    <div class="iframeFather" >
			<div id="div_pannel" class="iframeBox"></div>
			</div>
	  </div>
  		<!--右侧内容 end--> 
</div>




	<!--系统菜单 end-->
 <!--组织弹窗-->
 <jsp:include page="index/organi.jsp" />
 <!--即时消息-->
 <jsp:include page="index/popMessage.jsp" />
	 
  <!--底部信息-->
  <jsp:include page="index/bottom.jsp" />
	
	

	<script type="text/javascript" src="${ctx}/js/common/weather.js"></script>
	<script type="text/javascript" src="${ctx}/js/logined/indexHr.js?v=1.0"></script>
	<script type="text/javascript">
	     addTab("shouye", "${ctx}logined/desktop/mainPage.action", "&nbsp;桌&nbsp;&nbsp;&nbsp;面", false, "");
        $(document).ready(function() {
	        //$("#div_shouye").css("height","");
	        $(".indexContent").click(function() {
		        $(".menu .hideDiv").hide();
	        });
	        $(".tabbox").click(function() {
		        $(".menu .hideDiv").hide();
	        });
	        $(".s_tab").click(function() {
		        $(".menu .hideDiv").hide();
	        });
            $("#taskbar_center").click(function() {
                $(".menu").hide();
                return true;
            });
            $("#index_nav_right .skin_peeler").click(function(){
                if($("#oa_hf").is(":hidden")){
					$("#oa_hf").show();
				}else{
				    $("#oa_hf").hide();
				}
            });
            $("#oa_hf .close_hf em").click(function(){
                $("#oa_hf").hide();
            });
        });
	</script>
<!--换首页 换颜色 结构 -->  
  <jsp:include page="index/change.jsp?styleType=indexHrLeft" />
  <!--结构-->
  <div class="changeStru hideDiv popo"  id="jiegou"  style="display:none">
     <ul>
        <li> <a  href="javascript:void(0);"  id="hideMenu" ><img src="${ctx }flat/images/city/pic5.png"></img><h2>菜单固定</h2></a></li>
        <li><a  href="indexFix.jsp"  ><img src="${ctx }flat/images/city/pic6.png"></img><h2>菜单收起</h2></a></li>
     	<%-- <li><a href="indexFix.jsp"><img src="${ctx }flat/images/city/pic7.png"></img><h2>固定宽度居中</h2></a></li> --%>
     </ul>
  </div>  
    <!-- 换肤 换肤加class -->
  <jsp:include page="index/changeSkinClass.jsp" />  
 
</body>
</html>
