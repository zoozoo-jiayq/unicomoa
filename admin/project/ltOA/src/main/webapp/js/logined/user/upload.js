$(document).ready(function() {
	qytx.app.fileupload({
		id	:	"file_upload",
		module	:	"address",
		fileTypeExts	:	"*.jpg;*.jpeg;*.gif;*.png",
		fileSizeLimit	:	"1024KB",
		callback	:	function(data){
			 $("#photo").val(data.attachFile);
	         displayPhoto();
		}
	});
});
//上传成功后点击删除按钮
function removeFile(target){

}