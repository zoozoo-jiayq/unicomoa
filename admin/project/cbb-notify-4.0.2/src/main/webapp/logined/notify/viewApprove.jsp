<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="/affairShow" prefix="affairShow" %>
<%
String notifyId = request.getParameter("notifyId");
String columnId = request.getParameter("columnId");
request.setAttribute("columnId",columnId);
 %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>公告通知审批</title>
<%@ include file="../../../common/flatHead.jsp"%>
<link href="${ctx}/flat/css/reset.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/flat/plugins/form/skins/form_default.css" rel="stylesheet" type="text/css" />
<link href="${ctx }flat/plugins/annex/skins/annex_default.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}js/logined/cbb/affairShow/affairShow.js"></script>
<script type="text/javascript" src="${ctx}js/logined/notify/viewApprove.js"></script>
</head>
<body>
<input type="hidden" id="notifyId" value="<%=notifyId %>"/>
<input type="hidden" id="columnId" name="columnId" value="${columnId}"/>
<div class="formPage">
  <div class="formbg">
    <div class="big_title">发布审核</div>
    <div class="content_form">
     <div class="small_title">公告详情</div>
	<form id="form1">
	<table width="100%" border="0" cellpadding="0" cellspacing="0"  class="inputTable">
	<tbody>
          <tr class="firstEmpty">
        	<th></th>
            <td></td>
            <th></th>
            <td></td>
          </tr>
			<tr>
				<th><label>标题：</label></th>
				<td colspan="3"><i id="subject"></i></td>
            </tr>
           <tr style="display: none">
				<th><label>发布人</label><br /></th>
				<td id="createUserName"></td>
			</tr>
			<tr>
				<th><label>发布范围：</label></th>
				<td colspan="3" >
				<font id="publishScope" ></font>
				</td>
			</tr>
			<tr style="">
            <th><label>类型：</label></th>
            <td id="notifyType"></td>
            <th><label>有效期：</label></th>
            <td id="limitDate"><span id="begDate"></span>&nbsp;-&nbsp;<span id="endDate"></span></td>           
          	</tr><%--
			<tr>
					<th><label>创建时间</label></th>
					<td id="createTimeStr"></td>
			</tr>
			<tr>
					<th><label>开始时间</label></th>
					<td id="begDate"></td>
			</tr>
			<tr>
					<th><label>终止时间</label></th>
					<td id="endDate"></td>
			</tr>
			--%>
		  <tr id="sms_remind_tr">
            <th><label>置顶设置：</label></th>
            <td><label class="radio">
                <input type="checkbox"  disabled="true" id="isTop"  name="isTop" >置顶</label></td>
            <input type="hidden" id="hidAffair" value="<affairShow:affairShow moduleCode="ggtz" />"/>
 			<th><label>提醒设置：</label></th>
           <td id="sendRemind">
           </td>		
          </tr>
          <tr>
            <th><label>内容：</label></th>
            <td colspan="3" id="content"></td>
          </tr>
          <tr id="attach" style="display: none">
            <th><label>上传附件：</label></th>
            <td colspan="3">
            	<div class="annex">
                    <%--<div class="icon"> <em class="doc"></em> </div>
                    --%><%--
                    <div class="txt">
                      <p>文件名文件名文件名文件名文件名文件名文件名文件名文件名文件名</p>
                      <p class="gray_9"><span class="progress_bar mr5"><a href="#" class="ml10">预览</a><a href="#" class="ml10">删除</a></p>
                    </div>
                    --%>
                    <ul id="attachmentList"></ul>
                    <div class="clear"></div>
              	</div>
            </td>
          </tr>
          </tbody>
          </table>
          <div class="small_title">审核意见</div>
      		<table width="100%" cellspacing="0" cellpadding="0" border="0" class="inputTable">
      		<tbody>
          		<tr>
            	<th width="70px"><label>我的意见：</label></th>
            	<td>
            	<label class="radio"><input name="approve" type="radio" checked="checked" value="1">通过</label>
                <label class="radio ml10"><input name="approve" type="radio" value="0">不通过</label>
                </td>
                </tr>
			<tr id="reasonContent"  style="display:none">
				<th><label>批复内容：</label></th>				
				<td>
<!-- 				valid="required" errmsg="notify.notify_reason_not_null" -->
				<textarea class="formTextarea" rows="2" id="reason"   maxlength="100"></textarea>
                 </td>
			</tr>
		</tbody>
	</table>
	</div>
	</div>
	<div class="buttonArea">
            <input hideFocus="" class="formButton_green" value="确定" type="button"  onclick="approveNotify()" />
			<input hideFocus="" class="formButton_grey" value="取消" type="button" onclick="javascript:window.location.href = document.referrer;"/>
	</div>
	</form>
</div>
</body>
</html>
