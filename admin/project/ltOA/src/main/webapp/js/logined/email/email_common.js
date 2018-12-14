/**
 * @author tang_botao
 * Email模块共用脚本
 */

/**
 * 关闭窗口
 */
function closeWindow() {
    window.opener = null;
    window.parent = null;
    window.open('', '_self');
    top.close();
}

/**
 * 发送微讯
 * @param userId
 */
function sendMessage(userId, userName, subjectSpanId) {
    var contentInfo = "";
    if (typeof(subjectSpanId) != "undefined" && $("#" + subjectSpanId).length > 0) {
        var subject = $("#subject").html();
        contentInfo = "您好，我已收到您的邮件:" + subject;
    }
    window.top.openSendMessage(userId, userName, contentInfo);
}

/**
 * 销毁选中的邮件
 */
function destroyEmailToChecked() {
    if (_checkedIds.length<=0) {
        qytx.app.dialog.alert(sprintf("email.no_selected_email&&永久删除"));
    } else {
        var emailToIds = _checkedIds.substring(0, _checkedIds.length-1);
        qytx.app.dialog.confirm(sprintf("email.confirm_destroy_selected_email"), function () {
            //删除选中的记录ajax
        	qytx.app.ajax({
                url: basePath + "logined/email/emailToDestroy.action",
                type: "post",
                dataType: 'text',
                data: {
                    emailToIds: emailToIds
                },
                success: function (data) {
                	_checkedIds="";
                    getDataTable(); //刷新人员信息
                    //更新左边的邮件数量
                    window.parent.updateBoxEmailCount();
                }
            });
        }, function () {

        })
    }
}

/**
 *导出选中的收件箱和废纸篓邮件
 */
function reportEmailToChecked() {
    if (_checkedIds.length<=0) {
    	qytx.app.dialog.alert(sprintf("email.no_selected_email&&导出"));
    } else {
        var emailToIds = _checkedIds.substring(0, _checkedIds.length-1);
        /* qytx.app.dialog.confirm(sprintf("email.confirm_export_selected_email"), function () {
            //导出选中的邮件
            var exportEmailToURL = basePath + "logined/email/emailToExport.action?emailToIds=" + emailToIds;
            window.open(exportEmailToURL);
        }, function () {
        })*/
    	 var exportEmailToURL = basePath + "logined/email/emailToExport.action?emailToIds=" + emailToIds;
         window.open(exportEmailToURL);
    }
}

/**
 *导出选中的发件箱或者草稿箱邮件
 */
function reportEmailBodyChecked() {
    if (_checkedIds.length<=0) {
    	qytx.app.dialog.alert(sprintf("email.no_selected_email&&导出"));
    } else {
        var emailBodyIds = _checkedIds.substring(0, _checkedIds.length-1);
//        qytx.app.dialog.confirm(sprintf("email.confirm_export_selected_email"), function () {
//            //导出选中的邮件
//            var exportEmailToURL = basePath + "logined/email/emailBodyExport.action?emailBodyIds=" + emailBodyIds ;
//            window.open(exportEmailToURL);
//        }, function () {
//        })
        var exportEmailToURL = basePath + "logined/email/emailBodyExport.action?emailBodyIds=" + emailBodyIds ;
        window.open(exportEmailToURL);
    }
}

/**
 * 删除选中的邮件体（已发送、草稿箱）
 */
function deleteEmailBodyChecked(isFromDraft) {
    var confirmMsg = sprintf("email.confirm_delete_selected_email");
    if (typeof (isFromDraft) == "undefined" || !isFromDraft) {
        //来自于收件箱的调用
        confirmMsg += "\n" + sprintf("email.alert_delete_not_read_email");
    }

    if (_checkedIds.length<=0) {
    	qytx.app.dialog.alert(sprintf("email.no_selected_email&&删除"));
    } else {
        var emailBodyIds = _checkedIds.substring(0, _checkedIds.length-1);
        qytx.app.dialog.confirm(confirmMsg, function () {
            //删除选中的记录ajax
            _deleteEmailBody(emailBodyIds);
        }, function () {

        })
    }
}

/**
 * 删除当前打开的邮件体（已发送、草稿箱）
 * @param {Object} isFromDraft
 */
