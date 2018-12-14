/**
 * @author REN
 */
$(document).ready(function() {
	// 清除cookie中的分页信息
	$.removeTableCookie('SpryMedia_DataTables_productionScheduleTable_productionScheduleList.jsp');

	// 获取信息列表
	getDataTable();
	

	// 单击删除
	$("#userDelete").click(function() {
				deleteUser();
				return false;
			});
	
	// 单击新增
	$("#addproductionSchedule").click(function() {
		window.location.href = basePath + "logined/productionSchedule/productionScheduleAdd.jsp";
		return false;
	});

	
	// 查询
	$("#searchLoginUser").click(function() {
		$.removeTableCookie('SpryMedia_DataTables_productionScheduleTable_productionScheduleList.jsp');

		getDataTable();//要触发的方法
	    return false;
    });
	
	 //回车事件
	$(document).keydown(function(event){
		var code=event.which;
		if (code == 13) {
			// 清除cookie中的分页信息
			$.removeTableCookie('SpryMedia_DataTables_productionScheduleTable_productionScheduleList.jsp');

			getDataTable();//要触发的方法
        }
	});
	
	$("#uploadError").click(function(){
		uploadError();
	});
});
/**
 * 获取列表
 */
function getDataTable() {

	
	qytx.app.grid({
		id	:	"productionScheduleTable",
		url	:	basePath + "productionSchedule/getProductionSchedule.action",
		iDisplayLength:	tableDisplayLength,
		selectParam:	{
			"startTime":$.trim($("#startTime").val()),
			"endTime":$.trim($("#endTime").val())
						},
		valuesFn:	[ 
	         	 {
						"aTargets" : [22],
						"fnRender" : function(oObj) {
							var id = oObj.aData.id;	
							return '<a href="javascript:void(0);" class="view_url" onclick=updateSchedule("'+id+'") >修改</a>'
							+ '<a href="javascript:void(0);" class="view_url" onclick=deleteSchedule("'+id+'") >删除</a>';
						}
					}
		         	 ]
	});
}
/**
 * 头部全选记录
 */
function checkTotal() {
	var isTotalChecked = $("input:checkbox[id='total']").prop("checked");
	var listCheckbox = $("input:checkbox[name='chk']");
	if (isTotalChecked) {
		listCheckbox.prop("checked", function(i, val) {
					if (!$(this).prop("disabled")) {
						return true;
					}
				});
	} else {
		listCheckbox.prop("checked", false);
	}
}
/**
 * 选择记录
 */
function check() {
	var checkTotalNum = $("input:checkbox[name='chk']");
	var checkNum = 0;
	var checkNumAll = true;
	checkTotalNum.each(function(i, val) {
				if ($(checkTotalNum[i]).prop("checked")) {
					checkNum++;
				} else {
					checkNumAll = false;
				}
			});
	if (!checkNumAll) {
		$("#total").prop("checked", false);
	} else {
		$("#total").prop("checked", true);
	}
}
/**
 * 删除人员
 */
function deleteUser() {
	// 获取选择管理员id
	var chkbox = document.getElementsByName("chk");
	var userIds = "";
	var selLen = 0;
	for (var i = 0; i < chkbox.length; i++) {
		if (chkbox[i].checked) {
			userIds += chkbox[i].value + ",";
			selLen++;
		}
	}
	if (selLen == 0) {
		qytx.app.dialog.alert('请选择要删除的人员信息！');
		return;
	}
	var paramData = {
		'userIds' : userIds
	};
	// 删除管理员
	qytx.app.dialog.confirm('确定删除该人员吗？', function() {
			qytx.app.ajax({
							url : basePath + "user/deleteUser.action",
							type : "post",
							dataType : 'text',
							data : paramData,
							success : function(data) {
								if (data == "success") {
									getDataTable(); // 刷新人员信息
								}
							}
						});
			});
}

/**
 * 删除
 * 
 * @param id
 *         
 */
function deleteSchedule(id){
	var paramData = {
			'productionScheduleId' : id
		};
	
	// 删除管理员
	qytx.app.dialog.confirm('确定删除吗？', function() {
		qytx.app.ajax({
					url : basePath + "productionSchedule/deleteproductionSchedule.action",
					type : "post",
					dataType : 'text',
					data : paramData,
					success : function(data) {
						if (data == "success") {
							getDataTable(); // 刷新人员信息
						} 
					}
				});
	});
}


/**
 * 更新
 * @param id
 */
function updateSchedule(id){
	url = basePath+"productionSchedule/updateProductionSchedule.action?productionScheduleId="+id;
	window.location.href=url;
}


/**
 * 上传错误日期
 */
function uploadError(){
	var url  = "logined/productionSchedule/uploadError.jsp";
	qytx.app.dialog.openUrl({
		url	:	basePath + url,
		title: 	"上报错误日期",
		size:	"M",
		customButton:[{name: '确定',
			callback:function() {
				/***/			
				var iframe = this.iframe.contentWindow;
				var date = $(iframe.document).find("#date").val();
				 if(!date){
					 qytx.app.dialog.alert("请选择日期！");
					 return false;
				 }else {
					var dataParam = {
						'date' : date
					};
					qytx.app.ajax({
						type : 'post',
						url : basePath + "productionSchedule/uploadError.action",
						data : dataParam,
						dataType : 'text',
						success : function(data) {
							if (data != 1) {
								qytx.app.dialog.alert("保存失败！", function(){
							        return true;
								});
							} else {		
								qytx.app.dialog.tips("保存成功！");	
							}
						}
					});
					return true;
				}
			},focus: true},{name: '取消'}]
	});
}