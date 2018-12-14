//流程处理服务js
/**
 * 删除任务
 * @param taskId
 */
function deleteTask(processInstanceId){
	var paramData={
			'processInstanceId':processInstanceId
	 };
	 art.dialog.confirm('确定要删除？', function () {
		 $.ajax({
		      url:basePath+"jbpmflow/operation!deleteInstance.action",
		      type:"post",
		      dataType: "html",
		      data:paramData,
		      success: function(data){
		    	     if(data==1){
		    	    	  getDataTable();
		    	     }else{
		    	    	 art.dialog.alert('该任务无法删除！');
		    	     }
		    	}
		 }); 
	}, function () {
			return ;
	});
}

/**
 * 挂起任务
 * @param taskId
 */
function suspendTask(taskId){
	 var paramData={
				'taskId':taskId
		 };
	 art.dialog.confirm('确认要挂起吗？', function () {
		 $.ajax({
		      url:basePath+"jbpmflow/operation!suspendTask.action",
		      type:"post",
		      dataType: "html",
		      data:paramData,
		      success: function(data){
		    	     if(data==1){
		    	     	art.dialog({
		    	     		title:"挂起操作!",
							height : 109,
							width : 317,
		    	     		content:"操作成功,请到“已挂起”中查看该记录!",
		    	     		icon: 'succeed',
		    	     		lock:true,
		    	     		ok:function(){
		    	    	 		window.location.reload();
		    	     		}
		    	     	});
		    	     }else{
		    	    	 art.dialog.alert('该任务无法挂起！');
		    	     }
		    	}
		 }); 
	}, function () {
			return ;
	});
}

/**
 * 恢复任务
 * @param taskId
 */
function resumeTask(taskId,urlSource){
	 var paramData={
				'taskId':taskId
		 };
	 art.dialog.confirm('确认要恢复该工作流程吗？', function () {
		 $.ajax({
		      url:basePath+"jbpmApp/approve_resumeTask.action",
		      type:"post",
		      dataType: "html",
		      data:paramData,
		      success: function(data){
		    	     if(data==1){
		    	    	  getDataTable();
		    	     }else{
		    	    	 art.dialog.alert('该任务无法恢复！');
		    	     }
		    	}
		 }); 
	}, function () {
			return ;
	});
	 
	
}


/**
 * 功能：驳回，回退任务
 * @param taskId
 */
function rollbackTask(taskId,urlSource){
	 var paramData={
				'taskId':taskId
		 };
		 art.dialog.confirm('确认要驳回该工作流程吗？', function () {
			 $.ajax({
			      url:basePath+"jbpmApp/approve_rollbackTask.action",
			      type:"post",
			      dataType: "html",
			      data:paramData,
			      success: function(data){
			    	     if(data==1){
			    	    	 window.location.href = document.referrer;
			    	     }else{
			    	    	 art.dialog.alert('该工作流程不能驳回！');
			    	     }
			    	}
			 }); 
		}, function () {
				return ;
		});
		 
	
}

/**
 * 功能：驳回，回退任务
 * @param taskId
 */
function rollbackTaskTwo(taskId,urlSource){
	 var paramData={
				'taskId':taskId
		 };
		 art.dialog.confirm('下一步骤尚未接收时可收回至本步骤重新办理，确认要收回吗？', function () {
			 $.ajax({
			      url:basePath+"jbpmApp/approve_rollbackTask.action",
			      type:"post",
			      dataType: "html",
			      data:paramData,
			      success: function(data){
			    	     if(data==1){
			    	    	  getDataTable();
			    	     }else{
			    	    	 art.dialog.alert('该工作流程不能收回！');
			    	     }
			    	}
			 }); 
		}, function () {
				return ;
		});
}

/**
 * 主办
 */
function goHold(processId,executionId,taskId){
	 var viewStr=basePath+"jbpmflow/detailSearch!toApply.action?processId="+processId+"&processInstanceId="+encodeURI(executionId)+"&taskId="+taskId;
     window.location.href=viewStr;
}
