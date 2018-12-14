<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>日程查询</title>
<jsp:include page="../../common/flatHead.jsp"/>
<link href="${ctx}/flat/css/reset.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/flat/css/Reminder.css" rel="stylesheet" type="text/css" />
<link href="${ctx}flat/plugins/datatable/skins/datatable_page.css" rel="stylesheet" type="text/css" />
<link href="${ctx}flat/plugins/datatable/skins/datatable_default.css" rel="stylesheet" type="text/css" />
<link href="r" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}flat/plugins/datatable/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="${ctx}plugins/datatable/selecedForDatatablePagination.js"></script>
<script language="javascript" type="text/javascript" src="${ctx}plugins/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx}js/common/jquery.lock.js"></script>
<script type="text/javascript" src="${ctx}js/logined/programme/programme.js"></script>
<script type="text/javascript" src="${ctx}js/logined/programme/listProgramme.js"></script>
<style type="text/css">
	.buttonArea .formButton_grey{width:100px;height:30px;line-height:30px; text-align:center;outline:0;cursor:pointer;color:#555;font-size:16px;border:0; background:#dce7e9; border-radius:3px;margin-right:20px;}
</style>
</head>
<body>
<div class="list">
		<div class="searchArea">
				<table cellspacing="0" cellpadding="0">
						<tbody>
								<tr>
                                        <td class="right">
                                         <label>起止时间：<input name="Input32" onclick="WdatePicker({maxDate: '#F{$dp.$D(\'endTime\')}',skin:'default',dateFmt:'yyyy-MM-dd HH:mm'})" id="begTime" type="text"  class="Wdate formText" />&nbsp;-&nbsp;
																<input onclick="WdatePicker({minDate: '#F{$dp.$D(\'begTime\')}',skin:'default',dateFmt:'yyyy-MM-dd HH:mm'})" name="Input32" id="endTime" type="text"  class="Wdate formText" />
										</label>
																<input id="search" type="button" class="searchButton"  value="查询"/>
                                        </td>
                                        <td style="width:92px;"><div class="fButton orangeBtn"><span class="delete" >删除</span> </div></td>
								</tr>
						</tbody>
				</table>
		</div>
		<table class="pretty dataTable"  cellspacing="0" cellpadding="0" id="Table">
				<thead>
						<tr >
								<th class="chk"><input class="inptradio" type="checkbox"  id="total"/></th>
								<th style="width:130px;" id="begTimeStr">开始时间</th>
								<th style="width:130px;">结束时间</th>
								<th class="longTxt">内容</th>
								<th class="right_bdr0" style="width:80px;">操作</th>
						</tr>
				</thead>
				<tbody>
						
				</tbody>
		</table>
</div>
<div class="buttonArea">
      <input type="button"  hidefocus="" value="返回" class="formButton_grey" onclick="javacsript:window.location.href='${ctx}/logined/programme/myProgramme.jsp'">
    </div>
    <br/>
</body>
</html>
