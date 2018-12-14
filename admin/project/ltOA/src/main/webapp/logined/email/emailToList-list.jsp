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
    <script type="text/javascript" src="${ctx}js/common/placeholder.js"></script>
    <script type="text/javascript" src="${ctx}/js/base.js"></script>
</head>
<body>
<div class="list">
    <div class="meil_search_area searchArea">
    	<span class="meil_btn"><input type="button" onclick="deleteEmailToChecked()" value="删除"/></span>
        <span class="meil_btn"><input type="button" onclick="destroyEmailToChecked()" value="彻底删除"/></span>
        <span class="meil_btn"><input type="button" onclick="forwardEmailToChecked()" value="转发"/></span>
        <span class="meil_btn"><input type="button" onclick="readedEmailToChecked()" value="标为已读"/></span>
        <span class="meil_btn"><input type="button" id="readAllButton" onclick="readedEmailToAll()" value="全部标为已读"/></span>
        <span class="morebtn"><input type="button" value="移动到..." class="meil_btn_0" />
        	<div class="m_btnlist">
        	    <s:if test="%{#boxList.size==0}">
                    <p><a href="javascript:void(0);"  onclick="addDiyBox()">新增文件夹...</a></p>
                </s:if>
                <s:else>
                <s:iterator value="#boxList" var="emailBox">
                    <p><a href="javascript:void(0);" onclick="moveEmailToChecked('${emailBox.id}','${emailBox.boxName}')">${emailBox.boxName}</a></p>
                </s:iterator>
                <p><a href="javascript:void(0);" onclick="addDiyBox()">新增文件夹...</a></p>
                </s:else>
            </div>
        </span>
        <span class="meil_btn"><input type="button" onclick="reportEmailToChecked()" value="导出Excel" /></span>
        <span class="meil_btn" style="display: none;"  id="notReadExclude"><input type="button" value="删除所有已读" onclick="deleteAllReadedEmailTo()"/></span>
    	<span class="meil_search">
    		<label>关键字：</label>
            <span style="position:relative">
            <input type="text" id="searchWord" placeholder="发件人/邮件主题" class="formText" style="width:200px" /></span>
            <input class="searchButton" onclick="getDataTable()" type="button" value="查询" />
        </span>
    </div>
    <table id="dataTable_toList" cellpadding="0" cellspacing="0" class="pretty dataTable">
        <thead>
        <tr role="row">
            <th class="chk"><input type="checkbox" id="checkAll"/></th>
            <th style="width: 90px;" class="longTxt">发件人</th>
            <th style="width:40%" class="longTxt">主题</th>
            <th style="width:60%" class="longTxt">内容</th>
            <th style="width: 100px;" class="right_bdr0">日期</th>
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

<input type="hidden" name="emailBoxIdHidden" id="emailBoxIdHidden"
       value="${emailBoxId}"/>
<input type="hidden" name="notReadHidden" id="notReadHidden" value="${notRead}"/>
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
        src="${ctx}/js/logined/email/email_to_list-list.js">
</script>
<script>funPlaceholder(document.getElementById("searchWord"));
</script>
</body>
</html>
