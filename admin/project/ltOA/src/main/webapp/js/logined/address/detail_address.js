$(document).ready(function() {
    //单击编辑
    $("#updateAddress").click(function(){
    	var address_url = basePath + "address/getDetailInfo.action?operateType=update&addressVo.id="+$("#addressId").val();
    	window.location.href=address_url;
        return false;
    });
    
    //单击共享
    $("#shareAddress").click(function(){
    	window.location.href=basePath+"address/getDetailInfo.action?operateType=share&addressVo.id="+$("#addressId").val();
        return false;
    });
	setImage();
    window.parent.filterFocus();
    
    var type = $("#type", window.parent.document).val();
    var currentUserId = $("#currentUserId", window.parent.document).val();
    if (type == "share"){

    	// 如果有修改权限则 编辑可见
    	var allowUpdateUserIds = $("#allowUpdateUserIds").val();
    	if ((null != allowUpdateUserIds && allowUpdateUserIds != ''
    		&& allowUpdateUserIds.indexOf(","+currentUserId+",") >= 0)
    				|| $("#createUserId").val() == currentUserId){
    		$("#updateAddress").show();
    	}else{
    		$("#updateAddress").hide();
    	}
    	
    	if ($("#createUserId").val() != currentUserId){
    		$("#shareAddress").hide();
    	}else{
    		$("#shareAddress").show();
    	}
    }else{
    	$("#updateAddress").show();
    	$("#shareAddress").show();
    }
    
    // 公共组用户不允许修改和共享
    if ($("#publicSign").val() == '1'){
    	$("#updateAddress").hide();
    	$("#shareAddress").hide();
    }
    
    $("body").show();
});


function setImage() {
	var photo = $("#photo").val();
	if (null != photo && "" != photo) {
		$("#photoImg").attr("src", basePath + "upload/img/" + photo);
	} else {
		var sex = $("#sex").val();
		if (null != sex && sex == "0") {
			$("#photoImg").attr("src", basePath + "flat/plugins/form/skins/default/meeting.png");
		} else {
			$("#photoImg").attr("src", basePath + "flat/plugins/form/skins/default/meeting.png");
		}
	}
}