<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>人事档案职称-新增或修改</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<jsp:include page="../../../common/flatHead.jsp" />
<script>
		var contentInfo = '${recordTitle.remark}';
		var accessType = "${recordTitle.accessType}";
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
<script type="text/javascript" src="${ctx}/js/logined/record/common.js"></script>
<script type="text/javascript" src="${ctx}/js/common/validate_form.js"></script>
<script type="text/javascript" src="${ctx}/js/common/showError.js"></script>
<!-- 上传 start -->
<script type="text/javascript" src="${ctx}js/logined/upload/attachHelp.js"></script>   
<script type="text/javascript" src="${ctx}js/logined/cbb/attachment/attachment.js"></script>
<script type="text/javascript"
	src="${ctx}/js/logined/record/commonUpload.js"></script>
<script type="text/javascript"
	src="${ctx}/js/logined/record/other/titleAddOrUpdate.js"></script>
<style type="text/css">
.inputTable th {
	width: 110px;
}
.annex ul li{float: left}
</style>
</head>
<body>
<input type="hidden" value="${param.titleId}" id="titleId"/>
<input type="hidden" value="${recordTitle.userInfo.userId}" id="userId"/>
	<div class="formPage" style="width: 700px">
		<form  id="form1">
			<div class="formbg">
				<div class="big_title">${empty recordTitle.id?"新增职称信息":"修改职称信息"}</div>
				<div class="content_form">
					<div class="small_title">基本信息</div>
					<table width="100%" border="0" cellpadding="0" cellspacing="0"
						class="inputTable">
						<tr>
							<th><label>姓名：</label></th>
							<td><input type="text"   class="readOnlyText" style="width: 190px" readonly="readonly" value="${recordTitle.userInfo.userName}"/></td>
							<th><label>批准人：</label></th>
							<td>
								<div id="approveUserTreeDiv"
									style="z-index:66;position:relative">
									<input id="approveUserId" type="hidden" value="${recordTitle.approveUser.userId}"/> <input
										id="approveUserName" type="text" readonly="readonly"
										class="formText iconTree" value="${recordTitle.approveUser.userName}"/>
									<div id="approveUserContent" style="position: absolute;display: none;">
										<ul id="approveUserTree" class="ztree"
											style="position: absolute; margin-top: 0; width: 190px;height:150px;overflow:auto; background: #ffffff;  border: 1px solid #8a9ba5"></ul>
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<th><em class="requireField">*</em><label>职称名称：</label></th>
							<td><input type="text" class="formText" id="title"
								value="${recordTitle.title}" maxlength="25" valid="required" errmsg="recordTitle.title_not_null"/></td>
							<th><em class="requireField">*</em><label>获取方式：</label></th>
							<td>
								<select style="width: 192px" id="accessType">
									<option value="">请选择</option>
									<option value="1">绩效评定</option>
									<option value="2">考试</option>
								</select>
							</td>
						</tr>
						<tr>
							<th><label>申报日期：</label></th>
							<td><input id="applyDate" type="text"
								 class="Wdate formText" style="width: 192px"
								onFocus="WdatePicker({maxDate: '#F{$dp.$D(\'giveDate\')}',skin:'default',dateFmt:'yyyy-MM-dd'})" value='<fmt:formatDate value="${recordTitle.applyDate}" pattern="yyyy-MM-dd"/>'/>
								<input type="hidden" id="applyDateHidden" />
								</td>
							<th><label>获取日期：</label></th>
							<td><input id="giveDate" type="text"
								 class="Wdate formText" style="width: 192px"
								onFocus="WdatePicker({minDate: '#F{$dp.$D(\'applyDate\')}',skin:'default',dateFmt:'yyyy-MM-dd'})" value='<fmt:formatDate value="${recordTitle.giveDate}" pattern="yyyy-MM-dd"/>'/>
								<input type="hidden" id="giveDateHidden" />
								</td>
						</tr>
						<tr>
							<th><label>下次申报职称：</label></th>
							<td><input type="text" class="formText" id="nextTitle"
								value="${recordTitle.nextTitle}" maxlength="25" /></td>
							<th><label>下次申报日期：</label></th>
							<td><input id="nextApplyDate" type="text"
								 class="Wdate formText" style="width: 192px"
								onFocus="WdatePicker({minDate: '#F{$dp.$D(\'applyDate\')}',skin:'default',dateFmt:'yyyy-MM-dd'})" value='<fmt:formatDate value="${recordTitle.nextApplyDate}" pattern="yyyy-MM-dd"/>'/>
								<input type="hidden" id="nextApplyDateHidden" />
								</td>
						</tr>
						<tr>
							<th><label>聘用职务：</label></th>
							<td><input type="text" class="formText" id="hiringPosition"
								value="${recordTitle.hiringPosition}" maxlength="25" /></td>
							<th><label>聘用单位：</label></th>
							<td><input type="text" class="formText" id="hiringUnits"
								value="${recordTitle.hiringUnits}" maxlength="25" />
								</td>
						</tr>
						<tr>
							<th><label>聘用开始日期：</label></th>
							<td><input id="hiringBeginDate" type="text"
								 class="Wdate formText" style="width: 192px"
								onFocus="WdatePicker({maxDate: '#F{$dp.$D(\'hiringEndDate\')}',skin:'default',dateFmt:'yyyy-MM-dd'})" value='<fmt:formatDate value="${recordTitle.hiringBeginDate}" pattern="yyyy-MM-dd"/>'/>
								</td>
							<th><label>聘用结束日期：</label></th>
							<td><input id="hiringEndDate" type="text"
								 class="Wdate formText" style="width: 192px"
								onFocus="WdatePicker({minDate: '#F{$dp.$D(\'hiringBeginDate\')}',skin:'default',dateFmt:'yyyy-MM-dd'})" value='<fmt:formatDate value="${recordTitle.hiringEndDate}" pattern="yyyy-MM-dd"/>'/>
								</td>
						</tr>
						<tr>
							<th>评定详情：</th>
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
									<input type="hidden" id="attachmentId" value="${recordTitle.attment }"/>
								    <input id="file_upload" name="fileupload" type="file" multiple="true" />
								    <!-- 上传队列 -->
								    <div id="queue"  style="display:none;"></div>
					               <div class="annex">
					                <ul id="attachmentList">
					                	<c:forEach items="${recordTitle.attachmentList }" var="attach">
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
					class="formButton_green" onclick="addOrUpdateTitle(this)" />
			</div>
		</form>
	</div>
</body>
</html>