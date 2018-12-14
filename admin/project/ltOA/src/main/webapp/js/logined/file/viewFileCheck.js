$(document).ready(function() {

			var notifyId = $("#sortId").val();
			var id = $("#id").val();
			getViewFileCheckList();
		})

/**
 * 获取未读公告列表
 * 
 * @return
 */
function getViewFileCheckList() {
	if (typeof oTable == 'undefined') {
		oTable = $('#myTable').dataTable({
					"bProcessing" : true,
					'bServerSide' : true,
					'fnServerParams' : function(aoData) {
						aoData.push({
									"name" : "sortId",
									"value" : $.trim($("#sortId").val())
								}, {
									"name" : "id",
									"value" : $.trim($("#id").val())
								});
					},
					"sAjaxSource" : basePath
							+ "file/getViewFileCheckList.action",
					"sServerMethod" : "POST",
					"sPaginationType" : "full_numbers",
					"bPaginate" : false, // 翻页功能
					"bLengthChange" : false, // 改变每页显示数据数量
					"bFilter" : false, // 过滤功能
					"bSort" : false, // 排序功能
					"bInfo" : false,// 页脚信息
					"bAutoWidth" : false,// 自动宽度
					"iDisplayLength" : 10, // 每页显示多少行
					"aoColumns" : [{
								"sTitle" : '部门/成员单位',
								"mDataProp" : "department",
								"sWidth" : "10%",
								"sClass" : "tdCenter"
							}, {
								"sTitle" : '已读人员',
								"mDataProp" : "reader",
								"sWidth" : "5%",
								"sClass" : "data_l"
							}, {
								"sTitle" : '未读人员',
								"mDataProp" : "notReader",
								"sWidth" : "15%",
								"sClass" : "data_l"
							}],
					"oLanguage" : {
						"sUrl" : basePath + "plugins/datatable/cn.txt" // 中文包
					}
				});
	} else {
		var oSettings = oTable.fnSettings();
		oSettings._iDisplayStart = 0;
		oTable.fnClearTable();
	}
}

function clearFileCheck() {
	var id = $("#id").val();
	var dataParam = {
		'id' : id
	};
	art.dialog.confirm('确定要清空查阅情况吗？', function() {
				$.ajax({
							type : 'post',
							url : basePath + "file/clearFileCheck.action",
							data : dataParam,
							dataType : 'json',
							async : false,
							success : function(data) {
								getViewFileCheckList();
							}
						});
			}, function() {
				return;
			});
}

function sendRemind() {
	var isRemind = document.getElementById("isRemind").checked;
	if (isRemind) {
		//
	}
	window.location.href = document.referrer;
}

function closeDialog() {
	art.dialog.close();
}