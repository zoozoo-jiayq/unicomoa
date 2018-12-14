$(document).ready(function(){
	if(!setNotifyType()){
		list();
	}
	//回车事件
	$(document).keydown(function(event){
		var code=event.which;
		if (code == 13) {
			list();
			return false;
		}
	}); 
	
	$("input[type='button']").click(function(){
		list();
	});
	
	$("#totalCheck").live("click",function(){
		if($(this).attr("checked")=="checked"){
			$("input[name='ids']").attr("checked","checked");
		}else{
			$("input[name='ids']").removeAttr("checked");
		}
	});
})


/**
 * 初始公告类型
 * 
 * @return
 */
function setNotifyType() {
	var paramData = {
		'infoType' : "notifyType"+$("#columnId").val(),
		'sysTag' : 1
	};
	$.ajax({
		url : basePath + "dict/getDicts.action",
		type : "post",
		dataType : "html",
		data : paramData,
		success : function(data) {
			jsonData = eval("(" + data + ")");
			$("#notifyType").empty();
			$("#notifyType").append("<option value=''>全部类型</option>");
			for (var i = 0; i < jsonData.length; i++) {
				$("#notifyType").append("<option value='"
						+ jsonData[i].value + "'>" + jsonData[i].name
						+ "</option>");
			}
		}
	});
}
/**
 * 获取公告列表
 * 
 * @return
 */
function list() {
	$('#myTable').dataTable({
		"bProcessing" : true,
		'bServerSide' : true,
		"bStateSave" : false, // 状态保存
		"bDestroy" : true,
		'fnServerParams' : function(aoData) {
			aoData.push({
				"name" : "subject",
				"value" : $.trim($("#subject").val())
			},{
				"name" : "notifyType",
				"value" : $.trim($("#notifyType").val())
			},{
				"name" : "columnId",
				"value" : $.trim($("#columnId").val())
			},{
				"name" : "beginDate",
				"value" : $.trim($("#beginDate").val())
			},{
				"name" : "endDate",
				"value" : $.trim($("#endDate").val())
			});
		},
		"sAjaxSource" : basePath + "notify/notify_list.action",
		"sServerMethod" : "POST",
		"sPaginationType" : "full_numbers",
		"bPaginate" : true, // 翻页功能
		"bLengthChange" : false, // 改变每页显示数据数量
		"bFilter" : false, // 过滤功能
		"bSort" : false, // 排序功能
		"bInfo" : true,// 页脚信息
		"bAutoWidth" : false,// 自动宽度
		"iDisplayLength" : 15, // 每页显示多少行
		"aoColumns" : [{
					"sTitle" : '<input type="checkbox" id="totalCheck"/>',
					"mDataProp" : null,
					"sWidth" : "15"
				},{
					"mDataProp" : "subject",
					"sClass" : "longTxt"
				},{					
					"mDataProp" : "notifyType"
				},{					
					"mDataProp" : "username"					
				},{					
					"mDataProp" : "createDate"					
				},{					
					"mDataProp" : "browse",
					"sClass":"data_r"
				},{					
					"mDataProp" : null
				},{					
					"mDataProp" : null
					
				}],
		"oLanguage" : {
			"sUrl" : basePath + "plugins/datatable/cn.txt" // 中文包
		},
		"aoColumnDefs" :[{
			"aTargets" : [0],
			"fnRender" : function(oObj) {
				var id = oObj.aData.id;
				return "<input type='checkbox' name='ids' value='"+id+"'/>";
			}          
		},{
			"aTargets" : [1],
			"fnRender" : function(oObj) {
				var id = oObj.aData.id;
				var columnId = $("#columnId").val();
				var subject = oObj.aData.subject;
				var isTop = oObj.aData.isTop;
				var topTemplate = "";
				if(isTop==1){
					topTemplate = '<font color="red"><b>[置顶]&nbsp;&nbsp;</b></font>';
				}
				return topTemplate+'<a  title="'+subject+'" style="cursor:pointer"color:#04578D;text-align:left;" href="'+ basePath + 'logined/notify/view.jsp?notifyId='+ id + '&columnId='+columnId+'&returnType=2">' + subject + '</a>'
			}          
		},{
			"aTargets" : [6],
			"fnRender" : function(oObj) {
				var status = oObj.aData.status;
				if(status==0){
					return "草稿";
				}else if(status==1){
					return "审核中";
				}else if(status==2){
					return "已发布";
				}else if(status==3){
					return "不通过";
				}else if(status==4){
					return "已终止";
				}
			}            
		},{
			"aTargets" : [7],
			"fnRender" : function(oObj) {
				var status = oObj.aData.status;
				var id = oObj.aData.id;
					return '<a style="cursor:pointer" title="查看"  href="'+ basePath + 'logined/notify/view.jsp?notifyId='+ id+ '&columnId='+$("#columnId").val()+'&returnType=10">查看</a>';
			}
		}]
	});
}

/**
 * 添加
 */
function addNotify() {
	window.location.href = basePath + 'logined/notify/viewAdd.jsp?id='+$("#columnId").val();
}


/**
 * 置顶
 * @param isTop
 */
function topSet(isTop){
	var ids ="";
	$("input[name='ids']").each(function(){
		var $obj = $(this);
		if($obj.attr("checked")){
			if(ids==""){
				ids += $obj.val();
			}else{
				ids += ","+$obj.val();
			}
		}
	});
	if(ids==""){
		art.dialog.alert('要置顶公告,请至少选择其中的一项！');
		return ;
	}else{
		art.dialog.confirm("确定要将所选项置顶吗？",function(){
		$.ajax({
			type:"post",
			url:basePath+"notify/notify_topSet.action",
			data:{"ids":ids,"isTop":isTop},
			success:function(result){
				if(result){
					list();
				}
			}
		});
	  });
    }
}

/**
 * 查阅情况
 * @param id
 * @return
 */
function rediectCheckPerson(id){
		var columnId = $("#columnId").val();
		var url = basePath + "logined/notify/viewNotify.jsp?id=" + id+"&columnId="+columnId+"&category="+1;
		window.location.href=url;
	}
/**
 * 撤销
 * @param id
 */
function draw(id){
	art.dialog.confirm("确定要撤销该选项吗？",function(){
		$.ajax({
			type:"post",
			url:basePath+"notify/notify_draw.action",
			data:{"id":id},
			success:function(result){
				if(result){
					list();
				}
			}
		});
	});
}
/**
 * 终止
 * @param id
 */
function stop(id){
	art.dialog.confirm("确定要终止该选项吗？",function(){
		$.ajax({
			type:"post",
			url:basePath+"notify/notify_stop.action",
			data:{"id":id},
			success:function(result){
				if(result){
					list();
				}
			}
		});
	});
}

function modifyStatus(notifyId,begDate,endDate){
	dataParam = {
		'id' : notifyId,
		'startDateStr' : begDate,
		'endDateStr' : endDate
	};
	$.ajax({
		type : 'post',
		url : basePath + "notify/notify_effect.action",
		data : dataParam,
		dataType : 'text',
		success : function(data) {
			if(data){
				list();
			}
		}
	});
}