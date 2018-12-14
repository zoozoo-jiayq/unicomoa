var ue;
$(document).ready(function() {
//	ue = UE.getEditor('contentInfo', {
//				initialFrameWidth : "100%"
//	});
	setNotifyType();
	setTimeout(function() {
		getNotifyInfo();
	}, 500);
	initButton();
	getNotifySetInfo();
})

function getNotifyInfo(){
	
	var id = $("#id").val();
	var columnId = $("#columnId").val();
	dataParam = {
		'id' : id,
		'columnId':columnId
	};
	$.ajax({
				type : 'post',
				url : basePath + "notify/notify_view.action",
				data : dataParam,
				dataType : 'json',
				async : false,
				success : function(data) {
					if(data){
						$("#notifyType").val(data.notifyType);
						$("#subject").val(data.subject);
						$("#publishScopeUserIds").val(data.publishScopeUserIds);
						$("#publishScopeUserNames").val(data.publishScopeUserNames);
						$("#attachmentId").val(data.attment);
						$("#createUserId").val(data.createUser.userId);
						if (data.isTop == 1) {
							document.getElementById("isTop").checked = true;
						}else{
							document.getElementById("isTop").checked = false;
						}
						if(null!=data.images && ""!=data.images){
							$("#imageId").val(data.images);
							for(var i = 0 ; i < data.imagesList.length; i++){
								$("#imageAttachmentList").append("<li><div class=\"icon\"><em class=\""+data.imagesList[i]["attacthSuffix"]+"\"></em></div><div class=\"txt\"><p>"+data.imagesList[i]["attachName"]+"</p><p><a style=\"cursor:pointer\"  onclick=\"viewfileOpen("+data.imagesList[i]["id"]+");\">预览</a><a  style=\"cursor:pointer\"   onclick=\"deleteAttachment_daily("+data.imagesList[i]["id"]+",this);\">删除</a></p></li>");
							}
						}
						var isSmipleText=$("#isSmipleText").val();
						if(isSmipleText==0){ue.setContent(data.content);}
						else{$("#contentInfo2").html(data.content);}
						if(null!=data.attment && ""!=data.attment){
				
							for(var i = 0 ; i < data.attachmentList.length; i++){
								$("#attachmentList").append("<li><div class=\"icon\"><em class=\""+data.attachmentList[i]["attacthSuffix"]+"\"></em></div><div class=\"txt\" style=\"width:700px\"><p>"+data.attachmentList[i]["attachName"]+"</p><p><a style=\"cursor:pointer\"  onclick=\"viewfileOpen("+data.attachmentList[i]["id"]+");\">预览</a><a  style=\"cursor:pointer\"  onclick=\"deleteAttachment_daily("+data.attachmentList[i]["id"]+",this);\">删除</a></p></li>");
							}
						}
						var begDate = data.beginDate;
						var endDate = data.endDate;
						if(begDate == null || begDate == ""){							
							$("#noLimit").attr("checked",true);
							$("#showDate").hide();
						}else{
							$("#yesLimit").attr("checked",true);
							$("#showDate").show();
							begDate=data.beginDate.replace("年","-").replace("月","-");
							endDate=data.endDate.replace("年","-").replace("月","-");
							$("#begDate").val(begDate.substring(0,10));
							$("#endDate").val(endDate.substring(0,10));
						}

						$("#auditer").val(data.auditer);
						$("#summary").val(data.summary);
						
						//提醒设置
						var sendType = data.sendType;
						if(sendType != null && sendType != ""){
						/*	var typeList = sendType.split("_");
							if(typeList[0] == 1){
								$("input[name='affair']").attr("checked","checked");
								$("input[name='affair']").val(1);
							}else{
								$("input[name='affair']").attr("checked",false);
								$("input[name='affair']").val(0);
							}if(typeList[1] == 1){
								$("input[name='message']").attr("checked","checked");
								$("input[name='message']").val(1);
							}else{
								$("input[name='message']").attr("checked",false);
								$("input[name='message']").val(0);
							}
						}else{
							$("input[name='affair']").attr("checked",false);
							$("input[name='affair']").val(0);
							$("input[name='message']").attr("checked",false);
							$("input[name='message']").val(0);
						}*/
							var s = $("#hidAffair").val();
							var a = s.split("</th>");
							var b = a[1].split("</tr>")[0];
							var c = b.split("<td>");
							var d = c[1].split("</td>")[0];
							$("#sendRemind").html(d);
							var typeList = sendType.split("_");
							if(typeList[0] == 1){
								$("#affairType1").attr("checked",true);
							}else{$("#affairType1").attr("checked",false)}
							if(typeList[1] == 1){
								$("#affairType2").attr("checked",true);
							}else{$("#affairType2").attr("checked",false)}
							if(typeList[2] == 1){
								$("#affairType3").attr("checked",true);
							}else{$("#affairType3").attr("checked",false)}
						}		
					}
				}
			});
}
/**
 * 初始化按钮
 * 
 * @return
 */
