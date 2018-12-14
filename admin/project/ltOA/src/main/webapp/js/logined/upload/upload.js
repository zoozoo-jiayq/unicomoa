$(document).ready(function() {
	removeAttachmentSession();
	$("#file_upload").uploadify({
		// 和存放队列的DIV的id一致
		'queueID' : 'queue',
		// 服务器端脚本使用的文件对象的名称 $_FILES个['upload']
		'fileObjName' : 'fileupload',
		// 上传处理程序
		'uploader' : basePath + 'filemanager/uploadfile.action?module='
				+ $("#moduleName").val(),
		// 按钮文字
		'buttonText' : '上传附件...',
		// 附带值
		'formData' : {},
		// 浏览按钮的背景图片路径
		'buttonImage' : basePath + 'flat/plugins/upload/upload.png',
		// 取消上传文件的按钮图片，就是个叉叉
		'cancel' : basePath + 'plugins/upload/upbutton.png',
		// 浏览按钮的宽度
		'width' : '48',
		// 浏览按钮的高度
		'height' : '15',
		// 在浏览窗口底部的文件类型下拉菜单中显示的文本
		'fileTypeDesc' : '支持的格式:',
		// 允许上传的文件后缀
		'fileTypeExts' : '*.*',
		// 上传文件的大小限制
		'fileSizeLimit' : '10MB',
		// 上传数量
		'queueSizeLimit' : 25,
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
        	onUploadProgressHelp(file, totalBytesUploaded, totalBytesTotal);
		},
		// 选择上传文件后调用
		'onSelect' : function(file) {
			var name =file.name;
			if(name.length>30){
				art.dialog.alert("文件名长度不能大于30个字符!");
				return;
			}
			
			onSelectHtml(file, "appendixList", "file_upload");
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
				default:
					break;
			}
		},
		// 检测FLASH失败调用
		'onFallback' : function() {
			art.dialog.alert("您未安装FLASH控件，无法上传图片！请安装FLASH控件后再试。");
		},
		// 上传到服务器，服务器返回相应信息到data里
		'onUploadSuccess' : function(file, data, response) {
			/**
			 * alert('The file ' + file.name + ' was successfully uploaded with
			 * a response of ' + response + ':' + data);
			 * 
			 * art.dialog.confirm('你确定要导入这些数据吗？', function () {
			 * importData(data); }, function () { art.dialog.tips('取消导入操作'); });
			 */
			// 上传成功后,删除附件（进度条）页面提示信息
			// $("#"+file.id).find("div.cancel>a").attr("href","javascript:removeFile('"+file.id+"')");
			// 保存上传附件的ID
			//addAttachment(file.name, data, file.id);
			// 用于显示html
			//addAttachment_0903(file.name, data, file.id,file);
			if(data){
				if(data=="filenametoolong"){
					art.dialog.alert("文件名长度不能大于30个字符!");
					return;
				}
			}
			var data= eval("("+data+")");
			var appendixIds = $("#appendixIds").val();
			if (appendixIds == null || appendixIds == "") {
				appendixIds = ",";
			}
			appendixIds += data.id + ",";
			$("#appendixIds").val(appendixIds);

			// 全部删除时,替换 掉 ","
			if (appendixIds == ",") {
				$("#appendixIds").val("");
			}
			
			onUploadSuccessHelp(file,null,"file_upload",data.id);
		},
		// 上传前取消文件
		'onCancel' : function(file) {
		}
	});
});

function deleteAttachment_daily(attachId) {
    var attachmentVal = $("#appendixIds").val();
    attachId = ","+attachId+",";
    //attachmentVal = deleteAttachmentByPath(filePath, attachmentVal);
    attachmentVal = attachmentVal.replace(attachId,",");
    $("#appendixIds").val(attachmentVal);
}
/**
 * 文件后缀名判断的函数 根据不同的后缀名称 进行相应的处理
 * @param {} type
 * @return {}
 */
function getClassByFileType(type) {
	if (type.lastIndexOf(".") != -1) {
		type = type.substr(type.lastIndexOf(".")+1, type.length);
	}
	type = type.toLocaleLowerCase();
	var defaultType = {
		txt : "fileTxt",
		doc : "fileWord",
		ppt : "filePPT",
		excel : "fileExcel",
		img : "filePicture",
		rar : "fileRar",
		html:"fileHtml",
		yinyue:"fileYinyue"
	};
	switch (type) {
		case "txt" :
			return defaultType.txt;
		case "doc" :
		case "docx" :
			return defaultType.doc;
		case "ppt" :
		case "pptx" :
			return defaultType.ppt;
		case "xls" :
		case "xlsx" :
			return defaultType.excel;
		case "gif" :
		case "jpg" :
		case "jpeg" :
		case "png" :
			return defaultType.img;
		case "rar" :
		case "zip" :
		case "7z" :
			return defaultType.rar;
		case "js" :
		case "html" :
		case "jsp" :
			return defaultType.html;
		case "mp3" :
		case "mp4" :
		case "avi" :
		case "rmvb" :
			return defaultType.yinyue;
		default :
			return defaultType.txt;
	}
}

