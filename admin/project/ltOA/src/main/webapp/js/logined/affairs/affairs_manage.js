$(document).ready(function() {
	// 获取设置信息
	queryAffairsManage();
	
	loadRemind();

	// 选择 允许事务提醒
	$("#myTable").delegate("input:checkbox[name='affairPvir']", "click", function() {
		if ($(this).attr("checked") == "checked") {
			if ($(":checkbox[name='affairPvir']:checked").length == $(":checkbox[name='affairPvir']").length) {
				$("#affairAll").attr("checked", "checked");
			}
		} else {
			$("#affairAll").removeAttr("checked");
		}
	});

	
	$("#myTable").delegate("input:checkbox[name='smsPvir']", "click", function() {
		if ($(this).attr("checked") == "checked") {
			if ($(":checkbox[name='smsPvir']:checked").length == $(":checkbox[name='smsPvir']").length) {
				$("#smsAll").attr("checked", "checked");
			}
		} else {
			$("#smsAll").removeAttr("checked");
		}
	});
	
	$("#myTable").delegate("input:checkbox[name='pushPvir']", "click", function() {
		if ($(this).attr("checked") == "checked") {
			if ($(":checkbox[name='pushPvir']:checked").length == $(":checkbox[name='pushPvir']").length) {
				$("#pushAll").attr("checked", "checked");
			}
		} else {
			$("#pushAll").removeAttr("checked");
		}
	});
	
	
});

function queryAffairsManage() {
	qytx.app.grid({
		id	:	"myTable",
		url	:	basePath + "affairSetting/getAffairSettingList.action",
		iDisplayLength	:	20000,
		bInfo	:	false,
		sPage	:	false,
		valuesFn	:	[
                         {
                        	 "aTargets" : [ 2 ],// 覆盖第2列
                        	 "fnRender" : function(oObj) {
                        		 // 是否允许事务提醒
                        		 var affairPriv = oObj.aData.affairPriv;
                        		 var id = oObj.aData.id;
                        		 if (null != affairPriv && "" != affairPriv) {
                        			 if (affairPriv == '1') {
                        				 return '<input name="affairPvir" type="checkbox" checked="checked" value="'+id+'"/>';
                        			 }
                        		 }
                        		 return '<input name="affairPvir" type="checkbox" value="'+id+'"/>';
                        	 }
                         },
                         {
                        	 "aTargets" : [ 3 ],// 覆盖第3列
                        	 "fnRender" : function(oObj) {
                        		 // 是否发送短信提醒
                        		 var smsPriv = oObj.aData.smsPriv;
                        		 var id = oObj.aData.id;
                        		 if (null != smsPriv && "" != smsPriv) {
                        			
                        			 if (smsPriv == '1') {
                        				 return '<input name="smsPvir" type="checkbox" checked="checked" value="1"/>';
                        			 }
                        		 }
                        		 return '<input name="smsPvir" type="checkbox" value="0"/>';
                        	 }
                         },
                         {
                        	 "aTargets" : [ 4 ],// 覆盖第5列
                        	 "fnRender" : function(oObj) {
                        		 // 是否手机推送提醒
                        		 var pushPriv = oObj.aData.pushPriv;
                        		 var id = oObj.aData.id;
                        		 if (null != pushPriv && "" != pushPriv) {
                        			
                        			 if (pushPriv == '1') {
                        				 return '<input name="pushPvir" type="checkbox" checked="checked" value="1"/>';
                        			 }
                        		 }
                        		 return '<input name="pushPvir" type="checkbox" value="0"/>';
                        	 }
                         }]
	});
	setTimeout(function(){
		  if ($(":checkbox[name='affairPvir']:checked").length == $(":checkbox[name='affairPvir']").length && $(":checkbox[name='affairPvir']").length > 0) {
    		   $("#affairAll").attr("checked", "checked");
    	   }
    	   if ($(":checkbox[name='smsPvir']:checked").length == $(":checkbox[name='smsPvir']").length
    			   && $(":checkbox[name='smsPvir']").length > 0) {
    		   $("#smsAll").attr("checked", "checked");
    	   }
    	   if ($(":checkbox[name='pushPvir']:checked").length == $(":checkbox[name='pushPvir']").length
    			   && $(":checkbox[name='pushPvir']").length > 0) {
    		   $("#pushAll").attr("checked", "checked");
    	   }
	},100);
}

