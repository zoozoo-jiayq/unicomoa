<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>${emailBoxName}</title>
    <jsp:include page="../../common/flatHead.jsp"/>
    <link href="${ctx}flat/css/reset.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}flat/css/mail.css" rel="stylesheet" type="text/css" />
    <link href="${ctx}flat/plugins/datatable/skins/datatable_default.css" rel="stylesheet" type="text/css" />
    
    <script type="text/javascript" src="${ctx}plugins/datatable/jquery.dataTables.min.js"></script>
	<script type="text/javascript" src="${ctx}plugins/datatable/selecedForDatatablePagination.js"></script>
    <script type="text/javascript" src="${ctx}flat/js/base.js"></script>
</head>
<body>
<div class="list">
    <div class="meil_search_area">
        <span class="meil_btn"><input type="button" value="删除" onclick="deleteEmailBodyChecked()"/></span>
        <span class="meil_btn"><input type="button" value="导出Excel" onclick="reportEmailBodyChecked()"/></span>
    </div>
    <table id="dataTable_sendedlist" width="100%" class="pretty dataTable" cellspacing="0" cellpadding="0">
        <thead>
        <tr role="row">
            <th class="chk"><input type="checkbox" id="checkAll"/></th>
            <th style="width: 90px;" class="longTxt" id="toName">收件人</th>
            <th class="longTxt">主题</th>
            <th style="width: 100px;">日期</th>
            <th style="width: 155px" class="right_bdr0 oper">操作</th>
        </tr>
        </thead>
    </table>
    <form action="${ctx}logined/email/searchPage.action" method="post">
        <input type="hidden" name="searchJson" id="searchJson" value="<s:property value="[1].searchJson"/>"/>
        <div class="buttonArea" id="btnForSearchResult" style="display: none">
            <span class="mainButton">
                <input type="submit" value="返  回"/>
            </span>
        </div>
    </form>
</div>

<input type="hidden" name="iDisplayLengthHidden"
       id="iDisplayLengthHidden" value="${iDisplayLength}"/>
<input type="hidden" name="from" id="from" value="<s:property value="[1].from"/>"/>

<script type="text/javascript"
        src="${ctx}js/common/datatable_checkbox.js">
</script>
<script type="text/javascript"
        src="${ctx}/js/logined/email/email_common.js">
</script>
<script type="text/javascript"
        src="${ctx}/js/logined/email/email_sended_list-list.js">
</script>
</body>
</html>
