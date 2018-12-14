/**
 * @author wuzhou
 */
$(document).ready(function() {
    //新增表单
    $("#addForm").click(function(){
        saveFormInfo();
        return false;
    });
    //获取表单类别
    setFormType();
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
			'fa.formName':$.trim($("#formName").val()),
			'fa.categoryId':$("#formType").val()
	};
	qytx.app.ajax({
		url:basePath+"workflowForm/addForm.action",
		dataType: "json",
	    data:paramData,
	    shade:true,
	    success:function(data){
	    	if(data.marked ==1 ) {
	    		  qytx.app.dialog.success("添加表单成功!",function(){
	    			  window.location.href=basePath+"/logined/customForm/addForm.jsp?type="+$("#type").val();
            		  return true;
	    		  });
	         }else if(data.marked == 0){
	            	qytx.app.dialog.alert('表单名称已经存在，请重新添加！');
	         }else{
	            	qytx.app.dialog.alert("添加失败！");
	         }
	    }
	});
 
}