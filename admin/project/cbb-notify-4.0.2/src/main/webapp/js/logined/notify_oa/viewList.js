/**
 * 页面加载时执行
 */
var xgrid;
$(document).ready(function() {
		if(!setNotifyType()){
			xgrid = new qytx.app.grid({
				id:"myTable",
				url:basePath + "notify/notify_viewList.action",
				selectParam:{"columnId":$("#columnId").val(),"isShowOut":1},
				valuesFn:[{
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
								if(isTop==1){
									return '<a title="'+subject+'" style="text-decoration:none;color:#04578D;text-align:left;'+fontWeight+'"  href="'+ basePath+ 'logined/notify/view.jsp?notifyId='+ oObj.aData.notifyId + '&columnId='+columnId+'&returnType=1"><font color="red"><b>'+"[置顶]&nbsp;&nbsp;</b></font>"+subject + '</a>';
								}else{
									return '<a title="'+subject+'" style="text-decoration:none;color:#04578D;text-align:left;'+fontWeight+'"  href="'+ basePath+ 'logined/notify/view.jsp?notifyId='+ oObj.aData.notifyId+ '&columnId='+columnId+'&returnType=1">'+ subject+ '</a>';
								}
							}            
						},{
							"aTargets" : [1],
							"fnRender" : function(oObj) {
								var typename = oObj.aData.typename;
								if(typename==null){
									return "--";
								}else{
									return typename;
								}
							}
						}]
			});
		}
		
		//回车事件
		$(document).keydown(function(event){
			var code=event.which;
			if (code == 13) {
				xgrid.query();
				return false;
			}
		});
		$("#searchButton").click(function(){
			xgrid.query();
		});
})
/**
 * 获取公告类型
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
			for (var i = 0; i < jsonData.length; i++) {
				$("select[name='notifyType']").append("<option value='"
						+ jsonData[i].value + "'>" + jsonData[i].name
						+ "</option>");
			}
		}
	});
}
