/**
 * javascript
 * qyoa wap dialog,author:tang
 */

/*Wap端弹窗口js类
 * 参数：
 * title：标题（默认为“标题”）
 * content：内容（默认为“空”）
 * btn1Name：第一个按钮名称（默认为“确认”）
 * btn1CallBack：第一个按钮点击后的回调方法
 * btn2Name：第二个按钮名称
 * btn2CallBack：第二个按钮点击后的回调方法
 *
 * 方法：点击弹窗上的按钮时默认调用close方法
 * show：显示弹窗，
 * close：关闭弹窗。
 */
function WapDialog(dataMap) {
    this.dialogId = "wap_dialog_" + _wapDialogGenerateRandom();
    this.title = dataMap.title == undefined ? "提示" : dataMap.title;
    this.content = dataMap.content == undefined ? "" : dataMap.content;
    this.btn1Name = dataMap.btn1Name;
    this.btn1CallBack = dataMap.btn1CallBack;
    this.btn2Name = dataMap.btn2Name;
    this.btn2CallBack = dataMap.btn2CallBack;
    this.btn1Id = "wap_dialog_btn1_" + _wapDialogGenerateRandom();
    this.btn2Id = "wap_dialog_btn2_" + _wapDialogGenerateRandom();
    //打开Dialog弹窗
    this.show = function () {
        _generateWapDialogHTML(this);
        document.getElementById(this.dialogId).style.display = "block";
        document.getElementById(this.dialogId).getElementsByClassName("selectBox")[0].style.display = "block";
    };
    //关闭Dialog弹窗
    this.close = function () {
        document.body.removeChild(document.getElementById(this.dialogId));
    };
}
//内部方法，生成弹窗的HTML内容并追加到dom文档的最后面
function _generateWapDialogHTML(wapDialog) {
    var dialogNode = document.createElement("div");
    dialogNode.className = "dialog";
    dialogNode.id = wapDialog.dialogId;
    var html = "<div class='selectBox'>";
    html += "<h2 class='hdTitle'>" + wapDialog.title + "</h2>";
    html += "<div class='opInfo' style='text-align:left'>" + wapDialog.content + "</div>";
    html += "<div class='opboxbtn'>";
    html += "<table width='50%' border='0' cellpadding='0' cellspacing='0'>";
    html += "<tr>";
    var showBtn2 = true;
    if (wapDialog.btn1Name == undefined || wapDialog.btn1Name == "") {
        wapDialog.btn1Name = "确认";
    }
    if (wapDialog.btn2Name == undefined || wapDialog.btn2CallBack == undefined) {
        showBtn2 = false;
    }
    if (!showBtn2) {
        html += "<td><input type='button' class='btnMain' id='" + wapDialog.btn1Id + "' value='" + wapDialog.btn1Name + "' /></td>"
    } else {
        html += "<td class='pr5'><input type='button' class='btnMain' id='" + wapDialog.btn1Id + "' value='" + wapDialog.btn1Name + "' /></td>";
        html += "<td class='pl5'><input type='button' class='btnReplay' id='" + wapDialog.btn2Id + "' value='" + wapDialog.btn2Name + "'/></td>";
    }
    html += "</tr>";
    html += "</table>";
    html += "</div>";
    html += "</div>";
    dialogNode.innerHTML = html;
    document.body.appendChild(dialogNode);
    setTimeout(_bindBtn1Click(wapDialog), 0);
    if (showBtn2) {
        setTimeout(_bindBtn2Click(wapDialog), 0);
    }
}

//内部方法，第一个按钮的点击事件
function _bindBtn1Click(wapDialog) {
    document.getElementById(wapDialog.btn1Id).onclick = function () {
        wapDialog.close();
        if (typeof(wapDialog.btn1CallBack) == "function") {
            wapDialog.btn1CallBack();
        }
    }
}
//内部方法，第二个按钮的点击事件
function _bindBtn2Click(wapDialog) {
    document.getElementById(wapDialog.btn2Id).onclick = function () {
        wapDialog.close();
        if (typeof(wapDialog.btn2CallBack) == "function") {
            wapDialog.btn2CallBack();
        }
    }
}
//内部方法，生成随机字符串
function _wapDialogGenerateRandom() {
    return new Date().getTime() + "" + Math.random();
}