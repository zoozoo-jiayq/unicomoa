<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>人员列表</title>
<jsp:include page="../../common/flatHead.jsp" />
<link href="${ctx}flat/css/reset.css" rel="stylesheet" type="text/css" />
<link href="${ctx}flat/css/Reminder.css" rel="stylesheet" type="text/css" />
<link href="${ctx}flat/plugins/datatable/skins/datatable_default.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}flat/js/base.js"></script>
<script type="text/javascript" src="${ctx}flat/plugins/dialog/iframeTools.js"></script>
<script type="text/javascript" src="${ctx}js/logined/user/loginUserList.js"></script>
<script type="text/javascript" src="${ctx}js/common/placeholder.js"></script>
</head>
<body>
<div class="list">
		<div class="searchArea">
				<table cellspacing="0" cellpadding="0">
						<tbody>
								<tr>
                                    <td class="right">
                                    <span class="fr">
									关键字：<input id="key_word" type="text" placeholder="用户名/姓名" style="width : 200px" class="formText" maxlength="35" name=""/>
									<input id="searchLoginUser" type="button" class="searchButton" value="查询"/></span>
                                    </td>
                                    <td style="width:92px;"><div class="fButton greenBtn"><span id="addLoginUser" class="add">新增</span></div></td>
								</tr>
						</tbody>
				</table>
		</div>
		
		<table id="userTable" cellpadding="0" cellspacing="0"  class="pretty dataTable">
			<thead>
				<tr>
					<th class="num" id="no">排序</th>
					<th style="width: 110px;" id="loginName" class="tdCenter">用户名</th>
                    <th style="width: 90px;" id="userName">姓名</th>
					<th style="width: 40px;">性别</th>
					<th id="groupName" class="data_l">单位/部门</th>
					<th id="job" class="tdCenter">职务</th>
                    <th id="role" class="longTxt">角色</th>
                    <th style="width:90px;" id="registerTime">创建日期</th>
                    <th style="width:60px;">状态</th>
                    <th class="oper right_bdr0" style="width:80px;">操作</th>
				</tr>
			</thead>
		</table>
	</div>
</div>
<script>funPlaceholder(document.getElementById("key_word"));</script>
</body>
</html>