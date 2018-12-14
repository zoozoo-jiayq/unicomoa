$(document).ready(function() {
	findGroups();

	// 单击修改
	$("#updateAddress").click(function() {
		updateAddress();
		return false;
	});

	// 单击返回
	$("#backBtn").click(function() {
		// 跳转到详情页面
    	window.location.href = window.location.href.replace("operateType=update", "operateType=detail").replace("#", "");
		return false;
	});
	
	setImage();
	
    $("#deletePhoto").live("click",function(){
        $("#photo").val("");
		var sex = $("#sex").val();
		if (null != sex && sex == "0") {
			$("#photoImg").attr("src", basePath + "flat/plugins/form/skins/default/meeting.png");
		} else {
			$("#photoImg").attr("src", basePath + "flat/plugins/form/skins/default/meeting.png");
		}
        $("#deletePhoto").hide();        
    });
});

/**
 * 获取通讯簿组列表
 */
function findGroups() {
	$.ajax({
	    url : basePath + "addressGroup/setup_getAddressGroupList.action",
	    type : "post",
	    dataType : 'json',
	    success : function(data) {
		    if (data.aaData != null) {
			    for ( var i = 0; i < data.aaData.length; i++) {
				    if ($("#groupId").val() == data.aaData[i].id) {
					    // 联系人组
					    $("#addressGroupId").append(
					            '<option value=' + data.aaData[i].id + ' selected="selected">' + data.aaData[i].name
					                    + '</option>');
				    } else {
					    // 联系人组
					    $("#addressGroupId").append(
					            '<option value=' + data.aaData[i].id + '>' + data.aaData[i].name + '</option>');
				    }
			    }
		    }
	    }
	});
}

function setImage() {
	var photo = $("#photo").val();
	if (null != photo && "" != photo) {
		$("#photoImg").attr("src", basePath + "upload/img/" + photo);
		$("#deletePhoto").show();
	} else {
		var sex = $("#sex").val();
		if (null != sex && sex == "0") {
			$("#photoImg").attr("src", basePath + "flat/plugins/form/skins/default/meeting.png");
		} else {
			$("#photoImg").attr("src", basePath + "flat/plugins/form/skins/default/meeting.png");
		}
	}
}
function updateAddress() {
    // 框架校验
	if(!validator(document.getElementById("form1"))){
        return;
    }
	
	// 排序号
	var orderNo = $("#orderNo").val();
	// 姓名
	var name = $("#name").val();
	// 性别
	var sex = $("#sex").val();
	// 生日
	var birthday = $("#birthday").val();
	// 昵称
	var nickname = $("#nickname").val();
	// 职务
	var postInfo = $("#postInfo").val();
	// 配偶
	var wifeName = $("#wifeName").val();
	// 子女
	var children = $("#children").val();
	// 联系人照片上传
	var photo = $("#photo").val();
	// 单位名称
	var companyName = $("#companyName").val();
	// 单位地址
	var companyAddress = $("#companyAddress").val();
	// 单位邮编
	var companyPostCode = $("#companyPostCode").val();
	// 工作电话
	var officePhone = $("#officePhone").val();
	// 工作传真
	var officeFax = $("#officeFax").val();
	// 家庭住址
	var familyAddress = $("#familyAddress").val();
	// 家庭邮编
	var familyPostCode = $("#familyPostCode").val();
	// 家庭电话
	var familyPhoneNo = $("#familyPhoneNo").val();
	// 手机
	var familyMobileNo = $("#familyMobileNo").val();
	// 电子邮件
	var familyEmail = $("#familyEmail").val();
	// 备注
	var remark = $("#remark").val();
	var paramData = {
	    'address.orderNo' : $.trim(orderNo),
	    'address.name' : $.trim(name),
	    'address.sex' : $.trim(sex),
	    'address.birthdayStr' : $.trim(birthday),
	    'address.nickname' : $.trim(nickname),
	    'address.postInfo' : $.trim(postInfo),
	    'address.wifeName' : $.trim(wifeName),
	    'address.children' : $.trim(children),
	    'address.photo' : $.trim(photo),
	    'address.companyName' : $.trim(companyName),
	    'address.companyAddress' : $.trim(companyAddress),
	    'address.companyPostCode' : $.trim(companyPostCode),
	    'address.officePhone' : $.trim(officePhone),
	    'address.officeFax' : $.trim(officeFax),
	    'address.familyAddress' : $.trim(familyAddress),
	    'address.familyPostCode' : $.trim(familyPostCode),
	    'address.familyPhoneNo' : $.trim(familyPhoneNo),
	    'address.familyMobileNo' : $.trim(familyMobileNo),
	    'address.familyEmail' : $.trim(familyEmail),
	    'address.remark' : $.trim(remark),
	    'address.addressGroupId' : $("#addressGroupId").val(),
	    'address.id' : $("#addressId").val()
	};
	$.ajax({
	    url : basePath + "address/setup_updateAddress.action",
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
		    if ("success" == data) {
			    // 跳转到详情页面
		    	window.location.href = window.location.href.replace("operateType=update", "operateType=detail").replace("#", "");
		    } else {
		    	art.dialog.alert(sprintf("addressbook.msg_update_address_error"));
		    }
	    }
	});
}


/**
 * 预览照片
 */
function displayPhoto() {
    var photoName = $("#photo").val();
    if (photoName != "") {
        var photoUrl = basePath + "upload/img/" + photoName;
        $("#photoImg").attr("src", photoUrl);
        $("#deletePhoto").show();
    }
}