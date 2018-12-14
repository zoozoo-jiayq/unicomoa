jQuery(document).ready(function($){

	/**
	 * 复制流程
	 */
	$("#copySure").click(function(){
		if($("#directions").val().length>500){
				art.dialog.alert("流程说明不能超过500个字符！");
				return null;
			}
			if(!validator(document.getElementById("newForm"))){
				return;
			}
			var pid = "";
			if($("#pid").val()){
				pid = $("#pid").val();
			}
			$.post(basePath+"/workflow/manager!checkProcessNameIsRepeat.action?processName="+encodeURI($("#pname").val())+"&processAttributeId="+pid,function(result){
				if(result == "success"){
					art.dialog.alert("流程名称不能重复!");
					$("#pname").val("");
				}else if(result == "fail"){
					var processName = $("#pname").val();
					var oldJson = $("#jsonJpdl").val();
					if(oldJson){
						var newObj = eval("("+oldJson+")");
						newObj.props.props.name.value=processName;
						var newJson = JSON.stringify(newObj);
						$("#jsonJpdl").val(newJson);	
					}
					$("form").submit();
				}
			});
		});

	//取消复制，即删除该对象
	$("#cancelCopy").click(function(){
		var processId = $("#processAttributeId").val();
		$.get(basePath+"/workflow/manager!deleteProcess.action?processAttributeId="+processId,function(result){
			if(result == "success"){
				if(window.parent && window.parent.parent && window.parent.parent.closeCurrentTab){
					window.parent.parent.closeCurrentTab();
				}else{
					window.close();
				}
			}
		});
	});
});