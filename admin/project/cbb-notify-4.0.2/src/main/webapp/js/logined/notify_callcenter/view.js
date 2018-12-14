/**
 * 查看详情
 */
$(document).ready(function() {
			var notifyId = $("#notifyId").val();
			getNotifyInfo(notifyId);
			signCheck(notifyId);
			//commentSet();
			$("#subComment").click(function(){
				subComment();
			});
			initButton();
			
})
function showComment(){
	$("div.reviewList").show();
	commentList();
}
/**
 * 获取公告列表
 * 
 * @return
 */
function commentList() {
	$('#myTable').dataTable({
		"bProcessing" : true,
		'bServerSide' : true,
		"bStateSave" : false, // 状态保存
		"bDestroy" : true,
		'fnServerParams' : function(aoData) {
			aoData.push({
				"name" : "notifyId",
				"value" : $.trim($("#notifyId").val())
			});
		},
		"sAjaxSource" : basePath + "notify/comment_page.action",
		"sServerMethod" : "POST",
		"sPaginationType" : "full_numbers",
		"bPaginate" : true, // 翻页功能
		"bLengthChange" : false, // 改变每页显示数据数量
		"bFilter" : false, // 过滤功能
		"bSort" : false, // 排序功能
		"bInfo" : true,// 页脚信息
		"bAutoWidth" : false,// 自动宽度
		"iDisplayLength" : 15, // 每页显示多少行
		"aoColumns" : [{
					"sTitle" : '',
					"mDataProp" : "photo",
					'sWidth':"120"
				},{
					"sTitle" : '',
					"mDataProp" : null
				}],
		"oLanguage" : {
			"sUrl" : basePath + "plugins/datatable/cn.txt" // 中文包
		},
		"aoColumnDefs" :[{
			"aTargets" : [0],
			"fnRender" : function(oObj) {
				var photo = oObj.aData.photo;
				return '<em class="reviewLeft"><img width="50" height="50" src="'+photo+'"></em>';
			}          
		},{
			"aTargets" : [1],
			"fnRender" : function(oObj) {
				var username = oObj.aData.username;
				var comment = oObj.aData.comment;
				var date = oObj.aData.date;
				var isDelete = oObj.aData.isDelete;
				var id = oObj.aData.id;
				var delTemplate = "";
				if(isDelete==1){
					var delTemplate = '<a onclick="del('+id+')" class="fr">删除</a>';
				}
				var template = '<div class="reviewRight"><h5>'+delTemplate+username+'</h5><p>'+comment+'</p><p class="time">'+date+'</p></div>';;
				return template;
			}          
		}]
	});
}

/**
 * 提交答案
 */
function subComment(){
	var notifyId = $("#notifyId").val();
	var comment = $("#comment").val();
	if(comment==null || comment==""){
		art.dialog.alert("评论内容不能为空！");
		return ;
	}else if(comment.length>=500){
		art.dialog.alert("评论内容太长！");
		return ;
	}else{
		$.ajax({
			type : 'post',
			url : basePath + "notify/comment_save.action",
			data : {"notifyComment.notify.id":notifyId,"notifyComment.comment":comment},
			dataType : 'json',
			async : false,
			success : function(data) {
				if(data){
					window.location.reload();
				}else{
					art.dialog.alert("系统异常，请稍后再试！");
				}
			}
		});
	}
}
/**
 * 加载是否可以评论信息
 */		
function commentSet(){
	var notifyId = $("#notifyId").val();
	var columnId = $("#columnId").val();
	if ($("#returnType").val() == 1){
		$.ajax({
			type : 'post',
			url : basePath + "notify/comment_setting.action",
			data : {"columnId":columnId,"notifyId":notifyId},
			dataType : 'json',
			async : false,
			success : function(data) {
				if(data.isComment==1){
					if(data.photo!=null && data.photo==""){
						$(".reviewCon dt").find("em").html('<img src="'+data.photo+'" style="width:60px;height:60px;"/>');
					}
					$("#countComment").html(data.counting+"条评论");
					$("dt p").html(data.username);
					$("#comment").val("");
					$(".reviewBox").show();
				}
			}
		});
	}
}		
/**
 * 加载公告信息
 * @param notifyId
 */
