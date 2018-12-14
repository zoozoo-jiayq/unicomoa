<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<jsp:include page="../../common/taglibs.jsp" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>人员详情</title>
<jsp:include page="../../common/head.jsp" />
<script type="text/javascript">
	var basePath = "${ctx}";
</script>
<script type="text/javascript" src="${ctx}/js/logined/record/common.js"></script>
<script type="text/javascript" src="${ctx}/js/logined/record/show.js"></script>
</head>
<body>
	<div class="input" style="width:auto">
		
		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			class="inputTable" style="table-layout:auto">
			<tr>
				<th>姓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名</th>
				<td>${userRecord.userInfo.userName}</td>
				<th>部&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;门</th>
				<td>${userRecord.groupNames}</td>
				<td rowspan="5" style="width:110px;" valign="top">
			<img id="photoImg" src="${ctx}flat/plugins/form/skins/default/meeting.png" width="110px" height="140px" /> <input	type="hidden" id="photoUrlHidden" value="${userRecord.photoUrl}">
		</td>
			</tr>
			<tr>
				<th>职&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;务</th>
				<td>${userRecord.userInfo.job}</td>
				<th>性&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;别</th>
				<td><s:if test="%{userRecord.userInfo.sex==1}">
                男
            </s:if> <s:if test="%{userRecord.userInfo.sex==0}">
                女
            </s:if>
				</td>
			</tr>
			<tr>
				<th>生&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;日</th>
				<td><s:property value="#request.userRecord.userInfo.birthDay.substring(0,10)" /></td>
				<th>手&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;机</th>
				<td>${userRecord.userInfo.phone}</td>
			</tr>
			<tr>
				<th>E-mail</th>
				<td>${userRecord.userInfo.email}</td>
				<th>Q&nbsp;&nbsp;Q</th>
				<td>${userRecord.qq}</td>
			</tr>
			<tr>
				<th>MSN</th>
				<td>${userRecord.msn}</td>
				<th>联系电话</th><td>${userRecord.userInfo.phone2}</td>
			</tr>
		</table>
		</div>
</body>
</html>