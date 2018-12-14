/**
 * @author wuzhou
 */
$(document).ready(function() {
	// 获取列表
	getDataTable();
	//添加活动
	$("#add").click(function(){
		window.location.href = basePath + "logined/jpush/pushAdd.jsp";
		return false;
	});
	//点击查询
	$("#search").click(function(){
		_checkedIds="";
		getDataTable();
		return false;
	});
	//头部全选复选框
	$("#myTable").delegate("#total", "click", function(event){
	   	checkTotal();
		event.stopPropagation();
    });
	//单击删除
	$("#delete").click(function(){
		deleteUser();
		return false;
	});
});
//回车事件
document.onkeydown = function (e) {
    // 兼容FF和IE和Opera
    var theEvent = e || window.event;
    var code = theEvent.keyCode || theEvent.which || theEvent.charCode;

    if (code == 13) {
    	_checkedIds="";
    	getDataTable();
    }
    return true;
};
/**
 * 获取查询列表
 */
function getDataTable() {
	var startDate = "";
	var endDate = "";
	var searchName = $.trim($("#searchName").val());
	if($.trim($("#startDate").val())!=""){
		startDate = $("#startDate").val() + " 00:00:00";
	}
	if($.trim($("#endDate").val())!=""){
		endDate = $("#endDate").val() + " 23:59:59";
	}
	$('#myTable').dataTable({
		"bDestroy" : true,
		"bProcessing" : true,
		'bServerSide' : true,
		'fnServerParams' : function(aoData) {
            aoData.push( { "name":"subject", "value":searchName },
            			 { "name":"startTime", "value":startDate },
            			 { "name":"endTime", "value":endDate }
            );
		},
		"sAjaxSource" : basePath + "push/findPushList.action",
		"sServerMethod": "POST",
        "sPaginationType": "full_numbers",
        "bPaginate": true, //翻页功能
        "bLengthChange": false, //改变每页显示数据数量
        "bFilter": false, //过滤功能
        "bSort": false, //排序功能
        "bInfo": true,//页脚信息
        "bAutoWidth": false,//自动宽度
        "bDestroy":true,
        "iDisplayLength":15, //每页显示多少行
		"aoColumns" : [{
					"mDataProp" : null,
					"sClass" : "chk"
				},{
					"mDataProp" : "num"
				}, {
					"mDataProp" : "subject",
					"sClass" : "longTxt"
				}, {
					"mDataProp" : "content",
					"sClass" : "longTxt"
				}, {
					"mDataProp" : "name"
				}, {
					"mDataProp" : "pushTime"
				}, {
					"mDataProp" : null,
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
            "aTargets": [0],
            "fnRender": function ( oObj ) {
            	return '<input name="chk" value="' + oObj.aData.id + '" type="checkbox" />'; 
            }
        },{
		    "aTargets": [6],
		    "fnRender": function ( oObj ) {
		    var id = oObj.aData.id;
           // var html="<a href='"+basePath+"/push/viewPush.action?pushId="+id+"&type=0' >查看</a><a href='"+basePath+"/push/viewPush.action?pushId="+id+"&type=1' >编辑</a>";
		    var html="<a href='"+basePath+"/push/viewPush.action?pushId="+id+"&type=0' >查看</a>";
		    return html; 
		    }
		}]
	});
}
/**
 * 头部全选记录
 */
function checkTotal() {
	var isTotalChecked=$("input:checkbox[id='total']").prop("checked");
	var listCheckbox=$("input:checkbox[name='chk']");
	if(isTotalChecked){
		listCheckbox.prop("checked", function( i, val ) {
			if (!$(this).prop("disabled")) {
				return true;
			}
        });
	}else{
		listCheckbox.prop("checked", false);
	}
}
/**
 * 删除角色
 */
function deleteUser() {
   //获取选择推荐id
	var chkbox = document.getElementsByName("chk");
	var ids = _checkedIds;
    var selLen = _checkedIds.length;
    if (selLen <=1) {
    	art.dialog.alert('请选择要删除的推送！');
    	return;
    }
	 
	//删除推送
	art.dialog.confirm('确定删除该推送吗？', function () {
		var paramData = {'ids':ids}
		$.ajax({
			url : basePath+"push/deletePush.action",
			type : "post",
			dataType :'text',
			data: paramData,
			success : function(data) {
				if(data > 0) {    
					art.dialog.alert('删除推送成功！',function(){
						_checkedIds="";
						getDataTable();
					});
				} else {
					art.dialog.alert('删除推送失败！');
				}
			}
		});
	}, function () {
	    return;
	});
}