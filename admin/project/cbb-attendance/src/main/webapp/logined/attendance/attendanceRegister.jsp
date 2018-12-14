<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="java.text.SimpleDateFormat"%>
<jsp:include page="../../common/taglibs.jsp" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>考勤登记</title>
    <link href="${ctx }flat/css/reset.css" rel="stylesheet" type="text/css" />
    <link href="${ctx }flat/css/attendance.css" rel="stylesheet" type="text/css" />
    <link href="${ctx }flat/plugins/form/skins/form_default.css" rel="stylesheet" type="text/css" />
    <link href="${ctx }flat/plugins/datatable/skins/datatable_page.css" rel="stylesheet" type="text/css" />
    <link href="${ctx }flat/plugins/datatable/skins/datatable_default.css" rel="stylesheet" type="text/css" />
    <jsp:include page="../../common/flatHead.jsp" />
<script type="text/javascript" language="javascript" src="${ctx}js/logined/attendance/getCNDate.js"></script>
<script type="text/javascript" language="javascript" src="${ctx}js/logined/attendance/attendanceRegister.js"></script>
</head>

<body>
    <div class="formPage">
        <div class="formbg">
            <!-- <div class="punchClock">
      <div class="clockBox fl">
        <h3>2014-07-02</h3>
        <p class="clockCurrent">2</p>
        <p>星期三</p>
        <p>农历六月初六</p>
      </div>
      <div class="clockBtn fr">
        <h4>11:34 <span>35</span></h4>
        <p>
          <input name="" class="btn_blue" value="打卡" type="button" />
        </p>
      </div>
    </div> -->

            <div class="punchClock">
                <div class="clockBox fr clockDiv">
                <input type="hidden" id="day" value=""/>
                    <h3 id="date1"></h3>
                    <div class="fl clolcFont">
                        <p class="clockCurrent" id="date2"></p>
        				<p id="date3"></p>
        				<p id="date4"></p>
                    </div>
                    <div class="line fl"></div>
					<%
						Date date = new Date();
						SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm");
						SimpleDateFormat sdf2 = new SimpleDateFormat("ss");
						String HHMM = sdf1.format(date);
						String second = sdf2.format(date);
					%>
					<div class="fl">
                        <h4 >
                        	<span style="font-size:43px" id="nowTime"><%=HHMM %></span>
                        	<span id="nowTime_s" style="margin-left: -10px;"><%=second %></span>
                        </h4>
                    </div>
                </div>
                <div class="clockBtn">
                    <input  class="btn_grey" value="打卡" type="button" id="click"/>
                </div>
            </div>
            <h2 class="big_title">今日打卡记录</h2>
            <div class="p_all_5">
                <table cellpadding="0" cellspacing="0" class="pretty" style="margin-top:0;">
                    <thead>
                        <tr>
                            <th style="width:70px;">登记序号</th>
                            <th style="width:30%;">打卡时间</th>
                            <th style="width:70px;">来源</th>
                            <th style="width:70%;" class="right_bdr0">位置/IP</th>
                        </tr>
                    </thead>
                    <tbody id="tBody">
       				</tbody>
                </table>
            </div>
        </div>
    </div>
</body>

</html>
