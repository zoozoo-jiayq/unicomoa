<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<jsp:include page="../../common/taglibs.jsp" />
<jsp:include page="../../common/flatHead.jsp" />
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript">
	var basePath = '${ctx}';
    window.UEDITOR_HOME_URL = basePath + "flat/plugins/ueditor/";
</script>
<title>新增知识库</title>

<link href="${ctx}flat/css/reset.css" rel="stylesheet" type="text/css" />
<link href="${ctx}flat/plugins/form/skins/form_default.css" rel="stylesheet" type="text/css" />
<link href="${ctx}flat/plugins/annex/skins/annex_default.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="${ctx}flat/plugins/tree/skins/tree_default.css" type="text/css" />
<style type="text/css">
.inputTable th {
	width: 82px;
}
.pla_label label{ margin-top:1px !important;}
</style>

<!-- 验证start -->
<script type="text/javascript" src="${ctx}js/common/showError.js?version=${version}"></script>
<!-- 验证end -->
<script type="text/javascript"
	src="${ctx}js/logined/knowledge/konwledgeTypeTree.js?version=${version}"></script>

<script type="text/javascript"
	src="${ctx}js/common/treeNode.js?version=${version}"></script>
<script type="text/javascript" charset="utf-8"
	src="${ctx}flat/plugins/ueditor/ueditor.config.js?version=${version}"></script>
<script type="text/javascript" charset="utf-8"
	src="${ctx}flat/plugins/ueditor/ueditor.all.min.js?version=${version}"></script>

<script type="text/javascript"
	src="${ctx}js/logined/knowledge/group.js?version=${version}"></script>
<script type="text/javascript"
	src="${ctx}js/logined/knowledge/addKnowledge.js?version=${version}"></script>
	
<style type="text/css">
	.uploadify{margin:7px 0px;}
</style>
</head>
<body>
<input id="columnId" type="hidden" value="${param.columnId }"/>
	<form id="addKnowledgeForm">
		<input type="hidden" id="isBeforeOrAfter" value="${param.isBeforeOrAfter}" /> 
		<input type="hidden" id="treeNodeId" value="${param.treeNodeId}" /> 
		<input type="hidden" id="isToView" value="${isToView}" /> 
		<input type="hidden" id="oldTitle" value="${knowledge.title}" /> 
		<input type="hidden" id="oldKeyword" value="${knowledge.keyword}" /> 
		<input type="hidden" id="oldAttachmentIds" value="${knowledge.attachmentIds}" /> 
		<input type="hidden" id="oldTypeName" value="${knowledge.knowledgeType.name }" /> 
		<span style="display:none;" id="oldContent">${knowledge.contentInfo}</span>
		<div class="formPage">
			<div class="formbg">
				<div class="big_title">
					<c:if test="${isToView==1}">修改<c:if test="${param.columnId==46 }">车站站细</c:if><c:if test="${param.columnId==47 }">规章制度</c:if></c:if>
					<c:if test="${isToView!=1}">新增<c:if test="${param.columnId==46 }">车站站细</c:if><c:if test="${param.columnId==47 }">规章制度</c:if></c:if>
				</div>
				<div class="content_form">
					<table width="100%" border="0" cellpadding="0" cellspacing="0"
						class="inputTable">
						<tr>
							<th><em class="requireField">*</em><label>标题：</label>
							</th>
							<td><span  class="pla_label"> <input name="knowledge.title"
									value="${knowledge.title}" id="title" type="text"
									class="formText searchkey" maxlength="100" valid="required"
									errmsg="knowledge.subject_not_null" placeholder="请输入标题，不超过100个字" />
							</span></td>
						</tr>
						<tr>
							<th><label>关键字：</label>
							</th>
							<td><span style="position:relative;" class="pla_label"> <input
									name="knowledge.keyword" value="${knowledge.keyword}"
									id="keyword" type="text" class="formText searchkey"
									maxlength="50" placeholder="请输入关键字，多个关键字以；分隔" /> </span></td>
						</tr>
						<tr>
							<th><em class="requireField">*</em><label>分类名称：</label>
							</th>
							<td>
								<div id="treeContent">
								<c:if test="${isToView!=1}">
									<input id="groupSel" type="text"
										value="${knowledge.knowledgeType.name }" readonly="readonly"
										class="formText iconTree" style="width:40%" valid="required"
										errmsg="knowledge.knowledgeType_not_null"/> 
								</c:if>
								<c:if test="${isToView==1}">
								<input id="groupSel" type="text"
										value="${knowledge.knowledgeType.name }" readonly="readonly"
										class="formText iconTree" style="width:40%" valid="required"
										errmsg="knowledge.knowledgeType_not_null" disabled="disabled"/> 
								</c:if>
								<span class="selectdot" id="groupSel_div"></span> <input id="typeid"
										type="hidden" name="" value="${knowledge.knowledgeType.vid}" />
									<input id="id" type="hidden" name="" value="${knowledge.vid}" />
									<input id="parentId" type="hidden" name=""
										value="${knowledge.knowledgeType.vid }" /> <input
										id="containAll" type="hidden" value="1" name="" />
									<div id="menuContent"
										style="position: absolute;display: none; z-index:99999;">
										<ul id="groupUserTree" class="ztree"
											style="position: absolute; width:373px; margin-top: 0;height:150px;overflow:auto; background: #ffffff;  border: 1px solid #8a9ba5"></ul>
									</div>
								</div></td>
						</tr>
						<tr>
							<th><em class="requireField">*</em><label>内容：</label>
							</th>
							<td><input id="contentInfoInput" type="hidden" /> <script
									id="contentInfo" type="text/plain" valid="required"
									errmsg="knowledge.content_not_null">${knowledge.contentInfo}</script>
							</td>
						</tr>
						<tr>
							<th><label>附件：</label></th>
							<td>
							    <input type="hidden" id="attachmentId" name="attachmentId" value="${knowledge.attachmentIds }" />
							    <input id="file_upload" name="fileupload" type="file" multiple="true" />
							    <!-- 上传队列 -->
							    <div id="queue"  style="display:none;"></div>
				               <div class="annex">
				                <ul id="attachmentList">
				                	<s:if test="#request.fileList!=null && #request.fileList.size()>0">
				                		<s:iterator id="f" value="#request.fileList">
				                			<li>
					                			<div class="icon">
					                				<em class="<s:property value="#f.attacthSuffix"/>"></em>
					                			</div>
				                				<div class="txt" style="width:700px">
					                				<p><s:property value="#f.attachName"/></p>
					                				<p><a  style="cursor:pointer"  onclick="deleteAttachment_knowledge('<s:property value="#f.id"/>',this);">删除</a></p>
				                				</div>
			                				</li>
				                		</s:iterator>
				                	</s:if>
				               </ul>
				               </div>
				            </td>
				         </tr>
					</table>
				</div>
			</div>
			<div class="buttonArea">
				<input type="button" id="addOrUpdate" class="formButton_green"
					value="提交" /> <input type="button"
					onclick="javascript:history.back()" class="formButton_grey"
					value="取消" />
			</div>
		</div>
	</form>
	<script>
		var title = document.getElementById("title").value;
        var keyword = document.getElementById("keyword").value;
        if (title == "") {
	        funPlaceholder(document.getElementById("title"));
        }
        if (keyword == "") {
	        funPlaceholder(document.getElementById("keyword"));
        } 
	</script>
</body>
</html>
