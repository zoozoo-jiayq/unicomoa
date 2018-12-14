<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>事务提醒设置</title>
<jsp:include page="../../common/flatHead.jsp" />
<link href="${ctx}/flat/css/reset.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/flat/plugins/form/skins/form_default.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/flat/plugins/datatable/skins/datatable_page.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/flat/plugins/datatable/skins/datatable_default.css" rel="stylesheet" type="text/css" />
<script type="text/javascript"	src="${ctx}js/logined/affairs/affairs_manage.js"></script>
</head>
<body>
<div class="formPage">
  <div class="formbg">
      <div class="big_title">提醒设置</div>
      <div class="content_form">
		<table  cellpadding="0" cellspacing="0" class="pretty mt10" id="myTable">
			<thead>
				<tr>
				    <th class="num" id="no">序号</th>
					<th id="moduleName" class="longTxt">模块名称</th>
					<th style="width:100px;"><label class="radio"><input name="" type="checkbox" id="affairAll" onclick="selectAffairAll()" value="" />在线消息</label></th>
					<th style="width:100px;"><label class="radio"><input name="" type="checkbox" id="smsAll" onclick="selectSmsAll()" value="" />短信</label></th>
					<th style="width:100px;" class="right_bdr0"><label class="radio"><input name="" type="checkbox" id="pushAll" onclick="selectPushAll()" value="" />手机推送</label></th>
				</tr>
			</thead>
		</table>
		</div>
		</div>
		<div class="buttonArea">
			<input hideFocus="" class="formButton_green" value="保 存" type="button" onclick="updateAffairManage();"/>
			<span class="blue">点击保存，提交提醒设置。</span>
		</div>
	</div>
</body>
</html>
