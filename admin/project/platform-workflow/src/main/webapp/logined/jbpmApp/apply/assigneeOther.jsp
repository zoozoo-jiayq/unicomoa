<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="../../../common/flatHead.jsp"></jsp:include>
<link href="${ctx }/flat/css/reset.css" rel="stylesheet" type="text/css" />
<link href="${ctx }/flat/plugins/annex/skins/annex_default.css" rel="stylesheet" type="text/css" />
<link href="${ctx }/flat/plugins/form/skins/form_default.css" rel="stylesheet" type="text/css" />
<link href="${ctx }/flat/css/handle.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx }/js/user/select_document_user.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>收文登记</title>
</head>
<body class="bg_white">
<div class="elasticFrame formPage">
	<input type="hidden" name="taskId" id="taskId" value="${taskId }"/>
		<table width="100%" border="0" cellpadding="0" cellspacing="0"  class="inputTable">
		<tr>
			<th><label>工作名称：</label></th>
			<td>${assigneeSet.title }</td>
		</tr>		
		<tr>
		  <th><label>当前步骤：</label></th>
		  <td>${assigneeSet.taskName }</td>
  		</tr>
		<tr>
		  <th><label>委托人：</label></th>
		  <td>${assigneeSet.oldAssignee }</td>
  		</tr>
  		<tr>
			<th><label>被委托人：</label></th>
			<td><input name="newAssignee" id="newAssigneeName" type="text" class="readOnlyText" readonly="readonly"  style="width:200px" />
			<input name="newAssignee" id="newAssigneeId" type="hidden"  />
			<span class="addMember auto_addMember"  ><a href="javascript:void(0);"  id="addUser" class="icon_add">选择</a></td>
		</tr>
	</table>
	</div>
</form>
<script type="text/javascript">
$("#addUser").click(function(){
	openDocSelectUser(3,function(data,param){
		   data.forEach(function(value, key) {
		       if(value.Type == "user"){
		    	   $("#newAssigneeName").val(value.Name);
		    	   $("#newAssigneeId").val(value.Id);
		       }
		    });
	},0);
});
</script>
</body>
</html>