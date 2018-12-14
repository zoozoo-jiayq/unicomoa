/**
 * 列表
 */
$(document).ready(function() {
		
		viewList();
		//回车事件
		$(document).keydown(function(event){
			var code=event.which;
			if (code == 13) {
				viewList();
				return false;
			}
		});
		$("#searchButton").click(function(){
			viewList();
		});
})

/**
 * 获取未读公告列表
 * 
 * @return
 */
function viewList() {
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
				"value" : 0
			},{
				"name" : "columnId",
				"value" : $.trim($("#columnId").val())
			});
		},
		"sAjaxSource" : basePath + "notify/notify_viewList.action",
		"sServerMethod" : "POST",
		"sPaginationType" : "full_numbers",
		"bPaginate" : true, // 翻页功能
		"bLengthChange" : false, // 改变每页显示数据数量
		"bFilter" : false, // 过滤功能
		"bSort" : false, // 排序功能
		"bInfo" : true,// 页脚信息
		"bAutoWidth" : false,// 自动宽度
		"iDisplayLength" : 20, // 每页显示多少行
		"aoColumns" : [{	
					"mDataProp" : "subject",
					"sClass" : "longTxt"
				},{		
					"mDataProp" : "username"
				},{
	
					"mDataProp" : "approveDate"
				},{
					"mDataProp" : "totalCount",
					"sClass" : 	"right_bdr0 data_r"
				}],
		"oLanguage" : {
			"sUrl" : basePath + "plugins/datatable/cn.txt" // 中文包
		},
		"aoColumnDefs" :[{
			"aTargets" : [0],
			"fnRender" : function(oObj) {
				var isTop = oObj.aData.isTop;
				var subject = oObj.aData.subject;
				var columnId =$("#columnId").val();
				var counting = oObj.aData.counting;
				var fontWeight = "";
				if(counting == 0){
					fontWeight = "font-weight:bold;";
				}
				return '<a title="'+subject+'" style="text-decoration:none;color:#04578D;text-align:left;'+fontWeight+'"  href="'+ basePath+ 'logined/notify/view.jsp?notifyId='+ oObj.aData.notifyId+ '&columnId='+columnId+'&returnType=1">'+ subject+ '</a>';
			}            
		}]
	});
}
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
					for (var i = 0; i < jsonData.length; i++) {
						$("#notifyType").append("<option value='"
								+ jsonData[i].value + "'>" + jsonData[i].name
								+ "</option>");
					}
				}
			});
}
