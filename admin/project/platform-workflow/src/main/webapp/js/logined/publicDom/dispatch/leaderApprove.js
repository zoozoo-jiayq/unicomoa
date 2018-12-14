
jQuery(document).ready(function($){
	$("#appCheck").click(function(){
		var taskIds = getSelectTaskId();
		if(!taskIds){
			art.dialog.alert("请先选择待核稿的任务！");
			return null;
		}else{
			  art.dialog.confirm("确定要批量完成核稿吗?",function(){
				  var nextAction="to 套红盖章";
				  var paramData={
							"taskIds":taskIds,
							"nextAction":nextAction
					 };
				  $.ajax({
				      url:basePath+"dispatchDom/managerAjax_completeTask.action",
				      type:"post",
				      dataType: "html",
				      data:paramData,
				      beforeSend:function(){
							$("body").lock();
						},
						complete:function(){
							$("body").unlock();
						},
				      success: function(data){
				    	  $("#selectAll").attr("checked",false);
				    	  getDataTable();
				    	}
				 }); 
				  
				  
				});
		}
	});

});
