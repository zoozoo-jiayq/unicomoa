/**
 *  实现申请流程前显示流程基本信息和判断申请名称是否重复功能
 */
 $(document).ready(function() {
	 	processNameCanupdate();//设置标题是否可以修改。
	    initAttachment();//重新发起的时候初始化附件
	    setEnableUpload();
	    //表单处理逻辑
		async.series({
			init:function(callback){
				readonlyForm();
				callback();
			},
			isHidden:function(callback){
				setFormHidden(callback);
			},
			loadData:function(callback){
				setFormDate(callback);//如果是重新发起，则加载原表单数据
			},
			isCanEdit:function(callback){
				setWriteable(callback);
			},
			generateWidget:function(callback){
				initWidget(callback);
			}
		},function(err,result){
		}); 
});
 
 /**
 * 重新发起的时候初始化附件
 */
function initAttachment(){
	attachList.push(orialAttachList);
 }
 
 /**
  * 设置标题是否可以修改
  */
  function processNameCanupdate(){
 	 var paramData={
 			 "processId":$("#processId").val()
 	 };
 	 $.ajax({
 		 url:basePath+"jbpmflow/detailSearch!processNameCanupdate.action",
 		 type:"post",
 		 dataType:"html",
 		 data:paramData,
 		 async:false,
 		 success:function(data){
 			 if(data==0){ //不可修改
 				 $("#processInstanceName").attr("readonly",true)
 			 }
 		 }
 	 });
  }

  $("#closeButton").click(function(){
	  if(window.top && window.top.closeCurrentTab){
		  window.top.closeCurrentTab();
	  }else{
		  window.close();
	  }
  });
  
  
//发起流程
$("#sendButton").click(function(){
	var title =  $("#processInstanceName").val(); //流程名称
	if(!title){
		art.dialog.alert("申请名称不能为空!");
		return;
	}
	var outComeNameShow = $("#outComeNameShow").val();//内容
   	var holdStr=$("#holdStr").val();//主办人员ID
  	if(holdStr==""&&outComeNameShow!="结束"){
     	art.dialog.alert("请选择办理人员！");
     	return ;
    }
    $("body").lock();
    $("#sendButton").attr("disabled","disabled");
	 var formDataJSON = getJSONVal();
	 var attachJSON = getAttachJSON();
	 var  outComeName= $("#outComeName").val();  //节点 到TO
	 var paramData={
				'processId':$("#processId").val(),
				"formDataJSON":formDataJSON,
				"attachJSON":attachJSON,
				"advice":"发起流程",
				"title":title,
				"outComeName":outComeName,
				"holdStr":holdStr,
				"documentExtId":$("#documentExtId").val()
		 };
		$.ajax({
		      url:basePath+"jbpmflow/operation!startProcess.action",
		      type:"post",
		      data:paramData,
		      beforeSend:function(){
					$("body").lock();
				},
				complete:function(){
					$("body").unlock();
				},
		      success: function(data){
		    	  if(data.indexOf('success')>-1){
		    		  art.dialog.tips("操作成功!",1.5);
		    		  setTimeout(function(){
		    			  if(window.top && window.top.closeCurrentTab){
		    				  window.top.closeCurrentTab();		    			  
		    			  }else{
		    				  window.close();
		    			  }
		    		  },1500);
		    		 /* var message = "<font style='font-size:20px'>操作成功！</font><br/>您可以到“申请列表”中查看该记录。";
		    	    	 art.dialog({
		    	    		   title:"消息",
		    	    		   content:message,
		    	    		   width:320,
		    	    		   height:110,
		    	    		   icon:"succeed",
		    	    		   opacity:0.08,
		    	    		   lock:true,
		    	    		   ok:function(){
		    	    			   //window.location.href = basePath+"jbpmflow/listSearch!often.action";
		    	    			   window.top.closeCurrentTab();
		    	    		   }
		    	    		});*/
		    	  }
		      }
		  });
});

