<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>学习信息-新增或修改</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<jsp:include page="../../../common/flatHead.jsp" />
<script>
		window.UEDITOR_HOME_URL = basePath+"plugins/ueditor/";
</script>
<link rel="stylesheet" type="text/css"
	href="${ctx}plugins/upload/uploadify.css" />
<link href="${ctx}/flat/css/reset.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/flat/plugins/form/skins/form_default.css"
	rel="stylesheet" type="text/css" />
<link href="${ctx }flat/plugins/tree/skins/tree_default.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/flat/plugins/annex/skins/annex_default.css"
	rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx }flat/plugins/tree/skins/jquery.ztree.all-3.2.min.js"></script>
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
	src="${ctx}/js/logined/record/other/learnAddOrUpdate.js"></script>
<style type="text/css">
.inputTable th {
	width: 110px;
}
.annex ul li{float: left}
</style>
</head>
<body>
<input type="hidden" value="${param.learnId}" id="learnId"/>
<input type="hidden" value="${recordLearn.userInfo.userId}" id="userId"/>
	<div class="formPage" style="width: 700px">
		<form  id="form1">
			<div class="formbg">
				<input type="hidden" id="transferId" value="${recordLearn.id}" />
				<div class="big_title">${empty recordLearn.id?"新增学习信息":"修改学习信息"}</div>
				<div class="content_form">
					<div class="small_title">基本信息</div>
					<table width="100%" border="0" cellpadding="0" cellspacing="0"
						class="inputTable">
						<tr>
							<th><label>姓名：</label></th>
							<td><input type="text"   class="readOnlyText" style="width: 190px" readonly="readonly" value="${recordLearn.userInfo.userName}"/></td>
							<th><em class="requireField">*</em><label>所学专业：</label></th>
							<td><input type="text"   class="formText" style="width: 190px" value="${recordLearn.major}" id="major" maxlength="25"/>
							<input type="hidden" id="majorHidden" />
							</td>
						</tr>
						<tr>
							<th><label>开始日期：</label></th>
							<td><input id="startDate" type="text"
								 class="Wdate formText"  style="width: 190px"
								 onFocus="WdatePicker({maxDate: '#F{$dp.$D(\'endDate\')}',skin:'default',dateFmt:'yyyy-MM-dd'})"
								 value='<fmt:formatDate value="${recordLearn.startDate}" pattern="yyyy-MM-dd"/>'/>
								</td>
							<th><label>结束日期：</label></th>
							<td><input id="endDate" type="text"
								 class="Wdate formText"  style="width: 190px"
								 onFocus="WdatePicker({minDate: '#F{$dp.$D(\'startDate\')}',skin:'default',dateFmt:'yyyy-MM-dd'})"
								 value='<fmt:formatDate value="${recordLearn.endDate}" pattern="yyyy-MM-dd"/>'/>
								<input type="hidden" id="dateHidden" />
								</td>
						</tr>						
						<tr>
							<th><em class="requireField">*</em><label>所获学历：</label><input type="hidden" id="leducation" value="${recordLearn.education}"/></th>
							<td><select id="education" style="width: 192px">
									<option value="">请选择</option>
								</select><input type="hidden" id="educationHidden" />
							</td>
							<th><label>所获学位：</label><input type="hidden" id="ldegree" value="${recordLearn.degree}"/></th>
							<td><select id="degree" style="width: 192px">
									<option value="">请选择</option>
								</select>
							</td>
						</tr>
						<tr>
							<th><label>曾任班干：</label></th>
							<td><input type="text" class="formText" id="classCadre"
								value="${recordLearn.classCadre}" maxlength="25" /></td>
							<th><em class="requireField">*</em><label>证明人：</label></th>
							<td><input type="text" class="formText" id="reterence"
								value="${recordLearn.reterence}" maxlength="25" /><input type="hidden" id="reterenceHidden" /></td>
						</tr>
						<tr>
							<th><em class="requireField">*</em>所在院校：</th>
							<td><input type="text" class="formText" id="school"
								value="${recordLearn.school}" maxlength="50" />
									<input type="hidden" id="schoolHidden" />
							</td>
							<th>院校所在地：</th>
							<td><input type="text" class="formText" id="schoolAddress"
								value="${recordLearn.schoolAddress}" maxlength="50" /></td>
						</tr>
						<tr>
							<th>获奖情况：</th>
							<td colspan="3"><textarea id="award" rows="2"  style="width: 500px"
									class="formTextarea" maxlength="255"   showremain="limitAward">${recordLearn.award}</textarea>
									<br/><font color="#A8A8A8">您还可以输入：<span id="limitAward">255</span>字</font></td>
						</tr>
						<tr>
							<th>所获证书：</th>
							<td colspan="3"><textarea id="certificates" rows="2" style="width: 500px"
									class="formTextarea" maxlength="255"  showremain="limitCertificates">${recordLearn.certificates}</textarea>
									<br/><font color="#A8A8A8">您还可以输入：<span id="limitCertificates">255</span>字</font></td>
						</tr>
						<tr>
							<th>备注：</th>
							<td colspan="3"><textarea id="remark" rows="2" style="width: 500px"
									class="formTextarea" maxlength="255" showremain="limitRemark">${recordLearn.remark}</textarea>
									<br/><font color="#A8A8A8">您还可以输入：<span id="limitRemark">255</span>字</font></td>
						</tr>
						</tbody>
					</table>
					<div class="small_title">附件</div>
					<table width="100%" cellspacing="0" cellpadding="0" border="0"
						class="inputTable">
						<tbody>
							<tr>
								<td colspan="3" id="fileAttachTd">
									<input type="hidden" id="attachmentId" value="${recordLearn.attment }"/>
								    <input id="file_upload" name="fileupload" type="file" multiple="true" />
								    <!-- 上传队列 -->
								    <div id="queue"  style="display:none;"></div>
					               <div class="annex">
					                <ul id="attachmentList">
					                	<c:forEach items="${recordLearn.attachmentList }" var="attach">
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
					class="formButton_green" onclick="addOrUpdate(this)" />
			</div>
		</form>
	</div>
</body>
</html>