<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="/affairShow" prefix="affairShow" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>新增日程</title>
<jsp:include page="/common/flatHead.jsp"/>
<script type="text/javascript">
	var basePath = '${ctx}';
    var update = '${bean.id}';
    var isAllDay = '${bean.isAllDay}';
    var hasEndDay = '${bean.hasEndDay}';
    var repeatTypeG = '${bean.repeatType}';
    var remaindTypeG = '${bean.remaindType}';

    var begHour = '${bean.begHour}';
    var begMinu = '${bean.begMinu}';

    var endHour = '${bean.endHour}';
    var endMinu = '${bean.endMinu}';

    var remaindHour = '${bean.remaindHour}';
    var remaindMinu = '${bean.remaindMinu}';
</script>

<link href="${ctx}/flat/css/reset.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/flat/plugins/form/skins/form_default.css" rel="stylesheet" type="text/css" />
<link href="${ctx }css/callendar.css" rel="stylesheet" type="text/css" />
<script language="javascript" type="text/javascript" src="${ctx}plugins/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx}js/common/jquery.lock.js"></script>
<script type="text/javascript" src="${ctx}js/logined/affairs/affairs_check.js"></script>
<script type="text/javascript" src="${ctx}flat/js/placeholder.js"></script>
<script type="text/javascript" src="${ctx}js/logined/cbb/affairShow/affairShow.js"></script>
<script type="text/javascript" src="${ctx}js/logined/programme/addProgramme.js"></script>
<style type="text/css">
.inputTable th{width:72px;}
.inputTable td{ position:inherit}
#affairShowId th{white-space:nowrap;}
.menu_box .menu_item A{ width:18px;}
</style>
</head>
<body class="bg_white">
<input type="hidden" value="${type}" id="type"/>
<div class="elasticFrame formPage">
			<div id="setHour" style="Z-INDEX: 1168; POSITION: absolute;"
				class="menu_obj">
				<div style="OVERFLOW-X: hidden; OVERFLOW-Y: auto;"
					class="m_body rounded3">
					<div>
						<ul id="hourUl" class="menu_box menu_bd menu_select_m">
						</ul>
					</div>
				</div>
			</div>
			
            <table width="100%" cellspacing="0" cellpadding="0" border="0" class="inputTable">
               <tbody><%--
            		  <tr class="firstEmpty">
                            <th></th>
                            <td></td>
                      </tr>
                      --%><tr>
                            <th><label>日程安排：</label></th>
                            <td ><textarea class="formTextarea" rows="5" placeholder="要安排什么事情呢？" id="dailyName">${bean.content}</textarea></td>
                      </tr>
                      <tr>
                          <th><label>开始时间：</label></th>
							<td ><input type="text" id="begTime" value='${bean.begTimeModify }'
								onClick="WdatePicker({maxDate: '#F{$dp.$D(\'endTime\')}',readOnly:true,skin:'default',dateFmt:'yyyy-MM-dd DD',onpicked:function(){dealRepeatType()}})"
								class="Wdate formText"style="width:150px" />&nbsp;&nbsp;
								 <span id='beginTimeSpan' class="mutil_input set_time_hm" >
								<input id="setHourInput" class="set_time_h" onclick="msg('setHour','setHourInput', event)" value="08" maxlength=2 onkeyup="this.value=this.value.replace(/[^0-9]/g,'')"/>:
								<input value="00" id="setMinuteInput" class="set_time_m" onclick="msg('setMinute','setMinuteInput', event)" maxlength=2 onkeyup="this.value=this.value.replace(/[^0-9]/g,'')" />
								</span>
								<label class="radio ml20"><input id="checkBoxEndTime"
									type="checkbox" value="" checked="checked"/>结束时间</label>&nbsp;<label class="radio"><input
									id="checkBoxAllDay" type="checkbox" value=""  />全天</label>
									<div id="setMinute"
				style="Z-INDEX: 1168; POSITION: absolute; display:none"
				class="menu_obj">
				<div style="OVERFLOW-X: hidden; OVERFLOW-Y: auto;"
					class="m_body rounded3">
					<div>
						<ul id="minu" class="menu_box menu_bd menu_select_m">
							<li class=menu_item value="00"><a href="javascript:void(0);"><i></i>00</a>
							</li>
							<li class=menu_item value="10"><a href="javascript:void(0);"><i></i>10</a>
							</li>
							<li class=menu_item value="20"><a href="javascript:void(0);"><i></i>20</a>
							</li>
							<li class=menu_item value="30"><a href="javascript:void(0);"><i></i>30</a>
							</li>
							<li class=menu_item value="40"><a href="javascript:void(0);"><i></i>40</a>
							</li>
							<li class=menu_item value="50"><a href="javascript:void(0);"><i></i>50</a>
							</li>
						</ul>
					</div>
				</div>
			</div>
							</td>
                      </tr>
                      <tr id="endTimeTr" >
							<th><label>结束时间：</label></th>
							<td ><input id="endTime" onchange=""
								onClick="WdatePicker({minDate: '#F{$dp.$D(\'begTime\')}',readOnly:true,skin:'default',dateFmt:'yyyy-MM-dd DD',onpicked:function(){dealRepeatType()}})" type="text"
								value="${bean.endTimeModify }" class="Wdate formText" style="width:150px" />&nbsp;&nbsp;
								<span id='endTimeSpan'  class="mutil_input set_time_hm">
								<input id="setEndHourInput" class="set_time_h" onclick="msg('setHour','setEndHourInput', event)"
									value="08" maxlength=2 onkeyup="this.value=this.value.replace(/[^0-9]/g,'')"/>:
								<input id="setEndMinuteInput" value="00" class="set_time_m" onkeyup="this.value=this.value.replace(/[^0-9]/g,'')"
										onclick="msg('setMinute','setEndMinuteInput', event)" maxlength=2 />
							</span></td>
						</tr>
                      <tr>
							<th><label>提醒设置：</label></th>
							<td><select id="remaindType"><option value="0">不提醒</option>
									<option value="1">同一天</option>
									<option value="2">前一天</option>
							</select>&nbsp;<span id='remaidSpan' class="mutil_input set_time_hm"
								style="display: none;">
								<input id="setRemaidHourInput"
									class="set_time_h" onclick="msg('setHour','setRemaidHourInput', event)"
									value="08" maxlength=2  type="text" onkeyup="this.value=this.value.replace(/[^0-9]/g,'')"/>:
									<input id="setRemaidMinuteInput" class="set_time_m" value="00"
										onclick="msg('setMinute','setRemaidMinuteInput', event)" maxlength=2 onkeyup="this.value=this.value.replace(/[^0-9]/g,'')"
										type="text"/>
										
							</span>&nbsp;<em id="tips"  class="tip" style="display:none">注：日程的开始时间同一天进行提醒</em>
								
							</td>

						</tr>
						 <tr>
			             <affairShow:affairShow moduleCode="rcgl" affairName="提醒方式" sendType="${bean.remindStyle }"/>
			            </tr>
              </tbody>
           </table>
</div>
<script>funPlaceholder(document.getElementById("dailyName"));</script>
</body>
</html>