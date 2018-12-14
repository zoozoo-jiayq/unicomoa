/**
 * 功能:邮件读取状态js
 * 版本:1.0
 */

function getDataTable() {
	qytx.app.grid({
		id	:	"dataTable",
		url	:	basePath + "logined/email/readStatus.action",
		bInfo : false,
		selectParam	:	{
							"from":$("#from").val(),
							"emailBodyId":$("#emailBodyId").val()
						},
		valuesFn	:	[
		                 {
		                     "aTargets": [0],//覆盖第一列
		                     "fnRender": function (oObj) {
		                         var readStatus = oObj.aData["readStatus"];

		                         var cls = "";
		                         if (readStatus == 1) {//已经读取
		                             return '<img src="'+basePath+'images/meil_readed.png"/>';
		                         } else if (readStatus == 2) {//已经取消
		                         	return '<img src="'+basePath+'images/del.png"/>';
		                         } else {//未读取
		                         	return '<img src="'+basePath+'images/meil_noread.png"/>';
		                         }
		                     }
		                 },
		                 {
		                     "aTargets": [5],//操作
		                     "fnRender": function (oObj) {
		                         var html = "<a href='javascript:void(0)' onclick='sendMessage(\""+oObj.aData["toUserId"]+"\",\""+oObj.aData["toName"]+"\")' >发即时消息</a> ";
		                         if (oObj.aData["canTakeBack"]) {
		                             html += "&nbsp;<a href='javascript:takeBackEmailTo("+oObj.aData["id"]+")'>收回</a> "
		                         }
		                         return html;
		                     }
		                 }
		             ]
	});
	
}

/**
 * 发送微讯
 * @param userId 用户ID
 * @param userName 用户名
 */
function sendMessage(userId, userName) {
	qytx.app.dialog.openUrl({
		id	:	"send_message",
		url	:	basePath + "logined/message/alert_send_message.jsp?type=box&userInfoId=" + userId
        + "&userInfoName=" + encodeURI(userName),
        title:	"发送即时消息",
        size:	'L',
        customButton	:	[{
				name : '发送',
				focus:true,
				callback : function() {
					var iframe = this.iframe.contentWindow;
					iframe.sendMessage();
					return false;
				}
			}, {
				name : '取消',
				callback : function() {
					return true;
				}
			}]
	});
}

function takeBackEmailTo(emailToId) {
    qytx.app.dialog.confirm("确定要收回吗?收回后该收件人将不再接收该邮件", function () {
        //移动邮件
    	qytx.app.ajax({
            url: basePath + "logined/email/emailToTakeBack.action",
            type: "post",
            dataType: 'text',
            data: {
                emailToIds: emailToId
            },
            success: function (data) {
            	qytx.app.dialog.alert(data);
                getDataTable(); //刷新人员信息
            }
        });
    }, function () {
        //取消
    });
}

var dataTable
$(document).ready(function () {
    //获取接收人列表
	$.removeTableCookie('SpryMedia_DataTables_dataTable_emailReadStatus.jsp');
    getDataTable();
});