function selectAffairAll() {
	var isTotalCheck = $("input:checkbox[id='affairAll']").prop("checked");
	var checkNum = 0;
	if (isTotalCheck) {
		$("input:checkbox[name='affairPvir']").prop("checked", function(i, val) {
			checkNum = checkNum + 1;
			return true;
		});
	} else {
		$("input:checkbox[name='affairPvir']").prop("checked", false);
	}
}

function selectSmsAll() {
	var isTotalCheck = $("input:checkbox[id='smsAll']").prop("checked");
	var checkNum = 0;
	if (isTotalCheck) {
		$("input:checkbox[name='smsPvir']").prop("checked", function(i, val) {
			checkNum = checkNum + 1;
			return true;
		});
	} else {
		$("input:checkbox[name='smsPvir']").prop("checked", false);
	}
}

function selectPushAll() {
	var isTotalCheck = $("input:checkbox[id='pushAll']").prop("checked");
	var checkNum = 0;
	if (isTotalCheck) {
		$("input:checkbox[name='pushPvir']").prop("checked", function(i, val) {
			checkNum = checkNum + 1;
			return true;
		});
	} else {
		$("input:checkbox[name='pushPvir']").prop("checked", false);
	}
}

/**
 * 保存配置信息
 */
function updateAffairManage() {
	qytx.app.dialog.confirm("您确定要修改设置信息吗？",function(){
		// 获取 允许事务提醒
		var sb = "";
		$.each( $("#myTable tbody  tr"), function(index, tr){
			sb+=$(tr).children().eq(2).children().val() + ",";
			if($(tr).children().eq(2).children().attr("checked")){
				sb+="1";
			}else{
				sb+="0";
			}
			sb+=",";
			if($(tr).children().eq(3).children().attr("checked")){
				sb+="1";
			}else{
				sb+="0";
			}
			sb+=",";
			if($(tr).children().eq(4).children().attr("checked")){
				sb+="1";
			}else{
				sb+="0";
			}
			sb+="_";
		});

		var paramData={
				'settings':sb
		};
		qytx.app.ajax({
			url : basePath + "/affairSetting/update.action",
			type : "post",
			data: paramData,
			dataType : "text",
			shade:true,
			success : function(data) {
				if ("0" == data) {
					qytx.app.dialog.success(sprintf("affairs.msg_configuration_success"), function(){
						window.location.reload();
					});
				} else {
					qytx.app.dialog.alert(sprintf("affairs.msg_configuration_error"));
				}
			},
			 error:function(){
				 qytx.app.dialog.alert("系统异常，请稍后再试");
	         }
		});
	},function(){});
	
}

/**
 * 提交操作
 */
function sub(){
	if($("#tab1").is(":hidden")){
		updateAffairsRemind();
	}else{
		updateAffairManage();
	}
}

/**
 * 更新重复提醒设置
 */
function updateAffairsRemind(){
	qytx.app.dialog.confirm("您确定要修改设置信息吗？",function(){
		var status=0;
		if($("#status").attr("checked")){
			status=1;
		}
		$.ajax({
			url:basePath+"/affairsRemind/save.action",
			type:"post",
			data:{"affairsRemind.status":status,"affairsRemind.times":$("#times").val()},
			dataType:"text",
			success:function(data){
				if(data == 1){
					qytx.app.dialog.success(sprintf("affairs.msg_configuration_success"), function(){
						window.location.reload();
					});
				}else{
					qytx.app.dialog.alert(sprintf("affairs.msg_configuration_error"));
				}
			}
		});
	},function(){
		
	});
}

/**
 * 加载重复提醒设置
 */
function loadRemind(){
	$.ajax({
		url:basePath+"/affairsRemind/load.action",
		type:"post",
		dataType:"text",
		async:false,
		success:function(data){
			if(data){
				var obj = eval("("+data+")");
				if(obj.status == 1){
					$("#status").attr("checked","checked");
				}
				if(obj.times!=0){
					$("#times").val(obj.times);
				}
			}
		}
	});
}