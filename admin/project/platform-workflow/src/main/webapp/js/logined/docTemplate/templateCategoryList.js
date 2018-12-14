/**
 * @author wuzhou
 */
$(document).ready(function() {
	// 获取表单分类列表
	getDataTable();
});
/**
 * 获取表单分类列表
 */
function getDataTable() {
	if (typeof oTable == 'undefined') {
		oTable = $('#myTable')
				.dataTable(
						{
							"bProcessing" : true,
							'bServerSide' : true,
							'fnServerParams' : function(aoData) {
								aoData.push(
									{ "name":"type", "value":3}
								);
								
							},
							"sAjaxSource" : basePath
									+ "workflowForm/findCategoryList.action",// 获取分类列表
							"sServerMethod" : "POST",
							"sPaginationType" : "full_numbers",
							"bPaginate" : true, // 翻页功能
							"bLengthChange" : false, // 改变每页显示数据数量
							"bFilter" : false, // 过滤功能
							"bSort" : false, // 排序功能
							"bInfo" : true,// 页脚信息
							"bAutoWidth" : false,// 自动宽度
							"iDisplayLength" : 20, // 每页显示多少行
							"aoColumns" : [ {
								"sTitle" : "序号",
								"mDataProp" : "no",
								"sWidth" : "40",
								"sClass" : "num"
							}, {
								"sTitle" : "类型名称",
								"mDataProp" : "name",
								"sWidth" : "100%",
								"sClass" : "longTxt"
							},{
								"sTitle" : "模板数",
								"mDataProp" : "count",
								"sWidth" : "60px",
								"sClass" : "data_r"
							},{
								"sTitle" : "创建时间",
								"mDataProp" : "createTime",
								"sWidth" : "130px",
								"sClass" : "tdCenter"
							},{
								"sTitle" : '操作',
								"mDataProp" : null,
								"sWidth" : "80",
								"sClass" : "data_l right_bdr0"
							} ],
							"oLanguage" : {
								"sUrl" : basePath + "plugins/datatable/cn.txt" // 中文包
							},
							"fnDrawCallback" : function(oSettings) {
								// $("#totalNum").html(oSettings.fnRecordsDisplay());
								// $("#companyAll").html(oSettings.fnRecordsDisplay());
								// $("#selectedNum").html(0);
								// $("#total").prop("checked",false);
								$('#myTable tbody  tr td[class="longTxt"]').each(function() {
									this.setAttribute('title', $(this).text());
								});
							},
							"aoColumnDefs" : [ {
								"aTargets" : [ 4 ],// 覆盖第4列
								"fnRender" : function(oObj) {
									var categoryId = oObj.aData.id;
									var count = oObj.aData.count;
									var loadStr = basePath+"workflowForm/loadCategory.action?categoryId="+ categoryId;
									var res = '<a style="cursor:pointer;" onclick="editTemplateCategory('+categoryId+');" class="view_url" id="editUrl">修改</a>';
								    if(count==0){
										   res+='<a href="javascript:void(0)" onclick="deleteFormCategory('+categoryId+');return false;" class="view_url" id="viewUrl">删除</a>';
								     }
									 return res;
								}
							} ]

						});
	} else {
		var oSettings = oTable.fnSettings();
		oSettings._iDisplayStart = 0;
		oTable.fnClearTable();
	}
}
/**
 * 删除表单分类
 * @param id 表单分类id
 */
function deleteFormCategory(id){
	art.dialog.confirm('确认要删除吗？删除后不可恢复。', function () {
	       $.ajax({
				url : basePath+"workflowForm/deleteCategory.action",
				type : "post",
				dataType :'json',
				data: {
	                'fc.categoryId': id,
	                'fc.type':1
	            },
				success : function(data) {
					if(data != null && data.marked !=null && data.marked == 1) {    
					//	art.dialog.alert('删除模板类型成功！');
						getDataTable();
					} else if(data != null && data.marked !=null && data.marked == 0){
						art.dialog.alert('该分类下有模板，不能删除！');
					} else{
						art.dialog.alert('删除模板类型失败！');
						getDataTable();
					}
				}
			});
		}, function () {
		    return;
		});
}


/**
 * 添加
 */
function  addTemplateCategory(){
	var url = basePath+'logined/docTemplate/addTemplateCategory.jsp';
	art.dialog.open(url, {
	    id : "addTemplate",
	    title : "新增模板类型",
	    width : 520,
	    height : 165,
	    close : function(){
			getDataTable();
	    	return true;
	    },
	    ok : function(){
	    	var iframe = this.iframe.contentWindow;
	    	iframe.saveFormCategoryInfo();	    	
	    	return false;
	    },
	    cancel : function(){
	    	return true;
	    }
	});
	
	
}

/**
 * 修改
 */
function  editTemplateCategory(categoryId){
	var url = basePath+"workflowForm/loadCategory.action?categoryId="+ categoryId;

	art.dialog.open(url,{
	    id : "modifyTemplate",
	    title : "修改模版类型",
	    width : 520,
	    height : 180,
	    lock : true,
	    drag:false,
	    opacity: 0.08,// 透明度
	    close : function(){
			getDataTable();
	    	return true;
	    },
	    ok : function(){
             var iframe = this.iframe.contentWindow;
             iframe.saveCategory();	
	    	 return false;
	    },
	    cancel : function(){
	    	return true;
	    }
	});
}