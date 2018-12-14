var  attachList = new Array();
$(document).ready(function() {
    $("#file_upload").uploadify({
        //和存放队列的DIV的id一致
        'queueID':'queue',
        //服务器端脚本使用的文件对象的名称 $_FILES个['upload']
        'fileObjName':'fileupload',
        //上传处理程序
        'uploader' : basePath + 'filemanager/uploadfile.action?module=workflow',
        //按钮文字
        'buttonText' : '上传附件...',
        //浏览按钮的背景图片路径
        'buttonImage':basePath+'flat/images/upload.png',
        //取消上传文件的按钮图片，就是个叉叉
        'cancel': basePath+'plugins/upload/upbutton.png',
        //浏览按钮的宽度
        'width':'51',
        //浏览按钮的高度
        'height':'17',
        //在浏览窗口底部的文件类型下拉菜单中显示的文本
        'fileTypeDesc':'支持的格式:',
        //允许上传的文件后缀
        'fileTypeExts':'*.jpg;*.jpeg;*.gif;*.png;*.xls;*.doc;*.docx;*.ppt;*.txt',
        //上传文件的大小限制
        'fileSizeLimit':'10MB',
        //上传数量
        'queueSizeLimit' : 25,
        //开启调试
        'debug' : false,
        //是否自动上传
        'auto':true,
        //上传后是否删除
        'removeComplete':false,
        //清除
        removeTimeout : 0,
        langFile:basePath+'plugins/upload/ZH_cn.js',
        //超时时间
        'successTimeout':99999,
        //flash
        'swf': basePath+'plugins/upload/uploadify.swf',
        //不执行默认的onSelect事件
        'overrideEvents' : ['onDialogClose'],
        //每次更新上载的文件的进展
        'onUploadProgress' : function(file, bytesUploaded, bytesTotal, totalBytesUploaded, totalBytesTotal) {
        },
        //选择上传文件后调用
        'onSelect' : function(file) {
        	//onSelectHtml(file, "appendixList", "file_upload");
            return true;
        },
        //返回一个错误，选择文件的时候触发
        'onSelectError':function(file, errorCode, errorMsg){
        	 switch (errorCode) {
             case -100:
             	art.dialog.alert("上传的文件数量已经超出系统限制的" + $('#file_upload').uploadify('settings', 'queueSizeLimit') + "个文件！");
                 break;
             case -110:
             	art.dialog.alert("文件 [" + file.name + "] 大小超出系统限制的" + $('#file_upload').uploadify('settings', 'fileSizeLimit') + "大小！");
                 break;
             case -120:
             	art.dialog.alert("文件 [" + file.name + "] 大小异常！");
                 break;
             case -130:
             	art.dialog.alert("文件 [" + file.name + "] 类型不正确！");
                 break;
         }
        },
        //检测FLASH失败调用
        'onFallback':function(){
        	art.dialog.alert("您未安装FLASH控件，无法上传图片！请安装FLASH控件后再试。");
        },
        //上传到服务器，服务器返回相应信息到data里
        'onUploadSuccess':function(file, data, response){
        	if(data.indexOf('filenametoolong')>=0){
        		art.dialog.alert("文件名过长！");
        		return;
        	}
        	var data= eval("("+data+")");
        	onUploadSuccessHelp(file.name,data.id);
        	attachList.push(data.id);
        },
        //上传前取消文件
        'onCancel' : function(file) {
            // alert('The file ' + file.name + ' was cancelled.');
        }
    });
});

function onUploadSuccessHelp(fileName,attachId){
	var type = getClassByFileTypeApply(fileName);
	var li = $("<li>");
	var divImg = $("<div>",{
		"class":"icon"
	}).append("<em class='"+type+"'>");
	var divContain = $("<div>",{
		"class":"txt"
	});
	$(divContain).append("<p>"+fileName+"</p>");
	
	var pLink = $("<p>");
	var viewLink = $("<a>",{
		"text":"下载",
		"href":"javascript:void(0);",
		"click":function(){
			window.open(basePath+ 'filemanager/downfile.action?attachmentId='+attachId);
		}
	});
	var deleteLink = $("<a>",{
		"text":"删除",
		"href":"javascript:void(0);",
		"click":function(){
			$(this).parent().parent().parent().remove();
			deleteAttach(attachId);
		}
	});
	$(pLink).append(viewLink);
	$(pLink).append(deleteLink);
	$(divContain).append(pLink);
	
	li = $(li).append(divImg).append(divContain);
	$("div.annex > ul").append(li);
}



function deleteAttach(attachId){
	var index = 0 ;
	for(var i =0; i<attachList.length; i++){
		if(attachList[i] == attachId){
			index = i;
		}
	}
	attachList.splice(index, 1);
}


function getClassByFileTypeApply(type) {
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

