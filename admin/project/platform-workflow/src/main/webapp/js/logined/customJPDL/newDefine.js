jQuery(document).ready(function($){
	//加载下拉树的数据
	$.selectTree(basePath+"workflow/manager!getFormCategoryTree.action?r="+Math.random()+"&type="+$("#type").val(),"formCategory","menuContent","treeDemo");
	//加载套红模板下拉树
	$.selectTree(basePath+"workflow/manager!getRedTemplateTree.action?r="+Math.random()+"&type="+$("#type").val(),"redTemplate","menuContentDoc","treeDemoDoc");

	if($("#dom").val()==1){
		$("#taohong").show();
	}else{$("#taohong").hide();}
    $("#dom").change(function(){
    	var p=$(this).children('option:selected').val();
    	if(p==1){
    		$("#taohong").show();
    	}else{$("#taohong").hide();}
    })
	//取消新增
	$("#cancel").click(function(){
		var tabName = $("#tabName").val();
		window.parent.parent.closeTab(tabName);
	});

	//取消修改
	$("#cancelUpdate").click(function(){
		if(window.parent && window.parent.parent && window.parent.parent.closeCurrentTab){
			window.parent.parent.closeCurrentTab();
		}else{
			window.close();
		}
	});

	//预览
	$("#prepView").click(function(){
		var formId = $("#formCategory_hidden").val();
		if(!formId){
			art.dialog.alert("请选择表单!");
			return ;
		}
		var formId = $("#formCategory_hidden").val();
		
		art.dialog.open(basePath+"workflowForm/viewForm.action?formId="+formId,{
			title:"预览表单",
			width:"60%",
			height:"80%",
			ok:true,
			lock : true,
		    opacity: 0.08
		});
	});

	$("#sure").click(function(){
		// editor.sync();
		if($("#directions").val().length>100){
			art.dialog.alert("流程说明不能超过100个字符！");
			return null;
		}
		if(!validator(document.getElementById("newForm"))){
			return;
		}
		if($("#categoryIdSelect").val() == "-1"){
			showObjError($("#categoryIdSelect"), 'customJpdl.process_type_not_null');
			return null;
		}
		var pid = "";
		if($("#pid").val()){
			pid = $("#pid").val();
		}
		$.post(basePath+"/workflow/manager!checkProcessNameIsRepeat.action?processName="+encodeURI($("#processName").val())+"&processAttributeId="+pid,function(result){
			if(result == "success"){
				art.dialog.alert("流程名称不能重复！");
				$("#processName").val("");
			}else if(result == "fail"){
				$("form").submit();
			}
		});
	});

});
// 展开显示
function showHide(id) {
	
	var v_ID = document.getElementById(id);
	if (jQuery(v_ID).is(":visible"))
		jQuery(v_ID).hide();
	else
		jQuery(v_ID).show();
	// 调整框架高度
	window.parent.frameResize();
}