/**
 * @author wuzhou
 */
$(document).ready(function() {
	//添加表单
    $("#createForm").click(function(){
    	formView(0,3);
        return false;
    });
  //更新缓存
    $("#refresh").click(function(){
    	qytx.app.dialog.success("缓存更新成功!",function(){
    		window.location.href = basePath+"workflowForm/getFormCategorys.action?type="+$("#type").val();
    	});
    });
    var _searchName="";
    
    //查询表单
    $("#searchName").keyup(function(){
    	var sName = $("#searchName").val();
    	if(sName!=_searchName){
    		_searchName= $("#searchName").val();
    		searchForm();
    	}
    });
    searchForm();
    $(".menu-on").find("a").click();
    
});
/**
 * id :表单id或者分类ID
 * @return 表单详细信息或者表单列表
 */
function formView(id,name,type){
	if(type==1){
		$("#page").attr("src",basePath+"workflowForm/loadForm.action?formId="+id+"&type="+$("#type").val());
	}else if (type==2) {
		$("#page").attr("src",basePath+"logined/customForm/listForm.jsp?categoryId="+id+"&categoryName="+encodeURI(encodeURI(name))+"&type="+$("#type").val());
	}else{
		$("#page").attr("src",basePath+"logined/customForm/addForm.jsp?type="+$("#type").val());
	}
	
}
function searchForm(){
	//if($.trim($("#searchName").val()) != ""){
		$("#page").attr("src",basePath+"logined/customForm/listForm.jsp?searchName="+$.trim($("#searchName").val()));
	//}
}
