$(function(){
	getGroupTree()
	getTable();
	findCount();
	$("#serch").click(function(){
		getTable();
		findCount();
	})
})

var treeGroupId='';

/**
 * 获取群组的树结构
 */
function getGroupTree() {
	// 设置树样式
	// 开始隐藏
	$("#menuContent").hide();
	// 组织机构树参数设置
	var setting = {
		view : {
			dblClickExpand : false
		},
		data : {
			simpleData : {
				enable : true
			}
		},
		callback : {
			onClick : onTreeNodeClick
		}
	};
	var dataParam = {
		'type' : 1,
		'showType' : 1
	};
	$.ajax({
				url : basePath + "user/selectUser.action",
				type : 'post',
				dataType : 'json',
				data : dataParam,
				success : function(data) {
					$.fn.zTree.init($("#groupTree"), setting, data);
					// 选择部门
					getGroup();
				}
			});

}

/**
 * 得到部门
 */
function getGroup() {
	var parentId = $("#parentId").val();
	var zTree = $.fn.zTree.getZTreeObj("groupTree");
	if(zTree){
		var nodes = zTree.transformToArray(zTree.getNodes());
		for (var i = 0; i < nodes.length; i++) {
			var gName = nodes[i].name;
			var gId = nodes[i].id;
			if (gId == "gid_" + parentId) {
				zTree.selectNode(nodes[i]);
			}
		};
	}
	
}
/**
 * 群组树调用点击事件
 */
function onTreeNodeClick(e, treeId, treeNode) {
	var zTree = $.fn.zTree.getZTreeObj("groupTree");
	var nodes = zTree.getSelectedNodes();
	if (nodes.length > 0) {
		var groupName = nodes[nodes.length - 1].name;
		var groupId = nodes[nodes.length - 1].id;
		treeGroupId=groupId.substring(4, groupId.length);
		$("#groupText").html(groupName);
		$("#groupTree").hide();
	}
	
}



function toggleSelectGroup(){
	$("#groupTree").show();
}








/**
 * 获得统计数
 */
function findCount(){
	var param={
		"state":$("#state").val(),
		"groupId":treeGroupId
	}
	$.ajax({
		url:basePath+"jbpmflow/step_findMapNum.action",
		type:'post',
		dataType:'json',
		data:param,
		success:function(result){
			$("#jobNum").html(result.zaizhiNum+" 人");
			$("#inPosition").html(result.daogangNum+" 人");
			$("#attendancePer").html(result.per+"%");
		}	
	})
}




function getTable(){
	var state =$("#state").val();
	$('#attenceTable').dataTable({
		"bDestroy" : true,
		"bProcessing" : true,
		'bServerSide' : true,
		'fnServerParams' : function(aoData) {
			aoData.push(
		            { "name":"groupId", "value":treeGroupId },
		            { "name":"state", "value":state }
		            );
		},
		"sAjaxSource" : basePath  + "jbpmflow/step_leaveList.action",//sAjaxSource属性是指定table数据来源
		"sServerMethod" : "post",
		"sPaginationType" : "full_numbers",
		"bPaginate" : true, // 翻页功能
		"bLengthChange" : false, // 改变每页显示数据数量
		"bFilter" : false, // 过滤功能
		"bSort" : false, // 排序功能
		"bInfo" : true,// 页脚信息
		"bAutoWidth" : false,// 自动宽度
		"iDisplayLength" :15, // 每页显示多少行
		"aoColumns" : [
	               {     						    //aoColumns属性是table的列的集合，和jsp页面中对应
	            	   "mDataProp" : "no"			//指定数据中显示的数据字段名
	               }, {
	            	   "mDataProp" : "userName",
	            	   "sClass":"longTxt"//
	               }, {
	            	   "mDataProp" : "groupName" ,
	            	   "sClass":"longTxt"//
	               },{
	            	   "mDataProp" : "stateStr",//
	            	   "sClass":"longTxt"
	               }],
	               "oLanguage" : {
	            	   "sUrl" : basePath + "plugins/datatable/cn.txt" // 中文包
	               },
	               "fnDrawCallback":function(oSettings){   //这个函数是呼吁每一个“画”事件,并允许您动态地修改任何方面你想要创建的DOM
	       			$(".longTxt").each(function(){
	       				this.setAttribute('title', $(this).text());
	       			});
	       			},
	               "aoColumnDefs" : [ 
					
					]
	  });
}