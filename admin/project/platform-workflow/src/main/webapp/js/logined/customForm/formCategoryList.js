/**
 * @author wuzhou
 */
$(document).ready(function() {
	// 获取表单分类列表
	getDataTable();
})
/**
 * 获取表单分类列表
 */
function getDataTable() {
	qytx.app.grid({
		id:"myTable",
		url:basePath+"workflowForm/findCategoryList.action",
		selectParam:{type:$("#type").val()},
		valuesFn:[{
			"aTargets" : [ 3 ],// 覆盖第4列
			"fnRender" : function(oObj) {
				var categoryId = oObj.aData.id;
				return '<a onclick="editFormCategory('+categoryId+')" style="cursor:pointer;"  class="view_url" id="editUrl">修改</a><a href="javascript:void(0)" onclick="deleteFormCategory('+categoryId+');return false;" class="view_url" id="viewUrl">删除</a>';
			}
		}]
	});
}
/**
 * 删除表单分类
 * @param id 表单分类id
 */
function deleteFormCategory(id){
	qytx.app.dialog.confirm('确定删除该表单分类吗？',function(){
		qytx.app.ajax({
			url : basePath+"workflowForm/deleteCategory.action",
			type : "post",
			dataType :'json',
			data: {
				'fc.categoryId': id,
				'fc.type':1
			},
			success : function(data) {
				if(data != null && data.marked !=null && data.marked == 1) {    
					getDataTable();
				} else if(data != null && data.marked !=null && data.marked == 0){
					qytx.app.dialog.alert('该分类下有表单，不能删除！');
				} else{
					qytx.app.dialog.alert('删除表单分类失败！');
					getDataTable();
				}
			}
		});
		
	});
}

/**
 * 添加
 */
function  addFormCategory(){
	var url = basePath+'logined/customForm/addFormCategory.jsp?type='+$("#type").val();
	qytx.app.dialog.openUrl({
		id : "addFormCategory",
        title:"新增表单分类",
        size:"S",
        url:url,
        ok:function(){
        	var iframe = this.iframe.contentWindow;
	    	iframe.saveFormCategoryInfo();
	    	getDataTable();
	    	return false;
        },
        cancel:true
	});
}

/**
 * 修改
 */
function  editFormCategory(categoryId){
	var url = basePath+"workflowForm/loadCategory.action?categoryId="+ categoryId;
	qytx.app.dialog.openUrl({
		url:url,
		id:"editFormCategory",
		title:"修改表单分类",
		size:"S",
		ok:function(){
			var iframe = this.iframe.contentWindow;
	    	iframe.saveCategory();
	    	getDataTable();
	    	return false;
		},
		cancel:true
	});
}
