<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>新增从属部门</title>
		<jsp:include page="../../common/flatHead.jsp" />
		<link href="${ctx }flat/css/reset.css" rel="stylesheet" type="text/css" />
		<link href="${ctx }flat/plugins/form/skins/form_default.css" rel="stylesheet" type="text/css" />
		<link href="${ctx }flat/plugins/datatable/skins/datatable_default.css" rel="stylesheet" type="text/css" />
		<link href="${ctx }flat/plugins/tree/skins/tree_default.css" rel="stylesheet" type="text/css" />
	    <script type="text/javascript" src="${ctx}js/user/selectGroup.js"></script>
	</head>
  
	<body class="bg_white">
		<input type="hidden" id="groupId" value="${param.groupId }"/>
		<input type="hidden" id="id" value="${param.id }"/>
		<input type="hidden" id="order" value="${param.order }"/>
		
		<div class="formPage elasticFrame">
			<table width="100%" cellspacing="0" cellpadding="0" border="0" class="inputTable">
				<tbody>
					<tr>
						<th><em class="requireField">*</em>单位/部门：</th>
						<td>
							<input id="groupSel" type="text" readonly="readonly" class="formText iconTree" style="width: 375px;" value="${param.groupName }" />
	                        <span class="selectdot" id="groupSel_div"></span>
							<div id="menuContent" style="position: absolute;">
								<ul id="groupTree" class="ztree"
									style="position: absolute; margin-top: 0; width: 375px;height:150px;overflow:auto; background: #ffffff;  border: 1px solid #8a9ba5;z-index:1000"></ul>
							</div>
						</td>
					</tr>
					<tr>
						<th>职务：</th>
						<td><input type="text" class="formText" style="width:375px;" id="job" value="${param.job }"/></td>
					</tr>
					<tr>
						<th>办公电话：</th>
						<td><input type="text"   onkeyup="this.value=this.value.replace(/[^0-9]/g,'')" maxlength="12"   class="formText" style="width:375px;" id="telphone" value="${param.telphone }"/></td>
					</tr>
					<tr>
						<th>排序号：</th>
						<td><input type="text" class="formText" style="width:150px;"  onkeyup="this.value=this.value.replace(/[^0-9]/g,'')" id="orderIndex" maxlength="8" value="${param.orderIndex }"/>
						<span class="tip">设定默认的排序号，序号大的在最后</span></td>
					</tr>
				</tbody>
			</table>
		</div>
	</body>
</html>
