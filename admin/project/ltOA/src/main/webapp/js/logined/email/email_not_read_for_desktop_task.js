function getDataTable() {
	qytx.app.grid({
		id	:	"dataTable_notRead",
		url	:	basePath + "logined/email/emailToList.action",
		iDisplayLength	:	parseInt($("#iDisplayLengthHidden").val()),
		selectParam	:	{
							"emailBoxId":$("#emailBoxIdHidden").val(),
							"notRead":$("#notReadHidden").val()
						},
		valuesFn	:	[
			                 {
			                     "aTargets": [1],//覆盖第2列，主题
			                     "fnRender": function (oObj) {
	//		                         var readUrl = "/logined/email/emailDetail.action?emailToId=" + oObj.aData["id"];
	//		                         var openUrl = basePath + "/logined/email/emailMainPage.action?redirectURL=" + readUrl;
			                         return getImportantLevelHtml(oObj.aData["importantLevel"])
			                             + "<a href='javascript:void(0)' onclick='openDetailInTab(\"" + oObj.aData["id"] + "\",\"" + oObj.aData["subject"] + "\")' >" + oObj.aData["subject"] + "</a>";
			                     }
			                 },
			                 {
			                     "aTargets": [ 2 ],//覆盖第3列-日期
			                     "fnRender": function (oObj) {
			                         return "<span title='"
			                             + oObj.aData['longTime'] + "'>" + oObj.aData['shortTime'] + "</span>";
			                     }
			                 },
			                 {
			                     "aTargets": [ 3 ],//覆盖第4列-附件
			                     "fnRender": function (oObj) {
			                         if (oObj.aData["hasAttachment"]) {
			                             //显示有附件
			                             return "<div class='starsPop0'><span class='affix'></span></div>";
			                         } else {
			                             return "<span class='affix'></span>";
			                         }
			                     }
			                 },
			                 {
			                     "aTargets": [ 4 ],//覆盖第5列-大小
			                     "fnRender": function (oObj) {
			                         return convertFileSize(oObj.aData["attachmentSize"]);
			                     }
			                 }
			             ]
	});
}

var dataTable
$(document).ready(function () {
	
    //获取邮箱列表
    getDataTable();
});

/**
 * 在tab中打开邮件详情
 *emailToId：收件ID
 *subject:邮件主题
 */
function openDetailInTab(emailToId, subject) {
    var readUrl = basePath + "logined/email/emailDetail.action?hideBtn=returnBtn&emailToId=" + emailToId;
    window.top.addTab('emailDetail_' + emailToId, readUrl, "邮件", true, undefined);
}