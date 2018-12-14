
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
	if(createrPhoto!=null&&createrPhoto!=""&&createrPhoto!=undefined){
		 $("#photo").attr("src",createrPhoto);
		 $("#imgName").hide();
	 }else{
		 $("#photo").hide();
	 }
	$("#userName").html(createrName);
	$("#imgName").html(imgName);
	$("#imgDiv").addClass("head-bg-"+col);
	//表单数据各自流程按照自己的表单解析
	//事假1天
	var formData = jQuery.parseJSON(result.formData);
	var leaveType = formData.leaveType;
	var days = formData.days;
	$("#leaveType").html(leaveType+"<i>"+days+"</i>天");
	//时间范围:6月8号&nbsp;上午&nbsp;-&nbsp;6月9号&nbsp;下午
	var beginTime = formData.beginTime;
	var endTime = formData.endTime;
	$("#beginAndEndTime").html(beginTime+"&nbsp;-&nbsp;"+endTime);
	
	//原因
	var reason = formData.reason;
	$("#reason").html(reason);
	//审批历史
	processUserInfos(result.history,result.totalState,result.approverId,operation);
	//图片
//	processPhoto(formData.imgs);
}