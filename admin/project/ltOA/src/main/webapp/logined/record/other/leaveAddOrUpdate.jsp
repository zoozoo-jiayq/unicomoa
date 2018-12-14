<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>离职信息-新增或修改</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<jsp:include page="../../../common/flatHead.jsp" />
<script>
		var contentInfo = '${recordLeave.leaveReason}';
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
	src="${ctx}/js/logined/record/other/leaveAddOrUpdate.js"></script>
<style type="text/css">
.inputTable th {
	width: 110px;
}
.annex ul li{float: left}
</style>
</head>
<body>
<input type="hidden" value="${param.leaveId}" id="leaveId"/>
<input type="hidden" value="${recordLeave.userInfo.userId}" id="userId"/>
	<div class="formPage" style="width: 700px">
		<form  id="form1">
			<div class="formbg">
				<input type="hidden" id="transferId" value="${recordLeave.id}" />
				<div class="big_title">${empty recordLeave.id?"新增离职信息":"修改离职信息"}</div>
				<div class="content_form">
					<div class="small_title">基本信息</div>
					<table width="100%" border="0" cellpadding="0" cellspacing="0"
						class="inputTable">
						<tr>
							<th><label>姓名：</label></th>
							<td><input type="text"   class="readOnlyText" style="width: 190px" readonly="readonly" value="${recordLeave.userInfo.userName}"/></td>
							<th><label>担任职务：</label></th>
							<td><input type="text"   class="formText" style="width: 190px" value="${recordLeave.position}" id="position" maxlength="25"/></td>
						</tr>
						<tr>
							<th><em class="requireField">*</em><label>离职类型：</label><input type="hidden" id="lleaveType" value="${recordLeave.leaveType}"/></th>
							<td><select id="leaveType" style="width:192px">
									<option value="" >请选择</option>
								</select><input type="hidden" id="leaveTypeHidden" />
							</td>
							<th><label>申请日期：</label></th>
							<td><input id="applyDate" type="text" class="Wdate formText"  style="width:190px"
								onFocus="WdatePicker({maxDate: '#F{$dp.$D(\'intendedToLeaveDate\')}',skin:'default',dateFmt:'yyyy-MM-dd'})"
								value='<fmt:formatDate value="${recordLeave.applyDate}" pattern="yyyy-MM-dd"/>'/>
								<input type="hidden" id="applyHidden" />
							</td>
						</tr>
						<tr>
							<th><em class="requireField">*</em><label>拟离职日期：</label></th>
							<td><input id="intendedToLeaveDate" type="text" class="Wdate formText" style="width:190px"
								onFocus="WdatePicker({minDate: '#F{$dp.$D(\'applyDate\')}',skin:'default',dateFmt:'yyyy-MM-dd'})"
								value='<fmt:formatDate value="${recordLeave.intendedToLeaveDate}" pattern="yyyy-MM-dd"/>'/>
								<input type="hidden" id="intendedHidden" />
							</td>
							<th><em class="requireField">*</em><label>实际离职日期：</label></th>
							<td><input id="actualLeaveDate" type="text" class="Wdate formText" style="width:190px"
								onFocus="WdatePicker({minDate: '#F{$dp.$D(\'applyDate\')}',skin:'default',dateFmt:'yyyy-MM-dd'})"
								value='<fmt:formatDate value="${recordLeave.actualLeaveDate}" pattern="yyyy-MM-dd"/>'/>
								<input type="hidden" id="actualHidden" />
							</td>
						</tr>		
						<tr>
							<th><em class="requireField">*</em><label>工资截止日期：</label></th>
							<td><input id="wageCutoffDate" type="text"
								 class="Wdate formText" style="width:190px"
								onFocus="WdatePicker({skin:'default',dateFmt:'yyyy-MM-dd'})" value='<fmt:formatDate value="${recordLeave.wageCutoffDate}" pattern="yyyy-MM-dd"/>'/>
								<input type="hidden" id="wageHidden" />
							</td>													
							<th><label>离职部门：</label></th>
							<td><input type="text"   class="readOnlyText" style="width: 190px" readonly="readonly" value="${recordLeave.leaveDepartmentName}" id="leaveDepartmentName"/></td>		
						</tr>
						<tr>
							<th><label>离职当月薪资(元)：</label></th>							
							<td><input id="leaveTheMonthWage" type="text" style="width:190px" class="formText" 
								onkeyup="(this.v=function(){this.value=this.value.replace(/[^0-9-.]+/,'');}).call(this)" onblur="this.v();"   
								value="${recordLeave.leaveTheMonthWage}"  maxlength="6"/>
								<input type="hidden" id="leaveWageHidden" /></td>
						</tr>
						<tr>
							<th>去向：</th>
							<td colspan="3"><textarea id="whereabouts" rows="2"  style="width: 500px"
									class="formTextarea" maxlength="255"   showremain="limitWhereabouts">${recordLeave.whereabouts}</textarea>
									<br/><font color="#A8A8A8">您还可以输入：<span id="limitWhereabouts">255</span>字</font></td>
						</tr>
						<tr>
							<th>离职手续办理：</th>
							<td colspan="3"><textarea id="resignationProcedure" rows="2"  style="width: 500px"
									class="formTextarea" maxlength="255"  showremain="limitResignationProcedure">${recordLeave.resignationProcedure}</textarea>
									<br/><font color="#A8A8A8">您还可以输入：<span id="limitResignationProcedure">255</span>字</font></td>
						</tr>
						<tr>
							<th>备注：</th>
							<td colspan="3"><textarea id="remark" rows="2"  style="width: 500px"
									class="formTextarea" maxlength="255" showremain="limitRemark">${recordLeave.remark}</textarea>
									<br/><font color="#A8A8A8">您还可以输入：<span id="limitRemark">255</span>字</font></td>
						</tr>												
						<tr>
							<th>离职原因：</th>
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
									<input type="hidden" id="attachmentId" value="${recordLeave.attment }"/>
								    <input id="file_upload" name="fileupload" type="file" multiple="true" />
								    <!-- 上传队列 -->
								    <div id="queue"  style="display:none;"></div>
					               <div class="annex">
					                <ul id="attachmentList">
					                	<c:forEach items="${recordLeave.attachmentList }" var="attach">
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