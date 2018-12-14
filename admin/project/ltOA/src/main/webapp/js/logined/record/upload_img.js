$(document).ready(function() {	
	qytx.app.fileupload({
		id	:	"img_upload",
		buttonText:	"上传照片",
		moduleName:	"record_photo",
		queueID:	"img_queue",
		fileSizeLimit:	"200K",
		fileTypeExts:	"*.jpg;*.jpeg;*.gif;*.png",
		queueSizeLimit:	1,
		progress:	false,
		callback: 	function(data){
			$("#photoUrlHidden").val(data.attachFile);
            displayPhoto();
		}
	});
});