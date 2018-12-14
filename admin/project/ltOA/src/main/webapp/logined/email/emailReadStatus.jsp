<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>查阅状态</title>
    <jsp:include page="../../common/flatHead.jsp"/>
    <link href="${ctx}flat/css/reset.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}flat/plugins/datatable/skins/datatable_default.css" rel="stylesheet" type="text/css" />

	<script type="text/javascript" src="${ctx}plugins/datatable/jquery.dataTables.min.js"></script>
</head>
<body class="bg_white">
<div class="list">
    <table id="dataTable" style="width:790px;" cellpadding="0" cellspacing="0" class="pretty dataTable">
        <thead>
        <tr>
            <th class="num" id="readStatus">状态</th>
            <th style="width:80px;" id="toName">收件人</th>
            <th style="width:40%;" id="groupName" class="data_l">所属部门</th>
            <th style="width:60%;" id="roleName" class="data_l">角色</th>
            <th style="width:140px;" id="readTime">阅读时间</th>
            <th style="width:100px;" class="right_bdr0">操作</th>
        </tr>
        </thead>
    </table>
</div>
<input type="hidden" name="from" id="from" value="<%=request.getParameter("from")%>"/>
<input type="hidden" name="emailBodyId" id="emailBodyId" value="<%=request.getParameter("emailBodyId")%>">
<script type="text/javascript" src="${ctx}/js/logined/email/email_read_status.js"></script>
</body>
</html>
