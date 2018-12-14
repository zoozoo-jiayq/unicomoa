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
    data.imgs = photos;
    return JSON.stringify(data);
}
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
    var userId = h5Adapter.getItemValue("currentUserId");
    //发送数据
    jQuery.ajax({
        type: "POST",
        url: basePath+"/baseworkflow/start.c?_clientType=wap",
        data: {"formData":datas,"userIds":userIds,"userId":userId,"code":"baoxiao"},
        success: function(msg){
            if(msg.indexOf("100||")==0){
                window.location.href = basePath+"mobile/logined/workflow/myStartList.jsp?_clientType=wap";
            }else{
            	$("#sbn").attr("disabled",false);
            }
        }
    });
});

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
        h5Adapter.tips("请输入报销金额");
        return false;
    }else if(isNaN(rbSement)){
    	h5Adapter.tips("报销金额只能输入两位小数的数字");
    	return false;
    }else if(rbSement<0){
    	h5Adapter.tips("报销金额只能输入两位小数的数字");
    	return false;
    }else if(l>2){
    	h5Adapter.tips("报销金额只能输入两位小数的数字");
    	return false;
    }else if(rbSement.length>=255){
        h5Adapter.tips("报销金额长度应小于255");
        return false;
    }else if(rbSementType == ""||rbSementType == null){
        h5Adapter.tips("请输入报销类别");
        return false;
    }else if(rbSementType.length>=255){
        h5Adapter.tips("报销类别应小于255个字");
        return false;
    }else if(reason == ""||reason == null){
        h5Adapter.tips("请输入报销原因");
        return false;
    }else if(reason.length>=500){
        h5Adapter.tips("报销原因应小于500个字");
        return false;
    }else if(!userIds){
		h5Adapter.tips("请选择人员");
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

//从后台加载表单数据
function loadData(){
	h5Adapter.getItem($("#instanceId").val(), function(result){
		if(result){
			//showDataWithHTML(result);
		}
	});
    $.ajax({
        url:basePath+"/baseworkflow/view.c?_clientType=wap&instanceId="+$("#instanceId").val(),
        type:"POST",
        cache:false,
        success:function(msg){
            if(msg.indexOf("100||")==0){
                var result = msg.substring(5);
                showDataWithHTML(result);
                h5Adapter.setItem($("#instanceId").val(), result);
            }
        }
    });
}

function showDataWithHTML(result){
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
     //金额：金额<i>100.00</i>元
     var formData = jQuery.parseJSON(result.formData);
     var rbSement = formData.rbSement;
     $("#rbSement").html("金额<i>"+rbSement+"</i>元");
     var rbSementType = formData.rbSementType;
     $("#rbSementType").html(rbSementType);
     //原因
     var reason = formData.reason;
     $("#reason").html(reason);
     //图片
     processPhoto(formData.imgs);
     //审批历史
     processUserInfos(result.history,result.totalState,result.approverId);
}
/**
 * webview加载完毕初始化导航条按钮
 */
function loadSuccess(isNetOk){
	h5Adapter.onHeaderButton(null,{"type":"back","onClickMethod":"goBack"});
}

//退出调查问卷模块
function goBack(){
	var operation=$("#operation").val();
	if(operation!=null&&operation!=""&&operation!=undefined&&operation=="view"){
		window.location.href = basePath+"mobile/logined/workflow/myApproveList.jsp?_clientType=wap&flag=processed";
	}else{
		h5Adapter.backLast();
	}
}
