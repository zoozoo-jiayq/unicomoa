$(document).ready(function(){
	var moduleCode=$("#moduleCode").val();
	var type=$("#type").val();
	if(moduleCode&&moduleCode!="null"){
		if(moduleCode=="notify"){
			if(type=="view"){
				var instanceId=$("#instanceId").val();
				if(instanceId&&instanceId!="null"){
					notifyDetail(instanceId,35,1);
				}
			}else if(type=="list"){
				moreNotify();
			}else if(type=="add"){
				addNotify();
			}
			
		}else if(moduleCode=="workflow"){
			if(type=="list"){
				moreJbpmWaitApprove();
			}else if(type=="approve"){
				var instanceId=$("#instanceId").val();
				var processId=$("#processId").val();
				var executionId=$("#executionId").val();
				if(instanceId&&instanceId!="null"&&processId&&processId!="null"&&executionId&&executionId!="null"){
					jbpmWaitApprove(instanceId,processId,executionId);
				}
			}
		}
		//清除session
		$.get(basePath+"cleanSession.jsp?type=moduleCode");
	}
});


//公告详情
function notifyDetail(notifyId,columnId,returnType){
	var url = basePath+"logined/notify/view.jsp?notifyId="+notifyId+"&columnId="+columnId+"&returnType="+returnType+"&t="+Math.random();
	addTab("32",url,"公告查看",true,"");
}
//公告更多
function moreNotify(){
	var url = basePath+"logined/notify/viewList.jsp?id=35&t="+Math.random();
	addTab("32",url,"公告查看",true,"");
}

//待审批申请更多
function moreJbpmWaitApprove(){
	var url=basePath+"logined/jbpmApp/myjob/management.jsp";
    addTab("dsp",url,"待审批",true,"");
}

//待审批申请
function jbpmWaitApprove(taskId,processId,executionId){
	var url=basePath+"jbpmflow/detailSearch!toApply.action?processId="+processId+"&processInstanceId="+encodeURI(executionId)+"&taskId="+taskId+"&type=1";
    addTab("dsp",url,"待审批",true,"");
}
//工作流申请
function addJdpmFlow(processId){
	var url=basePath+"jbpmflow/detailSearch!toStartProcess.action?processId="+processId;
	addTab("92",url,"新增申请",true,"");
}


/**
* 生成随机数
* @param n
* @returns {String}
*/
function RndNum(n) {
	var rnd = "";
	for ( var i = 0; i < n; i++)
		rnd += Math.floor(Math.random() * 10);
	return rnd;
};
