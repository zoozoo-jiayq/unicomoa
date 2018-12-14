$(document).ready(function() {
	//填装数据
	var projectId = $("#projectId").val();
	var urlStr = basePath+"reportBD/preUpdateProject.action";
	$.post(urlStr,{"projectId":projectId},function(data){
			data = eval("("+data+")");
			if(data != '1'){
				$("#projectName").val(data.projectName);
				$("#note").val(data.note);
			}
	});
	$("#updateProject").click(function(){
		updateProject();
		return false;
	});	
	$("#backBDList").click(function(){
		backBDList();
		return false;
	});
	//回车事件
	$(document).keydown(function(event){
		var code=event.which;
		if (code == 13) {
			addProject();
			return false;
		}
	});
});

/**
 * 添加项目
 * @return
 */
function updateProject(){
	// 框架校验
	if (!validator(document.getElementById("form1"))) {
		return;
	}
	var projectId = $("#projectId").val();
	var projectName = $.trim($("#projectName").val());
	// 验证文本编辑框字数大小限制
	var note = $.trim($("#note").val());
	if (note.length > 1000) {
		showObjError($("#note"), 'report.note_count_max');
		return;
	}else{
		hideError($("#note"));
	}
	var urlStr = basePath+"reportBD/updateProject.action";
	var paramData = {
			"projectId":projectId,
			"projectName":projectName,
			"note":note
	};
	$.ajax({
		url:urlStr,
		type:"POST",
		data:paramData,
		dataType:"text",
		success:function(data){
		if(data==''){
			art.dialog({
				title:"消息",
            	content: '编辑成功！',
                icon: 'succeed',
                width:320,
                height:110,
                lock: true,
                ok:function(){},
				close:function(){
                	var win = art.dialog.open.origin;// 来源页
                	var url = basePath+"logined/report/projectBDList.jsp";
					win.document.location = url;
				}
			});
		}else{
			art.dialog.alert("编辑失败,请稍后重试!");
		}
	}
	});
}

function backBDList(){
	var win = art.dialog.open.origin;// 来源页
	var url = basePath+"logined/report/projectBDList.jsp";
	win.document.location = url;
}

