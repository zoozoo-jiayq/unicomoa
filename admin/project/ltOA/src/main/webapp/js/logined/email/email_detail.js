/**
 * 功能:邮件详情页面相关脚本
 * 版本:1.0
 */

/**
 *初始化功能按钮
 */
var allowBack = true;
function initBtnMethod() {
    $(".meil_btn").hide();//全部隐藏掉功能区域
    $(".meil_search_area").show();
    var hideBtn = $("#hideBtn").val();
    var allowBack = window.history.length > 1 && hideBtn.indexOf("returnBtn") == -1;

    if (allowBack) {
        //返回按钮
        $(".returnBtn").click(function () {
        	var type = $("#type").val();
        	if(type && type=="affairs"){
        		window.location.href = basePath + "logined/email/emailToListPage.action";
        	}else{
        		window.history.go(-1);
        	}
        }).parent().show();
    }

    var from = $("#from").val();
    var emailToId = $("#emailToId").val();
    var emailBodyId = $("#emailBodyId").val();
    if (from == "emailTo") {
        $(".replyBtn").click(function () {
            var url = basePath + "logined/email/emailToReply.action?emailToId=" + emailToId;
            openURLForEmail(url);
        }).parent().show();
        $(".replyAllBtn").click(function () {
            var url = basePath + "logined/email/emailToReply.action?emailToId=" + emailToId + "&replyAll=1";
            openURLForEmail(url);
        }).parent().show();
        $(".forwardBtn").click(function () {
            var url = basePath + "logined/email/emailToForward.action?emailToIds=" + emailToId;
            openURLForEmail(url);
        }).parent().show();
        $(".prevBtn").click(function () {
        	getPrevEmail();//获得上一封邮件
        }).parent().show();
        $(".nextBtn").click(function () {
        	getNextEmail();//获得下一封邮件
        }).parent().show();
        $(".deleteBtn").click(function () {
            qytx.app.dialog.confirm(sprintf("email.confirm_delete_this_email"), function () {
            	qytx.app.ajax({
                    url: basePath + "logined/email/emailToDelete.action",
                    type: "post",
                    dataType: 'text',
                    data: {
                        emailToIds: emailToId
                    },
                    success: function (data) {
                    	qytx.app.dialog.alert(data);
                        if (allowBack) {
                        	window.location.href = document.referrer;
                        } else {
                        	var url = window.top.location + "";
                        	// 新页面打开时,删除邮件时框架嵌套问题
                        	if (url.indexOf("indexFix.jsp")>0){
                        		window.parent.location.href = basePath+"logined/email/emailMainPage.action";
                         		//openURLForEmail(basePath+"logined/email/emailMainPage.action");
                        	}else{
                        		window.parent.location=basePath+"logined/email/emailMainPage.action";
                        	}
                        }
                    }
                });
            }, function () {

            })
        }).parent().show();

        $(".destroyBtn").click(function () {
        	qytx.app.dialog.confirm(sprintf("email.confirm_destroy_this_email"), function () {
                //删除选中的记录ajax
        		qytx.app.ajax({
                    url: basePath + "logined/email/emailToDestroy.action",
                    type: "post",
                    dataType: 'text',
                    data: {
                        emailToIds: emailToId
                    },
                    success: function (data) {
                    	qytx.app.dialog.alert(data);
                    	
                    	 if (allowBack) {
                         	window.location.href = document.referrer;
                         } else {
                         	var url = window.top.location + "";
                         	// 新页面打开时,删除邮件时框架嵌套问题
                         	if (url.indexOf("indexFix.jsp")>0){
                         		window.parent.location.href = basePath+"logined/email/emailMainPage.action";
                         		//openURLForEmail(basePath+"logined/email/emailMainPage.action");
                         	}else{
                         		window.parent.location=basePath+"logined/email/emailMainPage.action";
                         	}
                         }
                    }
                });
            }, function () {

            })
        }).parent().show();
    }
    if (from == "emailToWastebasket") {

        $(".recoveryBtn").click(function () {
        	qytx.app.dialog.confirm(sprintf("email.confirm_recovery_this_email"), function () {
                //删除选中的记录ajax
        		qytx.app.ajax({
                    url: basePath + "logined/email/emailToRecovery.action",
                    type: "post",
                    dataType: 'text',
                    data: {
                        emailToIds: emailToId
                    },
                    success: function (data) {
                    	qytx.app.dialog.alert(data);
                        if (allowBack) {
                        	window.location.href = document.referrer;//返回列表页面
                        } else {
                            openURLForEmail(basePath+"logined/email/emailMainPage.action");
                        }
                    }
                });
            }, function () {

            })
        }).parent().show();

        $(".destroyBtn").click(function () {
        	qytx.app.dialog.confirm(sprintf("email.confirm_destroy_this_email"), function () {
                //删除选中的记录ajax
        		qytx.app.ajax({
                    url: basePath + "logined/email/emailToDestroy.action",
                    type: "post",
                    dataType: 'text',
                    data: {
                        emailToIds: emailToId
                    },
                    success: function (data) {
                    	qytx.app.dialog.alert(data);
                        if (allowBack) {
                        	window.location.href = document.referrer;
                        } else {
                            openURLForEmail(basePath+"logined/email/emailMainPage.action");
                        }
                    }
                });
            }, function () {

            })
        }).parent().show();
    }
    if (from == "emailBody") {
        $("#sendMessageA").hide();
        $(".viewReadStatus").text("查看邮件状态");
        $(".againSendBtn").click(function () {
            var url = basePath + "/logined/email/emailBodyAgainSend.action?emailBodyId=" + emailBodyId;
            openURLForEmail(url);
        }).parent().show();
        $(".forwardBtn").click(function () {
            var url = basePath + "/logined/email/emailBodyForward.action?emailBodyId=" + emailBodyId;
            openURLForEmail(url);
        }).parent().show();

        $(".prevBtn").click(function () {
        	getPrevEmail();//获得上一封邮件
        }).parent().show();
        $(".nextBtn").click(function () {
        	getNextEmail();//获得下一封邮件
        }).parent().show();
        
        $(".deleteBtn").click(function () {
        	qytx.app.dialog.confirm(sprintf("email.confirm_delete_this_email") + "\n" + sprintf("email.alert_delete_not_read_email"), function () {
                //删除选中的记录ajax
        		qytx.app.ajax({
                    url: basePath + "logined/email/emailBodyDelete.action",
                    type: "post",
                    dataType: 'text',
                    data: {
                        emailBodyIds: emailBodyId
                    },
                    success: function (data) {
                    	qytx.app.dialog.alert(data);
                        if (allowBack) {
                        	window.location.href = document.referrer;//返回列表页面
                        } else {
                            openURLForEmail(basePath+"logined/email/emailMainPage.action");
                        }
                    }
                });
            }, function () {

            })
        }).parent().show();
    }
}

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
        html += "<p><a href='javascript:void(0)' onclick='downloadFile(\"" + path + "\",\"" + name + "\")'>下载</a>&nbsp;";
        html += createEmailNewOneAttachmentHTML(name, null, path, false, false);
        html += "&nbsp;</p></div>";
        html += "<p class='clear'></p>";
        html += "</li>"
    }
    if (count < 2) {
        $("#downloadAllFileA").hide();
    }
    $("#attachmentCountSpan").html(count);
    jObjAttachmentDiv.find("ul").html(html);
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
 *下载指定的文件
 * @param filePath 文件路径
 * @param fileName 文件名称
 */
