<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>导入</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <jsp:include page="../../common/flatHead.jsp"/>
    <link href="${ctx}flat/css/reset.css" rel="stylesheet" type="text/css" />
    <link href="${ctx}flat/css/systemManagement.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="${ctx}flat/plugins/dialog/artDialog.js?skin=default"></script>
    <script type="text/javascript" src="${ctx}flat/plugins/dialog/iframeTools.js"></script>
    <%-- <script type="text/javascript" src="${ctx}js/placeholder.js"></script> --%>
    <script type="text/javascript" src="${ctx}js/common/ajaxfileupload.js"></script>   
    <script type="text/javascript" src="${ctx}js/logined/record/import.js"></script>
</head>
<body class="bg_white">
    <div class="importBox">
        <h4>选择导入文件：<input type="file" name="fileToUpload" id="fileToUpload" accept=".XLS" /></h4>
	    <p style="text-align:left;padding-left:140px"><span id="msg"></span></p>
	    <p style="text-align:left;padding-left:140px"><span>暂时只支持导入Excel格式文件，后缀名为.xls</span><a href="${ctx}down/personnel.xls" id="importModule" class="ml10">获取模板？</a></p>
	    <p style="text-align:left;padding-left:140px">说明：</p>
	    <p style="text-align:left;padding-left:140px">1.支持后缀为xls的excel文件导入</p>
        <p style="text-align:left;padding-left:140px">2.数据有误导致导入失败的数据可以下载修改后重新导入</p>
        <p style="text-align:left;padding-left:140px">3.每次仅支持导入一个文件</p>
        <p style="text-align:left;padding-left:140px">4.下载的模版表头不可更改</p>
        <p style="text-align:left;padding-left:140px">5.标红字段为必填</p>
    </div>
</body>
</html>