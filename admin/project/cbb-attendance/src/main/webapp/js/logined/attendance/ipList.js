/**IP设置*/
$(function(){
	//getIPList();
	//为新增Ip段添加事件
	$(".add").click(function(){
		addIp();
		return false;
	});
});

function getIPList(){
	$('#ipTable').dataTable({
		"bDestroy" : true,
		"bProcessing" : true,
		'bServerSide' : true,
		'fnServerParams' : function(aoData) {
			aoData.push();
		},
		"sAjaxSource" : basePath + "attendance/getIPList.action",// 获取IP列表
		"sServerMethod" : "POST",
		"sPaginationType" : "full_numbers",
		"bPaginate" : true, // 翻页功能
		"bStateSave" : true, // 状态保存
		"bLengthChange" : false, // 改变每页显示数据数量
		"bFilter" : false, // 过滤功能
		"bSort" : false, // 排序功能
		"bInfo" : true,// 页脚信息
		"bAutoWidth" : false,// 自动宽度
		"iDisplayLength" : 15, // 每页显示多少行
		"aoColumns" : [{
					"sTitle" : '序号',
					"mDataProp" : "no",
					"sClass" : "tdCenter"
				}, {
					"sTitle" : '起始IP地址',
					"mDataProp" : "beginIp",
					"sClass" : "tdCenter"
				}, {
					"sTitle" : '截止IP地址',
					"mDataProp" : "endIp",
					"sClass" : "tdCenter"
				}, {
					"sTitle" : '操作人',
					"mDataProp" : "creater",
					"sClass" : "tdCenter"
				}, {
					"sTitle" : '操作时间',
					"mDataProp" : "createTime",
					"sClass" : "tdCenter"
				}, {
					"sTitle" : '操作',
					"mDataProp" : null,
					"sClass" : "right_bdr0 tdCenter"
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
		"aoColumnDefs" : [ {
			"aTargets" : [5],// 覆盖第6列
			"fnRender" : function(oObj) {
				var ipId = oObj.aData.id;
				var updateInfo = '<a onclick="updateIP('+ipId+')" style="cursor:pointer;">编辑</a>';
				var deleteInfo = '<a onclick="deleteIP('+ipId+')" style="cursor:pointer;">删除</a>';
				return updateInfo+deleteInfo;
			}
		}]
	});
}


function addIp(){
		var url = basePath+"logined/attendance/addIp.jsp";
		art.dialog.open(url, {
		    id : "addIp",
		    title : "新增IP段",
		    width : 600,
		    height : 300,
		    lock : true,
		    drag:true,
		    opacity: 0.08,// 透明度
		    close : function(){
		    	window.location.href=basePath+"attendance/getIPList.action";
		    	return true;
		    },
		    ok : function(){
		    	var iframe = this.iframe.contentWindow;
		    	iframe.a.click();
		    	return false;
		    },
		    cancel : function(){
		    	return true;
		    }
		});
}

function updateIp(ipSetId){
	var url = basePath+"logined/attendance/updateIp.jsp?ipSetId="+ipSetId;
	art.dialog.open(url, {
	    id : "updateIp",
	    title : "修改IP段",
	    width : 600,
	    height : 300,
	    drag:false,
	    lock : true,
	    opacity: 0.08,// 透明度
	    close : function(){
	    	window.location.href=basePath+"attendance/getIPList.action";
	    	return true;
	    },
	    ok : function(){
	    	var iframe = this.iframe.contentWindow;
	    	iframe.a.click();
	    	return false;
	    },
	    cancel : function(){
	    	return true;
	    }
	});
}

function deleteIp(ipSetId){
	qytx.app.dialog.confirm('确认要删除吗？', function(){
		var urlStr = basePath+"attendance/deleteIp.action";
		var paramData = {"ipSetId":ipSetId};
		qytx.app.ajax({
			url:urlStr,
			type:"post",
			data:paramData,
			dataType:"html",
			success:function(data){
				if(data==""){
					window.location.href=basePath+"attendance/getIPList.action";
				}else{
					art.dialog.alert("操作失败");
				}
			}
		});
	}, function(){
		art.dialog.tips('你取消了操作');
	});
}