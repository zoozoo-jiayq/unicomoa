$(function(){
	getTable();
})


function getTable(){
	var userId=$("#userId").val();
	$('#dataTable_work').dataTable({
		"bProcessing" : true,
		'bServerSide' : true,
		'fnServerParams' : function(aoData) {
			aoData.push({
						"name" : "recordWork.userInfo.userId",
						"value" : userId
					}
				);
		},
		"sAjaxSource" : basePath+"/logined/recordOther/findlistByPage.action",// 
		"sServerMethod" : "POST",
		"sPaginationType" : "full_numbers",
		"bPaginate" : true, // 翻页功能
		"bLengthChange" : false, // 改变每页显示数据数量
		"bFilter" : false, // 过滤功能
		"bSort" : false, // 排序功能
		"bInfo" : true,// 页脚信息
		"bAutoWidth" : false,// 自动宽度
		"bDestroy" : true,
		"iDisplayLength" : 15, // 每页显示多少行
		"aoColumns" : [
			{
					"mDataProp" : "no",
				},{
					"mDataProp" : "name",
				}, {
					"mDataProp" : "workUnit",
				    "sClass" : "longTxt"
				}, {
					"mDataProp" : "industryCategory",
					"sClass" : "longTxt"
				}, {
					"mDataProp" :"position",
					"sClass" : "longTxt"
				}, {
					"mDataProp" : "reterence"
				},{
					"mDataProp" : null
				}],
		"oLanguage" : {
			"sUrl" : basePath + "plugins/datatable/cn.txt" // 中文包
		},
		"fnDrawCallback" : function(oSettings) {
			$('#dataTable_work tbody  tr td').each(function() {
				this.setAttribute('title', $(this).text());
			});
              
		},
		"fnInitComplete" : function() {
			// 重置iframe高度
		
		},
		"aoColumnDefs" : [{
			"aTargets" : [6],
			"fnRender" : function(oObj) {
				var vid = oObj.aData.id;
				var html='<a href="javascript:void(0);" onclick="findDetail('+vid+')">查看</a>';
				html +='<a href="javascript:void(0);" onclick="editorWorkDeatil('+vid+')">修改</a>';
				return html;
			}
		}]
	});
}	

/**
 * 查看详情
 */
function findDetail(id){
	var openUrl = basePath+"/logined/recordOther/findDetail.action?recordWorkId="+id+"&type=1";
	window.open(openUrl,"","scrollbars=yes,top=100,left=400, width=830, height=600")
}

function editorWorkDeatil(id){
	var userId=$("#userId").val();
	var openUrl;
	if(id){
		openUrl = basePath+"/logined/recordOther/findDetail.action?recordWorkId="+id+"&type=2";
	}else{
		openUrl = basePath+"/logined/recordOther/toAddView.action?userId="+userId;
	}
	window.open(openUrl,"","scrollbars=yes,top=100,left=400, width=830, height=600")
}

