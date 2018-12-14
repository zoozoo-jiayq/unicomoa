<%--email list for wap by tbt--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="../../common/taglibs.jsp"/>
<!DOCTYPE html PUBLIC "-//WAPFORUM//DTD XHTML Mobile 1.0//EN" "http://www.wapforum.org/DTD/xhtml-mobile10.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>报表批阅</title>
<meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0;" name="viewport" /> 
<meta content="yes" name="apple-mobile-web-app-capable" />
<meta content="black" name="apple-mobile-web-app-status-bar-style" />
<meta content="telephone=no" name="format-detection" />
<script type="text/javascript" src="${ctx}/wap/js/jquery-1.8.1.min.js"></script>
<script type="text/javascript">
    var basePath = "${ctx}";
    function frameResize() {
    	var frame = $(".mainIfrme");
    	if (frame.length > 0) {
    		if (!($.browser.msie && $.browser.version == 6.0)) {
    			frame.height(frame.contents().find("body").height() + 60);
    		} else {
    			frame.height(frame.contents().find("body").height());
    		}
    	}
    }
    $(document).ready(function(e) {
        //iframe重置
    	$(".mainIfrme").load(function() {
    		frameResize();
    	});
    });
</script>
<link href="${ctx}/wap/logined/report/css/base.css" rel="stylesheet" type="text/css"/>
<link href="${ctx}/wap/logined/report/css/public.css" rel="stylesheet" type="text/css"/>
<script type="text/javascript" src="${ctx}/wap/js/logined/report/reportView.js"></script>
</head>
<body>
<input type="hidden" id="reportId" value="<%=request.getParameter("reportId")!=null?request.getParameter("reportId"):"0"%>"/>
<header><a style="cursor:pointer;" onclick="javascript:history.back();" class="btnBack">返回</a><h1>报表分类</h1></header>
<article>
  <iframe id="mainIfrme" src=""  frameborder="0" scrolling="auto" class="mainIfrme"></iframe>
</article>
</body>
</html>