function initButton() {
	var columnId = $("#columnId").val();
	$.ajax({
		type : 'post',
			url : basePath + "notify/notify_initButtion.action",
			data:{"columnId":columnId},
			dataType : 'html',
			success : function(data) {
				if (data == '1') {
					$("#tjsp").show();
				//	setAuditer();
				} else if (data == '0') {
					$("#sms_remind_tr").remove();
					$("#fb").show();
				}
			}
		});
}

//设置审批人
function setAuditer(){
	$.ajax({
		url : basePath + "notify/notify_getAuditer.action",
		type : "post",
		data:{"columnId":$("#columnId").val()},
		dataType : "html",
		success : function(data) {
			jsonData = eval("(" + data + ")");
			$("#auditer").empty();
			var auditerVal="";
			for (var i = 0; i < jsonData.length; i++) {
				auditerVal += "<option value='"
						+ jsonData[i].userId + "'>"
						+ jsonData[i].userName + "</option>";
			}
			$("#auditer").append(auditerVal);
		}
	});
}

/**
 * 初始公告类型
 * 
 * @return
 */
function setNotifyType() {
	var paramData = {
		'infoType' : "notifyType"+$("#columnId").val(),
		'sysTag' : 1
	};
	$.ajax({
				url : basePath + "dict/getDicts.action",
				type : "post",
				dataType : "html",
				data : paramData,
				success : function(data) {
					jsonData = eval("(" + data + ")");
					$("#notifyType").empty();
					$("#notifyType").append("<option value=''>请选择</option>");
					for (var i = 0; i < jsonData.length; i++) {
						$("#notifyType").append("<option value='"
								+ jsonData[i].value + "'>" + jsonData[i].name
								+ "</option>");
					}
				}
			});
}


/**
 * 提交操作
 * 
 * @param status
 * @return
 */
function tijiao(status) {
	// 框架校验
	if (!validator(document.getElementById("form1"))) {
		return;
	}
	var id = $("#id").val();
	var subject = $("#subject").val();
	var limitNum = $("input[name='limit']:checked").val();
	var begDate = "";
	var endDate = "";
	if(limitNum==0){
		begDate = $("#begDate").val();
		endDate = $("#endDate").val();
	}
//	var begDate = $("#begDate").val();
//	var endDate = $("#endDate").val();
	var isTop = document.getElementById("isTop").checked == true?1:0;
	var auditer = $("#auditer").val();
	var summary = $("#summary").val();
	var attment = $("#attachmentId").val();
	if (null == attment || "," == attment || "" == attment) {
		attment = "";
	}
	var images = $("#imageId").val();
	if(images==null||images==","||images==""){
		images = "";
	}
	var publishScopeUserIds = $("#publishScopeUserIds").val();
	var publishScopeUserNames = $("#publishScopeUserNames").val();
	if (isObjEmpty(publishScopeUserIds)) {
		showObjError($("#publishScopeUserNames"), 'notify.notify_userName_not_null');
		return;
	} else {
		hideError($("#publishScopeUserNames"));
	}
	var notifyType = $("#notifyType").val();
	if($.trim(notifyType)==""){
		art.dialog.alert("请选择公告类型");
		return false;
	}
//	var content = ue.getContent();
	var isSmipleText=$("#isSmipleText").val();
	var content
	if(isSmipleText==0){
	   content=ue.getContent();
	   if (null == content || '' == content) {
			showObjError($("#contentInfoInput"), 'notify.notify_content_not_null');
			return;
		} else {
			hideError($("#contentInfoInput"));
		}
	}else{
		content = $("#contentInfo2").val();
		if (null == content || '' == content) {
			showObjError($("#contentInfo2"), 'notify.notify_content_not_null');
			return;
		}else if(content.length > 1000){
			showObjError($("#contentInfo2"), 'notify.notify_content_length');
			return;
		} else {
			hideError($("#contentInfo2"));
		}	
	}
	var columnId = $("#columnId").val();
	var createUserId = $("#createUserId").val();
	dataParam = {
	    'notify.id':id,
		'notify.subject' : subject,
		'notify.content' : content,
		'notify.summary' : summary,
		'startDateStr':begDate,
		'endDateStr' : endDate,
		'notify.notifyType' : notifyType,
		'notify.isTop' : isTop,
		'notify.status':status,
		'notify.auditer':auditer,
		'notify.attment' : attment,
		'notify.publishScopeUserIds':publishScopeUserIds,
		'notify.publishScopeUserNames':publishScopeUserNames,
		'notify.columnId':columnId,
		'notify.images':images,
		'notify.viewCount':0,
		'notify.createUser.userId':createUserId,
		'notify.sendType':getSendType()
	};
	$.ajax({
			type : 'post',
			url : basePath + "notify/notify_approveEdit.action",
			data : dataParam,
			dataType : 'json',
			async : false,
		    beforeSend:function(){
				$("body").lock();
			},
			complete:function(){
				$("body").unlock();
			},
			success : function(data) {
				window.location.href=basePath+"logined/notify/viewApproveNoList.jsp?id="+$("#columnId").val();
			}
	});
}

