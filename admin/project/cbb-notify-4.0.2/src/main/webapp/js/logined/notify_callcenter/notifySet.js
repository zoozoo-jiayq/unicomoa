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
		'tbColumnSetting.isDataFilter' : isDataFilter//数据权限
	};
	$.ajax({
			type : 'post',
			url : basePath + "notify/notify_notifySet.action",
			data : dataParam,
			dataType : 'text',
			  beforeSend:function(){
					$("body").lock();
				},
				complete:function(){
					$("body").unlock();
				},
			success : function(data) {
				art.dialog.alert(data);
			}
		});
}
function selectAuthor() {
	openSelectUser(3, selectRangeExceptionUserCallBack, null,$("#publishUserIds").val(), 'notifySet');
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
	data.forEach(function(value, key) {
				ids += value.Id + ',';
				names += value.Name + ',';
			});
	$("#publishUserIds").val(ids);
	$("#publishUserNames").val(names);
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
