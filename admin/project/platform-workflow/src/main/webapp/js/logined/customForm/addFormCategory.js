/**
 * @author wuzhou
 */
/**
 * 保存表单分类信息
 */
function saveFormCategoryInfo(cb){
	
	if(!qytx.app.valid.check({
		form:document.getElementById("formCategoryform")
	})){
		return false;
	}
	var paramData={
			'fc.orderIndex':$.trim($("#formCategoryIndex").val()),
			'fc.categoryName':$.trim($("#formCategoryName").val()),
			'fc.type':$("#type").val()
	};
	qytx.app.ajax({
		url:basePath+"workflowForm/addCategory.action",
	    type:"post",
	    dataType: "json",
	    data:paramData,
	    async:false,
	    success: function(data){
	    	if(data.marked ==1 ) {
	    			qytx.app.dialog.tips("操作成功!",function(){
		    				art.dialog.close();
	    			});
	            } else if(data.marked == 0){
	            	//qytx.app.dialog.alert('表单类别名称已经存在，请重新添加！');
	            	qytx.app.valid.showObjError($("#formCategoryName"),"formcategory.cate_name_repeat");
	            }  else{
	            	qytx.app.dialog.alert("添加失败！");
	            }
	      }
	});
}

function goback(){
	//window.location.href=basePath+"logined/customForm/formCategoryList.jsp";
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