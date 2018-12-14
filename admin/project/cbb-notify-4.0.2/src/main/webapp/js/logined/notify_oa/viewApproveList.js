/**
 * 审批
 */
var xgird;
$(document).ready(function() {
	setNotifyType();
	xgird = new qytx.app.grid({
		id:"myTable",
		url:basePath + "notify/notify_viewApproveList.action",
		selectParam:{"subject":$("#subject").val(),"notifyType":$("#notifyType").val(),"columnId":$("#columnId").val(),"beginDate":$("#beginDate").val(),"endDate":$("#endDate").val(),"status":2},
		valuesFn:[
					{
						"aTargets" : [0],
						"fnRender" : function(oObj) {
							var id = oObj.aData.id;
							var columnId = $("#columnId").val();
							var subject = oObj.aData.subject;
							var isTop = oObj.aData.isTop;
							var topTemplate = "";
							if(isTop==1){
								topTemplate = '<font color="red"><b>[置顶]&nbsp;&nbsp;</b></font>';
							}
							return topTemplate+'<a  title = "'+subject+'"   href="'+ basePath + 'logined/notify/viewApproveDetails.jsp?notifyId='+ id +'&columnId='+columnId+'&returnType=3">' + subject + '</a>'
						}          
					},{
						"aTargets" : [5],
						"fnRender" : function(oObj) {
							var status = oObj.aData.status;
							if(status==0){
								return "草稿";
							}else if(status==1){
								return "待审核";
							}else if(status==2){
								return "通过";
							}else if(status==3){
								return "<font color=\"red\">不通过</font>"; 
							}else if(status==4){
								return "已终止";
							}
						}            
					},{
						"aTargets":[6],
						"fnRender":function(oObj){
							var columnId = $("#columnId").val();
							var id = oObj.aData.id;
							return '<a href="'+ basePath + 'logined/notify/viewApproveDetails.jsp?notifyId='+ id + '&columnId='+columnId+'&returnType=3">查看</a>'
						}
					}
					]
	});
	// 回车事件
	$(document).keydown(function(event) {
		var code = event.which;
		if (code == 13) {
			xgird.query();
			return false;
		}
	});
	
	$("input[type='button']").click(function(){
		xgird.query();
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
	qytx.app.ajax({
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
