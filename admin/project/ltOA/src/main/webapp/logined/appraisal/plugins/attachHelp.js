
/**
 * 选中上传后初始化页面
 * @param file 文件内容
 * @param fileListUl 附件上传展示的ul
 */
function onSelectHtml(file, fileListUl, fileUpload){
	var creatAttachHtml = '<li id="'+file.id+'_li"><div ><em></em></div>';
	creatAttachHtml += '<div ><p style="margin-top:15px;margin-right:15px;display:inline-block;" class="file_tishi"><em></em>'+file.name+'</p>';
	creatAttachHtml += '<p style="display:inline-block;" id="'+file.id+'_p"><span ><i id="'+file.id+'_i" style="width:0%"></i></span><font id="'
	    +file.id+'_font"></font><a style="color:#ff5454;font-size: 14px" id="'+file.id+'_delete"  href="javascript:cancleUpload(\''+file.id+'\', \''+fileUpload+'\')">删除</a></p>';
	creatAttachHtml += '</div><p ></p></li>';
	//alert(creatAttachHtml);
    $("#"+fileListUl).html("");
	$("#"+fileListUl).append(creatAttachHtml);
	//window.parent.frameResize();
}


/**
 * 变更上传进度
 * @param file 文件内容
 * @param totalBytesUploaded 总上传大小
 * @param totalBytesTotal 总文件大小
 */
function onUploadProgressHelp(file, totalBytesUploaded, totalBytesTotal){
	var percentage = Math.floor(totalBytesUploaded*100/totalBytesTotal);
	$("#"+file.id+"_i").attr("style", "width:"+percentage+"%");
	$("#"+file.id+"_font").html(percentage+"%");
}


/**
 * 上传完成后操作
 * @param file 文件内容
 */
function onUploadSuccessHelp(file, filePath, fileUpload, attachId){
	$("#"+file.id+"_i").parent().remove();
	$("#"+file.id+"_font").remove();
	if(attachId!=null&&attachId!=""&&undefined!=attachId){
		// 修改删除选项
		$("#"+file.id+"_delete").attr("href", 'javascript:cancleUpload(\''+file.id+'\', \''+fileUpload+'\',null,\''+attachId+'\')');
		
		// 增加预览功能	
		//var previewUrl = getPreviewUrl(attachId, null,  null);
		//$("#"+file.id+"_p").prepend("<a href='javascript:previewdownAttachment(\""+previewUrl+"\")'>预览</a>");
	}else{
		// 修改删除选项
		$("#"+file.id+"_delete").attr("href", 'javascript:cancleUpload(\''+file.id+'\', \''+fileUpload+'\',\''+filePath+'\',\''+attachId+'\')');
		
		// 增加预览功能	
		//var previewUrl = getPreviewUrl(null, filePath,  encodeURI(file.name));
		//$("#"+file.id+"_p").prepend("<a href='javascript:previewdownAttachment(\""+previewUrl+"\")'>预览</a>");
	}
    $("#file_upload").hide();
}

/**
 * 上传取消
 * @param fileId
 */
function cancleUpload(fileId, fileUpload, filePath,attachId){
    $('#'+fileUpload).uploadify('cancel', fileId);
    $('#'+fileId+"_li").remove();
   // window.parent.frameResize();

    //添加回调函数,add by jiayq
    try{

	    if(deleteAttach){
	    	deleteAttach(attachId);
	    }

    }catch(e){}
    try{
    	if(deleteAttachment_daily){
	    	deleteAttachment_daily(attachId);
	    }
    }catch(e){}

    try{
	    if (filePath != undefined && filePath != null && "" != filePath){
	    	if(deleteAttachment){
	    		deleteAttachment(filePath);
	    	}
	    }
    }catch(e){}

}

/**
 *根据文件类型获取对应class
 * @param type 文件类型
 * @return {string} class名称
 */
function getClassByFileName(fileName) {
	
    if (fileName.lastIndexOf(".") != -1) {
    	fileName = fileName.substr(fileName.lastIndexOf(".")+1, fileName.length);
    }else{
    	fileName = 'unknow';
    }
    fileName = fileName.toLocaleLowerCase();
    var defaultType = {txt: "wb", doc: "doc", ppt: "ppt", excel: "excel", img: "img", rar: "rar",unknown:"unknown"};
    switch (fileName) {
        case "txt":
            return defaultType.txt;
        case "doc":
        case "docx":
            return defaultType.doc;
        case "ppt":
        case "pptx":
            return defaultType.ppt;
        case "xls":
        case "xlsx":
            return defaultType.excel;
        case "gif":
        case "jpg":
        case "jpeg":
        case "png":
            return defaultType.img;
        case "rar":
        case "zip":
        case "7z":
            return defaultType.rar;
        default :
            return defaultType.unknown;
    }
}

function getPreviewUrl(attachmentId, attachPath, fileName){
    //如果传的是附件的id则调用此url
	var url= basePath+"/filemanager/htmlPreview.action?attachId="+attachmentId;
	if (null == attachmentId || '' == attachmentId){
	   //  如果传的是文件的路径调用此方法
	   url= basePath+"/filemanager/htmlPreview.action?attachFile="+attachPath+"&attachName="+encodeURIComponent(fileName);
	}
	return url;
}

/**
 * 需要开发的页面
 */
function previewdownAttachment(url){
 	window.open(url);
}

/**
 * 删除附件
 * 
 * @return
 */
function delAttachById(id,Object) {

	var appendixIds = $("#appendixIds").val();
	var removeId = "," + id + ",";
	appendixIds = appendixIds.replace(removeId, ",");
	if (appendixIds == ',') {
		appendixIds = "";
	}
	// alert(appendixIds);
	$("#appendixIds").val(appendixIds);
	$(Object).parent().parent().parent().remove();
}
function viewfileOpen(id){
	window.open(basePath+ 'filemanager/htmlPreview.action?attachId='+id);
}

/**
 * 下载
 * @param attachmentId
 */
function downLoadAttachment(attachmentId){
	var url = basePath+"filemanager/downfile.action?attachmentId="+attachmentId;
	document.location.href = url;
}
//删除
function deleteAttach(attachmentId) {
	var attachmentIdAll = $("#attachmentId").val();
	attachmentIdAll = attachmentIdAll.replace("," + attachmentId + ",", ",");
	$("#attachmentId").val(attachmentIdAll);
}
