$(document).ready(function() {
	// 单击修改
	$("#add").click(function() {
		addAddressGroup();
		return false;
	});

	// 单击返回
	$("#back").click(function() {
		window.location.href = basePath + "logined/address/list_addressGroup.jsp";
		return false;
	});
});

function addAddressGroup() {
	// 框架校验
	if (!validator(document.getElementById("form1"))) {
		return;
	}
	var orderNo = $("#orderNo").val();
	var name = $("#name").val();
	var groupType = 1;
	var paramData = {
	    'addressGroup.orderNo' : $.trim(orderNo),
	    'addressGroup.name' : $.trim(name),
	    'addressGroup.groupType' : groupType
	};
	$.ajax({
	    url : basePath + "addressGroup/setup_addAddressGroup.action",
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
			    window.parent.location.href = basePath + "logined/address/address_index.jsp";
		    } else {
			    art.dialog.alert(sprintf("addressbook.msg_add_group_error"));
		    }
	    }
	});
}