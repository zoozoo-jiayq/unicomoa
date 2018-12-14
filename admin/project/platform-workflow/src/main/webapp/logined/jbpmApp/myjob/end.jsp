<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>我的申请</title>
<jsp:include page="../../../common/flatHead.jsp"></jsp:include>
<link href="${ctx }/flat/css/reset.css" rel="stylesheet" type="text/css" />
<link href="${ctx }/flat/plugins/datatable/skins/datatable_default.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}js/common/placeholder.js"></script>
</head>

<body>
	<div class="list">
	     <div class="searchArea">
    <table cellspacing="0" cellpadding="0">
      <tbody>
        <tr>
         <td class="right">
            <label>申请时间：</label>
			<input id="beginDate" type="text" class="formText Wdate" onClick="WdatePicker({maxDate: '#F{$dp.$D(\'endDate\')}'})" style="width:110px;"/>-
			<input id="endDate" type="text" class="formText Wdate" onClick="WdatePicker({minDate: '#F{$dp.$D(\'beginDate\')}'})" style="width:110px;"/>  
          	<label>关键字：</label>
          	<span style="position:relative;">
            <input type="text" id="searchkey" class="formText searchkey" placeholder="申请名称/申请人">
            </span>
            <input type="button" class="searchButton" value="查询" id="search">
        </td>
        </tr>
      </tbody>
    </table>
  </div>

		<table id="myTable" cellpadding="0" cellspacing="0"  class="pretty dataTable">
			<thead>
			<tr>
				<th id="no" class="num">序号</th>
				<th id="createTime" style="width:150px">申请时间</th>
				<th id="title" class="longTxt" style="width:30%">申请名称</th>
				<th id="processType" class="longTxt" style="width:25%">流程名称</th>
				<th id="userName" style="width:15%">申请人</th>
				<th id="endTime" style="width:150px">办结时间</th>
				<th style="width:60px" class="right_bdr0 data_l">操作</th>	
			</tr>		
			</thead>
			
		</table>
	</div>
<script type="text/javascript" src="${ctx }/plugins/datatable/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="${ctx }/js/logined/jbpmApp/common.js"></script>
<script type="text/javascript" src="${ctx }/js/logined/jbpmApp/end.js"></script>
<script type="text/javascript">funPlaceholder(document.getElementById("searchkey"));</script>
</body>
</html>