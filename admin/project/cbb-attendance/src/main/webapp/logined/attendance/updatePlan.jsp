<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../../common/taglibs.jsp" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>新增考勤方案</title>
<jsp:include page="../../common/flatHead.jsp"></jsp:include>
<link href="${ctx}flat/css/reset.css" rel="stylesheet" type="text/css" />
<link href="${ctx}flat/plugins/form/skins/form_default.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}js/common/validate_form.js"></script>
<script type="text/javascript" src="${ctx}js/logined/js_lang_cn.js"></script>
<script type="text/javascript" src="${ctx}js/common/showError.js"></script>
<script type="text/javascript" src="${ctx}/flat/plugins/org/org.js?version=${version}"></script>
<script type="text/javascript" src="${ctx}js/logined/attendance/updatePlan.js"></script>
<style type="text/css">
	.day{
		padding-left:100px;
	}
	.bt{
		padding-left:100px;
	}
</style>
</head>
<body>
	<input type="hidden" id="planId" value="${vo.id }"/>
	<div class="formPage" style="padding-top:20px;">
	  <div class="formbg">
	    <div class="big_title">新增考勤方案</div>
	    <div class="content_form">
		      <table width="100%" border="0" cellpadding="0" cellspacing="0"  class="inputTable">
		        <tbody>
			        <tr>
			          <th><label>考勤方案名称：</label></th>
			          <td>${vo.subject }</td>
			          <td></td>
			        </tr>
			        <tr>
			          <th><label>考勤人员：</label></th>
			          <td>
			          	<c:if test="${requestScope.count>0 }"><input type="button" class="formButton_grey" maxlength="16" id="users" onclick="javascript:selectUser();" value="已选择${requestScope.count}人"/></c:if>
			          	<c:if test="${requestScope.count<=0 }"><input type="button" class="formButton_grey" maxlength="16" id="users" onclick="javascript:selectUser();" value="请选择"/></c:if>
			          	<input type="hidden" id="userIds" value="${vo.userIds }"/>
			          </td>
			          <td></td>
			        </tr>
		        </tbody>
		      </table>
	    </div>
	    <div class="big_title">打卡时间</div>
	    <div class="content_form">
		      <table width="100%" border="0" cellpadding="0" cellspacing="0"  class="inputTable">
		        <tbody>
			        <tr>
			          <th><label>上午上班时间：</label></th>
			          <td><input type="text" class="formText Wdate" value="${vo.commonOn}" id="commonOn" readonly="readonly" onfocus="WdatePicker({onpicked:picked(1),skin:'default',dateFmt:'HH:mm',maxDate:'#F{$dp.$D(\'commonAmOff\')||\'24:00\'}'})"/></td>
			          <td></td>
			        </tr>
			         <tr>
			          <th><label>上午下班时间：</label></th>
			          <td><input type="text" class="formText Wdate" value="${vo.commonAmOff}" id="commonAmOff"  readonly="readonly" onfocus="WdatePicker({onpicked:picked(2),skin:'default',dateFmt:'HH:mm',minDate:'#F{$dp.$D(\'commonOn\')}',maxDate:'#F{$dp.$D(\'commonPmOn\')}'})"/></td>
			          <td></td>
			        </tr>
			        <tr>
			          <th><label>下午上班时间：</label></th>
			          <td><input type="text" class="formText Wdate" value="${vo.commonPmOn}" id="commonPmOn" readonly="readonly" onfocus="WdatePicker({onpicked:picked(1),skin:'default',dateFmt:'HH:mm',minDate:'#F{$dp.$D(\'commonAmOff\')}',maxDate:'#F{$dp.$D(\'commonOff\')||\'24:00\'}'})"/></td>
			          <td></td>
			        </tr>
			        <tr>
			          <th><label>下午下班时间：</label></th>
			          <td><input type="text" class="formText Wdate" value="${vo.commonOff}" id="commonOff"  readonly="readonly" onfocus="WdatePicker({onpicked:picked(2),skin:'default',dateFmt:'HH:mm',minDate:'#F{$dp.$D(\'commonPmOn\')}'})"/></td>
			          <td></td>
			        </tr>
			        <tr style="display: none">
			          <th><label>考勤日期：</label></th>
			          <td>
			          </td>
			          <td></td>
			        </tr>
			        <tr id="mon" style="display: none">
			          <th></th>
			          <td>
			          		<c:if test="${vo.monRest==0}">
			          			<label><input name="checkbox" type="checkbox" checked/>&nbsp;周一</label>
				          		<span class="day"><font>${vo.monOn}</font>-<font>${vo.monOff}</font></span>
				          		<span class="bt" style="display:none;">休息</span>
			          		</c:if>
			          		<c:if test="${vo.monRest==1}">
			          			<label><input name="checkbox" type="checkbox"/>&nbsp;周一</label>
				          		<span class="day" style="display:none;"><font>${vo.commonOn}</font>-<font>${vo.commonOff}</font></span>
				          		<span class="bt">休息</span>
			          		</c:if>
			          </td>
			          <td><a href="javascript:void(0);">更改上下班时间</a></td>
			        </tr>
			        <tr id="tues" style="display: none">
			          <th></th>
			          <td>
			         		<c:if test="${vo.tuesRest==0}">
			          			<label><input name="checkbox" type="checkbox" checked/>&nbsp;周二</label>
				          		<span class="day"><font>${vo.tuesOn}</font>-<font>${vo.tuesOff}</font></span>
				          		<span class="bt" style="display:none;">休息</span>
			          		</c:if>
			          		<c:if test="${vo.tuesRest==1}">
			          			<label><input name="checkbox" type="checkbox"/>&nbsp;周二</label>
				          		<span class="day" style="display:none;"><font>${vo.commonOn}</font>-<font>${vo.commonOff}</font></span>
				          		<span class="bt">休息</span>
			          		</c:if>
			          </td>
			          <td><a href="javascript:void(0);">更改上下班时间</a></td>
			        </tr>
			        <tr id="wed" style="display: none">
			          <th></th>
			          <td>
			          		<c:if test="${vo.wedRest==0}">
			          			<label><input name="checkbox" type="checkbox" checked/>&nbsp;周三</label>
				          		<span class="day"><font>${vo.wedOn}</font>-<font>${vo.wedOff}</font></span>
				          		<span class="bt" style="display:none;">休息</span>
			          		</c:if>
			          		<c:if test="${vo.wedRest==1}">
			          			<label><input name="checkbox" type="checkbox"/>&nbsp;周三</label>
				          		<span class="day" style="display:none;"><font>${vo.commonOn}</font>-<font>${vo.commonOff}</font></span>
				          		<span class="bt">休息</span>
			          		</c:if>
			          </td>
			          <td><a href="javascript:void(0);">更改上下班时间</a></td>
			        </tr>
			        <tr id="thur" style="display: none">
			          <th></th>
			          <td>
			          		<c:if test="${vo.thurRest==0}">
			          			<label><input name="checkbox" type="checkbox" checked/>&nbsp;周四</label>
				          		<span class="day"><font>${vo.thurOn}</font>-<font>${vo.thurOff}</font></span>
				          		<span class="bt" style="display:none;">休息</span>
			          		</c:if>
			          		<c:if test="${vo.thurRest==1}">
			          			<label><input name="checkbox" type="checkbox"/>&nbsp;周四</label>
				          		<span class="day" style="display:none;"><font>${vo.commonOn}</font>-<font>${vo.commonOff}</font></span>
				          		<span class="bt">休息</span>
			          		</c:if>
			          </td>
			          <td><a href="javascript:void(0);">更改上下班时间</a></td>
			        </tr>
			        <tr id="fri" style="display: none">
			          <th></th>
			          <td>
			          		<c:if test="${vo.friRest==0}">
			          			<label><input name="checkbox" type="checkbox" checked/>&nbsp;周五</label>
				          		<span class="day"><font>${vo.friOn}</font>-<font>${vo.friOff}</font></span>
				          		<span class="bt" style="display:none;">休息</span>
			          		</c:if>
			          		<c:if test="${vo.friRest==1}">
			          			<label><input name="checkbox" type="checkbox"/>&nbsp;周五</label>
				          		<span class="day" style="display:none;"><font>${vo.commonOn}</font>-<font>${vo.commonOff}</font></span>
				          		<span class="bt">休息</span>
			          		</c:if>
			          </td>
			          <td><a href="javascript:void(0);">更改上下班时间</a></td>
			        </tr>
			        <tr id="sat" style="display: none">
			          <th></th>
			          <td>
			          		<c:if test="${vo.satRest==0}">
			          			<label><input name="checkbox" type="checkbox" checked/>&nbsp;周六</label>
				          		<span class="day"><font>${vo.satOn}</font>-<font>${vo.satOff}</font></span>
				          		<span class="bt" style="display:none;">休息</span>
			          		</c:if>
			          		<c:if test="${vo.satRest==1}">
			          			<label><input name="checkbox" type="checkbox"/>&nbsp;周六</label>
				          		<span class="day" style="display:none;"><font>${vo.commonOn}</font>-<font>${vo.commonOff}</font></span>
				          		<span class="bt">休息</span>
			          		</c:if>
			          </td>
			          <td><a href="javascript:void(0);">更改上下班时间</a></td>
			        </tr>
			        <tr id="sun" style="display: none">
			          <th></th>
			          <td>
			          		<c:if test="${vo.sunRest==0}">
			          			<label><input name="checkbox" type="checkbox" checked/>&nbsp;周日</label>
				          		<span class="day"><font>${vo.sunOn}</font>-<font>${vo.sunOff}</font></span>
				          		<span class="bt" style="display:none;">休息</span>
			          		</c:if>
			          		<c:if test="${vo.sunRest==1}">
			          			<label><input name="checkbox" type="checkbox"/>&nbsp;周日</label>
				          		<span class="day" style="display:none;"><font>${vo.commonOn}</font>-<font>${vo.commonOff}</font></span>
				          		<span class="bt">休息</span>
			          		</c:if>
			          </td>
			          <td><a href="javascript:void(0);">更改上下班时间</a></td>
			        </tr>
		        </tbody>
		      </table>
	    </div>
	    
	    <div class="big_title">考勤地点</div>
	    <div class="content_form">
		      <table width="100%" border="0" cellpadding="0" cellspacing="0"  class="inputTable">
		        <tbody>
			        <tr>
			          <th><label>考勤地点：</label></th>
			          <td>
			          		<span id="locationStr" style="width:80%;" >${vo.location}</span>
			          		<a id="setLocation" href="javascript:void(0);">更改</a>
			          		<input type="hidden" id="location" value="${vo.location}" />
			          		<input type="hidden" id="longitude" value="${vo.longitude}"/>
			          		<input type="hidden" id="latitude" value="${vo.latitude}"/>
			          </td>
			          <td></td>
			        </tr>
			        <tr>
			          <th><label>允许偏差：</label></th>
			          <td>
			          	<select id="range">
			          		<option value="100" <c:if test="${vo.range==100}">selected</c:if>>100米</option>
			          		<option value="200" <c:if test="${vo.range==200}">selected</c:if>>200米</option>
			          		<option value="300" <c:if test="${vo.range==300}">selected</c:if>>300米</option>
			          		<option value="400" <c:if test="${vo.range==400}">selected</c:if>>400米</option>
			          		<option value="500" <c:if test="${vo.range==500}">selected</c:if>>500米</option>
			          		<option value="600" <c:if test="${vo.range==600}">selected</c:if>>600米</option>
			          		<option value="700" <c:if test="${vo.range==700}">selected</c:if>>700米</option>
			          		<option value="800" <c:if test="${vo.range==800}">selected</c:if>>800米</option>
			          		<option value="900" <c:if test="${vo.range==900}">selected</c:if>>900米</option>
			          		<option value="1000" <c:if test="${vo.range==1000}">selected</c:if>>1000米</option>
			          		<option value="2000" <c:if test="${vo.range==2000}">selected</c:if>>2000米</option>
			          		<option value="3000" <c:if test="${vo.range==3000}">selected</c:if>>3000米</option>
			          	</select>
			          </td>
			          <td></td>
			        </tr>
		        </tbody>
		      </table>
	    </div>
	  </div>
	 	<div class="buttonArea">
	            <input type="button" value="保 存" class="formButton_green" id="save"/>
	            &nbsp;&nbsp;
	            <input type="button" value="返 回" class="formButton_grey" id="back"/>
	    </div>
	</div>
	<script type="text/javascript">
		// 百度地图API功能
		function G(id) {
			return document.getElementById(id);
		}
		var map = new BMap.Map("l-map");
		map.centerAndZoom("北京",12);                   // 初始化地图,设置城市和地图级别。
	
		var ac = new BMap.Autocomplete(    //建立一个自动完成的对象
			{"input" : "location"
		});
	
		var myValue;
		ac.addEventListener("onconfirm", function(e,m) {    //鼠标点击下拉列表后的事件
			var _value = e.item.value;
			myValue = _value.province +  _value.city +  _value.district +  _value.street +  _value.business;
			setPlace();
		});
	
		function setPlace(){
			map.clearOverlays();    //清除地图上所有覆盖物
			function myFun(){
				var pp = local.getResults().getPoi(0).point;    //获取第一个智能搜索的结果
				$("#longitude").val(pp.lng);
				$("#latitude").val(pp.lat);
			}
			var local = new BMap.LocalSearch(map, { //智能搜索
			  onSearchComplete: myFun
			});
			local.search(myValue);
		}
	</script>
</body>
</html>

