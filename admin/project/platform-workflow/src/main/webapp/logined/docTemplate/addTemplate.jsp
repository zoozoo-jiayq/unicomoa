<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
 String docTemplateId="";
 if(request.getParameter("docTemplateId")!=null){
     docTemplateId = request.getParameter("docTemplateId");
 }
 %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>新建表单分类</title>
<jsp:include page="../../common/flatHeadNoChrome.jsp" />
<link href="${ctx}/flat/css/reset.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/flat/css/reset.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/flat/plugins/form/skins/form_default.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/flat/plugins/annex/skins/annex_default.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" language="javascript" src="${ctx}js/logined/docTemplate/addTemplate.js" ></script>
<script type="text/javascript" src="${ctx}js/user/select_document_user.js"></script>
<script type="text/javascript" language="javascript" src="${ctx}js/logined/docTemplate/addSelectUser.js" ></script>

<style type="text/css">
.inputTable th { width:72px;}
</style>
</head>
<body>
<div class="formPage">
  <div class="formbg">
		<div class="big_title">上传模板</div>
		<div class="content_form">		
			<form id="docform"   action="${ctx }workflowForm/saveDocTemplate.action"  method="post" enctype="multipart/form-data">
			        <input type="hidden"  value="<%=docTemplateId %>" name="docTemplateId" id="docTemplateId" />
			        <input type="hidden" name="fileName" id="fileName" />
			        <input type="hidden"   id="nextUserName" name="nextUserName"  /> 
   					<input type="hidden"   id="nextUser"  name="nextUser" /> 
			     <table width="100%" cellspacing="0" cellpadding="0" border="0" class="inputTable">
					<tr>
						<th><label>模板名称：</label></th>
						<td><input type="text" maxlength="30"  valid="required" errmsg="docTemplate.name_not_null"   name="name" id="name" class="formText" style="width:100%"/></td>
			        </tr>
			        <tr>   
						<th><label>模板类型：</label></th>
						<td>
			                 <select valid="required" errmsg="docTemplate.category_not_null"   id="docTemplateType" name="docTemplateType">
			                </select>		                
			           </td>
					</tr>
					
					<% if(docTemplateId!=null&&!"".equals(docTemplateId)) { %>
						<tr>
									<th><label>模板文件：</label></th>
									<td>
									   <span    id="showFileSrc"  ></span>
									   <input   type="hidden"      id="showFile"    class="formText" value=""  />
									   <input   type="hidden"      id="src_showFile"    class="formText" value=""  />
									   <input   type="hidden"      id="src_showFileSrc"    class="formText" value=""  />
									   <input type="button" class="blueBtn" value="更换" onclick="addNewFile();" id="changeNewFile"/>
						        </td>
						 </tr>
							<tr id="newFileTd" style="display:none;"  > 
								<th><label>替换文件：</label></th>
								<td>
								   <input type="file" onchange="$('#fileName').val($(this).val());$('#showFile').val($(this).val());"   name="myfile" id="myfile"   errmsg="docTemplate.file_not_null"  class="readOnlyText mr5" style="width:80%;"   />
								 <br/>
					            <em class="gray_9">支持doc和docx文件</em>
					        </td>
							</tr>
					<% }else { %>
						<tr >
							<th><label>模板文件：</label></th>
							<td>
							   <input type="file" onchange="$('#fileName').val($(this).val());$('#showFile').val($(this).val());"   name="myfile" id="myfile"  valid="required" errmsg="docTemplate.file_not_null"      class="readOnlyText mr5" style="width:80%;"  />
							 <br/>
				            <em class="tip" style="padding:0;">支持doc和docx文件</em>
				        </td>
						</tr>
					<% } %>
					
			    </table>
			    </form>		
			  </div>
			</div>	     
	    <div class="buttonArea">
	        <span>
			    <input type="button" class="formButton_green"  id="submit"   value="确 定"/>
			</span>
	        <span >
			    <input class="formButton_grey" type="button" id="next" value="取 消" hidefocus="" onclick="javascript:window.location.href = document.referrer;"/>
			</span>
	    </div>
	 </div>

</body>
</html>
