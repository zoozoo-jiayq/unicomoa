function deleteEmailBodyIfToUserDeleted() {
    qytx.app.dialog.confirm(sprintf("email.confirm_delete_all_receiver_deleted"), function () {
        //删除选中的记录ajax
        qytx.app.ajax({
            url: basePath + "logined/email/emailBodyDeleteIfToUserDeleted.action"+"?_r=" + getOaRandom(),
            type: "post",
            dataType: 'text',
            data: {
            },
            success: function (data) {
                //art.dialog.alert(sprintf("email.delete_email_success&&" + data + "##用户已删除邮件"));
                getDataTable(); //刷新人员信息
                try{
                	//更新左边的邮件数量
                	window.parent.updateBoxEmailCount();
                }catch (e) {
				}
            }
        });
    }, function () {

    });
}

function deleteEmailBodyIfToUserReaded() {
    qytx.app.dialog.confirm(sprintf("email.confirm_delete_all_receiver_read"), function () {
        //删除选中的记录ajax
        qytx.app.ajax({
            url: basePath + "logined/email/emailBodyDeleteIfToUserReaded.action"+"?_r=" + getOaRandom(),
            type: "post",
            dataType: 'text',
            data: {
            },
            success: function (data) {
                //art.dialog.alert(sprintf("email.delete_email_success&&" + data + "##用户已读邮件"));
                getDataTable(); //刷新人员信息
                try{
                	//更新左边的邮件数量
                	window.parent.updateBoxEmailCount();
                }catch (e) {
				}
            }
        });
    }, function () {

    });
}


function deleteEmailBodyIfToUserNotRead() {
    qytx.app.dialog.confirm(sprintf("email.confirm_delete_all_receiver_unread"), function () {
        //删除选中的记录ajax
        qytx.app.ajax({
            url: basePath + "logined/email/emailBodyDeleteIfToUserNotRead.action"+"?_r=" + getOaRandom(),
            type: "post",
            dataType: 'text',
            data: {
            },
            success: function (data) {
                //art.dialog.alert(sprintf("email.delete_email_success&&" + data + "##用户未读邮件"));
                getDataTable(); //刷新人员信息
                try{
                	//更新左边的邮件数量
                	window.parent.updateBoxEmailCount();
                }catch (e) {
				}
            }
        });
    }, function () {

    });
}

/**
 * 转发--已发送
 */
function fowardEmailBodyById(emailBodyId) {
    var url = basePath
        + "/logined/email/emailBodyForward.action?emailBodyId="
        + emailBodyId;
    openURLForEmail(url);
}

/**
 * 再次发送--已发送
 */
function againSendEmailBodyById(emailBodyId) {
    var url = basePath
        + "/logined/email/emailBodyAgainSend.action?emailBodyId="
        + emailBodyId;
    openURLForEmail(url);
}

/**
 *加载收件箱列表
 */
