$(document).ready(function () {
	qytx.app.fileupload({
		id	:	"file_upload",
		moduleName:	"record_file",
		ulName:	"attachUL",
		callback:	function(data){
			var attachmentVal = $("#attachment").val();
            attachmentVal = addAttachment(data.attachFile, data.attachName, data.attachSize, attachmentVal);
            $("#attachment").val(attachmentVal);
		},
		deleteFun:	function(id,path){
			var attachmentVal = $("#attachment").val();
            attachmentVal = deleteAttachmentByPath(path,attachmentVal);
            $("#attachment").val(attachmentVal);
		}
	});
});

/**
 * 添加一个附件
 * @param path 附件路径
 * @param name 附件名称
 * @param size 附件大小
 * @param jsonListVal 附件JSON串
 */
function addAttachment(path, name, size, jsonListVal) {

    //把新的附件信息构造成一个Map
    var newJson = {"path": path, "name": name, "size": size};
    var result = "";
    if (typeof(jsonListVal) != "undefined" && jsonListVal != "") {
        jsonListVal = JSON.parse(jsonListVal);
        for (var i = 0; i < jsonListVal.length; i++) {
            result += JSON.stringify(jsonListVal[i]);
            result += ",";
        }
    }
    result += JSON.stringify(newJson);
    result += ",";
    result = result.substr(0, result.length - 1);
    result = "[" + result + "]";
    return result;
}

/**
 * 删除一个附件
 * @param path 要删除的附件的path
 * @param jsonListVal 附件JSON串
 */
function deleteAttachmentByPath(path, jsonListVal) {
    var result = "";
    if (typeof(jsonListVal) != "undefined" && jsonListVal != "") {
        jsonListVal = JSON.parse(jsonListVal);
        for (var i = 0; i < jsonListVal.length; i++) {
            var jsonVal = jsonListVal[i];
            if (jsonVal["path"] != path) {
                result += JSON.stringify(jsonVal);
                result += ",";
            }
        }
    }
    if (result.indexOf(",") != -1) {
        result = result.substr(0, result.length - 1);
    }
    if(result!=""){
        result = "[" + result + "]";
    }
    return result;
}

/**
 * 生成一个附件的HTML内容供给显示
 * @param fileName 文件名称
 * @param filePath 文件路径
 */
function createRecordAttachmentHTML(fileName, filePath) {
    var html = createNewOneAttachmentHTML(fileName, null, filePath);
    $('#mailAttachmentTd').append(html);
    $('#fileAttachTd').append(html);
}

function deleteAttachment(filePath, domAObj) {
    var attachmentVal = $("#attachment").val();
    attachmentVal = deleteAttachmentByPath(filePath, attachmentVal);
    $("#attachment").val(attachmentVal);
    $(domAObj).parent().remove();
}


/**
 * 修改时初始化附件的HTML;
 */
function initAttachmentHTML() {
    var jsonList = $("#attachment").val();
    if (jsonList != "") {
        jsonList = JSON.parse(jsonList);
        for (var i = 0; i < jsonList.length; i++) {
            var json = jsonList[i];
            createRecordAttachmentHTML(json["name"], json["path"]);
        }
    }
}