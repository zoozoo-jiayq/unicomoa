//页面加载完成后调用的方法，对页面一些内容进行初始化
$(document).ready(function () {
    initToNameHTML();
    initAttachmentDownloadHTML();
    initBtnClick();
    initBackToList();
});

//初始化显示收件人的HTML效果
function initToNameHTML() {
    $(".div_txt[toName]").each(function () {
        var divObj = $(this);
        var toName = divObj.attr("toName");
        toName = toName.split(",");
        $.each(toName, function (index, value) {
            divObj.append("<em class='memberList'>" + value + "</em>");
        });
    });
}
//初始化返回按钮单击事件
function initBackToList() {
    var from = $.trim($("#from").val());
    var showTab = "outBox";
    if (from == "emailTo") {
        showTab = "inBox";
    }
    $(".btnBack").click(function () {
        window.location = basePath + "wap/logined/email/list.jsp"+wapParam+"&showTab=" + showTab;
    });
}

//初始化“转发”，“回复”单击事件
function initBtnClick() {
    $(".forwardBtn").click(function () {
        window.location = basePath + "logined/email/emailForwardForWap.action"+wapParam+"&emailBodyId=" + $("#emailBodyId").val()+"&random="+Math.random();
    });
    $(".replyBtn").click(function () {
        window.location = basePath + "logined/email/emailReplyForWap.action"+wapParam+"&emailToId=" + $("#emailToId").val()+"&random="+Math.random();
    });
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
        path = encodeURI(path);
        html += "<li><a href='javascript:void(0)' class='btnDown fr' onclick='downloadFile(\"" + path + "\",\"" + name + "\")'>下载</a>";
        html += "<div class='icon'><em class='" + cls + "'></em></div>";
        html += "<div class='fileInfo'><p>" + name + "</p><span>" + size + "</span></div>";
        html += "</li>";
    }
    $("#attachmentCountSpan").html(count);
    jObjAttachmentDiv.find("ul").html(html);
}

/**
 *下载指定的文件
 * @param filePath 文件路径
 * @param fileName 文件名称
 */
function downloadFile(filePath, fileName) {
    var downloadURL = basePath + "upload/downFileByFilePath.action"+wapParam+"&filePath=" + filePath + "&fileName=" + fileName;
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