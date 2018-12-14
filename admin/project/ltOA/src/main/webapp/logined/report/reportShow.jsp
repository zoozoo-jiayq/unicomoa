<%--
功能: 报表管理
版本:1.0
开发人员: CQL
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="../../common/taglibs.jsp"/>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>报表查看</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <jsp:include page="../../common/head.jsp"/>
    <link href="${ctx}css/report_forms.css" rel="stylesheet" type="text/css" />
      <script type="text/javascript" src="${ctx}/js/logined/report/reportShow.js"></script>
    <script type="text/javascript">
        var basePath = "${ctx}";
    </script>
    <script type="text/javascript">
	$(document).ready(function(){
	  $(".close_jt").click(function(){
	   if($(this).hasClass('open_jt')){
		    $(this).removeClass("open_jt");
			$(".leftMenu").css("width","200px");
			$(".mainpage").css("padding-left","200px");
	   }else{
	        $(this).addClass("open_jt");
			$(".leftMenu").css("width","7px");
			$(".mainpage").css("padding-left","7px");
	   }	
	  });
	});
</script>
</head>
<body>
     <div class="mainpage">
	    <div class="leftMenu">
				<div class="blockBox"> 
					<div class="service-menu">
					<h1 style="border-bottom: 2px solid #5bbbc9; color: #017a8f; font-size: 16px;height: 35px;line-height: 35px;">
					  报表分类
					</h1>
					           ${reportHtml }
					</div>
                    <em class="close_jt"></em>
			   </div>
	   </div>
       <iframe class="iframeresize" id="page" src="${ctx }logined/report/reportShowEmpty.jsp?reportType=${reportType}" name="page"  border="0" frameBorder="0" scrolling="auto" style="width: 100%; height:100%" ></iframe>
   </div>
</body>
</html>