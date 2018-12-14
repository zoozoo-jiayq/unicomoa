jQuery(document).ready(function(){
	window.parent.frameResize();
	openSelectTreeUser(function(data){
		$("#groupId").val(data.Id);
		checkPower();
		getDataTable();
	});
	getDataTable();
	
	//提交 
	$("#sure").click(function(){
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
});

/**
 * 获取管理员信息列表
 */
function getDataTable() {
	$("#total").prop("checked", false);
	$('#userTable').dataTable({
		"bProcessing" : true,
		'bServerSide' : true,
		'fnServerParams' : function(aoData) {
			var groupId=$("#groupId").val();
			if(groupId==""){
				groupId=null;
			}
			var search = $.trim($("#searchkey").val());
			if (search == "") {
				search = null;
			}
			aoData.push(
					{
						"name" : "userVo.userName",
						"value" : search
					}, {
						"name" : "userVo.groupId",
						"value" : groupId
					},{
						"name":"userVo.phone",
						"value":search
					},{
						"name":"userVo.vNum",
						"value":search
					},{
						"name":"userVo.projectName",
						"value":"TXZL"
					});
		},
		 
		"sAjaxSource" : basePath + "user/getUserListByGroupId.action",
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
					"sClass" : "chk"
				}, {
					"sTitle" : '序号',
					"mDataProp" : "no",
					"sClass" : "num"
     			}, {
					"sTitle" : '姓名',
					"mDataProp" : "userName",
					"sWidth" : "80",
					"sClass" : "tdCenter"
				}, {
					"sTitle" : '手机号码',
					"mDataProp" : "phone",
					"sWidth" : "100",
					"sClass" : "tdCenter"
				}, {
					"sTitle" : '部门',
					"mDataProp" : "groupName",
					"sWidth" : "180",
					"sClass" : "tdCenter"
				}, {
					"sTitle" : '职务',
					"mDataProp" : "job",
					"sWidth" : "140",
					"sClass" : "tdCenter"
				}, {
					"sTitle" : '称呼',
					"mDataProp" : "title",
					"sWidth" : "80",
					"sClass" : "tdCenter"
				},
				 {
					"sTitle" : 'V网短号',
					"mDataProp" : "vNum",
					"sWidth" : "100",
					"sClass" : "tdCenter"
				},
				{
					"sTitle" : '操作',
					"mDataProp" : null,
					"sWidth" : "220",
					"sClass" : "oper"
				}],
		"oLanguage" : {
			"sUrl" : basePath + "plugins/datatable/cn.txt" // 中文包
		},
		"fnDrawCallback" : function(oSettings) {
			$("#total").prop("checked", false);
			$(".longTxt").each(function(){
   				this.setAttribute('title', $(this).text());
   			});
			// 重置iframe高度
			window.parent.frameResize();
			_userCount =   oSettings.fnRecordsDisplay();
		},
		"fnInitComplete" : function() {
			// 重置iframe高度
			window.parent.frameResize();
		},
		"aoColumnDefs" : [{
			"aTargets" : [0],
			"fnRender" : function(oObj) {
				var hasPower = oObj.aData.hasPower;
				if (!hasPower) {
					return '<input name="chk" disabled="disabled" value="'
							+ oObj.aData.id + '" type="checkbox" />';
				} else {
					return '<input name="chk" value="' + oObj.aData.id
							+ '" type="checkbox" />';
				}
			}
		}, {
			"aTargets" : [8],
			"fnRender" : function(oObj) {
				var hasPower = oObj.aData.hasPower;
				if(hasPower){
					return "修改";
				}
				else{
					return "无权限操作";
				}
			}
		}]
	});
}
/**
 * 判断是否有部门操作权限
 */
function checkPower(){
	var groupId=$("#groupId").val();
	$.ajax({
        url : basePath+"authority/userGroupPower_checkPower.action",
        type : "post",
        dataType :'text',
        data:{ 'groupId':groupId},
        success : function(data) {
            if(data==1)
            {
            	 $('#option').css("background","#fff");
            	
		    } else{
		    	  $('#option').css("background","#e8e8e8");
            }
        }});
	
}
