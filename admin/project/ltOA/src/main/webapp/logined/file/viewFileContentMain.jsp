<%@ page language="java" import="java.util.* ,java.net.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<jsp:include page="../../common/flatHead.jsp" />
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>知识管理-资料查询</title>
	<link href="${ctx}/css/qj-css.css" rel="stylesheet" type="text/css" />
	<style type="text/css" title="currentStyle">
		table.pretty tbody td.longTxt p a {
			word-break: keep-all; /* 不换行 */
			white-space: nowrap; /* 不换行 */
			overflow: hidden; /* 内容超出宽度时隐藏超出部分的内容 */
			text-overflow: ellipsis;
			/* 当对象内文本溢出时显示省略标记(...) ；需与overflow:hidden;一起使用。IE, Safari (WebKit)*/
			-o-text-overflow: ellipsis; /* 兼容Opera */
			text-align: left;
		}
		.upfileArea .uploadify{margin-top:3px;margin-right:4px;}
	</style>
<link href="${ctx}flat/css/reset.css" rel="stylesheet" type="text/css" />
<link href="${ctx}flat/css/knowledge.css" rel="stylesheet" type="text/css" />
<link href="${ctx}flat/plugins/datatable/skins/datatable_default.css" rel="stylesheet" type="text/css" />
<link href="${ctx}flat/plugins/Accormenus/skins/Accormenus_default.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}plugins/datatable/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="${ctx}plugins/datatable/selecedForDatatablePagination.js"></script>
		<script type="text/javascript"
			src="${ctx}js/logined/file/viewFileContent.js"></script>
		<script type="text/javascript" src="${ctx}js/logined/file/delFile.js"></script>
		<script type="text/javascript"
			src="${ctx}js/logined/file/checkFile.js"></script>
		<script type="text/javascript" src="${ctx}js/logined/file/downFile.js"></script>
		<script type="text/javascript" src="${ctx}js/logined/file/addFile.js"></script>
		<script type="text/javascript"
			src="${ctx}js/logined/file/delCatorlog.js"></script>
		<!-- 加入的批量上传的 -->
		<link rel="stylesheet" type="text/css"
			href="${ctx}plugins/upload/uploadify.css"/>
		<script type="text/javascript" language="javascript"
			src="${ctx}plugins/upload/jquery.uploadify.min.js"></script>
		<script type="text/javascript" src="${ctx}js/logined/file/upload.js"></script>
		<!-- 加入的批量上传的 -->
		<script type="text/javascript">
			var type = "${param.type}";
		</script>
	</head>
	<!--

	<body  style="display:none;height:520px;overflow:hidden;">
	-->
	<body style="display: none;">
		<input type="hidden" name="fileSortId" id="fileSortId" value="${param.fileSortId}"/>
			<input type="hidden" name="path" id="path" value="${param.path}"/>
			  <input type="hidden" name="fileTypeSort" id="fileTypeSort" value="<%=request.getParameter("fileTypeSort")%>" />
				<!--
				<div class="list" id="list" style="display: none; margin-top: 8px">
				-->
				<div class="list">
					<div class="searchArea">
						<table cellpadding="0" cellspacing="0" id="fileList" >
							<tr>
								<td class="right">&nbsp;</td>
           						<td style="width:92px;">
									<div class="fButton blueBtn" id="downFileContent" name="downFileContent">
										<div>
											<span class="load" onclick="downFile('${param.name}')">下载</span>
										</div>
									</div>
								</td>
								<td class="right"></td>
							</tr>
						</table>
					</div>
					<table cellpadding="0" cellspacing="0"	id="myTable" class="pretty dataTable">
						<thead>
							<th class="chk"><input type='checkbox' id='total'/></th>
							<th id="no" class="num">序号</th>
							<th style="width:600px;" class="longTxt">文件名称</th>
							<th id="fileSize" class="data_r" style="width:80px;">文件大小</th>
							<th id="createUser" style="width:100px;">创建人</th>
							<th id="createTime" class="right_bdr0" style="width:150px;" >创建时间</th>
						</thead>
						<tfoot>
						</tfoot>
					</table>
				</div>
				
	</body>
</html>
