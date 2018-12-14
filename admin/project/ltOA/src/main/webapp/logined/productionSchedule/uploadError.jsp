<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="java.text.SimpleDateFormat"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>上报事故日期</title>
<jsp:include page="../../common/flatHead.jsp"/>
<link href="${ctx}flat/css/reset.css" rel="stylesheet" type="text/css" />
<link href="${ctx}flat/plugins/form/skins/form_default.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}plugins/My97DatePicker/WdatePicker.js"></script>
<style type="text/css">
.inputTable th{width:100px;}
</style>
</head>
<body class="bg_white">
<div class="elasticFrame formPage">
            <table width="100%" cellspacing="0" cellpadding="0" border="0" class="inputTable">
               <tbody>
                      <tr>
                            <th><label>上次事故日期：</label></th>
                            <td><input name="" type="text" class="formText Wdate"  value="<%=new java.text.SimpleDateFormat("yyyy-MM-dd").format(new Date()) %>"  style="width:160px;" id="date"  
							  onclick="WdatePicker({skin:'default',dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-%d'})"/></td>
                      </tr>
              </tbody>
           </table>
</div>
</body>
</html>