/**
 * 判断是否为空
 * 
 * @param obj
 * @return
 */
function isObjEmpty(obj) {
	if (obj == null || obj == "") {
		return true;
	}
	return false;
}

/**
 * 添加按钮
 * 
 * @param obj
 * @return
 */
function selectAuthor(obj) {
	if (obj == 'group') {
		openSelectUser(1, selectAuthorCallBack, null, $("#groupId").val(),'notify');
	} else if (obj == 'role') {
		openSelectUser(2, selectAuthorCallBack, null, $("#roleId").val(),'notify');
	} else if (obj == 'user') {
		openSelectUser(3, selectAuthorCallBack, null, $("#publishScopeUserIds").val(),'notify');
	}
}

/**
 * 添加按钮(回调函数)
 * 
 * @param data
 * @return
 */
function selectAuthorCallBack(data) {
	var ids = '';
	var names = '';
	var type = '';
	data.forEach(function(value, key) {
				ids += value.Id + ',';
				names += value.Name + ',';
				type = value.Type;
			});
	if (type == 'group') {
		$("#groupId").val(ids);
		$("#groupName").val(names);
	} else if (type == 'user') {
		$("#publishScopeUserIds").val(ids);
		$("#publishScopeUserNames").val(names);
	} else if (type == 'role') {
		$("#roleId").val(ids);
		$("#roleName").val(names);
	}
}

/**
 * 清空操作
 * 
 * @param obj
 * @return
 */
function clearAuthor(obj) {
	if (obj == 'group') {
		$("#groupId").val('');
		$("#groupName").val('');
	} else if (obj == 'role') {
		$("#roleId").val('');
		$("#roleName").val('');
	} else if (obj == 'user') {
		$("#publishScopeUserIds").val('');
		$("#publishScopeUserNames").val('');
	}
}

/**
 * 上传操作
 */
