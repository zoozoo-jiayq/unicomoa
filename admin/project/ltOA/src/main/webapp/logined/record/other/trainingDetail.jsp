<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>人事档案培训-详情</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<jsp:include page="../../../common/flatHead.jsp" />
<link rel="stylesheet" type="text/css"
	href="${ctx}plugins/upload/uploadify.css" />
<link href="${ctx}/flat/css/reset.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/flat/plugins/form/skins/form_default.css"
	rel="stylesheet" type="text/css" />
<link href="${ctx }flat/plugins/tree/skins/tree_default.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/flat/plugins/annex/skins/annex_default.css"
	rel="stylesheet" type="text/css" />
<!-- 上传 start -->
<script type="text/javascript" src="${ctx}js/logined/upload/attachHelp.js"></script>   
<script type="text/javascript" src="${ctx}js/logined/cbb/attachment/attachment.js"></script>
<script type="text/javascript"
	src="${ctx}/js/logined/record/commonUpload.js"></script>
<style type="text/css">
.inputTable th {
	width: 110px;
}
.annex ul li{float: left}
</style>
</head>
<body>
	<div class="formPage" style="width: 700px">
			<div class="formbg">
				<div class="big_title">培训详情</div>
				<div class="content_form">
					<div class="small_title">基本信息</div>
					<table width="100%" border="0" cellpadding="0" cellspacing="0"
						class="inputTable">
						<tr>
							<th><label>培训计划：</label></th>
							<td colspan="3">${recordTraining.trainPlanName}</td>
						</tr>
						<tr>
							<th><label>培训机构：</label></th>
							<td>${recordTraining.trainMechanism}</td>
							<th><label>培训费用(元)：</label></th>
							<td>${empty recordTraining.trainMoney?"--":recordTraining.trainMoney}</td>
						</tr>
						<tr>
							<th><label>培训日期：</label></th>
							<td>
								<fmt:formatDate value="${recordTraining.trainDate}" pattern="yyyy-MM-dd"/>
								${empty recordTraining.trainDate?"--":""}
							</td>
							<th><label>结束日期：</label></th>
							<td>
								<fmt:formatDate value="${recordTraining.trainEndDate}" pattern="yyyy-MM-dd"/>
								${empty recordTraining.trainEndDate?"--":""}
							</td>
						</tr>
						</tbody>
					</table>
					<div class="small_title">附件</div>
					<table width="100%" cellspacing="0" cellpadding="0" border="0"
						class="inputTable">
						<tbody>
							<tr>
								<td colspan="3" id="fileAttachTd">
								
								<div class="annex">
					                <ul id="attachmentList">
					                	<c:forEach items="${recordTraining.attachmentList }" var="attach">
					                		<li><div class="icon"><em class="${attach.attacthSuffix}"></em></div><div class="txt" style="width:700px"><p>${attach.attachName}</p><p>
					                		<a style="cursor:pointer"  onclick="viewFile_record(${attach.id});">预览</a></p>
					                		<p><a  href="javascript:void(0);"  onclick="downFileById(${attach.id},this);">下载</a></p>
					                		</li>
					                	</c:forEach>
					                	<c:if test="${empty recordTraining.attachmentList }">无</c:if>
					               </ul>
					               </div>
							</td>
							</tr>
						</tbody>
					</table>

				</div>
				<!-- input div结束 -->
			</div>
	</div>
</body>
</html>