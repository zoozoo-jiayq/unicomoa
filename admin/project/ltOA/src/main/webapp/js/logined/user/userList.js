/**
 * @author REN

 */
var pd=0;
var _userCount=0;
$(document).ready(function() {
	// 清除cookie中的分页信息
	$.removeTableCookie('SpryMedia_DataTables_userTable_userList.jsp');
	// 获取人员信息列表
	getInfo();
	$("#searchButton").click(function(){
		$.removeTableCookie('SpryMedia_DataTables_userTable_userList.jsp');
		pd=1;
		_checkedIds="";
		getInfo();
	});
	
	// 打开新增页面
	$("#btnAddUser").click(function() {
				document.location = basePath + "logined/user/add.jsp";
				return false;
	});
	// 头部全选复选框
	$("#userTable").delegate("#total", "click", function(event) {
				checkTotal();
				event.stopPropagation();
			});
	// 第一列复选按钮
	$("#userTable").delegate("input:checkbox[name='chk']", "click",
			function(event) {
				check();
				event.stopPropagation();
			});
	// 单击删除
	$("#userDelete").click(function() {
				deleteUser();
				return false;
			});
	// 单击新增
	$("#userAdd").click(function() {
		window.location.href = basePath + "logined/user/userAdd.jsp?groupId="
				+ $("#groupId").val();
		return false;
	});
	// 重置密码
	$("#initPassword").click(function() {
				setPassword();
				return false;
			});
	// 登陆设置
	$("#userTable").delegate("a[name='loginUrl']", "click", function(event) {
				var userState = $(this).attr("userState");
				var userId = $(this).attr("userId");
				setUserState(userId, userState);
				return false;
			});
	
	
	// 单击导出
	$("#userExport").click(function() {
//		if(_userCount<=0){
//			qytx.app.dialog.alert("没有要导出的数据");
//			return false;
//		}
		 var data = $("#userSelectFrom").serialize();
		 data = "userVo." + data;
		 var re = /&/g;
		 var sex = $.trim($("#sex").val());
		 data = data.replace(re, "&userVo.");
		 var loginName = $.trim($("#searchName").val());
		 data +="&userVo.loginName="+loginName;
		 data +="&userVo.sex="+sex;
		 data +="&userVo.userName="+loginName;
		 data +="&selectedIds="+_checkedIds;
		/**   var data = "";
		   var chkbox = document.getElementsByName("chk");
			var userIds = "";
			var selLen = 0;
			for (var i = 0; i < chkbox.length; i++) {
				if (chkbox[i].checked) {
					userIds += chkbox[i].value + ",";
					selLen++;
				}
			}
			if(selLen<=0){
				art.dialog.alert("请勾选要导出的数据");
				return false;
			}
			
			data="userIds="+userIds;
			*/
		  document.location.href=basePath + "user/exportUserInfo.action?" + data;
		 //   document.location.href=basePath + "user/exportUserInfoCheck.action?" + data;
		//window.open(basePath + "user/exportUserInfo.action?"
		//		+ data);
		return false;
		 
	});
	
	// 捕获回车事件
    $("html").die().live("keydown", function(event) {
        if (event.keyCode == 13) {
        	$.removeTableCookie('SpryMedia_DataTables_userTable_userList.jsp');
        	pd=1;
    		// 获取人员信息列表
    		_checkedIds="";	
    		getInfo();
        }
    });
});
function getInfo(){
	if ($("#type").val() == 'view'){
		$("#userTable").hide();
		viewGrid();
	}else{
		$("#viewUserTable").hide();
		grid();
	}
}
var grid=function(){
	qytx.app.grid({
		id:'userTable',
		url:basePath + "user/getUserListByGroupId.action",
		selectParam:{
				'userVo.loginName':$.trim($("#searchName").val()),
                'userVo.userName':$.trim($("#searchName").val()),
                'userVo.sex': $.trim($("#sex").val()),
                'userVo.groupId':$.trim($("#groupId").val()),
                'userVo.roleId':$.trim($("#roleId").val()),
                'loginOrder':$.trim($("#loginOrder").val()),
                'userVo.phone':$.trim($("#searchName").val()),
                'pd':pd,
                'userVo.projectName':'OA'
				},
		valuesFn:[
		   {
			"aTargets" : [0],
			"fnRender" : function(oObj) {
			var isDefault = oObj.aData.isDefault;
			if (isDefault == 0) {
				return '<input name="chk" disabled="disabled" value="'+oObj.aData.id+'" type="checkbox" />';
			 } else {
				return '<input name="chk" value="'+ oObj.aData.id+'" type="checkbox" />';
			 }}}, 
			{
			"aTargets" : [6],
			"fnRender" : function(oObj) {
				var userState = oObj.aData.userState;
				var userStateName = "";
				if (userState == 0) {
					userStateName = "是";
				} else {
					userStateName = "否";
				}
				return userStateName;
			}}, 
			{
			"aTargets" : [7],
			"fnRender" : function(oObj) {
				var managerId = oObj.aData.id;
				var updateStr = basePath + "user/getUserById.action?userId="+ managerId;
				var userState = oObj.aData.userState;
				var isDefault = oObj.aData.isDefault;
				var loginStr = "";
				var loginStrA = "";
				var isDefault = oObj.aData.isDefault;
				if (isDefault == 0) {
					return ' ';
				} else {
					return '<a href="' + updateStr
					+ '" class="view_url" id="updateUrl">修改</a>'
					+ loginStrA;
				}
			}
		}]
});
}