function deleteEmailBodyOpended(isFromDraft) {
    var confirmMsg = sprintf("email.confirm_delete_this_email");
    if (typeof (isFromDraft) == "undefined" || !isFromDraft) {
        //来自于收件箱的调用
        confirmMsg += "\n" + sprintf("email.alert_delete_not_read_email");
    }
    var emailBodyIds = _getOpenedRowId();
    qytx.app.dialog.confirm(confirmMsg, function () {
        //删除选中的记录ajax
        _deleteEmailBody(emailBodyIds);
    }, function () {
    });
}

/**
 * 邮件体删除-子方法（已发送、草稿箱）
 * @param {Object} emailBodyIds
 */
function _deleteEmailBody(emailBodyIds) {
	qytx.app.ajax({
        url: basePath + "logined/email/emailBodyDelete.action",
        type: "post",
        dataType: 'text',
        data: {
            emailBodyIds: emailBodyIds
        },
        success: function (data) {
        	_checkedIds="";
            getDataTable(); //刷新邮件信息
            //更新左边的邮件数量
            window.parent.updateBoxEmailCount();
        }
    });
}

/**
 * 获取邮件详情页面的地址
 * @param emailBodyId 邮件体Id
 * @param emailToId 收件Id
 * @return {String} 邮件详情地址
 */
function getEmailDetailUrl(emailBodyId, emailToId) {
    if (typeof (emailToId) == "undefined") {
        emailToId = 0;
    }
    return basePath + "logined/email/emailDetail.action?emailBodyId="
        + emailBodyId + "&emailToId=" + emailToId;
}

/**
 * 在iframe中打开邮件详情
 * @param {Object} emailBodyId
 * @param {Object} emailToId
 */
function _openEmailInFrame(emailBodyId, emailToId) {
    //记录打开的id
    if (typeof (emailToId) == "undefined") {
        //已发送列表无emailToId
        _setOpenedRowId(emailBodyId);
        emailToId = 0;
    } else {
        //收件箱列表存在emailToId
        _setOpenedRowId(emailToId);
    }
    //在iframe中打开
    $("#emailDetailFrame").attr("src",
        getEmailDetailUrl(emailBodyId, emailToId));
}

/**
 * 在新打开的窗口中打开邮件详情
 * @param {Object} emailBodyId 邮件体ID
 * @param {Object} emailToId 邮件接收信息id
 */
function _openEmailInNewWindow(emailBodyId, emailToId) {
    openURLForEmail(getEmailDetailUrl(emailBodyId, emailToId));
}

/**
 * 打开新页面
 */
function openURLForEmail(url, inFrame) {
    if (typeof(inFrame) != "undefined") {
        inFrame = true;
    }
    if (inFrame) {
        window.open(url, "emailContent");
    } else {
        window.location = url;
    }
}


/**
 * 设置打开的数据行id
 */
function _setOpenedRowId(rowId) {
    $("#openedRowId").val(rowId);
}

/**
 * 获取打开的数据行id
 * @return {TypeName}
 */
function _getOpenedRowId() {
    return $("#openedRowId").val();
}

/**
 * 上一封或者下一封
 * @param isUp：true 打开上一封，false打开下一封
 */
function openEmailUpDown(isUp) {

    for (var i = 0; i < dataTable.fnGetData().length; i++) {
        var rowData = dataTable.fnGetData()[i];
        if (rowData.id.toString() == _getOpenedRowId().toString()) {
            var targetRow;
            if (isUp) {
                if (i == 0) {
                	qytx.app.dialog.alert(sprintf("email.already_is_first"));
                } else {
                    targetRow = dataTable.fnGetData()[i - 1]
                }
            } else {
                if (i == dataTable.fnGetData().length - 1) {
                	qytx.app.dialog.alert(sprintf("email.already_is_last"));
                } else {
                    targetRow = dataTable.fnGetData()[i + 1]
                }
            }
            if (typeof (targetRow["emailBodyId"]) == "undefined") {
                //为发件箱或草稿箱
                _openEmailInFrame(targetRow["id"], undefined);
            } else {
                //为收件箱或者废纸篓
                _openEmailInFrame(targetRow["emailBodyId"], targetRow["id"]);
            }
            break;
        }
    }

}

/**
 * 收件箱--数据表格的行单击和双击事件
 * @memberOf {TypeName}
 * 第一个参数:emailBodyIdKey,第二个参数:emailToIdKey
 */
