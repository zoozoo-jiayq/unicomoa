<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<jsp:include page="../../common/flatHead.jsp" />
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>无标题文档</title>
	<link href="${ctx}/css/qj-css.css" rel="stylesheet" type="text/css" />
	<style type="text/css" title="currentStyle">
		.uploadify{top:0;}
	</style>
<link href="${ctx}flat/css/reset.css" rel="stylesheet" type="text/css" />
<link href="${ctx}flat/css/knowledge.css" rel="stylesheet" type="text/css" />
<link href="${ctx}flat/plugins/datatable/skins/datatable_default.css" rel="stylesheet" type="text/css" />
<link href="${ctx}flat/plugins/Accormenus/skins/Accormenus_default.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}plugins/My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript" src="${ctx}js/logined/file/fileContent.js"></script>
		<script type="text/javascript" src="${ctx}js/logined/file/delFile.js"></script>
		<script type="text/javascript" src="${ctx}js/logined/file/checkFile.js"></script>
		<script type="text/javascript" src="${ctx}js/logined/file/downFile.js"></script>
		<script type="text/javascript" src="${ctx}js/logined/file/addFile.js"></script>
		<script type="text/javascript" src="${ctx}js/logined/file/delCatorlog.js"></script>
		<!-- 加入的批量上传的 -->
		<link rel="stylesheet" type="text/css" href="${ctx}plugins/upload/uploadify.css"/>
		<script type="text/javascript" src="${ctx}js/logined/file/upload.js"></script>
		<!-- 加入的批量上传的 -->
		<script type="text/javascript" src="${ctx}flat/js/placeholder.js?version=${version }"></script>
		<script type="text/javascript">
			var type = "${param.type}";
		</script>
	</head>
	<body style="display: none;  ">
		<input type="hidden" name="fileSortId" id="fileSortId" value="${param.fileSortId}"/>
			<input type="hidden" name="path" id="path" value="${param.path}"/>
		 	<input type="hidden" name="fileTypeSort" id="fileTypeSort" value="<%=request.getParameter("fileTypeSort") %>"/>
				<div class="list">
					<div class="searchArea">
						<table cellpadding="0" cellspacing="0" id="fileList">
							<tbody>
						          <tr>
						          	<td  class="right">
										<label>创建时间：</label>
										<input readonly="readonly" name="starttime" id="starttime" class="formText Wdate" type="text"
											onfocus="WdatePicker({maxDate: '#F{$dp.$D(\'endTime\')}',skin:'default',dateFmt:'yyyy-MM-dd HH:mm'})" />
										-
										<input  readonly="readonly"  name="endTime" id="endTime" class="formText Wdate"
											  type="text"
											onfocus="WdatePicker({minDate: '#F{$dp.$D(\'starttime\')}',skin:'default',dateFmt:'yyyy-MM-dd HH:mm'})" />
										<label>关键字：</label>
										<span class="position:relative;">
											<input name="subject" id="subject" maxlength="30" class="formText searchkey"  placeholder="文件名称"  type="text"/>
										</span>
										<input class="searchButton" hideFocus="" id="searchFile" type="button" value="查询" />
									</td>
						                 
						              <% if(request.getParameter("fileSortId")!=null&&"0".equals(request.getParameter("fileSortId").toString())){ %>
						                  <td style="width:185px;">
						              <%}else{ %>
						                  <td style="width:276px;">
						                  	<c:if test="${param.type=='1' }">
								              	<div class="fl" style="margin-left:10px"     >
													<input id="file_upload" name="fileupload" type="file" multiple="true" />
													<input type="hidden" id="appendixIds" name="appendixIds" />
													<input type="hidden" id="sortId" name="sortId"	value="${param.fileSortId}" />
													<input type="hidden" id="moduleName" name="moduleName" value="file" />
												</div>
											</c:if>	
						                  	<c:if test="${param.type=='2' }">
								              	<div class="fButton blueBtn">
									               <div> <span onclick="shareFile()" class="load">上传</span> </div>
									            </div>
											</c:if>	
						              <%} %>
						                 	
						              <div class="fButton blueBtn">
						                <div> <span onclick="downFile('${param.name}')" class="load">下载</span> </div>
						              </div>
						              <div class="fButton orangeBtn"> <span class="delete" onclick="javascript:delFile();">删除</span> </div>
						              <div class="btnseparator"></div>
						              </td>
						          </tr>
						        </tbody>
					
						</table>
					</div>
					<table width="100%" border="0" cellspacing="0" cellpadding="0" id="myTable" class="pretty dataTable">
						<thead>
							<th class="chk"><input type="checkbox" id="total"/></th>
							<th class="num" id="no">序号</th>
							<th style="width:600px;" class="longTxt">文件名称</th>
							<th id="fileSize" style="width:80px;" class="data_r">文件大小</th>
							<th id="createUser" style="width:100px;">创建人</th>
							<th id="createTime" style="width:150px;" class="right_bdr0">创建时间</th>
						</thead>
						<tfoot>
						</tfoot>
					</table>
				</div>
				<script>funPlaceholder(document.getElementById("subject"));</script>
	</body>
	
</html>
