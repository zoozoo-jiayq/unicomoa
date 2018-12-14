<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="cn.com.qytx.cbb.login.action.LogoConfig"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="common/taglibs.jsp"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%
    LogoConfig logoConfig = LogoConfig.getInstance();
	String sysName = logoConfig.getSysName();
	String defaultUrl = logoConfig.getLogoUrl();
	String defaultSysName = "新密市人民检察院办公系统"; 
	String logoUrl = "";
	if(sysName!=null&&sysName!=""){
		defaultSysName = sysName;
	}
	if(defaultUrl!=null&&defaultUrl!=""){
		logoUrl = logoUrl;
	}
%>
<title><%=defaultSysName%></title>
<meta http-equiv="X-UA-Compatible" content="chrome=1" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<link href="${ctx}images/favicon.ico" mce_href="favicon.ico" rel="bookmark" type="image/x-icon" /> 
<link href="${ctx}images/favicon.ico" mce_href="favicon.ico" rel="icon" type="image/x-icon" /> 
<link href="${ctx}images/favicon.ico" mce_href="favicon.ico" rel="shortcut icon" type="image/x-icon" /> 
<link href="${ctx}flat/css/loginForward/reset.css" rel="stylesheet" type="text/css" />
<link href="${ctx}flat/css/loginForward/login.css" rel="stylesheet" type="text/css" />
 
<script type="text/javascript" src="${ctx}js/common/jquery-1.8.0.min.js"></script>
<!--  dialog -->
<script type="text/javascript" src="${ctx}flat/plugins/dialog/artDialog.js?skin=default"></script>
<script type="text/javascript" src="${ctx}flat/plugins/dialog/artDialog_alert.js"></script>
<script type="text/javascript" src="${ctx}flat/plugins/dialog/iframeTools.js"></script>
<script type="text/javascript" src="${ctx}js/common/artClose.js"></script>
<!--  dialog end -->
<script type="text/javascript" src="${ctx}js/common/jquery.cookie.js"></script>
<script type="text/javascript" src="${ctx}js/common/jquery.query.js"></script>
<script type="text/javascript" src="${ctx}js/login.js"></script>
 
<script type="text/javascript" src="${ctx}js/iecheck.js"></script>
<script type="text/javascript" src="${ctx}flat/js/placeholder_login.js"></script>
<script type="text/javascript">
        //防止被加载到框架中
        if(self.location!=top.location){
            top.location=self.location;
        }
        var basePath ="${ctx}";
        
        //登录框上下居中
        $(document).ready(function($) {
		/*resizeLayout();//内容上下居中*/
});
function resizeLayout() {
	// 主操作区域高度
      var wHeight = (window.document.documentElement.clientHeight || window.document.body.clientHeight || window.innerHeight);
      var cHeight = wHeight - 375;
$(".mainBox").css("padding-top",cHeight/2);
};
$(window).resize(function() {
			/*resizeLayout();*/
		});
    </script>

</head>
<body>
<input type="hidden" id="checkCodeFlag" value="${checkCodeFlag }" />
<div class="login_head">
<div id="iecheck" class="prompt" style="display:none;"><em></em>您的IE浏览器版本过低，可能存在兼容性问题和安全隐患，建议您升级至IE10以上版本或<a href="${ctx }down/GoogleChromeframe.zip">安装插件</a></div>
   <span class="logo"><img src="${ctx}images/login.png" alt=""  heigth="38px" /><%=defaultSysName%>
      </span>
    <span class="help" style="display:none;"><a href="#">常见问题</a>|<a href="#">帮助中心</a></span>
</div>
<div class="login_cont">
   <div class="main_lc">

      		<%-- <div class="left_upload" >
           <a href="http://218.206.244.202:8080/Androidup/qyoaUp/qyoa.apk" class="az_xz" title="安卓下载">
               <h3>安卓版下载</h3>
               <p>版本号：v3.0.7</p>
               <p>更新时间：2014/4/12</p>
           </a>
           <a href="https://itunes.apple.com/cn/app/quan-yaoa/id930797941?mt=8" class="iphone_xz" title="iphone下载">
           	   <h3>iphone版下载</h3>
               <p>版本号：v3.0.7</p>
               <p>更新时间：2014/4/12</p>
           </a>
        </div>
        <div class="erweima"><a href="#"><img src="${ctx }flat/images/loginForward/erweima.png" /></a></div> --%>
	   <form action="${ctx}j_spring_security_check" method="post" name="loginForm" id="loginForm"  >
          <input type="hidden"   id="token" name="sso_token"  />  
	        <div class="login_box">
	           <h2>用户登录</h2>
	           <ul>
	             <li><input  type="text" class="ipt"   maxlength="16" id="j_username" name="j_username" placeholder="用户名"  /></li>
	             <li><input  type="password" class="ipt" maxlength="20" name="j_password" placeholder="密码" onkeyup="value=value.replace(/[^\w\.\*\@\#\%\^\&\(\)\?\/]/ig,'')" id="j_password" /></li>
	             <li><input class="ipt mr5" style="width:115px;"  placeholder="验证码"  id="checkCode" name="checkCode"  onkeyup="this.value=this.value.replace(/[^0-9]/g,'')" maxlength="4" type="text"/>
	             <img alt="点击更换验证码" title="点击更换验证码"   onclick="loadimage();" name="randImage" id="randImage" src="${ctx}/common/code.jsp" height="33" width="88" />&nbsp;&nbsp;&nbsp;<a  style="cursor:pointer;" onclick="loadimage();" class="gray_9" >换一张</a></li>
	             <li class="error" id="errorLi" style="display:none"><em></em><label id="perror"></label>&nbsp;</li>
	             <li class="operate"><label class="radio"><input id="cb_remember" type="checkbox" />记住用户名和密码</label> </li>
	           </ul>
	             <p class="login_btn"><input id="btnLogin"  id="btnLogin" class="bt_logo" value="登 录" type="button" /></p>
	          </div>
        </form>
   </div>
</div>
<div class="login_foot">(C)2010-2014 北京全亚通信技术有限公司 版权所有</div>

</body>
<!-- <script>
funPlaceholder(document.getElementById("j_username"));
funPlaceholder(document.getElementById("j_password"));
funPlaceholder(document.getElementById("checkCode"));
</script> -->
</html>