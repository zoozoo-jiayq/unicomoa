/**
 * @author REN
 */
$(document).ready(function() {
	// 清除cookie中的分页信息
	$.removeTableCookie('SpryMedia_DataTables_myTable_roleList.jsp');
	_checkedIds="";
	// 获取权限信息列表
	getDataTable();
	// 打开新增页面
	$("#addData").click(function() {
	        	window.location.href=basePath+"logined/authority/userGroupPowerAdd.jsp";
			});
	// 全选
	$("#selectAll").click(function() {
				checkAll();
				return false;
			});
	// 反选
	$("#unselectAll").click(function() {
				reverseCheck();
				return false;
			});
	// 头部全选复选框
	$("#myTable").delegate("#total", "click", function(event) {
				checkTotal();
				event.stopPropagation();
			});
	// 第一列复选按钮
	$("#myTable").delegate("input:checkbox[name='chk']", "click",
			function(event) {
				check();
				event.stopPropagation();
			});
	// 单击删除
	$("#deleteData").click(function() {
		        deleteUserGroup();
				return false;
			});
	
	$("#search").click(function() {
		getDataTable();
	});
	//回车事件
	$(document).keydown(function(event){
		var code=event.which;
		if (code == 13) {
			getDataTable();
			return false;
		}
	}); 
})
/**
 * 获取角色信息列表
 */
function getDataTable() {
	$("#total").prop("checked", false);
	$('#myTable').dataTable({
		"bDestroy" : true,
		"bProcessing" : true,
		'bServerSide' : true,
		'fnServerParams' : function(aoData) {
			aoData.push({
				"name" : "searchkey",
				"value" : $.trim($("#searchkey").val())
			});
		},
		"sAjaxSource" : basePath + "authority/userGroupPower_findUserList.action",// 获取角色列表
		"sServerMethod" : "POST",
		"sPaginationType" : "full_numbers",
		"bPaginate" : true, // 翻页功能
		"bStateSave" : true, // 状态保存
		"bLengthChange" : false, // 改变每页显示数据数量
		"bFilter" : false, // 过滤功能
		"bSort" : false, // 排序功能
		"bInfo" : true,// 页脚信息
		"bAutoWidth" : false,// 自动宽度
		"iDisplayLength" : tableDisplayLength, // 每页显示多少行
		"aoColumns" : [{
					"sTitle" : "<input class='chk' type='checkbox' id='total'/>",
					"mDataProp" : null,
					"sClass" : "chk"
				}, {
					"sTitle" : '序号',
					"mDataProp" : "no",
					"sClass" : "num"
				}, {
					"sTitle" : '姓名',
					"mDataProp" : "userName",
					"sWidth" : "100",
					"sClass" : "tdCenter"
				}, {
					"sTitle" : '手机号',
					"mDataProp" : "phone",
					"sWidth" : "20%",
					"sClass" : "tdCenter"
				}, 
				{
					"sTitle" : '单位/部门',
					"mDataProp" : "groupName",
					"sWidth" : "40%",
					"sClass" : "longTxt"
				}, 
				{
					"sTitle" : '授权范围',
					"mDataProp" : "groupPowerName",
					"sWidth" : "40%",
					"sClass" : "longTxt"
				}, 
				{
					"sTitle" : '操作',
					"mDataProp" : null,
					"sWidth" : "70",
					"sClass" : "right_bdr0"
				}],
		"oLanguage" : {
			"sUrl" : basePath + "plugins/datatable/cn.txt" // 中文包
		},
		"fnDrawCallback" : function(oSettings) {
			$('#myTable tbody  tr td[class="longTxt"]').each(function() {
				this.setAttribute('title', $(this).text());
			});
			_getChecked();
		},
		"aoColumnDefs" : [{
			"aTargets" : [0],// 覆盖第一列
			"fnRender" : function(oObj) {
					return '<input name="chk" value="' + oObj.aData.id+ '" type="checkbox"/>';
			}
		}, {
			"aTargets" : [6],
			"fnRender" : function(oObj) {
				return '<a href="javascript:void(0)" onclick="updatePower(\'' +oObj.aData.id + '\',\''+oObj.aData.groupPower+'\',\''+oObj.aData.userId+'\')" id="updateUrl">授权范围</a>'
			}
		}]
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
 * 全选
 * 
 * @param name
 */
function checkAll() {
	var checkNum = 0;
	$("input:checkbox[name='chk']").prop("checked", function(i, val) {
				checkNum = checkNum + 1;
				return true;
			});
	$("input:checkbox[name='total']").prop("checked", true);
	$("#selectedNum").html(checkNum);
}
/**
 * 反选
 */
function reverseCheck() {
	var checkNum = 0;
	$("input:checkbox[name='chk']").prop("checked", function(i, val) {
				if (!val) {
					checkNum = checkNum + 1;
				}
				return !val;
			});
	$("#selectedNum").html(checkNum);
}
/**
 * 更新授权范围
 */
function updatePower(id,groupPower,userId){
	if(id=="undefined"){id="";}
	openSelectUser(1,function(data){
		$("#groupIds").val("");
		data.forEach(function(value, key) {
           if(value.Type == "group"){
               var temp = $("#groupIds").val();
               if(temp==""){
            	   temp=","+value.Id+",";
               }else{
            	   temp+=value.Id+",";
               }
               $("#groupIds").val(temp);
           }
        });
		   var groupIds=$("#groupIds").val();
	       /*	if(groupIds.length==0){
	       		art.dialog.alert('授权范围不能为空！');
	       		return false;
	       	}*/
	       	$.ajax({
	       		url : basePath + "authority/userGroupPower_updateById.action",
	       		type : "post",
	       		dataType : 'text',
	       		data:{"userGroup.id":id,"userGroup.groupPower":groupIds,"userGroup.user.userId":userId},
	       		success : function(data) {
	       			if (data != "") {
	       				getDataTable();
	       			} else {
	       				art.dialog.alert("编辑失败！");
	       			}
	       		 }
	        	});
	},null,groupPower);

	
}
/**
 * 删除
 */
function deleteUserGroup() {
	var chkbox = document.getElementsByName("chk");
	var ids = "";
	var selLen = 0;
	var arr;
	if(!_checkedIds){
		art.dialog.alert('请选择要删除的授权！');
		return;
	}
	art.dialog.confirm('确定删除该授权吗？', function() {
				$.ajax({
							url : basePath + "authority/userGroupPower_removeByIds.action",
							type : "post",
							dataType : 'text',
							data :  {"ids":_checkedIds},
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
			})
}