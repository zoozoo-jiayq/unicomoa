<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="cn.com.qytx.cbb.login.action.LogoConfig"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="common/taglibs.jsp"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    
    <title>OA客户端下载</title>
    <script type="text/javascript" src="${ctx}js/common/jquery-1.8.0.min.js"></script>
    <script type="text/javascript">             
			 function browserRedirect() {                 
			  var sUserAgent = navigator.userAgent.toLowerCase();                  
			  var bIsIpad = sUserAgent.match(/ipad/i) == "ipad";                  
			  var bIsIphoneOs = sUserAgent.match(/iphone os/i) == "iphone os";                  
			  var bIsMidp = sUserAgent.match(/midp/i) == "midp";                  
			  var bIsUc7 = sUserAgent.match(/rv:1.2.3.4/i) == "rv:1.2.3.4";                  
			  var bIsUc = sUserAgent.match(/ucweb/i) == "ucweb";                  
			  var bIsAndroid = sUserAgent.match(/android/i) == "android";                  
			  var bIsCE = sUserAgent.match(/windows ce/i) == "windows ce";                  
			  var bIsWM = sUserAgent.match(/windows mobile/i) == "windows mobile";                  
			       if (!(bIsIphoneOs) ){                    
			    	   window.location.href="http://218.206.244.202:8080/Androidup/qyoaUp/qyoa.apk";  
			   		}else{
				  		 window.location.href="https://itunes.apple.com/cn/app/quan-yaoa/id930797941?mt=8";  
				   }
			 }             
		 browserRedirect();         
		</script>


</head>
<body>
 
</body>
</html>