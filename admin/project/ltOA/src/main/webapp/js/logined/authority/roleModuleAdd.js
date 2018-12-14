/**
 * @author REN
 */
$(document).ready(function() {
	//单击添加
	$("#addRoleModule").click(function(){
		submit_form();
		return false;
	});

	//单击复选框
	$("#moduleTable").delegate(":checkbox", "click", function(event){
		checkPrev(this);    
	   	checkSub(this);
		event.stopPropagation();
    });
});
/**
 * 选中祖先复选框
 */
function checkPrev(element){
    if(!element){
        return;
    }
	var isChecked=$(element).prop("checked");
	if(isChecked){
        var listCheckbox=$(element).parents("ul").parent().find(":checkbox:eq(0)");
        //选中父元素
        listCheckbox.prop("checked", true);
	}
}
/**
 * 选中后代复选框
 */
function checkSub(element){
    if(!element){
        return;
    }
    var isChecked=$(element).prop("checked");
    var listCheckbox = null;
    if($(element).attr("id") == "oneCheckbox"){
    	listCheckbox=$(element).closest("td").find(":checkbox");
    }else{
    	listCheckbox=$(element).closest("li").find(":checkbox");
    }
	if(isChecked){
		listCheckbox.prop("checked", function( i, val ) {
			if (!$(this).prop("disabled")) {
				return true;
			}
        });
	}else{
		listCheckbox.prop("checked", false);
	}
}
/**
 * 提交信息
 * @return
 */
function submit_form() {
	//得到角色id
	var roleId=$("#roleId").val();
	if(!roleId){
		return;
	}
	var moduleIds="";
	//得到所有选择模版
    var selectedCheckBox=$("#moduleTable").find(":checkbox");
    selectedCheckBox.each(function(i){
		 if($(this).prop("checked")){
		 	 moduleIds += '&moduleIds='+$(this).attr("value"); 
		 }
    });	
	qytx.app.ajax({
		url : basePath+"authority/addRoleModule.action",
		type : "post",
		dataType :'text',
		data:moduleIds+"&roleId="+roleId,
		success : function(data) {
			qytx.app.dialog.tips("修改权限成功");
	   }
	   });
}