/**
 * 文件后缀名判断的函数 根据不同的后缀名称 进行相应的处理
 * @param {} type
 * @return {}
 */
function getTypeFileType(type) {
	if (type.lastIndexOf(".") != -1) {
		type = type.substr(type.lastIndexOf(".")+1, type.length);
	}
	type = type.toLocaleLowerCase();
	if(type=='rar'||type=='zip'||type=='7z'){
		type='rar';
	}else if(type=='doc'||type=='docx'){
		type='doc';
	}else if(type=='excel'||type=='excelx'){
		type='excel';
	}else if(type=='ppt'||type=='pptx'){
		type='ppt';
	}else if(type=='png'||type=='bmp'||type=='jpg'||type=='gif'||type=='jpeg'){
		type='img';
	}else if(type=='txt'){
		type='txt';
	}
	
	return type;
}
/**
 * 生成一个附件的HTML内容供给显示
 * 
 * @param fileName
 *            文件名称
 * @param filePath
 *            文件路径
 */
function createOneAttachmentHTML(fileName, attachPath, id) { 
//	var html = "<p><span class='filesIco'>" + fileName
//			+ "<a class='xxx' href='javascript:void(0)' onclick='removeFile(\""
//			+ id + "\",this)'></a>" + "</span></p>";
	var attachmentId = $("#appendixId").val();
	var url="";
	var html = "";
	
	if(getClassByFileType(fileName)=="fileTxt"||getClassByFileType(fileName)=="fileHtml"||getClassByFileType(fileName)=="fileExcel"||getClassByFileType(fileName)=="fileWord")
	{
		// 传递附件Id或者附件所在的物理路径
		if (null == attachmentId){
			url= basePath+"upload/downview.action?attachPath="+attachPath+"&attachName="+fileName;
			attachmentId = attachPath;
		}else{
			url= basePath+"file/htmlPreview.action?attachId="+attachmentId;
		}
    	
		html = "<li><div class='icon'><em class='";
		html +=getTypeFileType(fileName);
		html += "'></em></div><div class='txt'><p>"
			+ fileName
			+ "</p><p><a  href='javascript:void(0)' onclick='downAttachment(\""+url+"\")'>预览</a><a  href='javascript:void(0)' onclick='removeFile(\""
			+ id + "\",this)'>删除</a></p></div><p class='clear'></p></li>";
		
		 
	}
	else if(getClassByFileType(fileName)=="filePicture")
	{
		// 传递附件Id或者附件所在的物理路径
		if (null == attachmentId){
			url= basePath+"upload/downview.action?attachPath="+attachPath+"&attachName="+fileName;
			attachmentId = attachPath;
		}else{
			url= basePath+"upload/downview.action?attachmentId="+attachmentId;
		}
    	
		html = "<li><div class='icon'><em class='";
		html +=getTypeFileType(fileName);
		html+="'></em></div><div class='txt'><p>"
			+ fileName
			+ "</p><p><a  href='javascript:void(0)' onclick='downAttachment(\""+url+"\")'>预览</a><a  href='javascript:void(0)' onclick='removeFile(\""
			+ attachmentId + "\",this)'>删除</a></p></div><p class='clear'></p></li>";
		
	}
	
	
	else /*if(getClassByFileType(fileName)=="fileRar"||getClassByFileType(fileName)=="filePPT"||getClassByFileType(fileName)=="fileYinyue")*/
	{
		if (null == attachmentId){
			attachmentId = attachPath;
		}
		html = "<li><div class='icon'><em class='";
		html +=getTypeFileType(fileName);
		html +="'></em></div><div class='txt'><p>"
			+ fileName
			+ "</p><p><a  href='javascript:void(0)' onclick='removeFile(\""
			+ id + "\",this)'>删除</a></p></div><p class='clear'></p></li>";
	}
	
	//$('#file_upload').after(html);
	return html;
}

// 上传成功后点击删除按钮
function removeFile(target, domAobj) {
	// $("#"+target).remove();
	// alert("取消上传" + target);
	// 首先隐藏附件显示
	$(domAobj).parent().parent().parent().remove();

	var paramData = {
		"filePageId" : target
	};
	// 发送ajax请求,获取隐藏域中的附件ID
	$.ajax({
				url : basePath + 'upload/delAttachment.action',
				type : 'post',
				dataType : 'json',
				data : paramData,
				success : function(data) {
					// $("#result").append("<a
					// href="+basePath+"demo/downFile.action?fileuploadFileName="+data.importData.errorFileName+">下载导入失败的数据</a>");;
					var appendixIds = $("#appendixIds").val();
					var str = "," + data + ",";
					var appendixIdsNew = appendixIds.replace(str, ",");
					$("#appendixIds").val(appendixIdsNew);
					var apppenIds = $("#appendixIds").val();
					// 全部删除时,替换 掉 ","
					if (apppenIds == ",") {
						$("#appendixIds").val("");
					}
				}
			});
}

