<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>人事档案培训-新增或修改</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<jsp:include page="../../../common/flatHead.jsp" />
<link rel="stylesheet" type="text/css"
	href="${ctx}plugins/upload/uploadify.css" />
<link href="${ctx}/flat/css/reset.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/flat/plugins/form/skins/form_default.css"
	rel="stylesheet" type="text/css" />
<link href="${ctx}/flat/plugins/annex/skins/annex_default.css"
	rel="stylesheet" type="text/css" />
<script type="text/javascript"
	src="${ctx}/plugins/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx}/js/logined/record/common.js"></script>
<script type="text/javascript" src="${ctx}/js/common/validate_form.js"></script>
<script type="text/javascript" src="${ctx}/js/common/showError.js"></script>
<!-- 上传 start -->
<script type="text/javascript" src="${ctx}js/logined/upload/attachHelp.js"></script>   
<script type="text/javascript" src="${ctx}js/logined/cbb/attachment/attachment.js"></script>
<script type="text/javascript"
	src="${ctx}/js/logined/record/commonUpload.js"></script>
<script type="text/javascript"
	src="${ctx}/js/logined/record/other/trainingAddOrUpdate.js"></script>
<style type="text/css">
.inputTable th {
	width: 110px;
}
.annex ul li{float: left}
</style>
</head>
<body>
<input type="hidden" value="${param.trainingId}" id="trainingId"/>
<input type="hidden" value="${recordTraining.userInfo.userId}" id="userId"/>
	<div class="formPage" style="width: 700px">
		<form  id="form1">
			<div class="formbg">
				<div class="big_title">${empty recordTraining.id?"新增培训信息":"修改培训信息"}</div>
				<div class="content_form">
					<div class="small_title">基本信息</div>
					<table width="100%" border="0" cellpadding="0" cellspacing="0"
						class="inputTable">
						<tr>
							<th><em class="requireField">*</em><label>培训计划：</label></th>
							<td colspan="3">
								<input type="text" class="formText" id="trainPlanName"  
								value="${recordTraining.trainPlanName}" maxlength="25" valid="required" errmsg="recordTraining.trainPlanName_not_null"/>
							</td>
						</tr>
						<tr>
							<th><em class="requireField">*</em><label>培训机构：</label></th>
							<td><input type="text" class="formText" id="trainMechanism"   style="width: 160px"
								value="${recordTraining.trainMechanism}" maxlength="25"  valid="required" errmsg="recordTraining.trainMechanism_not_null"/></td>
							<th><label>培训费用：</label></th>
							<td>
								<input type="text" class="formText" id="trainMoney" onkeyup="value=value.replace(/[^\d|\.]/g,'')"  style="width: 160px"
								value="${recordTraining.trainMoney}" maxlength="6" valid="isNumber" errmsg="recordTraining.trainMoney_not_isNumber"/>
							</td>
						</tr>
						<tr>
							<th><em class="requireField">*</em><label>培训日期：</label></th>
							<td><input id="trainDate" type="text"
								 class="Wdate formText" style="width: 160px"
								onFocus="WdatePicker({maxDate: '#F{$dp.$D(\'trainEndDate\')}',skin:'default',dateFmt:'yyyy-MM-dd'})" value='<fmt:formatDate value="${recordTraining.trainDate}" pattern="yyyy-MM-dd"/>'/>
								<input type="hidden" id="trainDateHidden" value="${recordTraining.trainDate}"/>
								</td>
							<th><label>结束日期：</label></th>
							<td><input id="trainEndDate" type="text"
								 class="Wdate formText" style="width: 160px"
								onFocus="WdatePicker({minDate: '#F{$dp.$D(\'trainDate\')}',skin:'default',dateFmt:'yyyy-MM-dd'})" value='<fmt:formatDate value="${recordTraining.trainEndDate}" pattern="yyyy-MM-dd"/>'/>
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
									<input type="hidden" id="attachmentId" value="${recordTraining.attment }"/>
								    <input id="file_upload" name="fileupload" type="file" multiple="true" />
								    <!-- 上传队列 -->
								    <div id="queue"  style="display:none;"></div>
					               <div class="annex">
					                <ul id="attachmentList">
					                	<c:forEach items="${recordTraining.attachmentList }" var="attach">
					                		<li><div class="icon"><em class="${attach.attacthSuffix}"></em></div><div class="txt" style="width:700px"><p>${attach.attachName}</p><p>
					                		<a style="cursor:pointer"  onclick="viewFile_record(${attach.id});">预览</a><a  style="cursor:pointer"  onclick="deleteAttachment_record(${attach.id},this);">删除</a></p></li>
					                	</c:forEach>
					               </ul>
							</td>
							</tr>
						</tbody>
					</table>

				</div>
				<!-- input div结束 -->
			</div>
			<div class="buttonArea" style="text-align: center">
				<input hideFocus="" value="保 存" type="button"
					class="formButton_green" onclick="addOrUpdateTraining(this)" />
			</div>
		</form>
	</div>
</body>
</html>