function downloadFile(filePath, fileName) {
    var downloadURL = basePath + "filemanager/downFileByFilePath.action?filePath=" + filePath + "&fileName=" + fileName;
    window.open(downloadURL);
}

createNewOneAttachmentHTML

/**
 *下载所有附件
 */
function downloadAllFile() {
    var downloadURL = basePath + "filemanager/downZipFileByFilesJson.action?filesJson=" + $.trim($("#attachment").val());
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

/**
 * 初始化查阅状态按钮的事件
 */
function initReadStatus() {
    $(".viewReadStatus").click(function () {
        showReadStatus($("#emailBodyId").val(), $("#from").val());
    }).hide().eq(-1).show();
}

$(document).ready(function () {
    initReadStatus();
    initBtnMethod();
    initAttachmentDownloadHTML();
    window.parent.frameResize()
});

/**
 * 获得上一封邮件
 */
function getPrevEmail(){
	if($("#prevFlag").val()!="1"){
		qytx.app.dialog.alert("已经是第一封邮件！");
		return false;
	}
	var url = basePath + "logined/email/emailDetail.action?emailBodyId="+$("#prevEmailBodyId").val()+"&emailToId="+$("#prevEmailToId").val()+"";
	openURLForEmail(url);
}

/**
 * 获得下一封邮件
 */
function getNextEmail(){
	if($("#nextFlag").val()!="1"){
		qytx.app.dialog.alert("已经是最后一封邮件！");
		return false;
	}
	var url = basePath + "logined/email/emailDetail.action?emailBodyId="+$("#nextEmailBodyId").val()+"&emailToId="+$("#nextEmailToId").val()+"";
	openURLForEmail(url);
}


