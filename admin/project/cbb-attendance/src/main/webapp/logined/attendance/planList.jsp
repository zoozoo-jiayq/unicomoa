<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>考勤管理</title>
		<jsp:include page="../../common/flatHead.jsp" />
 		<link href="${ctx}flat/css/reset.css" rel="stylesheet" type="text/css" />
		<link href="${ctx}flat/css/main.css" rel="stylesheet" type="text/css" />
		<link href="${ctx}flat/plugins/datatable/skins/datatable_default.css" rel="stylesheet" type="text/css" />
		<link href="${ctx}flat/plugins/Accormenus/skins/Accormenus_default.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="${ctx}flat/js/base.js"></script>
		<script type="text/javascript" src="${ctx}flat/plugins/Accormenus/skins/jquery.Accormenus.js"></script>
		<script type="text/javascript" src="${ctx}plugins/datatable/jquery.dataTables.min.js"></script>
		<script type="text/javascript" src="${ctx}/flat/plugins/org/org.js?version=${version}"></script>
		<script type="text/javascript" src="${ctx}js/logined/attendance/planList.js"></script>
</head>
 
  <body>
  		<input type="hidden" id="planId" />
		 <div class="list" >
		     <div class="searchArea">
			    <table cellspacing="0" cellpadding="0">
			      <tbody>
			        <tr>
			          <td class="right">
			         </td>
			         <td style="width:92px;">
			              <!-- <div class="fButton blueBtn">
			              	<span class="add" id="toAdd" >新增</span>
			              </div> -->
			          </td>
			        </tr>
			      </tbody>
			    </table>
			  </div>
		        <table cellpadding="0" cellspacing="0"  class="pretty dataTable" id="myTable">    
		               <thead>
					        <tr>
					          <th id="no" class="num">序号</th>
					          <th id="subject" style="width:30%;">方案名称</th>
					          <th id="userCounts" style="width:90px;">人数</th>
					          <!-- <th id="detail" class="data_l" style="width:70%;">考勤时间</th> -->
					          <th style="width:200px;" class="right_bdr0">操作</th>
					        </tr>
				      </thead>
		                <tbody></tbody>
		        </table>
		    </div>
  </body>
</html>
