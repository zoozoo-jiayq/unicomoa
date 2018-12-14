<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>日志概况</title>
	<jsp:include page="../../common/flatHead.jsp" />
	<link href="${ctx }flat/css/reset.css" rel="stylesheet" type="text/css" />
	<link href="${ctx }flat/plugins/form/skins/form_default.css" rel="stylesheet" type="text/css" />
	<link href="${ctx }flat/plugins/datatable/skins/datatable_default.css" rel="stylesheet" type="text/css" />
	<!-- table.css -->
	<script type="text/javascript" src="${ctx}flat/js/base.js"></script>
	<script type="text/javascript" src="${ctx}js/logined/log/logSumup.js"></script>
<style>
.inputTable th{ width:105px;}
</style>
</head>
<body>
<input type="hidden" id="gobackType"/>
<input type="hidden" id="max" value="10"/>
<div class="formPage">
      <div class="formbg">
                 <div class="big_title">日志概况</div>
                 <div class="content_form">
                   <h2 class="small_title">日志信息</h2>
                    <table width="100%" cellspacing="0" cellpadding="0" border="0" class="inputTable">
                            <tbody>
                                    <tr>
                                            <th>总统计天数：</th>
                                            <td><label id="allDay"></label></td>
                                            <th>总访问量：</th>
                                            <td><label id="allNum"></label></td>
                                    </tr>
                                    <tr>
                                            <th>今年访问量：</th>
                                           <td><label id="thisYearNum"></label></td>
                                            <th>本月访问量：</th>
                                           <td><label id="thisMonthNum"></label></td>
                                    </tr>
                                     <tr>
                                         <th>今日总访问量：</th><td><label id="todayNum"></label></td>
	        	<th>平均日访问量：</th><td><label id="averageNum"></label></td>
                                    </tr>
                            </tbody>
                    </table>
                    <h2 class="small_title">最新10条系统日志</h2>
                    <table cellpadding="0" cellspacing="0"  class="pretty dataTable" id="Table">
                                    <thead>
                                            <tr>
                                            <th style="width:150px;" id="insertTime">时间</th>
											<th style="width:90px;" id="userName">用户姓名</th>
                                            <th style="width:120px;" id="ip">IP地址</th>
                                            <th style="width:90px;" id="logType">日志类型</th>
                                            <th class="right_bdr0 longTxt" id="remark">备注</th>
                                            </tr>
                                    </thead>
	</table>
</div>
</div>
</div>
</body>
</html>