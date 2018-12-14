


/**
 * 回复、全部回复
 * @param {boolean} isReplyAll：全部回复时设置为true
 */
function replyOpenedEmailTo(isReplyAll) {
    var replyAll = 0;
    if (isReplyAll) {
        replyAll = 1;
    }
    var emailToId = _getOpenedRowId();
    var url = basePath + "logined/email/emailToReply.action?emailToId=" + emailToId + "&replyAll=" + replyAll;
    openURLForEmail(url);
}


/**
 * 删除所有已读取邮件
 */
function deleteAllReadedEmailTo() {
    qytx.app.dialog.confirm(sprintf("email.confirm_delete_this_page_read"), function () {
        qytx.app.ajax({
            url: basePath + "logined/email/emailToReadedDelete.action"+"?_r=" + getOaRandom(),
            type: "post",
            dataType: 'text',
            data: {
                targetBoxId: $("#emailBoxIdHidden").val()
            },
            success: function (data) {
            	qytx.app.dialog.success(sprintf("email.delete_email_success&&" + data + "##已读取邮件"),function(){
            		getDataTable(); //刷新人员信息
                    //更新左边的邮件数量
                    window.parent.updateBoxEmailCount();
            	});
            }
        });
    }, function () {

    });
}

/**
 * 删除选中的邮件
 */
function deleteEmailToChecked() {
    if (_checkedIds==null|| _checkedIds=="") {
        qytx.app.dialog.alert(sprintf("email.no_selected_email&&删除"));
    } else {
        var emailToIds = "";
        if (_checkedIds!=null&&_checkedIds!="") {
        	emailToIds = _checkedIds.substring(0, _checkedIds.length - 1);
    	}
        qytx.app.dialog.confirm(sprintf("email.confirm_delete_selected_email"), function () {
            //删除选中的记录ajax
            _deleteEmailTo(emailToIds);
        }, function () {

        });
    }
}

/**
 * 删除已经打开的邮件
 */
function deleteEmailToOpened() {

    qytx.app.dialog.confirm(sprintf("email.confirm_delete_this_email"), function () {
        _deleteEmailTo(_getOpenedRowId());
    }, function () {

    });
}

/**
 * 删除邮件子方法
 * @param {Object} emailToIds 邮件id，英文逗号分割
 */
function _deleteEmailTo(emailToIds) {
    qytx.app.ajax({
        url: basePath + "logined/email/emailToDelete.action",
        type: "post",
        dataType: 'text',
        data: {
            "emailToIds": emailToIds
        },
        success: function (data) {
          //  art.dialog.alert(data);
            getDataTable(); //刷新人员信息
            //更新左边的邮件数量
            window.parent.updateBoxEmailCount();
        }
    });
}

/**
 * 选中的邮件标记为已读取
 */
function readedEmailToChecked() {

    if (_checkedIds==null|| _checkedIds=="") {
        qytx.app.dialog.alert(sprintf("email.no_selected_email&&标记"));
    } else {
    	var emailToIds = "";

    	if (_checkedIds!=null&&_checkedIds!="") {
    		emailToIds = _checkedIds.substring(0, _checkedIds.length - 1);
    	}
    	_readedEmailTo(emailToIds);
//        art.dialog.confirm(sprintf("email.confirm_flag_read_selected"), function () {
//        }, function () {
//
//        });
    }
}

/**
 * 所有的邮件标记为已读取
 */
function readedEmailToAll() {
    qytx.app.dialog.confirm(sprintf("email.confirm_flag_read_all"),
        function () {
            qytx.app.ajax({
                url: basePath + "logined/email/emailToReadedAll.action"+"?_r=" + getOaRandom(),
                type: "post",
                dataType: 'text',
                data: {
                    targetBoxId: $("#emailBoxIdHidden").val()
                },
                success: function (data) {
                	qytx.app.dialog.success(sprintf("email.flag_read_all_success&&" + data),function(){
                		 getDataTable(); //刷新人员信息
                         //更新左边的邮件数量
                         window.parent.updateBoxEmailCount();
                	});
                }
            });
        },
        function () {
        });
}

/**
 * 标记邮件为已读取 子方法
 * @param {Object} emailToIds 邮件id，英文逗号分割
 */
function _readedEmailTo(emailToIds) {
    qytx.app.ajax({
        url: basePath + "logined/email/emailToReaded.action",
        type: "post",
        dataType: 'text',
        data: {
            emailToIds: emailToIds
        },
        success: function (data) {
        	getDataTable(); //刷新人员信息
        	//更新左边的邮件数量
        	window.parent.updateBoxEmailCount();
          
        }
    });
}

/**
 * 转发选中的邮件
 */
