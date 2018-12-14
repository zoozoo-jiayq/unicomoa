/**
 * 流程申请
 * author:CQL
 */
 var editor;
 $(document).ready(function() {
    var currentTaskCount = $("#currentTaskCount").val();
    setEnableUpload();
    if(currentTaskCount>2){
    	$("#nextLCDIV").hide();
    }else{
    	$("#nextLCDIV").show();
    }
    //表单处理逻辑
	async.series({
		init:function(callback){
			readonlyForm();
			$("body").lock();
			callback();
		},
		isHidden:function(callback){
			setFormHidden(callback);
		},
		loadData:function(callback){
			setFormDate(callback);
		},
		isCanEdit:function(callback){
			setWriteable(callback);
		},
		generateWidget:function(callback){
			$("body").unlock();
			initWidget(callback);
		}
	},function(err,result){
	}); 
});




/** add by 贾永强
 * 功能：驳回，回退任务
 * @param taskId
 */
function rollbackTask(taskId) {
    var advice = $("#advice").val();//处理意见
    if (advice&&advice == "") {
        art.dialog.alert("请输入处理意见！");
        return;
    }
    if (advice&&advice.length > 1000) {
        art.dialog.alert("输入内容不得超过1000个字符！");
        return;
    }
    var formDataJSON = getJSONVal();
    var attachJSON = getAttachJSON();
    var paramData = {
        'taskId': taskId,
        "advice": advice,
        "processInstanceId":encodeURI($("#instanceId").val()),
        "formDataJSON":formDataJSON,
        "attachJSON":attachJSON,
    };
    art.dialog.confirm('确认驳回吗？', function () {
        $.ajax({
            url: basePath + "jbpmflow/operation!rollbackTask.action",
            type: "post",
            dataType: "html",
            data: paramData,
            beforeSend:function(){
				$("body").lock();
			},
			complete:function(){
				$("body").unlock();
			},
            success: function (data) {
                if (data == 1) {
                	if($("#affireflag").val()=="affireflag"){
                		if(window.top && window.top.closeCurrentTab){
                			window.top.closeCurrentTab();
                		}else{
                			window.close();
                		}
		        	}else{
		        		window.history.go(-1);
		        	}
                } else {
                    art.dialog.alert('该工作流程不能驳回！');
                }
            }
        });
    }, function () {
        return;
    });
}

/*
 *  add by jiayq
 *  完成任务
 */
function completeTask(){
	var advice =$("#advice").val();//内容
	var formDataJSON = getJSONVal();
	var attachJSON = getAttachJSON();
	var taskId = $("#taskId").val();
	var holdStr=$("#holdStr").val();//主办人员ID
	var outComeName= $("#outComeName").val();  //节点 到TO
    var currentTaskCount = $("#currentTaskCount").val();
    if(!currentTaskCount){
    	currentTaskCount = 1;
    }
    //不是结束节点，不是会签的最后一个办理人节点，才判断办理人员为空
	if(outComeName!='TO 结束'  && !(currentTaskCount>1)  && !holdStr){
		art.dialog.alert("请选择办理人员！");
		return ;
	}
	if(advice&&advice.length>50){
		  art.dialog.alert("输入内容不得超过50个字符！");
		  return;
	}
	$("body").lock();
	$("#sendButton").attr("disabled","disabled");
	var paramData = {
		"advice":advice,
		"formDataJSON":formDataJSON,
		"attachJSON":attachJSON,
		"taskId":taskId,
		"holdStr":holdStr,
		"outComeName":outComeName
	};
    $.ajax({
	    url: basePath + "jbpmflow/operation!completdTask.action",
	    type: "post",
	    dataType: "html",
	    data: paramData,
	    beforeSend:function(){
  			$("body").lock();
  	    },
  		complete:function(){
  			$("body").unlock();
  		},
	    success: function (data) {
	    	if(data == "success"){
	        	$("#sendButton").val("提交");
	        	qytx.app.dialog.tips("操作成功!",function(){
	        		if($("#affireflag").val()=="affireflag"){
	        			if(window.top && window.top.closeCurrentTab){
	        				window.top.closeCurrentTab();
	        			}else{
	        				window.close();
	        			}
		        	}else{
		        		window.location.href = basePath+"logined/jbpmApp/myjob/management.jsp";
		        	}
	        	});
	        }else{
	        	$("#sendButton").val("提交");
	        	art.dialog.alert("服务异常，请稍后重试!");
	        }
	    }
	});
}

/**
 *提交表单
 */
function form_submit() {
    var item = $("input[name='mySuggestion']:checked").val();
    if (item == 1) {
        $("#approvalResult").val("同意");
        completeTask();
    }
    else if (item == 0) {
        var taskId = $("#taskId").val();
        $("#approvalResult").val("不同意");
        rollbackTask(taskId);
    }
}

$(function(){
    $(":radio").click(function(){
        var val=$(this).val();
        if(val==0)
        {
            $("#approvalResult").val("不同意");
            $("#advice").val("不同意");
            $("#nextLCDIV").hide();
        }
        else
        {
            $("#approvalResult").val("同意");
            $("#advice").val("同意");
             $("#nextLCDIV").show();
        }
    });
    $("#advice").val("同意");
});

//返回
function goback(){
	if($("#affireflag").val()=="affireflag"){
		if(window.top && window.top.closeCurrentTab){
			window.top.closeCurrentTab();
		}else{
			window.close();
		}
	}else{
		window.location.href = document.referrer;
	}
}

//初始化附件列表
(function(){
    $("#uploadTable").find("li").each(function(){
        var temp = this;
        var attachName = $(".attachName",temp).html();
        var className = getClassByFileTypeApply(attachName);
        $(".icon",temp).find("em").removeClass();
        $(".icon",temp).find("em").addClass(className);
    });
})();
