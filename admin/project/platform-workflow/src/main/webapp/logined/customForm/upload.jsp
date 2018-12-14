<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../../common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>上传测试</title>
    <jsp:include page="../../common/flatHeadNoChrome.jsp"/>
    <script type="text/javascript">
        var formId = <%=request.getParameter("formId")%>;
    </script>    
    <link rel="stylesheet" type="text/css" href="${ctx}plugins/upload/uploadify.css" />
    <script type="text/javascript" language="javascript" src="${ctx}plugins/upload/jquery.uploadify.min.js" ></script>
    <script type="text/javascript" src="${ctx}js/logined/customForm/upload.js"></script>
</head>
<body style="margin:10px;" scroll="no">
    <!-- 上传按钮 -->
    <input id="file_upload" name="fileupload" type="file" multiple="true">
    &nbsp;只支持html和htm格式文件！
    <!-- <a href="javascript:$('#file_upload').uploadify('upload','*')">开始上传</a> -->
    <!-- 上传队列 -->
    <div id="queue"></div>
    <span class="ml20" id="result"></span>
    <div class="buttonArea">
		<input hideFocus="" value="返 回" type="hidden" onclick="javascript:window.location.href = document.referrer;" class="formButton" />
	
	</div>
</body>
</html>