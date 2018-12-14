<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="java.text.SimpleDateFormat"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>生产进度修改</title>
    <jsp:include page="../../common/flatHead.jsp"/>
    <link href="${ctx }flat/css/reset.css" rel="stylesheet" type="text/css" />
    <link href="${ctx }flat/plugins/form/skins/form_default.css" rel="stylesheet" type="text/css" />
	<link href="${ctx }flat/plugins/datatable/skins/datatable_default.css" rel="stylesheet" type="text/css" />
		<style>
			img{cursor:pointer;}
			.inputTable th{width:110px;}
		</style>
	<link href="${ctx }flat/plugins/annex/skins/annex_default.css" rel="stylesheet" type="text/css" />
	<link href="${ctx }flat/plugins/tree/skins/tree_default.css" rel="stylesheet" type="text/css" />
   <link rel="stylesheet" type="text/css" href="${ctx}plugins/upload/uploadify.css"/>
    <script type="text/javascript" src="${ctx}/js/logined/productionSchedule/productionScheduleUpdate.js"></script>

    <script type="text/javascript" src="${ctx}plugins/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript">
	$(document).ready(function(){
	   $(".myphoto dt").hover(function() {
			$(this).find(".close").fadeToggle();
		});
	});
	</script>
</head>
<body>
<form action="#" id="userForm">
<input type="hidden" id="roleId"/>
<input type="hidden" id="assistId"/>
<input type="hidden" id=productionScheduleId value="${productionSchedule.id}"/>
<input type="hidden" id="groupId" value="${param.groupId}"/>
<div class="formPage">
  <div class="formbg">
    <div class="big_title">修改生产进度</div>
     
    <div class="content_form">
      <table width="100%" border="0" cellpadding="0" cellspacing="0"  class="inputTable">
        <tr>
          <th><label>日期：</label></th>
          <td>
               <input name="" type="text" class="formText Wdate"  value="${productionSchedule.rTimeStr}"  style="width:160px;" id="rTime"  
							  onclick="WdatePicker({skin:'default',dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'rTime\')}'})" disabled="disabled"/>
          </td>
        </tr>
        <tr>
          <th><label>每日总装车：</label></th>
          <td><input type="text" class="formText" maxlength="6" value="${productionSchedule.dailyCarAssembly} "
                          id="dailyCarAssembly" onkeyup="this.value=this.value.replace(/[^0-9\-\.]/g,'')"/></td>
          <th > <label>停时：</label></th>
                 <td><input type="text" class="formText" maxlength="6" value="${productionSchedule.stop} "
                          id="stop" onkeyup="this.value=this.value.replace(/[^0-9\-\.]/g,'')"/></td>   
        </tr>
        <tr>
        	<th><label title="运输收入较进度计划">运输收入较进度：</label></th>
      <td><input type="text" class="formText" maxlength="6"
                  value="${productionSchedule.transportSchedule} "  id="transportSchedule" onkeyup="this.value=this.value.replace(/[^0-9\-\.]/g,'')"/></td>  
                             	<th><label>煤装车：</label></th>
      <td><input type="text" class="formText" maxlength="6"
                  value="${productionSchedule.coalLoading} "        id="coalLoading" onkeyup="this.value=this.value.replace(/[^0-9\-\.]/g,'')"/></td>   
        </tr>
        <tr>
        	<th><label>中时：</label></th>
      <td><input type="text" class="formText" maxlength="6"
                  value="${productionSchedule.middle} "        id="middle" onkeyup="this.value=this.value.replace(/[^0-9\-\.]/g,'')"/></td>  
                          
          	<th><label>   门到站：</label></th>
      <td><input type="text" class="formText" maxlength="6"
                  value="${productionSchedule.doorToStand} "        id="doorToStand" onkeyup="this.value=this.value.replace(/[^0-9\-\.]/g,'')"/></td>            
        </tr>

        <tr>
        	<th><label> 两端收入：</label></th>
       <td><input type="text" class="formText" maxlength="6"
                  value="${productionSchedule.bothincome} "        id="Bothincome" onkeyup="this.value=this.value.replace(/[^0-9\-\.]/g,'')"/></td>   
          <th><label>小矿煤：</label></th>
           <td><input type="text" class="formText" maxlength="6"
                  value="${productionSchedule.smallCoalMine} "        id="smallCoalMine" onkeyup="this.value=this.value.replace(/[^0-9\-\.]/g,'')"/></td>   
        </tr>
        <tr>
        	<th><label> 大矿煤：</label></th>
       <td><input type="text" class="formText" maxlength="6"
                  value="${productionSchedule.largeCoalMine} "        id="largeCoalMine" onkeyup="this.value=this.value.replace(/[^0-9\-\.]/g,'')"/></td>   
          <th><label>收入：</label></th>
           <td><input type="text" class="formText" maxlength="6"
                value="${productionSchedule.income} "          id="income" onkeyup="this.value=this.value.replace(/[^0-9\-\.]/g,'')"/></td>   
        </tr>
        <tr>
        	<th><label> 发送人：</label></th>
       <td><input type="text" class="formText" maxlength="6"
                value="${productionSchedule.sender} "          id="sender" onkeyup="this.value=this.value.replace(/[^0-9\-\.]/g,'')"/></td>   
          <th><label>站到门：</label></th>
           <td><input type="text" class="formText" maxlength="6"
               value="${productionSchedule.standDoor} "           id="standDoor" onkeyup="this.value=this.value.replace(/[^0-9\-\.]/g,'')"/></td>   
        </tr>
        <tr>
        	<th><label> 杂货：</label></th>
       <td><input type="text" class="formText" maxlength="6"
                 value="${productionSchedule.groceries} "         id="groceries" onkeyup="this.value=this.value.replace(/[^0-9\-\.]/g,'')"/></td>   
          <th><label>运用车：</label></th>
           <td><input type="text" class="formText" maxlength="6"
                value="${productionSchedule.useOfVehicles} "          id="useOfVehicles" onkeyup="this.value=this.value.replace(/[^0-9\-\.]/g,'')"/></td>   
        </tr>
        <tr>
        	<th><label> 净载重：</label></th>
       <td><input type="text" class="formText" maxlength="6"
                 value="${productionSchedule.deadLoad} "         id="deadLoad" onkeyup="this.value=this.value.replace(/[^0-9\-\.]/g,'')"/></td>   
          <th><label>装车数较计划：</label></th>
           <td><input type="text" class="formText" maxlength="6"
                 value="${productionSchedule.comparedPlan} "         id="comparedPlan" onkeyup="this.value=this.value.replace(/[^0-9\-\.]/g,'')"/></td>   
        </tr>
        <tr>
        	<th><label> 门到门：</label></th>
       <td><input type="text" class="formText" maxlength="6"
                  value="${productionSchedule.doorToDoor} "        id="doorToDoor" onkeyup="this.value=this.value.replace(/[^0-9\-\.]/g,'')"/></td>   
          <th><label>发送吨：</label></th>
           <td><input type="text" class="formText" maxlength="6"
                 value="${productionSchedule.sendTons} "         id="sendTons" onkeyup="this.value=this.value.replace(/[^0-9\-\.]/g,'')"/></td>   
        </tr>
        <tr>
        	<th><label> 发送吨较计划：</label></th>
       <td><input type="text" class="formText" maxlength="6"
                 value="${productionSchedule.sendPlan} "         id="sendPlan" onkeyup="this.value=this.value.replace(/[^0-9\-\.]/g,'')"/></td>   
          <th><label>卸车：</label></th>
           <td><input type="text" class="formText" maxlength="6"
                  value="${productionSchedule.unload} "        id="unload" onkeyup="this.value=this.value.replace(/[^0-9\-\.]/g,'')"/></td>   
        </tr>
        <tr>
        	<th><label>发送人较计划：</label></th>
       <td><input type="text" class="formText" maxlength="6"
                 value="${productionSchedule.sendPersonPlan} "   id="sendPersonPlan" onkeyup="this.value=this.value.replace(/[^0-9\-\.]/g,'')"/></td>   
        
        </tr>
	</table>
    
  
  
    </div>
    
  <div class="buttonArea">
    <input type="button" class="formButton_green" value="确定"  id="userInfoUpdate"/>
    <input type="button" class="formButton_grey" value="返回" id="back" />
  </div>
    
  </div>
  
</div>
</form>
</body>
</html>