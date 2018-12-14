/**
 * @author REN
 */
$(document).ready(function() {
	// 清除cookie中的分页信息
	$.removeTableCookie('SpryMedia_DataTables_userTable_loginUserList.jsp');

	// 获取人员信息列表
	getDataTable();
	
	// 打开新增页面
	$("#addLoginUser").click(function() {
		updateLoginUser();
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
	
	// 查询
	$("#searchLoginUser").click(function() {
		$.removeTableCookie('SpryMedia_DataTables_userTable_loginUserList.jsp');
		getDataTable();//要触发的方法
	    return false;
    });
	
	 //回车事件
	$(document).keydown(function(event){
		var code=event.which;
		if (code == 13) {
			// 清除cookie中的分页信息
			$.removeTableCookie('SpryMedia_DataTables_userTable_loginUserList.jsp');
			getDataTable();//要触发的方法
        }
	});
});
/**
 * 获取管理员信息列表
 */
function getDataTable() {

	$("#total").prop("checked", false);
	qytx.app.grid({
		id	:	"userTable",
		url	:	basePath + "user/getUserListByGroupId.action",
		iDisplayLength:	tableDisplayLength,
		selectParam:	{
							"userVo.keyWordForLogin":$.trim($("#key_word").val()),
							"userVo.from":"loginManager"
						},
		valuesFn:	[{
						"aTargets" : [1],
			            "fnRender" : function(oObj) {				
							var userId = oObj.aData.id;
							var loginName = oObj.aData.loginName;
							
							return '<a href="javascript:void(0);" onclick=detailLoginUser("'+userId+'")>'+loginName+'</a>';
						}
			
					},{
						"aTargets" : [3],
						"fnRender" : function(oObj) {
							var sex = oObj.aData.sex;
							var sexName;
							if (sex == "0"){
								sexName = "女"; 
							}else{
								sexName = "男"; 
							}
							return sexName;
						}
					}, {
						"aTargets" : [8],
						"fnRender" : function(oObj) {
							var userState = oObj.aData.userState;
							var userStateName = "";
							if (userState == 1) {
								userStateName = "不启用";
							} else if(userState == 0) {
								userStateName = "启用";
							}
			
							return userStateName;
						}
					}, {
						"aTargets" : [9],
						"fnRender" : function(oObj) {
							var managerId = oObj.aData.id;	
							var isDefault = oObj.aData.isDefault;
							if (isDefault == '0' || oObj.aData.isVirtual == '1'){
								// 默认用户不允许修改和删除
								//虚拟用户不允许修改和删除
								return '';
							}
							return '<a href="javascript:void(0);" class="view_url" onclick=updateLoginUser("'+managerId+'") id="updateUrl">修改</a>'
							+ '<a href="javascript:void(0);" class="view_url" onclick=deleteLoginUser("'+managerId+'") id="updateUrl">删除</a>';
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
	var userIds = "";
	var selLen = 0;
	for (var i = 0; i < chkbox.length; i++) {
		if (chkbox[i].checked) {
			userIds += chkbox[i].value + ",";
			selLen++;
		}
	}
	if (selLen == 0) {
		qytx.app.dialog.alert('请选择要删除的人员信息！');
		return;
	}
	var paramData = {
		'userIds' : userIds
	};
	// 删除管理员
	qytx.app.dialog.confirm('确定删除该人员吗？', function() {
			qytx.app.ajax({
							url : basePath + "user/deleteUser.action",
							type : "post",
							dataType : 'text',
							data : paramData,
							success : function(data) {
								if (data == "success") {
									getDataTable(); // 刷新人员信息
								}
							}
						});
			});
}

/**
 * 删除登录用户配置信息
 * 
 * @param userId
 *            用户Id
 */
function deleteLoginUser(userId){
	var paramData = {
			'userIds' : userId
		};
	
	// 删除管理员
	qytx.app.dialog.confirm('确定删除该人员吗？', function() {
		qytx.app.ajax({
					url : basePath + "user/deleteLoginUser.action",
					type : "post",
					dataType : 'text',
					data : paramData,
					success : function(data) {
						if (data == "success") {
							getDataTable(); // 刷新人员信息
						} 
					}
				});
	});
}

function updateLoginUser(userId){
	art.dialog.data('getDataTable', getDataTable);
	var url ;
	var titleVal ;
	var idVal;
	if (null == userId){
		url = "logined/user/loginUserAddOrUpdate.jsp";
		titleVal= "新增登录用户";
		idVal="addLoginUser";
	}else{
		url = "user/detailLoginUser.action?type=update&user.userId="+userId;
		titleVal= "修改登录用户";
		idVal="updateLoginUser";
	}
	qytx.app.dialog.openUrl({
		url	: basePath + url,
		title:	titleVal,
		size:	"L",
		customButton: [
		               	{
		               		name: '确定',
		               		callback: function () {
											var iframe = this.iframe.contentWindow;
									    	iframe.addOrUpdateuser();
									    	return false;
			               				},
			               	focus: true
			            },
	               		{name: '取消'}
		              ]
	});
}


function detailLoginUser(userId){
	var url  = "user/detailLoginUser.action?type=detail&user.userId="+userId;
	qytx.app.dialog.openUrl({
		url	:	basePath + url,
		title: 	"查看登录用户",
		size:	"L",
		customButton:	[{name: '关闭',focus: true}]
	});
}
