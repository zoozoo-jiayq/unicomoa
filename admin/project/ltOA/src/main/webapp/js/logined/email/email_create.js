/**
 * 根据checkbox的选中状态，设置隐藏域的值
 * @param obj checkbox对象
 * @param hiddenId 隐藏域的Id
 */
function checkBoxCheck(obj, hiddenId) {
    if ($(obj).attr("checked") == "checked") {
        $("#" + hiddenId).val(1);
    } else {
        $("#" + hiddenId).val(0);
    }
}

/**
 * 文件上次初始化控件
 */
function initFileUpload() {
	qytx.app.fileupload({
		id	:	"file_upload",
		moduleName:	"email",
		buttonText: "上传文件",
		ulName:	"attachUL",
		callback:function(data){
			// 保存信息
        	var attachmentVal = $("#attachment").val();
            attachmentVal = addAttachment(data.attachFile, data.attachName, data.attachSize, attachmentVal);
            $("#attachment").val(attachmentVal);
            calcAttachmentSize();
		},
		deleteFun:function(id,path){
			var attachmentVal = $("#attachment").val();
			attachmentVal = deleteAttachmentByPath(path,attachmentVal);
			$("#attachment").val(attachmentVal);
		}
	});
	return;
}


function deleteAttachment(filePath) {
    var attachmentVal = $("#attachment").val();
    attachmentVal = deleteAttachmentByPath(filePath, attachmentVal);
    $("#attachment").val(attachmentVal);
    calcAttachmentSize();
}

/**
 * 统计附件总大小并存入Hidden域中
 */
function calcAttachmentSize() {
    var totalSize = getTotalSizeByJson($("#attachment").val());
//    alert("附件总大小：" + totalSize);
    $("#attachmentSize").val(totalSize);
}

/**
 *显示或者隐藏 某个快级元素
 * @param aId a标签的ID
 * @param trId tr的ID
 * @param showText a标签提示显示的文本
 * @param hiddenText a标签提示隐藏的文本
 */
function showOrHidden(aId, trId, showText, hiddenText) {
    var jAObj = $("#" + aId);
    var jTrObj = $("#" + trId);
    if (jTrObj.is(":visible")) {
        jTrObj.hide();
        if (jAObj.length > 0) {
            jAObj.text(showText);
        }
    } else {
        jTrObj.show();
        if (jAObj.length > 0) {
            jAObj.text(hiddenText);
        }
    }
}

/**
 * 添加最近联系人
 */
function addRecentUserTo(userId, userName) {

    var forId = "toId";
    var forName = "toName";
    //ID存在则删除、不存在则添加,userName可能会一样
    if (isExistRevId(forId, userId)) {
        removeRevId(forId, userId);
        removeRevName(forName, userName);
    } else {
        addRevId(forId, userId);
        addRevName(forName, userName);
    }
}

/**
 * 计算邮件正文的字数
 * @param wordCountId 字数显示的span的ID
 */
function calcContentInfo(wordCountId) {
    $("#" + wordCountId).html(ue.getContent().toString().length);
}

/**
 * 清空邮件正文
 */
function cleanContentInfo() {
    ue.setContent("");
}


/**
 *初始化抄送、密送显示和隐藏
 */
function initAddCopySecretTo() {

    var addCopyTo = $("#addCopyTo");
    var addSecretTo = $("#addSecretTo");

    addCopyTo.click(function () {
        showOrHidden(this.id, "copyToTr", "添加抄送", "隐藏抄送");
    });
    addSecretTo.click(function () {
        showOrHidden(this.id, "secretToTr", "添加密送", "隐藏密送");
    });

    if ($("#copyToId").val() != "") {
        showOrHidden("addCopyTo", "copyToTr", "添加抄送", "隐藏抄送");
    }
    if ($("#secretToId").val() != "") {
        showOrHidden("addSecretTo", "secretToTr", "添加密送", "隐藏密送");
    }
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
            
            var html = appendNewOneAttachmentHTML(json["name"], null, json["path"]);
            $('#attachUL').append(html);
            window.parent.frameResize();
        }
    }
}

