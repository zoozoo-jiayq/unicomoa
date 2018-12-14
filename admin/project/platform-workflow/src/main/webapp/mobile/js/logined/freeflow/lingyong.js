function getFormData(){
	//物品用处
	var useful = $.trim($("#useful").val());
	//备注
	var remark = $("#remark").val();
	var data = new Object();
	data.useful = useful;
	data.remark = remark;
//	data.imgs = photos;
	return JSON.stringify(data);
}

//验证
function check(){
	var useful = $.trim($("#useful").val());
	var remark = $("#remark").val();
	if(useful == ""||useful == null){
		mui.toast("请输入物品用处");
		return false;
	}
	if(useful.length>=255){
		mui.toast("物品用处应小于255个字");
		return false;
	}
	if(remark == ""||remark == null){
		mui.toast("请输入物品数量以及领用时间");
		return false;
	}
	if(remark.length>=500){
		mui.toast("物品数量以及领用时间应小于500个字");
		return false;
	}
	if(!userIds){
		mui.toast("请选择人员");
		return false;
	}
	return true;
}


mui.plusReady(function(){
	//提交
	$("#sbn").click(function(){
		$("#sbn").attr("disabled",true);
		var flag = check();
		if(!flag){
			$("#sbn").attr("disabled",false);
			return;
		}
		var datas = getFormData();
		var userId=window.windowCommon.approveLoginId;
		//发送数据
		jQuery.ajax({
			   type: "POST",
			   url: window.windowCommon.basePath+"baseworkflow/start.c?_clientType=wap",
			   data: {"formData":datas,"userIds":userIds,"userId":userId,"code":"lingyong"}, 
			   success: function(msg){
			     if(msg.indexOf("100||")==0){
			    	mui.toast("提交成功");
					//打开我的申请页面 
	            	mui.openWindow({
						url: '../fixedflow/myStartList.html',
						id:'myStartList'
					});
			     }else{
			     	 mui.toast("提交失败");
			    	 $("#sbn").attr("disabled",false);
			     }
			   }
		});
	});
});