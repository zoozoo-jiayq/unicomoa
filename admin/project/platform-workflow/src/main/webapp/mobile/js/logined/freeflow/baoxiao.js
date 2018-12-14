mui.plusReady(function(){
	$("#rbSementType").focus(function(){
		var rbSementType = $.trim($("#rbSementType").val());
		$("#rbSementType").val(rbSementType);
		if(rbSementType==null||rbSementType==""){
			$("#rbSementType").attr("placeholder","请输入报销类别");
		}
	});
	$("#rbSementType").blur(function(){
		var rbSementType = $.trim($("#rbSementType").val());
		$("#rbSementType").val(rbSementType);
		if(rbSementType==null||rbSementType==""){
			$("#rbSementType").attr("placeholder","如：采购经费、活动经费");
		}
	});
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
	        data: {"formData":datas,"userIds":userIds,"userId":userId,"code":"baoxiao"},
	        success: function(msg){
	            if(msg.indexOf("100||")==0){
			    	mui.toast("提交成功");
					//打开我的申请页面 
	            	mui.openWindow({
						url: '../fixedflow/myStartList.html',
						id:'myStartList'
					});
			     }else{
			     	$("#sbn").attr("disabled",false);
			     	 mui.toast("提交失败");
			     }
	        }
	    });
	});
});

function getFormData(){
    //报销金额
    var rbSement = $.trim($("#rbSement").val());
    //报销类型
    var rbSementType = $.trim($("#rbSementType").val());
    //报销原因
    var reason = $("#reason").val();

    var data = new Object();
    data.rbSement = rbSement;
    data.rbSementType = rbSementType;
    data.reason =  reason;
//  data.imgs = photos;
    return JSON.stringify(data);
}


function check(){
    var rbSement=$.trim($("#rbSement").val());
    var l=0;
    if(rbSement!=null&&rbSement!=""){
    	var formatrb = rbSement.split(".");
    	if(formatrb[1]!=null&&formatrb[1]!=""&&formatrb[1]!=undefined){
    		l = formatrb[1].length;
    	}
    }
    var rbSementType = $.trim($("#rbSementType").val());
    var reason = $("#reason").val();
    if(rbSement == ""||rbSement == null){
    	mui.toast("请输入报销金额");
        return false;
    }else if(isNaN(rbSement)){
    	mui.toast("报销金额只能输入两位小数的数字");
    	return false;
    }else if(rbSement<0){
    	mui.toast("报销金额只能输入两位小数的数字");
    	return false;
    }else if(l>2){
    	mui.toast("报销金额只能输入两位小数的数字");
    	return false;
    }else if(rbSement.length>=255){
    	mui.toast("报销金额长度应小于255");
        return false;
    }else if(rbSementType == ""||rbSementType == null){
    	mui.toast("请输入报销类别");
        return false;
    }else if(rbSementType.length>=255){
    	mui.toast("报销类别应小于255个字");
        return false;
    }else if(reason == ""||reason == null){
    	mui.toast("请输入报销原因");
        return false;
    }else if(reason.length>=500){
    	mui.toast("报销原因应小于500个字");
        return false;
    }else if(!userIds){
    	mui.toast("请选择人员");
		return false;
	}else{
        return true;
    }
}

//IOS调用成功
function onSuccess(method,data){
	if(method="selectOneUser"){
		data = eval('('+data+')');
		processSelectedUser(data.userId, data.userName, data.photoUrl);
	}
}

