$(document).ready(function(){
	list();
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
	$("#myTable").delegate("input:checkbox[name='ids']", "click",
			function(event) {
			   var checked =$("[name='ids'][checked]").length;
			   var totalChecked=$("[name='ids']").length;
			   if(checked==totalChecked){
				   $("#totalCheck").attr("checked",true);
			   }else{
				   $("#totalCheck").attr("checked",false);
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
				"name" : "status",
				"value" : $.trim($("#status").val())
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
					"mDataProp" : "createDate"					
				},{					
					"mDataProp" : "browse"
				},{					
					"mDataProp" : null
				},{					
					"mDataProp" : null,
					"sClass" : "right_bdr0 data_l"
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
				return topTemplate+'<a  title="'+subject+'" style="cursor:pointer"color:#04578D;text-align:left;" href="'+ basePath + 'logined/notify/view.jsp?notifyId='+ id + '&columnId='+columnId+'&returnType=2">' + subject + '</a>'
			}          
		},{
			"aTargets" : [4],
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
			"aTargets" : [5],
			"fnRender" : function(oObj) {
				var status = oObj.aData.status;
				var id = oObj.aData.id;
				if(status==0 ||status==3){
					return '<a style="cursor:pointer" title="修改"  href="'+ basePath + 'logined/notify/viewUpdate.jsp?id='+ id+ '&columnId='+$("#columnId").val()+'">修改</a>'+'<a style="cursor:pointer" title="删除" onclick="del('+id+')">删除</a>';
				}else if(status==1){
					return '<a style="cursor:pointer" title="查看"  href="'+ basePath + 'logined/notify/view.jsp?notifyId='+ id+ '&columnId='+$("#columnId").val()+'&returnType=2">查看</a>'+'<a style="cursor:pointer"color:#04578D" title="撤销" onclick="draw('+id+')">撤销</a>'+'<a style="cursor:pointer"color:#04578D" title="删除" onclick="del('+id+')">删除</a>';
				}else if(status==2){
					return '<a style="cursor:pointer"color:#04578D;text-align:left;" title="查阅状态" onclick=rediectCheckPerson('+id+')>查阅状态</a><a style="cursor:pointer"color:#04578D" title="删除" onclick="del('+id+')">删除</a>';
				}else if(status==4){
					var beginDate = oObj.aData.beginDate;
					var endDate = oObj.aData.endDate;
					return '<a style="cursor:pointer" title="生效" onclick="effect('+id+',\''+beginDate+'\',\''+endDate+'\')">生效</a><a style="cursor:pointer"color:#04578D" title="删除" onclick="del('+id+')">删除</a><a style="cursor:pointer"color:#04578D;text-align:left;" title="查阅状态" onclick=rediectCheckPerson('+id+')>查阅状态</a>';
				}
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
 * 单个删除
 * @param id
 */
function del(id){
	art.dialog.confirm("确定要删除吗？",function(){
		$.ajax({
			type:"post",
			url:basePath+"notify/notify_del.action",
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
 * 删除
 */
function deleteAll(){
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
		art.dialog.alert('请至少选择一项！');
		return ;
	}else{
		art.dialog.confirm("确定要删除吗？",function(){
			$.ajax({
				type:"post",
				url:basePath+"notify/notify_delByIds.action",
				data:{"ids":ids},
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
 * 取消置顶
 * @param isTop
 * @return
 */
function canceltopSet(isTop){
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
		art.dialog.alert('要取消置顶公告,请至少选择其中的一项！');
		return ;
	}else{
		art.dialog.confirm("确定要取消置顶吗？",function(){
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
/**
 * 生效
 * @param id
 */
function effect(id,beginDate,endDate){
	if(beginDate !=""){							
		beginDate=beginDate.substring(0,10);
	}
	if(endDate!=""){
		endDate=endDate.substring(0,10);
	}
	var html = "<table border=\"0\" style=\"font-size:12px\"><tr><th><label>有效期：</label><input type=\"hidden\" id=\"notifyId\" value=\""+id+"\"/></th>";
	html+="<td><input id=\"begDate\" class=\"Wdate formText\" value=\""+beginDate+"\" onclick=\"WdatePicker({maxDate: '#F{$dp.$D(\\'endDate\\')}',skin:\'default\',dateFmt:\'yyyy-MM-dd\',minDate:'%y-%M-%d %H:%m'})\" name=\"input\"";
	html+=" size=\"20\" type=\"text\" style=\"width:90px;\"> - <INPUT id=\"endDate\" value=\""+endDate+"\" class=\"Wdate formText\" onclick=\"WdatePicker({skin:\'default\',dateFmt:\'yyyy-MM-dd\',minDate:'#F{$dp.$D(\\'begDate\\') || \\'%y-%M-%d %H:%m\\'}'})\" name=\"input\"size=\"20\" type=\"text\" style=\"width:90px;\"> </td></tr></table>";
	art.dialog({
		id : 'modifyStatus',
		title : '生效设置',
		width : 350,
		height : 150,
		lock : true,
		content : html,
	    opacity: 0.08,
		init : function() {
		},
		ok : function() {
			var notifyId = $("#notifyId").val();
			var begDate = $("#begDate").val();
			var endDate = $("#endDate").val();
			if(begDate==""){  
				art.dialog.alert("开始时间不能为空！");
				 return false;
			}else{
				begDate = begDate+" 00:00:00";
			}
			if(endDate!=""){  
				var endDateStr = endDate.split(" ");
				/*if(endDateStr[0] == ""){
					art.dialog.alert("结束时间不能为空！");
				}*/
				if(endDate != ""&&(begDate)>(endDate+" 23:59:59")){
					art.dialog.alert("结束时间不能早于开始时间！");
					return false;
				}else{endDate=endDate+" 23:59:59"}
			}
				modifyStatus(notifyId,begDate,endDate) ;
		},
		okVal : '确定',
		cancelVal : '取消',
		cancel : true
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