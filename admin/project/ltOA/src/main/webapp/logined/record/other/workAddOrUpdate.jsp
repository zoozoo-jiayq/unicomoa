<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>工作经历-新增或修改</title>
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
<script type="text/javascript" src="${ctx}/flat/plugins/org/org.js"></script>
<script type="text/javascript" src="${ctx}/js/logined/record/common.js"></script>
<script type="text/javascript" src="${ctx}/js/common/validate_form.js"></script>
<script type="text/javascript" src="${ctx}/js/common/showError.js"></script>
<script type="text/javascript" src="${ctx }flat/plugins/maxlen_data/behaviour_min.js"></script>
<script type="text/javascript" src="${ctx }flat/plugins/maxlen_data/textarea_maxlen_min.js"></script>

<!-- 上传 start -->
<script type="text/javascript" src="${ctx}js/logined/upload/attachHelp.js"></script>   
<script type="text/javascript" src="${ctx}js/logined/cbb/attachment/attachment.js"></script>
<script type="text/javascript"
	src="${ctx}/js/logined/record/commonUpload.js"></script>
<script type="text/javascript"
	src="${ctx}/js/logined/record/other/workAddOrUpdate.js"></script>
<script>
		var contentInfo = '${recordWork.achievement}';
		window.UEDITOR_HOME_URL = basePath+"plugins/ueditor/";
</script>
<style type="text/css">
.inputTable th {
	width: 100px;
}
</style>
</head>
<body>
<input id="userId" value="${recordWork.userInfo.userId}" type="hidden">
<input id="workId" value="${recordWork.id}" type="hidden">
	<div class="formPage" style="width: 700px">
		<form action="#" name="userRecordForm">
			<div class="formbg">
				<div class="big_title">${empty recordWork.id?"新增工作经历":"修改工作经历"} </div>
				<div class="content_form">
					<table width="100%" border="0" cellpadding="0" cellspacing="0"
						class="inputTable">
						<tr>
							<th><label>姓名：</label></th>
							<td ><input type="text"   class="readOnlyText" style="width:200px" readonly="readonly" value="${recordWork.userInfo.userName}"/></td>
							<th><em class="requireField">*</em><label>担任职务：</label></th>
							<td><input type="text" class="formText" id="position"value="${recordWork.position}" maxlength="10" /></td>
						</tr>
						<tr>
							<th><em class="requireField">*</em><label>所在部门：</label></th>
							<td><input type="text" class="formText" id="department"value="${recordWork.department}" maxlength="15" /></td>
							<th><label>证明人：</label></th>
							<td><input type="text" class="formText" id="reterence"value="${recordWork.reterence}" maxlength="6" /></td>
						</tr>
						<tr>
							<th><em class="requireField">*</em><label>开始日期：</label></th>
							<td><input id="startDate" style="width:200px" type="text"
								value='<fmt:formatDate value="${recordWork.startDate}" pattern="yyyy-MM-dd"/>' class="Wdate formText"
								onFocus="WdatePicker({maxDate: '#F{$dp.$D(\'endDate\')}',skin:'default',dateFmt:'yyyy-MM-dd'})" />
								<input type="hidden" id="startDateHidden" /></td>
							<th><em class="requireField">*</em><label>结束日期：</label></th>
							<td><input id="endDate" style="width:200px" type="text"
								value='<fmt:formatDate value="${recordWork.endDate}" pattern="yyyy-MM-dd"/>' class="Wdate formText"
								onFocus="WdatePicker({minDate: '#F{$dp.$D(\'startDate\')}',skin:'default',dateFmt:'yyyy-MM-dd'})" />
								<input type="hidden" id="endDateHidden" /></td>
						</tr>

						<tr>
							<th><label>行业类别：</label></th>
							<td><input type="text" class="formText" id="industryCategory"value="${recordWork.industryCategory}" maxlength="25" />
							 </td>
							 <th><em class="requireField">*</em><label>工作单位：</label></th>
							<td><input type="text" class="formText" id="workUnit"value="${recordWork.workUnit}" maxlength="25" />
						   </td>
						</tr>
						<tr>
							<th><label>工作内容：</label></th>
							<td colspan="3"><textarea style="width: 510px" id="jobContent" rows="3"class="formTextarea" showremain="limitHomeJobContent" maxlength="255">${recordWork.jobContent}</textarea>
						    <br/>您还可以输入：<span id="limitHomeJobContent">255</span>字</td>
						</tr>
						<tr>
							<th><label>离职原因：</label></th>
							<td colspan="3"><textarea style="width: 510px" id="leavingReasons" rows="3"class="formTextarea" showremain="limitHomeLeavingReasons" maxlength="255">${recordWork.leavingReasons}</textarea>
						    <br/>您还可以输入：<span id="limitHomeLeavingReasons">255</span>字</td>
						</tr>
						<tr>
							<th>备注：</th>
							<td colspan="3"><textarea style="width: 510px" id="remark" rows="3"class="formTextarea" showremain="limitHomeRemark" maxlength="255">${recordWork.remark}</textarea>
						    <br/>您还可以输入：<span id="limitHomeRemark">255</span>字</td>
						</tr>
					    <tr>
							<th>主要业绩：</th>
							<td colspan="3">
								<script id="contentInfo" type="text/plain" style="width:100%"></script>
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
									<input type="hidden" id="attachmentId" value="${recordWork.attment}"/>
								    <input id="file_upload" name="fileupload" type="file" multiple="true" />
								    <!-- 上传队列 -->
								    <div id="queue"  style="display:none;"></div>
					               <div class="annex">
					                <ul id="attachmentList">
					                  <c:forEach items="${recordWork.attachmentList}" var="attachment" >
					                        <li><div class="icon"><em class="${attachment.attacthSuffix}"></em></div><div class="txt"><p>${attachment.attachName }</p><p><a style="cursor:pointer"  onclick="viewFile_record(${attachment.id})">预览</a> <a style="cursor:pointer"  onclick="deleteAttachment_record(${attachment.id},this)">删除</a></p></li>
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
					class="formButton_green" onclick="saveOrUpdateWork(this)" />
			</div>
		</form>
	</div>
</body>
</html>