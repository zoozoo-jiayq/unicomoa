<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>生产进度概况</title>
	<jsp:include page="../../common/flatHead.jsp" />
	<link href="${ctx }flat/css/reset.css" rel="stylesheet" type="text/css" />
	<link href="${ctx }flat/plugins/form/skins/form_default.css" rel="stylesheet" type="text/css" />
	<link href="${ctx }flat/plugins/datatable/skins/datatable_default.css" rel="stylesheet" type="text/css" />
	<!-- table.css -->
	<script type="text/javascript" src="${ctx}flat/js/base.js"></script>
	<script type="text/javascript" src="${ctx}js/logined/productionSchedule/productionScheduleSumup.js"></script>
<style>

.inputTable th{ width:115px;}
.formPage{}
#productionScheduleTable_wrapper{overflow:auto;overflow-y:hidden;}
#productionScheduleTable{table-layout:auto;}
#productionScheduleTable td,#productionScheduleTable th {text-overflow:ellipsis;white-space:nowrap;}
table tbody td.data_r{
	padding-right: 10px;
}
</style>
</head>
<body>
<input type="hidden" id="gobackType"/>
<input type="hidden" id="max" value="10"/>
<div class="formPage">
      <div class="formbg">
                 <div class="big_title">
                 	生产进度概况
                 	<table cellspacing="0" cellpadding="0" style="float:right;">
						<tbody>
								<tr>
                                    <td class="left">
                                                <label>起止日期：</label>
            <input type="text" id="startTime" onclick="WdatePicker({maxDate: '#F{$dp.$D(\'endTime\')}',skin:'default',dateFmt:'yyyy-MM-dd'})" class="formText Wdate"/>
            -
            <input type="text" id="endTime" onclick="WdatePicker({minDate: '#F{$dp.$D(\'startTime\')}',skin:'default',dateFmt:'yyyy-MM-dd'})" class="formText Wdate"/>
                                    <span>
									<input id="searchLoginUser" type="button" class="searchButton" value="查询"/></span>
                                    </td>
								</tr>
						</tbody>
				</table>
                 </div>
                 <div class="content_form">
                   <h2 class="small_title">安全生产天数</h2>
                   <table width="100%" cellspacing="0" cellpadding="0" border="0" class="inputTable">
                         <tr>
                                            <th>安全生产情况：</th>
                                            <td>截止<label id="curDate"></label>，焦作车务段实现安全生产<label id="diff"></label>天</td>
                                    </tr>
                 </table>
                 
                   <h2 class="small_title">生产进度信息</h2>
                         	<div class="searchArea">
				
		</div>
                    <table width="100%" cellspacing="0" cellpadding="0" border="0" class="inputTable">
                            <tbody>
                            
                            
                                    <tr>
                                            <th>每日总装车：</th>
                                            <td><label id="dailyCarAssembly"></label></td>
                                            <th>停时：</th>
                                            <td><label id="stop"></label></td>
                                    </tr>
                                    <tr>
                                            <th title="运输收入较进度计划">运输收入较进度：</th>
                                           <td><label id="transportSchedule"></label></td>
                                            <th>煤装车：</th>
                                           <td><label id="coalLoading"></label></td>
                                    </tr>
                                    <tr>
                                            <th>中时：</th>
                                           <td><label id="middle"></label></td>
                                            <th>门到站：</th>
                                           <td><label id="doorToStand"></label></td>
                                    </tr>
                                    <tr>
                                            <th>两端收入：</th>
                                           <td><label id="bothincome"></label></td>
                                            <th>小矿煤：</th>
                                           <td><label id="smallCoalMine"></label></td>
                                    </tr>
                                    <tr>
                                            <th>大矿煤：</th>
                                           <td><label id="largeCoalMine"></label></td>
                                            <th>收入：</th>
                                           <td><label id="income"></label></td>
                                    </tr>
                                    <tr>
                                            <th>发送人：</th>
                                           <td><label id="sender"></label></td>
                                            <th>站到门：</th>
                                           <td><label id="standDoor"></label></td>
                                    </tr>
                                    <tr>
                                            <th>杂货：</th>
                                           <td><label id="groceries"></label></td>
                                            <th>运用车：</th>
                                           <td><label id="useOfVehicles"></label></td>
                                    </tr>
                                    <tr>
                                            <th>净载重：</th>
                                           <td><label id="deadLoad"></label></td>
                                            <th>装车数较计划：</th>
                                           <td><label id="comparedPlan"></label></td>
                                    </tr>
                                    <tr>
                                            <th>门到门：</th>
                                           <td><label id="doorToDoor"></label></td>
                                            <th>发送吨：</th>
                                           <td><label id="sendTons"></label></td>
                                    </tr>
                                    <tr>
                                            <th>发送吨较计划：</th>
                                           <td><label id="sendPlan"></label></td>
                                            <th>卸车：</th>
                                           <td><label id="unload"></label></td>
                                    </tr>
                                    <tr>
                                            <th>发送人较计划：</th>
                                           <td><label id="sendPersonPlan"></label></td>
                                    </tr>
                            </tbody>
                    </table>
                    <h2 class="small_title">生产进度列表</h2>
<table id="productionScheduleTable" cellpadding="0" cellspacing="0"  class="pretty dataTable">
			<thead>
				<tr>
					<th id="creatTime" style="width:100px;">日期</th>
					<th id="dailyCarAssembly" class="data_r">每日总装车</th>
					<th id="stop" class="data_r">停时</th>
					<th id="transportSchedule" class="data_r">运输收入较进度计划</th>
					<th id="coalLoading" class="data_r">煤装车</th>
					<th id="middle" class="data_r">中时</th>
					<th id="doorToStand" class="data_r">门到站</th>
					<th id="Bothincome" class="data_r">两端收入</th>
					<th id="smallCoalMine" class="data_r">小矿煤</th>
					<th id="largeCoalMine" class="data_r">大矿煤</th>
					<th id="income" class="data_r">收入</th>
					<th id="sender" class="data_r">发送人</th>
					<th id="standDoor" class="data_r">站到门</th>
					<th id="groceries" class="data_r">杂货</th>
					<th id="useOfVehicles" class="data_r">运用车</th>
					<th id="deadLoad" class="data_r">净载重</th>
					<th id="comparedPlan" class="data_r">装车数较计划</th>
					<th id="doorToDoor" class="data_r">门到门</th>
					<th id="sendTons" class="data_r">发送吨</th>
					<th id="sendPlan" class="data_r">发送吨较计划</th>
					<th id="unload" class="data_r">卸车</th>
					<th id="sendPersonPlan" class="data_r">发送人较计划</th>
				</tr>
			</thead>
		</table>
</div>
</div>
</div>
</body>
</html>