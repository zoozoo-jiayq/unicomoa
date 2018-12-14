<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<% String id = request.getParameter("id");
	request.setAttribute("id",id);
 %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>公告管理页面</title>
<jsp:include page="../../common/flatHead.jsp"/>
<link href="${ctx}/flat/css/reset.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/flat/plugins/datatable/skins/datatable_page.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/flat/plugins/datatable/skins/datatable_default.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}js/logined/notify/list.js"></script>
</head>
<body>
	<div class="list">
	<div class="searchArea">
	<input type="hidden" id="columnId" name="columnId" value="${id}"/>
			<table cellspacing="0" cellpadding="0">
			<tbody>
			   <tr>
			   <td class="right">
				  <label>类型：</label><select name="notifyType">
				  		<option value="">全部类型</option>
                    </select>          
                  <label>创建时间：</label>
				<input id="beginDate" name="beginDate" type="text" class="formText Wdate" onClick="WdatePicker({maxDate: '#F{$dp.$D(\'endDate\')}'})" style="width:110px;"/>-
				<input id="endDate" name="endDate" type="text" class="formText Wdate" onClick="WdatePicker({minDate: '#F{$dp.$D(\'beginDate\')}'})" style="width:110px;"/>  
				<label>关键字：</label><span style="position:relative;">
				<input type="text" id="subject" name="subject" placeholder="标题" class="formText" maxlength="50"/>
				</span>
				<input hideFocus="" value="查 询" class="searchButton" type="button"/>                                
				</td>
			   	<td style="width:396px;">
					<div class="fButton greenBtn">
						<span onclick="addNotify();" class="add">新增</span>
					</div>
					<div class="fButton orangeBtn">
						<span class="delete" onclick="deleteAll();">删除</span>
					</div>
					<div class="btnseparator"></div>
					<div class="fButton blueBtn">
						 <div><span class="setTop" onclick="topSet(1);">置顶</span></div>
					</div>
					<div class="fButton blueBtn">
						<div> <span class="cancelTop" onclick="canceltopSet(0);">取消置顶</span></div>
					</div>
				</td>
				</tr>
				</tbody>
			</table>
	</div>
	<table cellpadding="0" cellspacing="0"  class="pretty dataTable" id="myTable">
		<thead>
		 <tr>
        	<th class="chk" style="width:15px;"><input type="checkbox" id="totalCheck"/></th>
        	<th class="longTxt" style="width:100%">标题</th>
       		<th id="notifyType" style="width:100px;">类型</th>
        	<th id="username" style="width:90px;">创建人</th>
        	<th id="createDate" style="width:150px;">创建时间</th>
       	 	<th id="browse" class="data_r" style="width:75px;">浏览人数</th>
       	 	<th style="width:70px;">状态</th>
        	<th style="width:190px;" class="right_bdr0 data_l">操作</th>
      	</tr>	
		</thead>
	</table>
</div>
<script>funPlaceholder(document.getElementById("subject"));</script>
</body>
</html>
