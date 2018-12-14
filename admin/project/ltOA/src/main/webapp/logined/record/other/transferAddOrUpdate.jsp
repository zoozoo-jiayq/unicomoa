<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>人事档案调动-新增或修改</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<jsp:include page="../../../common/flatHead.jsp" />
<script>
		var contentInfo = '${transfer.transferReason}';
		var transferType = "${transfer.type}";
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
	src="${ctx}/js/logined/record/other/transferAddOrUpdate.js"></script>
<style type="text/css">
.inputTable th {
	width: 110px;
}
.annex ul li{float: left}
</style>
</head>
<body>
<input type="hidden" value="${param.transferId}" id="transferId"/>
<input type="hidden" value="${transfer.userInfo.userId}" id="userId"/>
	<div class="formPage" style="width: 700px">
		<form  id="form1">
			<div class="formbg">
				<input type="hidden" id="transferId" value="${transfer.id}" />
				<div class="big_title">${empty transfer.id?"新增调动信息":"修改调动信息"}</div>
				<div class="content_form">
					<div class="small_title">基本信息</div>
					<table width="100%" border="0" cellpadding="0" cellspacing="0"
						class="inputTable">
						<tr>
							<th><label>姓名：</label></th>
							<td><input type="text"   class="readOnlyText" style="width: 190px" readonly="readonly" value="${transfer.userInfo.userName}"/></td>
							<th><em class="requireField">*</em><label>调动类型：</label></th>
							<td><select id="type"  style="width: 192px">
									<option value="">请选择</option>
									<option value="1">晋升</option>
									<option value="2">平调</option>
									<option value="3">降级</option>
									<option value="4">其他</option>
								</select>
							</td>
						</tr>
						<tr>
							<th><em class="requireField">*</em><label>调动日期：</label></th>
							<td><input id="transferDate" type="text"
								 class="Wdate formText"   style="width: 190px"
								onFocus="WdatePicker({maxDate: '#F{$dp.$D(\'effectiveDate\')}',skin:'default',dateFmt:'yyyy-MM-dd'})" value='<fmt:formatDate value="${transfer.transferDate}" pattern="yyyy-MM-dd"/>'/>
								<input type="hidden" id="transferDateHidden" />
								</td>
							<th><em class="requireField">*</em><label>调动生效日期：</label></th>
							<td><input id="effectiveDate" type="text"
								 class="Wdate formText"   style="width: 190px"
								onFocus="WdatePicker({minDate: '#F{$dp.$D(\'transferDate\')}',skin:'default',dateFmt:'yyyy-MM-dd'})" value='<fmt:formatDate value="${transfer.effectiveDate}" pattern="yyyy-MM-dd"/>'/>
								<input type="hidden" id="effectiveDateHidden" />
								</td>
						</tr>
						<tr>
							<th><label>调动前职务：</label></th>
							<td><input type="text" class="formText" id="beforePosition"
								value="${transfer.beforePosition}" maxlength="25" /></td>
							<th><label>调动后职务：</label></th>
							<td><input type="text" class="formText" id="postPosition"
								value="${transfer.postPosition}" maxlength="25" /></td>
						</tr>

						<tr>
							<th><em class="requireField">*</em><label>调动前部门：</label></th>
							<td>
								<div id="beforeGroupTreeDiv"
									style="z-index:66;position:relative">
									<input id="beforeGroupId" type="hidden" value="${transfer.beforeGroupId}"/> <input
										id="beforeGroupName" type="text" readonly="readonly"
										class="formText iconTree" valid="required" errmsg="transfer.beforeGroup_not_null" value="${transfer.beforeGroupName}"/>
									<div id="beforeGroupContent" style="position: absolute;display: none;">
										<ul id="beforeGroupTree" class="ztree"
											style="position: absolute; margin-top: 0; width: 190px;height:150px;overflow:auto; background: #ffffff;  border: 1px solid #8a9ba5"></ul>
									</div>
								</div>
							</td>
							<th><em class="requireField">*</em><label>调动后部门：</label></th>
							<td>
								<div id="postGroupTreeDiv" style="z-index:66;position:relative">
									<input id="postGroupId" type="hidden" value="${transfer.postGroupId}"/> <input
										id="postGroupName" type="text" readonly="readonly" value="${transfer.postGroupName}"
										class="formText iconTree" valid="required" errmsg="transfer.postGroup_not_null"/>
									<div id="postGroupContent" style="position: absolute;display: none;">
										<ul id="postGroupTree" class="ztree"
											style="position: absolute; margin-top: 0; width: 190px;height:150px;overflow:auto; background: #ffffff;  border: 1px solid #8a9ba5"></ul>
									</div>
								</div>
							</td>
						</tr>
						<!-- <tr>
							<th><em class="requireField">*</em><label>调动后角色：</label></th>
							<td>
								<div id="postRoleContentDiv" style="z-index:50;position:relative">
									<input id="postRoleId" type="hidden" /> <input
										id="postRoleName" type="text" readonly="readonly"
										class="formText iconTree" valid="required" errmsg="transfer.postRole_not_null"/>
									<div id="postRoleContent" style="position: absolute;display: none;">
										<ul id="postRoleTree" class="ztree"
											style="position: absolute; margin-top: 0; width: 200px;height:150px;overflow:auto; background: #ffffff;  border: 1px solid #8a9ba5"></ul>
									</div>
								</div>
							</td>
						</tr> -->
						<tr>
							<th>调动手续办理：</th>
							<td colspan="3"><textarea id="transferProcedure" rows="2"   style="width: 500px"
									class="formTextarea"  maxlength="255" showremain="limitTransferProcedure">${transfer.transferProcedure}</textarea>
									<br/>您还可以输入：<span id="limitTransferProcedure">255</span>字
									</td>
						</tr>
						<tr>
							<th>备注：</th>
							<td colspan="3"><textarea id="remark" rows="2"   style="width: 500px"
									class="formTextarea" maxlength="255" showremain="limitRemark">${transfer.remark}</textarea>
									<br/>您还可以输入：<span id="limitRemark">255</span>字
									</td>
						</tr>
						<tr>
							<th>调动原因：</th>
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
									<input type="hidden" id="attachmentId" value="${transfer.attment }"/>
								    <input id="file_upload" name="fileupload" type="file" multiple="true" />
								    <!-- 上传队列 -->
								    <div id="queue"  style="display:none;"></div>
					               <div class="annex">
					                <ul id="attachmentList">
					                	<c:forEach items="${transfer.attachmentList }" var="attach">
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
					class="formButton_green" onclick="addOrUpdateTransfer(this)" />
			</div>
		</form>
	</div>
</body>
</html>