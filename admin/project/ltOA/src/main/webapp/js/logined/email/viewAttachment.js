$(document).ready(function() {
	 
		//	downNotify();
		})


/**
 * 初始化
 *//*
 function downNotify(){
	

	var url =basePath+ "upload/downfile.action?attachmentId="+ 947;
	intializePage(url);  //读取文件，如果没有保存，则读取默认页
}*/
		
		
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
 * 
 * @param {} type
 * @return {}
 */
function getCByFileType(type) {
	if (type.lastIndexOf(".") != -1) {
		type = type.substr(type.lastIndexOf(".") + 1, type.length);
	}
	type = type.toLocaleLowerCase();
	var defaultType = {
		txt : "txt",
		doc : "doc",
		ppt : "ppt",
		excel : "excel",
		img : "img",
		rar : "rar",
		html : "txt",
		yinyue : "txt"
	};
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
	case "js":
	case "html":
	case "jsp":
		return defaultType.html;
	case "mp3":
	case "mp4":
	case "avi":
	case "rmvb":
		return defaultType.yinyue;
	default:
		return defaultType.txt;
	}
}
/**
 * 根据不用的后缀名 做预览的处理
 * @param {} fileName
 * @param {} attachmentId
 * @param {} attachPath 附件保存的物理路径
 * @return {}
 */



