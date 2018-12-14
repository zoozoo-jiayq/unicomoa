/**
 * 审批
 */
var xgird;
$(document).ready(function() {
	setNotifyType();
	xgird = new qytx.app.grid({
		id:"myTable",
		url:basePath + "notify/notify_viewApproveList.action",
		selectParam:{"columnId":$("#columnId").val(),"status":1},
		valuesFn:[{
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
						return topTemplate+'<a title = "'+subject+'"  href="'+ basePath + 'logined/notify/view.jsp?notifyId='+ id + '&columnId='+columnId+'&returnType=5">' + subject + '</a>'
					}          
				},{
					"aTargets" : [4],
					"fnRender" : function(oObj) {
						var status = oObj.aData.status;
						var isEdit = oObj.aData.isEdit;
						var id = oObj.aData.id;
						var isEdit = oObj.aData.isEdit;
						var html ="";
						if(isEdit==1){
							html='<a style="cursor:pointer" title="修改"  href="'+ basePath + 'logined/notify/viewUpdate.jsp?id='+ id+ '&columnId='+$("#columnId").val()+'">修改</a>';
						}
						html+='<a style="cursor:pointer;" title="审核" onclick="auditer('+id+')">审核</a>';
						return html;
					}
					}]
		
	});
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
			$("select[name='notifyType']").empty();
			$("select[name='notifyType']").append("<option value=''>全部类型</option>");
			for (var i = 0; i < jsonData.length; i++) {
				$("select[name='notifyType']").append("<option value='"
						+ jsonData[i].value + "'>" + jsonData[i].name
						+ "</option>");
			}
		}
	});
}

function rediectCheckPerson(id){
		var columnId = $("#columnId").val();
		var url = basePath + "logined/notify/viewNotify.jsp?id=" + id+"&columnId="+columnId+"&category="+2;
		window.location.href=url;
	}

/**
 * 批准操作
 * 
 * @param notifyId
 * @return
 */
function auditer(notifyId) {
	var columnId = $("#columnId").val();
	window.location.href = basePath + 'logined/notify/viewApprove.jsp?notifyId='
			+ notifyId + "&columnId=" + columnId;
}
/**
 * 修改操作
 * 
 * @param notifyId
 * @return
 */
function modifyNotify(id,status) {
	var columnId = $("#columnId").val();
	window.location.href = basePath
			+ 'logined/notify/viewApproveUpdate.jsp?id=' + id
			+ "&columnId=" + columnId+"&status="+status;
}