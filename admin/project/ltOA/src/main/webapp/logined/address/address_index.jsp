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
	src="${ctx}js/logined/address/address_index.js"></script>
<script type="text/javascript"
	src="${ctx}js/logined/js_lang_cn.js"></script>

<title>通讯薄</title>
</head>
<body>
	<div class="mainpage">
		<!--左侧begin-->
		<div class="leftMenu" style="top: 3px;*top: 2px;_top: 2px;">
			<div class="pageTitle">
				<em class="iconList">通讯薄</em>
			</div>
			<table cellpadding="0" cellspacing="0" class="BlockTop">
				<tr>
					<td class="left"></td>
					<td class="center"><h2>
							<em class="op"></em>联系人分组
						</h2>
					</td>
					<td class="right"></td>
				</tr>
			</table>
			<div class="blockBox">
				<ul id="groupListMenu" class="menuList addressList">

				</ul>
				<h2>
					<em></em>共享通讯薄
				</h2>
				<ul id="shareGroupMenu" class="menuList addressList">
				</ul>
			</div>
			<table cellpadding="0" cellspacing="0" class="BlockBot">
				<tr>
					<td class="left"></td>
					<td class="center" onclick='getAddressByGroup("${ctx}logined/address/list_addressGroup.jsp")'><h3>
							<em></em>管理分组
						</h3>
					</td>
					<td class="right"></td>
				</tr>
			</table>
		</div>
		<!--左侧end-->
		<iframe class="iframeresize" id="address_index_main" style="width:100%;height:100%"
			name="address_index_main" frameborder="no"  hidefocus></iframe>
	</div>
</body>
</html>