// 提交导入申请
function importData(fileuploadFileName) {
	var paramData = {
		"fileuploadFileName" : fileuploadFileName
	};

	// 发送ajax请求,获取对应的人员姓名
	$.ajax({
				url : basePath + 'demo/importUser.action',
				type : 'post',
				dataType : 'json',
				data : paramData,
				success : function(data) {
					$("#result").append("<a href=" + basePath
							+ "demo/downFile.action?fileuploadFileName="
							+ data.importData.errorFileName + ">下载导入失败的数据</a>");;
				}
			});
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
function addAttachment_0903(attachName, attachFile, filePageId,file) {
	var paramData = {
		"attach.attachFile" : attachFile,
		"attach.attachName" : attachName,
		"filePageId" : filePageId
	};

	// 发送ajax请求,添加附件
	$.ajax({
				url : basePath + 'filemanager/addAttachment.action',
				type : 'post',
				dataType : 'json',
				data : paramData,
				success : function(data) {
//					$("#appendixId").val(data.attachmentId);
					var appendixIds = $("#appendixIds").val();
					if (appendixIds == null || appendixIds == "") {
						appendixIds = ",";
					}
					appendixIds += data.attachmentId + ",";
					$("#appendixIds").val(appendixIds);

					// 全部删除时,替换 掉 ","
					if (appendixIds == ",") {
						$("#appendixIds").val("");
					}
					/**
					if($("#appendixId").val()!=""){
						var htmlOld = $("#appendixList").html();
						var htmlNew = createOneAttachmentHTML(attachName, data, filePageId);
						var html = htmlOld+htmlNew;
						$("#appendixList").html(html);
					}
					*/
					onUploadSuccessHelp(file,null,"file_upload",data.attachmentId);
					
					//add by jiayq,添加上传成功后的回调函数，回调函数原型：uploadCallback()
					if(uploadCallback){
						uploadCallback();
					}
				}
			});
}

function removeAttachmentSession() {
	// 发送ajax请求,添加附件
	$.ajax({
				url : basePath + 'filemanager/removeAttachmentSession.action',
				type : 'post',
				dataType : 'json',
				success : function(data) {
					// alert(data);
				}
			});

}

/**
 * 需要开发的页面
 */
 function viewAttachment(url){
    
    var	url=encodeURIComponent(url);
	window.open(basePath+ 'logined/notify/viewAttachment.jsp?url='+url+'');
   

/*	var url =basePath+ "upload/downfile.action?attachmentId="+ 947;
	intializePage(url);  //读取文件，如果没有保存，则读取默认页
*/}
/**
 * 需要开发的页面
 */
 function downAttachment(url){
 	
 	window.open(url);
   

/*	var url =basePath+ "upload/downfile.action?attachmentId="+ 947;
	intializePage(url);  //读取文件，如果没有保存，则读取默认页
*/}
 
 
 /**
  * 查看页面需要提供预览功能
  * @param fileName
  * @param attachmentId
  * @param attachPath
  * @returns {String}
  */
 function createViewAttachmentHTML(fileName, attachmentId, attachPath) {
		var url="";
		var html = "";
		
		if(getClassByFileType(fileName)=="fileTxt"||getClassByFileType(fileName)=="filePicture"||getClassByFileType(fileName)=="fileHtml")
		{
			// 传递附件Id或者附件所在的物理路径
			if (null == attachmentId){
				url= basePath+"upload/downview.action?attachPath="+attachPath+"&attachName="+fileName;
				attachmentId = attachPath;
			}else{
				url= basePath+"upload/downview.action?attachmentId="+attachmentId;
			}
	    	
			html = "<a  href='javascript:void(0)' onclick='downAttachment(\""+url+"\")'>预览</a>";
			 
		}
		else if(getClassByFileType(fileName)=="fileExcel"||getClassByFileType(fileName)=="fileWord")
		{
			if (null == attachmentId){
				url= basePath+"upload/viewfile.action?attachPath="+attachPath+"&attachName="+fileName;
				attachmentId = attachPath;
			}else{
				url= basePath+"upload/viewfile.action?attachmentId="+attachmentId;
			}
			html = "<a  href='javascript:void(0)' onclick='viewAttachment(\""+url+"\")'>预览</a>";			
		}
		
		
		else /*if(getClassByFileType(fileName)=="fileRar"||getClassByFileType(fileName)=="filePPT"||getClassByFileType(fileName)=="fileYinyue")*/
		{
			if (null == attachmentId){
				attachmentId = attachPath;
			}
			html = "";
		}
		
		return html;
	}
 
 /**
  * 下载链接
  * @param attachmentId
  */
 function downloadFile(attachmentId) {
	    var downloadURL = basePath + "filemanager/downfile.action?attachmentId=" +attachmentId;
	    window.open(downloadURL);
	}
 
 
  