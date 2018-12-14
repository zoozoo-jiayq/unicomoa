/**
 * 模块权限设置js
 */

$(document).ready(function() {
			initMenuListClick();
		});

function closeDialog() {
	art.dialog.close();
}

function initMenuListClick() {
	$(".menuList li").click(function() {
				var moduleName = $(this).find("a").text();
				$(this).parent().find(".current").removeClass("current");
				$(this).addClass("current");
				$("#targetModuleName").text(moduleName);
				getAndSetManagementRange();
			});
	$(".menuList li:first").addClass("current");
	$("#targetModuleName").text($(".menuList li:first a").text());
	getAndSetManagementRange();
}

function saveModulePriv() {
	$.ajax({
				url : basePath + "user/saveManagementRange.action",
				type : "post",
				dataType : 'json',
				data : {
					"modulePriv.userId" : $.trim($("#userId").val()),
					"modulePriv.moduleName" : $.trim($("#targetModuleName")
							.text()),
					"modulePriv.userIds" : $.trim($("#userIds").val()),
					"modulePriv.userNames" : $.trim($("#userNames").val()),
					"modulePriv.roleIds" : $.trim($("#roleIds").val()),
					"modulePriv.roleNames" : $.trim($("#roleNames").val()),
					"modulePriv.groupIds" : $.trim($("#groupIds").val()),
					"modulePriv.groupNames" : $.trim($("#groupNames").val())
				},
				success : function(data) {
					data = eval(data);
					if (data["status"] == "success") {
						art.dialog({
							   title:"消息",
							   content:"保存成功！",
							   width:320,
							   height:110,
							   icon:"succeed",
							   opacity:0.08,
							   ok:function(){},
							   close:function(){
								
							   }
							});
					} else if (data["status"] == "error") {
						art.dialog.alert(data["content"]);
					}
				}
			});
}

function getAndSetManagementRange() {

	var userId = $("#userId").val();
	if (userId != undefined && $.trim(userId) != "") {
		$.ajax({
					url : basePath + "user/getManagementRange.action",
					type : "post",
					dataType : 'json',
					data : {
						userId : userId,
						moduleName : $.trim($("#targetModuleName").text())
					},
					success : function(data) {
						data = eval(data);
						if (data["status"] == "success") {
							var modulePriv = eval(data["content"]);
							var userNames = modulePriv["userNames"];
							var userIds = modulePriv["userIds"];
							var roleIds = modulePriv["roleIds"];
							var roleNames = modulePriv["roleNames"];
							var groupIds = modulePriv["groupIds"];
							var groupNames = modulePriv["groupNames"];
							if (userNames != undefined
									&& $.trim(userNames) != "") {
								$("#userNames").val(userNames);
							} else {
								$("#userNames").val("");
							}

							if (userIds != undefined && $.trim(userIds) != "") {
								$("#userIds").val(userIds);
							} else {
								$("#userIds").val("");
							}

							if (roleIds != undefined && $.trim(roleIds) != "") {
								$("#roleIds").val(roleIds);
							} else {
								$("#roleIds").val("");
							}

							if (roleNames != undefined
									&& $.trim(roleNames) != "") {
								$("#roleNames").val(roleNames);
							} else {
								$("#roleNames").val("");
							}

							if (groupIds != undefined && $.trim(groupIds) != "") {
								$("#groupIds").val(groupIds);
							} else {
								$("#groupIds").val("");
							}

							if (groupNames != undefined
									&& $.trim(groupNames) != "") {
								$("#groupNames").val(groupNames);
							} else {
								$("#groupNames").val("");
							}

						} else if (data["status"] == "error") {
							art.dialog.alert(data["content"]);
						}
					}
				});
	}
}