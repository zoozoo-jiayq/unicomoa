<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML>
<!-- 软航科技电子印章管理工具 V1版本 -->
<!-- 版本：V1.0.1 -->
<!-- 作者：软航技术夏 -->
<!-- 邮箱：xiawb@ntko.com -->
<!-- 网页和JS源代码版权归软件作者所有 -->
<!-- 印章管理控件遵循自带软件协议 -->

<!--
2012-07-24 更新：
    全新的实时消息通知模式
    全新的危险操作通知
    印章预览随实际大小自动调整，超出常规长宽带有危险通知信息
    新增设置和修改EKEY持有者
-->

<html>
<head>
<title>软航科技电子印章制作管理工具</title>

<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<style type="text/css">
body {
	font-family:"微软雅黑";
    line-height: 20px;
    color: #333;
    font-size:14px;
}

a {
    color: #0088cc;
    text-decoration: none;
}

a:hover {
    color: #005580;
    text-decoration: underline;
}

.well {
    height: 700px;
    padding: 19px;
    margin-bottom: 10px;
    background-color: whiteSmoke;
    border: 1px solid #EEE;
    border: 1px solid rgba(0, 0, 0, 0.05);
    -webkit-border-radius: 4px;
    -moz-border-radius: 4px;
    border-radius: 4px;
    -webkit-box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.05);
    -moz-box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.05);
    box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.05);
}

.esp-display {
    height: 200px;
    width: 150px;
    /*height: 100%;*/
    /*width: 100%;*/
    padding: 3px;
    margin-bottom: 7px;
    background-color: whiteSmoke;
    border: 1px solid #EEE;
    border: 1px solid rgba(0, 0, 0, 0.05);
    -webkit-border-radius: 4px;
    -moz-border-radius: 4px;
    border-radius: 4px;
    -webkit-box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.05);
    -moz-box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.05);
    box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.05);
}

.modal {
    position: fixed;
    /*top: 50%;*/
    /*left: 50%;*/
    z-index: 1050;
    overflow: auto;
    width: 100%;
    margin: 5px;
    background-color: white;
    border: 1px solid #999;
    border: 1px solid rgba(0, 0, 0, 0.3);
    -webkit-border-radius: 6px;
    -moz-border-radius: 6px;
    border-radius: 6px;
    -webkit-box-shadow: 0 3px 7px rgba(0, 0, 0, 0.3);
    -moz-box-shadow: 0 3px 7px rgba(0, 0, 0, 0.3);
    box-shadow: 0 3px 7px rgba(0, 0, 0, 0.3);
    -webkit-background-clip: padding-box;
    -moz-background-clip: padding-box;
    background-clip: padding-box;
}

.modal-header {
    height:36px;line-height:36px;padding-left:10px;font-size:16px;font-weight:bold;
    border-bottom: 1px solid #c8c8c8;
}

.modal-body {
    overflow-y: auto;
    height: 580px;
    padding: 10px;
}

.modal-footer {
    padding: 14px 15px 15px;
    margin-bottom: 0;
    text-align: right;
    background-color: whiteSmoke;
    border-top: 1px solid #DDD;
    -webkit-border-radius: 0 0 6px 6px;
    -moz-border-radius: 0 0 6px 6px;
    border-radius: 0 0 6px 6px;
    -webkit-box-shadow: inset 0 1px 0 white;
    -moz-box-shadow: inset 0 1px 0 #ffffff;
    box-shadow: inset 0 1px 0 white;
}

