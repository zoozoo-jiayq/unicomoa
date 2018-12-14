/**
 * 人员更新
 */

$(document).ready(function() {
	
	/**
	 * 加载从属部门
	 */
	loadOtherGroups();
	
	//删除头像
    $(".close").bind("click",function(){
        $("#photo").val("");
        var photoUrl = basePath + "flat/plugins/form/skins/default/meeting.png";
        $("#photoImg").attr("src", photoUrl);
    });
	
	// 单击更新
	$("#userInfoUpdate").click(function() {
				var cr = check();
				if(cr){
					var valid = qytx.app.valid.check({form:$("#userForm")[0]});
					if (valid) {
						submitForm();
					}
					return false;
				}
			});
	// 单击取消
	$("#back").click(function() {
		window.history.go(-1);
				return false;
			});
	// 显示辅助选项
	$("#showAssist").click(function() {
				$("#assistContent").toggle();
				return false;
			});
	// 单击主角色
	$("#roleSelect").click(function() {
				selectUserRole();
				return false;
			});
	// 单击主角色清除
	$("#roleRemove").click(function() {
				$("#roleId").val("");
				$("#role").val("");
				return false;
			});
	// 单击辅助角色
	$("#assistSelect").click(function() {
				selectUserAssist();
				return false;
			});
	// 单击辅助角色清除
	$("#assistRemove").click(function() {
				$("#assistId").val("");
				$("#assist").val("");
				return false;
			});
	// 单击选择部门
	$("#groupSelect").click(function() {
				selectGroup();
				return false;
			});
	// 单击部门清除
	$("#groupRemove").click(function() {
				$("#groupId").val("");
				$("#group").val("");
				return false;
			});
	// 添加用户
	$("#forwordUserAdd").click(function() {
		window.location.href = basePath + "logined/user/userAdd.jsp?groupId="
				+ $("#defaultgroupId").val();
		return false;
	});

	// 用户权限信息-管理范围-选择
	var managementRange = $("#managementRange_hidden").val();
	if (managementRange != undefined && $.trim(managementRange) != "") {
		$("#managementRange").val(managementRange);
		if (managementRange == "appointGroup") {
			$("#appointGroupTr").show();
		}
	} else {
		// 未设置管理范围的用户,默认范围为全体部门
		$("#managementRange").val(4);
	}
	$("#managementRange").bind("change click", function() {
				if ($(this).val() == "5") {
					$("#appointGroupTr").show();
				} else {
					$("#appointGroupTr").hide();
					$("#appointGroupIds").val("");
					$("#appointGroupNames").val("");
				}
			});

	// 管理范围-部门-单击添加
	$("#appointGroupAdd").click(function() {
				selectAppointGroup();
				return false;
			});
	// 管理范围-部门-单击清空
	$("#appointGroupRemove").click(function() {
				$("#appointGroupIds").val("");
				$("#appointGroupNames").val("");
			});

	// 单击‘按模块设置管理范围’
	$("#moduleManagementRange").click(function() {
		var appBoxURL = basePath
				+ "user/moduleManagementRangePage.action?userId="
				+ $.trim($("#userId").val());
		art.dialog.open(appBoxURL, {
					id : "setManagementRangeByModuleDialog",
					title : "按模块设置管理范围",
					size:"L"
				})
	});
});
/**
 * 添加管理员信息
 */
function submitForm() {
	// 用户id
	var userId = $.trim($("#userId").val());
	// 登录名
	var loginName = $.trim($("#loginName").val());
	// 姓名
	var userName = $.trim($("#userName").val());
	// 部门id
	var groupId = $.trim($("#groupId").val());
	// 用户排序号
	var userOrder = $.trim($("#userOrder").val());
	//职务
	var job = $.trim($("#job").val());
	// 别名
	var alterName = $.trim($("#alterName").val());
	// 性别
	var sex = $('input[name="sex"]:checked').val();
	// 生日
	var birthDay = $.trim($("#birthDay").val());
//	if (birthDay != "") {
//		birthDay += " 00:00:00.000";
//	}
	// 手机
	var phone = $.trim($("#phone").val());
	// 手机号是否公开1公开，0不公开
	var phonePublic = 1;
	if ($("#phonePublic").prop("checked")) {
		phonePublic = 0;
	}
	// 电子邮件
	var email = $.trim($("#email").val());
	// 工作电话
	var officeTel = $.trim($("#officeTel").val());
	// 主角色
	var roleIds = $.trim($("#roleId").val());
	// 辅助角色
	var assistIds = $.trim($("#assistId").val());

//	var managementRange = $("#managementRange").val();
//	if (managementRange == null || "" == managementRange) {
//		managementRange = 4; // 全体部门
//	}

	//签章类型
	var signType = $("#userSign").val();
	var signUrl = $("#imgSignUrl").val();

	var sign = $("#sign").val();
	var office = $("#office").val();
	var print = $("#print").val();
	var photo = $("#photo").val();
	var workNo = $("#workNo").val();
	var hidePhone = $("#hidePhone").val();//号码隐藏 0关闭 1开启
	var isLeader = $("#isLeader").val();//高管模式 0关闭 1开启
	
	var phone2=$("#oldPhone").val();//旧电话
	var homeTel=$("#oldWorkNo").val();//旧集团短号
	
	if(isRepeatForSub()){//判断选中的部门中是否有重复的部门信息
		qytx.app.dialog.alert("从属部门中包含重复的部门!");
		return;
	}
	
	
	var paramData = {
		'user.userId' : userId,
		'user.loginName' : loginName,
		'user.userName' : userName,
		'groupId' : groupId,
		'user.orderIndex' : userOrder,
		'user.alterName' : alterName,
		'user.sex' : sex,
		'birthDay' : birthDay,
		'user.phone' : phone,
		'user.phonePublic' : phonePublic,
		'user.email' : email,
		'user.officeTel' : officeTel,
		'roleIds' : roleIds,
		'assistIds' : assistIds,
		'user.job' :job,
//		'managementRange' : managementRange,
//		'appointGroupIds' : $("#appointGroupIds").val(),
//		'appointGroupNames' : $("#appointGroupNames").val(),
		'type' : 'address',  // 类型等于维护通讯录,至修改通讯录基本信息
		'user.signType':signType,
		'user.signUrl':signUrl,
		'user.taoDa':print,
		'user.officeWidget':office,
		'user.photo':photo,
		'user.sinWidget':sign,
		'user.workNo':workNo,
		'user.hidePhone':hidePhone,
		'user.isLeader':isLeader,
		'user.phone2':phone2,////旧电话
		'user.homeTel':homeTel,//旧集团短号
		"otherGroupJson":JSON.stringify(otherGroupJson)
	};
	qytx.app.ajax({
				type : 'post',
				url : basePath + "user/updateUser.action",
				data : paramData,
				shade:true,
				success : function(data) {
					if (data == "success") {					
						window.parent.refreshTree("uid_"+userId);
						window.location.reload();
					} else {
						qytx.app.dialog.alert(data);
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

function loadOtherGroups(){
	qytx.app.ajax({
		url: basePath+"/user/loadOtherGroups.action",
		data:{"userId":$.trim($("#userId").val())},
		dataType:"json",
		type:"post",
		success:function(data){
			otherGroupJson = data;
			showOtherGroups();
		}
	});
}