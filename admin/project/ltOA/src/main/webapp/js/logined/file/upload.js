$(document).ready(function() {
	var fileName = "";
	qytx.app.fileupload({
		id	:	"file_upload",
		moduleName: $("#moduleName").val(),
		width:	82,
		height:	28,
		buttonImage : basePath + 'flat/images/uploads.png',
		callback:	function(data){
			var sortId = $("#fileSortId").val();
			addAttachment(data.id,data.attachName, data.attachFile, data.id, sortId);
		}
	});
});
// 上传成功后点击删除按钮
function removeFile(target) {
	$("#" + target).remove();
	// alert("取消上传" + target);

}

/**
 * @param attachName
 *            附件名字
 * @param attachFile
 *            附件地址
 * @param filePageId
 *            页面显示ID 用于删除
 * @return
 */
function addAttachment(id,attachName, attachFile, filePageId, sortId) {

	var paramData = {
		"attach.id" : id,
		"attach.attachFile" : attachFile,
		"attach.attachName" : attachName,
		"filePageId" : filePageId,
		"type":type,
		"sortId" : sortId
	};
	
	// 发送ajax请求,添加附件
	$.ajax({
				url : basePath + 'file/att.action',
				type : 'post',
				dataType : 'json',
				data : paramData,
				success : function(data) {
					var name = data.name;
					var fileSortId = data.fileSortId;
					var path = data.path;
					qytx.app.dialog.tips("上传成功！", function(){getDataTable();});

				}
			});
}
