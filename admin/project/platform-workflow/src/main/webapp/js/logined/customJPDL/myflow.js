jQuery(document).ready(function($){

	//获取流程名称
	$.get("<%=request.getContextPath()%>/workflow/manager!getProcessAttribute.action?processAttributeId="+$("#processAttribute").val(),function(data){
		
	});
	
});