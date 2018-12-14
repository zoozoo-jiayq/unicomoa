$(function(){
	var userId = $("#userId").val();
	$("#btnAddTraining").click(function(){
		toAddOrUpdateTraining("");
		return false;
	});
	
	$("#btnRefreshTraining").click(function(){
		trainingList();
		return false;
	});
	trainingList();
});

function toAddOrUpdateTraining(trainingId){
	var userId = $("#userId").val();
	var openUrl = basePath+"/logined/recordOther/toAddTraining.action?trainingId="+trainingId+"&userId="+userId;
	window.open(openUrl,"","scrollbars=yes,top=100,left=300, width=830, height=600")
}

function trainingList(){
	var userId = $("#userId").val();
	qytx.app.grid({
		id	:	"dataTable_record",
		url	:	basePath + "/logined/recordOther/trainingList.action",
		iDisplayLength:	15,
		selectParam:{"userId":userId},
		valuesFn:	[{
			"aTargets" : [1],
			"fnRender" : function(oObj) {
				var trainingName = oObj.aData.trainingName;
				var html = "<span title='"+trainingName+"'>"+trainingName+"</span>";
				return html;
			}
		},
		{
			"aTargets" : [2],
			"fnRender" : function(oObj) {
				var trainMechanism = oObj.aData.trainMechanism;
				var html = "<span title='"+trainMechanism+"'>"+trainMechanism+"</span>";
				return html;
			}
		}
		,{
			"aTargets" : [6],
			"fnRender" : function(oObj) {
				var trainingId = oObj.aData.trainingId;
				var html = "<a style='cursor:pointer' onClick='viewTraining("+trainingId+")'>查看</a>&nbsp;<a style='cursor:pointer' onClick='updateTraining("+trainingId+")'>修改</a>";
				return html;
			}
		}]
	});
}

function viewTraining(trainingId){
	var openUrl = basePath+"/logined/recordOther/toAddTraining.action?trainingId="+trainingId+"&operType=detail";
	window.open(openUrl,"","scrollbars=yes,top=100,left=300, width=830, height=600")
}

function updateTraining(trainingId){
	var userId = $("#userId").val();
	var openUrl = basePath+"/logined/recordOther/toAddTraining.action?trainingId="+trainingId+"&userId="+userId;
	window.open(openUrl,"","scrollbars=yes,top=100,left=300, width=830, height=600")
}

