/**
 * @author wuzhou
 */
$(document).ready(function() {
	// 获取表单列表
	getDataTable();
	
})
/**
 * 获取表单列表
 */
function getDataTable() {
	var searchName="";
	if($("#searchName").val()=="null" || $("#searchName").val()==""){
		searchName="";
	}else{
		searchName=$.trim($("#searchName").val());
	}
	var formType=0;
	if($("#formType").val()==null || $("#formType").val()=="null" || $("#formType").val()==""){
		formType=0;
	}else{
		formType=$.trim($("#formType").val());
	}
	qytx.app.grid({
		id:"myTable",
		url:basePath+"workflowForm/findFormList.action",
		selectParam:{"formType":formType,"searchName":searchName},
		valuesFn:[ {
			"aTargets" : [ 2 ],// 覆盖第3列
			"fnRender" : function(oObj) {
				var formId = oObj.aData.id;
				var formName = oObj.aData.name;
				var loadStr = basePath+"workflowForm/loadForm.action?formId="+ formId+"&type="+$("#type").val();
				var editorStr = basePath
						+ "workflowForm/toEditor.action?formId="
						+ formId+"&type="+$("#type").val();
				var reportStr = basePath
						+ "workflowForm/reportForm.action?formId="
						+ formId+"&type="+$("#type").val();
				var uploadStr = basePath
				        + "logined/customForm/upload.jsp?formId="
				        + formId+"&type="+$("#type").val();
				var viewStr = basePath+"workflowForm/viewForm.action?formId="+formId+"&type="+$("#type").val();
				return '<a href="javascript:void(0)" onclick="viewForm('+formId+');return false;" class="view_url" id="viewUrl" title="预览">预览</a>'+
				'<a href="'+ loadStr+ '" class="view_url" id="editUrl" title="修改">修改</a>'+
				'<a href="javascript:void(0)" onclick="importForm('+formId+');return false;" class="view_url" id="importUrl" title="导入">导入</a>'+
				'<a href="'+ reportStr+ '" class="view_url" id="exportUrl" title="导出">导出</a>'+
				'<a href="javascript:void(0)" onclick="deleteForm('+formId+');return false;" class="view_url" id="viewUrl" title="删除">删除</a>'+
				'<a href="'+ editorStr+ '" class="view_url" id="formUrl" target="_blank" title="表单设计">表单设计</a>';
			}
		} ]
	});
}
function deleteForm(id){
	qytx.app.dialog.confirm("确定删除该表单吗？",function(){
		qytx.app.ajax({
			url : basePath+"workflowForm/deleteForm.action",
			type : "post",
			dataType :'json',
			data: {
                'formId': id
            },
			success : function(data) {
				if(data != null && data.marked !=null && data.marked == 1) {    
				//	art.dialog.alert('删除表单成功！');
					getDataTable();
				} else if(data != null && data.marked !=null && data.marked == 0){
			   		qytx.app.dialog.alert('表单已被使用，不能删除！');
				} else{
					qytx.app.dialog.alert('删除表单失败！');
					getDataTable();
				}
			}
		});
	})
}
/**
 * 预览表单
 */
function viewForm(formId){
	qytx.app.dialog.openUrl({
		title:"预览表单",
		size:"L",
		url:basePath+"workflowForm/viewForm.action?formId="+formId,
		close:true
	});
}
/**
 * 导入表单
 */
function importForm(formId){
	qytx.app.dialog.openUrl({
		title:"导入表单",
		size:"S",
		url:basePath+"logined/customForm/upload.jsp?formId="+formId,
		ok:true
	});
}
