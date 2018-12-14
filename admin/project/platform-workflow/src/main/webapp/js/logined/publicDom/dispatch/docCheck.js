 jQuery(document).ready(function($){
	//初始化数据
	 var taskIds=$("#taskIds").val();
	 var url = basePath+"dispatchDom/dispatchManger!docEditForm.action?taskIds="+taskIds;
	 $("#ntkoframeFrom").attr("src",url);
 });
 

 
 /**
  * 保存
  */
 function save(){
	 $("#tabContent1").trigger("click");
	 var isOpen = $("#isOpen").val();
     var taskIds = $("#taskIds").val();
	 //var formDataJSON = getJSONVal();
     var formDataJSON = window.frames["ntkoframeFrom"].getJSONVal();
     window.frames["ntkoframeFrom"].saveSign(); //保存签章
	 var attachJSON = getAttachJSON();
	 var paramData={
				"formData":formDataJSON,
				"attachData":attachJSON,
				"taskIds":taskIds
		 };
		$.ajax({
		      url:basePath+"dispatchDom/managerAjax_saveAll.action",
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
		    	  if(isOpen=="1"){  //如果打开正文则保存正文
	    		    	 window.frames["ntkoframe"].saveOffice(); //保存正文
	    		    	 if(attachJSON!="[]"){
	    		    		 // document.location.reload(); 
	    		    	 }
	    		    }else{
	    		    	 art.dialog({
	    		    		   title:"消息",
	    		    		   content:"保存成功！",
	    		    		   width:320,
	    		    		   height:110,
	    		    		   icon:"succeed",
	    		    		   opacity:0.08,
	    		    		   lock:true,
	    		    		   ok:function(){
	    		    			   art.dialog.close();
	    		    		   },
	    		    		   close:function(){
	    		    			
	    		    		   }
	    		    		});
	    		    }
		    	}
		 }); 
 }

 
/**
 * 完成
 */
 function doCheck(){
	 var taskIds = $("#taskIds").val();
	 var nextAction="to 套红盖章";
	 var userIds=$("#userIds").val();
	 if(userIds==""||userIds==null){
		 art.dialog.alert("请选择人员！");
		 return;
	 }
	 var paramData={
				"taskIds":taskIds,
				"nextAction":nextAction,
				"userIds":userIds
		 };
	 saveAndCheck(paramData);
	/**	$.ajax({
		      url:basePath+"dispatchDom/managerAjax_completeTask.action",
		      type:"post",
		      dataType: "html",
		      data:paramData,
		      success: function(data){
		    	    art.dialog.close();
		    	}
		 }); 
		 */
 }
 
 
 /**
  * 获取附件json数据
  * @returns {String}
  */
 function getAttachJSON(){
 	var uploadData = JSON.stringify(attatchmap);
 	var json = eval('('+uploadData+')');
 	var uploadData = JSON.stringify(json.arr);
     return uploadData;
 }
 
 
 function saveAndCheck(paramDataCheck){
	 var isOpen = $("#isOpen").val();
     var taskIds = $("#taskIds").val();
	 //var formDataJSON = getJSONVal();
     var formDataJSON = window.frames["ntkoframeFrom"].getJSONVal();
     window.frames["ntkoframeFrom"].saveSign(); //保存签章
	 var attachJSON = getAttachJSON();
	 var paramData={
				"formData":formDataJSON,
				"attachData":attachJSON,
				"taskIds":taskIds
		 };
		$.ajax({
		      url:basePath+"dispatchDom/managerAjax_saveAll.action",
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
		    	  if(isOpen=="1"){  //如果打开正文则保存正文
	    		    	 window.frames["ntkoframe"].saveOffice(); //保存正文
	    		    }
		    	  $.ajax({
				      url:basePath+"dispatchDom/managerAjax_completeTask.action",
				      type:"post",
				      dataType: "html",
				      data:paramDataCheck,
				      success: function(data){
				    	    art.dialog.close();
				    	}
				   }); 
		    	  
		    	}
		 }); 
 }
 