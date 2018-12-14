$(document).ready(function() {
	 
});
 

/**
 * 选择部门人员控件，选择人员
 */
function selectPerson() {
	openSelectUser(3, selectCallBackForm, null, $("#userIds").val(), 'userNames');// 选择人员
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
	setRole();
	
}
 

/**
 * 授权
 */
function setRole(){
	var reportInfoIds = _reportIds;
	var userIds = $("#userIds").val();
	var userNames = $("#userNames").val();
   
	var paramData = {
		'reportInfoIds':reportInfoIds,
	    'userIds' :userIds,
	    'userNames' : $.trim(userNames) 
	};

	$.ajax({
	    url : basePath + "reportInfo/setRole.action",
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
		    		getList();
	    }
	});
	 
	
}
 
