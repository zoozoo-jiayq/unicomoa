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
	//add by jiayq,如果是公文流程分类，需要显示是收文还是发文
	var type = $("#type").val();
	qytx.app.grid({
		id:"myTable",
		url:basePath+"workflowForm/findCategoryList.action",
		selectParam:{"type":$("#type").val()},
		valuesFn:[ {
			"aTargets" : [3],// 覆盖第4列
			"fnRender" : function(oObj) {
				var categoryId = oObj.aData.id;
				return '<a onclick="editProcessCategory('+categoryId+')" style="cursor:pointer;"  class="view_url" id="editUrl">修改</a><a href="javascript:void(0)" onclick="deleteProcessCategory('+categoryId+');return false;" class="view_url" id="viewUrl">删除</a>';
			}
		} ]
	});
}

/**
 * 删除单个流程分类
 * @param id
 */
function deleteProcessCategory(id){
	qytx.app.dialog.confirm("确定删除该流程分类吗？",function(){
		qytx.app.ajax({
			url : basePath+"workflowForm/deleteCategory.action",
			type : "post",
			dataType :'json',
			data: {
                'fc.categoryId': id,
                'fc.type':2
            },
			success : function(data) {
				if(data != null && data.marked !=null && data.marked == 1) {    
				//	art.dialog.alert('删除流程分类成功！');
					getDataTable();
				} else if(data != null && data.marked !=null && data.marked == 0){
					qytx.app.dialog.alert('该分类下有流程，不能删除！');
				} else{
					qytx.app.dialog.alert('删除流程分类失败！');
					getDataTable();
				}
			}
		});
	});
}

/**
 * 添加
 */
function  addProcessCategory(){
	var url = basePath+'logined/customForm/addProcessCategory.jsp?type='+$("#type").val();
	qytx.app.dialog.openUrl({
		id:"addProcessCategory",
		title:"新建流程分类",
		url:url,
		size:"S",
		ok:function(){
			var iframe = this.iframe.contentWindow;
	    	iframe.saveProcessCategoryInfo();
	    	getDataTable();
	    	return false;
		},
		cancel:true
	});
}

/**
 * 修改
 */
function  editProcessCategory(categoryId){
	var url = basePath+"workflowForm/loadCategory.action?categoryId="+ categoryId+"&type="+$("#type").val();
	qytx.app.dialog.openUrl({
		url:url,
		title:"修改流程分类",
		size:"S",
		ok:function(){
			var iframe = this.iframe.contentWindow;
	    	iframe.editProcessCategory();
	    	getDataTable();
	    	return false;
		},
		cancel:true
	});
}
