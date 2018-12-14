/**
 * 查看流程实例
 * @param processId
 * @param processInstanceName
 * @param taskId
 * @param taskName
 */
function goTaskView(processId,processInstanceName){
	var url = basePath +"jbpmflow/detailSearch!view.action?processId="
	+processId+"&processInstanceId="+encodeURI(processInstanceName); 
	var title = processInstanceName.substring(0,processInstanceName.lastIndexOf("."));
	if(window.top && window.top.addTab){
		window.top.addTab(RndNum(15), url, title, true, null);
	}else{
		window.open(url, title);
	}
}

/**
* 生成随机数
* @param n
* @returns {String}
*/
function RndNum(n) {
var rnd = "";
for ( var i = 0; i < n; i++)
	rnd += Math.floor(Math.random() * 10);
return rnd;
};



/**
 * 查看流程图 不带节点
 */
function goProcessView(processId){
	var url =  "workflow/jpdl!view.action?processAttributeId="+processId;
	try{
		window.top.closeTab(1001);
		window.top.gotoUrl(1001,'流程图',basePath+url,'');
	}catch(e){
		window.open(basePath+url);
	}
}



/**
 * 查看流程图
 */
function goView(processId,taskName){
	if(taskName.length>3){
		if(taskName.substring(0,3)=="TO "){
			taskName = taskName.substring(3,taskName.lengh);
		}
	}
	var url = basePath + "workflow/jpdl!view.action?processAttributeId="+processId+"&actives="+taskName;
	try{
		window.parent.closeTab(1002);
		if(taskName!=""){
			window.top.gotoUrl(1002,'流程图('+taskName+")",url,'');
		}else{
			window.top.gotoUrl(1002,'流程图查看',url,'');
		}
	}catch(e){
		if(taskName!=""){
			// window.top.gotoUrl(1002,'流程图('+taskName+")",url,'');
			window.open(url);
		}else{
			// window.top.gotoUrl(1002,'流程图查看',url,'');
			window.open(url);
		}
	}
}

/**
 * 中止任务
 */
function stopTask(processInstanceId,taskId,taskName){
	 var paramData = {
	            'taskId': taskId,
	            "advice": "",
	            "taskName": taskName,
	            "processInstanceId": processInstanceId,
	            "approvalResult":"中止"
	        };
	        art.dialog.confirm('确认中止吗？', function () {
	            $.ajax({
	                url: basePath + "jbpmApp/approve_stopTask.action",
	                type: "post",
	                dataType: "html",
	                data: paramData,
	                success: function (data) {
	                    if (data == 1) {
	                    	getDataTable();
	                    } else {
	                        art.dialog.alert('该工作流程不能中止！');
	                    }
	                }
	            });
	        }, function () {
	            return;
	        });

}

/**
 * 获取状态
 * @param state
 */
function getShowState(state){
	var res="";
	if(state=="start"){
		res="<font >未审批</font>";
	}
	if(state=="approve"){
		res="<font>审批中</font>";
	}
	if(state=="suspend"){
		res="<font>已挂起</font>";
	}
	if(state=="end"){
		res="完结";
	}
	if(state=="endnoagree"){
		res="<font >驳回</font>";
	}
	if(state=="stop"){
		res="已中止";
	}
	if(state == "repeal"){
		res = "撤销";
	}
 
	return res;
}

//打印预览
function printView(){
	var processId = $("#processId").val();
	var instanceId = $("#processInstanceId").val();
	window.open(basePath+"/jbpmflow/detailSearch!printView.action?processId="
		+processId+"&processInstanceId="+encodeURI(instanceId),"打印预览","toolbar=yes,menubar=no,scrollbars=yes, resizable=yes,location=no, status=no");
}

//打开办理历史
function openHistoryShoaList(){
   var processInstanceId = $("#processInstanceId").val();
   var rollbackProcessInstanceId = $("#rollbackProcessInstanceId").val();
   if(rollbackProcessInstanceId){
	   processInstanceId = rollbackProcessInstanceId;
   }
    var urlStr = basePath + "jbpmflow/detailSearch!adviceHistoryShowList.action?processInstanceId=" + processInstanceId  ;
	qytx.app.dialog.openUrl({
		title:"办理历史",
		url:urlStr,
		size:"L",
		close:true
	});
}

