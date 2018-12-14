$(document).ready(function() {
	var notifyId = $("#notifyId").val();
	getNotifyInfo(notifyId);
	//signCheck(notifyId);
	$("input[name='approve']").live("click",function(){
		if($(this).val()==0){
			$("#reasonContent").show();
		}
		else{$("#reasonContent").hide();}
	})
})

// $("[input='radio']:checked").val();

function getNotifyInfo(notifyId) {
	var columnId = $("#columnId").val();
	dataParam = {
		'id' : notifyId,
		'columnId' : columnId
	};
	$.ajax({
				type : 'post',
				url : basePath + "notify/notify_view.action",
				data : dataParam,
				dataType : 'json',
				async : false,
				success : function(data) {
					$("#subject").text(data.subject);
//					$("#createUserName")
//							.text("发布人：" + data.createUser.userName);
					$("#publishScope").text(data.publishScopeUserNames);
					
					//公告类型
					var notifyType = data.notifyType;
					if(data.notifyType!= null && data.notifyType != "" ){
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
//								$("#notifyType").empty();
//								$("#notifyType").append("<option value=''>请选择</option>");
							//	$("#notifyType").text(jsonData[0].name);
								for (var i = 0; i < jsonData.length; i++) {
									if(notifyType==jsonData[i].value){
										$("#notifyType").text(jsonData[i].name);
									}
								}
							}
						});
					}else{
						$("#notifyType").text("");
					}
										
//					$("#createTimeStr").text("发布于：" + data.createDate);
					//有效期
					var begDate = data.beginDate;
					var endDate = data.endDate;
					if(begDate != null && begDate != ""){						
						var beginStr = begDate.split(" ");
						$("#begDate").text(beginStr[0]);
						if(endDate != null){
							var endStr = endDate.split(" ");
							$("#endDate").text(endStr[0]);
						}else{
							$("#endDate").text("");
						}					
					}else{
						$("#limitDate").html("不限制");
					}
					//置顶
					var isTop = data.isTop;
					if(isTop == 1){
						$("input[name='isTop']").attr("checked","checked");
					}else{
						$("input[name='isTop']").attr("checked",false);
					}
					//提醒设置
					var sendType = data.sendType;
					if(sendType != null && sendType != ""){
						var s = $("#hidAffair").val();
						var a = s.split("</th>");
						var b = a[1].split("</tr>")[0];
						var c = b.split("<td>");
						var d = c[1].split("</td>")[0];
						$("#sendRemind").html(d);
						$("#sendRemind :checkbox").attr("disabled",true);
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
					
					//内容
					$("#content").html(data.content);
					//附件
					if (null != data.attment && "" != data.attment) {
						$("#attach").show();
						$("#attachmentId").val(data.attment);
						for (var i = 0; i < data.attachmentList.length; i++) {
							$("#attachmentList")
							.append("<li><div class=\"icon\"><em class=\"doc\">" 
									+"</em></div><div class=\"txt\"><p >"
									+ data.attachmentList[i]["attachName"]
									+ "</p><p ><a  href=\"javascript:void(0);\"  onclick=\"downFileById("
									+ data.attachmentList[i]["id"]
									+ ",this);\">下载</a></p></li>");
						}
					} else {
						$("#fileListDiv").css("display", "none");
					}
				}
			});
}
function viewfileOpen(id) {
	window.open(basePath + 'filemanager/htmlPreview.action?attachId=' + id);
}
function downFileById(id) {
	window.open(basePath + 'filemanager/downfile.action?attachmentId=' + id);
}

/**
 * *查找关键词
 */
function keywordShow() {
	$("#keywordShow").html("未能找到关键词 ");
}

/**
 * *转发
 */
function transmit() {
	var notifyId = $("#notifyId").val();
	var columnId = $("#columnId").val();
	art.dialog.confirm('确定要转发该公告吗？', function() {
		window.location.href = basePath
				+ 'logined/notify/transmitNotify.jsp?notifyId=' + notifyId
				+ '&columnId=' + columnId;
	}, function() {
		return;
	});
}

/**
 * *下一个
 */
function next() {
	var notifyId = $("#notifyId").val();
	var columnId = $("#columnId").val();
	dataParam = {
		'notifyVo.notifyId' : notifyId,
		'notifyVo.option' : 'next',
		'notifyVo.columnId' : columnId
	};
	$.ajax({
		type : 'post',
		url : basePath + "notify/getNotifyByOption.action",
		data : dataParam,
		dataType : 'json',
		async : false,
		success : function(data) {
			if (data.notifyId != "" && data.notifyId != null) {
				$("#notifyId").val(data.notifyId);
				getNotifyInfo(data.notifyId);
			} else {
				art.dialog.alert("已没有下一篇公告");
			}
		}
	});
}

/**
 * *上一个
 */
function prev() {
	var notifyId = $("#notifyId").val();
	var columnId = $("#columnId").val();
	dataParam = {
		'notifyVo.notifyId' : notifyId,
		'notifyVo.option' : 'prev',
		'notifyVo.columnId' : columnId
	};
	$.ajax({
		type : 'post',
		url : basePath + "notify/getNotifyByOption.action",
		data : dataParam,
		dataType : 'json',
		async : false,
		success : function(data) {
			if (data.notifyId != "" && data.notifyId != null) {
				$("#notifyId").val(data.notifyId);
				getNotifyInfo(data.notifyId);
			} else {
				art.dialog.alert("已没有上一篇公告");
			}
		}
	});
}
/**
 * 返回
 */
function goback() {
	var columnId = $("#columnId").val();
	window.location.href = basePath + 'logined/notify/viewApproveList.jsp?id='+ columnId;
}
/**
 * 审批提交
 * 
 * @param notifyId
 * @return
 */
function approveNotify() {
	// 框架校验
	if (!validator(document.getElementById("form1"))) {
		return;
	}
	var status=2;
	var notifyId = $("#notifyId").val();
	var reason = $.trim($("#reason").val());
	var approve=$("input[name='approve']:checked").val();
	if(approve==0){
		status=3;
		if (reason.length > 100 || reason.length < 1) {
			showObjError($("#reason"), 'notify.notify_reason_length');
			return;
		}
	}
	dataParam = {
		'notify.id' : notifyId,
		'notify.reason' : reason,
		'notify.status' : status
	};
	$.ajax({
		type : 'post',
		url : basePath + "notify/notify_approve.action",
		data : dataParam,
		dataType : 'text',
		async : false,
		success : function(data) {
			if (data == 1) {
				var columnId = $("#columnId").val();
				window.location.href = basePath+'/logined/notify/viewApproveNoList.jsp?id='+columnId
			} else {
				art.dialog.alert("审批失败！");
			}
		}
	});
	/*
	 * window.location.href = basePath +
	 * 'logined/notify/viewApproveYes.jsp?notifyId=' + notifyId + "&columnId=" +
	 * columnId;
	 */
}


function closeNotify() {

	var isIE = navigator.appName == "Microsoft Internet Explorer";
	// alert(isIE);
	if (isIE) {
		window.opener = "";
		window.open("", "_self");
		window.close();

	} else {
		/* FF 还要在 about:config 允许脚本脚本关闭窗口 */

		window.close();
	}

}