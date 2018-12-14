<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<% String id = request.getParameter("id");
	request.setAttribute("id",id);
 %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>公告查看</title>
<jsp:include page="../../common/flatHead.jsp" />
<script type="text/javascript" src="${ctx}plugins/datatable/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="${ctx}js/common/jquery.cookie.js"></script>
<script language="javascript" type="text/javascript" src="${ctx}plugins/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx}js/logined/column/viewList.js"></script>
<script type="text/javascript" src="${ctx}js/common/placeholder.js"></script>
<script type="text/javascript" src="${ctx}/flat/js/base.js"></script>
<link href="${ctx}/flat/css/reset.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/flat/css/main.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/flat/plugins/datatable/skins/datatable_default.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/flat/plugins/Accormenus/skins/Accormenus_default.css" rel="stylesheet" type="text/css" />
</head>
<body>
<input type="hidden" id="columnId" name="columnId" value="${id}"/>
<input type="hidden" id="groupId" value="${groupId}"/>
<div class="mainpage">
  <div class="leftMenu">
    <div class="service-menu" id="groutName">
      <h1>部门列表</h1>
    </div>
  </div>
    <div class="list">
      <div class="searchArea">
        <table cellspacing="0" cellpadding="0">
          <tbody>
            <tr>
              <td style="width: 20%"></td>
              <td class="right" style="width: 80%"><label>类型：</label>
                <select id="notifyType">
                  <option value="0">全部类型</option>
                  <option value="1">部门新闻</option>
                  <option value="2">部门公告</option>
                  <option value="3">学习资料</option>
                </select>
                <label>关键字：</label>
				<span style="position:relative;">
				<input type="text" id="subject" placeholder="标题" class="formText searchkey" maxlength="50" />
				</span>
				<input hideFocus="" value="查 询" class="searchButton" type="button" id="searchButton"/>                               
				</td>
            </tr>
          </tbody>
        </table>
      </div>
      <table cellpadding="0" cellspacing="0"  class="pretty dataTable" id="myTable">
        <thead>
          <tr>
            <th>标题</th>
            <th style="width:100px;">类型</th>
            <th style="width:90px;">发布人</th>
            <th style="width:150px;">发布时间</th>
            <th style="width:90px;" class="right_bdr0">游览次数</th>
          </tr>
        </thead>
      </table>
    </div>
 </div>   
</body>
<script>funPlaceholder(document.getElementById("subject"));</script>
</html>