var viewGrid=function(){
	qytx.app.grid({
		id:'viewUserTable',
		url:basePath + "user/getUserListByGroupId.action",
		selectParam:{
				'userVo.loginName':$.trim($("#searchName").val()),
                'userVo.userName':$.trim($("#searchName").val()),
                'userVo.sex': $.trim($("#sex").val()),
                'userVo.groupId':$.trim($("#groupId").val()),
                'userVo.roleId':$.trim($("#roleId").val()),
                'loginOrder':$.trim($("#loginOrder").val()),
                'userVo.phone':$.trim($("#searchName").val()),
                'pd':pd,
                'userVo.projectName':'OA'
				},
		valuesFn:[
			{
				"aTargets" : [3],
				"fnRender" : function(oObj) {
					var phone = oObj.aData.phone;
					var phonePublic = oObj.aData.phonePublic;
					if (phonePublic == '0') {
						return "";
					} else {
						return phone;
					}
				}
			}, {
				"aTargets" : [5],
				"fnRender" : function(oObj) {
					var userState = oObj.aData.userState;
					var userStateName = "";
					if (userState == 0) {
						userStateName = "是";
					} else {
						userStateName = "否";
					}

					return userStateName;
				}
			}]
});	
}
/**
 * 头部全选记录
 */
function checkTotal() {
	var isTotalChecked = $("input:checkbox[id='total']").prop("checked");
	var listCheckbox = $("input:checkbox[name='chk']");
	if (isTotalChecked) {
		listCheckbox.prop("checked", function(i, val) {
					if (!$(this).prop("disabled")) {
						return true;
					}
				});
	} else {
		listCheckbox.prop("checked", false);
	}
}
/**
 * 选择记录
 */
function check() {
	var checkTotalNum = $("input:checkbox[name='chk']");
	var checkNum = 0;
	var checkNumAll = true;
	checkTotalNum.each(function(i, val) {
				if ($(checkTotalNum[i]).prop("checked")) {
					checkNum++;
				} else {
					checkNumAll = false;
				}
			});
	if (!checkNumAll) {
		$("#total").prop("checked", false);
	} else {
		$("#total").prop("checked", true);
	}
}
/**
 * 删除人员
 */
function deleteUser() {
	// 获取选择管理员id
	var chkbox = document.getElementsByName("chk");
	var userIds = _checkedIds;
	var selLen = _checkedIds.length;
	if (selLen <= 1) {
		qytx.app.dialog.alert("请至少选择一项");
		return;
	}
	var groupId=$("#groupId").val();
	var paramData = {
		'userIds' : userIds,
		'groupId' : groupId
	};
	// 删除管理员
	qytx.app.dialog.confirm('确定要删除吗？', function() {
		    qytx.app.ajax({
							url : basePath + "user/deleteUser.action",
							type : "post",
							data : paramData,
							success : function(data) {
								if (data == "success") {
										window.parent.refreshTree("gid_" + $("#groupId").val());
										_checkedIds="";
										getInfo();
								}else{qytx.app.dialog.alert("操作失败");}
							}
						});
			});
}
/**
 * 重置密码
 */
function setPassword() {
	// 获取选择管理员id
	var chkbox = document.getElementsByName("chk");
	var userIds = "";
	var selLen = 0;
	for (var i = 0; i < chkbox.length; i++) {
		if (chkbox[i].checked) {
			userIds += chkbox[i].value + ",";
			selLen++;
		}
	}
	if (selLen == 0) {
		qytx.app.dialog.alert('请选择要重置密码的人员！');
		return;
	}
	var paramData = {
		'userIds' : userIds
	};
	qytx.app.dialog.confirm('确定重置密码吗？', function() {
		    qytx.app.ajax({
							url : basePath + "user/updateUserPassword.action",
							type : "post",
							data : paramData,
							success : function(data) {
								if (data == "") {
									qytx.app.dialog.success('重置密码成功！',true);
								} else {
									qytx.app.dialog.alert("操作失败");
								}
							}
						});
			});
}
/**
 * 禁止登陆
 */
function setUserState(userId, userState) {
	var paramData = {
		'userId' : userId,
		'userState' : userState
	};
	var userStateTip = "";
	if (userState == 1) {
		userStateTip = '确定禁止该人员登录吗？';
	} else {
		userStateTip = '确定允许该人员登录吗？';
	}
	art.dialog.confirm(userStateTip, function() {
		qytx.app.ajax({
							url : basePath + "user/updateUserState.action",
							type : "post",
							dataType : 'text',
							data : paramData,
							success : function(data) {
								if (data == "") {
									qytx.app.dialog.success('设置成功！',true);
									grid();
								} else {
									qytx.app.dialog.alert(data);
								}
							}
						});
			});
		}