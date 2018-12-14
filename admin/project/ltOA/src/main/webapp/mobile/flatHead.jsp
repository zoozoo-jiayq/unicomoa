<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String serverPath = request.getScheme() + "://"
        + request.getServerName() + ":" + request.getServerPort();
	String basePath = serverPath+ path+"/" ;
    request.setAttribute("ctx", basePath);
    request.setAttribute("serverPath", serverPath);
    request.setAttribute("mobileSysName", "乐工作");
    request.setAttribute("version", "3.1");
%>
<link rel="stylesheet" type="text/css" href="${ctx}/mobile/css/base.css"/>
<link rel="stylesheet" type="text/css" href="${ctx}/mobile/css/idangerous.swiper.css"/>
<script type="text/javascript" src="${ctx}/mobile/js/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/mobile/js/base.js"></script>
<script type="text/javascript" src="${ctx }/mobile/js/html5Adapter.js"></script>
<script type="text/javascript" src="${ctx }/mobile/js/numConvert.js"></script>
<script type="text/javascript" src="${ctx }/mobile/js/idangerous.swiper.min.js"></script>
<script type="text/javascript" src="${ctx }/mobile/js/slide.js"></script>
<script type="text/javascript" src="${ctx }/mobile/js/map.js"></script>

<script type="text/javascript">
	var basePath = "${ctx}";
	var serverPath = "${serverPath}";
</script>
<link rel="stylesheet" type="text/css" id="sc" href=""/>
<script type="text/javascript">             
 function browserRedirect() {                 
	  var sUserAgent = navigator.userAgent.toLowerCase();                  
	  var bIsIphoneOs = sUserAgent.match(/iphone os/i) == "iphone os";                  
	  if (bIsIphoneOs){ 
	   var sc=document.getElementById("sc");                       
	   sc.setAttribute("href",basePath+"/mobile/css/IOS.css");        
	   }
  }             
  browserRedirect();         
</script>
<meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0;" name="viewport" />
<meta content="yes" name="apple-mobile-web-app-capable" />
<meta content="black" name="apple-mobile-web-app-status-bar-style" />
<meta content="telephone=no" name="format-detection" />
