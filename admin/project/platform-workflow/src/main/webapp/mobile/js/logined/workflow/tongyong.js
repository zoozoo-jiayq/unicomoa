function getFormData(){
    //申请内容
    var applyContent = $.trim($("#applyContent").val());
    //申请原因
    var reason = $("#reason").val();

    var data = new Object();
    data.applyContent = applyContent;
    data.reason =  reason;
    data.imgs = photos;
    return JSON.stringify(data);
}

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
        data: {"formData":datas,"userIds":userIds,"userId":userId,"code":"tongyong"},
        success: function(msg){
            if(msg.indexOf("100||")==0){
            	 window.location.href = basePath+"mobile/logined/workflow/myStartList.jsp?_clientType=wap";
            }else{
            	$("#sbn").attr("disabled",false);
            }
        }
    });
});

//验证
function check(){
    var applyContent = $.trim($("#applyContent").val());
    var reason = $("#reason").val();
    if(applyContent == ""|| applyContent == null){
        h5Adapter.tips("请输入申请内容");
        return false;
    }else if(applyContent.length>=255){
        h5Adapter.tips("申请内容应小于255个字");
        return false;
    }else if(reason == ""||reason == null){
        h5Adapter.tips("请输入申请原因");
        return false;
    }else if(reason.length>=500){
        h5Adapter.tips("申请原因应小于500个字");
        return false;
    }else if(!userIds){
		h5Adapter.tips("请选择人员");
		return false;
	}else{
        return true;
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
        url:basePath+"/baseworkflow/view.c?instanceId="+$("#instanceId").val(),
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
    var formData = jQuery.parseJSON(result.formData);
    var applyContent = formData.applyContent;
    $("#applyContent").html(applyContent);
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