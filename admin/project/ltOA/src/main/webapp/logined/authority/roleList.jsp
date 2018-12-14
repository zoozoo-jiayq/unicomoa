<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>角色权限管理</title>
<link href="${ctx}css/author_main.css" rel="stylesheet" type="text/css" />
<jsp:include page="../../common/flatHead.jsp" />
<script type="text/javascript">
	var basePath = "${ctx}";
</script>
<link href="${ctx}flat/css/reset.css" rel="stylesheet" type="text/css" />
<link href="${ctx}flat/css/Reminder.css" rel="stylesheet" type="text/css" />
<link href="${ctx}flat/plugins/form/skins/form_default.css" rel="stylesheet" type="text/css" />
<link href="${ctx}flat/plugins/datatable/skins/datatable_default.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" language="javascript" src="${ctx}js/logined/authority/roleList.js"></script>
<!-- 人员选择  start-->
<script type="text/javascript" src="${ctx}flat/js/base.js"></script>
<script type="text/javascript" src="${ctx}js/logined/authority/selectUser.js"></script>
<script type="text/javascript" src="${ctx}/flat/plugins/org/org.js"></script>
<script type="text/javascript" src="${ctx}js/common/hashmap.js"></script>
<script type="text/javascript" src="${ctx}js/common/treeNode.js"></script>
</head>
<body>
<input name="roleId" id="roleId" type="hidden"/>
<div class="list">
	<div class="searchArea">
				<table cellspacing="0" cellpadding="0">
						<tbody>
								<tr>
                                	<td class="right">&nbsp;</td>
									<td style="width:184px;"><div class="fButton greenBtn"><span class="add" id="addRole">新增</span></div>
                                    <div class="fButton orangeBtn">
                                      <span class="delete" id="deleteRole">删除</span>
                                    </div>
                                    </td>
								</tr>
						</tbody>
				</table>
		</div>
		<table cellpadding="0" cellspacing="0" class="pretty dataTable"	id="myTable">
			<thead>
				<th class="chk"><input class='chk' type='checkbox' id='total'/></th>
				<th class="num" id="no">序号</th>
				<th class="data_l" id="roleName" style="width:40%;">角色名称</th>
				<th class="longTxt" id="memo" style="width:60%;">角色说明</th>
				<th class="right_bdr0 data_l" style="width:250px;">操作</th>
			</thead>
		</table>
	</div>
</body>
</html>