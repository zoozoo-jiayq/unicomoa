 $(document).ready(function() {
     var currentCategoryId=$("#currentCategoryId").val();
     //当前第一个节点
     toSubProcessList(currentCategoryId);
     $(".service-menu>p").first().addClass("on");
});

/**
 * 提交流程申请，跳转到流程起草页面
 * @param processId
 */
function apply(processId){
	applyFlow(processId);
}

/*
* 申请新收文文流程
*/
function applyDomflowGather(processId){
	if(window.parent && window.parent.parent && window.parent.parent.parent && window.parent.parent.parent.addTab){
		window.parent.parent.parent.addTab(Math.random(),basePath+"/domflow/operation!toAddGather.action?processId="+processId,"录入收文基本信息");
	}else{
		window.open(basePath+"/domflow/operation!toAddGather.action?processId="+processId,"录入收文基本信息");
	}
}

/*
* 申请发文流程
*/
function applyDomflowDispatch(processId){
	if(window.parent && window.parent.parent && window.parent.parent.parent && window.parent.parent.parent.addTab){
		window.parent.parent.parent.addTab(Math.random(),basePath+"/domflow/operation!toAddDispatche.action?processId="+processId,"录入发文基本信息");
	}else{
		window.open(basePath+"/domflow/operation!toAddDispatche.action?processId="+processId,"录入发文基本信息");
	}
}

/**
* 申请工作流
*/
function applyFlow(processId){
	var url = basePath+"jbpmflow/detailSearch!toStartProcess.action?processId="+processId;
	if(window.top && window.top.addTab){
		window.top.addTab(Math.random(),url,"填写申请");
	}else{
		window.open(url, "填写申请");
	}

}

 /**
  *  跳转到子流程列表
  * @param categoryId
  */
 function toSubProcessList(categoryId)
 {
     if(categoryId){
    	 $("#page").attr("src",basePath+"jbpmflow/listSearch!categorylist.action?categoryId="+categoryId+"&type="+$("#type").val()+"&domType="+$("#domType").val());
     }
 }
 
 function addSelectState(Obj){
	 $(".service-menu>p").removeClass("on");
	 $(Obj).addClass("on");
 }

