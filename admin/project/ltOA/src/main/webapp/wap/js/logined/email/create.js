//页面加载完成后调用的方法，对页面一些内容进行初始化
$(document).ready(function () {
    initPageUI();
    initToUser();
    initUserDelete();
    initForwardOrReply();
});
//初始化转发和回复的相关功能
function initForwardOrReply() {
    var from = $.trim($("#from").val());
    var defaultContentJSONMap = $("#defaultContentJSONMap").val();
    var isForwardOrReply = false;
    if (from == "forward") {
        $("#createTitle").text("转发邮件");
        document.title = "转发邮件";
        isForwardOrReply = true;
    } else if (from == "reply") {
        $("#createTitle").text("回复邮件");
        document.title = "回复邮件";
        isForwardOrReply = true;
    }

    if (isForwardOrReply && defaultContentJSONMap != "") {
        defaultContentJSONMap = JSON.parse(defaultContentJSONMap);
        var html = "";
        //html += "<div style='height:0px;border-bottom:1px #c0c2cf solid;margin:5px auto'><br></div>";
        html += "<br/>";
        html += "<br/>";
        html += "<hr/>";
        html += "<div style='padding:5px 15px;border-bottom:1px #cccccc solid;background:#edf6db;font-size:12px;'>";
        html += "<span style='line-height:16px;'><b>发件人：</b>" + defaultContentJSONMap["fromName"] + "</span>";
        html += "<br/>";
        if (defaultContentJSONMap["toName"] != "") {
            html += "<span style='line-height:16px;'><b>收件人：</b>" + defaultContentJSONMap["toName"] + "</span>";
            html += "<br/>";
        }
        if (defaultContentJSONMap["copyToName"] != "") {
            html += "<span style='line-height:16px;'><b>抄送人：</b>" + defaultContentJSONMap["copyToName"] + "</span>";
            html += "<br/>";
        }
        html += "<span style='line-height:16px;'><b>发送时间：</b>" + defaultContentJSONMap["sendTime"] + "</span>";
        html += "<br/>";
        html += "<span style='line-height:16px;'><b>主题：</b>" + defaultContentJSONMap["subject"] + "</span></div>";
        html += "<br/>";
        $("#oldEmailInfo").html(html + $("#oldEmailContentInfo").val());
        $("#autoHasOldEmailTips").show();
        $("#oldEmailInfoEdit").click(function () {
            new WapDialog({
                title: "重要提示",
                content: "编辑原邮件将失去一些非文本内容，确认继续编辑吗？",
                btn1Name: "取消",
                btn2Name: "确认",
                btn2CallBack: function () {
                    $("#contentInfo").val($("#contentInfo").val() + "\r\n" + $("#oldEmailInfo").text());
                    $("#oldEmailInfo").html("");
                    $("#autoHasOldEmailTips").hide();
                }
            }).show();
        });
    }
}
//初始化添加默认的收件人和抄送人。。在转发和回复时使用
function initToUser() {
    var toId = $("#toId").val();
    var toName = $("#toName").val();
    var copyToId = $("#copyToId").val();
    var copyToName = $("#copyToName").val();
    addDefaultUser(toId, toName, "toUserDiv");
    if (copyToId != undefined && $.trim(copyToId) != "") {
        addDefaultUser(copyToId, copyToName, "copyToUserDiv");
        $("#copyAndSecretToTd").click();
    }
}
//添加默认人员（多个时用英文逗号隔开）到指定的DIV
function addDefaultUser(toId, toName, toUserDivId) {
    if (toId != undefined && $.trim(toId) != "") {
        toId = toId.split(",");
        toName = toName.split(",");
        for (var i = 0; i < toId.length; i++) {
            addAnReceiver2UserDiv(toId[i], toName[i], toUserDivId);
        }
    }
}
//初始化页面一些效果，来自UI部门
function initPageUI() {
    $("#copyAndSecretToTd").click(function () {    	
    	$("#copyAndsecretTr").hide();
        $("#copyToUserTr").show();
        $("#secretToUserTr").show();
    });//显示密送
}