$(document).ready(function() {
	$("#file_upload").uploadify({
		// 和存放队列的DIV的id一致
		'queueID' : 'queue',
		// 服务器端脚本使用的文件对象的名称 $_FILES个['upload']
		'fileObjName' : 'fileupload',
		// 上传处理程序
		'uploader' : basePath +  'filemanager/uploadfile.action?module=notify',
		// 按钮文字
		'buttonText' : '上传附件...',
		// 附带值
		'formData' : {
			'userid' : '用户id',
			'username' : '用户名',
			'rnd' : '加密密文'
		},
		// 浏览按钮的背景图片路径
		'buttonImage' : basePath + 'flat/images/upload.png',
		// 取消上传文件的按钮图片，就是个叉叉
		'cancel' : basePath + 'plugins/upload/upbutton.png',
		// 浏览按钮的宽度
		'width' : '100',
		// 浏览按钮的高度
		'height' : '25',
		// 在浏览窗口底部的文件类型下拉菜单中显示的文本
		'fileTypeDesc' : '支持的格式:',
		// 允许上传的文件后缀
		'fileTypeExts' : '*.*',
		// 上传文件的大小限制
		'fileSizeLimit' : '10MB',
		// 上传数量
		'queueSizeLimit' : 25,
		// 开启调试
		'debug' : false,
		// 是否自动上传
		'auto' : true,
		// 上传后是否删除
		'removeComplete' : false,
		// 清除
		removeTimeout : 0,

		langFile : basePath + 'plugins/upload/ZH_cn.js',

		// 超时时间
		'successTimeout' : 99999,
		// flash
		'swf' : basePath + 'plugins/upload/uploadify.swf',
		// 不执行默认的onSelect事件
		'overrideEvents' : ['onDialogClose'],
		// 每次更新上载的文件的进展
		'onUploadProgress' : function(file, bytesUploaded, bytesTotal,
				totalBytesUploaded, totalBytesTotal) {
			// 有时候上传进度什么想自己个性化控制，可以利用这个方法
			onUploadProgressHelp(file, totalBytesUploaded, totalBytesTotal);
		},
		// 选择上传文件后调用
		'onSelect' : function(file) {
			if(file.name.length > 30 ){
				art.dialog.alert("附件名称过长，上传失败");
				return false;
			}else{
				onSelectHtml(file, "attachmentList", "file_upload");
			}
			return true;
		},
		// 返回一个错误，选择文件的时候触发
		'onSelectError' : function(file, errorCode, errorMsg) {
			switch (errorCode) {
				case -100 :
					art.dialog.alert.dialog.alert("上传的文件数量已经超出系统限制的"
							+ $('#file_upload').uploadify('settings',
									'queueSizeLimit') + "个文件！");
					break;
				case -110 :
					art.dialog.alert.dialog.alert("文件 ["
							+ file.name
							+ "] 大小超出系统限制的"
							+ $('#file_upload').uploadify('settings',
									'fileSizeLimit') + "大小！");
					break;
				case -120 :
					art.dialog.alert.dialog.alert("文件 [" + file.name + "] 大小异常！");
					break;
				case -130 :
					art.dialog.alert.dialog.alert("文件 [" + file.name + "] 类型不正确！");
					break;
			}
		},
		// 检测FLASH失败调用
		'onFallback' : function() {
			art.dialog.alert("您未安装FLASH控件，无法上传图片！请安装FLASH控件后再试。");
		},
		// 上传到服务器，服务器返回相应信息到data里
		'onUploadSuccess' : function(file, data, response) {
			data = eval("(" + data + ")");
			var attachmentId = $("#attachmentId").val();
			if (attachmentId) {
				attachmentId = attachmentId + data.id + ",";
			} else {
				attachmentId = "," + data.id + ",";
			}
			$("#attachmentId").val(attachmentId);
			onUploadSuccessHelp(file,null,"file_upload",data.id);
		},
		// 上传前取消文件
		'onCancel' : function(file) {
			// art.dialog.alert('The file ' + file.name + ' was cancelled.');
		}
	});
	/**
	 * 图片上传
	 */
	$("#image_upload").uploadify({
		// 和存放队列的DIV的id一致
		'queueID' : 'queueImage',
		// 服务器端脚本使用的文件对象的名称 $_FILES个['upload']
		'fileObjName' : 'fileupload',
		// 上传处理程序
		'uploader' : basePath + 'filemanager/uploadfile.action?module=notify',
		// 按钮文字
		'buttonText' : '上传图片...',
		// 附带值
		'formData' : {
			'userid' : '用户id',
			'username' : '用户名',
			'rnd' : '加密密文'
		},
		// 浏览按钮的背景图片路径
		'buttonImage' : basePath + 'plugins/upload/uploadfile.png',
		// 取消上传文件的按钮图片，就是个叉叉
		'cancel' : basePath + 'plugins/upload/upbutton.png',
		// 浏览按钮的宽度
		'width' : '100',
		// 浏览按钮的高度
		'height' : '25',
		// 在浏览窗口底部的文件类型下拉菜单中显示的文本
		'fileTypeDesc' : '支持的格式:',
		// 允许上传的文件后缀
		'fileTypeExts' : '*.bmp;*.jpg;*.gif;*.png',
		// 上传文件的大小限制
		'fileSizeLimit' : '10MB',
		// 上传数量
		'queueSizeLimit' : 25,
		// 开启调试
		'debug' : false,
		// 是否自动上传
		'auto' : true,
		// 上传后是否删除
		'removeComplete' : false,
		// 清除
		removeTimeout : 0,

		langFile : basePath + 'plugins/upload/ZH_cn.js',

		// 超时时间
		'successTimeout' : 99999,
		// flash
		'swf' : basePath + 'plugins/upload/uploadify.swf',
		// 不执行默认的onSelect事件
		'overrideEvents' : ['onDialogClose'],
		// 每次更新上载的文件的进展
		'onUploadProgress' : function(file, bytesUploaded, bytesTotal,
				totalBytesUploaded, totalBytesTotal) {
			// 有时候上传进度什么想自己个性化控制，可以利用这个方法
			onUploadProgressHelp(file, totalBytesUploaded, totalBytesTotal);
		},
		// 选择上传文件后调用
		'onSelect' : function(file) {
			onSelectHtml(file, "imageAttachmentList", "image_upload");
			return true;
		},
		// 返回一个错误，选择文件的时候触发
		'onSelectError' : function(file, errorCode, errorMsg) {
			switch (errorCode) {
				case -100 :
					art.dialog.alert.dialog.alert("上传的文件数量已经超出系统限制的"
							+ $('#file_upload').uploadify('settings',
									'queueSizeLimit') + "个文件！");
					break;
				case -110 :
					art.dialog.alert.dialog.alert("文件 ["
							+ file.name
							+ "] 大小超出系统限制的"
							+ $('#file_upload').uploadify('settings',
									'fileSizeLimit') + "大小！");
					break;
				case -120 :
					art.dialog.alert.dialog.alert("文件 [" + file.name + "] 大小异常！");
					break;
				case -130 :
					art.dialog.alert.dialog.alert("文件 [" + file.name + "] 类型不正确！");
					break;
			}
		},
		// 检测FLASH失败调用
		'onFallback' : function() {
			art.dialog.alert("您未安装FLASH控件，无法上传图片！请安装FLASH控件后再试。");
		},
		// 上传到服务器，服务器返回相应信息到data里
		'onUploadSuccess' : function(file, data, response) {
			var imageId = $("#imageId").val();
			data = eval("(" + data + ")");
			if (imageId) {
				imageId = imageId + data.id + ",";
			} else {
				imageId = "," + data.id + ",";
			}
			$("#imageId").val(imageId);
			onUploadSuccessHelp(file,null,"image_upload",data.id);
		},
		// 上传前取消文件
		'onCancel' : function(file) {
			// art.dialog.alert('The file ' + file.name + ' was cancelled.');
		}
	});
});

function deleteAttachment_daily(attachmentId, domAObj) {
	$(domAObj).parent().parent().parent().remove();
	var attachmentIdAll = $("#attachmentId").val();
	attachmentIdAll = attachmentIdAll.replace("," + attachmentId + ",", ",");
	$("#attachmentId").val(attachmentIdAll);
}

// 上传成功后点击删除按钮
function removeFile(target) {
	$("#" + target).remove();
	art.dialog.alert("取消上传" + target);
}

/**
*加载通知公告设置信息
*/
function getNotifySetInfo(){
	var instentceid = $("#columnId").val();
	$.ajax({
		type : 'post',
		url : basePath + "/notify/notify_loadSetInfo.action",
		data:{"columnId":instentceid},
		dataType : 'json',
		success : function(result) {
			$("#isSmipleText").val(result.isSmipleText);
			if(result.isSmipleText==0){
				$("#text1").show();
				ue = UE.getEditor('contentInfo', {
				initialFrameWidth : "100%"
				});
			}else{$("#text2").show();}
			}
	});
}
function goBack(){
	window.location.href=basePath+'/logined/notify/viewApproveNoList.jsp?id='+$("#columnId").val();
}
