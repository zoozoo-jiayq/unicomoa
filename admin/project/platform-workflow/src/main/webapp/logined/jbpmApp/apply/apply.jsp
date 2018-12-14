<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>审批</title>
<jsp:include page="../../../common/flatHead.jsp"></jsp:include>
<script type="text/javascript">
        var processId =<s:property value="#request.processId" />;
</script>
<link href="${ctx }/flat/css/reset.css" rel="stylesheet" type="text/css" />
<link href="${ctx }/flat/css/myworks.css?version=3" rel="stylesheet" type="text/css" />
<link href="${ctx }/flat/plugins/form/skins/form_default.css" rel="stylesheet" type="text/css" />
<link href="${ctx }/flat/plugins/annex/skins/annex_default.css" rel="stylesheet" type="text/css" />
 <link rel="stylesheet" type="text/css" href="${ctx}plugins/upload/uploadify.css"/>
<script type="text/javascript" src="${ctx}plugins/upload/jquery.uploadify.min.js"></script>
<script type="text/javascript" src="${ctx}js/logined/customForm/formEvent.js" ></script>
<script type="text/javascript" src="${ctx }js/common/async.js"></script>
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
<style>
  .inputTable th{ width:80px;}
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
<input type="hidden"  id="affireflag" value='${affireflag }'/>
 <input type="hidden" id="taskId" name="taskId" value="<s:property value="#request.taskId"/>"/>
<input type="hidden" id="processInstanceId" value="<s:property value="#request.processInstanceId"/>"/>
<input type="hidden" id="instanceId" value="<s:property value="#request.processInstanceId"/>"/>
<input type="hidden" name="attachment" id="attachment"/>
<input type="hidden" id="suspend" value="<%=request.getParameter("suspend")%>"/>
<input  type="hidden" id="affireflag" value="${affireflag }"/>
<input  type="hidden" id="moduleName" value="workflow"/>

<div class="formbg">
	<h1 class="big_title">审批<span class="f16">（${processName}）</span></h1>
	<div class="content_form">
			<table width="100%" cellspacing="0" cellpadding="0" border="0" class="inputTable">
			<tbody>
			<tr>
			  <th><label>流程：</label></th>
			  <td><jsp:include page="processHistoryList.jsp" /></td>
			</tr>
				<tr>
				<th><label>表单：</label><span class="tab_fd hover"></span></th>
						<%-- <td  id="form"><div class="pt10" style="overflow: auto;">${formSource }</div></td> --%>
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
					  <td>
					  <s:if test="CurrentNodeFormAttribute.editDoc==1">
					     <a target="_blank" href="<%=request.getContextPath()%>/logined/publicDom/ntkodoc/ntkoDoc.jsp?documentExtId=${documentExtId}&templateId=${processAttribute.redTemplate}&taskId=${taskId}">正文</a>
					  </s:if>
					  <s:else>
					      <a target="_blank" href="<%=request.getContextPath()%>/dom/public!getPdf.action?instanceId=${processInstanceId}">正文</a>
					  </s:else>
					  </td>
					</tr>
				</s:if>
			<tr id='yesUpload'>
					<th><label>附件：</label></th>
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
							</div>
					</td>
			</tr>
			<tr>
					<th>我的意见：</th>
					<td><label  class="radio"><input type="radio" name="mySuggestion" value="1" checked="checked" />同意</label><label  class="radio ml10"><input type="radio"
                                                                                                           name="mySuggestion"
                                                                                                           value="0"/>不同意</label>
                         <input id="adviceInput" type="hidden"/></td>
			</tr>
			<tr <c:if test="${approveComment==1}">style="display:'';"</c:if><c:if test="${approveComment!=1}">style="display:none;"</c:if> >
					<th><label>意见/备注：</label></th>
					<td><textarea id="advice" class="formTextarea" rows="3" onKeyUp="if(this.value.length > 50) this.value=this.value.substr(0,50)"></textarea></td>
			</tr>

<s:if test="nextEnd=='结束' ">
   <input type="hidden" id="outComeNameShow" value="${defaultApplyNextNode.name}" />
</s:if>
<s:else>
	   <!-- 下一步流程 -->
	   <input type="hidden" id="selectUserMode" value="${processAttribute.selectUserMode}"/>
	   <input type="hidden" id="currentTaskCount" value="${currentTaskCount}" />
	   <input type="hidden" id="nodeType" value="${defaultApplyNextNode.type}" />
	   <input type="hidden" id="outComeNameShow" value="${defaultApplyNextNode.name}" />
		<tr id="nextLCDIV">
				<th><label>下一步：</label></th>
				<td><select id="nextNode">
	            <c:forEach items="${applyNextNodes}" var="node" >
	                <option value="${node.nodeId }"><font class="orange">${node.name }</font></option>
	            </c:forEach>
	        </select>
						<label class="title">办理人：</label>
						<input id="exeUser"  class="readOnlyText" readonly="readonly" value=""   type="text"  />
						<span class="addMember" style="width:120px;text-align:left;"><a href="javascript:void(0);" onclick="selectNextUserApply();"  class="icon_add">选择</a><a style="display: none" id="cleanButton" href="javascript:void(0);" class="icon_clear" onclick="$('#exeUser').val('');$('#holdStr').val('');$('#handleStr').val('');_selectId='';">清空</a></span></td>
		</tr>
			</s:else>
		</tbody>
	</table>
</div>
<!--操作-->
<form method="post" id="form1" action="${ctx}jbpmApp/jbpmLaunch_sendNextPage.action">
    <input type="hidden" id="urlSource" name="urlSource" value="<s:property value="#request.urlSource"/>"/>
    <input type="hidden" id="processId" name="processId" value="<s:property value="#request.processId"/>"/>
    <input type="hidden" id="processInstanceIdForm" name="processInstanceId"  value="<s:property value="#request.processInstanceId"/>"/>
    <input type="hidden" id="taskName" name="taskName" value="<s:property value="#request.taskName"/>"/>
    <input type="hidden" id="approvalResult" value="同意"/>
    <input type="hidden" id="outComeNameShow" value='${defaultApplyNextNode.name}'/>
    <input type="hidden" id="outComeName" value='TO ${defaultApplyNextNode.name}'/>
    <input type="hidden" id="nodeId" value='${defaultApplyNextNode.nodeId}'/> 
    <input type="hidden" id="holdStr" /> <!-- 办理人 -->
</form>
<div class="buttonArea">
    <input id="sendButton" onclick="form_submit();"   value="提 交" type="button" class="formButton_green"  />
    <input onclick="goback();" class="formButton_grey" value="返回" type="button"/>
</div>
</div>	

</div>

<script type="text/javascript" >
var nextNodes = [
                 <c:forEach items="${applyNextNodes}" var="node">
                 {"id":"${node.nodeId }","name":"${node.name }","type":"${node.type }"},
                 </c:forEach>
                 {"id":"-1","name":"0","type":"0"}
             ];
</script>
<script type="text/javascript" src="${ctx}js/logined/jbpmApp/common.js"></script>
<script type="text/javascript" src="${ctx}js/logined/jbpmApp/apply.js"></script>
<script type="text/javascript" src="${ctx}js/logined/jbpmApp/nextOperation.js"></script>
<script type="text/javascript" src="${ctx}js/user/select_document_user.js"></script>
<script type="text/javascript" src="${ctx}js/user/user.js"></script>
<script type="text/javascript">
   $(document).ready(function () {
       $(".tab li").eq(0).click(function () {
           $(".tabContent").show();
       });
   });//选项卡
</script>
</body>
</html>
