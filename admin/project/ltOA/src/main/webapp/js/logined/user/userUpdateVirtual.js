
$(document).ready(function() {
	// 单击取消
	$("#back").click(function() {
		window.location.href = document.referrer;
				return false;
			});
	
	// 单击更新
	$("#userInfoUpdate").click(function() {
				submitForm();
			});
});

function submitForm() {
	// 用户id
	var userId = $.trim($("#userId").val());
	// 用户排序号
	var userOrder = $.trim($("#userOrder").val());
	//职务
	var job = $.trim($("#job").val());
	// 工作电话
	var phone2 = $.trim($("#phone2").val());

	var paramData = {
		'user.userId' : userId,
		'user.orderIndex' : userOrder,
		'user.phone2' : phone2,
		'user.job' :job
	};
	qytx.app.ajax({
				type : 'post',
				url : basePath + "user/updateUserForVirtual.action",
				data : paramData,
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