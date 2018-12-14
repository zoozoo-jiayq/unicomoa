<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>基础设置</title>
<%@ include file="../../common/taglibs.jsp"%>
<jsp:include page="../../common/head.jsp" />
<script type="text/javascript">
	var basePath = "${ctx}";
</script>
<script type="text/javascript"
	src="${ctx}js/logined/address/detail_address.js"></script>
<style>.info_details .touxiang{position:inherit;}</style>
</head>
<body style="display: none;">
    <input id="publicSign" type="hidden"
		value='<s:property value="#request.publicSign" />' />
	<input id="addressId" type="hidden"
		value='<s:property value="#request.address.id" />' />
	<input id="photo" type="hidden"
		value='<s:property value="#request.address.photo" />' />
	<input id="allowUpdateUserIds" type="hidden"
		value='<s:property value="#request.address.allowUpdateUserIds" />' />
	<input id="createUserId" type="hidden"
		value='<s:property value="#request.address.createUserInfo.userId" />' />
	<input id="sex" type="hidden"
		value='<s:property value="#request.address.sex" />' />
	<div class="list" style="margin-top: 3px;">
		<table cellpadding="0" cellspacing="0" class="BlockTop">
			<tr>
				<td class="left"></td>
				<td class="center">详情</td>
				<td class="right"></td>
			</tr>
		</table>
		<div class="TableBox" style="overflow:auto;">
			<div class="info_details" style="height:auto;">
		<img id="photoImg"  class="headpic fl" />
				<h3>
					<s:property value="#request.address.name" />
					<i> <s:if test="#request.address.sex==1">（男）</s:if> <s:if
							test="#request.address.sex==0">（女）</s:if>
					</i>
				</h3>
				<h4 class="birthday">
					<s:date name="#request.address.birthday" format="yyyy-MM-dd" />
				</h4>
				<h4 style="height:auto;">
					<s:property value="#request.address.companyName" /><s:if test="#request.address.companyName!=''&&#request.address.companyName!=null">&nbsp;&nbsp;&nbsp;&nbsp;</s:if><s:property value="#request.address.postInfo" />
				</h4>
				<div class="fr mt10"><input id="updateAddress" class="formButton"
						type="button" value="编 辑"  style="display: none;"/>&nbsp;&nbsp;<input type="button"
						id="shareAddress" style="display: none;" value="共享通讯录" class="formButtonlong" />
				</div>
				<div style="display: none;" class="mt10">
					<em class="sendMail" title="发送邮件"></em><em class="sendMsg"
						title="发送短信"></em>
				</div>
				<div class="clear"></div>
			</div>
			<ul class="tab">
				<li class="current"><a href="#">个人信息</a></li>
				<li><a href="#">其他信息</a></li>
				<li><a href="#">备注</a>
				</li>
			</ul>
			<div class="tabContent" style="display: block;overflow-y:auto;">
				<table width="100%" border="0" cellpadding="0" cellspacing="0" style="table-layout:fixed;"
					class="inputTable1 mt10">
					<tr>
						<th>工作单位</th>
						<td style="width: 35%"><s:property value="#request.address.companyName" />
						</td>
						<th>单位地址</th>
						<td><s:property value="#request.address.companyAddress" />
						</td>
					</tr>
					<tr>
						<th>单位邮编</th>
						<td><s:property value="#request.address.companyPostCode" />
						</td>
						<th>家庭地址</th>
						<td><s:property value="#request.address.familyAddress" />
						</td>
					</tr>
					<tr>
						<th>家庭邮编</th>
						<td><s:property value="#request.address.familyPostCode" />
						</td>
						<th>配偶</th>
						<td><s:property value="#request.address.wifeName" />
						</td>
					</tr>
					<tr>
						<th>子女</th>
						<td><s:property value="#request.address.children" />
						</td>
						<th>电子邮件</th>
						<td><s:property value="#request.address.familyEmail" />
						</td>
					</tr>
					<tr>
						<th>手机</th>
						<td><s:property value="#request.address.familyMobileNo" />
						</td>
						<th>工作电话</th>
						<td><s:property value="#request.address.officePhone" />
						</td>
					</tr>
					<tr>
						<th>工作传真</th>
						<td><s:property value="#request.address.officeFax" />
						</td>
						<th>家庭电话</th>
						<td><s:property value="#request.address.familyPhoneNo" />
						</td>
					</tr>
				</table>
			</div>
			<div class="tabContent">
				<table width="100%" border="0" cellpadding="0" cellspacing="0"
					class="inputTable1">
					<tr>
						<th>分组</th>
						<td><s:property value="#request.address.groupName" />
						</td>
					</tr>
					<tr>
						<th>排序号</th>
						<td><s:property value="#request.address.orderNo" />
						</td>
					</tr>
				</table>
			</div>
			<div class="tabContent">
				<div class="m_all_10">
					<s:property value="#request.address.remark" />
				</div>
			</div>
		</div>
	</div>
</body>
</html>