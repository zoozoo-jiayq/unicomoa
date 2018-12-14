<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<% String instentceid = request.getParameter("id");
	request.setAttribute("instentceid",instentceid);
 %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<title>设置</title>
<jsp:include page="../../common/flatHead.jsp" />
<script type="text/javascript" >
	var basePath='${ctx}';
</script>
<link href="${ctx}/flat/css/reset.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/flat/plugins/form/skins/form_default.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/flat/plugins/annex/skins/annex_default.css" rel="stylesheet" type="text/css" />
 <script type="text/javascript" src="${ctx}/flat/plugins/org/org.js?version=${version}"></script>
<script type="text/javascript" src="${ctx}js/logined/notify/notifySet.js"></script>
<style type="text/css">
.inputTable th {
	width: 125px;
}
.inputTable td .formText {
	width: 60%
}
</style>
</head>
<body>
<div class="formPage">
   <div class="formbg">
	<input type="hidden" id="instentceid" name="instentceid" value="${instentceid}"/>
	<div class="big_title">
						<label>公告通知设置</label>
	</div>
	<div class="content_form">
	   <table width="100%" border="0" cellpadding="0" cellspacing="0"  class="inputTable">
		<tr style="display:none;">
				<th><label>是否允许评论：</label></th>
				<td><label class="radio">
                       <input type="radio" value="1" name="isComment"/>是
                    </label>
                    <label class="radio">
                        <input type="radio" value="0" name="isComment"/>否
                    </label>
                    <span class="tip">注：设置为是，启用评论功能，在查看详情页面，所有人员可以发表评论</span>
                </td>
		</tr>
	    <tr>
		 		<th><label>是否需要审核：</label></th>
				<td><label class="radio">
                       <input type="radio" value="1" name="isAuditing"/>是
                    </label>
                    <label class="radio">
                        <input type="radio" value="0" name="isAuditing"/>否
                    </label>
                <span class="tip">注：设置为是，启用审核功能，所有内容在经过审核后才能发布</span>   
                </td>
		</tr>
			<tr>
		 		<th><label>审核是否可以修改：</label></th>
				<td><label class="radio">
                       <input type="radio" value="1" name="isEdit"/>是
                    </label>
                    <label class="radio">
                        <input type="radio" value="0" name="isEdit"/>否
                    </label>
                <span class="tip">注：设置为是，启用审核修改功能，所有审核人可以对审核内容进行修改</span>   
                </td>
		</tr>
		 <tr  style="display:none;"  >
		 		<th><label>查看附件验证：</label></th>
				<td><label class="radio">
                       <input type="radio" value="1" name="isSeeAttach"/>是
                    </label>
                    <label class="radio">
                        <input type="radio" value="0" name="isSeeAttach"/>否
                    </label>
                	<span class="tip">注：该设置主要针对手机应用程序，设置后，手机端查看附件需要再次输入登录密码</span>
                </td>
		</tr>
			<tr>
		 		<th><label>列表图片显示：</label></th>
				<td><label class="radio">
                       <input type="radio" value="1" name="showImage"/>是
                    </label>
                    <label class="radio">
                        <input type="radio" value="0" name="showImage"/>否
                    </label>
                	<span class="tip">注：该设置主要针对手机应用程序，设置后，手机端查看公告时，列表中显示图片</span>    
                </td>
			</tr>
			<tr>
		 		<th><label>公告内容设置：</label></th>
				<td><label class="radio">
                       <input type="radio" value="1" name="isSmipleText" />纯文本框
                   </label>
                    <label class="radio">
                        <input type="radio" value="0" name="isSmipleText" />富文本框
                    </label>
                    <span class="tip">注：设置为纯文本框，输入公告内容时，只能输入文字。富文本框可自定义样式和插入图片</span>
                </td>
		   </tr> 
   			<tr>
		 		<th><label>客户端风格：</label></th>
				<td><label class="radio">
                       <input type="radio" value="1" name="clientType"/>风格1
                    </label>
                    <label class="radio">
                        <input type="radio" value="2" name="clientType"/>风格2
                    </label>
                    <label class="radio">
                        <input type="radio" value="3" name="clientType"/>风格3
                    </label>
                </td>
			</tr>
		   	<tr>
		 		<th><label>添加数据权限：</label></th>
				<td><label class="radio">
                       <input type="radio" value="1" name="isDataFilter" />是
                   </label>
                    <label class="radio">
                        <input type="radio" value="0" name="isDataFilter" />否
                    </label>
                    <span class="tip">注：设置为是时，只能审批本部门公告。</span>
                </td>
		   </tr> 
		<tr>
            <th>可直接发布人员：</th>
            <input type="hidden" id="publishUserIds" name="publishUserIds"/>
		    <td><textarea style="width:85%;" name="textarea" class="readOnlyTextarea"   readonly="readonly"   rows="5" name="publishUserNames" id="publishUserNames"></textarea><span class="addMember"  ><a class="icon_add" href="javascript:void(0)" onclick="selectAuthor();">添加</a><a  class="icon_clear" href="javascript:void(0)" onclick="clearAuthor()">清空</a></span>
		    	<p><span class="tip">注：可直接发布人员在发布公告时，无需通过审核，直接发布</span></p>
		    </td>
		      </tr>
	         </table>
			</div>
		</div>
	<div class="buttonArea">
			<input hideFocus="" value="确定" type="button" onclick="saveSysPara();" class="formButton_green" /><span class="blue">点击“确定”，保存修改设置</span>
	</div>
</div>
</body>
</html>
