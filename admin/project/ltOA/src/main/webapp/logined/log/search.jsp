<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>日志查询列表</title>
	<jsp:include page="../../common/flatHead.jsp" />
	<!-- flat css js start-->
	<link href="${ctx }flat/css/reset.css" rel="stylesheet" type="text/css" />
	<link href="${ctx }flat/css/Reminder.css" rel="stylesheet" type="text/css" />
	<link href="${ctx }flat/plugins/datatable/skins/datatable_default.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="${ctx }flat/js/placeholder.js"></script>
	<script type="text/javascript" src="${ctx}plugins/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="${ctx}js/logined/log/search.js"></script>
</head>
<body>
<input type="hidden" id="gobackType"/>
<div class="list">
  <div class="searchArea">
 <input type="hidden" id="max" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" class="formText" value="300"/>
    <table cellspacing="0" cellpadding="0">
      <tbody>
        <tr>
          <td class="right"><label>日志类型：</label>
           <select id="logType">
                        <option value="0">所有日志</option>
                        <option value="1">登录日志</option>
                        <!-- 
                        <option value="2">密码错误</option>
                       <option value="3">用户名错误</option>-->
                   <!-- 
                        <option value="4">admin清空密码</option>
                        <option value="5">非法IP登录</option>  --> 
                        <option value="6">退出系统</option>
                        <option value="7">添加用户</option>
                        <option value="8">用户修改</option>
                        <option value="9">用户删除</option>
                      <!-- <option value="10">员工离职</option>   --> 
                        <option value="11">登录密码修改</option>
                        <option value="12">部门添加</option>
                        <option value="13">部门修改</option>
                        <option value="14">删除部门</option>
                       <!-- <option value="15">删除公告</option>-->
                        <!-- <option value="16">发送邮件</option>-->
                        <option value="17">邮件删除</option>
                       <!-- <option value="18">按模块设置管理范围</option></select></span> --> 
	        </select>
            <label>起止时间：</label>
            <input id="startTime" style="width:170px;" name="startTime" size="25"   onkeyup="value=value.replace(/(\s*$)/g,'')" onkeydown="FSubmit(event.keyCode||event.which);"  value=""  class="Wdate formText"  type="text" onFocus="WdatePicker({maxDate: '#F{$dp.$D(\'endTime\')}',skin:'default',dateFmt:'yyyy-MM-dd HH:mm'})"/>-<input id="endTime"  style="width:170px;" name="endTime"  size="25"   onkeyup="value=value.replace(/(\s*$)/g,'')" onkeydown="FSubmit(event.keyCode||event.which);"  value=""  class="Wdate formText"  type="text" onfocus="WdatePicker({minDate: '#F{$dp.$D(\'startTime\')}',skin:'default',dateFmt:'yyyy-MM-dd HH:mm'})"/>
            <label>关键字：</label>
            <span class="position:relative;">
			<input  type="text" id="loginUserName" class="formText  searchkey" placeholder="用户姓名"/>
            </span>
            <input class="searchButton" id="search" type="button" value="查询"/></td>
          <td style="width:120px;"><div class="fButton blueBtn"><span class="export"  id="exportLog">全部导出</span></div></td>
        </tr>
      </tbody>
    </table>
  </div>
  <table class="pretty dataTable"  cellspacing="0" cellpadding="0" id="Table">
    <thead>
      <tr>
        <th style="width:130px;" id="insertTime">时间</th>
        <th style="width:90px;" id="userName">用户姓名</th>
        <th style="width:130px;" id="ip">IP地址</th>
        <th style="width:100px;" id="logType">日志类型</th>
        <th class="right_bdr0 longTxt" id="remark">备注</th>
      </tr>
    </thead>
	</table>
</div>
</body>
<script>funPlaceholder(document.getElementById("loginUserName"));</script>
</html>