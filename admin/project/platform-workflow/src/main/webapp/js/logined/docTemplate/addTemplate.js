
/**
 * @author wuzhou
 */
$(document).ready(function() {
	  //获取表单类别
    setFormType();
    $("#submit").click(function(){
    	save();
    });
    //替换文件
     $("#changeNewFile").click(function(){
    	 if($("#changeNewFile").val()=="更换"){
    		 	$("#changeNewFile").val("取消");
    		 	addNewFile();
    		}else{
    			$("#changeNewFile").val("更换");
    			cancleNewFile();
    		}
     });
});


function save(){
	if (!validator(document.getElementById("docform"))) {		
		   return;
	}else{
		var myfile = $("#myfile").val();
		if(myfile!=""){
			var arr = myfile.split(".");
			var hzm = arr[arr.length-1];
			
			if(hzm=="doc"||hzm=="dot" || hzm=="docx"){
				
			}else{
				art.dialog.alert("模板文件格式不正确！");
				return;
			}
		}
		
		
		var name = $("#name").val();
		var docTemplateId= $("#docTemplateId").val();
		 $.ajax({
				url : basePath+"workflowForm/docTemplateAjax_isExsit.action",
				type : "post",
				dataType :'json',
				data: {
	                'docTemplateId': docTemplateId,
	                'name':name
	            },
	            beforeSend:function(){
	    			$("body").lock();
	    	    },
	    		complete:function(){
	    			$("body").unlock();
	    		},
				success : function(data) {
					 if(data=="0"){
						 $("#docform").submit();
					 }else{
						 art.dialog.alert("模板名称已经存在！");
					 }
				}
			});
		 
	}
}


/**
 * 同步文件名称
 * @param obj
 */
function setTemplateFile(obj){
	var fileName = obj.value;
	var isdoc = isDocFile(fileName);
	if(isdoc){
		$("#showFile").val(fileName);
	}else{
		art.dialog.alert("文件格式不正确！ ");
		$("#showFile").val("");
	}
}


function isDocFile(fileName){
    if(fileName!=null && fileName !=""){
   //lastIndexOf如果没有搜索到则返回为-1
   if (fileName.lastIndexOf(".")!=-1) {
					var fileType = (fileName.substring(fileName.lastIndexOf(".")+1,fileName.length)).toLowerCase();
					var suppotFile = new Array();
					 suppotFile[0] = "doc";
                     suppotFile[1] = "DOC";
                     suppotFile[2] = "dot";
                     suppotFile[3] = "DOT";
                     suppotFile[4] = "docx";
                     suppotFile[5] = "DOCX";
					for (var i =0;i<suppotFile.length;i++) {
						 if (suppotFile[i]==fileType) {
							 	return true;
						 } else{
							 	continue;
						 }
					}
						//alert("文件类型不合法,只能是jpg、gif、bmp、png、jpeg类型！");
						return false;
   					} else{
   						//alert("文件类型不合法,只能是 jpg、gif、bmp、png、jpeg 类型！");
   						return false;
   					}
    		}
}


/***
***获取表单类别
**/
function setFormType(){
	$.ajax({
		url:basePath+"/documentType/getRedTemplateType.action",
		type:"post",
		dataType:"json",
		success:function(data){
			var jsonData = data; 
			$("#docTemplateType").empty();
			$("#docTemplateType").append("<option value=''>请选择</option>");
		    for(var i=0;i<jsonData.length;i++){  
		         $("#docTemplateType").append("<option value='"+jsonData[i].id+"'>"+jsonData[i].name+"</option>"); 
		    }
		    
		    var docTemplateId = $("#docTemplateId").val();
		    if(docTemplateId!=""){
		    	initData(docTemplateId);
		    }
		}
	});
	
 }


function initData(docTemplateId){
	
	       $.ajax({
				url : basePath+"workflowForm/docTemplateAjax_getDocTemplate.action",
				type : "post",
				dataType :'json',
				data: {
	                'docTemplateId': docTemplateId
	            },
				success : function(data) {
					var docTemplateId = data.docTemplateId;
					var docTemplateName = data.docTemplateName;
					var fileName = data.fileName;
					var docUrl = data.docUrl;
					var categoryId = data.categoryId;
					var userNames =data.userNames;
					var userIds = data.userIds;
					$("#name").val(docTemplateName);
					$("#docTemplateType").val(categoryId);
					$("#showFile").val(fileName);
					$("#src_showFile").val(fileName);
					$("#src_showFileSrc").val(fileName);
					var img="<img src='"+basePath+"images/u89_normal.png' /> "
					var edocUrl = encodeURI(docUrl);
					var url = "<a target=\"_blank\" href=\""+basePath+"/workflowForm/view.action?docUrl="+edocUrl+"\" > "+img+fileName+"</a>";
					$("#showFileSrc").html(url);
					
				}
			});
		
}

/**
 *替换文件
 */
function addNewFile(){
	$("#newFileTd").show();
}

/**
 *取消替换文件
 */
function cancleNewFile(){
	$("#newFileTd").hide();
	$("#showFile").val($("#src_showFile").val());
	$("#showFileSrc").val($("#src_showFileSrc").val());
	var myfile = $("#myfile");
	resetFileInput(myfile); //清空文件
}

function resetFileInput(file){   
    file.after(file.clone().val(""));   
    file.remove();   
} 


 