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
	 var col = Math.floor(Math.random()*7+1);
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
	//目的地和天数：北京<i>2.5</i>天
	var formData = jQuery.parseJSON(result.formData);
	var destion = formData.destination;
	var days = formData.days;
	$("#destion").html(destion+"<i>"+days+"</i>天");
	//时间范围:6月8号&nbsp;上午&nbsp;-&nbsp;6月9号&nbsp;下午
	var beginTime = formData.beginTime;
	var endTime = formData.endTime;
	$("#beginAndEndTime").html(beginTime+"&nbsp;-&nbsp;"+endTime);
	//原因
	var reason = formData.reason;
	$("#reason").html(reason);
	//图片
//	processPhoto(formData.imgs);
	//审批历史
	processUserInfos(result.history,result.totalState,result.approverId,operation);
}