/**
 * 删除选中的人事档案
 */
function deleteUserRecordChecked() {
	var confirmMsg = sprintf("record.confirm_to_delete_select");
	if (getCheckedCount() == 0) {
		qytx.app.dialog.alert(sprintf("record.no_select_item_to_delete"));
	} else {
		var ids = getCheckedValues();
		qytx.app.dialog.confirm(confirmMsg, function() {
					// 删除选中的记录ajax
				qytx.app.ajax({
								url : basePath + "logined/record/delete.action",
								type : "post",
								dataType : 'text',
								data : {
									ids : ids
								},
								success : function(data) {
									// art.dialog.alert(data);
									getDataTable(); // 刷新档案信息
								}
							});
				}, function() {

				});
	}
}

/**
 * 人事档案列表页面 js
 */
function getDataTable() {
	qytx.app.grid({
		id	:	"dataTable_record",
		url	:	basePath + "logined/record/listAjax.action",
		iDisplayLength:	tableDisplayLength,
		selectParam:	{
							"groupId":$("#groupId").val(),
							"treeType":$("#treeType").val(),
							"from":$("#from").val(),
							"searchJson":$("#searchJson").val()
						},
		valuesFn:	[{
						"aTargets" : [1],// 覆盖第二列：姓名
						"fnRender" : function(oObj) {
							var showURL = basePath + "logined/record/show.action?from=detail&id="
									+ oObj.aData["id"];
							return "<a href='" + showURL + "' title='"+oObj.aData["userName"]+"'>"
									+ oObj.aData["userName"] + "</a> ";
						}
					}, {
						"aTargets" : [2],// 覆盖第二列-性别
						"fnRender" : function(oObj) {
							if (oObj.aData["sex"].toString() == "1") {
								return "男";
							}
							if (oObj.aData["sex"].toString() == "0") {
								return "女";
							}
						}
					}, {
						"aTargets" : [8],// 覆盖第八列-操作
						"fnRender" : function(oObj) {
							var showURL = basePath + "logined/record/show.action?from=detail&id="
									+ oObj.aData["id"];
							var editURL = basePath + "logined/record/edit.action?id="
									+ oObj.aData["id"];
			
							var showHTML = "<a href='" + showURL + "' title='点击查看' >查看</a>";
							var editHTML = "<a href='" + editURL + "' title='点击编辑' >编辑</a>";
							return showHTML;
						}
					}]
	});
}

/**
 * 搜索结果列表页面的“返回”按钮单击事件
 */
function returnSearch() {
	openURLForRecord(basePath + "logined/record/searchPage.action?searchJson="
			+ $("#searchJson").val());
}

/**
 * 检查from是否来自搜索，判断是否需要显示返回按钮
 */
function initForSearch() {
	var from = $("#from").val();
	from = $.trim(from);
	if (from == "search") {
		$("#btnForSearchResult").show();
		document.title = document.title + " - 查询结果";
		$(".pageTitle em").html($(".pageTitle em").html() + " - 查询结果");
		$(".search_no").hide();
	}
}

// 处理表格中没有值的时候在IE7及一下显示的边框问题
function processTableTdEmptyTo() {
	$("table td").each(function() {
				var content = $(this).html();
				if (content == undefined || $.trim(content) == "") {
					$(this).html("&nbsp;");
				}
			});
}

$(document).ready(function() {
			initForSearch();
			getDataTable();
			$("body").show();
		});