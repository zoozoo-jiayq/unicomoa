<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%
String id = request.getParameter("id");
request.setAttribute("id",id); 
String columnId = request.getParameter("columnId");
request.setAttribute("columnId",columnId); 
String status = request.getParameter("status");
request.setAttribute("status",status); 
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>修改</title>
<%@ include file="../../../common/taglibs.jsp"%>
<%@ include file="../../../common/head.jsp"%>
	<script>
	   var basePath = "${ctx}";
		window.UEDITOR_HOME_URL = basePath+"plugins/ueditor/";
	</script>
<link rel="stylesheet" type="text/css" href="${ctx}plugins/upload/uploadify.css">
<script type="text/javascript" src="${ctx}js/common/messenger.js"></script>
<script type="text/javascript" language="javascript" src="${ctx}plugins/upload/jquery.uploadify.min.js" ></script>
<script charset="utf-8" src="<%=request.getContextPath() %>/plugins/kindeditor/kindeditor-min.js"></script>
<script charset="utf-8" src="<%=request.getContextPath() %>/plugins/kindeditor/lang/zh_CN.js"></script>
<script type="text/javascript" charset="utf-8" src="${ctx}plugins/ueditor/editor_config.js"></script>
 <script type="text/javascript" charset="utf-8" src="${ctx}plugins/ueditor/editor_all_min.js"></script>
<script type="text/javascript" src="${ctx}js/logined/notify/viewApproveUpdate.js"></script>
<script type="text/javascript" src="${ctx}js/logined/notify/viewAttachment.js"></script>
<script type="text/javascript" src="${ctx}js/user/selectuser.js"></script>
<script type="text/javascript" src="${ctx}js/common/hashmap.js"></script>
<script type="text/javascript" src="${ctx}js/common/treeNode.js"></script>
<script type="text/javascript" src="${ctx}js/common/validate_form.js"></script>
<script type="text/javascript" src="${ctx}js/common/showError.js"></script>
<script type="text/javascript" src="${ctx}js/logined/affairs/affairs_check.js"></script>
<!-- 上传 start -->
 <script type="text/javascript"  language="javascript" src="${ctx}plugins/upload/jquery.uploadify.min.js"></script>
 <script type="text/javascript" src="${ctx}js/logined/upload/attachHelp.js"></script>   
 <script type="text/javascript" src="${ctx}js/logined/cbb/attachment/attachment.js"></script> 
</head>
<body>
<input type="hidden" id="id" value="${id}"/>
<input type="hidden" id="status" value="${status}"/>
<input  type="hidden" id="columnId" name="columnId" value="${columnId}"/>
<div class="input_new">
	<form id="form1">
	    <table class="listTitle" cellspacing="0" cellpadding="0" border="0">
    	<tbody><tr>
        	<td class="left"></td><td class="center">修改</td><td class="right"></td>
        </tr>
    </tbody></table>
	<table width="100%" border="0" cellpadding="0" cellspacing="0"  class="inputTable" >
	<tr  style="">
			<th><label>类型</label>
            </th>
			<td>
				<label><select id="notifyType"></select></label>
			</td>
		</tr>
		<tr>
			<th><label class="requireField">*</label><label>标题</label></th>
			<td><input id="subject" name="subject" type="text" maxlength="50" class="formText" valid="required" errmsg="notify.notify_subject_not_null"></input>
			</td>
        </tr>
        <tr id="userDiv" >
			<th><em class="requireField">*</em><label>发布对象</label>
             </th>
			<td>
				<input type="hidden" id="publishScopeUserIds" name="publishScopeUserIds"/>
				<input type="hidden" id="columnId" name="columnId" value="${id}"/>
				<textarea class="readOnlyText" style="width:80%" rows="5" id="publishScopeUserNames" name="publishScopeUserNames" readonly="readonly"></textarea><span class="addMember"><a class="icon_add" href="#" onclick="selectAuthor('user');">添加</a><a  class="icon_clear" href="#" onclick="clearAuthor('user')">清空</a></span>
			</td>
		</tr>
		<tr>
			<th><label>置顶</label></th>
			<td><label class="radio"><input type="checkbox" id="isTop" value="1"/></td>
		</tr>
		<tr>
			<th><label>图片</label></th>
			<td>
			   <p class="top5"></p>
			    <input type="hidden" id="imageId" name="imageId" value=""/>
			    <input id="image_upload" name="image_upload" type="file" multiple="true" />
			    <!-- 上传队列 -->
			    <div id="queue"  style="display:none;"></div>
               <div class="annex">
                <ul id="imageAttachmentList">
                </ul>
               </div>
            </td>
         </tr>
        <tr>
        <th><span class="requireField"> *</span><label>内容</label></th>
	            <td class="td_warning" >
	                 <input id="contentInfoInput" type="hidden"/>
				     <script id="contentInfo" type="text/plain" style="width:100%"></script>
	            </td>
	      </tr>
	    <tr>
			<th><label>附件</label></th>
			<td>
			   <p class="top5"></p>
			    <input type="hidden" id="attachmentId" value=""/>
			    <input id="file_upload" name="fileupload" type="file" multiple="true" />
			    <!-- 上传队列 -->
			    <div id="queue"  style="display:none;"></div>
               <div class="annex">
                <ul id="attachmentList">
               </ul>
               </div>
            </td>
         </tr>
	</table>
	 <div class="pageTitle" style="margin-top:15px"><a class="shareShow" onclick="showHide('shareTr')" href="#">更多操作</a></div>
	 <table width="100%" cellspacing="0" cellpadding="0" border="0" class="inputTable" id="shareTr" style="display:none;">
		<tbody>
	       <tr>
			<th><label>有效期</label></th>
			<td>
			<input name="input" type="text" class="Wdate formText" value="<%=new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()) %>" size="20" name="begin_date" id="begDate" onClick="WdatePicker({maxDate :'#F{$dp.$D(\'endDate\')}',skin:'default',dateFmt:'yyyy-MM-dd HH:mm',minDate:'%y-%M-%d %H:%m'})"/>
			-
			<input name="input" type="text" class="Wdate formText" size="20" name="end_date" id="endDate" onClick="WdatePicker({skin:'default',dateFmt:'yyyy-MM-dd HH:mm',minDate:'#F{$dp.$D(\'begDate\') || \'%y-%M-%d %H:%m\'}'})" />
			&nbsp;<span style="width:auto;color:#0647a8">不选择结束日期为手动终止</span></td>
		</tr>
			<tr id="sms_remind_tr">
				<th><label>审批人</label></th>
				<td><select id="auditer" name="auditer"></select></td>
	        </tr>
	       <tr>
				<th><label>内容简介</label></th>
				<td><input id="summary" type="text" class="formText"  style="width:80%"  maxlength="30"/>
						<em class="gray_9">(最多输入30个字)</em>
				</td>
			</tr>
		</tbody></table>
	 
	<div class="buttonArea"> 
<!-- 			<input  value="提交审批" class="formButton_green" type="button" onclick="tijiao(1);" id="tjsp" style="display:none"/> -->
			<input hideFocus="" value="保  存" class="formButton_green" type="button" onClick="tijiao()" id="fb" />
<!-- 			<input hideFocus="" value="存草稿" class="formButton_grey" type="button" onClick="tijiao(0);"/> -->
			<input hideFocus="" onClick="goBack();" class="formButton_grey" value="返 回" type="button" />
	</div>
	</form>
</div>
</body>
</html>
