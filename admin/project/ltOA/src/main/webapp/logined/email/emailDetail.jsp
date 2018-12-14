<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>邮件查看</title>
    <jsp:include page="../../common/flatHead.jsp"/>
    <link href="${ctx}flat/css/reset.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}flat/plugins/annex/skins/annex_default.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}flat/css/mail.css" rel="stylesheet" type="text/css" />
    
    
    <script type="text/javascript" src="${ctx}flat/js/base.js"></script>
    <script type="text/javascript" src="${ctx}js/common/placeholder.js"></script>
    <script type="text/javascript" src="${ctx}js/logined/email/viewAttachment.js"></script> 
</head>
<body>
<input type="hidden" id="prevFlag" value ="${prevFlag}"/>
<input type="hidden" id="nextFlag" value ="${nextFlag}"/>
<input type="hidden" id="type" value="${param.type }"/>
<input type="hidden" id="prevEmailToId" value ="${prevEmailToId}"/>
<input type="hidden" id="prevEmailBodyId" value ="${prevEmailBodyId}"/>
<input type="hidden" id="nextEmailToId" value ="${nextEmailToId}"/>
<input type="hidden" id="nextEmailBodyId" value ="${nextEmailBodyId}"/>
<div class="list" style="padding:5px;">
    <div class="meil_search_area">
        <span class="meil_btn"><input type="button" class="returnBtn" value="返回"/></span>
        <span class="meil_btn"><input type="button" class="replyBtn" value="回复"/></span>
        <span class="meil_btn"><input type="button" class="replyAllBtn" value="回复全部"/></span>
        <span class="meil_btn"><input type="button" class="againSendBtn" value="再次发送"/></span>
        <span class="meil_btn"><input type="button" class="forwardBtn" value="转发"/></span>
        <span class="meil_btn"><input type="button" class="deleteBtn" value="删除"/></span>
        <span class="meil_btn"><input type="button" class="recoveryBtn" value="恢复"/></span>
        <span class="meil_btn"><input type="button" class="destroyBtn" value="彻底删除"/></span>
        <span class="meil_btn"><input type="button" class="prevBtn" title="${prevTitle}" value="上一封"></span>
        <span class="meil_btn"><input type="button" class="nextBtn" title="${nextTitle}" value="下一封"></span>
    </div>
	<div class="meil_details">
       <div class="top_title">
           <ul>
           		<li>
	           		<label>主题：</label>
	           		<strong>${emailBody.subject}</strong>
		           		<s:if test="%{emailBody.importantLevel==1}">
		                    <span class="a_oreg">&nbsp;重要</span>
		                </s:if>
		                <s:if test="%{emailBody.importantLevel==2}">
		                    <span class="a_red">&nbsp;非常重要</span>
		                </s:if>
           		</li>
               	<li>
	               	<label>发件人：</label>
	               	<font>${emailBody.createUserInfo.userName}</font>
	               	<a id="sendMessageA" class="ml5" href="javascript:void(0)" style="display:none"
                   onclick="sendMessage('${emailBody.createUserInfo.userId}','${emailBody.createUserInfo.userName}','subject')">
                    		发即时消息</a>
               	</li>
               	<s:if test="%{emailBody.toName!=''}">
               		<li>
               			<label>收件人：</label>
               				${emailBody.toName}
               				<a href="javascript:void(0)" class="viewReadStatus">查看详情</a>
               		</li>
               	</s:if>
               	<s:if test="%{emailBody.copyToName!=''}">
               		<li>
               			<label>抄送人：</label>
               				${emailBody.copyToName}
               				<a href="javascript:void(0)" class="viewReadStatus">查看详情</a>
               		</li>
               	</s:if>
               	<s:if test="%{emailBody.secretToName!=''}">
               		<s:if test="%{from=='emailBody'}">
               			<li>
               			<label>密送人：</label>
               				${emailBody.secretToName}
               				<a href="javascript:void(0)" class="viewReadStatus">查看详情</a>
               		</li>
               		</s:if>
               	</s:if>
               	<li>
	               	<label>时间：</label>
	               	<s:date name="emailBody.createTime" format="yyyy-MM-dd HH:mm:ss"/>
               	</li>
           </ul>
       </div>
       <div class="meil_cont">
          <p>${emailBody.contentInfo}</p>
       </div>

    <input type="hidden" id="attachment" value="<s:property value="emailBody.attachment"/>"/>

    <div class="meil_annex" id="attachmentDiv">
        <h3>附件（<span id="attachmentCountSpan">0</span>个）</h3>
        <div class="annex">
	        <ul>
	
	        </ul>
	    </div>
        <div class="top5"></div>
    </div>
</div>
<input type="hidden" id="from" value="${from}"/>
<input type="hidden" id="emailToId" value="${emailToId}">
<input type="hidden" id="emailBodyId" value="${emailBody.id}">
<input type="hidden" id="hideBtn" value="${hideBtn}" />
<script type="text/javascript" src="${ctx}/js/logined/email/email_common.js"></script>
<script type="text/javascript" src="${ctx}/js/logined/email/email_detail.js"></script>

</body>
</html>
