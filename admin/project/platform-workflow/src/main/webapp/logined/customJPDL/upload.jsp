<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../../common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>上传测试</title>
    <jsp:include page="../../common/flatHead.jsp"/>
    <script type="text/javascript">
        var formId = <%=request.getParameter("formId")%>;
    </script>    
    <link rel="stylesheet" type="text/css" href="${ctx}flat/plugins/upload/uploadify.css" />
    <script type="text/javascript" language="javascript" src="${ctx}plugins/upload/jquery.uploadify.min.js" ></script>
    <script type="text/javascript" src="${ctx}js/logined/customJPDL/upload.js"></script>
    <link href="${ctx }flat/css/reset.css" rel="stylesheet" type="text/css" />
	<link href="${ctx }flat/css/systemManagement.css" rel="stylesheet" type="text/css" />
	<style type="text/css">
	.uploadify{display:inline-block;margin:0 5px;}
	</style>
</head>
<body class="bg_white">
<div class="importBox">
<% 
String type = request.getParameter("type");
if(type==null || type.equals("")){
    type="2";
}
%>
<input type="hidden"  id="type" value="<%=type%>"/>
    <!-- 上传按钮 -->
    <%--<input id="file_upload" name="fileupload" type="file" multiple="true"/>
    --%>
    <h4>选择导入文件<img src="${ctx }flat/images/scan.png" width="80" height="30" id="file_upload" name="fileupload"/><span id="fileName">未选择文件</span></h4>
    <input type="hidden" id="categoryId" value="<%=request.getParameter("categoryId")%>"/><%--
    &nbsp;只支持.xml格式文件！
    --%><p  style="text-align:left;padding-left:30px; color:#f60"><span>只支持.xml格式文件！</span></p>
    <!-- <a href="javascript:$('#file_upload').uploadify('upload','*')">开始上传</a> -->
    <!-- 上传队列 -->
    <div id="queue"></div><%--
    <span class="ml20" id="result"></span>
    <div class="buttonArea">
		<input hideFocus="" value="返 回" type="hidden" onclick="javascript:window.location.href = document.referrer;" class="formButton" />	
	</div>

--%>
</div>
</body>
</html>