//初始化删除单个收件人员
function initUserDelete() {
    $(".div_txt .memberList").live("click", function () {
        $(this).remove();
    });
}
//添加收件人-往指定的DIV区域
function addReceiver(toUserDivId) {
    openUserSelect($("#" + toUserDivId),addReceiverCallBack,toUserDivId);
}

//添加人员回调方法
function addReceiverCallBack(userMap, toUserDivId) {
    if (userMap != undefined) {
        userMap.forEach(function(value,key){
            if(value.Type=="user"){
                addAnReceiver2UserDiv(value.Id, value.Name, toUserDivId);
            }
        });
    }
}
//删除人员的小图标xx
var deleteUserIcon = basePath + "/wap/images/icon_cls.png";
//向指定的DIV区域添加一个人员
function addAnReceiver2UserDiv(userId, userName, toUserDivId) {
    var toUserDiv = $("#" + toUserDivId);
    if (toUserDiv.find("img[userId='" + userId + "']").length == 0) {//没有则加上
        var html = "<em class='memberList'>";
        html += "<img src='" + deleteUserIcon + "' class='cls' userId='" + userId + "' userName='" + userName + "' />";
        html += userName;
        html += "</em>";
        toUserDiv.append(html);
    }
}

//检查并发送邮件
function checkAndSendEmail() {
    //检查收件人是否为空
    if ($("#toUserDiv .memberList").length == 0) {
        new WapDialog({title: "提示", content: "收信人不能为空，请选择收信人。"}).show();
        return false;
    }
    if ($.trim($("#subject").val()) == "") {
        new WapDialog({
            title: "提示",
            content: "邮件主题为空，是否发送邮件？",
            btn1Name: "取消",
            btn2Name: "确认",
            btn2CallBack: function () {
                sendEmail()
            }
        }).show();
        return false;
    } else {
        sendEmail();
    }
    return true;
}
//发送邮件
function sendEmail() {
    var toUser = getUserIdNames("toUserDiv");
    var copyToUser = getUserIdNames("copyToUserDiv");
    var secretToUser = getUserIdNames("secretToUserDiv");    
    var emailBodyInfo = {
        "emailBody.toId": toUser.userId,
        "emailBody.toName": toUser.userName,
        "emailBody.copyToId": copyToUser.userId,
        "emailBody.copyToName": copyToUser.userName,
        "emailBody.secretToId": secretToUser.userId,
        "emailBody.secretToName": secretToUser.userName,
        "emailBody.subject": $.trim($("#subject").val()),
        "emailBody.contentInfo": $.trim($("#contentInfo").val() + $("#oldEmailInfo").html()),
        "emailBody.attachment": $.trim($("#attachment").val()),
        "emailBody.attachmentSize": $.trim($("#attachmentSize").val()),
        "emailBody.smsRemind": 0,
        "emailBody.needReceipt": 0,
        "send": 1,
        "from": $("#from").val()
    };
    $.ajax({
        url: basePath + "/logined/email/emailBodySave.action"+wapParam+"&random="+Math.random(),
        type: "post",
        dataType: 'json',
        data: emailBodyInfo,
        success: function (data) {
            if (data == undefined || data["status"] == undefined) {
                return;
            }
            if (data["status"] == 0) {
                alert(data["result"]);
            } else {
                window.location = basePath + "wap/logined/email/list.jsp"+wapParam+"&showTab=outBox";
            }
        }
    });
}
//从页面中获取用户名和用户ID，多个时用英文逗号隔开
function getUserIdNames(toUserDivId) {
    var userName = "";
    var userId = "";
    $("#" + toUserDivId + " .memberList img").each(function () {
        userName += "," + $(this).attr("userName");
        userId += "," + $(this).attr("userId");
    });
    if (userName.length > 1) {
        userName = userName.substr(1);
    }
    if (userId.length > 1) {
        userId = userId.substr(1);
    }
    return {userName: userName, userId: userId};
}