/**
 *发送草稿箱中的邮件-可批量
 */
function draftSendEmailBody() {
    if (_checkedIds==null|| _checkedIds=="") {
        qytx.app.dialog.alert(sprintf("email.no_selected_email&&发送"));
    } else {
        var emailBodyIds = "";
        if (_checkedIds!=null&&_checkedIds!="") {
        	emailBodyIds = _checkedIds.substring(0, _checkedIds.length - 1);
    	}
        qytx.app.dialog.confirm(sprintf("email.confirm_send_selected_email"), function () {
            //删除选中的记录ajax
        	qytx.app.ajax({
                url: basePath + "logined/email/emailBodyDraftSend.action",
                type: "post",
                dataType: 'text',
                data: {
                    emailBodyIds: emailBodyIds
                },
                shade:true,
                success: function (data) {
                    art.dialog.alert(data);
                    getDataTable(); //刷新人员信息
                    //更新左边的邮件数量
                    window.parent.updateBoxEmailCount();
                }
            });
        }, function () {

        })
    }
}

function getDataTable() {
	_checkedIds="";
	qytx.app.grid({
		id	:	"dataTable_draft",
		url	:	basePath + "logined/email/emailDraftList.action",
		iDisplayLength	:	parseInt($("#iDisplayLengthHidden").val()),
		selectParam	:	{
							"searchJson":$("#searchJson").val(),
							"from":$("#from").val()
						},
		valuesFn	:	[
			                 {
			                     "aTargets": [0],//覆盖第一列
			                     "fnRender": function (oObj) {
			                         return '<input name="checkChild" value="' + oObj.aData.id + '" type="checkbox" />';
			                     }
			                 },
			                 {"aTargets": [1],//覆盖第二列-主题
			                     "fnRender": function (oObj) {
			                         var emailBodyEditURL = basePath + "logined/email/emailBodyEdit.action?emailBodyId=" + oObj.aData["id"];
			                         return getImportantLevelHtml(oObj.aData["importantLevel"]) +
			                             "<a href='" + emailBodyEditURL + "' title='点击修改'>" + oObj.aData["subject"] + "</a> ";
			                     }
			                 },
			                 {
			                     "aTargets": [2],//覆盖第3列-日期
			                     "fnRender": function (oObj) {
			                         return "<span title='" + oObj.aData["longTime"] + "'>" + oObj.aData["shortTime"] + "</span>";
			                     }
			                 }
			             ]
	
	});
}

var dataTable
$(document).ready(function () {
	$.removeTableCookie('SpryMedia_DataTables_dataTable_draft');
	
    initFromBtn();
    //获取邮箱列表
    getDataTable();
    //更新左边的邮件数量
    window.parent.updateBoxEmailCount();
    
    $("#dataTable_draft").delegate("#checkAll", "click", function(event){    
	   	checkTotal();
	   	event.stopPropagation();
    });
	$("#dataTable_draft").delegate(":checkbox[name='checkChild']", "click", function(event) {
		checkChange();
		event.stopPropagation();
	});
	
});

function checkTotal(){
	var isTotalCheck=$("input:checkbox[id='checkAll']").prop("checked");
	var checkNum=0;
	if(isTotalCheck){
		$("input:checkbox[name='checkChild']").prop("checked", function( i, val ) {
			checkNum=checkNum+1;
            return true;
        });
	}else{
		$("input:checkbox[name='checkChild']").prop("checked", false);
	}

}

function checkChange(){
	if ($('#dataTable_draft :checkbox[name="checkChild"][checked="checked"]').length ==
		$('#dataTable_draft :checkbox[name="checkChild"]').length){
		$("input:checkbox[id='checkAll']").prop("checked", true);
	}else{
		$("input:checkbox[id='checkAll']").prop("checked", false);
	}
	
	
}