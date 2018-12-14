/**
 * 清空废纸篓
 */
function cleanEmailToWastebasket() {
    qytx.app.dialog.confirm(sprintf("email.confirm_clean_wastebasket"),
        function () {
            //删除选中的记录ajax
    	qytx.app.ajax({
                url: basePath + "logined/email/emailToWastebasketClean.action"+"?_r=" + getOaRandom(),
                type: "post",
                dataType: 'text',
                data: {},
                success: function (data) {
                    //art.dialog.alert(sprintf("email.already_clean_wastebasket"));
                    getDataTable();   //刷新人员信息
                    //更新左边的邮件数量
                    window.parent.updateBoxEmailCount();
                }
            });
        }, function () {

        });
}

/**
 * 恢复删除的邮件
 */
function recoveryEmailToChecked() {
    if (_checkedIds==null|| _checkedIds=="") {
    	qytx.app.dialog.alert(sprintf("email.no_selected_email&&恢复"));
    } else {
        var emailToIds = _checkedIds.substring(0, _checkedIds.length - 1);
        qytx.app.dialog.confirm(sprintf("email.confirm_recovery_selected_email"),
            function () {
                //删除选中的记录ajax
        	qytx.app.ajax({
                    url: basePath + "logined/email/emailToRecovery.action",
                    type: "post",
                    dataType: 'text',
                    data: {emailToIds: emailToIds},
                    success: function (data) {
                    		qytx.app.dialog.success(data+"！",function(){
	                    		getDataTable();   //刷新人员信息
	                            //更新左边的邮件数量
	                            window.parent.updateBoxEmailCount();
	                    	});
                    }
                });
            }, function () {

            })
    }
}

function getDataTable() {
	_checkedIds="";
	qytx.app.grid({
		id	:	"dataTable_wastebasket",
		url	:	basePath + "logined/email/emailWastebasketList.action",
		iDisplayLength	:	parseInt($("#iDisplayLengthHidden").val()),
		selectParam	:	{
							"searchJson":$("#searchJson").val(),
							"from":$("#from").val()
						},
		valuesFn	:	[
			                 {
			                     "aTargets": [0],//覆盖第一列
			                     "fnRender": function (oObj) {
			                         return '<input name="checkChild" value="' + oObj.aData["id"] + '" type="checkbox" />';
			                     }
			                 },
			                 {
			                     "aTargets": [2],//覆盖第3列-主题
			                     "fnRender": function (oObj) {
			                     	if(oObj.aData["readStatus"] == 1){
			                     		return getImportantLevelHtml(oObj.aData["importantLevel"]) +
			                     		"<a class=\"personMeil_readed\" href='javascript:_openEmailInNewWindow(" + oObj.aData["emailBodyId"] + ","
			                     		+ oObj.aData["id"] + ")' >" + oObj.aData["subject"] + "</a>";
			                     	}else{
			                     		return getImportantLevelHtml(oObj.aData["importantLevel"]) +
			                     		"<a class=\"personMeil_noread\" href='javascript:_openEmailInNewWindow(" + oObj.aData["emailBodyId"] + ","
			                     		+ oObj.aData["id"] + ")' >" + oObj.aData["subject"] + "</a>";
			                     	}
			                     }
			                 },
			                 {
			                     "aTargets": [3],//覆盖第四列-日期
			                     "fnRender": function (oObj) {
			                         return "<span title='" + oObj.aData['longTime'] + "'>" + oObj.aData['shortTime'] + "</span>";
			                     }
			                 }
			             ]
	});
}
var dataTable
$(document).ready(function () {	
	$.removeTableCookie('SpryMedia_DataTables_dataTable_wastebasket');
	
    initFromBtn();
    //获取邮箱列表
    getDataTable();
    //更新左边的邮件数量
    window.parent.updateBoxEmailCount();
    
    $("#dataTable_wastebasket").delegate("#checkAll", "click", function(event){    
	   	checkTotal();
		
		event.stopPropagation();
    });
	$("#dataTable_wastebasket").delegate(":checkbox[name='checkChild']", "click", function(event) {
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
	if ($('#dataTable_wastebasket :checkbox[name="checkChild"][checked="checked"]').length ==
		$('#dataTable_wastebasket :checkbox[name="checkChild"]').length){
		$("input:checkbox[id='checkAll']").prop("checked", true);
	}else{
		$("input:checkbox[id='checkAll']").prop("checked", false);
	}

}