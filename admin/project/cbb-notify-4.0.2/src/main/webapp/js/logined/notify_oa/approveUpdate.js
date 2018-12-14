var ue;
$(document).ready(function() {
	setNotifyType();
	setTimeout(function() {
		getNotifyInfo();
	}, 500);
	getNotifySetInfo();
	var fileupload = new qytx.app.fileupload({
		id:"file_upload",
		hiddenID:"attachmentId",
		queueID:"queue",
		ulName:"attachmentList",
		deleteFun:function(id,path){
			var attachmentIdAll = $("#attachmentId").val();
			attachmentIdAll = attachmentIdAll.replace("," + id + ",", ",");
			$("#attachmentId").val(attachmentIdAll);
		}
	});
})

function getNotifyInfo(){
	
	var id = $("#id").val();
	var columnId = $("#columnId").val();
	dataParam = {
		'id' : id,
		'columnId':columnId
	};
	qytx.app.ajax({
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
 * 初始公告类型
 * 
 * @return
 */
function setNotifyType() {
	var paramData = {
		'infoType' : "notifyType"+$("#columnId").val(),
		'sysTag' : 1
	};
	qytx.app.ajax({
				url : basePath + "dict/getDicts.action",
				type : "post",
				dataType : "html",
				data : paramData,
				success : function(data) {
					jsonData = eval("(" + data + ")");
					$("#notifyType").empty();
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
	qytx.app.ajax({
			type : 'post',
			url : basePath + "notify/notify_approveEdit.action",
			data : dataParam,
			dataType : 'json',
			async : false,
			shade:true,
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
	openSelectUser(3, selectAuthorCallBack, null, $("#publishScopeUserIds").val());
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
	$(data).each(function(i,item){
			ids += item.id + ',';
			names += item.name + ',';
	});
	$("#publishScopeUserIds").val(ids);
	$("#publishScopeUserNames").val(names);
	qytx.app.valid.hideError($("#publishScopeUserNames"));
}

/**
 * 清空操作
 * 
 * @param obj
 * @return
 */
function clearAuthor(obj) {
	$("#publishScopeUserIds").val('');
	$("#publishScopeUserNames").val('');
}

/**
 * 上传操作
 */
$(document).ready(function() {
	
});


/**
*加载通知公告设置信息
*/
function getNotifySetInfo(){
	var instentceid = $("#columnId").val();
	qytx.app.ajax({
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
function deleteAttachment_daily(attachmentId, domAObj) {
	$(domAObj).parent().parent().parent().remove();
	var attachmentIdAll = $("#attachmentId").val();
	attachmentIdAll = attachmentIdAll.replace("," + attachmentId + ",", ",");
	$("#attachmentId").val(attachmentIdAll);
}
//上传成功后点击删除按钮
function removeFile(target) {
	$("#" + target).remove();
	art.dialog.alert("取消上传" + target);
}
