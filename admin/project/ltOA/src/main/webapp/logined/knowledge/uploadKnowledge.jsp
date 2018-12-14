<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>上传</title>
<jsp:include page="../../common/taglibs.jsp" />
<jsp:include page="../../common/flatHead.jsp" />
<link href="${ctx}flat/css/systemManagement.css" rel="stylesheet" type="text/css" />
<link href="${ctx}flat/css/reset.css" rel="stylesheet" type="text/css" />
<link href="${ctx}flat/plugins/form/skins/form_default.css" rel="stylesheet" type="text/css" />
<link href="${ctx}flat/plugins/annex/skins/annex_default.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="${ctx}flat/plugins/tree/skins/tree_default.css" type="text/css" />
<script type="text/javascript" src="${ctx}js/placeholder.js"></script>
<script type="text/javascript" src="${ctx}js/common/ajaxfileupload.js"></script>
<script type="text/javascript" language="javascript" src="${ctx}plugins/upload/jquery.uploadify.min.js" ></script>
<script type="text/javascript" src="${ctx}js/logined/knowledge/uploadKnowledge.js"></script>
<script type="text/javascript" src="${ctx}js/logined/knowledge/group.js?version=${version}"></script>
<script type="text/javascript" src="${ctx}js/logined/knowledge/konwledgeTypeTree.js?version=${version}"></script>
<script type="text/javascript" src="${ctx}js/common/treeNode.js?version=${version}"></script>
<style type="text/css">
 .inputTable th{ width:100px;}
</style>
</head>
<body class="bg_white">
<input id="typeId" type="hidden" value="${param.typeId }"/>
<input id="typeid" type="hidden" name="" value="${param.typeId }" />
<input type="hidden" id="treeNodeId" value="${param.treeNodeId }" />
<input type="hidden" id="isToView" value="" />
<div class="formPage " style="width:100%; padding-top:0">
     <table border="0" cellpadding="0" cellspacing="0" class="inputTable">
						<tr>
							<th><label>文件类型：</label></th>
							<td>
								<select id="fileType" onchange="selectFileType();">
									<option value="1">zip</option>
									<option value="2">其他</option>
								</select>
							</td>
						</tr>
						<tr>
							<th><label>分类名称：</label></th>
							<td>
							     <div id="treeContent">
									<input id="groupSel" type="text"
										value="" readonly="readonly"
										class="formText iconTree" style="width:300px" valid="required"
										errmsg="knowledge.knowledgeType_not_null"/> 
								<span class="selectdot" id="groupSel_div" style="width:40%"></span> 
									<input id="parentId" type="hidden" name="" value="0" /> 
									<input id="containAll" type="hidden" value="1" name="" />
									<div id="menuContent"
										style="position: absolute;display: none; z-index:3;">
										<ul id="groupUserTree" class="ztree"
											style="position: absolute; width:320px; margin-top: 0;height:150px;overflow:auto; background: #ffffff;  border: 1px solid #8a9ba5"></ul>
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<th><label>选择上传文件：</label></th>
							<td><div class="upload">
									<input id="file_upload" name="file_upload" type="file" multiple="true" />
								</div>
						    </td>
						</tr>
						<tr>
							<th></th>
							<td> 
								<p><span>仅支持后缀名为.html、.htm、.txt、.doc和.zip格式</span></p>
							    <p><span id="msg" style="color:red"></span></p>
						    </td>
						</tr>
	</table>					
						

	
    <!--  -->
    <!-- 上传成功后，文件位置 -->
    <input  id="fileNames" type="hidden"/>
</div>
</body>
</html>
