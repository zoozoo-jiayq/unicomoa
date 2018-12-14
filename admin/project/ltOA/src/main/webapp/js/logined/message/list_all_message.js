$(document).ready(function() {
	//$("#tableArea").hide();
	// 头部全选复选框
	$("#myTable").delegate("#total", "click", function(event) {
				checkTotal();
				event.stopPropagation();
			});

	// 子项复选框按钮
	$("#myTable").delegate(":checkbox[name='chk']", "click", function(event) {
				checkChange();
				event.stopPropagation();
			});
	
	//回车事件
	$(document).keydown(function(event){
		var code=event.which;
		if (code == 13) {
			//
			queryAllMessage();
			return false;
		}
	});

	// 单击确认
	$("#confirm").click(function() {
		$.removeTableCookie('SpryMedia_DataTables_myTable_list_all_message.jsp');
		_checkedIds="";
		queryAllMessage();
		return false;
	});

	// 单击返回
	$("#backBtn").click(function() {
				$("#searchArea").show();
				//$("#tableArea").hide();

				// 重置iframe高度
				frameResize();
	});

	// 单击删除
	$("#deleteBtn").click(function() {
				deleteMessage();
				return false;
	});

	// 单击导出
	$("#exportBtn").click(function() {
		exportMessageByVo();
		return false;
	});
	
	//
	queryAllMessage();
});

function queryAllMessage() {
	_checkedIds="";
	qytx.app.grid({
		id	:	"myTable",
		url	:	basePath + "message/setup_getAllMessagePage.action",
		iDisplayLength	:	20,
		selectParam	:	{
					"messageVo.msgType":$("#msgType").val(),
					"messageVo.contentInfo":$.trim($("#contentInfo").val()),
					"messageVo.startTime":$.trim($("#startTime").val()),
					"messageVo.endTime":$.trim($("#endTime").val()),
					"messageVo.sortFiled":$.trim($("#sortFiled").val()),
					"messageVo.sortType":$.trim($("#sortType").val())
			},
			valuesFn	:	[{
				"aTargets" : [0],// 覆盖第一列
				"fnRender" : function(oObj) {
					return '<input name="chk" value="' + oObj.aData.id
							+ '" type="checkbox" />';
				}
			}, {
				"aTargets" : [1],// 覆盖第二列
				"fnRender" : function(oObj) {
					// 查看详情
					var msgType = oObj.aData.msgType;
					if ('1' == msgType) {
						return "网页消息";
					} else if ('2' == msgType) {
						return "移动版(Android)";
					}
					return msgType;
				}
			}]
	});
	
}
/**
 * 头部全选记录
 */
function checkTotal() {
	var isTotalCheck = $("input:checkbox[id='total']").prop("checked");
	var checkNum = 0;
	if (isTotalCheck) {
		$("input:checkbox[name='chk']").prop("checked", function(i, val) {
					checkNum = checkNum + 1;
					return true;
				});
	} else {
		$("input:checkbox[name='chk']").prop("checked", false);
	}
}

/**
 * 删除选择的微讯信息
 */
function deleteMessage() {
	// 获取选择删除的微讯Id
	var chkbox = document.getElementsByName("chk");
	var ids = _checkedIds;
	if (ids.length <= 1) {
		art.dialog.alert(sprintf("message.msg_del_at_least_one"));
		return;
	}
	ids = ids.substring(0,ids.length-1).replace(/\,/g, "&messageId=");
	ids = "&messageId="+ids;
	// 删除微讯
	art.dialog.confirm(sprintf("message.msg_confirm_info"), function() {
		$.ajax({
					url : basePath
							+ "message/setup_deleteMessageById.action?type=delete",
					type : "post",
					dataType : 'text',
					data : ids,
					success : function(data) {
						if (data == "success") {
							_checkedIds="";
							queryAllMessage();
						} else {
							art.dialog
									.alert(sprintf("message.msg_del_failure"));
						}
					}
				});
	}, function() {
		return;
	});
}

/**
 * 根据vo选择删除微讯信息
 */
function deleteMessageByVo() {
	var paramData = {
		'messageVo.msgType' : $("#msgType").val(),
		'messageVo.contentInfo' : $.trim($("#contentInfo").val()),
		'messageVo.startTime' : $.trim($("#startTime").val()),
		'messageVo.endTime' : $.trim($("#endTime").val())
	};

	// 删除vo选择的微讯
	
	art.dialog.confirm(sprintf("message.msg_confirm_info_all"), function() {
				$.ajax({
							url : basePath
									+ "message/setup_deleteMessageByVo.action",
							type : "post",
							dataType : 'text',
							data : paramData,
							success : function(data) {
								if (data == "success") {
									art.dialog({
										   title:"消息",
										   content:sprintf("message.msg_del_success"),
										   width:320,
										   height:110,
										   icon:"succeed",
										   opacity:0.08,
										   lock:true,
										   ok:function(){},
										   close:function(){
											
										   }
										});
								} else {
									art.dialog
											.alert(sprintf("message.msg_del_failure"));
								}
							}
						});
			}, function() {
				return;
			});
}

function exportMessageByVo() {
	var ids = "";
	
	if(_checkedIds.length>0){
		ids = _checkedIds.substring(0, _checkedIds.length-1);
	}
	var url = basePath
	+ "message/setup_exportMessageByVo.action?messageVo.msgType="
	+ $("#msgType").val() + "&messageVo.contentInfo="
	+ $.trim($("#contentInfo").val()) + "&messageVo.startTime="
	+ $.trim($("#startTime").val()) + "&messageVo.endTime="
	+ $.trim($("#endTime").val()) + "&messageVo.sortFiled="
	+ $.trim($("#sortFiled").val()) + "&messageVo.sortType="
	+ $.trim($("#sortType").val())+"&messageVo.ids="+ids;
	if(!ids){
		qytx.app.dialog.confirm('你是否要导出全部数据？', function() {
			window.open(url);
		}, function() {
                  return;
		});
	}else{
		window.open(url);
	}
    
}

/**
 * 子项复选框变更
 */
function checkChange() {
	if ($('#myTable :checkbox[name="chk"][checked="checked"]').length == $('#myTable :checkbox[name="chk"]').length) {
		$("input:checkbox[id='total']").prop("checked", true);
	} else {
		$("input:checkbox[id='total']").prop("checked", false);
	}
}