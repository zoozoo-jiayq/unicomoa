$(document).ready(function() {
	//得到ProjectName
	var projectId = $("#projectId").val();
	var sectionId = $("#sectionId").val();
	var urlStr = basePath+"reportBD/preUpdateSection.action";
	$.post(urlStr,{"projectId":projectId,"sectionId":sectionId},function(data){
			data = eval("("+data+")");
			if(data != '1'){
				$("#projectName").val(data.projectName);
				$("#sectionName").val(data.sectionName);
				$("#sectionNumber").val(data.sectionNumber);
				$("#note").val(data.note);
			}
	});
	
	$("#updateSection").click(function(){
		updateSection();
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
 * 添加标段
 * @return
 */
function updateSection(){
	// 框架校验
	if (!validator(document.getElementById("form1"))) {
		return;
	}
	var sectionName = $.trim($("#sectionName").val());
	var sectionNumber = $.trim($("#sectionNumber").val());
	var sectionId = $.trim($("#sectionId").val());
	
	// 验证文本编辑框字数大小限制
	var note = $.trim($("#note").val());
	if (note.length > 1000) {
		showObjError($("#note"), 'report.note_count_max');
		return;
	}else{
		hideError($("#note"));
	}
	if(isNaN(sectionNumber)){
		showObjError($("#sectionNumber"), 'report.sectionNumber_isNum');
		return;
	}else{
		hideError($("#sectionNumber"));
	}
	var projectId = $("#projectId").val();
	var urlStr = basePath+"reportBD/updateSection.action";
	var paramData = {
			"projectId":projectId,
			"sectionId":sectionId,
			"sectionNumber":sectionNumber,
			"sectionName":sectionName,
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
	            	content: '编辑成功！',
	                icon: 'succeed',
	                width:320,
	                height:110,
	                lock: true,
	                ok:function(){},
					close:function(){
	                	var win = art.dialog.open.origin;// 来源页
	                	var url = basePath+"logined/report/sectionBDList.jsp?projectId="+$("#projectId").val();
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

