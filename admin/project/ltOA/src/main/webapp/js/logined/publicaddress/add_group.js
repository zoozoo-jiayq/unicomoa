$(document).ready(function() {
	// 单击修改
	$("#add").click(function() {
		addAddressGroup();
		return false;
	});
});

function addAddressGroup() {
	
	var valid = qytx.app.valid.check({form:$("#form1")[0]});
	// 框架校验
	if (!valid){
		return;
	}
	var orderNo = $("#orderNo").val();
	var name = $("#name").val();

	var userIds = $("#userIds").val();
	if (null != userIds && "" != userIds && "," != userIds.charAt(0)) {
		userIds = "," + userIds;
	}
	var groupIds = $("#groupIds").val();
	if (null != groupIds && "" != groupIds && "," != groupIds.charAt(0)) {
		groupIds = "," + groupIds;
	}	
	var roleIds = $("#roleIds").val();
	if (null != roleIds && "" != roleIds && "," != roleIds.charAt(0)) {
		roleIds = "," + roleIds;
	}
	var maintainUserIds = $("#maintainUserIds").val();
	if (null != maintainUserIds && "" != maintainUserIds && "," != maintainUserIds.charAt(0)) {
		maintainUserIds = "," + maintainUserIds;
	}
	var roleNames = $("#roleNames").val();
	var groupNames = $("#groupNames").val();
	var userNames = $("#userNames").val();
	var maintainUserNames = $("#maintainUserNames").val();
    var groupType = 2;
	var paramData = {
		'addressGroup.id':$("#groupId").val(),
	    'addressGroup.orderNo' : $.trim(orderNo),
	    'addressGroup.name' : $.trim(name),
	    'addressGroup.userIds' : $.trim(userIds),
	    'addressGroup.groupIds' : $.trim(groupIds),
	    'addressGroup.roleIds' : $.trim(roleIds),
	    'addressGroup.roleNames' : $.trim(roleNames),
	    'addressGroup.groupNames' : $.trim(groupNames),
	    'addressGroup.userNames' : $.trim(userNames),
	    'addressGroup.groupType' : groupType,
	    'addressGroup.maintainUserIds' : $.trim(maintainUserIds), //编辑对象授权id
	    'addressGroup.maintainUserNames' : $.trim(maintainUserNames) //编辑对象授权姓名
	};

	qytx.app.ajax({
		url	:	basePath + "addressGroup/setup_addAddressGroup.action",
		type:"post",
		data:paramData,
		shade	:	true,
		success	:	function(data){
			if (data == "nameExist") {
			    qytx.app.dialog.alert(sprintf("addressbook.msg_groupName_exist"));
		    } else if ("success" == data) {
			    window.location.href = basePath + "logined/publicaddress/list_setupgroup.jsp";
		    } else {
		    	qytx.app.dialog.alert(sprintf("addressbook.msg_add_group_error"));
		    }
		}
	});
}

/**
 * 清空人员
 */
function clearPerson() {
	$("#userIds").val(null);
	$("#userNames").val(null);
}

/**
 * 选择部门人员控件，选择人员
 */
function selectPerson() {
	openSelectUser(3, selectCallBackForm, null, $("#userIds").val());// 选择人员
}

/**
 * 部门人员选择的回调函数
 */
function selectCallBackForm(data, param) {
	var userIds = "";
	var userNames = "";
	$(data).each(function(i,item){
		userIds += item.id + ",";
		userNames += item.name + ",";
	});
	$("#userIds").val(userIds);
	$("#userNames").val(userNames);

	if ("" != userIds) {
		hideError($("#userNames"));
	}
}

/**
 * 选择部门人员控件，选择部门
 */
function selectGroup() {
	openSelectUser(1, selectGroupCallBackForm, null, $("#groupIds").val());// 选择部门
}

/**
 * 部门选择的回调函数
 */
function selectGroupCallBackForm(data, param) {
	var userIds = "";
	var userNames = "";
	data.each(function(i,item){
//	data.forEach(function(value, key) {
		userIds += item.id + ",";
		userNames += item.name + ",";
	});

	$("#groupIds").val(userIds);
	$("#groupNames").val(userNames);

	if ("" != userIds) {
		hideError($("#groupNames"));
	}
}

/**
 * 清空部门
 */
function clearGroup() {
	$("#groupIds").val(null);
	$("#groupNames").val(null);
}

/**
 * 选择部门人员控件，选择角色
 */
function selectRole() {
	openSelectUser(2, selectRoleCallBackForm, null, $("#roleIds").val(), 'publicAddressGroup');// 选择角色
}

/**
 * 角色选择的回调函数
 */
function selectRoleCallBackForm(data, param) {
	var userIds = "";
	var userNames = "";
	
	data.each(function(i,item){
		userIds += item.id + ",";
		userNames += item.name + ",";
	});
	
	$("#roleIds").val(userIds);
	$("#roleNames").val(userNames);

	if ("" != userIds) {
		hideError($("#rolerNames"));
	}
}

/**
 * 清空角色
 */
function clearRole() {
	$("#roleIds").val(null);
	$("#roleNames").val(null);
}


/**
 * 选择部门人员控件，选择编辑对象授权
 */
function selectUpdatePerson() {
	openSelectUser(3, selectUpdatePersonCallBack, null, $("#maintainUserIds").val());// 选择人员
}

/**
 * 编辑对象授权的回调函数
 */
function selectUpdatePersonCallBack(data, param) {
	var userIds = "";
	var userNames = "";
	$(data).each(function(i,item){
		userIds += item.id + ",";
		userNames += item.name + ",";
	});

	$("#maintainUserIds").val(userIds);
	$("#maintainUserNames").val(userNames);

	if ("" != userIds) {
		hideError($("#maintainUserNames"));
	}
}


/**
 * 清空人员
 */
function clearUpdatePerson() {
	$("#maintainUserIds").val(null);
	$("#maintainUserNames").val(null);
}