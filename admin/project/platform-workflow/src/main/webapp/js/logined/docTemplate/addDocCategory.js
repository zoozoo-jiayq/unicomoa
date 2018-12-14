/**
 * @author wuzhou
 */
$(document).ready(function() {

	
    //新增表单分类
    $("#addCategory").click(function(){
        saveFormCategoryInfo();
        return false;
    });
    //返回列表
    $("#goback").click(function(){
        goback();
        return false;
    });
}) ;

/**
 * 保存表单分类信息
 */
function saveFormCategoryInfo(){
	if($.trim($("#formCategoryIndex").val())==""){
		art.dialog.alert("请输入模板类型排序号！");
		return false;
	}
	if($.trim($("#formCategoryName").val())==""){
		art.dialog.alert("请输入模板类型名称！");
		return false;
	}
	var paramData={
			'fc.orderIndex':$.trim($("#formCategoryIndex").val()),
			'fc.categoryName':$.trim($("#formCategoryName").val()),
			'fc.type':3
	};
	$.ajax({
	      url:basePath+"workflowForm/addCategory.action",
	      type:"post",
	      dataType: "json",
	      data:paramData,
	      beforeSend:function(){
  			$("body").lock();
  	      },
	  	  complete:function(){
	  			$("body").unlock();
	  	  },
	      success: function(data){
	    	  if(data.marked ==1 ) {
	    		  
	    		/*  art.dialog.alert("添加模板类型成功！",function(){*/
	    			    art.dialog.close();
				    /*    return true;
	    		  });*/
	               
	            } else if(data.marked == 0){
	            	art.dialog.alert('模板类型已经存在，请重新添加！');
	            }  else{
	            	art.dialog.alert("添加失败！");
	            }
	      }
	 }); 
}
function goback(){
	//window.location.href=basePath+"logined/docTemplate/docTemplateLCategoryist.jsp";
	art.dialog.close();
}

/**
 * 输入框只能输入数字
 * @param obj
 */
function testNum(obj){
	if(!/^(\d)*$/.test(obj.value)){//验证需要增加别的字符的时候/^(\d|;|,)*$/
		obj.value = obj.value.replace(/[^\d]/g,'');
	}
}