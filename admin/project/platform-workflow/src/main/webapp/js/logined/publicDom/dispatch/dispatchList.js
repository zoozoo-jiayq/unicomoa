/**
 * 发送
 * @param taskId
 */
function send(taskId){
	var url=basePath+"dispatchDom/dispatch!selectBm.action";
	art.dialog.open(url,
	        {
	            title:"发送公文",
	            width : 800,
	            height : 450,
	            fixed: true,
	            resize: false,
	            drag: true,
	            lock : true,
			    opacity: 0.08,
	            close:function(){
	            	 
	            },

	            ok:function(){
	    			//子窗口对象
	    			var ifr = this.iframe;
	    			var subWin = ifr.contentWindow;
	    			var data = subWin["getData"]();
	    			if(data=="[]"){
	    				art.dialog.alert("没有选择要分发的部门！");
	    				return false;
	    			} else{
	    				 sendFormData(taskId,data);
	    			}
	    			return;
	    		},
	    		cancel:function(){
	    			true
	    		}
	            
	        }, false
	        );
	 
}


/**
 * 发送
 * @param taskId
 */
function sendFormData(taskId,userIds){
	 var nextAction="to 归档";
	 var paramData={
				"taskIds":taskId,
				"nextAction":nextAction,
				"userIds":userIds
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
                    return true;
		    	}
		 }); 
}

//批量发送
function sendMore(){
	var ids = getSelectTaskId();
	if(!ids){
		art.dialog.alert("请选择待发送的任务!");
		$("#selectAll").attr("checked",false);
		return null;
	}
	send(ids);
}

 