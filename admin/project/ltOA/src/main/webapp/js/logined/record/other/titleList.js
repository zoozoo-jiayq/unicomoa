$(function(){
	var userId = $("#userId").val();
	$("#btnAddTitle").click(function(){
		toAddOrUpdateTitle("");
		return false;
	});
	
	$("#btnRefreshTitle").click(function(){
		titleList();
		return false;
	});
	titleList();
});

function toAddOrUpdateTitle(titleId){
	var userId = $("#userId").val();
	var openUrl = basePath+"/logined/recordOther/toAddTitle.action?titleId="+titleId+"&userId="+userId;
	window.open(openUrl,"","scrollbars=yes,top=100,left=300, width=830, height=600")
}

function titleList(){
	var userId = $("#userId").val();
	qytx.app.grid({
		id	:	"dataTable_record",
		url	:	basePath + "/logined/recordOther/titleList.action",
		iDisplayLength:	15,
		selectParam:{"userId":userId},
		valuesFn:	[{
			"aTargets" : [3],
			"fnRender" : function(oObj) {
				var title = oObj.aData.title;
				var html = "<span title='"+title+"' class='longTxt'>"+title+"</span>";
				return html;
			}
		},{
			"aTargets" : [6],
			"fnRender" : function(oObj) {
				var titleId = oObj.aData.titleId;
				var html = "<a style='cursor:pointer' onClick='viewTitle("+titleId+")'>查看</a>&nbsp;<a style='cursor:pointer' onClick='updateTitle("+titleId+")'>修改</a>";
				return html;
			}
		}]
	});
}

function viewTitle(titleId){
	var openUrl = basePath+"/logined/recordOther/toAddTitle.action?titleId="+titleId+"&operType=detail";
	window.open(openUrl,"","scrollbars=yes,top=100,left=300, width=830, height=600")
}

function updateTitle(titleId){
	var openUrl = basePath+"/logined/recordOther/toAddTitle.action?titleId="+titleId;
	window.open(openUrl,"","scrollbars=yes,top=100,left=300, width=830, height=600")
}

