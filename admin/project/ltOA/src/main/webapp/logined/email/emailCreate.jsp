<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="/affairShow" prefix="affairShow" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

    <title>邮件编辑</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link rel="stylesheet" type="text/css" href="${ctx}/plugins/upload/uploadify.css">
	<jsp:include page="../../common/flatHead.jsp"/>
	<link href="${ctx}flat/css/reset.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}flat/css/mail.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}flat/plugins/form/skins/form_default.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}flat/plugins/annex/skins/annex_default.css" rel="stylesheet" type="text/css" />
	
    
    <script type="text/javascript" src="${ctx}/plugins/upload/jquery.uploadify.min.js"></script>
    <script type="text/javascript" src="${ctx}/flat/plugins/org/org.js"></script>
    <script type="text/javascript" src="${ctx}/js/common/hashmap.js"></script>
    <script type="text/javascript" src="${ctx}/js/common/treeNode.js"></script>
    <script type="text/javascript" src="${ctx}/js/logined/email/email_attachment.js"></script>
    <script type="text/javascript" src="${ctx}/js/logined/email/email_common.js"></script>
    <script type="text/javascript" src="${ctx}/js/logined/email/email_common_select_user.js"></script>
    <script type="text/javascript" src="${ctx}/js/common/validate_form.js"></script>
    <script type="text/javascript" src="${ctx}/js/common/showError.js"></script>
    <script type="text/javascript" src="${ctx}/js/logined/email/email_create.js"></script>
    <script type="text/javascript" src="${ctx}js/logined/affairs/affairs_check.js"></script>
    <script type="text/javascript">
        window.UEDITOR_HOME_URL = basePath+"plugins/ueditor/";
    </script>
    <script type="text/javascript" charset="utf-8" src="${ctx}plugins/ueditor/editor_config.js"></script>
    <script type="text/javascript" charset="utf-8" src="${ctx}plugins/ueditor/editor_all_min.js"></script>
    <script type="text/javascript" src="${ctx}js/logined/cbb/attachment/attachment.js"></script> 
    <script type="text/javascript" src="${ctx}js/logined/cbb/affairShow/affairShow.js"></script> 
    <script type="text/javascript">
	  $(document).ready(function(){
		  $("#add_lxr").hover(function(){
		     $(this).find(".cz_list").show();
		  },function(){
			   $(this).find(".cz_list").hide();
		  });
	  });
	</script>
	<style type="text/css">
	.inputTable th{width:70px;}
	.meil_cz ul li{
		margin-left: -6px;
	}
	</style>
</head>

<body>
<s:set value="%{from=='emailBodyEditNotRead'}" var="emailBodyNotRead"/>
<div class="formPage">
	<div class="formbg">
        	<div class="big_title"><s:if test="#emailBodyNotRead">编辑已发送邮件</s:if><s:else>写邮件</s:else></div>
          <div class="content_form">
    <table width="100%" cellspacing="0" cellpadding="0" border="0" class="inputTable">
      <tbody>
            <s:if test="%{errorMsg!=null}">
                <tr>
                    <th><label>错误消息</label></th>
                    <td>
                        <span style="font-size: 12px; color: red">${errorMsg}</span>
                    </td>
                </tr>
            </s:if>
            <tr>
                <th><em class="requireField">*</em><label>收件人：</label></th>
                <td>
                    <input type="hidden" name="emailBody.toId" id="toId" value="${emailBody.toId}"/>
                    <s:if test="#emailBodyNotRead">
                        ${emailBody.toName}
                    </s:if>
                    <s:else>
                        <textarea class="readOnlyTextarea" readonly="readonly" rows="3" name="emailBody.toName"
                                  id="toName">${emailBody.toName}</textarea>
                                 <span class="addMember">
                        <a class="icon_add" href="javascript:selectUser('toName','toId')">添加</a>
                        <a class="icon_clear" href="javascript:cleanUser('toName','toId')">清空</a>
                        </span>
                       <div class="meil_cz">
                       <ul>
                          	<li>
                        		<a id="addCopyTo" href="javascript:void(0);">添加抄送</a>
                        	</li>
                        	<li>&nbsp;|</li>
                        	<li>
		                        <a id="addSecretTo" href="javascript:void(0);">添加密送</a>
                        	</li>
                        	<li>&nbsp;|</li>
                        	<li id="add_lxr">
                        		<a href="javascript:void(0)">最近联系人</a>
                            	<div class="cz_list">
	                                <s:iterator value="#recentUserInfoList" var="userInfo">
	                                    <p>
	                                        <a href="javascript:addRecentUserTo(${userInfo.userId},'${userInfo.userName}')">${userInfo.userName}</a>
	                                    </p>
	                                </s:iterator>
	                                <s:if test="%{#recentUserInfoList.size==0}">
	                                    <p><a href="javascript:void(0)">无</a></p>
	                                </s:if>
                            	</div>
                            </li>
                           </ul>
                        </div>
                    </s:else>
                </td>
            </tr>
            <s:if test="#emailBodyNotRead">
                <tr>
                    <th>
                        <label>发送时间：</label>
                    </th>
                    <td><s:date name="emailBody.sendTime" format="yyyy-MM-dd HH:mm:ss" /></td>
                </tr>
            </s:if>
            <tr id="copyToTr" style="display: none">
                <th><label>抄送：</label></th>
                <td>
                    <input id="copyToId" name="emailBody.copyToId" type="hidden" value="${emailBody.copyToId}">
                    <s:if test="#emailBodyNotRead">
                        ${emailBody.copyToName}
                    </s:if>
                    <s:else>
                        <input type="text" class="readOnlyText" name="emailBody.copyToName" id="copyToName"
                               value="${emailBody.copyToName}" readonly="readonly" style="width:80%" />
                        <span class="addMember" style="width:auto">
	                        <a class="icon_add" href="javascript:selectUser('copyToName','copyToId')">添加</a>
	                        <a class="icon_clear" href="javascript:cleanUser('copyToName','copyToId')">清空</a>
                    	</span>
                    </s:else>
                </td>
            </tr>
            <tr id="secretToTr" style="display: none">
                <th><label>密送：</label></th>
                <td>
                    <input id="secretToId" type="hidden"  name="emailBody.secretToId" value="${emailBody.secretToId}">
                    <s:if test="#emailBodyNotRead">
                        ${emailBody.secretToName}
                    </s:if>
                    <s:else>
                        <input type="text" class="readOnlyText" name="emailBody.secretToName" id="secretToName"
                               value="${emailBody.secretToName}" readonly="readonly" style="width:80%" />
                        <span class="addMember" style="width:auto">	
	                        <a class="icon_add" href="javascript:selectUser('secretToName','secretToId')">添加</a>
	                        <a class="icon_clear" href="javascript:cleanUser('secretToName','secretToId')">清空</a>
                    	</span>
                    </s:else>
                </td>
            </tr>
            <tr>
                <th><label>主题：</label></th>
                <td>
                    <input type="text" class="formText" style="width:79%"  name="emailBody.subject"
                           value="${emailBody.subject}" id="subject" maxlength="100"/>
