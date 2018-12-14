<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	session.setAttribute("tdAdvice", null);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>填写表单</title>
<jsp:include page="../../../common/flatHead.jsp"></jsp:include>
<link href="${ctx}/flat/css/reset.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/flat/css/myworks.css" rel="stylesheet" type="text/css" />
<link href="${ctx }/flat/plugins/form/skins/form_default.css" rel="stylesheet" type="text/css" />
<link href="${ctx }/flat/plugins/annex/skins/annex_default.css" rel="stylesheet" type="text/css" />
 <link rel="stylesheet" type="text/css" href="${ctx}plugins/upload/uploadify.css"/>
 <script type="text/javascript" src="${ctx}plugins/upload/jquery.uploadify.min.js"></script>
 <script type="text/javascript" src="${ctx}js/logined/jbpmApp/upload.js"></script>
 <script type="text/javascript">
   $(document).ready(function($) {
	   $("span.tab_fd").click(function(){
		$(".form_box").addClass("form_box_big");  
		   })
	   $(".form_box h2 a.close").click(function(){
		$(".form_box").removeClass("form_box_big");  
		   })
	   })
</script>
<style type="">
  .inputTable th{width:85px;}
  .form_box h2{height:40px; padding-left:15px; line-height:40px; background:#3da9bc; color:#fff; font-size:16px; display:none;border-radius:5px 5px 0 0 ; margin:0 auto;}
  .form_box_big{ position:absolute; top:10px; left:10px; width:98%; height:98%; background:#fff; border-radius:5px;  padding:0px; z-index:999}
  .form_box_big h2{ display:block;}
  .form_box_big h2 a.close{ display:inline-block; float:right; width:15px; height:15px; background:url(../flat/images/icon_close.png) no-repeat 0px 0px; margin-top:12px; margin-right:15px;}
  .form_box_big h2 a.close:Hover{ background-position: center -32px;}
  .formbd{ padding:10px;}
</style>
</head>
<body>
<div class="formPage">
<div class="formbg">
<h1 class="big_title">新增申请</h1>
	<div class="content_form">
	<table width="100%" cellspacing="0" cellpadding="0" border="0" class="inputTable">
		<tbody>
			<tr>
					<th><label>申请名称：</label></th>
					<td><input type="text" class="formText" id="processInstanceName" maxlength="80" value="${generateProcessInstanceName }"/></td>
			</tr>
			<tr>
					<th><label>表单内容：</label><span class="tab_fd hover"></span></th>
					<%-- <td  id="form">
						<div class="pt10" style="overflow: auto;">${formSource }</div>
					</td> --%>
					
					<td style="position:inherit" id="form">
						<div class="form_box">
						<h2><a href="javascript:void()" class="close"></a>表单正文</h2>
						<div class="formbd">${formSource }</div>
						</div>
					</td>
			</tr>
			<s:if test="processAttribute.isDom==1">
					<tr>
					  <th>正文：</th>
					  <td><a target="_blank" href="<%=request.getContextPath()%>/logined/publicDom/ntkodoc/ntkoDoc.jsp?documentExtId=${documentExtId}&templateId=${processAttribute.redTemplate}">正文</a></td>
					</tr>
				</s:if>
			<tr id="yesUpload">
					<th><label>上传附件：</label></th>
					<td><div class="pt10 pb10"><input id="file_upload" name="fileupload" type="file" multiple="true" /></div>
							<div class="annex">
									<ul>
										<s:if test="attachMap!=null&&attachMap.size>0">
	                        			<s:iterator value="attachMap" id="column" status="status" >
											<li>
													<div class="icon"><em class="img"></em></div>
													<div class="txt">
															<p><s:property value="attachName"/></p>
															<p><a class="btnDown" href="${ctx}filemanager/downfile.action?attachmentId=<s:property value="id"/>">下载</a></p>
													</div>
											</li>
										</s:iterator>
	                    			</s:if>
									</ul>
							</div></td>
			</tr>
			<tr>
					<th><label>下一步流程：</label></th>
					<td><select id="nextNode">
				            <c:forEach items="${nextNodes}" var="node" >
				            	<option value="${node.id }"><font class="orange">${node.nodeName }</font></option>
				            </c:forEach>
				        </select>
							<label class="title">办理人：</label>
							<input type="text" class="readOnlyText" readonly="readonly" id="exeUser"  />
							<span class="addMember auto_addMember"  ><a href="javascript:void(0);"  onclick="selectNextUserApply();" class="icon_add">选择</a><a style="display: none" id="cleanButton" href="javascript:void(0);" onclick="$('#exeUser').val('');$('#holdStr').val('');_selectId='';" class="icon_clear">清空</a></span></td>
			</tr>
		</tbody>
</table>
</div>
</div>

<!--隐藏域-->
<form method="post" id="form1" >
    <input type="hidden" id="processId" name="processId" value="${processId }"/>
    <input type="hidden" id="selectUserMode" value="${processAttribute.selectUserMode}"/>
    <input type="hidden" id="outComeNameShow" value='${defaultNextNode.nodeName}'/>
    <input type="hidden" id="outComeName" value='TO ${defaultNextNode.nodeName}'/>
    <input type="hidden" id="nodeId" value='${defaultNextNode.id}'/> 
    <input type="hidden" id="nodeType" value="${defaultNextNode.nodeType}"/>
    <input type="hidden" id="holdStr" /> <!-- 办理人 -->
    <input type="hidden" id="currentTaskCount" value="0"/>
    <input type="hidden" id="taskId"/>
    <input type="hidden" id="processInstanceId"/>
    <input type="hidden" id="instanceId"/>
	<input type="hidden" id="rollbackProcessInstanceId" value="${processInstanceId }"/>
	<input type="hidden" name="attachment" id="attachment"/>
	<input type="hidden" id="documentExtId" value="${documentExtId }"/>
</form>
<div class="buttonArea">
<input type="button" class="formButton_green" value="提交" id="sendButton"/>
<!-- <input type="button" value="返回" class="formButton_grey" onclick="javascript:window.location.href='${ctx}/jbpmflow/listSearch!often.action';"/> -->
<input type="button" value="关闭" class="formButton_grey" id="closeButton" />
</div>
</div> 
<script type="text/javascript">
var nextNodes = [
    <c:forEach items="${nextNodes}" var="node">
    {"id":"${node.id }","name":"${node.nodeName }","type":"${node.nodeType }"},
    </c:forEach>
    {"id":"-1","name":"0","type":"0"}
];
var orialAttachList = new Array();
<s:iterator value="attachMap" id="column" status="status" >
	orialAttachList.push(<s:property value="id"/>);										
</s:iterator>
</script>

<script type="text/javascript" src="${ctx}js/logined/customForm/formEvent.js" ></script>
<script type="text/javascript" src="${ctx}js/logined/jbpmApp/common.js"></script>
<script type="text/javascript" src="${ctx}js/logined/jbpmApp/applyBefore.js"></script>
<script type="text/javascript" src="${ctx}js/user/select_document_user.js"></script>
<script type="text/javascript" src="${ctx}js/user/selectuser.js"></script>
<script type="text/javascript" src="${ctx}js/user/user.js"></script>
<script type="text/javascript" src="${ctx}js/logined/jbpmApp/nextOperation.js"></script>

</body>
</html>
