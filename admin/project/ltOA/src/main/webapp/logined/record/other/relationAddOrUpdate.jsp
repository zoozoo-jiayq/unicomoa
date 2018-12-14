<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>关系-新增或修改</title>
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
	src="${ctx}/js/logined/record/other/relationAddOrUpdate.js"></script>
<style type="text/css">
.inputTable th {
	width: 137px;
}
.inputTable td .formTextarea {
    width: 96%;
}
</style>
</head>
<body>
<input id="userId" value="${recordRelation.userInfo.userId}" type="hidden">
<input id="recordRelationId" value="${recordRelation.id}" type="hidden">
	<div class="formPage" style="width: 700px">
		<form action="#" name="userRecordForm">
			<div class="formbg">
				<div class="big_title">${empty recordRelation.id?"新增关系信息":"修改关系信息"} </div>
				<div class="content_form">
					<table width="100%" border="0" cellpadding="0" cellspacing="0"
						class="inputTable">
						<tr>
							<th><label>单位员工：</label></th>
							<td ><input type="text"   class="readOnlyText" style="width:164px" readonly="readonly" value="${recordRelation.userInfo.userName}"/></td>
							<th><em class="requireField">*</em><label>成员姓名：</label></th>
							<td><input type="text" class="formText" id="memberName"value="${recordRelation.memberName}" maxlength="6" /></td>
						</tr>
						<tr>
							<th><em class="requireField">*</em><label>与本人关系：</label></th>
							<td >
							<select id="relation"style="width: 166px;" ></select><input type="hidden" id="relationId" value="${recordRelation.relation}"/>
							</td>
							<th><label>出生日期：</label></th>
							<td><input id="birthDate" style="width: 163px" type="text"
								value='<fmt:formatDate value="${recordRelation.birthDate}" pattern="yyyy-MM-dd"/>' class="Wdate formText"
								onFocus="WdatePicker({skin:'default',dateFmt:'yyyy-MM-dd'})" />
						</tr>
						<tr>
							<th><label>政治面貌：</label></th>
							<td><select id="politicalStatus"style="width: 166px;"></select><input type="hidden" id="politicalStatusId" value="${recordRelation.politicalStatus}"/></td>
							<th><label>职业：</label></th>
							<td><input type="text" class="formText" id="occupation"value="${recordRelation.occupation}" maxlength="15" /></td>
						</tr>

						<tr>
							<th><label>担任职务：</label></th>
							<td ><input type="text" class="formText" id="duties"value="${recordRelation.duties}" maxlength="10" /></td>
						    <th><em class="requireField">*</em><label>联系电话（个人）：</label></th>
							<td ><input type="text" class="formText" id="personalPhone"value="${recordRelation.personalPhone}" maxlength="12" /></td>
						</tr>
						<tr>
							<th><em class="requireField">*</em><label>联系电话（家庭）：</label></th>
							<td ><input type="text" class="formText" id="homePhone"value="${recordRelation.homePhone}" maxlength="12" /></td>
						    <th><label>联系电话（单位）：</label></th>
							<td ><input type="text" class="formText" id="workTelephone"value="${recordRelation.workTelephone}" maxlength="12" /></td>
						</tr>
						<tr>
							<th><label>工作单位：</label></th>
							<td ><input type="text" class="formText" id="workUnit"value="${recordRelation.workUnit}" maxlength="25" />
							</td>
							<th><label>单位地址：</label></th>
							<td> <input type="text" class="formText" id="unitAddress"value="${recordRelation.unitAddress}" maxlength="25" />
							</td>
						</tr>
						<tr>
							<th><em class="requireField">*</em><label>家庭地址：</label></th>
							<td colspan="3"><input type="text" class="formText" id="homeAddress"value="${recordRelation.homeAddress}" maxlength="25" />
							</td>
						</tr>
						<tr>
							<th>备注：</th>
							<td colspan="3"><textarea id="remark" rows="3"class="formTextarea" showremain="limitRemark" maxlength="255">${recordRelation.remark}</textarea>
							<br/>您还可以输入：<span id="limitRemark">255</span>字
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
									<input type="hidden" id="attachmentId" value="${recordRelation.attment}"/>
								    <input id="file_upload" name="fileupload" type="file" multiple="true" />
								    <!-- 上传队列 -->
								    <div id="queue"  style="display:none;"></div>
					               <div class="annex">
					                <ul id="attachmentList">
					                  <c:forEach items="${recordRelation.attachmentList}" var="attachment" >
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
				<input hideFocus="" value="保 存" type="button" class="formButton_green" onclick="saveOrUpdateRelation(this)" />
			</div>
		</form>
	</div>
</body>
</html>