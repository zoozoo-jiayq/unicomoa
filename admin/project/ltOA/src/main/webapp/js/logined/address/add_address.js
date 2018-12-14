$(document).ready(function() {
	findGroups();
	
    //单击添加
    $("#add").click(function(){
    	addAddress();
        return false;
    });
    
    $("#deletePhoto").live("click",function(){
        $("#photo").val("");
        var photoUrl = basePath + "flat/plugins/form/skins/default/meeting.png";
        $("#photoImg").attr("src", photoUrl);
        $("#deletePhoto").hide();        
    });
});

/**
 * 获取通讯簿组列表
 */
function findGroups()
{
	var url;
	// 添加公共通讯簿组下联系人
	if('2' == $("#groupType").val()){
		url = basePath+"addressGroup/setup_getPublicAddressGroupList.action?maintain=1&updateSign=1";
	}else{
		url = basePath+"addressGroup/setup_getAddressGroupList.action";
	}
	
    qytx.app.ajax({
        url :  url,
        type : "post",
        dataType :'json',
        success : function(data) {
			if(data.aaData!=null){
				var defaultGroupId = $("#groupId").val();
				for(var i=0;i<data.aaData.length;i++){
					if (defaultGroupId == data.aaData[i].id){
						// 联系人组
						$("#addressGroupId").append('<option selected="selected" value='+data.aaData[i].id+'>'+data.aaData[i].name+'</option>');
					}else{
						// 联系人组
						$("#addressGroupId").append('<option value='+data.aaData[i].id+'>'+data.aaData[i].name+'</option>');
					}
				}				
			}
	    }
	});
}

function addAddress(){
	var valid = qytx.app.valid.check({form:$("#form1")[0]});
    // 框架校验
	if(!valid){
        return;
    }
	
    // 排序号
    var orderNo = $("#orderNo").val();
    // 姓名
    var name = $("#name").val();
    // 性别
    var sex = $('input[name="sex"]:checked').val();
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

	var paramData={
			'address.orderNo':$.trim(orderNo),
			'address.name':$.trim(name),
			'address.sex':$.trim(sex),
			'address.birthdayStr':$.trim(birthday),
			'address.nickname':$.trim(nickname),
			'address.postInfo':$.trim(postInfo),
			'address.wifeName':$.trim(wifeName),
			'address.children':$.trim(children),
			'address.photo':$.trim(photo),
			'address.companyName':$.trim(companyName),
			'address.companyAddress':$.trim(companyAddress),
			'address.companyPostCode':$.trim(companyPostCode),
			'address.officePhone':$.trim(officePhone),
			'address.officeFax':$.trim(officeFax),
			'address.familyAddress':$.trim(familyAddress),
			'address.familyPostCode':$.trim(familyPostCode),
			'address.familyPhoneNo':$.trim(familyPhoneNo),
			'address.familyMobileNo':$.trim(familyMobileNo),
			'address.familyEmail':$.trim(familyEmail),
			'address.remark':$.trim(remark),
			'address.addressGroupId':$("#addressGroupId").val()
	 };
	qytx.app.ajax({
	      url:basePath+"address/setup_addAddress.action",
	      type:"post",
	      data:paramData,
	      success: function(data){
				if("success" == data){
					window.history.go(-1);
				}else if("exist" == data){
					qytx.app.dialog.alert(sprintf("addressbook.msg_add_exist"));
				}else{
					qytx.app.dialog.alert(sprintf("addressbook.msg_add_error"));
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
        var photoUrl = basePath + "filemanager/prevViewByPath.action?filePath=" + photoName;
        $("#photoImg").attr("src", photoUrl);
        $("#deletePhoto").show();
    }
}