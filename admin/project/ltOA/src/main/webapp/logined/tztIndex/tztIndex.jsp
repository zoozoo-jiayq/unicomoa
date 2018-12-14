<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="cn.com.qytx.platform.org.domain.UserInfo"%>
<%

	//设置无缓存
	response.setHeader("progma","no-cache");   
	response.setHeader("Cache-Control","no-cache");   
	response.setDateHeader("Expires",0);

	//新增
   String basePath = request.getScheme() + "://"
        + request.getServerName() + ":" + request.getServerPort()
        + "/hwt/" ;
	UserInfo userInfo = (UserInfo) session.getAttribute("adminUser");
    Integer companyId = userInfo.getCompanyId();
    String phone = userInfo.getPhone();
    Integer userId =  userInfo.getUserId();
    
    String infoType = request.getParameter("infoType"); 
    String token = "";
    token = (String) request.getSession().getAttribute("sso_token");
    if(infoType != null && "sms".equals(infoType)){
    	//虚拟登陆
    	//String smsUrl = basePath+"logined/logined.action?systemName=sms&masterPhone="+phone
        //		+"&userId="+userId+"&companyId="+companyId+"&token="+token;
    	//sso登陆
    	String smsUrl = basePath+"logined/logined.action?systemName=sms&sso_token="+token+"&t="+Math.random();
    	response.sendRedirect(smsUrl); 
    }
    if(infoType != null && "notice".equals(infoType)){
    	//虚拟登陆
    	 //String noticeUrl = basePath+"logined/logined.action?systemName=notice&masterPhone="+phone
    	   // 		+"&userId="+userId+"&companyId="+companyId;
    	//sso登陆
    	String noticeUrl = basePath+"logined/logined.action?systemName=notice&sso_token="+token+"&t="+Math.random();
    	response.sendRedirect(noticeUrl); 
    }
    if(infoType != null && "meeting".equals(infoType)){
    	//虚拟登陆
    	 //String noticeUrl = basePath+"logined/logined.action?systemName=meeting&masterPhone="+phone
    	   // 		+"&userId="+userId+"&companyId="+companyId;
    	//sso登陆
    	String meetingUrl = basePath+"logined/logined.action?systemName=meeting&sso_token="+token+"&t="+Math.random();
    	response.sendRedirect(meetingUrl); 
    }
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="pragma" content="no-cache" /> 
<meta http-equiv="cache-control" content="no-cache" /> 
<meta http-equiv="expires" content="0" />
<title>对接</title>
<link href="${ctx}/flat/css/reset.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/flat/plugins/datatable/skins/datatable_default.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/flat/plugins/form/skins/form_default.css" rel="stylesheet" type="text/css" />
</head>
<body class="home">
	<div class="bottom"></div>
	<!--底部信息begin-->
	<div class="bottom"><span>中国移动通信集团河南有限公司</span></div>
	<!--底部信息end-->
</body>
</html>