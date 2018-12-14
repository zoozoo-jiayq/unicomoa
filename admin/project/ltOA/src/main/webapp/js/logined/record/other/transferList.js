$(function(){
	var userId = $("#userId").val();
	$("#btnAddTransfer").click(function(){
		toAddOrUpdateTransfer("");
		return false;
	});
	
	$("#btnRefreshTransfer").click(function(){
		transferList();
		return false;
	});
	transferList();
});

function toAddOrUpdateTransfer(transferId){
	var userId = $("#userId").val();
	var openUrl = basePath+"/logined/recordOther/toAddTransfer.action?transferId="+transferId+"&userId="+userId;
	window.open(openUrl,"","scrollbars=yes,top=100,left=300, width=830, height=600")
}

function transferList(){
	var userId = $("#userId").val();
	qytx.app.grid({
		id	:	"dataTable_record",
		url	:	basePath + "/logined/recordOther/transferList.action",
		iDisplayLength:	15,
		selectParam:{"userId":userId},
		valuesFn:	[{
			"aTargets" : [5],
			"fnRender" : function(oObj) {
				var transferId = oObj.aData.transferId;
				var html = "<a style='cursor:pointer' onClick='viewTransfer("+transferId+")'>查看</a>&nbsp;<a style='cursor:pointer' onClick='updateTransfer("+transferId+")'>修改</a>";
				return html;
			}
		}]
	});
}

function viewTransfer(transferId){
	var openUrl = basePath+"/logined/recordOther/toAddTransfer.action?transferId="+transferId+"&operType=detail";
	window.open(openUrl,"","scrollbars=yes,top=100,left=300, width=830, height=600")
}

function updateTransfer(transferId){
	var openUrl = basePath+"/logined/recordOther/toAddTransfer.action?transferId="+transferId;
	window.open(openUrl,"","scrollbars=yes,top=100,left=300, width=830, height=600")
}

