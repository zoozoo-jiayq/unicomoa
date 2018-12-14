jQuery(document).ready(function() {
			/** 全选 */
			$("#addFile2").click(function() {
						submit_form();
						return false;
					});
			/** 全选 */
			$("aa").click(function() {

						submit_form_file();
						return false;
					});
			/** 添加 */
			$("#addFileContent").click(function() {
						rediectAddFileContent();
						return false;
					});
			$("body").show();
			setAffairCheck("文件柜", "affairsSign", "affairsTd");

		});

/**
 * 获取是否显示及选中 发送事务提醒 选项
 * 
 * @param moduleName
 * @param checkBoxId
 * @param parentId
 *            说明：调用公共方法时，在部分电脑上无法找到此方法，故暂时卸载js内部
 */
function setAffairCheck(moduleName, checkBoxId, parentId) {
	var paramData = {
		'moduleName' : moduleName
	};

	$.ajax({
				url : basePath + "affairs/setup_getAffairPriv.action",
				type : "post",
				data : paramData,
				dataType : "text",
				success : function(data) {
					if ("" != data && "error" != data) {
						var affairPrivarr = data.split("|");
						if ("1" == affairPrivarr[0]) {
							$("#" + parentId).show();
							if (affairPrivarr.length > 1
									&& "1" == affairPrivarr[1]) {
								$("#" + checkBoxId).prop("checked", true);
							}
						} else {
							$("#" + parentId).hide();
						}
					}
				}
			});
}

/***/
function addChildSort(fileSortId, path) {

	window.location.href = basePath
			+ 'logined/file/addChildSort.jsp?fileSortId=' + fileSortId
			+ "&path=" + path;
}

/**
 * @description:[提交表单]
 */
function submit_form() {

	// 框架校验
	if (!validator(document.getElementById("form1"))) {
		return;
	}
	var tixing = $("input[name='SMS_SELECT_REMIND']:checked").val();

	var SMS_SELECT_REMIND_TO_ID = $.trim($("#SMS_SELECT_REMIND_TO_ID").val());
	var subject = $.trim($("#subject").val());
	var contentId = $.trim($("#contentId").val());
	var content = $.trim(editor.html());
	var keyWord = $.trim($("#keyWord").val());
	var contentNo = $.trim($("#contentNo").val());
	var attachment = $.trim($("#appendixIds").val());
	var attachmentDesc = $.trim($("#attachmentDsec").val());
	var sortId = $.trim($("#fileSortId").val());
	var createTime = $.trim($("#createTime").val());
	var createUserId = $.trim($("#createUserId").val());
	var type = $.trim($("#type").val());
	if (null == content || '' == content) {
		showObjError($("#content"), 'file.fileContent_content_not_null');
		return;
	} else {
		hideError($("#content"));
	}
	var paramData = {
		'fileContent.subject' : subject,
		'fileContent.content' : content,
		'fileContent.keyWord' : keyWord,
		'fileContent.contentNo' : contentNo,
		'fileContent.attachment' : attachment,
		'fileContent.attachmentDesc' : attachmentDesc,
		'fileContent.contentId' : contentId,
		'sortId' : sortId,
		'tixing' : tixing,
		'SMS_SELECT_REMIND_TO_ID' : SMS_SELECT_REMIND_TO_ID,
		'createTime' : createTime,
		'createUserId' : createUserId,
		'type' : type

	};
	$.ajax({
				url : basePath + "file/addFile.action",
				type : "post",
				dataType : 'json',
				data : paramData,
				beforeSend:function(){
          			$("body").lock();
          	    },
          		complete:function(){
          			$("body").unlock();
          		},
				success : function(data) {
					var name = data.name;
					var fileSortId = data.fileSortId;
					var path = data.path;
					/*art.dialog({
								lock : true,
								background : '#000',
								opacity : 0.1,
								title : '提示',
								content : '添加成功！',
								height : 109,
								width : 317,
								icon : 'succeed',
								ok : function() {*/

									window.location.href = basePath
											+ 'logined/file/fileContentMain.jsp?name='
											+ name + '&path=' + path
											+ "&fileSortId=" + fileSortId;
								/*	return false;
								},
								close : function() {
									window.location.href = basePath
											+ 'logined/file/fileContentMain.jsp?name='
											+ name + '&path=' + path
											+ "&fileSortId=" + fileSortId;
									return false;
								}
							});*/

				}
			});
}
/**
 * @description:[提交表单]
 */
