<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>权限设置</title>
<jsp:include page="../../common/flatHead.jsp" />
<link href="${ctx}flat/css/reset.css" rel="stylesheet" type="text/css" />
<link href="${ctx}flat/plugins/form/skins/form_default.css" rel="stylesheet" type="text/css" />
<link href="${ctx }flat/plugins/datatable/skins/datatable_default.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}js/logined/secret/secretList.js"></script>
<style type="text/css">
  label.radio{ margin-top:7px; float:left;}
  label.radio input{ margin:0;}
</style>
</head>
<body>
   <div class="list">
	  <div class="searchArea">
	    <table cellspacing="0" cellpadding="0">
	      <tbody>
	        <tr>
	          <td class="right">&nbsp;</td>
	          <td style="width:184px">
	          	<div class="fButton greenBtn"><span class="add" onclick="javascript:toAdd();" >新增</span> </div>
	            <div class="fButton orangeBtn"><span class="delete" onclick="javascript:del();">删除</span> </div>
	          </td>
	        </tr>
	      </tbody>
	    </table>
	  </div>
	 <table cellpadding="0" cellspacing="0"  class="pretty dataTable" id="myTable">
	    <thead>
	      <tr>
	        <th class="num"><input type="checkbox" id="checkAll" /></th>
	        <th style="width:80px;" id="attributeName">保密字段</th>
	        <th class="longTxt" id="applyUserNames">控制范围</th>
	        <th class="longTxt" id="invisibleUserNames">不可查看人员</th>
	        <th style="width:80px;" class="right_bdr0">操作</th>
	      </tr>
	    </thead>
	    <tbody>
	    </tbody>
	  </table>
	</div>
</body>
</html>