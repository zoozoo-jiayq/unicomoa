<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<jsp:include page="../../common/taglibs.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>人员列表</title>
<style type="text/css">
.inputTable th {
	width: 150px;
}
</style>
<jsp:include page="../../common/head.jsp" />
<script type="text/javascript">
	var basePath = "${ctx}";
	//点击导入
	jQuery(document).ready(function($) {

		$("#importUser").click(function() {
			uploadUser();
			return false;
		});
	});
</script>
<script type="text/javascript" src="${ctx}js/common/placeholder.js"></script>
<script type="text/javascript"
	src="${ctx}js/logined/user/secret_userList.js?version=${version}"></script>
<script type="text/javascript"
	src="${ctx}js/common/validate_form.js?version=${version}"></script>
<script type="text/javascript"
	src="${ctx}js/common/showError.js?version=${version}"></script>
<script type="text/javascript"
	src="${ctx}js/logined/user/importuser.js?version=${version}"></script>

<script type="text/javascript"
	src="${ctx}js/common/treeNode.js?version=${version}"></script>
<script type="text/javascript"
	src="${ctx}js/logined/user/userTree.js?version=${version}"></script>
<script type="text/javascript"
	src="${ctx}js/common/ajaxfileupload.js?version=${version}"></script>

<script type="text/javascript"
	src="${ctx}js/logined/user/userSelect.js?version=${version}"></script>
<script type="text/javascript"
	src="${ctx}js/logined/user/selectUser.js?version=${version}"></script>
<script type="text/javascript"
	src="${ctx}js/common/hashmap.js?version=${version}"></script>
<script type="text/javascript"
	src="${ctx}js/logined/user/selectGroup.js?version=${version}"></script>
</head>
<body>
	<form id="userSelectFrom">
		<input type="hidden" id="type" value="${param.type}" /> 
		<input name="groupId" type="hidden" id="groupId" value="${param.groupId}" /> 
		<input type="hidden" id="loginName" value="${param.loginName}" /> 
		<input type="hidden" id="alterName" value="${param.alterName}" /> 
		<input name="roleId" type="hidden" id="roleId" value="${param.roleId}" /> 
		<input name="loginOrder" type="hidden" id="loginOrder" value="${param.loginOrder}" />
		<input id="mobileViewState" type="hidden" value="-1"/>

	</form>
	<div class="list">
		<c:if test="${paramValues.type[0]!='view'}">
		</c:if>
		<div class="listbtn">
			<table cellpadding="0" cellspacing="0">
				<tr>
					<td style="width: 40%">
						<div class="tDiv2">

							<div class="fbutton" id="yc">
								<span><a class="ycry" href="javascript:void(0)" id="btnUserState">仅显示信息隐藏人员</a></span>
							</div>
							<div class="fbutton" id="qb" style="display:none">
								<span><a class="ycry" href="javascript:void(0)">显示全部人员</a></span>
							</div>
							<div class="fbutton">
								<span><a class="plyc" href="javascript:void(0)"
									onclick="hideAll();">批量信息隐藏</a></span>
							</div>
						</div>
					</td>
					<td style="width: 60%" class="right">
					<input name="userName"
						class="formText" type="text" style="width:200px;" id="userName" placeholder="请输入姓名/手机号进行查询" /> &nbsp; <input
						value="查询" class="formButton" id="searchButton" type="button" />
					</td>
				</tr>
			</table>
		</div>

		<div class="newtable">
			<table cellpadding="0" cellspacing="0" class="pretty dataTable"
				id="userTable">
			</table>
		</div>
	</div>
	 <script>funPlaceholder(document.getElementById("userName"));</script>
</body>
</html>