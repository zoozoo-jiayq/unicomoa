<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>公告管理页面</title>
<jsp:include page="../../common/flatHead.jsp" />
<script type="text/javascript" src="${ctx}plugins/datatable/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="${ctx}js/common/jquery.cookie.js"></script>
<script language="javascript" type="text/javascript" src="${ctx}plugins/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx}js/logined/module/index.js"></script>
<script type="text/javascript" src="${ctx}js/common/placeholder.js"></script>
<link href="${ctx}/flat/css/reset.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/flat/plugins/datatable/skins/datatable_page.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/flat/plugins/datatable/skins/datatable_default.css" rel="stylesheet" type="text/css" />
</head>
<body>
	<div class="list">
       <div class="searchArea">
				<table cellspacing="0" cellpadding="0">
						<tbody>
								<tr>
                                  <td class="right"><label>关键字：</label>
                                    <span style="position:relative;">
                                    <input type="text" id="searchkey"  class="formText searchkey" placeholder="菜单名称/菜单代码" maxlength="50"></span>
                                    <input type="button" id="search" class="searchButton" value="查询">
                                 </td>
                                    <td style="width:277px;">
                                        <div class="fButton greenBtn">
                                              <span id="add" class="add">新增</span>
                                        </div>
                                         <div class="fButton greenBtn">
                                              <span id="importButton" class="import">导入</span>
                                        </div>
                                         <div class="fButton greenBtn">
                                              <span id="exportButton" class="export">导出</span>
                                        </div>
                                    </td>
								</tr>
						</tbody>
				</table>
		</div>
		<table id="myTable" cellpadding="0" cellspacing="0"  class="pretty dataTable">
			<thead>
					<tr>
                          <th style="width:40px;" id="no">编号</th>
                          <th style="width:120px" id="name" class="tdCenter">菜单名称</th>
                          <th style="width:80px" id="orderList" class="data_r">排序号</th>
                          <th style="width:100px;" id="code" class="longTxt">菜单代码</th>
                          <th style="width:70px;" id="level">菜单级别</th>
                          <th id="url" class="longTxt">页面路径/URL</th>
                          <th style="width:80px;" id="icon">图标</th>
                          <th style="width:80px;" id="statue">状态</th>
                          <th style="width:120px;" class="right_bdr0 oper">操作</th>
					</tr>
			</thead>
		</table>
</div>
<script>funPlaceholder(document.getElementById("searchkey"));</script>
</body>
</html>
