
/**
 * 统计附件总大小并存入Hidden域中
 */
function calcAttachmentSize() {
	var totalSize = Attachment.getTotalSizeByJson($("#attachment").val());
	// alert("附件总大小：" + totalSize);
	$("#attachmentSize").val(totalSize);
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
        return "&nbsp;";
    }
}

/**
 * 文件后缀名判断的函数 根据不同的后缀名称 进行相应的处理
 * 
 * @param {}
 *            type
 * @return {}
 */
function getClassByFileType(type) {
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

var Attachment = {
		/**
		 * 需要开发的页面
		 */
		downAttachment: function (url){
		 	window.open(url);
		},
	/**
	 * 获取附件JSON字符串中所有附件的总大小
	 * 
	 * @param jsonListVal
	 *            附件字符串
	 * @return {number} 总大小，byte为单位
	 */
	getTotalSizeByJson : function(jsonListVal) {
		var size = 0;
		if (typeof (jsonListVal) != "undefined" && jsonListVal != "") {
			jsonListVal = JSON.parse(jsonListVal);
			for ( var i = 0; i < jsonListVal.length; i++) {
				size += parseInt(jsonListVal[i]["size"], 10);
			}
		}
		return size;
	},
	deleteAttachment : function(filePath, domAObj) {
		var attachmentVal = $("#attachment").val();
		attachmentVal = this.deleteAttachmentByPath(filePath, attachmentVal);
		$("#attachment").val(attachmentVal);
		calcAttachmentSize();
		$(domAObj).parent().parent().parent().remove();
	},
	/**
	 * 删除一个附件
	 * 
	 * @param path
	 *            要删除的附件的path
	 * @param jsonListVal
	 *            附件JSON串
	 */
	deleteAttachmentByPath : function(path, jsonListVal) {
		var result = "";
		if (typeof (jsonListVal) != "undefined" && jsonListVal != "") {
			jsonListVal = JSON.parse(jsonListVal);
			for ( var i = 0; i < jsonListVal.length; i++) {
				var jsonVal = jsonListVal[i];
				// alert(jsonVal["path"]);
				if (jsonVal["path"] != path) {
					result += JSON.stringify(jsonVal);
					result += ",";
				}
			}
		}
		if (result.indexOf(",") != -1) {
			result = result.substr(0, result.length - 1);
		}
		if (result != "") {
			result = "[" + result + "]";
		}
		// alert("删除后:" + result);
		return result;
	},

	/**
	 * 功能:邮件附件处理相关js方法类,需要引入json2.js支持 版本:1.0 说明：附件JSON串格式
	 * [{"name":"xxxxxx.xls","path":"/ddd/ccc/asdfasdfa.xls","size":"454645"},
	 * {"name":"xxxxxx222.xls","path":"/ddd/ccc/asdfasdfa2222.xls","size":"454645222"}]
	 */

	/**
	 * 添加一个附件
	 * 
	 * @param path
	 *            附件路径
	 * @param name
	 *            附件名称
	 * @param size
	 *            附件大小
	 * @param jsonListVal
	 *            附件JSON串
	 */
	addAttachment : function(path, name, size, jsonListVal) {

		// 把新的附件信息构造成一个Map
		var newJson = {
			"path" : path,
			"name" : name,
			"size" : size
		};
		var result = "";
		if (typeof (jsonListVal) != "undefined" && jsonListVal != "") {
			jsonListVal = JSON.parse(jsonListVal);
			for ( var i = 0; i < jsonListVal.length; i++) {
				result += JSON.stringify(jsonListVal[i]);
				result += ",";
			}
		}
		result += JSON.stringify(newJson);
		result += ",";
		result = result.substr(0, result.length - 1);
		result = "[" + result + "]";
		// alert("新增后:" + result);
		return result;
	},

	/**
	 * 不用插件的附件预览功能的方法
	 * 
	 * @param {}
	 *            fileName
	 * @param {}
	 *            attachmentId
	 * @param {}
	 *            attachPath
	 * @return {}
	 * 
	 * 
	 * <li> <div class="icon"> <em class="doc"></em> </div> <div class="txt">
	 * <p>
	 * Resume Liu Yang.docx
	 * </p>
	 * <p>
	 * <a>预览</a><a>删除</a>
	 * </p>
	 * </div>
	 * <p class="clear">
	 * </p>
	 * </li>
	 * 
	 */
	createNewOneAttachmentHTML : function(fileName, attachmentId, attachPath,
			isShowFileName, isShowDelete) {

		var url = "";
		var html = "";
		html = '<li><div class="icon"> <em class="';
		html += getClassByFileType(fileName) + '" ></em></div> ';
		html += '<div class="txt"><p>' + fileName + '</p><p>'
		if (getClassByFileType(fileName) == "txt"
				|| getClassByFileType(fileName) == "txt"
				|| getClassByFileType(fileName) == "excel"
				|| getClassByFileType(fileName) == "doc"
				|| getClassByFileType(fileName) == "ppt") {
			// 如果传的是附件的id则调用此url
			url = basePath + "/filemanager/htmlPreview.action?attachId="
					+ attachmentId;

			if (null == attachmentId || '' == attachmentId) {
				// 如果传的是文件的路径调用此方法
				url = basePath + "/filemanager/htmlPreview.action?attachFile="
						+ attachPath + "&attachName="
						+ encodeURIComponent(fileName);
				attachmentId = attachPath;
			}

			html += "<a  href='javascript:void(0)' onclick='Attachment.downAttachment(\""
					+ url + "\")'>预览</a>";

			if (isShowDelete != false) {
				html = html
						+ "<a  href='javascript:void(0)' onclick='Attachment.deleteAttachment(\""
						+ attachmentId + "\",this)'>&nbsp;删除</a>" + "</p>";
			}
		} else if (getClassByFileType(fileName) == "img") {

			// 传递附件Id或者附件所在的物理路径
			if (null == attachmentId) {
				url = basePath + "filemanager/downview.action?attachPath="
						+ attachPath + "&attachName=" + fileName;
				attachmentId = attachPath;
			} else {
				url = basePath + "filemanager/downview.action?attachmentId="
						+ attachmentId;
			}

			html += "<a  href='javascript:void(0)' onclick='Attachment.downAttachment(\""
					+ url + "\")'>预览</a>"

			if (isShowDelete != false) {
				html = html
						+ "<a  href='javascript:void(0)' onclick='Attachment.deleteAttachment(\""
						+ attachmentId + "\",this)'>删除</a>" + "</span></p>";
			}
		} else /* if(getClassByFileType(fileName)=="fileRar"||getClassByFileType(fileName)=="filePPT"||getClassByFileType(fileName)=="fileYinyue") */
		{
			if (null == attachmentId) {
				attachmentId = attachPath;
			}
			if (isShowFileName == false) {
				fileName = ''
			}

			if (isShowDelete != false) {
				html = html
						+ "<a  href='javascript:void(0)' onclick='Attachment.deleteAttachment(\""
						+ attachmentId + "\",this)'>删除</a>" + "</span></p>";
			}
		}
		return html + '<p class="clear"></p>';
	},
	// init html
	initAttachmentHTML : function(attachmentSourceId, attachUlid) {
		var jsonList = $('#' + attachmentSourceId).val();
		if (jsonList != "") {
			jsonList = JSON.parse(jsonList);
			for ( var i = 0; i < jsonList.length; i++) {
				var json = jsonList[i];

				var html = this.createNewOneAttachmentHTML(json["name"], null,
						json["path"]);
				$('#' + attachUlid).append(html);
				window.parent.frameResize();
			}
		}
	},
	// add one attachment
	appendOneAttachmentHTML : function(fileName, filePath) {
		var html = this.createNewOneAttachmentHTML(fileName, null, filePath,
				true, true);
		$('#attachUL').append(html);
		window.parent.frameResize();
	},

	onUploadSuccess : function(file, data) {
		this.appendOneAttachmentHTML(file.name, data);
		var attachmentVal = $("#attachment").val();
		attachmentVal = this.addAttachment(data, file.name, file.size,
				attachmentVal);

		$("#attachment").val(attachmentVal);
		var subject = $("#subject").val();
		subject = $.trim(subject);
		if (subject == "") {
			$("#subject").val(file.name);
		}
		calcAttachmentSize();
	},
	/**
	 *初始化附件下载的HTML内容
	 */
	initAttachmentDownloadHTML:function () {
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
	        var cls = getClassByFileType(type);
	        var size = convertFileSize(jsonMap["size"]);
	        html += "<li>";
	        html += "<div class='icon'><em class='" + cls + "'></em></div>";
	        html += "<div class='txt'>";
	        html += "<p>" + name + "(" + size + ")" + "</p>";
	        html += "<p><a href='javascript:void(0)' onclick='downloadFile(\"" + path + "\",\"" + name + "\")'>下载</a>&nbsp;";
	        html += createNewOneAttachmentHTML(name, null, path, false, false);
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

};
