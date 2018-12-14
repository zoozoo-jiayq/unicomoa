$(document).ready(function() {
    //单击修改
    $("#update").click(function(){
    	updateAddressGroup();
        return false;
    });
    
    //单击返回
    $("#back").click(function(){
    	window.location.href=basePath+"logined/address/list_addressGroup.jsp";
        return false;
    });
});

function updateAddressGroup(){
    // 框架校验
	if(!validator(document.getElementById("form1"))){
        return;
    }
	
	var id = $("#id").val();
    var orderNo = $("#orderNo").val();
    var name = $("#name").val();
	var paramData={
			'addressGroup.id':id,
			'addressGroup.orderNo':$.trim(orderNo),
			'addressGroup.name':$.trim(name),
			'addressGroup.groupType':1
	 };
	$.ajax({
	      url:basePath+"addressGroup/setup_updateAddressGroup.action",
	      type:"post",
	      dataType: "text",
	      data:paramData,
	      beforeSend:function(){
				$("body").lock();
		    },
			complete:function(){
				$("body").unlock();
			},
	      success: function(data){
	    	  if (data == "nameExist") {
				    art.dialog.alert(sprintf("addressbook.msg_groupName_exist"));
			    } else if ("success" == data) {
				    window.parent.location.href = basePath + "logined/address/address_index.jsp";
			    } else {
				    art.dialog.alert(sprintf("addressbook.msg_update_group_error"));
			    }
	    	}
	 });
}