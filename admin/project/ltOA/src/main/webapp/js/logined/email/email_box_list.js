$(document).ready(function () {
    //更新左边自定义邮件箱列表
    window.parent.updateDiyBoxList();
    //获取邮箱列表
    getDataTable();
});
/**
 * 功能:邮件箱列表页面处理脚本
 * 版本:1.0
 */

/**
 * 新建邮件箱表单提交前的校验
 * @return {boolean}
 */
function checkEmailBoxForm() {
    if (validator(document.emailBoxForm)) {
        saveEmailBox();
    }
    return false;
}

/**
 * 新增
 */
function  toAddEmailBox(){
	qytx.app.dialog.openUrl({
		url	:	basePath + "logined/email/addDiyBox.jsp",
		title	:	"新增文件夹",
		size	:	"M",
		id 	: 	"addDiyBox",
		ok : function(){
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
 * 编辑
 */
function  toUpdateEmailBox(id){
	qytx.app.dialog.openUrl({
		id	:	"updateDiyBox",
		title 	: 	"修改文件夹",
		size	:	"M",
		url	:	basePath + "logined/email/emailBoxEdit.action?id="+ id,
		ok : function(){
	    	var iframe = this.iframe.contentWindow;
	    	// 序号
	    	var orderNo = $(iframe.document).find("#orderNo").val();
	    	// 邮箱id
	    	var emailBoxId = $(iframe.document).find("#emailBoxId").val();
	    	// 名称
	    	var boxName = $(iframe.document).find("#boxName").val();
	    	if (null == boxName || "" == boxName){
	    		showObjError($(iframe.document).find("#boxName"), 'email.email_box_name_not_null');
	    		return false;
	    	}
	    	updateEmailBox(orderNo, boxName,emailBoxId);
	    	return false;
	    }
	});
}



/**
 * 返回
 * 
 * @return
 */
function goback() {
	var url=basePath+"logined/email/emailBoxList.jsp";
	window.document.location = url;
}
/**
 * 保存一个新的邮件箱
 */
function saveEmailBox() {
    qytx.app.ajax({
        url: basePath + "logined/email/emailBoxSave.action"+"?_r=" + getOaRandom(),
        type: "post",
        dataType: 'text',
        data: $("form[name='emailBoxForm']").serialize(),
        success: function (data) {
            if (data == "") {
//                art.dialog.alert(sprintf("email.email_box_add_success"),function (){
            	goback() ;

//                });
//                $("form[name='emailBoxForm'] input").val("");
//                //更新左边自定义邮件箱列表
//                window.parent.updateDiyBoxList();
                //getDataTable();   //刷新邮件箱信息
//                art.dialog.alert("新建文件夹成功！",function (){
//                	var url=basePath+"logined/email/emailBoxList.jsp";
//                	window.parent.reload();
//                });
                
            } else {
            	qytx.app.dialog.alert(data);
            }
        }
    })
}

/**
 * 获取邮件箱列表
 */
function getDataTable() {
	qytx.app.grid({
		id	:	"dataTable",
		url	:	basePath + "logined/email/emailBoxList.action",
		bInfo:false,
		valuesFn	:	[
							{"aTargets": [1], //覆盖第四列-空间占用
								"fnRender": function (oObj) {
								  var size = parseFloat(oObj.aData["boxSize"]) / 1024 / 1024;
								  size = size.toFixed(2);
								  return oObj.aData["boxSize"] + "字节 (约合" + size + " MB)";
								}
							},
							{"aTargets": [2], //覆盖第四列-每页邮件显示数
								"fnRender": function (oObj) {
								  var html = "<input type='text' size='6' maxlength='3' id='pageMax_" + oObj.aData["id"] + "' class='formText numberKeyUp' value='" + oObj.aData["pageMax"] + "'/>";
								//  html += "<input type='button' class='formButton ml5' onclick='changePageMax(" + oObj.aData["id"] + ")' value='保存' />";
								  return html;
								}
							},
							{"aTargets": [3], //覆盖第5列-操作
								"fnRender": function (oObj) {
									var saveHTML="<a href='javascript:void(0)' onclick='changePageMax(" + oObj.aData["id"] + ")'>保存</a>";
								  if (oObj.aData["boxType"] == 5) {//此处已经被上面的覆盖第三列修改为“自定义”而非5
								      var editHTML = "<a href='javascript:void(0);' onclick='toUpdateEmailBox("+oObj.aData["id"]+")' >修改</a>";
								      var deleteHTML = "<a href='javascript:void(0)' onclick='confirmDelete(" + oObj.aData["id"] + ")' >删除</a>";
								      return saveHTML + editHTML + deleteHTML;
								  } else {
								      return saveHTML;
								  }
								}
							}
						]
	});
}

/**
 * 修改指定邮件箱的每页显示数
 * @param boxId 邮件箱ID
 */
function changePageMax(boxId) {
    var pageMax = $("#pageMax_" + boxId).val();
    var isNumber = /^[-\+]?\d+(\.\d+)?$/;
    if (!isNumber.test(pageMax)) {
    	qytx.app.dialog.alert(sprintf("email.email_box_page_max_must_be_number"));
        return;
    }
    pageMax = parseInt($.trim(pageMax));
    if (pageMax <= 0) {
    	qytx.app.dialog.alert(sprintf("email.email_box_per_page_max_size_must_over_zero"));
    } else {
    	qytx.app.ajax({
            url: basePath + "logined/email/emailBoxChangePageMax.action"+"?_r=" + getOaRandom(),
            type: "post",
            dataType: 'text',
            data: {pageMax: pageMax, id: boxId},
            success: function (data) {
            	if(data.indexOf("成功")>0){
            		qytx.app.dialog.tips(data, function(){
            			getDataTable();   //刷新邮件箱信息
            		},2);
            	}else{
            		art.dialog.alert(data);
                    getDataTable();   //刷新邮件箱信息
            	}
                
            }
        })
    }
}

/**
 * 删除一个邮件箱
 * @param id 邮件箱ID
 * @return {boolean} false 使超链接不发送跳转动作
 */
function confirmDelete(id) {
    qytx.app.dialog.confirm(("确认要删除吗？"), function () {
        //ajax删除邮件箱操作
    	qytx.app.ajax({
            url: basePath + "logined/email/emailBoxDelete.action",
            type: "post",
            dataType: 'text',
            data: {id: id},
            success: function (data) {
                if (data == "") {
                    //art.dialog.alert(sprintf("email.email_box_delete_success"));
                    getDataTable();   //刷新人员信息
                    //更新左边的邮件数量
                    window.parent.updateBoxEmailCount();
                    window.parent.deleteEmailBox(id);
                } else {
                	qytx.app.dialog.alert(data);
                }
            }
        });
    }, function () {
    });
    return false;
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
            	qytx.app.dialog.tips(sprintf("email.email_box_add_success"), function(){
														            			   //更新左边自定义邮件箱列表
														                           window.parent.updateDiyBoxList();
														                           // 刷新本页面
														                           window.location.reload(true);
														            			},2);
            } else {
            	art.dialog.list["addDiyBox"].hide();
            	qytx.app.dialog.alert(data,function(){
                	art.dialog.list["addDiyBox"].show();
                });
            }
        }
    });
}

/**
 * 修改一个邮箱
 */
function updateEmailBox(orderNo, boxName,emailBoxId){
	var paramData={
			'emailBox.orderNo':$.trim(orderNo),
			'emailBox.boxName':$.trim(boxName),
			'emailBox.id':$.trim(emailBoxId)
	 };
	
	qytx.app.ajax({
        url: basePath + "logined/email/emailBoxUpdate.action"+"?_r=" + getOaRandom(),
        type: "post",
        dataType: 'text',
        data: paramData,
        success: function (data) {
            if (data == "") {
            	art.dialog.list["updateDiyBox"].close();
            	qytx.app.dialog.tips(sprintf("email.email_box_update_success"), function(){
																	            		   //更新左边自定义邮件箱列表
																                           window.parent.updateDiyBoxList();
																                           // 刷新本页面
																                           window.location.reload(true);
																	            	},2);
                
            } else {
            	art.dialog.list["updateDiyBox"].hide();
            	qytx.app.dialog.alert(data,function(){
                	art.dialog.list["updateDiyBox"].show();
                });
            }
        }
    });

}

