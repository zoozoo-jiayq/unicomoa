$(document).ready(function() {
	var userId = $("#userId").val();
	$("#btnAddLearn").click(function(){
		toAddOrUpdate("");
		return false;
	});
	$("#btnRefreshLearn").click(function(){
		learnList();
		return false;
	});
	learnList();
} );

function toAddOrUpdate(learnId){
	var userId = $("#userId").val();
	var openUrl = basePath+"/logined/recordOther/toAddlearn.action?learnId="+learnId+"&userId="+userId;
	window.open(openUrl,"","scrollbars=yes,top=100,left=300, width=830, height=600")
}

var learnList=function(){
	qytx.app.grid({
		id:'myTable',
		url:basePath + "/logined/recordOther/listlearn.action?userId="+userId,
		iDisplayLength:	15,
		valuesFn:[{
					"aTargets" : [2],
					"fnRender" : function(oObj) {
						var major = oObj.aData.major;
						var html = "<span title='"+major+"' class='longTxt'>"+major+"</span>";
						return html;
					}
				},{
					"aTargets" : [4],
					"fnRender" : function(oObj) {
						var school = oObj.aData.school;
						var html = "<span title='"+school+"' class='longTxt'>"+school+"</span>";
						return html;
					}
				},{
					"aTargets" : [5],
					"fnRender" : function(oObj) {
						var reterence = oObj.aData.reterence;
						var html = "<span title='"+reterence+"' class='longTxt'>"+reterence+"</span>";
						return html;
					}
				},{
	                    "aTargets": [6],// 覆盖第6列
	                    "fnRender": function ( oObj ) {
	                        var learnId =oObj.aData.learnId;
	                    	var res="";      
					        res+="<a style=\"cursor: pointer;\"  onclick=\"goShowView("+learnId+")\" >查看</a><a style=\"cursor: pointer;\"  onclick=\"goUpdate("+learnId+")\" >修改</a>";
					        return   res;
	                    }
	                }
			]
		});
}

var goShowView=function(learnId){
	var openUrl = basePath+"/logined/recordOther/findByIdlearn.action?learnId="+learnId;
	window.open(openUrl,"","scrollbars=yes,top=100,left=300, width=830, height=600")
}

var goUpdate=function(learnId){
	var openUrl = basePath+"/logined/recordOther/toAddlearn.action?learnId="+learnId;
	window.open(openUrl,"","scrollbars=yes,top=100,left=300, width=830, height=600")
}