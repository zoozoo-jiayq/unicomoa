$(function(){
	$("#file_upload").uploadify({
		// 和存放队列的DIV的id一致
		'queueID' : 'queue',
		// 服务器端脚本使用的文件对象的名称 $_FILES个['upload']
		'fileObjName' : 'fileupload',
		// 上传处理程序
		'uploader' : basePath +  'filemanager/uploadfile.action?module=record',
		// 按钮文字
		'buttonText' : '上传附件...',
		// 附带值
		'formData' : {
			'userid' : '用户id',
			'username' : '用户名',
			'rnd' : '加密密文'
		},
		// 浏览按钮的背景图片路径
		'buttonImage' : basePath + 'logined/appraisal/images/add_fujian.png',
		// 取消上传文件的按钮图片，就是个叉叉
		'cancel' : basePath + 'plugins/upload/upbutton.png',
		// 浏览按钮的宽度
		'width' : '98',
		// 浏览按钮的高度
		'height' : '24',
		'margin':"15",
		// 在浏览窗口底部的文件类型下拉菜单中显示的文本
		'fileTypeDesc' : '支持的格式:',
		// 允许上传的文件后缀
		'fileTypeExts' : '*.*',
		// 上传文件的大小限制
		'fileSizeLimit' : '3MB',
		// 上传数量
		'queueSizeLimit' : 1,
		// 开启调试
		'debug' : false,
		// 是否自动上传
		'auto' : true,
		// 上传后是否删除
		'removeComplete' : false,
		// 清除
		removeTimeout : 0,

		langFile : basePath + 'plugins/upload/ZH_cn.js',

		// 超时时间
		'successTimeout' : 99999,
		// flash
		'swf' : basePath + 'plugins/upload/uploadify.swf',
		// 不执行默认的onSelect事件
		'overrideEvents' : ['onDialogClose'],
		// 每次更新上载的文件的进展
		'onUploadProgress' : function(file, bytesUploaded, bytesTotal,
				totalBytesUploaded, totalBytesTotal) {
			// 有时候上传进度什么想自己个性化控制，可以利用这个方法
			onUploadProgressHelp(file, bytesUploaded, bytesTotal);
		},
		// 选择上传文件后调用
		'onSelect' : function(file) {
			var isCanSelect = true;
            var name =file.name;
            if(name.length>30){
                art.dialog.alert("文件名长度不能大于30个字符!");
                return;
            }
            var tempAttachmentId = $("#attachmentId").val();
            if(tempAttachmentId){
            	tempAttachmentId = tempAttachmentId.substring(1,tempAttachmentId.length-1);
            	if(tempAttachmentId.split(",").length>5){
            		isCanSelect = false;
            	}
            }
            if(!isCanSelect){
            	return false;
            }else{
            	onSelectHtml(file, "attachmentList", "file_upload");
            }
			return true;
		},
		// 返回一个错误，选择文件的时候触发
		'onSelectError' : function(file, errorCode, errorMsg) {
			switch (errorCode) {
				case -100 :
					art.dialog.alert("上传的文件数量已经超出系统限制的"
							+ $('#file_upload').uploadify('settings',
									'queueSizeLimit') + "个文件！");
					break;
				case -110 :
					art.dialog.alert("文件 ["
							+ file.name
							+ "] 大小超出系统限制的"
							+ $('#file_upload').uploadify('settings',
									'fileSizeLimit') + "大小！");
					break;
				case -120 :
					art.dialog.alert("文件 [" + file.name + "] 大小异常！");
					break;
				case -130 :
					art.dialog.alert("文件 [" + file.name + "] 类型不正确！");
					break;
			}
		},
		// 检测FLASH失败调用
		'onFallback' : function() {
			art.dialog.alert("您未安装FLASH控件，无法上传图片！请安装FLASH控件后再试。");
		},
		// 上传到服务器，服务器返回相应信息到data里
		'onUploadSuccess' : function(file, data, response) {
			var attachmentId = $("#attachmentId").val();
			var isCanUpload = true;
			 if(attachmentId){
	            	var tempAttachmentId = attachmentId.substring(1,attachmentId.length-1);
	            	if(tempAttachmentId.split(",").length>5){
	            		isCanUpload = false;
	            	}
            }
			 if(isCanUpload){
				 data = eval("(" + data + ")");
					$("#attachmentId").val(data.id);
					 onUploadSuccessHelp(file,null,"file_upload",data.id);
			 }else{
				 art.dialog.alert("上传的文件数量已经超出系统限制的");
			 }
			
		},
		// 上传前取消文件
		'onCancel' : function(file) {
			// art.dialog.alert('The file ' + file.name + ' was cancelled.');
		}
	});
	
});

function deleteAttachment_record(attachmentId, domAObj) {
    $("#file_upload").show();
	$(domAObj).parent().parent().remove();
	var attachmentIdAll = $("#attachmentId").val();
	attachmentIdAll = attachmentIdAll.replace("," + attachmentId + ",", ",");
	$("#attachmentId").val(attachmentIdAll);
}

function viewFile_record(id){
	window.open(basePath+ 'filemanager/htmlPreview.action?attachId='+id,"","toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=no, resizable=no, copyhistory=no");
}

function downFileById(id) {
	alert(basePath + 'filemanager/downfile.action?_clientType=wap&attachmentId=' + id)
	window.location.href = basePath + 'filemanager/downfile.action?_clientType=wap&attachmentId=' + id;
}

/**
 * 上传取消
 * @param fileId
 */
function cancleUpload(fileId, fileUpload, filePath,attachId){
	$("#file_upload").show();
    /*$('#'+fileUpload).uploadify('cancel', fileId);*/
    $('#'+fileId+"_li").remove();
   // window.parent.frameResize();
    //添加回调函数,add by jiayq
    try{

	    if(deleteAttach){
	    	deleteAttach(attachId);
	    }

    }catch(e){}
   
}

//删除
function deleteAttach(attachmentId) {
	var attachmentIdAll = $("#attachmentId").val();
	attachmentIdAll = attachmentIdAll.replace("," + attachmentId + ",", ",");
	$("#attachmentId").val(attachmentIdAll);
}