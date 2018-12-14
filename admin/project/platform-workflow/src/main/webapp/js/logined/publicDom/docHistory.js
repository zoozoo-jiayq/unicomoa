jQuery(document).ready(function($){
	var taskId = $("#taskId").val();
	$.get(basePath+"/dom/public!getDocHistory.action?taskId="+taskId,function(data){
		$("#num").html(data.length);
		for (var i = data.length - 1; i >= 0; i--) {
			var temp = data[i];
			var flag = i%2;
			var c = "even";
			if(flag == 0){
				c= 'odd';
			}
			var tro = $("<tr>",{
				"class":c
			});
			var tdo1 = $("<td>",{
				"text":temp.formTime
			});
			var tdo2 = $("<td>",{
				"text":temp.updateUser
			});
			var tdo3 = $("<td>").append($("<a>",{
				"text":"下载",
				"href":basePath+"/filemanager/downFileByFilePath.action?filePath="+temp.hostoryDocPath
			}));
			tro.append(tdo1).append(tdo2).append(tdo3);
			$("#show").append(tro);
		}
	},"json");
});