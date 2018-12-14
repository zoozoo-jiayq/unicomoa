<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>生产进度列表</title>
<jsp:include page="../../common/flatHead.jsp" />
<link href="${ctx}flat/css/reset.css" rel="stylesheet" type="text/css" />
<link href="${ctx}flat/css/Reminder.css" rel="stylesheet" type="text/css" />
<link href="${ctx}flat/plugins/datatable/skins/datatable_default.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}flat/js/base.js"></script>
<script type="text/javascript" src="${ctx}flat/plugins/dialog/iframeTools.js"></script>
<script type="text/javascript" src="${ctx}js/logined/productionSchedule/productionScheduleList.js"></script>
<script type="text/javascript" src="${ctx}js/common/placeholder.js"></script>
<style>
.list{overflow:auto;}
#productionScheduleTable{table-layout:auto;}
#productionScheduleTable td,#productionScheduleTable th {text-overflow:ellipsis;white-space:nowrap;}
table tbody td.data_r{
	padding-right: 10px;
}
</style>
</head>
<body>
<div class="list">
		<div class="searchArea">
				<table cellspacing="0" cellpadding="0">
						<tbody>
								<tr>
                                    <td class="right">
                                                <label>起止日期：</label>
            <input type="text" id="startTime" onclick="WdatePicker({maxDate: '#F{$dp.$D(\'endTime\')}',skin:'default',dateFmt:'yyyy-MM-dd'})" class="formText Wdate"/>
            -
            <input type="text" id="endTime" onclick="WdatePicker({minDate: '#F{$dp.$D(\'startTime\')}',skin:'default',dateFmt:'yyyy-MM-dd'})" class="formText Wdate"/>
                                    <span class="fr">
									<input id="searchLoginUser" type="button" class="searchButton" value="查询"/></span>
                                    </td>
                                    <td style="width:184px;">
                                    	<div class="fButton greenBtn"><span id="addproductionSchedule" class="add">新增</span></div>
                                    	<div class="fButton greenBtn"><span id="uploadError" class="">上报</span></div>
                                    </td>
								</tr>
						</tbody>
				</table>
		</div>
		
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
                    <th class="oper right_bdr0" style="width:80px;">操作</th>
				</tr>
			</thead>
		</table>
	</div>
</div>
<script>funPlaceholder(document.getElementById("key_word"));</script>
</body>
</html>