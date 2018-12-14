<%--
功能:人事档案 新建和修改页面
版本:1.0
开发人员: 汤波涛
创建日期: 2013-03-22
修改日期: 2013-03-22
修改人员: 汤波涛
修改列表:
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>人事档案-列表</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <jsp:include page="../../common/flatHead.jsp"/>
    <link href="${ctx}/flat/css/reset.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/flat/plugins/datatable/skins/datatable_default.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/flat/plugins/form/skins/form_default.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="${ctx}/js/common/datatable_checkbox.js"></script>
    <script type="text/javascript" src="${ctx}/js/logined/record/common.js"></script>
    <script type="text/javascript" src="${ctx}/js/logined/record/list.js"></script>
    <script type="text/javascript">
        var basePath = "${ctx}";
    </script>
</head>
<body style="display: none">
<div class="list">
        <table id="dataTable_record" cellpadding="0" cellspacing="0" class="pretty dataTable">
            <thead>
            <tr>
            
            
                  <th class="num" id="num">序号</th>
                  <th style="width:90px;" id="userName">姓名</th>
                  <th style="width:50px;" id="sex">性别</th>
                  <th style="width:90px;" id="job">职务</th>
                  <th style="width:90px;" id="birthDay">出生日期</th>
                  <th style="width:100px;" id="nationality">民族</th>
                  <th style="width:100%" id="nativityPlace" class="longTxt">籍贯</th>
                  <th style="width:160px;" id="identityNo">身份证号</th>
                  <th class="right_bdr0" style="width:70px;">操作</th>
            
            <!--  
                <th style="width: 100px;">姓名</th>
                <th style="width: 40px;">性别</th>
                <th style="width: 80px;">出生日期</th>
                <th style="width: 100px;">民族</th>
                <th style="width:100%">籍贯</th>
                <th style="width: 80px">政治面貌</th>
                <th style="width: 250px;">身份证号</th>
                <th style="width:40px;">操作</th>-->
            </tr>
            </thead>
        </table>
    <div class="clear"></div>
<form action="${ctx}logined/record/searchPage.action" method="post">
    <input type="hidden" name="searchJson" id="searchJson" value="<s:property value="searchJson"/>"/>
    <div class="buttonArea" id="btnForSearchResult" style="display: none">
                <input class="formButton_grey" type="submit" value="返 回"/><span class="blue">点击返回档案查询</span>
    </div>
</form>
<input type="hidden" name="iDisplayLengthHidden" id="iDisplayLengthHidden" value="${iDisplayLength}"/>
<input type="hidden" id="groupId" value="${groupId}" />
<input type="hidden" id="treeType" value="${treeType}" />
<input type="hidden" name="from" id="from" value="<s:property value="from"/>"/>
</div>
</body>
</html>