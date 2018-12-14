<%--
功能: 报表管理
版本:1.0
开发人员: CQL
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="../../common/taglibs.jsp"/>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>报表管理</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <jsp:include page="../../common/head.jsp"/>
    <script type="text/javascript">
        var basePath = "${ctx}";
    </script>
        <script type="text/javascript" src="${ctx}js/user/selectuser.js"></script>
        <script type="text/javascript" src="${ctx}js/common/map.js"></script>
        <script type="text/javascript" src="${ctx}js/common/hashmap.js"></script>
        <script type="text/javascript" src="${ctx}js/user/user.js"></script>
        <script type="text/javascript" src="${ctx}/js/logined/report/list_manager_report.js"></script>
        <script type="text/javascript" src="${ctx}/js/logined/report/reportSet.js"></script>
</head>
<body>
<input id="userIds"   type="hidden" />
<input id=userNames   type="hidden" />
     <div class="list">
		<div class="listbtn">
		<div class="fbutton">
			  <div><span class="finish" id="plsq">批量授权</span></div> </div>
		      <div class="fr">
							<label >报表类型：</label><select id="reportType"><option value="0">请选择</option><option value="1">填报</option><option value="2">审批</option><option value="3">批阅</option></select>
							<label >报表分类：</label><select id="reportCategory"><option value="0">请选择</option></select>
							<label >报表名称：</label><input id="reportName" placeholder="请输入报表名称"  maxlength="20" type="text" class="formText" style="width:200px" />
							<input hideFocus="" id="search"  value="查询" class="formButton" type="button"  /></div>
    </div>
    <div class="newtable">
        <table id="dataTable_report" cellpadding="0" cellspacing="0" class="pretty dataTable">
            <thead>
            <tr>
                <th class="chk"><input name="" type="checkbox" value=""/></th>
                <th style="width: 80px;">报表类型</th>
                <th style="width: 150px;">报表分类</th>
                <th style="width: 250px;">报表名称</th>
                <th >授权用户</th>
                <th style="width:50px;">操作</th>
            </tr>
            </thead>
           </table>
      </div>
        <div class="clear"></div>
    </div>
</body>
</html>