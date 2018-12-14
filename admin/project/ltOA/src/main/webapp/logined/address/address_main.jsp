<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>联系人查看</title>
<%@ include file="../../common/taglibs.jsp"%>
<jsp:include page="../../common/head.jsp" />
<script type="text/javascript">
	var basePath = "${ctx}";
</script>
<script type="text/javascript"
	src="${ctx}js/logined/address/address_main.js"></script>
	<style>
	.addrTitile{padding-top:5px;height:25px; margin-bottom:5px;}
	.addrTitile span{ vertical-align:middle;margin-right:5px; display:block;float:left;}
	</style>
</head>
<body style="display:none;height:520px;overflow:hidden;">
	<input id="groupId" type="hidden" value="${paramValues.groupId[0]}" />
	<input id="type" type="hidden" value="${paramValues.type[0]}" />
	<input type="hidden" id="groupName" value='${paramValues.groupName[0]}' />
	<input type="hidden" id="publicSign"
		value='${paramValues.publicSign[0]}' />
	<input id="currentUserId" type="hidden"
		value='<s:property value="#session.adminUser.userId"/>' />

	<input id="groupName" type="hidden" />
	<div class="addrTitile" >
		<span class="ml10"><input id="addAddress" type="button" class="formButton" value="新 建" /></span>&nbsp;<span class="treeSearch"><input id="filter"
			type="text" value="" class="formText"  onkeyup="filterAddress()" /></span>
		<span><input id="searchAddress" class="formButton"
			type="button" value="高级查询" /></span>&nbsp;<span class="morebtn ml5"><a
			href="javascript:void(0);" class="m_btn" style="margin-top:3px">排序</a>
			<div class="m_btnlist">
				<p>
					<a href="javascript:void(0);" onclick="orderBy(1)">按序号（升）</a>
				</p>
				<p>
					<a href="javascript:void(0);" onclick="orderBy(2)">按序号（降）</a>
				</p>
				<p>
					<a href="javascript:void(0);" onclick="orderBy(3)">姓名（A-->Z）</a>
				</p>
				<p>
					<a href="javascript:void(0);" onclick="orderBy(4)">姓名（Z-->A）</a>
				</p>
			</div> </span>
	</div>
	<div class="mainpage">
		<!--左侧begin-->
		<div class="leftMenu" style="top: 3px;">
			<table cellpadding="0" cellspacing="0" class="BlockTop">
				<tr>
					<td class="left"></td>
			        <td class="center"><h2 style="width: 170px;" id="groupH2">联系人(<font id="groupNameStr"  class="td_font_title"></font>-<font id="groupNum">0</font>)</h2>
					</td>
					<td class="right"></td>
				</tr>
			</table>
			<div class="blockBox"  style="overflow:hidden;">
				<ul id="addressList" class="nameList"  style="height:500px" style="overflow-y: auto;">
				</ul>
			</div>
		</div>
		<!--左侧end-->
		<iframe id="address_main"
			style="width:100%; height:550px; overflow:hidden;" scrolling="no"
			name="address_main" frameborder="no" hidefocus></iframe>
	</div>
</body>
</html>
