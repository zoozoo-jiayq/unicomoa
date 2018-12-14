$(document).ready(function() {
	$.removeTableCookie('SpryMedia_DataTables_Table_listprogramme.jsp');
	getList();
	// 保存按钮绑定事件
	$("#search").bind("click", function() {
		 $("#begTime").val();
		 _checkedIds="";
		 $.removeTableCookie('SpryMedia_DataTables_Table_listprogramme.jsp');
				getList();
			});

	//回车事件
	$(document).keydown(function(event){
		var code=event.which;
		if (code == 13) {
			_checkedIds="";
			$.removeTableCookie('SpryMedia_DataTables_Table_listprogramme.jsp');
			getList();
			return false;
		}
	});
	// 导出按钮绑定事件
	$("#export").bind("click", function() {
				exportDaily();
			});

	// 头部全选复选框
	$("#Table").delegate("#total", "click", function(event) {
				checkTotal();
				event.stopPropagation();
			});
	// 删除
	$(".delete").live("click", function() {
				deleteObject();
			});

	// 导出按钮绑定事件
	$("#returnback").bind("click", function() {
				returnback();
			});

	// 返回按钮绑定事件
	$("#goback").bind("click", function() {
				goback();
			});

	// 列表详情页面返回列表页面
	setTimeout("gobackType()", 100);
});

function gobackType() {
	var gobackType = $("#gobackType").val();
	if (gobackType == 1) {
		jQuery("#search").trigger("click");
	}
}

// 处理事件的函数
function FSubmit(e) {
	if (e == 13) {
		// 获取search内容
		jQuery("#search").trigger("click");
		e.returnValue = false;
		// 返回false，在没有使用document.loginForm.submit()时可用于取消提交
	}
}

/**
 * 头部全选记录
 */
function checkTotal() {
	var isTotalCheck = $("input:checkbox[id='total']").prop("checked");
	var checkNum = 0;
	if (isTotalCheck) {
		$("input:checkbox[name='ck']").prop("checked", function(i, val) {
					checkNum = checkNum + 1;
					return true;
				});
	} else {
		$("input:checkbox[name='ck']").prop("checked", false);
	}
}

/**
 * 选择记录
 */
function check() {
	var checkTotalNum = $("input:checkbox[name='ck']");
	var isAllChecked = true;
	checkTotalNum.each(function(i, val) {
				if ($(checkTotalNum[i]).prop("checked")) {
				} else {
					isAllChecked = false;
				}
			});
	if (!isAllChecked) {
		$("input:checkbox[id='total']").prop("checked", false);
	} else {
		$("input:checkbox[id='total']").prop("checked", true);
	}
}

/**
 * 批量删除
 * 
 * @return
 */
function deleteObject() {
	var deleteIds = _checkedIds;;

	if (deleteIds.length <=1) {
		art.dialog.alert("请至少选择一项");
		return;
	}
	if (deleteIds != "") {
		deleteIds = deleteIds.substring(0, deleteIds.length - 1);
	}
	qytx.app.dialog.confirm('确定要删除吗？', function() {
		ajaxDelete(deleteIds);
	}, function() {
		return;
	});
}

/**
 * 删除日程
 * @param ids
 */
function ajaxDelete(ids){
	qytx.app.ajax({
		url : basePath
				+ "calendar/deleteProgramms.action?ids="
				+ ids,
		type : "post",
		dataType : "html",
		shade	:	true,
		success : function(data) {
			if (data == 0) {
//				art.dialog.alert("删除成功！", function() {
//							getList();
//						});
				$.removeTableCookie('SpryMedia_DataTables_Table_listprogramme.jsp');
				_checkedIds="";
				getList();
			} else {
				qytx.app.dialog.alert("删除失败！");
			}
		}
	});
}


function deleteWhenClickDelete(id){
	
	qytx.app.dialog.confirm('确认要删除吗？', function() {
		ajaxDelete(id);
	}, function() {
		
	});
}
/**
 * 查询类型修改
 * 
 * @return
 */
function searchTypeChange() {
	var searchType = $("input[name='searchType']:checked").val();
	if (searchType == 1) {
		$("#userIds").val($("#userId").val());
	} else if (searchType == 2) {
		$("#userIds").val(0);// 0：代表查询全部人员的日志
	}
}

/**
 * 查询日志获取列表
 */
function getList() {
	
	var begTime= $("#begTime").val();
	if(begTime!=null&&begTime!=''){
		begTime+=':00';
	}
//	alert(begTime)
	var endTime=$("#endTime").val();
	if(endTime!=''){
		endTime+=':00';
	}
	qytx.app.grid({
		id	:	"Table",
		url	:	basePath + "calendar/listProgramme.action",
		selectParam	:	{"bean.begTime":begTime,"bean.endTime":endTime},
		valuesFn	:	[{
			"aTargets" : [0],
			"fnRender" : function(oObj) {
				var vid = oObj.aData.id;
				return '<input type="checkbox" value="' + vid
						+ '" name="ck"  onclick="check();" />';
			}
		},{
			"aTargets" : [2],
			"fnRender" : function(oObj) {
				var endTimeStr = oObj.aData.endTimeStr;
				 if(endTimeStr){
					 endTimeStr = $.trim(endTimeStr);
				 }
				 if(endTimeStr==''){
					 endTimeStr='--';
				 }
				return endTimeStr;
			}
		},{
			"aTargets" : [3],
			"fnRender" : function(oObj) {
				var content = oObj.aData.content;
				var id = oObj.aData.id;
				var result='<a  href="javascript:programmeView('+id+')" >'+content+'</a>';
				return result;
			}
		}, {
			"aTargets" : [4],
			"fnRender" : function(oObj) {
				var id = oObj.aData.id;
				var isModify = oObj.aData.isModify;
				var modify = '<a onclick="modify('+id+')" href="javascript:modify('+id+')">修改</a>';
				if(!isModify==1){
					modify='<a  href="javascript:programmeView('+id+')">查看</a>';;
				}
				var result=modify+'<a  href="javascript:deleteWhenClickDelete('+id+')" >删除</a>';
				return result;
			}
		}

	]
	});
}

/**
 * 修改日程
 * @param id
 */
function modify(id) {
	var url = basePath + "calendar/toUpdateProgramme.action?id=" + id+"&type=list";
	Programme.modify(Programme.modifyTitle,url,800,450,id);
}



/**
 * id :日志ID
 * 
 * @return 日志详细信息
 */
function programmeView(id) {
	var url = basePath + "calendar/showViewProgramme.action?Id="
			+ id;
	Programme.show(Programme.detailTitle,url,800,450,id);

}

/**
 * id :日志ID
 * 
 * @return 日志详细信息
 */
function dailyView(id) {
	window.top.closeTab('dailyView');
	window.top.addTab('dailyView', basePath
					+ 'daily/getDailyView.action?doUpdateType=2&dailyId=' + id,
			'日志详情', 1, '');
}


/**
 * 返回
 * 
 * @return
 */
function goback() {
	var url = basePath + "logined/daily/latestDailyList.jsp";
	window.document.location = url;
}
