<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<% String id = request.getParameter("id");
	request.setAttribute("id",id);
 %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>公告管理页面</title>
<jsp:include page="../../common/flatHead.jsp" />
<script type="text/javascript" src="${ctx}plugins/datatable/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="${ctx}js/common/jquery.cookie.js"></script>
<script language="javascript" type="text/javascript" src="${ctx}plugins/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx}js/logined/notify/list.js"></script>
<script type="text/javascript" src="${ctx}js/common/placeholder.js"></script>
<!-- 勿删  兼容西区和新版 吴洲 -->
<link href="${ctx}ydzj/css/main.css" rel="stylesheet" type="text/css" />
<link href="${ctx}css/main.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/flat/css/reset.css" rel="stylesheet" type="text/css" />
<link href="${ctx}ydzj/css/d_index.css" rel="stylesheet" type="text/css" />
<link href="${ctx}css/d_index.css" rel="stylesheet" type="text/css" />
<link href="${ctx}plugins/datatable/skins/datatable_default.css" rel="stylesheet" type="text/css" />
</head>
<body class="frame_class">
	<div class="list">
	<div class="searchArea">
	<input type="hidden" id="columnId" name="columnId" value="${id}"/>	
			<table cellspacing="0" cellpadding="0">
			<tbody>
			   <tr>
			   <td class="right">
				  <label>状态：</label>
				  	<select id="status" name="status">
				  		<option value="">全部</option>
				  		<option value="2">已发布</option>
				  		<option value="0">草稿</option>
                    </select>          
                  <label>创建时间：</label>
				<input id="beginDate" type="text" class="formText Wdate" onClick="WdatePicker({maxDate: '#F{$dp.$D(\'endDate\')}'})" style="width:110px;"/>-
				<input id="endDate" type="text" class="formText Wdate" onClick="WdatePicker({minDate: '#F{$dp.$D(\'beginDate\')}'})" style="width:110px;"/>  
				<label>关键字：</label><span style="position:relative;">
				<input type="text" id="subject" placeholder="标题" class="formText" maxlength="50"/>
				</span>
				<input hideFocus="" value="查 询" class="searchButton" type="button"/>                                
				</td>
			   	<td style="width:184px;">
					<div class="fButton greenBtn">
						<span onclick="addNotify();" class="add">新增</span>
					</div>
					<div class="fButton orangeBtn">
						<span class="delete" onclick="deleteAll();">删除</span>
					</div>
				</td>
				</tr>
				</tbody>
			</table>
	</div>
	<table cellpadding="0" cellspacing="0"  class="pretty dataTable" id="myTable">
		<thead>
		 <tr>
        	<th class="chk"><input type="checkbox"/></th>
        	<th style="width:100%">标题</th>
        	<th style="width:150px;">创建时间</th>
       	 	<th style="width:90px;">浏览人数</th>
       	 	<th style="width:70px;">状态</th>
        	<th style="width:190px;" class="right_bdr0">操作</th>
      	</tr>	
		</thead>
	</table>
</div>
<script>funPlaceholder(document.getElementById("subject"));</script>
</body>
</html>