function forwardEmailToChecked() {

    if (_checkedIds==null|| _checkedIds=="") {
        qytx.app.dialog.alert(sprintf("email.no_selected_email&&转发"));
        return;
    }
    var checkedCount = _checkedIds.split(",").length-1;
    var emailToIds = "";

	if (_checkedIds!=null&&_checkedIds!="") {
		emailToIds = _checkedIds.substring(0, _checkedIds.length - 1);
	}
    var forwardURL = _getEmailToForwardUrl(emailToIds);
    if (checkedCount > 1) {
        qytx.app.dialog.alert(sprintf("email.alert_only_can_forward_one_email"));
        //后续做批量转发
    } else {
        //转发邮件
        openURLForEmail(forwardURL);
    }
}

/**
 * 获取邮件转发地址，带参数
 * @param {Object} emailToIds 转发邮件的ID组合
 */
function _getEmailToForwardUrl(emailToIds) {
    return basePath + "logined/email/emailToForward.action?emailToIds="
        + emailToIds;
}

/**
 * 转发打开的当前的邮件
 */
function forwardEmailToOpened() {
    var emailToIds = _getOpenedRowId();
    openURLForEmail(_getEmailToForwardUrl(emailToIds));
}

/**
 * 添加自定义文件夹
 */
function addDiyBox(){
				
	qytx.app.dialog.openUrl({
		id	:	"addDiyBox",
		url	:	basePath + "logined/email/addDiyBox.jsp",
		title:	"新增文件夹",
		size:	"M",
		ok	:	function(){
			    	var iframe = this.iframe.contentWindow;
			    	// 序号
			    	var orderNo = $(iframe.document).find("#orderNo").val();
			    	
			    	// 名称
			    	var boxName = $(iframe.document).find("#boxName").val();
			    	if (null == boxName || "" == boxName){
			    		showObjError($(iframe.document).find("#boxName"), 'email.email_box_name_not_null');
			    		return false;
			    	}
			    	 
			    	saveDiyBox(orderNo, boxName);
			    	return false;
				}
		});
	}

/**
 * 移动邮件到某个邮件箱
 * targetBoxId 目标邮件箱ID
 */
function moveEmailToChecked(targetBoxId, targetBoxName) {
    if (targetBoxId != "0") {
        if (_checkedIds!=null&&_checkedIds!="") {
            var emailToIds =  _checkedIds.substring(0, _checkedIds.length - 1);
            qytx.app.dialog.confirm(sprintf("email.confirm_move_email_to&&" + targetBoxName), function () {
                //移动邮件
                qytx.app.ajax({
                    url: basePath + "logined/email/emailToMove.action",
                    type: "post",
                    dataType: 'text',
                    data: {
                        emailToIds: emailToIds,
                        targetBoxId: targetBoxId
                    },
                    success: function (data) {
                    	qytx.app.dialog.success(data+"！",function(){
                    		getDataTable(); //刷新人员信息
                            //更新左边的邮件数量
                            window.parent.updateBoxEmailCount();
                    	});
                    }
                });
            }, function () {
                //取消
            });
        } else {
            qytx.app.dialog.alert(sprintf("email.no_selected_email&&移动"));
        }
    }
}

/**
 * 修改邮件星级
 */
function changMarkLevel(emailToId, markLevel, obj) {
    qytx.app.ajax({
        url: basePath + "logined/email/emailToChangeMarkLevel.action"+"?_r=" + getOaRandom(),
        type: "post",
        dataType: 'text',
        data: {
            emailToId: emailToId,
            markLevel: markLevel
        },
        shade:false,
        success: function (data) {
            var cls = "morebtn " + $(obj).attr("class");
            $("#markColorSpan_" + emailToId).attr("class", cls);
        }
    });
}

