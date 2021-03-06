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
	//外出2小时
	var formData = jQuery.parseJSON(result.formData);
	var outTime = formData.outTime;
	$("#outTime").html("外出"+"<i>"+outTime+"</i>小时");
	
	//时间范围:6月8号&nbsp;上午&nbsp;-&nbsp;6月9号&nbsp;下午
	var beginTime = formData.beginYMD+""+formData.beginHMS;
	var endTime = formData.endYMD+""+formData.endHMS;
	
	$("#beginAndEndTime").html(beginTime+"&nbsp;-&nbsp;"+endTime);
	//原因
	var reason = formData.reason;
	$("#reason").html(reason);
	//图片
//	processPhoto(formData.imgs);
	//审批历史
	processUserInfos(result.history,result.totalState,result.approverId,operation);
}  