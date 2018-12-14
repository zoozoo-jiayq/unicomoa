<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<jsp:include page="../../common/taglibs.jsp"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>未读邮件</title>
    <jsp:include page="../../common/head.jsp"/>
    <link href="${ctx}plugins/datatable/table_style.css" rel="stylesheet" type="text/css"/>
    <link href="${ctx}plugins/datatable/datatable_page.css" rel="stylesheet" type="text/css"/>
    <style type="text/css">
        #dataTable tr th {
            border-top: #ddd 1px solid;
        }
    </style>
</head>
<body>

<div class="list">
    <div class="meilPageTitle">
         <!-- 注释gx <em class="iconMeil">未读邮件</em>-->
    </div>
    <table id="dataTable_notRead" cellpadding="0" cellspacing="0" class="pretty">
        <thead>
        <tr>
            <th style="width: 100px;" id="fromUserName">发件人</th>
            <th id="subject" class="longTxt">主题</th>
            <th style="width: 120px;" id="shortTime">日期</th>
            <th style="width: 60px">附件</th>
            <th style="width: 80px;" id="attachmentSize">大小</th>
        </tr>
        </thead>
    </table>
</div>

<input type="hidden" name="emailBoxIdHidden" id="emailBoxIdHidden"
       value="${emailBoxId}"/>
<input type="hidden" name="notReadHidden" id="notReadHidden" value="${notRead}">
<input type="hidden" name="iDisplayLengthHidden"
       id="iDisplayLengthHidden" value="${iDisplayLength}"/>
<script type="text/javascript"
        src="${ctx}/js/logined/email/email_common.js">
</script>
<script type="text/javascript"
        src="${ctx}/js/logined/email/email_not_read_for_desktop_task.js">
</script>
</body>
</html>