function getNotifyInfo(notifyId) {
	dataParam = {
		'id' : notifyId
	};
	$.ajax({
		type : 'post',
		url : basePath + "notify/notify_view.action",
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
			$("#subject").text(data.subject);
//			if(data.approveDate == null){
//				$("#createTimeStr").text("发布时间：" + data.createDate);
//			}else{
//				$("#createTimeStr").text("发布时间：" + data.approveDate);
//			}
			if(data.status !=2&&data.status !=4){
				$("#createTimeStr").text("发布时间：-");
			}else{
				$("#createTimeStr").text("发布时间：" + data.approveDate);
			}
			$("#createUserName").text("发布人："+data.createUser.userName);
			$("#viewCount").text("浏览次数：" + data.viewCount);
			$("#content").html(data.content);
			//判断是查看还是管理
//			var returnType = $("#returnType").val();
			if (null != data.attment && "" != data.attment) {
				
					$("#attachmentId").val(data.attment);
//				if(returnType == 1){
					for (var i = 0; i < data.attachmentList.length; i++) {
						$("#attachmentList")
								.append("<li><div class=\"icon\"><em class=\"doc\">" 
										+"</em></div><div class=\"txt\"><p>"
										+ data.attachmentList[i]["attachName"]
										+ "</p><p><a  href=\"javascript:void(0);\"  onclick=\"downFileById("
										+ data.attachmentList[i]["id"]
										+ ",this);\">下载</a></p></li>");
					}
//				}
//				if(returnType == 2){
//					for (var i = 0; i < data.attachmentList.length; i++) {
//
//						$("#attachmentList")
//						.append("<li><div class=\"icon\"><em class=\""
//								+ data.attachmentList[i]["attacthSuffix"]
//								+ "\"></em></div><div class=\"txt\"><p>"
//								+ data.attachmentList[i]["attachName"]
//								+ "</p><p><a href=\"javascript:void(0);\"  onclick=\"viewfileOpen("
//								+ data.attachmentList[i]["id"]
//								+ ");\">预览</a><a  href=\"javascript:void(0);\"  onclick=\"del("
//								+ data.attachmentList[i]["id"]
//								+ ",this);\">删除</a></p></li>");
//					}
//				}
				
			} else {
				$("#fileListDiv").css("display", "none");
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
			url : basePath + "notify/notify_initTransmitButtion.action",
			data:{"columnId":columnId},
			dataType : 'html',
			success : function(data) {
				if (data == '1') {
					$("#forword").show();
				} else if (data == '0') {
					$("#forword").hide();
				}
			}
		});
}
/**
 * *转发
 */
function transmit() {
	var notifyId = $("#notifyId").val();
	var columnId = $("#columnId").val();
	art.dialog.confirm('确定要转发该公告吗？', function() {
				window.location.href = basePath
						+ 'logined/notify/transmit.jsp?id='
						+ notifyId + "&columnId="+ columnId;
			}, function() {
				return;
			});
}
/**
 * 签阅公告
 * 
 * @param notifyId
 * @return
 */
function signCheck(notifyId) {
	var notifyId = $("#notifyId").val();
	if ($("#returnType").val() != 1){
		return;
	}else{
		dataParam = {
				'notifyId' : notifyId
			};
			$.ajax({
				type : 'post',
				url : basePath + "notify/view_save.action",
				data : dataParam,
				dataType : 'json',
				async : false,
				success : function(data) {
	
				}
			});
	}
}
/**
 * 去掉提示框
 */
function closeNotify() {

	var isIE = navigator.appName == "Microsoft Internet Explorer";
	//alert(isIE);
	if (isIE) {
		window.opener = "";
		window.open("", "_self");
		window.close();

	} else {
		/* FF 还要在 about:config 允许脚本脚本关闭窗口 */

		window.close();
	}

}

function viewfileOpen(id) {
	window.open(basePath + 'filemanager/htmlPreview.action?attachId=' + id);
}

function downFileById(id) {
	window.open(basePath + 'filemanager/downfile.action?attachmentId=' + id);
}

function goback() {
	var returnType = $("#returnType").val();
	var columnId = $("#columnId").val();
	var notifyId=$('#notifyId').val();
	var cate=$.trim($('#category').val());
	if (returnType == 2) {
		window.location.href = basePath + 'logined/notify/list.jsp?id=' + columnId +'&returnType='+returnType;
	}else if (returnType == 1) {
		window.location.href = basePath + 'logined/notify/viewList.jsp?id=' + columnId;
	}else if (returnType == 3) {
		window.location.href = basePath + 'logined/notify/viewApproveList.jsp?id='+ columnId +'&returnType='+returnType;
	}else if (returnType == 4) {
		window.location.href = basePath + 'logined/notify/viewNotify.jsp?id='+notifyId+'&columnId='+columnId+'&category='+cate;
	}else if(returnType==5){
		window.location.href = basePath + 'logined/notify/viewApproveNoList.jsp?id='+ columnId +'&returnType='+returnType;
	//pxd add
	}else if(returnType==10){
		window.location.href = basePath + 'logined/notify/list_seat.jsp?_clientType=wap&id=35';
	}
}

function del(id){
	$.ajax({
		type : 'post',
		url : basePath + "notify/comment_del.action",
		data : {"id":id},
		dataType : 'text',
		async : false,
		success : function(data) {
			if(data){
				commentList();
			}
		}
	});
}