function emailToListTableTrClick(emailBodyIdKey, emailToIdKey) {
    $("#dataTable tbody tr").click(function (event) {
        var rowData = dataTable.fnGetData(this);
        _openEmailInFrame(rowData[emailBodyIdKey], rowData[emailToIdKey])
    });

    $("#dataTable tbody tr").dblclick(function (event) {
        var rowData = dataTable.fnGetData(this);
        _openEmailInNewWindow(rowData[emailBodyIdKey], rowData[emailToIdKey]);
    });
}

/**
 * 发件箱--数据表格的行单击和双击事件
 * 参数:emailBodyIdKey
 * @memberOf {TypeName}
 */
function emailBodyListTableTrClick(emailBodyIdKey) {
    $("#dataTable tbody tr").click(function (event) {
        var rowData = dataTable.fnGetData(this);
        _openEmailInFrame(rowData[emailBodyIdKey], undefined)
    });

    $("#dataTable tbody tr").dblclick(function (event) {
        var rowData = dataTable.fnGetData(this);
        _openEmailInNewWindow(rowData[emailBodyIdKey], undefined);
    });
}

/**
 * 收件箱--打开列表中的第一封邮件
 * 第一个参数:emailBodyIdKey,第二个参数:emailToIdKey
 */
function openFirstEmailTo(emailBodyIdKey, emailToIdKey) {
    if ($("#dataTable tbody tr").length > 0) {
        var firstTr = $("#dataTable tbody tr:first-child")
        var rowData = dataTable.fnGetData(firstTr[0]);
        _openEmailInFrame(rowData[emailBodyIdKey], rowData[emailToIdKey]);
    }
    ;
}

/**
 * 打开已发送列表中的第一封邮件
 * 参数:emailBodyIdKey
 */
function openFirstEmailBody(emailBodyIdKey) {
    if ($("#dataTable tbody tr").length > 0) {
        var firstTr = $("#dataTable tbody tr:first-child")
        var rowData = dataTable.fnGetData(firstTr[0]);
        _openEmailInFrame(rowData[emailBodyIdKey], undefined);
    }
}

/**
 * 值指定的窗口打开新页面
 * @param url 页面地址
 * @param target 窗口名称
 */
function openURL(url, target) {
    if (target == undefined) {
        window.open(url);
    } else {
        window.open(url, target);
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


/**
 * 检查from是否来自搜索，判断是否需要显示返回按钮
 */
function initFromBtn() {
    var from = $("#from").val();
    from = $.trim(from);
    if (from == "search") {
        $("#btnForSearchResult").show();
        document.title = document.title + " - 搜索结果";
        $(".meilPageTitle em").html($(".meilPageTitle em").html() + " - 搜索结果");
    }
}

/**
 *参看读取状态
 */
function showReadStatus(emailBodyId, from) {
    qytx.app.dialog.openUrl({
    	id	: 	"showReadStatusDialog",
    	url	:	basePath + "logined/email/emailReadStatus.jsp?emailBodyId=" + emailBodyId + "&from=" + from,
    	size:	'L',
    	title:	"查看读取状态",
    	customButton:[{name: '关闭',focus: false,callback:function(){
	    		var win = art.dialog.open.origin;// 来源页
		    	win.refreshLeftMenu();
		    	return true;
    		}}]
    });
}
function refreshLeftMenu(){
	//更新左边的邮件数量
    window.parent.updateBoxEmailCount();
}

//处理表格中没有值的时候在IE7及一下显示的边框问题
function processTableTdEmptyTo() {
    $("table td").each(function () {
        var content = $(this).html();
        console.log(content);
        if (content == undefined || $.trim(content) == "") {
            $(this).html("&nbsp;");
            console.log("add nbsp:" + content);
        }
    });
}

//显示重要级别文字
function getImportantLevelHtml(level) {
    if (level == undefined) {
        return "";
    }
    switch (level.toString()) {
        case "1":
            return "<span class='a_oreg'>重要&nbsp;</span>";
        case "2":
            return "<span class='a_red'>非常重要&nbsp;</span>";
        default :
            return "";
    }
}

//获取随机数
function getOaRandom() {
    return new Date().getTime() + "" + Math.random();
}
