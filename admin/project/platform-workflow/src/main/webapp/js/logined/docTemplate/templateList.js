/**
 * @author wuzhou
 */
$(document).ready(function() {
	_checkedIds="";
	// 获取表单分类列表
	  document.onkeydown=function(event){
	        var e = event || window.event || arguments.callee.caller.arguments[0];
	         if(e && e.keyCode==13){ // enter 键
	        	 _checkedIds="";
	             //要做的事情
	             $.removeTableCookie('SpryMedia_DataTables_myTable_docTemplateList.jsp');
	        	 getDataTable();
	        }
	    }; 
    setFormType();
});
/**
 * 获取表单分类列表
 */
function getDataTable() {
	_checkedIds="";
	if (typeof oTable == 'undefined') {
		oTable = $('#myTable')
				.dataTable(
						{
							"bProcessing" : true,
							'bServerSide' : true,
							'fnServerParams' : function(aoData) {
								var docTemplateType = $("#docTemplateType").val();
								var title = $.trim($("#title").val());
								aoData.push(
									{ "name":"docTemplateType", "value":docTemplateType},
									{ "name":"name", "value":title}
								);
							},
							"sAjaxSource" : basePath+ "workflowForm/docTemplate_list.action",// 获取分类列表
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
								"sTitle" : "<input type='checkbox' id='checkAll'/>",
								"mDataProp" : null,
								"sClass" : "chk"
							},{
								"sTitle" : "序号",
								"mDataProp" : "no",
								"sWidth" : "40",
								"sClass" : "num"
							},{
								"sTitle" : "模板名称",
								"mDataProp" : "docTemplateName",
								"sWidth" : "60%",
								"sClass" : "longTxt"
							},{
								"sTitle" : "模板文件",
								"mDataProp" : "fileName",
								"sWidth" : "40%",
								"sClass" : "longTxt"
							},{
								"sTitle" : "模板类型",
								"mDataProp" : "typeName",
								"sWidth" : "120px",
							},{
								"sTitle" : "上传时间",
								"mDataProp" : "createTime",
								"sWidth" : "130px",
								"sClass" : "tdCenter"
							},{
								"sTitle" : '操作',
								"mDataProp" : null,
								"sWidth" : "80",
								"sClass" : "right_bdr0"
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
								// 绑定全选的checkbox事件
								bindCheckAllEvent();

								//_getChecked();
							},
							"aoColumnDefs" : [{
								"aTargets" : [0],// 覆盖第一列
								"fnRender" : function(oObj) {
										return '<input name="checkChild" value="' + oObj.aData.docTemplateId
												+ '" type="checkbox" />';
								}
							},{
								"aTargets" : [3],// 覆盖第一列
								"fnRender" : function(oObj) {
									 var fileName = oObj.aData.fileName;
									 var docurl = oObj.aData.docurl;
									var img="<img src='"+basePath+"images/u89_normal.png' /> "
									var url = basePath+"filemanager/prevViewByPath.action?filePath="+docurl;
									var res = "<a target=\"_blank\" href=\""+url+"\" > "+img+fileName+"</a>";
									return fileName;
								}
							},{
								"aTargets" : [6],// 覆盖第4列
								"fnRender" : function(oObj) {
									 var docTemplateId = oObj.aData.docTemplateId;
									 var loadStr = basePath+"workflowForm/loadDocTemplate.action?docTemplateId="+ docTemplateId;
									 var res = '<a  onclick="addDocTempalte('+docTemplateId+');return false;" style=\"cursor:pointer;\" class="view_url" id="editUrl">修改</a>';
								     res+='<a   style=\"cursor:pointer;\" onclick="deleteTemplate('+docTemplateId+');return false;" class="view_url" id="viewUrl">删除</a>';
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
 * 删除多个文件
 */
function  deleteMore(){
	// 获取选择角色id
	var checkedIds = document.getElementsByName("checkChild");
	var ids = "";
	var selLen = 0;
	var arr;
	/*if(_checkedIds!=null&&_checkedIds!=""){
		var checkedIds=_checkedIds.substring(0, _checkedIds.length-1);
		var arr=checkedIds.split(",");
		for (var i = 0; i < arr.length; i++) {
			ids += ''+arr[i]+",";
			selLen++;
		}
	}*/
	 for(var i=0;i<checkedIds.length;i++){
	      var temp = checkedIds[i];
	      if(temp.checked){
	    	  ids+=temp.value+",";
	       }
	    }
	if (ids.length == 0) {
		art.dialog.alert('请选择要删除的模板！');
		return;
	}

	art.dialog.confirm('确认要删除吗？删除后不可恢复。', function() {
				$.ajax({
							url : basePath + "workflowForm/docTemplateAjax_deleteMore.action",
							type : "post",
							dataType : 'text',
							data: {
				                'ids': ids
				            },
							success : function(data) {
								if (data != "") {
								//	art.dialog.alert('删除成功！');
									getDataTable();
								} else {
									art.dialog.alert('删除失败！');
									getDataTable();
								}
							}
						});
			}, function() {
				return;
			});
}


/**
 * 删除表单分类
 * @param id 表单分类id
 */
function deleteTemplate(id){
	art.dialog.confirm('确认要删除吗？删除后不可恢复。', function () {
	       $.ajax({
				url : basePath+"workflowForm/docTemplateAjax_delete.action",
				type : "post",
				dataType :'json',
				data: {
	                'docTemplateId': id
	            },
				success : function(data) {
					//	art.dialog.alert('删除模板成功！');
						getDataTable();
				}
			});
		}, function () {
		    return;
		});
}
 
/***
***获取表单类别
**/
function setFormType(){
	 getDataTable();
	$.ajax({
		url:basePath+"/documentType/getRedTemplateType.action",
		type:"post",
		dataType:"json",
		success:function(data){
			var jsonData = data; 
			$("#docTemplateType").empty();
			$("#docTemplateType").append("<option value=''>--全部--</option>");
		    for(var i=0;i<jsonData.length;i++){  
		         $("#docTemplateType").append("<option value='"+jsonData[i].id+"'>"+jsonData[i].name+"</option>"); 
		    }
		    
		}
	});
 }


function  addDocTempalte(id){
	var url = basePath+"logined/docTemplate/addTemplate.jsp";
	if(id>0){
		url+="?docTemplateId="+id;
	} 
	document.location.href=url;
}

