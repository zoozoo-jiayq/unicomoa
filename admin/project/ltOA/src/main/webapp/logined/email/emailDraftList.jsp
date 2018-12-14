<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>${emailBoxName}</title>
    <jsp:include page="../../common/flatHead.jsp"/>
    <link href="${ctx}flat/css/reset.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}flat/plugins/datatable/skins/datatable_default.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}flat/css/mail.css" rel="stylesheet" type="text/css" />
	
	<script type="text/javascript" src="${ctx}plugins/datatable/jquery.dataTables.min.js"></script>
	<script type="text/javascript" src="${ctx}plugins/datatable/selecedForDatatablePagination.js"></script>
    <script type="text/javascript" src="${ctx}/js/base.js"></script>
</head>
<body>
<div class="list">
    <div class="meil_search_area">
        <span class="meil_btn"><input type="button" onclick="deleteEmailBodyChecked(true)" value="删除"></span>
        <span class="meil_btn"><input type="button" onclick="draftSendEmailBody()" value="发送"></span>
        <span class="meil_btn"><input type="button" onclick="reportEmailBodyChecked()" value="导出Excel"></span>
    </div>
    
    <table width="100%" id="dataTable_draft" cellpadding="0" cellspacing="0" class="pretty dataTable">
        <thead>
        <tr role="row">
            <th class="chk"><input type="checkbox" id="checkAll"/></th>
            <th class="longTxt" id="subject">主题</th>
            <th style="width:100px;" id="shortTime" class="right_bdr0">日期</th>
        </tr>
        </thead>
    </table>
    
    
    <div class="clear"></div>
    <form action="${ctx}logined/email/searchPage.action" method="post">
        <input type="hidden" name="searchJson" id="searchJson" value="<s:property value="[1].searchJson"/>"/>
        <div class="buttonArea" id="btnForSearchResult" style="display: none">
            <span class="mainButton">
                <input type="submit" value="返 回"/>
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
        src="${ctx}js/logined/email/email_common.js">
</script>
<script type="text/javascript"
        src="${ctx}/js/logined/email/email_draft_list.js">
</script>
</body>
</html>
