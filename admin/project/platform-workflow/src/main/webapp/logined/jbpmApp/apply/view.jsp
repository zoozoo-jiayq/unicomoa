<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<jsp:include page="../../../common/flatHead.jsp"></jsp:include>
<title>查看流程</title>
<link href="${ctx }/flat/css/reset.css" rel="stylesheet" type="text/css" />
<link href="${ctx }/flat/css/myworks.css" rel="stylesheet" type="text/css" />
<link href="${ctx }/flat/plugins/form/skins/form_default.css" rel="stylesheet" type="text/css" />
<link href="${ctx }/flat/plugins/annex/skins/annex_default.css" rel="stylesheet" type="text/css" />
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
  .inputTable th{ width:45px;}
  .form_box h2{height:40px; padding-left:15px; line-height:40px; background:#3da9bc; color:#fff; font-size:16px; display:none;border-radius:5px 5px 0 0 ; margin:0 auto;}
  .form_box_big{ position:absolute; top:10px; left:10px; width:98%; height:98%; background:#fff; border-radius:5px;  padding:0px; z-index:999}
  .form_box_big h2{ display:block;}
  .form_box_big h2 a.close{ display:inline-block; float:right; width:15px; height:15px; background:url(../flat/images/icon_close.png) no-repeat 0px 0px; margin-top:12px; margin-right:15px;}
  .form_box_big h2 a.close:Hover{ background-position: center -32px;}
  .formbd{ padding:10px;}
</style>
</head>
<body>
<div class="formPage pt20">
	<div class="formbg">
		<h1 class="big_title">查看申请<span class="f14 gray_9">（<s:property value="#request.processName"/>）</span></h1>
		<div class="content_form">
						<table width="100%" cellspacing="0" cellpadding="0" border="0" class="inputTable">
								<tbody>
                                        <tr>
												<th><label>流程：</label></th>
												<td>
				<jsp:include page="processHistoryList.jsp" />
				 </td>
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
					  <td><a target="_blank" href="<%=request.getContextPath()%>/dom/public!getPdf.action?instanceId=${processInstanceId}">正文</a></td>
					</tr>
				</s:if>
									 <s:if test="attachMap!=null&&attachMap.size>0">
				<tr id='yesUpload'>
					<th><label>附件：</label></th>
					<td>
							<div class="annex">
									<ul>
	                        			<s:iterator value="attachMap" id="column" status="status" >
											<li>
													<div class="icon"><em class="<s:property value='attacthSuffix'/>"></em></div>
													<div class="txt">
															<p><s:property value="attachName"/></p>
															<p><a class="btnDown" href="${ctx}filemanager/downfile.action?attachmentId=<s:property value="id"/>">下载</a></p>
													</div>
											</li>
										</s:iterator>
									</ul>
							</div>
					</td>
				</tr>
	                    			</s:if>
			</tbody>
	</table>
</div>						
<input type="hidden"  id="affireflag" value='${affireflag }'/>
    <input type="hidden" id="urlSource" name="urlSource" value="<s:property value="#request.urlSource"/>"/>
    <input type="hidden" id="processId" name="processId" value="<s:property value="#request.processId"/>"/>
    <input type="hidden" id="taskId" name="taskId" />
    <input type="hidden" id="taskName" name="taskName" value="<s:property value="#request.taskName"/>"/>
    <input type="hidden" id="history" value="history"/>
	<input type="hidden" name="attachment" id="attachment"/>

<input type="hidden" name="processInstanceName" id="processInstanceName"
       value="<s:property value="#request.processName"/>"/>
<!--操作-->
<form method="post" id="form1" >

    <input type="hidden" id="processInstanceIdForm" name="processInstanceId"
           value="<s:property value="#request.processInstanceId"/>"/>
</form>
<input type="hidden" id="title" value="<s:property value="#request.processName"/>"/>
<input type="hidden" id="processInstanceId" value="<s:property value="#request.processInstanceId"/>"/>
<input type="hidden" id="instanceId" value="<s:property value="#request.processInstanceId"/>"/>
<input type="hidden" id="message" value="${message }"/>
<div class="buttonArea">
 		<input hideFocus="" id="returnButton" onclick="viewReturn();" value="关闭"  class="formButton_green"  type="button" />
 		<input hideFocus="" id="" onclick="printView();" value="打印表单"  class="formButton_grey"  type="button" />
 		<c:if test="${processInstanceState == 'ended' }">
 		<input hideFocus="" id="" onclick="exportForm();" value="导出表单"  class="formButton_grey"  type="button" />
 		</c:if>
 	<script type="text/javascript" >

            jQuery(document).ready(function($){
            
            });
     </script>
</div>
</div>
</div>
<script type="text/javascript" src="${ctx}js/logined/jbpmApp/common.js"></script>
<script type="text/javascript" src="${ctx }js/common/async.js"></script>
<script type="text/javascript" src="${ctx}js/logined/customForm/formEvent.js" ></script>
<script type="text/javascript">
   jQuery(document).ready(function($){
	   readonlyForm();
	    //表单处理逻辑
		async.series({
			loadData:function(callback){
				setFormDate(callback);
			},
			generateWidget:function(callback){
				initWidget(callback);
			}
		},function(err,result){
		}); 
		
		setEnableUpload();
		
		var mes = $("#message").val();
        if(mes == "message"){
        	$("#returnButton").hide();
        }
		
   });
   
   function viewReturn(){
     	if($("#affireflag").val()=="affireflag"){
     		window.close();
     	}else{
     		if(window.top && window.top.closeCurrentTab){
				window.top.closeCurrentTab();
     		}else{
     			window.close();
     		}
     	}
   }
   
   function exportForm(){
   		window.open(basePath+"/jbpmflow/detailSearch!download.action?processInstanceId="+encodeURI($("#processInstanceId").val())+"&processId="+$("#processId").val());
   }
     
     
</script>
</body>
</html>
