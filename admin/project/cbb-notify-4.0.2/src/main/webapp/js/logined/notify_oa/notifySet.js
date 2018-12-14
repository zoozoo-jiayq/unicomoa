$(document).ready(function(){
	getNotifySetInfo();
});

/**
*加载通知公告设置信息
*/
function getNotifySetInfo(){
	var instentceid = $("#instentceid").val();
	$.ajax({
		type : 'post',
		url : basePath + "/notify/notify_loadSetInfo.action",
		data:{"columnId":instentceid},
		dataType : 'json',
		success : function(result) {
			if(result){
				setRadioSelect("isComment",result.isComment);
				setRadioSelect("isAuditing",result.isAuditing);
				setRadioSelect("isSeeAttach",result.isSeeAttach);
				setRadioSelect("showImage",result.showImage);
				setRadioSelect("isEdit",result.isEdit);
				setRadioSelect("isSmipleText",result.isSmipleText);
				setRadioSelect("isDataFilter",result.isDataFilter);
				setRadioSelect("clientType",result.clientType);
				$("#publishUserIds").val(result.publishUserIds);
				$("#publishUserNames").val(result.publishUserNames);
			}
		}
	});
}
/*
*保存
*/
function saveSysPara(){
	var instentceid = $("#instentceid").val();
	var isComment = $("input[name='isComment']:checked").val();
	var isAuditing = $("input[name='isAuditing']:checked").val();
	var isSeeAttach = $("input[name='isSeeAttach']:checked").val();
	var showImage = $("input[name='showImage']:checked").val();
	var isEdit = $("input[name='isEdit']:checked").val();
	var isSmipleText = $("input[name='isSmipleText']:checked").val();
	var publishUserIds = $("#publishUserIds").val();
	var publishUserNames = $("#publishUserNames").val();
	var isDataFilter =  $("input[name='isDataFilter']:checked").val();
	var clientType =  $("input[name='clientType']:checked").val();
	dataParam = {
		'instentceid':instentceid,
		'tbColumnSetting.isComment':isComment,     //评论
		'tbColumnSetting.isAuditing' : isAuditing, //审批
		'tbColumnSetting.isEdit':isEdit,
		'tbColumnSetting.isSeeAttach' :isSeeAttach, //查看附件验证
		'tbColumnSetting.showImage':showImage,      //手机端列表图片显示
		'tbColumnSetting.publishUserIds' : publishUserIds,//可直接发布人员
		'tbColumnSetting.publishUserNames' : publishUserNames,//可直接发布人员
		'tbColumnSetting.isSmipleText' : isSmipleText,//编辑器
		'tbColumnSetting.clientType' : clientType,//客户端显示风格
		'tbColumnSetting.isDataFilter' : isDataFilter//数据权限
	};
	qytx.app.ajax({
			type : 'post',
			url : basePath + "notify/notify_notifySet.action",
			data : dataParam,
			dataType : 'text',
			shadea:true,
			success : function(data) {
				 qytx.app.dialog.tips(data,1.5);  
			}
		});
}
function selectAuthor() {
	openSelectUser(3, selectRangeExceptionUserCallBack, null,$("#publishUserIds").val());
}
/**
 * 添加按钮(回调函数)
 * 
 * @param data
 * @return
 */
function selectRangeExceptionUserCallBack(data) {
	var ids = '';
	var names = '';
	$(data).each(function(i,item){
			ids += item.id + ',';
			names += item.name + ',';
	});
	$("#publishUserIds").val(ids);
	$("#publishUserNames").val(names);
	qytx.app.valid.hideError($("#publishUserNames"));
}

/**
 * 清空操作
 * 
 * @param obj
 * @return
 */
function clearAuthor() {
	$("#publishUserIds").val('');
	$("#publishUserNames").val('');
}

/**
*设置单选框选中状态
**/
function setRadioSelect(name,value){
	var obj = document.getElementsByName(name);
	for(var i = 0 ; i < obj.length ;i++){
		if(obj[i].value == value){
			obj[i].checked = "checked";
		}else{
			obj[i].checked = "";
		}
	}
}
