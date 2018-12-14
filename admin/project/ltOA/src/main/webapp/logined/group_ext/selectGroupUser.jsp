<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<jsp:include page="../../common/taglibs.jsp" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>群组人员管理</title>
<jsp:include page="../../common/head.jsp" />
<script type="text/javascript">
	var basePath = "${ctx}";
</script>
<script type="text/javascript" src="${ctx}js/logined/group_ext/selectGroupUser.js"></script>
<!-- 人员选择  start-->
<script type="text/javascript" src="${ctx}/js/user/selectuser.js"></script>
<script type="text/javascript" src="${ctx}js/common/hashmap.js"></script>
</head>

<body>
	<input type="hidden" id="userIds" value="${userIds}"/>
	<input type="hidden" id="groupId" value="${groupId}"/>
	<div class="big_frame"  >
	    <table class="listTitle" cellspacing="0" cellpadding="0" border="0">
   			<tbody>
   				<tr>
       				<td class="left"></td><td class="center">选择人员</td><td class="right"></td>
       			</tr>
   			</tbody>
 		</table>
	    <form action="#" id="groupForm">
			<table width="100%" border="0" cellpadding="0" cellspacing="0" class="inputTable">
				<tr>
					<th><label>人员：</label>
					</th>
					<td>
						<textarea class="readOnlyText" style="width:78%" rows="5" id="userNames" readonly="readonly">${userNames}</textarea>
                        <span class="addMember">
	                        <a class="icon_add" href="javascript:selectUser('userNames','userIds')">添加</a>
	                        <a class="icon_clear" href="javascript:cleanUser('userNames','userIds')">清空</a>
                        </span>
					</td>
				</tr>
			</table>
		</form>
		<div class="buttonArea">
			<input value="保 存" type="submit" class="formButton_green" id="groupAdd"/>
		</div>
	</div>
</body>
</html>