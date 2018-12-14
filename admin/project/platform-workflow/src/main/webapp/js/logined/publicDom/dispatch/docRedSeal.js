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
	// var formDataJSON = getJSONVal();
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
	    		    		   ok:function(){},
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
 function doDone(){
	 $("#tabContent1").trigger("click");
	 var taskIds = $("#taskIds").val();
	 var nextAction="to 发文";
	 var userIds=$("#userIds").val();
	 var paramData={
				"taskIds":taskIds,
				"nextAction":nextAction,
				"userIds":userIds
		 };
	 saveAndDone(paramData);
	 /**
		$.ajax({
		      url:basePath+"dispatchDom/managerAjax_completeTask.action",
		      type:"post",
		      dataType: "html",
		      data:paramData,
		      success: function(data){
		    	  art.dialog.alert("完成套红盖章，请到待发列表中发送公文！",function(){ 
		    		  art.dialog.close();
		    	  });
		    	     
		    	}
		 }); */
 }
 
 /**
  * 转核稿
  */
  function doCheck(){
	  $("#tabContent1").trigger("click");
	  var taskIds = $("#taskIds").val();
		art.dialog.open(basePath+"dispatchDom/dispatch!openRegisterCheck.action?taskIds="+taskIds,{
			title:"转核稿",
			 width : 800,
			 height : 450,
			lock : true,
		    opacity: 0.08,
			ok:function(){
				//子窗口对象
				var ifr = this.iframe;
				var subWin = ifr.contentWindow;
				var data = subWin["getData"]();
				var userIds = data.userIds;
				 if(userIds==""||userIds==null){
					 art.dialog.alert("请选择人员！");
					 return false;
				 }
				 var paramData={
							"taskIds":data.taskIds,
							"nextAction":data.nextAction,
							"userIds":data.userIds
					 };
				 saveAndCheck(paramData);
					/**$.ajax({
					      url:basePath+"dispatchDom/managerAjax_completeTask.action",
					      type:"post",
					      dataType: "html",
					      data:paramData,
					      success: function(data){
					    	  art.dialog.close();
					    	}
					 }); */
			},
			cancel:true
		});
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
	// var formDataJSON = getJSONVal();
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
				      beforeSend:function(){
							$("body").lock();
						},
						complete:function(){
							$("body").unlock();
						},
				      success: function(data){
				    	  art.dialog.close();
				    	}
				   }); 
		    	  
		    	}
		 });  
 }
 
 
 /**
  * 完成并保存
  * @param paramDataCheck
  */
 function saveAndDone(paramDataCheck){
	 var isOpen = $("#isOpen").val();
     var taskIds = $("#taskIds").val();
	// var formDataJSON = getJSONVal();
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
				      beforeSend:function(){
							$("body").lock();
						},
						complete:function(){
							$("body").unlock();
						},
				      success: function(data){
				    	  art.dialog({
				    		   title:"消息",
				    		   content:"完成套红盖章，请到待发列表中发送公文！",
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
				 }); 
		    	  
		    	}
		 });  
 }