function getDataTable() {
	_checkedIds="";
	qytx.app.grid({
		id	:	"dataTable_sendedlist",
		url	:	basePath + "logined/email/emailSendedList.action",
		iDisplayLength	:	parseInt($("#iDisplayLengthHidden").val()),
		selectParam	:	{
							"searchJson":$("#searchJson").val(),
							"from":$("#from").val()
						},
		valuesFn	:	[
			                 {
			                     "aTargets": [ 0 ],//覆盖第一列
			                     "fnRender": function (oObj) {
			                         return '<input name="checkChild" value="' + oObj.aData.id + '" type="checkbox" />';
			                     }
			                 }/*,
			                 {
			                     "aTargets": [ 1 ],//覆盖第2列:收件人
			                     "fnRender": function (oObj) {
			                         var toName = oObj.aData["toName"];
			                         var html = "";
			                         if(oObj.aData["readStatus"] == 1){
			                         	if (oObj.aData["toCount"] > 1) {//群发邮件
			                                 html = "<a href='javascript:showReadStatus(" + oObj.aData["id"] + ",\"emailBody\")' >";
			                                 html += toName;
			                                 html += "</a>";
			                             } else {
			                                 html = "<span title='" + toName + "'>" + toName + "</span>";
			                             }
			                         }else{
			                         	if (oObj.aData["toCount"] > 1) {//群发邮件
			                                 html = "<a href='javascript:showReadStatus(" + oObj.aData["id"] + ",\"emailBody\")' ><strong>";
			                                 html += toName;
			                                 html += "</strong></a>";
			                             } else {
			                                 html = "<span title='" + toName + "'>" + toName + "</span>";
			                             }
			                         }
			                         
			                         return html;
			                     }
			                 }*/,
			                 {
			                     "aTargets": [ 2 ],//覆盖第3列:主题
			                     "fnRender": function (oObj) {
			                         var readStatus = oObj.aData["readStatus"];
			                         var cls = "";
			                         switch (readStatus) {
			                             case 0:
			                                 cls = "personMeil_noread";
			                                 break;
			                             case 1:
			                                 cls = "personMeil_readed";
			                                 break;
			                             case -1:
			                                 cls = "groupMeil";
			                                 break;
			                             case -2:
			                                 cls = "groupMeil";
			                                 break;
			                         }
			                     	return "<a href='javascript:_openEmailInNewWindow(" + oObj.aData["id"] + ")' class='" + cls + "'>"
			                         + oObj.aData["subject"] + "</a>";
			                     }
			                 },
			                 {
			                     "aTargets": [ 3 ],//覆盖第四列-日期
			                     "fnRender": function (oObj) {
			 	                	return "<span title='" + oObj.aData["longTime"] + "'>" + oObj.aData["shortTime"] + "</span>";
			                     }
			                 },
			                 {
			                     "aTargets": [ 4 ],//覆盖第5列-操作
			                     "fnRender": function (oObj) {
			                         var readStatus = oObj.aData["readStatus"];
			                         var html = "";
			                         var emailBodyId = oObj.aData["id"];
			                         if (readStatus == -2 || readStatus == 0) {//未读取，可继续编辑
			                             var editURL = basePath + "logined/email/emailBodyEditNotRead.action?emailBodyId=" + emailBodyId;
			                             html += "<a href='" + editURL + "'>修改</a> ";
			                         }
			                         html += "<a href='javascript:fowardEmailBodyById(" + emailBodyId + ")'>转发</a> ";
			                         html += "<a href='javascript:againSendEmailBodyById(" + emailBodyId + ")'>再次发送</a> ";
			                         return html;
			                     }
			                 }
			             ]
	});
}

/**
 * 头部全选记录
 */
function checkTotal() {
	var isTotalCheck = $("input:checkbox[id='checkAll']").prop("checked");
	var checkNum = 0;
	if (isTotalCheck) {
		$("input:checkbox[name='checkChild']").prop("checked", function(i, val) {
					checkNum = checkNum + 1;
					return true;
				});
	} else {
		$("input:checkbox[name='checkChild']").prop("checked", false);
	}
}
var dataTable
$(document).ready(function () {	
	$.removeTableCookie('SpryMedia_DataTables_dataTable_sendedlist');
	
	// 头部全选复选框
	$("#dataTable_sendedlist").delegate("#checkAll", "click", function(event) {
				checkTotal();
				event.stopPropagation();
			});
	
	$("#dataTable_sendedlist").delegate(":checkbox[name='checkChild']", "click", function(event) {
		checkChange();
		event.stopPropagation();
	});
    initFromBtn();
    //获取邮箱列表
    getDataTable();
    try{
    	//更新左边的邮件数量
    	window.parent.updateBoxEmailCount();
    }catch (e) {
	}
});


function checkChange(){
	if ($('#dataTable_sendedlist :checkbox[name="checkChild"][checked="checked"]').length ==
		$('#dataTable_sendedlist :checkbox[name="checkChild"]').length){
		$("input:checkbox[id='checkAll']").prop("checked", true);
	}else{
		$("input:checkbox[id='checkAll']").prop("checked", false);
	}
}