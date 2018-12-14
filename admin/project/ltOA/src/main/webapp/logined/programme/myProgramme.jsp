<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>日程管理</title>
<jsp:include page="/common/flatHead.jsp"/>
<link href="${ctx}/flat/css/reset.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/flat/css/schedule.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/flat/plugins/datatable/skins/datatable_default.css" rel="stylesheet" type="text/css" />
<link type="text/css" rel="stylesheet" href="${ctx }css/cCrm_main.css" />
<!-- 日历框架的css -->
<link rel='stylesheet' type='text/css' href='${ctx}/plugins/calendar/fullcalendar/fullcalendar.css'/>
<link rel='stylesheet' type='text/css' href='${ctx}/plugins/calendar/fullcalendar/fullcalendar.print.css' media='print'/>
<link rel='stylesheet' type='text/css' href='${ctx}/plugins/calendar/cupertino/theme.css'/>
<!-- 日历框架的js -->
 <script type='text/javascript' src='${ctx}/js/common/jquery-ui-1.8.23.custom.min.js'></script>
<script type='text/javascript' src='${ctx}/plugins/calendar/fullcalendar/fullcalendar.min.js'></script>

<script type='text/javascript' src='${ctx}/js/logined/programme/programme.js'></script>
<script type='text/javascript' src='${ctx}/js/logined/programme/myProgramme.js'></script>


</head>
<body>
<!-- 用于对应js中的取值 -->
<input type="hidden" value="<%=request.getParameter("year") %>" id="year"/>
<input type="hidden" value="<%=request.getParameter("month") %>" id="month"/>

<div class="schedule">
	<div class="searchArea">
        <table cellspacing="0" cellpadding="0">
              <tbody>
              <tr>
                <td class="right">
                </td>
                <td style="width:192px;">
                        <div class="fButton greenBtn">
                              <span onclick="addCalendar();" class="add">新增</span>
                        </div>
                        <input type="button" value="查询" class="searchButton" style="margin-left:15px"  onclick="toCalendarSearch();"/>
                </td>
               </tr>
               </tbody>
         </table>
         <div  id='calendar'></div>
	</div>
   
</div>   
</body>
</html>
