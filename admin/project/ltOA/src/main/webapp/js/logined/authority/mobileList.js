$(document).ready(function(){
	var roleId = $("#roleId").val();
	qytx.app.ajax({
		type:"post",
		url:basePath+"module/getModuleList.action",
		data:{"roleId":roleId},
		dataType:"json",
		success:function(result){
			if(result.length > 0){
				for(var i = 0 ; i < result.length ; i++){
					$("#module"+result[i].moduleId).attr("checked","checked");
				}
			}
		}
	});
	
	$("h2 input").click(function(){
		var $obj = $(this);
		if($obj.attr("checked")=="checked"){
			$obj.parent().next().find("li input").attr("checked","checked");
		}else{
			$obj.parent().next().find("li input").removeAttr("checked");
		}
	});
	$("li input").click(function(){
		var $obj = $(this);
		if($obj.attr("checked")=="checked"){
			$obj.parent().parent().prev().find("input").attr("checked","checked");
		}
	});
	$("#addRoleModuleMobile").click(function(){
		qytx.app.ajax({
			type:"post",
			url:basePath+"module/update.action",
			data:$("#myForm").serialize(),
			dataType:"text",
			success:function(result){
				qytx.app.dialog.tips("修改权限成功");
			}
		});
	});
})