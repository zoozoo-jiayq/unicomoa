/**
 * 流程申请
 * author:CQL
 */

$(document).ready(
		function() {
			//获取流程基本信息
			//设置表单数据
			// setFormData();
			var taskIds = $("#taskIds").val();
			var url = basePath
					+ "dispatchDom/dispatchManger!docEditForm.action?taskIds="
					+ taskIds;
			$("#ntkoframeFrom").attr("src", url);

		});

/**
 * 保存
 */
function save() {
	$("#tabContent1").trigger("click");
	var isOpen = $("#isOpen").val();
	var taskIds = $("#taskIds").val();
	// var formDataJSON = getJSONVal();
	var formDataJSON = window.frames["ntkoframeFrom"].getJSONVal();
	window.frames["ntkoframeFrom"].saveSign(); //保存签章
	var attachJSON = getAttachJSON();
	var paramData = {
		"formData" : formDataJSON,
		"attachData" : attachJSON,
		"taskIds" : taskIds
	};
	$.ajax({
		url : basePath + "dispatchDom/managerAjax_saveAll.action",
		type : "post",
		dataType : "html",
		data : paramData,
		beforeSend : function() {
			$("body").lock();
		},
		complete : function() {
			$("body").unlock();
		},
		success : function(data) {
			if (isOpen == "1") { //如果打开正文则保存正文
				window.frames["ntkoframe"].saveOffice(); //保存正文
				if (attachJSON != "[]") {
					// document.location.reload(); 
				}
			} else {
				art.dialog({
					title : "消息",
					content : "保存成功！",
					width : 320,
					height : 110,
					icon : "succeed",
					opacity : 0.3,
					lock:true,
					ok : function() {
					},
					close : function() {
						attatchmap = new Map();
					}
				});
			}
		}
	});
}

/**
 * 转核稿
 */
function doCheck() {
	$("#tabContent1").trigger("click");
	var taskIds = $("#taskIds").val();
	art.dialog.open(basePath
			+ "/dispatchDom/dispatch!openRegister.action?taskIds=" + taskIds, {
		title : "转交下一步",
		width : 800,
		height : 450,
		lock : true,
		opacity : 0.3,
		ok : function() {
			//子窗口对象
			var ifr = this.iframe;
			var subWin = ifr.contentWindow;
			var data = subWin["getData"]();
			var userIds = data.userIds;
			if (userIds == "" || userIds == null) {
				art.dialog.alert("请选择人员！");
				return false;
			}
			var paramData = {
				"taskIds" : data.taskIds,
				"nextAction" : data.nextAction,
				"userIds" : data.userIds
			};
			saveAndCheck(paramData);
			/**
			$.ajax({
			      url:basePath+"dispatchDom/managerAjax_completeTask.action",
			      type:"post",
			      dataType: "html",
			      data:paramData,
			      success: function(data){
			    	  art.dialog.close();
			    	}
			 }); 
			 */
		},
		cancel : true
	});
}

function saveAndCheck(paramDataCheck) {
	var isOpen = $("#isOpen").val();
	var taskIds = $("#taskIds").val();
	// var formDataJSON = getJSONVal();
	var formDataJSON = window.frames["ntkoframeFrom"].getJSONVal();
	window.frames["ntkoframeFrom"].saveSign(); //保存签章
	var attachJSON = getAttachJSON();
	var paramData = {
		"formData" : formDataJSON,
		"attachData" : attachJSON,
		"taskIds" : taskIds
	};
	$.ajax({
		url : basePath + "dispatchDom/managerAjax_saveAll.action",
		type : "post",
		dataType : "html",
		data : paramData,
		beforeSend : function() {
			$("body").lock();
		},
		complete : function() {
			$("body").unlock();
		},
		success : function(data) {
			if (isOpen == "1") { //如果打开正文则保存正文
				window.frames["ntkoframe"].saveOffice(); //保存正文
				if (attachJSON != "[]") {
					// document.location.reload(); 
				}
			}

			$.ajax({
				url : basePath + "dispatchDom/managerAjax_completeTask.action",
				type : "post",
				dataType : "html",
				data : paramDataCheck,
				beforeSend : function() {
					$("body").lock();
				},
				complete : function() {
					$("body").unlock();
				},
				success : function(data) {
					art.dialog.close();
				}
			});
		}
	});
}
