/**
 * 功能:人事档案展示页面js
 */
$(document).ready(function(){
    dateStr2ShortForTagText();
    displayPhoto();
    initAttachmentDownloadHTML();
    
    // 是否显示返回按钮
    if ($("#from").val() == "detail"){
    	$("#buttonAreaDiv").hide();
    }
});

/**
 *初始化附件下载的HTML内容
 */
function initAttachmentDownloadHTML() {
    var attachmentVal = $("#attachment").val();
    var jObjAttachmentDiv = $("#attachmentDiv");
    if (typeof(attachmentVal) == "undefined" || $.trim(attachmentVal) == "" || attachmentVal.length <= 2) {
        //没有附件
        jObjAttachmentDiv.hide();
        return;
    }
    var jsonList = JSON.parse(attachmentVal);
    var html = "";
    var count = 0;
    for (var i = 0; i < jsonList.length; i++) {
        count++;
        var jsonMap = jsonList[i];
        //path,name,size
        var path = jsonMap["path"];
        var name = jsonMap["name"];
        var type = name.substring(name.lastIndexOf(".") + 1);//不需要点号,类型如：txt,jpg,rar
        var cls = getClassByFileTypeOwn(type);
        var size = convertFileSize(jsonMap["size"]);
        html += "<li>";
        html += "<div class='icon'><em class='" + cls + "'></em></div>";
        html += "<div class='txt'>";
        html += "<p>" + name + "(" + size + ")" + "</p>";
        html += "<p><a href='javascript:void(0)' onclick='downloadFile(\"" + path + "\",\"" + name + "\")'>下载</a>";
        html += createEmailNewOneAttachmentHTML(name, null, path, false, false);
        html += "</p></div>";
        html += "<p class='clear'></p>";
        html += "</li>";
    }
    jObjAttachmentDiv.find("ul").html(html);
    window.parent.frameResize();
}

/**
 * 不用插件的附件预览功能的方法
 * @param {} fileName
 * @param {} attachmentId
 * @param {} attachPath
 * @return {}
 */
function createEmailNewOneAttachmentHTML(fileName, attachmentId, attachPath, isShowFileName, isShowDelete) {
	var url="";
	var html = "";
	if(getClassByFileType(fileName)=="fileTxt"||getClassByFileType(fileName)=="fileHtml"||getClassByFileType(fileName)=="fileExcel"||getClassByFileType(fileName)=="fileWord"||getClassByFileType(fileName)=="filePPT")
	{
	       //如果传的是附件的id则调用此url
			url= basePath+"/filemanager/htmlPreview.action?attachId="+attachmentId;
			
			if (null == attachmentId || '' == attachmentId){
			   //  如果传的是文件的路径调用此方法
			   url= basePath+"/filemanager/htmlPreview.action?attachFile="+attachPath+"&attachName="+encodeURIComponent(fileName);
			   attachmentId = attachPath;
			}
		 
			if (isShowFileName == false){
				fileName = ''
			}
				
		    html = "<span ";
		    
		    if (isShowFileName != false){
		    	html = html	+"class='filesIco'"
		    }
		    html = html +">"
			+ fileName
			+ "<a  href='javascript:void(0)' onclick='downAttachment(\""+url+"\")'>预览</a>";
			
			if (isShowDelete != false){
				html = html	+ "<a  href='javascript:void(0)' onclick='deleteAttachment(\""
				+ attachmentId + "\",this)'>&nbsp;删除</a>" + "</span></p>";
			}
			
		
		 
	}else if(getClassByFileType(fileName)=="filePicture")
	{
		
		// 传递附件Id或者附件所在的物理路径
		if (null == attachmentId){
			url= basePath+"filemanager/downview.action?attachPath="+attachPath+"&attachName="+fileName;
			attachmentId = attachPath;
		}else{
			url= basePath+"filemanager/downview.action?attachmentId="+attachmentId;
		}
		if (isShowFileName == false){
			fileName = ''
		}
		 html = "<span ";
		    
		    if (isShowFileName != false){
		    	html = html	+"class='filesIco'"
		    }
		html = html +">"
			+ fileName
			+ "<a  href='javascript:void(0)' onclick='downAttachment(\""+url+"\")'>预览</a>"
			
	    if (isShowDelete != false){
	    	html = html	+"<a  href='javascript:void(0)' onclick='deleteAttachment(\""
			+ attachmentId + "\",this)'>删除</a>" + "</span></p>";
	    }
	}
	else /*if(getClassByFileType(fileName)=="fileRar"||getClassByFileType(fileName)=="filePPT"||getClassByFileType(fileName)=="fileYinyue")*/
	{
		if (null == attachmentId){
			attachmentId = attachPath;
		}
		if (isShowFileName == false){
			fileName = ''
		}
		 html = "<span ";
		    
		    if (isShowFileName != false){
		    	html = html	+"class='filesIco'"
		    }
		html = html +">"
			+ fileName;
		
		if (isShowDelete != false){
	    	html = html	+"<a  href='javascript:void(0)' onclick='deleteAttachment(\""
			+ attachmentId + "\",this)'>删除</a>" + "</span></p>";
	    }
	}
	
	return html;
}

/**
 * 需要开发的页面
 */
 function downAttachment(url){
 	window.open(url);
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
 * 文件大小转换，字节量转成MB或KB
 * @param byteSize 文件字节量
 * @return
 */
function convertFileSize(byteSize) {
    if (byteSize > 0) {
        //return (byteSize / 1024).toFixed(2) + "KB约(" + (byteSize / 1024/1024).toFixed(2) + "MB)";
        return (byteSize / 1024).toFixed(2) + "KB";
    } else {
        return "";
    }
}

/**
 *下载指定的文件
 * @param filePath 文件路径
 * @param fileName 文件名称
 */
function downloadFile(filePath, fileName) {
    var downloadURL = basePath + "filemanager/downFileByFilePath.action?filePath=" + filePath + "&fileName=" + fileName;
    window.open(downloadURL);
}

/**
 *根据文件类型获取对应class
 * @param type 文件类型
 * @return {string} class名称
 */
function getClassByFileTypeOwn(type) {
    if (type.indexOf(".") != -1) {
        type = type.substr(1, type.length);
    }
    type = type.toLocaleLowerCase();
    var defaultType = {txt: "txt", doc: "doc", ppt: "ppt", excel: "excel", img: "img", rar: "rar"};
    switch (type) {
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
            return defaultType.txt;
    }
}

function otherInfo(){
	var userId = $("#userId").val();
	var url = basePath + "logined/record/otherInfo.jsp?userId="+userId;
	art.dialog.open(url,
	        {	id : "otherInfo",
	            title:"相关信息",
	            width : 1000,
	            height : 500,
			    opacity: 0.08,
			    lock:true,
			    close:function(){
			    	return true;
			    },
			    button: [
			             {
			            	 name: '关闭',
			            	 callback:function(){
			            		 return true;
			            	 }
			             }
			             ]
	        });
}