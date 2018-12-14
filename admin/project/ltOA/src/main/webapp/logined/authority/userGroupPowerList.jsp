<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>管理范围设置</title>
<link href="${ctx}css/author_main.css" rel="stylesheet" type="text/css" />
<jsp:include page="../../common/flatHead.jsp" />
<script type="text/javascript">
	var basePath = "${ctx}";
</script>
<link href="${ctx}flat/css/reset.css" rel="stylesheet" type="text/css" />
<link href="${ctx}flat/css/Reminder.css" rel="stylesheet" type="text/css" />
<link href="${ctx}flat/plugins/form/skins/form_default.css" rel="stylesheet" type="text/css" />
<link href="${ctx}flat/plugins/datatable/skins/datatable_default.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" language="javascript" src="${ctx}js/logined/authority/userGroupPowerList.js"></script>
<script type="text/javascript" src="${ctx}flat/js/base.js"></script>
<script type="text/javascript" src="${ctx}plugins/datatable/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="${ctx}plugins/datatable/selecedForDatatablePagination.js"></script>
<script type="text/javascript" src="${ctx}js/user/selectuser.js"></script>
<script type="text/javascript" src="${ctx}js/common/treeNode.js"></script>
<script type="text/javascript" src="${ctx}js/common/placeholder.js"></script>
</head>
<body>
<input type="hidden" id="groupIds" name="groupIds"/>
<div class="nav">
	<div class="navLeft">单位管理<span>></span>管理范围设置<span></div>
</div>
<div class="list">
	<div class="searchArea">
				<table cellspacing="0" cellpadding="0">
						<tbody>
								<tr>
                                	 <td class="right"><label>关键字：</label>
							            <span style="position:relative;">
							            <input type="text" id="searchkey"  class="formText searchkey" placeholder="姓名/手机号码" />
							            </span>
							            <input type="button" value="查询" class="searchButton" id="search"/></td>
									<td style="width:184px;"><div class="fButton greenBtn"><span class="add" id="addData">新增</span></div>
                                    <div class="fButton orangeBtn">
                                      <span class="delete" id="deleteData">删除</span>
                                    </div>
                                    </td>
								</tr>
						</tbody>
				</table>
		</div>
		<table cellpadding="0" cellspacing="0" class="pretty dataTable"	id="myTable">
		</table>
	</div>
	<script>funPlaceholder(document.getElementById("searchkey"));</script>
</body>
</html>