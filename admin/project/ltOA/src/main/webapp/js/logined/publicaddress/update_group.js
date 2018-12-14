$(document).ready(function() {
	// 单击修改
	$("#update").click(function() {
		updateAddressGroup();
		return false;
	});
});

function updateAddressGroup() {
	// 框架校验
	if (!validator(document.getElementById("form1"))) {
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

	var roleNames = $("#roleNames").val();
	var groupNames = $("#groupNames").val();
	var userNames = $("#userNames").val();
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
	    'addressGroup.groupType' : groupType
	};

	$.ajax({
	    url : basePath + "addressGroup/setup_updateAddressGroup.action",
	    type : "post",
	    dataType : "text",
	    data : paramData,
	    beforeSend:function(){
			$("body").lock();
		},
		complete:function(){
			$("body").unlock();
		},
	    success : function(data) {
		    if (data == "nameExist") {
			    art.dialog.alert(sprintf("addressbook.msg_groupName_exist"));
		    } else if ("success" == data) {
		    	window.location.href = basePath + "logined/publicaddress/list_setupgroup.jsp";
		    } else {
			    art.dialog.alert(sprintf("addressbook.msg_update_group_error"));
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
	data.forEach(function(value, key) {
		userIds += value.Id + ",";
		userNames += value.Name + ",";
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
	data.forEach(function(value, key) {
		userIds += value.Id + ",";
		userNames += value.Name + ",";
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
	openSelectUser(2, selectRoleCallBackForm, null, $("#roleIds").val());// 选择角色
}

/**
 * 角色选择的回调函数
 */
function selectRoleCallBackForm(data, param) {
	var userIds = "";
	var userNames = "";
	data.forEach(function(value, key) {
		userIds += value.Id + ",";
		userNames += value.Name + ",";
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
