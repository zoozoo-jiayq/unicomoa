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
<jsp:include page="../../common/taglibs.jsp"/>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>人事档案-列表</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <jsp:include page="../../common/head.jsp"/>
    <style type="text/css">
        .inputTable th {
            width: 150px;
        }
    </style>
    <script type="text/javascript" src="${ctx}/js/common/datatable_checkbox.js"></script>
    <script type="text/javascript" src="${ctx}/js/logined/record/common.js"></script>
    <script type="text/javascript" src="${ctx}/js/logined/record/list.js"></script>
    <script type="text/javascript">
        var basePath = "${ctx}";
    </script>
</head>
<body style="display: none">
<div class="list">
    <div class="pageTitle" style="margin-top:0;"><em class="iconList">${groupName}用户档案列表</em></div>
    <div class="listbtn">
        <div class="tDiv2">
            <div class="fbutton search_no">
                <div>
                    <span style="padding-left: 20px;" class="add">
                        <a href="${ctx}logined/record/create.action?groupId=${groupId}">新增</a>
                    </span>
                </div>
            </div>
            <div class="fbutton">
                <div>
                    <span style="padding-left: 20px;" class="delete">
                        <a href="javascript:deleteUserRecordChecked()">删除</a>
                    </span>
                </div>
            </div>
        </div>
    </div>
    <div class="newtable">
        <table id="dataTable_record" cellpadding="0" cellspacing="0" class="pretty dataTable">
            <thead>
            <tr>
                <th class="chk"><input name="" type="checkbox" value=""/></th>
                <th style="width: 100px;">姓名</th>
                <th style="width: 40px;">性别</th>
                <th style="width: 80px;">出生日期</th>
                <th style="width: 100px;">民族</th>
                <th style="width:100%">籍贯</th>
                <th style="width: 80px">政治面貌</th>
                <th style="width: 250px;">身份证号</th>
                <th style="width:60px;">操作</th>
            </tr>
            </thead>
        </table>
    </div>
    <div class="clear"></div>
</div>
<form action="${ctx}logined/record/searchPage.action" method="post">
    <input type="hidden" name="searchJson" id="searchJson" value="<s:property value="searchJson"/>"/>
    <div class="buttonArea" id="btnForSearchResult" style="display: none">
                <input type="submit" class="formButton_green" value="返 回"/>
    </div>
</form>
<input type="hidden" name="iDisplayLengthHidden" id="iDisplayLengthHidden" value="${iDisplayLength}"/>
<input type="hidden" id="groupId" value="${groupId}" />
<input type="hidden" name="from" id="from" value="<s:property value="from"/>"/>
</body>
</html>