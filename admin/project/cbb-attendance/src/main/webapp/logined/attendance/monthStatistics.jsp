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
		<link rel="stylesheet" href="${ctx}flat/plugins/tree/skins/tree_default.css" type="text/css"/>
		<script type="text/javascript" src="${ctx}flat/js/base.js"></script>
		<script type="text/javascript" src="${ctx}flat/js/placeholder.js"></script>
		<script type="text/javascript" src="${ctx}flat/plugins/Accormenus/skins/jquery.Accormenus.js"></script>
		<script type="text/javascript" src="${ctx}flat/plugins/tree/skins/jquery.ztree.all-3.2.min.js"></script>
		<script type="text/javascript" src="${ctx}js/common/treeNode.js"></script>
		<script type="text/javascript" src="${ctx}js/logined/attendance/userTree.js"></script>
		<script type="text/javascript" src="${ctx}js/logined/attendance/monthStatistics.js"></script>
		<script language="javascript" type="text/javascript" src="${ctx}plugins/My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript" src="${ctx}plugins/datatable/jquery.dataTables.min.js"></script>
		<script type="text/javascript" src="${ctx}plugins/datatable/selecedForDatatablePagination.js"></script>
		
		
		
</head>
 
  <body>
      <div class="mainpage">
          <div class="leftMenu">
            <div class="service-menu">
              <h1>组织结构</h1>
              <div class="zTreeDemoBackground">
                 <ul id="groupUserTree" class="ztree">
				  </ul>
              </div>
            </div>
          </div>
 <div class="list">
     <div class="searchArea">
    <table cellspacing="0" cellpadding="0">
      <tbody>
        <tr>
          <td class="right">
            <label>考勤状态：</label>
             <select  id="state">
                <option value="">全部</option>
                <option value="1">迟到</option>
             </select>
            <label>起止日期：</label>
             <input id="startTime"  value="<%=new SimpleDateFormat("yyyy-MM").format(new Date())+"-01" %>" readonly="readonly" class="formText Wdate" 
                 onfocus="WdatePicker({skin:'default',dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'endTime\')||\'2030-10-01\'}'})" />
            -
           <input  id="endTime"  value="<%=new SimpleDateFormat("yyyy-MM-dd").format(new Date()) %>"  readonly="readonly" class="formText Wdate" 
           		 onfocus="WdatePicker({skin:'default',dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'startTime\')}',maxDate:'<%=new SimpleDateFormat("yyyy-MM-dd").format(new Date()) %>'})" />
            <label>关键字：</label>
            <span class="position:relative;">
            <input type="text" style="width:200px" class="formText" placeholder="姓名" id="keyword" name="keyword" maxlength="5"/>
            </span>
            <input type="button" class="searchButton" id="searchRecords" value="查询"/>
         </td>
         <td style="width:92px;">
	            <input type="hidden" id="type" value="${param.type}" />
				<input type="hidden" id="groupId" name="groupId"  value="${param.groupId}" />
              <div class="fButton blueBtn">
              	<span class="export" id="exportAtten" >导出</span>
              </div>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
        <table cellpadding="0" cellspacing="0"  class="pretty dataTable" id="userTable">    
               <thead>
			        <tr>
			           <th class="num" rowspan="1">序号</th>
			           <th style="width:90px;" rowspan="1">姓名</th>
			           <th rowspan="1">部门</th>
			           <th rowspan="1">出勤天数</th>
			           <th rowspan="1">迟到次数</th>
			           <th rowspan="1">迟到时长</th>
			           <th rowspan="1">早退次数</th>
			           <th rowspan="1">早退时长</th>
			           <th rowspan="1" style="width: 85px;">上班缺卡次数</th>
			           <th rowspan="1" style="width: 85px;">下班缺卡次数</th>
			           <!--<th rowspan="1">旷工天数</th>
			            <th>出差</th> -->
			           <th>请假(天)</th>
			            <th>工作假(天)</th>
			             <th>公休假(天)</th>
			        </tr>
		      </thead>
                <tbody></tbody>
        </table>
    </div>
  </div>
  </body>
   <script>funPlaceholder(document.getElementById("keyword"));</script>
</html>