var ue;
function initUEEditor() {
    // 初始化富客户端
    ue = UE.getEditor('contentInfo', {
        initialFrameWidth:"100%",
        initialFrameHeight:180
    });
    ue.addListener('ready',function(){
        window.parent.frameResize();
        initEmailContentInfo();
    });
//    setTimeout(function(){
//    	window.parent.frameResize();
//        //初始化邮件内容，如回复、转发时的默认内容
//        initEmailContentInfo();
//    },100);


//    KindEditor.ready(function (K) {
//        editor = K.create('textarea[name="emailBody.contentInfo"]', {
//            resizeType: 1,
//            //height:"400px",
//            //width:"645px",
//            allowPreviewEmoticons: false,
//            allowImageUpload: true,
//            uploadJson: basePath
//                + 'plugins/kindeditor/upload_json.jsp',
//            items: [ 'fontname', 'fontsize', '|', 'forecolor',
//                'hilitecolor', 'bold', 'italic', 'underline',
//                'removeformat', '|', 'justifyleft',
//                'justifycenter', 'justifyright',
//                'insertorderedlist', 'insertunorderedlist', '|',
//                'emoticons', 'image', 'link' ]
//        });
//    });
}

function initEmailContentInfo() {
    var from = $("#from").val();
    var defaultContentJSONMap = $("#defaultContentJSONMap").val();
    var defaultToUsers = $("#defaultToUsers").val();
    if ((from == "forward" || from == "reply") && defaultContentJSONMap != "") {
        defaultContentJSONMap = JSON.parse(defaultContentJSONMap);
        var html = "";
        //html += "<div style='height:0px;border-bottom:1px #c0c2cf solid;margin:5px auto'><br></div>";
        html += "<br/>";
        html += "<br/>";
        html += "<br/>";
        html += "<br/>";
        html += "<br/>";
        html += "<hr/>";
        html += "<div style='padding:5px 15px;border-bottom:1px #cccccc solid;background:#edf6db;font-size:12px;'>";
        html += "<span style='line-height:16px;'><b>发件人：</b>&nbsp;" + defaultContentJSONMap["fromName"] + "</span>";
        html += "<br/>";
        if (defaultToUsers != "") {
            html += "<span style='line-height:16px;'><b>收件人：</b>&nbsp;" + defaultToUsers + "</span>";
            html += "<br/>";
        }
        if (defaultContentJSONMap["copyToName"] != "") {
            html += "<span style='line-height:16px;'><b>抄送人：</b>&nbsp;" + defaultContentJSONMap["copyToName"] + "</span>";
            html += "<br/>";
        }
        html += "<span style='line-height:16px;'><b>发送时间：</b>&nbsp;" + defaultContentJSONMap["sendTime"] + "</span>";
        html += "<br/>";
        html += "<span style='line-height:16px;'><b>主题：</b>&nbsp;" + defaultContentJSONMap["subject"] + "</span></div>";
        html += "<br/>";
        html += "</div>";
        html += "<br/>";
        html += defaultContentJSONMap["contentInfo"];
        ue.setContent(html);
    }
}

/**
 *保存并发送的校验
 */
function checkSaveAndSend(checkToUser) {
    $("#autoSave").val(0);
    if (checkToUser) {
        $("#send").val(1);
        var jObjToId = $("#toId");
        var toId = jObjToId.val();
        if (toId == "") {
            qytx.app.valid.showObjError(jObjToId, "email.receive_user_not_null");
            jObjToId.focus();
            scrollTo(0, 0);
            return false;
        }
    } else {
        $("#send").val(0);
    }
    var jObjSubject = $("#subject");
    var subject = jObjSubject.val();
    subject = $.trim(subject);
    jObjSubject.val(subject);
    if (subject == "") {
        qytx.app.dialog.confirm(sprintf("email.you_will_send_no_subject_email"),
            function () {
                submitForm();
            }, function () {
                jObjSubject.focus();
            });
    } else {
        submitForm();
    }
    return false;
}

function saveDraft() {
    $("#send").val(0);
    $("#autoSave").val(0);
    submitForm();
    return false;
}

/**
 *提交前做的准备
 */
