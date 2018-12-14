$(document).ready(function() {
	var notifyId = $("#notifyId").val();
	getNotifyInfo(notifyId);
	getNotifySetInfo();
	$("input[name='approve']").live("click",function(){
		if($(this).val()==0){
			$("#reasonContent").show();
		}
		else{$("#reasonContent").hide();}
	});
})

function getNotifyInfo(notifyId) {
	var columnId = $("#columnId").val();
	dataParam = {
		'id' : notifyId,
		'columnId' : columnId
	};
	qytx.app.ajax({
				type : 'post',
				url : basePath + "notify/notify_view.action",
				data : dataParam,
				dataType : 'json',
				async : false,
				success : function(data) {
					$("#subject").text(data.subject);
					$("#publishScope").text(data.publishScopeUserNames);
					if(null!=data.images && ""!=data.images){
						$("#myphoto").attr("src",basePath+"filemanager/downfile.action?_clientType=wap&attachmentId="+data.images);
					}
					
					var notifyType = data.notifyType;
					if(data.notifyType!= null && data.notifyType != "" ){
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
						$("#limitDate").html("未启用");
					}
					//置顶
					var isTop = data.isTop;
					if(isTop == 1){
						$("#isTop").html("置顶");
					}else{
						$("#isTop").html("不置顶");
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
							$("#affairType1").remove();
						}else{
							$("#affairType1").parent().remove();
						}
						if(typeList[1] == 1){
							$("#affairType2").remove();
						}else{
							$("#affairType2").parent().remove();
						}
						if(typeList[2] == 1){
							$("#affairType3").remove();
						}else{
							$("#affairType3").parent().remove();
						}
					}
					if(data.status==2){
						$("#approveStatus").html("通过");
					}if(data.status==3){
						$("#approveStatus").html("不通过");
						$("#reasonContent").show();
					}
					$("#reason").html(data.reason);
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
 * 返回
 */
function goback() {
	var columnId = $("#columnId").val();
	window.location.href = basePath + 'logined/notify/viewApproveList.jsp?id='+ columnId;
}
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
			if(result.showImage == 1){
				$("#imgdiv").show();
			}
		}
	});
}