function createOneAttachmentHTML(fileName, attachmentId, attachPath) {
	var url="";
	var html = "";
	
	if(getClassByFileType(fileName)=="fileTxt"||getClassByFileType(fileName)=="filePicture"||getClassByFileType(fileName)=="fileHtml")
	{
		// 传递附件Id或者附件所在的物理路径
		if (null == attachmentId){
			url= basePath+"filemanager/downview.action?attachPath="+attachPath+"&attachName="+fileName;
			attachmentId = attachPath;
		}else{
			url= basePath+"filemanager/downview.action?attachmentId="+attachmentId;
		}
    	
		html = "<span class='filesIco'>"
			+ fileName
			+ "<a  href='javascript:void(0)' onclick='downAttachment(\""+url+"\")'>预览</a><a  href='javascript:void(0)' onclick='deleteAttachment(\""
			+ attachmentId + "\",this)'>删除</a>" + "</span></p>";
		
		 
	}
	else if(getClassByFileType(fileName)=="fileExcel"||getClassByFileType(fileName)=="fileWord")
	{
		if (null == attachmentId){
			url= basePath+"filemanager/viewfile.action?attachPath="+attachPath+"&attachName="+fileName;
			attachmentId = attachPath;
		}else{
			url= basePath+"filemanager/viewfile.action?attachmentId="+attachmentId;
		}
		html = "<span class='filesIco'>"
			+ fileName
			+ "<a  href='javascript:void(0)' onclick='viewAttachment(\""+url+"\")'>预览</a><a  href='javascript:void(0)' onclick='deleteAttachment(\""
			+ attachmentId + "\",this)'>删除</a>" + "</span></p>";
		
	}
	
	
	else /*if(getClassByFileType(fileName)=="fileRar"||getClassByFileType(fileName)=="filePPT"||getClassByFileType(fileName)=="fileYinyue")*/
	{
		if (null == attachmentId){
			attachmentId = attachPath;
		}
		html = "<span class='filesIco'>"
			+ fileName
			+ "<a  href='javascript:void(0)' onclick='deleteAttachment(\""
			+ attachmentId + "\",this)'>删除</a>" + "</span></p>";
	}
	
	return html;
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
				url= basePath+"filemanager/downview.action?attachPath="+attachPath+"&attachName="+fileName;
				attachmentId = attachPath;
			}else{
				url= basePath+"filemanager/downview.action?attachmentId="+attachmentId;
			}
	    	
			html = "<a  href='javascript:void(0)' onclick='downAttachment(\""+url+"\")'>预览</a>";
			 
		}
		else if(getClassByFileType(fileName)=="fileExcel"||getClassByFileType(fileName)=="fileWord")
		{
			if (null == attachmentId){
				url= basePath+"filemanager/viewfile.action?attachPath="+attachPath+"&attachName="+fileName;
				attachmentId = attachPath;
			}else{
				url= basePath+"filemanager/viewfile.action?attachmentId="+attachmentId;
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
 * 不用插件的附件预览功能的方法
 * @param {} fileName
 * @param {} attachmentId
 * @param {} attachPath
 * @return {}
 */
function createNewOneAttachmentHTML(fileName, attachmentId, attachPath, isShowFileName, isShowDelete) {
	
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
		 
			html = "<li><div class='icon'> <em class='"+getCByFileType(fileName)+"'></em> </div> <div class='txt'><p>"+fileName+"</p><p><a  href='javascript:void(0)' onclick='downAttachment(\""+url+"\")'>预览</a><a  href='javascript:void(0)' onclick='deleteAttachment(\""
			+ attachmentId + "\",this)'>删除</a>" + "</p> <p class='clear'></p></li>";
			
		
		 
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
	html = "<li><div class='icon'> <em class='"+getCByFileType(fileName)+"'></em> </div> <div class='txt'><p>"+fileName+"</p><p><a  href='javascript:void(0)' onclick='downAttachment(\""+url+"\")'>预览</a><a  href='javascript:void(0)' onclick='deleteAttachment(\""
			+ attachmentId + "\",this)'>删除</a>" + "</p> <p class='clear'></p></li>";
	}
	else /*if(getClassByFileType(fileName)=="fileRar"||getClassByFileType(fileName)=="filePPT"||getClassByFileType(fileName)=="fileYinyue")*/
	{
	html = "<li><div class='icon'> <em class='"+getCByFileType(fileName)+"'></em> </div> <div class='txt'><p>"+fileName+"</p><p><a  href='javascript:void(0)' onclick='deleteAttachment(\""
			+ attachmentId + "\",this)'>删除</a>" + "</p> <p class='clear'></p></li>";
	}
	
	return html;
}




/**
 * 不用插件的附件预览功能的方法
 * @param {} fileName
 * @param {} attachmentId
 * @param {} attachPath
 * @return {}
 */
function createNewOneAttachmentHTMLCopy(fileName, attachmentId, attachPath, isShowFileName, isShowDelete) {
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
 * 不用插件的附件预览功能的方法
 * @param {} fileName
 * @param {} attachmentId
 * @param {} attachPath
 * @return {}
 * 
 * 
 * <li>
        <div class="icon"> <em class="doc"></em> </div>
        <div class="txt">
          <p>Resume Liu Yang.docx</p>
          <p><a>预览</a><a>删除</a></p>
        </div>
        <p class="clear"></p>
      </li>
      
 */
function appendNewOneAttachmentHTML(fileName, attachmentId, attachPath, isShowFileName, isShowDelete) {

	var url="";
	var html = "";
    html = '<li><div class="icon"> <em class="';
    html += getClassByFileTypeOwn(fileName) +'" ></em></div> ';
    html += '<div class="txt"><p>' + fileName + '</p><p>'
	if(getClassByFileType(fileName)=="fileTxt"||getClassByFileType(fileName)=="fileHtml"
		||getClassByFileType(fileName)=="fileExcel"||getClassByFileType(fileName)=="fileWord"
		||getClassByFileType(fileName)=="filePPT")
	{
	       //如果传的是附件的id则调用此url
			url= basePath+"/filemanager/htmlPreview.action?attachId="+attachmentId;
			
			if (null == attachmentId || '' == attachmentId){
			   //  如果传的是文件的路径调用此方法
			   url= basePath+"/filemanager/htmlPreview.action?attachFile="+attachPath+"&attachName="+encodeURIComponent(fileName);
			   attachmentId = attachPath;
			}

		    html += "<a  href='javascript:void(0)' onclick='downAttachment(\""+url+"\")'>预览</a>";
			
			if (isShowDelete != false){
				html = html	+ "<a  href='javascript:void(0)' onclick='deleteAttachment(\""
				+ attachmentId + "\",this)'>&nbsp;删除</a>" + "</p>";
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

		html += "<a  href='javascript:void(0)' onclick='downAttachment(\""+url+"\")'>预览</a>"
			
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
		
		if (isShowDelete != false){
	    	html = html	+"<a  href='javascript:void(0)' onclick='deleteAttachment(\""
			+ attachmentId + "\",this)'>删除</a>" + "</span></p>";
	    }
	}	
	return html + '<p class="clear"></p>';	
}

/**
 *根据文件类型获取对应class
 * @param type 文件类型
 * @return {string} class名称
 */
function getClassByFileTypeOwn(type) {
    if (type.lastIndexOf(".") != -1) {
        type = type.substr(1, type.length);
    }else{
    	return defaultType.txt;
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
 