/**
 * @author REN

 */
$(document).ready(function() {
	// 清除cookie中的分页信息
	$.removeTableCookie('SpryMedia_DataTables_userTable_userList.jsp');
 
	$("#yc").click(function(){
		$(this).hide();
		$("#qb").show();
		$("#mobileViewState").val("1");
		getDataTable();
	});

	$("#qb").click(function(){
		$(this).hide();
		$("#yc").show();
		$("#mobileViewState").val("-1");
		getDataTable();
	});
	
	
	getDataTable();
	$("#searchButton").click(function(){
		getDataTable();
	});
	
	// 头部全选复选框
	$("#userTable").delegate("#total", "click", function(event) {
				checkTotal();
				event.stopPropagation();
			});
	// 第一列复选按钮
	$("#userTable").delegate("input:checkbox[name='chk']", "click",
			function(event) {
				check();
				event.stopPropagation();
			});
	
	
});
/**
 * 获取管理员信息列表
 */
function getDataTable() {
	_checkedIds="";
	$("#total").prop("checked", false);
	$('#userTable').dataTable({
		"bProcessing" : true,
		'bServerSide' : true,
		'fnServerParams' : function(aoData) {
			var userName = $.trim($("#userName").val());
			if (userName == "") {
				userName = null;
			}
			var groupId = $.trim($("#groupId").val());
			if (groupId == "") {
				groupId = null;
			}
			var mobileViewState = $("#mobileViewState").val();
			aoData.push({
						"name" : "userVo.userName",
						"value" : userName
					},{
						"name" : "userVo.groupId",
						"value" : groupId
					}, {
						"name" : "mobileViewState",
						"value" : mobileViewState
					});
		},
		 
		"sAjaxSource" : basePath + "org/mobile/getMobileViewByGroupId.action",// 获取管理员列表
		"sServerMethod" : "POST",
		"sPaginationType" : "full_numbers",
		"bPaginate" : true, // 翻页功能
		"bStateSave" : true, // 状态保存
		"bLengthChange" : false, // 改变每页显示数据数量
		"bFilter" : false, // 过滤功能
		"bSort" : false, // 排序功能
		"bInfo" : true,// 页脚信息
		"bAutoWidth" : false,// 自动宽度
		"bDestroy" : true,
		"iDisplayLength" : 15, // 每页显示多少行
		"aoColumns" : [{
					"sTitle" : "<input type='checkbox' id='total'/>",
					"mDataProp" : null,
					"sWidth" : "25",
					"sClass" : "chk"
				}, {
					"sTitle" : '序号',
					"mDataProp" : "no",
					"sClass" : "num"
//				}, {
//					"sTitle" : '用户名',
//					"mDataProp" : "loginName",
//					"sWidth" : "50%",
//					"sClass" : "tdCenter"
				}, {
					"sTitle" : '姓名',
					"mDataProp" : "userName",
					"sWidth" : "120",
					"sClass" : "tdCenter"
				}, {
					"sTitle" : '性别',
					"mDataProp" : null,
					"sWidth" : "50",
					"sClass" : "tdCenter"
				}, {
					"sTitle" : '手机号码',
					"mDataProp" : "phone",
					"sWidth" : "100",
					"sClass" : "tdCenter"
				}, {
					"sTitle" : '部门',
					"mDataProp" : 'groupName',
					"sWidth" : "60",
					"sClass" : "tdCenter longTxt"
				}, {
					"sTitle" : '职务',
					"mDataProp" : "job",
					"sWidth" : "50%",
					"sClass" : "tdCenter"
				}, {
					"sTitle" : '状态',
					"mDataProp" : null,
					"sWidth" : "50%",
					"sClass" : "tdCenter"
				}, {
					"sTitle" : '操作',
					"mDataProp" : null,
					"sWidth" : "70"
					/*"sClass" : "oper"*/
				}],
		"oLanguage" : {
			"sUrl" : basePath + "plugins/datatable/cn.txt" // 中文包
		},
		"fnDrawCallback" : function(oSettings) {
			$("#totalNum").html(oSettings.fnRecordsDisplay());
			$("#companyAll").html(oSettings.fnRecordsDisplay());
			$("#selectedNum").html(0);
			$("#total").prop("checked", false);
			// 重置iframe高度
			window.parent.frameResize();
			$(".longTxt").each(function(){
   				this.setAttribute('title', $(this).text());
   			});
			_getChecked();
		},
		"fnInitComplete" : function() {
			// 重置iframe高度
			window.parent.frameResize();
		},
		"aoColumnDefs" : [{
			"aTargets" : [0],
			"fnRender" : function(oObj) {
				var isDefault = oObj.aData.isDefault;
				if (isDefault == 0) {
					return '<input name="chk" disabled="disabled" value="'
							+ oObj.aData.id + '" type="checkbox" />';
				} else {
					return '<input name="chk" value="' + oObj.aData.id
							+ '" type="checkbox" />';
				}

			}
		}, {
			"aTargets" : [3],
			"fnRender" : function(oObj) {
				var sex = oObj.aData.sex;
				var sexName = "";
				if (sex == 1) {
					sexName = "男";
				} else {
					sexName = "女";
				}
			 
				return sexName;
			}
		},{
			"aTargets" : [7],
			"fnRender" : function(oObj) {
				var mobileShowState = oObj.aData.mobileShowState;
				if(mobileShowState==1){
					return "信息隐藏";
				}else{
					return "信息公开";
				}
			}
		}, {
			"aTargets" : [8],
			"fnRender" : function(oObj) {
				var id = oObj.aData.id;

				var mobileShowState = oObj.aData.mobileShowState;
				if(mobileShowState==1){
					return '<a href="javascript:void(0);" onclick="mobileShow('+id+')" class="view_url" id="updateUrl">变更</a>';
				}else{
					return '<a href="javascript:void(0);" onclick="mobileHide('+id+')" class="view_url" id="updateUrl">变更</a>';
				} 
			}
		}]
	});
}

/**
 * 隐藏人员
 * @param id
 */
function mobileHide(id){
	art.dialog.confirm('确定隐藏该人员吗？', function() {
		$.post(basePath+"org/mobile/mobileHide.action",{"userIds":id},function(data){
			getDataTable();
		});
	});
}

/**
 * 显示人员
 */
function mobileShow(id){
	art.dialog.confirm('确定显示该人员吗？', function() {
		$.post(basePath+"org/mobile/mobileShow.action",{"userIds":id},function(data){
			getDataTable();
		});
	});
}

/**
 * 批量隐藏
 */
function hideAll(){
	var userIds = _checkedIds;
	if(userIds.length <= 1){
		art.dialog.alert('请选择要隐藏的人员信息！');
		return false;
	}
	userIds = userIds.substring(0,userIds.length-1);
	var paramData={
		"userIds":userIds	
	};
	art.dialog.confirm('确定隐藏已选择的人员吗？', function() {
		$.ajax({
					url : basePath + "org/mobile/mobileHide.action",
					type : "post",
					dataType : 'text',
					data : paramData,
					success : function(data) {
							// 刷新左边的人员树
							window.parent.refreshTree("gid_" + $("#groupId").val());
							getDataTable(); // 刷新人员信息
					}
				});
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



document.onkeydown = function(){
	if(event.keyCode == 13){
		$("#searchButton").click();
	}
}
