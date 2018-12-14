<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<jsp:include page="../../common/taglibs.jsp" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>密码设置</title>
	<jsp:include page="../../common/head.jsp" />
</head>
<body>
<form id="form1"  action="${ctx }/sysset/saveSet.action"  method="post">
	<div class="input_new" style="position:relative;">
		<table class="listTitle" border="0" cellspacing="0" cellpadding="0">
     <tbody>
     	<tr>
        	<td class="left"></td><td class="center">系统通用设置</td><td class="right"></td>
        </tr>
     </tbody>
     </table>
	    <table width="100%" border="0" cellpadding="0" cellspacing="0"  class="inputTable" style="table-layout:auto">
	    	<tr>
	        	<th>审批人信息</th>
	        	<td><label class="radio"><input name="config.approve_widget" type="radio" value="1"  <c:if test="${sysConfig.approve_widget == '1'}">checked="checked" </c:if> />是</label><label class="radio"><input name="config.approve_widget" type="radio" value="2"  <c:if test="${sysConfig.approve_widget == '2'}">checked="checked" </c:if> />否</label><em class="gray_9">（审批控件输入内容为空时是否显示）</em></td>
	        </tr>
	    	<tr>
	            <th>阅读控件排序规则</th>
	            <td><label class="radio"><input name="config.reader_widget" type="radio" value="1"  <c:if test="${sysConfig.reader_widget == '1'}">checked="checked" </c:if> />按级别</label><label class="radio"><input name="config.reader_widget" type="radio" value="2"  <c:if test="${sysConfig.reader_widget == '2'}">checked="checked" </c:if> />按时间</label></td>
	        </tr>
	        <tr>
	        	<th>审批意见</th>
	        	<td><label class="radio"><input name="config.approve_comment" type="radio" value="1"  <c:if test="${sysConfig.approve_comment == '1'}">checked="checked" </c:if> />显示</label><label class="radio"><input name="config.approve_comment" type="radio" value="2"  <c:if test="${sysConfig.approve_comment == '2'}">checked="checked" </c:if> />隐藏</label><em class="gray_9">（审批人审批公文时是否需要填写意见）</em></td>
	        </tr>
	        <tr style="display:none">
	        	<th>删除审批中的工作流</th>
	        	<td><label class="radio"><input name="config.delete_official" type="radio" value="1"  <c:if test="${sysConfig.delete_official == '1'}">checked="checked" </c:if> />允许</label><label class="radio"><input name="config.delete_official" type="radio" value="2"  <c:if test="${sysConfig.delete_official == '2'}">checked="checked" </c:if> />禁止</label><em class="gray_9">（是否允许删除流转中的公文）</em></td>
	        </tr>
	       	<tr>
	        	<th>是否提醒修改默认密码</th>
	        	<td><label class="radio"><input name="config.notice_update_password" type="radio" value="yes"  <c:if test="${sysConfig.notice_update_password == 'yes'}">checked="checked" </c:if> />是</label><label class="radio"><input name="config.notice_update_password" type="radio" value="no"  <c:if test="${sysConfig.notice_update_password == 'no'}">checked="checked" </c:if> />否</label></td>
	        </tr>
	    </table>
	     <div class="buttonArea">
	    <input hideFocus="" id="submitButton"  class="formButton_green"  value="保 存" type="button"/>
	    <input  type="hidden" name="flag" id="flag" value="${flag}"/>
	  </div>
	</div>
	</form>
</body>
<script type="text/javascript" >
$("#submitButton").click(function(){
	$("#form1").submit();
});

      if($("#flag").val() == "success"){
    	     art.dialog({
    	           title:"消息",
    	           content:"操作成功！",
    	           width:320,
    	           height:110,
    	           icon:"succeed",
    	           opacity:0.3,
    	           ok:true
    	        });
      }
</script>
</html>