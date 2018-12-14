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
    <script type="text/javascript" src="${ctx}/js/common/datatable_checkbox.js"></script>
    <script type="text/javascript" src="${ctx}/js/logined/record/common.js"></script>
    <script type="text/javascript" src="${ctx}/js/logined/record/list_manager.js"></script>
    <!--artDialog类库-->
    <script type="text/javascript" src="${ctx}flat/plugins/dialog/artDialog.js?skin=default"></script>
	<script type="text/javascript" src="${ctx}flat/plugins/dialog/artDialog_alert.js"></script>
	<script type="text/javascript" src="${ctx}flat/plugins/dialog/iframeTools.js"></script>
	<script type="text/javascript" src="${ctx}js/common/artClose.js"></script>
	<script type="text/javascript" src="${ctx}/js/logined/record/baseStation_import.js"></script>
</head>
<body>
     <div class="list">
	<!-- 	<div class="pageTitle"><em class="iconList">用户档案列表</em></div> -->
		<div class="searchArea">
			 <table cellspacing="0" cellpadding="0">
              <tbody>
              <tr>
                <td class="right">
                                <label>性别：</label><select id="sex" name=""><option  value="-1">全部</option><option value="1">男</option><option value="0">女</option></select>
                                <label>关键字：</label>
                                <span class="position:relative;"><input id="key_word" type="text" class="formText searchkey" placeholder="姓名"/></span>
                                <input type="button" class="searchButton" value="查询" onclick="getNewDataTable()"/>
                </td>
                <td style="width: 276px;">
                        <div class="fButton greenBtn">
                              <span onclick="window.location.href='${ctx}logined/record/create.action?from=create'" class="add">新增</span>
                        </div>
                        <div class="fButton orangeBtn">
                          <span class="delete" onclick="deleteUserRecordChecked()">删除</span>
                        </div>
                        <div class="fButton greenBtn">
                          <span class="import" onclick="uploadBaseStation()">导入</span>
                        </div>
                </td>
               </tr>
               </tbody>
         	</table>
    </div>
        <table id="dataTable_record" cellpadding="0" cellspacing="0" class="pretty dataTable">
            <thead>
            <tr>
                <th class="chk"><input id="checkAll" type="checkbox" value=""/></th>
                <th style="width: 90px;" id="userName">姓名</th>
                <th style="width: 50px;" id="sex">性别</th>
                <th style="width: 90px;" id="job">职务</th>
                <th style="width: 90px;" id="birthDay">出生日期</th>
                <th style="width: 100px;" id="nationality">民族</th>
                <th style="width:100%" id="nativityPlace" class="longTxt">籍贯</th>
                <th style="width: 160px;" id="identityNo">身份证号</th>
                <th style="width:95px;" class="right_bdr0 oper">操作</th>
            </tr>
            </thead>
        </table>
    <div class="clear"></div>
<form action="${ctx}logined/record/searchPage.action" method="post">
    <input type="hidden" name="searchJson" id="searchJson" value="<s:property value="searchJson"/>"/>
    <div class="buttonArea" id="btnForSearchResult" style="display: none">
                <input type="submit" class="formButton_grey" value="返 回"/>
    </div>
</form>
<input type="hidden" name="iDisplayLengthHidden" id="iDisplayLengthHidden" value="${iDisplayLength}"/>
<input type="hidden" id="groupId" value="${groupId}" />
<input type="hidden" name="from" id="from" value="manager"/>
</div>
</body>
</html>