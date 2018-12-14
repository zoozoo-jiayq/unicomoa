<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="/affairShow" prefix="affairShow" %>
<%
String id = request.getParameter("id");
request.setAttribute("id",id); 
String columnId = request.getParameter("columnId");
request.setAttribute("columnId",columnId); 
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>修改</title>
<jsp:include page="../../common/flatHead.jsp" />
	<script>
		window.UEDITOR_HOME_URL = basePath+"plugins/ueditor/";
	</script>
<link href="${ctx}/flat/css/reset.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/flat/plugins/form/skins/form_default.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/flat/plugins/annex/skins/annex_default.css" rel="stylesheet" type="text/css" />
<script charset="utf-8" src="<%=request.getContextPath() %>/plugins/kindeditor/kindeditor-min.js"></script>
<script type="text/javascript" charset="utf-8" src="${ctx}plugins/ueditor/editor_config.js"></script>
<script type="text/javascript" charset="utf-8" src="${ctx}plugins/ueditor/editor_all_min.js"></script>
<script type="text/javascript" src="${ctx}/flat/plugins/org/org.js?version=${version}"></script>
<script type="text/javascript" src="${ctx}js/logined/cbb/affairShow/affairShow.js"></script>
<script type="text/javascript" src="${ctx}js/logined/notify/approveUpdate.js"></script>
<style>
.inputTable th{ width:75px;}
.uploadify{margin:10px 0px;}
.formPage .readOnlyTextarea{
	padding-top:5px;
}
</style> 
</head>
<body>
<input type="hidden" id="isSmipleText" name="isSmipleText"/>
<input type="hidden" id="id" value="${id}"/>
<input  type="hidden" id="columnId" name="columnId" value="${columnId}"/>
<input  type="hidden" id="createUserId" name="createUserId"/>
<div class="formPage">
	<div class="formbg">
	<form id="form1">
	<div class="big_title">修改内容</div>
  		 <div class="content_form">
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
			<td colspan="3"><input id="subject" name="subject" type="text" maxlength="50" class="formText" valid="required" errmsg="notify.notify_subject_not_null"></input>
			</td>
        </tr>
        <tr id="userDiv" >
			<th><label>发布范围：</label></th>
			<td colspan="3">
				<input type="hidden" id="publishScopeUserIds" name="publishScopeUserIds"/>
				<input type="hidden" id="columnId" name="columnId" value="${id}"/>
				<textarea class="readOnlyTextarea" style="width:85%" rows="5" id="publishScopeUserNames" name="publishScopeUserNames" readonly="readonly"></textarea>
				<span class="addMember">
				<a class="icon_add" href="#" onclick="selectAuthor('user');">添加</a>
				<a class="icon_clear" href="#" onclick="clearAuthor('user')">清空</a>
				</span>
			</td>
		</tr>
		<tr>
			<th><label>类型：</label></th>
			<td colspan="3">
				<select id="notifyType"></select>
			</td>
		</tr>
		<tr>
			<th><label>有效期：</label></th>
			<td colspan="3">
			<label class="radio"><input type="radio" name="limit" onclick="isLimit(1)" value="1" id="noLimit"/>不限制</label>
            <label class="radio"><input type="radio" name="limit" onclick="isLimit(0)" value="0" id="yesLimit"/>启用</label>
            <span id="showDate">
			 <input  type="text" class="Wdate formText" value="<%=new java.text.SimpleDateFormat("yyyy-MM-dd").format(new Date()) %>" size="20" name="begin_date" id="begDate" onClick="WdatePicker({maxDate :'#F{$dp.$D(\'endDate\')}',skin:'default',dateFmt:'yyyy-MM-dd',minDate:'%y-%M-%d'})" style="width:110px;"/>
			-
			<input  type="text" class="Wdate formText" size="20" name="end_date" id="endDate" onClick="WdatePicker({skin:'default',dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'begDate\') || \'%y-%M-%d\'}'})" style="width:110px;" />
			</span>
			<span class="tip">不选择结束日期为手动终止</span>
			</td>
		</tr>
		<tr>
			<th><label>置顶设置：</label></th>
			<td><label class="radio"><input type="checkbox" id="isTop" value="1"/>置顶本记录</label></td>
			<input type="hidden" id="hidAffair" value="<affairShow:affairShow moduleCode="ggtz" />"/>
 			<th><label>提醒设置：</label></th>
           <td id="sendRemind">
           </td>		
		</tr>
		<tr style="display: none">
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
        <th><label>内容：</label></th>
	         <td colspan="3" >
	            <div id="text1" style="display:none">
	                 <input id="contentInfoInput" type="hidden"/>
				     <script id="contentInfo" type="text/plain" style="width:100%"></script>
				</div>
				<div id="text2" style="display:none">
				     <textarea class="formTextarea" rows="5" id="contentInfo2"style="width:100%"></textarea>
				</div>
	            </td>
	      </tr>
	    <tr>
			<th><label>上传附件：</label></th>
			<td>
			    <input type="hidden" id="attachmentId" name="attachmentId"/>
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
	</div>
	</div>
	<div class="buttonArea"> 
			<input hideFocus="" value="保存" class="formButton_green" type="button" onClick="tijiao(1);"/>
			<input hideFocus="" onClick="goBack();" class="formButton_grey" value="返 回" type="button" />
	</div>
	</form>
</div>
<script type="text/javascript">
	function isLimit(num){
		if(num==1){
			$("#showDate").hide();
		}else{
			$("#showDate").show();
		}
	}
</script>
</body>
</html>
