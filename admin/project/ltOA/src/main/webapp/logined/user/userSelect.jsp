<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<jsp:include page="../../common/taglibs.jsp" />
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
</script>
<script type="text/javascript" src="${ctx}js/common/validate_form.js"></script>
<script type="text/javascript" src="${ctx}js/common/showError.js"></script>
<script type="text/javascript" src="${ctx}js/logined/user/userSelect.js"></script>
<script type="text/javascript" src="${ctx}js/common/treeNode.js"></script>
<script type="text/javascript" src="${ctx}js/logined/user/userTree.js"></script>
<script type="text/javascript" src="${ctx}js/logined/user/selectUser.js"></script>
<script type="text/javascript" src="${ctx}js/common/hashmap.js"></script>
<script type="text/javascript" src="${ctx}js/logined/user/selectGroup.js"></script>
<script type="text/javascript" src="${ctx}plugins/My97DatePicker/WdatePicker.js"></script>
</head>
<body>
	<div class="input_new nobg" style="height: 300px">
	    <table class="listTitle" cellspacing="0" cellpadding="0" border="0">
    	<tbody><tr>
        	<td class="left"></td><td class="center">查询用户</td><td class="right"></td>
        </tr>
    </tbody></table>
		<form action="${ctx}logined/user/userList.jsp" id="userSelectFrom" method="get">
		<input type="hidden" id="roleId" name="roleId"/>
		<input type="hidden" name="type" value="${param.type}" />
		<input type="hidden" id="groupId" name="groupId"/>
			<table width="100%" border="0" cellpadding="0" cellspacing="0"
				class="inputTable">
				<tr style="display: none;">
					<th valign="top" style="width: 120px"><label>用户名</label>
					</th>
					<td valign="middle"><input type="text" class="formText" name="loginName" maxlength="16"
						size="30" /></td>
					<th style="width: 100px"><label>真实姓名</label>
					</th>
					<td><input type="text" class="formText" size="30" name="alterName" maxlength="16"/>
					</td>
				</tr>
				<tr>
					<th style="width: 100px"><label>姓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名</label>
					</th>
					<td><input type="text" class="formText" size="30" name="userName" maxlength="16"/>
					</td>
					<th><label>性&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;别</label>
					</th>
					<td><select name="sex"><option value="">全部</option>
					<option value="1">男</option>
					<option value="0">女</option>
					</select>
					</td>
				</tr>
				<tr>
					<th><label>部&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;门</label>
					</th>
					<td>
						<div id="treeContent" style="z-index:66;position:relative">
							<input id="groupSel" type="text" readonly="readonly"
								class="formText iconTree" size="30" style="width:80%" />
								<!-- <a class="icon_clear" href="#" id="parentRemove">清空</a><span class="selectdot" id="groupSel_div"></span>-->
							<div id="menuContent" style="position: absolute;display: none;">
								<ul id="groupTree" class="ztree"
									style="position: absolute; margin-top: 0; width: 200px;height:150px;overflow:auto; background: #ffffff;  border: 1px solid #8a9ba5"></ul>
							</div>
						</div>
                    </td>
					<th><label>角&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;色</label>
					</th>
					<td><input type="text" class="formText" style="width:30%" id="role"  maxlength="20" valid="required" errmsg="user.user_role_not_null" readonly="readonly"/><a
					class="icon_add" href="#" id="roleSelect">添加</a><a
					class="icon_clear" href="#" id="roleRemove">清空</a>
					</td>
				</tr>
				<tr>
					
					<th><label>登录时间排序</label>
					</th>
					<td><select name="loginOrder"><option value="">默认</option><option value="1">升序</option>
					<option value="0">降序</option>
					</select>
					</td>
					<th><label></label>
					</th>
					<td><select name="userState" style="display: none;"><option value="">全部</option><option value="0">是</option>
					<option value="1">否</option>
					</select>
					</td>
				</tr>
			</table>
		</form>
		<div class="buttonArea">
			<input value="查 询" class="formButton_green" type="submit" id="userSelect"/>
			&nbsp;&nbsp; <span class="mainButton" style="display: none;"><input 
				 value="导 出" id="exportExcel"
				type="button" />
			</span>
		</div>
	</div>
</body>
</html>