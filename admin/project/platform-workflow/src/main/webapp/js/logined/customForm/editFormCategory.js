/**
 * @author wuzhou
 */
/**
 * 保存表单信息
 */
function saveCategory(){
	if(!qytx.app.valid.check({
		form:document.getElementById("formCategoryform")
	})){
		return false;
	}
	var paramData={
			'fc.categoryId':$("#formCategoryId").val(),
			'fc.categoryName':$.trim($("#formCategoryName").val()),
			'oldCategoryName':$("#oldFormCategoryName").val(),
			'fc.orderIndex':$("#formCategoryIndex").val(),
			'fc.type':1
	};
	qytx.app.ajax({
		  url:basePath+"workflowForm/editCategory.action",
	      type:"post",
	      dataType: "json",
	      data:paramData,
	      success: function(data){
	    	  if(data.marked ==1 ) {
	    		  qytx.app.dialog.tips("操作成功!",function(){
	    				art.dialog.close();
	    		  });
	            } else if(data.marked == 0){
	            	qytx.app.valid.showObjError($("#formCategoryName"),"formcategory.cate_name_repeat");
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
	 art.dialog.close();
     return true;
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

/**
 * 修改表单名称 隐藏错误提示
 */
$("#formCategoryName").bind("change",function(){
	qytx.app.valid.hideError($("#formCategoryName"));
});