function submit_form_file() {

	// 框架校验
	if (!validator(document.getElementById("form1"))) {
		return;
	}
	var tixing = $("input[name='SMS_SELECT_REMIND']:checked").val();

	var SMS_SELECT_REMIND_TO_ID = $.trim($("#SMS_SELECT_REMIND_TO_ID").val());
	var subject = $.trim($("#subject").val());
	var contentId = $.trim($("#contentId").val());
	var content = $.trim(editor.html());
	var keyWord = $.trim($("#keyWord").val());
	var contentNo = $.trim($("#contentNo").val());
	var attachment = $.trim($("#appendixIds").val());
	var attachmentDesc = $.trim($("#attachmentDsec").val());
	var sortId = $.trim($("#fileSortId").val());
	var createTime = $.trim($("#createTime").val());
	var createUserId = $.trim($("#createUserId").val());
	var type = $.trim($("#type").val());
	if (null == content || '' == content) {
		showObjError($("#content"), 'message.file_fileContent_content_not_null');
		return;
	} else {
		hideError($("#content"));
	}
	var paramData = {
		'fileContent.subject' : subject,
		'fileContent.content' : content,
		'fileContent.keyWord' : keyWord,
		'fileContent.contentNo' : contentNo,
		'fileContent.attachment' : attachment,
		'fileContent.attachmentDesc' : attachmentDesc,
		'fileContent.contentId' : contentId,
		'sortId' : sortId,
		'tixing' : tixing,
		'SMS_SELECT_REMIND_TO_ID' : SMS_SELECT_REMIND_TO_ID,
		'createTime' : createTime,
		'createUserId' : createUserId,
		'type' : type

	};
	$.ajax({
				url : basePath + "file/updateFile.action",
				type : "post",
				dataType : 'text',
				data : paramData,
				beforeSend:function(){
          			$("body").lock();
          	    },
          		complete:function(){
          			$("body").unlock();
          		},
				success : function(data) {
					var name = data.name;
					var fileSortId = data.fileSortId;
					var path = data.path;
					art.dialog({
								lock : true,
								background : '#000',
								opacity : 0.1,
								title : '提示',
								content : '修改成功！',
								height : 109,
								width : 317,
								icon : 'succeed',
								ok : function() {

									window.location.href = basePath
											+ 'logined/file/fileContentMain.jsp?name='
											+ name + '&path=' + path
											+ "&fileSortId=" + fileSortId;
									return false;
								},
								close : function() {
									window.location.href = basePath
											+ 'logined/file/fileContentMain.jsp?name='
											+ name + '&path=' + path
											+ "&fileSortId=" + fileSortId;
									return false;
								}
							});

				}
			});
}
function downAttachment(attId) {
	window.location.href = basePath + 'upload/downfile.action?attachmentId='
			+ attId;
}

function rediectAddFileContent() {

	// var
	// ret=showModalDialog("/oa/personselect_huiyi.jsp?bmid=0",window,"status:0;help:0;edge:sunken;dialogWidth:682px;dialogHeight:390px;");

	var url = basePath + "logined/file/addfileContent.jsp";
	art.dialog.open(url, {
				id : 'addAttach',
				title : '选择附件',
				 width : 800,
				 height : 450,
				// 在open()方法中，init会等待iframe加载完毕后执行
				init : function() {
					// var iframe = this.iframe.contentWindow;
				},
				lock : true,
			    opacity: 0.08
			});

}
function deleteuser() {
	document.getElementById("SMS_SELECT_REMIND_TO_ID").value = "";
	document.getElementById("SMS_SELECT_REMIND_TO_NAME").value = "";
}

function goBack() {
	$("#input").hide();
	$("#table").show();
}