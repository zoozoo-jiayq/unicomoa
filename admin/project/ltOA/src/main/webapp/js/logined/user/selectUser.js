/**
 * 人员选择 REN
 */
/**
 * 主角色
 */
function selectUserRole() {
	openSelectUser(2, callBackRole, $("#roleId").val());// 选择人员
}
function callBackRole(data) {
	if (data) {
		var userIds = [];
		var userNames = [];
		data.forEach(function(value) {
					userIds.push(value.Id);
					userNames.push(value.Name);
				});
		$("#roleId").val(userIds.toString());
		$("#role").val(userNames.toString());
	}
}
/**
 * 主角色唯一
 */
function selectUserRoleOnly() {
	openSelectUser(2, callBackRoleOnly, $("#roleId").val());// 选择人员
}
function callBackRoleOnly(data) {
	if (data) {
		var userIds = [];
		var userNames = [];
		data.forEach(function(value) {
					userIds.push(value.Id);
					userNames.push(value.Name);
				});
		$("#roleId").val(userIds[0]);
		$("#role").val(userNames[0]);
	}
}
/**
 * 辅助角色
 */
function selectUserAssist() {
	openSelectUser(2, callBackAssist, $("#assistId").val());// 选择人员
}
function callBackAssist(data) {
	if (data) {
		var userIds = [];
		var userNames = [];
		data.forEach(function(value) {
					userIds.push(value.Id);
					userNames.push(value.Name);
				});
		$("#assistId").val(userIds.toString());
		$("#assist").val(userNames.toString());
	}
}
/**
 * 上级部门
 */
function selectGroup() {
	openSelectUser(1, callBackGroup, $("#groupId").val());
}
function callBackGroup(data) {
	if (data) {
		var userIds = [];
		var userNames = [];
		data.forEach(function(value) {
					userIds.push(value.Id);
					userNames.push(value.Name);
				});
		$("#groupId").val(userIds[0]);
		$("#group").val(userNames[0]);
	}
}

/**
 * 管理范围-指定部门
 */
function selectAppointGroup() {
	openSelectUser(1, callBackAppointGroup, $("#appointGroupIds").val(), $("#appointGroupIds").val());
}
function callBackAppointGroup(data) {
	
	if (data) {
		var userIds = [];
		var userNames = [];
	
		data.forEach(function(value) {
					userIds.push(value.Id);
					userNames.push(value.Name);
				});
		$("#appointGroupIds").val(userIds);
		$("#appointGroupNames").val(userNames);
	}
}
/**
 * 打开人员选择对话框 callback 回调函数，会把选择的人员以json格式返回
 * 
 * @showType 显示类型 1 显示部门 2显示角色 3显示人员
 * @callback 回调方法
 */
function openSelectUser(showType, callback, defaultSelectId) {
	var url = basePath + "/logined/user/selectuserSign.jsp?showType="
			+ showType + "&defaultSelectId=" + defaultSelectId;
	var title = "选择人员";
	if (showType == 1) {
		title = "选择部门";
	} else if (showType == 2) {
		title = "选择角色";
	}
	art.dialog.open(url, {
				title : title,
				width : 360,
				height : 407,
				lock : true,
			    opacity: 0.08,
				button : [{
							name : '确定',
							focus: true,
							callback : function() {
//								var iframe = this.iframe.contentWindow;
//						    	alert(iframe.userMap);		
								var userMap = art.dialog.data("userMap");
								callback(userMap);
								return true;
							}
						}, {
							name : '取消',
							callback : function() {
								return true;
							}
						}]
			}, false);

}