$(document).ready(function() {
	// 单击修改
	$("#update").click(function() {
		updateAddressGroup();
		return false;
	});
	
	// 单击关闭
	$("#closeBtn").click(function() {
		art.dialog.close();
		return false;
	});
});

function updateAddressGroup() {
	var maintainGroupIds = $("#maintainGroupIds").val();
	if (null != maintainGroupIds && "" != maintainGroupIds && "," != maintainGroupIds.charAt(0)) {
		maintainGroupIds = "," + maintainGroupIds;
	}
	var maintainUserIds = $("#maintainUserIds").val();
	if (null != maintainUserIds && "" != maintainUserIds && "," != maintainUserIds.charAt(0)) {
		maintainUserIds = "," + maintainUserIds;
	}
	var maintainGroupNames = $("#maintainGroupNames").val();
	var maintainUserNames = $("#maintainUserNames").val();
    
	var paramData = {
		'addressGroup.id':$("#groupId").val(),
	    'addressGroup.maintainGroupIds' : $.trim(maintainGroupIds),
	    'addressGroup.maintainUserIds' : $.trim(maintainUserIds),
	    'addressGroup.maintainGroupNames' : $.trim(maintainGroupNames),
	    'addressGroup.maintainUserNames' : $.trim(maintainUserNames),
	    'maintain' : 1 // 标示只维护权限
	};

	$.ajax({
	    url : basePath + "addressGroup/setup_maintainAddressGroup.action",
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
		    	art.dialog({
		    		   title:"消息",
		    		   content:sprintf("addressbook.msg_success"),
		    		   width:320,
		    		   height:110,
		    		   icon:"succeed",
		    		   opacity:0.08,
		    		   lock:true,
		    		   ok:function(){},
		    		   close:function(){
		    			
		    		   }
		    		});
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
	$("#maintainUserIds").val(null);
	$("#maintainUserNames").val(null);
}

/**
 * 选择部门人员控件，选择人员
 */
function selectPerson() {
	openSelectUser(3, selectCallBackForm, null, $("#maintainUserIds").val(), 'maintainUser');// 选择人员
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

	$("#maintainUserIds").val(userIds);
	$("#maintainUserNames").val(userNames);

	if ("" != userIds) {
		hideError($("#maintainUserNames"));
	}
}

/**
 * 选择部门人员控件，选择部门
 */
function selectGroup() {
	openSelectUser(1, selectGroupCallBackForm, null, $("#maintainGroupIds").val(), 'maintainUser');// 选择部门
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

	$("#maintainGroupIds").val(userIds);
	$("#maintainGroupNames").val(userNames);

	if ("" != userIds) {
		hideError($("#maintainGroupNames"));
	}
}

/**
 * 清空部门
 */
function clearGroup() {
	$("#maintainGroupIds").val(null);
	$("#maintainGroupNames").val(null);
}

/**
 * 选择部门人员控件，选择角色
 */
function selectRole() {
	openSelectUser(2, selectRoleCallBackForm, null);// 选择角色
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