/**
 * 设置表单是否可以上传附件
 */
function setEnableUpload(){
	 var processId = $("#processId").val();
	 var paramData={
				'processId':processId
		 };
		$.ajax({
		      url:basePath+"jbpmflow/detailSearch!isEnableUpload.action?random="+Math.random(),
		      type:"post",
		      dataType: "html",
		      data:paramData,
		      success: function(data){
		    	    if(data==1){//能上传附件
		    	    }else{ //不能上场附件
		    	    	 $("#yesUpload").hide();
		    	    }
		    	}
		 }); 
}

/**
 * 设置表单是否显示
 */
function setFormHidden(callback){
	if($("#history").val() == 'history'){
		callback();
		return;
	}
	var processId = $("#processId").val();
	var taskId = $("#taskId").val();
	var processInstanceId = $("#processInstanceId").val();
	var paramData={
			'processId':processId,
			'taskId':taskId,
			'processInstanceId':processInstanceId
	 };
	$.ajax({
	      url:basePath+"jbpmflow/detailSearch!isFormHidden.action",
	      type:"post",
	      dataType: "html",
	      data:paramData,
	      success: function(data){
	    	   if(data!=""){
	    		   jsonData = eval("("+data+")");  
					for(var i=0;i<jsonData.length;i++){  
						var propertyName = jsonData[i].propertyName;
						$("*[name='"+propertyName+"']").outerHTML(" 保密字段 ");
				     }
	    	   }
	    	  callback();
	    	}
	 }); 
}


