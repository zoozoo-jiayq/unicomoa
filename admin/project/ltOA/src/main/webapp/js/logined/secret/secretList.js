$(document).ready(function(){
	/**
	 * 获得字段权限设置
	 */
	getDatatable();
	
	
	$("#myTable").delegate("#checkAll", "click", function(event){    
	   	checkTotal();
		event.stopPropagation();
    });
	$("#myTable").delegate(":checkbox[name='checkChild']", "click", function(event) {
		checkChange();
		event.stopPropagation();
	});
});

/**
 * 获得字段权限设置
 */
function getDatatable(){
	qytx.app.grid({
		id	:	"myTable",
		url	:	basePath + "secret/secretSettingsList.action",
		iDisplayLength	:	15,
		valuesFn	:	[
			        	 	 {
			        	 		 "aTargets": [ 0 ],//覆盖第1列-checkbox
			        	 		 "fnRender": function (oObj) {
			        	 			 var id = oObj.aData["id"];
			        	 			 var html = '<input type="checkbox" name="checkChild" value="'+id+'" />';
			        	 			 return html;
			        	 		 }
			        	 	 },
							{
							    "aTargets": [ 4 ],//覆盖第5列-操作
							    "fnRender": function (oObj) {
							        var id = oObj.aData["id"];
							        var html = '<a href="javascript:void(0);" onclick="toEdit(\''+id+'\');">修改</a><a href="javascript:void(0);" onclick="del(\''+id+'\')">删除</a>';
							        return html;
							    }
							}
		        	 	 ]
	});
}

/**
 * 跳转到新增页面
 */
function toAdd(){
	window.location.href=basePath+"/logined/secret/secretAdd.jsp";
}

/**
 * 删除选中的保密设置
 * @param id
 */
function del(id){
	var ids = "";
	if(id){
		ids = id;
	}else{
		if(!_checkedIds){
			qytx.app.dialog.alert("请至少选择一项！");
			return;
		}
		ids = _checkedIds;
	}
	qytx.app.dialog.confirm("确定要删除吗？", function(){
		qytx.app.ajax({
			url: 	basePath+"secret/delete.action",
			type:	"post",
			data:	{ids:ids},
			dataType:"text",
			shade:true,
			success:function(data){
				if(data=="1"){
					qytx.app.dialog.tips("删除成功！", function(){
						window.location.reload();
					});
				}else{
					qytx.app.dialog.alert("操作失败！");
				}
			}
		});
	});
}


function toEdit(id){
	window.location.href = basePath+"/secret/toEdit.action?id="+id;
}


function checkTotal(){
	var isTotalCheck=$("input:checkbox[id='checkAll']").prop("checked");
	var checkNum=0;
	if(isTotalCheck){
		$("input:checkbox[name='checkChild']").prop("checked", function( i, val ) {
			checkNum=checkNum+1;
            return true;
        });
	}else{
		$("input:checkbox[name='checkChild']").prop("checked", false);
	}
}


function checkChange(){
	if ($('#myTable :checkbox[name="checkChild"][checked="checked"]').length ==
		$('#myTable :checkbox[name="checkChild"]').length){
		$("input:checkbox[id='checkAll']").prop("checked", true);
	}else{
		$("input:checkbox[id='checkAll']").prop("checked", false);
	}
}