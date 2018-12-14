<%--
  User: 吴洲
  Date: 13-11-22
  Time: 17:06
 
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path+"/" ;
    request.setAttribute("ctx", basePath);
%>
<!DOCTYPE html PUBLIC "-//WAPFORUM//DTD XHTML Mobile 1.0//EN" "http://www.wapforum.org/DTD/xhtml-mobile10.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>消息详情</title>
<meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0;" name="viewport" />
<meta content="yes" name="apple-mobile-web-app-capable" />
<meta content="black" name="apple-mobile-web-app-status-bar-style" />
<meta content="telephone=no" name="format-detection" />
<link rel="stylesheet" type="text/css" href="${ctx}/logined/jpush/mobile/base.css"/>
<link rel="stylesheet" type="text/css" href="${ctx}/logined/jpush/mobile/public.css"/>
</head>
<body>
<article class="news_show">
     <h1>${push.subject }</h1>
<!--      <h2>发布人：${push.userName }&nbsp;&nbsp;发布时间：${push.pushTimeStr }</h2> -->
     <h2>发布时间：${push.pushTimeStr }</h2>
     <c:out escapeXml="false" value="${push.pushContent }"/>
</article>
<div class="down_num"  style="display:none">
  <span>推荐数量：${push.pushCount }</span>
  <span>推荐成功数量：${push.pushSuccessCount }</span>
</div>
</body>
</html>