.btn {
    display: inline-block;
    padding: 4px 10px 4px;
    margin-bottom: 0;
    font-size: 13px;
    line-height: 18px;
    color: #333;
    text-align: center;
    text-shadow: 0 1px 1px rgba(255, 255, 255, 0.75);
    vertical-align: middle;
    background-color: whiteSmoke;
    background-image: -moz-linear-gradient(top, white, #E6E6E6);
    background-image: -ms-linear-gradient(top, white, #E6E6E6);
    background-image: -webkit-gradient(linear, 0 0, 0 100%, from(white), to(#E6E6E6));
    background-image: -webkit-linear-gradient(top, white, #E6E6E6);
    background-image: -o-linear-gradient(top, white, #E6E6E6);
    background-image: linear-gradient(top, white, #E6E6E6);
    background-repeat: repeat-x;
    filter: progid:DXImageTransform.Microsoft.gradient(startColorstr = '#ffffff', endColorstr = '#e6e6e6', GradientType = 0);
    border-color: #E6E6E6 #E6E6E6 #BFBFBF;
    border-color: rgba(0, 0, 0, 0.1) rgba(0, 0, 0, 0.1) rgba(0, 0, 0, 0.25);
    filter: progid:dximagetransform.microsoft.gradient(enabled = false);
    border: 1px solid #CCC;
    border-bottom-color: #B3B3B3;
    -webkit-border-radius: 4px;
    -moz-border-radius: 4px;
    border-radius: 4px;
    -webkit-box-shadow: inset 0 1px 0 rgba(255, 255, 255, 0.2), 0 1px 2px rgba(0, 0, 0, 0.05);
    -moz-box-shadow: inset 0 1px 0 rgba(255, 255, 255, 0.2), 0 1px 2px rgba(0, 0, 0, 0.05);
    box-shadow: inset 0 1px 0 rgba(255, 255, 255, 0.2), 0 1px 2px rgba(0, 0, 0, 0.05);
    cursor: pointer;
}

.grid_1 {
    width: 60px;
}

.grid_2 {
    width: 140px;
}

.grid_3 {
    width: 220px;
}

.grid_4 {
    width: 300px;
}

.grid_5 {
    width: 420px;
}

.grid_6 {
    width: 520px;
}

.grid_7 {
    width: 520px;
}

.grid_8 {
    width: 620px;
}

.grid_9 {
    width: 700px;
}

.grid_10 {
    width: 780px;
}

.grid_11 {
    width: 860px;
}

.grid_12 {
    width: 960px;
}

.column {
    margin: 0px;
    overflow: hidden;
    float: left;
}

.row {
    width: 960px;
    margin: 0 auto;
    overflow: hidden;
}

.row .row {
    width: auto;
    display: inline-block;
}

.column-title {
    border-bottom: 2px solid #2e8b57;
    margin-bottom: 8px;
    font-weight: bold;
}

.input-row {
    padding:0 3px;
    line-height:30px;
}

.esp-info {
    padding: 3px;
}
.button{margin-right:10px;float: left;cursor: pointer; height:28px; line-height:28px;  background:url(../../images/button_bg.png) no-repeat;vertical-align:middle; overflow:hidden;}
.button span{float:left; height:28px; padding:0 10px;line-height:28px; background:url(../../images/button_bg.png) no-repeat scroll right -28px;vertical-align:middle;}
div.button:hover{background-position:0px -56px;}
div.button:hover span{background-position:right -84px}
.button_no{margin-right:10px;float: left;cursor: pointer; height:28px; line-height:28px;background:url(../../images/button_nobg.png) no-repeat;}
.button_no span{float:left; height:28px; padding:0 10px;line-height:28px; background:url(../../images/button_nobg.png) no-repeat scroll right -28px;vertical-align:middle;}
.alertInfo_info {
    background: #6a5acd;
    color: #ffffff;
    border-bottom: 1px solid #da70d6;
    padding: 5px;
}

.alertInfo_warn {
    background: #b22222;
    color: #add8e6;
    border-bottom: 2px solid #da70d6;
    padding: 5px;
}

.alertInfo_hint {
    background: #4169e1;
    color: #ffffff;
    /*font-style: italic;*/
    font-size: 13px;
    /*border-bottom: 1px solid purple;*/
    padding: 2px 0 0 15px;
}
</style>
<script type="text/javascript">
var signCtrl, sign_c = false, getElement = function (id) {
    return document.getElementById(id);
}, timer, ekeyHolder = "未知", detectHolder = function () {
    ekeyHolder = signCtrl.GetEkeyUser();
    ekeyHolder = (/NTKO 100 Erased|""/.test(ekeyHolder.replace(/(^\s*)|(\s*$)/g, "")) ? "尚未设置" : ekeyHolder);
    getElement("EkeyHolder").innerHTML = signCtrl.IsEkeyConnected ? (ekeyHolder === "尚未设置" ? "<a href='javascript:mgEN()'>尚未设置</a>" : ekeyHolder) : "未知";
}, blockLayers = function (shown, callback) {
    var layers = ["rs_act", "un_act", "n_act", "ap_act", "up_act", "un_erase"], index = 0;
    for (; index < layers.length; index++) {
        getElement(layers[index]).style.display = (layers[index] === shown) ? "" : "none";
    }
    if (Object.prototype.toString.apply(callback) === '[object Function]') {
        callback.apply(null, []);
    }
};
//检查用户输入。参数IsNewSign标志是新建还是修改印章。新建的时候需要
//检查用户是否选择了印章原始文件。
function CheckInput(IsNewSign) {
    var signname = getElement("SignName").value;
    if (( signname == '') || ( undefined == typeof(signname))) {
        showAlert('请输入印章名称');
        return false;
    }

    var signuser = getElement("SignUser").value;
    if (( signuser == '') || ( undefined == typeof(signuser))) {
        showAlert('请输入印章使用人');
        return false;
    }

    var password = getElement("Password").value;
    if (( password == '') || ( undefined == typeof(password))) {
        showAlert('请输入印章口令');
        return false;
    }
    if ((password.length < 6) || (password.length > 32)) {
        showAlert('印章口令必须是6-32位.');
        return false;
    }
    if (IsNewSign == true) //如果是创建印章，需要用户选择原始印章文件
    {
        var signfile = getElement("SignFile").value;
        if (( signfile == '') || ( undefined == typeof(signfile))) {
            showAlert('请选择用来创建印章的原始文件(bmp,gif,jpg,png.');
            return false;
        }
        if ((-1 == signfile.toUpperCase().lastIndexOf("BMP")) &&
                (-1 == signfile.toUpperCase().lastIndexOf("GIF")) &&
                (-1 == signfile.toUpperCase().lastIndexOf("PNG")) &&
                (-1 == signfile.toUpperCase().lastIndexOf("JPG"))) {
            showAlert('请选择一个正确的印章原始文件(bmp,gif,jpg.');
            return false;
        }
    }
    signCtrl.SignName = getElement("SignName").value;
    if (0 != signCtrl.StatusCode) {
        showAlert("设置印章名称错误");
        return false;
    }
    signCtrl.SignUser = getElement("SignUser").value;
    if (0 != signCtrl.StatusCode) {
        showAlert("设置印章使用者错误");
        return false;
    }
    signCtrl.Password = getElement("Password").value;
    if (0 != signCtrl.StatusCode) {
        showAlert("设置印章口令错误");
        return false;
    }
    return true;
}
//生成新印章文件
function CreateNew() {
    if (!CheckInput(true))return;
    signCtrl.CreateNew(
            getElement("SignName").value,
            getElement("SignUser").value,
            getElement("Password").value,
            getElement("SignFile").value
    );
    if (0 != signCtrl.StatusCode) {
        sign_c = false;
        showAlert("创建印章文件错误.");
        return;
    }
    sign_c = true;
    ShowSignInfo();
    showAlert("创建印章成功.您现在可以插入EKEY,并点击'保存印章到EKEY'将创建的印章保存到EKEY.");
}

//对话框方式生成新的印章文件
function CreateNewWithDialog() {
    signCtrl.CreateNew();
    if (0 != signCtrl.StatusCode) {
        sign_c = false;
        showAlert("创建印章文件错误.");
        return;
    }
    //正确，显示印章信息
    sign_c = true;
    ShowSignInfo();
    showAlert("创建印章成功.您现在可以插入EKEY,并点击'保存印章到EKEY'将创建的印章保存到EKEY.");
}

function SaveToEkey() {
    if (!CheckInput(false))return;
    var ifCont = window.confirm("请插入EKEY到您的计算机.然后继续。");
    if (!ifCont)return;
    var index = signCtrl.SaveToEkey();
    if (0 == signCtrl.StatusCode) {
        getElement("EkeyFreeSize").innerHTML = signCtrl.EkeyFreeSize + "字节";
        showAlert("保存印章到EKEY成功!保存位置:" + index);
    }
    else {
        showAlert("保存印章到EKEY失败！！");
    }
}

function SaveToLocal() {
    if (!CheckInput(false))return;
    signCtrl.SaveToLocal('', true);
    if (0 == signCtrl.StatusCode) {
        showAlert("保存印章到本地文件成功!");
    }
    else {
        showAlert("保存印章到本地文件失败！！");
    }
}

function ResetEkeySigns() {
    signCtrl.ResetEkeySigns();
    if (0 == signCtrl.StatusCode) {
        getElement("EkeyFreeSize").innerHTML = signCtrl.EkeyFreeSize + "字节";
        showAlert("重设EKEY所有印章成功!");
        blockLayers('n_act');
    }
    else {
        showAlert("用户取消,或者重设EKEY所有印章失败!");
    }
}
function EnableEkeyButtons(isEnabled) {
    getElement("OpenFromEkey").disabled = !isEnabled;
    getElement("DeleteFromEkey").disabled = !isEnabled;
    getElement("ResetEkey").disabled = !isEnabled;
    getElement("EkeyFreeSize").innerHTML = isEnabled ? (signCtrl.EkeyFreeSize + "字节") : "未知";
    getElement("EkeySN").innerHTML = isEnabled ? (signCtrl.EkeySN) : "未知";
    getElement("ChangeEkeyAP").disabled = !isEnabled;
    getElement("ChangeEkeyUP").disabled = !isEnabled;
    getElement("SaveToEkey").disabled = !(sign_c && isEnabled);
    detectHolder();
}
function init() {
    signCtrl = getElement("ntkosignctl");
    signCtrl.IsShowStatus = false;

    EnableEkeyButtons(signCtrl.IsEkeyConnected);
    initKeyType();
}

function initKeyType() {
    curEkeyType = signCtrl.EkeyType;
    selectObj = getElement("EkeyTypeSelector");
    for (i = 0; i < selectObj.options.length; i++) {
        if (selectObj.options[i].value == curEkeyType) {
            selectObj.options[i].selected = true;
            break;
        }
    }
}

function changeEkeyType(ekeyType) {
    signCtrl.EkeyType = ekeyType;
    EnableEkeyButtons(signCtrl.IsEkeyConnected);
}

function OpenFromLocal() {
    signCtrl.OpenFromLocal('', true);
    sign_c = (0 === signCtrl.StatusCode);
    if (sign_c === false) {
        showAlert("从本地打开印章错误.");
        return;
    }
    ShowSignInfo();
}
function OpenFromEkey(pass) {
    var ifCont = window.confirm("请插入EKEY到您的计算机.然后继续。");
    if (!ifCont)return;
    signCtrl.OpenFromEkey(pass);
    if (0 != signCtrl.StatusCode) {
        sign_c = false;
        showAlert("从EKEY打开印章错误.");
        return;
    }
    //正确，显示印章信息
    sign_c = true;
    ShowSignInfo();
    showAlert("从EKEY打开印章成功！您现在可以修改印章的相关信息并重新保存到EKEY.此时选择印章原始文件无效.");
}
//如果成功状态，显示当前印章信息
function ShowSignInfo() {
    getElement("SignName").value = signCtrl.SignName;
    getElement("SignUser").value = signCtrl.SignUser;
    getElement("Password").value = signCtrl.Password;
    getElement("SignSN").innerHTML = signCtrl.SignSN;
    getElement("SaveToLocal").removeAttribute("disabled");
    if (sign_c && signCtrl.IsEkeyConnected) {
        getElement("SaveToEkey").removeAttribute("disabled");
    }

    var h = signCtrl.SignHeight,
            w = signCtrl.SignWidth, hstr, wstr;
    if (h < 196 && w < 196) {
        hstr = (h + 5) + "px";
        wstr = (w + 5) + "px";
    } else {
        hstr = "200px";
        wstr = "200px";
    }
    getElement("esp_display").style.height = hstr;
    getElement("esp_display").style.width = wstr;
    getElement("SignWidth").innerHTML = w;
    getElement("SignHeight").innerHTML = h;
    outOfRangeRisk();
}
function ChangeEkeyPin() {
    var flags = document.getElementsByName("forWho");
    var oldpass = getElement("oldPassword").value;
    var newpass1 = getElement("newPassword1").value;
    var newpass2 = getElement("newPassword2").value;
    if ((newpass1.length < 4) || (newpass1.length > 16)) {
        showAlert('EKEY访问口令必须是4-16位.');
        return;
    }
    if (newpass1 != newpass2) {
        showAlert('两次新口令不符合，请重新输入.');
        return;
    }
    var isAdmin = true;
    if (flags[0].checked) {
        isAdmin = false;
    }
    else {
        isAdmin = true;
    }
    signCtrl.ChangeEkeyPassword(oldpass, newpass1, isAdmin);
    if (0 == signCtrl.StatusCode) {
        showAlert(isAdmin ? "改变EKEY管理员口令成功!" : "改变EKEY用户口令成功!");
        blockLayers('n_act');
    }
    else {
        showAlert(isAdmin ? "改变EKEY管理员口令失败!" : "改变EKEY用户口令失败!");
    }
}
function ResetEkeyUserPin() {
    var adminPassword = getElement("adminPassword").value;
    var newUserPassword1 = getElement("newUserPassword1").value;
    var newUserPassword2 = getElement("newUserPassword2").value;
    if ((newUserPassword1.length < 4) || (newUserPassword1.length > 16)) {
        showAlert('EKEY访问口令必须是4-16位.');
        return;
    }
    if (newUserPassword1 != newUserPassword2) {
        showAlert('两次新口令不符合，请重新输入.');
        return;
    }
    signCtrl.ResetEkeyUserPassword(adminPassword, newUserPassword1);
    if (0 == signCtrl.StatusCode) {
        showAlert("重设EKEY用户口令成功!");
        blockLayers('n_act');
    } else {
        showAlert("重设EKEY用户口令失败!");
    }
}
function showAlert(message) {
    var clearDisplay = function () {
        if (typeof timer !== "undefined") {
            clearTimeout(timer);
        }
        getElement("alertInfo").innerHTML = "";
        getElement("alertInfo").className = "";
    }, display = function () {
        timer = setTimeout(clearDisplay, 5000);
        getElement("alertInfo").innerHTML = message;
        getElement("alertInfo").className = "alertInfo_info";
    };
    if (typeof timer !== "undefined") {
        clearDisplay();
        setTimeout(display, 333);
    } else {
        display();
    }
}
//创建的印章过于巨大
function outOfRangeRisk() {
    if (signCtrl.SignWidth > 192 || signCtrl.SignHeight > 192) {
        getElement("alertRisk").style.display = "";
        getElement("SaveToLocal").disabled = true;
        getElement("SaveToEkey").disabled = true;
    }
}
function ignoreRisk() {
    getElement("alertRisk").style.display = "none";
    getElement("SaveToLocal").removeAttribute("disabled");
    if (sign_c && signCtrl.IsEkeyConnected) {
        getElement("SaveToEkey").removeAttribute("disabled");
    }
}
function setNewEkeyHolder() {
    var newHolderName = getElement("newEkeyHolder").value;
    if (newHolderName.replace(/(^\s*)|(\s*$)/g, "") === "") {
        showAlert("请输入新的EKEY持有者名称！");
    } else if (newHolderName.replace(/(^\s*)|(\s*$)/g, "") === ekeyHolder) {
        showAlert("EKEY持有者名称没有改变！");
    } else {
        setTimeout(function () {
            setTimeout(function () {
                blockLayers('n_act');
            }, 500);
            var code = signCtrl.StatusCode;
            showAlert("EKEY返回状：" + (code === 0 ? "修改成功，当前持有者为‘" + newHolderName + "’" : "修改失败"));
            detectHolder();
        }, 500);
        signCtrl.SetEkeyUser(newHolderName);
    }
}
function eraseHolder() {
    if (/NTKO 100 Erased|""/.test(signCtrl.GetEkeyUser().replace(/(^\s*)|(\s*$)/g, ""))) {
        setTimeout(mgEN, 500);
        showAlert("EKEY持有者尚未设置！请先设置持有者！");
    } else {
        setTimeout(function () {
            var code = signCtrl.StatusCode;
            showAlert("EKEY返回状：" + (code === 0 ? "操作成功，EKEY持有者已被抹除" : "操作失败"));
            detectHolder();
            blockLayers('n_act');
        }, 500);
        signCtrl.SetEkeyUser("NTKO 100 Erased");
    }
}
<%
    String path = request.getContextPath();
    request.setAttribute("ctx", path);
%>
var ctx ="${ctx}";
</script>
</head>
<body onload="init()">
<div class="well" style="background-color: #888; border: none;">
<div class="row">
<div class="column grid_12">
<div id="displayEsp" class="modal"
     style="position: relative;top: auto; left: auto; margin: 0 auto; z-index: 1; width: 100%;">
<div class="modal-header">
    印章管理控件
</div>
<div id="alertInfo" style="width: 99%;"></div>
<div class="modal-body">
<div id="mainContent" class="row">
<div class="column grid_5">
    <div id="esp_display" class="esp-display">
    <input type="hidden" id="cabpath" value="<%=request.getContextPath()%>"/>
        <script type="text/javascript" src="<%=request.getContextPath() %>/js/logined/websign/ntkoGenOcxObj.js"></script>
        <script language="JScript" for="signCtrl" event="OnEkeyInserted()">
            EnableEkeyButtons(true);
        </script>
        <script language="JScript" for="signCtrl" event="OnEkeyRemoved()">
            EnableEkeyButtons(false);
        </script>
    </div>
    <div class="esp-info">印章信息:<span id="SignWidth">0</span>/<span id="SignHeight">0</span>(宽/高)
    </div>
    <div class="esp-info">印章 SN:<span id="SignSN">未知</span></div>
    <div class="esp-info">EKEY持有者 :<span id="EkeyHolder">未知</span></div>
    <div class="esp-info">EKEY SN :<span id="EkeySN">未知</span></div>
    <div class="esp-info">EKEY容量 :<span id="EkeyFreeSize">0byte</span></div>
    <div class="esp-info">
        EKEY类型 :<select id="EkeyTypeSelector"
                        onchange="changeEkeyType(this.options[this.options.selectedIndex].value)">
        <option value="1">HT_EKEY</option>
        <option value="2">M&W_EKEY</option>
        <option value="3">大明五洲</option>
        <option value="4">FT3K_EKEY</option>
        <option value="6">九思泰达EKEY</option>
        <option value="7">FT2K_EKEY</option>
        <option value="8">HD_EKEY</option>
        <option value="9">东方中讯</option>
        <option value="10">握奇</option>
    </select>
    </div>
</div>
<div class="column grid_7">
    <div class="row" id="n_act">
        <div class="column grid_6" style="padding-bottom:15px">
            <div class="column-title">
                创建印章
            </div>
            <span style="color: #ff0000;padding-left:3px;">注意：创建新印章时,以下所有信息必须输入。</span><br/>

            <div class="input-row">印 章 名 称 ：&nbsp;<input id="SignName" value="测试印章Sign01"
                                                         checked="checked"></div>
            <div class="input-row">印 章 用 户 ：&nbsp;<input id="SignUser" value="测试用户User01"></div>
            <div class="input-row">印 章 口 令 ：&nbsp;<input id="Password" value=""></div>
            <div class="input-row">请选择印章源文件(请选择bmp,gif,或者jpg)：<br>
                <input type=file id="SignFile">
            </div>
            <div class="input-row">
	            <div class="button" onClick="CreateNew();">
	            	<span>创建新印章</span>
	            </div>
	        </div>            
            &nbsp;&nbsp;&nbsp;
            <div class="button" onClick="CreateNewWithDialog();">
            	<span>创建新印章[使用对话框]</span>
            </div>
        </div>
        <div class="column grid_6" style="padding-bottom:15px">
            <div class="column-title">
                修改印章
            </div>
            <div class="input-row">
	            <div class="button" onClick="OpenFromLocal()" tabindex="7">
	            	<span>打开本地印章</span>
	            </div>
	            <div id="OpenFromEkey" class="button_no" onClick="OpenFromEkey('');">
	            	<span>打开EKEY印章</span>
	            </div>
	            <div id="DeleteFromEkey" class="button_no" onClick="signCtrl.DeleteFromEkey();">
	            	<span>从EKEY删除印章</span>
	            </div>
	        </div>
        </div>
        <div class="column grid_6" style="padding-bottom:15px">
            <div class="column-title">
                保存印章
            </div>
            <div id="alertRisk" class="alertInfo_warn" style="display: none">
                印章长宽过大，我们建议印章的尺寸不要超过192 * 192像素！<br/>
                <a style="color: #ffffff;" href="javascript:ignoreRisk()">忽略这个问题</a>
            </div>
            <div class="input-row">
	            <div id="SaveToLocal" class="button_no" onClick="SaveToLocal()">
	            	<span>保存印章到本地</span>
	            </div>
	            <div class="button_no" id="SaveToEkey" onClick="SaveToEkey()">
	            	<span>保存印章到EKEY</span>
	            </div>
	        </div>
        </div>
        <div class="column grid_6" style="padding-bottom:15px">
            <div class="column-title">
                数据恢复
            </div>
            <div class="input-row">
	            <div class="button_no" id="ResetEkey" onClick="blockLayers('rs_act')">
	            	<span>初始化EKEY</span>
	            </div>
	            <div class="button_no" id="ChangeEkeyAP" onClick="blockLayers('ap_act')">
	            	<span>EKEY口令管理</span>
	            </div>
	            <div class="button" id="ChangeEkeyUN" onClick="mgEN()">
	            	<span>管理EKEY用户</span>
	            </div>
	         </div>
            <script type="text/javascript">
                function mgEN() {
                    blockLayers("un_act", function () {
                        getElement("_ekeyHolder").innerHTML = ekeyHolder;
                    })
                }
            </script>
            <div class="input-row">
	            <div id="ChangeEkeyUP" class="button_no" onClick="blockLayers('up_act')">
	            	<span>重置用户密码</span>
	            </div>
            </div>
        </div>
    </div>
    <div class="row" id="rs_act" style="display: none;">
        <div class="column grid_12">
            <div class="column-title">
                重置EKEY印章
            </div>
            <div class="alertInfo_warn">
                警告：一个危险的操作即将执行，请确认是否需要重置EKEY!<br/>
                <a style="color: #ffffff;" id="process_reset" href="javascript:blockLayers('n_act')">返回</a>&nbsp;&nbsp;&nbsp;<a
                    style="color: #ffffff;" href="javascript:ResetEkeySigns()">继续执行</a>
            </div>
        </div>
    </div>
    <div class="row" id="ap_act" style="display: none;">
        <div class="column grid_12">
            <div class="column-title">
                EKEY口令管理
                <div id="hints" class="alertInfo_hint">
                    用户密码是在用户访问EKEY数据时会用到。请注意保管。
                </div>
            </div>
            <div class=column-content>
                <script type="text/javascript">
                    function alertRisk(show) {
                        if (show) {
                            showAlert("注意:请务必牢记管理员访问口令!如果丢失只能报废EKEY!");
                            getElement("hints").innerText = "管理员密码是在用户遗忘EKEY用户密码时重置用户密码时使用。";
                        } else {
                            getElement("hints").innerText = "用户密码是在用户访问EKEY数据时会用到。请注意保管。";
                        }
                        getElement("hints").style.display = "";
                    }
                </script>
                <div class="input-row">旧口令：<input type="password" id="oldPassword" value="">
                </div>
                <div class="input-row">新口令：<input type="password" id="newPassword1" value="">
                </div>
                <div class="input-row">请确认：<input type="password" id="newPassword2" value="">
                </div>
                <div class="input-row">口令源：<input type="radio" name="forWho" class="radio"
                                                  value="0"
                                                  checked
                                                  onchange="alertRisk(false)">用户
                    <input type="radio" name="forWho" class="radio" value="1"
                           onchange="alertRisk(true)">管理员
                </div>
                <div class="input-row">
                    <button id="ChangeEkeyPin" onClick="ChangeEkeyPin()">
                        修改EKEY口令
                    </button>
                </div>
            </div>
            <br/><br/>
            <a href="javascript:blockLayers('n_act')">返回</a>
        </div>
    </div>
    <div class="row" id="up_act" style="display: none;">
        <div class="column grid_12">
            <div class="column-title">
                使用管理员口令解锁EKEY用户口令
                <div class="alertInfo_hint">
                    当忘记EKEY用户口令时，可以通过管理员口令来重置用户口令！
                </div>
            </div>
            <div class="input-row">管理员口令：<input type="password" id="adminPassword" value="">
            </div>
            <div class="input-row">用户新口令：<input type="password" id="newUserPassword1" value="">
            </div>
            <div class="input-row">确认新口令：<input type="password" id="newUserPassword2" value="">
            </div>
            <div class="input-row">
                <button id="ResetEkeyUserPin" onClick="ResetEkeyUserPin()">
                    重置EKEY用户口令
                </button>
            </div>
            <br/><br/>

            <a href="javascript:blockLayers('n_act')">返回</a>
        </div>
    </div>
    <div class="row" id="un_act" style="display: none;">
        <div class="column grid_12">
            <div class="column-title">
                设置连接的EKEY持有者
                <div class="alertInfo_hint">
                    EKEY中保存了持有本硬件持有者的名称，你可以更改持有者，如果持有者发生了改变。
                </div>
            </div>
            <div style="padding-bottom: 15px">
                原持有者名称：<span id="_ekeyHolder"></span>
            </div>
            <div style="padding-bottom: 15px">
                新持有者名称：<input type="text" id="newEkeyHolder">
                <button onclick="setNewEkeyHolder()">更新</button>
            </div>
            <a href="javascript:blockLayers('un_erase')">抹除持有者</a>
            <br/><br/>

            <div>
                <a href="javascript:blockLayers('n_act')">返回</a>
            </div>
        </div>
    </div>
    <div class="row" id="un_erase" style="display: none;">
        <div class="column grid_12">
            <div class="column-title">
                抹除EKEY持有者
            </div>
            <div class="alertInfo_warn">
                警告：一个危险的操作即将执行，请确认是否需要抹除EKEY持有者!<br/>
                <a style="color: #ffffff;" id="process_erase" href="javascript:blockLayers('n_act')">返回</a>&nbsp;&nbsp;&nbsp;<a
                    style="color: #ffffff;" href="javascript:eraseHolder()">继续执行</a>
            </div>
        </div>
    </div>
</div>
</div>
</div>
</div>
</div>
</div>
</div>
</div>
</div>
</body>
</html>