&nbsp;				
                    <s:if test="%{#emailBodyNotRead==false}">
						<input type="hidden" value="${emailBody.importantLevel }" id="hiddenImportantLevel" />
                        <select name="emailBody.importantLevel"  id="importantLevel">
				          <option value="0">一般邮件</option>
				          <option value="1">重要邮件</option>
				          <option value="2">非常重要</option>
				        </select>
                    </s:if>
                </td>
            </tr>
            <tr>
                <th><label>内容：</label></th>
                <td>
                	<input id="contentInfoInput" type="hidden"/>
                    <script id="contentInfo" type="text/plain">${emailBody.contentInfo}</script>
                </td>
            </tr>
            <tr>
                <th>
                    <label>附件：</label>
                </th>
                <td  id="mailAttachmentTd">
                    <div style="padding-top:7px;"><input id="file_upload" type="file" multiple="true"/>
                    <input type="hidden" name="emailBody.attachment" id="attachment"
                           value="<s:property value="emailBody.attachment"/>"/>
                    <input type="hidden" name="emailBody.attachmentSize" id="attachmentSize"
                           value="${emailBody.attachmentSize}"/></div>
                    <div id="queue" style="display: none;"></div>
                    <div class="annex">
                    <ul id="attachUL">
                    </ul>
                    <div class="clear"></div>
  </div>
                </td>
            </tr>
            <tr>
            	<affairShow:affairShow moduleCode="nbyj" sendType="${emailBody.sendType }"/>
            </tr>
        </table>
    </form>
</div>
</div>
            <s:if test="#emailBodyNotRead">
                <div class="buttonArea">
                    <input type="button" class="formButton_green" value="保  存" onclick="return checkSaveAndSend(false)"/>
                    <input type="button" class="formButton_grey" onclick="javascript:window.location.href = document.referrer;" value="返  回"/>
                </div>
            </s:if>
            <s:else>
                <input type="hidden" name="send" id="send" value="0"/>                       
                <div class="buttonArea">
                <input type="button" class="formButton_green" value="立即发送" onclick="return checkSaveAndSend(true)"/>
                    <input type="button" class="formButton_grey" value="保存到草稿箱" onclick="return saveDraft()"/>
                 <s:if test="%{hideBtn!='returnBtn'}">           
                    <input type="button" class="formButton_grey" onclick="javascript:history.go(-1);" value="返  回"/>      
                </s:if>
                </div>                                         
            </s:else>

        <input type="hidden" id="saveAction" value="${ctx}/logined/email/emailBodySave.action"/>
        <input type="hidden" id="updateAction" value="${ctx}logined/email/emailBodyUpdate.action"/>
        <input type="hidden" name="emailBody.id" id="emailBodyId" value="${emailBody.id}"/>
        <input type="hidden" name="from" id="from" value="${from}"/>
        <input type="hidden" id="autoSave" value="0"/>
</div>
<input type="hidden" id="defaultContentJSONMap" value="<s:property value="defaultContentJSONMap" />"/>
<input type="hidden" id="defaultToUsers" value="<s:property value="defaultToUsers" />"/>
</body>
</html>
