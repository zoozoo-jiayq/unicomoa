mui.plusReady(function(){
	var self=plus.webview.currentWebview();
	var instanceId=self.instanceId;
	var operation=self.operation;
	$.ajax({
		url:window.windowCommon.basePath+"baseworkflow/view.c?instanceId="+instanceId,
		type:"POST",
		cache:true,
		success:function(msg){
			if(msg.indexOf("100||")==0){
				var result = msg.substring(5);
				showDataWithHTML(result,operation);
			}
		}
	});
});
function showDataWithHTML(result,operation){
	result =jQuery.parseJSON(result);
    //发起人的姓名和头像
    var createrName = result.createrName;
    var createrPhoto = result.createrPhoto;
    var imgName=createrName;
	 if(createrName!=null&&createrName.length>2){
	 	imgName=createrName.substring(createrName.length-2,createrName.length);
	 }
	 var col=letterCode(imgName);
	 $("#imgName").html(imgName);
	 $("#imgDiv").addClass("head-bg-"+col);
	 if(createrPhoto!=null&&createrPhoto!=""&&createrPhoto!=undefined){
		 $("#photo").attr("src",createrPhoto);
		 $("#imgName").hide();
	 }else{
		 $("#photo").hide();
	 }
    $("#userName").html(createrName);
    //表单数据各自流程按照自己的表单解析
    var formData = jQuery.parseJSON(result.formData);
    var applyContent = formData.applyContent;
    $("#applyContent").html(applyContent);
    //原因
    var reason = formData.reason;
    $("#reason").html(reason);
    //图片
//  processPhoto(formData.imgs);
    //审批历史
    processUserInfos(result.history,result.totalState,result.approverId,operation);
}