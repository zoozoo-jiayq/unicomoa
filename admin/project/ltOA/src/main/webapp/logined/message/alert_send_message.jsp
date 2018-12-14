<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>即时消息-发起消息</title>
<jsp:include page="../../common/flatHead.jsp" />
<script>
	   var basePath = "${ctx}";
		window.UEDITOR_HOME_URL = basePath+"plugins/ueditor/";
</script>
<link href="${ctx}/flat/css/reset.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/flat/css/main.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/flat/plugins/form/skins/form_default.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/flat/plugins/annex/skins/annex_default.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}/flat/js/base.js"></script>
<script type="text/javascript" src="${ctx}/flat/plugins/org/org.js"></script>
<script type="text/javascript" src="${ctx}js/common/hashmap.js"></script>
<script type="text/javascript" src="${ctx}js/user/user.js"></script>
<script type="text/javascript" charset="utf-8" src="${ctx}plugins/ueditor/editor_config.js"></script>
<script type="text/javascript" charset="utf-8" src="${ctx}plugins/ueditor/editor_all_min.js"></script>
<script type="text/javascript" src="${ctx}js/logined/message/send_message.js?v=1.1"></script>
<script type="text/javascript" src="${ctx}/flat/plugins/dialog/artDialog.js?skin=default"></script>
<script type="text/javascript" src="${ctx}/flat/plugins/dialog/iframeTools.js"></script>
<style type="text/css">
.inputTable th{width:60px;}
</style>
</head>
<body  class="bg_white">
	<input type="hidden" id="requestType" <s:if test="requestType!=null"> value='${requestType}' </s:if>
	                                    <s:else>value='${paramValues.type[0]}'</s:else> />
	<input type="hidden" id="requestUserInfoId" value='${paramValues.userInfoId[0]}' />
	<input type="hidden" id="requestUserInfoName" value='${paramValues.userInfoName[0]}' />
	<input type="hidden" id="requestContentInfo" value='${paramValues.contentInfo[0]}' />
	<input id="userIds" name="userIds" value="<s:property value='#request.userInfoId'/>" type="hidden" />
<div class="elasticFrame formPage">
	<form id="form1">
		<table width="100%" cellspacing="0" cellpadding="0" border="0" class="inputTable">
               <tbody>
                          <tr>
                            <th><label>收信人：</label></th>
                            <td>
                            	<textarea id="userNames" 
                             	valid="required" errmsg="message.message_user_not_null" readonly="readonly" class="readOnlyTextarea" style="width:85%;"></textarea>
                             	<span class="addMember"><a class="icon_add" onclick="selectPerson()" href="javascript:void(0)">添加</a>
                             	<a href="javascript:void(0);" onclick="clearPerson()" class="icon_clear">清空</a></span>
                             </td>
                          </tr>
                          <th><label>内容：</label></th>
                          <td>
                          	<input id="contentInfoInput" type="hidden"/>
                          	<script id="contentInfo" type="text/plain"><s:property value='#request.contentInfo' /></script>
                          </td>
              </tbody>
           </table>
       </form>
    <div class="buttonArea" style="display:none">
      <input type="button" class="formButton_green" value="发送" hidefocus="" id="sendBtn"/><span class="blue">点击发送，发送即时消息。</span>
    </div>
  </div>
</body>
</html>