/**
 * 设置是否可以编辑
 */
 function setWriteable(callback){
 	 if($("#history").val() == 'history'){
 	 	callback();
 	 	return ;
 	 }
	 var processId = $("#processId").val();
	 var taskId = $("#taskId").val();
	 var processInstanceId = $("#processInstanceId").val();
	 var paramData={
				'processId':processId,
				'taskId':taskId,
				'processInstanceId':processInstanceId
	 };
	$.ajax({
	      url:basePath+"jbpmflow/detailSearch!isWriteable.action",
	      type:"post",
	      dataType: "html",
	      data:paramData,
	      success: function(data){
	    	if(data!=""){
					jsonData = eval('('+data+')'); 
					for(var i=0;i<jsonData.length;i++){  
						var propertyName = jsonData[i].propertyName;
						$("*[name='"+propertyName+"']").removeAttr("disabled");
						$("*[name='"+propertyName+"']").removeAttr("readonly");
				     }
	    	}
	    	callback();
	    	}
	 }); 
 }
 
 jQuery.fn.outerHTML = function(s) {
	    return (s) ? this.before(s).remove() : jQuery("<p>").append(this.eq(0).clone()).html();
	}
 
 /**
  * 将表单元素设置为只读的
  */
 function readonlyForm(){
	 var jsonMap = new Map();
	 /** 遍历div下面所有input类型的数据*/
	 var divinpu = $('#form input');
	 $.each(divinpu,function(i,n){
		 $(this).attr("readonly","readonly") ;
	 });
	 /** 遍历所有textArea */
	 var divinpu = $('#form textarea');
	 $.each(divinpu,function(i,n){
		 $(this).attr("readonly","readonly") ;
	 });
	 /** 遍历所有select */
	 var divinpu = $('#form select');
	 $.each(divinpu,function(i,n){
		 $(this).attr("readonly","readonly") ;
	 });
 }
 
 /**
  * 获取表单中所有元素的值
  * @returns {String}
  */
 function  getJSONVal(){
	
	 var jsonMap = new Map();
	 /** 遍历div下面所有input类型的数据*/
	 var divinpu = $('#form input');
	 $.each(divinpu,function(i,n){
		  var type = $(this).attr("type");
		  if(type=="radio"){
			  if($(this).attr("checked")=="checked"){
				jsonMap.put($(this).attr("name"),$(this).val());
			  }
		  }else   if(type=="text"){
				jsonMap.put($(this).attr("name"),$(this).val());
		  } else if(type=="checkbox"){
			  	var ck = $(this).attr("checked");
			  	if(ck){
			  		var oldV = jsonMap.get($(this).attr("name"));
			  		if(!oldV){
			  			oldV = "";
			  		}
			  		var nV = $(this).val();
			  		jsonMap.put($(this).attr("name"),oldV+nV+",");
			  	}
	        }else{
	        	jsonMap.put($(this).attr("name"),$(this).val());
	        }
	 });
	 /** 遍历所有textArea */
	 var divinpu = $('#form textarea');
	 $.each(divinpu,function(i,n){
		jsonMap.put($(this).attr("name"),$(this).val());
	 });
	 /** 遍历所有select */
	 var divinpu = $('#form select');
	 $.each(divinpu,function(i,n){
		 jsonMap.put($(this).attr("name"),$(this).val());
	 });
	 
	 var res="";
	 //遍历map获取内容
	 if(jsonMap.size()>0){
			res+="[";
			for( var i=0;i<jsonMap.size();i++){
				var value =jsonMap.arr[i].value;
				var name =jsonMap.arr[i].key;
		          res+='{"name":"'+name+'","value":"'+value+'"},';
		      }
			res = res.substring(0, res.length-1);
			res+="]";
		}
      return res;
 }
 
 /**
  * 设置form值
  */
 function setFormDate(callback){
 	var rollbackInstanceId = $("#rollbackProcessInstanceId").val();
	var instanceId = $("#processInstanceId").val();
	var theInstanceId = "";
	if(rollbackInstanceId){
		theInstanceId = rollbackInstanceId;
	}
	if(instanceId){
		theInstanceId = instanceId;
	}
	var paramData={
			'processId':$("#processId").val(),
			'processInstanceId':theInstanceId
	 };
 	$.ajax({
 	      url:basePath+"jbpmflow/detailSearch!getFormData.action",
 	      type:"post",
 	      dataType: "html",
 	      data:paramData,
 	      success: function(data){
				var map = new Map();
				var  jsonData = eval("("+data+")");  
				var show=false;
     		    for(var i=0;i<jsonData.length;i++){  
     		       var name = jsonData[i].name;
     		       var value = jsonData[i].value;
     		       map.put(name,value);
     		       if(i==jsonData.length-1){
     		    	   show=true;
     		       }
     		    }   
			    if(show){
			    	var divinpu = $('#form input');
					 $.each(divinpu,function(i,n){
						  var type = $(this).attr("type");
						  if(type=="radio"){
							 if($(this).val()==map.get($(this).attr("name"))){
								 $(this).attr("checked",true);;
							 }
						  }else  if(type=="text"){
							   $(this).val(map.get(($(this).attr("name"))));
						  }else if(type=="checkbox"){
//							   if(map.get($(this).attr("name"))=="checked"){
//								   $(this).attr("checked",true);;
//							   }
							  var vas = map.get($(this).attr("name"));
							  vas = vas.split(",");
							  var thisVal = $(this).val(); 
							  for(var i=0; i<vas.length; i++){
								  if(vas[i] == thisVal){
									  $(this).attr("checked",true);
								  }
							  }
						  } else{
				        	 $(this).val(map.get(($(this).attr("name"))));
						  }
					 });
					 
					 /** 遍历所有textArea */
					 var divinpu = $('#form textarea');
					 $.each(divinpu,function(i,n){
						 $(this).val(map.get(($(this).attr("name"))));
					 });
					 /** 遍历所有select */
					 var divinpu = $('#form select');
					 $.each(divinpu,function(i,n){
						 $(this).val(map.get(($(this).attr("name"))));
					 });
			    }
			    if($("#sendButton")){
 	    			$("#sendButton").removeAttr("disabled");
			    }
 	    		if(callback){
 	    			callback();
 	    		}
 	    	}
 	 }); 
 }
 
 //获取附件信息
 function getAttachJSON(){
		var res="";
		if(attachList.length>0){
			res = attachList.join(",");
		}
		if(res.indexOf(",") == 0){
			res = res.substring(1, res.length);
		}
	    return res;
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
   
  /**
   * 文件后缀名判断的函数 根据不同的后缀名称 进行相应的处理
   * @param {} type
   * @return {}
   */
  function getFileType(type) {
  	if (type.lastIndexOf(".") != -1) {
  		type = type.substr(type.lastIndexOf(".")+1, type.length);
  	}
  	type = type.toLocaleLowerCase();
  	var defaultType = {
  		txt : "fileTxt",
  		doc : "fileWord",
  		ppt : "filePPT",
  		excel : "fileExcel",
  		img : "filePicture",
  		rar : "fileRar",
  		html:"fileHtml",
  		yinyue:"fileYinyue"
  	};
  	switch (type) {
  		case "txt" :
  			return defaultType.txt;
  		case "doc" :
  		case "docx" :
  			return defaultType.doc;
  		case "ppt" :
  		case "pptx" :
  			return defaultType.ppt;
  		case "xls" :
  		case "xlsx" :
  			return defaultType.excel;
  		case "gif" :
  		case "jpg" :
  		case "jpeg" :
  		case "png" :
  			return defaultType.img;
  		case "rar" :
  		case "zip" :
  		case "7z" :
  			return defaultType.rar;
  		case "js" :
  		case "html" :
  		case "jsp" :
  			return defaultType.html;
  		case "mp3" :
  		case "mp4" :
  		case "avi" :
  		case "rmvb" :
  			return defaultType.yinyue;
  		default :
  			return defaultType.txt;
  	}
  	
  }
  
//  查看办理历史
  function viewHistory(processId,instanceId){
	  qytx.app.dialog.openUrl({
		  title:"查看办理历史",
		  url:basePath+"/jbpmflow/detailSearch!toViewHistory.action?processId="+processId+"&processInstanceId="+encodeURI(instanceId),
		  size:"L",
		  close:true
	  });
	  
  }
  
  //显示任务的超时信息
  function showInfo(instanceId){
	  var inId = encodeURI(instanceId);
	  $.get(basePath+"/jbpmflow/detailSearch!getTimeSet.action?processInstanceId="+inId,function(data){
		  //已停留
		  var yitingliu = data.yitingliu;
		  //办理时限
		  var banlishixian = data.banlishixian;
		  //已超时
		  var yichaoshi = data.yichaoshi;
		  //总用时
		  var zongyongshi = data.zongyongshi;
		  art.dialog({
			  	padding: 0,
			  	width:200,
			  	height:100,
			    follow: document.getElementById(instanceId),
			    content: "<span><label style='float:left;width:80px;text-align:right'>已停留：</label>"+yitingliu+"</span><br/>" +
			    		 "<span><label style='float:left;width:80px;text-align:right'>办理时限：</label>"+banlishixian+"</span><br/>"+
			    		 "<span><font color='red'><label style='float:left;width:80px;text-align:right'>已超时：</label>"+yichaoshi+"</font></span><br/>"+
			    		 "<span><label style='float:left;width:80px;text-align:right'>总用时：</label>"+zongyongshi+"</span>"
			});
	  },"json");
  }
  
  //删除流程
  function deleteProcessInstance(instanceId,callback){
	  art.dialog.confirm("确定要删除此任务吗?",function(){
		  $.get(basePath+"/jbpmflow/operation!deleteInstance.action?processInstanceId="+encodeURI(instanceId),function(data){
			  callback();
		  });
	  });
  }
  
  //催办
  function nofityNextUser(instanceId){
	  $.get(basePath+"/jbpmflow/operation!notifyNextUser.action?processInstanceId="+encodeURI(instanceId),function(data){
		  if(data == 'success'){
			  qytx.app.dialog.success("催办成功！");
		  }else{
			  art.dialog.alert("委托失败!");
		  }
	  })
  }
  
  //委托
  function assigneeOther(taskId,callback){
	  qytx.app.dialog.openUrl({
		  url:basePath+"/jbpmflow/operation!assigneeOther.action?taskId="+taskId,
		  size:"M",
		  title:"委托设置",
		  ok:function(){
			  var ifr = this.iframe;
			  var subWin = ifr.contentWindow;
			  var content = $("#newAssigneeId",subWin.document).val();
			  if(!content){
				  art.dialog.alert("请选择被委托人!");
				  return false;
			  }else{
				  $.post(basePath+"/jbpmflow/operation!doAssigneeOther.action?taskId="+taskId+"&newAssigner="+content,function(data){
					  if(data == 'success'){
						  qytx.app.dialog.tips("委托设置成功!",callback);
					  }else{
						  art.dialog.alert("委托失败!");
					  }
				  })
			  }
		  },
		  cancel:true
	  });
  }
