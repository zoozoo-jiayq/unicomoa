<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
<script type="text/javascript" src="${ctx}js/logined/attendance/addPlan.js"></script>
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
	<div class="formPage" style="padding-top:20px;">
	  <div class="formbg">
	    <div class="big_title">新增考勤方案</div>
	    <div class="content_form">
		      <table width="100%" border="0" cellpadding="0" cellspacing="0"  class="inputTable">
		        <tbody>
			        <tr>
			          <th><label>考勤方案名称：</label></th>
			          <td><input type="text" class="formText" maxlength="16" id="subject"/></td>
			          <td></td>
			        </tr>
			        <tr>
			          <th><label>考勤人员：</label></th>
			          <td>
			          	<input type="button" class="formButton_grey" maxlength="16" id="users" onclick="javascript:selectUser();" value="请选择"/>
			          	<input type="hidden" id="userIds" value=""/>
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
			          <th><label>上班时间：</label></th>
			          <td><input type="text" class="formText Wdate" value="08:30" id="commonOn" readonly="readonly" onfocus="WdatePicker({onpicked:picked(1),skin:'default',dateFmt:'HH:mm',maxDate:'#F{$dp.$D(\'commonOff\')||\'24:00\'}'})"/></td>
			          <td></td>
			        </tr>
			        <tr>
			          <th><label>下班时间：</label></th>
			          <td><input type="text" class="formText Wdate" value="18:30" id="commonOff"  readonly="readonly" onfocus="WdatePicker({onpicked:picked(2),skin:'default',dateFmt:'HH:mm',minDate:'#F{$dp.$D(\'commonOn\')}'})"/></td>
			          <td></td>
			        </tr>
			        <tr>
			          <th><label>考勤日期：</label></th>
			          <td>
			          </td>
			          <td></td>
			        </tr>
			        <tr id="mon">
			          <th></th>
			          <td>
			          		<label><input name="checkbox" type="checkbox" checked/>&nbsp;周一</label>
			          		<span class="day"><font>08:30</font>-<font>18:30</font></span>
			          		<span class="bt" style="display:none;">休息</span>
			          </td>
			          <td><a href="javascript:void(0);">更改上下班时间</a></td>
			        </tr>
			        <tr id="tues">
			          <th></th>
			          <td>
			          		<label><input name="checkbox" type="checkbox" checked/>&nbsp;周二</label>
			          		<span class="day"><font>08:30</font>-<font>18:30</font></span>
			          		<span class="bt" style="display:none;">休息</span>
			          </td>
			          <td><a href="javascript:void(0);">更改上下班时间</a></td>
			        </tr>
			        <tr id="wed">
			          <th></th>
			          <td>
			          		<label><input name="checkbox" type="checkbox" checked/>&nbsp;周三</label>
			          		<span class="day"><font>08:30</font>-<font>18:30</font></span>
			          		<span class="bt" style="display:none;">休息</span>
			          </td>
			          <td><a href="javascript:void(0);">更改上下班时间</a></td>
			        </tr>
			        <tr id="thur">
			          <th></th>
			          <td>
			          		<label><input name="checkbox" type="checkbox" checked/>&nbsp;周四</label>
			          		<span class="day"><font>08:30</font>-<font>18:30</font></span>
			          		<span class="bt" style="display:none;">休息</span>
			          </td>
			          <td><a href="javascript:void(0);">更改上下班时间</a></td>
			        </tr>
			        <tr id="fri">
			          <th></th>
			          <td>
			          		<label><input name="checkbox" type="checkbox" checked/>&nbsp;周五</label>
			          		<span class="day"><font>08:30</font>-<font>18:30</font></span>
			          		<span class="bt" style="display:none;">休息</span>
			          </td>
			          <td><a href="javascript:void(0);">更改上下班时间</a></td>
			        </tr>
			        <tr id="sat">
			          <th></th>
			          <td>
			          		<label><input name="checkbox" type="checkbox"/>&nbsp;周六</label>
			          		<span class="day" style="display:none;"><font>08:30</font>-<font>18:30</font></span>
			          		<span class="bt">休息</span>
			          </td>
			          <td><a href="javascript:void(0);">更改上下班时间</a></td>
			        </tr>
			        <tr id="sun">
			          <th></th>
			          <td>
			          		<label><input name="checkbox" type="checkbox"/>&nbsp;周日</label>
			          		<span class="day" style="display:none;"><font>08:30</font>-<font>18:30</font></span>
			          		<span class="bt">休息</span>
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
			          		<span id="locationStr"></span>
			          		<a id="setLocation" href="javascript:void(0);">点击设置考勤地点</a>
			          		<input type="hidden" id="location"/>
			          		<input type="hidden" id="longitude" value=""/>
			          		<input type="hidden" id="latitude" value=""/>
			          </td>
			          <td></td>
			        </tr>
			        <tr>
			          <th><label>允许偏差：</label></th>
			          <td>
			          	<select id="range">
			          		<option value="100">100米</option>
			          		<option value="200">200米</option>
			          		<option value="300" selected>300米</option>
			          		<option value="400">400米</option>
			          		<option value="500">500米</option>
			          		<option value="600">600米</option>
			          		<option value="700">700米</option>
			          		<option value="800">800米</option>
			          		<option value="900">900米</option>
			          		<option value="1000">1000米</option>
			          		<option value="2000">2000米</option>
			          		<option value="3000">3000米</option>
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
</body>
</html>

