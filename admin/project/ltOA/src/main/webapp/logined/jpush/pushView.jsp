<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<jsp:include page="../../common/taglibs.jsp" />
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>消息详情</title>
<link rel="shortcut icon" href="${ctx }images/favicon.ico"
	type="image/x-icon" />
<jsp:include page="../../common/head.jsp" />
<script type="text/javascript">
	var basePath = "${ctx}";
</script>
</head>
<body>
	<div class="input_new">
		<table border="0" cellspacing="0" cellpadding="0" class="listTitle">
            <tbody><tr>
                <td class="left"></td><td class="center">推荐活动详情</td><td class="right"></td>
            </tr>
            </tbody>
        </table>
		<table width="100%" cellspacing="0" cellpadding="0" border="0"
			class="inputTable">
			<tbody>
				<tr>
					<th>主题：</th>
					<td>${pushInfo.subject }</td>
				</tr>
				<tr>
					<th>发布人：</th>
					<td>${recomendName }</td>
				</tr>
				<tr>
					<th>发布时间：</th>
					<td>${pushInfo.pushTimeStr}</td>
				</tr>
				<tr>
					<th>内容：</th>
					<td><c:out escapeXml="false" value="${pushInfo.pushContent }"/></td>
				</tr>
				<tr>
					<th>发布范围：</th>
					<td>${names }</td>
				</tr>
			</tbody>
		</table>
		<div class="buttonArea">
			<input type="button" value="返 回" class="formButton_grey"
				id="back" onclick="javascript:window.location.href = document.referrer;"/>
		</div>
	</div>
</body>
</html>
