$(document).ready(function(){
	var fileupload = new qytx.app.fileupload({
		id:"file_upload",
		hiddenID:"attachmentId",
		queueID:"queue",
		ulName:"attachmentList",
		fileSizeLimit:"20MB",
		deleteFun:function(attachId,filePath){
			var attachmentIdAll = $("#attachmentId").val();
			attachmentIdAll = attachmentIdAll.replace("," + attachId + ",", ",");
			$("#attachmentId").val(attachmentIdAll);
		}
	});
});

/**
 * 添加按钮
 * 
 * @param obj
 * @return
 */
function selectUser() {
	openSelectUser(3, selectAuthorCallBack, null, $("#userIds").val());
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
	$("#userIds").val(ids);
	$("#userNames").val(names);
}

/**
 * 清空操作
 */
function clearUser() {
	$("#userIds").val('');
	$("#userNames").val('');
}