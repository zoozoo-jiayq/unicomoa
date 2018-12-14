/**
 * @author wuzhou
 */
$(document).ready(function() {

	
    //保存表单
    $("#saveCategory").click(function(){
    	saveCategory();
        return false;
    });
  //返回列表
    $("#returnList").click(function(){
        goback();
        return false;
    });
}) ;

/**
 * 保存表单信息
 */
function saveCategory(){
	if($.trim($("#formCategoryIndex").val())==""){
		art.dialog.alert("请输入模板类型排序号！");
		return false;
	}
	if($("#formCategoryName").val()==0){
		art.dialog.alert("请输入模板类型名称！");
		return false;
	}
	var paramData={
			'fc.categoryId':$("#formCategoryId").val(),
			'fc.categoryName':$.trim($("#formCategoryName").val()),
			'oldCategoryName':$("#oldFormCategoryName").val(),
			'fc.orderIndex':$("#formCategoryIndex").val(),
			'fc.type':3
	};
	$.ajax({
	      url:basePath+"workflowForm/editCategory.action",
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
	    		 /* art.dialog.alert("修改模板类型成功！",function(){*/
	    			  art.dialog.close();
	    		/*  });*/
	    		  
/*	    		  art.dialog({
				        lock: true,
					    background: '#000',
					    opacity: 0.1,
					    title: '提示',
					    content: '修改模板类型成功！',
					    icon: 'succeed',
					    ok: function(){
					    	//window.location.href=basePath+"logined/docTemplate/docTemplateLCategoryist.jsp";
					    	art.dialog.close();
					        return true;
					    },
						close: function(){
							//window.location.href=basePath+"logined/docTemplate/docTemplateLCategoryist.jsp";
							art.dialog.close();
					        return true;
					    }
					});*/
	            } else if(data.marked == 0){
	            	art.dialog.alert('模板类型已经存在，修改失败！');
	            }  else{
	            	art.dialog.alert("修改失败！");
	            }
	      }
	 }); 
}
/**
 * 返回列表
 */
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