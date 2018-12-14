function getFormData(){
    //申请内容
    var applyContent = $.trim($("#applyContent").val());
    //申请原因
    var reason = $("#reason").val();

    var data = new Object();
    data.applyContent = applyContent;
    data.reason =  reason;
//  data.imgs = photos;
    return JSON.stringify(data);
}

//验证
function check(){
    var applyContent = $.trim($("#applyContent").val());
    var reason = $("#reason").val();
    if(applyContent == ""|| applyContent == null){
    	mui.toast("请输入申请内容");
        return false;
    }else if(applyContent.length>=255){
    	mui.toast("申请内容应小于255个字");
        return false;
    }else if(reason == ""||reason == null){
    	mui.toast("请输入申请原因");
        return false;
    }else if(reason.length>=500){
    	mui.toast("申请原因应小于500个字");
        return false;
    }else if(!userIds){
    	mui.toast("请选择人员");
		return false;
	}else{
        return true;
    }
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
	        data: {"formData":datas,"userIds":userIds,"userId":userId,"code":"tongyong"},
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