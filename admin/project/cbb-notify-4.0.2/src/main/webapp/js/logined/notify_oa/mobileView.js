
$(document).ready(function(){
	var id = $("#id").val();
	getNotifyInfo(id);
})

/**
 * 加载公告信息
 * @param notifyId
 */
function getNotifyInfo(notifyId) {
	dataParam = {
		'id' : notifyId,
		'_clientType':'wap'
	};
	$.ajax({
		type : 'post',
		url : basePath + "wapNotify/mobileViewById.action",
		data : dataParam,
		dataType : 'json',
		async : false,
		success : function(data) {
			$("#title").text(data.subject);
			$("#info").html(data.differenceTime+"&nbsp;&nbsp;<span>"+data.createUser.userName+"</span>");
			$("#content").html(data.content);
		}
	});
}