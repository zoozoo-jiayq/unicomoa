<%@page import="cn.com.qytx.platform.org.domain.CompanyInfo"%>
<%@ page language="java" import="java.util.*"  contentType="text/html; charset=UTF-8" pageEncoding="utf-8"  %>
<%@ page import="cn.com.qytx.platform.utils.datetime.Lunar"%>
<%@ page import="cn.com.qytx.cbb.login.action.LogoConfig"%>
<%@ page import="cn.com.qytx.cbb.weather.action.Weather"%>
<%@ page import=" cn.com.qytx.platform.org.domain.ModuleInfo" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<jsp:include page="../common/taglibs.jsp" />
<%@ page import="cn.com.qytx.platform.org.domain.UserInfo"%>
<%
response.setHeader("Pragma","No-cache");  
response.setHeader("Cache-Control","no-cache");  
	 UserInfo userInfo = (UserInfo) session.getAttribute("adminUser");
	 Integer tempSkinLogo = userInfo.getSkinLogo();
	if(tempSkinLogo != null){
		String str[] = (tempSkinLogo+"").split("");
		String tempStr = str[1]+"3";
		userInfo.setSkinLogo(Integer.parseInt(tempStr));//第二位如果是1的话 代表默认 菜单 如果是2的 话代表菜单 固定 3代表为固定宽度居中
	}  
	
	CompanyInfo company=(CompanyInfo)session.getAttribute("companyInfo");
	String sysName = "";
	if(company != null){
		sysName = company.getSysName();
		if(sysName == null){
			sysName = company.getCompanyName();
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
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>OA办公系统</title>
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
<script type="text/javascript" src="${ctx}flat/js/qytx-cbb-v1.0.js"></script>

<script type="text/javascript" src="${ctx}js/common/json2.js"></script>
<script type="text/javascript" src="${ctx}js/common/stringbuilder.js"></script>
<script type="text/javascript" src="${ctx}/js/common/calendarFix.js"></script>
<script type="text/javascript" src="${ctx}/js/switchskin.js"></script>
<script type="text/javascript" src="${ctx}/js/logined/userChat.js"></script>
<link href="${ctx }flat/css/reset.css" rel="stylesheet" type="text/css" />
<link href="${ctx }flat/css/main_gd.css" rel="stylesheet" type="text/css" />
<link href="${ctx }flat/css/InstantMessage.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx }flat/js/mainFix.js"></script>
<script src="${ctx }flat/js/menu.js" type="text/javascript"></script>
<script src="${ctx }flat/plugins/tab/tabFix.js" type="text/javascript"></script>

<script type="text/javascript" src="${ctx}plugins/tree/ztree/jquery.ztree.all-3.5.min.js"></script> 
<link rel="stylesheet" href="${ctx}flat/plugins/tree/skins/tree_default.css" type="text/css"/>
<script type="text/javascript" src="${ctx}/flat/js/jquery.SuperSlide.2.1.1.js"></script>
<script type="text/javascript" src="${ctx}flat/js/base.js"></script>
<script type="text/javascript">
$(function(){
		var url = "${ctx}/skinAndMenu/skinAndMenu.action?k="+Math.random();
  		$.get(url,null,function(data){});
	});
   function showHide(){
      $("#work_pop").hide(); 
   }
</script>
<script type="text/javascript" src="${ctx}js/common/refreshJsp.js"></script>
</head>
<body>
<input id="moduleType"  type="hidden" value="fix" />
<div class="head_door">
   <div class="box_door">
       <div class="indexTop">
          <span class="logo">
          <%-- <%if(LogoConfig.getInstance().getLogoUrl()!=null && !LogoConfig.getInstance().getLogoUrl().equals("")){ %><img id="companyLogo"   src="${ctx}filemanager/prevViewByPath.action?filePath=<%=LogoConfig.getInstance().getLogoUrl() %>" alt="" /><%}%><%=LogoConfig.getInstance().getSysName() %> --%>
           <%if(company!=null &&company.getLogUrl()!=null&&!company.getLogUrl().equals("")){ %><img id="companyLogo"   src="${ctx}filemanager/prevViewByPath.action?filePath=<%=company.getLogUrl() %>" alt="" /><%}%><font id="sysName"><%=sysName%></font> 
          </span>
          <span class="news">
          <label>欢迎您，${session.adminUser.userName }！</label>
          <label id="w_city"></label>
          <!-- <label><label id="w_weather"></label><label id="w_temperature"></label></label> -->
          <label class="pl20" id="clockFix">
         	<%=new java.text.SimpleDateFormat("yyyy年M月d日").format(new Date()) %>&nbsp;<%=new java.text.SimpleDateFormat("EEEE").format(new Date())%>
          	<%=Integer.parseInt( (new java.text.SimpleDateFormat("HH").format(new Date()) ) ) %>:<%=new java.text.SimpleDateFormat("mm:ss").format(new Date()) %>
          </label>
          </span>
          <div class="operate">
                  <span><em title="主页" class="index"></em></span>
                  <%-- <span><em title="结构" class="stru"></em></span> --%>
                  <span><em title="换肤" class="skin"></em></span>
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
                      <span style="margin-right:0"><em title="注销"  onclick="exit();" class="quit"></em></span>
                      <!--<em title="换肤" class="skin_peeler"></em>  -->
                      <%-- <span><em title="收起" class="clsHead"></em></span>	 --%>
          </div>
       </div>
       <div class="indexNav">
            <span class="index_menu">菜单</span>
            <div id="taskbar_center" class="taskbar_center">
					<span id="tabs_left_scroll" class="btnLeft" style="display: none;">
						<a href="javascript:void(0)"></a>
					</span>
					<div id="tabs_container" class="tabs_container">
					   <ul id="div_tab" class="div_tab">
                       </ul>
					</div>
					<span id="tabs_right_scroll" class="btnRight" style="display: none;">
						<a href="javascript:void(0)"></a>
					</span>
		   </div>
       </div>
   </div>
     <!--页面二级菜单-->
			<div class="s_tab"  id="s_tab">
              <div class="lastMenu" style="display: block;">
              </div>
            </div>
</div>
<div  class="iframeFather" onclick="jQuery(top.document).find('#div_menu').hide();">
		<div class="iframeBox" id="div_pannel">
        </div>
</div>
<div id="div_menu" class="menu">
		<div id="menu" class="m_c">
            <em class="top_jt"></em>
        </div>
  </div>
<%--  <div class="warn" onclick="jQuery(top.document).find('.menu .hideDiv').hide();" >
   <ul>
     <li><img src="${ctx }flat/images/fix_module/warn_swtx.png" onclick="openAffairs();" id='affairsEm' style="cursor: pointer;"><em class="num">5</em></li>
     <li><img src="${ctx }flat/images/fix_module/warn_jsxx.png" onclick="messageBoxClick();" id="messageBox" style="cursor: pointer;"></li>
     <li><img src="${ctx }flat/images/fix_module/warn_zzjg.png" onclick="openOrg();" style="cursor: pointer;"></li>
   </ul>
</div>  --%>
 <!--组织弹窗-->
 <jsp:include page="index/organi.jsp" />
 <!--即时消息-->
 <jsp:include page="index/popMessage.jsp" />
	  
  <!--底部信息-->
  <jsp:include page="index/bottom.jsp" />
  <script type="text/javascript" src="${ctx}/js/common/weather.js"></script>
	<script type="text/javascript" src="${ctx}/js/logined/index.js?v=1.0"></script>
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
  <jsp:include page="index/changeFix.jsp" >
  	<jsp:param value="fix" name="styleType"/>
  </jsp:include>
  
  <!--结构-->
  <div class="changeStru hideDiv popo"   id="jiegou"  style="display:none">
     <ul>
        <li><a href="indexLeft.jsp"><img src="${ctx }flat/images/city/pic5.png"></img><h2>菜单固定</h2></a></li>
        <li><a href="indexDefault.jsp"><img src="${ctx }flat/images/city/pic6.png"></img><h2>菜单收起</h2></a></li>
     	<li><a href="javascript:void(0);" id="hideMenu"><img src="${ctx }flat/images/city/pic7.png"></img><h2>固定宽度居中</h2></a></li>
     </ul>
  </div>  
  <!-- 换肤 换肤加class -->
 <jsp:include page="index/changeSkinClass.jsp" /> 


<!-- <div class="bottom">(C)2010-2014 北京全亚通信技术有限公司 版权所有</div> -->



</body>
</html>