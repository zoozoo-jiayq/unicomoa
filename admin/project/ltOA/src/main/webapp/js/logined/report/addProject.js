$(document).ready(function() {
	$("#addProject").click(function(){
		addProject();
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
function addProject(){
	// 框架校验
	if (!validator(document.getElementById("form1"))) {
		return;
	}
	var projectName = $.trim($("#projectName").val());
	// 验证文本编辑框字数大小限制
	var note = $.trim($("#note").val());
	if (note.length > 1000) {
		showObjError($("#note"), 'report.note_count_max');
		return;
	}else{
		hideError($("#note"));
	}
	var urlStr = basePath+"reportBD/addProject.action";
	var paramData = {
			"projectName":projectName,
			"note":note
	};
	$.ajax({
		url:urlStr,
		data:paramData,
		dataType:"html",
		type:"POST",
		success:function(data){
			if(data==''){
				art.dialog({
					title:"消息",
	            	content: '保存成功！',
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
				art.dialog.alert("添加失败,请稍后重试!");
			}
	}
	});
}

function backBDList(){
	var win = art.dialog.open.origin;// 来源页
	var url = basePath+"logined/report/projectBDList.jsp";
	win.document.location = url;
}

