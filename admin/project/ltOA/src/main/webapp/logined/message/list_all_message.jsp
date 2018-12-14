<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>即时消息-消息查询</title>
<jsp:include page="../../common/flatHead.jsp" />
<link href="${ctx}flat/css/reset.css" rel="stylesheet" type="text/css" />
<link href="${ctx}flat/plugins/datatable/skins/datatable_page.css" rel="stylesheet" type="text/css" />
<link href="${ctx}flat/plugins/datatable/skins/datatable_default.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}flat/plugins/datatable/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="${ctx}flat/plugins/datatable/selecedForDatatablePagination.js"></script>
<script type="text/javascript" src="${ctx}flat/js/placeholder.js"></script>
<script type="text/javascript" src="${ctx}plugins/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx}flat/js/base.js"></script>
<script type="text/javascript" src="${ctx}js/logined/message/list_all_message.js"></script>
</head>
<body>
<div class="list">
  <div class="searchArea">
    <table cellspacing="0" cellpadding="0">
      <tbody>
        <tr>
          <td class="right">
          	<label>消息类型：</label>
            	<select id="msgType" name="smsType">
                  <option value='-1'>所有类型</option>
                   <option value='1'>网页消息</option>
                   <option value="2">移动版(Android)</option>
                </select>
            <label>起止时间：</label>
            <input id="startTime" name="paramValue" type="text" class="Wdate formText" style="width:170px;"
                    onfocus="WdatePicker({maxDate: '#F{$dp.$D(\'endTime\')}',skin:'default',dateFmt:'yyyy-MM-dd HH:mm:ss'})" />&nbsp;-&nbsp;
            <input id="endTime" name="paramValue" type="text" class="Wdate formText" style="width:170px;"
                    onfocus="WdatePicker({minDate: '#F{$dp.$D(\'startTime\')}',skin:'default',dateFmt:'yyyy-MM-dd HH:mm:ss'})" />
            <input type="button" id="confirm" class="searchButton" value="查询"/></td>
          <td style="width:184px;">
              <div class="fButton orangeBtn">
                <span class="delete" id="deleteBtn" title="删除对方未读的即时消息后，对方将不会接收到" >删除</span> 
              </div>
              <div class="btnseparator"></div>
              <div class="fButton blueBtn">
                <div> <span id="exportBtn" class="export">导出</span> </div>
              </div>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
  <table cellpadding="0" cellspacing="0"  class="pretty dataTable" id="myTable">
    <thead>
      <tr>
        <th class="chk"><input type="checkbox" id="total"/></th>
        <th style="width:100px;" id="msgType">消息类型</th>
        <th style="width:100px;" id="fromUserName">发信人</th>
        <th style="width:100px;" id="toUserName">收信人</th>
        <th style="width:100%;"  id="contentInfo" class="longTxt">内容</th>
        <th style="width:110px;" id="sendTime" class="right_bdr0">发送时间</th>
      </tr>
    </thead>
    <tbody>
     
    </tbody>
  </table>
</div>
<script>funPlaceholder(document.getElementById("searchkey"));</script>
</body>
</html>