function submitForm() {
    var url = $("#saveAction").val();
    var emailBodyId = $.trim($("#emailBodyId").val());
    if (emailBodyId != "") {
        url = $("#updateAction").val();
    }
    var smsRemind = $("#smsRemind").attr("checked") == "checked" ? "1" : "0";
    var needReceipt = $("#needReceipt").attr("checked") == "checked" ? "1" : "0";
    var contentInfo =  ue.getContent();
    var contentInfoStr = ue.getContentTxt();
    if(contentInfoStr.length>10000){
    	qytx.app.valid.showObjError($("#contentInfoInput"), "email.email_content_too_long");
    	return false;
    }
//    if (typeof(editor) == "undefined") {
//        contentInfo = $("#contentInfo").val();
//    } else {
//        contentInfo = editor.html();
//    }

    var formData = {"emailBody.toId": $("#toId").val(),
        "emailBody.toName": $("#toName").val(),
        "emailBody.copyToId": $("#copyToId").val(),
        "emailBody.copyToName": $("#copyToName").val(),
        "emailBody.secretToId": $("#secretToId").val(),
        "emailBody.secretToName": $("#secretToName").val(),
        "emailBody.subject": $("#subject").val(),
        "emailBody.importantLevel": $("#importantLevel").val(),
        "emailBody.contentInfo": contentInfo,
        "emailBody.attachment": $("#attachment").val(),
        "emailBody.attachmentSize": $("#attachmentSize").val(),
        "emailBody.smsRemind": smsRemind,
        "emailBody.sendType": getSendType(),
        "emailBody.needReceipt": needReceipt,
        "emailBody.id": emailBodyId,
        "from": $("#from").val(),
        "send": $("#send").val()
    };
    qytx.app.ajax({
        url: url + "?_r=" + getOaRandom(),
        type: "post",
        dataType: 'json',
        data: formData,
        shade:true,
        success: function (data) {
            if (data == undefined || data["status"] == undefined) {
                return;
            }
            if (data["status"] == 0) {
            	qytx.app.dialog.alert(data["result"]);
            } else {
                $("#emailBodyId").val(data["result"]);
                if ($("#send").val() == "1" || $("#from").val() == "emailBodyEditNotRead") {//发送成功
                    openURLForEmail(basePath + "logined/email/emailSendedListPage.action")
                } else {
                    //保存成功，识别是否为自动保存
                    if ($("#autoSave").val() == "1") {
                        qytx.app.dialog.tips(sprintf("email.already_auto_saved_to_draft"));
                    } else {
                    	qytx.app.dialog.tips(sprintf("email.already_saved_to_draft"));
                    }
                }
                $(window.parent.document).find(".meil_wrap").find("li").removeClass("current");
                $(window.parent.document).find(".meil_wrap").find("#outbox").addClass("current");
                try{
                	//更新左边的邮件数量
                	window.parent.updateBoxEmailCount();
                }catch (e) {
				}
                
                $("#autoSave").val(0);
            }
        }
    });
}

function initSaveDraftTimer() {
    //自动保存仅针对草稿
    if ($("#from").val() != "emailBodyEditNotRead") {
        saveDraftTimer();
    } else {
        $(".remindTr").hide();
    }
}

var openedTime = 0;
function saveDraftTimer() {
    if (typeof (ue) != "undefined" && openedTime != 0) {
        if (ue.hasContents()) {
            $("#send").val(0);
            $("#autoSave").val(1);//标记为自动保存
            submitForm();
        }
    }
    openedTime = 1;
    setTimeout("saveDraftTimer()", 1000 * 60 * 3);//3分钟自动保存
}

//初始化邮件级别显示按钮
function initImportantLevel() {
    var level = $("#hiddenImportantLevel").val();
    if (level != undefined && $.trim(level) != "") {
    	$("#importantLevel").find("option").removeAttr("selected");
    	if(level==0){
    		$("#importantLevel").find("option[value='0']").attr("selected","selected");
    	}else if(level==1){
    		$("#importantLevel").find("option[value='1']").attr("selected","selected");
    	}else if(level==2){
    		$("#importantLevel").find("option[value='2']").attr("selected","selected");
    	}
    }
}


$(document).ready(function () {
    //初始化附件上传控件
    initFileUpload();
    //初始化邮件级别显示按钮
    initImportantLevel();
    //初始化富文本编辑器
    initUEEditor();

    //初始化隐藏显示抄送人、密送人信息
    initAddCopySecretTo();
    //修改时初始化附件的HTML;
    Attachment.initAttachmentHTML("attachment", "attachUL")
    //初始化自动保存的timer
    initSaveDraftTimer();
    //设置是否发送事务提醒
    setAffairCheck("内部邮件", "smsRemind", "smsRemindTr");
    
});

