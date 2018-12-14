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
<script type="text/javascript" src="${ctx}/flat/js/base.js"></script>
<script type="text/javascript" src="${ctx}/flat/plugins/org/org.js"></script>
<script type="text/javascript" charset="utf-8" src="${ctx}plugins/ueditor/editor_config.js"></script>
<script type="text/javascript" charset="utf-8" src="${ctx}plugins/ueditor/editor_all_min.js"></script>
<script type="text/javascript" src="${ctx}js/logined/message/send_message.js?v=1.1"></script>
<style type="text/css">
.inputTable th{width:58px;}
</style>
</head>
<body>
	<input type="hidden" id="requestType" <s:if test="requestType!=null"> value='${requestType}' </s:if>
	                                    <s:else>value='${paramValues.type[0]}'</s:else> />
	<input type="hidden" id="requestUserInfoId" value='${paramValues.userInfoId[0]}' />
	<input type="hidden" id="requestUserInfoName" value='${paramValues.userInfoName[0]}' />
	<input type="hidden" id="requestContentInfo" value='${paramValues.contentInfo[0]}' />
	<input id="userIds" name="userIds" value="<s:property value='#request.userInfoId'/>" type="hidden" />
  <div class="formPage">
    <div class="formbg">
      <div class="big_title">新增即时消息</div>
      <div class="content_form">
      <form id="form1">
        <table width="100%" cellspacing="0" cellpadding="0" border="0" class="inputTable">
          <tbody>
            <tr>
              <th><label>收信人：</label></th>
              <td>
						<textarea  id="userNames" readonly="readonly" rows="3" class="readOnlyTextarea"
						  valid="required" errmsg="message.message_user_not_null" ><s:property value='#request.userInfoName' /></textarea>
						<span class="addMember">
						<a class="icon_add" href="javascript:void(0);"onclick="selectPerson()">添加</a>
						 <a class="icon_clear" href="javascript:void(0);" onclick="clearPerson()">清空</a>
               			 </span>
                </td>
            </tr>
            <tr>
              <th><label>内容：</label></th>
              <td>
              			<input id="contentInfoInput" type="hidden"/>
              			<script id="contentInfo" type="text/plain"><s:property value='#request.contentInfo' /></script>
              </td>
            </tr>
          </tbody>
        </table>
        </form>
      </div>
    </div>
    <div class="buttonArea">
      <input type="button" class="formButton_green" value="发送" hidefocus="" id="sendBtn"/><span class="blue">点击发送，发送即时消息。</span>
    </div>
  </div>
</body>
</html>
