//javascript for wap email list.jsp

var iDisplayStartOfAffairslList = 0;

$(document).ready(function() {
	getNotifyInfo();

});

/**
 * 初始化公告信息
 * 
 * @return
 */
function getNotifyInfo() {
	var notifyId = $("#notifyId").val();
	dataParam = {
		'notifyVo.notifyId' : notifyId
	};
	$.ajax({
	    type : 'post',
	    url : basePath + "notify/getWapNotifyInfo.action"+"?random="+Math.random(),
	    data : dataParam,
	    dataType : 'json',
	    async : false,
	    success : function(data) {

		    $("#total").html(data.total);

		    $("#attament").html(data.attachmentList);
		    $("#subject").html(data.subject);
		    $("#createUser").html(data.createUserName);
		    $("#createTime").html(data.createTimeStr);
		    $("#endTime").html(data.begDate);
		    $("#info").html(data.content);
	    }
	});
}

/**
 * 下载指定的文件
 * 
 * @param filePath
 *            文件路径
 * @param fileName
 *            文件名称
 */
function downloadFile(filePath, fileName) {
	var downloadURL = basePath + "notify/downloadFile.action" + wapParam + "&downFileName=" + filePath + "&fileName="
	        + fileName+"&random="+Math.random();
	alert(downloadURL);
	window.open(downloadURL);
}
