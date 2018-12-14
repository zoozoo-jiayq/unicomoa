<%--
功能: 报表管理
版本:1.0
开发人员: PanBo
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
        <script type="text/javascript" src="${ctx}js/common/map.js"></script>
        <script type="text/javascript" src="${ctx}js/common/hashmap.js"></script>
        <script type="text/javascript" src="${ctx}/js/logined/report/sectionBDList.js"></script>
</head>
<body>
	<input type="hidden" name="projectId" id="projectId" value="${param.projectId}"/>
     <div class="list">
		<div class="listbtn">
		<div class="listbtn">
			<div class="fbutton">
			  <div><span class="add" id="add">新增</span></div> </div>
			<div class="fbutton">
			  <div><span class="delete" id="deleteMany">批量删除</span></div></div>
		<div class="fbutton">
			  <div><span class="reset" id="backSup">返回上一级</span></div></div>
		      <div class="fr">
							<label >状态：</label><select id="isDelete"><option value="-1">请选择</option><option value="0">有效</option><option value="1">无效</option></select>
							<label >关键字：</label><input id="sectionName" placeholder="请输入标段名称、标段编号"  maxlength="20" type="text" class="formText" style="width:200px" />
							<input hideFocus="" id="search"  value="查询" class="formButton" type="button"  /></div>
    </div>
    <div class="newtable">
        <table id="dataTable_report" cellpadding="0" cellspacing="0" class="pretty dataTable">
            <thead>
            <tr>
                <th class="chk"><input name="" type="checkbox" value=""/></th>
                <th style="width: 150px;">项目名称</th>
                <th style="width: 150px;">标段名称</th>
                <th style="width: 150px;">标段编号</th>
                <th style="width: 100px;">创建时间</th>
                <th style="width: 80px;">创建人</th>
                <th style="width: 80px;">状态</th>
                <th style="width: 150px;">备注</th>
                <th style="width:130px;">操作</th>
            </tr>
            </thead>
           </table>
      </div>
        <div class="clear"></div>
    </div>
</body>
</html>