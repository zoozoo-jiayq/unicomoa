$(document).ready(function() {	
	//$("#file_upload").show();
	qytx.app.fileupload({
		id	:	"file_upload",
		moduleName	:	"address",
		fileTypeExts	:	"*.jpg;*.jpeg;*.gif;*.png",
		fileSizeLimit	:	"500Kb",
		callback	:	function(data){
			var attachFile = data.attachFile;
          $("#photo").val(attachFile);
          displayPhoto();
		}
	});
});
//上传成功后点击删除按钮
function removeFile(target){

}