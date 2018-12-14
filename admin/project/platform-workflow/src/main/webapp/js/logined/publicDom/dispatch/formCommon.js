jQuery(document).ready(function($){
	async.series({
		//加载表单源代码
		init:function(callback){
			loadFormSource("customForm",callback);
		},
		//设置表单不能编辑
		isHidden:function(callback){
			readonlyForm("customForm");
			callback();
		},
		//加载数据
		loadData:function(callback){
			loadFormData("customForm",callback);
		},
		//设置保密字段
		isSecret:function(callback){
			setUpSecretProperties("customForm", encodeURI(instanceId), callback)
		},
		//设置可写字段
		isCanEdit:function(callback){
			var currentUserId = $("#currentUserId").val();
			var instanceId = $("#instanceId").val();
			readonlyFormAuthority("customForm",currentUserId,encodeURI(instanceId),callback); 
		},
		byMenu:function(callback){
			setUpReadonly("customForm",callback);
		},
		generateWidget:function(callback){
			initWidget(callback);
		}
	},function(err,result){
	});
	
});

/**
 * 设置form值
 */
function setFormData(formId,data){
     var map = new Map();
	 var  jsonData = [];
	 try{
	 jsonData = eval("("+data+")"); 
	 }catch(e){
		 
	 }
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
	    	var divinpu = $('#'+formId+' input');
			 $.each(divinpu,function(i,n){
				  var type = $(this).attr("type");
				  if(type=="radio"){
					 if($(this).val()==map.get($(this).attr("name"))){
						 $(this).attr("checked",true);;
					 }
				  }else  if(type=="text"){
					   $(this).val(map.get(($(this).attr("name"))));
				  }else if(type=="checkbox"){
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
			 var divinpu = $('#'+formId+' textarea');
			 $.each(divinpu,function(i,n){
				$(this).val(map.get(($(this).attr("name"))));
			 });
			 /** 遍历所有select */
			 var divinpu = $('#'+formId+' select');
			 $.each(divinpu,function(i,n){
				 $(this).val(map.get(($(this).attr("name"))));
			 });
			 
	    }
	     
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

//只读
function readonlyForm(formId){
	 var divinpu = $('#'+formId+ ' input');
	 $.each(divinpu,function(i,n){
		 $(this).attr("readonly","readonly") ;
	 });
	 /** 遍历所有textArea */
	 var divinpu = $('#customForm textarea');
	 $.each(divinpu,function(i,n){
		 $(this).attr("readonly","readonly") ;
	 });
	 /** 遍历所有select */
	 var divinpu = $('#customForm select');
	 $.each(divinpu,function(i,n){
		 $(this).attr("readonly","readonly") ;
	 });
}


//设置当前用户的访问表单权限,
//update by jiayq,获取有权限的字段
function readonlyFormAuthority(formId,currentUserId,instanceId,callback){
	var url= basePath+"baseSet/ajax_getReadOnlyFormAuthority.action";
	var paramData = {
			'instanceId':instanceId
	};
		$.ajax({
			url : url,
			type : "post",
			dataType :'html',
			data:paramData,
			success : function(data) {
				if(data!=""){
					var  jsonData = eval("("+data+")");  
					for (var i = 0; i < jsonData.length; i++) {
						var propertyName = jsonData[i].propertyName;
						$("*[name='"+propertyName+"']").removeAttr("disabled");
						$("*[name='"+propertyName+"']").removeAttr("readonly");
			         }
				}
				callback();
				}
		});
} 

//设置保密字段
function setUpSecretProperties(formId,instanceId,callback){
	var url= basePath+"baseSet/ajax_getSecretProperties.action";
	var paramData = {
			'instanceId':instanceId
	};
		$.ajax({
			url : url,
			type : "post",
			dataType :'html',
			data:paramData,
			success : function(data) {
				if(data!=""){
					var  jsonData = eval("("+data+")");  
					for (var i = 0; i < jsonData.length; i++) {
						var propertyName = jsonData[i].propertyName;
						$("*[name='"+propertyName+"']").outerHTML(" 保密字段 ");
			         }
				}
				callback();
				}
		});
}

//加载表单源代码
function loadFormSource(formId,callback){
	var instanceId = $("#instanceId").val();
	$.get(basePath+"/dom/public!ajaxFormTemplate.action?instanceId="+encodeURI(instanceId)+"&r="+Math.random(),function(data) {
		if(data == 'delete'){
			$("#"+formId).html("<font color='red'>该公文类型已删除或者该公文类型没有对应的表单!</font>");
		}else{
			$("#"+formId).html(data);
		}
		callback();
	});
}

function loadFormData(formId,callback){
	var instanceId = $("#instanceId").val();
	//获取表单属性值
	$.get(basePath+"/dom/public!ajaxFormPropertyValue.action?instanceId="+encodeURI(instanceId)+"&r="+Math.random(),function(data) {
		// body...
		//设置表单值
		setFormData(formId,data);
		callback();
	});
}

//根据不同的菜单和功能，设置是否可编辑
function setUpReadonly(formId,callback){
	var taskName = $("#taskName").val();
	var source = $("#source").val();
	var currentUserId = $("#currentUserId").val();
	var instanceId = $("#instanceId").val();
	var flag = $("#history").val();
	if(taskName == '发文分发' || taskName == '归档'){
		readonlyForm(formId);
	}

	if($("#source").val() == '系统外收文'){
		if(taskName == '收文分发'  || taskName == '归档'){
			readonlyForm(formId);
		}
	}else if(source == '系统内收文'){
		//readonlyForm();
	}
	//如果是查看已办结的任务，则不能编辑表单
	if(flag == 'history'){
		readonlyForm(formId);
		$(".menuList a").attr("disabled","disabled");
		$("#printlink").removeAttr("disabled");
	}
	callback();
}


/*
 *保存表单
 */
function doSaveForm() {
	// body...
	var instanceId = $("#instanceId").val();
	var taskName = $("#taskName").val();
	var customFormValue = getCustomFormValueMap();
	$.post("${ctx}/dom/public!saveDom.action",{
		"customFormValue":customFormValue,
		"taskId":$("#taskId").val(),
		"instanceId":instanceId,
		"taskName":taskName
	},function(data){
		if(data == "success"){
			try{
				saveSignByForm("yzform");
			}catch(e){
				
			}
			art.dialog.alert("表单保存成功!",function(){
			     return true;
			});
			 
		}
	});
}

//获取自定义表单的元素
function getCustomFormValueMap(){
	 var jsonMap = new Map();
	 var divinpu = $('#customForm input');
	 $.each(divinpu,function(i,n){
		  var type = $(this).attr("type");
		  if(type=="radio"){
			  if($(this).attr("checked")=="checked"){
				  jsonMap.put($(this).attr("name"),$(this).val());
			  }
		  }else   if(type=="text"){
			    jsonMap.put($(this).attr("name"),$(this).val());
		  } else if(type=="checkbox"){
//			     jsonMap.put($(this).attr("name"),$(this).attr("checked"));
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
	 var divinpu = $('#customForm textarea');
	 $.each(divinpu,function(i,n){
		 jsonMap.put($(this).attr("name"),$(this).val());
	 });
	 /** 遍历所有select */
	 var divinpu = $('#customForm select');
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