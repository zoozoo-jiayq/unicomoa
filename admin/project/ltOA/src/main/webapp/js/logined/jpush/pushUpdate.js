//发送范围批量设置
$(document).ready(function() {

	// 添加公司
	$("#addPushUsers").click(function() {
		var userIds = $("#userIds").val();
		var userIdsReal = "";
		if (userIds != "") {
			userIdsReal = userIds.replace(/c_/g, "");
		}
		selectUsers(3, userIdsReal);
		return false;
	});
	// 清空公司
	$("#clearPushUsers").click(function() {
		$("#userIds").val("");
		$("#userNames").val("");
		return false;
	});
	// 点击推送
	$("#updatePush").click(function() {
		updatePush();
		return false;
	});
	// 显示部门
	$("input[name='rangeType']").click(function() {
		if ($(this).val() == 5) {
			$("#sendGroups").show();
		} else {
			$("#sendGroups").hide();
		}
	});
	// 清空
	$("#canncel").click(function() {
		window.location.href = basePath + "logined/jpush/pushList.jsp";
		return false;
	});
});
// 增加推送
function updatePush() {
	var userIds = $("#userIds").val();
	if (userIds == "") {
		art.dialog.alert("请选择发送范围!");
		return false;
	}
	if (userIds.length > 0) {
		art.dialog.confirm('确认要发布活动吗？', function() {
			var paramData = {
				'userIds' : userIds.toString(),
				'pushId' : $.trim($("#pushId").val())
			};
			$.ajax({
				url : basePath + "jpush/editPush.action",
				type : "post",
				dataType : 'text',
				data : paramData,
				beforeSend : function() {
					$("body").lock();
				},
				complete : function() {
					$("body").unlock();
				},
				success : function(data) {
					art.dialog.alert("发布成功!", function() {
						window.location.href = basePath
								+ "logined/jpush/pushList.jsp";
					});
				}
			});
		});
	}
}
