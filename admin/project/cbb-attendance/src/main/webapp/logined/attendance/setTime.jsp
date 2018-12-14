<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../../common/taglibs.jsp" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>资源设定</title>
<jsp:include page="../../common/flatHead.jsp"></jsp:include>
<link href="${ctx}flat/css/reset.css" rel="stylesheet" type="text/css" />
<link href="${ctx}flat/plugins/form/skins/form_default.css" rel="stylesheet" type="text/css" />
<style type="text/css">
.inputTable th{width:100px;}
</style>
</head>
<body class="bg_white">
<div class="elasticFrame formPage pt20">
<input type="hidden" id="week" value="${param.week}"/>
     <c:if test="${param.week=='mon' }">周一</c:if>
     <c:if test="${param.week=='tues' }">周二</c:if>
     <c:if test="${param.week=='wed' }">周三</c:if>
     <c:if test="${param.week=='thur' }">周四</c:if>
     <c:if test="${param.week=='fri' }">周五</c:if>
     <c:if test="${param.week=='sta' }">周六</c:if>
     <c:if test="${param.week=='sun' }">周日</c:if>
     考勤时间设定
  <table width="100%" cellspacing="0" cellpadding="0" border="0" class="inputTable">
    <tbody>
	   <tr>
        <th><label>上班时间：</label></th>
        <td><input type="text" class="formText Wdate" value="${param.onTime}" id="onTime" readonly="readonly" onfocus="WdatePicker({skin:'default',dateFmt:'HH:mm',maxDate:'#F{$dp.$D(\'offTime\')||\'24:00\'}'})"/></td>
      </tr>
	   <tr>
        <th><label>下班时间：</label></th>
        <td><input type="text" class="formText Wdate" value="${param.offTime}" id="offTime" readonly="readonly" onfocus="WdatePicker({skin:'default',dateFmt:'HH:mm',minDate:'#F{$dp.$D(\'onTime\')}'})"/></td>
      </tr>
     </tbody>
  </table>
</div>
</body>
</html>