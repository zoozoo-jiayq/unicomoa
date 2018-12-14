/**
 * 删除选中的人事档案
 */
function deleteUserRecordChecked() {
	var confirmMsg = sprintf("record.confirm_to_delete_select");
	if (_checkedIds <= 1) {
		qytx.app.dialog.alert(sprintf("record.no_select_item_to_delete"));
	} else {
		var ids = _checkedIds.substring(0,_checkedIds.length-1);
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
									_checkedIds="";
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
	_checkedIds="";
	//合并输入框
	var key_word = $.trim($("#key_word").val());
	qytx.app.grid({
		id	:	"dataTable_record",
		url	:	basePath + "logined/record/listAjax.action",
		iDisplayLength:	tableDisplayLength,
		selectParam:	{
							"userRecordSearchVo.keyWord":key_word,
							"userRecordSearchVo.sex":$("#sex").val(),
							"from":$("#from").val()
						},
		valuesFn	:	[{
							"aTargets" : [0],// 覆盖第一列
							"fnRender" : function(oObj) {
								return '<input name="checkChild" value="' + oObj.aData["id"]
										+ '" type="checkbox" />';
							}
						}, {
							"aTargets" : [1],// 覆盖第二列：姓名
							"fnRender" : function(oObj) {
								var showURL = basePath + "logined/record/show.action?id="
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
								var showURL = basePath + "logined/record/show.action?id="
										+ oObj.aData["id"];
								var editURL = basePath + "logined/record/edit.action?id="
										+ oObj.aData["id"];
				
								var showHTML = "<a href='" + showURL + "' title='点击查看' >查看</a>";
								var editHTML = "<a href='" + editURL + "' title='点击修改' >修改</a>";
								return showHTML + "&nbsp;" + editHTML;
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

function getNewDataTable(){
	// 清除table页的cookie数据
	$.removeTableCookie('SpryMedia_DataTables_dataTable_record_list_manager.jsp');
	getDataTable();
}

$(document).ready(function() {
	        getNewDataTable();
			
			// 支持enter查询功能
			 //回车事件
			$(document).keydown(function(event){
				var code=event.which;
				if (code == 13) {
					getNewDataTable();//要触发的方法
		        }
			});
			
			// 头部全选复选框
			$("#dataTable_record").delegate("#checkAll", "click", function(event) {
				checkTotal();
				event.stopPropagation();
			});
			
			// 子项复选框按钮
			$("#dataTable_record").delegate(":checkbox[name='checkChild']", "click", function(event) {
				checkChange();
				event.stopPropagation();
			});
			
			
});

/**
 * 头部全选记录
 */
function checkTotal() {
	var isTotalCheck = $("input:checkbox[id='checkAll']").prop("checked");
	var checkNum = 0;
	if (isTotalCheck) {
		$("input:checkbox[name='checkChild']").prop("checked", function(i, val) {
			checkNum = checkNum + 1;
			return true;
		});
	} else {
		$("input:checkbox[name='checkChild']").prop("checked", false);
	}
}

/**
 * 子项复选框变更
 */
function checkChange(){
	if ($('#dataTable_record :checkbox[name="checkChild"][checked="checked"]').length ==
		$('#dataTable_record :checkbox[name="checkChild"]').length){
		$("input:checkbox[id='checkAll']").prop("checked", true);
	}else{
		$("input:checkbox[id='checkAll']").prop("checked", false);
	}
}
