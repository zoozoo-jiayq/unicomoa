
/**
 * 保存保密设置
 */
function save(){
	var id = $("#id").val();
//	保密字段
	var attribute = $("#select").val();
	if(!attribute){
		qytx.app.dialog.alert("保密字段不能为空！");
		return;
	}
	var attributeName = $("#select option:selected").text();
//	控制范围
	var applyUserIds = $("#applyUserIds").val();
	if(!applyUserIds){
		qytx.app.dialog.alert("控制范围不能为空！");
		return;
	}
//	黑名单
	var invisibleUserIds = $("#invisibleUserIds").val();
	if(!invisibleUserIds){
		qytx.app.dialog.alert("不可查看人员不能为空！");
		return;
	}
	
	var param={
			"secret.id":id,
			"secret.attribute":attribute,
			"secret.attributeName":attributeName,
			"secret.applyUserIds":applyUserIds,
			"secret.invisibleUserIds":invisibleUserIds
	};
	qytx.app.ajax({
		url:	basePath+"/secret/update.action",
		shade:	true,
		type:	"post",
		data:	param,
		dataType:"text",
		success:function(data){
			if(data == "1"){
				qytx.app.dialog.tips("保存成功！", function(){
					window.location.href=basePath+"/logined/secret/secretList.jsp?t="+Math.random();
				});
			}else{
				qytx.app.dialog.alert("操作失败！");
			}
		}
	});
}


/**
 * 添加控制范围
 */
function addApplyUser(){
	var applyUserIds = $("#applyUserIds").val();
	qytx.app.tree.alertUserTree({
		defaultSelectIds:applyUserIds,
		selTab:"1_1_1",
		callback:	function(data){
			var applyIds = "";
			var applyNames = "";
			$(data).each(function(i,item){
				applyIds += item.id+",";
				applyNames += item.name+",";
			});
			if(applyIds.length>0){
				applyIds = ","+applyIds;
				applyNames = applyNames.substring(0, applyNames.length-1);
			}
			$("#applyUserIds").val(applyIds);
			$("#applyUserNames").val(applyNames);
		}
	});
}

/**
 * 删除控制范围
 */
function delApplyUser(){
	$("#applyUserIds").val("");
	$("#applyUserNames").val("");
}

/**
 * 添加黑名单
 */
function addInvisibleUser(){
	var invisibleUserIds = $("#invisibleUserIds").val();
	qytx.app.tree.alertUserTree({
		defaultSelectIds:invisibleUserIds,
		selTab:"1_1_1",
		callback:	function(data){
			var invisibleIds = "";
			var invisibleNames = "";
			$(data).each(function(i,item){
				invisibleIds += item.id+",";
				invisibleNames += item.name+",";
			});
			if(invisibleIds.length>0){
				invisibleIds = ","+invisibleIds;
				invisibleNames = invisibleNames.substring(0, invisibleNames.length-1);
			}
			$("#invisibleUserIds").val(invisibleIds);
			$("#invisibleUserNames").val(invisibleNames);
		}
	});
}

/**
 * 删除黑名单
 */
function delInvisibleUser(){
	$("#invisibleUserIds").val("");
	$("#invisibleUserNames").val("");
}