function getDataTable(){
	initNotReadPage();
	_checkedIds="";
	this.qytx.app.grid({
		id	:	'dataTable_toList',
		url	:	basePath + "logined/email/emailToList.action",
		iDisplayLength:	parseInt($("#iDisplayLengthHidden").val()),
		selectParam:{
					"emailBoxId":$("#emailBoxIdHidden").val(),
					"searchWord":$.trim($("#searchWord").val()),
					"notRead":$("#notReadHidden").val(),
					"searchJson":$("#searchJson").val(),
					"from":$("#from").val()
				},
		valuesFn:[
	                  {
	                      "aTargets": [ 0 ],//覆盖第一列
	                      "fnRender": function (oObj) {
	                          return '<input name="checkChild" value="' + oObj.aData.id + '" type="checkbox" />';
	                      }
	                  },
	                  {
	                      "aTargets": [1],//覆盖第二列，主题
	                      "fnRender": function (oObj) {
	                      	return oObj.aData["fromUserName"];
	                      }
	                  },
	                  {
	                      "aTargets": [2],//覆盖第三列，主题
	                      "fnRender": function (oObj) {
	                      	if(oObj.aData["readStatus"] == 1){
	                      		return getImportantLevelHtml(oObj.aData["importantLevel"])
	                              + "<a class=\"personMeil_readed\" href='javascript:_openEmailInNewWindow" + "(" + oObj.aData["emailBodyId"] + ","
	                              + oObj.aData["id"] + ")' >" + oObj.aData["subject"] + "</a>" ;
	                      	}else{
	                      		return getImportantLevelHtml(oObj.aData["importantLevel"])
	                              + "<a class=\"personMeil_noread\" href='javascript:_openEmailInNewWindow" + "(" + oObj.aData["emailBodyId"] + ","
	                              + oObj.aData["id"] + ")' >" + oObj.aData["subject"] + "</a>" ;
	                      	}
	                      }
	                  },
	                  {
	                      "aTargets": [3],//覆盖第三列，主题
	                      "fnRender": function (oObj) {
	                      		return oObj.aData["content"];
	                      }
	                  },
	                  {
	                      "aTargets": [ 4 ],//覆盖第四列-日期
	                      "fnRender": function (oObj) {
	  	                	return "<span title='" + oObj.aData['longTime'] + "'>" + oObj.aData['shortTime'] + "</span>";
	                      }
	                  }
	              ]			
	});
}

$(document).ready(function () {
	$.removeTableCookie('SpryMedia_DataTables_dataTable_toList');
	//回车事件
	$(document).keydown(function(event){
		var code=event.which;
		if (code == 13) {  
			//获取邮箱列表
			getDataTable();
			return false;
		}
	});
	
    initFromBtn();
    //获取邮箱列表
    getDataTable();
    //更新左边的邮件数量
    window.parent.updateBoxEmailCount();
    
	
    // enter键查询
	$("html").die().live("keydown", function(event) {
		if (event.keyCode == 13) {
			$.removeTableCookie('SpryMedia_DataTables_dataTable_toList_emailToListPage.action');
			getDataTable();
		}
	});
	
	$("#dataTable_toList").delegate("#checkAll", "click", function(event){    
	   	checkTotal();
		event.stopPropagation();
    });
	$("#dataTable_toList").delegate(":checkbox[name='checkChild']", "click", function(event) {
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
	if ($('#dataTable_toList :checkbox[name="checkChild"][checked="checked"]').length ==
		$('#dataTable_toList :checkbox[name="checkChild"]').length){
		$("input:checkbox[id='checkAll']").prop("checked", true);
	}else{
		$("input:checkbox[id='checkAll']").prop("checked", false);
	}
}

/**
 * 针对 未读取 进行初始化，隐藏一些按钮
 */
function initNotReadPage() {
    if ($("#notReadHidden").val() == "1") {
        $("#notReadExclude").hide();
    }
    
    qytx.app.ajax({
    	url	:	basePath+"logined/email/getNotReadCount.action?_r=" + getOaRandom(),
    	type:	"post",
    	data:	{"targetBoxId":$("#emailBoxIdHidden").val()},
    	shade:	false,
    	dataType:"text",
    	success:function(data){
    		if(data<=0){
    			$("#readAllButton").addClass("no");
    	    	$("#readAllButton").attr("disabled","disabled");
    		}else{
    			$("#readAllButton").removeClass("no");
    	    	$("#readAllButton").removeAttr("disabled");
    		}
    	}
    });
    
}

/**
 * 初始化星级图标框浮动
 */
function initStarPop() {
    $(".morebtn").hover(function () {
        $(this).addClass("morebtnHover");
    }, function () {
        $(this).removeClass("morebtnHover");
    });//更多操作
}


/**
 * 保存一个新的邮件箱
 */
function saveDiyBox(orderNo, boxName) {
	var paramData={
			'emailBox.orderNo':$.trim(orderNo),
			'emailBox.boxName':$.trim(boxName)
	 };
	
    qytx.app.ajax({
        url: basePath + "logined/email/emailBoxSave.action"+"?_r=" + getOaRandom(),
        type: "post",
        dataType: 'text',
        data: paramData,
        success: function (data) {
            if (data == "") {
            	art.dialog.list["addDiyBox"].close();
            	qytx.app.dialog.success(sprintf("email.email_box_add_success"),function(){
            		//更新左边自定义邮件箱列表
                    window.parent.updateDiyBoxList();
                    // 刷新本页面
                    window.location.reload(true);
            	});
            } else {
            	art.dialog.list["addDiyBox"].hide();
                qytx.app.dialog.alert(data,function(){
                	art.dialog.list["addDiyBox"].show();
                });
            }
        }
    });
}