jQuery(document).ready(function($){
	/**
	 * 新建向导
	 */
	$("#flow_creat").click(function(){
		art.dialog.open(basePath+"/workflow/manager!prepCreate.action?type="+$("#type").val(),{
			title:"新建向导",
			lock : true,
		    opacity: 0.08
		});
	});
	
	/**
	 * 导入流程
	 */
	$("#flow_import").click(function(){
		var categoryId = $("#categoryId").val();
		art.dialog.open(basePath+"logined/customJPDL/upload.jsp?categoryId="+categoryId+"&type="+$("#type").val(),{
			title:"导入流程",
			 width : 400,
			 height : 200,
			ok:function(){
				window.parent.location.reload();
				return true;
			},
			lock : true,
		    opacity: 0.08
		});
	});
});