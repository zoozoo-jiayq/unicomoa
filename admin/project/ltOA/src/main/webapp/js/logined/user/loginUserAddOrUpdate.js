var getDataTable;
$(document).ready(function() {

	// 选择用户角色
	$("#selectRole").click(function() {
		openSelectUser(2, selectCallBackForm, null, $("#roleIds").val());// 选择人员
	});

	// 选择用户
	$("#userSelect").click(function() {
		openSelectUser(3, selectUserCallBack, 0,$("#userId").val(),"radio");// 选择人员
	});

	// 取消关闭按钮
	$("#cancleBtn").click(function() {
		art.dialog.close();
        return false;
	});
	
	getDataTable = art.dialog.data('getDataTable');
	
	//选择人员不能点击回车
    $("#userName").keydown(function(e) {
        if(e.which==8){  
            return false;       
        }
    });
    
    $("#roleNames").keydown(function(e) {
        if(e.which==8){  
            return false;       
        }
    });
    
    $("#sex").keydown(function(e) {
        if(e.which==8){  
            return false;       
        }
    });
    
    $("#groupName").keydown(function(e) {
        if(e.which==8){  
            return false;       
        }
    });
    $("#jod").keydown(function(e) {
        if(e.which==8){  
            return false;       
        }
    });
    $("#phone").keydown(function(e) {
        if(e.which==8){  
            return false;       
        }
    });
    $("#email").keydown(function(e) {
        if(e.which==8){  
            return false;       
        }
    });
});

/**
 * 部门人员选择的回调函数
 */
function selectCallBackForm(data, param) {
	var roleIds = "";
	var roleNames = "";
	if (data) {
		$(data).each(function(i,item){
			roleIds += item.id + ",";
			roleNames += item.name + ",";

		});
		if(roleNames != ""){
			roleNames = roleNames.substring(0,roleNames.lastIndexOf(","));
		}
		$("#roleIds").val(roleIds);
		$("#roleNames").val(roleNames);
	}

}

/**
 * 部门人员选择的回调函数
 */
function selectUserCallBack(data, param) {
	if (data) {
		$(data).each(function(i,item){
			$("#userId").val(item.id);
			$("#userName").val(item.name);

			qytx.app.ajax({
			    url : basePath + "/user/ajaxUserById.action",
			    type : "post",
			    dataType : 'json',
			    data : {
				    userId : item.id
			    },
			    success : function(data) {
				    if (null != data) {
				    	qytx.app.valid.hideError($("#userId"));
				    	
					    // 性别
					    var sex = data["sex"];
					    if ('0' == sex) {
						    $("#sex").val("女");
					    } else {
						    $("#sex").val("男");
					    }
					    $("#carHostId").val(data["id"]);
					    $("#owner").val(data["userName"]);
					    // 职务
					    $("#job").val(data["job"]);

					    // 手机
					    $("#phone").val(data["phone"]);
					    
					    // 手机
					    $("#loginName").val(data["phone"]);

					    // 邮件
					    $("#email").val(data["email"]);
					    
					    // 部门/单位
					    $("#groupName").val(data["groupPath"]);
				    }
			    }

			});

		});
	}
}

function addOrUpdateuser() {
	// 框架校验
	if (!qytx.app.valid.check({form:document.getElementById("userInfoFomr")})) {
		return;
	}

	// 登录名
	var loginName = $.trim($("#loginName").val());

	// 密码
	var password = $.trim($("#password").val());
	
	// 确认密码
	var confirmPass = $.trim($("#confirmPass").val());
	
	// 密码和确认密码必须一致
	if (password != confirmPass){
		qytx.app.valid.showObjError($("#confirmPass"), 'user.password_must_be_consistent');
		return;
	}else {
		qytx.app.valid.hideError($("#confirmPass"));
	}
	
	// 角色
	var roleIds = $.trim($("#roleIds").val());
	
	// 登录状态
	var userState = $("input:radio[name='state']:checked").val();
	
	// 选择用户Id
	var userId = $("#userId").val();
	if (null == userId || "" == userId){
		qytx.app.valid.showObjError($("#userId"), 'user.user_not_null');
		return;
	}

	var type = $("#type").val();
	if (null == type || '' == type){
		type = 'add';
	}
	var paramData = {
	    'user.loginName' : loginName,
	    'user.loginPass' : password,
	    'roleIds':roleIds,
	    'user.userId':userId,
	    'user.userState':userState,
	    'oldUserId':$("#oldUserId").val(),
	    'type' : type
	};
	qytx.app.ajax({
	    type : 'post',
	    url : basePath + "user/updateLoginUser.action",
	    data : paramData,
	    dataType : 'text',
	    success : function(data) {
		    if (data == "success") {
			    getDataTable();
			    art.dialog.close();
		    } else if (data == 'user.user_is_exist'){
		    	qytx.app.dialog.alert(sprintf("user.user_is_exist"));
		    } else if (data == 'user.loginName_is_exist'){
		    	qytx.app.dialog.alert(sprintf("user.loginName_is_exist"));
		    } else {
			    qytx.app.dialog.alert("新增登录用户失败!");
		    }
	    }
	});
}
