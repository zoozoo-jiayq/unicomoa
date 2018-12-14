/**
 * @author wuzhou
 */
$(document).ready(function() {
    //保存表单
    $("#saveForm").click(function(){
        saveFormInfo();
        return false;
    });
  //返回列表
    $("#returnList").click(function(){
        goback();
        return false;
    });
    //获取表单类别
    setFormType();
  //重置iframe高度
    window.parent.frameResize();
}) ;

/***
***获取表单类别
**/
function setFormType(){
	var paramData={
			'type':$("#type").val()
	};
	qytx.app.ajax({
		url:basePath+"workflowForm/findAllCategory.action",
		dataType: "json",
		data:paramData,
		success:function(data){
			var jsonData = data.fcList; 
			$("#formType").empty();
			$("#formType").append("<option value='0'>请选择</option>");
		    for(var i=0;i<jsonData.length;i++){  
		         $("#formType").append("<option value='"+jsonData[i].categoryId+"'>"+jsonData[i].categoryName+"</option>"); 
		    }
		    $("#formType option[value='"+formType+"']").attr("selected", true);
		}
	})
 }
/**
 * 保存表单信息
 */
function saveFormInfo(){
	if(!validator(document.getElementById("form1"))){
		return;
	}
	if($("#formType").val() == 0){
		showObjError($("#formType"), 'customForm.category_name_not_null');
		return ;
	}
	var categoryName = $("#formType").find('option:selected').text();
	var paramData={
			'fa.formId':$("#formId").val(),
			'fa.formName':$.trim($("#formName").val()),
			'oldFormName':$("#oldFormName").val(),
			'fa.categoryId':$("#formType").val()
	};
	qytx.app.ajax({
		url:basePath+"workflowForm/editForm.action",
		type:"post",
	    dataType: "json",
	    data:paramData,
	    success: function(data){
	    	  if(data.marked ==1 ) {
	    		  qytx.app.dialog.success(
//	    			  title:"消息",
//	    			  content:"保存表单成功!",
//	    			  ok:function(){
//	    				  window.location.href=basePath+"logined/customForm/listForm.jsp?categoryId="+$("#formType").val()+"&categoryName="+categoryName;
//		  	    		  return true;
//	    			  }
	    			  "保存表单成功!",
	    			  function(){
	    				  window.location.href=basePath+"logined/customForm/listForm.jsp?categoryId="+$("#formType").val()+"&categoryName="+categoryName;
	    			  });
	            } else if(data.marked == 0){
	            	qytx.app.dialog.alert("'表单名称已经存在，修改失败！'");
	            }  else{
	            	qytx.app.dialog.alert("修改失败！");
	            }
	      }
	});
}
/**
 * 返回列表
 */
function goback(){
	var categoryName = $("#formType").find('option:selected').text();
	window.location.href=basePath+"logined/customForm/listForm.jsp?categoryId="+$("#formType").val()+"&categoryName="+categoryName;
}
/**
 * 删除表单
 * @param id 表单id
 */
function deleteForm(id){
	var type = $("#formType").val();
	var categoryName = $("#formType").find('option:selected').text();
	qytx.app.dialog.confirm("确定删除该表单吗？",function(){
		qytx.app.ajax({
			url:basePath+"workflowForm/deleteForm.action",
			type : "post",
			dataType :'json',
			data: {
                'formId': id
            },
			success : function(data) {
				if(data != null && data.marked !=null && data.marked == 1) {    			
					qytx.app.dialog.success("删除表单成功!",function(){
						window.location.href=basePath+"logined/customForm/listForm.jsp?categoryId="+type+"&categoryName="+categoryName;
					});
				} else if(data != null && data.marked !=null && data.marked == 0){
					qytx.app.dialog.alert("表单已被使用，不能删除！");
				} else{
					qytx.app.dialog.alert('删除表单失败！');
				}
			}
		});
	});
}
/**
 * 导入表单
 */
function importForm(formId){
	qytx.app.dialog.openUrl({
		url:basePath+"logined/customForm/upload.jsp?formId="+formId,
		title:"导入表单",
		size:"S",
		ok:true
	});
}
/**
 * 预览表单
 */
function viewForm(formId){
	qytx.app.dialog.openUrl({
		title:"预览表单",
		url:basePath+"workflowForm/viewForm.action?formId="+formId,
		size:"L",
		close:true
	});
}